package com.rtx.treadmill.RtxMainFunction.MyWorkout;

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
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxView.RtxExerciseSimplePreview;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkoutLayout extends Rtx_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private Context mContext;

    private RtxImageView        imageView_InfoButton;
    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxFillerTextView   fillerTextView_Create;

    private RtxFillerTextView   fillerTextView_Start_1st;
    private RtxFillerTextView   fillerTextView_Start_2st;
    private RtxFillerTextView   fillerTextView_Start_3st;

    private RtxTextView         textView_Name_1st;
    private RtxTextView         textView_Name_2st;
    private RtxTextView         textView_Name_3st;

    private RtxExerciseSimplePreview simplePreview_1st;
    private RtxExerciseSimplePreview simplePreview_2st;
    private RtxExerciseSimplePreview simplePreview_3st;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;
    ArrayList<UiDataStruct.WORKOUT_ITEM_INFO> list_WorkoutItem;

    public MyWorkoutLayout(Context context, MainActivity mMainActivity) {
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

        setBackgroundColor(Common.Color.background_workout_main);
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

        if(check_Login())
        {
            setCreateButtonEnable(true);
        }
        else
        {
            setCreateButtonEnable(false);
        }
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_TopBackground();
            init_BackHome();
            init_Title();
            vSetTitleText(R.string.my_workout);
        }

        {
            if(imageView_InfoButton == null)            { imageView_InfoButton = new RtxImageView(mContext); }
            if(fillerTextView_Create == null)           { fillerTextView_Create = new RtxFillerTextView(mContext); }

            if(fillerTextView_Start_1st == null)        { fillerTextView_Start_1st = new RtxFillerTextView(mContext); }
            if(fillerTextView_Start_2st == null)        { fillerTextView_Start_2st = new RtxFillerTextView(mContext); }
            if(fillerTextView_Start_3st == null)        { fillerTextView_Start_3st = new RtxFillerTextView(mContext); }

            if(textView_Name_1st == null)               { textView_Name_1st = new RtxTextView(mContext); }
            if(textView_Name_2st == null)               { textView_Name_2st = new RtxTextView(mContext); }
            if(textView_Name_3st == null)               { textView_Name_3st = new RtxTextView(mContext); }

            if(simplePreview_1st == null)               { simplePreview_1st = new RtxExerciseSimplePreview(mContext,0); }
            if(simplePreview_2st == null)               { simplePreview_2st = new RtxExerciseSimplePreview(mContext,0); }
            if(simplePreview_3st == null)               { simplePreview_3st = new RtxExerciseSimplePreview(mContext,0); }
        }
    }

    private void init_event()
    {
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_InfoButton.setOnClickListener(mButtonListener);
        fillerTextView_Create.setOnClickListener(mButtonListener);

        textView_Name_1st.setOnClickListener(mButtonListener);
        textView_Name_2st.setOnClickListener(mButtonListener);
        textView_Name_3st.setOnClickListener(mButtonListener);

        simplePreview_1st.setOnClickListener(mButtonListener);
        simplePreview_2st.setOnClickListener(mButtonListener);
        simplePreview_3st.setOnClickListener(mButtonListener);

        fillerTextView_Start_1st.setOnClickListener(mButtonListener);
        fillerTextView_Start_2st.setOnClickListener(mButtonListener);
        fillerTextView_Start_3st.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxImagePaddingViewToLayout(imageView_InfoButton, R.drawable.info_icon, 910, 40, 34, 34, 30);//By Alan
        addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_workout, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 386, 363, 507, 75, Common.Color.login_button_yellow);
    }

    private void add_Preview(int iIndex , ArrayList<UiDataStruct.WORKOUT_ITEM_INFO> list_WorkoutItem)
    {
        if(iIndex == 0)      { add_Preview_1st(list_WorkoutItem.get(iIndex)); }
        else if(iIndex == 1) { add_Preview_2st(list_WorkoutItem.get(iIndex)); }
        else if(iIndex == 2) { add_Preview_3st(list_WorkoutItem.get(iIndex)); }
    }

    private void add_Preview_1st(UiDataStruct.WORKOUT_ITEM_INFO workout_Item)
    {
        addViewToLayout(simplePreview_1st, 81, 278, 418, 170);
        vSetPreviewInfo(simplePreview_1st,workout_Item);
    }

    private void add_Preview_2st(UiDataStruct.WORKOUT_ITEM_INFO workout_Item)
    {
        addViewToLayout(simplePreview_2st, 686, 278, 418, 170);
        vSetPreviewInfo(simplePreview_2st,workout_Item);
    }

    private void add_Preview_3st(UiDataStruct.WORKOUT_ITEM_INFO workout_Item)
    {
        addViewToLayout(simplePreview_3st, 81, 557, 418, 170);
        vSetPreviewInfo(simplePreview_3st,workout_Item);
    }

    private void vSetPreviewInfo(RtxExerciseSimplePreview preview, UiDataStruct.WORKOUT_ITEM_INFO workoutItem)
    {
        int iDataCount = 0;

        ArrayList<Float> list_speed = new ArrayList<Float>();
        workoutItem.bExportStageInfoToMinList(list_speed, workoutItem.TRANSLATE_SPEDD);

        ArrayList<Float> list_incline = new ArrayList<Float>();
        workoutItem.bExportStageInfoToMinList(list_incline, workoutItem.TRANSLATE_INCLINE);

        iDataCount = list_incline.size();

        preview.init(1f,60);
        preview.i_profile.vSetInclineList(list_incline);
        preview.i_profile.vSetSpeedList(list_speed);
        preview.i_profile.vSetflash(iDataCount-1);
    }

    private void add_Item(int iIndex , String sName)
    {
        if(iIndex == 0)      { add_Item_1st(sName); }
        else if(iIndex == 1) { add_Item_2st(sName); }
        else if(iIndex == 2) { add_Item_3st(sName); }
    }

    private void add_Item_1st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_1st, R.string.start, 18f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 514, 313, 115, 115, Common.Color.login_button_yellow);
        fillerTextView_Start_1st.setMode(1);
        addRtxTextViewToLayout(textView_Name_1st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 81 , 461 - 25, 417, 23 + 50);
    }

    private void add_Item_2st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_2st, R.string.start, 18f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1121, 313, 115, 115, Common.Color.login_button_yellow);
        fillerTextView_Start_2st.setMode(1);
        addRtxTextViewToLayout(textView_Name_2st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 686, 461 - 25, 417, 23 + 50);
    }

    private void add_Item_3st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_3st, R.string.start, 18f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 514, 593, 115, 115, Common.Color.login_button_yellow);
        fillerTextView_Start_3st.setMode(1);
        addRtxTextViewToLayout(textView_Name_3st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 81 , 741 - 25, 417, 23 + 50);
    }

    private boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();
        //Login
        if(bLogin)
        {

        }
        //Un Login
        else
        {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGIN,-1,-1);
            mMainActivity.dialogLayout_Login.fillerTextView_Login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    mMainActivity.mMainProcTreadmill.myWorkoutProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcTreadmill.myWorkoutProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    //mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcTreadmill.myWorkoutProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });

