package com.rtx.treadmill.RtxMainFunction.Home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataFunc;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxView.RtxFrameLayout;
import com.utils.MyLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by chasechang on 3/27/17.
 */

public class HomeProc {

    final private Integer INFO_DIALOG_MSG_KEY_WELCOME = 0;
    final private Integer INFO_DIALOG_MSG_KEY_SCH_CHANGE = 1;
    final private Integer INFO_DIALOG_MSG_KEY_CLASS_REMINDER = 2;
    final private Integer INFO_DIALOG_MSG_KEY_TARGET_UPDATE = 3;


    private String TAG = "HomeProc" ;

    private MainActivity mMainActivity ;
    private Context mContext;
    private HomeState mState      = HomeState.PROC_INIT ;
    private HomeState mNextState  = HomeState.PROC_NULL ;
    private HomeState tempState   = HomeState.PROC_NULL ;

    private int iCount = 0;
    private int iCount_Effect = 0;
    HomeLayout mHomeLayout;
    Exercise_HomeLayout mExercise_HomeLayout;

    UiDataStruct.HOME_USER_INFO userInfo;

    private boolean bServerResponseFlag_UploadUserInfo = false;
    private boolean bServerResponseFlag_GetMyClassInfo = false;
    private boolean bServerResponseFlag_GetGoal = false;

    private int     iServerResponseResult_UploadUserInfo = -1;
    private int     iServerResponseResult_GetMyClassInfo = -1;
    private int     iServerResponseResult_GetGoal = -1;

    private Calendar cal_StartDate;
    private Calendar cal_EndDate;

    private ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL>        list_CloudTargetGoal = CloudDataStruct.CloudData_24.list_CloudTargetGoal;
    private UiDataStruct.TargetTrainInfo                        mTargetTrainInfo;
    private boolean bAlreadyLoadInfo = false;
    public RtxFrameLayout frameLayout_Dialog;


    ////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////

    public HomeProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState           = HomeState.PROC_INIT ;
        mNextState       = HomeState.PROC_NULL ;

        mContext = mMainActivity.mContext;

        if(mHomeLayout == null)             { mHomeLayout = new HomeLayout(mMainActivity.mContext,mMainActivity); }
        if(mExercise_HomeLayout == null)    { mExercise_HomeLayout = new Exercise_HomeLayout(mMainActivity.mContext,mMainActivity); }

        if(userInfo == null)                { userInfo = new UiDataStruct.HOME_USER_INFO(mMainActivity.mContext); }
        if(mTargetTrainInfo == null)        { mTargetTrainInfo = new UiDataStruct.TargetTrainInfo(); }
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(HomeState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        if(ExerciseData.b_is_exercising())
        {
            mState = HomeState.PROC_SHOW_PAGE_EXERCISE_HOME ;
        }
        else
        {
            mState = HomeState.PROC_SHOW_PAGE_HOME ;
        }

    }

