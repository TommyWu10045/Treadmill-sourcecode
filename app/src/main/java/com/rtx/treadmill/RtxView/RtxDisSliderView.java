package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxDisSliderView extends RtxView {
    Context mContext;

    Paint paint_Main;
    Paint paint_Sub;

    int       FONT_TYPE = Typeface.NORMAL;

    int iMain_clolor = Common.Color.white;
    int iSub_clolor = Common.Color.blue_1;

    float fVal = 0f;
    float fVal_unit = 1f;
    float fVal_min = 0f;
    float fVal_max = 10f;
    int iUnit = 0;
    String sUnit = "km";
    int iStep = 5;

    int iGap = 20;
    int iOffset = 30;

    public RtxDisSliderView(Context context) {
        super(context);

        mContext = context;
        default_Paint();
    }

    public void default_Paint()
    {
        setPaint(Common.Font.Relay_Black,iMain_clolor,66.66f,Common.Font.Relay_BlackItalic,iSub_clolor,33.05f);
    }

    public void setPaint(String sFont_1, int iColor_1, float fSize_1, String sFont_2, int iColor_2, float fSize_2)
    {
        {
            if(paint_Main == null)  { paint_Main = new Paint(); }

            Typeface font = Typeface.createFromAsset(mContext.getAssets(), sFont_1);
            Typeface type = Typeface.create(font, FONT_TYPE);
            paint_Main.setTypeface(type);
            paint_Main.setAntiAlias(true);
            paint_Main.setTextSize(fSize_1);
            iMain_clolor = iColor_1;
            paint_Main.setColor(iMain_clolor);
            paint_Main.setLetterSpacing(EngSetting.LETTER_SPACING);
        }

        {
            if(paint_Sub == null)  { paint_Sub = new Paint(); }

            Typeface font = Typeface.createFromAsset(mContext.getAssets(), sFont_2);
            Typeface type = Typeface.create(font, FONT_TYPE);
            paint_Sub.setTypeface(type);
            paint_Sub.setAntiAlias(true);
            paint_Sub.setTextSize(fSize_2);
            iSub_clolor = iColor_2;
            paint_Sub.setColor(iSub_clolor);
            paint_Sub.setLetterSpacing(EngSetting.LETTER_SPACING);
        }

    }

    public void setGap(int iGap)
    {
        this.iGap = iGap;
    }

    public void setDef(String sunit, int iunit, int istep, float fmin, float fmax)
    {
        sUnit = sunit;
        iUnit = iunit;
        iStep = istep;
        fVal_min = fmin ;

        if((fmax % 1) != 0) {
            fVal_max = (int)(fmax + 1.9f);
        }
        else
        {
            fVal_max = fmax + 0.999f;
        }

//        Log.e("Jerry", "==============fmax=" + fmax);
//        Log.e("Jerry", "==============fVal_max=" + fVal_max);
    }

    public void setVal(float fval)
    {
        fVal = fval;
        invalidate();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void draw(Canvas canvas, Rect r)
    {
        int iLoop;
        Rect bounds = new Rect();
        Rect bounds_sub = new Rect();
        String str = "";
        String str_sub = sUnit;
        int iw_total = 0;
        int iw_gap ;
        int icenter = ((iStep + 1) / 2) - 1;
        int x ;
        int y ;
        int x_sub ;
        int y_sub ;
        float fshift = fVal % fVal_unit;
        int ishift ;
        float fdraw_val;

        for(iLoop = 0; iLoop < iStep; iLoop++)
        {
            fdraw_val = fVal+(fVal_unit * (iLoop - icenter));
            //str = Rtx_TranslateValue.sFloat2String(fdraw_val, iUnit);
            str = Rtx_TranslateValue.sInt2String((int)fdraw_val);
            paint_Main.getTextBounds(str, 0, str.length(), bounds);
            paint_Sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);
            iw_total += bounds.width() + bounds_sub.width() + iGap;
        }


        if(iStep > 1)
        {
            iw_gap = (r.width() - iw_total - (iOffset * 2) ) / (iStep - 1);
            ishift = (int)((bounds.width() + bounds_sub.width() + iGap + iw_gap) * fshift);
            x = r.left + iOffset - ishift;
//            Log.e("Jerry", "==============fVal=" + fVal);
            for(iLoop = 0; iLoop < iStep; iLoop++) {
                fdraw_val = fVal + (fVal_unit * (iLoop - icenter));

                str = Rtx_TranslateValue.sInt2String((int)fdraw_val);

                paint_Main.getTextBounds(str, 0, str.length(), bounds);
                paint_Sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);

                y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();

                x_sub = x + iGap + bounds.width();
                y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height();

//                Log.e("Jerry", "==============fdraw_val=" + fdraw_val);
                if(fdraw_val >= fVal_min && fdraw_val <= fVal_max) {
                    canvas.drawText(str, x, y, paint_Main);
                    canvas.drawText(str_sub, x_sub, y_sub, paint_Sub);
                }

                x = x_sub + bounds_sub.width() + iw_gap;

            }
        }


    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = getWidth();
        rect.bottom = getHeight();

        canvas.drawColor(0x00000000);

        draw(canvas,rect);
    }

}