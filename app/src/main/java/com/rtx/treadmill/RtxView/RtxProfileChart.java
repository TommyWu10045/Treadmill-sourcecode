package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;

import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class  RtxProfileChart extends RtxImageView {
    private String TAG = "Jerry";

    private Context mContext;

    private int imode = 0; //0 : treadmill; etc : bike
    private int iIncline_mode = 0x02; //0x01 : bar ; 0x02 : path

    private Paint mPaintSpeed;          //Paint Speed
    private Paint mPaintIncline;        //Paint Incline
    private Paint mPaintLevel;        //Paint Level

    private List<Float> Incline_list = null;
    private List<Float> Speed_list = null;

    private int iXScale = 30;
    private int iflash_count = 0;
    private int ieffect = 0x01; //0x01: alpha; 0x02: flash
    private int[] iYScale = {0, 25};
    private float fspeed_times = 1f;

    private int COLOR_BACKGROUND = Common.Color.background;

    private int COLOR_SPEED = Common.Color.exercise_speed_bar;
    private int COLOR_SPEED_ALPHA = Common.Color.exercise_speed_bar_alpha;
    private int COLOR_INCLINE = Common.Color.exercise_incline_bar;
    private int COLOR_INCLINE_ALPHA = Common.Color.exercise_incline_bar_alpha;
    private int COLOR_LEVEL = Common.Color.exercise_level_bar;
    private int COLOR_LEVEL_ALPHA = Common.Color.exercise_level_bar_alpha;

    private int iViewWidth = 100;
    private int iViewHeight = 100;
    private float fscale = 1f;
    private int icount ;

    Bitmap b = null;
    Canvas c = null;
    Path path1 = new Path();
    Path path2 = new Path();

    public RtxProfileChart(Context context){
        super(context);

        mContext = context;

    }

    public void vSetInclineList(List<Float> list)
    {
        Incline_list = list;
    }

    public void vSetSpeedList(List<Float> list)
    {
        Speed_list = list;
    }

    public void vSetflash(int iflash)
    {
        iflash_count = iflash;
        Create_Bitmap();
    }

    public void vSet_iIncline_mode(int imode)
    {
        iIncline_mode = imode;
    }

    public void vSet_ieffect(int imode)
    {
        ieffect = imode;
    }

    public void vSet_mode(int imode)
    {
        this.imode = imode;
    }

    public void vSet_limit(int imin, int imax)
    {
        iYScale[0] = imin;
        iYScale[1] = imax;
    }

    public void init(int iscale, int imin, int imax, float fscale, int ix, int iy, int iw, int ih)
    {
        this.iXScale = iscale;
        this.fscale = fscale;
        iYScale[0] = imin;
        iYScale[1] = imax;
        iViewWidth = iw;
        iViewHeight = ih;
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            fspeed_times = 1f;
        }
        else
        {
            fspeed_times = EngSetting.km2mile;
        }
        init_paint();
        Create_Bitmap();
    }

    @Override
    public void setBackgroundColor(int iColor)
    {
        COLOR_BACKGROUND = iColor;
    }

    private void init_paint()
    {
        mPaintSpeed = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSpeed.setColor(COLOR_SPEED);
        mPaintSpeed.setAntiAlias(true);

        mPaintIncline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintIncline.setColor(COLOR_INCLINE);
        mPaintIncline.setAntiAlias(true);

        mPaintLevel = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLevel.setColor(COLOR_LEVEL);
        mPaintLevel.setAntiAlias(true);

    }

    /////////////////////////////
    private int iGet_Scale(int input)
    {
        int ival = (int)(input * fscale);

        if(ival <= 0)
        {
            ival = 1;
        }

        return ival;
    }

    private float fGet_Scale(float finput)
    {
        float fval = finput * fscale;

        if(fval <= 0)
        {
            fval = 1f;
        }

        return fval;
    }

    ////////////////////////////
    private boolean vSpeed_effect(int iLoop)
    {
        boolean bret = true;

        if((ieffect & 0x01) == 0x01)
        {
            if(iflash_count < 0)
            {
                mPaintSpeed.setColor(COLOR_SPEED_ALPHA);
            }
            else if (iLoop == iflash_count + 1) {
                mPaintSpeed.setColor(COLOR_SPEED_ALPHA);
            }
        }

        if((ieffect & 0x02) == 0x02)
        {
            if(iflash_count < 0)
            {
            }
            else if (iLoop == iflash_count) {
                if(icount % 8 >= 4) {
                    bret = false;
                }
            }
        }

        return bret;
    }

    private void iDrawSpeedData(Canvas canvas)
    {
        int iLoop ;
        int iPosX, iEndPosX ;
        int iPosY, iEndPosY ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iSingleHeight = iViewHeight/idiff;
        int iPosYOffset = 0;
        RectF rect;
        float fspeed;
        int iBox_h = iGet_Scale(15);
        int iWidthMax, iLoopDraw;

        //因為在最大速度時,繪圖會導致圖形顯示不完全,故計算偏移量
        if((iViewHeight - (int)(iYScale[1] * iSingleHeight) - iBox_h / 2) < 0)
        {
            iPosYOffset = 0 - (iViewHeight - (int)(iYScale[1] * iSingleHeight) - iBox_h / 2);
        }

        if(Speed_list != null)
        {
            mPaintSpeed.setColor(COLOR_SPEED);
            if(iSingleWidth > 0)
            {
                for(iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if(iLoop < Speed_list.size())
                    {
                        fspeed = (Speed_list.get(iLoop) * fspeed_times) - iYScale[0];
                        //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
                        if(fspeed > iYScale[1] && (fspeed - 1) < iYScale[1])
                        {
                            fspeed = iYScale[1];
                        }
                        if(fspeed < iYScale[0] && (fspeed + 1) > iYScale[0])
                        {
                            fspeed = iYScale[0];
                        }
                        iPosX = iSingleWidth * iLoop;
                        iEndPosX = iPosX + iSingleWidth;
                        iPosY = iViewHeight + iPosYOffset - (int)(fspeed * iSingleHeight) - iBox_h / 2;
                        iEndPosY = iPosY + iPosYOffset + iBox_h;

                        rect = new RectF(iPosX , iPosY , iEndPosX , iEndPosY);
                        if(vSpeed_effect(iLoop))
                        {
                            canvas.drawRoundRect(rect, iGet_Scale(15 / 2), iGet_Scale(15 / 2), mPaintSpeed);
                        }
                    }
                }
            }
            else
            {
                //當iXScale超過範圍iViewWidth
                iSingleWidth = 1;

                iWidthMax = (iViewWidth / (((iXScale * iSingleWidth) + 59) / 60));
                iLoopDraw = 0;

                for (iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if (iLoop % 60 < iWidthMax)
                    {
                        if (iLoop < Speed_list.size())
                        {
                            fspeed = (Speed_list.get(iLoop) * fspeed_times) - iYScale[0];
                            iPosX = iSingleWidth * iLoopDraw;
                            iEndPosX = iPosX + iSingleWidth;

                            iPosY = iViewHeight + iPosYOffset - (int) (fspeed * iSingleHeight) - iBox_h / 2;
                            iEndPosY = iPosY + iPosYOffset + iBox_h;

                            rect = new RectF(iPosX, iPosY, iEndPosX, iEndPosY);
                            if (vSpeed_effect(iLoopDraw))
                            {
                                canvas.drawRoundRect(rect, iGet_Scale(15 / 2), iGet_Scale(15 / 2), mPaintSpeed);
                            }
                        }
                        iLoopDraw++;
                    }
                }
            }
        }
    }

    ////////////////////////////
    private boolean vIncline_effect(int iLoop)
    {
        boolean bret = true;

        if((ieffect & 0x01) == 0x01)
        {
            if(iflash_count < 0)
            {
                mPaintIncline.setColor(COLOR_INCLINE_ALPHA);
            }
            else if (iLoop == iflash_count + 1) {
                mPaintIncline.setColor(COLOR_INCLINE_ALPHA);
            }
        }

        if((ieffect & 0x02) == 0x02)
        {
            if(iflash_count < 0)
            {
            }
            else if (iLoop == iflash_count) {
                if(icount % 8 >= 4) {
                    bret = false;
                }
            }
        }

        return bret;
    }

    private int vIncline_effect_path1(int iLoop)
    {
        int iret = 0; //0 不變化， 1 顯示， 2 閃動,不顯示

        if(iflash_count < 0)
        {
            iret =  0x01;
        }
        else if (iLoop == iflash_count) {
            if((ieffect & 0x02) == 0x02) {
                iret = 0x01;
                if (icount % 8 >= 4) {
                    iret = 0x02;
                }
            }
            else if((ieffect & 0x01) == 0x01)
            {
                iret =  0x01;
            }
        }
        return iret;
    }

    private void vIncline_effect_path_clolor()
    {
        if((ieffect & 0x01) == 0x01) {
            mPaintIncline.setColor(COLOR_INCLINE_ALPHA);
        }

        return;
    }

    private void iDrawInclineData_Path_square(Canvas canvas)
    {
        int iLoop ;
        int iPosX = 0 , iEndPosX = 0 ;
        int iPosY = 0, iEndPosY = 0 ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iSingleHeight = iViewHeight/idiff;
        RectF rect;
        float fincline;
        int istate = 0;
        int iWidthMax, iLoopDraw;

        if(Incline_list != null)
        {
            mPaintIncline.setColor(COLOR_INCLINE);
            Path path1 = new Path();
            Path path2 = new Path();
            iEndPosY = iViewHeight;

            path1.moveTo(0,iEndPosY);
            if(iSingleWidth > 0)
            {
                for (iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if(iLoop < Incline_list.size())
                    {
                        fincline = Incline_list.get(iLoop) - iYScale[0];
                        iPosX = iSingleWidth * iLoop;
                        iPosY = iEndPosY - (int)(fincline * iSingleHeight);
                        iEndPosX = iPosX + iSingleWidth;

                        if (istate == 0)
                        {
                            if (vIncline_effect_path1(iLoop) == 0x00)
                            {
                                path1.lineTo(iPosX, iPosY);
                                path1.lineTo(iEndPosX, iPosY);
                            }
                            else if (vIncline_effect_path1(iLoop) == 0x01)
                            {
                                path1.lineTo(iPosX, iPosY);
                                path1.lineTo(iEndPosX, iPosY);
                                path1.lineTo(iEndPosX, iEndPosY);
                                path1.close();

                                istate++;
                                path2.moveTo(iEndPosX, iEndPosY);
                            }
                            else if (vIncline_effect_path1(iLoop) == 0x02)
                            {
                                path1.lineTo(iPosX, iEndPosY);
                                path1.close();

                                istate ++;
                                path2.moveTo(iEndPosX, iEndPosY);
                            }
                        }
                        else if(istate == 1)
                        {
                            path2.lineTo(iPosX, iPosY);
                            path2.lineTo(iEndPosX, iPosY);
                        }
                    }
                }

                path2.lineTo(iEndPosX, iEndPosY);
                path2.close();

                canvas.drawPath(path1, mPaintIncline);
                vIncline_effect_path_clolor();
                canvas.drawPath(path2, mPaintIncline);
            }
            else
            {
                //當iXScale超過範圍iViewWidth
                iSingleWidth = 1;

                iWidthMax = (iViewWidth / (((iXScale * iSingleWidth) + 59) / 60));
                iLoopDraw = 0;

                for (iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if(iLoop % 60 < iWidthMax)
                    {
                        if(iLoop < Incline_list.size())
                        {
                            fincline = Incline_list.get(iLoop) - iYScale[0];
                            iPosX = iSingleWidth * iLoopDraw;
                            iPosY = iEndPosY - (int) (fincline * iSingleHeight);
                            iEndPosX = iPosX + iSingleWidth;

                            rect = new RectF(iPosX, iPosY, iEndPosX, iEndPosY);
                            if (vSpeed_effect(iLoopDraw))
                            {
                                canvas.drawRoundRect(rect, iGet_Scale(15 / 2), iGet_Scale(15 / 2), mPaintIncline);
                            }
                        }
                        iLoopDraw++;
                    }
                }
            }
        }
    }

    //return 0 : 平 ； 1 : 向上 ； 2 : 向下
    private int i_get_direction_head(int isel, List<Float> farray)
    {
        int iret = 0;
        float fcurr, fprev;

        if(farray != null)
        {
            if(isel > 0) {
                if (farray.size() > isel) {
                    fcurr = farray.get(isel);
                    fprev = farray.get(isel - 1);
                    if (fcurr > fprev) {
                        iret = 1;
                    } else if (fcurr < fprev) {
                        iret = 2;
                    }
                }
            }
            else
            {
                iret = 1;
            }
        }

        return iret;
    }

    //return 0 : 平 ； 1 : 向上 ； 2 : 向下
    private int i_get_direction_tail(int isel, List<Float> farray)
    {
        int iret = 0;
        float fcurr, fnext;

        if(farray != null)
        {
            if(farray.size() > (isel + 1))
            {
                fcurr = farray.get(isel);
                fnext = farray.get(isel+1);
                if(fnext > fcurr)
                {
                    iret = 1;
                }
                else if(fnext < fcurr)
                {
                    iret = 2;
                }
            }
            else
            {
                iret = 2;
            }
        }

        return iret;
    }

    private void v_get_path1(int idir_head, int idir_tail, int iPosX, int iPosY, int iEndPosX, int iEndPosY, int iRadius)
    {
        if(idir_head == 0)
        {
            path1.lineTo(iPosX, iPosY);
        }
        else if(idir_head == 1)
        {
            path1.lineTo(iPosX, iPosY + iRadius);
            path1.arcTo(iPosX, iPosY, iPosX + iRadius * 2, iPosY + iRadius * 2, -180, 90, false);
        }
        else if(idir_head == 2)
        {
            path1.lineTo(iPosX, iPosY - iRadius);
            path1.arcTo(iPosX, iPosY - iRadius * 2, iPosX + iRadius * 2, iPosY, -180, -90, false);
        }

        if(idir_tail == 0)
        {
            path1.lineTo(iEndPosX, iPosY);
        }
        else if(idir_tail == 1)
        {
            path1.lineTo(iEndPosX - iRadius, iPosY);
            path1.arcTo(iEndPosX - iRadius * 2, iPosY - iRadius * 2, iEndPosX, iPosY, 90, -90, false);
        }
        else if(idir_tail == 2)
        {
            path1.lineTo(iEndPosX - iRadius, iPosY);
            path1.arcTo(iEndPosX - iRadius * 2, iPosY, iEndPosX, iPosY + iRadius * 2, -90, 90, false);
        }

        return ;
    }

    private void v_get_path2(int idir_head, int idir_tail, int iPosX, int iPosY, int iEndPosX, int iEndPosY, int iRadius)
    {
        if(idir_head == 0)
        {
            path2.lineTo(iPosX, iPosY);
        }
        else if(idir_head == 1)
        {
            path2.lineTo(iPosX, iPosY + iRadius);
            path2.arcTo(iPosX, iPosY, iPosX + iRadius * 2, iPosY + iRadius * 2, -180, 90, false);
        }
        else if(idir_head == 2)
        {
            path2.lineTo(iPosX, iPosY - iRadius);
            path2.arcTo(iPosX, iPosY - iRadius * 2, iPosX + iRadius * 2, iPosY, -180, -90, false);
        }

        if(idir_tail == 0)
        {
            path2.lineTo(iEndPosX, iPosY);
        }
        else if(idir_tail == 1)
        {
            path2.lineTo(iEndPosX - iRadius, iPosY);
            path2.arcTo(iEndPosX - iRadius * 2, iPosY - iRadius * 2, iEndPosX, iPosY, 90, -90, false);
        }
        else if(idir_tail == 2)
        {
            path2.lineTo(iEndPosX - iRadius, iPosY);
            path2.arcTo(iEndPosX - iRadius * 2, iPosY, iEndPosX, iPosY + iRadius * 2, -90, 90, false);
        }

        return ;
    }

    private int iGet_Path_Radius(int iw) {
        int iRadius = 0;
        int ival ;

        ival = iw/ 5;
        if(ival > 2)
        {
            iRadius = ival;
        }

        return iRadius;
    }

    private void iDrawInclineData_Path_round(Canvas canvas, int iRadius)
    {
        int iLoop ;
        int iPosX = 0 , iEndPosX = 0 ;
        int iPosY = 0, iEndPosY = 0 ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iSingleHeight = iViewHeight/idiff;
        float fincline;
        int istate = 0;
        int idir_head = 0;
        int idir_tail = 0;

        if(Incline_list != null)
        {
            mPaintIncline.setColor(COLOR_INCLINE);
            iEndPosY = iViewHeight;

            path1.reset();
            path2.reset();
            path1.moveTo(0,iEndPosY);
            for (iLoop = 0; iLoop < iXScale + 1; iLoop++) {
                if(iLoop < Incline_list.size()) {
                    fincline = Incline_list.get(iLoop) - iYScale[0];
                    iPosX = iSingleWidth * iLoop;
                    iPosY = iEndPosY - (int)(fincline * iSingleHeight);
                    iEndPosX = iPosX + iSingleWidth ;;

                    idir_head = i_get_direction_head(iLoop, Incline_list);
                    idir_tail = i_get_direction_tail(iLoop, Incline_list);

                    if(istate == 0) {
                        if (vIncline_effect_path1(iLoop) == 0x00) {
                            v_get_path1(idir_head, idir_tail, iPosX, iPosY, iEndPosX, iEndPosY, iRadius);
                        }
                        else if (vIncline_effect_path1(iLoop) == 0x01)
                        {
                            v_get_path1(idir_head, idir_tail, iPosX, iPosY, iEndPosX, iEndPosY, iRadius);
                            path1.lineTo(iEndPosX, iEndPosY);
                            path1.close();

                            istate ++;
                            path2.moveTo(iEndPosX,iEndPosY);
                        }
                        else if (vIncline_effect_path1(iLoop) == 0x02) {
                            path1.lineTo(iPosX, iEndPosY);
                            path1.close();

                            istate ++;
                            path2.moveTo(iEndPosX,iEndPosY);
                        }
                    }
                    else if(istate == 1) {
                        v_get_path2(idir_head, idir_tail, iPosX, iPosY, iEndPosX, iEndPosY, iRadius);
                    }
                }
                else
                {
                    path1.lineTo(iEndPosX, iEndPosY);
                    path1.close();
                }
            }

            path2.lineTo(iEndPosX, iEndPosY);
            path2.close();

            canvas.drawPath(path1, mPaintIncline);
            vIncline_effect_path_clolor();
            canvas.drawPath(path2, mPaintIncline);

        }
    }

    private void iDrawInclineData_Path(Canvas canvas)
    {
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iRadius = iGet_Path_Radius(iSingleWidth);

       if(iRadius > 0)
       {
           iDrawInclineData_Path_round(canvas, iRadius);
       }
       else
       {
           iDrawInclineData_Path_square(canvas);
       }
    }

    private void iDrawInclineData_Bar(Canvas canvas)
    {
        int iLoop ;
        int iPosX, iEndPosX ;
        int iPosY, iEndPosY ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iSingleHeight = iViewHeight/idiff;
        RectF rect;
        float fincline;

        if(Incline_list != null)
        {
            mPaintIncline.setColor(COLOR_INCLINE);
            for (iLoop = 0; iLoop < iXScale + 1; iLoop++) {
                if(iLoop < Incline_list.size()) {
                    fincline = Incline_list.get(iLoop) - iYScale[0];
                    iPosX = iSingleWidth * iLoop;
                    iEndPosX = iPosX + (int)(iSingleWidth * 0.9f) ;
                    iPosY = iViewHeight - (int)(fincline * iSingleHeight);
                    iEndPosY = iViewHeight;

                    rect = new RectF(iPosX , iPosY , iEndPosX , iEndPosY);

                    if(vIncline_effect(iLoop)) {
                        canvas.drawRoundRect(rect, iGet_Scale(3), iGet_Scale(3), mPaintIncline);
                    }
                }
            }
        }
    }

    ////////////////////////////
    private boolean vLevel_effect(int iLoop)
    {
        boolean bret = true;

        if((ieffect & 0x01) == 0x01)
        {
            if(iflash_count < 0)
            {
                mPaintLevel.setColor(COLOR_LEVEL_ALPHA);
            }
            else if (iLoop == iflash_count + 1) {
                mPaintLevel.setColor(COLOR_LEVEL_ALPHA);
            }
        }

        if((ieffect & 0x02) == 0x02)
        {
            if(iflash_count < 0)
            {
            }
            else if (iLoop == iflash_count) {
                if(icount % 8 >= 4) {
                    bret = false;
                }
            }
        }

        return bret;
    }

    private void iDrawLevelData(Canvas canvas)
    {
        int iLoop ;
        int iPosX, iEndPosX ;
        int iPosY, iEndPosY ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleWidth = iViewWidth/(iXScale+1);
        int iSingleHeight = iViewHeight/idiff;
        RectF rect;
        float fincline;
        int iWidthMax, iLoopDraw;

        if(Incline_list != null)
        {
            mPaintLevel.setColor(COLOR_LEVEL);
            if(iSingleWidth > 1)
            {
                for(iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if(iLoop < Incline_list.size())
                    {
                        fincline = Incline_list.get(iLoop) - iYScale[0];
                        iPosX = iSingleWidth * iLoop;
                        iEndPosX = iPosX + (int) (iSingleWidth * 0.9f);
                        iPosY = iViewHeight - (int) (fincline * iSingleHeight);
                        iEndPosY = iViewHeight;

                        rect = new RectF(iPosX, iPosY, iEndPosX, iEndPosY);
                        if(vLevel_effect(iLoop))
                        {
                            //canvas.drawRoundRect(rect, iGet_Scale(3), iGet_Scale(3), mPaintLevel);
                            canvas.drawRect(rect, mPaintLevel);
                        }
                    }
                }
            }
            else
            {
                //當iXScale超過範圍iViewWidth
                iSingleWidth = iSingleWidth + 1;

                iWidthMax = (iViewWidth / (((iXScale * iSingleWidth) + 59) / 60));
                iLoopDraw = 0;

                for(iLoop = 0; iLoop < iXScale + 1; iLoop++)
                {
                    if(iLoop % 60 < iWidthMax)
                    {
                        if(iLoop < Incline_list.size())
                        {
                            fincline = Incline_list.get(iLoop) - iYScale[0];
                            iPosX = iSingleWidth * iLoopDraw;
                            iEndPosX = iPosX + iSingleWidth;
                            iPosY = iViewHeight - (int) (fincline * iSingleHeight);
                            iEndPosY = iViewHeight;

                            rect = new RectF(iPosX, iPosY, iEndPosX, iEndPosY);
                            if(vLevel_effect(iLoopDraw))
                            {
                                //canvas.drawRoundRect(rect, iGet_Scale(3), iGet_Scale(3), mPaintLevel);
                                canvas.drawRect(rect, mPaintLevel);
                            }
                        }
                        iLoopDraw++;
                    }
                }
            }
        }
    }

    protected void Create_Bitmap() {
        b = Bitmap.createBitmap(iViewWidth, iViewHeight, Bitmap.Config.RGB_565);
        b.eraseColor(COLOR_BACKGROUND);
        c = new Canvas(b);

        Create_Bitmap(c);
    }

    protected void Create_Bitmap(Canvas canvas) {
        icount++;
        if(imode == 0) {
            if((iIncline_mode & 0x01) == 0x01) {
                iDrawInclineData_Bar(canvas);
            }
            else if((iIncline_mode & 0x02) == 0x02) {
                iDrawInclineData_Path(canvas);
            }

            iDrawSpeedData(canvas);
        }
        else
        {
            iDrawLevelData(canvas);
        }

        setImageBitmap(b);
    }

}
