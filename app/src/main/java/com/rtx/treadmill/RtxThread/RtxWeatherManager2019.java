package com.rtx.treadmill.RtxThread;

import android.content.Context;

import com.mopub.volley.Request;
import com.mopub.volley.RequestQueue;
import com.mopub.volley.Response;
import com.mopub.volley.VolleyError;
import com.mopub.volley.toolbox.Volley;
import com.rtx.treadmill.GlobalData.GlobalData;

public class RtxWeatherManager2019 extends Thread {
    private String TAG = "Jerry===WeatherCheck" ;
    private boolean DEBUG = false ;

	private static RtxWeatherManager2019 sInstance;

	Context mContext;
	RequestQueue mRequestQueue;

    private boolean isrun = true;
    //Loop sleep time(ms)
    private int loopMilli = 1000;

    private int icount = 0 ;

    public int iRetry;
    private int iRetry_max = 10;
    public boolean bResearch = true;

    private boolean bAlreadySync = false;

    private RtxWeatherCheck2019 thread_RtxWeatherCheck2019;

	public static synchronized RtxWeatherManager2019 getInstance(Context context)
    {
		if (sInstance == null) {
			sInstance = new RtxWeatherManager2019(context);
		}
		return sInstance;
	}

	private RtxWeatherManager2019(Context context) {
		mContext = context;
		mRequestQueue = Volley.newRequestQueue(mContext);
	}

	public <T> void addToRequestQueue(Request<T> request) {
		mRequestQueue.add(request);
	}

    public void run() {
        thread_RtxWeatherCheck2019 = new RtxWeatherCheck2019(Request.Method.GET, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // Add success logic here
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Add error handling here
            }
        });
        addToRequestQueue(thread_RtxWeatherCheck2019);

        while (isrun) {
            try {
//                if (DEBUG) Log.e(TAG, "------run====global.itimeout=" + global.itimeout + "   ===itimecount=" + global.itimecount);
                Thread.sleep(loopMilli);

                if (GlobalData.Weather.bUpdate == false)
                {
                    //if( icount >= (EngSetting.i_Get_Weather_Check_interval() * 60))
                    if ((System.currentTimeMillis() / 1000) % 1800 == 0)
                    {

                        if (bAlreadySync == false)
                        {
                            bAlreadySync = true;

                            icount = 0;
                            iRetry = 0;

                            if (GlobalData.Weather.sCity != null && GlobalData.Weather.sCity.compareTo("") != 0)
                            {
                                mRequestQueue.add(thread_RtxWeatherCheck2019);
                            }
                            else
                            {
                            }
                        }
                    }
                    else if (bResearch && iRetry < iRetry_max && icount % 24 == 0)
                    {
                        bAlreadySync = false;
//                        if (DEBUG) Log.e(TAG, "===bResearch =" + bResearch + "     iRetry=" + iRetry);
                        if (GlobalData.Weather.sCity != null && GlobalData.Weather.sCity.compareTo("") != 0)
                        {
                            mRequestQueue.add(thread_RtxWeatherCheck2019);
                        }
                        else
                        {
                        }
                    } else {
                        bAlreadySync = false;
                    }
                    icount++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!isrun) {
                break;
            }
        }
    }
}