package com.MobileCourse.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.MobileCourse.Activities.ChatActivity;
import com.MobileCourse.Activities.NewFriendActivity;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.Contact;
import com.MobileCourse.Adapters.ContactAdapter;
import java.util.LinkedList;
import java.util.List;

import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Repositorys.GroupRepository;
import com.MobileCourse.ViewModels.ApplicationViewModel;
import com.MobileCourse.ViewModels.FriendsViewModel;
import com.MobileCourse.ViewModels.MeViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";

    @BindView(R.id.contacts_recylerview)
    RecyclerView recyclerView;

    Context context;

    FriendsViewModel friendsViewModel;
    ApplicationViewModel applicationViewModel;
    MeViewModel meViewModel;

    @BindView(R.id.newFriend)
    ViewGroup newFriendViewGroup;

    @BindView(R.id.new_friend_icon)
    ImageView newFriendIconImageView;

    @BindView(R.id.newGroup)
    ViewGroup newGroupViewGroup;

    BadgeDrawable badgeDrawable;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        friendsViewModel = new ViewModelProvider(getActivity()).get(FriendsViewModel.class);
        applicationViewModel = new ViewModelProvider(getActivity()).get(ApplicationViewModel.class);
        meViewModel = new ViewModelProvider(getActivity()).get(MeViewModel.class);

        ContactAdapter contactAdapter = new ContactAdapter(new ContactAdapter.ContactDiff(),(User user,View view1)->{
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra(ChatActivity.CHAT_TIMELINE_ID,user.getId());
            intent.putExtra(ChatActivity.CREATE_TIMELINE,"USER");
            intent.putExtra(ChatActivity.CREATE_TIMELINE_USER,user);
            startActivity(intent);
        },(user -> {
            meViewModel.deleteFriend(user).observe(getViewLifecycleOwner(),(resource -> {
                if(resource!=null){
                    switch (resource.status){
                        case ERROR:{
                            Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SUCCESS:{
                            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }));
        }),false);
        recyclerView.setAdapter(contactAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        friendsViewModel.getFriends().observe(getViewLifecycleOwner(), contactAdapter::submitList);

        newFriendViewGroup.setOnClickListener((view1)->{
            Intent intent = new Intent(getContext(), NewFriendActivity.class);
            startActivity(intent);
        });
        applicationViewModel.getApplications().observe(getViewLifecycleOwner(),
                (applications)->{
                    // TODO FIXME should show unread applications
                    int count =  (int) applications.stream().filter((application -> {
                        return application.isUnRead();
                    })).count();
                    if(badgeDrawable==null){
                        badgeDrawable = BadgeDrawable.create(context);
                    }
                    badgeDrawable.setNumber(count);
                    Log.e(TAG,String.valueOf(count));
                    if(badgeDrawable.getNumber()>0){
                        badgeDrawable.setVisible(true);
                        BadgeUtils.attachBadgeDrawable(badgeDrawable, newFriendIconImageView);
                    } else {
                        badgeDrawable.setVisible(false);
                        BadgeUtils.detachBadgeDrawable(badgeDrawable,newFriendIconImageView);
                    }
                });

        newGroupViewGroup.setOnClickListener((View view1)->{
            NewGroupFragment.display("新建群聊",(String name, List<String> members)->{
                GroupRepository.getInstance(context).createGroup(name,members).observe(getViewLifecycleOwner(),(resource)->{
                    if(resource!=null){
                        switch (resource.status){
                            case SUCCESS:{
                                if(resource.data!=null){
                                    Toast.makeText(context, "创建群聊成功", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                    }
                });
            },getFragmentManager(),true);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = getContext();
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}