package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainDetail_Weight_Layout;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Weight_Layout_E extends TargetTrainDetail_Weight_Layout {


    public TargetTrainDetail_Weight_Layout_E(Context context, MainActivity mMainActivity, int iMode) {
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
        addViewToLayout(doubleStringView_Val,-1,375,600,120);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,109.03f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,29.75f);
        doubleStringView_Val.setGap(16);

        addRtxTextViewToLayout(textView_LeftStr, 29.43f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 278, 101, 278, 80);
        addRtxTextViewToLayout(textView_RightStr, 29.43f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 723, 101, 278, 80);

        addRtxTextViewToLayout(textView_LeftVal, 57.53f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 278, 123, 278, 142);
        addRtxTextViewToLayout(textView_RightVal, 57.53f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 723, 123, 278, 142);

        addViewToLayout(gaugeView,-1,206,325,325);

        addRtxImageViewToLayout(imageView_InputIcon, R.drawable.target_input_button_disable, 131, 219, 118, 118);
        addRtxTextViewToLayout(textView_InputText, R.string.input_body_fat, 24f, Common.Color.gray_4, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 21, 311, 338, 124);

        add_Botton_Item_1(96,165);
        add_Botton_Item_2(402,182);
        add_Botton_Item_3(694,137);
        add_Botton_Item_4(992,153);

        set_Bottom_Item_1("100", EngSetting.Weight.getUnitString(mContext),R.string.target_weight);
        set_Bottom_Item_2("130",EngSetting.Weight.getUnitString(mContext),R.string.current_weight);
        set_Bottom_Item_3("330","days",R.string.target_days);
        set_Bottom_Item_4("300","days",R.string.current_days);
    }

    @Override
    protected void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
        imageView_Close.setOnClickListener(mButtonListener);
        imageView_Share.setOnClickListener(mButtonListener);
    }
}
