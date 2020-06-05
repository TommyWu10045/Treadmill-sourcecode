package com.rtx.treadmill.Engmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.retronix.circleuart.UartCommunication;
import com.retronix.circleuart.UartCommunicationVirtual;
import com.rtx.treadmill.Cloud.CloudCmd;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxShare.Service_VirtualButton;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
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


public class EngModeActivity extends Rtx_BaseActivity {
    private static String TAG = "Jerry=EngMode";
    private final static boolean DEBUG = true;

    public Context mContext;
    public Handler mUI_Handler;
    public CloudCmd mCloudCmd;
    public Uartcommand mUartcmd = null ;

    public boolean bUartTest = !Rtx_Debug.RELEASE_MODE;

    public EngModeProc mEngModeProc = null;

    private SimpleDateFormat mSimpleDateFormat;
    private Date mDate;

    private IntentFilter mFilter;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.e(TAG, "================EngModeActivity onCreate================");

        mContext = this;
        GlobalData.global_context = mContext;

        mUI_Handler = new Handler();

        Perf.v_ReLoad_Perf(mContext);

        base_init();

        if(bUartTest) {
            try {
                cUartDeviceTask();
            } catch (Exception e) {

            }

            if (mUartcmd == null) {
                mUartcmd = new Uartcommand();
            }
        }

        v_file_permission();

        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        mFilter.addDataScheme("file");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(DEBUG)Log.e(TAG, "======intent.getAction() =" + intent.getAction());
                if(intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
                    WriteLogUtil.vSave_LogInfo_to_USB_Storage();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) Log.e(TAG, "================EngModeActivity onStart================");

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

            {
                Intent it = new Intent(this,Service_VirtualButton.class);
                stopService(it);
            }
        }

        if(mCloudCmd == null)
        {
            mCloudCmd = new CloudCmd();
        }

        if(mEngModeProc == null)
        {
            mEngModeProc = new EngModeProc(this);
            mEngModeProc.start();
        }

        mContext.registerReceiver(mReceiver, mFilter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) Log.e(TAG, "================EngModeActivity onResume================");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (DEBUG) Log.e(TAG, "================EngModeActivity onPause================");


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DEBUG) Log.e(TAG, "================EngModeActivity onStop================");
        mContext.unregisterReceiver(mReceiver);

        if(bUartTest) {
            if(!Rtx_Debug.VIRTUAL_UART_ENABLE) {
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.e(TAG, "================EngModeActivity onDestroy================");

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        Toast.makeText(this, newConfig.toString(), Toast.LENGTH_SHORT).show();
    }

    /* Uart Device Communication Thread */
    protected void cUartDeviceTask() throws IOException {
        if (DEBUG) Log.e(TAG, "---cUartDeviceTask-------");
        if(!Rtx_Debug.VIRTUAL_UART_ENABLE) {
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
