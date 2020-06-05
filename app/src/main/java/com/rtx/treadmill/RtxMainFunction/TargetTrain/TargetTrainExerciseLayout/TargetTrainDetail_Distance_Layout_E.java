package com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainDetailBaseLayout;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainDetail_Distance_Layout;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainState;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxPercentBlockView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Distance_Layout_E extends TargetTrainDetail_Distance_Layout {


    public TargetTrainDetail_Distance_Layout_E(Context context, MainActivity mMainActivity, int iMode) {
        super(context , mMainActivity , iMode);
    }



    @Override
    public void display()
    {
        init_TargetBaseView();
        init_View();
        add_TargetDaysLeftView();
        add_View();
        init_event();
    }

    @Override
    protected void add_View()
    {
        addViewToLayout(mRtxPercentBlockView, 371, 800-471, 537, 103);
        addRtxImageViewToLayout(imageView_TypeIcon, R.drawable.target_type_a, 620, 800-687, 41, 41);

        mRtxPercentBlockView.setPercentValue(0);

        addRtxTextViewToLayout(textView_PercentVal, 109.03f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 800-609-20, 1280, 78 + 40);
        addRtxTextViewToLayout(textView_CurrentText, R.string.current, 20.0f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 145 - 25, 800-383-20, 95 + 100, 15 + 40);

        textView_PercentVal.setText("0%");

        addViewToLayout(doubleStringView_CurrentVal,133-100,800-458-50,125+200,49+100);
        addViewToLayout(doubleStringView_TargetVal,766-100,800-337-50,141+200,49+100);

        doubleStringView_CurrentVal.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_TargetVal.setPaint(Common.Font.Relay_Black,Common.Color.white,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);

        doubleStringView_CurrentVal.setText("0", EngSetting.Distance.getUnitString(mContext));
        doubleStringView_TargetVal.setText("0",EngSetting.Distance.getUnitString(mContext));

        add_Botton_Item_1(57,183);
        add_Botton_Item_2(381,199);
        add_Botton_Item_3(700,137);
        add_Botton_Item_4(1007,154);

        set_Bottom_Item_1("0.0",EngSetting.Distance.getUnitString(mContext),R.string.target_distance);
        set_Bottom_Item_2("0.0",EngSetting.Distance.getUnitString(mContext),R.string.current_distance);
        set_Bottom_Item_3("-","days",R.string.target_days);
        set_Bottom_Item_4("-","days",R.string.current_days);
    }
}
