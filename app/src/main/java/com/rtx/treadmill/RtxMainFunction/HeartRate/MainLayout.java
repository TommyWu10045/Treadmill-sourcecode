package com.rtx.treadmill.RtxMainFunction.HeartRate;

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

public class MainLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView        i_hrcinfo;
    private RtxImageView[]      i_hrc;
    private RtxTextView[]       t_date;
    private RtxImageView[]      i_info;

    private int istr_list[][];
    private String sinfo_list[];

    public MainLayout(Context context, MainActivity mMainActivity) {
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
        istr_list = mMainActivity.mMainProcTreadmill.heartrateProc.itype_list;
        sinfo_list = mMainActivity.mMainProcTreadmill.heartrateProc.sinfolist;

        int imax = istr_list.length;

        init_Title();
        init_BackHome();

        if(i_hrcinfo == null)    { i_hrcinfo = new RtxImageView(mContext);     }

        if(i_hrc == null) {
            i_hrc = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_hrc[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_date == null) {
            t_date = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_date[iLoop] = new RtxTextView(mContext);
            }
        }

        if(i_info == null) {
            i_info = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_info[iLoop] = new RtxImageView(mContext);
            }
        }
    }

    public void init_event()
    {
        int iLoop ;
        int imax = istr_list.length;

        i_hrcinfo.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_hrc[iLoop].setOnClickListener(mButtonListener);
            i_info[iLoop].setOnClickListener(mButtonListener);
        }

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

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.heart_rate_control);
        vSetTitleText(sdata.toUpperCase());

//        info
        ix = 955;//By Alan
        iy = 35;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_hrcinfo, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//        icon
        ix = 75;
        iy = 300;
        iw = 210 ;
        ih = 210;
        ix_shift = 310 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImagePaddingViewToLayout(i_hrc[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0);
            ix += ix_shift;
        }

        ix = 25;
        iy += ih + 50;//By Alan
        iw = 310 ;
        ih = 60;//By Alan
        fsize = 28f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]).toUpperCase();
            addRtxTextViewToLayout(t_date[iLoop], sdata, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            ix += ix_shift;
        }

        ix = 170;
        iy += ih + 16;
        iw = 20 ;
        ih = 20;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImage(null, i_info[iLoop], R.drawable.info_icon, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            ix += ix_shift;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClickinfo(int iLoop)
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vSet_Select(iLoop);

        String stitle01 = LanguageData.s_get_string(mContext, istr_list[iLoop][1]).toUpperCase();;
        String sinfo01 = LanguageData.s_get_string(mContext, istr_list[iLoop][3]).toUpperCase();;

        Dialog_UI_Info.v_tist_Dialog(istr_list[iLoop][2], -1, stitle01, null, sinfo01, null, sinfo_list[iLoop], ImageView.ScaleType.CENTER_INSIDE);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    private void vClickitem(int iLoop)
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vSet_Select(iLoop);
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_WEIGHT);
    }

    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.heart_rate_control).toUpperCase();
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.hrc_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_heart_rate_control, -1, stitle01, null, sinfo01, null, "hrc_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_hrcinfo)      { vShowInfoDialog(); }
            else if(v == imageView_BackHome)      { vClickMain(); }
            else {
                int iLoop;
                int imax = istr_list.length;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_hrc[iLoop])
                    {
                        vClickitem(iLoop);
                        break;
                    }
                    else if(v == i_info[iLoop])
                    {
                        vClickinfo(iLoop);
                        break;
                    }
                }
            }
        }
    }
}
