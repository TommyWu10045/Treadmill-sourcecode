package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;

import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Frequency_Layout extends Rtx_TargetTrainSimple_BaseLayout {

    protected Context mContext;

    protected RtxImageView    imageView_Face;
    protected ArrayList<RtxImageView> list_ImageViewDays;

    public TargetTrainSimple_Frequency_Layout(Context context, int iMode) {
        super(context,iMode);

        mContext = context;
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void display()
    {
        init_View();
        add_View();

        add_ClickView();
    }

    public void setArg(UiDataStruct.TargetTrainInfo data)
    {
//        Log.e("Jerry","data.targetTrainInfo_Freq.iAchievmentDaysInCurrentWeek = " + data.targetTrainInfo_Freq.iAchievmentDaysInCurrentWeek);
//        Log.e("Jerry","data.targetTrainInfo_Freq.iTargetDaysPerWeek = " + data.targetTrainInfo_Freq.iTargetDaysPerWeek);
//        Log.e("Jerry","data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.size() = " + data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.size());
//        Log.e("Jerry","data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.get(0) = " + data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.get(0));
//        Log.e("Jerry","data.targetTrainInfo_Freq.iWeeks_Left = " + data.targetTrainInfo_Freq.iWeeks_Left);
//        Log.e("Jerry","data.targetTrainInfo_Freq.iWeeks_Current = " + data.targetTrainInfo_Freq.iWeeks_Current);

        {
            int iCurrentIndex = data.targetTrainInfo_Freq.iWeeks_Current - 1;
            int iDays = 0;
            int iAchievementDays = 0;

            iDays = data.targetTrainInfo_Freq.iTargetDaysPerWeek;

            if(iCurrentIndex >= 0 && iCurrentIndex < data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.size())
            {
                iAchievementDays = data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.get(iCurrentIndex);
                setLeft(data.targetTrainInfo_Freq.iWeeks_Left,false);
            }
            else
            {
                iAchievementDays = 0;
                setLeft(data.targetTrainInfo_Freq.iWeeks_Left - 1,false);
            }

            add_DaysPerWeekView(iDays, iAchievementDays);
            setFaceState(iDays, iAchievementDays);
        }
    }

    public void onDestroy()
    {
//        removeAllViews();
//        System.gc();
    }

    protected void init_View()
    {
        setInfoTitle(R.string.exercise_frequency);

        if(imageView_Face == null)      { imageView_Face = new RtxImageView(mContext); }

        if(list_ImageViewDays == null)
        {
            list_ImageViewDays = new ArrayList<>();

            {
                int iIndex = 0;
                int iSize = 7;

                for( ; iIndex < iSize ; iIndex ++ )
                {
                    RtxImageView imageView_Day = new RtxImageView(mContext);
                    list_ImageViewDays.add(imageView_Day);
                }
            }
        }
    }

    protected void add_View()
    {
        addRtxImageViewToLayout(imageView_Face, R.drawable.target_normal_face_middle, -1, 215, 81, 81);
    }

    protected void setFaceState(int iDays , int iAchievementDays)
    {
        if(imageView_Face != null)
        {
            if(iAchievementDays >= iDays)
            {
                imageView_Face.setImageResource(R.drawable.target_smile_face_middle);
            }
            else
            {
                imageView_Face.setImageResource(R.drawable.target_normal_face_middle);
            }
        }
    }

    protected void add_DaysPerWeekView(int iDays , int iAchievementDays)
    {
        int iIconTotalW = 0;
        int iLayoutW = 426;
        int iGap = 24;
        int iPosX = 0;
        int iPosY = 117;

        int iW = 35;
        int iH = 35;

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

    protected void remove_DaysView()
    {
        if(list_ImageViewDays != null)
        {
            int iIndex = 0;
            int iSize = 7;

            for( ; iIndex < iSize ; iIndex++)
            {
                this.removeView(list_ImageViewDays.get(iIndex));
            }
        }
    }
}
