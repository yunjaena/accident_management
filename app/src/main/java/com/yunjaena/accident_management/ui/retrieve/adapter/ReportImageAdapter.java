package com.yunjaena.accident_management.ui.retrieve.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.util.glide.FirebaseGlideUtil;
import com.yunjaena.core.recyclerview.RecyclerViewClickListener;

import java.util.List;

public class ReportImageAdapter extends RecyclerView.Adapter<ReportImageAdapter.ViewHolder> {
    private List<String> bitmapList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public ReportImageAdapter(Context context, List<String> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String bitmapName = bitmapList.get(position);
        FirebaseGlideUtil.setImage(context, bitmapName, holder.imageImageView);
        holder.imageImageView.setOnClickListener(v -> {
            if (recyclerViewClickListener != null)
                recyclerViewClickListener.onClick(holder.imageImageView, position);
        });
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageImageView = itemView.findViewById(R.id.report_image_view);
        }
    }
}
