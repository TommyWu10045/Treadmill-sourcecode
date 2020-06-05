package com.rtx.treadmill.RtxMainFunctionBike.Physical;

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

public class PhysicalLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxImageView        i_info;
    private RtxImageView[]      i_phy;
    private RtxTextView[]       t_phy;
    private RtxImageView[]      i_phyinfo;

    private int istr_list[][];
    private String sinfo_list[];

    public PhysicalLayout(Context context, MainActivity mMainActivity) {
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
        istr_list = mMainActivity.mMainProcBike.physicalProc.itype_tread_list;
        sinfo_list = mMainActivity.mMainProcBike.physicalProc.sinfotreadlist;

        int imax = istr_list.length;

        init_Title();
        init_BackHome();

        if(i_info == null)    { i_info = new RtxImageView(mContext);     }

        if(i_phy == null) {
            i_phy = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_phy[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_phy == null) {
            t_phy = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_phy[iLoop] = new RtxTextView(mContext);
            }
        }

        if(i_phyinfo == null) {
            i_phyinfo = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_phyinfo[iLoop] = new RtxImageView(mContext);
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
            i_phy[iLoop].setOnClickListener(mButtonListener);
            i_phyinfo[iLoop].setOnClickListener(mButtonListener);
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
        int ifirst_count = 4;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.physical_test);
        vSetTitleText(sdata.toUpperCase());

//        info
        ix = 870;
        iy = 35;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_info, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//        icon
        ix = 553;
        iy = 300;
        iw = 173 ;
        ih = 173;
        ix_shift = 320 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 73;
                iy += 320;
            }
            addRtxImagePaddingViewToLayout(i_phy[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0);
            ix += ix_shift;
        }

        ix = 50;//By Alan
        iy = 260;//By Alan
        iw = 250 ;//By Alan
        ih = 60;//By Alan
        fsize = 28f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 50;//By Alan
                iy += 320;
            }
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_phy[iLoop], sdata.toUpperCase(), fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            ix += ix_shift;
        }

        ix = 300;//By Alan
        iy = 325;//By Alan
        iw = 20 ;
        ih = 20;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count)
            {
                ix = 300;//By Alan
                iy += 320;//By Alan
            }
            addRtxImage(null, i_phyinfo[iLoop], R.drawable.info_icon, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            ix += ix_shift;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcBike.physicalProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClickinfo(int iLoop)
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_mode(iLoop);

        String stitle01 = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
        String sinfo01 = LanguageData.s_get_string(mContext, istr_list[iLoop][3]);

        Dialog_UI_Info.v_tist_Dialog(istr_list[iLoop][2], -1, stitle01, null, sinfo01, null, sinfo_list[iLoop], ImageView.ScaleType.CENTER_INSIDE);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    private void vClickitem(int iLoop)
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_mode(iLoop);
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_WEIGHT);
    }

    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.physical_test);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.phy_dialog_info);

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_physical_test, -1, stitle01, null, sinfo01, null, "phy_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_info)      { vShowInfoDialog(); }
            else if(v == imageView_BackHome)      { vClickMain(); }
            else {
                int iLoop;
                int imax = istr_list.length;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_phy[iLoop])
                    {
                        vClickitem(iLoop);
                        break;
                    }
                    else if(v == i_phyinfo[iLoop])
                    {
                        vClickinfo(iLoop);
                        break;
                    }
                }
            }
        }
    }
}
