package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxView;

import java.util.Calendar;

/**
 * TODO: document your custom view class.
 */
public class RtxView_DateIcon_Big extends RtxView {

    int iLayoutWidth = 74;
    int iLayoutHeight = 74;

    Context mContext;

    String  sFontPath;
    int     iFontStyle;

    Paint mPaint;
    Paint mTextPaint;


    String string_DayOfMonth = "";
    String string_DayOfWeek = "";


    public RtxView_DateIcon_Big(Context context) {
        super(context);

        mContext = context;

        sFontPath = "fonts/Relay-Bold.ttf";
        iFontStyle = Typeface.BOLD;

        init();

    }

    public RtxView_DateIcon_Big(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public RtxView_DateIcon_Big(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init()
    {
        init_paint();
    }

    private void init_paint()
    {
        {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
            mPaint.setColor(Common.Color.blue_8);
        }

        {
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Typeface font = Typeface.createFromAsset(mContext.getAssets(), sFontPath);
            Typeface type = Typeface.create(font, iFontStyle);
            mTextPaint.setTypeface(type);
            mTextPaint.setAntiAlias(true);
        }
    }

    public void setDay(Calendar cal)
    {
        if(cal == null)
        {
            return;
        }

        String sDayOfMonth = null;
        String sDayOfWeek = null;

        sDayOfMonth = Rtx_TranslateValue.sInt2String(cal.get(Calendar.DAY_OF_MONTH));
        sDayOfWeek = Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1];

        setDay(sDayOfMonth,sDayOfWeek);

    }

    public void setDay(String sDayOfMonth , String sDayOfWeek)
    {
        string_DayOfMonth =  sDayOfMonth;
        string_DayOfWeek = sDayOfWeek;
        invalidate();
    }

    private void drawCenter(Canvas canvas, Rect r, Paint paint, String str)
    {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        int x = r.left + (r.width() / 2) - (bounds.width() / 2);
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        {
            canvas.drawRect(0, 0, iLayoutWidth, iLayoutHeight, mPaint);
        }

        {

            mTextPaint.setTextSize(18.99f);
            mTextPaint.setColor(0xFF2E2EEE);

            {
                Rect rect = new Rect();
                rect.left = 0;
                rect.top = 10;
                rect.right = iLayoutWidth;
                rect.bottom = 35;

                drawCenter(canvas,rect,mTextPaint,string_DayOfWeek);
            }
        }

        {
            //mPaint.setStrokeWidth(2);
            mTextPaint.setTextSize(26.67f);
            mTextPaint.setColor(Common.Color.white);

            {
                Rect rect = new Rect();
                rect.left = 0;
                rect.top = 30;
                rect.right = iLayoutWidth;
                rect.bottom = 70;

                drawCenter(canvas,rect,mTextPaint,string_DayOfMonth);
            }
        }
    }
}
