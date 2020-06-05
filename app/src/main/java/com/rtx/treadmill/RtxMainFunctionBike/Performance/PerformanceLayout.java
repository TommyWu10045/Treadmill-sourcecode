package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class PerformanceLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView        i_info;
    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_data;

    public int istr_list[][] =  {
            {R.drawable.circle_blue01    ,       R.string.session1  },
            {R.drawable.circle_blue01    ,       R.string.month    },
            {R.drawable.circle_blue01    ,       R.string.year     }
    };

    int imax = istr_list.length;

    public PerformanceLayout(Context context, MainActivity mMainActivity) {
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

    public void init_View()
    {
        int iLoop ;

        init_Title();
        init_BackHome();

        if(i_info == null)       { i_info = new RtxImageView(mContext);   }

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

    public void init_event()
    {
        int iLoop ;

        i_info.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_tr[iLoop].setOnClickListener(mButtonListener);
            t_data[iLoop].setEnabled(false);
            t_data[iLoop].setClickable(false);
        }
    }

    public void add_View()
    {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        String sdata;
        int ix_shift ;
        int ipadding = 30;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.my_performance);
        vSetTitleText(sdata.toUpperCase());

//        info
        ix = 825;
        iy = 35;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_info, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//      Circle icon
        ix = 200;
        iy = 300;
        iw = 200 ;
        ih = 200;
        fsize = 33.33f;
        ix_shift = 340 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImage(null, i_tr[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_data[iLoop], sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            ix += ix_shift;
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickitem(int iLoop)
    {
        switch (iLoop)
        {
            case 0:
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_SESSION);
                break;
            case 1:
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_MONTH);
                break;
            case 2:
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_YEAR);
                break;
            default:
                break;
        }

    }

    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.performanceProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.performance).toUpperCase();
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.performance_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_my_performance, -1, stitle01, null, sinfo01, null, "pf_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)            { vBackPrePage(); }
            else if(v == i_info)                      { vShowInfoDialog(); }
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

