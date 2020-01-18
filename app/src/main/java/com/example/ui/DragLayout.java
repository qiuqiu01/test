package com.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * 07_自定义控件预习资料\QQ5.0-视频-day01----示例1
 */
public class DragLayout extends FrameLayout {
    private ViewGroup mLeftContent;
    private ViewGroup mMainContent;

    private ViewDragHelper mHelper;

    private int mHeight;
    private int mWidth;
    private int mRange;

    public DragLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * 1.创建ViewDragHelper辅助类
         * forParent 父类的容器
         * sensitivity 敏感度, 越大越敏感.1.0f是默认值
         * Callback 事件回调
         */
        mHelper = ViewDragHelper.create(this, 1.0f, mCallback);
    }

    // 4.定义ViewDragHelper内部Callback回调，处理最后的事件逻辑
    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 返回值决定了child是否可以被拖拽
         * @param child 被用户拖拽的子View
         * @param pointerId 多点触控的手指id
         * @return
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
//            return child == mMainContent;
            // 因为后面要用到左右面板，返回true让两个子View都可以移动
            // 拖动左面板时，要得到偏移量，然后根据偏移量来移动主面板位置。
            // 利用layout方法时将左面板固定不动，只移动右面版（同样是右面板的layout方法固定位置）。
            return true;
        }

        /*******************************************************************************************
         * 获取视图水平方向的拖拽范围
         *  * 父类中搜索，它在computeSettleDuration中被调用，拖拽到x=300的位置，它计算这个x的值;
         *  * 然后根据x的值来决定动画（左面板被拖出松手后它返回时有个动画）执行的时长是多少;
         *  * 一般x越大，执行时间越长;
         * *****************************************************************************************
         * @param child
         * @return 返回拖拽的范围. 返回一个>0 的值, 决定了动画的执行时长, 水平方向是否可以被滑开
         * *****************************************************************************************
         * 注意：这里只是得到一个拖拽范围，和动画及水平方向能否花开有关，而不能决定拖拽范围
         * 决定拖拽范围的是clampViewPositionHorizontal()方法
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mRange;
        }

        /********************************************************************************************
         * 当控件位置改变时的回调;
         * *可以做:伴随动画, 状态的更新, 事件的回调等。
         * ******************************************************************************************
         * @param changedView 被移动的子View
         * @param left  最新的水平方向的位置
         * @param top
         * @param dx    移动的水平偏移量，向右拖动为正，拖动越快值越大
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
             System.out.println("onViewPositionChanged: " + " left:" + left + " dx: " + dx);
            if(changedView == mLeftContent){ // 如果移动的是左面板，则左面板不动，主面板移动对应距离
                // 1. 将左边面板放回原来的位置
                mLeftContent.layout(0, 0, 0 + mWidth, 0 + mHeight);
                // 2. 把左面板发生的变化量dx转递给主面板
                int newLeft = mMainContent.getLeft() + dx;
                // 修正左边值（主要是防止拖出范围）.
                newLeft = fixLeft(newLeft);
                // 利用左边的偏移量，设置主面板拖动的位置
                mMainContent.layout(newLeft, 0, newLeft + mWidth, 0 + mHeight);
            }

            // 为了兼容低版本, 手动重绘界面所有内容.
            invalidate();
        }

        /**
         * 当视图拖拽状态改变的时候
         * @param state
         */
