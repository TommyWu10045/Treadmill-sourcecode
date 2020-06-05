package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

import android.content.Context;
import android.graphics.Typeface;
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
import com.rtx.treadmill.RtxView.RtxCircularDirectSeekBar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxUpDownButton;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkout_Setting_Layout extends Rtx_BaseLayout {

    private final int     DEF_TIME_VAL_MAX      = 60;
    private final int     DEF_TIME_VAL_MIN      = 1;
    private final int     DEF_TIME_VAL_DEFAULT  = 1;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private CircleSeekDirectBarListener mCircleSeekDirectBarListener;
    private MainActivity        mMainActivity;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    private RtxTextView textView_Incline;
//    private RtxTextView textView_Speed;
    private RtxTextView textView_Time;
    private RtxTextView textView_Min;
    private RtxTextView textView_TimeVal;

    private RtxImageView imageView_Incline;
//    private RtxImageView imageView_Speed;
    private RtxImageView imageView_Time;

    private RtxImageView imageView_Delete;
    private RtxImageView imageView_Confirm;

    private RtxCircularDirectSeekBar directSeekBar;

    private RtxUpDownButton mUpDownButton_Incline;
//    private RtxUpDownButton mUpDownButton_Speed;

    int icolor_circle = 0xFFFFEC00;
    int icolor_hide = 0x00FFEC00;

    private int iTimeVal = 0;

    private int iSelectIndex = -1;

    public MyWorkout_Setting_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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
//            if(textView_Speed == null)          { textView_Speed = new RtxTextView(mContext); }
            if(textView_Time == null)           { textView_Time = new RtxTextView(mContext); }
            if(textView_Min == null)            { textView_Min = new RtxTextView(mContext); }
            if(textView_TimeVal == null)        { textView_TimeVal = new RtxTextView(mContext); }

            if(imageView_Incline == null)       { imageView_Incline = new RtxImageView(mContext); }
//            if(imageView_Speed == null)         { imageView_Speed = new RtxImageView(mContext); }
            if(imageView_Time == null)          { imageView_Time = new RtxImageView(mContext); }
            if(imageView_Delete == null)        { imageView_Delete = new RtxImageView(mContext); }
            if(imageView_Confirm == null)       { imageView_Confirm = new RtxImageView(mContext); }

            if(directSeekBar == null)           { directSeekBar = new RtxCircularDirectSeekBar(mContext); }

            if(mUpDownButton_Incline == null)   { mUpDownButton_Incline = new RtxUpDownButton(mContext,16f,1f,1.0f,1.0f,""); }

//            if(mUpDownButton_Speed == null)
//            {
//                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
//                {
//                    mUpDownButton_Speed = new RtxUpDownButton(mContext,20f,0.8f,0.1f,0.8f, EngSetting.Distance.getSpeedUnitLowerString(mContext));
//                }
//                else
//                {
//                    mUpDownButton_Speed = new RtxUpDownButton(mContext,12.5f,0.5f,0.1f,0.5f, EngSetting.Distance.getSpeedUnitLowerString(mContext));
//                }
//            }
        }
    }

    private void init_event()
    {
        imageView_Confirm.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(textView_Incline, R.string.resistance_level, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 766 - 50,125 - 25,192 + 100,21 + 50);
//        addRtxTextViewToLayout(textView_Speed  , R.string.speed, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 996-50, 125 - 25, 65 + 100, 21 + 50);
        addRtxTextViewToLayout(textView_Time   , R.string.time, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 399-50, 639 - 25, 49 + 100, 21 + 50);
        addRtxTextViewToLayout(textView_Min    , R.string.min, 33.05f, Common.Color.gray_2, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 392-50, 246 - 25, 56 + 100, 35 + 50);
        addRtxTextViewToLayout(textView_TimeVal, -1, 93.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 263-50, 155 - 25, 318 + 100, 98 + 50);

        addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.workout_setting_level, 838, 166, 46, 17, 0);
//        addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.workout_setting_speed, 1013, 161, 30, 16, 0);
        addRtxImagePaddingViewToLayout(imageView_Time, R.drawable.workout_setting_time, 411, 670, 25, 25, 0);
        addRtxImagePaddingViewToLayout(imageView_Delete, R.drawable.workout_setting_delete, 77, 662, 87, 87, 30);
        addRtxImagePaddingViewToLayout(imageView_Confirm, R.drawable.icon_confirm, 1085, 638, 133, 133, 30);

        addViewToLayout(mUpDownButton_Incline,665,226,392,388);
