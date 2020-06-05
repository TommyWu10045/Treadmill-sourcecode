package com.rtx.treadmill.RtxMainFunction.Exercise.CountDown;

import android.content.Context;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;


/**
 * Created by chasechang on 3/22/17.
 */

public class CountDown01Layout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxImageView      i_countdown;

    public CountDown01Layout(Context context, MainActivity mMainActivity) {
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
        iy = 176;
        iw = 454;
        ih = 454;
        addRtxImagePaddingViewToLayout(i_countdown, R.drawable.exsercise_01, ix, iy, iw, ih, 0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

