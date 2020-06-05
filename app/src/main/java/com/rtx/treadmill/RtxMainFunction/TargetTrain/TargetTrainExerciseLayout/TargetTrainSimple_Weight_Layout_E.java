package com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainSimple_Weight_Layout;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxGaugeView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Weight_Layout_E extends TargetTrainSimple_Weight_Layout {

    public TargetTrainSimple_Weight_Layout_E(Context context, int iMode) {
        super(context,iMode);
    }

    @Override
    protected void add_View()
    {
        addViewToLayout(doubleStringView_Val,-1, 48, 426, 79);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,54f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,26.77f);
        doubleStringView_Val.setGap(16);

        addRtxTextViewToLayout(textView_LeftStr, 16.20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 120, 215, 52);
        addRtxTextViewToLayout(textView_RightStr, 16.20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 120, 215, 52);

        addRtxTextViewToLayout(textView_LeftVal, 35.09f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 145, 215, 67);
        addRtxTextViewToLayout(textView_RightVal, 35.09f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 145, 215, 67);

        addViewToLayout(gaugeView,-1,191,176,176);
    }
}
