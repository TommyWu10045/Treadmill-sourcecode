package com.rtx.treadmill.RtxThread;

import android.content.Context;
import android.util.Log;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxTools.Rtx_Preferences;

import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

/**
 * hide status and software button
 * 
 */
public class RtxWeatherCheck extends Thread implements YahooWeatherInfoListener {
	private String TAG = "Jerry===WeatherCheck" ;
	private boolean DEBUG = false ;

	private MainActivity mMainActivity;

	private boolean isrun = true;
	//Loop sleep time(ms)
	private int loopMilli = 1000;

	private int icount = 0 ;

	Context mContext = null;

	private String sdef_search = "CITY";
	private int iRetry;
	private int iRetry_max = 10;
	private boolean bResearch = true;

	private boolean bAlreadySync = false;

	private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, true);

	//Create
	public RtxWeatherCheck(Context context, MainActivity mMainActivity) {
		if(DEBUG) Log.e(TAG, "===========WeatherCheck==========");
		isrun =  true;

		mContext = context ;
		this.mMainActivity = mMainActivity;

		GlobalData.Weather.sCity = EngSetting.s_Get_DEFAULT_CITY();
		iRetry = 0;
		if(sdef_search.compareTo("GPS") == 0)
		{
			v_WeatherCheck_searchByGPS();
		}
		else
		{
			if(GlobalData.Weather.sCity != null && GlobalData.Weather.sCity.compareTo("") != 0) {
				v_WeatherCheck_searchByPlaceName(GlobalData.Weather.sCity);
			}
			else
			{
				v_WeatherCheck_searchByGPS();
			}
		}
	}

	public void v_WeatherCheck_stop()
	{
		isrun = false;
	}


	private void v_WeatherCheck_update_city(String location)
	{
		if(DEBUG) Log.e(TAG, "v_WeatherCheck_update_city=" + location );
		if(location != null) {
			GlobalData.Weather.sCity = location;
			Rtx_Preferences.v_Data_SetPreferences(mContext, Rtx_Preferences.PREF_MACHINE_CITY, location);
		}
	}

	@Override
	public void gotWeatherInfo(final WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
		// TODO Auto-generated method stub
		if (weatherInfo != null) {
			if (mYahooWeather.getSearchMode() == YahooWeather.SEARCH_MODE.GPS) {
				if (weatherInfo.getAddress() != null) {
					GlobalData.Weather.sCity = YahooWeather.addressToPlaceName(weatherInfo.getAddress());
					v_WeatherCheck_update_city(GlobalData.Weather.sCity);
				}
			}

			GlobalData.Weather.sDate = weatherInfo.getCurrentConditionDate();
			GlobalData.Weather.iFTemp = weatherInfo.getCurrentTemp();
			GlobalData.Weather.fCTemp =  (float)((GlobalData.Weather.iFTemp - 32 ) * 5) / 9;
			GlobalData.Weather.iCurrentCode = weatherInfo.getCurrentCode();
			if (weatherInfo.getCurrentConditionIcon() != null)
			{
				if (DEBUG) Log.e(TAG, "===weatherInfo.getCurrentConditionIconURL()=" + weatherInfo.getCurrentConditionIconURL());
				GlobalData.Weather.Bmap = weatherInfo.getCurrentConditionIcon();
			}
			if (DEBUG) Log.e(TAG, "===weather.sCity=" + GlobalData.Weather.sCity);
            if (DEBUG) Log.e(TAG, "===weather.sDate=" + GlobalData.Weather.sDate);
			if (DEBUG) Log.e(TAG, "===weather.iFTemp=" + GlobalData.Weather.iFTemp);
			if (DEBUG) Log.e(TAG, "===weather.fCTemp=" + GlobalData.Weather.fCTemp);
			/*
			"date: " + weatherInfo.getCurrentConditionDate() + "\n" +
			"weather: " + weatherInfo.getCurrentText() + "\n" +
			"temperature in °C: " + weatherInfo.getCurrentTemp() + "\n" +
			"wind chill: " + weatherInfo.getWindChill() + "\n" +
			"wind direction: " + weatherInfo.getWindDirection() + "\n" +
			"wind speed: " + weatherInfo.getWindSpeed() + "\n" +
			"Humidity: " + weatherInfo.getAtmosphereHumidity() + "\n" +
			"Pressure: " + weatherInfo.getAtmospherePressure() + "\n" +
			"Visibility: " + weatherInfo.getAtmosphereVisibility()
			*/
			//mMainActivity.Weather_Refresh();
			GlobalData.Weather.bUpdate = true;
			iRetry = 0;
			bResearch = false;
		} else {
			GlobalData.Weather.sError = errorType.name();
			iRetry++;
			bResearch = true;
			if (DEBUG) Log.e(TAG, "===weather.sError=" + GlobalData.Weather.sError);
		}
	}

	private void v_WeatherCheck_set_temp_unit(int itemp)
	{
		//if(itemp == 0)
		//{
		//	mYahooWeather.setUnit(YahooWeather.UNIT.CELSIUS);
		//}
		//else
		//{
			mYahooWeather.setUnit(YahooWeather.UNIT.FAHRENHEIT);
		//}
	}

	private void v_WeatherCheck_searchByGPS() {
		if (DEBUG) Log.e(TAG, "===v_WeatherCheck_searchByGPS");
		bResearch = false;
		mYahooWeather.setNeedDownloadIcons(true);
		v_WeatherCheck_set_temp_unit(1);
		mYahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.GPS);
		if(mMainActivity.mUI_Handler != null) {
			mMainActivity.mUI_Handler.post(new Runnable() {
				@Override
				public void run() {
					mYahooWeather.queryYahooWeatherByGPS(mContext, RtxWeatherCheck.this);
				}
			});
		}
	}

	private void v_WeatherCheck_searchByPlaceName(String location) {
		if (DEBUG) Log.e(TAG, "===v_WeatherCheck_searchByPlaceName=" + location);
		Log.e("Jerry","Weather Sync!!!");
		bResearch = false;
		mYahooWeather.setNeedDownloadIcons(true);
		v_WeatherCheck_set_temp_unit(1);
		mYahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.PLACE_NAME);
		mYahooWeather.queryYahooWeatherByPlaceName(mContext, location, this);
	}

	public void run() {
		while(isrun){
			try {
				//if (DEBUG) Log.e(TAG, "------run====global.itimeout=" + global.itimeout + "   ===itimecount=" + global.itimecount);
				Thread.sleep(loopMilli);
				if(GlobalData.Weather.bUpdate == false)
				{
					//if( icount >= (EngSetting.i_Get_Weather_Check_interval() * 60))
					if((System.currentTimeMillis() / 1000) % 1800 == 0)
					{
						if(bAlreadySync == false)
						{
							bAlreadySync = true;

							icount = 0;
							iRetry = 0;
							if(GlobalData.Weather.sCity != null && GlobalData.Weather.sCity.compareTo("") != 0)
							{
								v_WeatherCheck_searchByPlaceName(GlobalData.Weather.sCity);
							}
							else
							{
								v_WeatherCheck_searchByGPS();
							}
						}
					}
					else if(bResearch && iRetry < iRetry_max && icount % 24 == 0)
					{
						bAlreadySync = false;
						if (DEBUG) Log.e(TAG, "===bResearch =" + bResearch + "     iRetry=" + iRetry);
						if(GlobalData.Weather.sCity != null && GlobalData.Weather.sCity.compareTo("") != 0)
						{
							v_WeatherCheck_searchByPlaceName(GlobalData.Weather.sCity);
						}
						else
						{
							v_WeatherCheck_searchByGPS();
						}
					}
					else
					{
						bAlreadySync = false;
					}
					icount++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!isrun)
			{
				break;
			}

		}
	}
}
