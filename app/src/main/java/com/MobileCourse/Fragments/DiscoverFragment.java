package com.MobileCourse.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Discover;
import com.MobileCourse.Adapters.DiscoverAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.DiscoverViewModel;
import com.MobileCourse.ViewModels.MeViewModel;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DiscoverFragment extends DialogFragment {
    private static final String TAG = "DiscoverFragment";

    @BindView(R.id.discover_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.newDiscover)
    ImageView newDiscoverImageView;

    @BindView(R.id.iv_return)
    ImageView returnImageView;

    private DiscoverViewModel discoverViewModel;
    private MeViewModel meViewModel;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    public static DiscoverFragment display(FragmentManager fragmentManager) {
        DiscoverFragment discoverFragment = new DiscoverFragment();
        discoverFragment.show(fragmentManager, TAG);
        return discoverFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO
        discoverViewModel = new ViewModelProvider(getActivity()).get(DiscoverViewModel.class);
        meViewModel = new ViewModelProvider(getActivity()).get(MeViewModel.class);

        recyclerView = (RecyclerView) view.findViewById(R.id.discover_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(new DiscoverAdapter.DiscoverDiff(), meViewModel.getMe().getValue(),
                new DiscoverAdapter.OnClickCallback() {
                    @Override
                    public void onLikeCallback(Discover discover) {
                        discoverViewModel.likeDiscover(discover.getId()).observe(getViewLifecycleOwner(),(resource)->{
                            switch (resource.status){
                                case SUCCESS:{
                                    Log.e(TAG,"Like Success");
                                    break;
                                }
                                case ERROR:{
                                }
                            }
                        });
                    }

                    @Override
                    public void onUnLikeCallback(Discover discover) {
                        discoverViewModel.unLikeDiscover(discover.getId()).observe(getViewLifecycleOwner(),(resource)->{
                            switch (resource.status){
                                case SUCCESS:{
                                    Log.e(TAG,"UnLike Success");
                                    break;
                                }
                                case ERROR:{
                                }
                            }
                        });
                    }

                    @Override
                    public void onReplyCallback(Discover discover) {
                        EditDialogFragment.display("发表回复","",(text -> {
                            discoverViewModel.replyDiscover(discover.getId(),text).observe(getViewLifecycleOwner(),(resource -> {
                                switch (resource.status){
                                    case ERROR:{
                                        break;
                                    }
                                    case SUCCESS:{
                                        Log.e(TAG,"REPLY SUCCESS");
                                        break;
                                    }
                                }
                            }));

                        }),getFragmentManager());
                    }
                });
        recyclerView.setAdapter(discoverAdapter);

        discoverViewModel.getDiscovers().observe(getViewLifecycleOwner(),(resource)->{
            switch (resource.status){
                case LOADING:{
                    if(resource.data!=null){
                        discoverAdapter.submitList(resource.data);
                    }
                    break;
                }
                case SUCCESS:{
                    discoverAdapter.submitList(resource.data);
                    break;
                }
                case ERROR:{
                    discoverAdapter.submitList(new ArrayList<>());
                    break;
                }
            }
        });

        newDiscoverImageView.setOnClickListener((view1)->{
            CreateDiscoverFragment.display((text,images,video)->{
                discoverViewModel.createDiscover(text,images,video).observe(this,(resource)->{
                    switch (resource.status){
                        case ERROR:{
                            break;
                        }
                        case SUCCESS:{
                            Toast.makeText(getContext(),"发表成功",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                });
            },getFragmentManager());
        });

        returnImageView.setOnClickListener((view1)->{
            dismiss();
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this,view);
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
}