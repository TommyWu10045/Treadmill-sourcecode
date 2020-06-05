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

public class RtxDialog_Logout_Layout extends Rtx_BaseLayout{
    private Context mContext;
    private RtxView             view_InfoBackground;
    public RtxImageView         imageView_Close;
    public RtxImageView         imageView_Logo;
    public RtxTextView          textView_Info_Line_1;
    public RtxTextView          textView_Info_Line_2;
    public RtxFillerTextView    fillerTextView_Login;
    public RtxFillerTextView    fillerTextView_SignUp;
    private int icolor_Login = Common.Color.login_button_yellow;
    private int icolor_SignUp = Common.Color.login_button_green;

    public RtxDialog_Logout_Layout(Context context){
        super(context);
        mContext = context;
        init();
        display();
    }

    @Override
    public void init(){
    }

    @Override
    public void display(){
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    private void init_View(){
        {
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
            if(imageView_Close == null)             { imageView_Close = new RtxImageView(mContext);   }
            if(textView_Info_Line_1 == null)        { textView_Info_Line_1 = new RtxTextView(mContext);   }
            if(fillerTextView_Login == null)        { fillerTextView_Login = new RtxFillerTextView(mContext);   }
            if(fillerTextView_SignUp == null)       { fillerTextView_SignUp = new RtxFillerTextView(mContext);   }
        }
    }

    private void init_event(){
    }

    private void add_View(){
        addViewToLayout(view_InfoBackground,0,100,1002,488);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.dialog_close_icon, 899, 50, 32, 32, 30);
        addRtxTextViewToLayout(textView_Info_Line_1, R.string.Logout_confirm, 28f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 197, 165, 604, 61);//By Alan
        addRtxTextViewToLayout(fillerTextView_Login, R.string.log_out, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 312, 300, 378, 75, icolor_Login);//By Alan
        addRtxTextViewToLayout(fillerTextView_SignUp, R.string.cancel, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 312, 430, 378, 75, icolor_SignUp);//By Alan
        imageView_Close.setVisibility(INVISIBLE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

