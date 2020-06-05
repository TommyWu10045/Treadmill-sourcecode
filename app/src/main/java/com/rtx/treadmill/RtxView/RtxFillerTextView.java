package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by jerry on 2016/12/29.
 */

public class RtxFillerTextView extends RtxTextView
{

    private Paint mPaint = new Paint();
    private int iMode;

    int iFillet = 30;
    int iStrokeWidth = 10;

    public RtxFillerTextView(Context context) {
        super(context);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    public RtxFillerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    public RtxFillerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    public RtxFillerTextView(Context context , boolean bButtonEffect) {
        super(context,bButtonEffect);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    public void setStrokeWidth(int iW)
    {
        iStrokeWidth = iW;
    }

    @Override
    public void setBackgroundColor(int color) {
        // TODO Auto-generated method stub
        mPaint.setColor(color);
    }

    public void setMode(int iVal)
    {
        iMode = iVal;
        invalidate();
    }

    public void setFillet(int iR)
    {
        iFillet = iR;
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub

        if(iMode == 1)
        {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, mPaint);
        }
        else
        if(iMode == 2)
        {
            mPaint.setStrokeWidth(iStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, (Math.max(getWidth(), getHeight()) / 2) - (iStrokeWidth / 2), mPaint);
        }
        else
        if(iMode == 3)
        {
            int iStrokeWidth = 4;
            mPaint.setStrokeWidth(iStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(iStrokeWidth / 2, iStrokeWidth / 2, getWidth() - (iStrokeWidth / 2), getHeight() - (iStrokeWidth / 2), iFillet, iFillet, mPaint);
        }
        else
        if(iMode == 4)
        {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, getWidth(), getHeight() , mPaint);
        }
        else
        if(iMode == 5)
        {
            int iStrokeWidth = 4;
            mPaint.setStrokeWidth(iStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(0, 0, getWidth(), getHeight() , mPaint);
        }
        else
        if(iMode == -1)
        {

        }
        else
        {
            mPaint.setStyle(Paint.Style.FILL);
            setFillet(getHeight() / 2);
            canvas.drawRoundRect(0, 0, getWidth(), getHeight(), iFillet, iFillet, mPaint);
        }

        super.draw(canvas);
    }
}
