package com.rtx.treadmill.Dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.infolist;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.TextJustification;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TitleImageInfoLayoutE extends Rtx_BaseLayout {
    private String TAG = "Jerry=" ;

    private Context mContext;
    private     MainActivity        mMainActivity;

    private ButtonListener mButtonListener;

    private FrameLayout f_back;
    public RtxImageView imageView_Close;
    private RtxTextView textView_InfoTitle;
    public RtxImageView         imageView_Title;
    private RtxScrollView t_Contents;
    private RtxTextView t_info;


    public TitleImageInfoLayoutE(Context context, MainActivity mMainActivity) {
        super(context);

        this.mMainActivity = mMainActivity;
        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.dialog_background);

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
        if(f_back == null)                      { f_back = new FrameLayout(mContext);   }
        if(imageView_Close == null)             { imageView_Close = new RtxImageView(mContext);   }
        if(imageView_Title == null)             { imageView_Title = new RtxImageView(mContext);   }
        if(textView_InfoTitle == null)          { textView_InfoTitle = new RtxTextView(mContext);   }
        if(t_Contents == null)     { t_Contents = new RtxScrollView(mContext);     }
        if(t_info == null)          { t_info = new RtxTextView(mContext);   }
    }

    private void init_event()
    {
        imageView_Close.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        int ipadding = 30;
        String sdata;

        ix = 180 ;
        iy = 60;
        iw = 920 ;
        ih = 525;
        addViewToLayout(f_back,ix, iy, iw, ih);
        f_back.setBackgroundColor(Common.Color.background_dialog);

        ix = 810 ;
        iy = 45;
        iw = 40 ;
        ih = 40;
        addRtxImage(f_back, imageView_Close, R.drawable.dialog_close_icon, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);


        ix = 425 ;
        iy = 73;
        iw = 60 ;
        ih = 60;
        addRtxImage(f_back, imageView_Title, -1, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        ix = 140 ;
        iy += 90;
        iw = 640 ;
        ih = 60;//By Alan
        fsize = 28f;
        sdata = "";
        addRtxTextView(f_back, textView_InfoTitle, sdata,  fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 120;
        ih = 250;
        fsize = 23.3f;
        addViewToLayout(f_back, t_Contents, ix, iy, iw, ih);
        t_Contents.addRtxTextView(t_info, fsize, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.TOP|Gravity.LEFT);
        t_info.setLineSpacing(0f, 1.8f);
        t_info.setLetterSpacing(0.09f);

        v_update_data(iw);

    }

    private void v_update_data(int iw)
    {
        v_update_data(Dialog_UI_Info.i_title01_id, Dialog_UI_Info.s_title01, Dialog_UI_Info.s_info01, Dialog_UI_Info.s_info_tag, Dialog_UI_Info.scaletype);

        //分散對齊,unmask below
        //v_leftright_alignment(t_info, iw);
    }

    private void v_leftright_alignment(RtxTextView tview, int iwidth)
    {
        TextJustification.justify(tview, iwidth);
    }

    private void v_update_data(int ititle, String stitle01, String sinfo01, String info_tag, ImageView.ScaleType scaletype)
    {
        if(ititle != -1) {
            imageView_Title.setImageResource(ititle);
            if(scaletype != null) {
                imageView_Title.setScaleType(scaletype);
            }

        }

        if(stitle01 != null) {
            textView_InfoTitle.setText(stitle01.toUpperCase());
        }

        if(sinfo01 != null) {
            t_info.setText(sinfo01);
        }

        if(info_tag != null) {
            infolist.v_set_info(t_info, info_tag);
        }

    }

    /////////////////////////////////////////////////////////////////////////////
    private void vClickClose()
    {
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
    }

    /////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_Close)      { vClickClose(); }

        }
    }
}
