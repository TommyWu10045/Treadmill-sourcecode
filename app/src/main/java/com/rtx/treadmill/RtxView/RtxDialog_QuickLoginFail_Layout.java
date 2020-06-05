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

public class RtxDialog_QuickLoginFail_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_Background;
    private RtxView             view_InfoBackground;
    private RtxTextView         textView_Msg1;
    private RtxTextView         textView_Msg2;
    private RtxImageView        imageView_Close;
    public RtxFillerTextView    fillerTextView_OK;

    public RtxDialog_QuickLoginFail_Layout(Context context) {
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
            if(view_Background == null)       { view_Background = new RtxView(mContext);   }
            if(view_InfoBackground == null)   { view_InfoBackground = new RtxView(mContext);   }
            if(textView_Msg1 == null)         { textView_Msg1 = new RtxTextView(mContext);   }
            if(textView_Msg2 == null)         { textView_Msg2 = new RtxTextView(mContext);   }
            if(imageView_Close == null)       { imageView_Close = new RtxImageView(mContext);   }
            if(fillerTextView_OK == null)     { fillerTextView_OK = new RtxFillerTextView(mContext);     }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_Background,0,0,1280,800);
        view_Background.setBackgroundColor(Common.Color.black);
        addViewToLayout(view_InfoBackground,-1,66,1002,667);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxTextViewToLayout(textView_Msg1, R.string.unable_to_connect,46.75f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 57+66, 500, 76+76);//By Alan
        addRtxTextViewToLayout(textView_Msg2, R.string.please_try_again_later,32.71f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 137+66+76, 1280, 65);//By Alan

        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.quick_login_close, -1, 287+66+50, 116, 116, 0);//By Alan
        addRtxTextViewToLayout(fillerTextView_OK, R.string.ok, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 521+66, 380, 75, Common.Color.red_1);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
