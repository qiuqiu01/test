package com.example.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingMenu extends ViewGroup {
    private int downX; // 按下时x轴的偏移量
    private final int SCREEN_MENU = 0; // 菜单界面
    private final int SCREEN_MAIN = 1; // 主界面

    private int currentScreen = SCREEN_MAIN; // 当前屏幕显示的界面, 默认为: 主界面

    private Scroller scroller;
    private int touchSlop;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());

        touchSlop = ViewConfiguration.get(getContext()).getTouchSlop();
    }

    /**
     * widthMeasureSpec 填充屏幕
     * heightMeasureSpec 填充屏幕
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 测量菜单的宽和高. 宽: 240dip, 高: 填充屏幕
        View menuView = getChildAt(0);
//        menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);
        menuView.measure(menuView.getMeasuredWidth(), heightMeasureSpec);

        // 测量主界面的宽和高. 宽: 填充屏幕, 高: 填充屏幕
        View contentView = getChildAt(1);
        contentView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * int l 左边=0
     * int t 上边=0
     * int r 右边=屏幕的宽度
     * int b 下边=屏幕的高度
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 把菜单的位置放置在屏幕的左侧
        View menuView = getChildAt(0);
//        menuView.layout(-menuView.getLayoutParams().width, t, 0, b);
        menuView.layout(-menuView.getMeasuredWidth(), t, 0, b);

        // 主界面的位置放置在屏幕左上角
        View contentView = getChildAt(1);
        contentView.layout(l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 只有在横着滑动时才可以拦截.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();

                int diff = Math.abs(downX - moveX);
                if(diff > touchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrollX;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();

                int deltaX = downX - moveX;

                // 判断给定当前的增量移动后, 是否能够超出边界.
                scrollX = getScrollX() + deltaX;
                if(scrollX < -getChildAt(0).getMeasuredWidth()) {
                    // 当前超出了左边界, 应该设置为在菜单的左边界位置上.
                    scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
                } else if(scrollX > 0) {
                    // 当前超出了右边界, 应该设置为0
                    scrollTo(0, 0);
                } else {
                    scrollBy(deltaX, 0);
                }

                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                // 获取菜单宽度的一半
                int center = -getChildAt(0).getMeasuredWidth() / 2;
                scrollX = getScrollX(); // 当前屏幕左上角的值

                if(scrollX > center) {
                    System.out.println("当前切换到主界面");
                    currentScreen = SCREEN_MAIN;
                } else {
                    System.out.println("当前切换到菜单界面");
                    currentScreen = SCREEN_MENU;
                }

                switchScreen();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 根据currentScreen变量来切换屏幕显示
     */
    private void switchScreen() {
        int startX = getScrollX(); // 开始的位置
        int dx = 0; // 增量值 = 目的地位置 - 开始的位置;

        if(currentScreen == SCREEN_MAIN) {
//			scrollTo(0, 0);
            dx = 0 - startX;
        } else if(currentScreen == SCREEN_MENU) {
//			scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
            dx = -getChildAt(0).getMeasuredWidth() - startX;
        }

        int duration = Math.abs(dx) * 10;
        if(duration > 1000) {
            duration = 1000;
        }
        scroller.startScroll(startX, 0, dx, 0, duration);

        // 刷新当前控件, 会引起onDraw方法的调用.
        invalidate(); // -> drawChild -> view.draw -> view.computeScroll
    }

    @Override
    public void computeScroll() {
        // 当scroller数据模拟完毕时, 不应该继续进行递归
        // 反之, 如果正在模拟数据才进行递归的操作

        if(scroller.computeScrollOffset()) {// 当前还是正在模拟数据中
            // 把当前scroller正在模拟的数据取出来, 使用scrollTo方法切换屏幕
            int currX = scroller.getCurrX();
            scrollTo(currX, 0);
            invalidate(); // 在触发当前方法, 相当于递归.
        }
    }

    /**
     * 是否显示菜单
     * @return true 显示菜单, false 不显示
     */
    public boolean isShowMenu() {
        return currentScreen == SCREEN_MENU;
    }

    /**
     * 隐藏菜单
     */
    public void hideMenu() {
        currentScreen = SCREEN_MAIN;
        switchScreen();
    }

    /**
     * 显示菜单
     */
    public void showMenu() {
        currentScreen = SCREEN_MENU;
        switchScreen();
    }
}
