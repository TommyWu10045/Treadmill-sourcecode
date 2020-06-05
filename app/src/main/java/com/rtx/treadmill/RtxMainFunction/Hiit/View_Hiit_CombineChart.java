package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxProfileYBase;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class View_Hiit_CombineChart extends Rtx_BaseLayout{
    private Context mContext;
    private int iPosX_Offset = 0;
    public int iAxisX_Offset = 55 - 21;

    private RtxProfileYBase i_yprofile ;
    private View_HIIT_RunChart mView_HIIT_RunChart;
    private View_HIIT_TimeBar mView_HIIT_TimeBar;

    private RtxTextView textView_Speed;
    private RtxTextView textView_Incline;
    private RtxTextView textView_WarmUp;
    private RtxTextView textView_Hiit;
    private RtxTextView textView_CoolDown;
    private RtxTextView textView_WarmUp_Val;
    private RtxTextView textView_Hiit_Val;
    private RtxTextView textView_CoolDown_Val;

    private RtxImageView imageView_Speed;
    private RtxImageView imageView_Incline;

    private float fScaleRate=1.0f;
    private boolean bSummary=false;


    public View_Hiit_CombineChart(Context context) {
        super(context);
        mContext=context;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        iPosX_Offset = 0;
        init();
    }

    public View_Hiit_CombineChart(Context context, float fScale, boolean bSummary){
        super(context);
        mContext=context;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        fScaleRate=fScale;
        this.bSummary=bSummary;
        if(this.bSummary){ iPosX_Offset=20; }
        init();
    }

    public View_Hiit_CombineChart(Context context , float fScale){
        super(context);
        mContext = context;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        fScaleRate = fScale;
        init();
    }

    @Override
    public void init(){
        init_View();
        add_View();
    }

    @Override
    public void display(){

    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    private int getVal(int iVal){
        int iResult=0;
        iResult=(int)(iVal*fScaleRate);
        return iResult;
    }

    /////////////////////////////
    private int iGet_YMin(){
        int ival;
        float fspeed;
        float fincline;
        float flevel;

        fspeed = EngSetting.f_Get_Min_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL){ fspeed *= EngSetting.km2mile; }
        fincline=EngSetting.f_Get_Min_Incline();

        if(fspeed < fincline){ ival=(int) (fspeed-0.9); }
        else{ ival=(int) (fincline-0.9); }
        return ival;
    }

    private int iGet_YMax(){
        int ival;
        float fspeed;
        float fincline;

        fspeed=EngSetting.f_Get_Max_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL){ fspeed *= EngSetting.km2mile; }
        fincline=EngSetting.f_Get_Max_Incline();
        if(fspeed < fincline){ ival=(int) (fincline + 0.9); }
        else{ ival=(int)(fspeed + 0.9); }
        return ival;
    }

    private void init_View(){
        {
            if(i_yprofile == null){ i_yprofile = new RtxProfileYBase(mContext); }
            if(mView_HIIT_RunChart == null){ mView_HIIT_RunChart = new View_HIIT_RunChart(mContext, getVal(983), getVal(350)); }
            if(mView_HIIT_TimeBar == null){ mView_HIIT_TimeBar = new View_HIIT_TimeBar(mContext, getVal(983), getVal(50)); }
        }

        {
            if(textView_Speed == null){ textView_Speed = new RtxTextView(mContext); }
            if(textView_Incline == null){ textView_Incline = new RtxTextView(mContext); }
            if(textView_WarmUp == null){ textView_WarmUp = new RtxTextView(mContext); }
            if(textView_Hiit == null){ textView_Hiit = new RtxTextView(mContext); }
            if(textView_CoolDown == null){ textView_CoolDown = new RtxTextView(mContext); }
            if(textView_WarmUp_Val == null){ textView_WarmUp_Val = new RtxTextView(mContext); }
            if(textView_Hiit_Val == null){ textView_Hiit_Val = new RtxTextView(mContext); }
            if(textView_CoolDown_Val == null){ textView_CoolDown_Val = new RtxTextView(mContext); }
            if(imageView_Speed == null){ imageView_Speed = new RtxImageView(mContext); }
            if(imageView_Incline == null){ imageView_Incline = new RtxImageView(mContext); }
        }
    }

    private void add_View(){
        {
            addViewToLayout(mView_HIIT_RunChart, getVal(109 + iPosX_Offset + iAxisX_Offset), getVal(53), getVal(983), getVal(350));
        }

        if(bSummary){
            addViewToLayout(i_yprofile, getVal(0 + iAxisX_Offset), getVal(45), getVal(79), getVal(350));
            i_yprofile.vSet_mode(0x01);
            i_yprofile.init(1f, iGet_YMin(), iGet_YMax(), getVal(0 + iAxisX_Offset), getVal(53), getVal(79), getVal(350), 3);
            addRtxTextViewToLayout(textView_Speed, R.string.speed_upper, 8f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(28 + iPosX_Offset + iAxisX_Offset), getVal(5), getVal(60), getVal(18));
            addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.hiit_chart_speed, getVal(42 + iPosX_Offset + iAxisX_Offset), getVal(38), getVal(25), getVal(13), getVal(0));
        }else{
            addViewToLayout(i_yprofile, getVal(0 + iAxisX_Offset), getVal(53), getVal(79), getVal(350));
            i_yprofile.vSet_mode(0x01);
            i_yprofile.init(1f, iGet_YMin(), iGet_YMax(), getVal(0 + iAxisX_Offset), getVal(53), getVal(79), getVal(345), 5);
            addRtxTextViewToLayout(textView_Speed, R.string.speed_upper, 10f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(28 + iPosX_Offset + iAxisX_Offset), getVal(5), getVal(46), getVal(18));
            addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.hiit_chart_speed, getVal(42 + iPosX_Offset + iAxisX_Offset), getVal(35), getVal(25), getVal(13), getVal(0));
        }
        {
            addViewToLayout(mView_HIIT_TimeBar, getVal(109 + iPosX_Offset + iAxisX_Offset), getVal(53) + getVal(350) - mView_HIIT_RunChart.getVerticalOffset(), getVal(983), getVal(50));
        }
        {
            if(bSummary) {
                addRtxTextViewToLayout(textView_WarmUp, R.string.warm_up, 15f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, getVal(130 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(200), getVal(41));
                addRtxTextViewToLayout(textView_Hiit, R.string.hiit, 15f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(450 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(300), getVal(41));
                addRtxTextViewToLayout(textView_CoolDown, R.string.cool_donw, 15f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, getVal(1020 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(200), getVal(41));
            }else{
                addRtxTextViewToLayout(textView_WarmUp, R.string.warm_up, 20f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, getVal(120 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(200), getVal(41));
                addRtxTextViewToLayout(textView_Hiit, R.string.hiit, 20f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(450 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(280), getVal(41));
                addRtxTextViewToLayout(textView_CoolDown, R.string.cool_donw, 20f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, getVal(1005 + iAxisX_Offset), getVal(438 + iPosX_Offset), getVal(200), getVal(41));
            }
            addRtxTextViewToLayout(textView_WarmUp_Val, -1, 21.98f, Common.Color.green_7, Common.Font.Lato_Black, Typeface.NORMAL, Gravity.RIGHT | Gravity.CENTER_VERTICAL, getVal(0 + iPosX_Offset + iAxisX_Offset), getVal(53) + getVal(350) - mView_HIIT_RunChart.getVerticalOffset(), getVal(97), getVal(50));
            addRtxTextViewToLayout(textView_Hiit_Val, -1, 21.98f, Common.Color.purple_2, Common.Font.Lato_Black, Typeface.NORMAL, Gravity.CENTER, getVal(505 + iPosX_Offset + iAxisX_Offset), getVal(53) + getVal(350) - mView_HIIT_RunChart.getVerticalOffset(), getVal(204), getVal(50));
            addRtxTextViewToLayout(textView_CoolDown_Val, -1, 21.98f, Common.Color.blue_13, Common.Font.Lato_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, getVal(1100 + iPosX_Offset + iAxisX_Offset), getVal(53) + getVal(350) - mView_HIIT_RunChart.getVerticalOffset(), getVal(90), getVal(50));

            textView_WarmUp_Val.setSingleLine();
            textView_Hiit_Val.setSingleLine();
            textView_CoolDown_Val.setSingleLine();
        }

        {
//            addRtxTextViewToLayout(textView_Incline, R.string.incline_upper, 10f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(60 + iPosX_Offset + iAxisX_Offset), getVal(325), getVal(66), getVal(30));
            if(bSummary) {
                addRtxTextViewToLayout(textView_Incline, R.string.incline_upper, 8f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(21 - 55 + iPosX_Offset + iAxisX_Offset), getVal(492 - 136), getVal(56), getVal(18));
            }else{
                addRtxTextViewToLayout(textView_Incline, R.string.incline_upper, 10f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, getVal(21 - 55 + iPosX_Offset + iAxisX_Offset), getVal(492 - 136), getVal(51), getVal(18));
            }
//            addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.hiit_chart_incline, getVal(85 + iPosX_Offset), getVal(357), getVal(16), getVal(12), getVal(0));
            addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.hiit_chart_incline, getVal(73 - 55 + iPosX_Offset + iAxisX_Offset), getVal(493 - 136), getVal(16), getVal(12), getVal(0));

        }

        //vSetTime(10,70,3800);
    }

    protected void vSetTime(int iWarmUpSec , int iHiitSec , int iCoolDownSec)
    {
        String  sWarmUpTime = "";
        String  sHiitTime = "";
        String  sCoolDownTime = "";

        if(iWarmUpSec > 0)
        {
            sWarmUpTime = Rtx_Calendar.sSec2Str(iWarmUpSec);
        }

        if(iHiitSec > 0)
        {
            sHiitTime = Rtx_Calendar.sSec2Str(iHiitSec);
        }

        if(iCoolDownSec > 0)
        {
            sCoolDownTime = Rtx_Calendar.sSec2Str(iCoolDownSec);
        }

        textView_WarmUp_Val.setText(sWarmUpTime);
        if(sWarmUpTime.equals(""))
        {
            textView_WarmUp.setVisibility(INVISIBLE);
        }
        else
        {
            textView_WarmUp.setVisibility(VISIBLE);
        }

        textView_Hiit_Val.setText(sHiitTime);

        textView_CoolDown_Val.setText(sCoolDownTime);
        if(sCoolDownTime.equals(""))
        {
            textView_CoolDown.setVisibility(INVISIBLE);
        }
        else
        {
            textView_CoolDown.setVisibility(VISIBLE);
        }


        Log.e("Jerry","sWarmUpTime = " + sWarmUpTime);
        Log.e("Jerry","sCoolDownTime = " + sCoolDownTime);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo)
    {
        mView_HIIT_RunChart.setItemInfo(hiitItemInfo);
        mView_HIIT_TimeBar.setItemInfo(hiitItemInfo);

        {
            int iSec_WarmUp = 0;
            int iSec_ColdDown = 0;
            int iSec_SpeedFast = 0;
            int iIndex = 0;
            int iSize = 0;

            iSec_WarmUp = hiitItemInfo.stage_WarmUp.iTime;
            iSec_ColdDown = hiitItemInfo.stage_CoolDown.iTime;

            {
                iSize = hiitItemInfo.list_Stage.size();

                for(iIndex = 0 ; iIndex < iSize ; iIndex ++)
                {
                    iSec_SpeedFast += hiitItemInfo.list_Stage.get(iIndex).iTime;
                }
            }

            vSetTime(iSec_WarmUp,iSec_SpeedFast,iSec_ColdDown);
        }
    }
}
