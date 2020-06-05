package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringForTargetHistoryView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainHistory_Block extends Rtx_BaseLayout {

    private Context         mContext;
    private MainActivity    mMainActivity;

    private ButtonListener  mButtonListener;

    private RtxImageView    imageView_Item;
    private RtxImageView    imageView_Delete;
    private RtxImageView    imageView_ExerciseType;
    private RtxImageView    imageView_Score;

    private RtxTextView     textView_Item;
    private RtxTextView     textView_Date;

    private RtxDoubleStringForTargetHistoryView doubleStringView_Val;

    CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE cloud_TargetGoalClose;

    public TargetTrainHistory_Block(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;

        setBackgroundColor(Common.Color.background);
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
        if (imageView_Item == null)         { imageView_Item = new RtxImageView(mContext); }
        if (imageView_Delete == null)       { imageView_Delete = new RtxImageView(mContext); }
        if (imageView_ExerciseType == null) { imageView_ExerciseType = new RtxImageView(mContext); }
        if (imageView_Score == null)        { imageView_Score = new RtxImageView(mContext); }

        if (textView_Item == null)          { textView_Item = new RtxTextView(mContext); }
        if (textView_Date == null)          { textView_Date = new RtxTextView(mContext); }
        if (doubleStringView_Val == null)   { doubleStringView_Val = new RtxDoubleStringForTargetHistoryView(mContext); }
    }

    private void init_event()
    {
        imageView_Delete.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Item          , -1                            ,  -1,   0, 67, 67);
        addRtxImageViewToLayout(imageView_Delete        , R.drawable.goal_close_delete  , 265,   0, 67, 67);
        addRtxImageViewToLayout(imageView_ExerciseType  , -1                            , 275,  70, 45, 45);
        addRtxImageViewToLayout(imageView_Score         , -1                            ,  -1, 194, 74, 71);

        addRtxTextViewToLayout(textView_Item, -1, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 84 - 25, 406, 16 + 50);
        addRtxTextViewToLayout(textView_Date, -1, 26.67f, Common.Color.blue_5, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, -1, 282 - 25, 406, 20 + 25);

        addViewToLayout(doubleStringView_Val, -1 , 144 - 25 , 406, 36 + 50);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.white,40f,Common.Font.Relay_BoldItalic,Common.Color.blue_5,23.00f);//By Alan
    }

    private void vParseGoalCloseData(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data,boolean bDeleteVisible)
    {
        if(data.sGOL_ITM.equals("Distance"))
        {
            vSetBlock_Distance(cloud_TargetGoalClose);
        }
        else if(data.sGOL_ITM.equals("Calories"))
        {
            vSetBlock_Calories(cloud_TargetGoalClose);
        }
        else if(data.sGOL_ITM.equals("Frequency"))
        {
            vSetBlock_Frequency(cloud_TargetGoalClose);
        }
        else if(data.sGOL_ITM.equals("Target Weight"))
        {
            vSetBlock_Weight(cloud_TargetGoalClose);
        }
        else if(data.sGOL_ITM.equals("Target Body Fat"))
        {
            vSetBlock_BodyFat(cloud_TargetGoalClose);
        }
        else
        {

        }

        vVisibleDeleteButton(bDeleteVisible);
    }

    private void vSetBlock_Distance(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data)
    {
        imageView_Item.setImageResource(R.drawable.target_history_dis);
        textView_Item.setText(LanguageData.s_get_string(mContext, R.string.distance));

        if     (data.sGOL_TYP.equals("R"))   { imageView_ExerciseType.setImageResource(R.drawable.exercise_type_r_small); }
        else if(data.sGOL_TYP.equals("B"))   { imageView_ExerciseType.setImageResource(R.drawable.exercise_type_b_small); }
        else if(data.sGOL_TYP.equals("E"))   { imageView_ExerciseType.setImageResource(R.drawable.exercise_type_e_small); }
        else                                 { imageView_ExerciseType.setImageResource(R.drawable.exercise_type_a_small); }

        {
            String sUserVal = null;
            String sTargetVal = null;
            String sUnit = null;

            sUserVal   = EngSetting.Distance.getValString(Rtx_TranslateValue.fString2Float(data.sGOL_RST));
            sTargetVal = EngSetting.Distance.getValString(Rtx_TranslateValue.fString2Float(data.sGOL_DAT_1));
            sUnit = EngSetting.Distance.getUnitString(mContext);

            doubleStringView_Val.setText(sUserVal + "/" + sTargetVal , sUnit);
        }

        {
            if      (data.sGOL_CLS_LVL.equals("1")) { imageView_Score.setImageResource(R.drawable.goal_close_gold_small); }
            else if (data.sGOL_CLS_LVL.equals("2")) { imageView_Score.setImageResource(R.drawable.goal_close_silver_small); }
            else                                    { imageView_Score.setImageResource(R.drawable.goal_close_goodtry_small); }
        }

        {
            textView_Date.setText(data.sEND_TIM);
        }
    }

    private void vSetBlock_Calories(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data)
    {
        imageView_Item.setImageResource(R.drawable.target_history_cal);
        textView_Item.setText(LanguageData.s_get_string(mContext, R.string.calories));
        imageView_ExerciseType.setVisibility(INVISIBLE);

        {
            String sUserVal = null;
            String sTargetVal = null;
            String sUnit = null;

            sUserVal   = Rtx_TranslateValue.sFormatToDecimal(data.sGOL_RST,0);
            sTargetVal = Rtx_TranslateValue.sFormatToDecimal(data.sGOL_DAT_1,0);
            sUnit = LanguageData.s_get_string(mContext, R.string.kcal);

            doubleStringView_Val.setText(sUserVal + "/" + sTargetVal , sUnit);
        }

        {
            if      (data.sGOL_CLS_LVL.equals("1")) { imageView_Score.setImageResource(R.drawable.goal_close_gold_small); }
            else if (data.sGOL_CLS_LVL.equals("2")) { imageView_Score.setImageResource(R.drawable.goal_close_silver_small); }
            else                                    { imageView_Score.setImageResource(R.drawable.goal_close_goodtry_small); }
        }

        {
            textView_Date.setText(data.sEND_TIM);
        }
    }

    private void vSetBlock_Frequency(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data)
    {
        imageView_Item.setImageResource(R.drawable.target_history_freq);
        textView_Item.setText(LanguageData.s_get_string(mContext, R.string.exercise_frequency));
        imageView_ExerciseType.setVisibility(INVISIBLE);

        {
            String sUserVal = null;
            String sTargetVal = null;
            String sUnit = null;

            {
                int iDays = 0;
                Calendar cStart = GlobalData.getInstance();
                Calendar cEnd   = GlobalData.getInstance();

                cStart = Rtx_Calendar.cStr2Calendar(data.sGOL_STR_DT,"yyyy-MM-dd");
                cEnd = Rtx_Calendar.cStr2Calendar(data.sEND_TIM,"yyyy-MM-dd");

                cStart.set(Calendar.DAY_OF_WEEK,1);
                cEnd.set(Calendar.DAY_OF_WEEK,7);
                iDays = Rtx_Calendar.getDiffDays(cStart,cEnd) + 1;
                sTargetVal = Rtx_TranslateValue.sInt2String(iDays / 7);

                sUserVal   = data.sGOL_RST;
            }

            sUnit = LanguageData.s_get_string(mContext, R.string.weeks);

            doubleStringView_Val.setText(sUserVal + "/" + sTargetVal , sUnit);
        }

        {
            if      (data.sGOL_CLS_LVL.equals("1")) { imageView_Score.setImageResource(R.drawable.goal_close_gold_small); }
            else if (data.sGOL_CLS_LVL.equals("2")) { imageView_Score.setImageResource(R.drawable.goal_close_silver_small); }
            else                                    { imageView_Score.setImageResource(R.drawable.goal_close_goodtry_small); }
        }

        {
            textView_Date.setText(data.sEND_TIM);
        }
    }

    private void vSetBlock_Weight(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data)
    {
        imageView_Item.setImageResource(R.drawable.target_history_wei);
        textView_Item.setText(LanguageData.s_get_string(mContext, R.string.weight_upper));
        imageView_ExerciseType.setVisibility(INVISIBLE);

        {
            String sUserVal = null;
            String sTargetVal = null;
            String sUnit = null;

            sUserVal = EngSetting.Weight.getValString(Rtx_TranslateValue.fString2Float(data.sGOL_RST));
            sTargetVal = EngSetting.Weight.getValString(Rtx_TranslateValue.fString2Float(data.sGOL_DAT_1));
            sUnit = EngSetting.Weight.getUnitString(mContext);

            doubleStringView_Val.setText(sUserVal + "/" + sTargetVal , sUnit);
        }

        {
            if      (data.sGOL_CLS_LVL.equals("1")) { imageView_Score.setImageResource(R.drawable.goal_close_gold_small); }
            else if (data.sGOL_CLS_LVL.equals("2")) { imageView_Score.setImageResource(R.drawable.goal_close_silver_small); }
            else                                    { imageView_Score.setImageResource(R.drawable.goal_close_goodtry_small); }
        }

        {
            textView_Date.setText(data.sEND_TIM);
        }
    }

    private void vSetBlock_BodyFat(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE data)
    {
        imageView_Item.setImageResource(R.drawable.target_history_bdy_fat);
        textView_Item.setText(LanguageData.s_get_string(mContext, R.string.bodyfat_percent));
        imageView_ExerciseType.setVisibility(INVISIBLE);

        {
            String sUserVal = null;
            String sTargetVal = null;
            String sUnit = null;

            sUserVal = data.sGOL_RST;
            sTargetVal = data.sGOL_DAT_1;
            sUnit = "%";

            //doubleStringView_Val.setText(sUserVal + "/" + sTargetVal , sUnit);
            doubleStringView_Val.setText(Rtx_TranslateValue.sFormatToDecimal(sUserVal,1) + "/" + Rtx_TranslateValue.sFormatToDecimal(sTargetVal,1) , sUnit);
        }

        {
            if      (data.sGOL_CLS_LVL.equals("1")) { imageView_Score.setImageResource(R.drawable.goal_close_gold_small); }
            else if (data.sGOL_CLS_LVL.equals("2")) { imageView_Score.setImageResource(R.drawable.goal_close_silver_small); }
            else                                    { imageView_Score.setImageResource(R.drawable.goal_close_goodtry_small); }
        }

        {
            textView_Date.setText(data.sEND_TIM);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vDelete()
    {
        //Log.e("Jerry","click delete sGolSeq = " + cloud_TargetGoalClose.sGOL_SEQ);
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetGoalSeq(cloud_TargetGoalClose.sGOL_SEQ);
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_DELETE_GOAL_CLOSE);
    }

    protected void vShowDeleteDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE,LanguageData.s_get_string(mContext,R.string.delete_description),-1);

        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vDelete();
            }
        });
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_Delete)     { vShowDeleteDialog(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vVisibleDeleteButton(boolean isVisible)
    {
        if(isVisible)
        {
            imageView_Delete.setVisibility(VISIBLE);
        }
        else
        {
            imageView_Delete.setVisibility(INVISIBLE);
        }
    }

    protected void vSetCloudTargetGoalClose(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE cloud_TargetGoalClose , boolean bDeleteVisible)
    {
        this.cloud_TargetGoalClose = cloud_TargetGoalClose;

        init();
        display();
        vParseGoalCloseData(cloud_TargetGoalClose,bDeleteVisible);
    }
}
