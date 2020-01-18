package com.example.activity.launchmode;

import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.base.BaseActivity;

public class SingleTopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);

        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_open_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openStandard(SingleTopActivity.this);
            }
        });

        findViewById(R.id.btn_open_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTop(SingleTopActivity.this);
            }
        });

        findViewById(R.id.btn_open_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTask(SingleTopActivity.this);
            }
        });

        findViewById(R.id.btn_open_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleInstance(SingleTopActivity.this);
            }
        });
    }
}
