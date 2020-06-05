package com.rtx.treadmill.Engmode;


import android.content.Context;
import android.content.Intent;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

import static com.rtx.treadmill.Engmode.EngModeState.PROC_INIT;
import static com.rtx.treadmill.Engmode.EngModeState.PROC_NULL;
import static com.rtx.treadmill.GlobalData.EngSetting.DEF_UNIT_TIME;

/**
 * Created by chasechang on 3/27/17.
 */

public class EngModeProc extends Thread {
    private String TAG = "Jerry" ;

    private EngModeActivity mEngModeActivity ;
    private Context mContext;

    private long        lStartTimeMillis    = 0 ;
    private long        lNowTimeMillis      = 0 ;
    private boolean     bRun                = false ;
    private int         iCount              = 0;
    private EngModeState   mState              = PROC_INIT ;
    private EngModeState   mNextState          = PROC_NULL ;

    private EngWifiFrame f_EngWifiFrame;
    private EngAboutFrame f_EngAboutFrame;
    private EngSettingFrame f_EngSettingFrame;
    private EngUpdateFrame f_EngUpdateFrame;
    private EngErrorFrame f_EngErrorFrame;
    private UartTestFrame f_UartTestFrame;

    public EngModeProc(EngModeActivity mEngModeActivity) {
        this.mEngModeActivity = mEngModeActivity ;
        this.mContext = mEngModeActivity.mContext;

        lStartTimeMillis = lNowTimeMillis = System.currentTimeMillis();
        bRun             = false ;
        mState           = PROC_INIT ;
        iCount           = 0 ;

        if (f_EngWifiFrame == null) {  f_EngWifiFrame = new EngWifiFrame(mEngModeActivity.mContext, mEngModeActivity); }
        if (f_EngAboutFrame == null) {  f_EngAboutFrame = new EngAboutFrame(mEngModeActivity.mContext, mEngModeActivity); }
        if (f_EngSettingFrame == null) {  f_EngSettingFrame = new EngSettingFrame(mEngModeActivity.mContext, mEngModeActivity); }
        if (f_EngUpdateFrame == null) {  f_EngUpdateFrame = new EngUpdateFrame(mEngModeActivity.mContext, mEngModeActivity); }
        if (f_EngErrorFrame == null) {  f_EngErrorFrame = new EngErrorFrame(mEngModeActivity.mContext, mEngModeActivity); }

        if (f_UartTestFrame == null) {  f_UartTestFrame = new UartTestFrame(mEngModeActivity.mContext, mEngModeActivity); }

    }

    public void vSetNextState(EngModeState nextState)
    {
        mNextState = nextState;
    }

    public void vExitThread()
    {
        bRun = false;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        mEngModeActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

//        f_EngSettingFrame.vEngSettingFrame_Register();

//        f_EngSettingFrame.vEngSettingFrame_Connect();

        if(mNextState == EngModeState.PROC_NULL)
        {
            mState = EngModeState.PROC_SHOW_WIFI;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Wifi() {
        vChangeDisplayPage(f_EngWifiFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_About() {
        vChangeDisplayPage(f_EngAboutFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Setting() {
        vChangeDisplayPage(f_EngSettingFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Update() {
        vChangeDisplayPage(f_EngUpdateFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Error() {
        vChangeDisplayPage(f_EngErrorFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_UartTest() {
        vChangeDisplayPage(f_UartTestFrame);
        mState = EngModeState.PROC_IDLE ;
    }

    private void vProc_ShowPage_UartTest_check() {
        if(f_UartTestFrame != null)
        {
            f_UartTestFrame.vUartCmd_check();
        }
        vProc_Idle();
    }

    private void vProc_ShowPage_UartTest_repeat() {
        if(f_UartTestFrame != null)
        {
            f_UartTestFrame.vUartCmd_Repeat_check();
        }
        vProc_Idle();
    }

    private void vProc_Idle()
    {
        if(mNextState != EngModeState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = EngModeState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        Perf.v_Update_Perf_EngSetting(mContext);

        Intent intent = new Intent();
        intent.setClass(mEngModeActivity.mContext, MainActivity.class);
        mEngModeActivity.mContext.startActivity(intent);

        mState = EngModeState.PROC_INIT ;
        mNextState = EngModeState.PROC_NULL;

        vExitThread();
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mEngModeActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mEngModeActivity.removeAllViews();
                mEngModeActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(EngModeState state)
    {
        mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_EXIT);
    }

    /* ------------------------------------------------------------------------ */
    /* Override Functions */
    @Override
    public void start() {
        bRun = true ;
        super.start();
    }

    public void run() {
        super.run();

        try {
            while (bRun) {
                long lBetweenTimeMillis;

                switch (mState) {
                    case PROC_INIT: {
                        vProc_Init();
                        break;
                    }
                    case PROC_SHOW_WIFI: {
                        vProc_ShowPage_Wifi();
                        break;
                    }
                    case PROC_SHOW_ABOUT: {
                        vProc_ShowPage_About();
                        break;
                    }
                    case PROC_SHOW_SETTING: {
                        vProc_ShowPage_Setting();
                        break;
                    }
                    case PROC_SHOW_UPDATE: {
                        vProc_ShowPage_Update();
                        break;
                    }
                    case PROC_SHOW_ERROR: {
                        vProc_ShowPage_Error();
                        break;
                    }
                    case PROC_SHOW_UARTTEST: {
                        vProc_ShowPage_UartTest();
                        break;
                    }
                    case PROC_SHOW_UARTTEST_CHECK: {
                        vProc_ShowPage_UartTest_check();
                        break;
                    }
                    case PROC_SHOW_UARTTEST_REPEAT: {
                        vProc_ShowPage_UartTest_repeat();
                        break;
                    }

                    case PROC_IDLE: {
                        vProc_Idle();
                        break;
                    }
                    case PROC_EXIT: {
                        vProc_Exit();
                        break;
                    }
                    default: {
                        vProc_Idle();
                        break;
                    }
                }

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
