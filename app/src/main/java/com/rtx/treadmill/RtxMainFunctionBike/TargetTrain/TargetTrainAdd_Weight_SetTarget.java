package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Weight_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_Weight_SetTarget extends Rtx_NumberWheel_Weight_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    protected RtxTextView textView_loss_gain;
    protected RtxTextView textView_loss_gain_Val;

    private   float fCurrentVal = 0;
    private   float fStartVal = 0;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_Weight_SetTarget(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        vSetTitleText(R.string.target_weight);

        if(textView_loss_gain == null)               { textView_loss_gain = new RtxTextView(mContext); }
        if(textView_loss_gain_Val == null)           { textView_loss_gain_Val = new RtxTextView(mContext); }
    }

    @Override
    protected void add_CustomerView()
    {
        removeView(textView_Unit);
        imageView_Confirm.setImageResource(R.drawable.next_big_button_enable);

        addRtxTextViewToLayout(textView_loss_gain,50f, Common.Color.blue_1, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, 1027-100, 299-50, 152+200, 36+100);
        addRtxTextViewToLayout(textView_loss_gain_Val,85f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1020-100, 428-50, 163+200, 60+100);
    }

    @Override
    protected void changeDefaultValRange()
    {
        //User Override Can Change value range

        DEFAULT_METRIC_SCALE = 0.5f;
        //DEFAULT_METRIC_MAX_VAL = 220.0f;
        //20190108 TargetTrain Target 維持 35
        DEFAULT_METRIC_MIN_VAL = 35.0f;
        //DEFAULT_METRIC_VAL = 80.0f;
        //
        //
        DEFAULT_IMPERIAL_SCALE = 1f;
        //DEFAULT_IMPERIAL_MAX_VAL = 484.0f;
        //20190108 TargetTrain Target 維持 77
        DEFAULT_IMPERIAL_MIN_VAL = 77.0f;
        //DEFAULT_IMPERIAL_VAL = 176.0f;
    }

    @Override
    protected void onValueChange_Customer(int oldVal, int newVal)
    {
        float fVal;

        //fVal = (oldVal  * numberWheelData.fScale) - fCurrentVal;
        fVal = (newVal  * numberWheelData.fScale) - fCurrentVal;
        vSetDiffVal(fVal);
    }

    private void vSetDiffVal(float fVal)
    {
        if(fVal > 0)
        {
            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
            {
                textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.kg_gain));
            }
            else
            {
                textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.lb_gain));
            }

            String Team_a=Rtx_TranslateValue.sFloat2String(fVal,1);

            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
            {
                if (fVal == 0.0f || fVal == 0.5f) {
                    imageView_Confirm.setVisibility(INVISIBLE);

                } else {

                    imageView_Confirm.setVisibility(VISIBLE);
                }

            }else{

                if (Team_a.equals("1.0"))
                {
                    imageView_Confirm.setVisibility(INVISIBLE);

                } else {

                    imageView_Confirm.setVisibility(VISIBLE);
                }

            }
        }
        else
        if(fVal <= 0)
        {
            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
            {
                textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.kg_loss));
            }
            else
            {
                textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.lb_loss));
            }


            textView_loss_gain_Val.setText(Rtx_TranslateValue.sFloat2String(fVal,1));

            String Team_b=Rtx_TranslateValue.sFloat2String(fVal,1);

            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
            {
                if (fVal == 0.0f || fVal == -0.5f) {
                    imageView_Confirm.setVisibility(INVISIBLE);

                } else {

                    imageView_Confirm.setVisibility(VISIBLE);
                }
            }else{

                if ( Team_b.equals("-1.0") || Team_b.equals("0.0")  )
                {
                    imageView_Confirm.setVisibility(INVISIBLE);

                } else {

                    imageView_Confirm.setVisibility(VISIBLE);
                }

            }
        }
        else
        {
            textView_loss_gain.setText("");
            textView_loss_gain_Val.setText("");
        }
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_WEI_SET_CURRENT);
    }

    @Override
    public void vClickButton_Confirm()
    {
        if(cloud_TargetGoal.sUsr_Wei.equals(Rtx_TranslateValue.sFloat2String(getCurrentVal_Metric(), 5)))
        {
            return;
        }

        {
            cloud_TargetGoal.sGol_Val = Rtx_TranslateValue.sFloat2String(getCurrentVal_Metric(), 5);
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_SET_DAY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;

        if(cloud_TargetGoal != null)
        {
            if(cloud_TargetGoal.sUsr_Wei != null)
            {
                //20190108 TargetTrain Target 維持 35
                if(Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sGol_Val) < DEFAULT_METRIC_MIN_VAL) {
                    setCurrentVal_Metric(DEFAULT_METRIC_MIN_VAL);
                }
                else
                {
                    setCurrentVal_Metric(Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sGol_Val));
                }
                fStartVal = fGetStartVal();
                fCurrentVal = EngSetting.Weight.getVal(Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei));
                vSetDiffVal(fStartVal - fCurrentVal);
            }
        }
    }
}

