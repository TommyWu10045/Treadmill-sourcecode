package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class EmailSentLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;
    private RtxImageView        imageView_Email;
    private RtxTextView         textView_EmailSent;
    protected RtxFillerTextView fillerTextView_OK;


    public EmailSentLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
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
            if(imageView_Email == null)       { imageView_Email = new RtxImageView(mContext);   }
            if(textView_EmailSent == null)    { textView_EmailSent = new RtxTextView(mContext);     }
            if(fillerTextView_OK == null)     { fillerTextView_OK = new RtxFillerTextView(mContext);}
        }
    }

    private void init_event()
    {
        fillerTextView_OK.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Email, R.drawable.email_sent, 452, 113, 375, 375);
        addRtxTextViewToLayout(textView_EmailSent, R.string.email_sent, 34.69f, Common.Color.login_button_green, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 559, 1280, 45);
        addRtxTextViewToLayout(fillerTextView_OK, R.string.ok, 20.77f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 676, 280, 55, Common.Color.login_button_yellow);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickOK()
    {
        mMainActivity.mMainProcBike.loginProc.vMainChangePage(MainState.PROC_HOME);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == fillerTextView_OK)      { vClickOK(); }
        }
    }
}
