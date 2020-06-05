package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * TODO: document your custom view class.
 */
public class RtxLap extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private int COLOR_BACKGROUND = Common.Color.exercise_backgroud;

    private float fscale = 1f;

    public RtxTextView t_lap_num ;
    private RtxTextView t_lap ;
    public RtxLapCircle i_lap ;

    public RtxLap(Context context){
        super(context);

        mContext = context;

        setBackgroundColor(COLOR_BACKGROUND);

        if(t_lap_num == null)     {   t_lap_num = new RtxTextView(mContext);}
        if(t_lap == null)     {   t_lap = new RtxTextView(mContext);}
        if(i_lap == null)     {   i_lap = new RtxLapCircle(mContext);}

    }

    public void init(float fscale)
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        this.fscale = fscale;
        ix = -1;
        iy = iGet_Scale(100);
        iw = iGet_Scale(850);
        ih = iGet_Scale(380);
        fsize = fGet_Scale(150f);
        addViewToLayout(i_lap, ix, iy, iw, ih);
        sdata = "1";
        addRtxTextView(null, t_lap_num, sdata.toUpperCase(), fsize, Common.Color.exercise_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = iGet_Scale(535);
        iy = iGet_Scale(370);
        iw = iGet_Scale(200);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(33.33f);
        sdata = LanguageData.s_get_string(mContext, R.string.lap);
        addRtxTextView(null, t_lap, sdata.toUpperCase(), fsize, Common.Color.exercise_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

    }

    ////////////////////////////////////////////////////////
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
