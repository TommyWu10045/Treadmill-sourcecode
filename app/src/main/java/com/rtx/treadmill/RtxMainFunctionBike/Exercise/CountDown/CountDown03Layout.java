package com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown;

import android.content.Context;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;


/**
 * Created by chasechang on 3/22/17.
 */

public class CountDown03Layout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxImageView      i_countdown;

    public CountDown03Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.background);
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
        i_countdown = new RtxImageView(mContext);
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        int ix, iy, iw, ih;

        ix = 413;
        iy = 172;
        iw = 454;
        ih = 455;
        addRtxImagePaddingViewToLayout(i_countdown, R.drawable.exsercise_03, ix, iy, iw, ih, 0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
