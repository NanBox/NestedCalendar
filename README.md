[![](https://api.bintray.com/packages/southernbox/maven/NestedCalendar/images/download.svg)](https://bintray.com/southernbox/maven/NestedCalendar/_latestVersion)
[![](https://img.shields.io/badge/Android%20Arsenal-NestedCalendar-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6978)
[![](https://img.shields.io/badge/API-16+-green.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://badge.juejin.im/entry/5ab9c5caf265da239d4952e4/likes.svg?style=flat)](https://juejin.im/post/5ab9c553f265da237f1e5079)

# NestedCalendar

Make MaterialCalendarView can be nested scroll, and smooth switch to week or month mode.

![](https://upload-images.jianshu.io/upload_images/1763614-2df81caa213e0794.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/304/format/webp)

# Usage

**Add the dependencies to your gradle file:**

```javascript
dependencies {
    implementation 'com.nanbox:NestedCalendar:1.3.0'
}
```
**Use in your layout file:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.prolificinteractive.materialcalendarview.MaterialCalendarView
        android:id="@+id/calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_behavior="@string/calendar_behavior"
        app:mcv_showOtherDates="all" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="110dp"
        android:background="@color/color_ee"
        app:layout_behavior="@string/calendar_scrolling_behavior"
        tools:listitem="@layout/item_list" />

    <com.nanbox.nestedcalendar.view.WeekTitleView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#fafafa" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
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

Thanks to the developers.
