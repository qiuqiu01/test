package com.example.myapplication.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.interfaces.IService;
import com.example.myapplication.service.MessengerService;
import com.example.myapplication.service.TestService;

public class TestActivity extends BaseActivity {
    private TestConn mConn;
    private IService mIService;

    /** Messenger for communicating with the service. */
    private Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    /**
     *  Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service. We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_openclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, CloseActivity.class);
                startActivity(intent);
            }
        });

        bindService(new Intent(this, MessengerService.class), mConnection, BIND_AUTO_CREATE);

        mConn = new TestConn();

        // 绑定服务
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        // 解除服务绑定
        unbindService(mConn);

        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }

        super.onDestroy();
    }

    public void sayHello(View v) {
        if (!mBound)
            return;

        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class TestConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIService = (IService)service;
            mIService.calledMethodInService(20);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
