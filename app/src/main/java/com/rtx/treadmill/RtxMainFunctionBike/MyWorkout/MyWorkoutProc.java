package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

import android.util.Log;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/27/17.
 */

public class MyWorkoutProc {
    private String TAG = "MyWorkout" ;

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private int iMode = MODE_NONE;

    private MainActivity mMainActivity ;

    private MyWorkoutState mState = MyWorkoutState.PROC_INIT ;
    private MyWorkoutState mNextState = MyWorkoutState.PROC_NULL ;
    private MyWorkoutState tempState = MyWorkoutState.PROC_NULL ;
    private MyWorkoutState mPreState = MyWorkoutState.PROC_NULL ;

    private MyWorkoutLayout             mMyWorkoutLayout;
    private MyWorkout_Name_Layout       mMyWorkout_Name_Layout;
    private MyWorkout_DataList_Layout   mMyWorkout_DataList_Layout;
    private MyWorkout_Setting_Layout    mMyWorkout_Setting_Layout;
    private MyWorkout_Preview_Layout    mMyWorkout_Preview_Layout;
    private MyWorkout_Details_Layout    mMyWorkout_Details_Layout;

    private MyWorkout_Exercise_Layout   mMyWorkout_Exercise_Layout;
    private AllFinishLayout             mAllFinishLayout;

    private int         iCount = 0;

    private boolean bServerResponseFlag_GetNameList = false;
    private boolean bServerResponseFlag_GetInterval = false;
    private boolean bServerResponseFlag_AddInterval = false;
    private boolean bServerResponseFlag_DeleteInterval = false;
    private boolean bServerResponseFlag_UpdateInterval = false;

    private int     iServerResponseResult_GetNameList = -1;
    private int     iServerResponseResult_GetInterval = -1;
    private int     iServerResponseResult_AddInterval = -1;
    private int     iServerResponseResult_DeleteInterval = -1;
    private int     iServerResponseResult_UpdateInterval = -1;

    public ArrayList<UiDataStruct.WORKOUT_ITEM_INFO> list_WorkoutItem;
    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    private String sMch_Type;
    private String sWorkout_ID;

    private int    iSettingIndex = -1;
    private int    iSelectItem = -1;

    private final int MODE_DELETE_ITEM = 0;
    private final int MODE_DELETE_INTERVAL = 1;


    private int    iDeleteMode = -1;

