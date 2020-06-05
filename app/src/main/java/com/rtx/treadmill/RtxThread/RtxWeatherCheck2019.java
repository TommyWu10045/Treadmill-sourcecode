package com.rtx.treadmill.RtxThread;

import com.mopub.volley.AuthFailureError;
import com.mopub.volley.NetworkResponse;
import com.mopub.volley.ParseError;
import com.mopub.volley.Response;
import com.mopub.volley.toolbox.HttpHeaderParser;
import com.mopub.volley.toolbox.JsonRequest;
import com.google.gson.JsonSyntaxException;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RtxWeatherCheck2019<T> extends  JsonRequest<T> {

	final String appId = "BjKcX16u";
	final String CONSUMER_KEY = "dj0yJmk9c2FsOE1kMldvTmpnJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWM5";
	final String CONSUMER_SECRET = "b4d1826b6f91363e00cec84c85ae5bb5fe0a4e18";
	final String baseUrl = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

	public RtxWeatherCheck2019(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);

		GlobalData.Weather.sCity = EngSetting.s_Get_DEFAULT_CITY();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<>();
		OAuthConsumer consumer = new OAuthConsumer(null, CONSUMER_KEY, CONSUMER_SECRET, null);
		consumer.setProperty(OAuth.OAUTH_SIGNATURE_METHOD, OAuth.HMAC_SHA1);
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		try {
			OAuthMessage request = accessor.newRequestMessage(OAuthMessage.GET, getUrl(), null);

			String authorization = request.getAuthorizationHeader(null);
			headers.put("Authorization", authorization);
		} catch (OAuthException e ) {
			throw new AuthFailureError(e.getMessage());
		} catch (IOException| URISyntaxException e) {
			throw new AuthFailureError(e.getMessage());
		}

		headers.put("Yahoo-App-Id", appId);
		headers.put("Content-Type", "application/json");
		return headers;
	}

	@Override
	public String getUrl() {
		return baseUrl + "?location=" + GlobalData.Weather.sCity + "&format=json";
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(
					response.data,
					HttpHeaderParser.parseCharset(response.headers));
			T parsedResponse = parseResponse(json);

			return Response.success(
					parsedResponse,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException | JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	private T parseResponse(String jsonObject) {
        try {
            JSONObject jsonObjectAll = new JSONObject(jsonObject);

			JSONObject jsonObjectCurrentObservation = new JSONObject(jsonObjectAll.getString("current_observation"));

			JSONObject jsonObjectCondition = new JSONObject(jsonObjectCurrentObservation.getString("condition"));

            GlobalData.Weather.iFTemp = Integer.valueOf(jsonObjectCondition.getString("temperature"));
            GlobalData.Weather.fCTemp = (float)((GlobalData.Weather.iFTemp - 32 ) * 5) / 9;
            GlobalData.Weather.iCurrentCode = Integer.valueOf(jsonObjectCondition.getString("code"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalData.Weather.bUpdate = true;

		return null; // Add response parsing here
	}
}