package com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainMainLayout;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainMainLayout_E extends TargetTrainMainLayout {

    public TargetTrainMainLayout_E(Context context, MainActivity mMainActivity) {
        super(context,mMainActivity);
    }

    @Override
    protected void vMatchSimpleLayout()
    {
        mTargetTrainSimple_Distance_Layout = mTargetTrainSimple_Distance_Layout_Exercise;
        mTargetTrainSimple_Calories_Layout = mTargetTrainSimple_Calories_Layout_Exercise;
        mTargetTrainSimple_Frequency_Layout = mTargetTrainSimple_Frequency_Layout_Exercise;
        mTargetTrainSimple_Weight_Layout = mTargetTrainSimple_Weight_Layout_Exercise;
        mTargetTrainSimple_BodyFat_Layout = mTargetTrainSimple_BodyFat_Layout_Exercise;
    }

    protected void setCreateButtonState(int iTargetSize)
    {
        {
            removeView(imageView_BackHome);
            addRtxImagePaddingViewToLayout(imageView_BackHome, R.xml.home, 1049, 38, 39, 39, 30);
        }

        {
            addRtxImageViewToLayout(imageView_ReturnExercisePage, R.drawable.home_return_exercise, 1151, 35, 51, 46);
        }

        if(iTargetSize <= 0)
        {
            this.removeView(fillerTextView_Create);

            addRtxImagePaddingViewToLayout(imageView_CreateButton, R.drawable.target_create_button, 573, 409, 134, 134, 30);
            addRtxTextViewToLayout(textView_CreateText, R.string.create_your_fitness_target, 20f, Common.Color.login_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 433, 575, 415, 35);

            if( CloudDataStruct.CloudData_20.is_log_in())
            {
                setCreateButtonEnable(true);
            }
            else
            {
                setCreateButtonEnable(false);
            }
        }
        else
        if(iTargetSize >= 3)
        {
            this.removeView(imageView_CreateButton);
            this.removeView(textView_CreateText);

            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_target, 22.45f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 120, 374, 60, Common.Color.login_button_yellow);
            fillerTextView_Create.setFillet(75 / 2);
            setCreateButtonEnable(false);
        }
        else
        {
            this.removeView(imageView_CreateButton);
            this.removeView(textView_CreateText);

            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_target, 22.45f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 120, 374, 60, Common.Color.login_button_yellow);
            fillerTextView_Create.setFillet(75 / 2);
            setCreateButtonEnable(true);
        }
    }

    protected void setCreateButtonEnable(boolean bEnable)
    {
        bEnable = false;    //Because In Exercise Mode

        {
            int iResId = -1;

            if (bEnable) {
                iResId = R.drawable.target_create_button;
            } else {
                iResId = R.drawable.target_create_disable_icon;
            }

            if (imageView_CreateButton != null) {
                imageView_CreateButton.setImageResource(iResId);
                imageView_CreateButton.setClickable(bEnable);
            }

            if (textView_CreateText != null) {
                textView_CreateText.setClickable(bEnable);
            }
        }

        {
            int iColor = 0;

            if(bEnable)
            {
                iColor = Common.Color.login_button_yellow;
            }
            else
            {
                iColor = Common.Color.gray_4;
            }

            if(fillerTextView_Create != null)
            {
                fillerTextView_Create.setBackgroundColor(iColor);
                fillerTextView_Create.setClickable(bEnable);
            }
        }
    }

    protected int getSimplePosX(int iOrder)
    {
        int iPosX = 0;

        iPosX = 1 + (426 * iOrder);

        return iPosX;
    }

    protected void setTargetTrainInfo(UiDataStruct.TargetTrainInfo data)
    {
        if(data.bIsDistance())
        {
            addViewToLayout(mTargetTrainSimple_Distance_Layout,getSimplePosX(data.targetTrainInfo_Dis.iOrder),190,426,453);
        }

        if(data.bIsCalories())
        {
            addViewToLayout(mTargetTrainSimple_Calories_Layout,getSimplePosX(data.targetTrainInfo_Cal.iOrder),190,426,453);
        }

        if(data.bIsFreq())
        {
            addViewToLayout(mTargetTrainSimple_Frequency_Layout,getSimplePosX(data.targetTrainInfo_Freq.iOrder),190,426,453);
        }

        if(data.bIsWeight())
        {
            addViewToLayout(mTargetTrainSimple_Weight_Layout,getSimplePosX(data.targetTrainInfo_Wei.iOrder),190,426,453);
        }

        if(data.bIsBodyFat())
        {
            addViewToLayout(mTargetTrainSimple_BodyFat_Layout,getSimplePosX(data.targetTrainInfo_BdyFat.iOrder),190,426,453);
        }

        int iTargetSize = data.getTargetCount();

        if( iTargetSize <= 0)
        {

        }
        else if(iTargetSize < 3)
        {

        }
        else
        {

        }
    }

    @Override
    protected void vClickReturnExercisePage()
    {
        mMainActivity.mMainProcTreadmill.v_Goto_ExercisePage();
    }

    @Override
    protected void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.target_train);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.targer_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_target_train, -1, stitle01, null, sinfo01, null, "tt_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFOE);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
