package com.southernbox.nestedcalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.southernbox.nestedcalendar.R;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DATE;

/**
 * 星期标题
 * Created by SouthernBox on 2018/1/23.
 */

public class WeekTitleView extends ViewGroup {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;

    private Calendar calendar;

    private float textSize;

    public WeekTitleView(Context context) {
        this(context, null);
    }

    public WeekTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calendar = Calendar.getInstance();
        TypedArray typedArray = context
                .getTheme()
                .obtainStyledAttributes(attrs, R.styleable.WeekTitleView, 0, 0);
        textSize = typedArray.getLayoutDimension(R.styleable.WeekTitleView_text_size, 12);
        addView();
    }

    private void addView() {
        for (int i = 1; i <= DEFAULT_DAYS_IN_WEEK; i++) {
            TextView weekTextView = new TextView(getContext());
            calendar.set(Calendar.DAY_OF_WEEK, i);
            weekTextView.setTextSize(textSize);
            weekTextView.setGravity(Gravity.CENTER);
            weekTextView.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
            addView(weekTextView);
            calendar.add(DATE, 1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (specHeightMode == MeasureSpec.AT_MOST) {
            specHeightSize = specWidthSize / DEFAULT_DAYS_IN_WEEK;
        }

        setMeasuredDimension(specWidthSize, specHeightSize);

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    specWidthSize / DEFAULT_DAYS_IN_WEEK,
                    MeasureSpec.EXACTLY
            );
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    specHeightSize,
                    MeasureSpec.EXACTLY
            );
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childLeft = 0;

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, 0, childLeft + width, height);
            childLeft += width;
        }
    }
}
