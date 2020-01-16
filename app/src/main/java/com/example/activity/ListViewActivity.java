package com.example.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.R;
import com.example.adapter.LvDemoAdatper;


public class ListViewActivity extends BaseActivity {
    private ListView mLvDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        initViews();
    }

    private void initViews() {
        mLvDemo = findViewById(R.id.lv_demo);
        mLvDemo.setAdapter(new LvDemoAdatper(this));
    }
}
