package com.example.activity.launchmode;

import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.base.BaseActivity;

public class SingleTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_open_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openStandard(SingleTaskActivity.this);
            }
        });

        findViewById(R.id.btn_open_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTop(SingleTaskActivity.this);
            }
        });

        findViewById(R.id.btn_open_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTask(SingleTaskActivity.this);
            }
        });

        findViewById(R.id.btn_open_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleInstance(SingleTaskActivity.this);
            }
        });
    }
}
