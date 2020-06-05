package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainSimple_Distance_Layout;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Distance_Layout_E extends TargetTrainSimple_Distance_Layout {

    public TargetTrainSimple_Distance_Layout_E(Context context, int iMode) {
        super(context,iMode);
    }

    @Override
    protected void add_View()
    {
        {
            addViewToLayout(mRtxPercentBlockView, 80, 145, 266, 51);
        }

        {
            addRtxTextViewToLayout(textView_PercentVal, 53.87f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 57, 426, 79);
            addRtxImageViewToLayout(imageView_TypeIcon, R.drawable.target_type_a, 277, 7, 41, 41);

        }
    }
}
