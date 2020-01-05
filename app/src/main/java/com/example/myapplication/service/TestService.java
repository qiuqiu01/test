package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.interfaces.IService;

public class TestService extends Service {
    private class TestBinder extends Binder implements IService {

        @Override
        public void calledMethodInService(int number) {
            if (number >= 0) {
                methodInService();
            } else {
                Toast.makeText(TestService.this, "number is not enough!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TestBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void methodInService() {
        Toast.makeText(this, "methodInService is called!!", Toast.LENGTH_SHORT).show();
    }
}
