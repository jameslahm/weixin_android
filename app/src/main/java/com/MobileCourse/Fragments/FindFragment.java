package com.MobileCourse.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.MobileCourse.Components.WechatSubmenu;
import com.MobileCourse.R;
import com.MobileCourse.Utils.EventListenerUtil;

public class FindFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.social_circle_menu)
    WechatSubmenu socialCircleMenu;
    
    public FindFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init();
    }

    public void init(){
        EventListenerUtil.addViewsListener(this, new int[]{
                R.id.social_circle_menu, R.id.scan_menu, R.id.swip_menu,
                R.id.look_menu, R.id.search_menu, R.id.people_nearby_menu,
                R.id.shop_menu, R.id.game_menu, R.id.applets_menu
        }, this);

        socialCircleMenu.rotate();

        socialCircleMenu.setOnClickListener((view)->{
            DiscoverFragment.display(getFragmentManager());
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), v.getId() + "", Toast.LENGTH_SHORT).show();
    }
}
