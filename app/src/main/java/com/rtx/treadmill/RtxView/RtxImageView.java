package com.rtx.treadmill.RtxView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * TODO: document your custom view class.
 */

@SuppressLint("AppCompatCustomView")
public class RtxImageView extends ImageView {

    public RtxImageView(Context context, int imode)
    {
        super(context);
    }

    public RtxImageView(Context context)
    {
        super(context);
        setKeyEffect();
    }

    public RtxImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setKeyEffect();
    }

    public RtxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setKeyEffect();
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
}
