package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.retonix.circlecloud.Cloud_09_GET_WRK_ITV_SET;
import com.retonix.circlecloud.Cloud_16_GET_EW_SUM_HIS;
import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retonix.circlecloud.Cloud_30_GET_BLT_BOD;
import com.retonix.circlecloud.Cloud_32_GET_CLS_BSC;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.MyGym.Layout_ClassList;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_FitnessCalculation;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by jerry on 2017/6/23.
 */

public class UiDataStruct
{
    final static int TARGET_EXERCISE_RUNNING = 0;
    final static int TARGET_EXERCISE_BIKONG = 1;
    final static int TARGET_EXERCISE_ELLIPTICAL = 2;
    final static int TARGET_EXERCISE_ALL = 3;

    /***** Target *****/
    public static class TargetTrainValItem
    {
        public int         iOrder;
        public float       fCurrentVal;
        public float       fTargetVal;
        public float       fStartVal;
        public int         iDays_Target;
        public int         iDays_Current;
        public int         iDays_Left;
        public float       fCompleteness;
        public int         iExerciseType;
        public String      sExerciseType;

        public Calendar    cal_Start;
        public Calendar    cal_End;
        public Calendar    cal_HalfWay;

        public CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGol;

        public  boolean     bContinue = false;
        private boolean     bTargetReached = false;
        private boolean     bDaysReached = false;
        private boolean     bHalfWayThrough = false;

        final int          SCORE_GOLD       = 1;
        final int          SCORE_SILVER     = 2;
        final int          SCORE_GOODTRY    = 3;


        public TargetTrainValItem()
        {
            cloud_TargetGol = new CloudDataStruct.CLOUD_TATGET_GOAL();
            cal_Start = GlobalData.getInstance();
        }

        public void clear()
        {
            iOrder = 0;
            fCurrentVal = 0;
            fTargetVal = 0;
            fStartVal = 0;
            iDays_Target = 0;
            iDays_Current = 0;
            iDays_Left = 0;
            fCompleteness = 0;
            iExerciseType = 0;
            sExerciseType = "A";
            cal_Start = GlobalData.getInstance();
            cal_End = GlobalData.getInstance();
            cal_HalfWay = GlobalData.getInstance();

            bTargetReached = false;
            bDaysReached = false;
            bHalfWayThrough = false;
        }

        public int iGetScore()
        {
            int iScore = -1;

            if(bTargetReached && (!bContinue))
            {
                iScore = SCORE_GOLD;
            }
            else if(bTargetReached && bContinue)
            {
                iScore = SCORE_SILVER;
            }
            else
            {
                iScore = SCORE_GOODTRY;
            }

            return iScore;
        }

        public void vSetDaysReached()
        {
            Calendar cal_Today = GlobalData.getInstance();

            if(cal_Today.compareTo(cal_End) >=0 )
            {
                bDaysReached = true;
            }
        }

        public void vSetHalfWayReached()
        {
            Calendar cal_Today = GlobalData.getInstance();

            if(cal_Today.compareTo(cal_HalfWay) >=0 )
            {
                bHalfWayThrough = true;
            }
        }

        public boolean bIsTargetReached()
        {
            return bTargetReached;
        }

        public boolean bIsDaysReached()
        {
            return bDaysReached;
        }

        public boolean bIsHalfDayReached()
        {
            return bHalfWayThrough;
        }

        public void vExportDataToTargetClose(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_close)
        {
            goal_close.sGOL_SEQ = new String(cloud_TargetGol.sGol_Seq);
            //goal_close.sUSR_SEQ = new String(cloud_TargetGol.sGol_Seq);
            goal_close.sGOL_ITM = new String(cloud_TargetGol.sGol_Item);
            goal_close.sGOL_TYP = new String(cloud_TargetGol.sGol_Type);
            goal_close.sGOL_DAT_1 = new String(cloud_TargetGol.sGol_Val);
            goal_close.sGOL_DAT_2 = new String(cloud_TargetGol.sGol_Duration);
            goal_close.sGOL_STR_DT = new String(cloud_TargetGol.sStartDate);
            goal_close.sUSR_WEI = new String(cloud_TargetGol.sUsr_Wei);
            goal_close.sNOW_WEI = new String(Rtx_TranslateValue.sFloat2String(fCurrentVal,3));
            goal_close.sGOL_WEI = new String(cloud_TargetGol.sGol_Val);
            goal_close.sGOL_STS = new String("C");
            goal_close.sGOL_RST = new String(Rtx_TranslateValue.sFloat2String(fCurrentVal,3));
            goal_close.sEND_TIM = new String(Rtx_Calendar.sTodayCalendar2Str("yyyy-MM-dd"));
            goal_close.sGOL_CLS_LVL = new String(Rtx_TranslateValue.sInt2String(iGetScore()));

//            Log.e("Jerry","goal_close.sGOL_SEQ = " + goal_close.sGOL_SEQ);
//            Log.e("Jerry","goal_close.sGOL_ITM = " + goal_close.sGOL_ITM);
//            Log.e("Jerry","goal_close.sGOL_TYP = " + goal_close.sGOL_TYP);
//            Log.e("Jerry","goal_close.sGOL_DAT_1 = " + goal_close.sGOL_DAT_1);
//            Log.e("Jerry","goal_close.sGOL_DAT_2 = " + goal_close.sGOL_DAT_2);
//            Log.e("Jerry","goal_close.sGOL_STR_DT = " + goal_close.sGOL_STR_DT);
//            Log.e("Jerry","goal_close.sUSR_WEI = " + goal_close.sUSR_WEI);
//            Log.e("Jerry","goal_close.sNOW_WEI = " + goal_close.sNOW_WEI);
//            Log.e("Jerry","goal_close.sGOL_WEI = " + goal_close.sGOL_WEI);
//            Log.e("Jerry","goal_close.sGOL_STS = " + goal_close.sGOL_STS);
//            Log.e("Jerry","goal_close.sGOL_RST = " + goal_close.sGOL_RST);
//            Log.e("Jerry","goal_close.sEND_TIM = " + goal_close.sEND_TIM);
//            Log.e("Jerry","goal_close.sGOL_CLS_LVL = " + goal_close.sGOL_CLS_LVL);
        }
    }

    public static class TargetTrainFreqItem
    {
        final int TARGET_MAX_COUNT = 3;

        public int         iOrder;
        public int         iTargetDaysPerWeek;

        public int         iWeeks_Target;
        public int         iWeeks_Current;
        public int         iWeeks_Left;
        public int         iAchievmentDaysInCurrentWeek;
        public int         iAchievmentWeeks;
        public int         iExerciseType;
        public String      sExerciseType;

        public Calendar    cal_Start;
        public Calendar    cal_End;
        public Calendar    cal_HalfWay;

        public ArrayList<Integer> list_AchievmentDaysInWeek;
        public ArrayList<Calendar>  list_ExerciseCal;

        public CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGol;

        public boolean     bContinue = false;
        public boolean     bTargetReached = false;
        public boolean     bDaysReached = false;
        public boolean     bHalfWayThrough = false;

        final int          SCORE_GOLD       = 1;
        final int          SCORE_SILVER     = 2;
        final int          SCORE_GOODTRY    = 3;

        public TargetTrainFreqItem()
        {
            list_AchievmentDaysInWeek = new ArrayList<Integer>();
            list_ExerciseCal = new ArrayList<Calendar>();
            cal_Start = GlobalData.getInstance();
            cloud_TargetGol = new CloudDataStruct.CLOUD_TATGET_GOAL();
        }

        public void clear()
        {
            iOrder = 0;
            iTargetDaysPerWeek = 0;
            iWeeks_Target = 0;
            iWeeks_Current = 0;
            iAchievmentDaysInCurrentWeek = 0;
            iAchievmentWeeks = 0;
            iExerciseType = 0;
            sExerciseType = "A";
            cal_Start = GlobalData.getInstance();
            cal_End = GlobalData.getInstance();
            cal_HalfWay = GlobalData.getInstance();
            if(list_AchievmentDaysInWeek != null)   { list_AchievmentDaysInWeek.clear(); }
            if(list_ExerciseCal != null)            { list_ExerciseCal.clear(); }

            bContinue = false;
            bTargetReached = false;
            bDaysReached = false;
            bHalfWayThrough = false;
        }

        public int iGetScore()
        {
            int iScore = -1;

            int iWeeks_Gold = 0;
            int iWeeks_Silver = 0;

            iWeeks_Gold = (int)Math.ceil((double)iWeeks_Target * (double)0.601);
            iWeeks_Silver = (int)Math.ceil((double)iWeeks_Target * (double)0.201);

            if(iAchievmentWeeks >= iWeeks_Gold)
            {
                iScore = SCORE_GOLD;
            }
            else if(iAchievmentWeeks >= iWeeks_Silver)
            {
                iScore = SCORE_SILVER;
            }
            else
            {
                iScore = SCORE_GOODTRY;
            }

            return iScore;
        }

        public void vSetDaysReached()
        {
            Calendar cal_Today = GlobalData.getInstance();

            if(cal_Today.compareTo(cal_End) >=0 )
            {
                bDaysReached = true;
            }
        }

        public void vSetHalfWayReached()
        {
            Calendar cal_Today = GlobalData.getInstance();

            if(cal_Today.compareTo(cal_HalfWay) >=0 )
            {
                bHalfWayThrough = true;
            }
        }

        public boolean bIsTargetReached()
        {
            return bTargetReached;
        }

        public boolean bIsDaysReached()
        {
            return bDaysReached;
        }

        public boolean bIsHalfDayReached()
        {
            return bHalfWayThrough;
        }

