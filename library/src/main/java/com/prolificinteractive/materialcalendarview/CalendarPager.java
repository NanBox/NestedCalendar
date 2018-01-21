package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.v4.view.BetterViewPager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
class CalendarPager extends BetterViewPager {


//    private ViewDragHelper mDragHelper;
//    private int weekOfMonth = 1;

//    public void setWeekOfMonth(int weekOfMonth) {
//        this.weekOfMonth = weekOfMonth;
//    }

//    public boolean isOnTop() {
//        final int topHeight = (getMeasuredHeight() / 7) * weekOfMonth;
//        return getTop() == topHeight;
//    }

//    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
//        @Override
//        public boolean tryCaptureView(View child, int pointerId) {
//            return true;
//        }
//
//        @Override
//        public int clampViewPositionVertical(View child, int top, int dy) {
//
//            final int topHeight = (getMeasuredHeight() / 7) * weekOfMonth;
//
//            if (top > 0) {
//                return 0;
//            } else if (top < -topHeight) {
//                return -topHeight;
//            }
//
//
//            return top;
//        }
//
//        @Override
//        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//        }
//
//        @Override
//        public int getViewHorizontalDragRange(View child) {
//            return getMeasuredWidth();
//        }
//
//        @Override
//        public int getViewVerticalDragRange(View child) {
//            return getMeasuredHeight();
//        }
//    };


    private boolean pagingEnabled = true;

    public CalendarPager(Context context) {
        super(context);
//        mDragHelper = ViewDragHelper.create(this, mCallback);
    }

    /**
     * enable disable viewpager scroll
     *
     * @param pagingEnabled false to disable paging, true for paging (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    /**
     * @return is this viewpager allowed to page
     */
    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return pagingEnabled && mDragHelper.shouldInterceptTouchEvent(ev);
//    }

    private float mDownY;
    private boolean isDrag;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                float deltaX = ev.getRawY() - mDownY;
                if (Math.abs(deltaX) > 50) {
                    isDrag = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false);
                isDrag = false;
                break;
        }

//        mDragHelper.processTouchEvent(ev);
        return pagingEnabled;
    }

//    public void setTopBottomOffset(int offset) {
//
//
//        final int topHeight = (getMeasuredHeight() / 7) * weekOfMonth;
//
//        int newTop = getTop() + offset;
//
//        if (newTop <= 0 && newTop >= -topHeight) {
//            ViewCompat.offsetTopAndBottom(this, offset);
//        }
//    }

    @Override
    public boolean canScrollVertically(int direction) {
        /**
         * disables scrolling vertically when paging disabled, fixes scrolling
         * for nested {@link android.support.v4.view.ViewPager}
         */
        return pagingEnabled && super.canScrollVertically(direction);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        /**
         * disables scrolling horizontally when paging disabled, fixes scrolling
         * for nested {@link android.support.v4.view.ViewPager}
         */
        return pagingEnabled && super.canScrollHorizontally(direction);
    }

//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if (mDragHelper.continueSettling(true)) {
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//    }
}
