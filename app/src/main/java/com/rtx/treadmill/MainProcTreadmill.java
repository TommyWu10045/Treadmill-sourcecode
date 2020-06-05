package com.rtx.treadmill;

import android.content.Intent;
import android.util.Log;

import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.Cloud.CloudInfoProc;
import com.rtx.treadmill.Dialog.DialogProc;
import com.rtx.treadmill.Engmode.EngModeActivity;
import com.rtx.treadmill.Fan.FanProc;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.IdleTimeout.IdleTimeoutProc;
import com.rtx.treadmill.RtxApkUpdate.ApkUpdateProc;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.BodyManager.BodyManagerProc;
import com.rtx.treadmill.RtxMainFunction.BodyManager.BodyManagerState;
import com.rtx.treadmill.RtxMainFunction.Exercise.Bottominfo.BottomInfoProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.ChoiceItem.ChoiceItemProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.Info.InfoProc;
import com.rtx.treadmill.RtxMainFunction.HeartRate.HeartRateProc;
import com.rtx.treadmill.RtxMainFunction.HeartRate.HeartRateState;
import com.rtx.treadmill.RtxMainFunction.Hiit.HiitProc;
import com.rtx.treadmill.RtxMainFunction.Hiit.HiitState;
import com.rtx.treadmill.RtxMainFunction.Home.HomeProc;
import com.rtx.treadmill.RtxMainFunction.Home.HomeState;
import com.rtx.treadmill.RtxMainFunction.Home.HomeLayout;//By Alan
import com.rtx.treadmill.RtxMainFunction.Login.LoginProc;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxMainFunction.MyGym.MyGymProc;
import com.rtx.treadmill.RtxMainFunction.MyGym.MyGymState;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutProc;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutState;
import com.rtx.treadmill.RtxMainFunction.Performance.PerformanceProc;
import com.rtx.treadmill.RtxMainFunction.Performance.PerformanceState;
import com.rtx.treadmill.RtxMainFunction.Physical.PhysicalProc;
import com.rtx.treadmill.RtxMainFunction.Physical.PhysicalState;
import com.rtx.treadmill.RtxMainFunction.QuickStart.QuickStartProc;
import com.rtx.treadmill.RtxMainFunction.QuickStart.QuickStartState;
import com.rtx.treadmill.RtxMainFunction.Setting.SettingProc;
import com.rtx.treadmill.RtxMainFunction.Setting.SettingState;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainProc;
import com.rtx.treadmill.RtxMainFunction.TargetTrain.TargetTrainState;
import com.rtx.treadmill.RtxMainFunction.Training.TrainingProc;
import com.rtx.treadmill.RtxMainFunction.Training.TrainingState;
import com.rtx.treadmill.RtxTools.WriteLogUtil;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.UartProc;
import com.utils.MyLog;

import static com.rtx.treadmill.GlobalData.EngSetting.DEF_UNIT_TIME;
import static com.rtx.treadmill.MainState.PROC_HOME;
import static com.rtx.treadmill.MainState.PROC_IDLE;
import static com.rtx.treadmill.MainState.PROC_INIT;
import static com.rtx.treadmill.MainState.PROC_NULL;


/**
 * Created by chasechang on 3/27/17.
 */

public class MainProcTreadmill extends Thread {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    //private static final int DEF_UNIT_TIME = 41 ;
//    private static final int DEF_UNIT_TIME = 50 ;
//    private static final int DEF_SEC_COUNT = 1000 / DEF_UNIT_TIME;

    private long        lStartTimeMillis    = 0 ;
    private long        lNowTimeMillis      = 0 ;
    private boolean     bRun                = false ;
    private int         iCount              = 0;
    private boolean     Physical_Start_Bt   = true; // By Alan
    private MainState   mState              = PROC_INIT ;
    private MainState   mNextState          = PROC_NULL ;
    private MainState   tempState           = PROC_NULL ;
    private MainState   mBeforeLoginState   = PROC_NULL ;

    public HomeProc         homeProc;
    public LoginProc        loginProc;
    public TargetTrainProc  targetTrainProc;
    public BodyManagerProc  bodymanagerProc;
    public HeartRateProc    heartrateProc;
    public SettingProc      settingProc;
    public TrainingProc     trainingProc;
    public PhysicalProc     physicalProc;
    public ExerciseProc     exerciseProc;
    public MyWorkoutProc    myWorkoutProc;
    public MyGymProc        myGymProc;
    public HiitProc         hiitProc;
    public QuickStartProc         quickStartProc;
    public PerformanceProc performanceProc;

