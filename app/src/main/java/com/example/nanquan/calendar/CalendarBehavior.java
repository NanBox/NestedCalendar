package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanquan.calendar.helper.ViewOffsetBehavior;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

/**
 * Created by nanquan on 2018/1/19.
 */

public class CalendarBehavior extends ViewOffsetBehavior<MaterialCalendarView> {

    private int calendarLineHeight;
    private CalendarMode calendarMode = CalendarMode.MONTHS;
    private int weekOfMonth = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);

    public CalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

//    @Override
//    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View target, float velocityX, float velocityY) {
//        return true;
//    }

    private boolean shouldInitConsumed;

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        if (calendarMode == CalendarMode.MONTHS) {
            // 移动头部
            if (calendarLineHeight == 0) {
                calendarLineHeight = child.getMeasuredHeight() / 7;
            }
            int headerMinOffset = -calendarLineHeight * (weekOfMonth - 1);
            int headerOffset = MathUtils.clamp(getTopAndBottomOffset() - dy, headerMinOffset, 0);
            setTopAndBottomOffset(headerOffset);

            // 移动列表
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
            if (behavior instanceof CalendarScrollBehavior) {
                final CalendarScrollBehavior listBehavior = (CalendarScrollBehavior) behavior;
                int listMinOffset = -calendarLineHeight * 5;
                int listOffset = MathUtils.clamp(listBehavior.getTopAndBottomOffset() - dy, listMinOffset, 0);
                listBehavior.setTopAndBottomOffset(listOffset);
                if (listOffset > listMinOffset && listOffset < 0) {
                    consumed[1] = dy;
                }
                if (listBehavior.getTopAndBottomOffset() <= listMinOffset) {
                    shouldInitConsumed = true;
                    setWeekMode(child, listBehavior);
                }
            }
        } else {
            // 固定头部
            setTopAndBottomOffset(0);

            // 移动列表
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
            if (behavior instanceof CalendarScrollBehavior) {
                final CalendarScrollBehavior listBehavior = (CalendarScrollBehavior) behavior;
                int listMinOffset = -calendarLineHeight * 5;
                int listOffset = MathUtils.clamp(-calendarLineHeight * 5 - dy, listMinOffset, 0);
                listBehavior.setTopAndBottomOffset(listOffset);
                if (shouldInitConsumed) {
                    consumed[1] = -listMinOffset;
                    shouldInitConsumed = false;
                } else if (listOffset > listMinOffset && listOffset < 0) {
                    consumed[1] = dy;
                }

                if (listBehavior.getTopAndBottomOffset() > listMinOffset + 5) {
                    setMonthMode(child);
                }
            }
        }
    }

    // 切换星期模式
    private void setWeekMode(final MaterialCalendarView calendarView, final CalendarScrollBehavior listBehavior) {
        if (calendarMode == CalendarMode.WEEKS) {
            return;
        }
        calendarView.setVisibility(View.INVISIBLE);
        calendarView.postDelayed(new Runnable() {
            @Override
            public void run() {
                calendarView.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit();
                calendarMode = CalendarMode.WEEKS;
                setTopAndBottomOffset(0);
                listBehavior.setTopAndBottomOffset(0);

                calendarView.setVisibility(View.VISIBLE);
            }
        }, 1);

    }

    // 切换月模式
    private void setMonthMode(MaterialCalendarView calendarView) {
        if (calendarMode == CalendarMode.MONTHS) {
            return;
        }
        calendarView.state().edit()
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarMode = CalendarMode.MONTHS;

        setTopAndBottomOffset(-calendarLineHeight * (weekOfMonth - 1));
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

}
