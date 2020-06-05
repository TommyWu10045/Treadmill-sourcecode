package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.os.Build;
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
import com.rtx.treadmill.RtxMainFunction.BodyManager.BodyManagerFunc;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.utils.MyLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBDY_FAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBMI;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBMR;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBON_MAS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fECR_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fITR_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fMinerals;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fOBY_DGE;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fProtein;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fTAL_BDY_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fVCL_FAT_RTG;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fWIT_HI_RAO;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fWeight;

/**
 * Created by jerry on 2017/6/23.
 */

public class CloudDataStruct{
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    /***** Response *****/
    public static class ServerResponse{
        public boolean  bSuccess;
        public int      iResult;
        public String   sMsg;
        public String   sCode;

        public ServerResponse(){
            iResult = -1;
            sMsg = "";
            sCode = "";
            bSuccess = false;
        }

        public void setResult(int iVal){
            iResult = iVal;
            bSuccess = bCheckResult(iResult);
        }

        public void setCode(String sValue)
        {
            sCode = sValue;
        }

        public void setMsg(String msg)
        {
            sMsg = msg;
        }

        private boolean bCheckResult(int iVal){
            boolean bFlag = false;

            if((iVal == 0) || (iVal == 3)){
                bFlag = true;
            }

            return bFlag;
        }

        public int getResult()
        {
            return iResult;
        }

        public boolean isSuccess()
        {
            return bSuccess;
        }

        public String getMsg()
        {
            return sMsg;
        }

        public String getCode()
        {
            return sCode;
        }
    }

    /***** Reg *****/
    public static class Registration{
        public String    sEmail = "";
        public String    sPassword = "";
        public String    sConfirmPassword = "";
        public String    sName = "";
        public int       iUnit= -1;      //0:Metric  1:English
        public int       iGender = -1;   //0:Female  1:Male
        public float     fHeight = -1;
        public Calendar  cBirthDay = null;
        public boolean   bAgreePolicy = false;
        public boolean   bCheckSuccess = false;
        public int       iRegisterResult = -99;

        public void clear(){
            sEmail = "";
            sPassword = "";
            sConfirmPassword = "";
            sName = "";
            iUnit= -1;      //0:Metric  1:English
            iGender = -1;   //0:Female  1:Male
            fHeight = -1;
            cBirthDay = null;
            bAgreePolicy = false;
            bCheckSuccess = false;
            iRegisterResult = -99;
        }

        public void clearPassword(){
            sPassword = "";
            sConfirmPassword = "";
        }
    }

    /***** Target Goal *****/
    public static class CLOUD_TATGET_GOAL{
        public String sGol_Seq;
        public String sGol_Item;
        public String sGol_Type;
        public String sGol_Val;
        public String sGol_Duration;
        public String sStartDate;
        public String sUsr_Wei;
        public String sGol_Sts;
        public String sGol_Tag;

        public void clear(){
            sGol_Seq = null;
            sGol_Item = null;
            sGol_Type = null;
            sGol_Val = null;
            sGol_Duration = null;
            sStartDate = null;
            sUsr_Wei = null;
            sGol_Sts = null;
            sGol_Tag = null;
        }

        public void copy(CLOUD_TATGET_GOAL data){
            if(data != null){
                clear();
                sGol_Seq = data.sGol_Seq;
                sGol_Item = data.sGol_Item;
                sGol_Type = data.sGol_Type;
                sGol_Val = data.sGol_Val;
                sGol_Duration = data.sGol_Duration;
                sStartDate = data.sStartDate;
                sUsr_Wei = data.sUsr_Wei;
                sGol_Sts = data.sGol_Sts;
                sGol_Tag = data.sGol_Tag;
            }
        }

        public boolean bCheckDataNull(){
            boolean bFlag = false;
            if(sGol_Seq == null)        { bFlag = true; }
            if(sGol_Item == null)       { bFlag = true; }
            if(sGol_Type == null)       { bFlag = true; }
            if(sGol_Val == null)        { bFlag = true; }
            if(sGol_Duration == null)   { bFlag = true; }
            if(sStartDate == null)      { bFlag = true; }
            //if(sUsr_Wei == null)        { bFlag = true; }
            if(sGol_Sts == null)        { bFlag = true; }
            if(sGol_Tag == null)        { bFlag = true; }
            return bFlag;
        }

        public boolean bCheckDataEmpty(){
            boolean bFlag = false;
            if(sGol_Seq.equals(""))         { bFlag = true; }
            if(sGol_Item.equals(""))        { bFlag = true; }
            if(sGol_Type.equals(""))        { bFlag = true; }
            if(sGol_Val.equals(""))         { bFlag = true; }
            if(sGol_Duration.equals(""))    { bFlag = true; }
            if(sStartDate.equals(""))       { bFlag = true; }
            //if(sUsr_Wei.equals(""))         { bFlag = true; }
            if(sGol_Sts.equals(""))         { bFlag = true; }
            if(sGol_Tag.equals(""))         { bFlag = true; }
            return bFlag;
        }

        public void setDataForAdd(){
            //sGol_Seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sGol_Type = EngSetting.S_Get_ExerciseMode();
            sUsr_Wei = "0";
            sGol_Sts = "O";
            sGol_Tag = "0";
            sGol_Seq = "";
        }

        public void setDefaultWei()
        {
            sUsr_Wei = "80";
        }

        public void setDefaultBodyFat()
        {
            sUsr_Wei = "20";
        }

        public void setDataForDelete()
        {
            sGol_Sts = "D";
        }

        public void setDataForContinue()
        {
            sGol_Sts = "K";
        }

        public void setDataForAlreadyPush()
        {
            sGol_Tag = "1";
        }


        public void setType(int iType)
        {
            if      (iType == EngSetting.RUNNING || iType == EngSetting.RUNNING6 || iType == EngSetting.RUNNING7)       { sGol_Type = "R" ; }
            else if (iType == EngSetting.RBIKING || iType == EngSetting.RBIKING6)       { sGol_Type = "B" ; }
            else if (iType == EngSetting.UBIKING || iType == EngSetting.UBIKING6)       { sGol_Type = "B" ; }
            else if (iType == EngSetting.ELLIPTICAL || iType == EngSetting.ELLIPTICAL6)    { sGol_Type = "E" ; }
            else if (iType == EngSetting.ALL)           { sGol_Type = "A" ; }
            else if (iType == -1)                       { sGol_Type = null ; }
            else                                        { sGol_Type = "A" ; }
        }

        public int getType()
        {
            int iType = -1;

            Log.e("Jerry","sGol_Type = " + sGol_Type);

            if(sGol_Type == null)
            {
                iType =  -1;
            }
            else if(sGol_Type.equals("R"))
            {
                iType = EngSetting.RUNNING;
            }
            else if(sGol_Type.equals("B"))
            {
                iType = EngSetting.RBIKING;
            }
            else if(sGol_Type.equals("E"))
            {
                iType = EngSetting.ELLIPTICAL;
            }
            else if(sGol_Type.equals("A"))
            {
                iType = EngSetting.ALL;
            }
            else
            {
                iType = -1;
            }

            return iType;
        }

        public boolean isWeight()
        {
            boolean bResult = false;

            if(sGol_Item.equals("Target Weight"))
            {
                bResult = true;
            }

            return bResult;
        }

        public boolean isBodyFat()
        {
            boolean bResult = false;

            if(sGol_Item.equals("Target Body Fat"))
            {
                bResult = true;
            }

            return bResult;
        }

        public void printAllArgs()
        {
            Log.e("Jerry","sGol_Seq = " + sGol_Seq);
            Log.e("Jerry","sGol_Item = " + sGol_Item);
            Log.e("Jerry","sGol_Type = " + sGol_Type);
            Log.e("Jerry","sGol_Val = " + sGol_Val);
            Log.e("Jerry","sGol_Duration = " + sGol_Duration);
            Log.e("Jerry","sStartDate = " + sStartDate);
            Log.e("Jerry","sUsr_Wei = " + sUsr_Wei);
            Log.e("Jerry","sGol_Sts = " + sGol_Sts);
            Log.e("Jerry","sGol_Tag = " + sGol_Tag);
        }
    }

    /***** Target Goal-Close *****/
    public static class CLOUD_TATGET_GOAL_CLOSE
    {
        public String sGOL_SEQ;
        public String sUSR_SEQ;
        public String sGOL_ITM;
        public String sGOL_TYP;
        public String sGOL_DAT_1;
        public String sGOL_DAT_2;
        public String sGOL_STR_DT;
        public String sUSR_WEI;
        public String sNOW_WEI;
        public String sGOL_WEI;
        public String sGOL_STS;
        public String sGOL_RST;
        public String sEND_TIM;
        public String sGOL_CLS_LVL;

        public void clear()
        {
            sGOL_SEQ = null;
            sUSR_SEQ = null;
            sGOL_ITM = null;
            sGOL_TYP = null;
            sGOL_DAT_1 = null;
            sGOL_DAT_2 = null;
            sGOL_STR_DT = null;
            sUSR_WEI = null;
            sNOW_WEI = null;
            sGOL_WEI = null;
            sGOL_STS = null;
            sGOL_RST = null;
            sEND_TIM = null;
            sGOL_CLS_LVL = null;
        }
    }

    /***** BodyIndexData_Clear_All *****/
    public static void Clear_All_Cloud_data() {

        BodyIndexData_05.clear();
        CloudData_09.clear();

        CloudData_10.clear();
        CloudData_11.clear();
        CloudData_13.clear();
        BodyIndexData_14.clear();
        BodyIndexData_15.clear();
        CloudData_16.clear();
        CloudData_17.clear();

        CloudData_20.clear();
        CloudData_21.clear();
        CloudData_23.clear();
        CloudData_24.clear();
        CloudData_26.clear();
        CloudData_29.clear();

        CloudData_30.clear();
        CloudData_31.clear();
        CloudData_32.clear();
        CloudData_33.clear();
        CloudData_34.clear();
        CloudData_37.clear();
        CloudData_39.clear();

        CloudData_43.clear();
        CloudData_44.clear();
        CloudData_45.clear();
        CloudData_47.clear();

        CloudData_50.clear();
        CloudData_59.clear();

        CloudData_60.clear();
        CloudData_62.clear();
        CloudData_63.clear();
        CloudData_65.clear();
        CloudData_67.clear();
        CloudData_69.clear();
        return ;
    }

