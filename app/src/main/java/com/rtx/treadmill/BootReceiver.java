package com.rtx.treadmill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.rtx.treadmill.GlobalData.GlobalData;

public class BootReceiver extends BroadcastReceiver {
	private String TAG = "Jerry" ;
	private boolean DEBUG = false ;
	private Context mContext;

	public void onReceive(Context context, Intent intent) {
		mContext = context;
		String saction = intent.getAction();
		if (DEBUG) Log.e(TAG, this.getClass() + "====BootReceiver=============saction=" + saction);

		if (saction.equals("android.intent.action.BOOT_COMPLETED"))
		{
			if((GlobalData.i_check_isruning() & 0x80) != 0x80) {
				GlobalData.v_set_run_status(0x80);
//				_Handler.postDelayed(run, 3000);
				start_apk();
			}
		}
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
		if (DEBUG) Log.e(TAG, "===BootReceiver====start_apk=======");
		_Handler.removeCallbacks(run);
		Intent i = new Intent(mContext, MainActivity.class);
		i.setAction("android.intent.action.MAIN"); //Activity action defined in AndroidManifest.xml
		i.addCategory("android.intent.category.LAUNCHER");//Activity category defined in AndroidManifest.xml
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(i);
	}

}