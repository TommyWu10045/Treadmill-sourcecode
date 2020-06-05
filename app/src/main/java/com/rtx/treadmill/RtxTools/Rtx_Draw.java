package com.rtx.treadmill.RtxTools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.rtx.treadmill.GlobalData.EngSetting;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Draw {

    private static int FONT_TYPE = Typeface.NORMAL;

    public static int iGetTextWidth(Context mContext, String sText, String sFontPath, float fSize) {
        int iWidth = 0;

        Paint paint = new Paint();
        setPaint(mContext,paint,sFontPath,fSize);

        Rect bounds = new Rect();
        paint.getTextBounds(sText, 0, sText.length(), bounds);

        iWidth = bounds.right;

        return iWidth;
    }

    private static void setPaint(Context mContext, Paint paint, String sFontPath, float fSize)
    {
        {
            if(paint == null)  { return; }

            Typeface font = Typeface.createFromAsset(mContext.getAssets(), sFontPath);
            Typeface type = Typeface.create(font, FONT_TYPE);
            paint.setTypeface(type);
            paint.setAntiAlias(true);
            paint.setTextSize(fSize);
            paint.setLetterSpacing(EngSetting.LETTER_SPACING);
        }
    }
}