//            setCreateButtonState(0);
        }

        return bLogin;
    }

    protected void setCreateButtonEnable(boolean bEnable)
    {
        if(bEnable)
        {
            fillerTextView_Create.setBackgroundColor(Common.Color.login_button_yellow);
        }
        else
        {
            fillerTextView_Create.setBackgroundColor(Common.Color.gray_4);
        }

        fillerTextView_Create.setClickable(bEnable);
        fillerTextView_Create.setEnabled(bEnable);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem)
    {
        this.workoutItem = workoutItem;
    }

    protected void vUpdateNameInfo(ArrayList<UiDataStruct.WORKOUT_ITEM_INFO> list_WorkoutItem)
    {
        if(list_WorkoutItem == null)
        {
            return;
        }

        int iSize = list_WorkoutItem.size();
        int iIndex = 0;

        if(iSize > 0)
        {
            removeView(fillerTextView_Create);
            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_a_new_workout, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 386, 147, 507, 75, Common.Color.login_button_yellow);
            if(iSize >= 3)
            {
                setCreateButtonEnable(false);
            }
            else
            {
                setCreateButtonEnable(true);
            }

            for( ; iIndex < iSize ; iIndex++)
            {
                add_Item(iIndex,list_WorkoutItem.get(iIndex).sName);
            }
        }
    }

    protected void vUpdatePreviewInfo(ArrayList<UiDataStruct.WORKOUT_ITEM_INFO> list_WorkoutItem)
    {
        if(list_WorkoutItem == null)
        {
            return;
        }

        this.list_WorkoutItem = list_WorkoutItem;

        int iSize = list_WorkoutItem.size();
        int iIndex = 0;

        if(iSize > 0)
        {
            for( ; iIndex < iSize ; iIndex++)
            {
                add_Preview(iIndex,list_WorkoutItem);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void vBackHome()
    {
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vShowInfoDialog()
    {
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TITLE_INFO,R.string.information,R.string.workout_info);

        String stitle01 = LanguageData.s_get_string(mContext, R.string.my_workout);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.workout_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_my_workout, -1, stitle01, null, sinfo01, null, "mw_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickCreateButton()
    {
        workoutItem.clear();
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_NAME);
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetMode(MODE_CREATE);
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetSelectItemIndex(-1);
    }

    private void vClickItem(int iIndex)
    {
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_PREVIEW);
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetMode(MODE_PREVIEW);
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetSelectItemIndex(iIndex);
    }

    private void vClickStart(int iIndex)
    {
        if(iIndex < list_WorkoutItem.size())
        {
            list_WorkoutItem.get(iIndex).copyTo(workoutItem);
            mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXERCISE_PREPARE);
        }
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)                                         { vBackHome(); }
            else if(v == imageView_InfoButton)                                  { vShowInfoDialog(); }
            else if(v == fillerTextView_Create)                                 { vClickCreateButton(); }
            else if(v == textView_Name_1st)                                     { vClickItem(0); }
            else if(v == textView_Name_2st)                                     { vClickItem(1); }
            else if(v == textView_Name_3st)                                     { vClickItem(2); }
            else if(v == simplePreview_1st)                                     { vClickItem(0); }
            else if(v == simplePreview_2st)                                     { vClickItem(1); }
            else if(v == simplePreview_3st)                                     { vClickItem(2); }
            else if(v == fillerTextView_Start_1st)                              { vClickStart(0); }
            else if(v == fillerTextView_Start_2st)                              { vClickStart(1); }
            else if(v == fillerTextView_Start_3st)                              { vClickStart(2); }

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
