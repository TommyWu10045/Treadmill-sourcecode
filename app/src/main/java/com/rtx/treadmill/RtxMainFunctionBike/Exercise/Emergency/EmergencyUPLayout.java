package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Emergency;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxImageView;


/**
 * Created by chasechang on 3/22/17.
 */

public class EmergencyUPLayout extends EmergencyLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxImageView      i_arrow;

    public EmergencyUPLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        if(i_arrow == null) {i_arrow = new RtxImageView(mContext);}
    }

    @Override
    protected void init_CustomerEvent()
    {

    }

    @Override
    protected void add_CustomerView()
    {
        int ix, iy, iw, ih;

        ix = 614;
        iy = 577;
        iw = 51 ;
        ih = 80;
        addRtxImagePaddingViewToLayout(i_arrow, R.drawable.emergency_arrow, ix, iy, iw, ih, 0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

