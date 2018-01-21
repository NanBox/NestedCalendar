package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by nanquan on 2018/1/19.
 */

public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float mDownY;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = e.getRawX();
//                mDownY = e.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //竖向滑动时拦截事件
//                float deltaX = e.getRawX() - mDownX;
//                float deltaY = e.getRawY() - mDownY;
//                if (deltaY != 0.0 &&
//                        Math.abs(deltaX / deltaY) < 1) {
//                    return true;
//                }
//                break;
//        }
//        return super.onInterceptTouchEvent(e);
//    }

    private float deltaY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //竖向滑动时拦截事件
                deltaY = e.getRawY() - mDownY;
                mDownY = e.getRawY();
                break;
        }
        Log.d("top", getTop() + getTranslationY() + "");

        if (deltaY > 0 && canScrollVertically(-1)) {
            return super.onTouchEvent(e);
        }


        if (getTop() + getTranslationY() + deltaY > 300 && getTranslationY() + deltaY < 0 ||
                getTop() + getTranslationY() == 300 ||
                getTranslationY() == 0) {
            if (getTop() + getTranslationY() + deltaY < 300) {
                setTranslationY(300 - getTop());
            } else if (getTranslationY() > 0) {
                setTranslationY(0);
            }

            setTranslationY(getTranslationY() + deltaY);
            return true;
        } else {
            return super.onTouchEvent(e);
        }

    }

}