    public static void v_print_data(String[] sdata)
    {
        int iLoop ;
        int imax = sdata.length;

        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            Log.e(TAG, "sdata[" + iLoop + "]=" + sdata[iLoop]);
        }
    }

    /***** CloudData_01 Get Update Info*****/
    //設備代碼查詢更新資訊
    public static class CloudData_01 {
        public static String[] sdata_in = new String[Cloud_01_QRY_UPD.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_01_QRY_UPD.sDB_Cmd[3].length];

        public static void clear() {
            for (int i = 0; i < sdata_in.length; i++) {
                sdata_in[i] = "";
            }
            for (int i = 0; i < sdata_out.length; i++) {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            String devmsn = EngSetting.s_Get_ENG_DEV_MSN();
            String devgnr = EngSetting.s_Get_ENG_GNR();
            String conid = EngSetting.s_Get_CON_ID();
            String devmdl = EngSetting.s_Get_ENG_MDL();
            String mch_type = EngSetting.S_Get_ExerciseType();

            String code = "1";

            return Set_input(devmsn, devgnr, devmdl, conid
                    , devmsn, mch_type, code);
        }

        public static boolean Set_input(String id, String devgnr, String devmdl, String conid
                , String devmsn, String mchtyp, String code)
        {
//            "WS_ID", "sDEV_GNR", "sDEV_MDL", "sCON_ID"
//            "sDEV_MSN", "sMCH_TYP", "sASK_COD"
            if(id == null || devgnr == null || devmdl == null || conid == null
                    || devmsn == null || mchtyp == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_01_QRY_UPD.Input.WS_ID] = id;
            sdata_in[Cloud_01_QRY_UPD.Input.sCON_ID] = conid;
            sdata_in[Cloud_01_QRY_UPD.Input.sDEV_GNR] = devgnr;
            sdata_in[Cloud_01_QRY_UPD.Input.sDEV_MDL] = devmdl;
            sdata_in[Cloud_01_QRY_UPD.Input.sDEV_MSN] = devmsn;

            sdata_in[Cloud_01_QRY_UPD.Input.sMCH_TYP] = mchtyp;
            sdata_in[Cloud_01_QRY_UPD.Input.sASK_COD] = code;

            return true;
        }
    }
    /***** CloudData_02 Set Dev Status*****/
    //上傳設備狀態
    public static class CloudData_02
    {
        public static String[] sdata_in = new String[Cloud_02_CHK_LIV.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_02_CHK_LIV.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            String STT = "";
            String COD = "";

            if(ExerciseData.uart_data1.serror_code != null) {
                COD = ExerciseData.uart_data1.serror_code;
            }

            STT = ExerciseData.Check_error_type(COD);

            return Set_input(STT, COD);
        }

        public static boolean Set_input(String STT, String COD)
        {
            String devseq = EngSetting.s_Get_ENG_DEV_MSN();
            String code = "1";

            return Set_input(devseq, devseq, STT, COD, code);
        }

        public static boolean Set_input(String id, String devseq, String STT, String COD, String code)
        {
            //"WS_ID", "sDEV_MSN", "sDEV_STT", "sSTT_COD", "sASK_COD"
            if(id == null || devseq == null || STT == null || COD == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_02_CHK_LIV.Input.WS_ID] = id;
            sdata_in[Cloud_02_CHK_LIV.Input.sDEV_MSN] = devseq;
            sdata_in[Cloud_02_CHK_LIV.Input.sDEV_STT] = STT;
            sdata_in[Cloud_02_CHK_LIV.Input.sSTT_COD] = COD;
            sdata_in[Cloud_02_CHK_LIV.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_03 Set Dev Info*****/
    //上傳設備使用距離(公里)及時間(小時)
    public static class CloudData_03
    {
        public static String[] sdata_in = new String[Cloud_03_DEV_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_03_DEV_BSC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            String devkm = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_ENG_DEV_DISTANCE(), 3);
            String devtim = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_ENG_DEV_TIME(), 3);

            String devmsn = EngSetting.s_Get_ENG_DEV_MSN();
            String fwupd = "2017-12-12 12:12:12";
            String swupd = EngSetting.s_Get_SW_UPDATE_TIME();
            String devgnr = EngSetting.s_Get_ENG_GNR();
            String conid = EngSetting.s_Get_CON_ID();

            String devmdl = EngSetting.s_Get_ENG_MDL();
            String devcsn = EngSetting.s_Get_ENG_CSN();
            String mch_type = EngSetting.S_Get_ExerciseType();
            String nowver = Build.VERSION.SDK + "_" + EngSetting.s_Get_APK_VER().split("\\.")[2];
//            String nowver = Build.VERSION.SDK + "_" + EngSetting.s_Get_APK_VER();

            String owndlr = EngSetting.s_Get_ENG_OWN_DLR();
            String owngym = EngSetting.s_Get_ENG_GYM_ID();
            String excmdl = EngSetting.S_Get_ExerciseMode();
            String code = "1";

            return Set_input(devmsn, fwupd, swupd, devkm, devgnr
                    , conid, devmdl, devcsn, devtim, mch_type, nowver
                    , devmsn, owndlr, owngym, excmdl, code);
        }

        public static boolean Set_input(String id, String fwupd, String swupd, String devkm, String devgnr
                , String conid, String devmdl, String devcsn, String devtim, String mchtyp, String nowver
                , String devmsn, String owndlr, String owngym, String excmdl, String code)
        {
            //"WS_ID", "dFW_UPD_DT", "dSW_UPD_DT", "fDEV_KM", "sDEV_GNR", "sCON_ID", "sDEV_MDL", "sDEV_CSN", "sDEV_TIM", "sMCH_TYP", "sNOW_VER"
            //"sDEV_MSN", "sOWN_DLR", "sOWN_GYM", "sEXC_MDL", "sASK_COD"
            if(id == null || fwupd == null || swupd == null || devkm == null || devgnr == null || conid == null
                    || devmdl == null || devcsn == null || devtim == null || mchtyp == null || nowver == null
                    || devmsn == null || owndlr == null || owngym == null || excmdl == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_03_DEV_BSC.Input.WS_ID] = id;
            sdata_in[Cloud_03_DEV_BSC.Input.dFW_UPD_DT] = fwupd;
            sdata_in[Cloud_03_DEV_BSC.Input.dSW_UPD_DT] = swupd;
            sdata_in[Cloud_03_DEV_BSC.Input.fDEV_KM] = devkm;
            sdata_in[Cloud_03_DEV_BSC.Input.sDEV_GNR] = devgnr;
            sdata_in[Cloud_03_DEV_BSC.Input.sCON_ID] = conid;

            sdata_in[Cloud_03_DEV_BSC.Input.sDEV_MDL] = devmdl;
            sdata_in[Cloud_03_DEV_BSC.Input.sDEV_CSN] = devcsn;
            sdata_in[Cloud_03_DEV_BSC.Input.sDEV_TIM] = devtim;
            sdata_in[Cloud_03_DEV_BSC.Input.sMCH_TYP] = mchtyp;
            sdata_in[Cloud_03_DEV_BSC.Input.sNOW_VER] = nowver;

            sdata_in[Cloud_03_DEV_BSC.Input.sDEV_MSN] = devmsn;
            sdata_in[Cloud_03_DEV_BSC.Input.sOWN_DLR] = owndlr;
            sdata_in[Cloud_03_DEV_BSC.Input.sOWN_GYM] = owngym;
            sdata_in[Cloud_03_DEV_BSC.Input.sEXC_MDL] = excmdl;
            sdata_in[Cloud_03_DEV_BSC.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_04 CHK_AVL_MSN_DLR*****/
    //驗證新機台編號及 Dealer_ID 是否正確
    public static class CloudData_04
    {
        public static String[] sdata_in = new String[Cloud_04_CHK_AVL_MSN_DLR.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_04_CHK_AVL_MSN_DLR.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            String devmsn = EngSetting.s_Get_ENG_DEV_MSN();
            String dlrid = EngSetting.s_Get_ENG_OWN_DLR();
            String code = "1";

            return Set_input(devmsn, devmsn, dlrid, code);
        }

        public static boolean Set_input(String id, String devmsn, String dlrid, String code)
        {
            //"WS_ID", "sDEV_MSN", "sDLR_ID", "sASK_COD"
            if(id == null || devmsn == null || dlrid == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_04_CHK_AVL_MSN_DLR.Input.WS_ID] = id;
            sdata_in[Cloud_04_CHK_AVL_MSN_DLR.Input.sDEV_MSN] = devmsn;
            sdata_in[Cloud_04_CHK_AVL_MSN_DLR.Input.sDLR_ID] = dlrid;
            sdata_in[Cloud_04_CHK_AVL_MSN_DLR.Input.sASK_COD] = code;

            return true;
        }
    }

    /***** BodyIndexData_05 *****/
    public static class BodyIndexData_05
    {
        public static String[] sdata_in = new String[Cloud_05_DB_BDY_IDX_REC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_05_DB_BDY_IDX_REC.sDB_Cmd[3].length];
        public static String[] sdata_in_old = new String[sdata_in.length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
                sdata_in_old[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static void v_SetBodyIndex(int index, String str)
        {
            if(str != null && index < sdata_in.length) {
                sdata_in[index] = str;
            }
        }

        public static void v_SetBodyIndex_Update_Data()
        {
            int imch_type ;

            imch_type = BodyManagerFunc.iget_body_machine_type();

            sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.dMSR_DTE] = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd", null, null, 0, 0);
            sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.dMSR_DT] = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd HH:mm:ss", null, null, 0, 0);

            if(imch_type == 0 ) {
                sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sMCH_TYP] = "Manual-IBA";
            }
            else if(imch_type == 1) {
                sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sMCH_TYP] = "Manual-InBody";
            }

            if(sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.iUSR_SEQ].compareTo("") == 0)
            {
                sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.iUSR_SEQ      ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
                sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.WS_ID         ] = sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.iUSR_SEQ       ];
                sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sASK_COD      ] = "1";
            }

        }

        public static void v_BodyIndex_Undo()
        {

            MyLog.d(" CloudDataStruct.BodyIndexData_05.v_BodyIndex_Undo();");
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = sdata_in_old[i];
            }
        }

        public static void v_BodyIndex_Keep()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in_old[i] = sdata_in[i];
            }
        }

        public static void v_BodyIndex_update_all()
        {
            String[] from_tags =  {
                    "USR_SEQ", "MSR_DT", "MCH_TYP", "Weight", "Weight_U", "Weight_L", "BDY_FAT", "BDY_FAT_L", "BDY_FAT_U", "BDY_FAT_MAS",
                    "BDY_FAT_MAS_L", "BDY_FAT_MAS_U","BMI","BMI_L", "BMI_U",  "BMR", "BON_MAS", "ECR_WAT", "ECR_WAT_L","ECR_WAT_U",
                    "ECW_TBW", "FAT_FRE_MAS", "FAT_FRE_MAS_L", "FAT_FRE_MAS_U", "ITR_WAT", "ITR_WAT_L", "ITR_WAT_U", "LFT_ARF_FMS", "LFT_ARM_FAT", "LFT_LGF_FMS",
                    "LFT_LGT", "Minerals", "Minerals_L", "Minerals_U",  "OBY_DGE", "Protein", "Protein_L", "Protein_U", "RIG_LGF_FMS", "RIT_ARF_FMS",
                    "RIT_ARM_FAT", "RIT_LGT", "TAL_BDY_WAT", "TAL_BDY_WAT_L", "TAL_BDY_WAT_U", "TRK_FAT_MAS","Trunk_fat", "VCL_FAT_RTG", "WIT_HI_RAO", "WIT_HI_RAO_L",
                    "WIT_HI_RAO_U"};
            String[] to_tags = {
                    "iUSR_SEQ", "dMSR_DT", "sMCH_TYP", "fWeight", "fWeight_U", "fWeight_L", "fBDY_FAT", "fBDY_FAT_L", "fBDY_FAT_U", "fBDY_FAT_MAS",
                    "fBDY_FAT_MAS_L", "fBDY_FAT_MAS_U", "fBMI", "fBMI_L", "fBMI_U", "fBMR", "fBON_MAS", "fECR_WAT", "fECR_WAT_L", "fECR_WAT_U",
                    "fECW_TBW", "fFAT_FRE_MAS", "fFAT_FRE_MAS_L", "fFAT_FRE_MAS_U", "fITR_WAT", "fITR_WAT_L", "fITR_WAT_U", "fLFT_ARF_FMS", "fLFT_ARM_FAT", "fLFT_LGF_FMS",
                    "fLFT_LGT", "fMinerals", "fMinerals_L", "fMinerals_U", "fOBY_DGE", "fProtein", "fProtein_L", "fProtein_U", "fRIG_LGF_FMS", "fRIT_ARF_FMS",
                    "fRIT_ARM_FAT", "fRIT_LGT", "fTAL_BDY_WAT", "fTAL_BDY_WAT_L", "fTAL_BDY_WAT_U", "fTRK_FAT_MAS", "fTrunk_fat", "fVCL_FAT_RTG", "fWIT_HI_RAO", "fWIT_HI_RAO_L",
                    "fWIT_HI_RAO_U"};

            int iLoop ;
            int iLoop_max = from_tags.length ;
            int i_index ;
            int i_index_max = Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[3].length;
            String[] i_ss = Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[3];
            String i_stag;
            int o_index ;
            String o_stag;
            int o_index_max = Cloud_05_DB_BDY_IDX_REC.sDB_Cmd[2].length ;
            String[] o_ss = Cloud_05_DB_BDY_IDX_REC.sDB_Cmd[2];

            for(iLoop = 0; iLoop < iLoop_max; iLoop++)
            {
                for(i_index = 0; i_index < i_index_max; i_index++)
                {
                    i_stag = from_tags[iLoop];
                    if(i_stag.compareTo(i_ss[i_index]) == 0)
                    {
                        break;
                    }
                }

                if(i_index < i_index_max)
                {
                    for(o_index = 0; o_index < o_index_max; o_index++)
                    {
                        o_stag = to_tags[iLoop];
                        if(o_stag.compareTo(o_ss[o_index]) == 0)
                        {
                            sdata_in[o_index] = BodyIndexData_15.sdata_out[i_index];
                            if(DEBUG) Log.e(TAG, "sdata_in[" + o_index + "]=" + sdata_in[i_index]);
                            break;
                        }
                    }

                }
            }

            sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.WS_ID         ] = sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.iUSR_SEQ];
            sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sASK_COD      ] = "1";
            sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.dMSR_DTE      ] = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd", null, null, 1, 0);
            v_BodyIndex_Keep();
        }
    }

    /***** CloudData_9 Workout Data *****/
    public static class CloudData_09
    {
        public static String[] sdata_in = new String[Cloud_09_GET_WRK_ITV_SET.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_09_GET_WRK_ITV_SET.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID)
        {
            boolean bResult;

            String code = "1";

            bResult = Set_input(sMCH_TYP,sWRK_ID,code);

            return bResult;
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sAskCod)
        {
            if(sMCH_TYP == null || sWRK_ID == null || sAskCod == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null)
            {
                return false;
            }

            sdata_in[Cloud_09_GET_WRK_ITV_SET.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_09_GET_WRK_ITV_SET.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_09_GET_WRK_ITV_SET.Input.sWRK_ID] = sWRK_ID;
            sdata_in[Cloud_09_GET_WRK_ITV_SET.Input.sMCH_TYP] = sMCH_TYP;
            sdata_in[Cloud_09_GET_WRK_ITV_SET.Input.sASK_COD] = sAskCod;

            return true;
        }
    }

    public static class CLOUD_WORKOUT_NAME
    {
        public String sWRK_ID;      //Workout ID
        public String sMCH_TYP;     //Machine Type
        public String sWRK_NAM;     //Workout Name
    }

    /***** CloudData_10 Get Workout Name List *****/
    public static class CloudData_10
    {
        public static String[] sdata_in = new String[Cloud_10_GET_WRK_NAM.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_10_GET_WRK_NAM.sDB_Cmd[3].length];
        public static ArrayList<CLOUD_WORKOUT_NAME> list_CloudWorkoutName = new ArrayList<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            boolean bResult;

            String code = "1";

            bResult = Set_input(code);

            return bResult;
        }

        public static boolean Set_input(String sAskCod)
        {
            if(sAskCod == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null)
            {
                return false;
            }

            sdata_in[Cloud_10_GET_WRK_NAM.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_10_GET_WRK_NAM.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_10_GET_WRK_NAM.Input.sASK_COD] = sAskCod;

            return true;
        }
    }

    /***** CloudData_11 Add Exercise Data *****/
    public static class CloudData_11
    {
        public static String[] sdata_in = new String[Cloud_11_INS_END_WKO_REC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_11_INS_END_WKO_REC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            boolean bResult;
            float fval;

            String smch_type = EngSetting.S_Get_ExerciseName();
            String s_dur_time = Rtx_Calendar.s_trans_integer(0, ExerciseGenfunc.i_get_total_time());
            fval = ExerciseGenfunc.f_get_total_distance_km();
            String s_duration = Rtx_TranslateValue.sFloat2String(fval, 2);
            String s_mode = EngSetting.S_Get_ExerciseMode();
            String s_mode_name = "";

            String s_wrk_name = "";
            String s_avg_hr = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_ave_heartrate(), 1);
            String s_avg_pace = Rtx_Calendar.s_trans_integer(0, (int)ExerciseGenfunc.f_get_ave_pace());
            String s_calories = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_total_calories(), 1);
            //How to define Watt at Treadmill?
            String s_avg_watt = Rtx_TranslateValue.sFloat2String(ExerciseGenfunc.f_get_total_calories(), 1);

            String s_date = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd HH:mm:ss", null, null, 0, 0);

            bResult = Set_input(smch_type, s_dur_time, s_duration, s_mode, s_mode_name
                    , s_avg_hr, s_avg_pace, s_calories, s_avg_watt, s_wrk_name
                    , s_date);

            return bResult;

        }

        public static boolean Set_input(String smch_type, String s_dur_time, String s_duration, String s_mode, String s_mode_name
                , String s_avg_hr, String s_avg_pace, String s_calories, String s_avg_watt, String s_wrk_name
                , String s_date)
        {
            boolean bResult = true;

            if(smch_type == null || s_dur_time == null || s_duration == null || s_mode == null || s_mode_name == null
                    || s_avg_hr == null || s_avg_pace == null || s_calories == null || s_avg_watt == null || s_wrk_name == null
                    || s_date == null)
            {
                return false;
            }

            if(CloudData_20.is_log_in()) {
                sdata_in[Cloud_11_INS_END_WKO_REC.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
                sdata_in[Cloud_11_INS_END_WKO_REC.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            }
            else
            {
                sdata_in[Cloud_11_INS_END_WKO_REC.Input.WS_ID] = "0" ;
                sdata_in[Cloud_11_INS_END_WKO_REC.Input.iUSR_SEQ] = "0" ;
            }

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sOWN_GYM] = EngSetting.s_Get_ENG_GYM_ID();
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sDEV_MSN] = EngSetting.s_Get_ENG_DEV_MSN();
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sDEV_CTY] = EngSetting.s_Get_DEFAULT_CITY();

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sMCH_TYP] = smch_type;

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sDRT_TIM] = s_dur_time;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.fRUN_DST] = s_duration;

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sDEV_TIM] = s_dur_time;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.fDEV_KM] = s_duration;

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sEXC_MDL] = s_mode;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sEXC_MDL_NAM] = s_mode_name;

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.fAVG_HRT_RAT] = s_avg_hr;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sAVG_PAC] = s_avg_pace;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.fCOS_CAL] = s_calories;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.fAVG_WAT] = s_avg_watt;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sWRK_NAM] = s_wrk_name;
            sdata_in[Cloud_11_INS_END_WKO_REC.Input.dCRE_DT] = s_date;

            sdata_in[Cloud_11_INS_END_WKO_REC.Input.sASK_COD] = "1";

