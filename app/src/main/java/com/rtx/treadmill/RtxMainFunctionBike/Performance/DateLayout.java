package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.NumberScroll.NumberPicker;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_date_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;

/**
 * Created by chasechang on 3/22/17.
 */

public class DateLayout extends Rtx_NumberWheel_date_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity   mMainActivity;

    private RtxTextView         t_month;
    private RtxTextView         t_day;
    private RtxTextView         t_year;

    private boolean             isMaxDaysOfMonth = false;
    
    private DATE_TIMES mDATE_TIMES;
    private Calendar cCurrCal;
    private int icurr_year;
    private int icurr_month;
    private int icurr_day;

    public DateLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        if(t_month == null) {t_month = new RtxTextView(mContext);}
        if(t_day == null) {t_day = new RtxTextView(mContext);}
        if(t_year == null) {t_year = new RtxTextView(mContext);}
        if(mDATE_TIMES == null) {
            mDATE_TIMES = new DATE_TIMES();
            create_DATE_TIMES(mDATE_TIMES);
        }

        setNumberPickerArg(numberWheel_Years, mDATE_TIMES.iyear_min, mDATE_TIMES.iyear_max, mDATE_TIMES.syears, (mDATE_TIMES.iyear_max - mDATE_TIMES.iafter), year_np);
        setNumberPickerArg(numberWheel_Months, mDATE_TIMES.imounth_min, mDATE_TIMES.imounth_max, mDATE_TIMES.smonth, 5, month_np);
        setNumberPickerArg(numberWheel_Days, mDATE_TIMES.iday_min, mDATE_TIMES.iday_max, mDATE_TIMES.sdays, 5, day_np);
        numberWheel_Years.setEnabled(false);
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

        cCurrCal = Rtx_Calendar.cStr2Calendar(Rtx_Calendar.sTodayCalendar2Str("yyyy-MM-dd"), "yyyy-MM-dd");
        setCalendar(cCurrCal);
        icurr_year = cCurrCal.get(Calendar.YEAR);
        icurr_month = cCurrCal.get(Calendar.MONTH) + 1;
        icurr_day = cCurrCal.get(Calendar.DAY_OF_MONTH);

        imageView_Next.setImageResource(R.xml.comfirm_arrow_next);

        vSetTitleText(R.string.select_date);
        vSetSubTitleText(R.string.date);

        sdata = LanguageData.s_get_string(mContext, R.string.month);
        addRtxTextViewToLayout(t_month, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 140,610,298,40);
        sdata = LanguageData.s_get_string(mContext, R.string.day);
        addRtxTextViewToLayout(t_day, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 490,610,298,40);
        sdata = LanguageData.s_get_string(mContext, R.string.year);
        addRtxTextViewToLayout(t_year, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 808,610,298,40);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void vClickButton_Back()
    {
        if(mMainActivity.mMainProcBike.performanceProc.i_Get_type() == 3)
        {
            mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_NAME);
        }
        else
        {
            mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_TYPE_SOURCE);
        }
    }

    @Override
    protected void vClickButton_Confirm()
    {
        Calendar cBirthDay = getCalendar();
        mMainActivity.mMainProcBike.performanceProc.vSet_date(cBirthDay);
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_TIME);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private NumberPicker.OnValueChangeListener year_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            numberWheel_Days.setMaxValue(Rtx_Calendar.getDaysOfMonth(newVal, numberWheel_Months.getValue()));
            if(isMaxDaysOfMonth)
            {
                numberWheel_Days.setValue(numberWheel_Days.getMaxValue());
            }
        }
    };

    private NumberPicker.OnValueChangeListener month_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            numberWheel_Days.setMaxValue(Rtx_Calendar.getDaysOfMonth(numberWheel_Years.getValue(), newVal));
            if(isMaxDaysOfMonth)
            {
                numberWheel_Days.setValue(numberWheel_Days.getMaxValue());
            }
            v_check_date_volid();
        }
    };

    private NumberPicker.OnValueChangeListener day_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            if(newVal == picker.getMaxValue())
            {
                isMaxDaysOfMonth = true ;
            }
            else
            {
                isMaxDaysOfMonth = false ;
            }
            v_check_date_volid();
        }
    };

    private void v_check_date_volid()
    {
        int isel_year = numberWheel_Years.getValue();
        int isel_month = numberWheel_Months.getValue();
        int isel_day = numberWheel_Days.getValue();

        if(isel_month == icurr_month)
        {
            if(isel_day > icurr_day)
            {
                numberWheel_Years.setValue(mDATE_TIMES.iyear_min);
            }
            else
            {
                numberWheel_Years.setValue(mDATE_TIMES.iyear_max);
            }
        }
        else if(isel_month > icurr_month)
        {
            numberWheel_Years.setValue(mDATE_TIMES.iyear_min);
        }
        else
        {
            numberWheel_Years.setValue(mDATE_TIMES.iyear_max);
        }
    }


    private class DATE_TIMES{
        public int iafter =  0 ;
        public  int ibefor = 1 ;

        public  int iyear_min ;
        public  int iyear_max ;
        public  String[] syears ;
        public  int imounth_min = 1 ;
        public  int imounth_max = 12 ;
        public  String[] smonth = Common.MONTH_OF_YEAR ;
        public  int iday_min = 1 ;
        public  int iday_max = 31 ;
        public  String[] sdays ;
        public  int ihour_min = 0 ;
        public  int ihour_max = 23 ;
        public  String[] shours ;
        public  int iminute_min = 0 ;
        public  int iminute_max = 59 ;
        public  String[] sminutes ;
    }

    private void create_DATE_TIMES(DATE_TIMES mdate_times)
    {
        int iafter = mdate_times.iafter;
        int ibefor = mdate_times.ibefor;
        Calendar cd = GlobalData.getInstance();

        mdate_times.iyear_max = cd.get(Calendar.YEAR) + iafter;
        mdate_times.iyear_min = cd.get(Calendar.YEAR) - ibefor ;

        int icount = mdate_times.iyear_max - mdate_times.iyear_min ;
        int idata = mdate_times.iyear_min;

        if(mdate_times.syears == null)
        {
            mdate_times.syears = new String[icount + 1];
            for(int i = 0; i <= icount ; i++)
            {
                mdate_times.syears[i] = String.valueOf(idata);
                idata += 1 ;
            }
        }

        idata = 1;
        if(mdate_times.sdays == null)
        {
            mdate_times.sdays = new String[31];
            for(int i = 0; i < mdate_times.sdays.length ; i++)
            {
                mdate_times.sdays[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }

        idata = 0;
        if(mdate_times.shours == null)
        {
            mdate_times.shours = new String[24];
            for(int i = 0; i < mdate_times.shours.length ; i++)
            {
                mdate_times.shours[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }

        idata = 0;
        if(mdate_times.sminutes == null)
        {
            mdate_times.sminutes = new String[60];
            for(int i = 0; i < mdate_times.sminutes.length ; i++)
            {
                mdate_times.sminutes[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }
    }


}
