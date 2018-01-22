package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanquan.calendar.helper.ViewOffsetBehavior;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

/**
 * Created by nanquan on 2018/1/19.
 */

public class CalendarBehavior extends ViewOffsetBehavior<MaterialCalendarView> {

    private int weekOfMonth = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);

    public CalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarScrollBehavior) {
            final CalendarScrollBehavior listBehavior = (CalendarScrollBehavior) behavior;
            return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !listBehavior.getIsOnTop();
        }
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

//    @Override
//    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
//        return true;
//    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        // 移动头部
        int headerMinOffset = -(child.getMeasuredHeight() / 7) * weekOfMonth;
        int headerOffset = MathUtils.clamp(getTopAndBottomOffset() - dy, headerMinOffset, 0);
        setTopAndBottomOffset(headerOffset);

        // 移动列表
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarScrollBehavior) {
            final CalendarScrollBehavior listBehavior = (CalendarScrollBehavior) behavior;
            int listMinOffset = -(child.getMeasuredHeight() / 7) * 6;
            int listOffset = MathUtils.clamp(listBehavior.getTopAndBottomOffset() - dy, listMinOffset, 0);
            listBehavior.setTopAndBottomOffset(listOffset);
            if (listOffset > listMinOffset && listOffset < 0) {
                consumed[1] = dy;
            }
        }
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

}
