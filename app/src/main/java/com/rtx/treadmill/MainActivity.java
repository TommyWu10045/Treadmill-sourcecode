package com.rtx.treadmill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.retronix.circleuart.UartCommunication;
import com.retronix.circleuart.UartCommunicationVirtual;
import com.rtx.treadmill.Cloud.CloudCmd;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.GlobalData.infolist;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxThread.RtxWeatherCheck;
import com.rtx.treadmill.RtxThread.RtxWeatherManager2019;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.WriteLogUtil;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.BIND_SCREENING_SERVICE;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.CHANGE_NETWORK_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.CLEAR_APP_CACHE;
import static android.Manifest.permission.DELETE_CACHE_FILES;
import static android.Manifest.permission.DELETE_PACKAGES;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.Manifest.permission.WAKE_LOCK;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends Rtx_BaseActivity implements Thread.UncaughtExceptionHandler{
    public static boolean DEBUG = false;
    private static String TAG = "Jerry=";

    public Context mContext;
    public Handler mUI_Handler;
    public CloudCmd mCloudCmd;
    public Uartcommand mUartcmd  ;

    //public  MainProcTreadmill mMainProcTreadmill = null;
    //public  MainProcBike mMainProcBike = null;
    private RtxWeatherCheck thread_RtxWeatherCheck;
    private RtxWeatherManager2019 thread_RtxWeatherManager2019;

    private SimpleDateFormat mSimpleDateFormat;
    private Date mDate;

    private IntentFilter mFilter;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.e(TAG, "================MainActivity onCreate================");

        if((GlobalData.i_check_isruning() & 0x10) == 0x10)
        {
            System.exit(0);
        }
        GlobalData.v_set_run_status(0x10);

        mContext = this;
        GlobalData.global_context = mContext;

        Perf.v_ReLoad_Perf(mContext);

        base_init();

        mUI_Handler = new Handler();

        mFilter = new IntentFilter();
        mFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(DEBUG)Log.e(TAG, "======intent.getAction() =" + intent.getAction());
               if(intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED))
                {
//                    Stop_apk();
                }
            }
        };

        v_file_permission();

        WriteLogUtil.vSave_LogCat_Start();

        Thread.setDefaultUncaughtExceptionHandler(this); // By Alan
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) Log.e(TAG, "================MainActivity onStart================");

        infolist minfolist = new infolist();
        minfolist.parse_info_file();

        LanguageData.getDeviceLanguage(mContext);

        GlobalData.globalData_init();

        {
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String sVersion = pInfo.versionName;

            EngSetting.v_Set_APK_VER(sVersion);
//            EngSetting.v_Set_APK_VER(sVersion.split("\\.")[2]);
            EngSetting.v_Set_ENG_GNR(sVersion.split("\\.")[0]);

            try {
                long lFirstInstallTime = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).firstInstallTime;
                long lLastUpdateTime = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).lastUpdateTime;
                mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                if(lFirstInstallTime >= lLastUpdateTime)
                {
                    mDate = new Date(lFirstInstallTime);
                }
                else
                {
                    mDate = new Date(lLastUpdateTime);
                }
                EngSetting.v_Set_SW_UPDATE_TIME(mSimpleDateFormat.format(mDate));
            }catch (PackageManager.NameNotFoundException e)
            {}
        }

        if(mCloudCmd == null)
        {
            mCloudCmd = new CloudCmd();
        }

        if(mUartcmd == null)
        {
            mUartcmd = new Uartcommand();
        }

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
            if (mMainProcTreadmill == null) {
                mMainProcTreadmill = new MainProcTreadmill(this);
                mMainProcTreadmill.start();
            }
        }
        else
        {
            if (mMainProcBike == null) {
                mMainProcBike = new MainProcBike(this);
                mMainProcBike.start();
            }
        }

        if(Rtx_Debug.bAutoLogin())
        {
            CloudDataStruct.CloudData_20.log_in_test(Rtx_Debug.sGetID_ForAutoLogin());
            mCloudCmd.iCloudCmd_GetUserInfo();
        }

        mContext.registerReceiver(mReceiver, mFilter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        stopVirtualButtonSetvice();
        dismissInfoDialog();
        if (DEBUG) Log.e(TAG, "================MainActivity onResume================");


    }

    @Override
    protected void onPause() {
        startVirtualButtonSetvice();
        super.onPause();
        if (DEBUG) Log.e(TAG, "================MainActivity onPause================");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        Toast.makeText(this, newConfig.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DEBUG) Log.e(TAG, "================MainActivity onStop================");

        mContext.unregisterReceiver(mReceiver);

        if (thread_RtxWeatherCheck != null) {
            thread_RtxWeatherCheck.v_WeatherCheck_stop();
            thread_RtxWeatherCheck = null;
            if (DEBUG) Log.e(TAG, "thread_RtxWeatherCheck stop");

        }

        if(!Rtx_Debug.bGetVirtualUartEnable()) {
            if (UartData.m_UartCommunicationThread != null) {
                UartData.m_UartCommunicationThread.UartCommunication_stop();
                UartData.m_UartCommunicationThread = null;
                if (DEBUG) Log.e(TAG, "m_UartCommunicationThread stop");
            }
        }
        else
        {
            if (UartData.m_UartCommunicationVirtualThread != null) {
                UartData.m_UartCommunicationVirtualThread.UartCommunication_stop();
                UartData.m_UartCommunicationVirtualThread = null;
                if (DEBUG) Log.e(TAG, "m_UartCommunicationVirtualThread stop");
            }
        }

        UartData.vUartCmd_clean_all(true);

        GlobalData.v_clear_bit_run_status(0x10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.e(TAG, "================MainActivity onDestroy================");

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.out.println("uncaughtException");
        System.exit(0);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //idle
        GlobalData.vResetInteractionTime();
    }

    private void Stop_apk()
    {
        if (DEBUG) Log.e(TAG, "=========Stop_apk============Start");
        if (mMainProcTreadmill != null) {
            mMainProcTreadmill.exit();
            mMainProcTreadmill = null;
        }

        if (mMainProcBike != null) {
            mMainProcBike.exit();
            mMainProcBike = null;
        }

        System.exit(0);
        if (DEBUG) Log.e(TAG, "=========Stop_apk============End");
    }

    /* Uart Device Communication Thread */
    protected void cUartDeviceTask() throws IOException {
        if (DEBUG) Log.e(TAG, "---cUartDeviceTask-------");
        if(!Rtx_Debug.bGetVirtualUartEnable()) {
            if (UartData.m_UartCommunicationThread == null) {
                //guart.DEBUG = true;
                UartData.m_UartCommunicationThread = new UartCommunication(mContext);
                UartData.m_UartCommunicationThread.start();
                if (DEBUG) Log.e(TAG, "m_UartCommunicationThread start");
            }
        }
        else
        {
            if (UartData.m_UartCommunicationVirtualThread == null) {
                //guart.DEBUG = true;
                UartData.m_UartCommunicationVirtualThread = new UartCommunicationVirtual(mContext);
                UartData.m_UartCommunicationVirtualThread.start();
                if (DEBUG) Log.e(TAG, "m_UartCommunicationVirtualThread start");
            }
        }
    }

    //Weather/////////////////////////////////////////////////////////////////////////////

    /* Weather Thread */
    protected void cWeatherTask()
    {
        Rtx_Log.Loge(DEBUG,TAG,"---cWeatherTask-------");
        if(EngSetting.ENABLE_WEATHER)
        {
            //As of Thursday, Jan. 3, 2019, the weather.yahooapis.com and query.yahooapis.com for Yahoo Weather API will be retired.
            if(false)
            {
                if (thread_RtxWeatherCheck == null)
                {
                    thread_RtxWeatherCheck = new RtxWeatherCheck(mContext, this);
                    thread_RtxWeatherCheck.start();
                    if (DEBUG) Log.e(TAG, "thread_RtxWeatherCheck start");
                }
            }
            else
            {
                thread_RtxWeatherManager2019 = RtxWeatherManager2019.getInstance(this);
                thread_RtxWeatherManager2019.start();
                if (DEBUG) Log.e(TAG, "thread_RtxWeatherManager2019 start");
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void v_file_permission()
    {
        int REQUEST_EXTERNAL_STORAGE = 1234;
        String[] spermission = new String[]{
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE,
                RECEIVE_BOOT_COMPLETED,
                CHANGE_NETWORK_STATE,
                ACCESS_NETWORK_STATE,
                CHANGE_WIFI_STATE,
                ACCESS_WIFI_STATE,
                ACCESS_COARSE_LOCATION,
                BLUETOOTH,
                BLUETOOTH_ADMIN,
                SYSTEM_ALERT_WINDOW,
                BIND_SCREENING_SERVICE,
                CLEAR_APP_CACHE,
                DELETE_CACHE_FILES,
                DELETE_PACKAGES,
                WAKE_LOCK,

        };

        int iIndex = 0;
        int iSize = spermission.length;

        for( ; iIndex < iSize ; iIndex++)
        {
            int permission = ActivityCompat.checkSelfPermission(this, spermission[iIndex]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 無權限，向使用者請求
                ActivityCompat.requestPermissions(
                        this,
                        spermission,
                        REQUEST_EXTERNAL_STORAGE
                );

                break;
            }
        }
    }
}
