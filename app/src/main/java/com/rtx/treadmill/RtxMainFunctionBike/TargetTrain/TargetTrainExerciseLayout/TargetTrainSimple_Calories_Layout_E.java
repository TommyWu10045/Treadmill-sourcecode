package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainSimple_Calories_Layout;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Calories_Layout_E extends TargetTrainSimple_Calories_Layout {

    public TargetTrainSimple_Calories_Layout_E(Context context, int iMode) {
        super(context,iMode);
    }

    @Override
    protected void add_View()
    {
        {
            addViewToLayout(mRtxPercentCircleView, -1, 70, 186, 186);
        }

        {
            addRtxTextViewToLayout(textView_PercentVal, 53.87f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 70, 186, 186);
        }
    }
}
