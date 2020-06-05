package com.rtx.treadmill.RtxMainFunctionBike.Training;

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
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TrainingLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView        i_info;
    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_tr;
    private RtxImageView[]      i_trinfo;

    public RtxFillerTextView fillerTextView_goal;

    private int istr_list[][];
    private String sinfo_list[];

    public TrainingLayout(Context context, MainActivity mMainActivity) {
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

    public void init_View()
    {
        int iLoop ;
        istr_list = mMainActivity.mMainProcBike.trainingProc.itype_tread_list;
        sinfo_list = mMainActivity.mMainProcBike.trainingProc.sinfotreadlist;

        int imax = istr_list.length;

        init_Title();
        init_BackHome();

        if(fillerTextView_goal == null)        { fillerTextView_goal = new RtxFillerTextView(mContext);   }

        if(i_info == null)    { i_info = new RtxImageView(mContext);     }

        if(i_tr == null) {
            i_tr = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_tr[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_tr == null) {
            t_tr = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_tr[iLoop] = new RtxTextView(mContext);
            }
        }

        if(i_trinfo == null) {
            i_trinfo = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_trinfo[iLoop] = new RtxImageView(mContext);
            }
        }
    }

    public void init_event()
    {
        int iLoop ;
        int imax = istr_list.length;

        i_info.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_tr[iLoop].setOnClickListener(mButtonListener);
            i_trinfo[iLoop].setOnClickListener(mButtonListener);
        }
        fillerTextView_goal.setOnClickListener(mButtonListener);

    }

    public void add_View() {
        int iLoop;
        int imax = istr_list.length;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        String sdata;
        int ipadding = 30;
        int ix_shift ;
        int ifirst_count = 4;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.training);
        vSetTitleText(sdata.toUpperCase());

//        info
        ix = 780;//By Alan
        iy = 37;//By Alan
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_info, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//        icon
        ix = 90;
        iy = 140;
        iw = 136 ;
        ih = 136;
        ix_shift = 320 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 90;
                iy += 290;
            }
            addRtxImagePaddingViewToLayout(i_tr[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0);
            ix += ix_shift;
        }

        ix = 0;
        iy = 286;//By Alan
        iw = 320 ;
        ih = 60;//By Alan
        fsize = 28f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 0;
                iy += 290;
            }
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_tr[iLoop], sdata.toUpperCase(), fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            ix += ix_shift;
        }

        ix = 260;//By Alan
        iy = 200;//By Alan
        iw = 20 ;
        ih = 20;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 260;//By Alan
                iy += 290;
            }
            addRtxImage(null, i_trinfo[iLoop], R.drawable.info_icon, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            ix += ix_shift;
        }

        addRtxTextViewToLayout(fillerTextView_goal, R.string.set_a_goal, 28f, Common.Color.training_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 467, 702, 340, 66, Common.Color.training_word_blue);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcBike.trainingProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClickinfo(int iLoop)
    {
        mMainActivity.mMainProcBike.trainingProc.vSet_mode(iLoop);

        String stitle01 = LanguageData.s_get_string(mContext, istr_list[iLoop][1]).toUpperCase();
        String sinfo01 = LanguageData.s_get_string(mContext, istr_list[iLoop][3]).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(istr_list[iLoop][2], -1, stitle01, null, sinfo01, null, sinfo_list[iLoop], ImageView.ScaleType.CENTER_INSIDE);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    private void vClickitem(int iLoop)
    {
        mMainActivity.mMainProcBike.trainingProc.vSet_mode(iLoop);
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WEIGHT);
    }

    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.training).toUpperCase();
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.tr_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_training, -1, stitle01, null, sinfo01, null, "tr_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    private void vClickSet_A_Goal()
    {
        mMainActivity.mMainProcBike.trainingProc.vSet_mode(mMainActivity.mMainProcBike.trainingProc.i_target_start);
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WEIGHT);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_info)      { vShowInfoDialog(); }
            else if(v == imageView_BackHome)      { vClickMain(); }
            else if(v == fillerTextView_goal)      { vClickSet_A_Goal(); }
            else {
                int iLoop;
                int imax = istr_list.length;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_tr[iLoop])
                    {
                        vClickitem(iLoop);
                        break;
                    }
                    else if(v == i_trinfo[iLoop])
                    {
                        vClickinfo(iLoop);
                        break;
                    }
                }
            }
        }
    }
}