    public MyWorkoutProc(MainActivity mMainActivity)
    {
        this.mMainActivity = mMainActivity ;
        mState = MyWorkoutState.PROC_INIT ;

        //Layout
        if(mMyWorkoutLayout == null)                          { mMyWorkoutLayout = new MyWorkoutLayout(mMainActivity.mContext , mMainActivity); }
        if(mMyWorkout_Name_Layout == null)                    { mMyWorkout_Name_Layout = new MyWorkout_Name_Layout(mMainActivity.mContext , mMainActivity); }
        if(mMyWorkout_DataList_Layout == null)                { mMyWorkout_DataList_Layout = new MyWorkout_DataList_Layout(mMainActivity.mContext , mMainActivity); }
        if(mMyWorkout_Setting_Layout == null)                 { mMyWorkout_Setting_Layout = new MyWorkout_Setting_Layout(mMainActivity.mContext , mMainActivity); }
        if(mMyWorkout_Preview_Layout == null)                 { mMyWorkout_Preview_Layout = new MyWorkout_Preview_Layout(mMainActivity.mContext , mMainActivity); }
        if(mMyWorkout_Details_Layout == null)                 { mMyWorkout_Details_Layout = new MyWorkout_Details_Layout(mMainActivity.mContext , mMainActivity); }

        if(mMyWorkout_Exercise_Layout == null)                { mMyWorkout_Exercise_Layout = new MyWorkout_Exercise_Layout(mMainActivity.mContext , mMainActivity); }
        if(mAllFinishLayout == null)                          { mAllFinishLayout = new AllFinishLayout(mMainActivity.mContext , mMainActivity); }

        if(list_WorkoutItem == null)                          { list_WorkoutItem = new ArrayList<UiDataStruct.WORKOUT_ITEM_INFO>(); }
        if(workoutItem == null)                               { workoutItem = new UiDataStruct.WORKOUT_ITEM_INFO(); }
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(MyWorkoutState nextState)
    {
        mNextState = nextState;
    }

    public void vSetPreState(MyWorkoutState preState)
    {
        mPreState = preState;
    }

    public void vBackPreState()
    {
        mNextState = mPreState;
        mPreState = MyWorkoutState.PROC_NULL;
    }

    public MyWorkoutState vGerPreState()
    {
        return mPreState;
    }

    /* ------------------------------------------------------------------------ */

    private void vProc_Init()
    {
        if(mNextState == MyWorkoutState.PROC_NULL)
        {
            if(ExerciseData.b_is_exercising())
            {
                mState = MyWorkoutState.PROC_EXERCISE_SHOW;
            }
            else
            {
                mState = MyWorkoutState.PROC_SHOW_PAGE_MAIN;
            }
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_Idle()
    {
        if(mNextState != MyWorkoutState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = MyWorkoutState.PROC_NULL;
        }

        iCount = 0;
    }

    private void vProc_Exit()
    {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = MyWorkoutState.PROC_INIT ;
        mNextState = MyWorkoutState.PROC_NULL;
    }

    private void vProc_ShowPage_Main()
    {
        vChangeDisplayPage(mMyWorkoutLayout);

        if(CloudDataStruct.CloudData_20.is_log_in())
        {
            vSetNextState(MyWorkoutState.PROC_CLOUD_GET_NAME_LIST);

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {

                    mMyWorkoutLayout.vSetWorkoutItemInfo(workoutItem);
                }
            });
        }
        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Name()
    {
        vChangeDisplayPage(mMyWorkout_Name_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

                mMyWorkout_Name_Layout.vSetWorkoutItemInfo(workoutItem,iGetMode());
            }
        });

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataList()
    {

        vChangeDisplayPage(mMyWorkout_DataList_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                //dismiss Delete Finish dialog
                if(mMainActivity.dialogLayout_DeleteFinish != null)
                {
                    if (mMainActivity.dialogLayout_DeleteFinish.isShown())
                    {
                        mMainActivity.dismissInfoDialog();
                    }
                }
                mMyWorkout_DataList_Layout.vSetWorkoutItemInfo(workoutItem,iGetMode());
            }
        });

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Setting()
    {
        vChangeDisplayPage(mMyWorkout_Setting_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMyWorkout_Setting_Layout.vSetWorkoutItemInfo(workoutItem,iSettingIndex);
            }
        });

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Preview()
    {
        vChangeDisplayPage(mMyWorkout_Preview_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                if(iGetMode() == MODE_PREVIEW)
                {
                    vSetSelectItemInfo();
                }

                mMyWorkout_Preview_Layout.vSetWorkoutItemInfo(workoutItem,iGetMode());
            }
        });

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Details()
    {
        vChangeDisplayPage(mMyWorkout_Details_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMyWorkout_Details_Layout.vSetWorkoutItemInfo(workoutItem);
            }
        });

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_Exercise_Prepare()
    {
        vCreateProgram();
        vSetNextState(MyWorkoutState.PROC_EXERCISE_CHECK_COUNTDOWN);

        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_Check_Is_Countdown() {

        boolean bret = ExerciseData.bGet_Is_Coundown();

        if(bret)
        {
            vSetNextState(MyWorkoutState.PROC_EXERCISE_SHOW);
        }

        vProc_Idle() ;
    }

    private void vProc_ShowPage_Exercise()
    {
        vChangeDisplayPage(mMyWorkout_Exercise_Layout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        vSetNextState(MyWorkoutState.PROC_EXERCISE_INIT);
        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_ShowDialog_Delete()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(iCount == 0)
                {
                    mMainActivity.dismissInfoDialog();
                    mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_DELETE_FINISH,-1,-1);
                    iCount ++;
                }
                else if(iCount > EngSetting.DEF_SEC_COUNT)
                {
//                    mMainActivity.dismissInfoDialog();
                    iCount = 0;
                    mState = MyWorkoutState.PROC_IDLE;

                    if(iDeleteMode == MODE_DELETE_ITEM)
                    {
                        vSetNextState(MyWorkoutState.PROC_CLOUD_DELETE_INTERVAL);
                    }
                    else
                    {
                        vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
                    }
                }
                else
                {
                    iCount ++;
                }
            }
        });
    }

    private void vProc_Exercise_Init()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {

            }
        });

        vSetNextState(MyWorkoutState.PROC_EXERCISE_REFRESH);
        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_Exercise_Refresh()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mMyWorkout_Exercise_Layout != null)
                {
                    mMyWorkout_Exercise_Layout.Refresh();
                }
            }
        });

        vProc_Idle();
    }

    private void vProc_Exercise_Summary()
    {
        vChangeDisplayPage(mAllFinishLayout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mAllFinishLayout != null)
                {
                    mAllFinishLayout.vSetWorkName(workoutItem.sName);
                }
            }
        });
        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_Exercise_Done()
    {
        vMainChangePage(MainState.PROC_HOME);
        mState = MyWorkoutState.PROC_IDLE ;
    }

    private void vProc_Exercise_Logout()
    {
        CloudDataStruct.CloudData_20.set_log_in(false);

        vMainChangePage(MainState.PROC_HOME);
        mState = MyWorkoutState.PROC_IDLE ;
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vProc_GetNameList()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetNameList).start();
            }
        });

        mState = MyWorkoutState.PROC_CLOUD_GET_NAME_LIST_CHECK;
    }

    private void vProc_GetNameList_Check()
    {
        if(bServerResponseFlag_GetNameList)
        {
            bServerResponseFlag_GetNameList = false;
            mState = MyWorkoutState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetNameList == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_GetNameList_Check Success!");
                        vParseWorkoutName();
                        mMyWorkoutLayout.vUpdateNameInfo(list_WorkoutItem);
                        vSetNextState(MyWorkoutState.PROC_CLOUD_GET_INTERVAL);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_GetNameList_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_GetInterval()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetInterval).start();
                //dismiss Delete Finish dialog
                if(mMainActivity.dialogLayout_DeleteFinish != null)
                {
                    if (mMainActivity.dialogLayout_DeleteFinish.isShown())
                    {
                        mMainActivity.dismissInfoDialog();
                    }
                }
            }
        });

        mState = MyWorkoutState.PROC_CLOUD_GET_INTERVAL_CHECK;
    }

    private void vProc_GetInterval_Check()
    {
        if(bServerResponseFlag_GetInterval)
        {
            bServerResponseFlag_GetInterval = false;
            mState = MyWorkoutState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetInterval == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_GetInterval_Check Success!");
                        mMyWorkoutLayout.vUpdatePreviewInfo(list_WorkoutItem);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_GetInterval_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_AddInterval()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Add).start();
            }
        });

        mState = MyWorkoutState.PROC_CLOUD_ADD_INTERVAL_CHECK;
    }

    private void vProc_AddInterval_Check()
    {
        if(bServerResponseFlag_AddInterval)
        {
            bServerResponseFlag_AddInterval = false;
            mState = MyWorkoutState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_AddInterval == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_AddInterval_Check Success!");
                        vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_MAIN);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_AddInterval_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_UpdateInterval()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Update).start();
            }
        });

        mState = MyWorkoutState.PROC_CLOUD_UPDATE_INTERVAL_CHECK;
    }

    private void vProc_UpdateInterval_Check()
    {
        if(bServerResponseFlag_UpdateInterval)
        {
            bServerResponseFlag_UpdateInterval = false;
            mState = MyWorkoutState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_UpdateInterval == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_UpdateInterval_Check Success!");
                        vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_MAIN);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_UpdateInterval_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_DeleteInterval()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Delete).start();
            }
        });

        mState = MyWorkoutState.PROC_CLOUD_DELETE_INTERVAL_CHECK;
    }

    private void vProc_DeleteInterval_Check()
    {
        if(bServerResponseFlag_DeleteInterval)
        {
            bServerResponseFlag_DeleteInterval = false;
            mState = MyWorkoutState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_DeleteInterval == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_DeleteInterval_Check Success!");
                        vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_MAIN);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_DeleteInterval_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    public void run() {

        if(tempState != mState)
        {
            Log.e("Jerry", "[MyWorkout] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                  :{ vProc_Init();                              break;  }

            case PROC_SHOW_PAGE_MAIN                        :{ vProc_ShowPage_Main();                     break;  }
            case PROC_SHOW_PAGE_NAME                        :{ vProc_ShowPage_Name();                     break;  }
            case PROC_SHOW_PAGE_DATALIST                    :{ vProc_ShowPage_DataList();                 break;  }
            case PROC_SHOW_PAGE_SETTING                     :{ vProc_ShowPage_Setting();                  break;  }
            case PROC_SHOW_PAGE_PREVIEW                     :{ vProc_ShowPage_Preview();                  break;  }
            case PROC_SHOW_PAGE_DETAILS                     :{ vProc_ShowPage_Details();                  break;  }

            case PROC_SHOW_DIALOG_DELETE                    :{ vProc_ShowDialog_Delete();                 break;  }

            case PROC_CLOUD_GET_NAME_LIST                   :{ vProc_GetNameList();                       break;  }
            case PROC_CLOUD_GET_NAME_LIST_CHECK             :{ vProc_GetNameList_Check();                 break;  }
            case PROC_CLOUD_GET_INTERVAL                    :{ vProc_GetInterval();                       break;  }
            case PROC_CLOUD_GET_INTERVAL_CHECK              :{ vProc_GetInterval_Check();                 break;  }

            case PROC_CLOUD_ADD_INTERVAL                    :{ vProc_AddInterval();                       break; }
            case PROC_CLOUD_ADD_INTERVAL_CHECK              :{ vProc_AddInterval_Check();                 break; }
            case PROC_CLOUD_DELETE_INTERVAL                 :{ vProc_DeleteInterval();                    break; }
            case PROC_CLOUD_DELETE_INTERVAL_CHECK           :{ vProc_DeleteInterval_Check();              break; }
            case PROC_CLOUD_UPDATE_INTERVAL                 :{ vProc_UpdateInterval();                    break; }
            case PROC_CLOUD_UPDATE_INTERVAL_CHECK           :{ vProc_UpdateInterval_Check();              break; }

            case PROC_EXERCISE_PREPARE                      :{ vProc_Exercise_Prepare();                  break; }
            case PROC_EXERCISE_SHOW                         :{ vProc_ShowPage_Exercise();                 break; }
            case PROC_EXERCISE_INIT                         :{ vProc_Exercise_Init();                     break; }
            case PROC_EXERCISE_REFRESH                      :{ vProc_Exercise_Refresh();                  break; }
            case PROC_EXERCISE_FINISH                       :{ vProc_Exercise_Summary();                  break; }
            case PROC_EXERCISE_SHOW_DONE                    :{ vProc_Exercise_Done();                     break; }
            case PROC_EXERCISE_SHOW_LOGOUT                  :{ vProc_Exercise_Logout();                   break; }
            case PROC_EXERCISE_CHECK_COUNTDOWN              :{   vProc_Check_Is_Countdown();              break;  }

            case PROC_IDLE                                  :{ vProc_Idle();                              break;  }
            case PROC_EXIT                                  :{ vProc_Exit();                              break;  }
            default                                         :{ vProc_Idle();                              break;  }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Runnable CloudRunnable_GetNameList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetNameList = false;
            iServerResponseResult_GetNameList = mMainActivity.mCloudCmd.iCloudCmd_GetWorkoutName();
            bServerResponseFlag_GetNameList = true;
        }
    };

    private Runnable CloudRunnable_GetInterval = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetInterval = false;

            if(list_WorkoutItem != null)
            {
                int iIndex = 0;
                int iSize = list_WorkoutItem.size();

                for( ; iIndex < iSize ; iIndex++)
                {
                    vSetWorkoutID(list_WorkoutItem.get(iIndex).sID);
                    iServerResponseResult_GetInterval = mMainActivity.mCloudCmd.iCloudCmd_GetWorkoutData(sGetMchType(),sWorkout_ID);

                    if(iServerResponseResult_GetInterval == 0)
                    {
                        list_WorkoutItem.get(iIndex).vImportCloudStageData();
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            bServerResponseFlag_GetInterval = true;
        }
    };

    private Runnable CloudRunnable_Interval_Add = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_AddInterval = false;

            {
                String sMchType = sGetMchType();
                String sWorkoutID = sGetFreeWorkoutID();
                String sWorkoutName = workoutItem.sName;

                workoutItem.vExportCloudData(1);

                if(sMchType != null && sWorkoutID != null && sWorkoutName != null && workoutItem.list_UploadStageData != null)
                {
                    iServerResponseResult_AddInterval = mMainActivity.mCloudCmd.iCloudCmd_Workout_Add(sMchType,sWorkoutID,sWorkoutName,workoutItem.list_UploadStageData);
                }
                else
                {
                    iServerResponseResult_AddInterval = 0;
                }
            }

            bServerResponseFlag_AddInterval = true;
        }
    };

    private Runnable CloudRunnable_Interval_Update = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_UpdateInterval = false;

            {
                String sMchType = workoutItem.sType;
                String sWorkoutID = workoutItem.sID;
                String sWorkoutName = workoutItem.sName;

                workoutItem.vExportCloudData(1);

                if(sMchType != null && sWorkoutID != null && sWorkoutName != null && workoutItem.list_UploadStageData != null)
                {
                    iServerResponseResult_UpdateInterval = mMainActivity.mCloudCmd.iCloudCmd_Workout_Update(sMchType,sWorkoutID,sWorkoutName,workoutItem.list_UploadStageData);
                }
                else
                {
                    iServerResponseResult_UpdateInterval = 0;
                }
            }

            bServerResponseFlag_UpdateInterval = true;
        }
    };

    private Runnable CloudRunnable_Interval_Delete = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_DeleteInterval = false;

            {
                String sMchType = workoutItem.sType;
                String sWorkoutID = workoutItem.sID;
                String sWorkoutName = workoutItem.sName;

                if(sMchType != null && sWorkoutID != null && sWorkoutName != null)
                {
                    iServerResponseResult_DeleteInterval = mMainActivity.mCloudCmd.iCloudCmd_Workout_DeleteItem(sMchType,sWorkoutID,sWorkoutName);
                }
                else
                {
                    iServerResponseResult_DeleteInterval = 0;
                }
            }

            bServerResponseFlag_DeleteInterval = true;
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vSetMode(int mode)
    {
        iMode = mode;
    }

    public int iGetMode()
    {
        return iMode;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String sGetFreeWorkoutID()
    {
        if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null)
        {
            return null;
        }

        int iFreeWorkoutID = 0;
        int iFreeWorkoutID_MAX = 99;
        boolean bSame = false;

        int iItemSize = CloudDataStruct.CloudData_10.list_CloudWorkoutName.size();

        for( ; iFreeWorkoutID <= iFreeWorkoutID_MAX ; iFreeWorkoutID++)
        {
            int iIndex = 0;

            bSame = false;

            for( ; iIndex < iItemSize ; iIndex++)
            {
                if(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_ID.equals(Rtx_TranslateValue.sInt2String(iFreeWorkoutID)))
                {
                    bSame = true;
                    break;
                }
            }

            if(bSame == false)
            {
                return Rtx_TranslateValue.sInt2String(iFreeWorkoutID);
            }
        }

        return null;
    }

    private void vParseWorkoutName()
    {
        if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null)
        {
            return;
        }

        int iIndex = 0;
        int iSize = CloudDataStruct.CloudData_10.list_CloudWorkoutName.size();

        list_WorkoutItem.clear();

        for( ; iIndex < iSize ; iIndex++)
        {
            if(((CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_NAM.length() > 3)) && (CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_NAM.substring(0,3).equals("#H#")))
            {
                Log.e("Jerry","Skip Hiit Item");
                continue;
            }
            else
            if(!CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sMCH_TYP.equals(sGetMchType()))
            {
                Log.e("Jerry","Skip Other Machine Type");
                continue;
            }
            else
            {
                UiDataStruct.WORKOUT_ITEM_INFO itemInfo = new UiDataStruct.WORKOUT_ITEM_INFO();
                itemInfo.vImportCloudNameData(iIndex);
                list_WorkoutItem.add(itemInfo);
            }
        }
    }

    protected void vSetSelectItemIndex(int iIndex)
    {
        iSelectItem = iIndex;
    }

    protected void vSetSelectItemInfo()
    {
        workoutItem.clear();

        Log.e("Jerry","iSelectItem = " + iSelectItem);

        if(iSelectItem != -1)
        {
            list_WorkoutItem.get(iSelectItem).copyTo(workoutItem);
        }
    }

    protected void vSetSettingIndex(int iIndex)
    {
        iSettingIndex = iIndex;
    }

    protected void vSetWorkoutID(String sID)
    {
        sWorkout_ID = new String(sID);
    }

    protected void vSetDeleteMode(int iMode)
    {
        iDeleteMode = iMode;
    }

    private String sGetMchType()
    {
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 ||EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            sMch_Type = "T";
        }
        else
        if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
        {
            sMch_Type = "U";
        }
        else
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
        {
            sMch_Type = "R";
        }
        else
        if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
        {
            sMch_Type = "E";
        }
        else
        if(EngSetting.i_Get_ExerciseType() == EngSetting.ALL)
        {
            sMch_Type = "T";
        }
        else
        {
            sMch_Type = "T";
        }

        return sMch_Type;
    }

    private void vCreateProgram()
    {
        int iStepSec = ExerciseData.iStep_time;
        float fWeight;

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        fWeight = CloudDataStruct.CloudData_17.f_get_user_weight();
        ExerciseData.v_set_weight(fWeight);

        if(workoutItem == null)
        {
            return;
        }

        if(workoutItem.list_Stage == null)
        {
            return;
        }

        ArrayList<Float> list_speed = new ArrayList<Float>();
        workoutItem.bExportStageInfoToMinList(list_speed, workoutItem.TRANSLATE_SPEDD);

        ArrayList<Float> list_incline = new ArrayList<Float>();
        workoutItem.bExportStageInfoToMinList(list_incline, workoutItem.TRANSLATE_INCLINE);

        int iIndex = 0;
        int iSize = list_speed.size();

        for(iIndex = 0 ; iIndex < iSize ; iIndex++)
        {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = iStepSec;
            suart_cmd_orig.fspeed = list_speed.get(iIndex);
            suart_cmd_orig.fincline = list_incline.get(iIndex);

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }

        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(1000, Integer.MAX_VALUE);
        ExerciseGenfunc.v_set_target_distance(Integer.MAX_VALUE);

        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_WORKOUT);
        mMainActivity.mMainProcBike.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}
