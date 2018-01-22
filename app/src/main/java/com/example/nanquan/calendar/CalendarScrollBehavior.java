package com.example.nanquan.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanquan.calendar.helper.ViewOffsetBehavior;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

/**
 * Created by nanquan on 2018/1/19.
 */

public class CalendarScrollBehavior extends ViewOffsetBehavior<RecyclerView> {

    private int headerHeight;

    public CalendarScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        if (headerHeight == 0) {
            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, z = dependencies.size(); i < z; i++) {
                View view = dependencies.get(i);
                if (view instanceof MaterialCalendarView) {
                    headerHeight = view.getMeasuredHeight();
                }
            }
        }
        child.setTop(headerHeight);
        child.setBottom(child.getBottom() + headerHeight);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof MaterialCalendarView;
    }

//    @Override
//    protected int getScrollRange(View v) {
////        if (v instanceof MaterialCalendarView) {
////            return v.getMeasuredHeight();
////        }
//        return Math.max(0, v.getMeasuredHeight());
//    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return isOnTop;
//    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, RecyclerView child, View dependency) {
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

    public boolean getIsOnTop() {
        return isOnTop;
    }
}
