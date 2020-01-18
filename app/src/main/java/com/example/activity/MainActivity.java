package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.example.R;
import com.example.activity.base.BaseActivity;
import com.example.activity.exit.ExitActivity;
import com.example.activity.launchmode.OpenTool;
import com.example.activity.launchmode.StandardActivity;
import com.example.activity.ui.view.ClockViewActivity;
import com.example.activity.ui.view.DragViewActivity;
import com.example.activity.ui.viewgroup.DrawLayoutActivity;
import com.example.activity.ui.viewgroup.NewsActivity;
import com.example.activity.ui.viewextend.RefreshListviewActivity;
import com.example.activity.view.ListViewActivity;
import com.example.activity.view.ViewPagerActivity;

import java.util.Date;

public class MainActivity extends BaseActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edittext);

        findViewById(R.id.btn_open_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExitActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTool.openStandard(MainActivity.this);
            }
        });

        findViewById(R.id.btn_open_vp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.btn_open_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshListviewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_lv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_dragview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DragViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_clockview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClockViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open_drawlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawLayoutActivity.class);
                startActivity(intent);
            }
        });

        // 这里构建重启案例需要将手机进行横竖屏切换，其他方式虽然能触发onSaveInstanceState但是无法
        // 重新执行onCreate方法，也就无法执行这里的步骤
        if (savedInstanceState != null) {
            final String savedContent = savedInstanceState.getString("content");
            // 创建监听，否则EditText还未创建完成，无法设置值
            mEditText.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mEditText.setText(savedContent);
                    mEditText.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("content", "试试能不能保存数据");
    }

    /**
     * 四个method方法来使用traceView工具，暂时还未用到，后面看到性能优化部分再来测试这部分的用法
     */
    public void method1(View view) {
        int result = jisuan();
        System.out.println(result);
    }

    public int jisuan() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }
        return 1;
    }

    public void method2(View view) {
        SystemClock.sleep(2000);
    }

    public void method3(View view) {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += i;
        }
        System.out.println("sum=" + sum);
    }

    public void method4(View view) {
        Toast.makeText(this, "" + new Date(), Toast.LENGTH_SHORT).show();
    }
}
