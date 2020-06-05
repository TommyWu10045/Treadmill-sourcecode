package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.UiDataStruct;


/**
 * TODO: document your custom view class.
 */
public class View_HIIT_RunChart extends View {

    private float SPEED_MAX = 20f;
    private float SPEED_MIN = 0f;

    private float INCLINE_MAX = 20f;
    private float INCLINE_MIN = 0f;

    int BLOCK_HEIGHT = 30;
    int VERTICAL_OFFSET = BLOCK_HEIGHT / 2;

    Context mContext;
    int iLayoutWidth;
    int iLayoutHeight;

    Paint mPaint;

    int COLOR_BACKGROUND    = 0x00000000;
    int COLOR_INCLINE       = 0xFF088AFF;
    int COLOR_SPEED_WARM    = 0xFF4FECBE;
    int COLOR_SPEED_COLD    = 0xFF66ACFF;
    int COLOR_SPEED_RUN     = 0xFFFFEC00;
    int COLOR_SPEED_WALK    = 0xFFFFFFFF;

    //UiDataStruct.INTERVAL_STAGE_INFO data_list
    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;

    int iSec_WarmUp = 0;
    int iSec_SpeedFast = 0;
    int iSec_ColdDown = 0;
    int iSec_TotalTime = 0;

    public View_HIIT_RunChart(Context context, int iW, int iH ) {
        super(context);

        mContext = context;
        iLayoutWidth = iW;

        BLOCK_HEIGHT = iH / 12;

        VERTICAL_OFFSET = BLOCK_HEIGHT / 2;
        iLayoutHeight = iH - BLOCK_HEIGHT;

        init();
    }

    public void init()
    {
        setBackgroundColor(COLOR_BACKGROUND);
        init_paint();
    }

    private void init_paint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void vSetWarmUpColor(int iColor)
    {
        COLOR_SPEED_WARM = iColor;
    }

    public void vSetCoolDownColor(int iColor)
    {
        COLOR_SPEED_COLD = iColor;
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

        if(hiitItemInfo.stage_WarmUp != null)
        {
            iSec_WarmUp = hiitItemInfo.stage_WarmUp.iTime;
        }

        if(hiitItemInfo.stage_CoolDown != null)
        {
            iSec_ColdDown = hiitItemInfo.stage_CoolDown.iTime;
        }

        {
            iSize = hiitItemInfo.list_Stage.size();

            for(iIndex = 0 ; iIndex < iSize ; iIndex ++)
            {
                iSec_SpeedFast += hiitItemInfo.list_Stage.get(iIndex).iTime;
            }
        }

        iSec_TotalTime = iSec_WarmUp + iSec_ColdDown + iSec_SpeedFast;
    }

    private void iDrawSpeedData(Canvas canvas)
    {
        int iHalfBlockHight = BLOCK_HEIGHT / 2;
        float fW = 0;
        float fH = BLOCK_HEIGHT;
        float fPosX = 0;
        float fPosY = 0;

        int rx = iHalfBlockHight;
        int ry = iHalfBlockHight;

        if(hiitItemInfo == null)
        {
            return;
        }

        if(hiitItemInfo.stage_WarmUp == null)
        {
            return;
        }

        {
            mPaint.setColor(COLOR_SPEED_WARM);
            fPosX = 0;
            fW = ((float)iSec_WarmUp / (float)iSec_TotalTime) * (float)iLayoutWidth;

            fPosY = (iLayoutHeight - ((hiitItemInfo.stage_WarmUp.fGetSpeed() / SPEED_MAX) * (iLayoutHeight - iHalfBlockHight))) - iHalfBlockHight;
            RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
            canvas.drawRoundRect(rect, rx, ry, mPaint);
        }

        {
            int iIndex = 0;
            int iSize = 0;

            iSize = hiitItemInfo.list_Stage.size();

            if(iSize > 0)
            {
                for(iIndex = 0 ; iIndex < iSize ; iIndex ++)
                {
                    if((iIndex % 2) == 0)
                    {
                        mPaint.setColor(COLOR_SPEED_RUN);

                        fPosX += fW;
                        fW = ((float) hiitItemInfo.list_Stage.get(iIndex).iTime / (float) iSec_TotalTime) * (float) iLayoutWidth;
                        fPosY = (iLayoutHeight - ((hiitItemInfo.list_Stage.get(iIndex).fGetSpeed() / SPEED_MAX) * (iLayoutHeight - iHalfBlockHight))) - iHalfBlockHight;

                        RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
                        canvas.drawRoundRect(rect, rx, ry, mPaint);
                    }
                    else
                    {
                        mPaint.setColor(COLOR_SPEED_WALK);

                        fPosX += fW;
                        fW = ((float) hiitItemInfo.list_Stage.get(iIndex).iTime / (float) iSec_TotalTime) * (float) iLayoutWidth;
                        fPosY = (iLayoutHeight - ((hiitItemInfo.list_Stage.get(iIndex).fGetSpeed() / SPEED_MAX) * (iLayoutHeight - iHalfBlockHight))) - iHalfBlockHight;

                        RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
                        canvas.drawRoundRect(rect, rx, ry, mPaint);
                    }
                }
            }
        }

        {
            mPaint.setColor(COLOR_SPEED_COLD);
            fW = ((float)iSec_ColdDown / (float)iSec_TotalTime) * (float)iLayoutWidth;
            fPosX = iLayoutWidth - fW;

            fPosY = (iLayoutHeight - ((hiitItemInfo.stage_CoolDown.fGetSpeed() / SPEED_MAX) * (iLayoutHeight - iHalfBlockHight))) - iHalfBlockHight;
            RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
            canvas.drawRoundRect(rect, rx, ry, mPaint);
        }
    }

