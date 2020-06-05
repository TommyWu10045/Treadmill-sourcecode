package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.rtx.treadmill.GlobalData.Common;


/**
 * TODO: document your custom view class.
 */
public class RtxPercentCircleView extends RtxView {

    int iColor_Background = Common.Color.gray_3;
    int iColor_ValueBar = Common.Color.yellow_4;
    int iPantStrokeWidth = 18;

    Paint mPaint;
    int iPercentValue = 0;

    public RtxPercentCircleView(Context context ) {
        super(context);

        init();
    }

    public void init()
    {
        init_paint();
    }

    private void init_paint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(iPantStrokeWidth);
        mPaint.setColor(iColor_ValueBar);
    }

    public void setPercentValue(int iPercent)
    {
        iPercentValue = iPercent;
        invalidate();
    }

    public void setPercentValue(float fVal , float fTotal)
    {
        iPercentValue = (int)(( fVal * 100 ) / fTotal);
        invalidate();
    }

    public void setBackgroundColor(int iColor)
    {
        iColor_Background = iColor;
    }

    public void setValueBarColor(int iColor)
    {
        iColor_ValueBar = iColor;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        {   //Draw Circle
            canvas.save();
            RectF oval=new RectF();
            oval.left=0 + (iPantStrokeWidth/2);
            oval.top=0 + (iPantStrokeWidth/2);
            oval.right=getWidth() - (iPantStrokeWidth/2);
            oval.bottom=getHeight() - (iPantStrokeWidth/2);
            mPaint.setColor(iColor_Background);
            canvas.drawArc(oval, 0, 360, false, mPaint);
            canvas.restore();
        }

        {
            int iAngle = ( iPercentValue * 360 ) / 100;
            canvas.save();
            RectF oval=new RectF();
            oval.left=0 + (iPantStrokeWidth/2);
            oval.top=0 + (iPantStrokeWidth/2);
            oval.right=getWidth() - (iPantStrokeWidth/2);
            oval.bottom=getHeight() - (iPantStrokeWidth/2);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setColor(iColor_ValueBar);
            canvas.drawArc(oval, -90, iAngle, false, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int sideLength = Math.min(widthSpecSize, heightSpecSize);
        setMeasuredDimension(sideLength, sideLength);
    }
}
