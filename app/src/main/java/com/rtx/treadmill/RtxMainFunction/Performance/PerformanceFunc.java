package com.rtx.treadmill.RtxMainFunction.Performance;

import com.rtx.treadmill.R;

/**
 * 儲存全域設定
 *
 * @author Tom
 */
public class PerformanceFunc {
    private static String TAG="Jerry=";
    private static boolean DEBUG=false;

    public static String[] ssession_EXC_MDL_icon_name = {
            "Running",
            "Elliptical",
            "Bike",
            "OManual",
    };

    public static int[] isession_EXC_MDL_icon_id = {
            R.drawable.session_treadmill,
            R.drawable.exercise_type_e,
            R.drawable.exercise_type_b,
            R.drawable.exercise_type_o,
    };

    public static String[] ssession_MCH_TYP_icon_name = {
            "Treadmill",
            "Elliptical",
            "Upright Bike",
            "Recumbent Bike",
            "Nike+",
            "Garmin",
            "Mymaprun",
            "Mymapwalk",
            "Mymapride",
            "Google Fit",
            "Runkeeper",
            "Fitbit",
            "Jawbon",
            "Manual Name",
    };

    public static int[] isession_MCH_TYP_icon_id = {
            R.drawable.exercise_type_r,
            R.drawable.session_elliptical,
            R.drawable.session_ubike,
            R.drawable.session_rbike,
            R.drawable.session_nikeplus,
            R.drawable.session_garmin,
            R.drawable.session_mymaprun,
            R.drawable.session_mymapwalk,
            R.drawable.session_mymapride,
            R.drawable.session_googlefit,
            R.drawable.session_runkeeper,
            R.drawable.session_fitbit,
            R.drawable.session_jawbone,
            R.drawable.session_manual,
    };
}
