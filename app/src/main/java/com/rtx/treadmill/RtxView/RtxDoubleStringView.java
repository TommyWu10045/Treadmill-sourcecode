package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxDoubleStringView extends RtxView {
    Context mContext;

    Paint paint_Main;
    Paint paint_Sub;

    int       FONT_TYPE = Typeface.NORMAL;
    String sMain;
    String sSub;


    int iMain_clolor = Common.Color.white;
    int iSub_clolor = Common.Color.blue_1;

    int iGap = 20;
    int iGap_Arrow = 40;

    boolean bArrow = false;
    int ix_shift = 10;

    private boolean bEllipsis = false;
    private int gravity = Gravity.NO_GRAVITY;

    public RtxDoubleStringView(Context context) {
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

    public String getMainText()
    {
        return sMain;
    }

    public void setText(String str1, String str2)
    {
        sMain = str1;
        sSub = str2;
        invalidate();
    }

    public void setText(String str1, int iResID)
    {
        sMain = str1;
        sSub = LanguageData.s_get_string(mContext, iResID);
        invalidate();
    }

    public void setColor(int iColor_1, int iColor_2)
    {
        iMain_clolor = iColor_1;
        iSub_clolor = iColor_2;
        paint_Main.setColor(iMain_clolor);
        paint_Sub.setColor(iSub_clolor);
        invalidate();
    }

    public void vsetArrow(boolean ben, int iGap)
    {
        bArrow = ben;
        this.iGap_Arrow = iGap;
    }

    public int i_Get_Xshift()
    {
        return ix_shift;
    }

    public void setEllipsis(boolean bFlag)
    {
        bEllipsis = bFlag;
    }

    public void setAlignLeft(int gravity)
    {
        this.gravity = gravity;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void drawCenter(Canvas canvas, Rect r, Paint paint, String str, int iGap, Paint paint_sub, String str_sub)
    {
        if((str == null) || (str_sub == null))
        {
            return;
        }

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        int x = r.left + (r.width() / 2) - (bounds.width() / 2);
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);


        Rect bounds_sub = new Rect();
        paint_sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);
        int x_sub = r.left + (r.width() / 2) + (bounds.width() / 2) + iGap;
        //int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height()/2;
        int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height();
        canvas.drawText(str_sub, x_sub, y_sub, paint_sub);

        if(bArrow)
        {
            if(str_sub.length() > 0) {
                ix_shift = x_sub + bounds_sub.width() + iGap_Arrow;
            }
            else
            {
                ix_shift = x_sub - iGap +  iGap_Arrow;
            }
        }

    }

    private void drawCenterAll(Canvas canvas, Rect r, Paint paint, String str, int iGap, Paint paint_sub, String str_sub)
    {
        if((str == null) || (str_sub == null))
        {
            return;
        }

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        Rect bounds_sub = new Rect();
        paint_sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);

        int x = r.left + (r.width() / 2) - (bounds.width() / 2) - (bounds_sub.width() / 2) + iGap / 2;
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);

        int x_sub = r.left + (r.width() / 2) + (bounds.width() / 2) + iGap - (bounds_sub.width() / 2);
        int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height();
        canvas.drawText(str_sub, x_sub, y_sub, paint_sub);

        if(bArrow)
        {
            if(str_sub.length() > 0) {
                ix_shift = x_sub + bounds_sub.width() + iGap_Arrow;
            }
            else
            {
                ix_shift = x_sub - iGap +  iGap_Arrow;
            }
        }

    }


    private void drawLeft(Canvas canvas, Rect r, Paint paint, String str, int iGap, Paint paint_sub, String str_sub)
    {
        if((str == null) || (str_sub == null))
        {
            return;
        }

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        int x = r.left;
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);


        Rect bounds_sub = new Rect();
        paint_sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);
        int x_sub = r.left + bounds.width() + iGap;
        //int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height()/2;
        int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height();
        canvas.drawText(str_sub, x_sub, y_sub, paint_sub);
    }

    private void drawRight(Canvas canvas, Rect r, Paint paint, String str, int iGap, Paint paint_sub, String str_sub)
    {
        if((str == null) || (str_sub == null))
        {
            return;
        }

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        Rect bounds_sub = new Rect();
        paint_sub.getTextBounds(str_sub, 0, str_sub.length(), bounds_sub);

        int x = r.right - bounds.width() - iGap - bounds_sub.width() - 10;
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);

        int x_sub = r.right - bounds_sub.width() - 10  ;
        int y_sub = r.top + (r.height() / 2) - (bounds_sub.height() / 2) + bounds_sub.height();
        canvas.drawText(str_sub, x_sub, y_sub, paint_sub);
    }

    private String vBreakText(String sFstStr , String sSecStr , Paint pFstPaint , Paint pSecPaint)
    {
        String sText = null;

        float fFstStrWidth = 0;
        float fSecStrWidth = 0;

        if(sFstStr == null)
        {
            return sText;
        }

        fFstStrWidth = pFstPaint.measureText(sFstStr);

        if(sSecStr == null)
        {
            fSecStrWidth = 0;
            iGap = 0;
        }
        else
        {
            fSecStrWidth = pSecPaint.measureText(sSecStr);
        }


        if((fFstStrWidth + fSecStrWidth + iGap) > getWidth())
        {
            float fFstEnoughWidth = getWidth() - iGap - fSecStrWidth;
            int iSubIndex = pFstPaint.breakText(sFstStr, 0, sFstStr.length(), true, fFstEnoughWidth, null);

            sText = sFstStr.substring(0, iSubIndex - 3) + "...";
        }

        return sText;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        String sMainBreakText = null;

        if(bEllipsis)
        {
            sMainBreakText = vBreakText(sMain,sSub,paint_Main,paint_Sub);

            if(sMainBreakText != null)
            {
                sMain = new String(sMainBreakText);
            }
        }

        {
            Rect rect = new Rect();
            rect.left = 0;
            rect.top = 0;
            rect.right = getWidth();
            rect.bottom = getHeight();

            canvas.drawColor(0x00000000);

            if(gravity == Gravity.LEFT)
            {
                drawLeft(canvas,rect,paint_Main,sMain,iGap,paint_Sub,sSub);
            }
            else if(gravity == Gravity.RIGHT)
            {
                drawRight(canvas,rect,paint_Main,sMain,iGap,paint_Sub,sSub);
            }
            else if(gravity == Gravity.CENTER)
            {
                drawCenterAll(canvas,rect,paint_Main,sMain,iGap,paint_Sub,sSub);
            }
            else
            {
                drawCenter(canvas,rect,paint_Main,sMain,iGap,paint_Sub,sSub);
            }

        }
    }
}