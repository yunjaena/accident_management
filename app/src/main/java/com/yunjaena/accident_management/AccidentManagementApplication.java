package com.yunjaena.accident_management;

import android.app.Application;
import android.content.Context;

import com.yunjaena.core.toast.ToastUtil;

public class AccidentManagementApplication  extends Application {
    private Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        init();
    }

    private void init(){
        ToastUtil.getInstance().init(applicationContext);
    }
}
