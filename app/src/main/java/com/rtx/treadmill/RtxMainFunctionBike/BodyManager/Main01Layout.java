package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.retonix.circlecloud.Cloud_15_GET_BDY_IDX_REC;
import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fTAL_BDY_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fVCL_FAT_RTG;

/**
 * Created by chasechang on 3/22/17.
 */

public class Main01Layout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxTextView         t_date;
    private RtxTextView         t_date_data;
    private RtxImageView        i_heart_rate;
    private RtxTextView         t_heart_rate;
    private RtxImageView        i_edit_pen;
    private RtxImageView        i_info;
    private RtxImageView        i_next;
    private RtxImageView        i_bdinfo;
    private RtxImageView        i_muscle;
    private RtxImageView        i_bodyfat;
    private RtxTextView         t_muscle;
    private RtxTextView         t_bodyfat;

    private RtxDoubleStringView[]         t_data;
    private RtxTextView[]         t_data_name;
    private RtxImageView[]        i_edit;

    private int istr_list[];
    private int imax;

    private int i_gap_null = 40;
    private int i_gap_data = 20;

    private int iedit_mode = 0; //0 view; 1 edit
    private boolean blogin = false;

    public Main01Layout(Context context, MainActivity mMainActivity) {
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

        setBackgroundColor(Common.Color.background_dialog);
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
        istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[0];
        imax = istr_list.length;

        init_Title();
        init_BackHome();

        if(t_date == null)    { t_date = new RtxTextView(mContext);     }
        if(t_date_data == null)    { t_date_data = new RtxTextView(mContext);     }
        if(i_heart_rate == null)    { i_heart_rate = new RtxImageView(mContext);    }
        if(t_heart_rate == null)    { t_heart_rate = new RtxTextView(mContext);     }
        if(i_edit_pen == null)       { i_edit_pen = new RtxImageView(mContext);   }
        if(i_info == null)       { i_info = new RtxImageView(mContext);   }
        if(i_next == null)       { i_next = new RtxImageView(mContext);   }
        if(i_bdinfo == null)       { i_bdinfo = new RtxImageView(mContext);   }
        if(i_muscle == null)       { i_muscle = new RtxImageView(mContext);   }
        if(i_bodyfat == null)       { i_bodyfat = new RtxImageView(mContext);   }
        if(t_muscle == null)    { t_muscle = new RtxTextView(mContext);     }
        if(t_bodyfat == null)    { t_bodyfat = new RtxTextView(mContext);     }
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
        if(i_edit == null) {
            i_edit = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_edit[iLoop] = new RtxImageView(mContext);
            }
        }
    }

    public void init_event()
    {
        int iLoop ;

        i_edit_pen.setOnClickListener(mButtonListener);
        i_info.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);
        i_next.setOnClickListener(mButtonListener);
        i_bdinfo.setOnClickListener(mButtonListener);
        i_muscle.setOnClickListener(mButtonListener);
        i_bodyfat.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_edit[iLoop].setOnClickListener(mButtonListener);
        }

    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata, sHeartRate;
        float fsize_unit;
        int ipadding = 30;
        int igap, idata_color;
        int ix_shift = 30;
        int ix_next = 270 + ix_shift * 2;   //物件間距

