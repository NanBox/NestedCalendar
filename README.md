[![](https://travis-ci.org/SouthernBox/NestedCalendar.svg?branch=master)](https://travis-ci.org/SouthernBox/NestedCalendar)
[![](https://api.bintray.com/packages/southernbox/maven/NestedCalendar/images/download.svg) ](https://bintray.com/southernbox/maven/NestedCalendar/_latestVersion)

# NestedCalendar

Make [MaterialCalendarView](https://github.com/prolificinteractive/material-calendarview) can be nested scroll, and smooth switch to week or month mode.

![]()

# Usage

**Add the dependencies to your gradle file:**

```javascript
dependencies {
    compile 'com.southernbox:NestedCalendar:1.1.0'
}
```
**Use in your layout file:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.prolificinteractive.materialcalendarview.MaterialCalendarView
        android:id="@+id/calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_behavior="@string/calendar_behavior"
        app:mcv_showOtherDates="all" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="@dimen/list_margin_bottom"
        app:layout_behavior="@string/calendar_scrolling_behavior"/>

    <com.southernbox.nestedcalendar.view.WeekTitleView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#fafafa" />

</android.support.design.widget.CoordinatorLayout>
```

**Add listener:**

```java
calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
    @Override
    public void onDateSelected(MaterialCalendarView widget,
                               CalendarDay date,
                               boolean selected) {
        Calendar calendar = date.getCalendar();
        calendarBehavior.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));
    }
});
```

**Calendar setting:**

About MaterialCalendarView, you can see more usage at:

[https://github.com/prolificinteractive/material-calendarview](https://github.com/prolificinteractive/material-calendarview)

Thanks to its developers.