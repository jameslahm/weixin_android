package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Discover;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.MobileCourse.R;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.LinkedList;
public class DiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinkedList<Discover> data;

    public DiscoverAdapter(LinkedList<Discover> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        Discover discover = data.get(position);
        return discover.getImageCount();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_1, parent, false);
                return new DiscoverViewHolder(itemView, viewType);
            }
            case 2: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_2, parent, false);
                return new DiscoverViewHolder(itemView, viewType);
            }
            case 3: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_3, parent, false);
                return new DiscoverViewHolder(itemView, viewType);
            }
            case 4: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_4, parent, false);
                return new DiscoverViewHolder(itemView, viewType);
            }
            default: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_0, parent, false);
                return new DiscoverViewHolder(itemView, viewType);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // TODO
        ((DiscoverViewHolder)(holder)).setDiscover(data.get(position));
    }

    @Override
    public int getItemCount() {
        // TODO
        return data.size();
    }

    // TODO: 完成DiscoverViewHolder类
    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        public int imageCount;
        private View view;

        // TODO: 添加其他包含的其他控件
        public DiscoverViewHolder(@NonNull View itemView, int imageCount) {
            super(itemView);
            view = itemView;
            this.imageCount = imageCount;
        }

        public void setDiscover(Discover discover){
            ImageView avatarImageView = (ImageView) view.findViewById(R.id.avatar_icon);
            avatarImageView.setImageResource(discover.getAvatarIcon());

            TextView nickNameTextView = (TextView) view.findViewById(R.id.nickname_text);
            nickNameTextView.setText(discover.getNickname());

            TextView paragraphTextView = (TextView) view.findViewById(R.id.paragraph);
            paragraphTextView.setText(discover.getText());

            TextView timeTextView = (TextView) view.findViewById(R.id.time);
            timeTextView.setText(discover.getPublishedTime());

            switch (imageCount) {
                case 1:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    image1View.setImageResource(discover.getImages().get(0));
                    return;
                }
                case 2:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    image1View.setImageResource(discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    image1View.setImageResource(discover.getImages().get(1));
                    return;
                }
                case 3:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    image1View.setImageResource(discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    image1View.setImageResource(discover.getImages().get(1));
                    ImageView image3View = (ImageView) view.findViewById(R.id.image3);
                    image1View.setImageResource(discover.getImages().get(2));
                    return;
                }
                case 4:{
                    ImageView image1View = (ImageView) view.findViewById(R.id.image1);
                    image1View.setImageResource(discover.getImages().get(0));
                    ImageView image2View = (ImageView) view.findViewById(R.id.image2);
                    image1View.setImageResource(discover.getImages().get(1));
                    ImageView image3View = (ImageView) view.findViewById(R.id.image3);
                    image1View.setImageResource(discover.getImages().get(2));
                    ImageView image4View = (ImageView) view.findViewById(R.id.image4);
                    image1View.setImageResource(discover.getImages().get(3));
                }

            }
        }

    }


}
