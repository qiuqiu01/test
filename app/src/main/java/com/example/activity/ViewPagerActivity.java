package com.example.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {
    private ViewPager vpShow;
    private LinearLayout llPointGroup;
    private TextView tvDescription;

    private List<ImageView> ivList = new ArrayList<>();

    private VpAdapter adapter =  new VpAdapter();

    private String[] descriptions = {
            "001庵后发货代发货",
            "002电话费ID衣服",
            "003水电费后方",
            "004的防腐剂福建省",
            "005废话很多"
    };
    private int curPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initViews();
        initData();
        adapter.notifyDataSetChanged();
    }

    private void initViews() {
        vpShow = findViewById(R.id.vp_show);
        vpShow.setAdapter(adapter);
        vpShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 页面滑动时调用
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 页面被选中时调用
             */
            @Override
            public void onPageSelected(int position) {
                llPointGroup.getChildAt(position).setEnabled(true);
                llPointGroup.getChildAt(curPos).setEnabled(false);
                tvDescription.setText(descriptions[position]);

                curPos = position;
            }

            /**
             * 页面状态改变时调用
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llPointGroup = findViewById(R.id.ll_point_group);
        tvDescription = findViewById(R.id.tv_image_description);

    }

    private void initData() {
        int[] imageResIDs = {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e};

        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imageResIDs[i]);
            ivList.add(iv);

            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    40, 40);
            if (i != 0) {
                params.leftMargin = 20;
            }
            view.setLayoutParams(params);
            view.setEnabled(false);
            llPointGroup.addView(view);
        }

        // 设置圆点初始状态
        llPointGroup.getChildAt(0).setEnabled(true);
        tvDescription.setText(descriptions[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.notifyDataSetChanged();
    }

    private class VpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ivList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView iv = ivList.get(position);
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(ivList.get(position));
        }
    }
}
