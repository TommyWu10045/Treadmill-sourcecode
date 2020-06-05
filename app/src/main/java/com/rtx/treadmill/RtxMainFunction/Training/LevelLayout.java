package com.rtx.treadmill.RtxMainFunction.Training;

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

public class LevelLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_data;

    public int istr_list[][] =  {
            {R.xml.tr_easy    ,       R.string.easy   },
            {R.xml.tr_medium        ,       R.string.medium    },
            {R.xml.tr_hard    ,       R.string.hard     }
    };

    int imax = istr_list.length;


    private float[] fdata = new float[imax];

    public LevelLayout(Context context, MainActivity mMainActivity) {
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
        sdata = LanguageData.s_get_string(mContext, R.string.level);
        vSetTitleText(sdata.toUpperCase());

//      weight icon
        ix = 152;
        iy = 278;
        iw = 232 ;
        ih = 232;
        fsize = 41.64f;
        ix_shift = 372 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImagePaddingViewToLayout(i_tr[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0);

            ix_temp = ix;
            iy_temp = iy + 272;
            iw_temp = iw;
            ih_temp = 50;
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);
            ix += ix_shift;
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickitem(int iLoop)
    {
        mMainActivity.mMainProcTreadmill.trainingProc.vSet_Level(iLoop);
        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
    }

    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TIME);
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

