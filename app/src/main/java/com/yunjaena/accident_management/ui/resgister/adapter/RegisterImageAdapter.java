package com.yunjaena.accident_management.ui.resgister.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yunjaena.accident_management.R;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;

import java.util.List;

public class RegisterImageAdapter extends RecyclerView.Adapter<RegisterImageAdapter.ViewHolder> {
    private List<Bitmap> bitmapList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public RegisterImageAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position >= bitmapList.size()){
            holder.addImageView.setVisibility(View.VISIBLE);
            holder.frameLayout.setBackgroundColor(context.getResources().getColor(R.color.gray5));
            holder.imageImageView.setVisibility(View.GONE);
            holder.frameLayout.setOnClickListener(v -> {
                if (recyclerViewClickListener != null)
                    recyclerViewClickListener.onClick(holder.frameLayout, position);
            });
            return;
        }

        Bitmap bitmap = bitmapList.get(position);
        holder.addImageView.setVisibility(View.GONE);
        holder.imageImageView.setVisibility(View.VISIBLE);
        holder.frameLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        Glide.with(context).load(bitmap).into(holder.imageImageView);
        holder.frameLayout.setOnClickListener(v -> {
            if (recyclerViewClickListener != null)
                recyclerViewClickListener.onClick(holder.frameLayout, position);
        });
    }

    @Override
    public int getItemCount() {
        return bitmapList.size() + 1;
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