        public void vExportDataToTargetClose(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_close)
        {
            goal_close.sGOL_SEQ = new String(cloud_TargetGol.sGol_Seq);
            //goal_close.sUSR_SEQ = new String(cloud_TargetGol.sGol_Seq);
            goal_close.sGOL_ITM = new String(cloud_TargetGol.sGol_Item);
            goal_close.sGOL_TYP = new String(cloud_TargetGol.sGol_Type);
            goal_close.sGOL_DAT_1 = new String(cloud_TargetGol.sGol_Val);
            goal_close.sGOL_DAT_2 = new String(cloud_TargetGol.sGol_Duration);
            goal_close.sGOL_STR_DT = new String(cloud_TargetGol.sStartDate);
            goal_close.sUSR_WEI = new String("");
            goal_close.sNOW_WEI = new String("");
            goal_close.sGOL_WEI = new String("");
            goal_close.sGOL_STS = new String("C");
            goal_close.sGOL_RST = new String(Rtx_TranslateValue.sInt2String(iAchievmentWeeks));
            goal_close.sEND_TIM = new String(Rtx_Calendar.sTodayCalendar2Str("yyyy-MM-dd"));
            goal_close.sGOL_CLS_LVL = new String(Rtx_TranslateValue.sInt2String(iGetScore()));

//            Log.e("Jerry","goal_close.sGOL_SEQ = " + goal_close.sGOL_SEQ);
//            Log.e("Jerry","goal_close.sGOL_ITM = " + goal_close.sGOL_ITM);
//            Log.e("Jerry","goal_close.sGOL_TYP = " + goal_close.sGOL_TYP);
//            Log.e("Jerry","goal_close.sGOL_DAT_1 = " + goal_close.sGOL_DAT_1);
//            Log.e("Jerry","goal_close.sGOL_DAT_2 = " + goal_close.sGOL_DAT_2);
//            Log.e("Jerry","goal_close.sGOL_STR_DT = " + goal_close.sGOL_STR_DT);
//            Log.e("Jerry","goal_close.sUSR_WEI = " + goal_close.sUSR_WEI);
//            Log.e("Jerry","goal_close.sNOW_WEI = " + goal_close.sNOW_WEI);
//            Log.e("Jerry","goal_close.sGOL_WEI = " + goal_close.sGOL_WEI);
//            Log.e("Jerry","goal_close.sGOL_STS = " + goal_close.sGOL_STS);
//            Log.e("Jerry","goal_close.sGOL_RST = " + goal_close.sGOL_RST);
//            Log.e("Jerry","goal_close.sEND_TIM = " + goal_close.sEND_TIM);
//            Log.e("Jerry","goal_close.sGOL_CLS_LVL = " + goal_close.sGOL_CLS_LVL);
        }
    }

    public static class TargetTrainInfo
    {
        final int TARGET_TRAIN_DIS      = 0x00000001;
        final int TARGET_TRAIN_CAL      = 0x00000010;
        final int TARGET_TRAIN_WEI      = 0x00000100;
        final int TARGET_TRAIN_BDY_FAT  = 0x00001000;
        final int TARGET_TRAIN_FREQ     = 0x00010000;

        final int TARGET_TRAIN_MAX      = 5;

        int iCurrentMode = 0;

        public TargetTrainValItem  targetTrainInfo_Dis;
        public TargetTrainValItem  targetTrainInfo_Cal;
        public TargetTrainValItem  targetTrainInfo_Wei;
        public TargetTrainValItem  targetTrainInfo_BdyFat;
        public TargetTrainFreqItem targetTrainInfo_Freq;

        public TargetTrainInfo()
        {
            targetTrainInfo_Dis     = new TargetTrainValItem();
            targetTrainInfo_Cal     = new TargetTrainValItem();
            targetTrainInfo_Wei     = new TargetTrainValItem();
            targetTrainInfo_BdyFat  = new TargetTrainValItem();
            targetTrainInfo_Freq    = new TargetTrainFreqItem();
        }

        public void clear()
        {
            if(targetTrainInfo_Dis != null)     { targetTrainInfo_Dis.clear(); }
            if(targetTrainInfo_Cal != null)     { targetTrainInfo_Cal.clear(); }
            if(targetTrainInfo_Wei != null)     { targetTrainInfo_Wei.clear(); }
            if(targetTrainInfo_BdyFat != null)  { targetTrainInfo_BdyFat.clear(); }
            if(targetTrainInfo_Freq != null)    { targetTrainInfo_Freq.clear(); }

            iCurrentMode = 0;
        }

        public boolean vParseCloudData(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudData)
        {
            boolean bRet = false;
            int iIndex = 0;
            int iSize = 0;
            int iOrder = 0;

            clear();

            if(list_CloudData != null)
            {
                iSize = list_CloudData.size();
                CloudDataStruct.CLOUD_TATGET_GOAL cloudData;

                for(iIndex = 0 ; iIndex < iSize ; iIndex++)
                {
                    cloudData = list_CloudData.get(iIndex);

                    //Log.e("Jerry","cloudData.sGol_Item = " + cloudData.sGol_Item);

                    if(iParseMode(cloudData.sGol_Item) == TARGET_TRAIN_DIS)
                    {
                        if((!bIsDistance()) && (getTargetCount() < 3))
                        {
                            iCurrentMode = iCurrentMode | TARGET_TRAIN_DIS;
                            targetTrainInfo_Dis.iOrder = iOrder;
                            bParseValItem(targetTrainInfo_Dis,list_CloudData.get(iIndex));
                            iOrder++;
                            bRet = true;
                        }
                    }
                    else if(iParseMode(cloudData.sGol_Item) == TARGET_TRAIN_CAL)
                    {
                        if((!bIsCalories()) && (getTargetCount() < 3))
                        {
                            iCurrentMode = iCurrentMode | TARGET_TRAIN_CAL;
                            targetTrainInfo_Cal.iOrder = iOrder;
                            bParseValItem(targetTrainInfo_Cal,list_CloudData.get(iIndex));
                            iOrder++;
                            bRet = true;
                        }
                    }
                    else if(iParseMode(cloudData.sGol_Item) == TARGET_TRAIN_FREQ)
                    {
                        if((!bIsFreq()) && (getTargetCount() < 3))
                        {
                            iCurrentMode = iCurrentMode | TARGET_TRAIN_FREQ;
                            targetTrainInfo_Freq.iOrder = iOrder;
                            bParseFreqItem(targetTrainInfo_Freq,list_CloudData.get(iIndex));
                            iOrder++;
                            bRet = true;
                        }
                    }
                    else if(iParseMode(cloudData.sGol_Item) == TARGET_TRAIN_WEI)
                    {
                        if((!bIsWeight()) && (getTargetCount() < 3))
                        {
                            iCurrentMode = iCurrentMode | TARGET_TRAIN_WEI;
                            targetTrainInfo_Wei.iOrder = iOrder;
                            bParseValItem(targetTrainInfo_Wei,list_CloudData.get(iIndex));
                            vWeightRoundVal();
                            iOrder++;
                            bRet = true;
                        }
                    }
                    else if(iParseMode(cloudData.sGol_Item) == TARGET_TRAIN_BDY_FAT)
                    {
                        if((!bIsBodyFat()) && (getTargetCount() < 3))
                        {
                            iCurrentMode = iCurrentMode | TARGET_TRAIN_BDY_FAT;
                            targetTrainInfo_BdyFat.iOrder = iOrder;
                            bParseValItem(targetTrainInfo_BdyFat,list_CloudData.get(iIndex));
                            iOrder++;
                            bRet = true;
                        }
                    }
                    else
                    {

                    }
                }
            }

            return  bRet;
        }

        public int iParseMode(String sMode)
        {
            int iMode = 0;

            if(sMode.equals("Distance"))
            {
                iMode = TARGET_TRAIN_DIS;
            }
            else if(sMode.equals("Calories"))
            {
                iMode = TARGET_TRAIN_CAL;
            }
            else if(sMode.equals("Frequency"))
            {
                iMode = TARGET_TRAIN_FREQ;
            }
            else if(sMode.equals("Target Weight"))
            {
                iMode = TARGET_TRAIN_WEI;
            }
            else if(sMode.equals("Target Body Fat"))
            {
                iMode = TARGET_TRAIN_BDY_FAT;
            }
            else
            {

            }

            return iMode;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////
        /***** ParseValItem *****/
        ////////////////////////////////////////////////////////////////////////////////////////////////
        public boolean bParseValItem(TargetTrainValItem target_item , CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            boolean bRet = false;

            if(target_item == null)
            {
                return bRet;
            }

            if(cloud_Data == null)
            {
                return bRet;
            }

            target_item.cal_Start = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");
            target_item.fStartVal = fGetStartVal(cloud_Data);
            target_item.fTargetVal = fGetTargetVal(cloud_Data);
            target_item.iDays_Target = iGetTargetDays(cloud_Data);

            target_item.cal_End = (Calendar) target_item.cal_Start.clone();
            target_item.cal_End.add(Calendar.DATE,target_item.iDays_Target);

            target_item.cal_HalfWay = (Calendar) target_item.cal_Start.clone();
            int iHalfWayDay = target_item.iDays_Target / 2;

//            Log.e("Jerry","target_item.iDays_Target = " + target_item.iDays_Target);
//            Log.e("Jerry","target_item.cal_HalfWay = " + Rtx_Calendar.sCalendar2Str(target_item.cal_HalfWay,"yyyy-MM-dd"));

            target_item.cal_HalfWay.add(Calendar.DATE,iHalfWayDay);


            target_item.iDays_Current = iGetCurrentDays(cloud_Data);
            target_item.bContinue = bIsContinue(cloud_Data);
            target_item.iDays_Left = iGetLeftDays(cloud_Data);
            target_item.iExerciseType = iGetTargetExerciseType(cloud_Data);
            target_item.sExerciseType = new String(cloud_Data.sGol_Type);
            target_item.cloud_TargetGol = cloud_Data;

            target_item.vSetDaysReached();
            target_item.vSetHalfWayReached();

//            Log.e("Jerry","-------------------------------------------------------------------------");
//            Log.e("Jerry","cloud_Data.sGol_Item = " + cloud_Data.sGol_Item);
//            Log.e("Jerry","cloud_Data.sStartDate = " + cloud_Data.sStartDate);
//            Log.e("Jerry","target_item.iOrder = " + target_item.iOrder);
//            Log.e("Jerry","target_item.fTargetVal = " + target_item.fTargetVal);
//            Log.e("Jerry","cloud_Data.sGol_Val = " + cloud_Data.sGol_Val);
//            Log.e("Jerry","target_item.iDays_Target = " + target_item.iDays_Target);
//            Log.e("Jerry","target_item.iDays_Current = " + target_item.iDays_Current);
//            Log.e("Jerry","target_item.iDays_Left = " + target_item.iDays_Left);
//            Log.e("Jerry","target_item.sExerciseType = " + target_item.sExerciseType);

            return bRet;
        }

        public float fGetTargetVal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            float fVal = 0;

            fVal = Rtx_TranslateValue.fString2Float(cloud_Data.sGol_Val);

            return fVal;
        }

