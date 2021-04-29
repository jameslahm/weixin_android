package com.MobileCourse.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Discover;
import com.MobileCourse.Adapters.DiscoverAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.MobileCourse.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.MobileCourse.Fragments.DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {
    private RecyclerView recyclerView;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreFragment.
     */
    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO
        recyclerView = (RecyclerView) view.findViewById(R.id.discover_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        LinkedList<Discover> discovers = new LinkedList<>();

        discovers.add(new Discover(getString(R.string.nickname1),R.drawable.avatar2,getString(R.string.paragraph1),"1小时前",new ArrayList<>(List.of(R.drawable.image1))));

        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discovers);
        recyclerView.setAdapter(discoverAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }
}