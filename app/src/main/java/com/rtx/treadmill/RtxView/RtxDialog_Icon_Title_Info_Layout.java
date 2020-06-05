package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Icon_Title_Info_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public  RtxImageView        imageView_Close;
    private RtxTextView         textView_InfoTitle;
    private RtxTextScrollView   textScrollView_Contents;
    private RtxImageView        imageView_Icon;

    public RtxDialog_Icon_Title_Info_Layout(Context context) {
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
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
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
        addViewToLayout(view_InfoBackground,0,0,1115,709);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.dialog_close_icon, 1023, 46, 32, 32, 30);
        addRtxImagePaddingViewToLayout(imageView_Icon, -1, -1, 151-46, 63, 63, 0);
        addRtxTextViewToLayout(textView_InfoTitle, 27.99f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 240-46, 740, 73);
        addRtxTextScrollViewToLayout(textScrollView_Contents, 28.09f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, -1, 380-46, 740, 503);
        textScrollView_Contents.textView.setLineSpacing(1.0f,1.5f);
    }

    public void setInfoTitle(int iResId)
    {
        textView_InfoTitle.setText(LanguageData.s_get_string(mContext,iResId));
    }

    public void setInfoContents(int iResId)
    {
        textScrollView_Contents.setText(LanguageData.s_get_string(mContext,iResId));
    }

    public void setInfoContents(String info_tags)
    {
        textScrollView_Contents.set_infolist(info_tags);
    }

    public void setIconResourceId(int iResId)
    {
        imageView_Icon.setImageResource(iResId);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