        public float fGetStartVal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            float fVal = 0;

            fVal = Rtx_TranslateValue.fString2Float(cloud_Data.sUsr_Wei);

            return fVal;
        }

        public int iGetTargetDays(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            int iDays = 0;

            if(cloud_Data == null)
            {
                return iDays;
            }

            iDays = Rtx_TranslateValue.iString2Int(cloud_Data.sGol_Duration);

            return iDays;
        }

        public int iGetCurrentDays(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            int iDays = 0;
            Calendar cal_Current = GlobalData.getInstance();

            if(cloud_Data == null)
            {
                return iDays;
            }

            Calendar cal_Start = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");

            iDays = Rtx_Calendar.getDiffDays(cal_Start,cal_Current);
            iDays = iDays + 1;  //Add First Day

            return iDays;
        }

        public int iGetLeftDays(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            int iDays = 0;

            if(cloud_Data == null)
            {
                return iDays;
            }

            if(!bIsContinue(cloud_Data))
            {
                int         iDurationDays = Rtx_TranslateValue.iString2Int(cloud_Data.sGol_Duration);
                Calendar    cal_Current = GlobalData.getInstance();
                Calendar    cal_Target = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");

                cal_Target.add(Calendar.DAY_OF_MONTH, iDurationDays - 1);

                iDays = Rtx_Calendar.getDiffDays(cal_Current,cal_Target);

                iDays = iDays + 1;  //Add Firsrt Day
            }

            return iDays;
        }

        /***** Parse Freq *****/
        public boolean bParseFreqItem(TargetTrainFreqItem target_item , CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            boolean bRet = false;

            if(target_item == null)
            {
                return bRet;
            }

            if(cloud_Data == null)
            {
                return bRet;
            }

            target_item.cal_Start = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");
            target_item.iTargetDaysPerWeek = iGetTargetDaysPerWeek(cloud_Data);
            target_item.iWeeks_Target = iGetTargetWeeks(cloud_Data);

            int iDayOfWeek = target_item.cal_Start.get(target_item.cal_Start.DAY_OF_WEEK);

            target_item.cal_End = (Calendar)target_item.cal_Start.clone();
            target_item.cal_End.add(Calendar.DATE, (1 - iDayOfWeek));
            target_item.cal_End.add(Calendar.WEEK_OF_YEAR,target_item.iWeeks_Target);

            target_item.cal_HalfWay = (Calendar) target_item.cal_Start.clone();
            int iHalfWayDay = (target_item.iWeeks_Target / 2) * 7;
            target_item.cal_HalfWay.add(Calendar.DATE, (1 - iDayOfWeek));
            target_item.cal_HalfWay.add(Calendar.DATE,iHalfWayDay);

            target_item.bContinue = bIsContinue(cloud_Data);
            target_item.iWeeks_Current = iGetCurrentWeek(cloud_Data);
            target_item.iWeeks_Left = iGetLeftWeeks(cloud_Data);
            target_item.iExerciseType = iGetTargetExerciseType(cloud_Data);
            target_item.sExerciseType = new String(cloud_Data.sGol_Type);

            target_item.cloud_TargetGol = cloud_Data;

            target_item.vSetDaysReached();
            target_item.vSetHalfWayReached();

            return bRet;
        }

        public int iGetTargetExerciseType( CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data )
        {
            int iType = TARGET_EXERCISE_ALL;

            if(cloud_Data == null)
            {
                return iType;
            }

            if(cloud_Data.sGol_Type.equals("R"))    {   iType = TARGET_EXERCISE_RUNNING;    }
            if(cloud_Data.sGol_Type.equals("E"))    {   iType = TARGET_EXERCISE_ELLIPTICAL;    }
            if(cloud_Data.sGol_Type.equals("B"))    {   iType = TARGET_EXERCISE_BIKONG;    }
            if(cloud_Data.sGol_Type.equals("A"))    {   iType = TARGET_EXERCISE_ALL;    }

            return iType;
        }

        public int iGetTargetDaysPerWeek( CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data )
        {
            int iDays = 0;

            if(cloud_Data == null)
            {
                return iDays;
            }

            iDays = Rtx_TranslateValue.iString2Int(cloud_Data.sGol_Val);
            return iDays;
        }

        public int iGetTargetWeeks( CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data )
        {
            int iWeeks = 0;

            if(cloud_Data == null)
            {
                return iWeeks;
            }

            iWeeks = (int)Rtx_TranslateValue.fString2Float(cloud_Data.sGol_Duration);
            return iWeeks;
        }

        public int iGetCurrentWeek( CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data )
        {
            int iWeeks = 0;
            int iDiffDays = 0;
            int iDayOfWeek = 0;

            if(cloud_Data == null)
            {
                return iWeeks;
            }

            Calendar    cal_Current = GlobalData.getInstance();
            Calendar    cal_Start = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");
            iDiffDays = Rtx_Calendar.getDiffDays(cal_Start,cal_Current);
            iDayOfWeek = cal_Start.get(cal_Start.DAY_OF_WEEK);
            if(iDiffDays >= 0)
            {
                iWeeks = ((iDiffDays - (1 - iDayOfWeek)) / 7) + 1;
            }

            return iWeeks;
        }

        public int iGetLeftWeeks( CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data )
        {
            int iWeeks = 0;
            int iCurrentWeeks = 0;
            int iDiffDays = 0;
            int iDuration = 0;
            int iDayOfWeek = 0;

            if(cloud_Data == null)
            {
                return iWeeks;
            }

            if(!bIsContinue(cloud_Data))
            {
                Calendar    cal_Current = GlobalData.getInstance();
                Calendar    cal_Start = Rtx_Calendar.cStr2Calendar(cloud_Data.sStartDate,"yyyy-MM-dd");
                iDiffDays = Rtx_Calendar.getDiffDays(cal_Start,cal_Current);
                iDayOfWeek = cal_Start.get(cal_Start.DAY_OF_WEEK);
                if(iDiffDays >= 0)
                {
                    iCurrentWeeks = ((iDiffDays - (1 - iDayOfWeek))/ 7) + 1;
                }

                iDuration = Rtx_TranslateValue.iString2Int(cloud_Data.sGol_Duration);

                iWeeks = iDuration - iCurrentWeeks + 1;
            }

            return iWeeks;
        }

        /***** Get User Excise Value *****/

        public boolean bGetCurrentDis()
        {
            boolean bRet = false;

            if(bIsDistance())
            {
                String stotal = CloudDataStruct.CloudData_16.sdata_out[Cloud_16_GET_EW_SUM_HIS.Output.TTL_DST];
                if(stotal.compareTo("") != 0)
                {
                    targetTrainInfo_Dis.fCurrentVal = Rtx_TranslateValue.fString2Float(stotal);

                    //20181223 修正變更轉換單位所產生的誤差
                    if(Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(targetTrainInfo_Dis.fCurrentVal), 1) >= Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(targetTrainInfo_Dis.fTargetVal), 1))
                    {
                        targetTrainInfo_Dis.bTargetReached = true;
                    }
                }
                else
                {
                    targetTrainInfo_Dis.fCurrentVal = 0;
                }

