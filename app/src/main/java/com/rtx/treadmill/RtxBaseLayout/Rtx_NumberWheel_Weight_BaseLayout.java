package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
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
 * public void  setCurrentVal_Metric(float fVal)
 * public float getCurrentVal_Metric()
 ************************************************************
 * User Could Override Function:
 *
 * protected void init_CustomerView();
 * protected void init_CustomerEvent();
 * protected void add_CustomerView();
 * protected void changeDefaultValRange()
 * protected void onCustomerClick(View v)
 *************************************************************
 *
 */


public class Rtx_NumberWheel_Weight_BaseLayout extends Rtx_BaseLayout {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    final float DEFAULT_FONT_SIZE = 140.35f;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    protected float DEFAULT_METRIC_SCALE = 0.5f;
    protected float DEFAULT_METRIC_MAX_VAL = 220.0f;
    //20190108 Target/Body 規格 35 變更為 34
    protected float DEFAULT_METRIC_MIN_VAL = 34.0f;
    protected float DEFAULT_METRIC_VAL = 80.0f;

    protected float DEFAULT_IMPERIAL_SCALE = 0.1f;
    //20181221 規格 484 變更為 485
    protected float DEFAULT_IMPERIAL_MAX_VAL = 485.0f;
    //20190108 Target/Body 規格 77 變更為 75
    protected float DEFAULT_IMPERIAL_MIN_VAL = 75.0f;
    protected float DEFAULT_IMPERIAL_VAL = 176.0f;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private     Context mContext;

    protected     ButtonListener      mButtonListener;

    protected   RtxTextView         textView_Unit;
    private     RtxImageView        imageView_Icon;
    private     RtxImageView        imageView_CircleBackground;
    protected   RtxImageView        imageView_Confirm;
    private     RtxNumberWheel      numberWheel_Val;

    private     float fStartVal = 0f;

    protected   GlobalData.NumberWheelData  numberWheelData = null;

    protected     float fMaxVal       ;
    protected     float fMinVal       ;
    protected     float fScale        ;
    protected     float fDefaultVal   ;


    public Rtx_NumberWheel_Weight_BaseLayout(Context context) {
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
        changeDefaultValRange();
        init_Data();
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

    private void init_Data()
    {
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            fScale = DEFAULT_METRIC_SCALE;
            fMaxVal = DEFAULT_METRIC_MAX_VAL;
            fMinVal = DEFAULT_METRIC_MIN_VAL;
            fDefaultVal = DEFAULT_METRIC_VAL;
        }
        else
        {
            fScale = DEFAULT_IMPERIAL_SCALE;
            fMaxVal = DEFAULT_IMPERIAL_MAX_VAL;
            fMinVal = DEFAULT_IMPERIAL_MIN_VAL;
            fDefaultVal = DEFAULT_IMPERIAL_VAL;
        }
    }

    private void init_View()
    {
        {
            init_BackPrePage();
            init_Title();
        }

        {
            GlobalData.fNumberWheelFontSize = DEFAULT_FONT_SIZE;
//            if(numberWheel_Val == null)
//            {
                numberWheel_Val = new RtxNumberWheel(mContext,null);
//            }
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
        addRtxTextViewToLayout(textView_Unit,EngSetting.Weight.getUnitString(mContext),62.46f, Common.Color.blue_1, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 1016 - 20, 400 - 20, 63 + 40, 59 + 40);
        textView_Unit.setText(EngSetting.Weight.getUnitString(mContext));

        addRtxImageViewToLayout(imageView_Icon,R.drawable.target_input_weight_icon,195,405,51,57);
        addRtxImageViewToLayout(imageView_CircleBackground,R.drawable.input_background_circle,-1,130,612,612);
        addRtxImageViewToLayout(imageView_Confirm,R.drawable.icon_confirm,1097,628,133,133);
        addViewToLayout(numberWheel_Val, -1 , 232 - 15 , 348+100 , 398 + 30);

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

        //DEFAULT_METRIC_SCALE = 0.1f;
        //DEFAULT_METRIC_MAX_VAL = 220.0f;
        //DEFAULT_METRIC_MIN_VAL = 35.0f;
        //DEFAULT_METRIC_VAL = 80.0f;
        //
        //
        //DEFAULT_IMPERIAL_SCALE = 0.1f;
        //DEFAULT_IMPERIAL_MAX_VAL = 484.0f;
        //DEFAULT_IMPERIAL_MIN_VAL = 77.0f;
        //DEFAULT_IMPERIAL_VAL = 176.0f;
    }

    protected void onValueChange_Customer(int oldVal, int newVal)
    {

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void init_NumberWheel()
    {
        fDefaultVal = fDefaultVal + (fScale / 2);
        numberWheelData = new GlobalData.NumberWheelData(fScale,fMaxVal,fMinVal,fDefaultVal);
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
    public void v_set_netx_icon(int resId)
    {
        if(resId != -1) {
            imageView_Confirm.setImageResource(resId);
        }
    }

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
    public float fGetStartVal()
    {
        return fStartVal;
    }

    public void setCurrentVal_Metric(float fVal)
    {
        float fCurrentWeight = 0;

        fCurrentWeight = EngSetting.Weight.getVal(fVal);
        numberWheel_Val.setValue(numberWheelData.getNearIndex(fCurrentWeight));

        fStartVal = numberWheelData.getNearIndex(fCurrentWeight) * numberWheelData.fScale;
    }

    public float getCurrentVal_Metric()
    {
        float fVal = 0;

        fVal = EngSetting.Weight.fTranslat2KG(numberWheel_Val.getValue() * numberWheelData.fScale);
        return fVal;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

