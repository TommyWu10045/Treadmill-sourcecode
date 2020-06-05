package com.rtx.treadmill.TutorialView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxTutorialBaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxImageView    imageView_Background;
    protected RtxTextView   textView_Str;

    public RtxTutorialBaseLayout(Context context) {
        super(context);

        mContext = context;

        init();
    }

    @Override
    public void init()
    {
        init_View();
        add_View();
    }

    @Override
    public void display()
    {

    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            if(imageView_Background == null)    { imageView_Background = new RtxImageView(mContext);   }
            if(textView_Str == null)            { textView_Str = new RtxTextView(mContext);   }
        }
    }

    private void add_View()
    {
        addRtxImagePaddingViewToLayout(imageView_Background, -1, 0, 0, 1280, 800, 0);
    }

    protected void vSetBackgroundImage(int iResId)
    {
        imageView_Background.setImageResource(iResId);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