    private void vProc_ShowPage_Home() {

        vChangeDisplayPage(mHomeLayout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHomeLayout.vAddLoginView(bCheckLogin());

                //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TUTORIAL,null,null);//Jerry Test

                if(bCheckLogin())
                {
                    if(bAlreadyLoadInfo == false)
                    {
                        vSetNextState(HomeState.PROC_CLOUD_GET_MY_CLASS_INFO_LIST);
                        userInfo.vSetAlreadyReaded(false);
                    }
                    else
                    {
                        vSetNextState(HomeState.PROC_UPDATE_USER_INFO);
//                        userInfo.vSetAlreadyReaded(true);
                    }
                }
                else
                {
                    //非手動登出時要清空
                    userInfo.vSetFirstSignUp(false);
                }
            }
        });

        mState = HomeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_ExerciseHome() {

        vChangeDisplayPage(mExercise_HomeLayout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mExercise_HomeLayout.vAddLoginView(bCheckLogin());
            }
        });

        mState = HomeState.PROC_IDLE ;
    }

    private void vProc_Logout() {
       //CloudDataStruct.CloudData_20.set_log_in(false);
       // bAlreadyLoadInfo = false;


                MyLog.d("vProc_Logout");
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {

                MyLog.d("vProc_Logout run");
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGOUT,-1,-1);
                mMainActivity.dialogLayout_Logout.fillerTextView_Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CloudDataStruct.CloudData_20.set_log_in(false);
                        bAlreadyLoadInfo = false;
                        mHomeLayout.vAddLoginView(bCheckLogin());
                        userInfo.vSetFirstSignUp(false);
                        mMainActivity.dialogLayout_Logout.imageView_Close.performClick();
                    }
                });
                mMainActivity.dialogLayout_Logout.fillerTextView_SignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMainActivity.dialogLayout_Logout.imageView_Close.performClick();
                    }
                });
                //mHomeLayout.vAddLoginView(alertDialog());
            }
        });

        //登出時要清空
        //userInfo.vSetFirstSignUp(false);
        mState = HomeState.PROC_IDLE;


    }

    private boolean alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
        dialog.setMessage("Do you want to log out?");
        dialog.setTitle("LOG OUT");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                mHomeLayout.vAddLoginView(bCheckLogin());
                            }
                        });


                    }
                });
        dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        return true;
    }




    private void vProc_ShowPage_Home_Update() {

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

                if(bAlreadyLoadInfo == false)
                {
                    userInfo.bExportSummaryInfo(mTargetTrainInfo);
                    mHomeLayout.vUpdateMailState(userInfo);

                    if(userInfo.bGetNeedToUploadFirstLogin())
                    {
                        vSetNextState(HomeState.PROC_CLOUD_UPLOAD_FIRST_LOGIN);
                    }

                    bAlreadyLoadInfo = true;
                }
                else
                {
                    userInfo.bExportSummaryInfo(mTargetTrainInfo);
                    mHomeLayout.vUpdateMailState(userInfo);
                }
            }
        });

        mState = HomeState.PROC_IDLE ;
    }




    private void vProc_GetWeather()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHomeLayout.Weather_Refresh();
            }
        });
    }

    private void vProc_Idle()
    {
        vProc_GetWeather();

        if(mNextState != HomeState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = HomeState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = HomeState.PROC_INIT ;
        mNextState = HomeState.PROC_NULL;
    }

    private void vProc_EmailEffect()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHomeLayout.effectMail();
            }
        });
    }

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

    public void vChangeDisplayPage(final Rtx_BaseLayoutE layout)
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
    /* ------------------------------------------------------------------------ */
    public void run() {

        {
            iCount ++;
            if(iCount > EngSetting.DEF_SEC_COUNT)
            {
                iCount = 0;
                if(mHomeLayout != null)
                {
                    mMainActivity.mUI_Handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHomeLayout.updateDateAndTime();
                        }
                    });

                }
            }
        }

        if (tempState != mState)
        {
            Log.e("Jerry", "[HomeProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                  :   {   vProc_Init();                       break;  }
            case PROC_SHOW_PAGE_HOME                        :   {   vProc_ShowPage_Home();              break;  }
            case PROC_SHOW_PAGE_EXERCISE_HOME               :   {   vProc_ShowPage_ExerciseHome();      break;  }
            case PROC_LOGOUT                                :   {   vProc_Logout();                     break;  }
            case PROC_UPDATE_USER_INFO                      :   {   vProc_ShowPage_Home_Update();       break;  }

            case PROC_CLOUD_GET_MY_CLASS_INFO_LIST          :   {   vProc_GetMyClassInfoList();         break;  }
            case PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK    :   {   vProc_GetMyClassInfoList_Check();   break;  }
            case PROC_CLOUD_LOAD_GOAL                       :   {   vProc_LoadGoal();                   break;  }
            case PROC_CLOUD_LOAD_GOAL_CHECK                 :   {   vProc_LoadGoal_Check();             break;  }
            case PROC_CLOUD_UPLOAD_FIRST_LOGIN              :   {   vProc_UploadFirstLogin();           break;  }
            case PROC_CLOUD_UPLOAD_FIRST_LOGIN_CHECK        :   {   vProc_UploadFirstLogin_Check();     break;  }

            case PROC_IDLE                                  :   {   vProc_Idle();                       break;  }
            case PROC_EXIT                                  :   {   vProc_Exit();                       break;  }
            default                                         :   {                                       break;  }
        }

        if(bCheckLogin() && (!userInfo.bGetAlreadyReaded()))
        {
            iCount_Effect++;
            if(iCount_Effect > (EngSetting.DEF_100ms_COUNT / 2))
            {
                iCount_Effect = 0;
                vProc_EmailEffect();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean bCheckLogin()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();

        return bLogin;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vProc_UploadFirstLogin()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();

                CloudDataStruct.CloudData_45.Set_input();

                new Thread(CloudRunnable_UploadUserInfo).start();
            }
        });

        mState = HomeState.PROC_CLOUD_UPLOAD_FIRST_LOGIN_CHECK;
    }

    private void vProc_UploadFirstLogin_Check()
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
                        Log.e("Jerry","vProc_UploadFirstLogin_Check Success!");
                        userInfo.vSetNeedToUploadFirstLogin(false);
                        //vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_WEIGHT);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_UploadFirstLogin_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = HomeState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_GetMyClassInfoList()
    {
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();

                    cal_StartDate = GlobalData.getInstance();
                    cal_EndDate = GlobalData.getInstance();
                    cal_EndDate.add(Calendar.DATE,1);

                    new Thread(CloudRunnable_GetMyClassInfoList).start();
                }
            });
        }

        mState = HomeState.PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK;
    }

    private void vProc_GetMyClassInfoList_Check()
    {
        if(bServerResponseFlag_GetMyClassInfo)
        {
            bServerResponseFlag_GetMyClassInfo = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetMyClassInfo == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_GetMyClassInfoList_Check Success!");
                        userInfo.vImportTodayClass(CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
                        userInfo.vImportTomorrowClass(CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
                        userInfo.vImportClassChange(CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
                        //userInfo.bExportSummaryInfo();

                        vSetNextState(HomeState.PROC_CLOUD_LOAD_GOAL);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_GetMyClassInfoList_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = HomeState.PROC_IDLE ;
                }
            });
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
            }
        });

        mState = HomeState.PROC_CLOUD_LOAD_GOAL_CHECK;
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
                        Log.e("Jerry","vProc_LoadGoal_Check Success!");
                        vSetNextState(HomeState.PROC_UPDATE_USER_INFO);
                    }
                    else
                    {
                        //Fail
                        //vSetNextState(TargetTrainState.PROC_REFRESH_SIMPLE_INFO);
                        Log.e("Jerry","Load Goal Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = HomeState.PROC_IDLE;
                }
            });
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Runnable CloudRunnable_UploadUserInfo = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_UploadUserInfo = false;
            iServerResponseResult_UploadUserInfo = mMainActivity.mCloudCmd.iCloudCmd_UpdateUserInfo();
            bServerResponseFlag_UploadUserInfo = true;
        }
    };

    public Runnable CloudRunnable_GetMyClassInfoList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetMyClassInfo = false;
            CloudDataStruct.CloudData_39.list_CloudMyClassInfo.clear();
            iServerResponseResult_GetMyClassInfo = mMainActivity.mCloudCmd.iCloudCmd_GetGymMyClassList(cal_StartDate,cal_EndDate);
            bServerResponseFlag_GetMyClassInfo = true;
        }
    };

    public Runnable CloudRunnable_GetGoal = new Runnable() {
        @Override
        public void run()
        {
            Calendar cal_Today = GlobalData.getInstance();
            cal_Today.add(Calendar.DAY_OF_YEAR,1);

            bServerResponseFlag_GetGoal = false;
            iServerResponseResult_GetGoal = mMainActivity.mCloudCmd.iCloudCmd_GetTargetGoal(list_CloudTargetGoal);
            //mMainActivity.mCloudCmd.iCloudCmd_GetUserInfo();

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

            bServerResponseFlag_GetGoal = true;
        }
    };
}
