package com.rtx.treadmill.RtxMainFunction.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
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
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.utils.Consts;
import com.utils.MyLog;
import com.utils.MyUtils;

/**
 * Created by chasechang on 3/22/17.
 */

public class Main02LayoutE extends Rtx_BaseLayoutE {

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxTextView         t_date;
    private RtxTextView         t_date_data;
    private RtxImageView        i_heart_rate;
    private RtxTextView         t_heart_rate;
    private RtxImageView        i_edit_pen;
    private RtxImageView        i_edit_cancel;
    private RtxImageView        i_info;

    private FrameLayout f_back;
    private RtxImageView        i_next;
    private RtxImageView        i_muscle;
    private RtxImageView        i_bodyfat;
    private RtxTextView         t_muscle;
    private RtxTextView         t_bodyfat;
    private RtxDoubleStringView[]         t_data;
    private RtxTextView[]         t_data_name;
    private RtxImageView[]        i_edit;

    private int istr_list[] ;
    private int imax;

    private int i_gap_null = 40;
    private int i_gap_data = 20;
    private float fscale = 0.77f;

    private int iedit_mode = 0; //0 view; 1 edit
    private boolean blogin = false;

    public Main02LayoutE(Context context, MainActivity mMainActivity) {
        super(context);

        MyLog.d("===>  Main02LayoutE");

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
        istr_list = mMainActivity.mMainProcTreadmill.bodymanagerProc.ipage_list[1];
        imax = istr_list.length;

        init_Title();
        init_BackHome();
        init_BackExercise();

        if(t_date == null)    { t_date = new RtxTextView(mContext);     }
        if(t_date_data == null)    { t_date_data = new RtxTextView(mContext);     }
        if(i_heart_rate == null)    { i_heart_rate = new RtxImageView(mContext);    }
        if(t_heart_rate == null)    { t_heart_rate = new RtxTextView(mContext);     }
        if(i_edit_pen == null)       { i_edit_pen = new RtxImageView(mContext);   }
        if(i_edit_cancel == null)       { i_edit_cancel = new RtxImageView(mContext);   }
        if(i_info == null)       { i_info = new RtxImageView(mContext);   }

        if(f_back == null)    { f_back = new FrameLayout(mContext);     }
        if(i_next == null)       { i_next = new RtxImageView(mContext);   }
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

    private void init_event()
    {
        int iLoop ;

        i_edit_pen.setOnClickListener(mButtonListener);
        i_edit_cancel.setOnClickListener(mButtonListener);
        i_info.setOnClickListener(mButtonListener);
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);

        i_next.setOnClickListener(mButtonListener);
        i_muscle.setOnClickListener(mButtonListener);
        i_bodyfat.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < imax; iLoop++) {
            i_edit[iLoop].setOnClickListener(mButtonListener);
        }

    }

    private void add_View() {
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
        int ix_next = 357 + ix_shift * 2;   //物件間距

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
        ix = 255;//By Alan
        iy = 40;
        iw = 58;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_edit_pen, R.drawable.bd_edit_done, ix, iy, iw, ih, ipadding);



        ix = 330;//By Tommy 20200603
        iy = 40;
        iw = 82;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_edit_cancel, R.drawable.bd_edit_cancel, ix, iy, iw, ih, ipadding);
        i_edit_cancel.setVisibility(INVISIBLE);





//        menu
        vSetTitleText(R.string.body_manager);

//        info
        ix = 870;//By Alan
        iy = 36;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_info, R.drawable.info_icon, ix, iy, iw, ih, ipadding);

//        frame
        ix = 0;
        iy = 100;
        iw = 1280;
        ih = 800;
        addRtxViewToLayout(f_back, ix, iy, iw, ih, Common.Color.bd_exercise_back);

//        first line
        ix = 145 - ix_shift;
        iy = iGet_Scale(40);
        iw = 270 + ix_shift*2;
        ih = iGet_Scale(90);
        fsize = fGet_Scale(66.66f);
        fsize_unit = fGet_Scale(33.33f);
        fsize_temp = fGet_Scale(21.00f);//By Alan
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == 3)
            {
                //        second line
                ix = 145 - ix_shift;
                iy = iGet_Scale(226);
            }

            addRtxDoubleStringView(f_back, t_data[iLoop], ix, iy, iw, ih);
            sdata = BodyManagerFunc.s_get_drawdata(mContext, istr_list[iLoop]);
            if(sdata.compareTo("") == 0)
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
          //  t_data[iLoop].setText(sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, istr_list[iLoop]));

            MyLog.d("sdata===>"+sdata);
            String v_sdata = sdata;
            if(MyUtils.str2double(v_sdata)==0){
                v_sdata=" ";
            }
            t_data[iLoop].setText(v_sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, istr_list[iLoop]));


            ix_temp = ix + ix_shift;
            iy_temp = iy + ih;
            iw_temp = iw - ix_shift*2 ;
            ih_temp = iGet_Scale(70);
            sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, istr_list[iLoop]);
            addRtxTextView(f_back, t_data_name[iLoop], sdata.toUpperCase(), fsize_temp, Common.Color.bd_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);
            ix_temp = ix + 117 + ix_shift;
            iy_temp += ih_temp;
            iw_temp = 36;
            ih_temp = iGet_Scale(27);
            addRtxImage(f_back, i_edit[iLoop], R.drawable.next_arrow_edit, ix_temp, iy_temp, iw_temp, ih_temp, ipadding, null);
            ix += ix_next - ix_shift * 2;
        }

