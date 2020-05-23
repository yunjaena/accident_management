package com.yunjaena.accident_management.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.ui.main.MainActivity;
import com.yunjaena.core.activity.ActivityBase;

public class SplashActivity extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_splash);
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2000);
    }
}
