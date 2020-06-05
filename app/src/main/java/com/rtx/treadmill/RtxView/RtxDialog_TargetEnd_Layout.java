package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
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

public class RtxDialog_TargetEnd_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;

    private RtxTextView         textView_DialogTitle;
    private RtxTextView         textView_Description;
    private RtxImageView        imageView_Status;
    private RtxTextView         textView_StatusMsg;
    public RtxFillerTextView    fillerTextView_END;
    public RtxFillerTextView    fillerTextView_CONTINUE;

    private RtxImageView        imageView_Item;
    private RtxTextView         textView_Item;
    private RtxImageView        imageView_TypeIcon;
    private RtxDoubleStringView doubleStringView_Val;

    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close;

    public RtxDialog_TargetEnd_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {
        if(fillerTextView_END == null)           { fillerTextView_END = new RtxFillerTextView(mContext);   }
        if(fillerTextView_CONTINUE == null)      { fillerTextView_CONTINUE = new RtxFillerTextView(mContext);   }
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
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,667);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxTextViewToLayout(textView_DialogTitle, R.string.target_end_title, 32.71f, Common.Color.blue_6, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 55 - 25, 1002, 30 + 50);
        addRtxTextViewToLayout(textView_Description, R.string.target_end_description, 32.71f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 95 - 25, 723, 63 + 50);
        addRtxTextViewToLayout(textView_StatusMsg, R.string.continue_exclamation_mark, 32.71f, Common.Color.green_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 401 - 25, 1002, 24 + 50);
        addRtxTextViewToLayout(textView_Item, -1, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 633 - 100, 316 - 25, 269 + 200, 18 + 50);

        addRtxImagePaddingViewToLayout(imageView_Status, -1, 408, 173, 187, 212, 0);
        addRtxImagePaddingViewToLayout(imageView_Item, -1, 734, 231, 67, 67, 0);
        addRtxImagePaddingViewToLayout(imageView_TypeIcon, -1, 842, 301, 45, 45, 0);

        addRtxTextViewToLayout(fillerTextView_END, R.string.end, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 312, 468, 378, 75, Common.Color.yellow_1);
        addRtxTextViewToLayout(fillerTextView_CONTINUE, R.string.continue_str, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 312, 566, 378, 75, Common.Color.login_button_green);

        addViewToLayout(doubleStringView_Val,698-150,376-50,138+300,36+100);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.white,50f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(16);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final int STATUS_GOLD       =   0;
    final int STATUS_SILVER     =   1;
    final int STATUS_GOODTRY    =   2;

    private void setStatus(int iStatus)
    {
        int iX = -1;
        int iY = 173;
        int iW = 0;
        int iH = 0;

        int iIconScoreResID = -1;

        if(iStatus == STATUS_GOLD)
        {
            iW = 222;
            iH = 217;

            iIconScoreResID = R.drawable.goal_close_gold_big;
        }
        else if(iStatus == STATUS_SILVER)
        {
            iW = 221;
            iH = 216;

            iIconScoreResID = R.drawable.goal_close_silver_big;
        }
        else //if(iStatus == STATUS_GOODTRY)
        {
            iW = 187;
            iH = 212;

            iIconScoreResID = R.drawable.target_half_way_big;
        }

        removeView(imageView_Status);
        addRtxImagePaddingViewToLayout(imageView_Status, iIconScoreResID, iX, iY, iW, iH, 0);
    }

    private void setStatus(String sStatus)
    {
        int iGoal = 0;

        iGoal = Rtx_TranslateValue.iString2Int(sStatus);
        setStatus(iGoal);
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

    public void setGoalCloseInfo(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close)
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
            setStatus(goal_Close.sGOL_CLS_LVL);
        }
    }
}
