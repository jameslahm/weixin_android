package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.Discover;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.LinkedList;

import hilt_aggregated_deps._com_MobileCourse_ViewModels_TimeLineViewModel_HiltModules_BindsModule;

public class DiscoverAdapter extends ListAdapter<Discover,DiscoverAdapter.DiscoverViewHolder> {
    public static interface OnClickCallback {
        void onLikeCallback(Discover discover);
        void onUnLikeCallback(Discover discover);
        void onReplyCallback(Discover discover);
    }

    private User me;
    private OnClickCallback onClickCallbackObj;

    public DiscoverAdapter(@NonNull DiffUtil.ItemCallback<Discover> diffCallback, User me, OnClickCallback onClickCallbackObj) {
        super(diffCallback);
        this.me = me;
        this.onClickCallbackObj = onClickCallbackObj;
    }

    @Override
    public int getItemViewType(int position) {
        Discover discover = getItem(position);
        return discover.getImages().size();
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_1, parent, false);
                return new DiscoverViewHolder(itemView, viewType, onClickCallbackObj,me);
            }
            case 2: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_2, parent, false);
                return new DiscoverViewHolder(itemView, viewType,onClickCallbackObj,me);
            }
            case 3: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_3, parent, false);
                return new DiscoverViewHolder(itemView, viewType,onClickCallbackObj,me);
            }
            case 4: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_4, parent, false);
                return new DiscoverViewHolder(itemView, viewType,onClickCallbackObj,me);
            }
            default: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_0, parent, false);
                return new DiscoverViewHolder(itemView, viewType,onClickCallbackObj,me);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        // TODO
        ((DiscoverViewHolder)(holder)).setDiscover(getItem(position));
    }

    // TODO: 完成DiscoverViewHolder类
    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        public int imageCount;
        private View view;
        private OnClickCallback onClickCallbackObj;
        private ImageView likeImageView;
        private ImageView replyImageView;
        private User me;
        private RecyclerView likesByRecyclerView;
        private RecyclerView repliesRecyclerView;
        private LikeAdapter likeAdapter;
        private ReplyAdapter replyAdapter;
        private PlayerView videoView;
        private SimpleExoPlayer player;

        // TODO: 添加其他包含的其他控件
        public DiscoverViewHolder(@NonNull View itemView, int imageCount, OnClickCallback onClickCallbackObj, User me) {
            super(itemView);
            view = itemView;
            this.imageCount = imageCount;
            this.onClickCallbackObj  =onClickCallbackObj;
            this.me = me;
            likeImageView = view.findViewById(R.id.like);
            replyImageView = view.findViewById(R.id.reply);
            likesByRecyclerView = view.findViewById(R.id.likesBy);
            videoView = view.findViewById(R.id.video);

            likeAdapter = new LikeAdapter(new LikeAdapter.LikeDiff());
            replyAdapter = new ReplyAdapter(new ReplyAdapter.ReplyDiff());

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(view.getContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            likesByRecyclerView.setAdapter(likeAdapter);
            likesByRecyclerView.setLayoutManager(layoutManager);

            repliesRecyclerView = view.findViewById(R.id.replies);
            repliesRecyclerView.setAdapter(replyAdapter);
            repliesRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            player = new SimpleExoPlayer.Builder(videoView.getContext()).build();
            videoView.setPlayer(player);

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setDiscover(Discover discover){

            ImageView avatarImageView = (ImageView) view.findViewById(R.id.avatar_icon);

            MiscUtil.loadImage(avatarImageView,discover.getSender().getAvatar());

            TextView nickNameTextView = (TextView) view.findViewById(R.id.nickname_text);
            nickNameTextView.setText(discover.getSender().getUsername());

            TextView paragraphTextView = (TextView) view.findViewById(R.id.paragraph);
            paragraphTextView.setText(discover.getContent());

            TextView timeTextView = (TextView) view.findViewById(R.id.time);
            timeTextView.setText(MiscUtil.formatTimestamp(discover.getTimestamp()));

            switch (imageCount) {
                case 1:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    MiscUtil.loadImage(image1View,discover.getImages().get(0));
                    break;
                }
                case 2:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    MiscUtil.loadImage(image1View,discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    MiscUtil.loadImage(image2View,discover.getImages().get(1));
                    break;
                }
                case 3:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    MiscUtil.loadImage(image1View,discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    MiscUtil.loadImage(image2View,discover.getImages().get(1));
                    ImageView image3View = (ImageView) view.findViewById(R.id.image3);
                    MiscUtil.loadImage(image3View,discover.getImages().get(2));
                    break;
                }
                case 4:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    MiscUtil.loadImage(image1View,discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    MiscUtil.loadImage(image2View,discover.getImages().get(1));
                    ImageView image3View = (ImageView) view.findViewById(R.id.image3);
                    MiscUtil.loadImage(image3View,discover.getImages().get(2));
                    ImageView image4View = (ImageView) view.findViewById(R.id.image4);
                    MiscUtil.loadImage(image4View,discover.getImages().get(3));
                    break;
                }
            }

            User liked =  discover.getLikesBy().stream().filter((user -> {
                return user.getId().equals(me.getId());
            })).findFirst().orElse(null);

            if(liked!=null){
                likeImageView.setImageResource(R.drawable.ic_heart_fill);
            } else {
                likeImageView.setImageResource(R.drawable.ic_heart);
            }

            likeImageView.setOnClickListener((view)->{
                if(liked==null){
                    onClickCallbackObj.onLikeCallback(discover);
                } else {
                    onClickCallbackObj.onUnLikeCallback(discover);
                }
            });

            replyImageView.setOnClickListener((view)->{
                onClickCallbackObj.onReplyCallback(discover);
            });

            likeAdapter.submitList(discover.getLikesBy());
            replyAdapter.submitList(discover.getReplies());
            String video = discover.getVideo();
            if(video!=null){
                AppExecutors.getInstance().getMainThread().execute(()->{
                    player.setMediaItem(MediaItem.fromUri(video));
                    player.prepare();
                });
            } else {
                videoView.setVisibility(View.INVISIBLE);
                videoView.getLayoutParams().height = 0;
                videoView.requestLayout();
            }
        }

    }

    public static class DiscoverDiff extends DiffUtil.ItemCallback<Discover> {

        @Override
        public boolean areItemsTheSame(@NonNull Discover oldItem, @NonNull Discover newItem) {
            return oldItem == newItem;
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull Discover oldItem, @NonNull Discover newItem) {
            return false;
        }
    }


}
