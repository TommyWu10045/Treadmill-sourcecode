package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumKeyboardView_Height;
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
 * public void  clearNumKeyboard()
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

public class Rtx_NumKeyboard_Height_BaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected ButtonListener mButtonListener;

    private RtxNumKeyboardView_Height mNumKeyboard;
    protected RtxImageView        imageView_Next;
    private RtxTextView         textView_Note;
    private RtxFillerTextView   background_Metric;
    private RtxTextView         background_Imperial;
    private RtxTextView         textView_Unit;
    private RtxEditText         editText_Val;
    private RtxTextView         textView_ErrorMsg;

    public Rtx_NumKeyboard_Height_BaseLayout(Context context) {
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
        setNextButtonEnable(false);
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
            init_SubTitle();
        }

        {
            if(mNumKeyboard == null)            {   mNumKeyboard = new RtxNumKeyboardView_Height(mContext , null); }
            if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }
            if(textView_Unit == null)           {   textView_Unit = new RtxTextView(mContext); }
            if(textView_Note == null)           {   textView_Note = new RtxTextView(mContext); }
            if(editText_Val == null)            {   editText_Val = new RtxEditText(mContext); }
            if(textView_ErrorMsg == null)       {   textView_ErrorMsg = new RtxTextView(mContext); }

            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC){
                if(background_Metric == null)       {   background_Metric = new RtxFillerTextView(mContext); }
            }
            else{
                if(background_Imperial == null)     {   background_Imperial = new RtxTextView(mContext); }
            }
        }
    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        {
            int iX,iY,iH,iW;

            if (EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                background_Metric.setMode(1);
                iX = 676;
                iY = 183;
                iW = 451;
                iH = 451;
                addRtxTextViewToLayout(background_Metric, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iX, iY, iW, iH, Common.Color.blue_2);
            } else {
                iX = 577;
                iY = 209;
                iW = 643;
                iH = 398;
                addRtxTextViewToLayout(background_Imperial, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iX, iY, iW, iH, Common.Color.blue_2);
            }

            setEditText(editText_Val,Common.Font.Relay_Black,195.68f,Common.Color.login_word_green,Common.Color.login_word_pink);
            addViewToLayout(editText_Val, 676,279,451,208);
        }

        {
            addViewToLayout(mNumKeyboard, 195 - 40, 200, 300 + 40 + 40, 400);
            mNumKeyboard.setKeyMode(R.xml.view_keyboard_mode1);
            mNumKeyboard.hideSystemSofeKeyboard(mContext, editText_Val);
            editText_Val.setEnabled(false);

            mNumKeyboard.setOnKeyboardActionListener(editText_Val, 3);
        }

        addRtxTextViewToLayout(textView_Unit, GlobalData.getHeightUnit(), 62.47f, Common.Color.login_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 843, 493, 135, 65);
        addRtxTextViewToLayout(textView_Note, R.string.height_required, 19.78f, Common.Color.login_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 250, 705, 778, 38);
        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);
        addRtxTextViewToLayout(textView_ErrorMsg, R.string.height_metric_error_msg, 33.33f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, 570, 194, 678, 50);
        textView_ErrorMsg.setVisibility(INVISIBLE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private void setEditText(final RtxEditText editText , String sFontPath , float fFontSize , final int iColor , final int iWarningColor)
    {
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(iColor);
        editText.setSingleLine(true);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Common.Color.transparent);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), sFontPath);
        editText.setTypeface(tf);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,fFontSize);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                setWarning(false,editText_Val,textView_ErrorMsg);

                if(mNumKeyboard.getVal() > 0)
                {
                    setNextButtonEnable(true);
                }
                else
                {
                    setNextButtonEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setNextButtonEnable(boolean bFlag)
    {
        imageView_Next.setEnabled(bFlag);
    }

    private void setWarning(boolean bFlag , RtxEditText editText , TextView textView)
    {
        if(bFlag)
        {
            textView.setVisibility(VISIBLE);
            editText.setTextColor(Common.Color.warning);
        }
        else
        {
            textView.setVisibility(INVISIBLE);
            editText.setTextColor(Common.Color.login_word_green);
        }
    }

    private boolean checkValAndSetWarningMsg()
    {
        boolean bResult = true;

        float fVal = mNumKeyboard.getVal();

        if(!EngSetting.Height.checkLimit(fVal))
        {
            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
            {
                textView_ErrorMsg.setText(LanguageData.s_get_string(mContext,R.string.height_metric_error_msg));
            }
            else
            {
                textView_ErrorMsg.setText(LanguageData.s_get_string(mContext,R.string.height_imperial_error_msg));
            }
            setWarning(true,editText_Val,textView_ErrorMsg);

            bResult = false;
        }
        else
        {
            setWarning(false,editText_Val,textView_ErrorMsg);
        }

        return bResult;
    }

    private void vClickButton_Confirm_Check()
    {
        if(checkValAndSetWarningMsg())
        {
            vClickButton_Confirm();
        }
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
            else if(v == imageView_Next)    { vClickButton_Confirm_Check(); }
            else                            { onCustomerClick(v); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void clearNumKeyboard()
    {
        mNumKeyboard.clear(editText_Val);
    }

    public void setCurrentVal_Metric(float fVal)
    {
        mNumKeyboard.setVal(fVal,editText_Val);
    }

    public float getCurrentVal_Metric()
    {
        float fVal = 0;

        if(mNumKeyboard != null)
        {
            fVal = mNumKeyboard.getVal();
        }

        return fVal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
