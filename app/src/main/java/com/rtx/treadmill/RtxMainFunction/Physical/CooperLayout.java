package com.rtx.treadmill.RtxMainFunction.Physical;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class CooperLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxTextView         t_data;
    private RtxTextView[]       t_info;
    private RtxDoubleStringView t_distance;

    private RtxPercentCircleView mPercentCircle;

    private int icount = 0;
    private int imax = 2;

    public CooperLayout(Context context, MainActivity mMainActivity) {
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

        icount = 0;

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

    public void init_View()
    {
        int iLoop ;

        init_Title();

        if(t_distance == null)    { t_distance = new RtxDoubleStringView(mContext);     }
        if(t_data == null)        { t_data = new RtxTextView(mContext);                 }

        if(t_info == null) {
            t_info = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_info[iLoop] = new RtxTextView(mContext);
            }
        }

        if(mPercentCircle == null)           { mPercentCircle = new RtxPercentCircleView(mContext); }

    }

    public void init_event()
    {

    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;

        sdata = LanguageData.s_get_string(mContext, R.string.cooper_test);
        vSetTitleText(sdata.toUpperCase());

//        data
        ix = 161;
        iy = 355;
        iw = 443 ;
        ih = 160;
        fsize = 150f;
        sdata = "";
        addRtxTextViewToLayout(t_data, sdata, fsize, Common.Color.physical_word_pink, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        addViewToLayout(mPercentCircle, 126, 178, 518, 518);
        mPercentCircle.setValueBarColor(Common.Color.physical_word_pink);

        iy += ih;
        ih = 60;
        fsize = 20f;
        iLoop = 0;
        sdata = LanguageData.s_get_string(mContext, R.string.remaining_time);
        addRtxTextViewToLayout(t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 653;
        iy = 355;
        iw = 575 ;
        ih = 160;
        fsize = 150f;
        fsize_unit = 40f;
        sdata = EngSetting.Distance.getValString(ExerciseData.mCaculate_Data.sdistance.fcurr);
        addRtxDoubleStringView(t_distance, ix, iy, iw, ih);
        t_distance.setGap(40);
        t_distance.setPaint(Common.Font.Relay_Black, Common.Color.physical_word_yellow, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
        t_distance.setText(sdata, EngSetting.Distance.getUnitString(mContext));

        iy += ih;
        ih = 60;
        fsize = 20f;
        iLoop = 1;
        sdata = LanguageData.s_get_string(mContext, R.string.distance);
        addRtxTextViewToLayout(t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        init_ViewMask();
        vVisibleMaskView(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_refresh_target()
    {
        int itotal = ExerciseData.mCaculate_Data.itotal_time;
        int itarget = ExerciseData.mCaculate_Data.itarget_time;
        float fcurr = ExerciseData.mCaculate_Data.sdistance.ftotal/1000;

        int idiff;
        String sdata;
        int iPercent;

        idiff = itarget - itotal;
        if(itarget <= 0)
        {
            iPercent = 100;
        }
        else {
            if(idiff < 0)
            {
                iPercent = 0;
            }
            else {
                iPercent = (int) (100f * idiff / itarget);
                if(iPercent < 0)
                {
                    iPercent = 0;
                }
                else if(iPercent > 100)
                {
                    iPercent = 100;
                }
            }

        }

        sdata = Rtx_Calendar.s_trans_integer(1, idiff);
        t_data.setText(sdata);

        sdata = EngSetting.Distance.getValString(fcurr);
        t_distance.setText(sdata, EngSetting.Distance.getUnitString(mContext));

        mPercentCircle.setPercentValue(iPercent);
    }

    public void Refresh()
    {
        if(icount % EngSetting.DEF_EXERCISE_PHYSICAL_REFRESH == 0)
        {
            v_refresh_target();
        }

        if(icount % (EngSetting.DEF_SEC_COUNT * 2) == 0)
        {
            vVisibleMaskView(false);
        }

        icount++;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {

        }
    }
}
