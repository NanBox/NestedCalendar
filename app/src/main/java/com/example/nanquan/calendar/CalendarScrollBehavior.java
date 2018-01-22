package com.example.nanquan.calendar;

import android.content.Context;
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

    private int calendarHeight;

    public CalendarScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof MaterialCalendarView;
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        if (calendarHeight == 0) {
            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, z = dependencies.size(); i < z; i++) {
                View view = dependencies.get(i);
                if (view instanceof MaterialCalendarView) {
                    calendarHeight = view.getMeasuredHeight();
                }
            }
        }
        child.setTop(calendarHeight);
        child.setBottom(child.getBottom() + calendarHeight);
    }
}
