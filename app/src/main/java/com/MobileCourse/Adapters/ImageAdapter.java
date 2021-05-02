package com.MobileCourse.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;

public class ImageAdapter extends ListAdapter<String, ImageAdapter.ImageViewHolder> {

    public ImageAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        @SuppressLint("UnsafeExperimentalUsageError")
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        public void setImage(String url){
            MiscUtil.loadImage(imageView,url);
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // TODO
        String url = getItem(position);
        holder.setImage(url);
    }

    public static class ImageDiff extends DiffUtil.ItemCallback<String> {

        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }
}
