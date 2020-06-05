package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainSimple_BodyFat_Layout;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_BodyFat_Layout_E extends TargetTrainSimple_BodyFat_Layout {

    public TargetTrainSimple_BodyFat_Layout_E(Context context, int iMode) {
        super(context,iMode);
    }

    @Override
    protected void add_View()
    {
        addRtxTextViewToLayout(textView_Val, 54f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 48, 426, 79);

        addRtxTextViewToLayout(textView_LeftStr, 16.20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 120, 215, 52);
        addRtxTextViewToLayout(textView_RightStr, 16.20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 120, 215, 52);

        addRtxTextViewToLayout(textView_LeftVal, 35.09f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 145, 215, 67);
        addRtxTextViewToLayout(textView_RightVal, 35.09f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 145, 215, 67);

        addViewToLayout(gaugeView,-1,191,176,176);
    }
}
