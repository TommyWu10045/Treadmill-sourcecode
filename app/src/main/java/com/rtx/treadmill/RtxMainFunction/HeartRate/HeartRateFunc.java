package com.rtx.treadmill.RtxMainFunction.HeartRate;

import android.content.Context;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;

/**
 * 儲存全域設定
 *
 * @author Tom
 */
public class HeartRateFunc {
    private static String TAG = "Jerry=";
    private static boolean DEBUG = false;

    //siip 0 : kg ; 1 : lb
    public static String get_mass_unit(int siip) {
        String sdata;
        if (siip == 0) {
            sdata = "kg";
        } else {
            sdata = "lb";
        }
        return sdata;
    }

    public static String get_mass_unit(Context mContext) {
        String sdata;

        sdata = EngSetting.Weight.getUnitString(mContext);

        return sdata;
    }

    public static String get_unit(Context mContext, int ikey) {
        String sdata;

        sdata = LanguageData.s_get_string(mContext, ikey);

        return sdata;
    }

    public static int[] v_calculate_bpm(int imode, float fage)
    {
        int[] idata = new int[3]  ;//min, max, def

        switch (imode)
        {
            case 0:
                idata[0] = (int) ((220f - fage) * 0.60f);
                idata[1] = (int) ((220f - fage) * 0.70f);
                idata[2] = (int) ((220f - fage) * 0.65f);
                break;
            case 1:
                idata[0] = (int) ((220f - fage) * 0.71f);
                idata[1] = (int) ((220f - fage) * 0.80f);
                idata[2] = (int) ((220f - fage) * 0.75f);
                break;
            case 2:
                idata[0] = (int) ((220f - fage) * 0.81f);
                idata[1] = (int) ((220f - fage) * 0.90f);
                idata[2] = (int) ((220f - fage) * 0.85f);
                break;
            case 3:
                idata[0] = 80;
                idata[1] = 200;
                idata[2] = 120;
                break;
            default:
                idata[0] = 80;
                idata[1] = 200;
                idata[2] = 120;
                break;
        }
        return idata;
    }



}
