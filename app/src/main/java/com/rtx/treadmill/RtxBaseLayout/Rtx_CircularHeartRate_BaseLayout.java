package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxCircularSeekBar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


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
 * public void setCurrentVal(int iVal)
 * public int  getCurrentVal()
 * public void clear()
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

abstract public class Rtx_CircularHeartRate_BaseLayout extends Rtx_BaseLayout {

    protected int   DEFAULT_VAL = 300;
    protected int   DEFAULT_CIRCLE_MAX = 600;
    protected int   DEFAULT_MAX_VAL = 999999;
    protected int   DEFAULT_MIN_VAL = 100;

    private     Context mContext;

    protected   ButtonListener      mButtonListener;

    private RtxImageView        imageView_Icon;
    private RtxImageView        imageView_Confirm;
    private RtxTextView         textView_ProgressVal;
    private RtxTextView         textView_Unit;
    private RtxCircularSeekBar  circularSeekBar;

    int icolor_circle = Common.Color.yellow_1;
    int icolor_hide = Common.Color.transparent;

    int idef_val = DEFAULT_VAL;
    int ione_circle_val = DEFAULT_CIRCLE_MAX;
    int imax_val = DEFAULT_MAX_VAL;
    int imin_val = DEFAULT_MIN_VAL;
    int icur_val;

    public Rtx_CircularHeartRate_BaseLayout(Context context) {
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
            vSetTitleText(R.string.calories);
        }

        {
            if(imageView_Icon == null)          { imageView_Icon = new RtxImageView(mContext); }
            if(imageView_Confirm == null)       { imageView_Confirm = new RtxImageView(mContext); }
            if(textView_Unit == null)           { textView_Unit = new RtxTextView(mContext); }
            if(textView_ProgressVal == null)    { textView_ProgressVal = new RtxTextView(mContext); }
            if(circularSeekBar == null)         { circularSeekBar = new RtxCircularSeekBar(mContext); }
        }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Confirm.setOnClickListener(mButtonListener);
        textView_ProgressVal.setEnabled(false);
        textView_ProgressVal.setClickable(false);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Icon,R.drawable.input_calories_icon,173,387,58,53);
        addRtxImageViewToLayout(imageView_Confirm,R.drawable.next_big_button_enable,1097,628,133,133);
        addRtxTextViewToLayout(textView_Unit,R.string.kcal,62.46f, Common.Color.blue_1, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 998 - 100, 403 - 50, 109 + 200, 45 + 100);
        addViewToLayout(circularSeekBar, -1, 118, 617, 617);
        init_CircularSeekBar();
        addRtxTextViewToLayout(textView_ProgressVal,140.35f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 168, 517, 517);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void init_CircularSeekBar()
    {
        circularSeekBar.setLockEnabled(true);
        circularSeekBar.setCircleColor(Common.Color.blue_4);
        circularSeekBar.setCircleProgressColor(icolor_circle);
        circularSeekBar.setCircleProgressGlowColor(icolor_hide);
        circularSeekBar.setPointerColor(Common.Color.green_1);
        circularSeekBar.setMax(ione_circle_val);
        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBarListener());
        setCurrentVal(idef_val);
    }

    class CircularSeekBarListener implements RtxCircularSeekBar.OnCircularSeekBarChangeListener {
        @Override
        public void onProgressChanged(RtxCircularSeekBar circularSeekBar, int progress, boolean fromUser) {
            // TODO Insert your code here
            textView_ProgressVal.setText(String.valueOf(progress + imin_val));
            icur_val = progress;
        }

        @Override
        public void onStopTrackingTouch(RtxCircularSeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(RtxCircularSeekBar seekBar) {

        }
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void v_set_title(int resId)
    {
        vSetTitleText(resId);
    }

    public void v_set_icon(int resId)
    {
        if(resId != -1) {
            imageView_Icon.setImageResource(resId);
        }
    }

    public void v_set_unit(String str)
    {
        if(str != null) {
            textView_Unit.setText(str);
        }
    }

    public void v_set_default_val(int imin, int imax, int idef)
    {
        int idiff ;

        if(idef > imax)
        {
            idef_val = imax;
        }
        else if(idef < imin)
        {
            idef_val = imin;
        }
        else
        {
            idef_val = idef;
        }

        idef_val -= imin;
        imax_val = imax;
        imin_val = imin;
        idiff = imax_val - imin_val;

        circularSeekBar.setMax(idiff);

        setCurrentVal(idef_val);

    }

    abstract public void vClickButton_Back();

    abstract public void vClickButton_Confirm();

    protected void onCustomerClick(View v)
    {

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                      { vClickButton_Back(); }
            else if(v == imageView_Confirm)                     { vClickButton_Confirm(); }
            else                                                { onCustomerClick(v); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getCurrentVal()
    {
        return icur_val + imin_val;
    }

    public void setCurrentVal(int iVal)
    {
        circularSeekBar.setProgress(iVal);
        textView_ProgressVal.setText(Rtx_TranslateValue.sInt2String(iVal + imin_val));
        icur_val = iVal;
    }

}

