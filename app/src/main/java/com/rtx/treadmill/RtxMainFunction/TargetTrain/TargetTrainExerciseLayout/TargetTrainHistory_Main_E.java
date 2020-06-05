package com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.HistoryAdapter;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainHistory_Main;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainState;
import com.rtx.treadmill.RtxView.RtxFillerTextView;

import java.util.ArrayList;


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
