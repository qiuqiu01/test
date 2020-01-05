package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Date;

public class MainActivity extends BaseActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edittext);
        findViewById(R.id.btn_opentest1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
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
            // Toast来查看是否调用了方法
//            Toast.makeText(this, savedContent, Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, "onSaveInstanceState被调用了", Toast.LENGTH_SHORT).show();
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
