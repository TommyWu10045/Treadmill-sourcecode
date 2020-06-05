package com.rtx.treadmill.GlobalData;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retonix.circlecloud.Cloud_45_UPD_USR_BSC;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by jerry on 2017/6/23.
 */

public class CloudDataFunc
{
    public static void setWeight2UserInfo(float fWeight)
    {
        String sVal = Rtx_TranslateValue.sFloat2String(fWeight,3);

        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI] = sVal;
        CloudDataStruct.CloudData_45.v_Set_input_index(Cloud_45_UPD_USR_BSC.Input.sUSR_WEI,sVal);
    }

    public static void setWeight2UserInfo(String sWeight)
    {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI] = sWeight;
        CloudDataStruct.CloudData_45.v_Set_input_index(Cloud_45_UPD_USR_BSC.Input.sUSR_WEI,sWeight);
    }

    public static void setBodyFat2UserInfo(float fBodyFat)
    {
        String sVal = Rtx_TranslateValue.sFloat2String(fBodyFat,3);

        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT] = sVal;
        CloudDataStruct.CloudData_45.v_Set_input_index(Cloud_45_UPD_USR_BSC.Input.fBDY_FAT,sVal);
    }

    public static void setBodyFat2UserInfo(String sBodyFat)
    {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT] = sBodyFat;
        CloudDataStruct.CloudData_45.v_Set_input_index(Cloud_45_UPD_USR_BSC.Input.fBDY_FAT,sBodyFat);
    }
}
