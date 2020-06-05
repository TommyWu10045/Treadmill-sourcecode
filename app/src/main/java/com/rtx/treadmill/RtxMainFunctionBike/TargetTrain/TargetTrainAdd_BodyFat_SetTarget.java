package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_BodyFat_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_BodyFat_SetTarget extends Rtx_NumberWheel_BodyFat_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    protected RtxTextView textView_loss_gain;
    protected RtxTextView textView_loss_gain_Val;

    private   float fStartVal = 0;
    private   float fCurrentVal = 0;


    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_BodyFat_SetTarget(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        vSetTitleText(R.string.target_body_fat);

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

    private void vSetDiffVal(float fVal)
    {
        if(fVal > 0)
        {
            textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.percent_gain));
            textView_loss_gain_Val.setText(Rtx_TranslateValue.sFloat2String(fVal,1));

            if(fVal==0.5 ||fVal==0)
            {

                imageView_Confirm.setVisibility(INVISIBLE);
            }else{
                imageView_Confirm.setVisibility(VISIBLE);
            }
        }
        else
        if(fVal <= 0)
        {
            textView_loss_gain.setText(LanguageData.s_get_string(mContext,R.string.percent_loss));
            textView_loss_gain_Val.setText(Rtx_TranslateValue.sFloat2String(fVal,1));

            if(fVal==-0.5 ||fVal==0)
            {

                imageView_Confirm.setVisibility(INVISIBLE);
            }else{
                imageView_Confirm.setVisibility(VISIBLE);
            }
        }
        else
        {
            textView_loss_gain.setText("");
            textView_loss_gain_Val.setText("");
        }
    }

    @Override
    protected void onValueChange_Customer(int oldVal, int newVal)
    {
//        Log.e("Jerry","oldVal = " + oldVal / numberWheelData.fScale);
//        Log.e("Jerry","newVal = " + newVal / numberWheelData.fScale);

        float fVal;

        //fVal = (oldVal  * numberWheelData.fScale) - fStartVal;
        fVal = (newVal  * numberWheelData.fScale) - fStartVal;
        vSetDiffVal(fVal);
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_BDY_FAT_SET_CURRENT);
    }

    @Override
    public void vClickButton_Confirm()
    {
        if(cloud_TargetGoal.sUsr_Wei.equals(Rtx_TranslateValue.sFloat2String(getCurrentVal(), 5)))
        {
            return;
        }

        {
            cloud_TargetGoal.sGol_Val = Rtx_TranslateValue.sFloat2String(getCurrentVal(), 5);
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
                fCurrentVal = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sGol_Val);
                setCurrentVal(fCurrentVal);

                fStartVal = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei);

                vSetDiffVal(fStartVal - fCurrentVal);
            }
        }
    }
}

