package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxCircularDuration;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class Rtx_TimeBaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private RtxCircularDuration mProgress;
    private RtxImageView        imageView_timeback;
    private RtxImageView        imageView_Next;
    private RtxTextView         textView_Unit;
    private RtxTextView         textView_ErrorMsg;
    private RtxTextView         t_ProgressVal;

    private float fVal;

    int iCircle_count = 0;
    int ione_circle_val = 60;
    int imin_val = 16;
    int imax_val = 99;
    int idef_val = 30;

    private int ipre_val;
    private int cw_dir = 0;

    int icolor_circle = Common.Color.yellow_1;
    int icolor_backcircle = Common.Color.yellow_7;
    int icolor_hide = Common.Color.transparent;

    public Rtx_TimeBaseLayout(Context context, MainActivity mMainActivity) {
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
        iCircle_count = 0;

        init_BackPrePage();

        init_Title();

        if(mProgress == null)               {   mProgress = new RtxCircularDuration(mContext ); }
        if(imageView_timeback == null)      {   imageView_timeback = new RtxImageView(mContext); }
        if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }
        if(textView_Unit == null)           {   textView_Unit = new RtxTextView(mContext); }
        if(textView_ErrorMsg == null)       {   textView_ErrorMsg = new RtxTextView(mContext); }
        if(t_ProgressVal == null)           {   t_ProgressVal = new RtxTextView(mContext); }

        add_init_View();
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);

        add_init_event();
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        String sdata;

        v_set_title(R.string.duration);
        addRtxImageViewToLayout(imageView_timeback, R.drawable.circle_gray01, 678, 180, 450, 450);

        sdata = Rtx_TranslateValue.sInt2String(idef_val) ;
        t_ProgressVal.setText(sdata);
        addRtxTextViewToLayout(t_ProgressVal, sdata, 195.68f, Common.Color.yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 676,279,451,208);

        ix = 135;
        iy = 192;
        iw = 460;
        ih = 460;
        addViewToLayout(mProgress, ix, iy, iw, ih);
        init_CircularSeekBar();

        ix = 560 ;
        iw = 720 ;
        iy = 510 ;
        ih = 100 ;
        sdata = LanguageData.s_get_string(mContext, R.string.min);
        addRtxTextViewToLayout(textView_Unit, sdata.toLowerCase(), 62.47f, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_arrow_next, 1101, 631, 134, 134);

        add_add_View();
    }

    private void init_CircularSeekBar()
    {
        mProgress.setLockEnabled(false);
        mProgress.setCircleProgressColor(icolor_circle);    //畫start to end 的arc圖
        mProgress.setCenterCircleColor(0xFFFFFFFF);         //畫中心點
        mProgress.setPointerColor(0xFF4EECBE);              //畫指針
        mProgress.setMax(ione_circle_val);
        mProgress.setProgress(idef_val%ione_circle_val, 0);
        iCircle_count = (idef_val) / ione_circle_val;
        v_set_seeker_color(idef_val);

        mProgress.setOnSeekBarChangeListener(new CircleSeekDirectBarListener());

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void v_set_title(int istr_id)
    {
        String sdata;

        sdata = LanguageData.s_get_string(mContext, istr_id);
        vSetTitleText(sdata.toUpperCase());
    }

    public void v_set_default_val(int imin, int imax, int idef, int ione_circle_max)
    {
        imin_val = imin;
        imax_val = imax;
        if(idef > imax_val)
        {
            idef_val = imax_val;
        }
        else if(idef < imin_val)
        {
            idef_val = imin_val;
        }
        else
        {
            idef_val = idef;
        }
        ione_circle_val = ione_circle_max;

        mProgress.setMax(ione_circle_val);

        iCircle_count = (idef_val) / ione_circle_val;
        v_set_seeker_color(idef_val);
        setCurrentVal(idef_val);

    }


    public void setCurrentVal(int iVal)
    {
        mProgress.setProgress(iVal%ione_circle_val, 0);
        t_ProgressVal.setText(Rtx_TranslateValue.sInt2String(iVal));
    }

    public float f_get_input_val()
    {
        return fVal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //overide area
    public void add_init_View()
    {
    }

    public void add_init_event()
    {
    }

    public void add_add_View()
    {
    }

    public void set_next_state()
    {
    }

    public void set_back_state()
    {
    }

    public void on_click(View v)
    {
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        set_back_state();
    }

    private void vClickNext()
    {
        String sdata = t_ProgressVal.getText().toString();
        fVal = Rtx_TranslateValue.fString2Float(sdata);

        set_next_state();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CircleSeekDirectBarListener implements RtxCircularDuration.OnCircularSeekBarChangeListener {
        @Override
        public void onProgressChanged(RtxCircularDuration seekBar, int progress, boolean fromUser) {
            // TODO Insert your code here
            int idiff = progress - ipre_val;
            int idiff_max = seekBar.getMax() / 3;
            int ival ;

            if(idiff < -idiff_max)
            {
                cw_dir = 1; //colock dir
            }
            else if(idiff > idiff_max)
            {
                cw_dir = 2; //colock reverse dir
            }

            if(cw_dir == 1) {
                if(progress < ipre_val) {
                    iCircle_count++;
                }
            }
            else if(cw_dir == 2) {
                if(progress > ipre_val) {
                    iCircle_count--;
                }
            }

            ival = progress + iCircle_count * ione_circle_val;

            if(ival < imin_val)
            {
                ival = imin_val;
                seekBar.setProgress(ival%ione_circle_val, 0);
                if(iCircle_count < ival/ione_circle_val)
                {
                    iCircle_count = ival/ione_circle_val;
                }
            }
            else if(ival > imax_val)
            {
                ival = imax_val;
                seekBar.setProgress(ival%ione_circle_val, 0);
                if(iCircle_count > ival/ione_circle_val)
                {
                    iCircle_count = ival/ione_circle_val;
                }
            }

            v_set_seeker_color(ival);

            t_ProgressVal.setText(String.valueOf(ival));

            cw_dir = 0;
            ipre_val = progress;


        }

        @Override
        public void onStopTrackingTouch(RtxCircularDuration seekBar) {
        }

        @Override
        public void onStartTrackingTouch(RtxCircularDuration seekBar) {
            ipre_val = seekBar.getProgress();
            cw_dir = 0;
        }
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == imageView_Next)    { vClickNext(); }
            else {
                on_click(v);
            }
        }
    }

    ///////////////////////////////
    private void v_set_seeker_color(int ival)
    {
        if(ival >= ione_circle_val)
        {
            mProgress.setCircleProgressGlowColor(icolor_backcircle);
        }
        else
        {
            mProgress.setCircleProgressGlowColor(icolor_hide);
        }
    }



}
