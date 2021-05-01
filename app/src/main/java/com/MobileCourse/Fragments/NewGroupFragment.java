package com.MobileCourse.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Activities.ChatActivity;
import com.MobileCourse.Activities.NewFriendActivity;
import com.MobileCourse.Adapters.ContactAdapter;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.FriendsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGroupFragment extends DialogFragment {
    public static final String TAG = "NewGroupFragment";

    public interface ConfirmCallback {
        void confirmCallback(String text, List<String> members);
    }

    @BindView(R.id.contacts_recylerview)
    RecyclerView recyclerView;

    @BindView(R.id.cancelButton)
    TextView cancelButton;

    @BindView(R.id.confirmButton)
    Button confirmButton;

    @BindView(R.id.edit)
    EditText editText;

    FriendsViewModel friendsViewModel;

    @BindView(R.id.editDialogTitle)
    TextView editDialogTitleTextView;

    String title;
    boolean showEdit;

    HashSet<String> members = new HashSet<>();

    NewGroupFragment.ConfirmCallback confirmCallbackObj;

    public static NewGroupFragment display(String title,NewGroupFragment.ConfirmCallback confirmCallbackObj, FragmentManager fragmentManager, boolean showEdit) {
        NewGroupFragment dialogFragment = new NewGroupFragment();
        dialogFragment.confirmCallbackObj = confirmCallbackObj;
        dialogFragment.title = title;
        dialogFragment.showEdit = showEdit;
        dialogFragment.show(fragmentManager, TAG);
        return dialogFragment;
    }

    public void init(){

        cancelButton.setOnClickListener((View view)->{
            dismiss();
        });

        confirmButton.setOnClickListener((View view)->{
            this.confirmCallbackObj.confirmCallback(editText.getText().toString(),new ArrayList<>(members));
            dismiss();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_new_group, container, false);
        ButterKnife.bind(this,view);
        init();


        if(!showEdit){
            editText.setVisibility(View.INVISIBLE);
        }

        editDialogTitleTextView.setText(title);

        ContactAdapter contactAdapter = new ContactAdapter(new ContactAdapter.ContactDiff(),(User user, View view1)->{
            CheckBox checkBox = (CheckBox)view1;
            if(checkBox.isChecked()){
                members.add(user.getId());
            } else {
                members.remove(user.getId());
            }
        },true);
        recyclerView.setAdapter(contactAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        friendsViewModel = new ViewModelProvider(getActivity()).get(FriendsViewModel.class);

        friendsViewModel.getFriends().observe(getViewLifecycleOwner(), contactAdapter::submitList);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }
}
