package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Frequency_Layout extends TargetTrainDetailBaseLayout {

    protected Context                                     mContext;

    private ButtonListener                              mButtonListener;
    private MainActivity                                mMainActivity;

    protected RtxImageView                                imageView_Face;
    protected ArrayList<RtxImageView>                     list_ImageViewDays;
    protected RtxTextView                                 textView_WeekText;

    private CloudDataStruct.CLOUD_TATGET_GOAL           cloud_TargetGoal;
    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE     cloud_TatgetGoalClose;

    public TargetTrainDetail_Frequency_Layout(Context context, MainActivity mMainActivity, int iMode) {
        super(context , mMainActivity , iMode);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display()
    {
        init_TargetBaseView();
        init_View();
        add_TargetBaseView();
        add_View();
        init_event();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    protected void setTargetTrainInfo(UiDataStruct.TargetTrainInfo data , CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal,CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE cloud_TatgetGoalClose)
    {
        data.targetTrainInfo_Freq.vExportDataToTargetClose(cloud_TatgetGoalClose);

        this.cloud_TatgetGoalClose = cloud_TatgetGoalClose;
        this.cloud_TargetGoal = cloud_TargetGoal;
        this.cloud_TargetGoal.copy(data.targetTrainInfo_Freq.cloud_TargetGol);

        int iCurrentIndex = data.targetTrainInfo_Freq.iWeeks_Current - 1;
        int iDays = 0;
        int iAchievementDays = 0;

        iDays = data.targetTrainInfo_Freq.iTargetDaysPerWeek;

        if(iCurrentIndex >= 0)
        {
            iAchievementDays = data.targetTrainInfo_Freq.list_AchievmentDaysInWeek.get(iCurrentIndex);
            //setLeft(data.targetTrainInfo_Freq.iWeeks_Left,false);
        }
        else
        {
            iAchievementDays = 0;
            //setLeft(data.targetTrainInfo_Freq.iWeeks_Left - 1,false);
        }

        add_DaysPerWeekView(iDays, iAchievementDays);
        setFaceState(iDays, iAchievementDays);


        setTargetDaysPerWeek(iDays);
        setTargetWeeks(data.targetTrainInfo_Freq.iWeeks_Target);
        setCurrentWeek(data.targetTrainInfo_Freq.iWeeks_Current);
        setAchievement(data.targetTrainInfo_Freq.iAchievmentWeeks,data.targetTrainInfo_Freq.iWeeks_Target);

    }

    protected void init_View()
    {
        vSetTitleText(R.string.exercise_frequency);

        if(imageView_Face == null)      { imageView_Face = new RtxImageView(mContext); }
        if(textView_WeekText == null)   { textView_WeekText = new RtxTextView(mContext); }

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

    protected void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
        imageView_Close.setOnClickListener(mButtonListener);
        imageView_Share.setOnClickListener(mButtonListener);
    }

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

    private void vBackToPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
    }

    @Override
    protected void vClose()
    {
        if(cloud_TargetGoal != null)
        {
            cloud_TargetGoal.setDataForDelete();
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_ADD_GOAL_CLOSE);
    }

    private void vShare()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_SHARE,mMainActivity,mMainActivity.frameLayout_Base);
    }

    @Override
    protected void vDelete()
    {
        if(cloud_TargetGoal != null)
        {
            cloud_TargetGoal.setDataForDelete();
        }

        mMainActivity.mMainProcBike.targetTrainProc.setDeleteFlag(true);
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vBackToPrePage(); }
            else if(v == imageView_Close)   { vShowCloseDialog(); }
            else if(v == imageView_Delete)  { vShowDeleteDialog(); }
            else if(v == imageView_Share)   { vShare(); }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setTargetDaysPerWeek(int iVal)
    {
        String sDay;

        if(iVal > 1)
        {
            sDay = "days";
        }
        else
        {
            sDay = "day";
        }

        set_Bottom_ItemVal_1(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    private void setTargetWeeks(int iVal)
    {
        String sWeek;

        if(iVal > 1)
        {
            sWeek = "weeks";
        }
        else
        {
            sWeek = "week";
        }

        set_Bottom_ItemVal_2(Rtx_TranslateValue.sInt2String(iVal),sWeek);
    }

    private void setCurrentWeek(int iVal)
    {
        if(iVal > 0)
        {
            set_Bottom_ItemVal_3(sGetOrderStr(iVal), "");
            textView_WeekText.setText(LanguageData.s_get_string(mContext,R.string.this_week));
        }
    }

    private void setAchievement(int iAchievementWeek, int iTargetWeeks)
    {
        set_Bottom_ItemVal_4(Rtx_TranslateValue.sInt2String(iAchievementWeek) + "/" + Rtx_TranslateValue.sInt2String(iTargetWeeks),"");
    }

    private void setFaceState(int iDays , int iAchievementDays)
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

    private void remove_DaysView()
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
