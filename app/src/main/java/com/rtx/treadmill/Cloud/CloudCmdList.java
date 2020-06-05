package com.rtx.treadmill.Cloud;

import android.util.Log;

import com.retonix.circlecloud.CloudCommand;
import com.retonix.circlecloud.Cloud_10_GET_WRK_NAM;
import com.retonix.circlecloud.Cloud_14_GET_BDY_HIS;
import com.retonix.circlecloud.Cloud_24_GET_EXC_GOL;
import com.retonix.circlecloud.Cloud_29_GET_BLT_BOD_LST;
import com.retonix.circlecloud.Cloud_31_WEB_GET_CLS_SUC;
import com.retonix.circlecloud.Cloud_32_GET_CLS_BSC;
import com.retonix.circlecloud.Cloud_39_GET_GYM_BKI;
import com.retonix.circlecloud.Cloud_59_GET_EXC_GOL_TGE;
import com.retonix.circlecloud.Cloud_62_GET_TRD_TKN;
import com.retonix.circlecloud.Cloud_65_GET_TRD_PTY;
import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by jerry on 2016/12/27.
 */

public class CloudCmdList extends CloudCmdFunc
{
    private static String TAG = "Jerry===CloudCmd";
    private static boolean DEBUG = false;

    private static boolean bCmd_create = false;
    private static CloudCommand cCmd_array[] = new CloudCommand[cloudglobal.iCloud_CMD_MAX];

    private final int OTHER_ERROR = 99;

    public CloudCmdList()
    {
        if(DEBUG) Log.d(TAG, "bCmd_create=" + bCmd_create);
        if(bCmd_create == false) {
            bCmd_create = true;
            cloudglobal.DEBUG = Rtx_Debug.bGetCloud_MessageEnable();
            cloudglobal.create_circlecloud_command();
        }
    }

    private void vCmd_null( int icmd )
    {
        cCmd_array[icmd] = null;

        return ;
    }

    private boolean check_is_null(cloudglobal.circlecloud_command cCmd, String[] skey, String[] sdata)
    {
        boolean bret = false;

        if(cCmd == null || skey == null || sdata ==null)
        {
            bret = true;
        }

        return bret;
    }

    private boolean check_is_null(CloudCommand cCmd, String[] skey, String[] sdata)
    {
        boolean bret = false;

        if(cCmd == null || skey == null || sdata ==null)
        {
            bret = true;
        }

        return bret;
    }

