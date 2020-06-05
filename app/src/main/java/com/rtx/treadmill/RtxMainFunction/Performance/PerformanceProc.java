package com.rtx.treadmill.RtxMainFunction.Performance;

import android.util.Log;
import com.retonix.circlecloud.Cloud_21_GET_EXC_REC;
import com.retonix.circlecloud.Cloud_26_GET_EW_YER;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * Created by chasechang on 3/27/17.
 */

public class PerformanceProc{
    private boolean DEBUG=true;
    private String TAG="Jerry=";
    private MainActivity mMainActivity;
    private PerformanceState mState=PerformanceState.PROC_INIT;
    private PerformanceState mNextState=PerformanceState.PROC_NULL;
    private PerformanceState tempState=PerformanceState.PROC_NULL;
    private MainDefaultLayout mMainDefaultLayout;
    private PerformanceLayout mPerformanceLayout;
    private SessionLayout mSessionLayout;
    private MonthLayout mMonthLayout;
    private YearLayout mYearLayout;
    private TypeSourceLayout mTypeSourceLayout;
    private NameLayout mNameLayout;
    private DateLayout mDateLayout;
    private TimeLayout mTimeLayout;
    private DurationLayout mDurationLayout;
    private DistanceLayout mDistanceLayout;
    private CaloriesLayout mCaloriesLayout;
    private MainDefaultLayoutE mMainDefaultLayoutE;
    private SessionLayoutE  mSessionLayoutE;
    private MonthLayoutE mMonthLayoutE;
    private YearLayoutE mYearLayoutE;
    private SimpleDateFormat mSimpleDateFormat;
    private boolean bServerResponseFlag = false;
    private int iServerResponse=-1;
    private int imode;
    private boolean bexcise=false;
    private boolean blogin=false;
    private boolean bsession_show=false;
    private int itype;
    private String sname;
    private Calendar cdate;
    private Calendar ctime;
    private float fduration;
    private float fdistance;
    private float fcalorie;
    private int isession_reload=0;//0:server all data is load;
    private int imonth_reload=0; //0:server all data is load;
    private int iyear_reload=0;
    private int iDel_Work_Seq;
    private int iGet_Year;
    private int icount;

    private ArrayList<String[]> sessionlist=CloudDataStruct.CloudData_21.clound_cmd21_list;
    private TreeMap<String, String[]> yeartree=CloudDataStruct.CloudData_26.clound_cmd26_list;
    private TreeMap<String, String[]> monthtree=CloudDataStruct.CloudData_34.clound_cmd34_list;
    private TreeMap<String, String[]> daystree=CloudDataStruct.CloudData_33.clound_cmd33_list;

    public PerformanceProc(MainActivity mMainActivity){
        this.mMainActivity = mMainActivity;
        mState=PerformanceState.PROC_INIT;
        if(mMainDefaultLayout == null)      { mMainDefaultLayout = new MainDefaultLayout(mMainActivity.mContext, mMainActivity);}
        if(mPerformanceLayout == null)      { mPerformanceLayout = new PerformanceLayout(mMainActivity.mContext, mMainActivity);}
        if(mSessionLayout == null)          { mSessionLayout = new SessionLayout(mMainActivity.mContext, mMainActivity);}
        if(mMonthLayout == null)            { mMonthLayout = new MonthLayout(mMainActivity.mContext, mMainActivity);}
        if(mYearLayout == null)             { mYearLayout = new YearLayout(mMainActivity.mContext, mMainActivity);}
        if(mTypeSourceLayout == null)       { mTypeSourceLayout = new TypeSourceLayout(mMainActivity.mContext, mMainActivity);}
        if(mNameLayout == null)             { mNameLayout = new NameLayout(mMainActivity.mContext, mMainActivity);}
        if(mDateLayout == null)             { mDateLayout = new DateLayout(mMainActivity.mContext, mMainActivity);}
        if(mTimeLayout == null)             { mTimeLayout = new TimeLayout(mMainActivity.mContext, mMainActivity);}
        if(mDurationLayout == null)         { mDurationLayout = new DurationLayout(mMainActivity.mContext, mMainActivity);}
        if(mDistanceLayout == null)         { mDistanceLayout = new DistanceLayout(mMainActivity.mContext, mMainActivity);}
        if(mCaloriesLayout == null)         { mCaloriesLayout = new CaloriesLayout(mMainActivity.mContext, mMainActivity);}
        if(mMainDefaultLayoutE == null)     { mMainDefaultLayoutE = new MainDefaultLayoutE(mMainActivity.mContext, mMainActivity);}
        if(mSessionLayoutE == null)         { mSessionLayoutE = new SessionLayoutE(mMainActivity.mContext, mMainActivity);}
        if(mMonthLayoutE == null)           { mMonthLayoutE = new MonthLayoutE(mMainActivity.mContext, mMainActivity);}
        if(mYearLayoutE == null)            { mYearLayoutE = new YearLayoutE(mMainActivity.mContext, mMainActivity);}
    }

