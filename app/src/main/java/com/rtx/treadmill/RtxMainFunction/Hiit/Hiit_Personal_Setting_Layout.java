package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxCircularDuration;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxUpDownButton;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Personal_Setting_Layout extends Rtx_BaseLayout {

    private final int     MODE_NONE = -1;
    private final int     MODE_SPRINT = 0;
    private final int     MODE_RECOVERY = 1;

    private int iMode = MODE_NONE;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private CircleSeekDirectBarListener mCircleSeekDirectBarListener;
    private MainActivity        mMainActivity;

    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;

    private RtxTextView textView_Incline;
    private RtxTextView textView_Speed;
    private RtxTextView textView_Time;
    private RtxTextView textView_Min;
    private RtxTextView textView_TimeVal;

    private RtxImageView imageView_Incline;
    private RtxImageView imageView_Speed;
    private RtxImageView imageView_Time;

    private RtxImageView imageView_Delete;
    private RtxImageView imageView_Confirm;

    private RtxCircularDuration durationSeekBar;

    private RtxUpDownButton mUpDownButton_Incline;
    private RtxUpDownButton mUpDownButton_Speed;

    private UiDataStruct.INTERVAL_STAGE_INFO temp_StageInfo;

    private Hiit_Personal_Main_Layout MemSetNum;

    private int iTimeVal = 0;

    private int iSelectIndex = -1;
    private HiitProc    hiitProc;

    int iCircle_count = 0;
    int ione_circle_val = 12;
    int imin_val = 1;
    int imax_val = 120;
    int idef_val = 1;
    int iUnit = 5;

    private int ipre_val;
    private int cw_dir = 0;

    int icolor_circle = Common.Color.yellow_1;
    int icolor_backcircle = Common.Color.yellow_7;
    int icolor_hide = Common.Color.transparent;

    public Hiit_Personal_Setting_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
        super(context);

        mContext = context;
        this.hiitProc = hiitProc;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }

        if(mCircleSeekDirectBarListener == null)
        {
            mCircleSeekDirectBarListener = new CircleSeekDirectBarListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_Title();
        }

        {
            if(textView_Incline == null)        { textView_Incline = new RtxTextView(mContext); }
            if(textView_Speed == null)          { textView_Speed = new RtxTextView(mContext); }
            if(textView_Time == null)           { textView_Time = new RtxTextView(mContext); }
            if(textView_Min == null)            { textView_Min = new RtxTextView(mContext); }
            if(textView_TimeVal == null)        { textView_TimeVal = new RtxTextView(mContext); }

            if(imageView_Incline == null)       { imageView_Incline = new RtxImageView(mContext); }
            if(imageView_Speed == null)         { imageView_Speed = new RtxImageView(mContext); }
            if(imageView_Time == null)          { imageView_Time = new RtxImageView(mContext); }
            if(imageView_Delete == null)        { imageView_Delete = new RtxImageView(mContext); }
            if(imageView_Confirm == null)       { imageView_Confirm = new RtxImageView(mContext); }

            if(durationSeekBar == null)         { durationSeekBar = new RtxCircularDuration(mContext); }
            mUpDownButton_Incline = new RtxUpDownButton(mContext,15f,0f,0.5f,0f,"");
            //if(mUpDownButton_Incline == null)


            //if(mUpDownButton_Speed == null)
            {
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    mUpDownButton_Speed = new RtxUpDownButton(mContext,20f,0.8f,0.1f,0.8f, EngSetting.Distance.getSpeedUnitLowerString(mContext));
                }
                else
                {
                    mUpDownButton_Speed = new RtxUpDownButton(mContext,12.5f,0.5f,0.1f,0.5f, EngSetting.Distance.getSpeedUnitLowerString(mContext));
                }
            }


            mUpDownButton_Speed.setUnitText(EngSetting.Distance.getSpeedUnitLowerString(mContext));
        }
    }

    private void init_event()
    {
        imageView_Confirm.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(textView_Incline, R.string.incline, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 232-50, 125 - 25, 78 + 100, 21 + 50);
        addRtxTextViewToLayout(textView_Speed  , R.string.speed, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 976-50, 125 - 25, 65 + 100, 21 + 50);
        addRtxTextViewToLayout(textView_Time   , R.string.time, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 616-50, 639 - 25, 49 + 100, 21 + 50);
        addRtxTextViewToLayout(textView_Min    , R.string.sec, 33.05f, Common.Color.gray_2, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 612-50, 246 - 25, 56 + 100, 35 + 50);
        addRtxTextViewToLayout(textView_TimeVal, -1, 93.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 585-100, 155 - 25, 110 + 200, 98 + 50);

        addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.workout_setting_incline, 260, 161, 22, 16, 0);
        addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.workout_setting_speed, 993, 161, 30, 16, 0);
        addRtxImagePaddingViewToLayout(imageView_Time, R.drawable.workout_setting_time, 627, 670, 25, 25, 0);
        addRtxImagePaddingViewToLayout(imageView_Delete, R.drawable.workout_setting_delete, 77, 662, 87, 87, 30);
        addRtxImagePaddingViewToLayout(imageView_Confirm, R.drawable.icon_confirm, 1085, 638, 133, 133, 30);

        addViewToLayout(mUpDownButton_Incline,70,226,392,388);
        addViewToLayout(mUpDownButton_Speed,807,226,392,388);

        vAddDirectSeekerBar();
    }

    private void vAddDirectSeekerBar()
    {
        int ix = 480;
        int iy = 299;
        int iw = 318;
        int ih = 318;

        addViewToLayout(durationSeekBar, ix, iy, iw, ih);

        durationSeekBar.setLockEnabled(false);
        durationSeekBar.setCircleProgressColor(icolor_circle);    //畫start to end 的arc圖
        durationSeekBar.setCenterCircleColor(0xFFFFFFFF);         //畫中心點
        durationSeekBar.setPointerColor(0xFF4EECBE);              //畫指針
        durationSeekBar.setMax(ione_circle_val);
        durationSeekBar.setProgress(idef_val%ione_circle_val, 0);
        durationSeekBar.vRtxCircularDuration_SetPartLen(ione_circle_val);   //設定刻度數量
        iCircle_count = (idef_val) / ione_circle_val;
        v_set_seeker_color(idef_val);

        durationSeekBar.setOnSeekBarChangeListener(new CircleSeekDirectBarListener());

    }

    protected void vSetCircleColor(int iCollor)
    {
        icolor_circle = iCollor;
        icolor_backcircle = iCollor & 0x80FFFFFF;
        durationSeekBar.setCircleProgressColor(icolor_circle);    //畫start to end 的arc圖
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetHiitItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo, int iIndex)
    {
        this.hiitItemInfo = hiitItemInfo;
        iSelectIndex = iIndex;

        if(iIndex == hiitProc.SELECT_INDEX_WARM_UP)
        {
            //一圈600秒 60-600秒（單位60)
            iUnit = 60;
            ione_circle_val = 600/iUnit;
            imax_val = 600/iUnit;
            vAddDirectSeekerBar();

            vSetTitleText(R.string.warm_up);
            vSetCircleColor(Common.Color.yellow_1);
            if(hiitItemInfo.stage_WarmUp == null || hiitItemInfo.stage_WarmUp.iTime <= 0)
            {
                vSetDefaultVal();
            }
            else
            {
                vSetVal(hiitItemInfo.stage_WarmUp.iTime,hiitItemInfo.stage_WarmUp.fGetSpeed(),hiitItemInfo.stage_WarmUp.fGetIncline());
            }
        }
        else if(iIndex == hiitProc.SELECT_INDEX_COOL_DOWN)
        {
            //一圈600秒 60-600秒（單位60)
            iUnit = 60;
            ione_circle_val = 600/iUnit;
            imax_val = 600/iUnit;
            vAddDirectSeekerBar();

            vSetTitleText(R.string.cool_donw);
            vSetCircleColor(Common.Color.blue_1);
            if(hiitItemInfo.stage_CoolDown == null || hiitItemInfo.stage_CoolDown.iTime <= 0)
            {
                vSetDefaultVal();
            }
            else
            {
                vSetVal(hiitItemInfo.stage_CoolDown.iTime,hiitItemInfo.stage_CoolDown.fGetSpeed(),hiitItemInfo.stage_CoolDown.fGetIncline());
            }
        }
        else
        {
            //一圈60秒 10-180秒（單位10)
            iUnit = 10;
            ione_circle_val = 60/iUnit;
            imax_val = 180/iUnit;
            vAddDirectSeekerBar();

            vSetSprintMode();
        }
    }

    private void vSetSprintMode()
    {
        iMode = MODE_SPRINT;

        vSetCircleColor(Common.Color.yellow_1);

        if(iSelectIndex == -1 || (((iSelectIndex + 1) * 2) > hiitItemInfo.list_Stage.size()))
        {
            iSelectIndex = -1;
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.sprint) + " " + Rtx_TranslateValue.sInt2String(hiitItemInfo.list_Stage.size() / 2 + 1));
            vSetDefaultVal();
        }
        else
        {
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.sprint) + " " + Rtx_TranslateValue.sInt2String(iSelectIndex + 1));
            vSetVal(hiitItemInfo.list_Stage.get(iSelectIndex*2).iTime,hiitItemInfo.list_Stage.get(iSelectIndex*2).fGetSpeed(),hiitItemInfo.list_Stage.get(iSelectIndex*2).fGetIncline());
        }
    }

    private void vSetRecoveryMode()
    {
        iMode = MODE_RECOVERY;

        vSetCircleColor(Common.Color.blue_1);

        if(iSelectIndex == -1 || ((iSelectIndex * 2 + 1) > hiitItemInfo.list_Stage.size()))
        {
            iSelectIndex = -1;
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.recovery) + " " + Rtx_TranslateValue.sInt2String(hiitItemInfo.list_Stage.size() / 2 + 1));
            vSetDefaultVal();
        }
        else
        {
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.recovery) + " " + Rtx_TranslateValue.sInt2String(iSelectIndex + 1));
            vSetVal(hiitItemInfo.list_Stage.get(iSelectIndex*2 + 1).iTime,hiitItemInfo.list_Stage.get(iSelectIndex*2 + 1).fGetSpeed(),hiitItemInfo.list_Stage.get(iSelectIndex*2 + 1).fGetIncline());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vButtonClick_Confirm()
    {
        UiDataStruct.INTERVAL_STAGE_INFO info = new  UiDataStruct.INTERVAL_STAGE_INFO();
        info.iTime = iTimeVal;
        info.vSetSpeed(mUpDownButton_Speed.fGetVal());
        info.vSetIncline(mUpDownButton_Incline.fGetVal());


        //MemSetNum.SprintTime=iTimeVal;
        //MemSetNum.SprintSpeed=mUpDownButton_Speed.fGetVal();
        //MemSetNum.SprintIncline=mUpDownButton_Incline.fGetVal();



        //info.fSpeed = mUpDownButton_Speed.fGetVal();
        //info.fIncline = mUpDownButton_Incline.fGetVal();

        if(iSelectIndex == hiitProc.SELECT_INDEX_WARM_UP)
        {
            vSetTitleText(R.string.warm_up);
            hiitItemInfo.stage_WarmUp = info;

            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        }
        else if(iSelectIndex == hiitProc.SELECT_INDEX_COOL_DOWN)
        {
            vSetTitleText(R.string.cool_donw);
            hiitItemInfo.stage_CoolDown = info;

            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        }
        else
        {
            if(iMode == MODE_SPRINT)
            {
                temp_StageInfo = info;
                vSetRecoveryMode();

            }
            else if(iMode == MODE_RECOVERY)
            {
                if(iSelectIndex == -1)
                {
                    hiitItemInfo.list_Stage.add(temp_StageInfo);
                    hiitItemInfo.list_Stage.add(info);
                }
                else
                {
                    hiitItemInfo.list_Stage.set(iSelectIndex * 2 + 0,temp_StageInfo);
                    hiitItemInfo.list_Stage.set(iSelectIndex * 2 + 1,info);
                }

                hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);


            }
        }
    }

    private void vButtonClick_Delete()
    {
        vShowDeleteDialog();
    }

    private void vSetDefaultVal()
    {
        mUpDownButton_Incline.clear();
        mUpDownButton_Speed.clear();
        v_set_seeker_color(idef_val);
        vSetTime(idef_val * iUnit);
    }

    private void vSetVal(int iTime , float fSpeed , float fIncline)
    {
        vSetTime(iTime);
        //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
        if(fSpeed > EngSetting.f_Get_Max_Speed() && (fSpeed - 1) < EngSetting.f_Get_Max_Speed())
        {
            fSpeed = EngSetting.f_Get_Max_Speed();
        }
        if(fSpeed < EngSetting.f_Get_Min_Speed() && (fSpeed + 1) > EngSetting.f_Get_Min_Speed())
        {
            fSpeed = EngSetting.f_Get_Min_Speed();
        }
        mUpDownButton_Speed.vSetVal(fSpeed);
        mUpDownButton_Incline.vSetVal(fIncline);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_Confirm)          { vButtonClick_Confirm(); }
            else if(v == imageView_Delete)      { vButtonClick_Delete(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vSetTime(int iVal)
    {
        ipre_val = iVal / iUnit;
        iCircle_count =  iVal / (ione_circle_val * iUnit);

        durationSeekBar.setProgress(iVal / iUnit);  //jump to onChange

        iTimeVal = iVal;

        vSetTimeValText(Rtx_TranslateValue.sInt2String(iTimeVal));
    }

    private void vSetTimeValText(String sVal)
    {
        textView_TimeVal.setText(sVal);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vShowDeleteDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE,LanguageData.s_get_string(mContext,R.string.delete_interval_description),-1);
        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vDeleteDialog();
                hiitProc.vSetDeleteMode(hiitProc.MODE_DELETE_INTERVAL);
                hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_DIALOG_DELETE);
            }
        });
    }

    private void vDeleteDialog()
    {
        if(iSelectIndex == hiitProc.SELECT_INDEX_WARM_UP)
        {
            hiitItemInfo.stage_WarmUp.clear();
        }
        else if(iSelectIndex == hiitProc.SELECT_INDEX_COOL_DOWN)
        {
            hiitItemInfo.stage_CoolDown.clear();
        }
        else
        {
            if(iSelectIndex == -1)
            {

            }
            else
            {
                hiitItemInfo.list_Stage.remove(iSelectIndex * 2);
                hiitItemInfo.list_Stage.remove(iSelectIndex * 2);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void v_set_seeker_color(int ival)
    {
        if(ival == imax_val)
        {
            durationSeekBar.setCircleProgressPartEnd(true);
            durationSeekBar.setCircleProgressGlowColor(icolor_circle);
        }
        else if(ival >= ione_circle_val)
        {
            durationSeekBar.setCircleProgressPartEnd(false);
            durationSeekBar.setCircleProgressGlowColor(icolor_backcircle);
        }
        else
        {
            durationSeekBar.setCircleProgressPartEnd(false);
            durationSeekBar.setCircleProgressGlowColor(icolor_hide);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CircleSeekDirectBarListener implements RtxCircularDuration.OnCircularSeekBarChangeListener {
        @Override
        public void onProgressChanged(RtxCircularDuration seekBar, int progress, boolean fromUser) {

            // TODO Insert your code here
            int idiff = progress - ipre_val;
            int idiff_max = seekBar.getMax() / 3;
            int ival ;

            if(idiff < -idiff_max)
            {
                cw_dir = 1; //colock dir
            }
            else if(idiff > idiff_max)
            {
                cw_dir = 2; //colock reverse dir
            }

            if(cw_dir == 1) {
                if(progress < ipre_val) {
                    iCircle_count++;
                }
            }
            else if(cw_dir == 2) {
                if(progress > ipre_val) {
                    iCircle_count--;
                }
            }

            if(progress > ione_circle_val)
            {
                //cloud回傳值
                ival = progress%(ione_circle_val) + iCircle_count * ione_circle_val;
            }
            else
            {
                //使用者滑動值
                ival = progress + iCircle_count * ione_circle_val;
            }

            if(ival < imin_val)
            {
                ival = imin_val;
                seekBar.setProgress(ival%ione_circle_val, 0);
                if(iCircle_count < ival/ione_circle_val)
                {
                    iCircle_count = ival/ione_circle_val;
                }
            }
            else if(ival > imax_val)
            {
                ival = imax_val;
                seekBar.setProgress(ival%ione_circle_val, 0);
                if(iCircle_count > ival/ione_circle_val)
                {
                    iCircle_count = ival/ione_circle_val;
                }
            }

            v_set_seeker_color(ival);

            //t_ProgressVal.setText(String.valueOf(ival));
            iTimeVal = ival * iUnit;
            vSetTimeValText(String.valueOf(iTimeVal));

            cw_dir = 0;
            ipre_val = progress;


        }

        @Override
        public void onStopTrackingTouch(RtxCircularDuration seekBar) {

        }

        @Override
        public void onStartTrackingTouch(RtxCircularDuration seekBar) {

            ipre_val = seekBar.getProgress();
            cw_dir = 0;
        }
    }
}
