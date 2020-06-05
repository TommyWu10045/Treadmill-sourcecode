package com.rtx.treadmill.Cloud;

import android.util.Log;

import com.retonix.circlecloud.Cloud_01_QRY_UPD;
import com.retonix.circlecloud.Cloud_02_CHK_LIV;
import com.retonix.circlecloud.Cloud_03_DEV_BSC;
import com.retonix.circlecloud.Cloud_04_CHK_AVL_MSN_DLR;
import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.retonix.circlecloud.Cloud_09_GET_WRK_ITV_SET;
import com.retonix.circlecloud.Cloud_10_GET_WRK_NAM;
import com.retonix.circlecloud.Cloud_11_INS_END_WKO_REC;
import com.retonix.circlecloud.Cloud_12_DEL_END_WKO_REC;
import com.retonix.circlecloud.Cloud_13_DEL_BKG_CLS;
import com.retonix.circlecloud.Cloud_14_GET_BDY_HIS;
import com.retonix.circlecloud.Cloud_15_GET_BDY_IDX_REC;
import com.retonix.circlecloud.Cloud_16_GET_EW_SUM_HIS;
import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retonix.circlecloud.Cloud_20_CHK_LGI;
import com.retonix.circlecloud.Cloud_21_GET_EXC_REC;
import com.retonix.circlecloud.Cloud_23_DB_EXC_GOL;
import com.retonix.circlecloud.Cloud_24_GET_EXC_GOL;
import com.retonix.circlecloud.Cloud_26_GET_EW_YER;
import com.retonix.circlecloud.Cloud_27_GET_DEV_BSC;
import com.retonix.circlecloud.Cloud_29_GET_BLT_BOD_LST;
import com.retonix.circlecloud.Cloud_30_GET_BLT_BOD;
import com.retonix.circlecloud.Cloud_31_WEB_GET_CLS_SUC;
import com.retonix.circlecloud.Cloud_32_GET_CLS_BSC;
import com.retonix.circlecloud.Cloud_33_GET_EW_DAY;
import com.retonix.circlecloud.Cloud_34_GET_EW_MON;
import com.retonix.circlecloud.Cloud_37_UPD_MDF_PW;
import com.retonix.circlecloud.Cloud_39_GET_GYM_BKI;
import com.retonix.circlecloud.Cloud_42_DEL_WRK_ITV_SET;
import com.retonix.circlecloud.Cloud_43_DB_TRD_SET;
import com.retonix.circlecloud.Cloud_44_INS_USR_BSC;
import com.retonix.circlecloud.Cloud_45_UPD_USR_BSC;
import com.retonix.circlecloud.Cloud_46_INS_WRK_ITV_SET;
import com.retonix.circlecloud.Cloud_47_INS_BKG_CLS;
import com.retonix.circlecloud.Cloud_48_UPD_WRK_ITV_SET;
import com.retonix.circlecloud.Cloud_50_DB_EXC_GOL_TGE;
import com.retonix.circlecloud.Cloud_59_GET_EXC_GOL_TGE;
import com.retonix.circlecloud.Cloud_60_GET_END_WRK_FQC;
import com.retonix.circlecloud.Cloud_62_GET_TRD_TKN;
import com.retonix.circlecloud.Cloud_63_SEN_MAL_PW;
import com.retonix.circlecloud.Cloud_65_GET_TRD_PTY;
import com.retonix.circlecloud.Cloud_67_DEL_EXC_GOL_TGE;
import com.retonix.circlecloud.Cloud_69_GET_ACN_DAT;
import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

/**
 * Created by jerry on 2016/12/27.
 */

public class CloudCmd extends CloudCmdList{
    private static String TAG = "Jerry===CloudCmd";
    private static boolean DEBUG = false;

    //////////////////////////////////////////////////////////////////////////////////////////
    //  command
    //
    //01    public int iCloudCmd_GetUpdateInfo()
    //02    public int iCloudCmd_SetDeviceStatus(String STT, String COD)
    //03    public int iCloudCmd_SetDeviceInfo(String SKM, String STIM)
    //05    public int iCloudCmd_SetBodyIndex()
    //04    public int iCloudCmd_RegisterDevice()
    //09    public int iCloudCmd_GetWorkoutData(String sMCH_TYP, String sWRK_ID)

    //10    public int iCloudCmd_GetWorkoutName()
    //11    public int iCloudCmd_SendExercise_Data()
    //12    public int iCloudCmd_DelExercise_Session(String Work_seq)
    //13    public int iCloudCmd_GymClass_Delete(String sBkgSeq)
    //14    public int iCloudCmd_GetBodyIndexHistory()
    //15    public int iCloudCmd_GetBodyIndex()
    //16    public int iCloudCmd_GetDstAndCalVal(String sUserSeq, String sExcMode, Calendar calStart, Calendar calEnd, CloudDataStruct.CLOUND_GET_DST_AND_CAL_VAL data)
    //17    public int iCloudCmd_GetUserInfo(String sUserSeq, CloudDataStruct.USER_INFO data)

