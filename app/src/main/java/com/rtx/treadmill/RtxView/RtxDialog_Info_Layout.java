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

public class RtxDialog_Info_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxImageView         imageView_Close;
    private RtxTextScrollView   textScrollView_Contents;

    public RtxDialog_Info_Layout(Context context) {
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
            if(textScrollView_Contents == null)     { textScrollView_Contents = new RtxTextScrollView(mContext);     }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,583);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.dialog_close_icon, 899, 50, 32, 32, 30);
        addRtxTextScrollViewToLayout(textScrollView_Contents, 28.1f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 144, 136, 694, 340);
    }

    public void setInfoContents(int iResId)
    {
        textScrollView_Contents.setText(LanguageData.s_get_string(mContext,iResId));
    }

    public void setInfoContents(String info_tags)
    {
        textScrollView_Contents.set_infolist(info_tags);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
