package com.example.activity.exit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.base.BaseActivity;

public class ExitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);

        findViewById(R.id.btn_close_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishApp();
            }
        });
    }

    private void finishApp() {
        Intent intent = new Intent();
        intent.setAction(BaseActivity.EXIT_APP);
        sendBroadcast(intent);
    }
}