                bRet = true;
            }

            return bRet;
        }

        public boolean bGetCurrentCal()
        {
            boolean bRet = false;

            if(bIsCalories())
            {
                String stotal = CloudDataStruct.CloudData_16.sdata_out[Cloud_16_GET_EW_SUM_HIS.Output.TTL_CAL];
                if(stotal.compareTo("") != 0)
                {
                    targetTrainInfo_Cal.fCurrentVal = Rtx_TranslateValue.fString2Float(stotal);

                    if(targetTrainInfo_Cal.fCurrentVal >= targetTrainInfo_Cal.fTargetVal)
                    {
                        targetTrainInfo_Cal.bTargetReached = true;
                    }
                }
                else
                {
                    targetTrainInfo_Cal.fCurrentVal = 0;
                }

                bRet = true;
            }

            return bRet;
        }


        public void vWeightRoundVal()
        {
            if(targetTrainInfo_Wei == null)
            {
                return;
            }

            targetTrainInfo_Wei.fStartVal = Rtx_TranslateValue.fRoundingVal(targetTrainInfo_Wei.fStartVal,5);
            targetTrainInfo_Wei.fTargetVal = Rtx_TranslateValue.fRoundingVal(targetTrainInfo_Wei.fTargetVal,5);
        }

        public boolean bGetCurrentWeight()
        {
            boolean bRet = false;

            if(targetTrainInfo_Wei == null)
            {
                return bRet;
            }

            if(bIsWeight())
            {
                targetTrainInfo_Wei.fCurrentVal = Rtx_TranslateValue.fString2Float(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_WEI]);

                if(targetTrainInfo_Wei.fTargetVal > targetTrainInfo_Wei.fStartVal)//增重
                {
                    //20181221 修正變更轉換單位所產生的誤差
                    if(Rtx_TranslateValue.fRoundingVal(EngSetting.Weight.getVal(targetTrainInfo_Wei.fCurrentVal), 1) >= Rtx_TranslateValue.fRoundingVal(EngSetting.Weight.getVal(targetTrainInfo_Wei.fTargetVal), 1))
                    {
                        targetTrainInfo_Wei.bTargetReached = true;
                    }
                }
                else    //減重
                {
                    //20181221 修正變更轉換單位所產生的誤差
                    if(Rtx_TranslateValue.fRoundingVal(EngSetting.Weight.getVal(targetTrainInfo_Wei.fCurrentVal), 1) <= Rtx_TranslateValue.fRoundingVal(EngSetting.Weight.getVal(targetTrainInfo_Wei.fTargetVal), 1))
                    {
                        targetTrainInfo_Wei.bTargetReached = true;
                    }
                }

                bRet = true;
            }

            return bRet;
        }

        public boolean bGetCurrentBodyFat()
        {
            boolean bRet = false;

            if(targetTrainInfo_BdyFat == null)
            {
                return bRet;
            }

            if(bIsBodyFat())
            {
                targetTrainInfo_BdyFat.fCurrentVal = Rtx_TranslateValue.fString2Float(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.BDY_FAT]);

                if(targetTrainInfo_BdyFat.fTargetVal > targetTrainInfo_BdyFat.fStartVal)//增重
                {
                    if(targetTrainInfo_BdyFat.fCurrentVal >= targetTrainInfo_BdyFat.fTargetVal)
                    {
                        targetTrainInfo_BdyFat.bTargetReached = true;
                    }
                }
                else    //減重
                {
                    if(targetTrainInfo_BdyFat.fCurrentVal <= targetTrainInfo_BdyFat.fTargetVal)
                    {
                        targetTrainInfo_BdyFat.bTargetReached = true;
                    }
                }

                bRet = true;
            }

            return bRet;
        }

        public boolean bGetCurrentFreq(ArrayList<String> list_sDate)
        {

            boolean bRet = false;

            if(targetTrainInfo_Freq == null)
            {
                return bRet;
            }

            if(list_sDate != null)
            {
                int iIndex = 0;
                int iSize = list_sDate.size();
                Calendar cal;

                for(iIndex = 0 ; iIndex < iSize ; iIndex++)
                {
                    cal = Rtx_Calendar.cStr2Calendar(list_sDate.get(iIndex),"yyyy-MM-dd");
                    targetTrainInfo_Freq.list_ExerciseCal.add(cal);
                }

                if(targetTrainInfo_Freq.iWeeks_Target > 0)
                {
                    //用目前週數當作最大size
                    iSize = targetTrainInfo_Freq.iWeeks_Current;

                    Calendar cal_Week;

                    for(iIndex = 0 ; iIndex < iSize ; iIndex++)
                    {
                        int iIndex_Days = 0;
                        int iSize_Days = 0;
                        int iAchievementCount = 0;

                        iSize_Days = targetTrainInfo_Freq.list_ExerciseCal.size();
                        cal_Week = (Calendar) targetTrainInfo_Freq.cal_Start.clone();
                        cal_Week.add(Calendar.DAY_OF_YEAR,(7 * iIndex));

                        for(iIndex_Days = 0 ; iIndex_Days < iSize_Days ; iIndex_Days++)
                        {
                            if( (cal_Week.get(Calendar.YEAR) == targetTrainInfo_Freq.list_ExerciseCal.get(iIndex_Days).get(Calendar.YEAR)) &&
                                (cal_Week.get(Calendar.WEEK_OF_YEAR) == targetTrainInfo_Freq.list_ExerciseCal.get(iIndex_Days).get(Calendar.WEEK_OF_YEAR)))
                            {
                                iAchievementCount ++;
                            }
                        }

                        targetTrainInfo_Freq.list_AchievmentDaysInWeek.add(iAchievementCount);

                        if(iAchievementCount >= targetTrainInfo_Freq.iTargetDaysPerWeek)
                        {
                            targetTrainInfo_Freq.iAchievmentWeeks++;
                        }
                    }
                }

                if(targetTrainInfo_Freq.iAchievmentWeeks >= targetTrainInfo_Freq.iWeeks_Target)
                {
                    targetTrainInfo_Freq.bTargetReached = true;
                }
            }

            return bRet;
        }

        /***** Common *****/

        public boolean bIsContinue(CloudDataStruct.CLOUD_TATGET_GOAL cloud_Data)
        {
            boolean bRet = false;

            if(cloud_Data.sGol_Sts.equals("K"))
            {
                bRet = true;
            }

            return bRet;
        }

        public int getTargetCount()
        {
            int iIndex = 0;
            int iSample = 0x00000001;
            int iCount = 0;

            for(iIndex = 0 ; iIndex < TARGET_TRAIN_MAX ; iIndex++)
            {
                if((iCurrentMode & iSample) > 0)
                {
                    iCount ++;
                }
                iSample = iSample << 4;
            }

            return iCount;
        }

        public boolean bIsDistance()
        {
            boolean bRet = false;

            if((iCurrentMode & TARGET_TRAIN_DIS) > 0)
            {
                bRet = true;
            }

            return bRet;
        }

        public boolean bIsCalories()
        {
            boolean bRet = false;

            if((iCurrentMode & TARGET_TRAIN_CAL) > 0)
            {
                bRet = true;
            }

            return bRet;
        }

        public boolean bIsWeight()
        {
            boolean bRet = false;

            if((iCurrentMode & TARGET_TRAIN_WEI) > 0)
            {
                bRet = true;
            }

            return bRet;
        }

        public boolean bIsBodyFat()
        {
            boolean bRet = false;

            if((iCurrentMode & TARGET_TRAIN_BDY_FAT) > 0)
            {
                bRet = true;
            }

            return bRet;
        }

        public boolean bIsFreq()
        {
            boolean bRet = false;

            if((iCurrentMode & TARGET_TRAIN_FREQ) > 0)
            {
                bRet = true;
            }

            return bRet;
        }
    }

    /***** My Gym *****/

    public static class CLASS_DETAIL_INFO
    {
        public Calendar cStartDateTime;
        public Calendar cEndDateTime;
        public ArrayList<PHOTO_INFO>   list_Pho;

        public String sTime = "";
        public String sClassName = "";
        public String sInstructor = "";
        public String sContents = "";
        public String sClassID = "";

        public static class PHOTO_INFO
        {
            public String  sUri;
            public Boolean bAlreadySet = false;
        }

        public CLASS_DETAIL_INFO()
        {
            cStartDateTime = GlobalData.getInstance();
            cEndDateTime = GlobalData.getInstance();
            list_Pho = new ArrayList<PHOTO_INFO>();
        }

        public void vClear()
        {
            cStartDateTime = GlobalData.getInstance();
            cEndDateTime = GlobalData.getInstance();
            list_Pho.clear();

            sTime = "";
            sClassName = "";
            sInstructor = "";
            sContents = "";
            sClassID = "";
        }

        public void vImportCloudData()
        {
            if(CloudDataStruct.CloudData_32.list_CloudClassDetailInfo == null)
            {
                return;
            }

            if(CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.size() <= 0)
            {
                return;
            }

            CloudDataStruct.CLOUD_GYM_CLASS_DETAIL_INFO mCLOUD_GYM_CLASS_DETAIL_INFO = CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.get(0);

            cStartDateTime = Rtx_Calendar.cStr2Calendar(mCLOUD_GYM_CLASS_DETAIL_INFO.sUser_CLS_STR_TIM,"yyyy-MM-dd HH:mm:ss");
            cEndDateTime = Rtx_Calendar.cStr2Calendar(mCLOUD_GYM_CLASS_DETAIL_INFO.sUser_CLS_END_TIM,"yyyy-MM-dd HH:mm:ss");

            if((cStartDateTime != null) && (cEndDateTime != null))
            {
                String sStart = Rtx_Calendar.sCalendar2Str(cStartDateTime,"HH:mm");
                String sEnd = Rtx_Calendar.sCalendar2Str(cEndDateTime,"HH:mm");
                sTime = new String(sStart + " - " + sEnd);
            }

            if(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_NAM != null)
            {
                sClassName = new String(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_NAM);
            }

            if(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_TCH_NAM != null)
            {
                sInstructor = new String(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_TCH_NAM);
            }

            if(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_CTT != null)
            {
                sContents = new String(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_CTT);
            }

            if(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_ID != null)
            {
                sClassID = new String(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_ID);
            }

            if(mCLOUD_GYM_CLASS_DETAIL_INFO.sCLS_PHO != null)
            {
                int iIndex = 0;
                int iSize = CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.size();

                for ( ; iIndex < iSize ; iIndex++)
                {
                    PHOTO_INFO photoInfo = new PHOTO_INFO();
                    photoInfo.sUri = CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.get(iIndex).sCLS_PHO;
                    list_Pho.add(photoInfo);
                }
            }
        }
    }

    /***** My Workout *****/

    public static class INTERVAL_STAGE_INFO
    {
        public int   iTime;
        public float fSpeed;
        public float fIncline;

        public INTERVAL_STAGE_INFO(int iTimeVal, float fSpeedVal, float fInclineVal)
        {
            iTime = iTimeVal;
            fSpeed = fSpeedVal;
            fIncline = fInclineVal;
        }

        public INTERVAL_STAGE_INFO()
        {

        }

        public void vSetSpeed(float fVal)
        {
            fSpeed = EngSetting.Distance.fTranslat2KM(fVal);
        }

        public void vSetIncline(float fVal)
        {
            fIncline = fVal;
        }

        public float fGetSpeed()
        {
            float fVal = 0;

            fVal = Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(fSpeed),1);

            return fVal;
        }

        public float fGetIncline()
        {
            float fVal = 0;

            fVal = fIncline;

            return fVal;
        }

        public void clear()
        {
            iTime = 0;
            fSpeed = 0;
            fIncline = 0;
        }
    }

    public static class WORKOUT_ITEM_INFO
    {
        public String  sName = null;
        public String  sType = null;
        public String  sID = null;
        public ArrayList<INTERVAL_STAGE_INFO> list_Stage;
        public ArrayList<String> list_UploadStageData;

        public WORKOUT_ITEM_INFO()
        {
            list_Stage = new ArrayList<INTERVAL_STAGE_INFO>();
            list_UploadStageData = new ArrayList<String>();
        }

        public void clear()
        {
            sName = "";
            sType = "";
            sID = "";

            if(list_Stage != null)
            {
                list_Stage.clear();
            }

            if(list_UploadStageData != null)
            {
                list_UploadStageData.clear();
            }
        }

        public void copyTo(WORKOUT_ITEM_INFO info)
        {
            info.sName = new String(sName);
            info.sType = new String(sType);
            info.sID = new String(sID);

            if(list_Stage == null)
            {
                return;
            }

            {
                info.list_Stage.clear();

                int iIndex = 0;
                int iSize = list_Stage.size();

                for( ; iIndex < iSize ; iIndex++)
                {
                    INTERVAL_STAGE_INFO stage_info = new INTERVAL_STAGE_INFO();
                    stage_info.iTime = list_Stage.get(iIndex).iTime;
                    stage_info.fSpeed = list_Stage.get(iIndex).fSpeed;
                    stage_info.fIncline = list_Stage.get(iIndex).fIncline;
                    info.list_Stage.add(stage_info);
                }
            }
        }

        public void vImportCloudNameData(int iItemIndex)
        {
            if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null)
            {
                return;
            }

            if(CloudDataStruct.CloudData_10.list_CloudWorkoutName.size() <=0)
            {
                return;
            }

            {

                sID = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sWRK_ID);
                sType = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sMCH_TYP);
                sName = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sWRK_NAM);

