package com.bgylde.ticket.ui;

import android.content.Context;
import android.graphics.Bitmap;
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

    private float imgWidth;
    private float imgHeight;

    private float itemLength;

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
        imgWidth = bm.getWidth();
        imgHeight = bm.getHeight();

        itemLength = imgWidth / 4;
    }

    private void recordPoint(MotionEvent event) {
        int index = calculatePoint(event);
        LogUtils.d(TAG, "index: " + index);
        int offsetX = 0;
        int offsetY = 0;
        switch (index) {
            case 1:
                offsetX = 42;
                offsetY = 46;
                break;
            case 2:
                offsetX = 46;
                offsetY = 105;
                break;
            case 3:
                offsetX = 45;
                offsetY = 184;
                break;
            case 4:
                offsetX = 48;
                offsetY = 256;
                break;
            case 5:
                offsetX = 36;
                offsetY = 117;
                break;
            case 6:
                offsetX = 112;
                offsetY = 115;
                break;
            case 7:
                offsetX = 114;
                offsetY = 181;
                break;
            case 8:
                offsetX = 111;
                offsetY = 252;
                break;
        }

        if (!clickPointList.contains(offsetX) && !clickPointList.contains(offsetY)) {
            clickPointList.add(offsetX);
            clickPointList.add(offsetY);
        }
    }

    private int calculatePoint(MotionEvent event) {
        float eventX = event.getX() - getLeft();
        float eventY = event.getY() - getTop();

        LogUtils.d(TAG, "itemLength: " + itemLength);
        LogUtils.d(TAG, "imgHeight: " + imgHeight + " imgWidth: " + imgWidth);
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
        return ((x + 1) * 4 + (y + 1) * 2);
    }
}
