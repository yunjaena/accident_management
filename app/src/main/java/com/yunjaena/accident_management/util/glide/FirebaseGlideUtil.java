package com.yunjaena.accident_management.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseGlideUtil {
    public static void setImage(Context context, String fileName, ImageView imageView) {
        StorageReference ref = FirebaseStorage.getInstance().getReference(fileName);
        GlideApp.with(context)
                .load(ref)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
