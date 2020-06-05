package com.rtx.treadmill.Engmode;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.WriteLogUtil;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;

public class EngErrorFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    Context mContext;
    EngModeActivity mEngModeActivity;

    ButtonListener mButtonListener;

    private RtxTextView t_close;
    private RtxTextView t_itemlist;
    private RtxTextScrollView textScrollView_Contents;

    public EngErrorFrame(Context context, EngModeActivity mEngModeActivity) {
        super(context);

        this.mContext = context;
        this.mEngModeActivity = mEngModeActivity;
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
        if(t_close == null)           {t_close = new RtxTextView(mContext);}
        if(t_itemlist == null)           {t_itemlist = new RtxTextView(mContext);}
        if(textScrollView_Contents == null)           {textScrollView_Contents = new RtxTextScrollView(mContext);}
    }

    private void init_event()
    {
        t_close.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int ix;
        int iy;
        int iw;
        int ih;
        float fsize;
        String sdata;

        ix = 50;
        iy = 50;
        iw = 200;
        ih = 60;
        fsize = 40f;
        sdata = "CLOSE";
        addRtxTextViewToLayout(t_close, sdata.toUpperCase(), fsize, Common.Color.engmode_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        iw = 1200;
        fsize = 32f;
        sdata = "\t\t\t\t\t\t\tDATE\t\t\t\t\t\t\tTYPE\t\tCODE\t\t\t\t\t\t\t\tDESCRIPT";
//        fsize = 26f;
//        sdata = "\t\t\t\t\t\t\tDATE\t\t\t\t\t\t\t\t\tTYPE\tCODE\t\t\t\t\t\t\tDESCRIPT";
        addRtxTextViewToLayout(t_itemlist, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        iy += ih;
        ih = 600;
        fsize = 32f;
        addRtxTextScrollViewToLayout(textScrollView_Contents,fsize,Common.Color.white,Common.Font.DroidSansMono,Typeface.NORMAL, Gravity.LEFT | Gravity.TOP, ix, iy, iw, ih);

        Load_Error_file();
    }

    private void Load_Error_file()
    {
        String sdata = "";

        sdata = WriteLogUtil.sRead_Error_file();

        textScrollView_Contents.setText(sdata);
        return ;
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == t_close) {
                if (DEBUG) Log.e(TAG, "==========t_close========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_ABOUT);
            }
        }

    }
}
