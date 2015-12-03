package com.jcdroid.jcandroiddemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jcdroid.jcandroiddemo.R;

/**
 * http://www.jianshu.com/p/84cee705b0d3
 * Created by caojing on 2015/12/3.
 */
public class RainbowProgress extends View {

    private int barColor = Color.parseColor("#1E88E5");
    private int lineWidth = RainbowProgress.dp2px(80, getResources());
    private int lineHeight = RainbowProgress.dp2px(4, getResources());
    private int space = RainbowProgress.dp2px(10, getResources());

    private float startX = 0;
    private float delta = 10f;

    private Paint mPaint;

    public RainbowProgress(Context context) {
        super(context);
    }

    public RainbowProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RainbowProgress, defStyleAttr, 0);
        try {
            barColor = t.getColor(R.styleable.RainbowProgress_lineColor, barColor);
            lineWidth = t.getDimensionPixelSize(R.styleable.RainbowProgress_lineWidth, lineWidth);
            lineHeight = t.getDimensionPixelSize(R.styleable.RainbowProgress_lineHeight, lineHeight);
        } finally {
            t.recycle();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
        mPaint.setStrokeWidth(lineHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//get screen width
        float sw = this.getMeasuredWidth();
        if (startX >= sw + (lineWidth + space) - (sw % (lineWidth + space))) {
            startX = 0;
        } else {
            startX += delta;
        }
        float start = startX;
        // draw latter parse
        while (start < sw) {
            canvas.drawLine(start, 5, start + lineWidth, 5, mPaint);
            start += (lineWidth + space);
        }

        start = startX - space - lineWidth;

        // draw front parse
        while (start >= -lineWidth) {
            canvas.drawLine(start, 5, start + lineWidth, 5, mPaint);
            start -= (lineWidth + space);
        }

        invalidate();
    }

    private static int dp2px(int dp, Resources resources) {
        float scale = resources.getDisplayMetrics().density;
        return (int)(scale * dp + 0.5);
    }
}
