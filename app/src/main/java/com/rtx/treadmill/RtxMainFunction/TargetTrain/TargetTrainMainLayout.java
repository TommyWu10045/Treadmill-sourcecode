package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout.TargetTrainSimple_BodyFat_Layout_E;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout.TargetTrainSimple_Calories_Layout_E;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout.TargetTrainSimple_Distance_Layout_E;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout.TargetTrainSimple_Frequency_Layout_E;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainExerciseLayout.TargetTrainSimple_Weight_Layout_E;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainMainLayout extends Rtx_BaseLayout {

    protected Context mContext;

    protected     RtxImageView        imageView_CreateButton;
    protected     RtxImageView        imageView_InfoButton;
    protected     RtxTextView         textView_CreateText;

    protected     RtxImageView        imageView_History;
    protected     RtxTextView         textView_History;

    protected     RtxFillerTextView   fillerTextView_Create;
    protected     ButtonListener      mButtonListener;
    protected     MainActivity        mMainActivity;

    protected     TargetTrainSimple_Distance_Layout   mTargetTrainSimple_Distance_Layout;
    protected     TargetTrainSimple_Calories_Layout   mTargetTrainSimple_Calories_Layout;
    protected     TargetTrainSimple_Frequency_Layout  mTargetTrainSimple_Frequency_Layout;
    protected     TargetTrainSimple_Weight_Layout     mTargetTrainSimple_Weight_Layout;
    protected     TargetTrainSimple_BodyFat_Layout    mTargetTrainSimple_BodyFat_Layout;

    protected TargetTrainSimple_Distance_Layout     mTargetTrainSimple_Distance_Layout_Normal;
    protected TargetTrainSimple_Calories_Layout     mTargetTrainSimple_Calories_Layout_Normal;
    protected TargetTrainSimple_Frequency_Layout    mTargetTrainSimple_Frequency_Layout_Normal;
    protected TargetTrainSimple_Weight_Layout       mTargetTrainSimple_Weight_Layout_Normal;
    protected TargetTrainSimple_BodyFat_Layout      mTargetTrainSimple_BodyFat_Layout_Normal;

    protected TargetTrainSimple_Distance_Layout_E   mTargetTrainSimple_Distance_Layout_Exercise;
    protected TargetTrainSimple_Calories_Layout_E   mTargetTrainSimple_Calories_Layout_Exercise;
    protected TargetTrainSimple_Frequency_Layout_E  mTargetTrainSimple_Frequency_Layout_Exercise;
    protected TargetTrainSimple_Weight_Layout_E     mTargetTrainSimple_Weight_Layout_Exercise;
    protected TargetTrainSimple_BodyFat_Layout_E    mTargetTrainSimple_BodyFat_Layout_Exercise;

    protected RtxImageView        imageView_ReturnExercisePage;

    public TargetTrainMainLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
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
        init_View();
        init_event();
        add_View();

        check_Login();

//        if(check_Login())
//        {
//            mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_LOAD_GOAL);
//        }
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    protected void init_View()
    {
        {
            init_TopBackground();
            init_BackHome();
            init_Title();
            vSetTitleText(R.string.target_train);
        }

        //ImageView
        {
            if(imageView_CreateButton == null)  { imageView_CreateButton = new RtxImageView(mContext); }
            if(imageView_InfoButton == null)    { imageView_InfoButton = new RtxImageView(mContext); }
            if(imageView_History == null)       { imageView_History = new RtxImageView(mContext); }
        }

        //TextView
        {
            if(textView_CreateText == null)     { textView_CreateText = new RtxTextView(mContext); }
            if(textView_History == null)        { textView_History = new RtxTextView(mContext); }
        }

        {
            if(fillerTextView_Create == null)   { fillerTextView_Create = new RtxFillerTextView(mContext); }
        }

        {
            if(imageView_ReturnExercisePage == null)    {   imageView_ReturnExercisePage = new RtxImageView(mContext);  }
        }

        {
            init_SimpleLayout();
        }
    }

    protected void init_SimpleLayout()
    {
        if(mTargetTrainSimple_Distance_Layout_Normal == null)   {mTargetTrainSimple_Distance_Layout_Normal = new TargetTrainSimple_Distance_Layout(mContext,0);}
        if(mTargetTrainSimple_Calories_Layout_Normal == null)   {mTargetTrainSimple_Calories_Layout_Normal = new TargetTrainSimple_Calories_Layout(mContext,0);}
        if(mTargetTrainSimple_Frequency_Layout_Normal == null)  {mTargetTrainSimple_Frequency_Layout_Normal = new TargetTrainSimple_Frequency_Layout(mContext,0);}
        if(mTargetTrainSimple_Weight_Layout_Normal == null)     {mTargetTrainSimple_Weight_Layout_Normal = new TargetTrainSimple_Weight_Layout(mContext,0);}
        if(mTargetTrainSimple_BodyFat_Layout_Normal == null)    {mTargetTrainSimple_BodyFat_Layout_Normal = new TargetTrainSimple_BodyFat_Layout(mContext,0);}

        if(mTargetTrainSimple_Distance_Layout_Exercise == null)   {mTargetTrainSimple_Distance_Layout_Exercise = new TargetTrainSimple_Distance_Layout_E(mContext,1);}
        if(mTargetTrainSimple_Calories_Layout_Exercise == null)   {mTargetTrainSimple_Calories_Layout_Exercise = new TargetTrainSimple_Calories_Layout_E(mContext,1);}
        if(mTargetTrainSimple_Frequency_Layout_Exercise == null)  {mTargetTrainSimple_Frequency_Layout_Exercise = new TargetTrainSimple_Frequency_Layout_E(mContext,1);}
        if(mTargetTrainSimple_Weight_Layout_Exercise == null)     {mTargetTrainSimple_Weight_Layout_Exercise = new TargetTrainSimple_Weight_Layout_E(mContext,1);}
        if(mTargetTrainSimple_BodyFat_Layout_Exercise == null)    {mTargetTrainSimple_BodyFat_Layout_Exercise = new TargetTrainSimple_BodyFat_Layout_E(mContext,1);}

        vMatchSimpleLayout();
    }

    protected void vMatchSimpleLayout()
    {
        mTargetTrainSimple_Distance_Layout = mTargetTrainSimple_Distance_Layout_Normal;
        mTargetTrainSimple_Calories_Layout = mTargetTrainSimple_Calories_Layout_Normal;
        mTargetTrainSimple_Frequency_Layout = mTargetTrainSimple_Frequency_Layout_Normal;
        mTargetTrainSimple_Weight_Layout = mTargetTrainSimple_Weight_Layout_Normal;
        mTargetTrainSimple_BodyFat_Layout = mTargetTrainSimple_BodyFat_Layout_Normal;
    }

    protected void init_event()
    {
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_InfoButton.setOnClickListener(mButtonListener);
        imageView_CreateButton.setOnClickListener(mButtonListener);
        fillerTextView_Create.setOnClickListener(mButtonListener);
        imageView_History.setOnClickListener(mButtonListener);
        textView_History.setOnClickListener(mButtonListener);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        {
            addRtxImagePaddingViewToLayout(imageView_InfoButton, R.drawable.info_icon, 886, 40, 34, 34, 30);//By Alan
        }
    }

    protected boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();

        if(bLogin)
        {
            if(!ExerciseData.b_is_exercising())
            {
                setCreateButtonState(0);
            }
        }
        else
        {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGIN,-1,-1);
            mMainActivity.dialogLayout_Login.fillerTextView_Login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    mMainActivity.mMainProcTreadmill.targetTrainProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcTreadmill.targetTrainProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    //mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcTreadmill.targetTrainProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });

            setCreateButtonState(0);
        }

        return bLogin;
    }

    protected void setCreateButtonState(int iTargetSize)
    {
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

            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_target, 28.06f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 132, 468, 75, Common.Color.login_button_yellow);
            fillerTextView_Create.setFillet(75 / 2);
            setCreateButtonEnable(false);
        }
        else
        {
            this.removeView(imageView_CreateButton);
            this.removeView(textView_CreateText);

            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_target, 28.06f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 132, 468, 75, Common.Color.login_button_yellow);
            fillerTextView_Create.setFillet(75 / 2);
            setCreateButtonEnable(true);
        }
    }

    protected void setCreateButtonEnable(boolean bEnable)
    {
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
            addViewToLayout(mTargetTrainSimple_Distance_Layout,getSimplePosX(data.targetTrainInfo_Dis.iOrder),230,426,570);
        }

        if(data.bIsCalories())
        {
            addViewToLayout(mTargetTrainSimple_Calories_Layout,getSimplePosX(data.targetTrainInfo_Cal.iOrder),230,426,570);
        }

        if(data.bIsFreq())
        {
            addViewToLayout(mTargetTrainSimple_Frequency_Layout,getSimplePosX(data.targetTrainInfo_Freq.iOrder),230,426,570);
        }

        if(data.bIsWeight())
        {
            addViewToLayout(mTargetTrainSimple_Weight_Layout,getSimplePosX(data.targetTrainInfo_Wei.iOrder),230,426,570);
        }

        if(data.bIsBodyFat())
        {
            addViewToLayout(mTargetTrainSimple_BodyFat_Layout,getSimplePosX(data.targetTrainInfo_BdyFat.iOrder),230,426,570);
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

    protected void init_SimpleTargetTrain(UiDataStruct.TargetTrainInfo data)
    {
        if(data.bIsDistance())
        {
            if(mTargetTrainSimple_Distance_Layout != null) {mTargetTrainSimple_Distance_Layout.init();}
        }

        if(data.bIsCalories())
        {
            if(mTargetTrainSimple_Calories_Layout != null) {mTargetTrainSimple_Calories_Layout.init();}
        }

        if(data.bIsFreq())
        {
            if(mTargetTrainSimple_Frequency_Layout != null) {mTargetTrainSimple_Frequency_Layout.init();}
        }

        if(data.bIsWeight())
        {
            if(mTargetTrainSimple_Weight_Layout != null) {mTargetTrainSimple_Weight_Layout.init();}
        }

        if(data.bIsBodyFat())
        {
            if(mTargetTrainSimple_BodyFat_Layout != null) {mTargetTrainSimple_BodyFat_Layout.init();}
        }
    }

    protected void display_SimpleTargetTrain(UiDataStruct.TargetTrainInfo data)
    {
        setCreateButtonState(data.getTargetCount());

        if(data.bIsDistance())
        {
            if(mTargetTrainSimple_Distance_Layout != null)
            {
                mTargetTrainSimple_Distance_Layout.removeAllViews();
                mTargetTrainSimple_Distance_Layout.add_SimpleBaseLayout();
                mTargetTrainSimple_Distance_Layout.display();
                mTargetTrainSimple_Distance_Layout.view_ClickLayout.setOnClickListener(mButtonListener);
                mTargetTrainSimple_Distance_Layout.setArg(data);
            }
        }

        if(data.bIsCalories())
        {
            if(mTargetTrainSimple_Calories_Layout != null)
            {
                mTargetTrainSimple_Calories_Layout.removeAllViews();
                mTargetTrainSimple_Calories_Layout.add_SimpleBaseLayout();
                mTargetTrainSimple_Calories_Layout.display();
                mTargetTrainSimple_Calories_Layout.view_ClickLayout.setOnClickListener(mButtonListener);
                mTargetTrainSimple_Calories_Layout.setArg(data);
            }
        }

        if(data.bIsFreq())
        {
            if(mTargetTrainSimple_Frequency_Layout != null)
            {
                mTargetTrainSimple_Frequency_Layout.removeAllViews();
                mTargetTrainSimple_Frequency_Layout.add_SimpleBaseLayout();
                mTargetTrainSimple_Frequency_Layout.display();
                mTargetTrainSimple_Frequency_Layout.view_ClickLayout.setOnClickListener(mButtonListener);
                mTargetTrainSimple_Frequency_Layout.setArg(data);
            }
        }

        if(data.bIsWeight())
        {
            if(mTargetTrainSimple_Weight_Layout != null)
            {
                mTargetTrainSimple_Weight_Layout.removeAllViews();
                mTargetTrainSimple_Weight_Layout.add_SimpleBaseLayout();
                mTargetTrainSimple_Weight_Layout.display();
                mTargetTrainSimple_Weight_Layout.view_ClickLayout.setOnClickListener(mButtonListener);
                mTargetTrainSimple_Weight_Layout.setArg(data);
            }
        }

        if(data.bIsBodyFat())
        {
            if(mTargetTrainSimple_BodyFat_Layout != null)
            {
                mTargetTrainSimple_BodyFat_Layout.removeAllViews();
                mTargetTrainSimple_BodyFat_Layout.add_SimpleBaseLayout();
                mTargetTrainSimple_BodyFat_Layout.display();
                mTargetTrainSimple_BodyFat_Layout.view_ClickLayout.setOnClickListener(mButtonListener);
                mTargetTrainSimple_BodyFat_Layout.setArg(data);
            }
        }
    }

    protected void refreshHistoryButton(int iCount)
    {
        if(iCount > 0)
        {
            addRtxImagePaddingViewToLayout(imageView_History, R.drawable.target_history_icon, 59, 39, 41, 37, 30);
            addRtxTextViewToLayout(textView_History, R.string.history_upper, 23.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 126 - 100, 46 - 25, 104 + 200, 18 + 50);
        }
        else
        {
            removeView(imageView_History);
            removeView(textView_History);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected void vBackHome()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vMainChangePage(MainState.PROC_HOME);
    }

//    protected void vShowInfoDialog()
//    {
//        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ONLY_INFO,-1,R.string.targer_dialog_info);
//    }

    protected void vShowDetailDistance()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_DISTANCE);
    }

    protected void vShowDetailCalories()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_CALORIES);
    }

    protected void vShowDetailWeight()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_WEIGHT);
    }

    protected void vShowDetailBodyFat()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_BODYFAT);
    }

    protected void vShowDetailFrequency()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_FREQUENCY);
    }

    protected void vShowChooseTargetPage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    protected void vShowHistoryPage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_HISTORY_MAIN);
    }

    protected void vShowInfoDialog()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.target_train);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.targer_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_target_train, -1, stitle01, null, sinfo01, null, "tt_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    protected void vClickReturnExercisePage()
    {

    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)                                         { vBackHome(); }
            else if(v == imageView_InfoButton)                                  { vShowInfoDialog(); }
            else if(v == imageView_CreateButton)                                { vShowChooseTargetPage(); }
            else if(v == mTargetTrainSimple_Distance_Layout.view_ClickLayout)   { vShowDetailDistance(); }
            else if(v == mTargetTrainSimple_Calories_Layout.view_ClickLayout)   { vShowDetailCalories(); }
            else if(v == mTargetTrainSimple_Weight_Layout.view_ClickLayout)     { vShowDetailWeight(); }
            else if(v == mTargetTrainSimple_BodyFat_Layout.view_ClickLayout)    { vShowDetailBodyFat(); }
            else if(v == mTargetTrainSimple_Frequency_Layout.view_ClickLayout)  { vShowDetailFrequency(); }
            else if(v == fillerTextView_Create)                                 { vShowChooseTargetPage(); }
            else if(v == imageView_History)                                     { vShowHistoryPage(); }
            else if(v == textView_History)                                      { vShowHistoryPage(); }
            else if(v == imageView_ReturnExercisePage)                          { vClickReturnExercisePage(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