    private boolean check_is_null(CloudCommand cCmd, String[] skey, ArrayList<String[]> sdata)
    {
        boolean bret = false;

        if(cCmd == null || skey == null || sdata ==null)
        {
            bret = true;
        }

        return bret;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //  command
    //      public void                             CloudApi_API_SetCommand(int icmd, String[] skey, String[] sdata)
    //      public CloudDataStruct.ServerResponse   CloudApi_API_GetCommand(int icmd, String[] skey, String[] sdata)
    //
    //05    public CloudDataStruct.ServerResponse   CloudApi_05_SetBodyIndex_get()
    //14    public CloudDataStruct.ServerResponse   CloudApi_14_GetBodyIndexHistory_get()
    //15    public CloudDataStruct.ServerResponse   CloudApi_15_GetBodyIndexCurrent_get()
    //24    public CloudDataStruct.ServerResponse   CloudApi_24_GetTargetGoal_get(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal)
    //44    public CloudDataStruct.ServerResponse   CloudApi_44_Registration_get()
    //45    public CloudDataStruct.ServerResponse   CloudApi_45_UpdateUserInfo_get()
    //60    public CloudDataStruct.ServerResponse   CloudApi_60_GetExerciseFreqInfo_get(String[] array_Str)
    //60    public CloudDataStruct.ServerResponse   CloudApi_62_GetExerciseFreqInfo_get(String[] array_Str)
    //
    //62    public CloudDataStruct.ServerResponse CloudApi_62_GetThirdPartyApps_get(int icmd, String[] skey)
    //65    public CloudDataStruct.ServerResponse CloudApi_65_GetThirdPartyApps_Enable_get(int icmd, String[] skey)
    //
    //////////////////////////////////////////////////////////////////////////////////////////
    //general send command
    public void CloudApi_API_SetCommand(int icmd, String[] skey, String[] sdata)
    {
        cloudglobal.circlecloud_command tm = cloudglobal.circlecloud_command_tree.get(icmd);
        if(!check_is_null(tm, skey, sdata)) {
            for (int iLoop = 0; iLoop < sdata.length; iLoop++) {
                if (DEBUG) Log.e(TAG, "CloudApi_API_SetCommand[" + iLoop + "]=" + sdata[iLoop]);
            }

            if (cCmd_array[icmd] == null) {
                cCmd_array[icmd] = new CloudCommand(tm, skey, sdata);
                cCmd_array[icmd].start();
            }
        }
    }
    //general get command
    public CloudDataStruct.ServerResponse CloudApi_API_GetCommand(int icmd, String[] skey, String[] sdata)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];

        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        if(!check_is_null(Ccmd, skey_arry, sdata)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0)
            {
                if(skey_arry[0] != null)
                {
                    for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                    {
                        sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                    }

                    for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                        if (sData[iLoop] == null) {
                            response.setResult(-1);
                            break;
                        }
                        if (sData[iLoop].length <= 0) {
                            response.setResult(-1);
                            break;
                        }

                        if (sData[iLoop].length < iLoop_max) {
                            iLoop_max = sData[iLoop].length;
                        }
                    }

                    if (response.getResult() == 0) {
                        for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                            for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                                sdata[iLoop] = sData[iLoop][iLoop_array];
                            }
                        }
                    }
                }
            }
        }
        vCmd_clear(Ccmd);
        vCmd_null(inum);
        return response;
    }

    //general get command
    public CloudDataStruct.ServerResponse CloudApi_API_GetCommand(int icmd, String[] skey, ArrayList<String[]> slist){
        int inum = icmd;
        String[] skey_arry = skey;
        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        if(!check_is_null(Ccmd, skey_arry, slist)) {
            iRunnable_ReceiveData(Ccmd, response);
            if(response.getResult() == 0){
                if(skey_arry[0] != null){
                    for(iLoop = 0; iLoop < skey_arry.length; iLoop++){
                        sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                    }
                    for(iLoop = 0; iLoop < skey_arry.length; iLoop++){
                        if (sData[iLoop] == null){
                            response.setResult(-1);
                            break;
                        }
                        if (sData[iLoop].length <= 0){
                            response.setResult(-1);
                            break;
                        }
                        if(sData[iLoop].length < iLoop_max){
                            iLoop_max = sData[iLoop].length;
                        }
                    }

                    if (response.getResult() == 0) {
                        for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                            String[] sdata = new String[skey_arry.length];
                            for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                                sdata[iLoop] = sData[iLoop][iLoop_array];
                                Log.e("EndersChen","Cloud33 SDATA["+iLoop+"]["+sdata[iLoop]+"]");
                            }
                            slist.add(sdata);
                        }
                    }
                }
            }
        }
        vCmd_clear(Ccmd);
        vCmd_null(inum);
        return response;
    }

    ////////////////////////////////////////////////////////////////////
    //05
    public CloudDataStruct.ServerResponse CloudApi_05_SetBodyIndex_get(int icmd){
        int inum = icmd;
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        iRunnable_ReceiveData(Ccmd,response);
        vCmd_clear(Ccmd);
        vCmd_null(inum);
        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //10
    public CloudDataStruct.ServerResponse CloudApi_10_GetWorkoutName_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_WORKOUT_NAME> list_CloudWorkoutName){
        int inum = icmd;
        String[] skey_arry = skey;
        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;
        list_CloudWorkoutName.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                    {
                        CloudDataStruct.CLOUD_WORKOUT_NAME cloundWorkoutName = new CloudDataStruct.CLOUD_WORKOUT_NAME();

                        cloundWorkoutName.sWRK_ID       = sData[Cloud_10_GET_WRK_NAM.Output.WRK_ID][iLoop_array];
                        cloundWorkoutName.sMCH_TYP      = sData[Cloud_10_GET_WRK_NAM.Output.MCH_TYP][iLoop_array];
                        cloundWorkoutName.sWRK_NAM      = sData[Cloud_10_GET_WRK_NAM.Output.WRK_NAM][iLoop_array];

                        list_CloudWorkoutName.add(cloundWorkoutName);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    ////////////////////////////////////////////////////////////////////
    //14
    public CloudDataStruct.ServerResponse CloudApi_14_GetBodyIndexHistory_get(int icmd, String[] skey)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        TreeMap<String, String[]> mHistTree = CloudDataStruct.BodyIndexData_14.tree_clound_cmd14_result;

        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        skey_arry[0] = CloudDataStruct.BodyIndexData_14.sdata_in[Cloud_14_GET_BDY_HIS.Input.rCOL];
        mHistTree.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        String[] Slasthis = new String[Cloud_14_GET_BDY_HIS.sDB_Cmd[3].length];
                        if(DEBUG) Log.e(TAG, "iLoop_array=" + iLoop_array);

                        for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            if(DEBUG) Log.e(TAG, "iLoop=" + iLoop);
                            Slasthis[iLoop] = sData[iLoop][iLoop_array];
                            if(DEBUG) Log.e(TAG, "CloudApi_14_get sdata[" + iLoop + "]=" + Slasthis[iLoop]);
                        }
                        //String sdate = Rtx_Calendar.s_trans_DateTime_Str(0, "M/dd/yyyy", "yyyy-MM-dd HH:mm:ss", Slasthis[Cloud_14_GET_BDY_HIS.Output.MSR_DT], 0, 0);
                        String sdate = Slasthis[Cloud_14_GET_BDY_HIS.Output.MSR_DT];
                        if (sdate != null) {
                            mHistTree.put(sdate, Slasthis);
                        }
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    ////////////////////////////////////////////////////////////////////
    //15
    public CloudDataStruct.ServerResponse CloudApi_15_GetBodyIndexCurrent_get(int icmd, String[] skey, String[] sdata)
    {
        int inum = icmd;
        String[] skey_arry = skey;
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();

        String[][] sData = new String[skey_arry.length][];
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        if(!check_is_null(Ccmd, skey_arry, sdata)) {
            iRunnable_ReceiveData(Ccmd, response);
            if(response.getResult() == 0) {
                for(iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for(iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if(sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if(sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }
                    if(sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if(response.getResult() == 0){
                    for(iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        for(iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            sdata[iLoop] = sData[iLoop][iLoop_array];
                        }
                        //Update to Cloud API 05
                        CloudDataStruct.BodyIndexData_05.v_BodyIndex_update_all();
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //24
    public CloudDataStruct.ServerResponse CloudApi_24_GetTargetGoal_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        list_CloudTargetGoal.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
//                        for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            CloudDataStruct.CLOUD_TATGET_GOAL cloundTargetGoal = new CloudDataStruct.CLOUD_TATGET_GOAL();

                            cloundTargetGoal.sGol_Seq       = sData[Cloud_24_GET_EXC_GOL.Output.GOL_SEQ][iLoop_array];
                            cloundTargetGoal.sGol_Item      = sData[Cloud_24_GET_EXC_GOL.Output.GOL_ITM][iLoop_array];
                            cloundTargetGoal.sGol_Type      = sData[Cloud_24_GET_EXC_GOL.Output.GOL_TYP][iLoop_array];
                            cloundTargetGoal.sGol_Val       = sData[Cloud_24_GET_EXC_GOL.Output.GOL_DAT_1][iLoop_array];
                            cloundTargetGoal.sGol_Duration  = sData[Cloud_24_GET_EXC_GOL.Output.GOL_DAT_2][iLoop_array];
                            cloundTargetGoal.sStartDate     = sData[Cloud_24_GET_EXC_GOL.Output.GOL_STR_DT][iLoop_array];
                            cloundTargetGoal.sUsr_Wei       = sData[Cloud_24_GET_EXC_GOL.Output.USR_WEI][iLoop_array];
                            cloundTargetGoal.sGol_Sts       = sData[Cloud_24_GET_EXC_GOL.Output.GOL_STS][iLoop_array];
                            cloundTargetGoal.sGol_Tag       = sData[Cloud_24_GET_EXC_GOL.Output.GOL_TAG][iLoop_array];

//                        }
                        list_CloudTargetGoal.add(cloundTargetGoal);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //29
    public CloudDataStruct.ServerResponse CloudApi_29_GetBulletinList_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        list_CloudBulletinInfo.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry))
        {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0)
            {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    if (sData[iLoop] == null)
                    {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0)
                    {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0)
                {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                    {
                        CloudDataStruct.CLOUD_BULLETIN_INFO cloundBulletinInfo= new CloudDataStruct.CLOUD_BULLETIN_INFO();

                        cloundBulletinInfo.sBLT_ID       = sData[Cloud_29_GET_BLT_BOD_LST.Output.BLT_ID][iLoop_array];
                        cloundBulletinInfo.sBLT_TIL      = sData[Cloud_29_GET_BLT_BOD_LST.Output.BLT_TIL][iLoop_array];
                        cloundBulletinInfo.sBLT_AUC_DT   = sData[Cloud_29_GET_BLT_BOD_LST.Output.BLT_AUC_DT][iLoop_array];
                        cloundBulletinInfo.sH_FLG        = sData[Cloud_29_GET_BLT_BOD_LST.Output.H_FLG][iLoop_array];

                        list_CloudBulletinInfo.add(cloundBulletinInfo);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //31
    public CloudDataStruct.ServerResponse CloudApi_31_GetGymClassList_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_GYM_CLASS_INFO> list_CloudGymClassInfo)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        //list_CloudGymClassInfo.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry))
        {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0)
            {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    if (sData[iLoop] == null)
                    {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0)
                    {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0)
                {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                    {
                        CloudDataStruct.CLOUD_GYM_CLASS_INFO cloundGymClassInfo = new CloudDataStruct.CLOUD_GYM_CLASS_INFO();

                        cloundGymClassInfo.sCLS_SUC_DT       = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_SUC_DT][iLoop_array];
                        cloundGymClassInfo.sCLS_ID      = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_ID][iLoop_array];
                        cloundGymClassInfo.sCLS_NAME   = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_NAM][iLoop_array];
                        cloundGymClassInfo.sCLS_STR_TIM   = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_STR_TIM][iLoop_array];
                        cloundGymClassInfo.sCLS_END_TIM   = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_END_TIM][iLoop_array];
                        cloundGymClassInfo.sCLS_PHO   = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_PHO][iLoop_array];
                        cloundGymClassInfo.sCLS_TCH_NAM = sData[Cloud_31_WEB_GET_CLS_SUC.Output.CLS_TCH_NAM][iLoop_array];

                        {
                            cloundGymClassInfo.sUser_CLS_SUC_DT = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd" , "yyyy-MM-dd HH:mm:ss", cloundGymClassInfo.sCLS_SUC_DT + " " + cloundGymClassInfo.sCLS_STR_TIM, 0, 0);
                            cloundGymClassInfo.sUser_CLS_STR_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymClassInfo.sCLS_SUC_DT + " " + cloundGymClassInfo.sCLS_STR_TIM, 0, 0);
                            cloundGymClassInfo.sUser_CLS_END_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymClassInfo.sCLS_SUC_DT + " " + cloundGymClassInfo.sCLS_END_TIM, 0, 0);

                            cloundGymClassInfo.cUser_CLS_STR = Rtx_Calendar.cStr2Calendar(cloundGymClassInfo.sUser_CLS_SUC_DT + " " + cloundGymClassInfo.sUser_CLS_STR_TIM ,"yyyy-MM-dd HH:mm:ss");
                        }

                        list_CloudGymClassInfo.add(cloundGymClassInfo);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //32
    public CloudDataStruct.ServerResponse CloudApi_32_GetGymClassDetailInfo_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO> list_CloudClassDetailInfo)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[] sPhotoArray;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        int iLoopPhoto;
        int iLoopPhotoMax ;

        list_CloudClassDetailInfo.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry))
        {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0)
            {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    if (sData[iLoop] == null)
                    {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0)
                    {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0)
                {
                  //  for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                  //  {
                  //      CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO cloundGymClassDetailInfo = new CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO();

                  //      cloundGymClassDetailInfo.sCLS_DEL       = sData[Cloud_32_GET_CLS_BSC.Output.CLS_DEL][iLoop_array];
                  //      cloundGymClassDetailInfo.sCLS_ID        = sData[Cloud_32_GET_CLS_BSC.Output.CLS_ID][iLoop_array];
                  //      cloundGymClassDetailInfo.sCLS_NAM       = sData[Cloud_32_GET_CLS_BSC.Output.CLS_NAM][iLoop_array];
                  //      cloundGymClassDetailInfo.sCLS_TCH_NAM   = sData[Cloud_32_GET_CLS_BSC.Output.CLS_TCH_NAM][iLoop_array];
                  //      cloundGymClassDetailInfo.sCLS_STR_TIM   = sData[Cloud_32_GET_CLS_BSC.Output.CLS_STR_TIM][iLoop_array];
                  //      cloundGymClassDetailInfo.sCLS_END_TIM   = sData[Cloud_32_GET_CLS_BSC.Output.CLS_END_TIM][iLoop_array];
                   //     cloundGymClassDetailInfo.sCLS_PHO       = sData[Cloud_32_GET_CLS_BSC.Output.CLS_PHO][iLoop_array];
                   //     cloundGymClassDetailInfo.sCLS_UTB       = sData[Cloud_32_GET_CLS_BSC.Output.CLS_UTB][iLoop_array];
                   //     cloundGymClassDetailInfo.sCLS_BKG_MAX   = sData[Cloud_32_GET_CLS_BSC.Output.CLS_BKG_MAX][iLoop_array];
                   //     cloundGymClassDetailInfo.sCLS_CTT       = sData[Cloud_32_GET_CLS_BSC.Output.CLS_CTT][iLoop_array];

                   //     cloundGymClassDetailInfo.sUser_CLS_STR_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymClassDetailInfo.sCLS_STR_TIM, 0, 0);
                   //     cloundGymClassDetailInfo.sUser_CLS_END_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymClassDetailInfo.sCLS_END_TIM, 0, 0);

                    //    list_CloudClassDetailInfo.add(cloundGymClassDetailInfo);
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        sPhotoArray = sData[Cloud_32_GET_CLS_BSC.Output.CLS_PHO][iLoop_array].split(",http");
                        iLoopPhotoMax = sPhotoArray.length;
                        for (iLoopPhoto = 0; iLoopPhoto < iLoopPhotoMax; iLoopPhoto++)
                        {
                            CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO cloundGymClassDetailInfo = new CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO();

                            cloundGymClassDetailInfo.sCLS_DEL = sData[Cloud_32_GET_CLS_BSC.Output.CLS_DEL][iLoop_array];
                            cloundGymClassDetailInfo.sCLS_ID = sData[Cloud_32_GET_CLS_BSC.Output.CLS_ID][iLoop_array];
                            cloundGymClassDetailInfo.sCLS_NAM = sData[Cloud_32_GET_CLS_BSC.Output.CLS_NAM][iLoop_array];
                            cloundGymClassDetailInfo.sCLS_TCH_NAM = sData[Cloud_32_GET_CLS_BSC.Output.CLS_TCH_NAM][iLoop_array];
                            cloundGymClassDetailInfo.sCLS_STR_TIM = sData[Cloud_32_GET_CLS_BSC.Output.CLS_STR_TIM][iLoop_array];
                            cloundGymClassDetailInfo.sCLS_END_TIM = sData[Cloud_32_GET_CLS_BSC.Output.CLS_END_TIM][iLoop_array];
                            if(iLoopPhoto == 0)
                            {
                                cloundGymClassDetailInfo.sCLS_PHO = sPhotoArray[iLoopPhoto];
                            }
                            else
                                {
                                    cloundGymClassDetailInfo.sCLS_PHO = "http" + sPhotoArray[iLoopPhoto];
                                }
                                cloundGymClassDetailInfo.sCLS_UTB = sData[Cloud_32_GET_CLS_BSC.Output.CLS_UTB][iLoop_array];
                                cloundGymClassDetailInfo.sCLS_BKG_MAX = sData[Cloud_32_GET_CLS_BSC.Output.CLS_BKG_MAX][iLoop_array];
                                cloundGymClassDetailInfo.sCLS_CTT = sData[Cloud_32_GET_CLS_BSC.Output.CLS_CTT][iLoop_array];

                                cloundGymClassDetailInfo.sUser_CLS_STR_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", cloundGymClassDetailInfo.sCLS_STR_TIM, 0, 0);
                                cloundGymClassDetailInfo.sUser_CLS_END_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", cloundGymClassDetailInfo.sCLS_END_TIM, 0, 0);

                                list_CloudClassDetailInfo.add(cloundGymClassDetailInfo);
                        }
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //39
    public CloudDataStruct.ServerResponse CloudApi_39_GetGymMyClassList_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudGymMyClassInfo)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        //list_CloudGymMyClassInfo.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry))
        {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0)
            {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++)
                {
                    if (sData[iLoop] == null)
                    {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0)
                    {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0)
                {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                    {
                        CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO cloundGymMyClassInfo = new CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO();

                        cloundGymMyClassInfo.sCLS_DEL       = sData[Cloud_39_GET_GYM_BKI.Output.CLS_DEL][iLoop_array];
                        cloundGymMyClassInfo.sBKG_SEQ      = sData[Cloud_39_GET_GYM_BKI.Output.BKG_SEQ][iLoop_array];
                        cloundGymMyClassInfo.sCLS_NAME   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_NAM][iLoop_array];
                        cloundGymMyClassInfo.sCLS_SUC_DT   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_SUC_DT][iLoop_array];
                        cloundGymMyClassInfo.sCLS_STR_TIM   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_STR_TIM][iLoop_array];
                        cloundGymMyClassInfo.sCLS_END_TIM   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_END_TIM][iLoop_array];
                        cloundGymMyClassInfo.sCLS_PHO   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_PHO][iLoop_array];
                        cloundGymMyClassInfo.sCLS_ID   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_ID][iLoop_array];
                        cloundGymMyClassInfo.sCLS_TCH_NAM   = sData[Cloud_39_GET_GYM_BKI.Output.CLS_TCH_NAM][iLoop_array];
                        cloundGymMyClassInfo.sO_CLS_STR_TIM   = sData[Cloud_39_GET_GYM_BKI.Output.O_CLS_STR_TIM][iLoop_array];
                        cloundGymMyClassInfo.sREAD_TAG   = sData[Cloud_39_GET_GYM_BKI.Output.READ_TAG][iLoop_array];

                        if(list_CloudGymMyClassInfo != null)    //Check data is exist
                        {
                            int iSize = list_CloudGymMyClassInfo.size();
                            int iIndex = 0;
                            CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO temp_cloundGymMyClassInfo;
                            boolean bIsSame = false;

                            for( ; iIndex < iSize ; iIndex++)
                            {
                                temp_cloundGymMyClassInfo = list_CloudGymMyClassInfo.get(iIndex);
                                if(     temp_cloundGymMyClassInfo.sCLS_ID.equals(cloundGymMyClassInfo.sCLS_ID) &&
                                        temp_cloundGymMyClassInfo.sBKG_SEQ.equals(cloundGymMyClassInfo.sBKG_SEQ) &&
                                        temp_cloundGymMyClassInfo.sCLS_NAME.equals(cloundGymMyClassInfo.sCLS_NAME))
                                {
                                    bIsSame = true;
                                    break;
                                }
                            }

                            if(!bIsSame)
                            {
                                {
                                    cloundGymMyClassInfo.sUser_CLS_SUC_DT = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd" , "yyyy-MM-dd HH:mm:ss", cloundGymMyClassInfo.sCLS_SUC_DT + " " + cloundGymMyClassInfo.sCLS_STR_TIM, 0, 0);
                                    cloundGymMyClassInfo.sUser_CLS_STR_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymMyClassInfo.sCLS_SUC_DT + " " + cloundGymMyClassInfo.sCLS_STR_TIM, 0, 0);
                                    cloundGymMyClassInfo.sUser_CLS_END_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "HH:mm:ss" , "yyyy-MM-dd HH:mm:ss", cloundGymMyClassInfo.sCLS_SUC_DT + " " + cloundGymMyClassInfo.sCLS_END_TIM, 0, 0);

                                    cloundGymMyClassInfo.cUser_CLS_STR = Rtx_Calendar.cStr2Calendar(cloundGymMyClassInfo.sUser_CLS_SUC_DT + " " + cloundGymMyClassInfo.sUser_CLS_STR_TIM ,"yyyy-MM-dd HH:mm:ss");
                                }

                                list_CloudGymMyClassInfo.add(cloundGymMyClassInfo);
                            }
                        }
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //59
    public CloudDataStruct.ServerResponse CloudApi_59_GetTargetGoalClose_get(int icmd, String[] skey, ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        list_CloudTargetGoalClose.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {

            iRunnable_ReceiveData(Ccmd, response);

            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++)
                    {
                        CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE cloundTargetGoalClose = new CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE();

                        cloundTargetGoalClose.sGOL_SEQ     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_SEQ][iLoop_array];
                        cloundTargetGoalClose.sUSR_SEQ     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.USR_SEQ][iLoop_array];
                        cloundTargetGoalClose.sGOL_ITM     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_ITM][iLoop_array];
                        cloundTargetGoalClose.sGOL_TYP     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_TYP][iLoop_array];
                        cloundTargetGoalClose.sGOL_DAT_1   = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_DAT_1][iLoop_array];
                        cloundTargetGoalClose.sGOL_DAT_2   = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_DAT_2][iLoop_array];
                        cloundTargetGoalClose.sGOL_STR_DT  = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_STR_DT][iLoop_array];
                        cloundTargetGoalClose.sUSR_WEI     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.USR_WEI][iLoop_array];
                        cloundTargetGoalClose.sNOW_WEI     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.NOW_WEI][iLoop_array];
                        cloundTargetGoalClose.sGOL_WEI     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_WEI][iLoop_array];
                        cloundTargetGoalClose.sGOL_STS     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_STS][iLoop_array];
                        cloundTargetGoalClose.sGOL_RST     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_RST][iLoop_array];
                        cloundTargetGoalClose.sEND_TIM     = sData[Cloud_59_GET_EXC_GOL_TGE.Output.END_TIM][iLoop_array];
                        cloundTargetGoalClose.sGOL_CLS_LVL = sData[Cloud_59_GET_EXC_GOL_TGE.Output.GOL_CLS_LVL][iLoop_array];

                        list_CloudTargetGoalClose.add(cloundTargetGoalClose);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //60
    public CloudDataStruct.ServerResponse CloudApi_60_GetExerciseFreqInfo_get(int icmd, String[] skey, String[] sdata, ArrayList<String> list_sDate)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];

        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        if(!check_is_null(Ccmd, skey_arry, sdata)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        String stemp = new String();
                        for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            sdata[iLoop] = sData[iLoop][iLoop_array];
                            stemp = sdata[iLoop];
                        }
                        list_sDate.add(stemp);
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }


    ////////////////////////////////////////////////////////////////////
    //62
    public CloudDataStruct.ServerResponse CloudApi_62_GetThirdPartyApps_get(int icmd, String[] skey)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        TreeMap<Integer, String[]> mAppsTree = CloudDataStruct.CloudData_62.tree_clound_cmd62_result;

        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        mAppsTree.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        String[] Slasthis = new String[Cloud_62_GET_TRD_TKN.sDB_Cmd[3].length];
                        if (DEBUG) Log.e(TAG, "iLoop_array=" + iLoop_array);

                        for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            if (DEBUG) Log.e(TAG, "iLoop=" + iLoop);
                            Slasthis[iLoop] = sData[iLoop][iLoop_array];
                            if (DEBUG) Log.e(TAG, "CloudApi_62_get sdata[" + iLoop + "]=" + Slasthis[iLoop]);
                        }
                        int iapp = Rtx_TranslateValue.iString2Int(Slasthis[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_APP]);
                        if (iapp != 0) {
                            mAppsTree.put(iapp, Slasthis);
                        }
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    ////////////////////////////////////////////////////////////////////
    //65
    public CloudDataStruct.ServerResponse CloudApi_65_GetThirdPartyApps_Enable_get(int icmd, String[] skey)
    {
        int inum = icmd;
        String[] skey_arry = skey;

        String[][] sData = new String[skey_arry.length][];
        TreeMap<Integer, String[]> mAppsTree = CloudDataStruct.CloudData_65.tree_clound_cmd65_result;

        CloudCommand Ccmd = cCmd_array[inum];
        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        int iLoop;
        int iLoop_array;
        int iLoop_max = 99999;

        mAppsTree.clear();

        if(!check_is_null(Ccmd, skey_arry, skey_arry)) {
            iRunnable_ReceiveData(Ccmd, response);
            if (response.getResult() == 0) {
                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    sData[iLoop] = Ccmd.getKeyword(skey_arry[iLoop]);
                }

                for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                    if (sData[iLoop] == null) {
                        response.setResult(-1);
                        break;
                    }
                    if (sData[iLoop].length <= 0) {
                        response.setResult(-1);
                        break;
                    }

                    if (sData[iLoop].length < iLoop_max) {
                        iLoop_max = sData[iLoop].length;
                    }
                }

                if (response.getResult() == 0) {
                    for (iLoop_array = 0; iLoop_array < iLoop_max; iLoop_array++) {
                        String[] Slasthis = new String[Cloud_65_GET_TRD_PTY.sDB_Cmd[3].length];
                        if (DEBUG) Log.e(TAG, "iLoop_array=" + iLoop_array);

                        for (iLoop = 0; iLoop < skey_arry.length; iLoop++) {
                            if (DEBUG) Log.e(TAG, "iLoop=" + iLoop);
                            Slasthis[iLoop] = sData[iLoop][iLoop_array];
                            if (DEBUG) Log.e(TAG, "CloudApi_65_get sdata[" + iLoop + "]=" + Slasthis[iLoop]);
                        }
                        int ilkid = Rtx_TranslateValue.iString2Int(Slasthis[Cloud_65_GET_TRD_PTY.Output.LK_ID]);
                        if (ilkid != 0) {
                            mAppsTree.put(ilkid, Slasthis);
                        }
                    }
                }
            }
        }

        vCmd_clear(Ccmd);
        vCmd_null(inum);

        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //67
    public CloudDataStruct.ServerResponse CloudApi_67_TargetClose_Delete()
    {
        int inum = cloudglobal.iDEL_EXC_GOL_TGE01;
        CloudCommand Ccmd = cCmd_array[inum];

        CloudDataStruct.ServerResponse response = new CloudDataStruct.ServerResponse();
        iRunnable_ReceiveData(Ccmd,response);

        Ccmd.clearData();
        cCmd_array[inum] = null;

        return response;
    }
}
