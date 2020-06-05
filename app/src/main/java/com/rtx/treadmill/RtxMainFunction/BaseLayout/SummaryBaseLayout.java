package com.rtx.treadmill.RtxMainFunction.BaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.UartDevice.UartData;

/**
 * Created by chasechang on 3/22/17.
 */

abstract public class SummaryBaseLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;
    private Context mContext;
    private ButtonListener mButtonListener;
    private MainActivity mMainActivity;
    private RtxImageView i_logout;
    private RtxTextView t_logout;
    private RtxTextView t_logout_info;
    private RtxFillerTextView f_done;
    private RtxDoubleStringView[] t_data;
    private RtxTextView[] t_info;
    private RtxTextView t_smode;

    public int istr_list[]={  R.string.cardio_type  ,  R.string.duration  ,  R.string.distance ,  R.string.calories ,  R.string.avg_pace  ,  R.string.best_pace  ,  R.string.avg_heart_rate ,  R.string.max_heart_rate };
    private int imax=istr_list.length;

    public SummaryBaseLayout(Context context, MainActivity mMainActivity){
        super(context);
        mContext=context;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init(){
        if(mButtonListener == null){ mButtonListener=new ButtonListener(); }
        UartData.vUartCmd_61(20, "2"); //By Alan
    }

    @Override
    public void display(){
        init_View();
        init_CustomerView();    //Let user can override this.
        init_event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.
    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    public void init_View(){
        int iLoop ;

        init_Title();
        if(i_logout == null){ i_logout = new RtxImageView(mContext); }
        if(t_logout == null){ t_logout = new RtxTextView(mContext); }
        if(t_logout_info == null){ t_logout_info = new RtxTextView(mContext); }
        if(f_done == null){ f_done = new RtxFillerTextView(mContext); }
        if(t_data == null){
            t_data=new RtxDoubleStringView[imax];
            for(iLoop=0; iLoop < imax; iLoop++){ t_data[iLoop]=new RtxDoubleStringView(mContext); }
        }
        if(t_info == null){
            t_info=new RtxTextView[imax];
            for(iLoop=0; iLoop < imax; iLoop++){ t_info[iLoop] = new RtxTextView(mContext); }
        }
        if(t_smode == null){ t_smode=new RtxTextView(mContext); }
        UartData.vUartCmd_61(20, "2"); //By Alan
    }

    public void init_event(){
        i_logout.setOnClickListener(mButtonListener);
        f_done.setOnClickListener(mButtonListener);
    }

    public void add_View(){
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding=0;
        int icolor;
        int ix_shift;
        int ifirst_count=4;
        float fval;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.gerkin_protocol);
        vSetTitleText(sdata.toUpperCase());

//      logout
        if(CloudDataStruct.CloudData_20.bLogin){
            ix = 61;
            iy = 42;
            iw = 37;
            ih = 30;
            addRtxImagePaddingViewToLayout(i_logout, R.drawable.main_logout_icon, ix, iy, iw, ih, ipadding);

            ix += iw;
            iw = 150;
            fsize = 23.33f;
            sdata = LanguageData.s_get_string(mContext, R.string.logout);
            addRtxTextViewToLayout(t_logout, sdata.toUpperCase(), fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

            ix = 340;
            iy = 80;//By Alan
            iw = 600;
            fsize = 20f;
            sdata = LanguageData.s_get_string(mContext, R.string.please_remember);
            addRtxTextViewToLayout(t_logout_info, sdata.toUpperCase(), fsize, Common.Color.physical_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, 60);//By Alan
        }

//      done
        ix = 1035;
        iy = 36;
        iw = 180;//By Alan
        ih = 43;
        fsize = 21.50f;//By Alan
        addRtxTextViewToLayout(f_done, R.string.done, fsize, Common.Color.physical_word_yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.physical_word_yellow);
        f_done.setMode(3);

//      detail
        ix = 90;
        iy = 535;
        iw = 275;
        ih = 50;
        ix_shift = 275;
        fsize = 43.32f;
        fsize_unit = 22f;
        sdata = "";
        sunit = "";
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count) {
                ix = 90;
                iy += 140;
            }

            if(iLoop == 0)
            {
                sdata = EngSetting.S_Get_ExerciseName();
                //sdata = LanguageData.s_get_string(mContext, R.string.treadmill);
                sunit = "";
            }
            else if(iLoop == 1)
            {
                sdata = Rtx_Calendar.s_trans_integer(5, ExerciseGenfunc.i_get_total_time());
                sunit = "";
            }
            else if(iLoop == 4)
            {
                fval = ExerciseGenfunc.f_get_ave_pace();
                if(EngSetting.getUnit() != EngSetting.UNIT_METRIC)
                {
                    fval *= EngSetting.mile2km;
                }
                sdata = Rtx_Calendar.s_trans_integer(5, (int)fval);
                sunit = "";
            }
            else if(iLoop == 5)
            {
                fval = ExerciseGenfunc.f_get_best_pace();
                if(EngSetting.getUnit() != EngSetting.UNIT_METRIC)
                {
                    fval *= EngSetting.mile2km;
                }
                sdata = Rtx_Calendar.s_trans_integer(5, (int)fval);
                sunit = "";
            }
            else if(iLoop == 2)
            {
                fval = ExerciseGenfunc.f_get_total_distance_show_km();
                sdata = EngSetting.Distance.getValString(fval);
                sunit = EngSetting.Distance.getUnitString(mContext);
            }
            else if(iLoop == 3)
            {
                sdata = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_total_calories(), 0);
                sunit = LanguageData.s_get_string(mContext, R.string.kcal);
            }
            else if(iLoop == 6){
                sdata = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_ave_heartrate(), 0);
                sunit = LanguageData.s_get_string(mContext, R.string.bpm);
            }
            else if(iLoop == 7){
                sdata = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_max_heartrate(), 0);
                sunit = LanguageData.s_get_string(mContext, R.string.bpm);
            }

            if(iLoop == 0){
                fsize = 28.32f;//By Alan
//                fsize = 43.32f;
                addRtxDoubleStringView(t_data[iLoop], 0, iy, iw + ix * 2, ih);
                t_data[iLoop].setGap(20);
                t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
                t_data[iLoop].setText(sdata.toUpperCase(), sunit.toLowerCase());
                t_data[iLoop].setVisibility(INVISIBLE);
                addRtxTextViewToLayout(t_smode, sdata.toUpperCase(), fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, ix-20, iy-ih, iw+40, ih*2);

            }
            else
            {
                fsize = 28.32f;//By Alan
                addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
                t_data[iLoop].setGap(20);
                t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
                t_data[iLoop].setText(sdata.toUpperCase(), sunit.toLowerCase());
            }

            ix += ix_shift;
        }

        ix = 90;
        iy = 585;
        iw = 275;
        ih = 50;//By Alan
        ix_shift = 275;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            if(iLoop == ifirst_count) {
                ix = 90;
                iy += 140;
            }

            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop]);
            addRtxTextViewToLayout(t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

            ix += ix_shift;
        }

    }

    public void v_set_type_name(String sa, String sb)
    {
        t_info[0].setText(sa);
        //t_data[0].setText(sb, "");
        t_smode.setText(sb);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

    abstract protected void v_logout();

    abstract protected void v_done();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_logout)
            {
                v_logout();
            }
            else if(v == f_done)
            {
                v_done();
            }
        }
    }
}
