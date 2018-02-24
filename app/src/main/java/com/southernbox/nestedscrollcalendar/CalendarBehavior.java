package com.southernbox.nestedscrollcalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.southernbox.nestedscrollcalendar.helper.ViewOffsetBehavior;

import java.util.Calendar;

/**
 * Created by SouthernBox on 2018/1/19.
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

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull final MaterialCalendarView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d("666", "start");
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
                if (listOffset == listMinOffset) {
                    child.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setWeekMode(child, listBehavior);
                        }
                    }, 100);
                }
            }
        } else if (dy <= 0) {
            setMonthMode(child);
        }
    }

//    @Override
//    public void onStopNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final MaterialCalendarView child, @NonNull final View target, int type) {
//        super.onStopNestedScroll(coordinatorLayout, child, target, type);
//        if (target.getTop() == calendarLineHeight * 2 ||
//                target.getTop() == calendarLineHeight * 7) {
//            return;
//        }
//        Log.d("666", "stop");
//        if (calendarMode == CalendarMode.MONTHS) {
//            final Scroller scroller = new Scroller(coordinatorLayout.getContext());
//            //设置scroller的滚动偏移量
//            scroller.startScroll(0, child.getTop(), 0, 0 - child.getTop(), 500);
//            ViewCompat.postOnAnimation(child, new Runnable() {
//                @Override
//                public void run() {
//                    //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。
//                    // 这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
//                    if (scroller.computeScrollOffset()) {
//                        int delta = scroller.getCurrY();
//                        Log.d("666666666666", scroller.getCurrY() + "");
//                        coordinatorLayout.onNestedPreScroll(target, 0, delta, new int[2]);
//                        ViewCompat.postOnAnimation(child, this);
//                    }
//                }
//            });
//        }
//    }

//    @Override
//    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MaterialCalendarView child, @NonNull View target, float velocityX, float velocityY) {
//        if (target.getTop() == calendarLineHeight * 2 ||
//                target.getTop() == calendarLineHeight * 7) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    // 切换星期模式
    private void setWeekMode(final MaterialCalendarView calendarView, final CalendarScrollBehavior listBehavior) {
        if (calendarMode == CalendarMode.WEEKS) {
            return;
        }
        calendarView.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        calendarMode = CalendarMode.WEEKS;
        setTopAndBottomOffset(0);
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
