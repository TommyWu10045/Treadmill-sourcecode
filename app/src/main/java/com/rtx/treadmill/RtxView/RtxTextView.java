package com.rtx.treadmill.RtxView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.rtx.treadmill.R;


/**
 * TODO: document your custom view class.
 */

@SuppressLint("AppCompatCustomView")
public class RtxTextView extends TextView {

    public RtxTextView(Context context)
    {
        super(context);
        setKeyEffect();
    }

    public RtxTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setKeyEffect();
    }

    public RtxTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setKeyEffect();
    }

    public RtxTextView(Context context , boolean bEffect)
    {
        super(context);

        if(bEffect == true) {
            setKeyEffect();
        }
    }

    private void setKeyEffect()
    {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean bFlag = false;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:

                        //=====Write down your Finger Pressed code here

                        if(!isClickable())
                        {
                            bFlag = true;
                        }
                        else
                        {
                            v.setAlpha(0.5f);
                        }

                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:

                        //=====Write down you code Finger Released code here

                        v.setAlpha(1f);

                        if(!isClickable())
                        {
                            bFlag = true;
                        }

                        break;
                }

                return bFlag;
            }
        });
    }

    public void vSetIconWithText(Context mContext, String str, int iStartPoint, int iResId)
    {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ImageSpan(mContext, iResId), iStartPoint, iStartPoint+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        setText(ssb, TextView.BufferType.SPANNABLE);
    }
}
