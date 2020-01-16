package com.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.R;
import com.example.ui.SlidingMenu;

public class NewsActivity extends BaseActivity {
    private SlidingMenu mSlidingMenu;
    private ImageButton ibShowSliding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initViews();
    }

    private void initViews() {
        mSlidingMenu = findViewById(R.id.slidemenu);
        ibShowSliding = findViewById(R.id.ib_back);

        ibShowSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前是哪一个屏幕
                boolean isShowMenu  = mSlidingMenu.isShowMenu();
                
                if(isShowMenu) {
                    // 是菜单界面, 切换到主界面
                    mSlidingMenu.hideMenu();
                } else {
                    // 是主界面, 应该把菜单显示出来
                    mSlidingMenu.showMenu();
                }
            }
        });
    }
}