    private void iDrawInclineData(Canvas canvas)
    {
        float fW = 0;
        float fH = 0;
        float fPosX = 0;
        float fPosY = 0;
        int iHalfBlockHight = BLOCK_HEIGHT / 2;

        if(iSec_TotalTime <= 0)
        {
            return;
        }

        mPaint.setColor(COLOR_INCLINE);

        {
            fPosX = 0;
            fW = ((float)iSec_WarmUp / (float)iSec_TotalTime) * (float)iLayoutWidth;
            fH = (hiitItemInfo.stage_WarmUp.fGetIncline() / INCLINE_MAX) * (iLayoutHeight - iHalfBlockHight);
            fPosY = iLayoutHeight - fH;

            RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
            canvas.drawRect(rect,  mPaint);

            //Log.e("Jerry","data_list.oStageData_WarmUp.fGetIncline() = " + data_list.oStageData_WarmUp.fGetIncline());
        }

        {
            int iIndex = 0;
            int iSize = 0;

            iSize = hiitItemInfo.list_Stage.size();

            if(iSize > 0)
            {
                for(iIndex = 0 ; iIndex < iSize ; iIndex ++)
                {
                    if((iIndex % 2) == 0)
                    {
                        fPosX += fW;
                        fW = ((float) hiitItemInfo.list_Stage.get(iIndex).iTime / (float) iSec_TotalTime) * (float) iLayoutWidth;
                        fH = (hiitItemInfo.list_Stage.get(iIndex).fGetIncline() / INCLINE_MAX) * (iLayoutHeight - iHalfBlockHight);
                        fPosY = iLayoutHeight - fH;

                        RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
                        canvas.drawRect(rect, mPaint);
                    }
                    else
                    {
                        fPosX += fW;
                        fW = ((float) hiitItemInfo.list_Stage.get(iIndex).iTime / (float) iSec_TotalTime) * (float) iLayoutWidth;
                        fH = (hiitItemInfo.list_Stage.get(iIndex).fGetIncline() / INCLINE_MAX) * (iLayoutHeight - iHalfBlockHight);
                        fPosY = iLayoutHeight - fH;

                        RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
                        canvas.drawRect(rect, mPaint);
                    }
                }
            }
        }

        {
            fW = ((float)iSec_ColdDown / (float)iSec_TotalTime) * (float)iLayoutWidth;
            fPosX = iLayoutWidth - fW;
            fH = (hiitItemInfo.stage_CoolDown.fGetIncline() / INCLINE_MAX) * (iLayoutHeight - iHalfBlockHight);
            fPosY = iLayoutHeight - fH;

            RectF rect = new RectF(fPosX, VERTICAL_OFFSET + fPosY, fPosX + fW, VERTICAL_OFFSET + fPosY + fH);
            canvas.drawRect(rect,  mPaint);

            //Log.e("Jerry","data_list.oStageData_ColdDown.fGetIncline() = " + data_list.oStageData_ColdDown.fGetIncline());
        }
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(EngSetting.Distance.getVal(EngSetting.f_Get_Max_Speed()) > EngSetting.f_Get_Max_Incline())
        {
            SPEED_MAX = EngSetting.Distance.getVal(EngSetting.f_Get_Max_Speed());
            INCLINE_MAX = EngSetting.Distance.getVal(EngSetting.f_Get_Max_Speed());
        }
        else
        {
            SPEED_MAX = EngSetting.f_Get_Max_Incline();
            INCLINE_MAX = EngSetting.f_Get_Max_Incline();
        }
        if(EngSetting.Distance.getVal(EngSetting.f_Get_Min_Speed()) > EngSetting.f_Get_Min_Incline())
        {
            SPEED_MIN = EngSetting.f_Get_Min_Incline();
            INCLINE_MIN = EngSetting.f_Get_Min_Incline();
        }
        else
        {
            SPEED_MIN = EngSetting.Distance.getVal(EngSetting.f_Get_Min_Speed());
            INCLINE_MIN = EngSetting.Distance.getVal(EngSetting.f_Get_Min_Speed());
        }

        iDrawInclineData(canvas);
        iDrawSpeedData(canvas);

    }

    public int getVerticalOffset()
    {
        return VERTICAL_OFFSET;
    }
}
