package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.lang.reflect.Method;
import java.util.List;


public class RtxNumKeyboardView_Height extends RtxNumKeyboardView {


    int iHeight = 0;
    float fMetricHeightVal = 0;

    public RtxNumKeyboardView_Height(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RtxNumKeyboardView_Height(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void clear(EditText editText)
    {
        iHeight = 0;
        fMetricHeightVal = 0;
        editText.setText("");
    }

    public float getVal()
    {
        float fVal = 0;

        fVal = fMetricHeightVal;

        return fVal;
    }

    public void setVal(float fVal , EditText editText)
    {
        String sVal = null;

        iHeight = EngSetting.Height.getHeightForInputFormat(fVal);
        Log.e("Jerry","iHeight = " + iHeight);
        fMetricHeightVal = fVal;
        sVal = EngSetting.Height.getString(fVal);

        editText.setText(sVal);
    }

    @Override
    public void setOnKeyboardActionListener(final EditText editText, final int iLen) {
        KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {

                if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                    if (iHeight > 0) {
                        iHeight /= 10;
                    }
                } else {
                    if (String.valueOf(iHeight).length() < iLen) {
                        iHeight *= 10;
                        iHeight += primaryCode - 0x30;
                    }
                }

                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    fMetricHeightVal = iHeight;

                    if(iHeight <= 0)
                    {
                        editText.setText("");
                    }
                    else
                    {
                        editText.setText(Rtx_TranslateValue.sInt2String(iHeight));
                    }
                }
                else
                {
                    int iFt = 0;
                    int iIn = 0;
                    String sVal = null;

                    if(iHeight == 0)
                    {
                        sVal = "";
                        fMetricHeightVal = 0;
                        editText.setText(sVal);
                    }
                    else
                    if(iHeight >= 100)
                    {
                        iFt = iHeight / 100;
                        iIn = iHeight % 100;

                        if(iIn > 11)
                        {
                            if (iHeight > 0)
                            {
                                iHeight /= 10;
                                return;
                            }
                        }
                        else
                        {
                            if(iIn < 10)
                            {
                                iHeight = (iFt * 10) + iIn;
                                sVal = Rtx_TranslateValue.sInt2String(iFt) + "\'" + " " + Rtx_TranslateValue.sInt2String(iIn) + "\"";
                            }
                            else
                            {
                                sVal = Rtx_TranslateValue.sInt2String(iFt) + "\'" + Rtx_TranslateValue.sInt2String(iIn) + "\"";
                            }
                        }
                        fMetricHeightVal = EngSetting.Height.fInch2Cm(iFt,iIn);
                        editText.setText(sVal);
                    }
                    else
                    if(iHeight >= 10)
                    {
                        iFt = iHeight / 10;
                        iIn = iHeight % 10;
                        sVal = Rtx_TranslateValue.sInt2String(iFt) + "\'" + " " + Rtx_TranslateValue.sInt2String(iIn) + "\"";
                        fMetricHeightVal = EngSetting.Height.fInch2Cm(iFt,iIn);
                        editText.setText(sVal);
                    }
                    else
                    {
                        iFt = iHeight;
                        sVal = Rtx_TranslateValue.sInt2String(iFt) + "\'" + "  " + "\"";
                        fMetricHeightVal = EngSetting.Height.fInch2Cm(iFt,0);
                        editText.setText(sVal);
                        if(sVal.length() >= 5) {
                            setTextViewSpan(editText, 4, 5, Common.Color.login_word_green_disable);
                        }
                    }
                }
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };

        this.setOnKeyboardActionListener(mOnKeyboardActionListener);
    }

    public void setTextViewSpan(EditText view, int start, int end, int color)
    {
        Spannable span = new SpannableString(view.getText());
        span.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        view.setText(span);
    }
}
