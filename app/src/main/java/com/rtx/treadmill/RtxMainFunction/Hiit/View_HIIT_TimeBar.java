package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.rtx.treadmill.GlobalData.UiDataStruct;


/**
 * TODO: document your custom view class.
 */
public class View_HIIT_TimeBar extends View {

    Context mContext;
    int iLayoutWidth;
    int iLayoutHeight;

    Paint mPaint;

    int COLOR_BACKGROUND    = 0x00000000;
    int COLOR_SPEED_WARM    = 0xFF4FECBE;
    int COLOR_SPEED_COLD    = 0xFF66ACFF;
    int COLOR_SPEED_RUN     = 0xFFFFEC00;

    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;

    int iSec_WarmUp = 0;
    int iSec_SpeedFast = 0;
    int iSec_ColdDown = 0;
    int iSec_TotalTime = 0;

    public View_HIIT_TimeBar(Context context, int iW, int iH ) {
        super(context);

        mContext = context;
        iLayoutWidth = iW;
        iLayoutHeight = iH;

        init();
    }

    public void init()
    {
        setBackgroundColor(COLOR_BACKGROUND);
        init_paint();
    }

    public void vSetWarmUpColor(int iColor)
    {
        COLOR_SPEED_WARM = iColor;
    }

    public void vSetHiitColor(int iColor)
    {
        COLOR_SPEED_RUN = iColor;
    }

    public void vSetCoolDownColor(int iColor)
    {
        COLOR_SPEED_COLD = iColor;
    }

    private void init_paint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setItemInfo(UiDataStruct.HIIT_ITEM_INFO data)
    {
        hiitItemInfo = data;
        vCalTotalSec();
        invalidate();
    }

    private void vCalTotalSec()
    {
        int iIndex,iSize;

        iSec_WarmUp = 0;
        iSec_ColdDown = 0;
        iSec_SpeedFast = 0;
        iSec_TotalTime = 0;

        iSec_WarmUp = hiitItemInfo.stage_WarmUp.iTime;
        iSec_ColdDown = hiitItemInfo.stage_CoolDown.iTime;

        {
            iSize = hiitItemInfo.list_Stage.size();

            for(iIndex = 0 ; iIndex < iSize ; iIndex ++)
            {
                iSec_SpeedFast += hiitItemInfo.list_Stage.get(iIndex).iTime;
            }
        }

        iSec_TotalTime = iSec_WarmUp + iSec_ColdDown + iSec_SpeedFast;
    }

    private void iDrawTimeBar(Canvas canvas)
    {
        float fW = iLayoutWidth;
        float fH = iLayoutHeight;
        float fPosX = 0;
        float fPosY = 0;

        float fMaskWidth = 20;

        if(iSec_TotalTime <= 0)
        {
            return;
        }

        {
            RectF rect;
            if(iSec_WarmUp > 0)
            {
                fPosX = 0;
                fW = ((float)iSec_WarmUp / (float)iSec_TotalTime) * (float)iLayoutWidth;
                mPaint.setColor(COLOR_SPEED_WARM);
                rect = new RectF(fPosX, fPosY, fPosX + fW + fMaskWidth, fPosY + fH);
            }
            else
            {
                fPosX = 0;
                fW = ((float)iSec_SpeedFast / (float)iSec_TotalTime) * (float)iLayoutWidth;
                mPaint.setColor(COLOR_SPEED_RUN);
                rect = new RectF(fPosX, fPosY, fPosX + fW, fPosY + fH);
            }



            canvas.drawRoundRect(rect, 20, 20, mPaint);
        }

        {
            RectF rect;

            if(iSec_ColdDown > 0)
            {
                fW = ((float)iSec_ColdDown / (float)iSec_TotalTime) * (float)iLayoutWidth;
                mPaint.setColor(COLOR_SPEED_COLD);
                fPosX = iLayoutWidth - fW;
                rect = new RectF(fPosX - fMaskWidth, fPosY, fPosX + fW, fPosY + fH);
            }
            else
            {
                fW = ((float)iSec_SpeedFast / (float)iSec_TotalTime) * (float)iLayoutWidth;
                mPaint.setColor(COLOR_SPEED_RUN);
                fPosX = iLayoutWidth - fW;
                rect = new RectF(fPosX, fPosY, fPosX + fW, fPosY + fH);
            }

            canvas.drawRoundRect(rect, 20, 20, mPaint);
        }

        {
            fW = ((float)iSec_SpeedFast / (float)iSec_TotalTime) * (float)iLayoutWidth;

            if(iSec_ColdDown <= 0)
            {
                fPosX = iLayoutWidth - fMaskWidth;
                fW = fW - fMaskWidth;
            }


            if(iSec_WarmUp > 0)
            {
                fPosX = fPosX - fW;
            }
            else
            {
                fW = fW - fMaskWidth;
                fPosX = fPosX - fW;
            }


            RectF rect = new RectF(fPosX, fPosY, fPosX + fW, fPosY + fH);
            mPaint.setColor(COLOR_SPEED_RUN);
            canvas.drawRect(rect,  mPaint);
        }
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        iDrawTimeBar(canvas);
    }
}
