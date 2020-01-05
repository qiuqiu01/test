package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    public static final String BROAD_CAST_MESSAGE = TAG + ".BROAD_CAST_MESSAGE";

    private ExitappReceiver mReceiver = null;

    class ExitappReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROAD_CAST_MESSAGE)) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        super.onDestroy();
    }

    private void registerReceiver() {
        try {
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mReceiver = new ExitappReceiver();
        // 定义广播过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROAD_CAST_MESSAGE);
        // 注册广播
        registerReceiver(mReceiver, filter);
    }
}
