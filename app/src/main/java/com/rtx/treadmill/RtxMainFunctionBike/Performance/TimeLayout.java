package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.NumberScroll.NumberPicker;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_time_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;

/**
 * Created by chasechang on 3/22/17.
 */

public class TimeLayout extends Rtx_NumberWheel_time_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity   mMainActivity;

    private RtxTextView         t_hour;
    private RtxTextView         t_minute;

    private DATE_TIMES mDATE_TIMES;
    private Calendar cCurrCal;

    public TimeLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        if(t_hour == null) {t_hour = new RtxTextView(mContext);}
        if(t_minute == null) {t_minute = new RtxTextView(mContext);}

        if(mDATE_TIMES == null) {
            mDATE_TIMES = new DATE_TIMES();
            create_DATE_TIMES(mDATE_TIMES);
        }

        setNumberPickerArg(numberWheel_Hours, mDATE_TIMES.ihour_min, mDATE_TIMES.ihour_max, mDATE_TIMES.shours, 6, hours_np);
        setNumberPickerArg(numberWheel_Minutes, mDATE_TIMES.iminute_min, mDATE_TIMES.iminute_max, mDATE_TIMES.sminutes, 0, minute_np);
        setNumberPickerArg(numberWheel_ampm, 0, 1, mDATE_TIMES.sampm, 0, ampm_np);
        numberWheel_ampm.setEnabled(false);
    }

    @Override
    protected void init_CustomerEvent()
    {

    }

    @Override
    protected void add_CustomerView()
    {
        String sdata;
        float fsize = 30f;

        cCurrCal = Rtx_Calendar.cStr2Calendar(Rtx_Calendar.sTodayCalendar2Str("HH-mm-ss"), "HH-mm-ss");
        setCalendar(cCurrCal);

        imageView_Next.setImageResource(R.xml.comfirm_arrow_next);

        vSetTitleText(R.string.select_time);
        vSetSubTitleText(R.string.time);

        sdata = LanguageData.s_get_string(mContext, R.string.h);
        addRtxTextViewToLayout(t_hour, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 294,650,253,40);
        sdata = LanguageData.s_get_string(mContext, R.string.m);
        addRtxTextViewToLayout(t_minute, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 728,650,253,40);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_DATE);
    }

    @Override
    protected void vClickButton_Confirm()
    {
        Calendar cBirthDay = getCalendar();
        mMainActivity.mMainProcBike.performanceProc.vSet_time(cBirthDay);
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_DURATION);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private NumberPicker.OnValueChangeListener hours_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if(newVal >= 12)
            {
                numberWheel_ampm.setValue(1);
            }
            else
            {
                numberWheel_ampm.setValue(0);
            }
        }
    };

    private NumberPicker.OnValueChangeListener minute_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    };

    private NumberPicker.OnValueChangeListener ampm_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    };

    private class DATE_TIMES{
        public  int ihour_min = 0 ;
        public  int ihour_max = 23 ;
        public  String[] shours ;
        public  int iminute_min = 0 ;
        public  int iminute_max = 59 ;
        public  String[] sminutes ;
        public  String[] sampm = {"AM", "PM"} ;
    }

    private void create_DATE_TIMES(DATE_TIMES mdate_times)
    {
        int iLoop, icount, idata;

        icount = mdate_times.ihour_max - mdate_times.ihour_min ;
        idata = 12;
        if(mdate_times.shours == null)
        {
            mdate_times.shours = new String[icount + 1];
            for(iLoop = 0; iLoop < mdate_times.shours.length ; iLoop++)
            {
                mdate_times.shours[iLoop] = Rtx_TranslateValue.sInt2String(idata, 2);
                if(idata == 12)
                {
                    idata = 0;
                }
                idata += 1 ;
            }
        }

        icount = mdate_times.iminute_max - mdate_times.iminute_min ;
        idata = mdate_times.iminute_min;
        if(mdate_times.sminutes == null)
        {
            mdate_times.sminutes = new String[icount + 1];
            for(iLoop = 0; iLoop < mdate_times.sminutes.length ; iLoop++)
            {
                mdate_times.sminutes[iLoop] = Rtx_TranslateValue.sInt2String(idata, 2);
                idata += 1 ;
            }
        }
    }
}
