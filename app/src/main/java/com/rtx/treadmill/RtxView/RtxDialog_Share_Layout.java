package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Share_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public  RtxImageView        imageView_Close;
    private RtxTextView         textView_InfoTitle;
    public RtxImageView        imageView_FB;
    public RtxImageView        imageView_Twitter;
    public RtxImageView        imageView_IG;
    public RtxImageView        imageView_Weibo;

    public RtxDialog_Share_Layout(Context context) {
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
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
            if(imageView_Close == null)             { imageView_Close = new RtxImageView(mContext);   }
            if(textView_InfoTitle == null)          { textView_InfoTitle = new RtxTextView(mContext);   }
            if(imageView_FB == null)                { imageView_FB = new RtxImageView(mContext);   }
            if(imageView_Twitter == null)           { imageView_Twitter = new RtxImageView(mContext);   }
            if(imageView_IG == null)                { imageView_IG = new RtxImageView(mContext);   }
            if(imageView_Weibo == null)             { imageView_Weibo = new RtxImageView(mContext);   }
        }
    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1118,452);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.dialog_close_icon, 1016, 50, 32, 32, 30);
        addRtxTextViewToLayout(textView_InfoTitle, R.string.share , 27.99f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 5, 800, 122);

        addRtxImagePaddingViewToLayout(imageView_FB, R.drawable.share_icon_fb           , 88, 165, 170, 170, 50);
        addRtxImagePaddingViewToLayout(imageView_Twitter, R.drawable.share_icon_twitter , 346, 165, 170, 170, 50);
        addRtxImagePaddingViewToLayout(imageView_IG, R.drawable.share_icon_ig           , 604 , 165, 170, 170, 50);
        addRtxImagePaddingViewToLayout(imageView_Weibo, R.drawable.share_icon_weibo     , 862, 165, 170, 170, 50);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
