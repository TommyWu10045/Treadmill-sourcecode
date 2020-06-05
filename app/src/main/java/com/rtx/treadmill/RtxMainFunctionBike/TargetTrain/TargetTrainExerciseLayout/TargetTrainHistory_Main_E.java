package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainHistory_Main;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainHistory_Main_E extends TargetTrainHistory_Main {

    public TargetTrainHistory_Main_E(Context context, MainActivity mMainActivity) {
        super(context,mMainActivity);
    }

    @Override
    protected void add_View()
    {
        removeView(fillerTextView_Edit);
    }

    @Override
    protected void add_GridView()
    {
        addViewToLayout(mGridView,31,183,1280-31-31,800-183-157);
    }

}
