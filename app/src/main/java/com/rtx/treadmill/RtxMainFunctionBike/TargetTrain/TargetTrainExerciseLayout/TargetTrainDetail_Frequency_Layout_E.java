package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainDetail_Frequency_Layout;



/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Frequency_Layout_E extends TargetTrainDetail_Frequency_Layout {

    public TargetTrainDetail_Frequency_Layout_E(Context context, MainActivity mMainActivity, int iMode) {
        super(context , mMainActivity , iMode);
    }

    @Override
    public void display()
    {
        init_TargetBaseView();
        init_View();
        add_View();
        init_event();
    }

    @Override
    protected void add_View()
    {
        addRtxImageViewToLayout(imageView_Face, R.drawable.target_normal_face_big, -1, 217, 133, 133);
        addRtxTextViewToLayout(textView_WeekText, 32.71f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 478 - 50, 1280, 34 + 100);

        add_Botton_Item_1(48,218);
        add_Botton_Item_2(393,155);
        add_Botton_Item_3(721,159);
        add_Botton_Item_4(1053,132);

        set_Bottom_Item_1("3","days",R.string.target_days_weeks);
        set_Bottom_Item_2("5","weeks",R.string.target_weeks);
        set_Bottom_Item_3("","",R.string.current_week);
        set_Bottom_Item_4("1/3","",R.string.achievement);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void add_DaysPerWeekView(int iDays , int iAchievementDays)
    {
        int iIconTotalW = 0;
        int iLayoutW = 1280;
        int iGap = 41;
        int iPosX = 0;
        int iPosY = 391;

        int iW = 56;
        int iH = 56;

        iIconTotalW = (iDays * iW) + ((iDays - 1) * iGap);
        iPosX = (iLayoutW - iIconTotalW) / 2;

        //remove_DaysView();

        {
            int iIndex = 0;

            for( ; iIndex < iDays ; iIndex ++ )
            {
                if(iIndex < iAchievementDays)
                {
                    addRtxImageViewToLayout(list_ImageViewDays.get(iIndex), R.drawable.target_freq_achievement_icon, iPosX, iPosY, iW, iH);
                }
                else
                {
                    addRtxImageViewToLayout(list_ImageViewDays.get(iIndex), R.drawable.target_freq_day_icon, iPosX, iPosY, iW, iH);
                }

                iPosX = iPosX + iW + iGap;
            }
        }
    }
}