//        muscle
        ix = 310;
        iy = iGet_Scale(445);
        iw = iGet_Scale(180);
        ih = iGet_Scale(180);
        fsize = fGet_Scale(23.33f);
        addRtxImage(f_back, i_muscle, R.drawable.bd_muscle, ix, iy, iw, ih, 0, null);
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = iGet_Scale(50);//By Alan
        sdata = LanguageData.s_get_string(mContext, R.string.muscle_mass);
        addRtxTextView(f_back, t_muscle, sdata, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

//        body fat
        ix += iw + 260;
        addRtxImage(f_back, i_bodyfat, R.drawable.bd_bodyfat, ix, iy, iw, ih, 0, null);
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = iGet_Scale(50);//By Alan
        sdata = LanguageData.s_get_string(mContext, R.string.body_fat);
        addRtxTextView(f_back, t_bodyfat, sdata, fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        //        next page
        ix = 60;
        iy = iGet_Scale(230);
        iw = iGet_Scale(85);
        ih = iGet_Scale(85);
        addRtxImage(f_back, i_next, R.drawable.triangle_prev, ix, iy, iw, ih, 0, null);

        vData_refresh();
    }

    /////////////////refresh result/////////////////////////////
    private void vData_refresh()
    {
        int imch_type = BodyManagerFunc.iget_body_machine_type();

        if(imch_type == 0)//Circle
        {
            if(iedit_mode != 1)
            {
                if(blogin)
                {
                    vData_refresh_IBA_NA();
                }
            }
        }
        else //etc.
        {
        }

        vsetPenMode();
    }

    private void vData_refresh_IBA_NA()
    {
        int iLoop;
        String sdata;

        sdata = Consts.IBA_NA_DEF;  // 20200603 Tommy
//        sdata = "N/A";
        for(iLoop = 0; iLoop < imax; iLoop++) {
            t_data[iLoop].setText(sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, istr_list[iLoop]));

        }
    }

    private void vData_refresh_IBA_Zero()
    {
        int iLoop;
        String sdata;

        sdata =  Consts.IBA_Zero;  // 20200615 Tommy
        for(iLoop = 0; iLoop < imax; iLoop++) {
            t_data[iLoop].setText(sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, istr_list[iLoop]));
        }
    }

    private void vsetPenMode()
    {
        if(iedit_mode == 0)
        {

            if( mMainActivity.mMainProcTreadmill.bodymanagerProc.mhaveChangeData){
                i_edit_cancel.setVisibility(VISIBLE);
            }else{
                i_edit_cancel.setVisibility(INVISIBLE);
            }


            v_icon_HandtoArrow();
        }
        else if(iedit_mode == 1)
        {
            i_edit_cancel.setVisibility(INVISIBLE);
            v_icon_ArrowtoHand();
        }

        if(blogin && iedit_mode <=1)
        {
            i_edit_pen.setVisibility(VISIBLE);
        }
        else
        {
            i_edit_cancel.setVisibility(INVISIBLE);
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
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClicknext()
    {
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);
    }

    private void vClickbdinfo()
    {
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_BME_INFO);
    }

    private void vClickmuscle()
    {
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Select(istr_list[0]);
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_MUSCLE);
    }

    private void vClickbdfat()
    {
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Select(istr_list[0]);
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_BODYFAT);
    }

    private void vClickitem(int isel)
    {
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Select(istr_list[isel]);

        if(BodyManagerFunc.s_get_drawdata(mContext, istr_list[isel]).compareTo("") != 0 ) {
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_HIS_GET);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_ALLVIEW);
        }
    }

    private void vClickitemEdit(int isel)
    {
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Select(istr_list[isel]);
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_INPUT);
    }

    private void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.body_manager);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.bodymanager_dialog_info);

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_body_manager, -1, stitle01, null, sinfo01, null, "bd_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFOE);
    }

    private void vEditPen_icon()
    {
        if(iedit_mode == 0)
        {
            iedit_mode = 1;
            v_icon_ArrowtoHand();
            int imch_type = BodyManagerFunc.iget_body_machine_type();
            if(imch_type == 0)//Circle
            {
                vData_refresh_IBA_Zero();
            }
            else //etc.
            {
            }
        }
        else if(iedit_mode == 1)
        {
            iedit_mode = 0;
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Select(istr_list[0]);
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_BD_SET);
        }

        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Edit_mode(iedit_mode);
    }

    private void v_icon_ArrowtoHand()
    {
        int iLoop;

        i_edit_pen.setImageResource(R.drawable.bd_edit_done);
        if( mMainActivity.mMainProcTreadmill.bodymanagerProc.mhaveChangeData){
            i_edit_cancel.setVisibility(VISIBLE);
        }else{
            i_edit_cancel.setVisibility(INVISIBLE);
        }
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

        i_edit_pen.setImageResource(R.drawable.bd_edit_pen);
        i_edit_cancel.setVisibility(INVISIBLE);
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

    private void vBackToExercisePage()
    {
        mMainActivity.mMainProcTreadmill.v_Goto_ExercisePage();
    }


    private void vEditPen_cancel_icon() {
        iedit_mode = 0;
        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Edit_mode(iedit_mode);
        mMainActivity.mMainProcTreadmill.bodymanagerProc.mhaveChangeData=false;
        v_icon_HandtoArrow();
        CloudDataStruct.BodyIndexData_05.v_BodyIndex_Undo();

        init();
        removeAllViews();
        display();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_edit_pen)                 {

                MyLog.d("===>  Main02LayoutE i_edit_pen click");

                vEditPen_icon(); }
            else if(v == i_edit_cancel)                 { vEditPen_cancel_icon(); }
            else if(v == i_info)                { vShowInfoDialog(); }
            else if(v == imageView_BackHome)    { vClickMain(); }
            else if(v == imageView_ReturnExercisePage)    { vBackToExercisePage(); }
            else if(v == i_next)                { vClicknext(); }
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
