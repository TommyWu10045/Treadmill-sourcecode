package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.util.Log;
import android.view.View;

import com.retonix.circlecloud.Cloud_15_GET_BDY_IDX_REC;
import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataFunc;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.BodyManager.BodyManagerProc;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainDetail_BodyFat_Layout_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainDetail_Calories_Layout_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainDetail_Distance_Layout_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainDetail_Frequency_Layout_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainDetail_Weight_Layout_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainHistory_Main_E;
import com.rtx.treadmill.RtxMainFunctionBike.TargetTrain.TargetTrainExerciseLayout.TargetTrainMainLayout_E;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chasechang on 3/27/17.
 */

public class TargetTrainProc {
    private String TAG = "TargetTrainProc" ;

    private MainActivity mMainActivity ;

    private TargetTrainState mState = TargetTrainState.PROC_INIT ;
    private TargetTrainState mNextState = TargetTrainState.PROC_NULL ;
    private TargetTrainState tempState = TargetTrainState.PROC_NULL ;
    private TargetTrainState mPreState = TargetTrainState.PROC_NULL ;

    private TargetTrainMainLayout mTargetTrainMainLayout;

    private BodyManagerProc                             mBodyManagerProc;

    private TargetTrainDetail_Distance_Layout           mTargetTrainDetail_Distance_Layout;
    private TargetTrainDetail_Calories_Layout           mTargetTrainDetail_Calories_Layout;
    private TargetTrainDetail_Weight_Layout             mTargetTrainDetail_Weight_Layout;
    private TargetTrainDetail_BodyFat_Layout            mTargetTrainDetail_BodyFat_Layout;
    private TargetTrainDetail_Frequency_Layout          mTargetTrainDetail_Frequency_Layout;

    private TargetTrainInput_CurrentWeight_Layout       mTargetTrainInput_CurrentWeight_Layout;
    private TargetTrainInput_CurrentBodyFat_Layout      mTargetTrainInput_CurrentBodyFat_Layout;

    private TargetTrainChooseTargetLayout               mTargetTrainChooseTargetLayout;

    private TargetTrainAdd_SelectType                   mTargetTrainAdd_SelectType;
    private TargetTrainAdd_Dis_Set                      mTargetTrainAdd_Dis_Set;
    private TargetTrainAdd_Cal_SetCal_Layout            mTargetTrainAdd_Cal_SetCal_Layout;
    private TargetTrainAdd_Weight_SetCurrent            mTargetTrainAdd_Weight_SetCurrent;
    private TargetTrainAdd_Weight_SetTarget             mTargetTrainAdd_Weight_SetTarget;
    private TargetTrainAdd_BodyFat_SetCurrent           mTargetTrainAdd_BodyFat_SetCurrent;
    private TargetTrainAdd_BodyFat_SetTarget            mTargetTrainAdd_BodyFat_SetTarget;
    private TargetTrainAdd_Freq_SelectDayPerWeek_Layout mTargetTrainAdd_Freq_SelectDayPerWeek_Layout;

    private TargetTrainCalendar_Day_Layout              mTargetTrainCalendar_Day_Layout;
    private TargetTrainCalendar_Week_Layout             mTargetTrainCalendar_Week_Layout;

    private TargetTrainHistory_Main                     mTargetTrainHistory_Main;

    private TargetTrainMainLayout                       mTargetTrainMainLayout_Normal;
    private TargetTrainMainLayout_E                     mTargetTrainMainLayout_Exercise;

    private TargetTrainDetail_Distance_Layout           mTargetTrainDetail_Distance_Layout_Normal;
    private TargetTrainDetail_Calories_Layout           mTargetTrainDetail_Calories_Layout_Normal;
    private TargetTrainDetail_Weight_Layout             mTargetTrainDetail_Weight_Layout_Normal;
    private TargetTrainDetail_BodyFat_Layout            mTargetTrainDetail_BodyFat_Layout_Normal;
    private TargetTrainDetail_Frequency_Layout          mTargetTrainDetail_Frequency_Layout_Normal;
    private TargetTrainHistory_Main                     mTargetTrainHistory_Main_Normal;

    private TargetTrainDetail_Distance_Layout_E         mTargetTrainDetail_Distance_Layout_Exercise;
    private TargetTrainDetail_Calories_Layout_E         mTargetTrainDetail_Calories_Layout_Exercise;
    private TargetTrainDetail_Weight_Layout_E           mTargetTrainDetail_Weight_Layout_Exercise;
    private TargetTrainDetail_BodyFat_Layout_E          mTargetTrainDetail_BodyFat_Layout_Exercise;
    private TargetTrainDetail_Frequency_Layout_E        mTargetTrainDetail_Frequency_Layout_Exercise;
    private TargetTrainHistory_Main_E                   mTargetTrainHistory_Main_Exercise;

    UiDataStruct.TargetTrainInfo                        mTargetTrainInfo;

    ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL>        list_CloudTargetGoal = CloudDataStruct.CloudData_24.list_CloudTargetGoal;
    CloudDataStruct.CLOUD_TATGET_GOAL                   cloud_TargetGoal = new CloudDataStruct.CLOUD_TATGET_GOAL();

    ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE>  list_CloudTargetGoalClose = CloudDataStruct.CloudData_59.list_CloudTargetGoal;
    CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE             cloud_TargetGoalClose = new CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE();

    private boolean bServerResponseFlag_GetGoal = false;
    private boolean bServerResponseFlag_UploadUserInfo = false;
    private boolean bServerResponseFlag_UploadTargetGoal = false;
    private boolean bServerResponseFlag_GetGoalClose = false;
    private boolean bServerResponseFlag_DeleteGoalClose = false;
    private boolean bServerResponseFlag_AddGoalClose = false;

    private int     iServerResponseResult_GetGoal = -1;
    private int     iServerResponseResult_UploadUserInfo = -1;
    private int     iServerResponseResult_UploadTargetGoal = -1;
    private int     iServerResponseResult_GetGoalClose = -1;
    private int     iServerResponseResult_DeleteGoalClose = -1;
    private int     iServerResponseResult_AddGoalClose = -1;


    private int         iCount = 0;
    private Calendar    cStartValidDate = GlobalData.getInstance();
    private String      sGoalSeq = null;
    private Boolean     bFlag_Delete = false;
//    private Boolean     bFlag_AlreadyCheck = false;

    public TargetTrainProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = TargetTrainState.PROC_INIT ;

