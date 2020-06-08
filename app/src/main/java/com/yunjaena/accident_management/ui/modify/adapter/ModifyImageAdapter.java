package com.yunjaena.accident_management.ui.modify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.data.network.entity.Image;
import com.yunjaena.accident_management.util.glide.FirebaseGlideUtil;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;

import java.util.List;

public class ModifyImageAdapter extends RecyclerView.Adapter<ModifyImageAdapter.ViewHolder> {
    private List<Image> imageList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public ModifyImageAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= imageList.size()) {
            holder.addImageView.setVisibility(View.VISIBLE);
            holder.frameLayout.setBackgroundColor(context.getResources().getColor(R.color.gray5));
            holder.imageImageView.setVisibility(View.GONE);
            holder.frameLayout.setOnClickListener(v -> {
                if (recyclerViewClickListener != null)
                    recyclerViewClickListener.onClick(holder.frameLayout, position);
            });
            return;
        }

        Image image = imageList.get(position);
        holder.addImageView.setVisibility(View.GONE);
        holder.imageImageView.setVisibility(View.VISIBLE);
        holder.frameLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        if (image.getBitmap() != null)
            Glide.with(context).load(image.getBitmap()).into(holder.imageImageView);
        else
            FirebaseGlideUtil.setImage(context, image.getImageString(), holder.imageImageView);

        holder.frameLayout.setOnClickListener(v -> {
            if (recyclerViewClickListener != null)
                recyclerViewClickListener.onClick(holder.frameLayout, position);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size() + 1;
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageImageView;
        public ImageView addImageView;
        private FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageImageView = itemView.findViewById(R.id.register_image_view);
            addImageView = itemView.findViewById(R.id.register_image_add);
            frameLayout = itemView.findViewById(R.id.register_item_frame_layout);
        }
    }
}
