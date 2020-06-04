package com.yunjaena.accident_management.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yunjaena.accident_management.R;
import com.yunjaena.accident_management.ui.retrieve.RetrieveActivity;
import com.yunjaena.accident_management.ui.resgister.RegisterActivity;
import com.yunjaena.core.activity.ActivityBase;

public class MainActivity extends ActivityBase implements View.OnClickListener {
    private Button registerButton;
    private Button inquireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        registerButton = findViewById(R.id.main_register_button);
        inquireButton = findViewById(R.id.main_retrieve_button);
        registerButton.setOnClickListener(this);
        inquireButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_register_button:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.main_retrieve_button:
                startActivity(new Intent(this, RetrieveActivity.class));
                break;
        }
    }
}
