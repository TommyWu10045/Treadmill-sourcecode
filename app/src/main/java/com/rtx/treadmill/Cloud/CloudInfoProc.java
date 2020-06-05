package com.rtx.treadmill.Cloud;

import android.content.Context;
import android.util.Log;

import com.retonix.circlecloud.Cloud_27_GET_DEV_BSC;
import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxTools.WriteLogUtil;

import static com.rtx.treadmill.GlobalData.CloudCmd_Info.CloudCMD_List;
import static com.rtx.treadmill.GlobalData.CloudCmd_Info.iCmd_index;
import static com.rtx.treadmill.GlobalData.CloudCmd_Info.sCmd;

/**
 * Created by chasechang on 3/27/17.
 */

public class CloudInfoProc {
    private String TAG = "Jerry=" ;
    private boolean DEBUG = false ;
    private Context mContext;
    private MainActivity mMainActivity ;
    private CloudInfoState mState = CloudInfoState.PROC_INIT ;
    private CloudInfoState mNextState = CloudInfoState.PROC_NULL ;
    private CloudInfoState tempState = CloudInfoState.PROC_NULL ;
    private boolean bServerResponseFlag = false;
    private int iServerResponse = -1;
    private int icount ;

    public CloudInfoProc(MainActivity mMainActivity) {
        this.mContext = mMainActivity.mContext;
        this.mMainActivity = mMainActivity;
        mState = CloudInfoState.PROC_INIT;
        icount = 1;
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(CloudInfoState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init(){
        if(mNextState == CloudInfoState.PROC_NULL){ mState = CloudInfoState.PROC_IDLE; }
        else{ mState = mNextState; }
    }

    private void vProc_Cloud(){
        sCmd = vCloudCmd_get(iCmd_index);
        if(DEBUG) Log.e(TAG, "===vProc_Cloud=====sCmd.icmd=" + sCmd.icmd);
        if(sCmd != null){
            bServerResponseFlag=false;
            new Thread(CloudRunnable_do).start();
            mState = CloudInfoState.PROC_INFO_CLOUD_CHECK;
        }else{ mState = CloudInfoState.PROC_IDLE; }
    }

    private void vProc_Cloud_check(){
        if(bServerResponseFlag){
            if (iServerResponse == 0){
                switch (sCmd.icmd){
                    case cloudglobal.iCHK_LIV05:
                        icount = 1 ;
                        break;
                    case cloudglobal.iDB_DEV_BSC03:
                        break;
                    case cloudglobal.iGET_DEV_BSC01:
                        {
                            float fval;
                            float fval_dev;
                            boolean bret=false;

                            fval = CloudDataStruct.CloudData_27.fGet_output(Cloud_27_GET_DEV_BSC.Output.DEV_TIM);
//                            if(fval >= 0)
                            {
                                fval_dev = EngSetting.f_Get_ENG_DEV_TIME();
                                //檢查是否clear
                                if(fval_dev == -11){
                                    EngSetting.v_Set_ENG_DEV_TIME(0f);
                                    Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_TIME, Rtx_TranslateValue.sFloat2String(0f, 3));
                                }else{
                                    if(fval > fval_dev){
                                        EngSetting.v_Set_ENG_DEV_TIME(fval);
                                        Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_TIME, Rtx_TranslateValue.sFloat2String(fval, 3));
                                    }
                                }
                            }

                            fval=CloudDataStruct.CloudData_27.fGet_output(Cloud_27_GET_DEV_BSC.Output.DEV_KM);
//                            if(fval >= 0)
                            {
                                fval_dev = EngSetting.f_Get_ENG_DEV_DISTANCE();
                                //檢查是否clear
                                if(fval_dev == -11){
                                    EngSetting.v_Set_ENG_DEV_DISTANCE(0f);
                                    Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_DISTANCE, Rtx_TranslateValue.sFloat2String(0f, 3));
                                }else{
                                    if(fval > fval_dev){
                                        EngSetting.v_Set_ENG_DEV_DISTANCE(fval);
                                        Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_DISTANCE, Rtx_TranslateValue.sFloat2String(fval, 3));
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                mState = CloudInfoState.PROC_IDLE;
            }else{
                if(sCmd.iretry < 1){
                    mState = CloudInfoState.PROC_IDLE;
                }else{
                    mState = CloudInfoState.PROC_INFO_CLOUD;
                    sCmd.iretry--;
                    iCmd_index--;
                }
            }
        }
    }

    private void vProc_Idle(){
       if(!b_Can_clear_CloudCmd()){
           mState = CloudInfoState.PROC_INFO_CLOUD ;
       }else{
           icount++;
           //Dayly write
           if(icount % EngSetting.DEF_UPDATE_DEV_STATUS == 0){ WriteLogUtil.vWrite_LogInfo_Code(true); }
       }
    }

    private void vProc_Exit(){
        mState=CloudInfoState.PROC_INIT ;
        mNextState=CloudInfoState.PROC_NULL;
    }

    /* ------------------------------------------------------------------------ */
    public void run(){
        if(tempState != mState){
            //Log.e("Jerry", "[CloudInfoProc] mState = " + mState);
            tempState = mState;
        }

        switch(mState){
            case PROC_INIT: { vProc_Init(); break; }
            case PROC_INFO_CLOUD: { vProc_Cloud(); break; }
            case PROC_INFO_CLOUD_CHECK: { vProc_Cloud_check(); break; }
            case PROC_IDLE: { vProc_Idle(); break; }
            case PROC_EXIT: { vProc_Exit(); break; }
            default: { vProc_Idle(); break; }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_do = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse=-1;

            if(sCmd != null){
               switch (sCmd.icmd){
                   case cloudglobal.iCHK_LIV05:
                       iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_SetDeviceStatus();
                       break;
                   case cloudglobal.iDB_DEV_BSC03:
                       iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_SetDeviceInfo();
                       break;
                   case cloudglobal.iGET_DEV_BSC01:
                       iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_GetDeviceInfo();
                       break;
                   default:
                       break;
               }
            }
            bServerResponseFlag = true;
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean b_Can_clear_CloudCmd(){
        boolean bret = false;

        if(iCmd_index >= CloudCMD_List.size()){
            bret=true;
            CloudCMD_List.clear();
            iCmd_index=0;
        }
        return bret;
    }

    private CloudCmd_Info.S_CMD vCloudCmd_get(int index){
        CloudCmd_Info.S_CMD scmd=null;

        if(index < CloudCMD_List.size()){ scmd=CloudCMD_List.get(index); }
        iCmd_index++;
        return scmd;
    }
}
