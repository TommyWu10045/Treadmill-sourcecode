package com.rtx.treadmill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;

public class FirstActivity extends Rtx_BaseActivity {
    public static boolean DEBUG = false;
    private static String TAG = "Jerry=";

    private Context mContext;

    private IntentFilter mFilter;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if((GlobalData.i_check_isruning() & 0x01) == 0x01)
        {
            System.exit(0);
        }
        GlobalData.v_set_run_status(0x01);

        mContext = this;
        GlobalData.global_context = mContext;


        start_apk();
        //_Handler.postDelayed(run, 3000);

        mFilter = new IntentFilter();
        mFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                GlobalData.v_clear_bit_run_status(0x01);
//                System.exit(0);
            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onResume");
        mContext.registerReceiver(mReceiver, mFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onPause");
        mContext.unregisterReceiver(mReceiver);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.e(TAG, "===FirstActivity=======onDestroy");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        Toast.makeText(this, newConfig.toString(), Toast.LENGTH_SHORT).show();
    }

    Handler _Handler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            start_apk();
        }
    };

    private void start_apk(){
        if (DEBUG) Log.e(TAG, "===FirstActivity====start_apk=======");
        _Handler.removeCallbacks(run);
        Intent i = new Intent(mContext, MainActivity.class);
        mContext.startActivity(i);
    }

}
