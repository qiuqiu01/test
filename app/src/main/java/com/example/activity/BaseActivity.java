package com.example.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();  // 全类名
    public static final String EXIT_APP = TAG + ".EXIT_APP";

    private ExitappReceiver mReceiver = null;

    class ExitappReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(EXIT_APP)) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 完全全屏显示（隐藏状态栏）
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        // 隐藏标题栏
        if (getSupportActionBar()!= null) {
            getSupportActionBar().hide();
        }

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
            // 如果mReceiver不为空，先将原来的广播关掉
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mReceiver = new ExitappReceiver();
        // 定义广播过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_APP);
        // 注册广播
        registerReceiver(mReceiver, filter);
    }
}