    public UartProc         uartProc;
    public InfoProc         infoProc;
    public BottomInfoProc   bottominfoProc;
    public ChoiceItemProc   choiceItemProc;
    public CloudInfoProc    cloudInfoProc;
    public ApkUpdateProc    apkUpdateProc;
    public FanProc          fanProc;
    public DialogProc       dialogProc;
    public IdleTimeoutProc  idleTimeoutProc;
    public HomeLayout       homeLayout;//By Alan

    public MainProcTreadmill(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;

        lStartTimeMillis = lNowTimeMillis = System.currentTimeMillis();
        bRun             = false ;
        mState           = PROC_INIT ;
        iCount           = 0 ;

    }

    public void exit() {
        mState = PROC_INIT;
        mNextState = PROC_NULL;

        bRun = false ;

    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        mState = PROC_HOME ;

        if(homeProc == null)        { homeProc = new HomeProc(mMainActivity); }
        if(loginProc == null)       { loginProc = new LoginProc(mMainActivity); }
        if(targetTrainProc == null) { targetTrainProc = new TargetTrainProc(mMainActivity); }
        if(myWorkoutProc == null)   { myWorkoutProc = new MyWorkoutProc(mMainActivity); }
        if(myGymProc == null)       { myGymProc = new MyGymProc(mMainActivity); }
        if(hiitProc == null)        { hiitProc = new HiitProc(mMainActivity); }
        if(cloudInfoProc == null)   { cloudInfoProc = new CloudInfoProc(mMainActivity);}
        if(apkUpdateProc == null)   { apkUpdateProc = new ApkUpdateProc(mMainActivity);}
        if(fanProc == null)         { fanProc = new FanProc(mMainActivity);}
        if(dialogProc == null)      { dialogProc = new DialogProc(mMainActivity);}
        if(idleTimeoutProc == null) { idleTimeoutProc = new IdleTimeoutProc(mMainActivity);}
        if(homeLayout == null)      { homeLayout = new HomeLayout(mMainActivity.mContext,mMainActivity);}//By Alan

        if(bottominfoProc == null)
        {
            bottominfoProc = new BottomInfoProc(mMainActivity);
        }

        mMainActivity.cWeatherTask();
        try{
            mMainActivity.cUartDeviceTask();
        }
        catch (Exception e)
        {

        }

        vSet_Machine_Uart_Data();
        vSet_Cloud_Cmd();

        iCount = 0 ;
    }

    private void vSet_Cloud_Cmd()
    {
        CloudCmd_Info.vCloudCmd_Add(cloudglobal.iGET_DEV_BSC01, 3);

        CloudCmd_Info.vCloudCmd_Add(cloudglobal.iDB_DEV_BSC03, 1);
    }

