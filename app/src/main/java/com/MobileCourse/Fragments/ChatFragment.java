package com.MobileCourse.Fragments;


import com.MobileCourse.Activities.ChatActivity;
import com.MobileCourse.Models.Chat;
import com.MobileCourse.Adapters.ChatAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.ChatViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;
import com.MobileCourse.WebSocket.MessageApi;
import com.MobileCourse.WebSocket.MessageService;
import com.tinder.scarlet.WebSocket;

import java.util.LinkedList;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ChatFragment extends Fragment {
    private ChatAdapter chatAdapter;
    private LinkedList<Chat> data;

    @BindView(R.id.listview)
    RecyclerView listView;

    private MessageApi messageApi = MessageService.getInstance().getMessageApi();

    private static String tag = "ChatFragment";

    ChatViewModel chatViewModel;

    public ChatFragment() {
        // Required empty public constructor
    }

    String timeLineId;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    private TimeLineViewModel timeLineViewModel;

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);

        timeLineViewModel = new ViewModelProvider(getActivity()).get(TimeLineViewModel.class);
        chatViewModel = new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        chatAdapter = new ChatAdapter(new ChatAdapter.ChatDiff(),(chat)->{
            Intent intent = new Intent(getContext(), ChatActivity.class);
            timeLineId = chat.getId();
            intent.putExtra(ChatActivity.CHAT_TIMELINE_ID,chat.getId());
            intent.putExtra(ChatActivity.CREATE_TIMELINE,"");
            startActivityForResult(intent,1);
        },getContext());
        listView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);

        chatViewModel.getChatsLiveData().observe(getViewLifecycleOwner(),(chats -> {
            chatAdapter.submitList(chats);
        }));

//        messageApi.observeMessage().subscribe((message)->{
//            Log.e(tag,String.valueOf(message));
//        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}