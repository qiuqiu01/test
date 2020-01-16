package com.example.activity.exit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.R;
import com.example.activity.BaseActivity;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_openclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ExitActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