        //Info
        if(mTargetTrainInfo == null)             { mTargetTrainInfo = new UiDataStruct.TargetTrainInfo(); }

        if(mBodyManagerProc == null)                                { mBodyManagerProc = new BodyManagerProc(mMainActivity); }

        //Layout
//        if(mTargetTrainMainLayout == null)                          { mTargetTrainMainLayout = new TargetTrainMainLayout(mMainActivity.mContext , mMainActivity); }
//        if(mTargetTrainDetail_Distance_Layout == null)              { mTargetTrainDetail_Distance_Layout = new TargetTrainDetail_Distance_Layout(mMainActivity.mContext , mMainActivity); }
//        if(mTargetTrainDetail_Calories_Layout == null)              { mTargetTrainDetail_Calories_Layout = new TargetTrainDetail_Calories_Layout(mMainActivity.mContext , mMainActivity); }
//        if(mTargetTrainDetail_Weight_Layout == null)                { mTargetTrainDetail_Weight_Layout = new TargetTrainDetail_Weight_Layout(mMainActivity.mContext , mMainActivity); }
//        if(mTargetTrainDetail_BodyFat_Layout == null)               { mTargetTrainDetail_BodyFat_Layout = new TargetTrainDetail_BodyFat_Layout(mMainActivity.mContext , mMainActivity); }
//        if(mTargetTrainDetail_Frequency_Layout == null)             { mTargetTrainDetail_Frequency_Layout = new TargetTrainDetail_Frequency_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainChooseTargetLayout == null)                  { mTargetTrainChooseTargetLayout = new TargetTrainChooseTargetLayout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainInput_CurrentWeight_Layout == null)          { mTargetTrainInput_CurrentWeight_Layout = new TargetTrainInput_CurrentWeight_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainInput_CurrentBodyFat_Layout == null)         { mTargetTrainInput_CurrentBodyFat_Layout = new TargetTrainInput_CurrentBodyFat_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_Freq_SelectDayPerWeek_Layout == null)    { mTargetTrainAdd_Freq_SelectDayPerWeek_Layout = new TargetTrainAdd_Freq_SelectDayPerWeek_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_Cal_SetCal_Layout == null)               { mTargetTrainAdd_Cal_SetCal_Layout = new TargetTrainAdd_Cal_SetCal_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainCalendar_Day_Layout == null)                 { mTargetTrainCalendar_Day_Layout = new TargetTrainCalendar_Day_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainCalendar_Week_Layout == null)                { mTargetTrainCalendar_Week_Layout = new TargetTrainCalendar_Week_Layout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_BodyFat_SetCurrent == null)              { mTargetTrainAdd_BodyFat_SetCurrent = new TargetTrainAdd_BodyFat_SetCurrent(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_BodyFat_SetTarget == null)               { mTargetTrainAdd_BodyFat_SetTarget = new TargetTrainAdd_BodyFat_SetTarget(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_Weight_SetCurrent == null)               { mTargetTrainAdd_Weight_SetCurrent = new TargetTrainAdd_Weight_SetCurrent(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_Weight_SetTarget == null)                { mTargetTrainAdd_Weight_SetTarget = new TargetTrainAdd_Weight_SetTarget(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_Dis_Set == null)                         { mTargetTrainAdd_Dis_Set = new TargetTrainAdd_Dis_Set(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainAdd_SelectType == null)                      { mTargetTrainAdd_SelectType = new TargetTrainAdd_SelectType(mMainActivity.mContext , mMainActivity); }
        //if(mTargetTrainHistory_Main == null)                        { mTargetTrainHistory_Main = new TargetTrainHistory_Main(mMainActivity.mContext , mMainActivity); }

        if(mTargetTrainMainLayout_Normal == null)                   { mTargetTrainMainLayout_Normal = new TargetTrainMainLayout(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainDetail_Distance_Layout_Normal == null)       { mTargetTrainDetail_Distance_Layout_Normal = new TargetTrainDetail_Distance_Layout(mMainActivity.mContext , mMainActivity,0); }
        if(mTargetTrainDetail_Calories_Layout_Normal == null)       { mTargetTrainDetail_Calories_Layout_Normal = new TargetTrainDetail_Calories_Layout(mMainActivity.mContext , mMainActivity,0); }
        if(mTargetTrainDetail_Weight_Layout_Normal == null)         { mTargetTrainDetail_Weight_Layout_Normal = new TargetTrainDetail_Weight_Layout(mMainActivity.mContext , mMainActivity,0); }
        if(mTargetTrainDetail_BodyFat_Layout_Normal == null)        { mTargetTrainDetail_BodyFat_Layout_Normal = new TargetTrainDetail_BodyFat_Layout(mMainActivity.mContext , mMainActivity,0); }
        if(mTargetTrainDetail_Frequency_Layout_Normal == null)      { mTargetTrainDetail_Frequency_Layout_Normal = new TargetTrainDetail_Frequency_Layout(mMainActivity.mContext , mMainActivity,0); }
        if(mTargetTrainHistory_Main_Normal == null)                 { mTargetTrainHistory_Main_Normal = new TargetTrainHistory_Main(mMainActivity.mContext , mMainActivity); }

        if(mTargetTrainMainLayout_Exercise == null)                 { mTargetTrainMainLayout_Exercise = new TargetTrainMainLayout_E(mMainActivity.mContext , mMainActivity); }
        if(mTargetTrainDetail_Distance_Layout_Exercise == null)     { mTargetTrainDetail_Distance_Layout_Exercise = new TargetTrainDetail_Distance_Layout_E(mMainActivity.mContext , mMainActivity,1); }
        if(mTargetTrainDetail_Calories_Layout_Exercise == null)     { mTargetTrainDetail_Calories_Layout_Exercise = new TargetTrainDetail_Calories_Layout_E(mMainActivity.mContext , mMainActivity,1); }
        if(mTargetTrainDetail_Weight_Layout_Exercise == null)       { mTargetTrainDetail_Weight_Layout_Exercise = new TargetTrainDetail_Weight_Layout_E(mMainActivity.mContext , mMainActivity,1); }
        if(mTargetTrainDetail_BodyFat_Layout_Exercise == null)      { mTargetTrainDetail_BodyFat_Layout_Exercise = new TargetTrainDetail_BodyFat_Layout_E(mMainActivity.mContext , mMainActivity,1); }
        if(mTargetTrainDetail_Frequency_Layout_Exercise == null)    { mTargetTrainDetail_Frequency_Layout_Exercise = new TargetTrainDetail_Frequency_Layout_E(mMainActivity.mContext , mMainActivity,1); }
        if(mTargetTrainHistory_Main_Exercise == null)               { mTargetTrainHistory_Main_Exercise = new TargetTrainHistory_Main_E(mMainActivity.mContext , mMainActivity); }
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(TargetTrainState nextState)
    {
        mNextState = nextState;
    }

    public void vSetPreState(TargetTrainState preState)
    {
        mPreState = preState;
    }

    public TargetTrainState vGerPreState()
    {
        return mPreState;
    }

    public void vSetGoalSeq(String sSeq)
    {
        sGoalSeq = new String(sSeq);
    }

    /* ------------------------------------------------------------------------ */

    private void vProc_Init()
    {
//        bFlag_AlreadyCheck = false;
        mState = TargetTrainState.PROC_SHOW_PAGE_MAIN;

        if(ExerciseData.b_is_exercising())
        {
            mTargetTrainMainLayout = mTargetTrainMainLayout_Exercise;
            mTargetTrainDetail_Distance_Layout = mTargetTrainDetail_Distance_Layout_Exercise;
            mTargetTrainDetail_Calories_Layout = mTargetTrainDetail_Calories_Layout_Exercise;
            mTargetTrainDetail_Weight_Layout = mTargetTrainDetail_Weight_Layout_Exercise;
            mTargetTrainDetail_BodyFat_Layout = mTargetTrainDetail_BodyFat_Layout_Exercise;
            mTargetTrainDetail_Frequency_Layout = mTargetTrainDetail_Frequency_Layout_Exercise;
            mTargetTrainHistory_Main = mTargetTrainHistory_Main_Exercise;
            Log.e("Jerry","********************************************************** exercise");
        }
        else
        {
            mTargetTrainMainLayout = mTargetTrainMainLayout_Normal;
            mTargetTrainDetail_Distance_Layout = mTargetTrainDetail_Distance_Layout_Normal;
            mTargetTrainDetail_Calories_Layout = mTargetTrainDetail_Calories_Layout_Normal;
            mTargetTrainDetail_Weight_Layout = mTargetTrainDetail_Weight_Layout_Normal;
            mTargetTrainDetail_BodyFat_Layout = mTargetTrainDetail_BodyFat_Layout_Normal;
            mTargetTrainDetail_Frequency_Layout = mTargetTrainDetail_Frequency_Layout_Normal;
            mTargetTrainHistory_Main = mTargetTrainHistory_Main_Normal;
            Log.e("Jerry","********************************************************** normal");
        }
    }

    private void vProc_LoadGoal()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetGoal).start();

