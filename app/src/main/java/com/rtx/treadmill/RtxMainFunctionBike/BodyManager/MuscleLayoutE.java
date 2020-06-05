package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class MuscleLayoutE extends Rtx_BaseLayoutE {

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private FrameLayout f_back;
    private RtxImageView        i_body;
    private RtxImageView        i_upbalance;
    private RtxImageView        i_lowbalance;
    private RtxImageView        i_upbody;
    private RtxImageView        i_lowbody;

    private RtxDoubleStringView[]         t_data;
    private RtxTextView[]         t_data_name;

    private int istr_list[] = {//item name
        Cloud_05_DB_BDY_IDX_REC.Input.fTRK_FAT_MAS,
        Cloud_05_DB_BDY_IDX_REC.Input.fLFT_ARF_FMS,
        Cloud_05_DB_BDY_IDX_REC.Input.fRIT_ARF_FMS,
        Cloud_05_DB_BDY_IDX_REC.Input.fLFT_LGF_FMS,
        Cloud_05_DB_BDY_IDX_REC.Input.fRIG_LGF_FMS,
    };
    int imax = istr_list.length;

    private int i_gap_null = 40;
    private int i_gap_data = 20;
    private int imode;
    private float fscale = 0.9f;

    private float[] f_muscle_val;
    private boolean b_val_null = false;

    public MuscleLayoutE(Context context, MainActivity mMainActivity) {
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
        int iLoop ;

        init_Title();
        init_BackPrePage();
        init_BackHome();
        init_BackExercise();

        if(f_back == null)    { f_back = new FrameLayout(mContext);     }
        if(i_body == null)       { i_body = new RtxImageView(mContext);   }
        if(i_upbalance == null)       { i_upbalance = new RtxImageView(mContext);   }
        if(i_lowbalance == null)       { i_lowbalance = new RtxImageView(mContext);   }
        if(i_upbody == null)       { i_upbody = new RtxImageView(mContext);   }
        if(i_lowbody == null)       { i_lowbody = new RtxImageView(mContext);   }

        if(t_data == null) {
            t_data = new RtxDoubleStringView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data[iLoop] = new RtxDoubleStringView(mContext);
            }
        }
        if(t_data_name == null) {
            t_data_name = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data_name[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);
    }

    private void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata;
        float fsize_unit;
        int igap , idata_color;
        int ix_shift = 50;


//        menu
        vSetTitleText(R.string.muscle_mass);

//        frame
        ix = 0;
        iy = 100;
        iw = 1280;
        ih = 800;
        addRtxViewToLayout(f_back, ix, iy, iw, ih, Common.Color.bd_exercise_back);

//        muscle balanced
        v_check_balance();

//        data
        ix = 540 - ix_shift;
        iy = 60;
        iw = 200 + ix_shift*2;
        ih = iGet_Scale(80);
        fsize = fGet_Scale(66.66f);
        fsize_unit = fGet_Scale(33.33f);
        fsize_temp = fGet_Scale(23.33f);
        for (iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == 1)
            {
                ix = 250 - ix_shift;
                iy = 160;
            }
            else  if(iLoop == 2)
            {
                ix += 560;
            }
            else  if(iLoop == 3)
            {
                ix = 300 - ix_shift;
                iy = 370;
            }
            else  if(iLoop == 4)
            {
                ix += 470;
            }
            addRtxDoubleStringView(f_back, t_data[iLoop], ix, iy, iw, ih);

            sdata = BodyManagerFunc.s_get_drawdata(mContext, istr_list[iLoop]);
            if(b_val_null || sdata.compareTo("") == 0)
            {
                sdata = "";
                igap = i_gap_null;
                idata_color = Common.Color.bd_word_gray;
            }
            else
            {
                igap = i_gap_data;
                idata_color = Common.Color.bd_word_white;
            }

            t_data[iLoop].setGap(igap);
            t_data[iLoop].setPaint(Common.Font.Relay_Black, idata_color, fsize, Common.Font.Relay_BlackItalic, Common.Color.bd_word_blue, fsize_unit);
            t_data[iLoop].setText(sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, istr_list[iLoop]));

            ix_temp = ix;
            iy_temp = iy + ih;
            iw_temp = iw;
            ih_temp = iGet_Scale(40);
            sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, istr_list[iLoop]);
            addRtxTextView(f_back, t_data_name[iLoop], sdata.toUpperCase(), fsize_temp, Common.Color.bd_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);
        }

    }

    public void v_setSelect(int isel)
    {
        imode = isel;
    }

    private void v_check_balance()
    {
        int iLoop ;
        int ix, iy, iw, ih;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float f_diff = 5f; //5%
        float f_left ;
        float f_right ;
        float f_data ;
        int ipadding = 0;

        f_muscle_val = new float[imax];

        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            f_muscle_val[iLoop] = BodyManagerFunc.f_get_bodymanage_rawdata(mContext, istr_list[iLoop]);
            if(f_muscle_val[iLoop] == -1 || f_muscle_val[iLoop] == 0)
            {
                b_val_null = true;
            }
        }

        ix = 480;
        iy = 150;
        iw = 320;
        ih = 360;
        addRtxImage(f_back, i_body, R.drawable.bodymanager_body, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        if(!b_val_null) {
            ix_temp = 500;
            iy_temp = 10;
            iw_temp = 270;
            ih_temp = 45;

            f_left = f_muscle_val[1];
            f_right = f_muscle_val[2];

            f_data = ((f_left / f_right) * 100) - 100;
            if (f_data > f_diff) {
                addRtxImage(f_back, i_upbalance, R.drawable.upper_body_imbalanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, ImageView.ScaleType.FIT_XY);
                addRtxImage(f_back, i_upbody, R.drawable.bodymanager_left_arm, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            } else if (f_data < -f_diff) {
                addRtxImage(f_back, i_upbalance, R.drawable.upper_body_imbalanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, ImageView.ScaleType.FIT_XY);
                addRtxImage(f_back, i_upbody, R.drawable.bodymanager_right_arm, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            } else {
                addRtxImage(f_back, i_upbalance, R.drawable.upper_body_balanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, null);
            }

            iy_temp += 480;
            f_left = f_muscle_val[3];
            f_right = f_muscle_val[4];

            f_data = ((f_left / f_right) * 100) - 100;
            if (f_data > f_diff) {
                addRtxImage(f_back, i_lowbalance, R.drawable.lower_body_imbalanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, ImageView.ScaleType.FIT_XY);
                addRtxImage(f_back, i_lowbody, R.drawable.bodymanager_left_leg, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            } else if (f_data < -f_diff) {
                addRtxImage(f_back, i_lowbalance, R.drawable.lower_body_imbalanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, ImageView.ScaleType.FIT_XY);
                addRtxImage(f_back, i_lowbody, R.drawable.bodymanager_right_leg, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);
            } else {
                addRtxImage(f_back, i_lowbalance, R.drawable.lower_body_balanced, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, null);
            }

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        int iLoop ;
        int istr_list[];

        istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[0];


        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);
                return;
            }
        }

        istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[1];
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE02);
                return;
            }
        }

        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vClickBack(); }
            else if(v == imageView_BackHome)    { vClickMain(); }
            else if(v == imageView_ReturnExercisePage)    { vBackToExercisePage(); }

        }
    }

    private void vClickMain()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vBackToExercisePage()
    {
        mMainActivity.mMainProcBike.v_Goto_ExercisePage();
    }

    ///////////////////////////////////////////
    private int iGet_Scale(int input)
    {
        int ival = (int)(input * fscale);

        if(ival <= 0)
        {
            ival = 1;
        }

        return ival;
    }

    private float fGet_Scale(float finput)
    {
        float fval = finput * fscale;

        if(fval <= 0)
        {
            fval = 1f;
        }

        return fval;
    }


}