//                Log.e("Jerry","sID = " + sID);
//                Log.e("Jerry","sType = " + sType);
//                Log.e("Jerry","sName = " + sName);
            }
        }

        public void vImportCloudStageData()
        {
            if(CloudDataStruct.CloudData_09.sdata_out == null)
            {
                return;
            }

            if(CloudDataStruct.CloudData_09.sdata_out.length <=0)
            {
                return;
            }

            int iCount = 3;

            if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
            {
                iCount = 3;
            }
            else
            {
                iCount = 2;
            }

            int iIndex = 0;
            int iSize = 50;
            int iIntervalIndex = Cloud_09_GET_WRK_ITV_SET.Output.ITV_01;

            list_Stage.clear();

            for( ; iIndex < iSize ; iIndex++)
            {
                String sStage = CloudDataStruct.CloudData_09.sdata_out[iIntervalIndex];

                //Log.e("Jerry","sStage[ " + iIntervalIndex + " ] = " + sStage);

                String sSep[] = sStage.split(",");

                if(sSep.length == iCount)
                {
                    if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
                    {
                        INTERVAL_STAGE_INFO stageInfo = new INTERVAL_STAGE_INFO();
                        stageInfo.fSpeed = Rtx_TranslateValue.fString2Float(sSep[0]);
                        stageInfo.fIncline = Rtx_TranslateValue.fString2Float(sSep[1]);
                        stageInfo.iTime = iGetMin(sSep[2]);

                        if(stageInfo.iTime == 0)
                        {
                            break;
                        }
                        else
                        {
                            list_Stage.add(stageInfo);
                        }
                    }
                    else
                    {
                        INTERVAL_STAGE_INFO stageInfo = new INTERVAL_STAGE_INFO();
                        stageInfo.fSpeed = 0;
                        stageInfo.fIncline = Rtx_TranslateValue.fString2Float(sSep[0]);
                        stageInfo.iTime = iGetMin(sSep[1]);

                        if(stageInfo.iTime == 0)
                        {
                            break;
                        }
                        else
                        {
                            list_Stage.add(stageInfo);
                        }
                    }
                }
                iIntervalIndex++;
            }
        }

        // mode 0 Treadmill , mode 1 Bike
        public void vExportCloudData(int iMode)
        {
            if(list_Stage == null)
            {
                return;
            }

            list_UploadStageData.clear();

            int iIndex = 0;
            int iSize = list_Stage.size();

            for( ; iIndex < iSize ; iIndex++)
            {
                String sSPeed = new String(Rtx_TranslateValue.sFloat2String(list_Stage.get(iIndex).fSpeed,1));
                String sIncline = new String(Rtx_TranslateValue.sFloat2String(list_Stage.get(iIndex).fIncline,1));
                String sTime = new String(list_Stage.get(iIndex).iTime + ":" + "00");

                String sStage;

                if(iMode == 0)
                {
                    sStage = new String(sSPeed + "," + sIncline + "," + sTime);
                }
                else
                {
                    sStage = new String(sIncline + "," + sTime);
                }

                list_UploadStageData.add(sStage);
            }
        }

        public int iGetMin(String sTime)
        {
            int iMin = 0;

            if(sTime == null)
            {
                return iMin;
            }

            String sTimes[] = sTime.split(":");
            String sMin;

            if(sTimes.length == 2)
            {
                sMin = sTimes[0];
                iMin = Rtx_TranslateValue.iString2Int(sMin);
            }

            return iMin;
        }

        public final int TRANSLATE_SPEDD = 0;
        public final int TRANSLATE_INCLINE = 1;
        public boolean bExportStageInfoToMinList(ArrayList<Float> list_Info, int iTranslate)
        {
            if(list_Stage == null)
            {
                return false;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();

            for( ; iIndex < iSize ; iIndex++ )
            {
                int iStage_Index = 0;
                int iStage_Size = list_Stage.get(iIndex).iTime;

                for( iStage_Index = 0 ; iStage_Index < iStage_Size ; iStage_Index++ )
                {
                    if(iTranslate == TRANSLATE_SPEDD)
                    {
                        list_Info.add(list_Stage.get(iIndex).fSpeed);
                    }
                    else
                    {
                        list_Info.add(list_Stage.get(iIndex).fIncline);
                    }
                }
            }

            return true;
        }

        public int iGetTotalMin()
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            int TotalMin = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                TotalMin += list_Stage.get(iIndex).iTime;
            }

            return TotalMin;
        }

        public float fGetTotalDistance()
        {
            float fDistance = 0;

            fDistance = EngSetting.Distance.getVal(fGetTotalDistanceKm());

            return fDistance;
        }

        public float fGetDistance(int iRPM)
        {
            float fDistance = 0;

            fDistance = EngSetting.Distance.getVal(fGetDistanceKm(iRPM));

            return fDistance;
        }

        public float fGetTotalDistanceKm()
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fDistance = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fDistance += Rtx_FitnessCalculation.f_distance_calculate(list_Stage.get(iIndex).iTime*60,EngSetting.i_Get_ExerciseType(),list_Stage.get(iIndex).fSpeed);
            }

            return fDistance;
        }

        public float fGetDistanceKm(int iRPM)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fDistance = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fDistance += Rtx_FitnessCalculation.f_distance_calculate(list_Stage.get(iIndex).iTime*60,EngSetting.i_Get_ExerciseType(),iRPM);
            }

            return fDistance;
        }

        public int iGetCalories(float fWeight)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fCalories = 0;
            int iCalories = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fCalories += Rtx_FitnessCalculation.f_calories_calculate
                        (
                            list_Stage.get(iIndex).iTime*60*1000,
                            EngSetting.i_Get_ExerciseType(),
                            list_Stage.get(iIndex).fSpeed,
                            list_Stage.get(iIndex).fIncline,
                            fWeight
                        );
            }

            iCalories = (int)Rtx_TranslateValue.fRoundingVal(fCalories,0);

            return iCalories;
        }

        public int iGetCalories(float fWeight, int iRPM)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fCalories = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fCalories += Rtx_FitnessCalculation.f_calories_calculate
                        (
                                list_Stage.get(iIndex).iTime*60*1000,
                                EngSetting.i_Get_ExerciseType(),
                                iRPM,
                                list_Stage.get(iIndex).fIncline,
                                fWeight
                        );
            }

            return (int)fCalories;
        }

        public int iGetAvgPaceMin()
        {
            int iAvgPaceMin = 0;

            {
                iAvgPaceMin = Rtx_FitnessCalculation.cal_avg_pace_calculate(fGetTotalDistance(),iGetTotalMin());
            }

            return iAvgPaceMin;
        }

        public int iGetAvgPaceSec()
        {
            int iAvgPaceSec = 0;

            {
                iAvgPaceSec = Rtx_FitnessCalculation.cal_avg_pace_calculate_sec(fGetTotalDistance(),iGetTotalMin() * 60);
            }

            return iAvgPaceSec;
        }


        public float fGetAvgSpeed()
        {
            if(list_Stage == null)
            {
                return 0;
            }

            float fAvgSpeed = 0;

            {
                fAvgSpeed = Rtx_FitnessCalculation.f_avg_speed_calculate(fGetTotalDistance(),iGetTotalMin()*60);
            }

            return fAvgSpeed;
        }

        public float fGetAvgSpeed(int iRPM)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fAvgSpeed = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fAvgSpeed = Rtx_FitnessCalculation.f_avg_speed_calculate(fGetDistance(iRPM),iGetTotalMin()*60);
            }

            return fAvgSpeed;
        }

        public float fGetWatt(int iRPM)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fWatt = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                fWatt += Rtx_FitnessCalculation.f_watt_calculate(
                        list_Stage.get(iIndex).iTime*60,
                        EngSetting.i_Get_ExerciseType(),
                        iRPM,
                        list_Stage.get(iIndex).fIncline);
            }

            return fWatt;
        }

        public float fGetAvgWatt(int iRPM)
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            float fAvgWatt = 0;

            fAvgWatt = fGetWatt(iRPM) / (float)iGetTotalMin();

            return fAvgWatt;
        }
    }

    /***** HIIT *****/

    public static class HIIT_ITEM_INFO
    {
        public String  sName = null;
        public String  sType = null;
        public String  sID = null;
        public INTERVAL_STAGE_INFO stage_WarmUp;
        public INTERVAL_STAGE_INFO stage_CoolDown;
        public ArrayList<INTERVAL_STAGE_INFO> list_Stage;
        public ArrayList<String> list_UploadStageData;

        public HIIT_ITEM_INFO()
        {
            list_Stage = new ArrayList<INTERVAL_STAGE_INFO>();
            list_UploadStageData = new ArrayList<String>();
            stage_WarmUp = new INTERVAL_STAGE_INFO();
            stage_CoolDown = new INTERVAL_STAGE_INFO();
        }

        public void clear()
        {
            sName = null;
            sType = null;
            sID = null;

            if(stage_WarmUp != null)
            {
                stage_WarmUp.iTime = 0;
                stage_WarmUp.fSpeed = 0;
                stage_WarmUp.fIncline = 0;
            }

            if(stage_CoolDown != null)
            {
                stage_CoolDown.iTime = 0;
                stage_CoolDown.fSpeed = 0;
                stage_CoolDown.fIncline = 0;
            }

            if(list_Stage != null)
            {
                list_Stage.clear();
            }

            if(list_UploadStageData != null)
            {
                list_UploadStageData.clear();
            }
        }

        public void copyTo(HIIT_ITEM_INFO info)
        {
            if(sName != null)   { info.sName = new String(sName); }
            if(sType != null)   { info.sType = new String(sType); }
            if(sID != null)     { info.sID = new String(sID); }

            if(list_Stage == null)
            {
                return;
            }

            {
                info.stage_WarmUp = new INTERVAL_STAGE_INFO(stage_WarmUp.iTime,stage_WarmUp.fSpeed,stage_WarmUp.fIncline);
                info.stage_CoolDown = new INTERVAL_STAGE_INFO(stage_CoolDown.iTime,stage_CoolDown.fSpeed,stage_CoolDown.fIncline);

                info.list_Stage.clear();

                int iIndex = 0;
                int iSize = list_Stage.size();

                for( ; iIndex < iSize ; iIndex++)
                {
                    INTERVAL_STAGE_INFO stage_info = new INTERVAL_STAGE_INFO();
                    stage_info.iTime = list_Stage.get(iIndex).iTime;
                    stage_info.fSpeed = list_Stage.get(iIndex).fSpeed;
                    stage_info.fIncline = list_Stage.get(iIndex).fIncline;
                    info.list_Stage.add(stage_info);
                }
            }
        }

        public void vImportCloudNameData(int iItemIndex)
        {
            if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null)
            {
                return;
            }

            if(CloudDataStruct.CloudData_10.list_CloudWorkoutName.size() <=0)
            {
                return;
            }

            {

                sID = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sWRK_ID);
                sType = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sMCH_TYP);
                sName = new String(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iItemIndex).sWRK_NAM);

