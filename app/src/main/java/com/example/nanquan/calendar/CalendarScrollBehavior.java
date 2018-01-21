package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanquan.calendar.helper.HeaderScrollingViewBehavior;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

/**
 * Created by nanquan on 2018/1/19.
 */

public class CalendarScrollBehavior extends HeaderScrollingViewBehavior {

    public CalendarScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof MaterialCalendarView;
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof MaterialCalendarView) {
                return view;
            }
        }
        return null;
    }

    @Override
    protected int getScrollRange(View v) {
//        if (v instanceof MaterialCalendarView) {
//            return v.getMeasuredHeight();
//        }
        return Math.max(0, v.getMeasuredHeight());
    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return isOnTop;
//    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
//        final CoordinatorLayout.Behavior behavior =
//                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
//        if (behavior instanceof CalendarBehavior) {
//            // Offset the child, pinning it to the bottom the header-dependency, maintaining
//            // any vertical gap and overlap
//            final CalendarBehavior mBehavior = (CalendarBehavior) behavior;
//            ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop())
//                    + mBehavior.mOffset);
//        }

//        child.setTranslationY(dependency.getTranslationY());

//        ViewCompat.offsetTopAndBottom(child, dependency.getTopAndBottomOffset());


//        final CoordinatorLayout.Behavior behavior =
//                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
//        if (behavior instanceof CalendarBehavior) {
//            // Offset the child, pinning it to the bottom the header-dependency, maintaining
//            // any vertical gap and overlap
//            final CalendarBehavior ablBehavior = (CalendarBehavior) behavior;
//
//            setTopAndBottomOffset(ablBehavior.getTopAndBottomOffset());
//
//        }

    }

    private boolean isOnTop;

    public void setOnTop(boolean isOnTop) {
        this.isOnTop = isOnTop;
    }

    public boolean getIsOnTop(){
        return isOnTop;
    }
}
