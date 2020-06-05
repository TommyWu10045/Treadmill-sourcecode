package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * TODO: document your custom view class.
 */
public class RtxProfile extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private boolean bXScale = true;
    private boolean bYScale = true;

    private int COLOR_BACKGROUND = Common.Color.exercise_backgroud;

    private float fscale = 1f;

    public RtxTextView t_speed ;
    public RtxImageView i_speed ;

    public RtxTextView t_incline ;
    public RtxImageView i_incline ;

    public RtxImageView i_min ;
    public RtxTextView t_min ;

    public RtxProfileXBase i_xprofile ;
    public RtxProfileYBase i_yprofile ;
    public RtxProfileChart i_profile ;


    public RtxProfile(Context context){
        super(context);

        mContext = context;

        setBackgroundColor(COLOR_BACKGROUND);

        if(t_speed == null) { t_speed = new RtxTextView(mContext);}
        if(i_speed == null) { i_speed = new RtxImageView(mContext);}
        if(t_incline == null) { t_incline = new RtxTextView(mContext);}
        if(i_incline == null) { i_incline = new RtxImageView(mContext);}
        if(i_min == null) { i_min = new RtxImageView(mContext);}
        if(t_min == null) { t_min = new RtxTextView(mContext);}

        if(i_xprofile == null) { i_xprofile = new RtxProfileXBase(mContext);}
        if(i_yprofile == null) { i_yprofile = new RtxProfileYBase(mContext);}
        if(i_profile == null) { i_profile = new RtxProfileChart(mContext);}

    }

    public void init(float fscale)
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        this.fscale = fscale;

        //        speed lable
        ix = iGet_Scale(0);
        iy = iGet_Scale(10);
        iw = iGet_Scale(270);
        ih = iGet_Scale(30);
        fsize = fGet_Scale(14.67f);
        sdata = LanguageData.s_get_string(mContext, R.string.speed);
        addRtxTextViewToLayout(t_speed, sdata.toUpperCase(), fsize, Common.Color.exercise_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = iGet_Scale(121);
        iy = iGet_Scale(60);
        iw = iGet_Scale(28);
        ih = iGet_Scale(14);
        addRtxImage(null, i_speed, R.drawable.speed_icon, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //        incline lable
        ix = iGet_Scale(20);
        iy = iGet_Scale(395);
        iw = iGet_Scale(80);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(20.00f);
        sdata = LanguageData.s_get_string(mContext, R.string.incline_100);
        addRtxTextViewToLayout(t_incline, sdata.toUpperCase(), fsize, Common.Color.exercise_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = iGet_Scale(85);
        iy = iGet_Scale(405);
        iw = iGet_Scale(20);
        ih = iGet_Scale(15);
        addRtxImage(null, i_incline, R.drawable.exercise_incline, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //        min lable
        ix = iGet_Scale(165);
        iy = iGet_Scale(425);
        iw = iGet_Scale(884);
        ih = iGet_Scale(2);
        addRtxImage(null, i_min, R.drawable.line_white, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ix = iGet_Scale(1060);
        iy = iGet_Scale(405);
        iw = iGet_Scale(40);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(18f);
        sdata = LanguageData.s_get_string(mContext, R.string.min);
        addRtxTextViewToLayout(t_min, sdata.toLowerCase(), fsize, Common.Color.exercise_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        if(bXScale) {
            //        xscale
            ix = iGet_Scale(165);
            iy = iGet_Scale(425);
            iw = iGet_Scale(870);
            ih = iGet_Scale(40);
            addViewToLayout(i_xprofile, ix, iy, iw + 14, ih);
            i_xprofile.init(ExerciseData.ilist_virtual_num, fscale, ix, iy, iw, ih);
        }

        if(bYScale) {
            //        yscale
            ix = iGet_Scale(80);
            iy = iGet_Scale(60);
            iw = iGet_Scale(65);
            ih = iGet_Scale(385);
            addViewToLayout(i_yprofile, ix, iy, iw, ih);
            i_yprofile.vSet_mode(0x01);
            i_yprofile.init(fscale, iGet_YMin(), iGet_YMax(), ix, iy, iw, ih, iGet_Scale(20));
        }

        //        speed/incline bar
        ix = iGet_Scale(182);
        iy = iGet_Scale(80);
        iw = iGet_Scale(870);
        ih = iGet_Scale(345);
        addViewToLayout(i_profile, ix, iy, iw, ih);

        i_profile.init(ExerciseData.ilist_virtual_num, iGet_YMin(), iGet_YMax(), fscale, ix, iy, iw, ih);
    }


    /////////////////////////////
    private int iGet_YMin()
    {
        int ival ;
        float fspeed ;
        float fincline ;
        float flevel ;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            fspeed = EngSetting.f_Get_Min_Speed();
            if (EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL) {
                fspeed *= EngSetting.km2mile;
            }
            fincline = EngSetting.f_Get_Min_Incline();

            if (fspeed < fincline) {
                ival = (int) (fspeed - 0.9);
            } else {
                ival = (int) (fincline - 0.9);
            }
        }
        else
        {
            flevel = EngSetting.f_Get_Min_level();

            ival = (int) (flevel - 0.9);
        }
        return ival;
    }

    private int iGet_YMax()
    {
        int ival ;
        float fspeed ;
        float fincline ;
        float flevel ;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            fspeed = EngSetting.f_Get_Max_Speed();
            if (EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
            {
                fspeed *= EngSetting.km2mile;
            }
            fincline = EngSetting.f_Get_Max_Incline();

            if (fspeed < fincline)
            {
                ival = (int) (fincline + 0.9);
            }
            else
            {
                ival = (int) (fspeed + 0.9);
            }
        }
        else
        {
            flevel = EngSetting.f_Get_Max_level();

            ival = (int) (flevel + 0.9);
        }
        return ival;
    }

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
