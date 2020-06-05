package com.rtx.treadmill.GlobalData;

import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;

import java.util.ArrayList;

/**
 * 儲存全域設定
 * @author Tom
 *
 */

public class CloudCmd_Info
{
	public static int iCmd_index = 0;
	public static ArrayList<S_CMD> CloudCMD_List = new ArrayList<S_CMD>();
	public static S_CMD sCmd;

	public static void clear()
	{
		iCmd_index = 0;
		CloudCMD_List.clear();
		sCmd = null;
	}

	public static class S_CMD{
		public int icmd ;
		public int iretry;
	}

	public static void vCloudCmd_Add(int icmd, int iretry) {
		S_CMD scmd = new S_CMD();

		scmd.icmd = icmd;
		scmd.iretry = iretry;

		CloudCMD_List.add(scmd);
	}

	public static void vCloudCmd_clean_all() {
		CloudCMD_List.clear();
		iCmd_index = 0;
	}


	///////////////////////////////////////////
    public static void vUpdate_Device03_info()
    {
        float fval;

        fval = EngSetting.f_Get_ENG_DEV_DISTANCE()
				+ ExerciseGenfunc.f_get_total_distance_km_eng()
				+ ExerciseGenfunc.f_get_total_distance_km();
        EngSetting.v_Set_ENG_DEV_DISTANCE(fval);

        fval = EngSetting.f_Get_ENG_DEV_TIME()
				+ ((float)ExerciseGenfunc.i_get_total_time_eng() / 3600f)
				+ ((float)ExerciseGenfunc.i_get_total_time() / 3600f);
        EngSetting.v_Set_ENG_DEV_TIME(fval);

		Perf.v_Update_Perf_Device_Info(GlobalData.global_context);

        if(CloudDataStruct.CloudData_27.bgetdata) {
            CloudCmd_Info.vCloudCmd_Add(cloudglobal.iDB_DEV_BSC03, 1);
        }
        else
        {
            CloudCmd_Info.vCloudCmd_Add(cloudglobal.iGET_DEV_BSC01, 1);
        }
    }

}
