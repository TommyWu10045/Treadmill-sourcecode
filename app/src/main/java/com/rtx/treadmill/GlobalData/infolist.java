package com.rtx.treadmill.GlobalData;

import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

import com.rtx.treadmill.RtxView.RtxTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.TreeMap;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class infolist {
	private static String TAG = "Tom=";
	private static boolean DEBUG = false;

	String fold_path = "/Android/info_list";
	String[] parse_skeys = {"title", "font", "info", "pos"};
	String[] parse_ikeys = {"size", "color"};

	public static class information
	{
		public String title;
		public String font;
		public String info;

		public int size;
		public int color;
		public int pos;

	}

	public static void clear()
	{
		info_list.clear();
	}

	public static TreeMap<String, information> info_list = new TreeMap<String, information>();

	private int get_pos(String str)
	{
		int iret = 0;
		int iLoop;

		if(str != null)
		{
			String[] sarray = str.split("\\|");
			for(iLoop = 0; iLoop < sarray.length; iLoop++)
			{
				if(DEBUG)Log.e(TAG, "======sarray[iLoop]=" + sarray[iLoop]);
				switch (sarray[iLoop])
				{
					case "left":
						iret |= Gravity.LEFT;
						break;
					case "right":
						iret |= Gravity.RIGHT;
						break;
					case "top":
						iret |= Gravity.TOP;
						break;
					case "bottom":
						iret |= Gravity.BOTTOM;
						break;
					case "center":
						iret |= Gravity.CENTER;
						break;
					case "center_vertical":
						iret |= Gravity.CENTER_VERTICAL;
						break;
					case "center_horizontal":
						iret |= Gravity.CENTER_HORIZONTAL;
						break;
				}
			}
		}

		return iret;
	}

	public void ReadFile ( String sfile ){
		if(DEBUG)Log.e(TAG, "====ReadFile=" + sfile);
		try {
			File yourFile = new File(Environment.getExternalStorageDirectory(), sfile);
			if(DEBUG)Log.e(TAG, "====yourFile.getPath()=" + yourFile.getPath());
			if(DEBUG)Log.e(TAG, "====yourFile.exists()=" + yourFile.exists());
			if(DEBUG)Log.e(TAG, "====yourFile.canRead()=" + yourFile.canRead());
			if(DEBUG)Log.e(TAG, "====yourFile.canWrite()=" + yourFile.canWrite());
			if(!yourFile.exists() || !yourFile.canRead())
			{
				return;
			}

			FileInputStream stream = new FileInputStream(yourFile);
			String jsonStr = null;
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

				jsonStr = Charset.defaultCharset().decode(bb).toString();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				stream.close();
			}
			JSONObject jsonObj = new JSONObject(jsonStr);
			// Getting data JSON Array nodes
			JSONArray data  = jsonObj.getJSONArray("data");
			if(DEBUG)Log.e(TAG, "====data.length=" + data.length());

			// looping through All nodes
			for (int i = 0; i < data.length(); i++) {
				JSONObject js = data.getJSONObject(i);
				information sdata = new information();

				Object Okeyword = js.opt(parse_skeys[0]);
				if(Okeyword != null) {
					sdata.title = Okeyword.toString();

					Okeyword = js.opt(parse_skeys[1]);
					if(Okeyword != null) {
						sdata.font = Okeyword.toString();
					}

					Okeyword = js.opt(parse_skeys[2]);
					if(Okeyword != null) {
						sdata.info = Okeyword.toString();
					}

					Okeyword = js.opt(parse_skeys[3]);
					if(Okeyword != null) {
						sdata.pos = get_pos(js.getString(parse_skeys[3]));
					}

					Okeyword = js.opt(parse_ikeys[0]);
					if(Okeyword != null) {
						sdata.size = js.getInt(parse_ikeys[0]);
					}

					Okeyword = js.opt(parse_ikeys[1]);
					if(Okeyword != null) {
						sdata.color = js.getInt(parse_ikeys[1]);
					}
					else
					{
						sdata.color = 0;
					}

					info_list.put(sdata.title, sdata);
				}
			}
		} catch (Exception e) {
			clear();
			e.printStackTrace();
		}
	}

	public void parse_info_file() {
		String fold = fold_path;
		clear();
		ReadFile(fold);
		return ;
	}

	public static void v_set_info(RtxTextView mView, String skey)
	{
		if(skey == null || skey.compareTo("") == 0 || info_list == null || mView == null)
		{
			return;
		}

		information infodata = info_list.get(skey);
		if(infodata != null)
		{
			if(infodata.font != null)
			{
				String sfont = "fonts/" + infodata.font;
				mView.setTypeface(Typeface.createFromAsset(GlobalData.global_context.getAssets(), sfont));
			}

			if(infodata.size >= 10)
			{
				mView.setTextSize(TypedValue.COMPLEX_UNIT_PX, infodata.size);
			}

			if(infodata.pos != 0) {
				mView.setGravity(infodata.pos);
			}

			if(infodata.color != 0) {
				mView.setTextColor(infodata.color);
			}

			if(infodata.info != null)
			{
				mView.setText(infodata.info);
			}
		}

	}


}
