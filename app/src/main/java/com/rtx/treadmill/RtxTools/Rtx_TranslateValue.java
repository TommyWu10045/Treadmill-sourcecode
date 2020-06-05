package com.rtx.treadmill.RtxTools;

import android.util.Log;

import com.rtx.treadmill.GlobalData.EngSetting;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_TranslateValue {
    public static float fString2Float(String sVal) {
        float fVal = 0;

        if(sVal == null)
        {
            return fVal;
        }

        try {
            fVal = Float.parseFloat(sVal);
        } catch (Exception ex) {
            fVal = 0;
        }

        return fVal;
    }

    public static float fString2Float(String sVal, float fdef) {
        float fVal;

        try {
            fVal = Float.parseFloat(sVal);
        } catch (Exception ex) {
            fVal = fdef;
        }

        return fVal;
    }

    public static int iString2Int(String sVal)
    {
        int iVal = 0;
        float fVal = 0;

        try {
//            iVal = Integer.parseInt(sVal);
            //Float.valueOf精度7位
            fVal = Float.valueOf(sVal);
            //小數點無條件捨去
            iVal = (int)fVal;
        } catch (NumberFormatException ex) {
            iVal = 0;
        }

        return iVal;
    }

    public static int iString2Int_Round(String sVal)
    {
        int iVal = 0;
        float fVal = 0;

        try {
            fVal = Float.parseFloat(sVal);
            iVal = Math.round(fVal);
        } catch (NumberFormatException ex) {
            iVal = 0;
        }

        return iVal;
    }

    public static int iString2Int(String sVal, int inum)
    {
        int iVal = 0;

        try {
            iVal = Integer.parseInt(sVal, inum);
        } catch (NumberFormatException ex) {
            iVal = 0;
        }

        return iVal;
    }

    public static String sFloat2String(float fVal , int iDecimalNumber)
    {
        String sVal = null;
        float fResultVal = 0;

        if(iDecimalNumber != 0)
        {
            fResultVal = Math.round(fVal * (float)Math.pow(10, iDecimalNumber)) / ((float)Math.pow(10, iDecimalNumber));
        }
        else
        {
            fResultVal = Math.round(fVal);
        }

        sVal = String.format("%." + iDecimalNumber +  "f",fResultVal);

        return sVal;
    }

    //無條件捨去
    public static String sFloat2StringInt(float fVal , int iDecimalNumber)
    {
        String sVal = null;
        float fResultVal = 0;

        if(iDecimalNumber != 0)
        {
            fResultVal = Math.round((fVal - 0.499999f) * (float)Math.pow(10, iDecimalNumber)) / ((float)Math.pow(10, iDecimalNumber));
        }
        else
        {
            fResultVal = Math.round(fVal - 0.499999f);
        }

        sVal = String.format("%." + iDecimalNumber +  "f",fResultVal);

        return sVal;
    }

    public static String sInt2String(int iVal)
    {
        String sVal = null;

        sVal = String.valueOf(iVal);

        return sVal;
    }

    public static String sInt2String(int iVal , int iDecimalNumber)
    {
        String sVal = null;

        sVal = String.format("%0" + iDecimalNumber +  "d",iVal);

        return sVal;
    }

    //imode  2 is lb to kg; 1 is kg to lb;
    public static float fMass_String2Float(int imode, String s)
    {
        float fdata = (float) 1;
        float fval = fString2Float(s, -1f);

        if(imode == 2)
        {
            fdata = EngSetting.lb2kg;
        }
        else if(imode == 1)
        {
            fdata = EngSetting.kg2lb;
        }

        if(fval != -1)
        {
            fval *= fdata;
        }

        return fval;
    }

    public static float fRoundingVal(float fVal, int iDecimalNumber)
    {
        float fResultVal = 0;

        if(iDecimalNumber != 0)
        {
            fResultVal = Math.round(fVal * (float)Math.pow(10, iDecimalNumber)) / ((float)Math.pow(10, iDecimalNumber));
        }
        else
        {
            fResultVal = Math.round(fVal);
        }


        return fResultVal;
    }

    public static float fRoundingVal(String sVal, int iDecimalNumber)
    {
        float fVal = 0;
        float fResultVal = 0;

        fVal = fString2Float(sVal);

        fResultVal = (float) (Math.round(fVal * (float)Math.pow(10, iDecimalNumber)) / ((float)Math.pow(10, iDecimalNumber)));

        return fResultVal;
    }

    public static boolean bIsDecimal(Float fVal)
    {
        boolean bResult = false;

        if(fVal % 1.0 == 0)
        {
            bResult = false;
        }
        else
        {
            bResult = true;
        }

        return bResult;
    }

    public static String sFormatToDecimal(String sVal, int iDecimal)
    {
        float fVal = fString2Float(sVal);
        String sFormatVal = null;

        sFormatVal = sFloat2String(fVal,iDecimal);

        return sFormatVal;
    }

}
