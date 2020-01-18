package com.example.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.wuyou.utils.YwLog;


/**
 * 定义一个可拖动的简单View（自定义View）
 * 触发onTouchEvent事件，进行相应操作即可
 * ***************************************************************
 * 另定义了一系列View事件生命周期相关的方法
 * ***************************************************************
 */
public class DragView extends View {

    private Scroller mSroller;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSroller = new Scroller(context);
    }

    private int lastX;
    private int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        YwLog.e("onTouchEvent");

        int x = (int)event.getX();
        int y = (int)event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = x - lastX;
                int offY = y - lastY;

                ////////////////////////////////////////////////////////////////////////
                // =============================方式1=================================//
                ////////////////////////////////////////////////////////////////////////

                // 在当前left、top、right、bottom基础上机上偏移量
                /*layout(getLeft() + offX,
                        getTop() + offY,
                        getRight() + offX,
                        getBottom() + offY);*/

                ////////////////////////////////////////////////////////////////////////
                // =============================方式2=================================//
                ////////////////////////////////////////////////////////////////////////

                /*offsetLeftAndRight(offX);
                offsetTopAndBottom(offY);*/

                ////////////////////////////////////////////////////////////////////////
                // =============================方式3=================================//
                ////////////////////////////////////////////////////////////////////////

                /*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
                params.leftMargin = getLeft() + offX;
                params.topMargin = getTop() + offY;
                setLayoutParams(params);*/

                ////////////////////////////////////////////////////////////////////////
                // =============================方式4=================================//
                ////////////////////////////////////////////////////////////////////////

                ((View)getParent()).scrollBy(-offX, -offY);

                ////////////////////////////////////////////////////////////////////////
                //==============================方式5=================================//
                //=========该方法并未实时随手移动,停止move时才移动到目标位置==========//
                ////////////////////////////////////////////////////////////////////////
               /* View parent = ((View)getParent());
                mSroller.startScroll(parent.getScrollX(), parent.getScrollY(), -offX, -offY);
                invalidate();*/

                break;
            case MotionEvent.ACTION_UP:
                /*// 方式5，设置松手后回到原点
                View parent1 = ((View)getParent());
                mSroller.startScroll(
                        parent1.getScrollX(),
                        parent1.getScrollY(),
                        -parent1.getScrollX(),
                        -parent1.getScrollY());
                invalidate();*/
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        YwLog.e("computeScroll");

        if (mSroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mSroller.getCurrX(), mSroller.getCurrY());
            // 刷新绘制不断调用computeScroll方法
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        YwLog.e("onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        YwLog.e("onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        YwLog.e("onSizeChanged");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        YwLog.e("onFinishInflate");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        YwLog.e("onLayout");
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        YwLog.e("onFocusChanged");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        YwLog.e("onWindowFocusChanged");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        YwLog.e("onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        YwLog.e("onDetachedFromWindow");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        YwLog.e("onWindowVisibilityChanged");
    }
}
