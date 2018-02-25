package com.southernbox.nestedscrollcalendar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.southernbox.nestedscrollcalendar.helper.ViewOffsetBehavior;

import java.util.Calendar;

import static android.support.v4.view.ViewCompat.TYPE_TOUCH;

/**
 * 列表 Behavior
 * Created by SouthernBox on 2018/1/19.
 */

public class CalendarBehavior extends ViewOffsetBehavior<MaterialCalendarView> {

    private int calendarLineHeight;
    private CalendarMode calendarMode = CalendarMode.MONTHS;
    private int weekOfMonth = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);

    private final static int MSG_WEEK_MODE = 0;
    private final static int MSG_MONTH_MODE = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WEEK_MODE: {
                    if (calendarMode == CalendarMode.WEEKS) {
                        return true;
                    }
                    MaterialCalendarView calendarView = (MaterialCalendarView) msg.obj;
                    calendarView.state().edit()
                            .setCalendarDisplayMode(CalendarMode.WEEKS)
                            .commit();
                    calendarMode = CalendarMode.WEEKS;
                    setTopAndBottomOffset(0);
                }
                break;
                case MSG_MONTH_MODE: {
                    if (calendarMode == CalendarMode.MONTHS) {
                        return true;
                    }
                    MaterialCalendarView calendarView = (MaterialCalendarView) msg.obj;
                    calendarView.state().edit()
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit();
                    calendarMode = CalendarMode.MONTHS;
                    setTopAndBottomOffset(-calendarLineHeight * (weekOfMonth - 1));
                }
                break;
            }
            return true;
        }
    });

    public CalendarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull MaterialCalendarView child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target,
                                       int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull final MaterialCalendarView child,
                                  @NonNull View target,
                                  int dx, int dy,
                                  @NonNull int[] consumed,
                                  int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (calendarMode == CalendarMode.MONTHS) {
            // 移动头部
            if (calendarLineHeight == 0) {
                calendarLineHeight = child.getMeasuredHeight() / 7;
            }
            int headerMinOffset = -calendarLineHeight * (weekOfMonth - 1);
            int headerOffset = MathUtils.clamp(
                    getTopAndBottomOffset() - dy, headerMinOffset, 0);
            setTopAndBottomOffset(headerOffset);

            // 移动列表
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) target.getLayoutParams()).getBehavior();
            if (behavior instanceof CalendarScrollBehavior) {
                final CalendarScrollBehavior listBehavior = (CalendarScrollBehavior) behavior;
                int listMinOffset = -calendarLineHeight * 5;
                int listOffset = MathUtils.clamp(
                        listBehavior.getTopAndBottomOffset() - dy, listMinOffset, 0);
                listBehavior.setTopAndBottomOffset(listOffset);
                if (listOffset > listMinOffset && listOffset < 0) {
                    consumed[1] = dy;
                }
                if (listOffset == listMinOffset) {
                    Message message = new Message();
                    message.what = MSG_WEEK_MODE;
                    message.obj = child;
                    mHandler.sendMessageDelayed(message, 200);
                } else {
                    mHandler.removeMessages(MSG_WEEK_MODE);
                }
            }
        } else if (dy <= 0) {
            mHandler.removeMessages(MSG_WEEK_MODE);
            Message message = new Message();
            message.what = MSG_MONTH_MODE;
            message.obj = child;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                   @NonNull final MaterialCalendarView child,
                                   @NonNull final View target,
                                   int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        if (target.getTop() == calendarLineHeight * 2 ||
                target.getTop() == calendarLineHeight * 7) {
            return;
        }
        if (calendarMode == CalendarMode.MONTHS) {
            final Scroller scroller = new Scroller(coordinatorLayout.getContext());
            //设置scroller的滚动偏移量
            scroller.startScroll(0, target.getTop(), 0, calendarLineHeight * 4 - target.getTop(), 500);
            ViewCompat.postOnAnimation(child, new Runnable() {
                @Override
                public void run() {
                    // 返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。
                    // 这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
                    if (scroller.computeScrollOffset()) {
                        int delta = scroller.getCurrY() - target.getTop();
                        ((RecyclerView) target).startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, TYPE_TOUCH);
                        ((RecyclerView) target).dispatchNestedPreScroll(0, delta, new int[2], new int[2], TYPE_TOUCH);
                        ViewCompat.postOnAnimation(child, this);
                    }
                }
            });
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout,
                                    @NonNull MaterialCalendarView child,
                                    @NonNull View target,
                                    float velocityX, float velocityY) {
        return !(target.getTop() == calendarLineHeight * 2 ||
                target.getTop() == calendarLineHeight * 7);
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }


}