//                Log.e("Jerry","sID = " + sID);
//                Log.e("Jerry","sType = " + sType);
//                Log.e("Jerry","sName = " + sName);
            }
        }

        public void vImportCloudStageData()
        {
            if(CloudDataStruct.CloudData_09.sdata_out == null)
            {
                return;
            }

            if(CloudDataStruct.CloudData_09.sdata_out.length <=0)
            {
                return;
            }

            int iElementCount = 3;  //Time , Speed , Incline

            int iIndex = 0;
            int iSize = 50;
            int iIntervalIndex = Cloud_09_GET_WRK_ITV_SET.Output.ITV_01;

            list_Stage.clear();

            //Stage Data
            for( ; iIndex < iSize ; iIndex++)
            {
                String sStage = CloudDataStruct.CloudData_09.sdata_out[iIntervalIndex];

                String sSep[] = sStage.split(",");

                if(sSep.length == iElementCount)
                {
                    if(iIntervalIndex == 0)
                    {
                        //Warm Up
                        stage_WarmUp.fSpeed = Rtx_TranslateValue.fString2Float(sSep[0]);
                        stage_WarmUp.fIncline = Rtx_TranslateValue.fString2Float(sSep[1]);
                        stage_WarmUp.iTime = iGetSec(sSep[2]);
                    }
                    else
                    {
                        INTERVAL_STAGE_INFO stageInfo = new INTERVAL_STAGE_INFO();
                        stageInfo.fSpeed = Rtx_TranslateValue.fString2Float(sSep[0]);
                        stageInfo.fIncline = Rtx_TranslateValue.fString2Float(sSep[1]);
                        stageInfo.iTime = iGetSec(sSep[2]);

                        if(stageInfo.iTime == 0)
                        {
                            break;
                        }
                        else
                        {
                            list_Stage.add(stageInfo);
                        }
                    }
                }
                iIntervalIndex++;
            }

            //Cool Down
            int iStageSize = list_Stage.size();

            if(iStageSize > 2)
            {
                if((iStageSize % 2) == 0)
                {
                    stage_CoolDown.fSpeed = 0;
                    stage_CoolDown.fIncline = 0;
                    stage_CoolDown.iTime = 0;
                }
                else
                {
                    stage_CoolDown.fSpeed = list_Stage.get(iStageSize - 1).fSpeed;
                    stage_CoolDown.fIncline = list_Stage.get(iStageSize - 1).fIncline;
                    stage_CoolDown.iTime = list_Stage.get(iStageSize - 1).iTime;

                    list_Stage.remove(iStageSize - 1);
                }
            }
        }

        public void vExportCloudData()
        {
            if(list_Stage == null)
            {
                return;
            }

            list_UploadStageData.clear();

            //Warm Up
            {
                String sSPeed = new String(Rtx_TranslateValue.sFloat2String(stage_WarmUp.fSpeed,1));
                String sIncline = new String(Rtx_TranslateValue.sFloat2String(stage_WarmUp.fIncline,1));
                String sTime = new String("00" + ":" + stage_WarmUp.iTime);
                String sStage = new String(sSPeed + "," + sIncline + "," + sTime);

                list_UploadStageData.add(sStage);
            }

            int iIndex = 0;
            int iSize = list_Stage.size();

            for( ; iIndex < iSize ; iIndex++)
            {
                String sSPeed = new String(Rtx_TranslateValue.sFloat2String(list_Stage.get(iIndex).fSpeed,1));
                String sIncline = new String(Rtx_TranslateValue.sFloat2String(list_Stage.get(iIndex).fIncline,1));
                String sTime = new String("00" + ":" + list_Stage.get(iIndex).iTime);
                String sStage = new String(sSPeed + "," + sIncline + "," + sTime);

                list_UploadStageData.add(sStage);
            }

            //Cool Down
            {
                String sSPeed = new String(Rtx_TranslateValue.sFloat2String(stage_CoolDown.fSpeed,1));
                String sIncline = new String(Rtx_TranslateValue.sFloat2String(stage_CoolDown.fIncline,1));
                String sTime = new String("00" + ":" + stage_CoolDown.iTime);
                String sStage = new String(sSPeed + "," + sIncline + "," + sTime);

                list_UploadStageData.add(sStage);
            }
        }

        public int iGetSec(String sTime)
        {
            int iMin = 0;
            int iSec = 0;

            if(sTime == null)
            {
                return iSec;
            }

            String sTimes[] = sTime.split(":");
            String sMin;
            String sSec;

            if(sTimes.length == 2)
            {
                sMin = sTimes[0];
                sSec = sTimes[1];
                iMin = Rtx_TranslateValue.iString2Int(sMin);
                iSec = Rtx_TranslateValue.iString2Int(sSec) + (iMin * 60);
            }

            return iSec;
        }

        public final int TRANSLATE_SPEDD = 0;
        public final int TRANSLATE_INCLINE = 1;


        public int iGetListStageSec()
        {
            if(list_Stage == null)
            {
                return 0;
            }

            int iIndex = 0;
            int iSize = list_Stage.size();
            int TotalSec = 0;

            for( ; iIndex < iSize ; iIndex++)
            {
                TotalSec += list_Stage.get(iIndex).iTime;
            }

            return TotalSec;
        }

        public int iGetTotalSec()
        {
            int iTotalSec = iGetListStageSec();

            if(stage_WarmUp.iTime > 0)
            {
                iTotalSec += stage_WarmUp.iTime;
            }

            if(stage_CoolDown.iTime > 0)
            {
                iTotalSec += stage_CoolDown.iTime;
            }

            return iTotalSec;
        }

        public int iGetTotalSize()
        {
            int iSize = 0;

            if(stage_WarmUp.iTime > 0)
            {
                iSize ++;
            }

            if(list_Stage != null)
            {
                iSize += list_Stage.size();
            }

            if(stage_CoolDown.iTime > 0)
            {
                iSize ++;
            }

            return iSize;
        }

        public String sGetDisplayName()
        {
            String str = "";

            if(sName != null)
            {
                if(sName.length() > 3)
                {
                    if(sName.substring(0,3).equals("#H#"))
                    {
                        str = new String(sName.substring(3));
                    }
                    else
                    {
                        str = new String(sName);
                    }
                }
                else
                {
                    str = new String(sName);
                }
            }

            return str;
        }

        public int iGetTotalItemSize()
        {
            int iSize = 0;

            if(stage_WarmUp.iTime > 0)
            {
                iSize++;
            }

            if(stage_CoolDown.iTime > 0)
            {
                iSize++;
            }

            if(list_Stage != null)
            {
                iSize += list_Stage.size();
            }

            return iSize;
        }

        public boolean bCurrentIsCoolDown(int iProgressIndex)
        {
            if(iProgressIndex == (iGetTotalSize() - 1) && (stage_CoolDown.iTime > 0))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public int iGetCoolDownIndex()
        {
            int iIndex = -1;

            if(stage_CoolDown.iTime <= 0)
            {
                iIndex = -1;
            }
            else
            {
                iIndex = iGetTotalItemSize() - 1;
            }

            return iIndex;
        }

        public INTERVAL_STAGE_INFO getStageInfo(int iProgressIndex)
        {
            INTERVAL_STAGE_INFO progressStageInfo = null;

            if(iProgressIndex >= iGetTotalSize())
            {
                return null;
            }

            if((iProgressIndex == 0) && (stage_WarmUp.iTime > 0))
            {
                progressStageInfo = stage_WarmUp;
            }
            else
            if(iProgressIndex == (iGetTotalSize() - 1) && (stage_CoolDown.iTime > 0))
            {
                progressStageInfo = stage_CoolDown;
            }
            else
            {
                if(stage_WarmUp.iTime > 0)
                {
                    iProgressIndex = iProgressIndex - 1;
                }

                progressStageInfo = list_Stage.get(iProgressIndex);
            }

            return progressStageInfo;
        }

        public int iGetCurrentProgressSec(int iCurrentProgressIndex)
        {
            int iSec = 0;
            int iIndex = 0;
            int iSize = iCurrentProgressIndex;

            INTERVAL_STAGE_INFO stageInfo;

            for( ; iIndex < iSize ; iIndex++ )
            {
                stageInfo = getStageInfo(iIndex);

                if(stageInfo != null)
                {
                    iSec += stageInfo.iTime;
                }
            }

            return iSec;
        }
    }

    /***** HOME *****/

    public static class HOME_USER_INFO {

        final private Integer INFO_DIALOG_MSG_KEY_WELCOME = 0;
        final private Integer INFO_DIALOG_MSG_KEY_CLASS_REMINDER = 1;
        final private Integer INFO_DIALOG_MSG_KEY_SCH_CHANGE = 2;
        final private Integer INFO_DIALOG_MSG_KEY_TARGET_UPDATE = 3;

        Context mContext;

        public TreeMap<Integer,UiDataStruct.HOME_USER_INFO_DIALOG> treeMap_UserInfoDialog;

        public TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> treeMap_SimpleClassInfo_Today;
        public TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> treeMap_SimpleClassInfo_Tomorrow;
        public TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> treeMap_ChangeClassInfo_TodayTomorrow;

        public boolean bUserAlreadyReaded = false;
        public boolean bNewInfo = false;
        public boolean bNeedToUploadFirstLogin = false;
        public boolean bFirstSignUp = false;
        public String  sMsg_Summary = "";

        public int iInfoIcon = R.drawable.home_dialog_mai_icon;
        public int iInfoItemTagIconResId = R.drawable.home_dialog_item_tag;

        public HOME_USER_INFO(Context context)
        {
            mContext = context;

            treeMap_UserInfoDialog = new TreeMap<Integer,UiDataStruct.HOME_USER_INFO_DIALOG>();
            treeMap_SimpleClassInfo_Today = new TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO>();
            treeMap_SimpleClassInfo_Tomorrow = new TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO>();
            treeMap_ChangeClassInfo_TodayTomorrow = new TreeMap<String,CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO>();
        }

        public void clear()
        {
            treeMap_UserInfoDialog.clear();
            //treeMap_SimpleClassInfo_Today.clear();
            //treeMap_SimpleClassInfo_Tomorrow.clear();
            //treeMap_ChangeClassInfo_TodayTomorrow.clear();
            //bUserAlreadyReaded = false;
            sMsg_Summary = "";
            bNewInfo = false;
        }

        public boolean bGetNewInfo()
        {
            return bNewInfo;
        }

        public void vSetAlreadyReaded(boolean bFlag)
        {
            bUserAlreadyReaded = bFlag;
        }

        public boolean bGetAlreadyReaded()
        {
            return bUserAlreadyReaded;
        }

        public void vSetFirstSignUp(boolean bFlag)
        {
            bFirstSignUp = bFlag;
        }

        public boolean bGetFirstSignUp()
        {
            return bFirstSignUp;
        }

        public boolean bCheckFirstLogin()
        {
            boolean bResult = false;

            if(CloudDataStruct.CloudData_20.is_log_in())
            {
                if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T] != null)
                    {
                        if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T].equals("0"))
                        {
                            CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T] = "1";
                            bResult = true;
                        }
                    }
                }
                else
                if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E] != null)
                    {
                        if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E].equals("0"))
                        {
                            CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E] = "1";
                            bResult = true;
                        }
                    }
                }
                else
                if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B] != null)
                    {
                        if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B].equals("0"))
                        {
                            CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B] = "1";
                            bResult = true;
                        }
                    }
                }
                else
                if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R] != null)
                    {
                        if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R].equals("0"))
                        {
                            CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R] = "1";
                            bResult = true;
                        }
                    }
                }
            }

            return bResult;
        }

        public void vImportTodayClass(ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudMyClassInfo)
        {
            if(list_CloudMyClassInfo == null)
            {
                return;
            }

            int iIndex = 0;
            int iSize = list_CloudMyClassInfo.size();
            Calendar cal = GlobalData.getInstance();
            Calendar cToday = GlobalData.getInstance();

            treeMap_SimpleClassInfo_Today.clear();

            for( ; iIndex < iSize ; iIndex++)
            {
                Log.e("Jerry","[ vImportTodayClass 0] list_CloudMyClassInfo.get(iIndex).sUser_CLS_SUC_DT = " + list_CloudMyClassInfo.get(iIndex).sUser_CLS_SUC_DT);
                cal = list_CloudMyClassInfo.get(iIndex).cUser_CLS_STR;

                if(Rtx_Calendar.isSameDate(cal,cToday))
                {
                    if(cal.compareTo(cToday) >= 0)
                    {
                        String sKey = list_CloudMyClassInfo.get(iIndex).sCLS_SUC_DT + list_CloudMyClassInfo.get(iIndex).sCLS_STR_TIM + list_CloudMyClassInfo.get(iIndex).sCLS_ID + iIndex;

                        treeMap_SimpleClassInfo_Today.put(sKey,list_CloudMyClassInfo.get(iIndex));
                    }
                }
            }
        }

        public void vImportTomorrowClass(ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudMyClassInfo)
        {
            if(list_CloudMyClassInfo == null)
            {
                return;
            }

            int iIndex = 0;
            int iSize = list_CloudMyClassInfo.size();
            Calendar cal = GlobalData.getInstance();
            Calendar cTomorrow = GlobalData.getInstance();
            cTomorrow.add(Calendar.DATE,1);

            treeMap_SimpleClassInfo_Tomorrow.clear();

            for( ; iIndex < iSize ; iIndex++)
            {
                cal = list_CloudMyClassInfo.get(iIndex).cUser_CLS_STR;

                if(Rtx_Calendar.isSameDate(cal,cTomorrow))
                {
                    {
                        String sKey = list_CloudMyClassInfo.get(iIndex).sCLS_SUC_DT + list_CloudMyClassInfo.get(iIndex).sCLS_STR_TIM + list_CloudMyClassInfo.get(iIndex).sCLS_ID + iIndex;

                        treeMap_SimpleClassInfo_Tomorrow.put(sKey,list_CloudMyClassInfo.get(iIndex));
                    }
                }
            }
        }

        public void vImportClassChange(ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudMyClassInfo)
        {
            if(list_CloudMyClassInfo == null)
            {
                return;
            }

            int iIndex = 0;
            int iSize = list_CloudMyClassInfo.size();
            Calendar cal = GlobalData.getInstance();

            treeMap_ChangeClassInfo_TodayTomorrow.clear();

            for( ; iIndex < iSize ; iIndex++)
            {
                if(list_CloudMyClassInfo.get(iIndex).sREAD_TAG.equals("0"))
                {
                    if(list_CloudMyClassInfo.get(iIndex).sCLS_DEL.equals("2"))
                    {
                        cal = list_CloudMyClassInfo.get(iIndex).cUser_CLS_STR;

//                        if(Rtx_Calendar.isDayInRange(cal,1))   //Today and Tomorrow
                        {
                            String sKey = list_CloudMyClassInfo.get(iIndex).sO_CLS_STR_TIM;
                            treeMap_ChangeClassInfo_TodayTomorrow.put(sKey,list_CloudMyClassInfo.get(iIndex));
                        }
                    }
                }
            }
        }

        public void vSetNeedToUploadFirstLogin(boolean bNeed)
        {
            bNeedToUploadFirstLogin = bNeed;
        }

        public boolean bGetNeedToUploadFirstLogin()
        {
            return bNeedToUploadFirstLogin;
        }

        public boolean bExportSummaryInfo_FirstLogin()
        {
            boolean bResult = false;

            if(bCheckFirstLogin() || bGetFirstSignUp())
            {
                vSetNeedToUploadFirstLogin(true);

                vSetFirstSignUp(true);

                int iImgResId = R.drawable.home_dialog_mai_icon;
                String sTitle = LanguageData.s_get_string(mContext,R.string.welcome);
                UiDataStruct.HOME_USER_INFO_DIALOG userInfo = new UiDataStruct.HOME_USER_INFO_DIALOG(iImgResId, sTitle);

                {
                    String sMsg = LanguageData.s_get_string(mContext, R.string.first_login_welcome);

                    UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(-1, sMsg);
                    userInfo.list_InfoItem.add(infoItem);
                }

                treeMap_UserInfoDialog.put(INFO_DIALOG_MSG_KEY_WELCOME,userInfo);

                bResult = true;
            }

            return bResult;
        }

        public boolean bExportSummaryInfo_TargetUpdate(UiDataStruct.TargetTrainInfo mTargetTrainInfo)
        {
            boolean bResult = false;

            int iImgResId = R.drawable.home_dialog_mai_icon;
            String sTitle = LanguageData.s_get_string(mContext,R.string.target_update);
            UiDataStruct.HOME_USER_INFO_DIALOG userInfo = new UiDataStruct.HOME_USER_INFO_DIALOG(iImgResId, sTitle);

            if(bAddTargetUpdateToList(mTargetTrainInfo, userInfo.list_InfoItem))
            {
                treeMap_UserInfoDialog.put(INFO_DIALOG_MSG_KEY_TARGET_UPDATE,userInfo);
                bResult = true;
            }

            return bResult;
        }

        public boolean bAddTargetUpdateToList(UiDataStruct.TargetTrainInfo mTargetTrainInfo, ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents)
        {
            Boolean bResult = false;

            int iImageResId = R.drawable.home_dialog_item_tag;

            if(mTargetTrainInfo.bIsDistance())
            {
                if(mTargetTrainInfo.targetTrainInfo_Dis.bIsTargetReached())
                {
                    vAddReachedInfoToList(list_Contents,iImageResId,R.string.distance);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Dis.bIsDaysReached())
                {
                    vAddTimesUpInfoToList(list_Contents,iImageResId,R.string.distance);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Dis.bIsHalfDayReached() && mTargetTrainInfo.targetTrainInfo_Dis.cloud_TargetGol.sGol_Tag.equals("0"))
                {
                    vAddHalfWayInfoToList(list_Contents,iImageResId,R.string.distance);
                    bResult = true;
                }
            }

            if(mTargetTrainInfo.bIsCalories())
            {
                if(mTargetTrainInfo.targetTrainInfo_Cal.bIsTargetReached())
                {
                    vAddReachedInfoToList(list_Contents,iImageResId,R.string.calories);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Cal.bIsDaysReached())
                {
                    vAddTimesUpInfoToList(list_Contents,iImageResId,R.string.calories);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Cal.bIsHalfDayReached() && mTargetTrainInfo.targetTrainInfo_Cal.cloud_TargetGol.sGol_Tag.equals("0"))
                {
                    vAddHalfWayInfoToList(list_Contents,iImageResId,R.string.calories);
                    bResult = true;
                }
            }

            if(mTargetTrainInfo.bIsFreq())
            {
                if(mTargetTrainInfo.targetTrainInfo_Freq.bIsTargetReached())
                {
                    vAddReachedInfoToList(list_Contents,iImageResId,R.string.exercise_frequency);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Freq.bIsDaysReached())
                {
                    vAddTimesUpInfoToList(list_Contents,iImageResId,R.string.exercise_frequency);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Freq.bIsHalfDayReached() && mTargetTrainInfo.targetTrainInfo_Freq.cloud_TargetGol.sGol_Tag.equals("0"))
                {
                    vAddHalfWayInfoToList(list_Contents,iImageResId,R.string.exercise_frequency);
                    bResult = true;
                }
            }

            if(mTargetTrainInfo.bIsWeight())
            {
                if(mTargetTrainInfo.targetTrainInfo_Wei.bIsTargetReached())
                {
                    vAddReachedInfoToList(list_Contents,iImageResId,R.string.weight_upper);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Wei.bIsDaysReached())
                {
                    vAddTimesUpInfoToList(list_Contents,iImageResId,R.string.weight_upper);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_Wei.bIsHalfDayReached()  && mTargetTrainInfo.targetTrainInfo_Wei.cloud_TargetGol.sGol_Tag.equals("0"))
                {
                    vAddHalfWayInfoToList(list_Contents,iImageResId,R.string.weight_upper);
                    bResult = true;
                }
            }

            if(mTargetTrainInfo.bIsBodyFat())
            {
                if(mTargetTrainInfo.targetTrainInfo_BdyFat.bIsTargetReached())
                {
                    vAddReachedInfoToList(list_Contents,iImageResId,R.string.body_fat);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_BdyFat.bIsDaysReached())
                {
                    vAddTimesUpInfoToList(list_Contents,iImageResId,R.string.body_fat);
                    bResult = true;
                }
                else if(mTargetTrainInfo.targetTrainInfo_BdyFat.bIsHalfDayReached()  && mTargetTrainInfo.targetTrainInfo_BdyFat.cloud_TargetGol.sGol_Tag.equals("0"))
                {
                    vAddHalfWayInfoToList(list_Contents,iImageResId,R.string.body_fat);
                    bResult = true;
                }
            }

            return bResult;
        }

        private void vAddReachedInfoToList(ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents, int iImageResId, int iTargetTypeResId)
        {
            String sMsg = LanguageData.s_get_string(mContext,R.string.home_msg_reached_1) + " " + LanguageData.s_get_string(mContext,iTargetTypeResId) + LanguageData.s_get_string(mContext,R.string.home_msg_reached_2);

            UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iImageResId,sMsg);
            list_Contents.add(infoItem);
        }

        private void vAddTimesUpInfoToList(ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents, int iImageResId, int iTargetTypeResId)
        {
            String sMsg = LanguageData.s_get_string(mContext,R.string.home_msg_times_up_1) + " " + LanguageData.s_get_string(mContext,iTargetTypeResId) + LanguageData.s_get_string(mContext,R.string.home_msg_times_up_2);

            UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iImageResId,sMsg);
            list_Contents.add(infoItem);
        }

        private void vAddHalfWayInfoToList(ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents, int iImageResId, int iTargetTypeResId)
        {
            String sMsg = LanguageData.s_get_string(mContext,R.string.home_msg_half_way_through_1) + " " + LanguageData.s_get_string(mContext,iTargetTypeResId) + LanguageData.s_get_string(mContext,R.string.home_msg_half_way_through_2);

            UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iImageResId,sMsg);
            list_Contents.add(infoItem);
        }



        public boolean bExportSummaryInfo_ClassReminder()
        {
            boolean bResult = false;

            int iImgResId = R.drawable.home_dialog_mai_icon;
            String sTitle = LanguageData.s_get_string(mContext,R.string.my_class_reminder);
            UiDataStruct.HOME_USER_INFO_DIALOG userInfo = new UiDataStruct.HOME_USER_INFO_DIALOG(iImgResId, sTitle);

            if(bAddClassReminderToList(userInfo.list_InfoItem))
            {
                treeMap_UserInfoDialog.put(INFO_DIALOG_MSG_KEY_CLASS_REMINDER,userInfo);
                bResult = true;
            }

            return bResult;
        }

        public boolean bExportSummaryInfo_ClassChange()
        {
            boolean bResult = false;

            int iImgResId = R.drawable.home_dialog_mai_icon;
            String sTitle = LanguageData.s_get_string(mContext,R.string.schedule_change);
            UiDataStruct.HOME_USER_INFO_DIALOG userInfo = new UiDataStruct.HOME_USER_INFO_DIALOG(iImgResId, sTitle);

            if(bAddClassChangeToList(userInfo.list_InfoItem))
            {
                treeMap_UserInfoDialog.put(INFO_DIALOG_MSG_KEY_SCH_CHANGE,userInfo);
                bResult = true;
            }

            return bResult;
        }

        public boolean bAddClassChangeToList(ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents)
        {
            boolean bResult = false;

            if(treeMap_ChangeClassInfo_TodayTomorrow != null)
            {
                int iIndex = 0;
                int iSize = treeMap_ChangeClassInfo_TodayTomorrow.size();
                CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO classInfo;

                for( ; iIndex < iSize ; iIndex++ )
                {
                    classInfo = treeMap_ChangeClassInfo_TodayTomorrow.get(treeMap_ChangeClassInfo_TodayTomorrow.keySet().toArray()[iIndex]);

                    String sO_CLS_STR_TIM = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm" , "yyyy-MM-dd HH:mm:ss", classInfo.sO_CLS_STR_TIM, 0, 0);
                    //String sCLS_STR_DT_TIM = classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM;
                    String sCLS_STR_DT_TIM = Rtx_Calendar.sParseDate2FormatStr(classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm");

                    String sMsg = "";

                    sMsg +=
                            LanguageData.s_get_string(mContext, R.string.this_week) + " " +
                                    classInfo.sCLS_NAME + " " +
                                    LanguageData.s_get_string(mContext, R.string.has_been_changed_from) + " " +sO_CLS_STR_TIM + " " +
                                    LanguageData.s_get_string(mContext, R.string.to) + " " + sCLS_STR_DT_TIM +
                                    "\n";

                    UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iInfoItemTagIconResId, sMsg);
                    list_Contents.add(infoItem);
                    bResult = true;
                }
            }

            return bResult;
        }

        public boolean bAddClassReminderToList(ArrayList<UiDataStruct.HOME_USER_INFO_ITEM_DIALOG> list_Contents)
        {
            boolean bResult = false;

            if(treeMap_SimpleClassInfo_Today != null)
            {
                int iIndex = 0;
                int iSize = treeMap_SimpleClassInfo_Today.size();
                CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO classInfo;

                for( ; iIndex < iSize ; iIndex++ )
                {
                    classInfo = treeMap_SimpleClassInfo_Today.get(treeMap_SimpleClassInfo_Today.keySet().toArray()[iIndex]);

                    String sMsg = "";
                    //String sCLS_STR_DT_TIM = classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM;
                    String sCLS_STR_DT_TIM = Rtx_Calendar.sParseDate2FormatStr(classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM,"yyyy-MM-dd HH:mm:ss","HH:mm");

                    sMsg +=
                            LanguageData.s_get_string(mContext, R.string.there_is_a) + " " +
                                    classInfo.sCLS_NAME + " " +
                                    LanguageData.s_get_string(mContext, R.string.today_at) + " " + sCLS_STR_DT_TIM + "\n";

                    UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iInfoItemTagIconResId, sMsg);
                    list_Contents.add(infoItem);
                    bResult = true;
                }
            }

            if(treeMap_SimpleClassInfo_Tomorrow != null)
            {
                int iIndex = 0;
                int iSize = treeMap_SimpleClassInfo_Tomorrow.size();

                CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO classInfo;

                for( ; iIndex < iSize ; iIndex++ )
                {
                    classInfo = treeMap_SimpleClassInfo_Tomorrow.get(treeMap_SimpleClassInfo_Tomorrow.keySet().toArray()[iIndex]);

                    String sMsg = "";
                    //String sCLS_STR_DT_TIM = classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM;
                    String sCLS_STR_DT_TIM = Rtx_Calendar.sParseDate2FormatStr(classInfo.sUser_CLS_SUC_DT + " " + classInfo.sUser_CLS_STR_TIM,"yyyy-MM-dd HH:mm:ss","HH:mm");

                    sMsg +=
                            LanguageData.s_get_string(mContext, R.string.there_is_a) + " " +
                                    classInfo.sCLS_NAME + " " +
                                    LanguageData.s_get_string(mContext, R.string.tomorrow_at) + " " +  sCLS_STR_DT_TIM + "\n";

                    UiDataStruct.HOME_USER_INFO_ITEM_DIALOG infoItem = new UiDataStruct.HOME_USER_INFO_ITEM_DIALOG(iInfoItemTagIconResId, sMsg);
                    list_Contents.add(infoItem);

                    bResult = true;
                }
            }

            return bResult;
        }

        public boolean bExportSummaryInfo(UiDataStruct.TargetTrainInfo mTargetTrainInfo)
        {
            boolean bResult = false;
            boolean bResult_FirstLogin = false;
            boolean bResult_ClassReminder = false;
            boolean bResult_ScheduleChange = false;
            boolean bResult_TargetUpdate = false;
            boolean bResult_FirstSignUp = false;

            clear();

            bResult_FirstLogin = bExportSummaryInfo_FirstLogin();
            bResult_ClassReminder = bExportSummaryInfo_ClassReminder();
            bResult_ScheduleChange = bExportSummaryInfo_ClassChange();

            if(mTargetTrainInfo != null)
            {
                bResult_TargetUpdate = bExportSummaryInfo_TargetUpdate(mTargetTrainInfo);
            }

            bResult_FirstSignUp = bGetFirstSignUp();

            bResult = bResult_FirstLogin | bResult_ClassReminder | bResult_ScheduleChange | bResult_TargetUpdate | bResult_FirstSignUp;

            bNewInfo = bResult;
            return bResult;
        }
    }

    public static class HOME_USER_INFO_ITEM_DIALOG
    {
        public int iImgResId_Tag = -1;
        public String sContents = null;

        public HOME_USER_INFO_ITEM_DIALOG(int iImageResId, String sContents)
        {
            this.iImgResId_Tag = iImageResId;
            this.sContents = new String(sContents);
        }
    }

    public static class HOME_USER_INFO_DIALOG
    {
        public int iImgResId_Icon = -1;
        public String sTitle = null;
        public ArrayList<HOME_USER_INFO_ITEM_DIALOG>   list_InfoItem;

        public HOME_USER_INFO_DIALOG(int iImageResId, String sTitle)
        {
            this.iImgResId_Icon = iImageResId;
            this.sTitle = new String(sTitle);
            list_InfoItem = new ArrayList<HOME_USER_INFO_ITEM_DIALOG>();
        }

    }
}