    private void vSet_Machine_Uart_Data()
    {

        UartData.vUartCmd_10(50);
        UartData.set_uart_machine_parameter(EngSetting.s_F18_parameter());

        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
            UartData.vUartCmd_05(50, "0");
        }
        else
        {
            UartData.vUartCmd_05(50, "1");
        }
        UartData.set_uart_limit_treadmill(Float.valueOf(EngSetting.Distance.getValString(EngSetting.f_Get_Min_Speed(), 1)), Float.valueOf(EngSetting.Distance.getValString(EngSetting.f_Get_Max_Speed(), 1)), EngSetting.f_Get_Min_Incline(), EngSetting.f_Get_Max_Incline());

    }

    private void vProc_Home() {

        if(homeProc == null)
        {
            homeProc = new HomeProc(mMainActivity);
        }

        homeProc.run();
        iCount = 0;
    }

    private void vProc_Login() {

        MyLog.d("vProc_Login()");
        if(loginProc == null)
        {  MyLog.d("new LoginProc()");
            loginProc = new LoginProc(mMainActivity);
        }

        loginProc.run();
        iCount = 0;
    }

    private void vProc_TargetTrain() {

        if(targetTrainProc == null)
        {
            targetTrainProc = new TargetTrainProc(mMainActivity);
        }

        targetTrainProc.run();
        iCount = 0;
    }

    private void vProc_MyGym() {

        if(myGymProc == null)
        {
            myGymProc = new MyGymProc(mMainActivity);
        }

        myGymProc.run();
        iCount = 0;
    }

    private void vProc_MyWorkout() {

        if(myWorkoutProc == null)
        {
            myWorkoutProc = new MyWorkoutProc(mMainActivity);
        }

        myWorkoutProc.run();
        iCount = 0;
    }

    private void vProc_Hiit() {

        if(hiitProc == null)
        {
            hiitProc = new HiitProc(mMainActivity);
        }

        hiitProc.run();
        iCount = 0;
    }

    private void vProc_BodyManager() {

        if(bodymanagerProc == null)
        {
            bodymanagerProc = new BodyManagerProc(mMainActivity);
        }

        bodymanagerProc.run();
        iCount = 0;
    }

    private void vProc_HeartRate() {

        if(heartrateProc == null)
        {
            heartrateProc = new HeartRateProc(mMainActivity);
        }

        heartrateProc.run();
        iCount = 0;
    }

    private void vProc_Setting() {

        if(settingProc == null)
        {
            settingProc = new SettingProc(mMainActivity);
        }

        settingProc.run();
        iCount = 0;
    }

    private void vProc_Training() {

        if(trainingProc == null)
        {
            trainingProc = new TrainingProc(mMainActivity);
        }

        trainingProc.run();
        iCount = 0;
    }

    private void vProc_Physical() {

        if(physicalProc == null)
        {
            physicalProc = new PhysicalProc(mMainActivity);
        }

        physicalProc.run();
        iCount = 0;
    }

    private void vProc_QuickStart() {

        if(quickStartProc == null)
        {
            quickStartProc = new QuickStartProc(mMainActivity);
        }

        quickStartProc.run();
        iCount = 0;
    }

    private void vProc_Performance() {

        if(performanceProc == null)
        {
            performanceProc = new PerformanceProc(mMainActivity);
        }

        performanceProc.run();
        iCount = 0;
    }

    private void vProc_Uart_Handshake() {

        if(uartProc == null)
        {
            uartProc = new UartProc(mMainActivity);
        }

        uartProc.run();
    }

    private void vProc_Dialog() {
        dialogProc.run();
    }

    private void vProc_Exercise() {

        if(exerciseProc == null)
        {
            exerciseProc = new ExerciseProc(mMainActivity);
        }

        exerciseProc.run();
    }

    private void vProc_Info() {

        if(infoProc == null)
        {
            infoProc = new InfoProc(mMainActivity);
        }

        infoProc.run();
    }

    private void vProc_BottomInfo() {

        if(bottominfoProc == null)
        {
            bottominfoProc = new BottomInfoProc(mMainActivity);
        }

        bottominfoProc.run();
    }

    private void vProc_ChoiceItem() {

        if(choiceItemProc == null)
        {
            choiceItemProc = new ChoiceItemProc(mMainActivity);
        }

        choiceItemProc.run();
    }

    private void vProc_CloudCmd() {

        cloudInfoProc.run();
    }

    private void vProc_RtxUpdate(){

        apkUpdateProc.run();
    }

    private void vProc_Fan(){

        fanProc.run();
    }

    private void vProc_Engmode() {
        WriteLogUtil.vSave_LogCat_Stop();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "mMainProcTreadmill vProc_Engmode");
        Intent intent = new Intent();
        intent.setClass(mMainActivity.mContext, EngModeActivity.class);
        mMainActivity.mContext.startActivity(intent);

        exit();
    }

    private void vProc_IdleTimeoutCheck()
    {
        idleTimeoutProc.run();
    }

    private void vProc_Idle() {
        if(mNextState != PROC_NULL)
        {
            mState = mNextState;
            mNextState = PROC_NULL;
        }
    }

    public void vSetIdleState()
    {
        mState = PROC_IDLE;
    }

    public void vSetNextState(MainState state)
    {
        mNextState = state;
    }

    public void vSetBeforeLoginState(MainState state)
    {
        mBeforeLoginState = state;
    }

    public void vClearBeforeLoginState()
    {
        mBeforeLoginState = PROC_NULL;
    }

    public MainState vGetBeforeLoginState()
    {
        return mBeforeLoginState;
    }

    public MainState getPageState()
    {
        return mState;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)//By Alan*
    {
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

    public void physical_Start_Button()//By Alan*
    {
        vChangeDisplayPage(homeLayout);
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                homeLayout.physical_Start_Button();
            }
        });
    } // *By Alan

    public void v_Goto_Home()
    {
        switch( mState )
        {
            case PROC_HOME                  : { homeProc.vSetNextState(HomeState.PROC_EXIT);                 break ; }
            case PROC_LOGIN                 : { loginProc.vMainChangePage(MainState.PROC_HOME);               break ; }
            case PROC_TARGET_TRAIN          : { targetTrainProc.vMainChangePage(MainState.PROC_HOME);   break ; }
            case PROC_BODY_MANAGER          : { bodymanagerProc.vMainChangePage(MainState.PROC_HOME);   break ; }
            case PROC_HEART_RATE_CONTROL    : { heartrateProc.vMainChangePage(MainState.PROC_HOME);       break ; }
            case PROC_SETTING               : { settingProc.vMainChangePage(MainState.PROC_HOME);           break ; }
            case PROC_TRAINING              : { trainingProc.vMainChangePage(MainState.PROC_HOME);         break ; }
            case PROC_PHYSICAL              : { physicalProc.vMainChangePage(MainState.PROC_HOME);         break ; }
            case PROC_MY_GYM                : { myGymProc.vMainChangePage(MainState.PROC_HOME);               break ; }
            case PROC_MY_WORKOUT            : { myWorkoutProc.vMainChangePage(MainState.PROC_HOME);       break ; }
            case PROC_HIIT                  : { hiitProc.vMainChangePage(MainState.PROC_HOME);                 break ; }
            case PROC_QUICK_START           : { quickStartProc.vMainChangePage(MainState.PROC_HOME);     break ; }
            case PROC_MY_PERFORMANCE        : { performanceProc.vMainChangePage(MainState.PROC_HOME);       break ; }
            default : {
                break ;
            }
        }
    }

    public void v_Goto_ExercisePage()
    {
        ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);
        boolean bchange = true;

        switch( mState )
        {
            case PROC_HOME                  : { homeProc.vSetNextState(HomeState.PROC_EXIT);                 break ; }
            case PROC_LOGIN                 : {

                loginProc.vSetNextState(LoginState.PROC_EXIT);

                break ; }
            case PROC_TARGET_TRAIN          : { targetTrainProc.vSetNextState(TargetTrainState.PROC_EXIT);   break ; }
            case PROC_BODY_MANAGER          : { bodymanagerProc.vSetNextState(BodyManagerState.PROC_EXIT);   break ; }
            case PROC_HEART_RATE_CONTROL    : { heartrateProc.vSetNextState(HeartRateState.PROC_EXIT);       break ; }
            case PROC_SETTING               : { settingProc.vSetNextState(SettingState.PROC_EXIT);           break ; }
            case PROC_TRAINING              : { trainingProc.vSetNextState(TrainingState.PROC_EXIT);         break ; }
            case PROC_PHYSICAL              : { physicalProc.vSetNextState(PhysicalState.PROC_EXIT);         break ; }
            case PROC_MY_GYM                : { myGymProc.vSetNextState(MyGymState.PROC_EXIT);               break ; }
            case PROC_MY_WORKOUT            : { myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXIT);       break ; }
            case PROC_HIIT                  : { hiitProc.vSetNextState(HiitState.PROC_EXIT);                 break ; }
            case PROC_QUICK_START           : { quickStartProc.vSetNextState(QuickStartState.PROC_EXIT);     break ; }
            case PROC_MY_PERFORMANCE        : { performanceProc.vSetNextState(PerformanceState.PROC_EXIT);       break ; }
            default : {
                bchange = false;
                break ;
            }
        }

        if(bchange) {
            switch (imode) {
                case PROC_EXERCISE_TR:
                    mMainActivity.mMainProcTreadmill.vSetNextState(MainState.PROC_TRAINING);
                    mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_EXERCISE_SHOW);
                    break;

                case PROC_EXERCISE_HEARTRATE:
                    mMainActivity.mMainProcTreadmill.vSetNextState(MainState.PROC_HEART_RATE_CONTROL);
                    mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_EXERCISE_SHOW);
                    break;

                case PROC_EXERCISE_WORKOUT:
                    mMainActivity.mMainProcTreadmill.vSetNextState(MainState.PROC_MY_WORKOUT);
                    mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXERCISE_SHOW);
                    break;

                case PROC_EXERCISE_QUICKSTART:
                    mMainActivity.mMainProcTreadmill.vSetNextState(MainState.PROC_QUICK_START);
                    mMainActivity.mMainProcTreadmill.quickStartProc.vSetNextState(QuickStartState.PROC_EXERCISE_SHOW);
                    break;
                default:
                    break;
            }
        }

    }

    /* ------------------------------------------------------------------------ */
    /* Override Functions */
    @Override
    public void start() {
        bRun = true ;
        super.start();
    }

    @Override
    public void run() {
        super.run();
        try {
            while ( bRun ) {
                long lBetweenTimeMillis;

                if (tempState != mState)
                {
                    Log.e("Jerry", "-------------------------------------------------------");
                    Log.e("Jerry", "[MainProcTreadmill] mState = " + mState);
                    tempState = mState;
                }

                switch( mState )
                {
                    case PROC_INIT                  : { vProc_Init()            ;       break ; }
                    case PROC_HOME                  : { vProc_Home()            ;       break ; }
                    case PROC_LOGIN                 : {

                        vProc_Login()           ;       break ; }
                    case PROC_TARGET_TRAIN          : { vProc_TargetTrain()     ;       break ; }
                    case PROC_BODY_MANAGER          : { vProc_BodyManager()     ;       break ; }
                    case PROC_HEART_RATE_CONTROL    : { vProc_HeartRate()       ;       break ; }
                    case PROC_SETTING               : { vProc_Setting()         ;       break ; }
                    case PROC_TRAINING              : { vProc_Training()        ;       break ; }
                    case PROC_PHYSICAL              : { vProc_Physical()        ;       break ; }
                    case PROC_MY_GYM                : { vProc_MyGym()           ;       break ; }
                    case PROC_MY_WORKOUT            : { vProc_MyWorkout()       ;       break ; }
                    case PROC_HIIT                  : { vProc_Hiit()            ;       break ; }
                    case PROC_QUICK_START           : { vProc_QuickStart()      ;       break ; }
                    case PROC_MY_PERFORMANCE        : { vProc_Performance()      ;       break ; }
                    case PROC_IDLE                  : { vProc_Idle()            ;       break ; }
                    case PROC_ENGMODE               : { vProc_Engmode()            ;       break ; }
                    default : {
                        mState = PROC_INIT ;
                        iCount = 0 ;
                        break ;
                    }
                }

                //Show Dialog
                {
                    vProc_Dialog();
                }

                //Check Emergency,Error,Common,Exercise
                {
                    vProc_Exercise();
                }

                //Check speed , incline change info
                {
                    vProc_Info();
                }

                //Exercice BottonInfo
                {
                    vProc_BottomInfo();
                }

                //Choice item
                {
                    vProc_ChoiceItem();
                }

                //Uart Command hanshake
                {
                    vProc_Uart_Handshake();
                }

                //Cloud Command
                {
                    vProc_CloudCmd();
                }

                //check update
                {
                    vProc_RtxUpdate();
                }

                //check fan only treadmill
                {
                    vProc_Fan();
                }

                //Idle Timeout Check
                {
                    vProc_IdleTimeoutCheck();
                }

                ExerciseData.icount_add();

                //*By Alan
                if(mState!=MainState.PROC_HOME && Physical_Start_Bt == true) {
                    Physical_Start_Bt =false;
                }else if(ExerciseData.uart_data1.ikey_event == 3 && mState==MainState.PROC_HOME && Physical_Start_Bt==true){
                    Physical_Start_Bt = false;
                    if(ExerciseData.i_get_finish() == 0x06) {
                        physical_Start_Button();
                        ExerciseData.v_set_finish(-1);
                    }
                }

                if(mState==MainState.PROC_HOME && Physical_Start_Bt==false)
                {
                    Physical_Start_Bt = true;
                }
                //*By Alan

                if ( bRun == false )
                {
                    break ;
                }

                lNowTimeMillis = System.currentTimeMillis() ;
                if ( lNowTimeMillis > lStartTimeMillis ) {
                    lBetweenTimeMillis = lNowTimeMillis - lStartTimeMillis ;
                } else {
                    lBetweenTimeMillis = 0 ;
                    lStartTimeMillis = lNowTimeMillis ;
                }

                if ( lBetweenTimeMillis < DEF_UNIT_TIME ) {
                    Thread.sleep( DEF_UNIT_TIME - lBetweenTimeMillis ) ;
                }

                lStartTimeMillis = System.currentTimeMillis() ;
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }

        System.exit(0);
    }
}
