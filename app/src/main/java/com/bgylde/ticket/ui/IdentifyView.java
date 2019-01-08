package com.bgylde.ticket.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bgylde.ticket.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyan on 2019/1/7
 */
public class IdentifyView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "IdentifyView";

    private List<Integer> clickPointList;

    private List<Point> eventPoint;

    private float imgWidth;
    private float imgHeight;

    private float itemLength;

    private Paint paint;

    public IdentifyView(Context context) {
        super(context);
        init();
    }

    public IdentifyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IdentifyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setClickable(true);
        clickPointList = new ArrayList<>();
        eventPoint = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                recordPoint(event);
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Point point : eventPoint) {
            LogUtils.d(TAG, "x: " + point.x + " y: " + point.y);
            canvas.drawCircle(point.x, point.y, 50, paint);
        }
    }

    public String getIdentifyPoint() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clickPointList.size(); i++) {
            Point point = convertFromIndex(clickPointList.get(i));
            sb.append(point.x).append(",").append(point.y);
            if (i != clickPointList.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    // 41,35,  41,118,  110,46,  119,110,  187,120,  193,44,  268,44,  265,118
    private Point convertFromIndex(int index) {
        Point point = new Point();
        switch (index) {
            case 1:
                point.x = 41;
                point.y = 35;
                break;
            case 2:
                point.x = 110;
                point.y = 46;
                break;
            case 3:
                point.x = 187;
                point.y = 44;
                break;
            case 4:
                point.x = 268;
                point.y = 44;
                break;
            case 5:
                point.x = 41;
                point.y = 118;
                break;
            case 6:
                point.x = 119;
                point.y = 110;
                break;
            case 7:
                point.x = 193;
                point.y = 120;
                break;
            case 8:
                point.x = 265;
                point.y = 118;
                break;
        }

        return point;
    }

    private void recordPoint(MotionEvent event) {
        int index = calculatePoint(event);
        LogUtils.d(TAG, "index: " + index);
        if (!clickPointList.contains(index)) {
            clickPointList.add(index);

            Point point = new Point((int)event.getX(), (int)event.getY());
            eventPoint.add(point);
            postInvalidate();
        }
    }

    private int calculatePoint(MotionEvent event) {
        imgWidth = getWidth();
        imgHeight = getHeight();

        itemLength = imgWidth / 4;

        float eventX = event.getX() - getLeft();
        float eventY = event.getY() - getTop();

        LogUtils.d(TAG, "itemLength: " + itemLength);
        LogUtils.d(TAG, "eventX: " + eventX + " eventY: " + eventY);

        int x = 0, y = 0;
        if (eventY < imgHeight - 2 * itemLength) {
            return -1;
        }

        if (imgWidth > 0 && eventX < imgWidth) {
            x = (int)(eventX / itemLength);
        }

        if (imgHeight > 0 && eventY < imgHeight) {
            y = (int)(eventY / itemLength);
            if (eventY > imgHeight - itemLength) {
                y = 1;
            } else if (eventY > imgHeight - 2 * itemLength) {
                y = 0;
            }
        }

        LogUtils.d(TAG, "x: " + x + " y:" + y);
        return (4 * y) + x + 1;
    }
}
