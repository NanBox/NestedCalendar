package com.southernbox.nestedcalendar.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.southernbox.nestedcalendar.behavior.CalendarBehavior;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCalendarView();
        initRecyclerView();
    }

    private void initCalendarView() {
        calendarView = findViewById(R.id.calendar);
        calendarView.setTopbarVisible(false);

        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                final CoordinatorLayout.Behavior behavior =
                        ((CoordinatorLayout.LayoutParams) calendarView.getLayoutParams()).getBehavior();
                if (behavior instanceof CalendarBehavior) {
                    final CalendarBehavior calendarBehavior = (CalendarBehavior) behavior;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date.getDate());
                    calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
                }
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ListAdapter(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
