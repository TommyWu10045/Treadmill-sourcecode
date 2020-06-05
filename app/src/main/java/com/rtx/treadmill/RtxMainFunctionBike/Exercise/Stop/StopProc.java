package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Stop;

import android.content.Context;
import android.widget.ImageView;

import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.MyWorkout.MyWorkoutState;

import static com.rtx.treadmill.RtxMainFunctionBike.HeartRate.HeartRateState.PROC_EXERCISE_HR_FINISH;
import static com.rtx.treadmill.RtxMainFunctionBike.Physical.PhysicalState.PROC_SHOW_GERKIN_FINISH;
import static com.rtx.treadmill.RtxMainFunctionBike.QuickStart.QuickStartState.PROC_EXERCISE_QS_FINISH;
import static com.rtx.treadmill.RtxMainFunctionBike.Training.TrainingState.PROC_EXERCISE_TR_FINISH;

/**
 * Created by chasechang on 3/27/17.
 */

public class StopProc {
    private String TAG = "Jerry=" ;

    private Context mContext;
    private MainActivity mMainActivity ;

    private StopState mState = StopState.PROC_INIT ;
    private StopState mNextState = StopState.PROC_NULL ;
    private StopState tempState = StopState.PROC_NULL ;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;

    public StopProc(MainActivity mMainActivity) {
        this.mContext = mMainActivity.mContext;
        this.mMainActivity = mMainActivity ;
        mState = StopState.PROC_INIT ;

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */
    private void v_check_finish_UI()
    {
        ExerciseData.v_set_exercising(false);

        //不管有無登入都要做上傳機器使用資料，失敗不用pop失敗畫面
        CloudCmd_Info.vUpdate_Device03_info();

        ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);

        mMainActivity.mMainProcBike.v_Goto_Home();

        switch (imode)
        {
            case PROC_EXERCISE_TR:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_TRAINING);
                mMainActivity.mMainProcBike.trainingProc.vSetNextState(PROC_EXERCISE_TR_FINISH);
                break;

            case PROC_EXERCISE_PHY:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_PHYSICAL);
                mMainActivity.mMainProcBike.physicalProc.vSetNextState(PROC_SHOW_GERKIN_FINISH);
                break;

            case PROC_EXERCISE_HEARTRATE:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_HEART_RATE_CONTROL);
                mMainActivity.mMainProcBike.heartrateProc.vSetNextState(PROC_EXERCISE_HR_FINISH);
                break;

            case PROC_EXERCISE_HIIT:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_HIIT);
                break;

            case PROC_EXERCISE_WORKOUT:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_MY_WORKOUT);
                mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXERCISE_FINISH);
                break;

            case PROC_EXERCISE_QUICKSTART:
                mMainActivity.mMainProcBike.vSetNextState(MainState.PROC_QUICK_START);
                mMainActivity.mMainProcBike.quickStartProc.vSetNextState(PROC_EXERCISE_QS_FINISH);
                break;

            default:
                break;
        }

    }

    private void v_check_cloud_upload_UI() {
        boolean bcheck = false;
        ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);

        switch (imode) {
            case PROC_EXERCISE_TR:
                bcheck = true;
                break;

            case PROC_EXERCISE_PHY:
                bcheck = true;
                break;

            case PROC_EXERCISE_HEARTRATE:
                bcheck = true;
                break;

            case PROC_EXERCISE_HIIT:
                bcheck = true;
                break;

            case PROC_EXERCISE_WORKOUT:
                bcheck = true;
                break;

            case PROC_EXERCISE_QUICKSTART:
                bcheck = true;
                break;

            default:
                break;
        }

        //失敗pop失敗畫面
        if(bcheck)
        {
            String stitle01 = LanguageData.s_get_string(mContext, R.string.connection_lost);
            String sinfo01 = LanguageData.s_get_string(mContext, R.string.data_upload_unsuccessful);

            Dialog_UI_Info.v_tist_Dialog(R.drawable.wifi_disconect_icon, -1, stitle01, null, sinfo01, null, null, ImageView.ScaleType.CENTER_INSIDE);
            Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_CLOUD_UPDATE_FAIL02);
            Dialog_UI_Info.v_Set_Dialog_Cloud_retryCmd(cloudglobal.iINS_END_WKO_REC04);
        }


    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(StopState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        if(mNextState == StopState.PROC_NULL)
        {
            mState = StopState.PROC_STOP_CLOUD11;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_Cloud_11()
    {
        bServerResponseFlag = false;

        //有登入才做上傳運動資料
        if(CloudDataStruct.CloudData_20.is_log_in()) {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();
                    new Thread(CloudRunnable_11).start();
                }
            });

            mState = StopState.PROC_STOP_CLOUD11_CHECK;
        }
        else
        {
            mState = StopState.PROC_STOP_FINISH;
        }

    }

    private void vProc_Cloud_11_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });

            if(iServerResponse != 0)
            {
                v_check_cloud_upload_UI();
            }

            mState = StopState.PROC_STOP_FINISH;

        }
    }

    private void vProc_Finished() {

        v_check_finish_UI();

        mState = StopState.PROC_EXIT ;
    }

    private void vProc_Idle()
    {
        if(mNextState != StopState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = StopState.PROC_NULL;
        }

    }

    private void vProc_Exit() {
        mMainActivity.mMainProcBike.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXIT);

        mState = StopState.PROC_INIT ;
        mNextState = StopState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
            //Log.e("Jerry", "[StopProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_STOP_FINISH                       :{   vProc_Finished();                      break;  }

            case PROC_STOP_CLOUD11                      :{   vProc_Cloud_11();                       break;  }
            case PROC_STOP_CLOUD11_CHECK                :{   vProc_Cloud_11_Check();                 break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_11 = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            if (CloudDataStruct.CloudData_11.Set_input()) {
                iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_SendExercise_Data();
            }

            bServerResponseFlag = true;
        }
    };

}