                new Thread(mBodyManagerProc.CloudRunnable_All).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_LOAD_GOAL_CHECK;
    }

    private void vProc_LoadGoal_Check()
    {
        if(bServerResponseFlag_GetGoal)
        {
            bServerResponseFlag_GetGoal = false;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetGoal == 0)
                    {
                        //Success
                        vSetNextState(TargetTrainState.PROC_REFRESH_SIMPLE_INFO);
                    }
                    else
                    {
                        //Fail
                        vSetNextState(TargetTrainState.PROC_REFRESH_SIMPLE_INFO);
                        Log.e("Jerry","Load Goal Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = TargetTrainState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_RefreshTargetTrainSimpleLayout()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainMainLayout.setTargetTrainInfo(mTargetTrainInfo);
                mTargetTrainMainLayout.init_SimpleTargetTrain(mTargetTrainInfo);
                mTargetTrainMainLayout.display_SimpleTargetTrain(mTargetTrainInfo);

                if(list_CloudTargetGoalClose != null)
                {
                    mTargetTrainMainLayout.refreshHistoryButton(list_CloudTargetGoalClose.size());
                }

//                if(bFlag_AlreadyCheck == false)
                //運動中不檢查是否完成目標
                if(ExerciseData.b_is_exercising())
                {

                }
                else
                {
                    vSetNextState(TargetTrainState.PROC_CHECK_TARGET_STATUS);
//                    bFlag_AlreadyCheck = true;
                }
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void vProc_CloseTarget()
    {
        cloud_TargetGoal.setDataForDelete();
        vSetNextState(TargetTrainState.PROC_CLOUD_ADD_GOAL_CLOSE);
    }

    private void vProc_ContinueTarget()
    {
        cloud_TargetGoal.setDataForAlreadyPush();
        cloud_TargetGoal.setDataForContinue();
        vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    private void vProc_AlreadyPushTarget()
    {
        cloud_TargetGoal.setDataForAlreadyPush();
        vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    private void vShowTargatEndDialog()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TARGET_END,cloud_TargetGoalClose,null);
                mMainActivity.dialogLayout_TargetEnd.fillerTextView_CONTINUE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vProc_ContinueTarget();
                        mMainActivity.dismissInfoDialog();
                    }
                });

                mMainActivity.dialogLayout_TargetEnd.fillerTextView_END.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vProc_CloseTarget();
                        mMainActivity.dismissInfoDialog();
                    }
                });
            }
        });
    }

    private void vShowTargetHalfWayDialog()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TARGET_CLOSE_INFO,cloud_TargetGoalClose,true);
                mMainActivity.dialogLayout_TargetCloseInfo.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vProc_AlreadyPushTarget();
                        mMainActivity.dismissInfoDialog();
                    }
                });
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    private boolean bCheckTargetTrainStatus(UiDataStruct.TargetTrainValItem data)
    {
        boolean bResult = false;

        if(data.bIsTargetReached())
        {
            cloud_TargetGoal.copy(data.cloud_TargetGol);
            data.vExportDataToTargetClose(cloud_TargetGoalClose);

            vProc_CloseTarget();

            bResult = true;
        }

        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }

    private boolean bCheckTargetTrainStatus(UiDataStruct.TargetTrainFreqItem data)
    {
        boolean bResult = false;

        if(data.bIsTargetReached())
        {
            cloud_TargetGoal.copy(data.cloud_TargetGol);
            data.vExportDataToTargetClose(cloud_TargetGoalClose);

            vProc_CloseTarget();

            bResult = true;
        }

        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    private boolean bCheckTargetTrainEndStatus(UiDataStruct.TargetTrainValItem data)
    {
        boolean bResult = false;

        if(data.bContinue == false)
        {
            if(data.bIsDaysReached())
            {
                cloud_TargetGoal.copy(data.cloud_TargetGol);
                data.vExportDataToTargetClose(cloud_TargetGoalClose);

                vShowTargatEndDialog();
                bResult = true;
            }
        }


        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }

    private boolean bCheckTargetTrainEndStatus(UiDataStruct.TargetTrainFreqItem data)
    {
        boolean bResult = false;

        if(data.bContinue == false)
        {
            if(data.bIsDaysReached())
            {
                cloud_TargetGoal.copy(data.cloud_TargetGol);
                data.vExportDataToTargetClose(cloud_TargetGoalClose);

                if(cloud_TargetGoalClose.sGOL_CLS_LVL.equals("3"))  //good try
                {
                    //Frequency 時間到判定完成, 不需要 CONTINUE
//                    vShowTargatEndDialog();
                    vProc_CloseTarget();
                }
                else    //Gold or Silver
                {
                    vProc_CloseTarget();
                }

                bResult = true;
            }
        }


        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    private boolean bCheckTargetTrainHalfWayStatus(UiDataStruct.TargetTrainValItem data)
    {
        boolean bResult = false;

        if(data.bIsHalfDayReached() && data.cloud_TargetGol.sGol_Tag.equals("0"))
        {
            cloud_TargetGoal.copy(data.cloud_TargetGol);
            data.vExportDataToTargetClose(cloud_TargetGoalClose);

            vShowTargetHalfWayDialog();

            bResult = true;
        }

        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }

    private boolean bCheckTargetTrainHalfWayStatus(UiDataStruct.TargetTrainFreqItem data)
    {
        boolean bResult = false;

        if(data.bIsHalfDayReached() && data.cloud_TargetGol.sGol_Tag.equals("0"))
        {
            cloud_TargetGoal.copy(data.cloud_TargetGol);
            data.vExportDataToTargetClose(cloud_TargetGoalClose);

            vShowTargetHalfWayDialog();

            bResult = true;
        }

        mState = TargetTrainState.PROC_IDLE;

        return bResult;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean vCheckTargetReachedStatus()
    {
        if(mTargetTrainInfo.bIsDistance())
        {
            if(bCheckTargetTrainStatus(mTargetTrainInfo.targetTrainInfo_Dis))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsCalories())
        {
            if(bCheckTargetTrainStatus(mTargetTrainInfo.targetTrainInfo_Cal))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsFreq())
        {
            if(bCheckTargetTrainStatus(mTargetTrainInfo.targetTrainInfo_Freq))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsWeight())
        {
            if(bCheckTargetTrainStatus(mTargetTrainInfo.targetTrainInfo_Wei))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsBodyFat())
        {
            if(bCheckTargetTrainStatus(mTargetTrainInfo.targetTrainInfo_BdyFat))
            {
                return true;
            }
        }

        return false;
    }

    private boolean vCheckDaysReachedStatus()
    {
        boolean bResult = false;

        if(mTargetTrainInfo.bIsDistance())
        {
            if(bCheckTargetTrainEndStatus(mTargetTrainInfo.targetTrainInfo_Dis))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsCalories())
        {
            if(bCheckTargetTrainEndStatus(mTargetTrainInfo.targetTrainInfo_Cal))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsFreq())
        {
            if(bCheckTargetTrainEndStatus(mTargetTrainInfo.targetTrainInfo_Freq))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsWeight())
        {
            if(bCheckTargetTrainEndStatus(mTargetTrainInfo.targetTrainInfo_Wei))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsBodyFat())
        {
            if(bCheckTargetTrainEndStatus(mTargetTrainInfo.targetTrainInfo_BdyFat))
            {
                return true;
            }
        }

        return bResult;
    }

    private boolean vCheckHalfWayStatus()
    {
        boolean bResult = false;

        if(mTargetTrainInfo.bIsDistance())
        {
            if(bCheckTargetTrainHalfWayStatus(mTargetTrainInfo.targetTrainInfo_Dis))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsCalories())
        {
            if(bCheckTargetTrainHalfWayStatus(mTargetTrainInfo.targetTrainInfo_Cal))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsFreq())
        {
            if(bCheckTargetTrainHalfWayStatus(mTargetTrainInfo.targetTrainInfo_Freq))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsWeight())
        {
            if(bCheckTargetTrainHalfWayStatus(mTargetTrainInfo.targetTrainInfo_Wei))
            {
                return true;
            }
        }

        if(mTargetTrainInfo.bIsBodyFat())
        {
            if(bCheckTargetTrainHalfWayStatus(mTargetTrainInfo.targetTrainInfo_BdyFat))
            {
                return true;
            }
        }

        return bResult;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vProc_CheckTargetTrainStatus()
    {
        boolean bResult = false;

        bResult = vCheckTargetReachedStatus();

        if(!bResult)
        {
            bResult = vCheckDaysReachedStatus();
        }

        if(!bResult)
        {
            bResult = vCheckHalfWayStatus();
        }

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_Idle()
    {
        if(mNextState != TargetTrainState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = TargetTrainState.PROC_NULL;
        }
    }

    private void vProc_Exit()
    {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = TargetTrainState.PROC_INIT ;
        mNextState = TargetTrainState.PROC_NULL;
    }

    private void vProc_ShowPage_Main()
    {
        vChangeDisplayPage(mTargetTrainMainLayout);

        if(CloudDataStruct.CloudData_20.is_log_in())
        {
            vSetNextState(TargetTrainState.PROC_CLOUD_LOAD_GOAL);
        }

        mState = TargetTrainState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Detail_Distance()
    {
        vChangeDisplayPage(mTargetTrainDetail_Distance_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainDetail_Distance_Layout.setTargetTrainInfo(mTargetTrainInfo,cloud_TargetGoal,cloud_TargetGoalClose);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_Detail_Calories()
    {
        vChangeDisplayPage(mTargetTrainDetail_Calories_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainDetail_Calories_Layout.setTargetTrainInfo(mTargetTrainInfo,cloud_TargetGoal,cloud_TargetGoalClose);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_Detail_Weight()
    {
        vChangeDisplayPage(mTargetTrainDetail_Weight_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainDetail_Weight_Layout.setTargetTrainInfo(mTargetTrainInfo,cloud_TargetGoal,cloud_TargetGoalClose);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_Detail_BodyFat()
    {
        vChangeDisplayPage(mTargetTrainDetail_BodyFat_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainDetail_BodyFat_Layout.setTargetTrainInfo(mTargetTrainInfo,cloud_TargetGoal,cloud_TargetGoalClose);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_Detail_Frequency()
    {
        vChangeDisplayPage(mTargetTrainDetail_Frequency_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainDetail_Frequency_Layout.setTargetTrainInfo(mTargetTrainInfo,cloud_TargetGoal,cloud_TargetGoalClose);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_ChooseTarget()
    {
        vChangeDisplayPage(mTargetTrainChooseTargetLayout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainChooseTargetLayout.setTargetTrainInfo(mTargetTrainInfo);
                mTargetTrainChooseTargetLayout.vSetCloudTargetGoal(cloud_TargetGoal);
                mTargetTrainCalendar_Day_Layout.vClearCalendarView();
                mTargetTrainCalendar_Week_Layout.vClearCalendarView();
                cloud_TargetGoal.clear();
                cloud_TargetGoal.setDataForAdd();
            }
        });

        vSetPreState(TargetTrainState.PROC_NULL);

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_ModifyCurrentWeight()
    {
        vChangeDisplayPage(mTargetTrainInput_CurrentWeight_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainInput_CurrentWeight_Layout.setCurrentVal_Metric(mTargetTrainInfo.targetTrainInfo_Wei.fCurrentVal);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_ModifyCurrentBodyFat()
    {
        vChangeDisplayPage(mTargetTrainInput_CurrentBodyFat_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainInput_CurrentBodyFat_Layout.setCurrentVal(mTargetTrainInfo.targetTrainInfo_BdyFat.fCurrentVal);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddSelectType()
    {
        vChangeDisplayPage(mTargetTrainAdd_SelectType);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                {
                    mTargetTrainAdd_SelectType.vSetCloudTargetGoal(cloud_TargetGoal);
                }
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddDistanceSetVal()
    {
        vChangeDisplayPage(mTargetTrainAdd_Dis_Set);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {

                {
                    mTargetTrainAdd_Dis_Set.vSetCloudTargetGoal(cloud_TargetGoal);
                }
            }
        });

        vSetPreState(TargetTrainState.PROC_SHOW_PAGE_ADD_DIS_SET);
        mState = TargetTrainState.PROC_SHOW_DISTANCE_REFRESH;
    }

    private void vProc_ShowPage_distance_refresh() {

        if(mTargetTrainAdd_Dis_Set != null)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mTargetTrainAdd_Dis_Set.v_Slide_bar_refresh();
                }
            });

        }

        vProc_Idle();
    }

    private void vProc_ShowPage_AddWeiSetCurrent()
    {
        vChangeDisplayPage(mTargetTrainAdd_Weight_SetCurrent);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {

                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI] != null)
                {
//                    float fWei = 0;
//
//                    fWei = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei);
//
//                    if(fWei <= 0)
//                    {
//                        if(Rtx_TranslateValue.fString2Float(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI]) <= 0)
//                        {
//                            cloud_TargetGoal.setDefaultWei();
//                        }
//                        else
//                        {
//                            cloud_TargetGoal.sUsr_Wei = CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI];
//                        }
//                    }

                    //2018/12/4 變更規格 weight default value參考body manager
                    if(mBodyManagerProc.bGet_ServerResponseFlag())
                    {
                        if (Rtx_TranslateValue.fString2Float(CloudDataStruct.BodyIndexData_15.sdata_out[Cloud_15_GET_BDY_IDX_REC.Output.Weight]) <= 0)
                        {
                            cloud_TargetGoal.setDefaultWei();
                        }
                        else
                        {
                            cloud_TargetGoal.sUsr_Wei = CloudDataStruct.BodyIndexData_15.sdata_out[Cloud_15_GET_BDY_IDX_REC.Output.Weight];
                        }
                    }

                    mTargetTrainAdd_Weight_SetCurrent.setCurrentVal_Metric(Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei));
                    mTargetTrainAdd_Weight_SetCurrent.vSetCloudTargetGoal(cloud_TargetGoal);
                }
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddWeiSetTarget()
    {
        vChangeDisplayPage(mTargetTrainAdd_Weight_SetTarget);

        float fWei = 0;

        fWei = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sGol_Val);

        if(fWei <= 0)
        {
            cloud_TargetGoal.sGol_Val = cloud_TargetGoal.sUsr_Wei;
        }

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainAdd_Weight_SetTarget.vSetCloudTargetGoal(cloud_TargetGoal);
            }
        });

        vSetPreState(TargetTrainState.PROC_SHOW_PAGE_ADD_WEI_SET_TARGET);
        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddBodyFatSetCurrent()
    {
        vChangeDisplayPage(mTargetTrainAdd_BodyFat_SetCurrent);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {

                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT] != null)
                {

//                    float fWei = 0;
//
//                    fWei = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei);
//
//                    if(fWei <= 0)
//                    {
//                        if(Rtx_TranslateValue.fString2Float(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT]) <= 0)
//                        {
//                            cloud_TargetGoal.setDefaultBodyFat();
//                        }
//                        else
//                        {
//                            cloud_TargetGoal.sUsr_Wei = CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT];
//                        }
//                    }

                    //2018/12/4 變更規格 BodyFat default value參考body manager
                    if(mBodyManagerProc.bGet_ServerResponseFlag())
                    {
                        if (Rtx_TranslateValue.fString2Float(CloudDataStruct.BodyIndexData_15.sdata_out[Cloud_15_GET_BDY_IDX_REC.Output.BDY_FAT]) <= 0)
                        {
                            cloud_TargetGoal.setDefaultBodyFat();
                        }
                        else
                        {
                            cloud_TargetGoal.sUsr_Wei = CloudDataStruct.BodyIndexData_15.sdata_out[Cloud_15_GET_BDY_IDX_REC.Output.BDY_FAT];
                        }
                    }

                    mTargetTrainAdd_BodyFat_SetCurrent.setCurrentVal(Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sUsr_Wei));
                    mTargetTrainAdd_BodyFat_SetCurrent.vSetCloudTargetGoal(cloud_TargetGoal);
                }
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddBodyFatSetTarget()
    {
        vChangeDisplayPage(mTargetTrainAdd_BodyFat_SetTarget);

        float fWei = 0;

        fWei = Rtx_TranslateValue.fString2Float(cloud_TargetGoal.sGol_Val);

        if(fWei <= 0)
        {
            cloud_TargetGoal.sGol_Val = cloud_TargetGoal.sUsr_Wei;
        }

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainAdd_BodyFat_SetTarget.vSetCloudTargetGoal(cloud_TargetGoal);
            }
        });

        vSetPreState(TargetTrainState.PROC_SHOW_PAGE_ADD_BDY_FAT_SET_TARGET);
        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddFreqSelectDay()
    {
        vChangeDisplayPage(mTargetTrainAdd_Freq_SelectDayPerWeek_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainAdd_Freq_SelectDayPerWeek_Layout.vSetCloudTargetGoal(cloud_TargetGoal);
            }
        });

        cloud_TargetGoal.setType(EngSetting.ALL);

        vSetPreState(TargetTrainState.PROC_SHOW_PAGE_ADD_FREQ_SELECT_DAY);
        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_AddCalSetVal()
    {
        vChangeDisplayPage(mTargetTrainAdd_Cal_SetCal_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainAdd_Cal_SetCal_Layout.vSetCloudTargetGoal(cloud_TargetGoal);

            }
        });

        vSetPreState(TargetTrainState.PROC_SHOW_PAGE_ADD_CAL_SET_CAL);
        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_SetDay()
    {
        {
            cStartValidDate = GlobalData.getInstance();
            cStartValidDate.add(Calendar.DATE,1);
            cStartValidDate.set(Calendar.AM_PM,Calendar.AM);
            cStartValidDate.set(Calendar.HOUR,0);
            cStartValidDate.set(Calendar.MINUTE,0);
            cStartValidDate.set(Calendar.SECOND,0);
            cStartValidDate.set(Calendar.MILLISECOND,0);
        }

        vChangeDisplayPage(mTargetTrainCalendar_Day_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainCalendar_Day_Layout.vSetCloudTargetGoal(cloud_TargetGoal);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_SetWeek()
    {
        cStartValidDate = mTargetTrainAdd_Freq_SelectDayPerWeek_Layout.cGetStartValidDate();

        vChangeDisplayPage(mTargetTrainCalendar_Week_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mTargetTrainCalendar_Week_Layout.vSetCloudTargetGoal(cloud_TargetGoal);
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }

    private void vProc_ShowPage_HistoryMain()
    {
        vChangeDisplayPage(mTargetTrainHistory_Main);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(list_CloudTargetGoalClose != null)
                {
                    mTargetTrainHistory_Main.vSetCloudTargetGoalCloseList(list_CloudTargetGoalClose);
                }
            }
        });

        mState = TargetTrainState.PROC_IDLE;
    }
////////////////////////////////
    private void vProc_UploadWeight()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                {
                    CloudDataFunc.setWeight2UserInfo(mTargetTrainInput_CurrentWeight_Layout.getCurrentVal_Metric());
                }

                new Thread(CloudRunnable_UploadUserInfo).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_UPLOAD_WEIGHT_CHECK;
    }

    private void vProc_UploadWeight_Check()
    {
        if(bServerResponseFlag_UploadUserInfo)
        {
            bServerResponseFlag_UploadUserInfo = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_UploadUserInfo == 0)
                    {
                        //Success
                        mTargetTrainInfo.targetTrainInfo_Wei.fCurrentVal = mTargetTrainInput_CurrentWeight_Layout.getCurrentVal_Metric();
                        vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_WEIGHT);
                    }
                    else
                    {
                        //Fail
                        CloudDataFunc.setWeight2UserInfo(mTargetTrainInfo.targetTrainInfo_Wei.fCurrentVal);
                        Log.e("Jerry","UploadWeight Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = TargetTrainState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_UploadBodyFat()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                {
                    CloudDataFunc.setBodyFat2UserInfo(mTargetTrainInput_CurrentBodyFat_Layout.getCurrentVal());
                }

                new Thread(CloudRunnable_UploadUserInfo).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_UPLOAD_BodyFat_CHECK;
    }

    private void vProc_UploadBodyFat_Check()
    {
        if(bServerResponseFlag_UploadUserInfo)
        {
            bServerResponseFlag_UploadUserInfo = false;
            //Log.e("Jerry","vProc_UploadBodyFat_Check()");

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_UploadUserInfo == 0)
                    {
                        //Success
                        mTargetTrainInfo.targetTrainInfo_BdyFat.fCurrentVal = mTargetTrainInput_CurrentBodyFat_Layout.getCurrentVal();
                        vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_BODYFAT);
                    }
                    else
                    {
                        //Fail
                        CloudDataFunc.setBodyFat2UserInfo(mTargetTrainInfo.targetTrainInfo_BdyFat.fCurrentVal);
                        Log.e("Jerry","UploadBodyFat Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = TargetTrainState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_UploadTargetGoal()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                new Thread(CloudRunnable_UploadTargetGoal).start();
            }
        });

        iCount = 0;
        mState = TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL_CHECK;
    }

    private void vProc_UploadTargetGoal_Check()
    {
        if(bServerResponseFlag_UploadTargetGoal)
        {
            if(iServerResponseResult_UploadTargetGoal == 0)
            {
                if(bFlag_Delete)
                {
                    if(iCount > EngSetting.DEF_SEC_COUNT)
                    {
                        bServerResponseFlag_UploadTargetGoal = false;
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                mMainActivity.dismissInfoDialog();
                                mMainActivity.closeProgressBar();
                            }
                        });

                        bFlag_Delete = false;
                        //Success
                        vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
                        mState = TargetTrainState.PROC_IDLE;
                    }
                    else
                    if(iCount == 0)
                    {
                        iCount ++;
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_DELETE_FINISH,-1,-1);
                            }
                        });
                    }
                    else
                    {
                        iCount ++;
                    }
                }
                else
                {
                    bServerResponseFlag_UploadTargetGoal = false;
                    vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
                    mState = TargetTrainState.PROC_IDLE;

                    mMainActivity.mUI_Handler.post(new Runnable() {
                        @Override
                        public void run()
                        {
                            mMainActivity.closeProgressBar();
                        }
                    });
                }
            }
            else
            {
                //Fail
                Log.e("Jerry","UploadTargetGoal Fail!");

                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mMainActivity.closeProgressBar();
                    }
                });

                mState = TargetTrainState.PROC_IDLE;
            }
        }
    }

    private void vProc_LoadGoalClose()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                new Thread(CloudRunnable_GetGoalClose).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_LOAD_GOAL_CLOSE_CHECK;
    }

    private void vProc_LoadGoalClose_Check()
    {
        if(bServerResponseFlag_GetGoalClose)
        {
            bServerResponseFlag_GetGoalClose = false;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetGoalClose == 0)
                    {
                        //Success
                        vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","Load Goal Close Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = TargetTrainState.PROC_IDLE;
                }
            });
        }
    }
    /////////////////////////////////

    private void vProc_DeleteGoalClose()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                new Thread(CloudRunnable_DeleteGoalClose).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_DELETE_GOAL_CLOSE_CHECK;
    }

    private void vProc_DeleteGoalClose_Check()
    {
        if(bServerResponseFlag_DeleteGoalClose)
        {
            if(iServerResponseResult_DeleteGoalClose == 0)
            {
                if(iCount > EngSetting.DEF_SEC_COUNT)
                {
                    bServerResponseFlag_DeleteGoalClose = false;
                    mMainActivity.mUI_Handler.post(new Runnable() {
                        @Override
                        public void run()
                        {
                            mMainActivity.dismissInfoDialog();
                            mMainActivity.closeProgressBar();
                        }
                    });

                    //Success
                    vSetNextState(TargetTrainState.PROC_SHOW_PAGE_HISTORY_MAIN);
                    mState = TargetTrainState.PROC_IDLE;
                }
                else
                if(iCount == 0)
                {
                    iCount ++;
                    mMainActivity.mUI_Handler.post(new Runnable() {
                        @Override
                        public void run()
                        {
                            mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_DELETE_FINISH,-1,-1);
                        }
                    });
                }
                else
                {
                    iCount ++;
                }
            }
            else
            {
                //Fail
                Log.e("Jerry","Load Goal Close Fail!");
            }
        }
    }
    /////////////////////////////////
    /////////////////////////////////

    private void vProc_AddGoalClose()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                new Thread(CloudRunnable_AddGoalClose).start();
            }
        });

        mState = TargetTrainState.PROC_CLOUD_ADD_GOAL_CLOSE_CHECK;
    }

    private void vProc_AddGoalClose_Check()
    {
        if(bServerResponseFlag_AddGoalClose)
        {
            bServerResponseFlag_AddGoalClose = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_AddGoalClose == 0)
                    {
                        //Success
                        //vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
                        mMainActivity.dismissInfoDialog();

                        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TARGET_CLOSE_INFO,cloud_TargetGoalClose,false);

                        mMainActivity.dialogLayout_TargetCloseInfo.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //vSetNextState(TargetTrainState.PROC_CHECK_TARGET_STATUS);
                                vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
                                mMainActivity.dismissInfoDialog();
                            }
                        });

                        mMainActivity.dialogLayout_TargetCloseInfo.imageView_Share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_SHARE,mMainActivity,mMainActivity.frameLayout_Dialog);
                            }
                        });
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_AddGoalClose Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = TargetTrainState.PROC_IDLE;
                }
            });
        }
    }
    /////////////////////////////////

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    public void run() {

        if(tempState != mState)
        {
            Log.e("Jerry", "[TargetTrain] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                  :{   vProc_Init();                              break;  }

            case PROC_SHOW_PAGE_MAIN                        :{   vProc_ShowPage_Main();                     break;  }

            case PROC_CLOUD_LOAD_GOAL                       :{   vProc_LoadGoal();                          break;  }
            case PROC_CLOUD_LOAD_GOAL_CHECK                 :{   vProc_LoadGoal_Check();                    break;  }

            case PROC_REFRESH_SIMPLE_INFO                   :{   vProc_RefreshTargetTrainSimpleLayout();    break;  }

            case PROC_SHOW_PAGE_DETAIL_DISTANCE             :{   vProc_ShowPage_Detail_Distance();          break;  }
            case PROC_SHOW_PAGE_DETAIL_CALORIES             :{   vProc_ShowPage_Detail_Calories();          break;  }
            case PROC_SHOW_PAGE_DETAIL_WEIGHT               :{   vProc_ShowPage_Detail_Weight();            break;  }
            case PROC_SHOW_PAGE_DETAIL_BODYFAT              :{   vProc_ShowPage_Detail_BodyFat();           break;  }
            case PROC_SHOW_PAGE_DETAIL_FREQUENCY            :{   vProc_ShowPage_Detail_Frequency();         break;  }

            case PROC_SHOW_PAGE_MODIFY_CURRENT_WEIGHT       :{   vProc_ShowPage_ModifyCurrentWeight();      break;  }
            case PROC_SHOW_PAGE_MODIFY_CURRENT_BODYFAT      :{   vProc_ShowPage_ModifyCurrentBodyFat();     break;  }

            case PROC_CLOUD_UPLOAD_WEIGHT                   :{   vProc_UploadWeight();                      break;  }
            case PROC_CLOUD_UPLOAD_WEIGHT_CHECK             :{   vProc_UploadWeight_Check();                break;  }
            case PROC_CLOUD_UPLOAD_BodyFat                  :{   vProc_UploadBodyFat();                     break;  }
            case PROC_CLOUD_UPLOAD_BodyFat_CHECK            :{   vProc_UploadBodyFat_Check();               break;  }
            case PROC_CLOUD_UPLOAD_TARGET_GOAL              :{   vProc_UploadTargetGoal();                  break;  }
            case PROC_CLOUD_UPLOAD_TARGET_GOAL_CHECK        :{   vProc_UploadTargetGoal_Check();            break;  }

            case PROC_SHOW_PAGE_CHOOSE_TARGET               :{   vProc_ShowPage_ChooseTarget();             break;  }

            case PROC_SHOW_PAGE_ADD_SELECT_TYPE             :{   vProc_ShowPage_AddSelectType();            break;  }
            case PROC_SHOW_PAGE_ADD_DIS_SET                 :{   vProc_ShowPage_AddDistanceSetVal();        break;  }
            case PROC_SHOW_PAGE_ADD_CAL_SET_CAL             :{   vProc_ShowPage_AddCalSetVal();             break;  }
            case PROC_SHOW_PAGE_ADD_WEI_SET_CURRENT         :{   vProc_ShowPage_AddWeiSetCurrent();         break;  }
            case PROC_SHOW_PAGE_ADD_WEI_SET_TARGET          :{   vProc_ShowPage_AddWeiSetTarget();          break;  }
            case PROC_SHOW_PAGE_ADD_BDY_FAT_SET_CURRENT     :{   vProc_ShowPage_AddBodyFatSetCurrent();     break;  }
            case PROC_SHOW_PAGE_ADD_BDY_FAT_SET_TARGET      :{   vProc_ShowPage_AddBodyFatSetTarget();      break;  }
            case PROC_SHOW_PAGE_ADD_FREQ_SELECT_DAY         :{   vProc_ShowPage_AddFreqSelectDay();         break;  }
            case PROC_SHOW_DISTANCE_REFRESH                 :{   vProc_ShowPage_distance_refresh();                break;  }

            case PROC_SHOW_PAGE_SET_DAY                     :{   vProc_ShowPage_SetDay();                   break;  }
            case PROC_SHOW_PAGE_SET_WEEK                    :{   vProc_ShowPage_SetWeek();                  break;  }

            case PROC_CLOUD_LOAD_GOAL_CLOSE                 :{   vProc_LoadGoalClose();                     break;  }
            case PROC_CLOUD_LOAD_GOAL_CLOSE_CHECK           :{   vProc_LoadGoalClose_Check();               break;  }

            case PROC_SHOW_PAGE_HISTORY_MAIN                :{   vProc_ShowPage_HistoryMain();              break;  }

            case PROC_CLOUD_DELETE_GOAL_CLOSE               :{   vProc_DeleteGoalClose();                   break;  }
            case PROC_CLOUD_DELETE_GOAL_CLOSE_CHECK         :{   vProc_DeleteGoalClose_Check();             break;  }
            case PROC_CLOUD_ADD_GOAL_CLOSE                  :{   vProc_AddGoalClose();                      break;  }
            case PROC_CLOUD_ADD_GOAL_CLOSE_CHECK            :{   vProc_AddGoalClose_Check();                break;  }

            case PROC_CHECK_TARGET_STATUS                   :{   vProc_CheckTargetTrainStatus();            break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                              break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                              break;  }
            default                                         :{   vProc_Idle();                              break;  }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_GetGoal = new Runnable() {
        @Override
        public void run()
        {
            Calendar cal_Today = GlobalData.getInstance();
            cal_Today.add(Calendar.DAY_OF_YEAR,1);

            bServerResponseFlag_GetGoal = false;
            iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetTargetGoal(list_CloudTargetGoal);
            mMainActivity.mCloudCmd.iCloudCmd_GetUserInfo();

            mTargetTrainInfo.vParseCloudData(list_CloudTargetGoal);

            if(iServerResponseResult_GetGoal == 0)
            {
                if(mTargetTrainInfo.bIsDistance())
                {
                    CloudDataStruct.CloudData_16.clear();
                    iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetDstAndCalVal(mTargetTrainInfo.targetTrainInfo_Dis.sExerciseType,mTargetTrainInfo.targetTrainInfo_Dis.cal_Start,cal_Today);
                    mTargetTrainInfo.bGetCurrentDis();
                }

                if(mTargetTrainInfo.bIsCalories())
                {
                    CloudDataStruct.CloudData_16.clear();
                    iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetDstAndCalVal("A",mTargetTrainInfo.targetTrainInfo_Cal.cal_Start,cal_Today);
                    mTargetTrainInfo.bGetCurrentCal();
                }

                if(mTargetTrainInfo.bIsWeight())
                {
                    //iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetUserInfo();
                    mTargetTrainInfo.bGetCurrentWeight();
                }

                if(mTargetTrainInfo.bIsBodyFat())
                {
                    //iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetUserInfo();
                    mTargetTrainInfo.bGetCurrentBodyFat();
                }

                if(mTargetTrainInfo.bIsFreq())
                {
                    ArrayList<String> list_sDate = new ArrayList<String>();
                    iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetExerciseDate(mTargetTrainInfo.targetTrainInfo_Freq.cal_Start,cal_Today,list_sDate);
                    mTargetTrainInfo.bGetCurrentFreq(list_sDate);
                }
            }

            iServerResponseResult_GetGoalClose = mMainActivity.mCloudCmd.iCloudCmd_GetTargetGoalClose(list_CloudTargetGoalClose);

            bServerResponseFlag_GetGoal = true;
        }
    };

    public Runnable CloudRunnable_UploadUserInfo = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_UploadUserInfo = false;
            iServerResponseResult_UploadUserInfo = mMainActivity.mCloudCmd.iCloudCmd_UpdateUserInfo();
            bServerResponseFlag_UploadUserInfo = true;
        }
    };

    public Runnable CloudRunnable_UploadTargetGoal = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_UploadTargetGoal = false;
            iServerResponseResult_UploadTargetGoal = mMainActivity.mCloudCmd.iCloudCmd_UploadTargetGoal(cloud_TargetGoal);

            if(cloud_TargetGoal.isWeight())
            {
                CloudDataFunc.setWeight2UserInfo(cloud_TargetGoal.sUsr_Wei);
                iServerResponseResult_UploadUserInfo = mMainActivity.mCloudCmd.iCloudCmd_UpdateUserInfo();
            }
            else if(cloud_TargetGoal.isBodyFat())
            {
                CloudDataFunc.setBodyFat2UserInfo(cloud_TargetGoal.sUsr_Wei);
                iServerResponseResult_UploadUserInfo = mMainActivity.mCloudCmd.iCloudCmd_UpdateUserInfo();
            }

            bServerResponseFlag_UploadTargetGoal = true;
        }
    };

    public Runnable CloudRunnable_GetGoalClose = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetGoalClose = false;
            iServerResponseResult_GetGoalClose = mMainActivity.mCloudCmd.iCloudCmd_GetTargetGoalClose(list_CloudTargetGoalClose);
            bServerResponseFlag_GetGoalClose = true;
        }
    };

    public Runnable CloudRunnable_DeleteGoalClose = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_DeleteGoalClose = false;
            if(sGoalSeq != null)
            {
                iServerResponseResult_DeleteGoalClose = mMainActivity.mCloudCmd.iCloudCmd_GoalClose_Delete(sGoalSeq);
                iServerResponseResult_GetGoalClose = mMainActivity.mCloudCmd.iCloudCmd_GetTargetGoalClose(list_CloudTargetGoalClose);
            }
            bServerResponseFlag_DeleteGoalClose = true;
        }
    };

    public Runnable CloudRunnable_AddGoalClose = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_AddGoalClose = false;

            iServerResponseResult_AddGoalClose = mMainActivity.mCloudCmd.iCloudCmd_GoalClose_Add(cloud_TargetGoalClose);
            if(iServerResponseResult_AddGoalClose == 0)
            {
                iServerResponseResult_UploadTargetGoal = mMainActivity.mCloudCmd.iCloudCmd_UploadTargetGoal(cloud_TargetGoal);
            }

            bServerResponseFlag_AddGoalClose = true;
        }
    };
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Calendar cGetValidStartDate()
    {
        return cStartValidDate;
    }

    public void setDeleteFlag(boolean bFlag)
    {
        bFlag_Delete = bFlag;
        iCount = 0;
    }
}
