package com.rtx.treadmill.Cloud;

import android.util.Log;

import com.retonix.circlecloud.CloudCommand;
import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.Calendar;

/**
 * Created by jerry on 2016/12/27.
 */

public class CloudCmdFunc {
    private static String TAG = "Jerry===CloudCmdFunc";
    private static boolean DEBUG = false;
    private long DelayCheckWifiStart=System.currentTimeMillis();
    private long DelayCheckWifiEnd=DelayCheckWifiStart+10000;

    //////////////////////////////////////////////////////////////////////////////////////////
    //  function
    //////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////
    //Clear Cmd
    //////////////////////////////////////////////////////////////////////////////////////////
    public void vCmd_clear(CloudCommand Ccmd){
        if(Ccmd != null) Ccmd.clearData();
        return ;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //Check Data Valid For Target Goal
    //////////////////////////////////////////////////////////////////////////////////////////

    public int iRunnable_ReceiveData(CloudCommand Ccmd , CloudDataStruct.ServerResponse response){
        int iResult = -1;
        if(Ccmd != null){
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : Result=[" + Ccmd.getResult() +"],Data=["+Ccmd.getResponse()+"]");
            if(bCheckResultStatus(Ccmd)){
                iResult = Ccmd.getResult();
                if(Ccmd.getResponse() == null) iResult = -1;
                response.setCode(Ccmd.getCode());
                response.setMsg(Ccmd.getMSG());
                //檢查 wifi disconnect dialog是否存在
                if(Dialog_UI_Info.iDialogUiInfo_GetWifiState() == 1){
                    Dialog_UI_Info.vDialogUiInfo_SetWifiState(0);
                    Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
                }
            }else{
                // 6:Server Not Response
                DelayCheckWifiStart=System.currentTimeMillis();
                if(DelayCheckWifiStart>DelayCheckWifiEnd){
                    // if(Ccmd.getResult() == 6) Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_CLOUD_WIFI_FAIL01); //Modify by StevenChen 20200320
                }
            }
        }
        response.setResult(iResult);
        return iResult;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //Check Result Status
    //////////////////////////////////////////////////////////////////////////////////////////
    public boolean bCheckResultStatus(CloudCommand Ccmd){
        boolean bret;
        int iret ;
        int iTimeout ;
        int iUnit = 20 ; //unit = 20ms
        int iCount = 0;
        int imax;
        imax = Ccmd.get_retry_max();
        iTimeout=((imax*Ccmd.get_connect_timeout())+Ccmd.get_waitdata_timeout()+500)/iUnit;
        //iResult -1:init;
        // 0:success;
        // 1:runing;
        // 2:json_put error;
        // 3:data is null;
        // 4:data json parse fail;
        // 5:Response data length error
        // 6:Server Not Response
        iret = Ccmd.getResult();
        synchronized(Ccmd){
            while(iret == -1 || iret == 1){
                try{
                    Thread.sleep(iUnit);
                    iret=Ccmd.getResult();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                iCount++;
                if(iCount > iTimeout) break;
            }
        }

        if((iret == 0) || (iret == 3)) bret=true;
        else bret=false;
        return bret;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //Check Data Valid For Target Goal
    //////////////////////////////////////////////////////////////////////////////////////////

    public boolean check_TargetGoalData(CloudDataStruct.CLOUD_TATGET_GOAL data){
        boolean bRet=false;

        if(data != null){
            if(data.bCheckDataNull()) return bRet;
            if(data.bCheckDataEmpty()) return bRet;
            //int iDuration = Rtx_TranslateValue.iString2Int(data.sGol_Duration);
            int iDuration=Rtx_TranslateValue.iString2Int_Round(data.sGol_Duration);
            float fVal=Rtx_TranslateValue.fString2Float(data.sGol_Val);
            //Compare Current Time and Start Time
            {
                //Calendar cal_Now = GlobalData.getInstance();
                //Calendar cal_Start = Rtx_Calendar.cStr2Calendar(data.sStartDate, "yyyy-MM-dd");
                //if (cal_Now.compareTo(cal_Start) == -1) { return bRet; }
            }
            int iDuration_Min = 0;
            int iDuration_Max = 0;
            float fVal_Min = 0;
            float fVal_Max = 0;

            if(data.sGol_Item.equals("Distance")){
                iDuration_Min = 1;
                iDuration_Max = 9999;
                fVal_Min = 0;
                fVal_Max = 9999;
            }else if(data.sGol_Item.equals("Calories")){
                iDuration_Min = 1;
                iDuration_Max = 9999;
                fVal_Min = 0;
                fVal_Max = 99999;
            }else if(data.sGol_Item.equals("Frequency")){
                iDuration_Min = 3;
                iDuration_Max = 144;
                fVal_Min = 1;
                fVal_Max = 7;
            }else if(data.sGol_Item.equals("Target Weight")){
                iDuration_Min = 1;
                iDuration_Max = 9999;
                //20190108 Target/Body 規格 35 變更為 34
                fVal_Min = 34;
                fVal_Max = 220;
            }else if(data.sGol_Item.equals("Target Body Fat")){
                iDuration_Min = 1;
                iDuration_Max = 9999;
                fVal_Min = 2;
                fVal_Max = 50;
            }else return bRet;

            if((iDuration < iDuration_Min) || (iDuration > iDuration_Max)) return bRet;
            if((fVal < fVal_Min) || (fVal > fVal_Max)) return bRet;
            bRet=true;
        }
        return bRet;
    }
}
