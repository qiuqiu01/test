package com.example.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.wuyou.utils.YwLog;

/**
 * 自定义一个简单的时钟控件
 */
public class ClockView extends View {
    private Paint circlePaint;      // 外圈圆的Paint
    private Paint scaleDegreePaint; // 刻度Paint
    private Paint hourPaint;        // 时针Paint
    private Paint minitePaint;      // 分针Paint

    int mWidth;             // 整个控件宽
    int mHeight;            // 整个控件高度

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(5);

        scaleDegreePaint = new Paint();
        scaleDegreePaint.setStrokeWidth(3);

        hourPaint = new Paint();
        hourPaint.setStrokeWidth(20);

        minitePaint = new Paint();
        minitePaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        YwLog.e("onDraw");

        // 绘制外圈圆
        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, circlePaint);

        // 画刻度
        for (int i = 0; i < 24; i++) {
            if (i%6==0) {
                scaleDegreePaint.setStrokeWidth(5);
                scaleDegreePaint.setTextSize(30);
                canvas.drawLine(mWidth/2, mHeight/2 - mWidth/2,
                        mWidth/2, mHeight/2 - mWidth/2 + 60, scaleDegreePaint);

                String degree = String.valueOf(i);
                canvas.drawText(degree, mWidth/2-scaleDegreePaint.measureText(degree)/2,
                        mHeight/2 - mWidth/2 + 90, scaleDegreePaint);
            } else {
                scaleDegreePaint.setStrokeWidth(3);
                scaleDegreePaint.setTextSize(15);
                canvas.drawLine(mWidth/2, mHeight/2 - mWidth/2,
                        mWidth/2, mHeight/2 - mWidth/2 + 30, scaleDegreePaint);

                String degree = String.valueOf(i);
                canvas.drawText(degree, mWidth/2-scaleDegreePaint.measureText(degree)/2,
                        mHeight/2 - mWidth/2 + 60, scaleDegreePaint);
            }

            canvas.rotate(360/24, mWidth/2, mHeight/2);
        }

        // 画时针和分针
        canvas.save();
        canvas.translate(mWidth/2, mHeight/2);
        canvas.drawLine(0, 0, 100, 100, hourPaint);
        canvas.drawLine(0, 0, 100, 200, minitePaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        YwLog.e("onMeasure");

        // 得到控件宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }
}
