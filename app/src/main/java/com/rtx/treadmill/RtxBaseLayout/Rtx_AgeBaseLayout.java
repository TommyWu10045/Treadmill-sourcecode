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
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumKeyboardView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class Rtx_AgeBaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private RtxNumKeyboardView  mNumKeyboard;
    private RtxImageView        imageView_ageback;
    private RtxImageView        imageView_Next;
    private RtxTextView         textView_Unit;
    private RtxEditText         editText_Val;
    private RtxTextView         textView_ErrorMsg;

    private float fVal ;

    public Rtx_AgeBaseLayout(Context context, MainActivity mMainActivity) {
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

        init_BackPrePage();

        init_Title();

        if(mNumKeyboard == null)            {   mNumKeyboard = new RtxNumKeyboardView(mContext , null); }
        if(imageView_ageback == null)       {   imageView_ageback = new RtxImageView(mContext); }
        if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }
        if(textView_Unit == null)           {   textView_Unit = new RtxTextView(mContext); }
        if(editText_Val == null)            {   editText_Val = new RtxEditText(mContext); }
        if(textView_ErrorMsg == null)       {   textView_ErrorMsg = new RtxTextView(mContext); }

        add_init_View();
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
        add_init_event();
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        String sdata;
        float fval;

        v_set_title(R.string.age);
        addRtxImageViewToLayout(imageView_ageback, R.drawable.circle_gray01, 678, 180, 450, 450);

        setEditText(editText_Val,Common.Font.Relay_Black,195.68f,Common.Color.login_word_green,Common.Color.login_word_pink);
        addViewToLayout(editText_Val, 678,279,450,208);
        //sdata = EngSetting.Age.getDef(mContext) ;
        fval = EngSetting.Age.getDef(mContext);
        sdata = Rtx_TranslateValue.sFloat2String(fval, 0);
        //editText_Val.setText(sdata);
        editText_Val.setText("");
        editText_Val.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        addViewToLayout(mNumKeyboard, 195 - 40, 200, 300 + 40 + 40, 400);
        mNumKeyboard.setKeyMode(R.xml.view_keyboard_mode1);
        mNumKeyboard.hideSystemSofeKeyboard(mContext, editText_Val);
        editText_Val.setEnabled(false);
        mNumKeyboard.setOnKeyboardActionListener(editText_Val, 2);

        ix = 560 ;
        iy = 205 ;
        iw = 720 ;
        ih = 40 ;
        sdata = LanguageData.s_get_string(mContext, R.string.age_range_error_msg) + " " + EngSetting.Age.getRangeString(mContext) ;
        addRtxTextViewToLayout(textView_ErrorMsg, sdata, 33.33f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 678 ;
        iy = 510 ;
        iw = 450 ;
        ih = 100 ;
        sdata = LanguageData.s_get_string(mContext, R.string.years);
        addRtxTextViewToLayout(textView_Unit, sdata.toLowerCase(), 62.47f, Common.Color.login_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);

        setWarning(false,editText_Val,textView_ErrorMsg);
        checkInputState();

        add_add_View();
    }

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
                checkInputState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkInputState()
    {
        if(editText_Val.getText().toString().compareTo("") == 0)
        {
            setNextButtonEnable(false);
        }
        else {
            setNextButtonEnable(true);
        }
    }

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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void v_set_title(int istr_id)
    {
        String sdata;

        sdata = LanguageData.s_get_string(mContext, istr_id);
        vSetTitleText(sdata.toUpperCase());
    }

    public void v_set_default_val(float fval)
    {
        String sdata = String.format("%d", EngSetting.Age.getDef(mContext));
        try {
            sdata = String.format("%.00f", fval);
        }
        catch (Exception e)
        {

        }
        finally {
            editText_Val.setText(sdata);
        }
    }

    public float f_get_input_val()
    {
        return fVal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //overide area
    public void add_init_View()
    {
    }

    public void add_init_event()
    {
    }

    public void add_add_View()
    {
    }

    public void set_next_state()
    {
    }

    public void set_back_state()
    {
    }

    public void on_click(View v)
    {
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        set_back_state();
    }

    private void vClickNext()
    {
        String sdata = editText_Val.getText().toString();
        fVal = Rtx_TranslateValue.fString2Float(sdata);

        if(!EngSetting.Age.checkLimit(fVal))
        {
            setWarning(true,editText_Val,textView_ErrorMsg);
        }
        else
        {
            set_next_state();
            setWarning(false,editText_Val,textView_ErrorMsg);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == imageView_Next)    { vClickNext(); }
            else {
                on_click(v);
            }
        }
    }
}