//        @Override
//        public void onViewDragStateChanged(int state) {
//            super.onViewDragStateChanged(state);
//        }

        /*******************************************************************************************
         * 当视图被释放时调用;
         * 决定了松手之后要做的事情, 结束的动画，回调事件等;
         *******************************************************************************************
         * @param releasedChild 被释放的子View
         * @param xvel 水平方向的速度，向右滑动时→为+，向左滑动←为-，滑动越快值越大。
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            System.out.println("onViewReleased: xvel: " + xvel);

            if(xvel == 0 && mMainContent.getLeft() > mRange * 0.5f){
                open();
            } else if (xvel > 0) {
                open();
            } else {
                close();
            }
        }

        /*******************************************************************************************
         * 修正子View水平方向的位置，还没有发生真正的移动
         * 返回值决定了View将会移动到的位置
         *******************************************************************************************
         * @param child 被拖拽的子View
         * @param left  建议移动到的位置
         * @param dx 跟旧的位置的差值
         * @return  决定移动的位置
         * ******************************************************************************************
         * （return left会跟着手指位置移动，如果返回100，则拖动后将一直停在100的位置）
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int oldLeft = mMainContent.getLeft();
            // 修正位置,防止越界
            if(child == mMainContent){
                left = fixLeft(left);
            }
            return left;
        }

        /**
         * 修正子View垂直方向的位置，还没有发生真正的移动
         * @param child 被拖拽的子View
         * @param top  建议移动到的位置
         * @param dy 跟旧的位置的差值
         * @return  返回值决定移动的位置
         * return top 会跟着手指位置移动，如果同时重写了clampViewPositionHorizontal可以任意移动
         * 这里不需要上下拖拽，返回值为0或者不重写该方法
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }
    };

    /**
     * 修正位置，防止拖出设定范围
     * @param left
     * @return
     */
    private int fixLeft(int left) {
        if(left < 0){
            return 0;
        }else if (left > mRange) {
            return mRange;
        }
        return left;
    }

    /**
     * 打开面板
     */
    public void open() {
        boolean isSmooth = true;
        open(isSmooth);
    }

    /**
     * 关闭面板
     */
    public void close() {
        boolean isSmooth = true;
        close(isSmooth);
    }

    /**
     * 是否平滑打开面板
     */
    public void open(boolean isSmooth){
        int finalLeft = mRange;
        if(isSmooth){ // 走平滑动画
            // 1. 触发一个平滑动画.
            // 参数依次是滑动的子View，最终滑动到的水平位置、垂直位置
            // 如果当前位置不是指定的最终位置. 返回true，表示需要移动
            // 如果当前位置是指定的最终位置. 返回false，表示不需要移动
            if(mHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)){
                // 需要重绘界面, 一定要传子View 所在的容器，它能引起整体的刷新
                ViewCompat.postInvalidateOnAnimation(this);
                // postInvalidate();   // 也可以使用这句刷新，推荐使用上面
            }
        }else {
            // 直接利用右面版layout方法固定位置
            mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
        }
    }

    /**
     * 是否平滑关闭面板
     */
    public void close(boolean isSmooth){
        int finalLeft = 0;
        if(isSmooth){ // 走平滑动画
            // 1. 触发一个平滑动画.
            // 参数依次是滑动的子View，最终滑动到的水平位置、垂直位置
            if(mHelper.smoothSlideViewTo(mMainContent, finalLeft, 0)){
                // 如果当前位置不是指定的最终位置. 返回true
                // 需要重绘界面, 一定要传 子View 所在的容器
                ViewCompat.postInvalidateOnAnimation(this);
                // postInvalidate();   // 也可以使用这句刷新，推荐使用上面
            }
        }else {
            // 直接利用右面版layout方法固定位置
            mMainContent.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
        }
    }

    //2. 维持动画的继续, 高频率调用(动画每一桢都会执行).
    @Override
    public void computeScroll() {
        super.computeScroll();

        if(mHelper.continueSettling(true)){
            // 如果当前位置还没有移动到最终位置. 返回true，需要继续重绘界面
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 2.转交触摸事件，由ViewDragHelper判断触摸事件是否拦截
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 3.返回值为true，并由ViewDragHelper来处理事件
        try {
            mHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * xml被完全填充完毕的回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 健壮性判断，至少有两个子View
        if (getChildCount() < 2) {
            throw new IllegalStateException("DragLayout must have 2 children views!!");
        }
        // 子View必须是ViewGroup的子类
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("子View必须是ViewGroup的子类");
        }

        // Github
        mLeftContent = (ViewGroup) getChildAt(0);
        mMainContent = (ViewGroup) getChildAt(1);
    }

    /**
     * 当View的大小发生变化时的回调
     * *这里用来获取控件的宽度，然后给定一个getViewHorizontalDragRange方法的返回值mRange。
     * *也可以在onMeasure里获取mRange，但任何手指事件都会调用onMeasure，频率太高，开销大。
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 当控件尺寸变化的时候调用
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        // 计算拖拽的范围
        mRange = (int) (mWidth * 0.6f);
    }
}
