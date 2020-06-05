package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jerry on 2017/6/23.
 */

public class GlobalData
{
    /////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


    public static void globalData_init()
    {
        create_DATE_TIMES(DATE_TIMES.iafter, DATE_TIMES.ibefor);
    }

    public static Context global_context;

    public static int UNIT_METRIC = EngSetting.UNIT_METRIC;
    public static int UNIT_IMPERIAL = EngSetting.UNIT_IMPERIAL;

    // 0x01 : FirstActivity is running;
    // 0x10 : MenuActivity is running
    // 0x80 : BOOT_COMPLETE have recieved.
    public static int irunn_status = 0 ;
    public static void v_set_run_status(int ival)
    {
        irunn_status |= ival;
    }

    public static void v_clear_bit_run_status(int ival)
    {
        irunn_status &= ~ival;
    }

    public static void v_clear_run_status()
    {
        irunn_status = 0;
    }

    public static int i_check_isruning()
    {
        return irunn_status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static long lInteractionTime = System.currentTimeMillis();
    public static long lInteractionStartTime = 0;
    final public static long CHECK_INTERACTION_60_SEC = 60;
    final public static long CHECK_INTERACTION_120_SEC = 120;
    final public static long CHECK_INTERACTION_180_SEC = 180;
    final public static long CHECK_INTERACTION_300_SEC = 300;

    public static boolean bCheckInteractionTimeAndReset(long lSec)
    {
        boolean bCheck = false;

        bCheck = bCheckInteractionTime(lSec);
        vResetInteractionTime();

        return bCheck;
    }

    public static boolean bCheckInteractionTime(long lSec)
    {
        boolean bCheck = false;
        long lDiffSec = 0;

        lDiffSec = lGetInteractionDiffSec();

        if(lDiffSec >= lSec)
        {
            bCheck = true;
        }

        return bCheck;
    }

    public static boolean bCheckInteractionStartTime(long lSec)
    {
        boolean bCheck = false;
        long lDiffSec = 0;

        lDiffSec = lGetInteractionDiffSec(lInteractionStartTime);

        if(lDiffSec >= lSec)
        {
            bCheck = true;
        }

        return bCheck;
    }


    public static long lGetInteractionDiffSec()
    {
        long lSec = 0;

        lSec = (System.currentTimeMillis() - lInteractionTime) / 1000;

        return lSec;
    }

    public static long lGetInteractionDiffSec(long lSec)
    {
        long lDiffSec = 0;

        lDiffSec = (System.currentTimeMillis() - lSec) / 1000;

        return lDiffSec;
    }

    public static void vSetInteractionStartTime()
    {
        lInteractionStartTime = lInteractionTime;
    }


    public static void vResetInteractionTime()
    {
        lInteractionTime = System.currentTimeMillis();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Weather ID map drawable
    public static class WeatherIcon {

        final public static int H1 = R.drawable.weather_h1;
        final public static int C2 = R.drawable.weather_c2;
        final public static int F2 = R.drawable.weather_f2;
        final public static int A2 = R.drawable.weather_a2;
        final public static int A4 = R.drawable.weather_a4;
        final public static int G1 = R.drawable.weather_g1;
        final public static int F4 = R.drawable.weather_f4;
        final public static int B2 = R.drawable.weather_b2;
        final public static int F1 = R.drawable.weather_f1;
        final public static int B5 = R.drawable.weather_b5;
        final public static int B1 = R.drawable.weather_b1;
        final public static int H3 = R.drawable.weather_h3;
        final public static int A1 = R.drawable.weather_a1;
        final public static int D2 = R.drawable.weather_d2;
        final public static int C1 = R.drawable.weather_c1;
        final public static int E2 = R.drawable.weather_e2;

        public static int iResId[] = {
                H1,B2,H1,C2,C2, //4
                F2,F2,F2,E2,A2, //9
                A2,A2,A2,A4,A4, //14
                A4,A4,F2,A4,G1, //19
                G1,G1,G1,F4,F4, //24
                A4,F1,F1,F1,F1, //29
                F1,B5,B1,H3,A1, //34
                F2,B1,D2,D2,D2, //39
                A2,F2,F2,F2,C1, //44
                C2,E2,C2        //47
        };
    }

    // Weather Info
    public static class Weather {

        public static boolean bUpdate = false;
        public static String sCity = "";
        public static String sDate = "";
        public static float fCTemp = 9999;
        public static int iFTemp = 9999;
        public static Bitmap Bmap = null;
        public static String sError = "";
        public static int iCurrentCode = -1;

        public static boolean checkUpdate()
        {
            return bUpdate;
        }

        public static String get_Temperature(int iUnit)
        {
            String sdata = "";

            if(iUnit == UNIT_METRIC)
            {
                sdata = String.format("%.00f °C", fCTemp);
            }
            else if(iUnit == UNIT_IMPERIAL)
            {
                sdata = String.format("%d °F", iFTemp);
            }
            else
            {
                sdata = String.format("%.00f °C", fCTemp);
            }

            GlobalData.Weather.bUpdate = false;

            return sdata;
        }
    }

    public static CloudDataStruct.Registration global_RegData = new CloudDataStruct.Registration();

    public static String getHeightUnit()
    {
        int iUnit = EngSetting.getUnit();
        String sData = null;

        if(iUnit == UNIT_METRIC)
        {
            if(global_context != null)
            {
                sData = global_context.getResources().getString(R.string.cm);
            }
            else
            {
                sData = "cm";
            }
        }
        else if(iUnit == UNIT_IMPERIAL)
        {
            if(global_context != null)
            {
                sData = global_context.getResources().getString(R.string.inch);
            }
            else
            {
                sData = "inch";
            }
        }
        else
        {
            if(global_context != null)
            {
                sData = global_context.getResources().getString(R.string.cm);
            }
            else
            {
                sData = "cm";
            }
        }

        return sData;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class DATE_TIMES{
        public static int iafter = -10 ;
        public static int ibefor = 99 ;

        public static int iyear_min ;
        public static int iyear_max ;
        public static String[] syears ;
        public static int imounth_min = 1 ;
        public static int imounth_max = 12 ;
        public static String[] smonth = Common.MONTH_OF_YEAR ;
        public static int iday_min = 1 ;
        public static int iday_max = 31 ;
        public static String[] sdays ;
        public static int ihour_min = 0 ;
        public static int ihour_max = 23 ;
        public static String[] shours ;
        public static int iminute_min = 0 ;
        public static int iminute_max = 59 ;
        public static String[] sminutes ;
    }

    public static Calendar getInstance()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2)+":"+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4)));
        return cal;
    }

    public static void vSetTimezone(SimpleDateFormat mSimpleDateFormat)
    {
        mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2)+":"+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3)+EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4)));
    }

    public static void create_DATE_TIMES(int iafter, int ibefor)
    {
        Calendar cd = GlobalData.getInstance();
        DATE_TIMES.iyear_max = cd.get(Calendar.YEAR) + iafter;
        DATE_TIMES.iyear_min = cd.get(Calendar.YEAR) - ibefor ;

        int icount = DATE_TIMES.iyear_max - DATE_TIMES.iyear_min ;
        int idata = DATE_TIMES.iyear_min;

        if(DATE_TIMES.syears == null)
        {
            DATE_TIMES.syears = new String[icount + 1];
            for(int i = 0; i <= icount ; i++)
            {
                DATE_TIMES.syears[i] = String.valueOf(idata);
                idata += 1 ;
            }
        }

        idata = 1;
        if(DATE_TIMES.sdays == null)
        {
            DATE_TIMES.sdays = new String[31];
            for(int i = 0; i < DATE_TIMES.sdays.length ; i++)
            {
                DATE_TIMES.sdays[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }

        idata = 0;
        if(DATE_TIMES.shours == null)
        {
            DATE_TIMES.shours = new String[24];
            for(int i = 0; i < DATE_TIMES.shours.length ; i++)
            {
                DATE_TIMES.shours[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }

        idata = 0;
        if(DATE_TIMES.sminutes == null)
        {
            DATE_TIMES.sminutes = new String[60];
            for(int i = 0; i < DATE_TIMES.sminutes.length ; i++)
            {
                DATE_TIMES.sminutes[i] = String.format("%02d", idata);
                idata += 1 ;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static float fNumberWheelFontSize = -1;

    public static class NumberWheelData
    {

        private float fMaxVal = 100;
        private float fMinVal = 0;
        private float fDefaultVal = 0;

        public float fScale = 1;
        public int   iIndex_Max = 99;
        public int   iIndex_Min = 0;
        public int   iCount = 100;

        public String[] sArrayVal = null;
        public int iIndex_Default = 1;

        public NumberWheelData(float fScale , float fMaxVal , float fMinVal , float fDefault)
        {
            this.fScale = fScale;
            this.fMaxVal = fMaxVal;
            this.fMinVal = fMinVal;
            this.fDefaultVal = fDefault;
            init();
            create();
        }

        private void init()
        {
            iIndex_Max = (int)(fMaxVal / fScale);
            iIndex_Min = (int)(fMinVal / fScale);
            iCount = (int)((fMaxVal - fMinVal) / fScale) + 1;
            iIndex_Default = (int)(fDefaultVal / fScale);
        }

        private void create()
        {
            float fVal = fMinVal;
            int iDecimalNumber = 0;
            sArrayVal = new String[iCount];

            {
                if(fScale < 1)  { iDecimalNumber = 1; }
                else            { iDecimalNumber = 0; }
            }

            {
                int iIndex = 0;

                for( ; iIndex < iCount ; iIndex++)
                {
                    sArrayVal[iIndex] = Rtx_TranslateValue.sFloat2String(fVal,iDecimalNumber);
                    fVal += fScale;
                }
            }
        }

        public int getNearIndex(float fVal)
        {
            int iIndex_Val = 0;
            float fBigVal = 0;
            float fSmallVal = 0;

            iIndex_Val = (int)(fVal / fScale);

            fBigVal = fVal - (iIndex_Val * fScale);
            fSmallVal = ((iIndex_Val + 1) * fScale) - fVal;

            if(fBigVal >= fSmallVal)
            {
                iIndex_Val++;
            }

            return  iIndex_Val;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal = new ArrayList<>();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class URI_LOADING
    {
        public String sUri;
        public Bitmap bBitmap;
        public boolean bLoadingFinish;
    }

    public static ArrayList<URI_LOADING> list_UriLoading = new ArrayList<URI_LOADING>();

    public static void addUriToLoading(String sUri)
    {
        URI_LOADING uriLoading;

        if(list_UriLoading != null)
        {
            uriLoading = new URI_LOADING();
            uriLoading.sUri = sUri;
            uriLoading.bBitmap = null;
            uriLoading.bLoadingFinish = false;

            list_UriLoading.add(uriLoading);
        }
    }

    public static boolean loadBitmap()
    {
        boolean bResume = false;

        int iSize = 0;
        int iIndex = 0;

        if(list_UriLoading != null)
        {
            iSize = list_UriLoading.size();

            if(iSize > 0)
            {
                for( ; iIndex < iSize ; iIndex++)
                {
                    if(list_UriLoading.get(iIndex) != null)
                    {
                        if(list_UriLoading.get(iIndex).bLoadingFinish == false)
                        {
                            list_UriLoading.get(iIndex).bBitmap = getImageBitmap(list_UriLoading.get(iIndex).sUri);
                            list_UriLoading.get(iIndex).bLoadingFinish = true;
                            bResume = true;
                            break;
                        }
                    }
                }
            }
        }

        return bResume;
    }

    public static Bitmap getImageBitmap(String url)
    {
        //long start_time = System.currentTimeMillis();
        //long duration = 0;

        {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("Jerry", "Error getting bitmap", e);
            }
            catch (Exception e) {
                Log.e("Jerry", "Error getting bitmap", e);
            }
            return bm;
        }
    }

    public static Bitmap searchBitmap(String sUri)
    {
        int iSize = 0;
        int iIndex = 0;
        Bitmap bitmap = null;

        boolean bUriExist = false;

        if(list_UriLoading != null)
        {
            iSize = list_UriLoading.size();
            if(iSize > 0)
            {
                for( ; iIndex < iSize ; iIndex++)
                {
                    if(list_UriLoading.get(iIndex) != null)
                    {
                        if(list_UriLoading.get(iIndex).sUri.equals(sUri))
                        {
                            bUriExist = true;

                            if(list_UriLoading.get(iIndex).bLoadingFinish)
                            {
                                bitmap = list_UriLoading.get(iIndex).bBitmap;
                            }
                        }
                    }
                }
            }
        }

        if(bUriExist == false)
        {
            addUriToLoading(sUri);
        }

        return bitmap;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean bGlobalData_FBFlag = false ;
    public static boolean bGlobalData_TWFlag = false ;
    public static boolean bGlobalData_IGFlag = false ;
    public static boolean bGlobalData_WBFlag = false ;

    public static void vGlobalData_SetFBFlag(boolean bFlag) { bGlobalData_FBFlag = bFlag; }
    public static void vGlobalData_SetTWFlag(boolean bFlag) { bGlobalData_TWFlag = bFlag; }
    public static void vGlobalData_SetIGFlag(boolean bFlag) { bGlobalData_IGFlag = bFlag; }
    public static void vGlobalData_SetWBFlag(boolean bFlag) { bGlobalData_WBFlag = bFlag; }

    public static void vGlobalData_ShareAllClear()
    {
//        if(bGlobalData_FBFlag == true)
        {
            bGlobalData_FBFlag = false;
            vGlobalData_ShareClear("com.facebook.katana");
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Facebook Clear!");
        }
//        if(bGlobalData_TWFlag == true)
        {
            bGlobalData_TWFlag = false;
            vGlobalData_ShareClear("com.twitter.android");
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Twitter Clear!");
        }
//        if(bGlobalData_IGFlag == true)
        {
            bGlobalData_IGFlag = false;
            vGlobalData_ShareClear("com.instagram.android");
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Instagram Clear!");
        }
//        if(bGlobalData_WBFlag == true)
        {
            bGlobalData_WBFlag = false;
            vGlobalData_ShareClear("com.sina.weibo");
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Weibo Clear!");
        }
    }

    public static void vGlobalData_ShareClear(String sUri)
    {
        Process p = null;
        try
        {
            p =Runtime.getRuntime().exec("pm clear " + sUri);
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        if(p == null)
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Fail!");
        }
        else
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + "Success!");
        }
    }
}
