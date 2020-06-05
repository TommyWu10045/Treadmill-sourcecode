package com.rtx.treadmill.RtxMainFunction.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TypeSourceLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_data;

    public int istr_list[][] =  {
            {R.xml.pf_running       ,       R.string.running   },
            {R.xml.pf_biking        ,       R.string.biking    },
            {R.xml.pf_elliptical    ,       R.string.elliptical    },
            {R.xml.pf_other         ,       R.string.other     }
    };

    int imax = istr_list.length;


    private float[] fdata = new float[imax];

    public TypeSourceLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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
        int iLoop ;

        init_BackPrePage();
        init_Title();

        if(i_tr == null) {
            i_tr = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_tr[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_data == null) {
            t_data = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event()
    {
        int iLoop ;

        imageView_BackPrePage.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_tr[iLoop].setOnClickListener(mButtonListener);
        }
    }

    private void add_View()
    {
        int iLoop;
        int ix, iy, iw, ih;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize;
        String sdata;
        int ix_shift ;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.select_exercise_type);
        vSetTitleText(sdata.toUpperCase());

//      weight icon
        ix = 75;
        iy = 285;
        iw = 175 ;
        ih = 175;
        fsize = 33.33f;
        ix_shift = 320 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImagePaddingViewToLayout(i_tr[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0);

            ix_temp = ix;
            iy_temp = iy + 200;
            iw_temp = iw;
            ih_temp = 40;
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp - 25, iy_temp, iw_temp + 50, ih_temp);
            ix += ix_shift;
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickitem(int iLoop)
    {
        mMainActivity.mMainProcTreadmill.performanceProc.vSet_type(iLoop);
        if(iLoop == 3) {//3 is other
            mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_ADD_NAME);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_ADD_DATE);
        }
    }

    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_SESSION);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                      { vBackPrePage(); }
            else {
                int iLoop;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_tr[iLoop])
                    {
                        vClickitem(iLoop);
                        break;
                    }
                }
            }
        }
    }

}

