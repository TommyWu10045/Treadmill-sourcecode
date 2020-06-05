package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * TODO: document your custom view class.
 */
public class RtxExerciseSimplePreview extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private final int DEF_WIDTH = 418;
    private final int DEF_HEIGHT = 170;
    private final int DEF_X_GAP = 35;
    private final int DEF_Y_GAP = 40;


    private Context mContext;

    //private int COLOR_BACKGROUND = Common.Color.blue_2;
    private int COLOR_BACKGROUND = Common.Color.background_simple_preview;

    private float fscale = 1f;

    public RtxProfileChart i_profile ;

    // mode //0 : treadmill; etc : bike
    public RtxExerciseSimplePreview(Context context, int iMode){
        super(context);

        mContext = context;

        setBackgroundColor(COLOR_BACKGROUND);

        if(i_profile == null) { i_profile = new RtxProfileChart(mContext);}
        setViewTouchDisable(i_profile);
        i_profile.vSet_mode(iMode);
    }

    public void init(float fscale , int iDataCount)
    {
        int ix, iy, iw, ih;
        int iYMin = 0;
        int iYMax = 20;

        this.fscale = fscale;

        //        speed/incline bar
        ix = iGet_Scale(DEF_X_GAP);
        iy = iGet_Scale(DEF_Y_GAP);
        iw = iGet_Scale(DEF_WIDTH - (2 * DEF_X_GAP));
        ih = iGet_Scale(DEF_HEIGHT - (2 * DEF_Y_GAP));
        addViewToLayout(i_profile, ix, iy, iw, ih);

        i_profile.setBackgroundColor(COLOR_BACKGROUND);
        i_profile.init(iDataCount, iYMin , iYMax, fscale, ix, iy, iw, ih);
    }


    /////////////////////////////
    private int iGet_YMin()
    {
        int ival ;
        float fspeed ;
        float fincline ;

        fspeed = EngSetting.f_Get_Min_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
        {
            fspeed *= EngSetting.km2mile;
        }
        fincline = EngSetting.f_Get_Min_Incline();

        if(fspeed < fincline)
        {
            ival = (int) (fspeed - 0.9);
        }
        else
        {
            ival = (int) (fincline - 0.9);
        }


        return ival;
    }

    private int iGet_YMax()
    {
        int ival ;
        float fspeed ;
        float fincline ;

        fspeed = EngSetting.f_Get_Max_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
        {
            fspeed *= EngSetting.km2mile;
        }
        fincline = EngSetting.f_Get_Max_Incline();

        if(fspeed < fincline)
        {
            ival = (int) (fincline + 0.9);
        }
        else
        {
            ival = (int) (fspeed + 0.9);
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
