package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainDetail_Calories_Layout;



/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Calories_Layout_E extends TargetTrainDetail_Calories_Layout {


    public TargetTrainDetail_Calories_Layout_E(Context context, MainActivity mMainActivity, int iMode) {
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
        addRtxImageViewToLayout(imageView_icon, R.drawable.target_cal_icon, 166, 218, 46, 58);

        addRtxTextViewToLayout(textView_CurrentText, R.string.calories , 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 125, 360, 129, 56);
        addRtxTextViewToLayout(textView_PercentVal , 109.3f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 140, 337, 337);

        addViewToLayout(doubleStringView_CurrentVal,10,258,256,146);
        doubleStringView_CurrentVal.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,60f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,29.75f);

        addViewToLayout(mRtxPercentCircleView, -1, 140, 337, 337);

        add_Botton_Item_1(23,262);
        add_Botton_Item_2(335,262);
        add_Botton_Item_3(647,262);
        add_Botton_Item_4(960,262);

        set_Bottom_Item_1("2000","kcal",R.string.target_calories);
        set_Bottom_Item_2("1999","kcal",R.string.current_calories);
        set_Bottom_Item_3("330","days",R.string.target_days);
        set_Bottom_Item_4("300","days",R.string.current_days);
    }

}
