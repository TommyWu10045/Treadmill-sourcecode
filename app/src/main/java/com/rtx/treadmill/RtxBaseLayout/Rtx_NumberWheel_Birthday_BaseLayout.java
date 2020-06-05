package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.NumberScroll.NumberPicker;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumberWheel;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;

/**
 *
 ************************************************************
 * Created by chasechang on 3/22/17.
 ************************************************************
 * User Must to Override Function:
 *
 * public void vClickButton_Back()
 * public void vClickButton_Confirm()
 ************************************************************
 * User Could Use Function
 *
 * public void setCalendar(Calendar cal)
 * public Calendar getCalendar()
 ************************************************************
 * User Could Override Function:
 *
 * protected void init_CustomerView();
 * protected void init_CustomerEvent();
 * protected void add_CustomerView();
 * protected void onCustomerClick(View v)
 *************************************************************
 *
 */

public class Rtx_NumberWheel_Birthday_BaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected ButtonListener mButtonListener;

    private RtxNumberWheel numberWheel_Years;
    private RtxNumberWheel numberWheel_Months;
    private RtxNumberWheel numberWheel_Days;

    protected RtxImageView        imageView_Next;
    private RtxTextView         textView_Note;

    private RtxTextView         textView_Month;
    private RtxTextView         textView_Day;
    private RtxTextView         textView_Year;

    private int iDayIndex = 0;

    private boolean             isMaxDaysOfMonth = false;


    public Rtx_NumberWheel_Birthday_BaseLayout(Context context) {
        super(context);

        mContext = context;
        setBackgroundColor(Common.Color.background);
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        init_CustomerView();    //Let user can override this.
        init_Event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.

        setNextButtonEnable(true);
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_BackPrePage();

            init_Title();
            init_SubTitle();
        }

        {
            if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }
            if(textView_Note == null)           {   textView_Note = new RtxTextView(mContext); }
            if(textView_Month == null)          {   textView_Month = new RtxTextView(mContext); }
            if(textView_Day == null)            {   textView_Day = new RtxTextView(mContext); }
            if(textView_Year == null)           {   textView_Year = new RtxTextView(mContext); }

            GlobalData.fNumberWheelFontSize = 87.17f;

            if(numberWheel_Years == null)
            {
                numberWheel_Years = new RtxNumberWheel(mContext,null);
                setNumberPickerArg(numberWheel_Years, GlobalData.DATE_TIMES.iyear_min, GlobalData.DATE_TIMES.iyear_max, GlobalData.DATE_TIMES.syears, (GlobalData.DATE_TIMES.iyear_max - GlobalData.DATE_TIMES.iafter), year_np);
            }

            if(numberWheel_Months == null)
            {
                numberWheel_Months = new RtxNumberWheel(mContext,null);
                setNumberPickerArg(numberWheel_Months, GlobalData.DATE_TIMES.imounth_min, GlobalData.DATE_TIMES.imounth_max, GlobalData.DATE_TIMES.smonth, 5, month_np);
            }

            if(numberWheel_Days == null)
            {
                numberWheel_Days = new RtxNumberWheel(mContext,null);
                setNumberPickerArg(numberWheel_Days, GlobalData.DATE_TIMES.iday_min, GlobalData.DATE_TIMES.iday_max, GlobalData.DATE_TIMES.sdays, 5, day_np);
            }

            //vSetWrapSelectorWheel(false);
        }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Note, R.string.date_of_birth_required, 19.78f, Common.Color.login_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 250, 705, 778, 45);


        addRtxTextViewToLayout(textView_Month, R.string.month, 36f, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 195, 615, 150, 40);
        addRtxTextViewToLayout(textView_Day,   R.string.birthday_day,  36f, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 515, 615, 150, 40);
        addRtxTextViewToLayout(textView_Year,  R.string.year,  36f, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 885, 615, 150, 40);

        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);
        addViewToLayout(numberWheel_Years,808,311,298,284);
        addViewToLayout(numberWheel_Months,124,311,298,284);
        addViewToLayout(numberWheel_Days,441,311,298,284);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    protected void init_CustomerView()
    {
        //Let user can override this.
    }

    protected void init_CustomerEvent()
    {
        //Let user can override this.
    }

    protected void add_CustomerView()
    {
        //Let user can override this.
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setNextButtonEnable(boolean bFlag)
    {
        imageView_Next.setEnabled(bFlag);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private NumberPicker.OnValueChangeListener year_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            int iMaxDays = Rtx_Calendar.getDaysOfMonth(newVal, numberWheel_Months.getValue());
            numberWheel_Days.setMaxValue(iMaxDays);

            if(isMaxDaysOfMonth)
            {
                iDayIndex = iMaxDays;
                numberWheel_Days.setValue(iDayIndex);
            }
        }
    };

    private NumberPicker.OnValueChangeListener month_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            int iMaxDays = Rtx_Calendar.getDaysOfMonth(numberWheel_Years.getValue(), newVal);
            numberWheel_Days.setMaxValue(iMaxDays);

            if(isMaxDaysOfMonth)
            {
                iDayIndex = iMaxDays;
                numberWheel_Days.setValue(iDayIndex);
            }
        }
    };

    private NumberPicker.OnValueChangeListener day_np = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            if(newVal == numberWheel_Days.getMaxValue())
            {
                isMaxDaysOfMonth = true ;
            }
            else
            {
                isMaxDaysOfMonth = false ;
            }
        }
    };


    private void vSetWrapSelectorWheel(boolean bFlag)
    {
        numberWheel_Years.setWrapSelectorWheel(bFlag);
        numberWheel_Months.setWrapSelectorWheel(bFlag);
        numberWheel_Days.setWrapSelectorWheel(bFlag);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vClickButton_Back()
    {

    }

    public void vClickButton_Confirm()
    {

    }

    protected void onCustomerClick(View v)
    {

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickButton_Back(); }
            else if(v == imageView_Next)    { vClickButton_Confirm(); }
            else                            { onCustomerClick(v); }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setCalendar(Calendar cal)
    {
        if(cal == null)
        {
            cal = GlobalData.getInstance();
            cal.add(Calendar.YEAR,-25);
            cal.set(Calendar.DAY_OF_YEAR,1);
        }

        numberWheel_Years.setValue(cal.get(Calendar.YEAR));
        numberWheel_Months.setValue(cal.get(Calendar.MONTH) + 1);
        numberWheel_Days.setValue(cal.get(Calendar.DAY_OF_MONTH));

        iDayIndex = cal.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getCalendar()
    {
        Calendar cal = GlobalData.getInstance();
        String sDate = numberWheel_Years.getValue() + "-" + numberWheel_Months.getValue() + "-" + numberWheel_Days.getValue();
        cal = Rtx_Calendar.cStr2Calendar(sDate,"yyyy-MM-dd");

        return cal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
