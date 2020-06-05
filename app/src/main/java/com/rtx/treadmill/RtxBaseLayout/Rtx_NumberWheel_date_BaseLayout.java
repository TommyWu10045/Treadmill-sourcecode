package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumberWheel;

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

public abstract class Rtx_NumberWheel_date_BaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected ButtonListener mButtonListener;

    protected RtxNumberWheel numberWheel_Years;
    protected RtxNumberWheel numberWheel_Months;
    protected RtxNumberWheel numberWheel_Days;

    protected RtxImageView        imageView_Next;

    public Rtx_NumberWheel_date_BaseLayout(Context context) {
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
        init_BackPrePage();
        init_Title();
        init_SubTitle();

        if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }

        GlobalData.fNumberWheelFontSize = 87.17f;
        if(numberWheel_Years == null)       {   numberWheel_Years = new RtxNumberWheel(mContext,null); }
        if(numberWheel_Months == null)      {   numberWheel_Months = new RtxNumberWheel(mContext,null); }
        if(numberWheel_Days == null)        {   numberWheel_Days = new RtxNumberWheel(mContext,null);   }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {

        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);
        addViewToLayout(numberWheel_Years,808,311,298,284);
        addViewToLayout(numberWheel_Months,140,311,298,284);
        addViewToLayout(numberWheel_Days,490,311,298,284);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setNextButtonEnable(boolean bFlag)
    {
        imageView_Next.setEnabled(bFlag);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

    abstract protected void vClickButton_Back();

    abstract protected void vClickButton_Confirm();

    public void onCustomerClick(View v)
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
        if(cal != null)
        {
            numberWheel_Years.setValue(cal.get(Calendar.YEAR));
            numberWheel_Months.setValue(cal.get(Calendar.MONTH) + 1);
            numberWheel_Days.setValue(cal.get(Calendar.DAY_OF_MONTH));
        }
    }

    public Calendar getCalendar()
    {
        Calendar cal;
        String sDate = numberWheel_Years.getValue() + "-" + numberWheel_Months.getValue() + "-" + numberWheel_Days.getValue();
        cal = Rtx_Calendar.cStr2Calendar(sDate,"yyyy-MM-dd");

        return cal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
