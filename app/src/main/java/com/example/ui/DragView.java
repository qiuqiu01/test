package com.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;


/**
 * 定义一个可拖动的简单View（自定义View）
 * 触发onTouchEvent事件，进行相应操作即可
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

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mSroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mSroller.getCurrX(), mSroller.getCurrY());
            // 刷新绘制不断调用computeScroll方法
            invalidate();
        }
    }

    private int lastX;
    private int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                // 并未移动View（AS上测试）
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

            case MotionEvent.ACTION_UP:
                ////////////////////////////////////////////////////////////////////////
                // =============================方式5=================================//
                // =======================该方法并未实现移动==========================//
                ////////////////////////////////////////////////////////////////////////

                View parent = ((View)getParent());
                mSroller.startScroll(
                        parent.getScrollX(),
                        parent.getScrollY(),
                        -parent.getScrollX(),
                        -parent.getScrollY());
                invalidate();
            default:
                break;
        }

        return true;
    }
}
