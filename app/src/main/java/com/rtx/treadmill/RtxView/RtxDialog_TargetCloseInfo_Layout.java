package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_TargetCloseInfo_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;

    private RtxTextView         textView_DialogTitle;
    private RtxTextView         textView_Description;
    private RtxImageView        imageView_Status;
    private RtxTextView         textView_StatusMsg;
    public RtxFillerTextView    fillerTextView_OK;

    private RtxImageView        imageView_Item;
    private RtxTextView         textView_Item;
    private RtxImageView        imageView_TypeIcon;
    private RtxDoubleStringView doubleStringView_Val;

    public RtxImageView        imageView_Share;

    private RtxTargetScoreForm mRtxTargetScoreForm;

    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close;

    public RtxDialog_TargetCloseInfo_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {
        if(fillerTextView_OK == null)           { fillerTextView_OK = new RtxFillerTextView(mContext);   }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }

        if(imageView_Status == null)            { imageView_Status = new RtxImageView(mContext);   }
        if(imageView_Item == null)              { imageView_Item = new RtxImageView(mContext);   }
        if(imageView_TypeIcon == null)          { imageView_TypeIcon = new RtxImageView(mContext);   }

        if(textView_DialogTitle == null)        { textView_DialogTitle = new RtxTextView(mContext);   }
        if(textView_Description == null)        { textView_Description = new RtxTextView(mContext);   }
        if(textView_StatusMsg == null)          { textView_StatusMsg = new RtxTextView(mContext);   }
        if(textView_Item == null)               { textView_Item = new RtxTextView(mContext);   }

        if(doubleStringView_Val == null)        { doubleStringView_Val = new RtxDoubleStringView(mContext);   }

        if(mRtxTargetScoreForm == null)         { mRtxTargetScoreForm = new RtxTargetScoreForm(mContext); }

        if(imageView_Share == null)             { imageView_Share = new RtxImageView(mContext);   }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,667);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxTextViewToLayout(textView_DialogTitle, R.string.target_ended, 46.75f, Common.Color.blue_6, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 78 - 25, 1002, 34 + 50);
        addRtxTextViewToLayout(textView_Description, R.string.try_again_next_time, 32.71f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 156 - 25, 1002, 23 + 50);
        addRtxTextViewToLayout(textView_StatusMsg, -1, 32.71f, Common.Color.green_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 442 - 25, 1002, 24 + 50);
        addRtxTextViewToLayout(textView_Item, -1, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 707 - 100, 365 - 25, 118 + 200, 16 + 50);

        addRtxImagePaddingViewToLayout(imageView_Status, -1, -1, 222, 187, 212, 0);
        addRtxImagePaddingViewToLayout(imageView_Item, -1, 734, 280, 67, 67, 0);
        addRtxImagePaddingViewToLayout(imageView_TypeIcon, -1, 842, 350, 45, 45, 0);

        addRtxTextViewToLayout(fillerTextView_OK, R.string.ok, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 312, 520, 378, 75, Common.Color.login_button_green);

        addViewToLayout(doubleStringView_Val,698-150,425-50,138+300,36+100);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.white,50f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(16);

        addRtxImagePaddingViewToLayout(imageView_Share, R.drawable.share,935,31,39,31,30);
        vRtxBaseLayoutE_SetShareView(imageView_Share);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final int STATUS_GOLD       =   1;
    final int STATUS_SILVER     =   2;
    final int STATUS_GOODTRY    =   3;

    private void setStatus(int iStatus , boolean bHalfWay , boolean bFreq)
    {
        int iX = -1;
        int iY = 222;
        int iW = 0;
        int iH = 0;

        int iStrTitle = -1;
        int iColorTitle = 0;

        int iStrDecription = -1;

        int iIconScoreResID = -1;

        int iColor = -1;
        int iStrScoreResId = -1;

        if(iStatus == STATUS_GOLD)
        {
            iW = 222;
            iH = 217;

            iStrTitle = R.string.congratulations;
            iColorTitle = Common.Color.green_1;

            iStrDecription = R.string.you_reached_your_target_in_time;

            iIconScoreResID = R.drawable.goal_close_gold_big;
            iColor = Common.Color.gold;
            iStrScoreResId = R.string.super_str;
        }
        else if(iStatus == STATUS_SILVER)
        {
            iW = 221;
            iH = 216;

            iStrTitle = R.string.congratulations;
            iColorTitle = Common.Color.green_1;

            iStrDecription = R.string.you_reached_your_target;

            iIconScoreResID = R.drawable.goal_close_silver_big;
            iColor = Common.Color.silver;
            iStrScoreResId = R.string.great;
        }
        else //if(iStatus == STATUS_GOODTRY)
        {
            iW = 187;
            iH = 212;

            iStrTitle = R.string.target_ended;
            iColorTitle = Common.Color.blue_6;

            iStrDecription = R.string.try_again_next_time;

            iIconScoreResID = R.drawable.goal_close_goodtry_big;
            iColor = Common.Color.green_1;
            iStrScoreResId = R.string.good_try;
        }

        removeView(mRtxTargetScoreForm);

        if(bHalfWay)
        {
            iStrTitle = R.string.half_way_through;
            iColorTitle = Common.Color.blue_6;

            iStrDecription = R.string.you_are_almost_there;

            iIconScoreResID = R.drawable.target_half_way_big;
            iColor = Common.Color.green_1;
            iStrScoreResId = R.string.continue_exclamation_mark;

            if(bFreq)
            {
                addViewToLayout(mRtxTargetScoreForm,25,277,280,377);

                mRtxTargetScoreForm.setScore(iStatus);
                mRtxTargetScoreForm.setScoreText(Rtx_TranslateValue.iString2Int(goal_Close.sGOL_DAT_2),Rtx_TranslateValue.iString2Int(goal_Close.sGOL_RST));
            }
        }

        textView_DialogTitle.setText(LanguageData.s_get_string(mContext,iStrTitle));
        textView_DialogTitle.setTextColor(iColorTitle);

        textView_Description.setText(LanguageData.s_get_string(mContext,iStrDecription));

        removeView(imageView_Status);
        addRtxImagePaddingViewToLayout(imageView_Status, iIconScoreResID, iX, iY, iW, iH, 0);

        textView_StatusMsg.setTextColor(iColor);
        textView_StatusMsg.setText(LanguageData.s_get_string(mContext,iStrScoreResId));
    }

    private void setStatus(String sStatus, boolean bHalfWay, boolean bFreq)
    {
        int iGoal = 0;

        iGoal = Rtx_TranslateValue.iString2Int(sStatus);
        setStatus(iGoal,bHalfWay,bFreq);
    }

    private void setExerciseType(String sType)
    {
        int iResId = -1;

        if(sType == null)
        {
            imageView_TypeIcon.setImageResource(0);
            return;
        }

        if(sType.equals("R")) { iResId = R.drawable.target_type_r; }
        else
        if(sType.equals("E")) { iResId = R.drawable.target_type_e; }
        else
        if(sType.equals("B")) { iResId = R.drawable.target_type_b; }
        else
        if(sType.equals("A")) { iResId = R.drawable.target_type_a; }

        if(iResId != -1)
        {
            if(imageView_TypeIcon != null)
            {
                imageView_TypeIcon.setImageResource(iResId);
            }
        }
    }

    public void setGoalCloseInfo(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close, boolean bHalfWay)
    {
        int iItemImg_ResId = -1;
        int iItemStr_ResId = -1;
        String sVal = null;
        String sUnit = null;

        this.goal_Close = goal_Close;

        if(goal_Close.sGOL_ITM.equals("Distance"))
        {
            iItemImg_ResId = R.drawable.target_history_dis;
            iItemStr_ResId = R.string.distance;
            sVal = new String(EngSetting.Distance.getValString(goal_Close.sGOL_RST) + "/" + EngSetting.Distance.getValString(goal_Close.sGOL_DAT_1));
            sUnit = EngSetting.Distance.getUnitString(mContext);
            setExerciseType(goal_Close.sGOL_TYP);
        }
        else if(goal_Close.sGOL_ITM.equals("Calories"))
        {
            iItemImg_ResId = R.drawable.target_history_cal;
            iItemStr_ResId = R.string.calories;
            sVal = new String(Rtx_TranslateValue.sFormatToDecimal(goal_Close.sGOL_RST,0) + "/" + Rtx_TranslateValue.sFormatToDecimal(goal_Close.sGOL_DAT_1,0));
            sUnit = LanguageData.s_get_string(mContext,R.string.kcal);
            setExerciseType(null);
        }
        else if(goal_Close.sGOL_ITM.equals("Frequency"))
        {
            iItemImg_ResId = R.drawable.target_history_freq;
            iItemStr_ResId = R.string.exercise_frequency;
            sVal = new String(goal_Close.sGOL_RST + "/" + goal_Close.sGOL_DAT_2);
            sUnit = LanguageData.s_get_string(mContext,R.string.weeks);
            setExerciseType(null);


        }
        else if(goal_Close.sGOL_ITM.equals("Target Weight"))
        {
            iItemImg_ResId = R.drawable.target_history_wei;
            iItemStr_ResId = R.string.weight_upper;
            sVal = new String(EngSetting.Weight.getValString(goal_Close.sGOL_RST) + "/" + EngSetting.Weight.getValString(goal_Close.sGOL_DAT_1));
            sUnit = EngSetting.Weight.getUnitString(mContext);
            setExerciseType(null);
        }
        else if(goal_Close.sGOL_ITM.equals("Target Body Fat"))
        {
            iItemImg_ResId = R.drawable.target_history_bdy_fat;
            iItemStr_ResId = R.string.body_fat;
            sVal = new String(Rtx_TranslateValue.sFormatToDecimal(goal_Close.sGOL_RST,1) + "/" + Rtx_TranslateValue.sFormatToDecimal(goal_Close.sGOL_DAT_1,1));
            sUnit = "%";
            setExerciseType(null);
        }

        if(iItemImg_ResId != -1)
        {
            imageView_Item.setImageResource(iItemImg_ResId);
            textView_Item.setText(LanguageData.s_get_string(mContext,iItemStr_ResId));
            doubleStringView_Val.setText(sVal,sUnit);
            setStatus(goal_Close.sGOL_CLS_LVL,bHalfWay,goal_Close.sGOL_ITM.equals("Frequency"));
        }
    }
}
