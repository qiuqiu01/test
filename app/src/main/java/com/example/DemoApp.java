package com.example;

import android.app.Application;

import com.wuyou.utils.YwUtils;

public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化工具lib
        YwUtils.init(this);
    }
}
