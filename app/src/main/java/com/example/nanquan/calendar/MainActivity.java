package com.example.nanquan.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendar);

        calendarView.setTopbarVisible(false);

        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());

        calendarView.getSelectedDate();


        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(new ListAdapter(this));

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                final CoordinatorLayout.Behavior behavior =
                        ((CoordinatorLayout.LayoutParams) calendarView.getLayoutParams()).getBehavior();
                if (behavior instanceof CalendarBehavior) {
                    // Offset the child, pinning it to the bottom the header-dependency, maintaining
                    // any vertical gap and overlap
                    final CalendarBehavior ablBehavior = (CalendarBehavior) behavior;

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date.getDate());
                    ablBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));


                }
            }
        });

    }
}
