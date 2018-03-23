package com.southernbox.nestedcalendar.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.southernbox.nestedcalendar.behavior.CalendarBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalendarBehavior calendarBehavior;
    private int dayOfWeek;
    private int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCalendarView();
        initRecyclerView();
    }

    private void initCalendarView() {
        MaterialCalendarView calendarView = findViewById(R.id.calendar);
        calendarView.setTopbarVisible(false);
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) calendarView.getLayoutParams()).getBehavior();
        if (behavior instanceof CalendarBehavior) {
            calendarBehavior = (CalendarBehavior) behavior;
        }
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(Calendar.getInstance());
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if (calendarBehavior != null) {
            calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget,
                                           @NonNull CalendarDay date,
                                           boolean selected) {
                    Calendar calendar = date.getCalendar();
                    calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
                    if (selected) {
                        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    }
                }
            });
            calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
                @Override
                public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                    if (calendarBehavior.getCalendarMode() == null) {
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    date.copyTo(calendar);
                    if (calendarBehavior.getCalendarMode() == CalendarMode.WEEKS) {
                        calendar.add(Calendar.DAY_OF_WEEK, dayOfWeek - 1);
                        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        int monthDays = calendar.getActualMaximum(Calendar.DATE);
                        if (dayOfMonth > monthDays) {
                            dayOfMonth = monthDays;
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, dayOfMonth - 1);
                        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    }
                    widget.setSelectedDate(calendar);
                    calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
                    setTitle((calendar.get(Calendar.MONTH) + 1) + "月");
                }
            });
        }
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        String[] names = getResources().getStringArray(R.array.query_suggestions);
        List<String> mList = new ArrayList<>();
        Collections.addAll(mList, names);
        rv.setAdapter(new ListAdapter(this, mList));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);
    }

}
