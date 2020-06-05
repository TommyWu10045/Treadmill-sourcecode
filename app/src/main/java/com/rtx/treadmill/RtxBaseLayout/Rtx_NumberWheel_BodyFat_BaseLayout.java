package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.NumberScroll.NumberPicker;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumberWheel;
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
 * public void  setCurrentVal(float fVal)
 * public float getCurrentVal()
 ************************************************************
 * User Could Override Function:
 *
 * protected void init_CustomerView();
 * protected void init_CustomerEvent();
 * protected void add_CustomerView();
 * protected void onValueChange_Customer(int oldVal, int newVal)
 * protected void onCustomerClick(View v)
 *************************************************************
 *
 */


public class Rtx_NumberWheel_BodyFat_BaseLayout extends Rtx_BaseLayout {

    final float DEFAULT_FONT_SIZE = 140.35f;

    float DEFAULT_SCALE = 0.5f;
    float DEFAULT_MAX_VAL = 50.0f;
    float DEFAULT_MIN_VAL = 2.0f;
    float DEFAULT_VAL = 20.0f;

    private     Context mContext;

    protected   ButtonListener      mButtonListener;

    protected   RtxTextView         textView_Unit;
    private     RtxImageView        imageView_Icon;
    private     RtxImageView        imageView_CircleBackground;
    protected   RtxImageView        imageView_Confirm;
    protected   RtxNumberWheel      numberWheel_Val;

    protected   GlobalData.NumberWheelData  numberWheelData = null;

    public Rtx_NumberWheel_BodyFat_BaseLayout(Context context) {
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
            vSetTitleText(R.string.current_body_fat);
        }

        {
            GlobalData.fNumberWheelFontSize = DEFAULT_FONT_SIZE;
            if(numberWheel_Val == null)     { numberWheel_Val = new RtxNumberWheel(mContext,null); }
            changeDefaultValRange();
            init_NumberWheel();
        }

        {
            if(textView_Unit == null)               { textView_Unit = new RtxTextView(mContext); }
            if(imageView_Icon == null)              { imageView_Icon = new RtxImageView(mContext); }
            if(imageView_CircleBackground == null)  { imageView_CircleBackground = new RtxImageView(mContext); }
            if(imageView_Confirm == null)           { imageView_Confirm = new RtxImageView(mContext); }
        }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Confirm.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Unit,"%",62.46f, Common.Color.blue_1, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 1016 - 20, 400 - 20, 63 + 40, 59 + 40);
        addRtxImageViewToLayout(imageView_Icon,R.drawable.target_input_bodyfat_icon,192,400,57,62);
        addRtxImageViewToLayout(imageView_CircleBackground,R.drawable.input_background_circle,-1,130,612,612);
        addRtxImageViewToLayout(imageView_Confirm,R.drawable.icon_confirm,1097,628,133,133);
        addViewToLayout(numberWheel_Val,-1,232 - 15,348,398 + 30);

        numberWheel_Val.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onValueChange_Customer(oldVal,newVal);
            }
        });
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

    protected void changeDefaultValRange()
    {
        //User Override Can Change value range

        //DEFAULT_SCALE = 0.1f;
        //DEFAULT_MAX_VAL = 50.0f;
        //DEFAULT_MIN_VAL = 1.0f;
        //DEFAULT_VAL = 20.0f;
    }

    protected void onValueChange_Customer(int oldVal, int newVal)
    {

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void init_NumberWheel()
    {
        numberWheelData = new GlobalData.NumberWheelData(DEFAULT_SCALE,DEFAULT_MAX_VAL,DEFAULT_MIN_VAL,DEFAULT_VAL);
        setNumberWheelArgs(numberWheel_Val,numberWheelData);
    }

    private void setNumberWheelArgs(RtxNumberWheel numberWheel , GlobalData.NumberWheelData data)
    {
        if(numberWheel == null) { return; }
        if(data == null)        { return; }

        numberWheel.setMinValue(data.iIndex_Min);
        numberWheel.setMaxValue(data.iIndex_Max);
        numberWheel.setDisplayedValues(data.sArrayVal);
        numberWheel.setFocusable(true);
        numberWheel.setFocusableInTouchMode(true);
        numberWheel.setValue(data.iIndex_Default);
        numberWheel.setDescendantFocusability(RtxNumberWheel.FOCUS_BLOCK_DESCENDANTS);
        GlobalData.fNumberWheelFontSize = -1;
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
            if(v == imageView_BackPrePage)      { vClickButton_Back(); }
            else if(v == imageView_Confirm)     { vClickButton_Confirm(); }
            else                                { onCustomerClick(v); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setCurrentVal(float fVal)
    {
        numberWheel_Val.setValue(numberWheelData.getNearIndex(fVal));
    }

    public float getCurrentVal()
    {
        float fVal = 0;

        fVal = numberWheel_Val.getValue() * numberWheelData.fScale;
        return fVal;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

