package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanquan.calendar.helper.HeaderBehavior;

import java.util.Calendar;

/**
 * Created by nanquan on 2018/1/19.
 */

public class CalendarBehavior extends HeaderBehavior {

    public CalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int weekOfMonth = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarScrollBehavior) {
            // Offset the child, pinning it to the bottom the header-dependency, maintaining
            // any vertical gap and overlap
            final CalendarScrollBehavior ablBehavior = (CalendarScrollBehavior) behavior;

            return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !ablBehavior.getIsOnTop();
        }
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

//    @Override
//    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
//        return true;
//    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);


//        child.setTranslationY(child.getTranslationY() - dy);

//        if (dy != 0) {
//            int min, max;
//            if (dy < 0) {
//                // We're scrolling down
//                min = -child.getTotalScrollRange();
//                max = min + child.getDownNestedPreScrollRange();
//            } else {
//                // We're scrolling up
//                min = -child.getUpNestedPreScrollRange();
//                max = 0;
//            }
//            if (min != max) {
//                consumed[1] = scroll(coordinatorLayout, child, dy, min, max);
//            }
//        }

//        int min, max;
//        if (dy < 0) {
//            // We're scrolling down
//            min = -child.getTotalScrollRange();
//            max = min + child.getDownNestedPreScrollRange();
//        } else {
//            // We're scrolling up
//            min = -child.getUpNestedPreScrollRange();
//            max = 0;
//        }
//        if (min != max) {

        int minOffset = -(child.getMeasuredHeight() / 7) * weekOfMonth;
        scroll(coordinatorLayout, child, dy, minOffset, 0);


//        consumed[1] =   scroll(coordinatorLayout, target, dy, minOffset2, 0);


        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarScrollBehavior) {
            // Offset the child, pinning it to the bottom the header-dependency, maintaining
            // any vertical gap and overlap
            final CalendarScrollBehavior ablBehavior = (CalendarScrollBehavior) behavior;

//            ablBehavior.setTopAndBottomOffset(dy);
//            ablBehavior.setOnTop(getTopAndBottomOffset() == minOffset);

//            final int curOffset = ablBehavior.getTopAndBottomOffset();

//            if (minOffset2 != 0 && curOffset >= minOffset2 ) {
            // If we have some scrolling range, and we're currently within the min and max
            // offsets, calculate a new offset
//                dy = MathUtils.clamp(dy, minOffset2, 0);

//                if (curOffset != dy) {
            int minOffset2 = -(child.getMeasuredHeight() / 7) * 6;
            int offset = MathUtils.clamp(ablBehavior.getTopAndBottomOffset() - dy, minOffset2, 0);
//            if (minOffset2 < ablBehavior.getTopAndBottomOffset() - dy &&
//                    0 > ablBehavior.getTopAndBottomOffset() - dy) {

            ablBehavior.setTopAndBottomOffset(offset);
            if (offset > minOffset2 && offset < 0) {
                // Update how much dy we have consumed
                consumed[1] = dy;
            }
//        RecyclerView rv = (RecyclerView) target;
//        ((RecyclerView) target).setOnFlingListener(new RecyclerView.OnFlingListener() {
//            @Override
//            public boolean onFling(int velocityX, int velocityY) {
//                return false;
//            }
//        });
//            }
//                }
//            }

        }

//        }

//        setTopAndBottomOffset(getTopAndBottomOffset() - dy);


        // 修正 y 轴偏移量
//        consumed[1] = getTopAndBottomOffset() - dy;
    }


//            final int topHeight = (getMeasuredHeight() / 7) * weekOfMonth;
//
//            if (top > 0) {
//                return 0;
//            } else if (top < -topHeight) {
//                return -topHeight;
//            }


//    @Override
//    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//    }


}
