package com.rtx.treadmill.RtxMainFunction.Exercise.Info;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Draw;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;
import com.rtx.treadmill.UartDevice.UartData;


/**
 * Created by chasechang on 3/22/17.
 */

public class ChangeValLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxView v_Background1;
    private RtxTextView[]      t_speed;
    private int imax = 2;
    private RtxTextView      t_infomation;

    public ChangeValLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        this.mMainActivity = mMainActivity;
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
        if(v_Background1 == null)        { v_Background1 = new RtxView(mContext);   }
        if(t_speed == null) {
            t_speed = new RtxTextView[imax];
            for(iLoop = 0; iLoop < imax; iLoop++)
            {
                t_speed[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_infomation == null) {t_infomation = new RtxTextView(mContext);}
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        int iLoop ;

        ix = 273;
        iy = 120;
        iw = 733 ;
        ih = 27;
        fsize = 25f;

        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxTextView(this, t_speed[iLoop], "", fsize, Common.Color.info_word_green, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.background);
            t_speed[iLoop].setEnabled(false);
            iy += 27;
        }

        ix = 40;
        iy = 82;
        iw = 800 ;
        ih = 65;
        fsize = 25f;
        addRtxTextView(this, t_infomation, "", fsize, Common.Color.info_word_green, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.background);
        t_infomation.setEnabled(false);

    }

    private void v_update_speed(int icount)
    {
        float fval = Change_UI_Info.fnext[0];
        int ival = Change_UI_Info.i_countdown - (icount * EngSetting.DEF_UNIT_TIME / 1000);

        if(ival <= 0)
        {
            UartData.set_uart_speed(fval);
            Change_UI_Info.v_clear_stage(Change_UI_Info.istate_speed);
        }

        return;
    }

    private void v_update_incline(int icount)
    {
        float fval = Change_UI_Info.fnext[1];
        int ival = Change_UI_Info.i_countdown - (icount * EngSetting.DEF_UNIT_TIME / 1000);

        if(ival <= 0)
        {
            UartData.set_uart_incline(fval);
            Change_UI_Info.v_clear_stage(Change_UI_Info.istate_incline);
        }

        return;
    }

    private void v_update_information(int icount)
    {
        int ival = Change_UI_Info.i_countdown_infomation - (icount * EngSetting.DEF_UNIT_TIME / 1000);

        if(ival <= 0)
        {
            Change_UI_Info.v_clear_stage(Change_UI_Info.istate_sinfo);
        }

        return;
    }

    private String s_get_speed(int icount)
    {
        String sdata;
        int ival;

        ival = Change_UI_Info.i_countdown - (icount * EngSetting.DEF_UNIT_TIME / 1000);

        //if(ival <= 2)   //Circle want to only show 3 Sec
       // {
            ival = 5;
        //}

        if (Change_UI_Info.fcurrent[0] > Change_UI_Info.fnext[0]) {
            //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
            if(Change_UI_Info.fnext[0] < EngSetting.f_Get_Min_Speed() && (Change_UI_Info.fnext[0] + 1) > EngSetting.f_Get_Min_Speed())
            {
                sdata = LanguageData.s_get_string(mContext, R.string.speed_decreases_to) + " "
                        + EngSetting.Distance.getValString(EngSetting.f_Get_Min_Speed()) + " "
                        + EngSetting.Distance.getSpeedUnitString(mContext) + " "
                        + LanguageData.s_get_string(mContext, R.string.in) + " "
                        + Rtx_TranslateValue.sInt2String(ival) + " "
                        + LanguageData.s_get_string(mContext, R.string.seconds);
            }
            else
            {
                sdata = LanguageData.s_get_string(mContext, R.string.speed_decreases_to) + " "
                        + EngSetting.Distance.getValString(Change_UI_Info.fnext[0]) + " "
                        + EngSetting.Distance.getSpeedUnitString(mContext) + " "
                        + LanguageData.s_get_string(mContext, R.string.in) + " "
                        + Rtx_TranslateValue.sInt2String(ival) + " "
                        + LanguageData.s_get_string(mContext, R.string.seconds);
            }
        }
        else if (Change_UI_Info.fcurrent[0] < Change_UI_Info.fnext[0]) {
            //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
            if(Change_UI_Info.fnext[0] > EngSetting.f_Get_Max_Speed() && (Change_UI_Info.fnext[0] - 1) < EngSetting.f_Get_Max_Speed())
            {
                sdata = LanguageData.s_get_string(mContext, R.string.speed_increases_to) + " "
                        + EngSetting.Distance.getValString(EngSetting.f_Get_Max_Speed()) + " "
                        + EngSetting.Distance.getSpeedUnitString(mContext) + " "
                        + LanguageData.s_get_string(mContext, R.string.in) + " "
                        + Rtx_TranslateValue.sInt2String(ival) + " "
                        + LanguageData.s_get_string(mContext, R.string.seconds);
            }
            else
            {
                sdata = LanguageData.s_get_string(mContext, R.string.speed_increases_to) + " "
                        + EngSetting.Distance.getValString(Change_UI_Info.fnext[0]) + " "
                        + EngSetting.Distance.getSpeedUnitString(mContext) + " "
                        + LanguageData.s_get_string(mContext, R.string.in) + " "
                        + Rtx_TranslateValue.sInt2String(ival) + " "
                        + LanguageData.s_get_string(mContext, R.string.seconds);
            }
        }
        else
        {
            sdata = "";
        }

        return sdata;
    }

    private String s_get_incline(int icount)
    {
        String sdata;
        int ival;

        ival = Change_UI_Info.i_countdown - (icount * EngSetting.DEF_UNIT_TIME / 1000);

       // if(ival <= 2)   //Circle want to only show 3 Sec
       // {
            ival = 5;
       // }

        if (Change_UI_Info.fcurrent[1] > Change_UI_Info.fnext[1]) {
            sdata = LanguageData.s_get_string(mContext, R.string.incline_decreases_to) + " "
                    + Rtx_TranslateValue.sFloat2String(Change_UI_Info.fnext[1],1) + " "
                    + LanguageData.s_get_string(mContext, R.string.in) + " "
                    + Rtx_TranslateValue.sInt2String(ival) + " "
                    + LanguageData.s_get_string(mContext, R.string.seconds);
        }
        else if (Change_UI_Info.fcurrent[1] < Change_UI_Info.fnext[1]) {
            sdata = LanguageData.s_get_string(mContext, R.string.incline_increases_to) + " "
                    + Rtx_TranslateValue.sFloat2String(Change_UI_Info.fnext[1],1) + " "
                    + LanguageData.s_get_string(mContext, R.string.in) + " "
                    + Rtx_TranslateValue.sInt2String(ival) + " "
                    + LanguageData.s_get_string(mContext, R.string.seconds);
        }
        else
        {
            sdata = "";
        }

        return sdata;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Refresh(int icount) {
        String sdata;
        int iLoop = 0;

        if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_speed)) {
            sdata = s_get_speed(icount);
            t_speed[iLoop].setText(sdata);

            v_update_speed(icount);
            //修正按SKIP會閃一下message的問題
            if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_speed)) {
                if (Change_UI_Info.ispeed_flash == 0)
                {
                    t_speed[iLoop].setVisibility(VISIBLE);
                    iLoop++;
                }
                else {
                    if ((icount % Change_UI_Info.ispeed_flash) <= (Change_UI_Info.ispeed_flash / 2)) {
                        t_speed[iLoop].setVisibility(VISIBLE);
                        iLoop++;
                    } else {
                        t_speed[iLoop].setVisibility(INVISIBLE);
                    }
                }
            }
            else
            {
                t_speed[iLoop].setVisibility(INVISIBLE);
            }
        } else {
            t_speed[iLoop].setVisibility(INVISIBLE);
        }

        if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_incline)) {
            sdata = s_get_incline(icount);
            t_speed[iLoop].setText(sdata);
            v_update_incline(icount);
            //修正按SKIP會閃一下message的問題
            if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_incline)) {
                if (Change_UI_Info.iinclne_flash == 0)
                {
                    t_speed[iLoop].setVisibility(VISIBLE);
                    iLoop++;
                }
                else {
                    if ((icount % Change_UI_Info.iinclne_flash) <= (Change_UI_Info.iinclne_flash / 2)) {
                        t_speed[iLoop].setVisibility(VISIBLE);
                        iLoop++;
                    } else {
                        t_speed[iLoop].setVisibility(INVISIBLE);
                    }
                }
            }
            else
            {
                t_speed[iLoop].setVisibility(INVISIBLE);
            }
        } else {
            t_speed[iLoop].setVisibility(INVISIBLE);
        }

        for( ; iLoop < imax; iLoop++)
        {
            t_speed[iLoop].setVisibility(INVISIBLE);
        }

        if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_sinfo)) {
            sdata = Change_UI_Info.sinfomation;
            t_infomation.setText(sdata);
            v_update_information(icount);

            int iY = 120;
            int iHeight = 80;
            //float fSize = 20f;
            //int iWidth = Rtx_Draw.iGetTextWidth(mContext, sdata, Common.Font.Relay_Black, fSize);
			int iWidth=733;
//            if(iWidth > 760)
//            {
//                iWidth = 760;
//            }
            addViewToLayout(this, t_infomation, 273, iY, iWidth, iHeight);

            //修正按SKIP會閃一下message的問題
            if (Change_UI_Info.b_get_stage(Change_UI_Info.istate_sinfo)) {
                if (Change_UI_Info.iinfomation_flash == 0)
                {
                    t_infomation.setVisibility(VISIBLE);
                }
                else {
                    if ((icount % Change_UI_Info.iinfomation_flash) <= (Change_UI_Info.iinfomation_flash -15)) {
                        t_infomation.setVisibility(VISIBLE);
                    } else {
                        t_infomation.setVisibility(INVISIBLE);
                    }
                }
            }
            else
            {
                t_infomation.setVisibility(INVISIBLE);
            }
        } else {
            t_infomation.setVisibility(INVISIBLE);
        }
    }
}