    /* ------------------------------------------------------------------------ */
    public void vSetNextState(PerformanceState nextState){
        mNextState=nextState;
    }

    public void vSet_mode(int imode){
        this.imode=imode;
    }

    public int i_Get_mode(){
        return imode;
    }

    public void vSet_type(int itype){
        this.itype=itype;
    }

    public int i_Get_type(){
        return itype;
    }

    public void vSet_name(String sname){
        this.sname=sname;
    }

    public void vSet_date(Calendar sdate){
        this.cdate=sdate;
    }

    public void vSet_time(Calendar fval){
        this.ctime=fval;
    }

    public void vSet_duration(float fval){
        this.fduration=fval;
    }

    public void vSet_distance(float fval){
        this.fdistance=fval;
    }

    public void vSet_calories(float fval){
        this.fcalorie=fval;
    }

    public void vSet_Del_WorkSeq(int ival){
        this.iDel_Work_Seq=ival;
    }

    public void vSet_Get_Exercise_Year(int ival){
        this.iGet_Year=ival;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init(){
        sname="";
        bexcise=ExerciseData.b_is_exercising();
        if(mNextState == PerformanceState.PROC_NULL){
            if(bexcise){ mState = PerformanceState.PROC_INIT_PAGEE; }
            else{ mState=PerformanceState.PROC_INIT_PAGE; }
        }else{ mState=mNextState; }
        blogin=CloudDataStruct.CloudData_20.is_log_in();
        if(blogin){
            iGet_Year=Rtx_Calendar.i_get_datetime(2, 0, null, null);
            v_reload_All_data(0x07, true);
        }
        else{ v_reload_Clear_data(0x07, true); }
    }

    private void vProc_ShowPage_Default(){
        vChangeDisplayPage(mMainDefaultLayout);
        mState = PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_performance(){
        bsession_show=false;
        vChangeDisplayPage(mPerformanceLayout);
        if(isession_reload == 2){ mState = PerformanceState.PROC_CLOUD_PF_GET_SESSION; }
        else if(iyear_reload == 2){ mState = PerformanceState.PROC_CLOUD_PF_GET_YEAR; }
        else{ mState = PerformanceState.PROC_IDLE; }
        v_show_list();
    }

    private void vProc_ShowPage_session(){
        bsession_show = true;
        vChangeDisplayPage(mSessionLayout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                //dismiss DIALOG_TYPE_DELETE_FINISH
                mMainActivity.dismissInfoDialog();
            }
        });
        mState=PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_typesource(){
        vChangeDisplayPage(mTypeSourceLayout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                if(mNameLayout != null){ mNameLayout.setName(""); }
            }
        });
        mState=PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_name(){
        vChangeDisplayPage(mNameLayout);
        mState=PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_date(){
        vChangeDisplayPage(mDateLayout);
        mState=PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_time(){
        vChangeDisplayPage(mTimeLayout);
        mState=PerformanceState.PROC_IDLE;
    }

    private void vProc_ShowPage_duration(){
        vChangeDisplayPage(mDurationLayout);
        mState=PerformanceState.PROC_IDLE;
    }

    private void vProc_ShowPage_distance(){
        vChangeDisplayPage(mDistanceLayout);
        mState=PerformanceState.PROC_SHOW_DISTANCE_REFRESH;
    }

    private void vProc_ShowPage_distance_refresh(){
        if(mDistanceLayout != null){
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mDistanceLayout.v_Slide_bar_refresh();
                }
            });
        }
        vProc_Idle();
    }

    private void vProc_ShowPage_calories(){
        vChangeDisplayPage(mCaloriesLayout);
        mState=PerformanceState.PROC_IDLE;
    }

    private void vProc_ShowPage_month(){
        vChangeDisplayPage(mMonthLayout);
        mState=PerformanceState.PROC_IDLE;
    }

    private void vProc_ShowPage_year(){
        vChangeDisplayPage(mYearLayout);
        mState=PerformanceState.PROC_IDLE;
    }

    /// Cloud Data ///
    private void vProc_Cloud_Get_Session(){
        if(isession_reload != 0){
            if(isession_reload == 2){
                sessionlist.clear();
                isession_reload = 1;
            }
            bServerResponseFlag=false;
            if(DEBUG) Log.e(TAG, "CloudRunnable_Get_Session ");
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();
                    new Thread(CloudRunnable_Get_Session).start();
                }
            });
            mState=PerformanceState.PROC_CLOUD_PF_GET_SESSION_CHECK;
        }else{ mState=PerformanceState.PROC_IDLE; }
    }

    private void vProc_Cloud_Get_Session_Check(){
        if(bServerResponseFlag){
            if(iyear_reload == 2){
                mState = PerformanceState.PROC_CLOUD_PF_GET_YEAR;
            }else{
                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mMainActivity.closeProgressBar();
                    }
                });
                mState=PerformanceState.PROC_IDLE;
            }
        }
    }

    private void vProc_Cloud_Get_Year(){
        if(iyear_reload != 0){
            if(iyear_reload == 2){
                iyear_reload = 0;
                yeartree.clear();
                monthtree.clear();
                daystree.clear();
            }
            bServerResponseFlag=false;
            if(DEBUG) Log.e(TAG, "vProc_Cloud_Get_Year ");
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();
                    new Thread(CloudRunnable_Get_Year).start();
                }
            });
            mState=PerformanceState.PROC_CLOUD_PF_GET_YEAR_CHECK;
        }else{ mState=PerformanceState.PROC_IDLE; }
    }

    private void vProc_Cloud_Get_Year_Check(){
        if(bServerResponseFlag){
            if(iServerResponse == 0){
                iGet_Year = Rtx_Calendar.i_get_datetime(2, 0, null, null);
                v_clear_over_year(iGet_Year, yeartree);
                mState = PerformanceState.PROC_CLOUD_PF_GET_MONTH;
            }else{
                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mMainActivity.closeProgressBar();
                    }
                });
                mState=PerformanceState.PROC_IDLE;
            }
        }
    }

    private void vProc_Cloud_Get_Month(){
        if(imonth_reload != 0) {
            bServerResponseFlag=false;
            if(DEBUG) Log.e(TAG, "vProc_Cloud_Get_Month ");
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();
                    new Thread(CloudRunnable_Get_Month).start();
                }
            });
            mState=PerformanceState.PROC_CLOUD_PF_GET_MONTH_CHECK;
        }else{ mState=PerformanceState.PROC_IDLE; }
    }

    private void vProc_Cloud_Get_Month_Check(){
        if(bServerResponseFlag){
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });

            if(bexcise){
                if(!bsession_show){ mState=PerformanceState.PROC_IDLE; }
                else{ mState = PerformanceState.PROC_SHOW_SESSIONE; }
            }else{
                if(!bsession_show){ mState=PerformanceState.PROC_IDLE; }
                else{ mState=PerformanceState.PROC_SHOW_SESSION; }
            }
        }
    }

    private void vProc_Cloud_Set_Session(){
        bServerResponseFlag = false;
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Add_Session).start();
            }
        });
        mState = PerformanceState.PROC_CLOUD_PF_ADD_SESSION_CHECK;
    }

    private void vProc_Cloud_Set_Session_Check(){
        if(bServerResponseFlag){
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            if(iServerResponse == 0){
                v_update_add_session();
                mState = PerformanceState.PROC_CLOUD_PF_GET_SESSION;
            }else{ mState=PerformanceState.PROC_IDLE; }
        }
    }

    private void vProc_Cloud_Del_Session(){
        bServerResponseFlag=false;
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Del_Session).start();
            }
        });
        mState=PerformanceState.PROC_CLOUD_PF_DEL_SESSION_CHECK;
    }

    private void vProc_Cloud_Del_Session_Check(){
        if(bServerResponseFlag){
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            icount=0;
            if (iServerResponse == 0){
                v_update_del_session(iDel_Work_Seq);
                mState=PerformanceState.PROC_SHOW_DIALOG_DELETE;
            }else{
                if(bexcise){ mState=PerformanceState.PROC_SHOW_SESSIONE; }
                else{ mState=PerformanceState.PROC_SHOW_SESSION; }
            }
        }
    }

    private void vProc_ShowDialog_Delete(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(icount == 0){
                    mMainActivity.dismissInfoDialog();
                    mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_DELETE_FINISH,-1,-1);
                    icount++;
                }else if(icount > EngSetting.DEF_SEC_COUNT){
                    icount=0;
                    //每刪除一筆資料就會重新get資料
                    if(bexcise){
//                        mState=PerformanceState.PROC_SHOW_SESSIONE;
                        mState=PerformanceState.PROC_CLOUD_PF_GET_SESSION;
                    }else{
//                        mState=PerformanceState.PROC_SHOW_SESSION;
                        mState=PerformanceState.PROC_CLOUD_PF_GET_SESSION;
                    }
                }else{ icount++; }
            }
        });
    }

    /// Exercise page ///
    private void vProc_ShowPage_DefaultE(){
        bsession_show = true;
        vChangeDisplayPage(mMainDefaultLayoutE);
        mState = PerformanceState.PROC_IDLE;
    }

    private void vProc_ShowPage_sessionE(){
        bsession_show = true;
        vChangeDisplayPage(mSessionLayoutE);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                //dismiss DIALOG_TYPE_DELETE_FINISH
                mMainActivity.dismissInfoDialog();
            }
        });
        mState = PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_monthE(){
        bsession_show = false;
        vChangeDisplayPage(mMonthLayoutE);
        mState = PerformanceState.PROC_IDLE ;
    }

    private void vProc_ShowPage_yearE(){
        bsession_show = false;
        vChangeDisplayPage(mYearLayoutE);
        mState = PerformanceState.PROC_IDLE ;
    }

    private void vProc_Idle(){
        if(mNextState != PerformanceState.PROC_NULL){
            mState = mNextState;
            mNextState = PerformanceState.PROC_NULL;
        }
    }

    private void vProc_Exit(){
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = PerformanceState.PROC_INIT ;
        mNextState = PerformanceState.PROC_NULL;
        sessionlist.clear();
        yeartree.clear();
        monthtree.clear();
        daystree.clear();

    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vChangeDisplayPage(final Rtx_BaseLayoutE layout){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state){
        mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    public void v_reload_All_data(int imode, boolean bclear){
        if((imode & 0x01) == 0x01){ isession_reload = 2; }
        if((imode & 0x02) == 0x02){ imonth_reload = 2; }
        if((imode & 0x04) == 0x04){ iyear_reload = 2; }
        if(bclear){
            sessionlist.clear();
            yeartree.clear();
            monthtree.clear();
            daystree.clear();
        }
    }

    public void v_reload_Clear_data(int imode, boolean bclear){
        if((imode & 0x01) == 0x01){ isession_reload = 0; }
        if((imode & 0x02) == 0x02){ imonth_reload = 0; }
        if((imode & 0x04) == 0x04){ iyear_reload = 0; }
        if(bclear){
            sessionlist.clear();
            yeartree.clear();
            monthtree.clear();
            daystree.clear();
        }
    }

    private void v_show_list(){
        int iLoop, iLoop1;
        String[] slist;

//        if(sessionlist != null){
//            for(iLoop=0; iLoop < sessionlist.size(); iLoop++){
//                slist=sessionlist.get(iLoop);
//                if(slist != null){
//                    for(iLoop1=0; iLoop1 < slist.length; iLoop1++){ Log.e(TAG, "sessionlist[" + iLoop + "][" + iLoop1 + "]=" + slist[iLoop1]); }
//                }
//            }
//        }
//
//        if(monthlist != null){
//            for (iLoop = 0; iLoop < monthlist.size(); iLoop++){
//                slist=monthlist.get(iLoop);
//                if(slist != null){
//                    for(iLoop1 = 0; iLoop1 < slist.length; iLoop1++){
//                        Log.e(TAG, "monthlist[" + iLoop + "][" + iLoop1 + "]=" + slist[iLoop1]);
//                    }
//                }
//            }
//        }
//
//        if(dayofmonthlist != null)
//        {
//            for (Map.Entry<String, String[]> entry : dayofmonthlist.entrySet()) {
//                String skey = entry.getKey();
//                slist = entry.getValue();
//                if (slist != null) {
//                    for (iLoop1 = 0; iLoop1 < slist.length; iLoop1++) {
//                        Log.e(TAG, "skey=" + skey + "[" + iLoop1 + "]=" + slist[iLoop1]);
//                    }
//                }
//            }
//        }

//        if(yearlist != null)
//        {
//            for (iLoop = 0; iLoop < yearlist.size(); iLoop++)
//            {
//                slist = yearlist.get(iLoop);
//                if(slist != null) {
//                    for (iLoop1 = 0; iLoop1 < slist.length; iLoop1++) {
//                        Log.e(TAG, "yearlist[" + iLoop + "][" + iLoop1 + "]=" + slist[iLoop1]);
//                    }
//                }
//            }
//        }


    }
    /* ------------------------------------------------------------------------ */
    public void run(){
        if(tempState != mState){
            //Log.e(TAG, "[performanceProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState ){
            case PROC_INIT:{ vProc_Init(); break; }
            case PROC_INIT_PAGE:{ vProc_ShowPage_Default(); break; }
            case PROC_SHOW_PERFORMANCE:{ vProc_ShowPage_performance(); break; }
            case PROC_SHOW_SESSION:{ vProc_ShowPage_session(); break; }
            case PROC_ADD_TYPE_SOURCE:{ vProc_ShowPage_typesource(); break; }
            case PROC_ADD_NAME:{ vProc_ShowPage_name(); break; }
            case PROC_ADD_DATE:{ vProc_ShowPage_date(); break; }
            case PROC_ADD_TIME:{ vProc_ShowPage_time(); break; }
            case PROC_ADD_DURATION:{ vProc_ShowPage_duration(); break; }
            case PROC_ADD_DISTANCE:{ vProc_ShowPage_distance(); break; }
            case PROC_ADD_CALORIES:{ vProc_ShowPage_calories(); break; }
            case PROC_SHOW_DISTANCE_REFRESH:{ vProc_ShowPage_distance_refresh(); break; }
            case PROC_SHOW_MONTH:{ vProc_ShowPage_month(); break; }
            case PROC_SHOW_YEAR:{ vProc_ShowPage_year(); break; }
            case PROC_INIT_PAGEE:{ vProc_ShowPage_DefaultE(); break; }
            case PROC_SHOW_SESSIONE:{ vProc_ShowPage_sessionE(); break; }
            case PROC_SHOW_MONTHE:{ vProc_ShowPage_monthE(); break; }
            case PROC_SHOW_YEARE:{ vProc_ShowPage_yearE(); break; }
            case PROC_CLOUD_PF_GET_SESSION:{ vProc_Cloud_Get_Session(); break; }
            case PROC_CLOUD_PF_GET_SESSION_CHECK:{ vProc_Cloud_Get_Session_Check(); break; }
            case PROC_CLOUD_PF_GET_MONTH:{ vProc_Cloud_Get_Month(); break; }
            case PROC_CLOUD_PF_GET_MONTH_CHECK:{ vProc_Cloud_Get_Month_Check(); break; }
            case PROC_CLOUD_PF_GET_YEAR:{ vProc_Cloud_Get_Year(); break; }
            case PROC_CLOUD_PF_GET_YEAR_CHECK:{ vProc_Cloud_Get_Year_Check(); break; }
            case PROC_CLOUD_PF_DEL_SESSION:{ vProc_Cloud_Del_Session(); break; }
            case PROC_CLOUD_PF_DEL_SESSION_CHECK:{ vProc_Cloud_Del_Session_Check(); break; }
            case PROC_CLOUD_PF_ADD_SESSION:{ vProc_Cloud_Set_Session(); break; }
            case PROC_CLOUD_PF_ADD_SESSION_CHECK:{ vProc_Cloud_Set_Session_Check(); break; }
            case PROC_SHOW_DIALOG_DELETE:{ vProc_ShowDialog_Delete(); break; }
            case PROC_IDLE:{ vProc_Idle(); break; }
            case PROC_EXIT:{ vProc_Exit(); break; }
            default:{ vProc_Idle(); break; }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean b_create_update_data(){
        boolean bResult = false;

        if(fdistance <= 0){ return bResult; }
        if(!CloudDataStruct.CloudData_20.is_log_in()){ return false; }

        String sdate;
        String stime;
        int ipace=(int)(fduration*60/fdistance);

        String smch_type;
        String s_date;
        String s_dur_time=Rtx_Calendar.s_trans_integer(0, (int)fduration*60);
        String s_duration=Rtx_TranslateValue.sFloat2String(fdistance, 2);
        String s_calories=Rtx_TranslateValue.sFloat2String(fcalorie, 1);
        String s_mode;
        String s_mode_name=sname;
        String s_wrk_name=sname;
        //String s_avg_hr = Rtx_TranslateValue.sFloat2String(70f, 1);
        String s_avg_hr="";
        String s_avg_pace=Rtx_Calendar.s_trans_integer(0, ipace);

        //How to define Watt at Treadmill?
        //String s_avg_watt = Rtx_TranslateValue.sFloat2String(fcalorie, 1);
        String s_avg_watt="";

        cdate.add(Calendar.HOUR_OF_DAY, ctime.get(Calendar.HOUR_OF_DAY));
        cdate.add(Calendar.MINUTE, ctime.get(Calendar.MINUTE));
        mSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"+"+00:00"));
        sdate=mSimpleDateFormat.format(cdate.getTime());
        mSimpleDateFormat=new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"+"+00:00"));
        stime=mSimpleDateFormat.format(cdate.getTime());
        s_date=sdate+" "+stime;

        switch (itype){
            case 0:
//                smch_type = "Treadmill";
                smch_type = "Manual Name";
                s_mode = "R";
                break;
            case 1:
//                smch_type = "Upright Bike";
                smch_type = "Manual Name";
                s_mode = "B";
                break;
            case 2:
//                smch_type = "Elliptical";
                smch_type = "Manual Name";
                s_mode = "E";
                break;
            case 3:
                smch_type = "Manual Name";
                s_mode = "O";
                break;
            default:
                return bResult;
        }
        bResult = CloudDataStruct.CloudData_11.Set_input(smch_type, s_dur_time, s_duration, s_mode, s_mode_name , s_avg_hr, s_avg_pace, s_calories, s_avg_watt, s_wrk_name, s_date);
        return bResult;
    }

    private void v_update_add_session()
    {
        v_reload_All_data(0x07, true);
    }

    private void v_update_del_session(int index){
        CloudDataStruct.CloudData_21.v_Del_Session(index);
        v_reload_All_data(0x07, false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_Get_Session=new Runnable(){
        @Override
        public void run(){
            iServerResponse=-1;
            String sdate;
            String scountt="10" ;
            if(sessionlist.size() <= 0){ sdate=Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd HH:mm:ss", null, null, 1, 0); }
            else{ sdate=sessionlist.get(sessionlist.size() - 1)[Cloud_21_GET_EXC_REC.Output.CRE_DT]; }
            if(DEBUG) Log.e(TAG, "CloudRunnable_Get_Session sdate=" + sdate + "     scountt=" + scountt );
            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetExercise_Session(sessionlist, sdate, scountt);
            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_Del_Session=new Runnable(){
        @Override
        public void run(){
            iServerResponse=-1;
            String sDel_Work_Seq ;
            if(sessionlist.size() > iDel_Work_Seq){
                sDel_Work_Seq = sessionlist.get(iDel_Work_Seq)[Cloud_21_GET_EXC_REC.Output.END_WKO_SEQ];
                if(sDel_Work_Seq != null){ iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_DelExercise_Session(sDel_Work_Seq); }
            }
            bServerResponseFlag=true;
        }
    };


    public Runnable CloudRunnable_Get_Month = new Runnable() {
        @Override
        public void run()
        {
            v_get_month(iGet_Year);
        }
    };

    public Runnable CloudRunnable_Get_Year = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse=-1;
            iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_GetExercise_Year(yeartree);
            bServerResponseFlag=true;
        }
    };

    public Runnable CloudRunnable_Add_Session = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse=-1;
            if(b_create_update_data()){ iServerResponse=mMainActivity.mCloudCmd.iCloudCmd_SendExercise_Data(); }
            bServerResponseFlag=true;
        }
    };

    ////////////////////////////////////////////////////////////////////
    private void v_get_month(int iYEAR){
        int iLoop, iyear;
        String[] scurr_data = null;
        int istart=0;
        int iend=0;
        int iyear_range=3;
        int iyear_count=0;

        if(yeartree != null && yeartree.size() > 0)
        {
            for(iLoop = 0; iLoop < yeartree.size(); iLoop++)
            {
                Object skey = yeartree.keySet().toArray(new Object[yeartree.size()])[iLoop];
                scurr_data = yeartree.get(skey);
                if(scurr_data != null) {
                    iyear = Rtx_TranslateValue.iString2Int(scurr_data[Cloud_26_GET_EW_YER.Output.YM]);
                    if(iyear <= iYEAR){ iyear_count++; }
                    if(iyear_count == 1){ iend=iyear; }
                    istart=iyear;
                    if(iyear_count >= iyear_range){ break; }
                }
            }
        }
        v_get_month_duration(istart, iend);
    }

    private void v_get_month_duration(int istart, int iend){
        iServerResponse = -1;

        String sort = "";
        String sdate;
        String edate;
        int icurr_month;
        int icurr_year;

        if(imonth_reload == 0 || monthtree == null || daystree == null){
            bServerResponseFlag=true;
            return;
        }

        if(istart == 0 || istart > iend){ bServerResponseFlag = true; return; }

        icurr_year = Rtx_Calendar.i_get_datetime(2, 0, null, null);
        icurr_month = Rtx_Calendar.i_get_datetime(2, 1, null, null);

        sdate = Rtx_TranslateValue.sInt2String(istart, 4) + "-01";
        if(iend == icurr_year){ edate=Rtx_TranslateValue.sInt2String(iend, 4) + "-" + Rtx_TranslateValue.sInt2String(icurr_month, 2); }
        else{ edate=Rtx_TranslateValue.sInt2String(iend, 4) + "-12"; }

        //Log.e(TAG, "CloudRunnable_Get_Month sdate=" + sdate + "     edate=" + edate);

        iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetExercise_Month(monthtree, sort, sdate, edate);

        if(iServerResponse == 11 || iServerResponse == 0){
            mMainActivity.mCloudCmd.iCloudCmd_GetExercise_DayofMonth(daystree, sdate, edate);
            if(iServerResponse == 11){ imonth_reload=0; }
            else if(iServerResponse == 0){ imonth_reload = 1; }
        }
        bServerResponseFlag = true;
    }

    private void v_clear_over_year(int icurr_year, TreeMap<String, String[]> stree){
        int iLoop, iyear;
        String[] scurr_data=null;

        if(stree != null && stree.size() > 0){
            for(iLoop=stree.size() - 1; iLoop >= 0; iLoop--){
                Object skey=stree.keySet().toArray(new Object[stree.size()])[iLoop];
                scurr_data=stree.get(skey);
                if(scurr_data != null){
                    iyear=Rtx_TranslateValue.iString2Int(scurr_data[Cloud_26_GET_EW_YER.Output.YM]);
                    if(iyear > icurr_year){ stree.remove(skey.toString()); }
                }
            }
        }
    }
}
