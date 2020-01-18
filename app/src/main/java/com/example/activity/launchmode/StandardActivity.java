package com.example.activity.launchmode;

import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.base.BaseActivity;

public class StandardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);

        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_open_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openStandard(StandardActivity.this);
            }
        });

        findViewById(R.id.btn_open_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTop(StandardActivity.this);
            }
        });

        findViewById(R.id.btn_open_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTask(StandardActivity.this);
            }
        });

        findViewById(R.id.btn_open_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleInstance(StandardActivity.this);
            }
        });
    }
}
