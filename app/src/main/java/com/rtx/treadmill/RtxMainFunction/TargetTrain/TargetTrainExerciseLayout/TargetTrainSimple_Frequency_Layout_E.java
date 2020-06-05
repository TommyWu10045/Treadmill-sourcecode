package com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;

import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainSimple_Frequency_Layout;
import com.rtx.treadmill.RtxView.RtxImageView;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Frequency_Layout_E extends TargetTrainSimple_Frequency_Layout {

    public TargetTrainSimple_Frequency_Layout_E(Context context, int iMode) {
        super(context,iMode);
    }

    @Override
    protected void add_View()
    {
        addRtxImageViewToLayout(imageView_Face, R.drawable.target_normal_face_middle, -1, 167, 67, 67);
    }

    protected void add_DaysPerWeekView(int iDays , int iAchievementDays)
    {
        int iIconTotalW = 0;
        int iLayoutW = 426;
        int iGap = 19;
        int iPosX = 0;
        int iPosY = 68;

        int iW = 28;
        int iH = 28;

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
