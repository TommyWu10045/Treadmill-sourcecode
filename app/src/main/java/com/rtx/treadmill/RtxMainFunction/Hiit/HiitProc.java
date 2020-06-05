package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.util.Log;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutState;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/27/17.
 */

public class HiitProc {
    private String TAG="Hiit" ;
    protected final int MODE_NORMAL=0;
    protected final int MODE_HIGH=1;
    protected final int MODE_ADVANCED=2;
    protected final int MODE_DELETE_ITEM=0;
    protected final int MODE_DELETE_INTERVAL=1;
    protected final int MODE_NONE=-1;
    protected final int MODE_CREATE=0;
    protected final int MODE_MODIFY=1;
    protected final int MODE_PREVIEW=2;
    protected final int SELECT_INDEX_WARM_UP=998;
    protected final int SELECT_INDEX_COOL_DOWN=999;
    private MainActivity mMainActivity ;
    private int iPersonalMode=-1;
    private int iDeleteMode=-1;
    private HiitState mState;
    private HiitState mNextState=HiitState.PROC_NULL;
    private HiitState tempState=HiitState.PROC_NULL;
    private HiitState mPreState=HiitState.PROC_NULL;
    private HiitLayout mHiitLayout;
    private Hiit_DefaultData_Preview_Layout mHiit_DefaultData_Preview_Layout;
    private Hiit_Personal_Main_Layout mHiit_Personal_Main_Layout;
    private Hiit_Personal_Name_Layout mHiit_Personal_Name_Layout;
    private Hiit_Personal_DataList_Layout mHiit_Personal_DataList_Layout;
    private Hiit_Personal_Setting_Layout mHiit_Personal_Setting_Layout;
    private Hiit_Personal_Preview_Layout mHiit_Personal_Preview_Layout;
    private Hiit_Exercise_Layout mHiit_Exercise_Layout;
    private int iCount=0;
    private int iLevelMode=MODE_NORMAL;
    private int iSelectItem=-1;
    private int iSettingIndex=-1;
    private ArrayList<UiDataStruct.HIIT_ITEM_INFO> list_HiitItem;
    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private String sMch_Type;
    private String sHiit_ID;
    private boolean bServerResponseFlag_GetNameList=false;
    private boolean bServerResponseFlag_GetInterval=false;
    private boolean bServerResponseFlag_AddInterval=false;
    private boolean bServerResponseFlag_DeleteInterval=false;
    private boolean bServerResponseFlag_UpdateInterval=false;
    private int iServerResponseResult_GetNameList=-1;
    private int iServerResponseResult_GetInterval=-1;
    private int iServerResponseResult_AddInterval=-1;
    private int iServerResponseResult_DeleteInterval=-1;
    private int iServerResponseResult_UpdateInterval=-1;
    private AllFinishLayout mAllFinishLayout;

    public HiitProc(MainActivity mMainActivity){
        this.mMainActivity=mMainActivity ;
        mState=HiitState.PROC_INIT ;
        //Layout
        if(mHiitLayout == null)                          { mHiitLayout = new HiitLayout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_DefaultData_Preview_Layout == null)     { mHiit_DefaultData_Preview_Layout = new Hiit_DefaultData_Preview_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Personal_Main_Layout == null)           { mHiit_Personal_Main_Layout = new Hiit_Personal_Main_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Personal_Name_Layout == null)           { mHiit_Personal_Name_Layout = new Hiit_Personal_Name_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Personal_DataList_Layout == null)       { mHiit_Personal_DataList_Layout = new Hiit_Personal_DataList_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Personal_Setting_Layout == null)        { mHiit_Personal_Setting_Layout = new Hiit_Personal_Setting_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Personal_Preview_Layout == null)        { mHiit_Personal_Preview_Layout = new Hiit_Personal_Preview_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mHiit_Exercise_Layout == null)                { mHiit_Exercise_Layout = new Hiit_Exercise_Layout(mMainActivity.mContext , mMainActivity , this); }
        if(mAllFinishLayout == null)                     { mAllFinishLayout = new AllFinishLayout(mMainActivity.mContext , mMainActivity); }
        if(list_HiitItem == null)                        { list_HiitItem = new ArrayList<UiDataStruct.HIIT_ITEM_INFO>(); }
        if(hiitItemInfo == null)                         { hiitItemInfo = new UiDataStruct.HIIT_ITEM_INFO(); }
    }

