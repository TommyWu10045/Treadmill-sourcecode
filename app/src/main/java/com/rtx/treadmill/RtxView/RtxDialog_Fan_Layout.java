package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

public class RtxDialog_Fan_Layout extends Rtx_BaseLayout {
    private Context mContext;

    private RtxFillerTextView   fillerTextView_Background;
    public  RtxImageView        imageView_Close;
    private RtxTextView         textView_InfoTitle;
    private RtxTextScrollView   textScrollView_Contents;
    private RtxImageView        imageView_Icon;

    public RtxDialog_Fan_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

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
        {
            if(fillerTextView_Background == null)   { fillerTextView_Background = new RtxFillerTextView(mContext);   }
            if(imageView_Close == null)             { imageView_Close = new RtxImageView(mContext);   }
            if(imageView_Icon == null)              { imageView_Icon = new RtxImageView(mContext);   }
            if(textView_InfoTitle == null)          { textView_InfoTitle = new RtxTextView(mContext);   }
            if(textScrollView_Contents == null)     { textScrollView_Contents = new RtxTextScrollView(mContext);     }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        //旋轉
//        RotateAnimation mRotateAnimation = new RotateAnimation(0.0f,360.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        mRotateAnimation.setDuration(2000);
//        mRotateAnimation.setRepeatCount(-1);
//        mRotateAnimation.setInterpolator(mContext,android.R.anim.linear_interpolator);
//        imageView_Icon.startAnimation(mRotateAnimation);

        addRtxTextViewToLayout(fillerTextView_Background, "", 28f, Common.Color.black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, -1, 557, 557, Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.dialog_close_icon, 1023, 46, 32, 32, 30);
        addRtxImagePaddingViewToLayout(imageView_Icon, -1, -1, -1, 557, 557, 0);
        addRtxTextViewToLayout(textView_InfoTitle, 16f, Common.Color.white, Common.Font.Lato_Black, Typeface.NORMAL, Gravity.CENTER, -1, 800-564-15, 36, 19);
        addRtxTextScrollViewToLayout(textScrollView_Contents, 40f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 800-207-37, 557, 83);
        textScrollView_Contents.textView.setLineSpacing(1.0f,1.5f);

    }

    public void setInfoTitle(int iResId)
    {
        textView_InfoTitle.setText(LanguageData.s_get_string(mContext,iResId));
    }

    public void setInfoContents(int iResId)
    {
        textScrollView_Contents.setText(LanguageData.s_get_string(mContext,iResId));
        textScrollView_Contents.textView.setGravity(Gravity.CENTER);
    }

    public void setInfoContents(String info_tags)
    {
        textScrollView_Contents.set_infolist(info_tags);
    }

    public void setIconResourceId(int iResId)
    {
        imageView_Icon.setImageResource(iResId);

        //填滿
//        imageView_Icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