//        addViewToLayout(mUpDownButton_Speed,838,226,392,388);

        vAddDirectSeekerBar();
    }

    private void vAddDirectSeekerBar()
    {
        int iValMax = 60;
        int iValDef = 5;

        int ix = 267;
        int iy = 299;
        int iw = 318;
        int ih = 318;
        directSeekBar.setLockEnabled(true);
        directSeekBar.setCircleProgressGlowColor(icolor_hide);  //畫arc底圖
        directSeekBar.setCircleProgressColor(icolor_circle);    //畫start to end 的arc圖
        directSeekBar.setCenterCircleColor(0xFFFFFFFF);         //畫中心點
        directSeekBar.setPointerColor(0xFF4EECBE);              //畫指針
        directSeekBar.setMax(iValMax);
        directSeekBar.setProgress(iValDef);
        directSeekBar.setOnSeekBarChangeListener(mCircleSeekDirectBarListener);
        addViewToLayout(directSeekBar, ix, iy, iw, ih);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem, int iIndex)
    {
        this.workoutItem = workoutItem;
        iSelectIndex = iIndex;

        if(iSelectIndex == -1)
        {
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.interval) + " " + Rtx_TranslateValue.sInt2String(workoutItem.list_Stage.size() + 1));
            vSetDefaultVal();
        }
        else
        {
            vSetTitleText(LanguageData.s_get_string(mContext,R.string.interval) + " " + Rtx_TranslateValue.sInt2String(iIndex + 1));
            vSetVal(workoutItem.list_Stage.get(iIndex).iTime,0,workoutItem.list_Stage.get(iIndex).fGetIncline());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vButtonClick_Confirm()
    {
//        Log.e("Jerry","mUpDownButton_Incline.fGetVal() = " + mUpDownButton_Incline.fGetVal());
//        Log.e("Jerry","mUpDownButton_Speed.fGetVal() = " + mUpDownButton_Speed.fGetVal());
//        Log.e("Jerry","iTimeVal = " + iTimeVal);

        if(iTimeVal < DEF_TIME_VAL_MIN)
        {
            return;
        }

        if(iSelectIndex == -1)
        {
            UiDataStruct.INTERVAL_STAGE_INFO info = new  UiDataStruct.INTERVAL_STAGE_INFO();
            info.iTime = iTimeVal;
//            info.vSetSpeed(mUpDownButton_Speed.fGetVal());
            info.vSetIncline(mUpDownButton_Incline.fGetVal());
            workoutItem.list_Stage.add(info);
        }
        else
        {
            workoutItem.list_Stage.get(iSelectIndex).iTime = iTimeVal;
//            workoutItem.list_Stage.get(iSelectIndex).vSetSpeed(mUpDownButton_Speed.fGetVal());
            workoutItem.list_Stage.get(iSelectIndex).vSetIncline(mUpDownButton_Incline.fGetVal());
        }

        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
    }

    private void vButtonClick_Delete()
    {
        vShowDeleteDialog();
        //mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
    }

    private void vSetDefaultVal()
    {
        mUpDownButton_Incline.clear();
//        mUpDownButton_Speed.clear();
        vSetTime(DEF_TIME_VAL_DEFAULT);
    }

    private void vSetVal(int iTime , float fSpeed , float fIncline)
    {
        vSetTime(iTime);
//        mUpDownButton_Speed.vSetVal(fSpeed);
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
        directSeekBar.setProgress(iVal);
        vSetTimeVal(iVal);
    }

    protected void vSetTimeVal(int iVal)
    {
        iTimeVal = iVal;
        vSetTimeValText(Rtx_TranslateValue.sInt2String(iTimeVal));
    }

    private void vSetTimeValText(String sVal)
    {
        textView_TimeVal.setText(sVal);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class CircleSeekDirectBarListener implements RtxCircularDirectSeekBar.OnCircularSeekBarChangeListener {
        @Override
        public void onProgressChanged(RtxCircularDirectSeekBar circularSeekBar, int progress, boolean fromUser)
        {
            vSetTimeVal(progress);
        }

        @Override
        public void onStopTrackingTouch(RtxCircularDirectSeekBar seekBar)
        {
            if(iTimeVal < DEF_TIME_VAL_MIN)
            {
                seekBar.setProgress(DEF_TIME_VAL_MIN);
            }
        }

        @Override
        public void onStartTrackingTouch(RtxCircularDirectSeekBar seekBar)
        {

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vShowDeleteDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE,LanguageData.s_get_string(mContext,R.string.delete_interval_description),-1);
        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vDeleteDialog();
                mMainActivity.mMainProcBike.myWorkoutProc.vSetDeleteMode(1);
                mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_DIALOG_DELETE);
            }
        });
    }

    private void vDeleteDialog()
    {
        if(iSelectIndex == -1)
        {

        }
        else
        {
            workoutItem.list_Stage.remove(iSelectIndex);
        }
    }
}
