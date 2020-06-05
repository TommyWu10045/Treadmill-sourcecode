package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBDY_FAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBMI;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBMR;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBON_MAS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fECR_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fITR_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fLFT_ARF_FMS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fLFT_ARM_FAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fLFT_LGF_FMS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fLFT_LGT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fMinerals;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fOBY_DGE;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fProtein;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fRIG_LGF_FMS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fRIT_ARF_FMS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fRIT_ARM_FAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fRIT_LGT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fTAL_BDY_WAT;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fTRK_FAT_MAS;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fTrunk_fat;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fVCL_FAT_RTG;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fWIT_HI_RAO;
import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fWeight;


/**
 * 儲存全域設定
 *
 * @author Tom
 */
public class BodyManagerFunc {
    private static String TAG = "Jerry=";
    private static boolean DEBUG = false;

    /*
    public static String s_get_bodymanage_title(Context mContext, int imode)
    public static String s_get_bodymanage_history_key(Context mContext, int imode)

    public static String get_body_gat_unit(String str, int siip)
    public static String s_get_bodymanage_unit(Context mContext, int imode)

    public static String s_get_bodymanage_info(Context mContext, int imode)
    public static String s_get_bodymanage_infolist(Context mContext, int imode)

    public static float f_get_drawdata_string(Context mContext, int imode, String str, int siip)
    public static String s_get_05rowdata(int imode)
    public static float f_get_bodymanage_rawdata(Context mContext, int imode)
    public static String s_get_bodymanage_rawdata(Context mContext, int imode)

    public static int i_get_bodymanage_vertical_type(Context mContext, int imode)
    public static int i_get_bodymanage_point(Context mContext, int imode, int itype)
    public static float fget_body_fat_val(String smode, String str, int siip, int ipoint)

    public static String s_get_drawdata_string(Context mContext, int imode, String str)
    public static String s_get_drawdata(Context mContext, int imode)

    public static boolean is_edit(Context mContext, int imode)
    public static float[] f_get_bar_rangers(Context mContext, int imode)
    public static String[] s_get_bar_Strings(Context mContext, int imode)
    public static int[] i_get_bar_colors(Context mContext, int imode)
    public static int i_get_modify_numbers(Context mContext, int imode, int iadd)
    public static int i_check_modify_range(Context mContext, int imode, float fval)
    public static String s_get_bodymanage_warring(Context mContext, int imode)
    public static String s_healthy_bone_mass(Context mContext, int imode)

     */


