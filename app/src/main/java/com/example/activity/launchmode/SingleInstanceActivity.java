package com.example.activity.launchmode;

import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.base.BaseActivity;

public class SingleInstanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);

        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_open_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openStandard(SingleInstanceActivity.this);
            }
        });

        findViewById(R.id.btn_open_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTop(SingleInstanceActivity.this);
            }
        });

        findViewById(R.id.btn_open_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleTask(SingleInstanceActivity.this);
            }
        });

        findViewById(R.id.btn_open_instance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTool.openSingleInstance(SingleInstanceActivity.this);
            }
        });
    }
}