//            for(int i = 0; i < sdata_in.length; i++)
//            {
//                Log.e(TAG, "sdata_in[" + i + "]=" + sdata_in[i]);
//            }

            return bResult;
        }
    }

    /***** CloudData_12 Exercise Session Delete*****/
    public static class CloudData_12
    {
        public static String[] sdata_in = new String[Cloud_12_DEL_END_WKO_REC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_12_DEL_END_WKO_REC.sDB_Cmd[3].length];

        public static ArrayList<String[]> clound_cmd26_list = new ArrayList<String[]>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            clound_cmd26_list.clear();
        }

        public static boolean Set_input(String work_seq)
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, work_seq, seq, code);
        }

        public static boolean Set_input(String id, String work_seq, String seq, String code)
        {
            //"WS_ID", ""iEND_WKO_SEQ", ""iUSR_SEQ", "sASK_COD"
            if(id == null || work_seq == null || seq == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_12_DEL_END_WKO_REC.Input.WS_ID] = id;
            sdata_in[Cloud_12_DEL_END_WKO_REC.Input.iEND_WKO_SEQ] = work_seq;
            sdata_in[Cloud_12_DEL_END_WKO_REC.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_12_DEL_END_WKO_REC.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_13 Delete Class *****/
    public static class CloudData_13
    {
        public static String[] sdata_in = new String[Cloud_13_DEL_BKG_CLS.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_13_DEL_BKG_CLS.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sBkgSeq)
        {
            boolean bResult;

            String code = "1";

            bResult = Set_input(sBkgSeq, code);

            return bResult;
        }

        public static boolean Set_input(String sBkgSeq,
                                        String sAskCod
        )
        {
            if(sBkgSeq == null || sAskCod == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null)
            {
                return false;
            }

            sdata_in[Cloud_13_DEL_BKG_CLS.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_13_DEL_BKG_CLS.Input.iBKG_SEQ] = sBkgSeq;
            sdata_in[Cloud_13_DEL_BKG_CLS.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_13_DEL_BKG_CLS.Input.sASK_COD] = sAskCod;

            return true;
        }
    }

    /***** BodyIndexData_14 *****/
    public static class BodyIndexData_14
    {
        public static String[] sdata_in = new String[Cloud_14_GET_BDY_HIS.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_14_GET_BDY_HIS.sDB_Cmd[3].length];
        public static String[] sout_keys = {"OUTTAG", "MSR_DT", "MCH_TYP", "TTL_CNT"};

        public static ArrayList<HistoryData> historypoints_list                         = new ArrayList<HistoryData>();
        public static TreeMap<String, String[]>    tree_clound_cmd14_result             = new TreeMap<String, String[]>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_BMI            = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_VCL_FAT_RTG    = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_BMR            = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_BDY_FAT        = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_TAL_BDY_WAT    = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_OBY_DGE        = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_BON_MAS        = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_Weight         = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_Protein        = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_Minerals       = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_WIT_HI_RAO     = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_ECR_WAT        = new TreeMap<String, HistoryData>(Collections.reverseOrder());
        public static TreeMap<String, HistoryData> tree_clound_cmd14_his_ITR_WAT        = new TreeMap<String, HistoryData>(Collections.reverseOrder());

        public static TreeMap<String, HistoryData> s_get_history_tree(int imode) {
            TreeMap<String, HistoryData> sdata;
            switch (imode) {
                case fBMI:
                    sdata = tree_clound_cmd14_his_BMI;
                    break;
                case fVCL_FAT_RTG:
                    sdata = tree_clound_cmd14_his_VCL_FAT_RTG;
                    break;
                case fBMR:
                    sdata = tree_clound_cmd14_his_BMR;
                    break;
                case fBDY_FAT:
                    sdata = tree_clound_cmd14_his_BDY_FAT;
                    break;
                case fTAL_BDY_WAT:
                    sdata = tree_clound_cmd14_his_TAL_BDY_WAT;
                    break;
                case fOBY_DGE:
                    sdata = tree_clound_cmd14_his_OBY_DGE;
                    break;
                case fBON_MAS:
                    sdata = tree_clound_cmd14_his_BON_MAS;
                    break;
                case fWeight:
                    sdata = tree_clound_cmd14_his_Weight;
                    break;
                case fProtein:
                    sdata = tree_clound_cmd14_his_Protein;
                    break;
                case fMinerals:
                    sdata = tree_clound_cmd14_his_Minerals;
                    break;
                case fWIT_HI_RAO:
                    sdata = tree_clound_cmd14_his_WIT_HI_RAO;
                    break;
                case fECR_WAT:
                    sdata = tree_clound_cmd14_his_ECR_WAT;
                    break;
                case fITR_WAT:
                    sdata = tree_clound_cmd14_his_ITR_WAT;
                    break;
                default:
                    sdata = null;
                    break;
            }
            return sdata;
        }

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            historypoints_list.clear();
            tree_clound_cmd14_result.clear();
            tree_clound_cmd14_his_BMI.clear();
            tree_clound_cmd14_his_VCL_FAT_RTG.clear();
            tree_clound_cmd14_his_BMR.clear();
            tree_clound_cmd14_his_BDY_FAT.clear();
            tree_clound_cmd14_his_TAL_BDY_WAT.clear();
            tree_clound_cmd14_his_OBY_DGE.clear();
            tree_clound_cmd14_his_BON_MAS.clear();
            tree_clound_cmd14_his_Weight.clear();
            tree_clound_cmd14_his_Protein.clear();
            tree_clound_cmd14_his_Minerals.clear();
            tree_clound_cmd14_his_WIT_HI_RAO.clear();
            tree_clound_cmd14_his_ECR_WAT.clear();
            tree_clound_cmd14_his_ITR_WAT.clear();
        }

        public static boolean Set_input(String type, String date, String cnt)
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, type, date, seq, cnt, code);
        }

        public static boolean Set_input(String id, String type, String date, String seq, String cnt, String code)
        {
            //"WS_ID", "rCOL", "dMSR_DT", "iUSR_SEQ", "iCNT", "sASK_COD"
            if(id == null || type == null || date == null || seq == null || cnt == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_14_GET_BDY_HIS.Input.WS_ID] = id;
            sdata_in[Cloud_14_GET_BDY_HIS.Input.rCOL] = type;
            sdata_in[Cloud_14_GET_BDY_HIS.Input.dMSR_DT] = date;
            sdata_in[Cloud_14_GET_BDY_HIS.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_14_GET_BDY_HIS.Input.iCNT] = cnt;
            sdata_in[Cloud_14_GET_BDY_HIS.Input.sASK_COD] = code;

            return true;
        }


    }

    public static class HistoryData {
        public String sDate = "";
        public String sVal = "";

        public HistoryData(String sdate, String sval)
        {
            sDate = sdate;
            sVal = sval;
        }

    }

    /***** BodyIndexData_15 *****/
    public static class BodyIndexData_15
    {
        public static String[] sdata_in = new String[Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_15_GET_BDY_IDX_REC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String date)
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, date, seq, code);
        }

        public static boolean Set_input(String id, String date, String seq, String code)
        {
            //"WS_ID", "iUSR_SEQ", "dMSR_DTE", "sASK_COD"
            if(id == null || date == null || seq == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_15_GET_BDY_IDX_REC.Input.WS_ID] = id;
            sdata_in[Cloud_15_GET_BDY_IDX_REC.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_15_GET_BDY_IDX_REC.Input.dMSR_DTE] = date;
            sdata_in[Cloud_15_GET_BDY_IDX_REC.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_16 *****/
    public static class CloudData_16
    {
        public static String[] sdata_in = new String[Cloud_16_GET_EW_SUM_HIS.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_16_GET_EW_SUM_HIS.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

        }

        public static boolean Set_input(String sExcMode, Calendar calStart, Calendar calEnd)
        {
            String code = "1";
            String seq = CloudDataStruct.CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            if(calStart == null || calEnd == null )
            {
                return  false;
            }
            String sStartDate = Rtx_Calendar.sCalendar2Str(calStart,"yyyy-MM-dd");
            String sEndDate = Rtx_Calendar.sCalendar2Str(calEnd,"yyyy-MM-dd");

            String shours="";
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0);
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1);
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2);

            int intValue = Integer.valueOf(shours);
            shours = Integer.toString(intValue);

            return Set_input(seq, seq, sExcMode, sStartDate, sEndDate, shours, code);
        }

        public static boolean Set_input(String id, String seq, String sExcMode, String sStartDate, String sEndDate, String shours, String code)
        {
            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(id == null || seq == null || sExcMode == null || sStartDate == null || sEndDate == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.WS_ID] = id;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.dEND_DT] = sEndDate;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.dSTD_DT] = sStartDate;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.sEXC_MDL] = sExcMode;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.sHOURS] = shours;
            sdata_in[Cloud_16_GET_EW_SUM_HIS.Input.sASK_COD] = code;

            return true;
        }
    }

    /***** CloudData_17 *****/
    public static class CloudData_17
    {
        public static String[] sdata_in = new String[Cloud_17_GET_USR_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_17_GET_USR_BSC.sDB_Cmd[3].length];
        public static String[] sdata_out_old = new String[Cloud_17_GET_USR_BSC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            for(int i = 0; i < sdata_out_old.length; i++)
            {
                sdata_out_old[i] = "";
            }
        }


        public static boolean Set_input()
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, seq, code);
        }

        public static boolean Set_input(String id, String seq, String code)
        {
            //"WS_ID", "iUSR_SEQ", "sASK_COD"
            if(id == null || seq == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_17_GET_USR_BSC.Input.WS_ID] = id;
            sdata_in[Cloud_17_GET_USR_BSC.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_17_GET_USR_BSC.Input.sASK_COD] = code;

            return true;
        }

        public static void Set_output(int index, String str)
        {
            if(str != null)
            {
                sdata_out[index] = str;
            }

            return ;
        }

        public static String Get_output(int index)
        {
            String sdata = "";

            if(sdata_out != null && sdata_out[index] != null)
            {
                sdata = sdata_out[index];
            }

            return sdata;
        }

        public static void Set_output_Old(int index, String str)
        {
            if(str != null)
            {
                sdata_out_old[index] = str;
            }

            return ;
        }

        public static void OutData_Undo()
        {
            for(int i = 0; i < sdata_out_old.length; i++)
            {
                sdata_out[i] = sdata_out_old[i];
            }
        }

        public static void OutData_Keep()
        {
            for(int i = 0; i < sdata_out_old.length; i++)
            {
                sdata_out_old[i] = sdata_out[i];
            }
        }

        //return user heihgt ; defaut 170 cm if fail.
        public static float f_get_user_height() {
            float fval = 170;

            try {
                fval = Float.parseFloat(sdata_out[Cloud_17_GET_USR_BSC.Output.USR_HIG]);
            } catch (Exception e) {

            }

            return fval;
        }

        //return user age ; defaut 25 if fail.
        public static int f_get_user_age(Context mContext) {
            int ival = (int)EngSetting.f_Get_Def_Age();
            float fmin = EngSetting.f_Get_Min_Age();
            float fmax = EngSetting.f_Get_Max_Age();

            int iyear_age = Rtx_Calendar.i_get_datetime(0, 0, "yyyy-MM-dd", sdata_out[Cloud_17_GET_USR_BSC.Output.USR_BIR]);
            int iyear_now = Rtx_Calendar.i_get_datetime(0, 0, null, null);

            if (iyear_now - iyear_age >= fmin && iyear_now - iyear_age <= fmax) {
                ival = iyear_now - iyear_age;
            }

            return ival;
        }

        //return user weight ; defaut 80 if fail.
        public static float f_get_user_weight() {
            float fval = EngSetting.f_Get_Def_Weight();
            float fmin = EngSetting.f_Get_Min_Weight();
            float fmax = EngSetting.f_Get_Max_Weight();
            float fdata;
            String sdata = sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI];

            fdata = Rtx_TranslateValue.fString2Float(sdata);

            if(fdata >= fmin && fdata <= fmax)
            {
                fval = fdata;
            }

            return fval;
        }

        //true : Male; false : Female
        public static boolean is_Male() {
            boolean bdata;
            String sdata = sdata_out[Cloud_17_GET_USR_BSC.Output.USR_SEX];

            if (sdata != null && sdata.toLowerCase().compareTo("m") == 0) {
                bdata = true;
            } else {
                bdata = false;
            }

            return bdata;
        }



    }

    /***** CloudData_20 *****/
    public static class CloudData_20
    {
        public static String[] sdata_in = new String[Cloud_20_CHK_LGI.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_20_CHK_LGI.sDB_Cmd[3].length];
        public static boolean bLogin = false;

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

            bLogin = false;
        }

        public static void Set_input(int index , String str)
        {
            if(str != null)
            {
                sdata_in[index] = str ;
            }

            return ;
        }

        public static boolean Set_input(String sname , String spassword)
        {
            String code = "1";

            return Set_input(sname, sname, spassword, code);
        }

        public static boolean Set_input(String id, String sname, String spass, String code)
        {
            //"WS_ID", "sUSR_ID", "sUSR_PW", "sASK_COD"
            if(id == null || sname == null || spass == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_20_CHK_LGI.Input.WS_ID] = id;
            sdata_in[Cloud_20_CHK_LGI.Input.sUSR_ID] = sname;
            sdata_in[Cloud_20_CHK_LGI.Input.sUSR_PW] = spass;
            sdata_in[Cloud_20_CHK_LGI.Input.sASK_COD] = code;

            return true;
        }

        public static void Set_output(int index, String str)
        {
            if(str != null)
            {
                sdata_out[index] = str;
            }

            return ;
        }

        public static String Get_output(int index)
        {
            String sdata = null;

            if(sdata_out != null && sdata_out[index] != null)
            {
                sdata = sdata_out[index];
            }

            return sdata;
        }

        public static boolean is_log_in()
        {
            return bLogin;
        }

        public static void set_log_in(boolean bset)
        {
            if(!bset)
            {
                Clear_All_Cloud_data();
                //登出清除
                if(Rtx_Debug.bRtxDebug_GetShareEnable())
                {
                    GlobalData.vGlobalData_ShareAllClear();
                }
                GlobalData.Weather.bUpdate = true;
            }
            else
            {
                //登入清除 -> 改從LoginProc.java執行
//                GlobalData.vGlobalData_ShareAllClear();
            }

            bLogin = bset;

        }

        public static void  log_in_test(String sID)
        {
            //set_log_in(true);
            if(sID != null)
            {
                sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] = sID;
            }
            return ;
        }
    }

    /***** CloudData_21 *****/
    public static class CloudData_21
    {
        public static String[] sdata_in = new String[Cloud_21_GET_EXC_REC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_21_GET_EXC_REC.sDB_Cmd[3].length];

        public static ArrayList<String[]> clound_cmd21_list = new ArrayList<String[]>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            clound_cmd21_list.clear();
        }

        public static boolean Set_input(String date, String cnt)
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, date, seq, cnt, code);
        }

        public static boolean Set_input(String id, String date, String seq, String cnt, String code)
        {
            //"WS_ID", "dCRE_DT", "iUSR_SEQ", "iCNT", "sASK_COD"
            if(id == null || date == null || seq == null || cnt == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_21_GET_EXC_REC.Input.WS_ID] = id;
            sdata_in[Cloud_21_GET_EXC_REC.Input.dCRE_DT] = date;
            sdata_in[Cloud_21_GET_EXC_REC.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_21_GET_EXC_REC.Input.iCNT] = cnt;
            sdata_in[Cloud_21_GET_EXC_REC.Input.sASK_COD] = code;

            return true;
        }

        public static void v_Add_Session(String[] sdata)
        {
            String[] sdata21 = new String[Cloud_21_GET_EXC_REC.sDB_Cmd[3].length];

            sdata21[Cloud_21_GET_EXC_REC.Output.USR_SEQ      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.iUSR_SEQ];
            //sdata21[Cloud_21_GET_EXC_REC.Output.END_WKO_SEQ  ] = sdata[Cloud_11_INS_END_WKO_REC.Input.dCRE_DT];
            sdata21[Cloud_21_GET_EXC_REC.Output.MCH_TYP      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.sMCH_TYP];
            sdata21[Cloud_21_GET_EXC_REC.Output.EXC_MDL      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.sEXC_MDL];
            sdata21[Cloud_21_GET_EXC_REC.Output.EXC_MDL_NAM  ] = sdata[Cloud_11_INS_END_WKO_REC.Input.sEXC_MDL_NAM];

            sdata21[Cloud_21_GET_EXC_REC.Output.CRE_DT       ] = sdata[Cloud_11_INS_END_WKO_REC.Input.dCRE_DT];
            sdata21[Cloud_21_GET_EXC_REC.Output.DRT_TIM      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.sDRT_TIM];
            sdata21[Cloud_21_GET_EXC_REC.Output.AVG_PAC      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.sAVG_PAC];
            sdata21[Cloud_21_GET_EXC_REC.Output.RUN_DST      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.fRUN_DST];
            sdata21[Cloud_21_GET_EXC_REC.Output.COS_CAL      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.fCOS_CAL];

            sdata21[Cloud_21_GET_EXC_REC.Output.AVG_HRT_RAT  ] = sdata[Cloud_11_INS_END_WKO_REC.Input.fAVG_HRT_RAT];
            sdata21[Cloud_21_GET_EXC_REC.Output.AVG_WAT      ] = sdata[Cloud_11_INS_END_WKO_REC.Input.fAVG_WAT];

            clound_cmd21_list.add(sdata21);

            sortByStringAscending(clound_cmd21_list, Cloud_21_GET_EXC_REC.Output.CRE_DT);

        }

        public static void v_Del_Session(int index)
        {
            if(clound_cmd21_list.size() > index) {
                clound_cmd21_list.remove(index);
            }
        }

    }

    /***** CloudData_23 add/delete Target *****/
    public static class CloudData_23
    {
        public static String[] sdata_in = new String[Cloud_23_DB_EXC_GOL.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_23_DB_EXC_GOL.sDB_Cmd[3].length];
        public static boolean bLogin = false;

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(CloudDataStruct.CLOUD_TATGET_GOAL data)
        {
            boolean bResult = false;

            bResult = Set_input(
                        CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ],
                        data.sGol_Item,
                        data.sStartDate,
                        data.sGol_Duration,
                        data.sGol_Val,
                        data.sGol_Sts,
                        data.sGol_Tag,
                        data.sGol_Type,
                        data.sUsr_Wei,
                        data.sGol_Seq,
                        "1");

            return bResult;
        }

        public static boolean Set_input(String sUsrSeq,
                                        String sItem,
                                        String sStartDate,          //yyyy-MM-dd
                                        String sDuration,
                                        String sTargetVal,
                                        String sSts,                //O:On-Going  ;  D:Delete  ;  K:Continue
                                        String sTag,                //Half Way Tag  ;  0:not yet push  ;  1:already push
                                        String sExerciseType,       //R E B A
                                        String sStart_Wei_BdyFat,
                                        String sGol_Seq,            //When add : ""  ; When Modify/Delete : "iGol_Seq"
                                        String sAskCod
                                        )
        {
            //"WS_ID", "sUSR_ID", "sUSR_PW", "sASK_COD"
            if(sUsrSeq == null || sItem == null || sStartDate == null || sDuration == null || sTargetVal == null || sSts == null || sTag == null || sExerciseType == null || sStart_Wei_BdyFat == null || sGol_Seq == null || sAskCod == null)
            {
                return false;
            }

            sdata_in[Cloud_23_DB_EXC_GOL.Input.WS_ID] = sUsrSeq;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.iUSR_SEQ] = sUsrSeq;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_ITM] = sItem;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.dGOL_STR_DT] = sStartDate;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_DAT_2] = sDuration;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_DAT_1] = sTargetVal;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_STS] = sSts;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_TAG] = sTag;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sGOL_TYP] = sExerciseType;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sUSR_WEI] = sStart_Wei_BdyFat;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.iGOL_SEQ] = sGol_Seq;
            sdata_in[Cloud_23_DB_EXC_GOL.Input.sASK_COD] = sAskCod;

            return true;
        }

    }

    /***** CloudData_24 add/delete Target *****/
    public static class CloudData_24
    {
        public static String[] sdata_in = new String[Cloud_24_GET_EXC_GOL.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_24_GET_EXC_GOL.sDB_Cmd[3].length];
        public static ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal = new ArrayList<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            list_CloudTargetGoal.clear();
        }

        public static boolean Set_input()
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, seq, code);
        }

        public static boolean Set_input(String id, String seq, String code)
        {
            //"WS_ID", "iUSR_SEQ", "sASK_COD"
            if(id == null || seq == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_24_GET_EXC_GOL.Input.WS_ID] = id;
            sdata_in[Cloud_24_GET_EXC_GOL.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_24_GET_EXC_GOL.Input.sASK_COD] = code;

            return true;
        }

    }

    public static class CLOUD_BULLETIN_INFO
    {
        public String sBLT_ID;//Bulletin ID
        public String sBLT_TIL;//Bulletin Title
        public String sBLT_AUC_DT;//Bulletin Date
        public String sH_FLG;//Bulletin Hot Flag
    }

    /***** CloudData_26 Exercise Year data*****/
    public static class CloudData_26
    {
        public static String[] sdata_in = new String[Cloud_26_GET_EW_YER.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_26_GET_EW_YER.sDB_Cmd[3].length];

        public static TreeMap<String, String[]> clound_cmd26_list = new TreeMap<String, String[]>(Collections.reverseOrder());

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            clound_cmd26_list.clear();
        }

        public static boolean Set_input()
        {
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";

            return Set_input(seq, seq, code);
        }

        public static boolean Set_input(String id, String seq, String code)
        {
            //"WS_ID", ""iUSR_SEQ", "sASK_COD"
            if(id == null || seq == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_26_GET_EW_YER.Input.WS_ID] = id;
            sdata_in[Cloud_26_GET_EW_YER.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_26_GET_EW_YER.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_27 Get Dev Info*****/
    //開機後，開到設備使用距離(公里)及時間(小時)
    public static class CloudData_27
    {
        public static String[] sdata_in = new String[Cloud_27_GET_DEV_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_27_GET_DEV_BSC.sDB_Cmd[3].length];
        public static boolean bgetdata = false;

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            bgetdata = false;
        }

        public static String sGet_output(int index)
        {
            String sdata = null;

            if(index < sdata_out.length)
            {
                sdata = sdata_out[index];
            }

            return sdata;
        }

        public static float fGet_output(int index)
        {
            String sdata = null;
            float fval = 0;

            sdata = sGet_output(index);

            if(sdata !=  null)
            {
                fval = Rtx_TranslateValue.fString2Float(sdata);
            }

            return fval;
        }

        public static boolean Set_input()
        {
            String devseq = EngSetting.s_Get_ENG_DEV_MSN();
            String code = "1";

            return Set_input(devseq, devseq, code);
        }

        public static boolean Set_input(String id, String seq, String code)
        {
            //"WS_ID", ""sDEV_MSN", "sASK_COD"
            if(id == null || seq == null || code == null)
            {
                return false;
            }
            sdata_in[Cloud_27_GET_DEV_BSC.Input.WS_ID] = id;
            sdata_in[Cloud_27_GET_DEV_BSC.Input.sDEV_MSN] = seq;
            sdata_in[Cloud_27_GET_DEV_BSC.Input.sASK_COD] = code;

            return true;
        }

        public static boolean b_is_get_dev_info()
        {
            return bgetdata;
        }

        public static void v_set_get_dev_info(boolean bval)
        {
            bgetdata = bval;
        }

    }


    /***** CloudData_29 Get Bulletin List *****/
    public static class CloudData_29
    {
        public static String[] sdata_in = new String[Cloud_29_GET_BLT_BOD_LST.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_29_GET_BLT_BOD_LST.sDB_Cmd[3].length];
        public static ArrayList<CLOUD_BULLETIN_INFO>  list_CloudBulletinInfo = new ArrayList<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(int iCount , String sGymID)
        {
            boolean bResult;

            String sWS_ID = "stiaua";
            String sStartIndex = "0";
            String sCount = Rtx_TranslateValue.sInt2String(iCount);
            String code = "1";

            bResult = Set_input(sWS_ID, sStartIndex, sCount, sGymID, code);

            return bResult;
        }

        public static boolean Set_input(String sWS_ID, String sStartIndex, String sCount, String sGymID, String sAskCod){
            if(sWS_ID == null || sStartIndex == null || sCount == null || sGymID == null || sAskCod == null){
                return false;
            }
            sdata_in[Cloud_29_GET_BLT_BOD_LST.Input.WS_ID] = sWS_ID;
            sdata_in[Cloud_29_GET_BLT_BOD_LST.Input.iCNT1] = sStartIndex;
            sdata_in[Cloud_29_GET_BLT_BOD_LST.Input.iCNT2] = sCount;
            sdata_in[Cloud_29_GET_BLT_BOD_LST.Input.sACN_ID] = sGymID;
            sdata_in[Cloud_29_GET_BLT_BOD_LST.Input.sASK_COD] = sAskCod;
            return true;
        }

    }

    /***** CloudData_30 Get Bulletin Detail Info *****/
    public static class CloudData_30{
        public static String[] sdata_in = new String[Cloud_30_GET_BLT_BOD.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_30_GET_BLT_BOD.sDB_Cmd[3].length];
        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sGymID , String sBLT_ID){
            boolean bResult;
            String code = "1";
            bResult = Set_input(sGymID, sBLT_ID, code);
            return bResult;
        }

        public static boolean Set_input(String sGymID, String sBLT_ID, String sAskCod){
            if(sGymID == null || sBLT_ID == null || sAskCod == null){
                return false;
            }
            sdata_in[Cloud_30_GET_BLT_BOD.Input.WS_ID] = sGymID;
            sdata_in[Cloud_30_GET_BLT_BOD.Input.sBLT_ID] = sBLT_ID;
            sdata_in[Cloud_30_GET_BLT_BOD.Input.sASK_COD] = sAskCod;
            return true;
        }
    }

    public static class CLOUD_GYM_CLASS_INFO{
        public String sCLS_SUC_DT;  //DateTime yyyy-MM-dd
        public String sCLS_ID;      //Class ID
        public String sCLS_NAME;    //Class Name
        public String sCLS_STR_TIM; //Class Start Time
        public String sCLS_END_TIM; //Class End Time
        public String sCLS_PHO;     //Class Photo
        public String sCLS_TCH_NAM; //Class Tracher Name
        public String sUser_CLS_SUC_DT;
        public String sUser_CLS_STR_TIM;
        public String sUser_CLS_END_TIM;
        public Calendar cUser_CLS_STR;
    }

    /***** CloudData_31 Get Gym Class List *****/
    public static class CloudData_31{
        public static String[] sdata_in = new String[Cloud_31_WEB_GET_CLS_SUC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_31_WEB_GET_CLS_SUC.sDB_Cmd[3].length];
        public static ArrayList<CLOUD_GYM_CLASS_INFO>  list_CloudClassInfo = new ArrayList<>();
        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sGymID , Calendar cStart , Calendar cEnd){
            boolean bResult;
            Calendar cSearchStart = (Calendar)cStart.clone();
            Calendar cSearchEnd = (Calendar)cEnd.clone();
            cSearchStart.add(Calendar.DATE,-1);
            cSearchEnd.add(Calendar.DATE,1);
            String code = "1";
            String sDate_Start = Rtx_Calendar.sCalendar2Str(cSearchStart,"yyyy-MM-dd");
            String sDate_End = Rtx_Calendar.sCalendar2Str(cSearchEnd,"yyyy-MM-dd");
            bResult = Set_input(sGymID, sDate_Start, sDate_End, code);
            return bResult;
        }

        public static boolean Set_input(String sGymID, String sDate_Start, String sDate_End, String sAskCod){
            if(sGymID == null || sDate_Start == null || sDate_End == null || sAskCod == null){
                return false;
            }
            sdata_in[Cloud_31_WEB_GET_CLS_SUC.Input.WS_ID] = sGymID;
            sdata_in[Cloud_31_WEB_GET_CLS_SUC.Input.dEND_DT] = sDate_End;
            sdata_in[Cloud_31_WEB_GET_CLS_SUC.Input.dSTR_DT] = sDate_Start;
            sdata_in[Cloud_31_WEB_GET_CLS_SUC.Input.sGYM_ID] = sGymID;
            sdata_in[Cloud_31_WEB_GET_CLS_SUC.Input.sASK_COD] = sAskCod;
            return true;
        }
    }

    public static class CLOUD_GYM_CLASS_DETAIL_INFO{
        public String sCLS_DEL;
        public String sCLS_ID;
        public String sCLS_NAM;
        public String sCLS_TCH_NAM;
        public String sCLS_STR_TIM;
        public String sCLS_END_TIM;
        public String sCLS_PHO;
        public String sCLS_UTB;
        public String sCLS_BKG_MAX;
        public String sCLS_CTT;
        public String sUser_CLS_STR_TIM;
        public String sUser_CLS_END_TIM;
    }

    /***** CloudData_32 Get Gym Class Detail Info *****/
    public static class CloudData_32
    {
        public static String[] sdata_in = new String[Cloud_32_GET_CLS_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_32_GET_CLS_BSC.sDB_Cmd[3].length];
        public static ArrayList<CLOUD_GYM_CLASS_DETAIL_INFO>  list_CloudClassDetailInfo = new ArrayList<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sClsID)
        {
            boolean bResult;

            String code = "1";

            bResult = Set_input(sClsID, code);

            return bResult;
        }

        public static boolean Set_input(String sClsID,
                                        String sAskCod
        )
        {
            if(sClsID == null || sAskCod == null)
            {
                return false;
            }

            sdata_in[Cloud_32_GET_CLS_BSC.Input.WS_ID] = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            sdata_in[Cloud_32_GET_CLS_BSC.Input.sCLS_ID] = sClsID;
            sdata_in[Cloud_32_GET_CLS_BSC.Input.sASK_COD] = sAskCod;

            return true;
        }
    }

    /***** CloudData_33 Exercise Days of Month Data*****/
    public static class CloudData_33
    {
        public static String[] sdata_in = new String[Cloud_33_GET_EW_DAY.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_33_GET_EW_DAY.sDB_Cmd[3].length];

        public static TreeMap<String, String[]> clound_cmd33_list = new TreeMap<String,String[]>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            clound_cmd33_list.clear();
        }

        public static boolean Set_input(String sdate, String edate){
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";
            String shours="";
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0);
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1);
            shours+=EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2);
            int intValue = Integer.valueOf(shours);
            shours = Integer.toString(intValue);

            return Set_input(seq, seq, sdate, edate, shours, code);
        }

        public static boolean Set_input(String id, String seq, String sdate, String edate, String shours, String code){
            //"WS_ID", "iUSR_SEQ", "sEND_MON", "sSTR_MON", "sASK_COD"
            if(id == null || seq == null || sdate == null || edate == null || code == null){
                return false;
            }
            sdata_in[Cloud_33_GET_EW_DAY.Input.WS_ID] = id;
            sdata_in[Cloud_33_GET_EW_DAY.Input.iUSR_SEQ] = shours;
            sdata_in[Cloud_33_GET_EW_DAY.Input.sEND_MON] = edate;
            sdata_in[Cloud_33_GET_EW_DAY.Input.sSTR_MON] = sdate;
            sdata_in[Cloud_33_GET_EW_DAY.Input.sHOURS] = seq;
            sdata_in[Cloud_33_GET_EW_DAY.Input.sASK_COD] = code;

            return true;
        }


    }

    /***** CloudData_34 Exercise Month data*****/
    public static class CloudData_34{
        public static String[] sdata_in = new String[Cloud_34_GET_EW_MON.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_34_GET_EW_MON.sDB_Cmd[3].length];

        public static TreeMap<String, String[]> clound_cmd34_list = new TreeMap<String, String[]>(Collections.reverseOrder());

        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){ sdata_in[i] = ""; }
            for(int i = 0; i < sdata_out.length; i++){ sdata_out[i] = ""; }
            clound_cmd34_list.clear();
        }

        public static boolean Set_input(String sort, String sdate, String edate){
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String code = "1";
            return Set_input(seq, seq, sort, sdate, edate, code);
        }

        public static boolean Set_input(String id, String seq, String desc, String sdate, String edate, String code){
            //"WS_ID", "iUSR_SEQ", "rDESC", "sSTR_YM", "sEND_YM", "sASK_COD"
            if(id == null || seq == null || desc == null || sdate == null || edate == null || code == null){ return false; }
            sdata_in[Cloud_34_GET_EW_MON.Input.WS_ID] = id;
            sdata_in[Cloud_34_GET_EW_MON.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_34_GET_EW_MON.Input.rDESC] = desc;
            sdata_in[Cloud_34_GET_EW_MON.Input.sSTR_YM] = sdate;
            sdata_in[Cloud_34_GET_EW_MON.Input.sEND_YM] = edate;
            sdata_in[Cloud_34_GET_EW_MON.Input.sASK_COD] = code;
            return true;
        }

    }

    /***** CloudData_37 modify password *****/
    public static class CloudData_37{
        public static String[] sdata_in = new String[Cloud_37_UPD_MDF_PW.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_37_UPD_MDF_PW.sDB_Cmd[3].length];
        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String spw){
            boolean bResult;
            String seq = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            String opw = CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_PW);
            String code = "1";
            bResult = Set_input(seq, spw, opw, code);
            return bResult;
        }

        public static boolean Set_input(String usr_id, String newpw, String oldwpw, String sAskCod){
            //"WS_ID", "New_USR_PW", "sUSR_PW", "sASK_COD"
            if(usr_id == null || newpw == null || oldwpw == null || sAskCod == null){ return false; }
            sdata_in[Cloud_37_UPD_MDF_PW.Input.WS_ID] = usr_id;
            sdata_in[Cloud_37_UPD_MDF_PW.Input.sNEW_USR_PW] = newpw;
            sdata_in[Cloud_37_UPD_MDF_PW.Input.iUSR_SEQ] = usr_id;
            sdata_in[Cloud_37_UPD_MDF_PW.Input.sUSR_PW] = oldwpw;
            sdata_in[Cloud_37_UPD_MDF_PW.Input.sASK_COD] = sAskCod;
            return true;
        }
    }

    public static class CLOUD_GYM_MY_CLASS_INFO{
        public String sCLS_DEL;     //0 : Normal   ;   1 : Stop Class
        public String sBKG_SEQ;     //ID
        public String sCLS_NAME;    //Class Name
        public String sCLS_SUC_DT;  //Class Date
        public String sCLS_STR_TIM; //Class Start Time
        public String sCLS_END_TIM; //Class End Time
        public String sCLS_PHO;     //Class Photo
        public String sCLS_ID;      //Class ID
        public String sCLS_TCH_NAM;     //Class Teacker Name
        public String sO_CLS_STR_TIM;   //Class First booking Time
        public String sREAD_TAG;        //User already read alarm
        public String sUser_CLS_SUC_DT;
        public String sUser_CLS_STR_TIM;
        public String sUser_CLS_END_TIM;
        public Calendar cUser_CLS_STR;
    }

    /***** CloudData_39 Get Gym MyClass List *****/
    public static class CloudData_39{
        public static String[] sdata_in = new String[Cloud_39_GET_GYM_BKI.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_39_GET_GYM_BKI.sDB_Cmd[3].length];
        public static ArrayList<CLOUD_GYM_MY_CLASS_INFO>  list_CloudMyClassInfo = new ArrayList<>();
        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
            list_CloudMyClassInfo.clear();
        }

        public static boolean Set_input(String sGymID , Calendar cStart , Calendar cEnd){
            boolean bResult;
            Calendar cSearchStart = (Calendar)cStart.clone();
            Calendar cSearchEnd = (Calendar)cEnd.clone();
            cSearchStart.add(Calendar.DATE,-1);
            cSearchEnd.add(Calendar.DATE,1);
            String code = "1";
            String sDate_Start = Rtx_Calendar.sCalendar2Str(cSearchStart,"yyyy-MM-dd");
            String sDate_End = Rtx_Calendar.sCalendar2Str(cSearchEnd,"yyyy-MM-dd");
            bResult = Set_input(sGymID, sDate_Start, sDate_End, code);
            return bResult;
        }

        public static boolean Set_input(String sGymID, String sDate_Start, String sDate_End, String sAskCod){
            if(sGymID == null || sDate_Start == null || sDate_End == null || sAskCod == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null){ return false; }
            sdata_in[Cloud_39_GET_GYM_BKI.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_39_GET_GYM_BKI.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_39_GET_GYM_BKI.Input.sGYM_ID] = sGymID;
            sdata_in[Cloud_39_GET_GYM_BKI.Input.dSTR_TIM] = sDate_Start;
            sdata_in[Cloud_39_GET_GYM_BKI.Input.dEND_TIM] = sDate_End;
            sdata_in[Cloud_39_GET_GYM_BKI.Input.sASK_COD] = sAskCod;
            return true;
        }
    }

    /***** CloudData_42 Delete Workout Item *****/
    public static class CloudData_42{
        public static String[] sdata_in = new String[Cloud_42_DEL_WRK_ITV_SET.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_42_DEL_WRK_ITV_SET.sDB_Cmd[3].length];

        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sMchTyp , String sWrkId , String sWrkName){
            boolean bResult;
            String code = "1";
            bResult = Set_input(sMchTyp, sWrkId, sWrkName, code);
            return bResult;
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, String sAskCod){
            if(sMCH_TYP == null || sWRK_ID == null || sWRK_NAM == null || sAskCod == null){ return false; }
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.WS_ID] = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.iUSR_SEQ] = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.sMCH_TYP] = sMCH_TYP;
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.sWRK_ID] = sWRK_ID;
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.sWRK_NAM] = sWRK_NAM;
            sdata_in[Cloud_42_DEL_WRK_ITV_SET.Input.sASK_COD] = sAskCod;
            return true;
        }
    }

    /***** CloudData_43 *****/
    public static class CloudData_43{
        public static String[] sdata_in = new String[Cloud_43_DB_TRD_SET.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_43_DB_TRD_SET.sDB_Cmd[3].length];

        public static void clear(){
            for(int i = 0; i < sdata_in.length; i++){
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++){
                sdata_out[i] = "";
            }
        }

        public static void v_Set_input_index(int index, String str){
            if(str != null && index < sdata_in.length){
                sdata_in[index] = str;
            }
        }

        public static boolean Set_input(String sappid, String sid, String spw, String strdof){
            boolean bResult;

            String sname = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);

            bResult = Set_input(sname, sappid, sid, spw, "", strdof);

            return bResult;
        }


        public static boolean Set_input(String sname, String sappid, String sid, String spw, String stkn, String strdof)
        {
            String code = "1";

            //"WS_ID", "sUSR_ID", "ssTRD_PTY_APP", "sTRD_PTY_ID", "sTRD_PTY_PW", "sTRD_TKN", "sTRD_OF", "sASK_COD"
            if(sname == null || sappid == null || sid == null || spw == null || spw == null || strdof == null)
            {
                return false;
            }

            sdata_in[Cloud_43_DB_TRD_SET.Input.WS_ID] = sname;
            sdata_in[Cloud_43_DB_TRD_SET.Input.iUSR_SEQ] = sname;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sTRD_PTY_APP] = sappid;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sTRD_PTY_ID] = sid;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sTRD_PTY_PW] = spw;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sTRD_TKN] = stkn;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sTRD_OF] = strdof;
            sdata_in[Cloud_43_DB_TRD_SET.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_44 *****/
    public static class CloudData_44
    {
        public static String[] sdata_in = new String[Cloud_44_INS_USR_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_44_INS_USR_BSC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static void v_Set_input_index(int index, String str)
        {
            if(str != null && index < sdata_in.length) {
                sdata_in[index] = str;
            }
        }

        public static boolean Set_input(String sname, String sbirth, String shig, String smal, String spw, String sex, String sunit)
        {
            String code = "1";

            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(sname == null || sbirth == null || shig == null || smal == null || spw == null || sex == null || sunit == null)
            {
                return false;
            }

            sdata_in[Cloud_44_INS_USR_BSC.Input.WS_ID] = sname;
            sdata_in[Cloud_44_INS_USR_BSC.Input.dUSR_BIR] = sbirth;
            sdata_in[Cloud_44_INS_USR_BSC.Input.fUSR_HIG] = shig;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_ID] = smal;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_MAL] = smal;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_NAM] = sname;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_PW] = spw;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_SEX] = sex;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sUSR_WEI] = "";
            sdata_in[Cloud_44_INS_USR_BSC.Input.fBDY_FAT] = "";
            sdata_in[Cloud_44_INS_USR_BSC.Input.sWEI_UNT] = sunit;
            sdata_in[Cloud_44_INS_USR_BSC.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_45 Upload User Info*****/
    public static class CloudData_45
    {
        public static String[] sdata_in = new String[Cloud_45_UPD_USR_BSC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_45_UPD_USR_BSC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static void v_Set_input_index(int index, String str)
        {
            if(str != null && index < sdata_in.length) {
                sdata_in[index] = str;
            }
        }

        public static boolean Set_input()
        {
            String code = "1";

            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(CloudData_20.sdata_out == null || CloudData_17.sdata_out == null)
            {
                return false;
            }

            sdata_in[Cloud_45_UPD_USR_BSC.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.dUSR_BIR] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_BIR];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.fUSR_HIG] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_HIG];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.iUSE_B] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.iUSE_E] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.iUSE_R] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.iUSE_T] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sLOV_TV] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.LOV_TV];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sMY_PAG] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.MY_PAG];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sMY_PAG_OF] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.MY_PAG_OF];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sUSR_MAL] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_MAL];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sUSR_NAM] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_NAM];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sUSR_SEX] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_SEX];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sUSR_WEI] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.fBDY_FAT] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sWEI_UNT] = CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.WEI_UNT];
            sdata_in[Cloud_45_UPD_USR_BSC.Input.sASK_COD] = "1";

            return true;
        }
    }

    /***** CloudData_46 Add Interval Data(Workout or Hiit) *****/
    public static class CloudData_46
    {
        public static String[] sdata_in = new String[Cloud_46_INS_WRK_ITV_SET.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_46_INS_WRK_ITV_SET.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage)
        {
            boolean bResult = true;

            String code = "1";
            bResult = Set_input(sMCH_TYP, sWRK_ID, sWRK_NAM, list_Stage, code);

            return bResult;
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage, String sAskCod)
        {
            if(sMCH_TYP == null || sWRK_ID == null || sWRK_NAM == null || list_Stage == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null|| sAskCod == null )
            {
                return false;
            }

            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.sMCH_TYP] = sMCH_TYP;
            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.sWRK_ID] = sWRK_ID;
            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.sWRK_NAM] = sWRK_NAM;
            sdata_in[Cloud_46_INS_WRK_ITV_SET.Input.sASK_COD] = sAskCod;

            if(list_Stage != null)
            {
                int iCloudIndex = 2;
                int iIndex = 0;
                int iSize = 50;
                int iListSize = list_Stage.size();

                for( ; iIndex < iSize ; iIndex++)
                {
                    if(iIndex < iListSize)
                    {
                        sdata_in[iCloudIndex] = list_Stage.get(iIndex);
                    }
                    else
                    {
                        sdata_in[iCloudIndex] = new String("0,0,00:00");
                    }

                    iCloudIndex++;
                }
            }

            return true;
        }
    }

    /***** CloudData_47 Add Class To My Class *****/
    public static class CloudData_47
    {
        public static String[] sdata_in = new String[Cloud_47_INS_BKG_CLS.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_47_INS_BKG_CLS.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(Calendar cClsTime , String sClsID)
        {
            boolean bResult;

            String code = "1";
            String sClsTime = Rtx_Calendar.sCalendar2Str(cClsTime,"yyyy-MM-dd HH:mm:ss");


            bResult = Set_input(sClsTime, sClsID, code);

            return bResult;
        }

        public static boolean Set_input(String sClsTime,
                                        String sClsID,
                                        String sAskCod
        )
        {
            if(sClsTime == null || sClsID == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null)
            {
                return false;
            }

            sdata_in[Cloud_47_INS_BKG_CLS.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_47_INS_BKG_CLS.Input.dCLS_TIM] = sClsTime;
            sdata_in[Cloud_47_INS_BKG_CLS.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_47_INS_BKG_CLS.Input.sCLS_ID] = sClsID;
            sdata_in[Cloud_47_INS_BKG_CLS.Input.sASK_COD] = sAskCod;

            return true;
        }
    }

    /***** CloudData_48 Update Interval Data(Workout or Hiit) *****/
    public static class CloudData_48
    {
        public static String[] sdata_in = new String[Cloud_48_UPD_WRK_ITV_SET.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_48_UPD_WRK_ITV_SET.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage)
        {
            boolean bResult = true;

            String code = "1";
            bResult = Set_input(sMCH_TYP, sWRK_ID, sWRK_NAM, list_Stage, code);

            return bResult;
        }

        public static boolean Set_input(String sMCH_TYP, String sWRK_ID, String sWRK_NAM, ArrayList<String> list_Stage, String sAskCod)
        {
            if(sMCH_TYP == null || sWRK_ID == null || sWRK_NAM == null || list_Stage == null || CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ] == null|| sAskCod == null )
            {
                return false;
            }

            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.sMCH_TYP] = sMCH_TYP;
            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.sWRK_ID] = sWRK_ID;
            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.sWRK_NAM] = sWRK_NAM;
            sdata_in[Cloud_48_UPD_WRK_ITV_SET.Input.sASK_COD] = sAskCod;

            if(list_Stage != null)
            {
                int iCloudIndex = 2;
                int iIndex = 0;
                int iSize = 50;
                int iListSize = list_Stage.size();

                for( ; iIndex < iSize ; iIndex++)
                {
                    if(iIndex < iListSize)
                    {
                        sdata_in[iCloudIndex] = list_Stage.get(iIndex);
                    }
                    else
                    {
                        sdata_in[iCloudIndex] = new String("0,0,00:00");
                    }

                    iCloudIndex++;
                }
            }

            return true;
        }
    }

    /***** CloudData_50 Add Goal-Colse*****/
    public static class CloudData_50
    {
        public static String[] sdata_in = new String[Cloud_50_DB_EXC_GOL_TGE.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_50_DB_EXC_GOL_TGE.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static void v_Set_input_index(int index, String str)
        {
            if(str != null && index < sdata_in.length) {
                sdata_in[index] = str;
            }
        }

        public static boolean Set_input(CLOUD_TATGET_GOAL_CLOSE goal_Close)
        {
            String code = "1";

            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(CloudData_20.sdata_out == null && goal_Close == null)
            {
                return false;
            }

            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.WS_ID] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.dEND_TIM] = goal_Close.sEND_TIM;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.dGOL_STR_DT] = goal_Close.sGOL_STR_DT;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.iUSR_SEQ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_CLS_LVL] = goal_Close.sGOL_CLS_LVL;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_DAT_1] = goal_Close.sGOL_DAT_1;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_DAT_2] = goal_Close.sGOL_DAT_2;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_ITM] = goal_Close.sGOL_ITM;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_RST] = goal_Close.sGOL_RST;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_STS] = goal_Close.sGOL_STS;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_TYP] = goal_Close.sGOL_TYP;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sGOL_SEQ] = goal_Close.sGOL_SEQ;
            sdata_in[Cloud_50_DB_EXC_GOL_TGE.Input.sASK_COD] = "1";

            return true;
        }
    }

    /***** CloudData_59 Get Goal-Close Info*****/
    public static class CloudData_59
    {
        public static String[] sdata_in = new String[Cloud_59_GET_EXC_GOL_TGE.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_59_GET_EXC_GOL_TGE.sDB_Cmd[3].length];
        public static ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE>  list_CloudTargetGoal = new ArrayList<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

            list_CloudTargetGoal.clear();
        }

        public static boolean Set_input(int iCount)
        {
            boolean bResult = false;

            bResult = Set_input();
            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.icnt ] = Rtx_TranslateValue.sInt2String(iCount);

            return bResult;
        }

        public static boolean Set_input()
        {
            String code = "1";

            //"WS_ID", "sUSR_ID", "sASK_COD"

            if(CloudData_20.sdata_out == null )
            {
                return false;
            }

            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.WS_ID ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.iUSR_SEQ ] = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.dEND_TIM ] = Rtx_Calendar.sTodayCalendar2Str("yyyy-MM-dd");
            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.icnt ] = "99";
            sdata_in[Cloud_59_GET_EXC_GOL_TGE.Input.sASK_COD] = "1";

            return true;
        }

    }

    /***** CloudData_60 *****/
    public static class CloudData_60
    {
        public static String[] sdata_in = new String[Cloud_60_GET_END_WRK_FQC.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_60_GET_END_WRK_FQC.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

        }

        public static boolean Set_input(Calendar calStart, Calendar calEnd)
        {
            String code = "1";
            String seq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];
            String sStartDate = Rtx_Calendar.sCalendar2Str(calStart,"yyyy-MM-dd");
            String sEndDate = Rtx_Calendar.sCalendar2Str(calEnd,"yyyy-MM-dd");


            return Set_input(seq, seq, sStartDate, sEndDate, code);
        }

        public static boolean Set_input(String id, String seq, String sSDate, String sEDate, String code)
        {
            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(id == null || seq == null || sSDate == null || sEDate == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_60_GET_END_WRK_FQC.Input.WS_ID] = id;
            sdata_in[Cloud_60_GET_END_WRK_FQC.Input.dEND_DT] = sEDate;
            sdata_in[Cloud_60_GET_END_WRK_FQC.Input.dSTR_DT] = sSDate;
            sdata_in[Cloud_60_GET_END_WRK_FQC.Input.iUSR_SEQ] = seq;
            sdata_in[Cloud_60_GET_END_WRK_FQC.Input.sASK_COD] = code;

            return true;
        }

    }

    /***** CloudData_62 user third party apps *****/
    public static class CloudData_62
    {
        public static String[] sdata_in = new String[Cloud_62_GET_TRD_TKN.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_62_GET_TRD_TKN.sDB_Cmd[3].length];
        public static TreeMap<Integer, String[]> tree_clound_cmd62_result = new TreeMap<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input()
        {
            boolean bResult;
            String seq = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            String code = "1";

            bResult = Set_input(seq, code);

            return bResult;
        }

        public static boolean Set_input(String usr_id,
                                        String sAskCod
        )
        {
            //"WS_ID", "sASK_COD"
            if(usr_id == null || sAskCod == null)
            {
                return false;
            }

            sdata_in[Cloud_62_GET_TRD_TKN.Input.WS_ID] = usr_id;
            sdata_in[Cloud_62_GET_TRD_TKN.Input.iUSR_SEQ] = usr_id;
            sdata_in[Cloud_62_GET_TRD_TKN.Input.sASK_COD] = sAskCod;

            return true;
        }

        public static String[] Create_Init(int app_id, String status )
        {
            String[] sdata = new String[Cloud_62_GET_TRD_TKN.sDB_Cmd[3].length];

            sdata[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_APP] = String.format("%d", app_id);
            sdata[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_ID] = "";
            sdata[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_PW] = "";
            sdata[Cloud_62_GET_TRD_TKN.Output.Token] = "";
            sdata[Cloud_62_GET_TRD_TKN.Output.TKN_TAG] = "1";
            sdata[Cloud_62_GET_TRD_TKN.Output.TRD_OF] = status;
            sdata[Cloud_62_GET_TRD_TKN.Output.LK_NUM] = "1";

            return sdata;
        }

    }

    /***** CloudData_63 *****/
    public static class CloudData_63
    {
        public static String[] sdata_in = new String[Cloud_63_SEN_MAL_PW.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_63_SEN_MAL_PW.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

        }

        public static boolean Set_input(String sname)
        {
            String code = "1";

            return Set_input(sname, sname, code);
        }

        public static boolean Set_input(String id, String sname, String code)
        {
            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(id == null || sname == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_63_SEN_MAL_PW.Input.WS_ID] = id;
            sdata_in[Cloud_63_SEN_MAL_PW.Input.sUSR_ID] = sname;
            sdata_in[Cloud_63_SEN_MAL_PW.Input.sASK_COD] = code;

            return true;
        }

        public static boolean check_is_null()
        {
            boolean bResult = false;

            if(sdata_out[Cloud_63_SEN_MAL_PW.Output.USR_SEQ] == null)
            {
                bResult = true;
            }
            else if(sdata_out[Cloud_63_SEN_MAL_PW.Output.USR_SEQ] == "")
            {
                bResult = true;
            }

            return bResult;
        }
    }

    /***** CloudData_65 user third party apps Enable by Cloud server*****/
    public static class CloudData_65
    {
        public static String[] sdata_in = new String[Cloud_65_GET_TRD_PTY.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_65_GET_TRD_PTY.sDB_Cmd[3].length];
        public static TreeMap<Integer, String[]> tree_clound_cmd65_result = new TreeMap<>();

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
            tree_clound_cmd65_result.clear();
        }

        public static boolean Set_input()
        {
            boolean bResult;
            String seq = CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
            String code = "1";

            bResult = Set_input(seq, code);

            return bResult;
        }

        public static boolean Set_input(String usr_id,
                                        String sAskCod
        )
        {
            //"WS_ID", "sASK_COD"
            if(usr_id == null || sAskCod == null)
            {
                return false;
            }

            sdata_in[Cloud_65_GET_TRD_PTY.Input.WS_ID] = usr_id;
            sdata_in[Cloud_65_GET_TRD_PTY.Input.iUSR_SEQ] = usr_id;
            sdata_in[Cloud_65_GET_TRD_PTY.Input.sASK_COD] = sAskCod;

            return true;
        }

    }

    /***** CloudData_67  Target-close Delete*****/
    public static class CloudData_67
    {
        public static String[] sdata_in = new String[Cloud_67_DEL_EXC_GOL_TGE.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_67_DEL_EXC_GOL_TGE.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }
        }

        public static boolean Set_input(String sGolSeq)
        {
            String code = "1";
            String sUsrSeq = CloudData_20.sdata_out[Cloud_20_CHK_LGI.Output.USR_SEQ];

            return Set_input(sUsrSeq, sGolSeq, code);
        }

        public static boolean Set_input(String sUsrSeq, String sGolSeq, String code)
        {
            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(sUsrSeq == null || sGolSeq == null || code == null)
            {
                return false;
            }

            sdata_in[Cloud_67_DEL_EXC_GOL_TGE.Input.WS_ID] = sUsrSeq;
            sdata_in[Cloud_67_DEL_EXC_GOL_TGE.Input.sGOL_SEQ] = sGolSeq;
            sdata_in[Cloud_67_DEL_EXC_GOL_TGE.Input.sASK_COD] = code;

            return true;
        }
    }

    public static void sortByStringAscending(ArrayList<String[]> list, final int index) {
        Collections.sort(list, new Comparator<String[]>() {
            @Override
            public int compare(String[] lhs, String[] rhs) {
                return (int)(rhs[index].compareTo(lhs[index]));
            }
        });
    }

    public static void sortByStringDescending(ArrayList<String[]> list, final int index) {
        Collections.sort(list, new Comparator<String[]>() {
            @Override
            public int compare(String[] lhs, String[] rhs) {
                return (int)(lhs[index].compareTo(rhs[index]));
            }
        });
    }

    /***** CloudData_69 *****/
    public static class CloudData_69
    {
        public static String[] sdata_in = new String[Cloud_69_GET_ACN_DAT.sDB_Cmd[2].length];
        public static String[] sdata_out = new String[Cloud_69_GET_ACN_DAT.sDB_Cmd[3].length];

        public static void clear()
        {
            for(int i = 0; i < sdata_in.length; i++)
            {
                sdata_in[i] = "";
            }
            for(int i = 0; i < sdata_out.length; i++)
            {
                sdata_out[i] = "";
            }

        }

        public static boolean Set_input(String sUsrId)
        {
            clear();

            String code = "1";

            return Set_input(sUsrId, sUsrId, code);
        }

        public static boolean Set_input(String WS_ID, String sUSR_ID, String sASK_COD)
        {
            //"WS_ID", "sUSR_ID", "sASK_COD"
            if(WS_ID == null || sUSR_ID == null || sASK_COD == null)
            {
                return false;
            }

            sdata_in[Cloud_69_GET_ACN_DAT.Input.WS_ID] = WS_ID;
            sdata_in[Cloud_69_GET_ACN_DAT.Input.sUSR_ID] = sUSR_ID;
            sdata_in[Cloud_69_GET_ACN_DAT.Input.sASK_COD] = sASK_COD;

            return true;
        }

        public static boolean bCheck_IdIsExist()
        {
            boolean bResult = false;

            if(sdata_out[Cloud_69_GET_ACN_DAT.Output.DATA] != null)
            {
                if(sdata_out[Cloud_69_GET_ACN_DAT.Output.DATA].equals("1"))
                {
                    bResult = true;
                }
            }

            return bResult;
        }
    }
}
