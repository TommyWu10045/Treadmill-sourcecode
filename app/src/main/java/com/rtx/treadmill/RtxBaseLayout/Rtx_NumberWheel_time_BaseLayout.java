package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumberWheel;

import java.util.Calendar;

public abstract class Rtx_NumberWheel_time_BaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected ButtonListener mButtonListener;

    protected RtxNumberWheel numberWheel_Hours;
    protected RtxNumberWheel numberWheel_Minutes;
    protected RtxNumberWheel numberWheel_ampm;

    protected RtxImageView        imageView_Next;
    private RtxImageView         imageView_Dot;

    public Rtx_NumberWheel_time_BaseLayout(Context context) {
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
        if(imageView_Dot == null)           {   imageView_Dot = new RtxImageView(mContext); }

        GlobalData.fNumberWheelFontSize = 110.17f;

        if(numberWheel_Hours == null)       {   numberWheel_Hours = new RtxNumberWheel(mContext,null); }
        if(numberWheel_Minutes == null)      {   numberWheel_Minutes = new RtxNumberWheel(mContext,null); }

        GlobalData.fNumberWheelFontSize = 40.17f;
        if(numberWheel_ampm == null)        {   numberWheel_ampm = new RtxNumberWheel(mContext,null);   }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);
        addViewToLayout(numberWheel_Hours,294,278,253,347);
        addRtxImageViewToLayout(imageView_Dot, R.drawable.time_dot_icon, 597, 416, 82, 77);
        addViewToLayout(numberWheel_Minutes,728,278,253,347);
        addViewToLayout(numberWheel_ampm,984,322,133,265);
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
        if(true)
        {
            int ih = 6;
            numberWheel_Hours.setValue(ih);
            numberWheel_Minutes.setValue(0);
            if(ih < 12) {
                numberWheel_ampm.setValue(0);
            }
            else
            {
                numberWheel_ampm.setValue(1);
            }
        }
        else {
            if (cal != null) {
                numberWheel_Hours.setValue(cal.get(Calendar.HOUR) + 1);
                numberWheel_Minutes.setValue(cal.get(Calendar.MINUTE));
                if(Calendar.HOUR > 11)
                {
                    numberWheel_ampm.setValue(cal.get(1));
                }
                else {
                    numberWheel_ampm.setValue(cal.get(0));
                }

            }
        }
    }

    public Calendar getCalendar()
    {
        Calendar cal;
        int ih = numberWheel_Hours.getValue();
        int im = numberWheel_Minutes.getValue();

        String sDate =  Rtx_TranslateValue.sInt2String(ih, 2) + "-" + Rtx_TranslateValue.sInt2String(im, 2) + "-00";
        cal = Rtx_Calendar.cStr2Calendar(sDate,"HH-mm-ss");

        return cal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
