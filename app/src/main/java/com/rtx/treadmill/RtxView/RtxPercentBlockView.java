package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.rtx.treadmill.GlobalData.Common;


/**
 * TODO: document your custom view class.
 */
public class RtxPercentBlockView extends RtxView {

    int iPercentValue = 0;
    Paint mPaint = null;

    int iColor_Background = Common.Color.gray_3;
    int iColor_ValueBar = Common.Color.yellow_4;

    public RtxPercentBlockView(Context context ) {
        super(context);

        init();

    }

    public void init()
    {
        init_paint();
    }

    private void init_paint()
    {
        if(mPaint == null)
        {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        mPaint.setColor(iColor_ValueBar);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setBackGroundColor(int iColor)
    {
        iColor_Background = iColor;
    }

    public void setValueBarColor(int iColor)
    {
        iColor_ValueBar = iColor;
    }

    public void setPercentValue(int iPercent)
    {
        iPercentValue = iPercent;
    }

    public void setPercentValue(float fVal , float fMax)
    {
        iPercentValue = (int)((fVal / fMax) * 100);
    }

    public int getPercentValue()
    {
        return iPercentValue;
    }

    private void setPaint(int iColor)
    {
        mPaint.setColor(iColor);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iWidth;

        iWidth = ( getWidth() * iPercentValue ) / 100;

        {
            RectF rect1 = new RectF(0, 0, getWidth(), getHeight());
            setPaint(iColor_Background);
            canvas.drawRect(rect1, mPaint);
        }

        {
            RectF rect2 = new RectF(0, 0, iWidth, getHeight());
            setPaint(iColor_ValueBar);
            canvas.drawRect(rect2, mPaint);
        }
    }
}