    /* ------------------------------------------------------------------------ */
    public void vSetNextState(HiitState nextState){
        mNextState=nextState;
    }

    public void vSetPreState(HiitState preState){
        mPreState=preState;
    }

    public void vBackPreState(){
        mNextState=mPreState;
        mPreState=HiitState.PROC_NULL;
    }

    public HiitState vGerPreState(){
        return mPreState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init(){
        mState=HiitState.PROC_SHOW_PAGE_MAIN;
    }

    private void vProc_Idle(){
        if(mNextState != HiitState.PROC_NULL){
            mState=mNextState;
            mNextState = HiitState.PROC_NULL;
        }
    }

    private void vProc_Exit(){
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState=HiitState.PROC_INIT ;
        mNextState=HiitState.PROC_NULL;
    }

    private void vProc_ShowPage_Main(){
        vChangeDisplayPage(mHiitLayout);
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DefaultData_Preview(){
        vChangeDisplayPage(mHiit_DefaultData_Preview_Layout);
        mMainActivity.mUI_Handler.post(new Runnable(){
            @Override
            public void run(){
                mHiit_DefaultData_Preview_Layout.vSetWorkoutItemInfo(hiitItemInfo, iGetLevelMode());
            }
        });

        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_PersonalMain(){
        vChangeDisplayPage(mHiit_Personal_Main_Layout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run(){
                vSetNextState(HiitState.PROC_CLOUD_GET_NAME_LIST);
                mHiit_Personal_Main_Layout.vSetItemInfo(hiitItemInfo);
            }
        });
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_PersonalName(){
        vChangeDisplayPage(mHiit_Personal_Name_Layout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHiit_Personal_Name_Layout.vSetItemInfo(hiitItemInfo,iGetPersonalMode());
            }
        });
        mState = HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_PersonalDataList(){
        vChangeDisplayPage(mHiit_Personal_DataList_Layout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                //dismiss Delete Finish dialog
                if(mMainActivity.dialogLayout_DeleteFinish != null){
                    if(mMainActivity.dialogLayout_DeleteFinish.isShown()){ mMainActivity.dismissInfoDialog(); }
                }
                mHiit_Personal_DataList_Layout.vSetItemInfo(hiitItemInfo,iGetPersonalMode());
            }
        });
        mState = HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_PersonalSetting(HiitState state){
        vChangeDisplayPage(mHiit_Personal_Setting_Layout);
        if(state == HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_WARM_UP){ vSetSettingIndex(SELECT_INDEX_WARM_UP); }
        else if(state == HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_COOL_DOWN){ vSetSettingIndex(SELECT_INDEX_COOL_DOWN); }
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHiit_Personal_Setting_Layout.vSetHiitItemInfo(hiitItemInfo,vGetSettingIndex());
            }
        });
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_ShowPage_PersonalPreview(){
        vChangeDisplayPage(mHiit_Personal_Preview_Layout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                if(iGetPersonalMode() == MODE_PREVIEW){ vSetSelectItemInfo(); }
                mHiit_Personal_Preview_Layout.vSetItemInfo(hiitItemInfo,iGetPersonalMode());
            }
        });
        mState=HiitState.PROC_IDLE ;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void vProc_ShowDialog_Delete(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(iCount == 0){
                    mMainActivity.dismissInfoDialog();
                    mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_DELETE_FINISH,-1,-1);
                    iCount++;
                }else if(iCount > EngSetting.DEF_SEC_COUNT){
//                    mMainActivity.dismissInfoDialog();
                    iCount=0;
                    mState=HiitState.PROC_IDLE;
                    if(iGetDeleteMode() == MODE_DELETE_ITEM){ vSetNextState(HiitState.PROC_CLOUD_DELETE_INTERVAL); }
                    else{ vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST); }
                }else{ iCount++; }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void vProc_GetNameList(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetNameList).start();
            }
        });
        mState=HiitState.PROC_CLOUD_GET_NAME_LIST_CHECK;
    }

    private void vProc_GetNameList_Check(){
        if(bServerResponseFlag_GetNameList){
            bServerResponseFlag_GetNameList=false;
            mState=HiitState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetNameList == 0){
                        //Success
                        Log.e("Jerry","vProc_GetNameList_Check Success!");
                        vParseWorkoutName();
                        mHiit_Personal_Main_Layout.vUpdateNameInfo(list_HiitItem);
                        vSetNextState(HiitState.PROC_CLOUD_GET_INTERVAL);
                    }else{
                        //Fail
                        Log.e("Jerry","vProc_GetNameList_Check Fail!");
                    }
                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_GetInterval(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetInterval).start();
                //dismiss Delete Finish dialog
                if(mMainActivity.dialogLayout_DeleteFinish != null){
                    if(mMainActivity.dialogLayout_DeleteFinish.isShown()){ mMainActivity.dismissInfoDialog(); }
                }
            }
        });
        mState=HiitState.PROC_CLOUD_GET_INTERVAL_CHECK;
    }

    private void vProc_GetInterval_Check(){
        if(bServerResponseFlag_GetInterval){
            bServerResponseFlag_GetInterval=false;
            mState = HiitState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetInterval == 0){
                        //Success
                        Log.e("Jerry","vProc_GetInterval_Check Success!");
                        mHiit_Personal_Main_Layout.vUpdatePreviewInfo(list_HiitItem);
                    }else{
                        //Fail
                        Log.e("Jerry","vProc_GetInterval_Check Fail!");
                    }
                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_AddInterval(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Add).start();
            }
        });
        mState=HiitState.PROC_CLOUD_ADD_INTERVAL_CHECK;
    }

    private void vProc_AddInterval_Check(){
        if(bServerResponseFlag_AddInterval){
            bServerResponseFlag_AddInterval=false;
            mState=HiitState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_AddInterval == 0){
                        //Success
                        Log.e("Jerry","vProc_AddInterval_Check Success!");
                        vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
                    }else{
                        //Fail
                        Log.e("Jerry","vProc_AddInterval_Check Fail!");
                    }
                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_UpdateInterval(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Update).start();
            }
        });
        mState=HiitState.PROC_CLOUD_UPDATE_INTERVAL_CHECK;
    }

    private void vProc_UpdateInterval_Check(){
        if(bServerResponseFlag_UpdateInterval){
            bServerResponseFlag_UpdateInterval=false;
            mState=HiitState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_UpdateInterval == 0){
                        //Success
                        Log.e("Jerry","vProc_UpdateInterval_Check Success!");
                        vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
                    }else{
                        //Fail
                        Log.e("Jerry","vProc_UpdateInterval_Check Fail!");
                    }
                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_DeleteInterval(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Interval_Delete).start();
            }
        });
        mState=HiitState.PROC_CLOUD_DELETE_INTERVAL_CHECK;
    }

    private void vProc_DeleteInterval_Check(){
        if(bServerResponseFlag_DeleteInterval){
            bServerResponseFlag_DeleteInterval=false;
            mState=HiitState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_DeleteInterval == 0){
                        //Success
                        Log.e("Jerry","vProc_DeleteInterval_Check Success!");
                        vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
                    }else{
                        //Fail
                        Log.e("Jerry","vProc_DeleteInterval_Check Fail!");
                    }
                    mMainActivity.closeProgressBar();
                }
            });
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void vProc_Exercise_Prepare(){
        vCreateProgram();
        vSetNextState(HiitState.PROC_EXERCISE_CHECK_COUNTDOWN);
        mState=HiitState.PROC_IDLE ;
    }


    private void vProc_Check_Is_Countdown(){
        boolean bret=ExerciseData.bGet_Is_Coundown();
        if(bret){ vSetNextState(HiitState.PROC_EXERCISE_SHOW); }
        vProc_Idle() ;
    }

    private void vProc_ShowPage_Exercise(){
//        vChangeDisplayPage(mHiit_Exercise_Layout);
//
//        mMainActivity.mUI_Handler.post(new Runnable() {
//            @Override
//            public void run() {
//                mHiit_Exercise_Layout.vSetHiitItemInfo(hiitItemInfo);
//            }
//        });
        vSetNextState(HiitState.PROC_EXERCISE_INIT);
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_Exercise_Init(){
        vChangeDisplayPage(mHiit_Exercise_Layout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mHiit_Exercise_Layout.vSetHiitItemInfo(hiitItemInfo);
            }
        });
        vSetNextState(HiitState.PROC_EXERCISE_REFRESH);
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_Exercise_Refresh(){
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mHiit_Exercise_Layout != null){ mHiit_Exercise_Layout.Refresh(); }
            }
        });
        vProc_Idle();
    }

    private void vProc_Exercise_Summary(){
        vChangeDisplayPage(mAllFinishLayout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mAllFinishLayout.vSetItemInfo(hiitItemInfo);
            }
        });
        mState=HiitState.PROC_IDLE ;
    }

    private void vProc_Exercise_Done(){
        vMainChangePage(MainState.PROC_HOME);
        mState=HiitState.PROC_IDLE;
    }

    private void vProc_Exercise_Logout(){
        CloudDataStruct.CloudData_20.set_log_in(false);
        vMainChangePage(MainState.PROC_HOME);
        mState=HiitState.PROC_IDLE;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

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

    public void vMainChangePage(MainState state){
        vSetNextState(HiitState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetLevelMode(int iMode)
    {
        iLevelMode = iMode;
    }

    protected int iGetLevelMode()
    {
        return iLevelMode;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vSetPersonalMode(int mode)
    {
        iPersonalMode = mode;
    }

    public int iGetPersonalMode()
    {
        return iPersonalMode;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetSettingIndex(int iIndex)
    {
        iSettingIndex = iIndex;
    }

    protected int vGetSettingIndex()
    {
        return iSettingIndex;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetHiitID(String sID)
    {
        sHiit_ID = new String(sID);
    }

    protected String sGetHiitID()
    {
        return sHiit_ID;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetSelectItemIndex(int iIndex)
    {
        iSelectItem = iIndex;
    }

    protected int iGetSelectItemIndex()
    {
        return iSelectItem;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetDeleteMode(int iMode)
    {
        iDeleteMode = iMode;
    }

    protected int iGetDeleteMode()
    {
        return iDeleteMode;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected String vGetTotalDurationTimeText(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo){
        int iSec=hiitItemInfo.iGetListStageSec();
        String sText=Rtx_Calendar.sSec2Str(iSec,"HH:mm:ss");
        return sText;
    }

    protected String vGetDurationTimeText(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo){
        int iSec=hiitItemInfo.iGetListStageSec();
        String sText=Rtx_Calendar.sSec2Str(iSec,"HH:mm:ss");
        return sText;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void run(){
//        if(tempState != mState){
//            Log.e("Jerry", "[Hiit] mState = " + mState);
//            tempState = mState;
//        }
        switch(mState){
            case PROC_INIT:{ vProc_Init(); break; }
            case PROC_SHOW_PAGE_MAIN:{ vProc_ShowPage_Main(); break; }
            case PROC_SHOW_PAGE_DEFAULT_DATA_PREVIEW:{ vProc_ShowPage_DefaultData_Preview(); break; }
            case PROC_SHOW_PAGE_PERSONAL_MAIN:{ vProc_ShowPage_PersonalMain(); break; }
            case PROC_SHOW_PAGE_PERSONAL_NAME:{ vProc_ShowPage_PersonalName(); break; }
            case PROC_SHOW_PAGE_PERSONAL_DATALIST:{ vProc_ShowPage_PersonalDataList(); break; }
            case PROC_SHOW_PAGE_PERSONAL_SETTING_WARM_UP:{ vProc_ShowPage_PersonalSetting(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_WARM_UP); break; }
            case PROC_SHOW_PAGE_PERSONAL_SETTING_COOL_DOWN:{ vProc_ShowPage_PersonalSetting(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_COOL_DOWN); break; }
            case PROC_SHOW_PAGE_PERSONAL_SETTING_SPRINT:{ vProc_ShowPage_PersonalSetting(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_SPRINT); break; }
            case PROC_SHOW_PAGE_PERSONAL_SETTING_RECOVERY:{ vProc_ShowPage_PersonalSetting(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_RECOVERY); break; }
            case PROC_SHOW_PAGE_PERSONAL_PREVIEW:{ vProc_ShowPage_PersonalPreview(); break; }
            case PROC_SHOW_PAGE_DIALOG_DELETE:{ vProc_ShowDialog_Delete(); break; }
            case PROC_CLOUD_GET_NAME_LIST:{ vProc_GetNameList(); break; }
            case PROC_CLOUD_GET_NAME_LIST_CHECK:{ vProc_GetNameList_Check(); break; }
            case PROC_CLOUD_GET_INTERVAL:{ vProc_GetInterval(); break; }
            case PROC_CLOUD_GET_INTERVAL_CHECK:{ vProc_GetInterval_Check(); break; }
            case PROC_CLOUD_ADD_INTERVAL:{ vProc_AddInterval(); break; }
            case PROC_CLOUD_ADD_INTERVAL_CHECK:{ vProc_AddInterval_Check(); break; }
            case PROC_CLOUD_DELETE_INTERVAL:{ vProc_DeleteInterval(); break; }
            case PROC_CLOUD_DELETE_INTERVAL_CHECK:{ vProc_DeleteInterval_Check(); break; }
            case PROC_CLOUD_UPDATE_INTERVAL:{ vProc_UpdateInterval(); break; }
            case PROC_CLOUD_UPDATE_INTERVAL_CHECK:{ vProc_UpdateInterval_Check(); break; }
            case PROC_EXERCISE_PREPARE:{ vProc_Exercise_Prepare(); break; }
            case PROC_EXERCISE_SHOW:{ vProc_ShowPage_Exercise(); break; }
            case PROC_EXERCISE_INIT:{ vProc_Exercise_Init(); break; }
            case PROC_EXERCISE_REFRESH:{ vProc_Exercise_Refresh(); break; }
            case PROC_EXERCISE_FINISH:{ vProc_Exercise_Summary(); break; }
            case PROC_EXERCISE_SHOW_DONE:{ vProc_Exercise_Done(); break; }
            case PROC_EXERCISE_SHOW_LOGOUT:{ vProc_Exercise_Logout(); break; }
            case PROC_EXERCISE_CHECK_COUNTDOWN:{ vProc_Check_Is_Countdown(); break; }
            case PROC_EXIT:{ vProc_Exit(); break; }
            case PROC_IDLE:
            default:{ vProc_Idle(); break; }
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Runnable CloudRunnable_GetNameList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetNameList=false;
            iServerResponseResult_GetNameList=mMainActivity.mCloudCmd.iCloudCmd_GetWorkoutName();
            bServerResponseFlag_GetNameList=true;
        }
    };

    private Runnable CloudRunnable_GetInterval=new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetInterval=false;
            if(hiitItemInfo != null){
                int iIndex=0;
                int iSize=list_HiitItem.size();
                for(; iIndex < iSize ; iIndex++){
                    vSetHiitID(list_HiitItem.get(iIndex).sID);
                    iServerResponseResult_GetInterval = mMainActivity.mCloudCmd.iCloudCmd_GetWorkoutData(sGetMchType(),sGetHiitID());
                    if(iServerResponseResult_GetInterval == 0){ list_HiitItem.get(iIndex).vImportCloudStageData(); }
                    try{
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            bServerResponseFlag_GetInterval=true;
        }
    };

    private Runnable CloudRunnable_Interval_Add=new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_AddInterval=false;
            {
                String sMchType=sGetMchType();
                String sWorkoutID=sGetFreeWorkoutID();
                //String sWorkoutName="#H#"+hiitItemInfo.sName;
                String sWorkoutName=hiitItemInfo.sName;
                hiitItemInfo.vExportCloudData();

                if(sMchType != null && sWorkoutID != null && sWorkoutName != null && hiitItemInfo.list_UploadStageData != null){
                    iServerResponseResult_AddInterval = mMainActivity.mCloudCmd.iCloudCmd_Workout_Add(sMchType,sWorkoutID,sWorkoutName,hiitItemInfo.list_UploadStageData);
                }else{ iServerResponseResult_AddInterval=0; }
            }
            bServerResponseFlag_AddInterval=true;
        }
    };

    private Runnable CloudRunnable_Interval_Update=new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_UpdateInterval=false;
            {
                String sMchType=hiitItemInfo.sType;
                String sWorkoutID=hiitItemInfo.sID;
                String sWorkoutName=hiitItemInfo.sName;
                hiitItemInfo.vExportCloudData();
                if(sMchType != null && sWorkoutID != null && sWorkoutName != null && hiitItemInfo.list_UploadStageData != null){
                    iServerResponseResult_UpdateInterval=mMainActivity.mCloudCmd.iCloudCmd_Workout_Update(sMchType,sWorkoutID,sWorkoutName,hiitItemInfo.list_UploadStageData);
                }else{ iServerResponseResult_UpdateInterval=0; }
            }
            bServerResponseFlag_UpdateInterval=true;
        }
    };

    private Runnable CloudRunnable_Interval_Delete=new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_DeleteInterval=false;
            {
                String sMchType=hiitItemInfo.sType;
                String sWorkoutID=hiitItemInfo.sID;
                String sWorkoutName=hiitItemInfo.sName;

                if(sMchType != null && sWorkoutID != null && sWorkoutName != null){
                    iServerResponseResult_DeleteInterval=mMainActivity.mCloudCmd.iCloudCmd_Workout_DeleteItem(sMchType,sWorkoutID,sWorkoutName);
                }else{ iServerResponseResult_DeleteInterval=0; }
            }
            bServerResponseFlag_DeleteInterval=true;
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vParseWorkoutName(){
        if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null){ return; }
        int iIndex=0;
        int iSize=CloudDataStruct.CloudData_10.list_CloudWorkoutName.size();
        list_HiitItem.clear();
        hiitItemInfo.clear();
        for(; iIndex < iSize ; iIndex++){
            if(((CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_NAM.length() > 3)) && (CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_NAM.substring(0,3).equals("#H#"))){
                UiDataStruct.HIIT_ITEM_INFO itemInfo = new UiDataStruct.HIIT_ITEM_INFO();
                itemInfo.vImportCloudNameData(iIndex);
                list_HiitItem.add(itemInfo);
            }else{
                Log.e("Jerry","Skip Workout Item");
                continue;
            }
        }
    }

    private String sGetMchType(){
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7){ sMch_Type="T"; }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6){ sMch_Type="U"; }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6){ sMch_Type="R"; }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6){ sMch_Type="E"; }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ALL){ sMch_Type="T"; }
        else{ sMch_Type="T"; }
        return sMch_Type;
    }

    private String sGetFreeWorkoutID(){
        if(CloudDataStruct.CloudData_10.list_CloudWorkoutName == null){ return null; }
        int iFreeWorkoutID=0;
        int iFreeWorkoutID_MAX=99;
        boolean bSame;
        int iItemSize=CloudDataStruct.CloudData_10.list_CloudWorkoutName.size();
        for(; iFreeWorkoutID <= iFreeWorkoutID_MAX; iFreeWorkoutID++){
            int iIndex=0;
            bSame=false;
            for(; iIndex < iItemSize ; iIndex++){
                if(CloudDataStruct.CloudData_10.list_CloudWorkoutName.get(iIndex).sWRK_ID.equals(Rtx_TranslateValue.sInt2String(iFreeWorkoutID))){
                    bSame=true;
                    break;
                }
            }
            if(bSame == false){ return Rtx_TranslateValue.sInt2String(iFreeWorkoutID); }
        }
        return null;
    }

    protected void vSetSelectItemInfo(){
        hiitItemInfo.clear();
        if(iSelectItem != -1){ list_HiitItem.get(iSelectItem).copyTo(hiitItemInfo); }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vCreateProgram(){
        float fWeight;
        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();
        fWeight=CloudDataStruct.CloudData_17.f_get_user_weight();
        ExerciseData.v_set_weight(fWeight);
        if(hiitItemInfo == null){ return; }
        if(hiitItemInfo.list_Stage == null){ return; }
        {
            if(hiitItemInfo.stage_WarmUp.iTime > 0){
                ExerciseData.DEVICE_COMMAND suart_cmd_orig=new ExerciseData.DEVICE_COMMAND();
                suart_cmd_orig.bcal=true;
                suart_cmd_orig.isec=hiitItemInfo.stage_WarmUp.iTime;
                Log.e("Jerry","hiitItemInfo.stage_WarmUp.iTime = "+hiitItemInfo.stage_WarmUp.iTime);
                suart_cmd_orig.fspeed=hiitItemInfo.stage_WarmUp.fSpeed;
                suart_cmd_orig.fincline=hiitItemInfo.stage_WarmUp.fIncline;
                ExerciseData.list_original_setting.add(suart_cmd_orig);
            }
        }
        int iIndex;
        int iSize=hiitItemInfo.list_Stage.size();
        for(iIndex=0; iIndex < iSize ; iIndex++){
            ExerciseData.DEVICE_COMMAND suart_cmd_orig=new ExerciseData.DEVICE_COMMAND();
            suart_cmd_orig.bcal=true;
            suart_cmd_orig.isec=hiitItemInfo.list_Stage.get(iIndex).iTime;
            suart_cmd_orig.fspeed=hiitItemInfo.list_Stage.get(iIndex).fSpeed;
            suart_cmd_orig.fincline=hiitItemInfo.list_Stage.get(iIndex).fIncline;
            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        {
            if(hiitItemInfo.stage_CoolDown.iTime > 0){
                ExerciseData.DEVICE_COMMAND suart_cmd_orig=new ExerciseData.DEVICE_COMMAND();
                suart_cmd_orig.bcal=true;
                suart_cmd_orig.isec=hiitItemInfo.stage_CoolDown.iTime;
                suart_cmd_orig.fspeed=hiitItemInfo.stage_CoolDown.fSpeed;
                suart_cmd_orig.fincline=hiitItemInfo.stage_CoolDown.fIncline;
                ExerciseData.list_original_setting.add(suart_cmd_orig);
            }
        }
        ExerciseGenfunc.v_Exercise_Keep();
        ExerciseGenfunc.v_set_target_limit(1000, Integer.MAX_VALUE);
        ExerciseGenfunc.v_set_target_distance(Integer.MAX_VALUE);
        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_HIIT);
        mMainActivity.mMainProcTreadmill.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final int   Hiit_Data_Normal_Time[]      = {180,30,90,30,90,30,90,30,90,30,90,30,90,30,90,30,90,120};
    public final float Hiit_Data_Normal_Incline[]   = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public final float Hiit_Data_Normal_A_Speed[]   = { 4 , 6 , 3 ,  6 , 3,  6 , 3 ,  7 , 3 ,  7 , 3 ,  7 , 3 ,  7 , 3 ,  7 , 3 , 4 };
    public final float Hiit_Data_Normal_B_Speed[]   = { 4 , 7 , 3 ,  7 , 3,  7 , 3 ,  8 , 3 ,  8 , 3 ,  8 , 3 ,  8 , 3 ,  8 , 3 , 4 };
    public final float Hiit_Data_Normal_C_Speed[]   = { 4 , 8 , 3 ,  8 , 3,  8 , 3 ,  9 , 3 ,  9 , 3 ,  9 , 3 ,  9 , 3 ,  9 , 3 , 4 };
    public final float Hiit_Data_Normal_D_Speed[]   = { 5 , 9 , 4 ,  9 , 4,  9 , 4 , 10 , 4 , 10 , 4 , 10 , 4 , 10 , 4 , 10 , 4 , 5 };
    public final float Hiit_Data_Normal_E_Speed[]   = { 5 ,10 , 4 , 10 , 4, 10 , 4 , 11 , 4 , 11 , 4 , 11 , 4 , 11 , 4 , 11 , 4 , 5 };
    public final float Hiit_Data_Normal_F_Speed[]   = { 5 ,11 , 4 , 11 , 4, 11 , 4 , 12 , 4 , 12 , 4 , 12 , 4 , 12 , 4 , 12 , 4 , 5 };
    public final int   Hiit_Data_Hard_Time[]        = {180,30,90,30,90,30,90,30,90,30,90,30,90,30,90,30,90,120};
    public final float Hiit_Data_Hard_Incline[]     = {0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0};
    public final float Hiit_Data_Hard_A_Speed[]     = { 6 , 10 , 5 , 10 , 5, 10 , 5 , 11 , 5 , 11 , 5 , 11 , 5 , 11 , 5 , 11 , 5 , 6 };
    public final float Hiit_Data_Hard_B_Speed[]     = { 6 , 11 , 5 , 11 , 5, 11 , 5 , 12 , 5 , 12 , 5 , 12 , 5 , 12 , 5 , 12 , 5 , 6 };
    public final float Hiit_Data_Hard_C_Speed[]     = { 6 , 12 , 5 , 12 , 5, 12 , 5 , 13 , 5 , 13 , 5 , 13 , 5 , 13 , 5 , 13 , 5 , 6 };
    public final float Hiit_Data_Hard_D_Speed[]     = { 7 , 13 , 6 , 13 , 6, 13 , 6 , 14 , 6 , 14 , 6 , 14 , 6 , 14 , 6 , 14 , 6 , 7 };
    public final float Hiit_Data_Hard_E_Speed[]     = { 7 , 14 , 6 , 14 , 6, 14 , 6 , 15 , 6 , 15 , 6 , 15 , 6 , 15 , 6 , 15 , 6 , 7 };
    public final float Hiit_Data_Hard_F_Speed[]     = { 7 , 16 , 6 , 16 , 6, 16 , 6 , 17 , 6 , 17 , 6 , 17 , 6 , 17 , 6 , 17 , 6 , 7 };
    public final int   Hiit_Data_Advanced_Time[]    = {180,30,90,30,90,30,90,30,90,30,90,30,90,30,90,30,90,120};
    public final float Hiit_Data_Advanced_Incline[] = {0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0};
    public final float Hiit_Data_Advanced_A_Speed[] = { 8 , 14 , 7 , 14 , 7, 14 , 7 , 15 , 7 , 15 , 7 , 15 , 7 , 15 , 7 , 15 , 7 , 8 };
    public final float Hiit_Data_Advanced_B_Speed[] = { 8 , 15 , 7 , 15 , 7, 15 , 7 , 16 , 7 , 16 , 7 , 16 , 7 , 16 , 7 , 16 , 7 , 8 };
    public final float Hiit_Data_Advanced_C_Speed[] = { 8 , 16 , 7 , 16 , 7, 16 , 7 , 17 , 7 , 17 , 7 , 17 , 7 , 17 , 7 , 17 , 7 , 8 };
    public final float Hiit_Data_Advanced_D_Speed[] = { 9 , 17 , 8 , 17 , 8, 17 , 8 , 18 , 8 , 18 , 8 , 18 , 8 , 18 , 8 , 18 , 8 , 9 };
    public final float Hiit_Data_Advanced_E_Speed[] = { 9 , 18 , 8 , 18 , 8, 18 , 8 , 19 , 8 , 19 , 8 , 19 , 8 , 19 , 8 , 19 , 8 , 9 };
    public final float Hiit_Data_Advanced_F_Speed[] = { 9 , 19 , 8 , 19 , 8, 19 , 8 , 20 , 8 , 20 , 8 , 20 , 8 , 20 , 8 , 20 , 8 , 9 };
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final float Hiit_Data_Normal_Speed[][]     = { Hiit_Data_Normal_A_Speed, Hiit_Data_Normal_B_Speed, Hiit_Data_Normal_C_Speed, Hiit_Data_Normal_D_Speed, Hiit_Data_Normal_E_Speed, Hiit_Data_Normal_F_Speed };
    public final float Hiit_Data_Hard_Speed[][]       = { Hiit_Data_Hard_A_Speed, Hiit_Data_Hard_B_Speed, Hiit_Data_Hard_C_Speed, Hiit_Data_Hard_D_Speed, Hiit_Data_Hard_E_Speed, Hiit_Data_Hard_F_Speed };
    public final float Hiit_Data_Advanced_Speed[][]   = { Hiit_Data_Advanced_A_Speed, Hiit_Data_Advanced_B_Speed, Hiit_Data_Advanced_C_Speed, Hiit_Data_Advanced_D_Speed, Hiit_Data_Advanced_E_Speed, Hiit_Data_Advanced_F_Speed };
    public final int   Hiit_Data_Time[][]             = { Hiit_Data_Normal_Time, Hiit_Data_Hard_Time, Hiit_Data_Advanced_Time };
    public final float Hiit_Data_Incline[][]          = { Hiit_Data_Normal_Incline, Hiit_Data_Hard_Incline, Hiit_Data_Advanced_Incline };
    public final float Hiit_Data_Speed[][][]          = { Hiit_Data_Normal_Speed, Hiit_Data_Hard_Speed, Hiit_Data_Advanced_Speed };
}