//        date
        ix = 50;
        iy = 30;
        iw = 270;
        ih = 21;
        fsize = 16.66f;

        sdata = Rtx_Calendar.s_trans_DateTime_Str(1, "M/dd/yyyy h:mm a", "yyyy-MM-dd HH:mm:ss", CloudDataStruct.BodyIndexData_05.sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.dMSR_DT], 0, 0);
        sHeartRate = CloudDataStruct.BodyIndexData_15.sdata_out[Cloud_15_GET_BDY_IDX_REC.Output.BMI_L] + " bpm";
        if(sdata == null)
        {
            addRtxTextViewToLayout(t_date, "", fsize, Common.Color.bd_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            iy += ih;
            //addRtxTextViewToLayout(t_date_data, R.string.no_data, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            addRtxTextViewToLayout(t_date_data, "", fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            iy += (ih + 2);
            addRtxImageViewToLayout(i_heart_rate, R.drawable.bd_heart_rate, ix, iy, 21, 17);
            i_heart_rate.setVisibility(INVISIBLE);
            ix += 27;
            iy -= 2;
            addRtxTextViewToLayout(t_heart_rate, "", fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
        }
        else
        {
            addRtxTextViewToLayout(t_date, R.string.last_updated, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            iy += ih;
            addRtxTextViewToLayout(t_date_data, sdata, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            iy += (ih + 2);
            addRtxImageViewToLayout(i_heart_rate, R.drawable.bd_heart_rate, ix, iy, 21, 17);
            i_heart_rate.setVisibility(VISIBLE);
            ix += 27;
            iy -= 2;
            addRtxTextViewToLayout(t_heart_rate, sHeartRate, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
        }

        //20190121文件 UI變更
//        edit pen
        ix = 260;
        iy = 40;
        iw = 58;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_edit_pen, R.drawable.bd_edit_done, ix, iy, iw, ih, ipadding);

//        menu
        vSetTitleText(R.string.body_manager);

//        info
        ix = 870; //By Alan
        iy = 36;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_info, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//        first line
        ix = 0 - ix_shift;
        iy = 100;
        iw = 270 + ix_shift*2;
        ih = 90;
        fsize = 66.66f;
        fsize_unit = 33.33f;
        fsize_temp = 23.33f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == 4)
            {
                //        second line
                ix = 60 - ix_shift + 45;
                iy = 306;
                ix_next = 357 + ix_shift * 2;
            }
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);

            sdata = BodyManagerFunc.s_get_drawdata(mContext, istr_list[iLoop]);

            if(sdata.compareTo("") == 0 )
            {
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
            ix_temp = ix+40;//By Alan
            iy_temp = iy + ih;
            iw_temp = 260;//By Alan
            ih_temp = 70;
            if(istr_list[iLoop] == fTAL_BDY_WAT) {
                sdata = LanguageData.s_get_string(mContext, R.string.total_body_water_two);
            }
            else if(istr_list[iLoop] == fVCL_FAT_RTG) {
                sdata = LanguageData.s_get_string(mContext, R.string.visceral_fat_rating_two);
            }
            else
            {
                sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, istr_list[iLoop]);
            }
            addRtxTextViewToLayout(t_data_name[iLoop], sdata.toUpperCase(), fsize_temp, Common.Color.bd_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

            ix_temp = ix + 130 + ix_shift;//By Alan
            iy_temp += ih_temp;
            iw_temp = 36;
            ih_temp = 27;
            addRtxImagePaddingViewToLayout(i_edit[iLoop], R.drawable.next_arrow_edit, ix_temp, iy_temp, iw_temp, ih_temp, ipadding);
            ix += ix_next - ix_shift * 2;
        }

//        muscle
        ix = 310;
        iy = 510;
        iw = 180;
        ih = 180;
        fsize = 23.33f;
        addRtxImageViewToLayout(i_muscle, R.drawable.bd_muscle, ix, iy, iw, ih);
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = 50;//By Alan
        addRtxTextViewToLayout(t_muscle, R.string.muscle_mass, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

//        body fat
        ix += iw + 260;
        addRtxImageViewToLayout(i_bodyfat, R.drawable.bd_bodyfat, ix, iy, iw, ih);
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = 50;//By Alan
        addRtxTextViewToLayout(t_bodyfat, R.string.body_fat, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        //        next page
        ix = 1140;
        iy = 230;
        iw = 85;
        ih = 85;
        addRtxImageViewToLayout(i_next, R.drawable.triangle_next, ix, iy, iw, ih);

//        body info
        ix = 1070;
        iy = 550;
        iw = 108;
        ih = 108;
        addRtxImageViewToLayout(i_bdinfo, R.drawable.bd_info, ix, iy, iw, ih);

        vData_refresh();
    }

    /////////////////refresh result/////////////////////////////
    private void vData_refresh()
    {
        vsetPenMode();
    }

    private void vsetPenMode()
    {
        if(iedit_mode == 0)
        {
            v_icon_HandtoArrow();
        }
        else if(iedit_mode == 1)
        {
            v_icon_ArrowtoHand();
        }

        if(blogin)
        {
            i_edit_pen.setVisibility(VISIBLE);
        }
        else
        {
            i_edit_pen.setVisibility(INVISIBLE);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    public void v_setSelect(boolean bval, int isel)
    {
        blogin = bval;
        iedit_mode = isel;
    }

    ////////////////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClicknext()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE02);
    }

    private void vClickbdinfo()
    {
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_BM_INFO);
    }

    private void vClickmuscle()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSet_Select(istr_list[0]);
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_MUSCLE);
    }

    private void vClickbdfat()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSet_Select(istr_list[0]);
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_BODYFAT);
    }

    private void vClickitem(int isel)
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSet_Select(istr_list[isel]);

        if(BodyManagerFunc.s_get_drawdata(mContext, istr_list[isel]).compareTo("") != 0 ) {
            mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_HIS_GET);
        }
        else
        {
            mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_ALLVIEW);
        }
    }

    private void vClickitemEdit(int isel)
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSet_Select(istr_list[isel]);
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_INPUT);
    }


    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.body_manager);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.bodymanager_dialog_info);

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_body_manager, -1, stitle01, null, sinfo01, null, "bd_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vEditPen_icon()
    {
        if(iedit_mode == 0)
        {
            iedit_mode = 1;
            v_icon_ArrowtoHand();

        }
        else if(iedit_mode == 1)
        {
            iedit_mode = 0;
            mMainActivity.mMainProcBike.bodymanagerProc.vSet_Select(istr_list[0]);
            mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_BD_SET);
        }

        mMainActivity.mMainProcBike.bodymanagerProc.vSet_Edit_mode(iedit_mode);
    }

    private void v_icon_ArrowtoHand()
    {
        int iLoop;

        i_muscle.setVisibility(INVISIBLE);
        t_muscle.setVisibility(INVISIBLE);
        i_bodyfat.setVisibility(INVISIBLE);
        t_bodyfat.setVisibility(INVISIBLE);

        i_edit_pen.setImageResource(R.drawable.bd_edit_done);

        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(BodyManagerFunc.is_edit(mContext, istr_list[iLoop])) {
                i_edit[iLoop].setImageResource(R.drawable.bd_edit_hand);
            }
            else
            {
                i_edit[iLoop].setVisibility(INVISIBLE);
            }
        }
    }

    private void v_icon_HandtoArrow()
    {
        int iLoop;

        i_muscle.setVisibility(VISIBLE);
        t_muscle.setVisibility(VISIBLE);
        i_bodyfat.setVisibility(VISIBLE);
        t_bodyfat.setVisibility(VISIBLE);

        i_edit_pen.setImageResource(R.drawable.bd_edit_pen);

        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(BodyManagerFunc.is_edit(mContext, istr_list[iLoop])) {
                i_edit[iLoop].setImageResource(R.drawable.next_arrow_edit);
            }
            else
            {
                i_edit[iLoop].setVisibility(VISIBLE);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_edit_pen)                 { vEditPen_icon(); }
            else if(v == i_info)                { vShowInfoDialog(); }
            else if(v == imageView_BackHome)    { vClickMain(); }
            else if(v == i_next)                { vClicknext(); }
            else if(v == i_bdinfo)              { vClickbdinfo(); }
            else if(v == i_muscle)              { vClickmuscle(); }
            else if(v == i_bodyfat)             { vClickbdfat(); }
            else {
                int iLoop;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_edit[iLoop])
                    {
                        if(iedit_mode == 1)
                        {
                            vClickitemEdit(iLoop);
                        }
                        else
                        {
                            vClickitem(iLoop);
                        }
                        break;
                    }
                }
            }
        }
    }
}