    //20    public int iCloudCmd_Login(String sName , String sPassword , CloudDataStruct.LoginData data)
    //21    public int public int iCloudCmd_GetExercise_Session(ArrayList<String[]> slist, String date, String cnt)
    //23    public int iCloudCmd_UploadTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    //24    public int iCloudCmd_GetTargetGoal(String sUserSeq, ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal)
    //26    public int iCloudCmd_GetExercise_Year(ArrayList<String[]> slist)
    //27    public int iCloudCmd_GetDeviceInfo()
    //29    public int iCloudCmd_GetBulletinList()

    //30    public int iCloudCmd_GetBulletinDetailInfo(String sBulletinID)
    //31    public int iCloudCmd_GetGymClassList(Calendar cStart , Calendar cEnd)
    //32    public int iCloudCmd_GetGymClassDetailInfo(String sClsID)
    //33    public int iCloudCmd_GetExercise_DayofMonth(TreeMap<String, String[]> stree, String sdate, String edate)
    //34    public int iCloudCmd_GetExercise_Month(ArrayList<String[]> slist, String sort, String sdate, String edate)
    //37    public int iCloudCmd_UploadPW(String pw)
    //39    public int iCloudCmd_GetGymMyClassList(Calendar cStart , Calendar cEnd)

    //42    public int iCloudCmd_Workout_DeleteItem(String sMchTyp , String sWrkId , String sWrkName) {
    //43
    //44    public int iCloudCmd_Registration(CloudDataStruct.Registration data)
    //45    public int iCloudCmd_UpdateUserInfo()
    //47    public int iCloudCmd_GymClass_Add(Calendar cClassDateTile, String sClsID)

    //50    public int iCloudCmd_GoalClose_Add(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close)
    //59    public int iCloudCmd_GetTargetGoalClose(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose)

    //60    public int iCloudCmd_GetExerciseDate(String sUserSeq, Calendar calStart, Calendar calEnd, String[] array_Str)
    //62    public int iCloudCmd_GetThirdPartyApps()
    //63    public int iCloudCmd_ForgotPassword(String sEmil)
    //65    public int iCloudCmd_GetThirdPartyApps_Enable()
    //67    public int iCloudCmd_GoalClose_Delete(String sGolSeq)
    //
    //
    //
    //////////////////////////////////////////////////////////////////////////////////////////