    // return get title string
    public static String s_get_bodymanage_title(Context mContext, int imode) {
        String sdata;

        switch (imode) {
            case fWeight:
                sdata = LanguageData.s_get_string(mContext, R.string.weight);
                break;
            case fBDY_FAT:
                sdata = LanguageData.s_get_string(mContext, R.string.bodyfat_percent);
                break;
            case fBMI:
                sdata = LanguageData.s_get_string(mContext, R.string.bmi);
                break;
            case fBMR:
                sdata = LanguageData.s_get_string(mContext, R.string.bmr);
                break;
            case fTAL_BDY_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.total_body_water);
                break;
            case fVCL_FAT_RTG:
                sdata = LanguageData.s_get_string(mContext, R.string.visceral_fat_rating);
                break;
            case fBON_MAS:
                sdata = LanguageData.s_get_string(mContext, R.string.bone_mass);
                break;
            case fITR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.intracellular_water);
                break;
            case fECR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.extracellular_water);
                break;
            case fProtein:
                sdata = LanguageData.s_get_string(mContext, R.string.protein);
                break;
            case fMinerals:
                sdata = LanguageData.s_get_string(mContext, R.string.mineral);
                break;
            case fWIT_HI_RAO:
                sdata = LanguageData.s_get_string(mContext, R.string.waist_hip_ratio);
                break;
            case fOBY_DGE:
                sdata = LanguageData.s_get_string(mContext, R.string.obesity_degree);
                break;
            case fTRK_FAT_MAS:
            case fTrunk_fat:
                sdata = LanguageData.s_get_string(mContext, R.string.trunk);
                break;
            case fLFT_ARF_FMS:
            case fLFT_ARM_FAT:
                sdata = LanguageData.s_get_string(mContext, R.string.left_arm);
                break;
            case fRIT_ARF_FMS:
            case fRIT_ARM_FAT:
                sdata = LanguageData.s_get_string(mContext, R.string.right_arm);
                break;
            case fLFT_LGF_FMS:
            case fLFT_LGT:
                sdata = LanguageData.s_get_string(mContext, R.string.left_leg);
                break;
            case fRIG_LGF_FMS:
            case fRIT_LGT:
                sdata = LanguageData.s_get_string(mContext, R.string.right_leg);
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    // return get history key word
    public static String s_get_bodymanage_history_key(Context mContext, int imode) {
        String sdata;

        switch (imode) {
            case fBMI:
                sdata = "BMI";
                break;
            case fVCL_FAT_RTG:
                sdata = "VCL_FAT_RTG";
                break;
            case fBMR:
                sdata = "BMR";
                break;
            case fBDY_FAT:
                sdata = "BDY_FAT";
                break;
            case fTAL_BDY_WAT:
                sdata = "TAL_BDY_WAT";
                break;
            case fOBY_DGE:
                sdata = "OBY_DGE";
                break;
            case fBON_MAS:
                sdata = "BON_MAS";
                break;
            case fWeight:
                sdata = "Weight";
                break;
            case fProtein:
                sdata = "Protein";
                break;
            case fMinerals:
                sdata = "Minerals";
                break;
            case fWIT_HI_RAO:
                sdata = "WIT_HI_RAO";
                break;
            case fECR_WAT:
                sdata = "ECR_WAT";
                break;
            case fITR_WAT:
                sdata = "ITR_WAT";
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    //circle mode %; inbody mode kg/lb
    public static String get_body_gat_unit(Context mContext, int siip) {
        String sdata;
        int itype = iget_body_machine_type();

        if (itype == 0 || itype == 2) {
            sdata = "%";
        } else {
            sdata = EngSetting.Weight.getUnitString(mContext);
        }
        return sdata;
    }

    //retrun unit
    public static String s_get_bodymanage_unit(Context mContext, int imode) {
        String sdata = "";
        int siip = EngSetting.getUnit();

        switch (imode) {
            case fWeight:
            case fBON_MAS:
            case fITR_WAT:
            case fECR_WAT:
            case fProtein:
            case fMinerals:
            case fTRK_FAT_MAS:
            case fLFT_ARF_FMS:
            case fRIT_ARF_FMS:
            case fLFT_LGF_FMS:
            case fRIG_LGF_FMS:
                sdata = EngSetting.Weight.getUnitString(mContext);
                break;
            case fBDY_FAT:
                sdata = "%";
                break;
            case fTrunk_fat:
            case fLFT_ARM_FAT:
            case fRIT_ARM_FAT:
            case fLFT_LGT:
            case fRIT_LGT:
                sdata = get_body_gat_unit(mContext, siip);
                break;
            case fBMI:
                sdata = "";
                break;
            case fBMR:
                sdata = "kcal";
                break;
            case fTAL_BDY_WAT:
                sdata = "%";
                break;
            case fVCL_FAT_RTG:
                sdata = "";
                break;
            case fWIT_HI_RAO:
                sdata = "";
                break;
            case fOBY_DGE:
                sdata = "%";
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    //return infomation string
    public static String s_get_bodymanage_info_subtitle(Context mContext, int imode) {
        String sdata;

        switch (imode) {
            case fWeight:
                sdata = LanguageData.s_get_string(mContext, R.string.info_weight_subtitle);
                break;
            case fBDY_FAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bodyfat_percent_subtitle);
                break;
            case fBMI:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bmi_subtitle);
                break;
            case fBMR:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bmr_subtitle);
                break;
            case fTAL_BDY_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_total_body_water_subtitle);
                break;
            case fVCL_FAT_RTG:
                sdata = LanguageData.s_get_string(mContext, R.string.info_visceral_fat_rating_subtitle);
                break;
            case fBON_MAS:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bone_mass_subtitle);
                break;
            case fITR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_intracellular_water_subtitle);
                break;
            case fECR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_extracellular_water_subtitle);
                break;
            case fProtein:
                sdata = LanguageData.s_get_string(mContext, R.string.info_protein_subtitle);
                break;
            case fMinerals:
                sdata = LanguageData.s_get_string(mContext, R.string.info_mineral_subtitle);
                break;
            case fWIT_HI_RAO:
                sdata = LanguageData.s_get_string(mContext, R.string.info_waist_hip_ratio_subtitle);
                break;
            case fOBY_DGE:
                sdata = LanguageData.s_get_string(mContext, R.string.info_obesity_degree_subtitle);
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    //return infomation string
    public static String s_get_bodymanage_info(Context mContext, int imode) {
        String sdata;

        switch (imode) {
            case fWeight:
                sdata = LanguageData.s_get_string(mContext, R.string.info_weight);
                break;
            case fBDY_FAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bodyfat_percent);
                break;
            case fBMI:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bmi);
                break;
            case fBMR:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bmr);
                break;
            case fTAL_BDY_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_total_body_water);
                break;
            case fVCL_FAT_RTG:
                sdata = LanguageData.s_get_string(mContext, R.string.info_visceral_fat_rating);
                break;
            case fBON_MAS:
                sdata = LanguageData.s_get_string(mContext, R.string.info_bone_mass);
                break;
            case fITR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_intracellular_water);
                break;
            case fECR_WAT:
                sdata = LanguageData.s_get_string(mContext, R.string.info_extracellular_water);
                break;
            case fProtein:
                sdata = LanguageData.s_get_string(mContext, R.string.info_protein);
                break;
            case fMinerals:
                sdata = LanguageData.s_get_string(mContext, R.string.info_mineral);
                break;
            case fWIT_HI_RAO:
                sdata = LanguageData.s_get_string(mContext, R.string.info_waist_hip_ratio);
                break;
            case fOBY_DGE:
                sdata = LanguageData.s_get_string(mContext, R.string.info_obesity_degree);
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    //return info_list Title string
    public static String s_get_bodymanage_infolist(Context mContext, int imode) {
        String sdata;

        switch (imode) {
            case fWeight:
                sdata = "bd_weight";
                break;
            case fBDY_FAT:
                sdata = "bd_body_fat";
                break;
            case fBMI:
                sdata = "bd_bmi";
                break;
            case fBMR:
                sdata = "bd_bmr";
                break;
            case fTAL_BDY_WAT:
                sdata = "bd_ttbw";
                break;
            case fVCL_FAT_RTG:
                sdata = "bd_vfr";
                break;
            case fBON_MAS:
                sdata = "bd_bone_mass";
                break;
            case fITR_WAT:
                sdata = "bd_icw";
                break;
            case fECR_WAT:
                sdata = "bd_ecw";
                break;
            case fProtein:
                sdata = "bd_protein";
                break;
            case fMinerals:
                sdata = "bd_minerals";
                break;
            case fWIT_HI_RAO:
                sdata = "bd_whr";
                break;
            case fOBY_DGE:
                sdata = "bd_obesity_degree";
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    // if str is not numeric ; return -1f
    //siip 0 : 不轉換; 1 : kg2lb； 2 : lb2kg
    public static float f_get_drawdata_string(Context mContext, int imode, String str, int siip) {
        float fdata;

        switch (imode) {
            case fVCL_FAT_RTG://VFR
            case fBMR://BMR
            case fBMI://BMI
            case fOBY_DGE://OD
            case fTAL_BDY_WAT://TTBW
            case fWIT_HI_RAO: //WHR
            case fBDY_FAT://BF
                fdata = Rtx_TranslateValue.fMass_String2Float(0, str);
                break;
            case fWeight://WEIGHT
            case fBON_MAS://BONE MASS
            case fProtein: //PROTEIN
            case fMinerals: //MINERAL
            case fECR_WAT: //EXTRACELLULAR WATER
            case fITR_WAT: //INTRACELLULAR WATER
            case fTRK_FAT_MAS:
            case fLFT_ARF_FMS:
            case fRIT_ARF_FMS:
            case fLFT_LGF_FMS:
            case fRIG_LGF_FMS:
                fdata = Rtx_TranslateValue.fMass_String2Float(siip, str);
                break;
            case fTrunk_fat:
            case fLFT_ARM_FAT:
            case fRIT_ARM_FAT:
            case fLFT_LGT:
            case fRIT_LGT:
                fdata = fget_body_fat_val(str, siip);
                break;
            default:
                fdata = 0;
                break;
        }

        return fdata;
    }

    //return RawData
    public static String s_get_05rowdata(int imode) {
        String sdata;

        sdata = CloudDataStruct.BodyIndexData_05.sdata_in[imode];

        return sdata;
    }

    //return -1 if data is not numeric; RawData
    public static float f_get_bodymanage_rawdata(Context mContext, int imode) {
        float fdata = -1f;

        try {
            fdata = Float.parseFloat(s_get_05rowdata(imode));
        } catch (Exception e) {

        }
        return fdata;
    }

    //return "-" if data is not numeric; RawData
    public static String s_get_bodymanage_rawdata(Context mContext, int imode) {
        String sdef = "-";
        String sdata ;
        float fval;

        switch (imode) {
            case fWeight:
            case fBDY_FAT:
            case fBMI:
            case fBMR:
            case fTAL_BDY_WAT:
            case fVCL_FAT_RTG:
            case fBON_MAS:
            case fITR_WAT:
            case fECR_WAT:
            case fProtein:
            case fMinerals:
            case fWIT_HI_RAO:
            case fOBY_DGE:

            case fTRK_FAT_MAS:
            case fLFT_ARF_FMS:
            case fRIT_ARF_FMS:
            case fLFT_LGF_FMS:
            case fRIG_LGF_FMS:

            case fTrunk_fat:
            case fLFT_ARM_FAT:
            case fRIT_ARM_FAT:
            case fLFT_LGT:
            case fRIT_LGT:
                fval = f_get_bodymanage_rawdata(mContext, imode);
                break;
            default:
                fval = -1f;
                break;
        }

        if(fval == -1)
        {
            sdata = sdef;
        }
        else
        {
            sdata = s_get_05rowdata(imode);
        }

        return sdata;
    }

    //0 : null; 1 : val; 2 : bar
    public static int i_get_bodymanage_vertical_type(Context mContext, int imode) {
        int idata;

        switch (imode) {
            case fWeight:
            case fBMR:
            case fBON_MAS:
            case fITR_WAT:
            case fECR_WAT:
            case fProtein:
            case fMinerals:
            case fWIT_HI_RAO:
                idata = 1;
                break;
            case fBDY_FAT:
            case fBMI:
            case fTAL_BDY_WAT:
            case fVCL_FAT_RTG:
            case fOBY_DGE:
                idata = 2;
                break;
            default:
                idata = 0;
                break;
        }

        return idata;
    }

    //0 : 整數；1 ： 小數一位
    //itype 移位
    public static int i_get_bodymanage_point(Context mContext, int imode, int itype) {
        int idata;

        switch (imode) {
            case fWeight:
                //20190121文件 規格變更 英制體重只能設整數
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    idata = 1;
                }
                else {
                    idata = 0;
                }
                break;
            case fITR_WAT:
            case fECR_WAT:
            case fProtein:
            case fMinerals:
            case fBDY_FAT:
            case fBMI:
            case fTAL_BDY_WAT:
            case fTRK_FAT_MAS:
            case fLFT_ARF_FMS:
            case fRIT_ARF_FMS:
            case fLFT_LGF_FMS:
            case fRIG_LGF_FMS:
            case fTrunk_fat:
            case fLFT_ARM_FAT:
            case fRIT_ARM_FAT:
            case fLFT_LGT:
            case fRIT_LGT:
                idata = 1;
                break;
            case fWIT_HI_RAO:
            case fBON_MAS:
                idata = 2;
                break;
            case fOBY_DGE:
            case fBMR:
            case fVCL_FAT_RTG:
            default:
                idata = 0;
                break;
        }

        if (itype != 0) {
            idata -= itype;
        }
        if (idata < 0) {
            idata = 0;
        }

        return idata;
    }

    //0,2 => circle %; 1,3 => inbody570 kg/lb ; 4 => tanita
    public static float fget_body_fat_val(String str, int siip) {
        float fdata;
        int imode = iget_body_machine_type();

        if (imode == 0 || imode == 2) {
            fdata = Rtx_TranslateValue.fMass_String2Float(0, str);
        } else if (imode == 1 || imode == 3){
            fdata = Rtx_TranslateValue.fMass_String2Float(siip, str);
        }
        else
        {
            fdata = Rtx_TranslateValue.fMass_String2Float(0, str);
        }

        return fdata;
    }

    //retrun 0 is can't find;
    public static int iget_body_machine_type() {
        int ival ;
        String smode = CloudDataStruct.BodyIndexData_05.sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sMCH_TYP];
        ival = iget_body_machine_type(smode);

        return ival;
    }

    public static int iget_body_machine_type(String smode) {
        int ival = 0;

        if (smode != null && smode.toLowerCase().compareTo("circle") == 0 ) {
            ival = 0;
        }
        else if (smode != null && smode.toLowerCase().compareTo("inbody") == 0 ) {
            ival = 1;
        }
        else if (smode != null && smode.toLowerCase().compareTo("manual-iba") == 0 ) {
            ival = 2;
        }
        else if (smode != null && smode.toLowerCase().compareTo("manual-inbody") == 0 ) {
            ival = 3;
        }
        else if (smode != null && smode.toLowerCase().compareTo("tanita") == 0 ) {
            ival = 4;
        }

        return ival;
    }

    // if str is not numeric ; return "-1" ; 有轉換kg2lb, 有point
    public static String s_get_drawdata_string(Context mContext, int imode, String str) {
        String sdata;
        float fdata;
        int ipoint;
        int siip = EngSetting.getUnit();

        fdata = f_get_drawdata_string(mContext, imode, str, siip);

        if (fdata != -1 && fdata != 0) {
            ipoint = i_get_bodymanage_point(mContext, imode, 0);
        } else {
            ipoint = 0;
        }
        sdata = Rtx_TranslateValue.sFloat2String(fdata, ipoint);

        return sdata;
    }

    //return "" if data is not numeric;
    public static String s_get_drawdata(Context mContext, int imode) {
        //float fval = f_get_bodymanage_rawdata(mContext, imode);
        String str = s_get_bodymanage_rawdata(mContext, imode);

        //if (str.compareTo("-") != 0 && i_check_modify_range(mContext, imode, fval) == 0)
        if (str.compareTo("-") != 0)
        {
            return s_get_drawdata_string(mContext, imode, str);
        }
        else
        {
            str = "";
        }

        return str;
    }

    //true : can edit; false : can't edit
    public static boolean is_edit(Context mContext, int imode) {
        boolean bdata;

        switch (imode) {
            case fWeight:
            case fBDY_FAT:
            case fBON_MAS:
            case fITR_WAT:
            case fECR_WAT:
            case fProtein:
            case fMinerals:
            case fWIT_HI_RAO:
            case fVCL_FAT_RTG:
                bdata = true;
                break;
            case fBMI:
            case fBMR:
            case fTAL_BDY_WAT:
            case fOBY_DGE:
            default:
                bdata = false;
                break;
        }

        return bdata;
    }


    public static float[] f_get_bar_rangers(Context mContext, int imode) {
        int iLoop;
        int igender;
        int iage;
        float[] fdata_array = null;

        int[][] iage_BDY_FAT = {
                {11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 40, 60, 999}, //Male
                {11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 40, 60, 999}       //FeMale
        };
        float[][][] ff_BDY_FAT = {
                {
                        {8, 13, 23, 28.001f, 33},
                        {8, 13, 23, 28.001f, 33},
                        {7, 12, 23, 28.001f, 33},
                        {7, 12, 22, 27.001f, 32},
                        {5, 11, 21, 26.001f, 31},
                        {3, 10, 21, 25.001f, 30},
                        {3, 10, 20, 24.001f, 30},
                        {3, 10, 20, 24.001f, 30},
                        {3, 10, 20, 24.001f, 30},
                        {2, 9 , 20, 24.001f, 30},
                        {1, 8 , 20, 25.001f, 30},
                        {4, 11, 22, 28.001f, 34},
                        {8, 13, 25, 30.001f, 35}
                },
                {
                        {10, 16, 28, 32.001f, 39},
                        {10, 16, 29, 33.001f, 38},
                        {10, 16, 29, 33.001f, 38},
                        {10, 16, 29, 33.001f, 38},
                        {10, 16, 30, 34.001f, 39},
                        {10, 16, 30, 34.001f, 39},
                        {10, 16, 30, 34.001f, 39},
                        {10, 16, 30, 35.001f, 40},
                        {10, 16, 30, 35.001f, 40},
                        {12, 18, 31, 36.001f, 41},
                        {13, 20, 32, 38.001f, 44},
                        {15, 22, 33, 39.001f, 45},
                        {16, 23, 35, 41.001f, 47}

                }
        };

        float[] f_BMI = {12f, 18.5f, 25f, 30f, 35f};
        float[] f_OD = {70f, 90f, 110f, 130f};
        float[][] f_TTBW = {
                {35f, 50f, 65f, 80f},   //Male
                {30f, 45f, 60f, 75f}    //FeMale
        };
        float[] f_VISC = {1f, 8.001f, 15f};


        if (CloudDataStruct.CloudData_17.is_Male()) {
            igender = 0;
        } else {
            igender = 1;
        }

        switch (imode) {
            case fBDY_FAT://BF
                iage = EngSetting.Age.getDef(mContext);
                float[][] f_array = ff_BDY_FAT[igender];

                for (iLoop = 0; iLoop < iage_BDY_FAT[igender].length; iLoop++) {
                    if (iage < iage_BDY_FAT[igender][iLoop]) {
                        fdata_array = f_array[iLoop];
                        break;
                    }
                }
                break;
            case fBMI://BMI
                fdata_array = f_BMI;
                break;
            case fOBY_DGE://OD
                fdata_array = f_OD;
                break;
            case fVCL_FAT_RTG://VFR
                fdata_array = f_VISC;
                break;
            case fTAL_BDY_WAT://TTBW
                fdata_array = f_TTBW[igender];
                break;
            default:
                break;
        }

        return fdata_array;
    }


    public static String[] s_get_bar_Strings(Context mContext, int imode) {
        String[] mIndex_String = null;

        String[] mBODY_FAT_String = {"UNDER", "HEALTHY", "OVER", "OBESE", "Fat"};
        String[] mBMI_String = {"UNDER", "HEALTHY", "OVER", "OBESE", "Fat"};
        String[] mOD_String = {"UNDER", "HEALTHY", "OVER", "OBESE", "Fat"};
        String[] mVISC_String = {"HEALTHY", "EXCESSIVE", "OVER", "OBESE", "Fat"};
        String[] mTTBW_String = {"UNDER", "HEALTHY", "OVER", "OBESE", "Fat"};

        switch (imode) {
            case fBDY_FAT://BF
                mIndex_String = mBODY_FAT_String;
                break;
            case fBMI://BMI
                mIndex_String = mBMI_String;
                break;
            case fOBY_DGE://OD
                mIndex_String = mOD_String;
                break;
            case fVCL_FAT_RTG://VFR
                mIndex_String = mVISC_String;
                break;
            case fTAL_BDY_WAT://TTBW
                mIndex_String = mTTBW_String;
                break;
            default:
                break;
        }

        return mIndex_String;
    }

    public static int[] i_get_bar_colors(Context mContext, int imode) {
        int[] i_array = null;
        int blue = Common.Color.blue_3;
        int green = Common.Color.green_4;
        int yellow = Common.Color.yellow_5;
        int pink = Common.Color.pink_1;
        int trans = Common.Color.transparent;

        int[] mBODY_FAT_COLOR = {blue, green, yellow, pink, trans, trans};
        int[] mBMI_COLOR = {blue, green, yellow, pink, trans, trans};
        int[] mOD_COLOR = {blue, green, yellow, pink, trans, trans};
        int[] mVISC_COLOR = {blue, green, yellow, pink, trans, trans};
        int[] mTTBW_COLOR = {blue, green, yellow, pink, trans, trans};

        switch (imode) {
            case fBDY_FAT://BF
                i_array = mBODY_FAT_COLOR;
                break;
            case fBMI://BMI
                i_array = mBMI_COLOR;
                break;
            case fOBY_DGE://OD
                i_array = mOD_COLOR;
                break;
            case fVCL_FAT_RTG://VFR
                i_array = mVISC_COLOR;
                break;
            case fTAL_BDY_WAT://TTBW
                i_array = mTTBW_COLOR;
                break;
            default:
                break;
        }

        return i_array;
    }


    // return 可輸入個數
    public static int i_get_modify_numbers(Context mContext, int imode, int iadd) {

        int ipoint;
        int siip = EngSetting.getUnit();

        if (siip == EngSetting.UNIT_METRIC) {
            switch (imode) {
                case fWeight://WEIGHT
                    ipoint = 5;
                    break;
                case fBDY_FAT://BF
                case fBMI://BMI
                case fBON_MAS://BONE MASS
                case fBMR://BMR
                case fTAL_BDY_WAT://TTBW
                case fECR_WAT: //EXTRACELLULAR WATER
                case fITR_WAT: //INTRACELLULAR WATER
                case fProtein: //PROTEIN
                case fMinerals: //MINERAL
                case fWIT_HI_RAO: //WHR
                    ipoint = 4;
                    break;
                case fOBY_DGE://OD
                    ipoint = 3;
                    break;
                case fVCL_FAT_RTG://VFR
                    ipoint = 2;
                    break;
                default:
                    ipoint = 5;
                    break;
            }
        } else {
            switch (imode) {
                case fWeight://WEIGHT
                    //20190121文件 規格變更 英制體重只能設整數
                    ipoint = 3;
                    break;
                case fBON_MAS://BONE MASS
                case fECR_WAT: //EXTRACELLULAR WATER
                case fITR_WAT: //INTRACELLULAR WATER
                case fProtein: //PROTEIN
                case fMinerals: //MINERAL
                    ipoint = 5;
                    break;
                case fBDY_FAT://BF
                case fBMI://BMI
                case fBMR://BMR
                case fTAL_BDY_WAT://TTBW
                case fWIT_HI_RAO: //WHR
                    ipoint = 4;
                    break;
                case fOBY_DGE://OD
                    ipoint = 3;
                    break;
                case fVCL_FAT_RTG://VFR
                    ipoint = 2;
                    break;
                default:
                    ipoint = 5;
                    break;
            }
        }
        return ipoint;
    }

    // return 是否超出範圍 : 0 : pass ; 1 over ; 2 : under ; 99 : unknow
    // fval 須為未轉換值
    public static int i_check_modify_range(Context mContext, int imode, float fval) {

        int ival = 0;
        float fmin, fmax;

        switch (imode) {
            case fWeight://WEIGHT
                //20190108 Target/Body 規格 35 變更為 34
                fmin = 34;
                fmax = 220;
                break;
            case fBDY_FAT://BF
                fmin = 2;
                fmax = 50;
                break;
            case fBMI://BMI
                fmin = 10;
                fmax = 45;
                break;
            case fBON_MAS://BONE MASS
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    fmin = 1;
                    fmax = 9;
                }
                else
                {
                    fmin = 2.2f * EngSetting.lb2kg;//2.2lb
                    fmax = 19.8f * EngSetting.lb2kg;//19.8lb
                }
                break;
            case fBMR://BMR
                fmin = 1000;
                fmax = 3000;
                break;
            case fTAL_BDY_WAT://TTBW
                fmin = 40;
                fmax = 70;
                break;
            case fECR_WAT: //EXTRACELLULAR WATER
            case fITR_WAT: //INTRACELLULAR WATER
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    fmin = 1;
                    fmax = 99;
                }
                else
                {
                    fmin = 1*EngSetting.lb2kg;//1lb
                    fmax = 218*EngSetting.lb2kg;//218lb
                }

                break;
            case fProtein: //PROTEIN
            case fMinerals: //MINERAL
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    fmin = 1;
                    fmax = 50;
                }
                else
                {
                    fmin = 1*EngSetting.lb2kg;//1lb
                    fmax = 110*EngSetting.lb2kg;//110lb
                }
                break;
            case fWIT_HI_RAO: //WHR
                fmin = 0;
                fmax = 2;
                break;
            case fOBY_DGE://OD
                fmin = 50;
                fmax = 150;
                break;
            case fVCL_FAT_RTG://VFR
                fmin = 1;
                fmax = 20;
                break;
            default:
                fmin = -1;
                fmax = 9999;
                ival = 99;
                break;
        }

        if (fval < fmin) {
            ival = 2;
        } else if (fval > fmax) {
            ival = 1;
        }

        return ival;
    }

    //return warring string
    public static String s_get_bodymanage_warring(Context mContext, int imode) {
        String sdata;
        String smesg;

        smesg = LanguageData.s_get_string(mContext, R.string.age_range_error_msg) + " ";


        switch (imode) {
            case fWeight:
                //20190108 Target/Body 規格 35 變更為 34
                sdata = smesg + EngSetting.Weight.getRangeString(mContext, 34f, 220f,0);
                break;
            case fBDY_FAT:
                sdata = smesg + Rtx_TranslateValue.sFloat2String(2f, 0)
                        + " - " + Rtx_TranslateValue.sFloat2String(50f, 0)
                        + " " + s_get_bodymanage_unit(mContext, imode);
                break;
            case fBMI:
                sdata = "";
                break;
            case fBMR:
                sdata = "";
                break;
            case fTAL_BDY_WAT:
                sdata = "";
                break;
            case fVCL_FAT_RTG:
                sdata = smesg + Rtx_TranslateValue.sFloat2String(1f, 0)
                        + " - " + Rtx_TranslateValue.sFloat2String(20f, 0);
                break;
            case fBON_MAS:
                sdata = smesg + EngSetting.Weight.getRangeString(mContext, 1f, 9f,0);
                break;
            case fITR_WAT:
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    sdata = smesg + EngSetting.Weight.getRangeString(mContext, 1f, 99f, 0);
                }
                else {
                    sdata = smesg + Rtx_TranslateValue.sFloat2String(1, 0)
                            + " - " + EngSetting.Weight.getValString(218, 0)
                            + " " + EngSetting.Weight.getUnitString(mContext);
                }
                break;
            case fECR_WAT:
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                    sdata = smesg + EngSetting.Weight.getRangeString(mContext, 1f, 99f, 0);
                }
                else {
                    sdata = smesg + Rtx_TranslateValue.sFloat2String(1, 0)
                            + " - " + EngSetting.Weight.getValString(218, 0)
                            + " " + EngSetting.Weight.getUnitString(mContext);
                }
                break;
            case fProtein:
                sdata = smesg + EngSetting.Weight.getRangeString(mContext, 1f, 50f, 0);
                break;
            case fMinerals:
                sdata = smesg + EngSetting.Weight.getRangeString(mContext, 1f, 50f, 0);
                break;
            case fWIT_HI_RAO:
                sdata = smesg + Rtx_TranslateValue.sFloat2String(0, 0)
                        + " - " + EngSetting.Weight.getValString(2, 0);
                break;
            case fOBY_DGE:
                sdata = "";
                break;
            default:
                sdata = "";
                break;
        }

        return sdata;
    }

    public static String s_healthy_bone_mass(Context mContext, int imode) {
        String sdata ;
        float fdata, fval = 2.2f;
        int iLoop ;
        int igender;
        float[][] fval_array = {
                {2.5f, 2.9f, 3.2f},
                {1.8f, 2.2f, 2.5f},
        };
        float[][] flimit = {
                {60, 75, 9999f},
                {45f, 60f, 9999f},
        };

        if(CloudDataStruct.CloudData_17.is_Male())
        {
            igender = 0;
        }
        else
        {
            igender = 1;
        }
        fdata = f_get_bodymanage_rawdata(mContext, fWeight);

        for(iLoop = 0; iLoop < flimit[igender].length; iLoop ++)
        {
            if(fdata < flimit[igender][iLoop])
            {
                fval = fval_array[igender][iLoop];
                break;
            }
        }

        sdata = EngSetting.Weight.getValString(fval) + " " + EngSetting.Weight.getUnitString(mContext);

        return sdata;

    }

    public static float f_ideal_weight(Context mContext, int imode) {
        float fval ;
        float fheigh = CloudDataStruct.CloudData_17.f_get_user_height();

        if(CloudDataStruct.CloudData_17.is_Male())
        {
            fval = (fheigh - 80f) * 0.7f;
        }
        else
        {
            fval = (fheigh - 80f) * 0.6f;
        }

        return fval;

    }


}
