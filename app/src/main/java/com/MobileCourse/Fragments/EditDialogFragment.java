package com.MobileCourse.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.MobileCourse.R;
import com.google.android.material.badge.BadgeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDialogFragment extends DialogFragment {

    public static final String TAG = "EditDialogFragment";

    public interface ConfirmCallback {
        void confirmCallback(String text);
    }

    @BindView(R.id.cancelButton)
    TextView cancelButton;

    @BindView(R.id.confirmButton)
    Button confirmButton;

    @BindView(R.id.edit)
    EditText editText;

    @BindView(R.id.editDialogTitle)
    TextView editDialogTitleTextView;

    ConfirmCallback confirmCallbackObj;

    String originText;
    String title;

    public static EditDialogFragment display(String title,String originText,ConfirmCallback confirmCallbackObj,FragmentManager fragmentManager) {
        EditDialogFragment dialogFragment = new EditDialogFragment();
        dialogFragment.confirmCallbackObj = confirmCallbackObj;
        dialogFragment.originText = originText;
        dialogFragment.title = title;

        dialogFragment.show(fragmentManager, TAG);
        return dialogFragment;
    }

    public void init(){
        editText.setText(originText);
        editDialogTitleTextView.setText(title);

        confirmButton.setEnabled(false);

        cancelButton.setOnClickListener((View view)->{
            dismiss();
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nowText = s.toString();
                if(nowText.equals(originText)){
                    confirmButton.setEnabled(false);
                } else {
                    confirmButton.setEnabled(true);
                }
            }
        });

        confirmButton.setOnClickListener((View view)->{
            this.confirmCallbackObj.confirmCallback(editText.getText().toString());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_edit_dialog, container, false);
        ButterKnife.bind(this,view);
        init();
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