    //01
    public int iCloudCmd_GetUpdateInfo(){
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iQRY_UPD03;

        if(CloudDataStruct.CloudData_01.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_01_QRY_UPD.sDB_Cmd[2], CloudDataStruct.CloudData_01.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_01_QRY_UPD.sDB_Cmd[3], CloudDataStruct.CloudData_01.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }  //Server Error
                else if(response.getMsg().equals("SQ1")){ iret = 0; }   //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }   //Fail
                else{ iret = 2; } //Unknow Error
                if (iret == 0){ }
            }
        }
        return iret;
    }

    //02
    public int iCloudCmd_SetDeviceStatus(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iCHK_LIV05;

        if(CloudDataStruct.CloudData_02.Set_input()) {
            CloudApi_API_SetCommand(inum, Cloud_02_CHK_LIV.sDB_Cmd[2], CloudDataStruct.CloudData_02.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_02_CHK_LIV.sDB_Cmd[3], CloudDataStruct.CloudData_02.sdata_out);
            if (response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("TQ1")){ iret = 0; }  //Success
            else if (response.getMsg().equals("TQ0")){ iret = 1; }  //Fail
            else { iret = 2; }
        }
        return iret;
    }

    //03
    public int iCloudCmd_SetDeviceInfo(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum = cloudglobal.iDB_DEV_BSC03;
        if(CloudDataStruct.CloudData_27.bgetdata){
            if(CloudDataStruct.CloudData_03.Set_input()){
                CloudApi_API_SetCommand(inum, Cloud_03_DEV_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_03.sdata_in);
                response=CloudApi_API_GetCommand(inum, Cloud_03_DEV_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_03.sdata_out);
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("FM1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("TM1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("FM0")){ iret = 1; }  //Fail
                else if(response.getMsg().equals("TM0")){ iret = 1; }  //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //04
    public int iCloudCmd_RegisterDevice(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iCHK_AVL_MSN_DLR01;
        if(CloudDataStruct.CloudData_04.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_04_CHK_AVL_MSN_DLR.sDB_Cmd[2], CloudDataStruct.CloudData_04.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_04_CHK_AVL_MSN_DLR.sDB_Cmd[3], CloudDataStruct.CloudData_04.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
            else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
            else{ iret = 2; }
        }
        return iret;
    }

    //05
    public int iCloudCmd_SetBodyIndex(){
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDB_BDY_IDX_REC06;
        CloudDataStruct.BodyIndexData_05.v_SetBodyIndex_Update_Data();
        CloudApi_API_SetCommand(inum, Cloud_05_DB_BDY_IDX_REC.sDB_Cmd[2], CloudDataStruct.BodyIndexData_05.sdata_in);
        response=CloudApi_05_SetBodyIndex_get(inum);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("FM1")){ iret = 0; }   //
            else if(response.getMsg().equals("TM1")){ iret = 0; }   //
            else{ iret = 2; }   //Unknow Error
            if(iret == 0){ }
        }
        return iret;
    }

    //09
    public int iCloudCmd_GetWorkoutData(String sMCH_TYP, String sWRK_ID){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_WRK_ITV_SET02;
        CloudDataStruct.CloudData_09.Set_input(sMCH_TYP,sWRK_ID);
        CloudApi_API_SetCommand(inum, Cloud_09_GET_WRK_ITV_SET.sDB_Cmd[2], CloudDataStruct.CloudData_09.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_09_GET_WRK_ITV_SET.sDB_Cmd[3], CloudDataStruct.CloudData_09.sdata_out);
        if(response.getMsg() == null){ iret = -1; }
        else if(response.getMsg().equals("SQ1")) { iret = 0; }  //Success
        else if(response.getMsg().equals("SQ0")) { iret = 1; }  //Fail
        else{ iret = 2; }
        return iret;
    }

    //10
    public int iCloudCmd_GetWorkoutName(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Unknow Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_WRK_NAM02;
        CloudDataStruct.CloudData_10.Set_input();
        CloudApi_API_SetCommand(inum, Cloud_10_GET_WRK_NAM.sDB_Cmd[2], CloudDataStruct.CloudData_10.sdata_in);
        response=CloudApi_10_GetWorkoutName_get(inum, Cloud_10_GET_WRK_NAM.sDB_Cmd[3],CloudDataStruct.CloudData_10.list_CloudWorkoutName);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("SQ1")){ iret = 0; }   //
            else if(response.getMsg().equals("SQ0")){ iret = 1; }   //
            else{ iret = 2; }   //Unknow Error
            if(iret == 0){ }
        }
        return iret;
    }

    //11
    public int iCloudCmd_SendExercise_Data(){
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iINS_END_WKO_REC04;
        CloudApi_API_SetCommand(inum, Cloud_11_INS_END_WKO_REC.sDB_Cmd[2], CloudDataStruct.CloudData_11.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_11_INS_END_WKO_REC.sDB_Cmd[3], CloudDataStruct.CloudData_11.sdata_out);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("SM1")){ iret = 0; }   //
            else{ iret = 2; }   //Unknow Error
            if(iret == 0){ }
        }
        return iret;
    }

    //12
    public int iCloudCmd_DelExercise_Session(String Work_seq){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDEL_END_WKO_REC01;
        if(CloudDataStruct.CloudData_12.Set_input(Work_seq)){
            CloudApi_API_SetCommand(inum, Cloud_12_DEL_END_WKO_REC.sDB_Cmd[2], CloudDataStruct.CloudData_12.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_12_DEL_END_WKO_REC.sDB_Cmd[3], CloudDataStruct.CloudData_12.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SM1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SM0")){ iret = 1; }  //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //13
    public int iCloudCmd_GymClass_Delete(String sBkgSeq){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDEL_BKG_CLS01;
        Log.e("Jerry","sBkgSeq = " + sBkgSeq);
        if(CloudDataStruct.CloudData_13.Set_input(sBkgSeq)){
            CloudApi_API_SetCommand(inum, Cloud_13_DEL_BKG_CLS.sDB_Cmd[2], CloudDataStruct.CloudData_13.sdata_in);
            response = CloudApi_API_GetCommand(inum, Cloud_13_DEL_BKG_CLS.sDB_Cmd[3], CloudDataStruct.CloudData_13.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SM1")){ iret = 0; }  //Success
            else if(response.getMsg().equals("SM0")){ iret = 1; }  //Fail
            else{ iret = 2; }
        }
        return iret;
    }

    //14
    public int iCloudCmd_GetBodyIndexHistory(){
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_BDY_HIS06;
        CloudApi_API_SetCommand(inum, Cloud_14_GET_BDY_HIS.sDB_Cmd[2], CloudDataStruct.BodyIndexData_14.sdata_in);
        response=CloudApi_14_GetBodyIndexHistory_get(inum, CloudDataStruct.BodyIndexData_14.sout_keys);
        if(response.isSuccess()){
            TreeMap<String, String[]> mHistTree = CloudDataStruct.BodyIndexData_14.tree_clound_cmd14_result;
            if(mHistTree != null && mHistTree.size() > 0){ iret = 0; }  //Success
            if(iret == 0){ }
        }
        return iret;
    }

    //15
    public int iCloudCmd_GetBodyIndex(){
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_BDY_IDX_REC02;
        CloudApi_API_SetCommand(inum, Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[2], CloudDataStruct.BodyIndexData_15.sdata_in);
        response=CloudApi_15_GetBodyIndexCurrent_get(inum, Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[3], CloudDataStruct.BodyIndexData_15.sdata_out);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("SQ1")){ iret = 0; }   //
            else{ iret = 2; }   //Unknow Error
            if(iret == 0){ }
        }
        return iret;
    }

    //16
    public int iCloudCmd_GetDstAndCalVal(String sExcMode, Calendar calStart, Calendar calEnd){
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_EW_SUM_HIS05;
        if(CloudDataStruct.CloudData_16.Set_input(sExcMode, calStart, calEnd)){
            CloudApi_API_SetCommand(inum, Cloud_16_GET_EW_SUM_HIS.sDB_Cmd[2], CloudDataStruct.CloudData_16.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_16_GET_EW_SUM_HIS.sDB_Cmd[3], CloudDataStruct.CloudData_16.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }  //Server Error
                else if(response.getMsg().equals("TQ1")){ iret = 0; }   //A
                else if(response.getMsg().equals("FQ1")){ iret = 0; }   //R,E,B
                else{ iret = 2; }   //Unknow Error
                if(iret == 0){ }
            }
        }
        return iret;
    }

    //17
    public int iCloudCmd_GetUserInfo(){
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_USR_BSC04;
        if(CloudDataStruct.CloudData_17.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_17_GET_USR_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_17.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_17_GET_USR_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_17.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }  //Server Error
                else if(response.getMsg().equals("SQ1")){ iret = 0; }   //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }   //Fail
                else{ iret = 2; }   //Unknow Error
                if(iret == 0) CloudDataStruct.CloudData_20.set_log_in(true);
            }
        }
        return iret;
    }

    //20
    public int iCloudCmd_Login(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Passwork fail
        //  2 : Unknow Error
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iCHK_LGI03;
        CloudApi_API_SetCommand(inum, Cloud_20_CHK_LGI.sDB_Cmd[2], CloudDataStruct.CloudData_20.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_20_CHK_LGI.sDB_Cmd[3], CloudDataStruct.CloudData_20.sdata_out);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("TQ1")){ iret = 1; }   //Fail
            else if(response.getMsg().equals("SQ1")){ iret = 0; }   //Success
            else{ iret = 2; }   //Unknow Error
        }
        return iret;
    }

    //21
    public int iCloudCmd_GetExercise_Session(ArrayList<String[]> slist, String date, String cnt){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_EXC_REC07;
        if(CloudDataStruct.CloudData_21.Set_input(date, cnt) && slist != null){
            CloudApi_API_SetCommand(inum, Cloud_21_GET_EXC_REC.sDB_Cmd[2], CloudDataStruct.CloudData_21.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_21_GET_EXC_REC.sDB_Cmd[3], slist);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //23
    public int iCloudCmd_UploadTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Passwork fail
        //  2 : Unknow Error
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDB_EXC_GOL03;
        CloudDataStruct.CloudData_23.Set_input(cloud_TargetGoal);
        CloudApi_API_SetCommand(inum, Cloud_23_DB_EXC_GOL.sDB_Cmd[2], CloudDataStruct.CloudData_23.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_23_DB_EXC_GOL.sDB_Cmd[3], CloudDataStruct.CloudData_23.sdata_out);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("FM1")){ iret = 0; }   //Add Success
            else if(response.getMsg().equals("FM0")){ iret = 1; }   //Add Fail
            else if(response.getMsg().equals("TM1")){ iret = 0; }   //Add Success
            else if(response.getMsg().equals("TM0")){ iret = 1; }   //Add Fail
            else{ iret = 2; }   //Unknow Error
        }
        return iret;
    }

    //24
    public int iCloudCmd_GetTargetGoal(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_EXC_GOL04;
        if(CloudDataStruct.CloudData_24.Set_input() && list_CloudTargetGoal != null){
            CloudApi_API_SetCommand(inum, Cloud_24_GET_EXC_GOL.sDB_Cmd[2], CloudDataStruct.CloudData_24.sdata_in);
            response=CloudApi_24_GetTargetGoal_get(inum, Cloud_24_GET_EXC_GOL.sDB_Cmd[3], list_CloudTargetGoal);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                if(iret == 0){
                    //Remove Invalid Data
                    if(list_CloudTargetGoal != null){
                        int iSize=list_CloudTargetGoal.size();
                        int iIndex;
                        int iRemoveCount=0;
                        int iListIndex;
                        for(iIndex=0 ; iIndex < iSize ; iIndex++){
                            iListIndex=iIndex-iRemoveCount;
                            if(!check_TargetGoalData(list_CloudTargetGoal.get(iListIndex))){
                                list_CloudTargetGoal.remove(iListIndex);
                                iRemoveCount++;
                            }
                        }
                    }
                }
            }
        }
        return iret;
    }

    //26
    public int iCloudCmd_GetExercise_Year(TreeMap<String, String[]> stree){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum = cloudglobal.iGET_EW_YER05;
        ArrayList<String[]> slist=new ArrayList<String[]>();
        String[] sdata;
        int iLoop;

        if(CloudDataStruct.CloudData_26.Set_input() && stree != null){
            CloudApi_API_SetCommand(inum, Cloud_26_GET_EW_YER.sDB_Cmd[2], CloudDataStruct.CloudData_26.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_26_GET_EW_YER.sDB_Cmd[3], slist);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
            }
            if(iret == 0 && slist != null){
                for(iLoop=0; iLoop < slist.size(); iLoop++){
                    sdata=slist.get(iLoop);
                    if(sdata != null){
                        stree.put(sdata[Cloud_26_GET_EW_YER.Output.YM], sdata);
                    }
                }
            }
        }
        return iret;
    }

    //27
    public int iCloudCmd_GetDeviceInfo(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_DEV_BSC01;
        if(CloudDataStruct.CloudData_27.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_27_GET_DEV_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_27.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_27_GET_DEV_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_27.sdata_out);
            if (response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SQ1")){ iret=0; CloudDataStruct.CloudData_27.v_set_get_dev_info(true); }  //Success
            else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
            else{ iret = 2; }
        }
        return iret;
    }

    //29
    public int iCloudCmd_GetBulletinList(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_BLT_BOD_LST02;
        CloudDataStruct.CloudData_29.Set_input(99, EngSetting.s_Get_ENG_GYM_ID());
        if(CloudDataStruct.CloudData_29.list_CloudBulletinInfo != null){
            CloudApi_API_SetCommand(inum, Cloud_29_GET_BLT_BOD_LST.sDB_Cmd[2], CloudDataStruct.CloudData_29.sdata_in);
            response=CloudApi_29_GetBulletinList_get(inum, Cloud_29_GET_BLT_BOD_LST.sDB_Cmd[3], CloudDataStruct.CloudData_29.list_CloudBulletinInfo);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                if(iret == 0){ }
            }
        }
        return iret;
    }

    //30
    public int iCloudCmd_GetBulletinDetailInfo(String sBulletinID){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_BLT_BOD02;
        CloudDataStruct.CloudData_30.Set_input(EngSetting.s_Get_ENG_GYM_ID(),sBulletinID);
        CloudApi_API_SetCommand(inum, Cloud_30_GET_BLT_BOD.sDB_Cmd[2], CloudDataStruct.CloudData_30.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_30_GET_BLT_BOD.sDB_Cmd[3], CloudDataStruct.CloudData_30.sdata_out);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("SQ0")){ iret = 1; }   //Fail
            else if(response.getMsg().equals("SQ1")){ iret = 0; }   //Success
            else{ iret = 2; }   //Unknow Error
        }
        return iret;
    }

    //31
    public int iCloudCmd_GetGymClassList(Calendar cStart , Calendar cEnd){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iWEB_GET_CLS_SUC03;
        CloudDataStruct.CloudData_31.Set_input(EngSetting.s_Get_ENG_GYM_ID(), cStart, cEnd);
        if(CloudDataStruct.CloudData_31.list_CloudClassInfo != null){
            //CloudDataStruct.CloudData_31.list_CloudClassInfo.clear();
            CloudApi_API_SetCommand(inum, Cloud_31_WEB_GET_CLS_SUC.sDB_Cmd[2], CloudDataStruct.CloudData_31.sdata_in);
            response = CloudApi_31_GetGymClassList_get(inum, Cloud_31_WEB_GET_CLS_SUC.sDB_Cmd[3], CloudDataStruct.CloudData_31.list_CloudClassInfo);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                if(iret == 0){ }
            }
        }
        return iret;
    }

    //32
    public int iCloudCmd_GetGymClassDetailInfo(String sClsID){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail

        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_CLS_BSC04;
        CloudDataStruct.CloudData_32.Set_input(sClsID);
        CloudApi_API_SetCommand(inum, Cloud_32_GET_CLS_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_32.sdata_in);
        response=CloudApi_32_GetGymClassDetailInfo_get(inum, Cloud_32_GET_CLS_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_32.list_CloudClassDetailInfo);
        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }  //Server Error
            else if(response.getMsg().equals("SQ0")){ iret = 1; }   //Fail
            else if(response.getMsg().equals("SQ1")){ iret = 0; }   //Success
            else{ iret = 2; }   //Unknow Error
        }
        return iret;
    }

    //33
    public int iCloudCmd_GetExercise_DayofMonth(TreeMap<String, String[]> stree, String sdate, String edate){
        // -1 : Server Error ;
        // 11 : success data end
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_EW_DAY02;
        ArrayList<String[]> slist = new ArrayList<String[]>();
        String[] sdata;
        int iLoop;

        if(CloudDataStruct.CloudData_33.Set_input(sdate, edate) && stree != null && slist != null){
            CloudApi_API_SetCommand(inum, Cloud_33_GET_EW_DAY.sDB_Cmd[2], CloudDataStruct.CloudData_33.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_33_GET_EW_DAY.sDB_Cmd[3], slist);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                //Log.e("EndersChen","Cloud33Response["+iret+"]");
            }

            if(iret == 0 && slist != null){
                //Log.e("EndersChen","Cloud33Msg["+slist.size()+"]");
                for(iLoop=0; iLoop < slist.size(); iLoop++){
                    sdata=slist.get(iLoop);
                    if(sdata != null){
                        stree.put(sdata[Cloud_33_GET_EW_DAY.Output.YM], sdata);
                    }
                }
            }
        }
        return iret;
    }

    //34
    public int iCloudCmd_GetExercise_Month(TreeMap<String, String[]> stree, String sort, String sdate, String edate){
        // -1 : Server Error ;
        // 11 : success data end
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum = cloudglobal.iGET_EW_MON06;
        ArrayList<String[]> slist = new ArrayList<String[]>();
        String[] sdata;
        int iLoop;

        if(CloudDataStruct.CloudData_34.Set_input(sort, sdate, edate) && stree != null && slist != null){
            CloudApi_API_SetCommand(inum, Cloud_34_GET_EW_MON.sDB_Cmd[2], CloudDataStruct.CloudData_34.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_34_GET_EW_MON.sDB_Cmd[3], slist);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("FQ1")){ iret = 11; }  //Success data end
                else if(response.getMsg().equals("TQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("FQ0")){ iret = 1; }  //Fail
                else if(response.getMsg().equals("TQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                //Log.e("EndersChen","Cloud34Response["+iret+"]");
            }
            if((iret == 0 || iret == 11) && slist != null){
                //Log.e("EndersChen","Cloud34Msg["+slist.size()+"]");
                for(iLoop=0; iLoop < slist.size(); iLoop++){
                    sdata=slist.get(iLoop);
                    if(sdata != null){
                        stree.put(sdata[Cloud_34_GET_EW_MON.Output.YM], sdata);
                    }
                }
            }
        }
        return iret;
    }

    //37
    public int iCloudCmd_UploadPW(String pw){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Passwork fail
        //  2 : Unknow Error
        int iret = -1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iUPD_MDF_PW01;
        if(CloudDataStruct.CloudData_37.Set_input(pw)) {
            CloudApi_API_SetCommand(inum, Cloud_37_UPD_MDF_PW.sDB_Cmd[2], CloudDataStruct.CloudData_37.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_37_UPD_MDF_PW.sDB_Cmd[3], CloudDataStruct.CloudData_37.sdata_out);
            if(response.isSuccess()) {
               if(response.getMsg().equals("SQ1")){ iret = 1; }   //Fail
               else if(response.getMsg().equals("TM1")){ iret = 0; }   //Success
               else{ iret = 2; }   //Unknow Error
            }
        }
        return iret;
    }

    //39
    public int iCloudCmd_GetGymMyClassList(Calendar cStart , Calendar cEnd)
    {
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum = cloudglobal.iGET_GYM_BKI04;
        CloudDataStruct.CloudData_39.Set_input(EngSetting.s_Get_ENG_GYM_ID(), cStart, cEnd);
        if(CloudDataStruct.CloudData_39.list_CloudMyClassInfo != null){
            //CloudDataStruct.CloudData_39.list_CloudMyClassInfo.clear();
            CloudApi_API_SetCommand(inum, Cloud_39_GET_GYM_BKI.sDB_Cmd[2], CloudDataStruct.CloudData_39.sdata_in);
            response=CloudApi_39_GetGymMyClassList_get(inum, Cloud_39_GET_GYM_BKI.sDB_Cmd[3], CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; }  //Fail
                else{ iret = 2; }
                if(iret == 0){ }
            }
        }
        return iret;
    }

    //42
    public int iCloudCmd_Workout_DeleteItem(String sMchTyp , String sWrkId , String sWrkName) {
        // -1 : Server Error ;
        //  0 : success
        //  1 : Delete Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDEL_WRK_ITV_SET02;
        Log.e("Jerry","sMchTyp = " + sMchTyp);
        Log.e("Jerry","sWrkId = " + sWrkId);
        Log.e("Jerry","sWrkName = " + sWrkName);
        if(CloudDataStruct.CloudData_42.Set_input(sMchTyp,sWrkId,sWrkName)){
            CloudApi_API_SetCommand(inum, Cloud_42_DEL_WRK_ITV_SET.sDB_Cmd[2], CloudDataStruct.CloudData_42.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_42_DEL_WRK_ITV_SET.sDB_Cmd[3], CloudDataStruct.CloudData_42.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SM1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SM0")){ iret = 1; } //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //43
    public int iCloudCmd_ThirdAPP_Set(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Delete Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDB_TRD_SET03;

        CloudApi_API_SetCommand(inum, Cloud_43_DB_TRD_SET.sDB_Cmd[2], CloudDataStruct.CloudData_43.sdata_in);
        response=CloudApi_API_GetCommand(inum, Cloud_43_DB_TRD_SET.sDB_Cmd[3], CloudDataStruct.CloudData_43.sdata_out);

        if(response.isSuccess()){
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("FM1")){ iret = 0; } //Success
            else if(response.getMsg().equals("FM0")){ iret = 1; } //Fail
            else if(response.getMsg().equals("TM1")){ iret = 0; } //Success
            else if(response.getMsg().equals("TM0")){ iret = 1; } //Fail
            else{ iret = 2; }
        }
        return iret;
    }

    //44
    public int iCloudCmd_Registration(CloudDataStruct.Registration data){
        // -1 : Server Error ;
        //  0 : success
        //  1 : exist
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iINS_USR_BSC03;
        String sName=new String(data.sName);
        String sBirthDay=Rtx_Calendar.sCalendar2Str(data.cBirthDay,"yyyy-MM-dd");
        String sHeight=Rtx_TranslateValue.sFloat2String(data.fHeight,1);
        String sEmail=new String(data.sEmail);
        String sPassword=new String(data.sPassword);
        String sSex;
        String sUnit;

        if(data.iGender == 0){ sSex=new String("F"); }
        else{ sSex = new String("M"); }

        if(data.iUnit == 0){ sUnit = new String("M"); }
        else{ sUnit = new String("E"); }

        if(CloudDataStruct.CloudData_44.Set_input(sName, sBirthDay, sHeight, sEmail, sPassword, sSex, sUnit)){
            CloudApi_API_SetCommand(inum, Cloud_44_INS_USR_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_44.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_44_INS_USR_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_44.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SQ1")){ iret = 1; }  //exist
            else if(response.getMsg().equals("SM1")){ iret = 1; }  //exist
            else if(response.getMsg().equals("TM1")){ iret = 0; }  //Success
            else{ iret = 2; }
        }
        CloudDataStruct.CloudData_44.clear();
        return iret;
    }

    //45
    public int iCloudCmd_UpdateUserInfo(){
        // -1 : Server Error ;
        //  0 : success
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iUPD_USR_BSC03;
        if(CloudDataStruct.CloudData_45.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_45_UPD_USR_BSC.sDB_Cmd[2], CloudDataStruct.CloudData_45.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_45_UPD_USR_BSC.sDB_Cmd[3], CloudDataStruct.CloudData_45.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SM1")){ iret = 0; }  //Success
            else{ iret = 1; }
        }
        return iret;
    }

    //46
    public int iCloudCmd_Workout_Add(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage){
        // -1 : Server Error ;
        //  0 : success
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iINS_WRK_ITV_SET04;
        if(CloudDataStruct.CloudData_46.Set_input(sMCH_TYP,sWRK_ID,sWRK_NAM,list_Stage)){
            CloudApi_API_SetCommand(inum, Cloud_46_INS_WRK_ITV_SET.sDB_Cmd[2], CloudDataStruct.CloudData_46.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_46_INS_WRK_ITV_SET.sDB_Cmd[3], CloudDataStruct.CloudData_46.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("TM1")){ iret = 0; }  //Success
            else if(response.getMsg().equals("SQ1")){ iret = 1; }  //Same ID
            else{ iret = 2; }
        }
        return iret;
    }

    //47
    public int iCloudCmd_GymClass_Add(Calendar cClassStartTime, String sClsID){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Already Added
        //  2 : Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iINS_BKG_CLS01;
        if(CloudDataStruct.CloudData_47.Set_input(cClassStartTime,sClsID)){
            CloudApi_API_SetCommand(inum, Cloud_47_INS_BKG_CLS.sDB_Cmd[2], CloudDataStruct.CloudData_47.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_47_INS_BKG_CLS.sDB_Cmd[3], CloudDataStruct.CloudData_47.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("TM1")){ iret = 0; }  //Success
            else if(response.getMsg().equals("SM1")){ iret = 1; }  //Already Added
            else{ iret = 2; }
        }
        return iret;
    }

    //48
    public int iCloudCmd_Workout_Update(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage){
        // -1 : Server Error ;
        //  0 : success
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iUPD_WRK_ITV_SET02;
        if(CloudDataStruct.CloudData_48.Set_input(sMCH_TYP,sWRK_ID,sWRK_NAM,list_Stage)){
            CloudApi_API_SetCommand(inum, Cloud_48_UPD_WRK_ITV_SET.sDB_Cmd[2], CloudDataStruct.CloudData_48.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_48_UPD_WRK_ITV_SET.sDB_Cmd[3], CloudDataStruct.CloudData_48.sdata_out);
            if(response.getMsg() == null){ iret = -1; }
            else if(response.getMsg().equals("SM1")){ iret = 0; }  //Success
            else if(response.getMsg().equals("SM0")){ iret = 1; }  //Fail
            else{ iret = 2; }
        }
        return iret;
    }

    //50
    public int iCloudCmd_GoalClose_Add(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Add Fail
        //  2 : Update Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDB_EXC_GOL_TGE02;
        if (CloudDataStruct.CloudData_50.Set_input(goal_Close)){
            CloudApi_API_SetCommand(inum, Cloud_50_DB_EXC_GOL_TGE.sDB_Cmd[2], CloudDataStruct.CloudData_50.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_50_DB_EXC_GOL_TGE.sDB_Cmd[3], CloudDataStruct.CloudData_50.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("FM1")){ iret = 0; } //Success
                else if(response.getMsg().equals("FM0")){ iret = 1; } //Fail
                else if(response.getMsg().equals("TM1")){ iret = 0; } //Fail
                else if(response.getMsg().equals("TM0")){ iret = 2; } //Fail
                else{ iret = 3; }
            }
        }
        return iret;
    }

    //59
    public int iCloudCmd_GetTargetGoalClose(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose){
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_EXC_GOL_TGE01;
        if(CloudDataStruct.CloudData_59.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_59_GET_EXC_GOL_TGE.sDB_Cmd[2], CloudDataStruct.CloudData_59.sdata_in);
            response=CloudApi_59_GetTargetGoalClose_get(inum, Cloud_59_GET_EXC_GOL_TGE.sDB_Cmd[3], list_CloudTargetGoalClose);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; }  //Success
                else{ iret = 1; }  //Fail
                if(iret == 0){ }
            }
        }
        return iret;
    }

    //60
    public int iCloudCmd_GetExerciseDate(Calendar calStart, Calendar calEnd, ArrayList<String> list_sDate){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_END_WRK_FQC01;
        if( CloudDataStruct.CloudData_60.Set_input(calStart, calEnd)){
            CloudApi_API_SetCommand(inum, Cloud_60_GET_END_WRK_FQC.sDB_Cmd[2], CloudDataStruct.CloudData_60.sdata_in);
            response=CloudApi_60_GetExerciseFreqInfo_get(inum, Cloud_60_GET_END_WRK_FQC.sDB_Cmd[3], CloudDataStruct.CloudData_60.sdata_out, list_sDate);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; } //Fail
                else { iret = 2; }
            }
        }
        return iret;
    }

    //62
    public int iCloudCmd_GetThirdPartyApps(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_TRD_TKN03;
        if( CloudDataStruct.CloudData_62.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_62_GET_TRD_TKN.sDB_Cmd[2], CloudDataStruct.CloudData_62.sdata_in);
            response=CloudApi_62_GetThirdPartyApps_get(inum, Cloud_62_GET_TRD_TKN.sDB_Cmd[3]);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; } //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //63
    public int iCloudCmd_ForgotPassword(String sEmil){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Account Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iSEN_MAL_PW02;
        if(CloudDataStruct.CloudData_63.Set_input(sEmil)){
            CloudApi_API_SetCommand(inum, Cloud_63_SEN_MAL_PW.sDB_Cmd[2], CloudDataStruct.CloudData_63.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_63_SEN_MAL_PW.sDB_Cmd[3], CloudDataStruct.CloudData_63.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("TQ1")){ iret = 1; } //Account Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //65
    public int iCloudCmd_GetThirdPartyApps_Enable(){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Fail
        //  2 : Other Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_TRD_PTY01;
        Log.e(TAG, "iCloudCmd_GetThirdPartyApps_Enable");
        if(CloudDataStruct.CloudData_65.Set_input()){
            CloudApi_API_SetCommand(inum, Cloud_65_GET_TRD_PTY.sDB_Cmd[2], CloudDataStruct.CloudData_65.sdata_in);
            response=CloudApi_65_GetThirdPartyApps_Enable_get(inum, Cloud_65_GET_TRD_PTY.sDB_Cmd[3]);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; } //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //67
    public int iCloudCmd_GoalClose_Delete(String sGolSeq){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Delete Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iDEL_EXC_GOL_TGE01;
        if(CloudDataStruct.CloudData_67.Set_input(sGolSeq)){
            CloudApi_API_SetCommand(inum, Cloud_67_DEL_EXC_GOL_TGE.sDB_Cmd[2], CloudDataStruct.CloudData_67.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_67_DEL_EXC_GOL_TGE.sDB_Cmd[3], CloudDataStruct.CloudData_67.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; } //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }

    //69
    public int iCloudCmd_Reg_CheckUserExist(String sUsrId){
        // -1 : Server Error ;
        //  0 : success
        //  1 : Delete Fail
        int iret=-1;
        CloudDataStruct.ServerResponse response;
        int inum=cloudglobal.iGET_ACN_DAT01;
        if(CloudDataStruct.CloudData_69.Set_input(sUsrId)){
            CloudApi_API_SetCommand(inum, Cloud_69_GET_ACN_DAT.sDB_Cmd[2], CloudDataStruct.CloudData_69.sdata_in);
            response=CloudApi_API_GetCommand(inum, Cloud_69_GET_ACN_DAT.sDB_Cmd[3], CloudDataStruct.CloudData_69.sdata_out);
            if(response.isSuccess()){
                if(response.getMsg() == null){ iret = -1; }
                else if(response.getMsg().equals("SQ1")){ iret = 0; } //Success
                else if(response.getMsg().equals("SQ0")){ iret = 1; } //Fail
                else{ iret = 2; }
            }
        }
        return iret;
    }
}
