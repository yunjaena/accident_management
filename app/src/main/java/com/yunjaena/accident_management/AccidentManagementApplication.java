package com.yunjaena.accident_management;

import android.app.Application;
import android.content.Context;

public class AccidentManagementApplication  extends Application {
    private Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }
}
