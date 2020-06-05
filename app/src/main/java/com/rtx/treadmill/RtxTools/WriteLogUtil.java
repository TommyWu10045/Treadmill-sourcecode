package com.rtx.treadmill.RtxTools;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * 日志文件类
 * @author Administrator
 *
 */
public class WriteLogUtil {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	private static String def_storage = "/storage/";
	private static String def_fold = "Circle";
	private static String fold_path = "/Android/" + def_fold;

	//Device Warning Error
	private static String sfile_Info = "/Dev_Info";

	//Debug logcat
	private static boolean blogcat_enable = false;
	private static ArrayList<String> slogcat_list = new ArrayList<String>();
	private static String def_file_01 = "/logcat_debug";

	private static String getLogFileName(String sfile) {
		String fileName = getLogPathName() + sfile_Info;;

		if (sfile != null) {
			fileName = getLogPathName() + sfile;
		}

		return fileName;
	}

	private static String getLogPathName() {
		return Environment.getExternalStorageDirectory() + fold_path;
	}

    private static String sGet_Tab() {
        String stab = "\t\t\t";

        return stab;
    }

	private static String Create_Log_Message(String sdata) {
		String message ;
		String sdate ;

		sdate = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd HH:mm:ss", null, null, 0, 0);
		message = sdate + sGet_Tab() + sdata;

		return message;
	}

	public synchronized static void writeLogInfotoFile(String sdata, String sfname, boolean bappend) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		String message ;

		if(sdata == null || sfname == null )
		{
			return;
		}

		message = Create_Log_Message(sdata);
		if(DEBUG) Log.e(TAG, "writeLogErrorFile==================message=" + message);

		try {
			if (fos == null) {
				System.gc();
				// Create fold
				File fileLogPath = new File(getLogPathName());
				if (!fileLogPath.exists()) {
					fileLogPath.mkdirs();
				}
				// Create file
				String sfile = getLogFileName(sfname);
				File fileLog = new File(sfile);
				if (!fileLog.exists()) {
					fileLog.createNewFile();
				}
				// 构建FileOutputStream,第二个为true即是追加，否则是覆盖
				fos = new FileOutputStream(fileLog, bappend);
			}
			osw = new OutputStreamWriter(fos,"GB2312");
		    writer = new BufferedWriter(osw); 
		    message = message.endsWith("\n") ? message : message + "\n";
			writer.write(message);
			writer.flush();

		} catch (IOException e) {
			Log.e(TAG, "IOException e=" + getStackTrace(e));
		}finally{
			closeLogStream(fos,osw,writer);
		}

	}

	public synchronized static void writeLogInfotoFile(ArrayList<String> sdata, String sfname, boolean bappend) {
		int iLoop;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		String message ;

		if(sdata == null || sfname == null )
		{
			return;
		}

		try {
			if (fos == null) {
				System.gc();
				// Create fold
				File fileLogPath = new File(getLogPathName());
				if (!fileLogPath.exists()) {
					fileLogPath.mkdirs();
				}
				// Create file
				String sfile = getLogFileName(sfname);
				File fileLog = new File(sfile);
				if (!fileLog.exists()) {
					fileLog.createNewFile();
				}
				// 构建FileOutputStream,第二个为true即是追加，否则是覆盖
				fos = new FileOutputStream(fileLog, bappend);
			}
			osw = new OutputStreamWriter(fos,"GB2312");
			writer = new BufferedWriter(osw);
			for(iLoop = 0; iLoop < sdata.size(); iLoop++)
			{
				message = sdata.get(iLoop);
				message = message.endsWith("\n") ? message : message + "\n";

				writer.write(message);
				writer.flush();
			}

		} catch (IOException e) {
			Log.e(TAG, "IOException e=" + getStackTrace(e));
		}finally{
			closeLogStream(fos,osw,writer);
		}

	}


	/**
	 * 关闭日志输出流
	 * */
	private static void closeLogStream(FileOutputStream fos,OutputStreamWriter osw,BufferedWriter writer) {
		try {
			if (fos != null) {
				fos.close();
			}
			if (osw != null) {
				osw.close();
			}
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException e=" + getStackTrace(e));
		}
	}

	/**
	 * 得到错误信息内容
	 * */
	private static String getStackTrace(Throwable t) {
		StackTraceElement[] items = t.getStackTrace();
		String stackTrace = t.getMessage();
		for (StackTraceElement item : items) {
			stackTrace += "\n    " + item.getClassName();
			stackTrace += "." + item.getMethodName();
			stackTrace += ":" + item.getLineNumber();
		}
		return stackTrace;
	}


	//////////////////////////////////////////////////////
	public static void vWrite_LogInfo_Code(boolean bcloud) {
		String stype = "";
		String sinfo = "";
		String message;

		if(ExerciseData.uart_data1.serror_code == null)
		{
			return;
		}

		stype = ExerciseData.Check_error_type(ExerciseData.uart_data1.serror_code);

		if(stype == null)
		{
			return;
		}

		if(stype.compareTo("E") == 0)
		{
			sinfo = ExerciseData.uart_data1.serror_code + sGet_Tab() + ExerciseData.Check_error_code(ExerciseData.uart_data1.serror_code);

			message = stype + sGet_Tab() + sinfo;
			writeLogInfotoFile(message, sfile_Info, true);
		}
		else if(stype.compareTo("W") == 0)
		{
			sinfo = ExerciseData.uart_data1.serror_code + sGet_Tab() + ExerciseData.Check_warning_code(ExerciseData.uart_data1.serror_code);

			message = stype + sGet_Tab() + sinfo;
			writeLogInfotoFile(message, sfile_Info, true);
		}
		else if(stype.compareTo("S") == 0)
		{
			sinfo = ExerciseData.uart_data1.serror_code + sGet_Tab() + "Standby";
		}
		else if(stype.compareTo("R") == 0)
		{
			sinfo = ExerciseData.uart_data1.serror_code + sGet_Tab() + "Runing";
		}

		if(bcloud) {
			CloudCmd_Info.vCloudCmd_Add(cloudglobal.iCHK_LIV05, 0);

			CloudCmd_Info.vCloudCmd_Add(cloudglobal.iQRY_UPD03, 0);
		}

	}

	public static String sRead_Error_file() {
		String message = "";
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(getLogFileName(sfile_Info));
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				message += sCurrentLine + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if(DEBUG) Log.e(TAG, "sRead_Error_file============message=" + message);

		return message;
	}

	//////////////////////////////////////////////////////
	private static String getFileDir(String filePath)
	{
		String spath = null;
		int iLoop, iLoop1;

		boolean bskip = false;
		String[] skip_path =
				{
						"emulated",
						"sdcard0",
						"self",
				};

		File f = new File(filePath);
		File[] files = null ;
		if (f.canRead())
		{
			files=f.listFiles();
		}

		if(files != null)
		{
			for(iLoop = 0; iLoop < files.length; iLoop++)
			{
				bskip = false;
				File file = files[iLoop];
				if(DEBUG)Log.d(TAG, "file.getName()=" + file.getName());

				for(iLoop1 = 0; iLoop1 < skip_path.length; iLoop1++)
				{
					if(file.getName().equals(skip_path[iLoop1]))
					{
						bskip = true;
						break;
					}
				}

				if(!bskip)
				{
					File folds = new File(filePath+file.getName());
					File[] folds_circle = null ;
					if (folds.canRead())
					{
						folds_circle = folds.listFiles();
					}

					for(iLoop1 = 0; iLoop1 < folds_circle.length; iLoop1++)
					{
						File fold_circle = folds_circle[iLoop1];
						if(fold_circle.getName().equals(def_fold))
						{
							spath = filePath+file.getName() + "/" + def_fold;
							return spath;
						}
					}
				}
			}
		}
		return spath;
	}

	public static boolean vSave_Debug_LogCatInfo(String sfname) {
		ArrayList<String> message;
		String sf ;
		String[] command = new String[] { "logcat", "-v", "time", "-d" };
		boolean bret = false;

		synchronized (command) {
			message = LogcatHelper.read(command);

			if (message.size() > 0) {
				sf = "/DebugInfo-" + Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd_HH-mm-ss", null, null, 0, 0) + ".log";
				if(DEBUG)Log.e(TAG, "===vSave_Debug_LogInfo===sf=" + sf);
				writeLogInfotoFile(message, sf, true);
				bret = true;
			}
		}

		return bret;

	}

	public static void vSave_LogCat_Start() {

		if (Rtx_Debug.bGetLogcat_SaveEnable()) {
			blogcat_enable = true;
			slogcat_list.clear();

			String ssrc = getLogFileName(def_file_01);
			File src = new File(ssrc);
			if(src.exists()) {
				src.delete();
			}

			new Thread(new Runnable() {
				@Override
				public void run()
				{
					String[] command = new String[] { "logcat", "-v", "time", "*:E"};
					Process process = null;
					try {
						process = Runtime.getRuntime().exec(command);
					} catch (IOException e) {
						e.printStackTrace();
					}
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String s = "";
					while (blogcat_enable) {
						try {
							s = bufferedReader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(s != null) {
							slogcat_list.add(s);
							if(slogcat_list.size() > 10240)
							{
								for(int iLoop = 0; iLoop < 100; iLoop++) {
									slogcat_list.remove(0);
								}
							}

						}
					}

				}
			}).start();

		}

		return;

	}

	public static void vSave_LogCat_Stop() {
		if(blogcat_enable) {
			blogcat_enable = false;
			if (slogcat_list != null && slogcat_list.size() > 0) {
				writeLogInfotoFile(slogcat_list, def_file_01, false);
			}
		}
	}

	public static boolean vSave_Debug_LogInfo(String sfname) throws IOException {
		boolean bret = true;
		File src ;
		File dst ;
		String ssrc = getLogFileName(def_file_01);
		String sdst = sfname + def_file_01 + "-" + Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd_HH-mm-ss", null, null, 0, 0) + ".log";

		src = new File(ssrc);
		dst = new File(sdst);

		if(src.exists()) {
			vCopy_LogInfo(src, dst);
		}

		return bret;
	}


	public static boolean vSave_Device_LogInfo(String sfname) throws IOException {
		boolean bret = true;
		File src ;
		File dst ;
		String ssrc = getLogFileName(sfile_Info);
		String sdst = sfname + sfile_Info + "-" + EngSetting.s_Get_ENG_DEV_MSN() + "-"
				+ Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd_HH-mm-ss", null, null, 0, 0) + ".log";

		src = new File(ssrc);
		dst = new File(sdst);
		if(src.exists()) {
			vCopy_LogInfo(src, dst);
		}
		return bret;
	}

	public static boolean vCopy_LogInfo(File src, File dst) throws IOException {
		boolean bret = true;

		if(src != null && dst != null && src.exists())
		{
			if(DEBUG) Log.e(TAG, "vCopy_LogInfo===src=" + src.getName());
			if(DEBUG) Log.e(TAG, "vCopy_LogInfo===dst=" + dst.getName());

			InputStream in = new FileInputStream(src);
			try {
				OutputStream out = new FileOutputStream(dst);
				try {
					// Transfer bytes from in to out
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				} finally {
					out.close();
				}
			} finally {
				in.close();
			}
		}

		return bret;
	}

	public static void vSave_LogInfo_to_USB_Storage() {
		String sfold_path = null;
		boolean bret = false;

		sfold_path = getFileDir(def_storage);
		if(DEBUG)Log.e(TAG, "===vSave_LogInfo_to_USB_Storage===sfold_path=" + sfold_path);
		if(sfold_path != null)
		{
			synchronized (sfold_path) {
				try {
					bret = vSave_Device_LogInfo(sfold_path);
					bret = vSave_Debug_LogInfo(sfold_path);
				} catch (Exception e) {

				}
			}
			if(DEBUG) Log.e(TAG, "vSave_LogInfo_to_USB_Storage============Copy Log file to USB Storage Success!=");
			Toast.makeText(GlobalData.global_context, "Copy Log file to USB Storage Success!", Toast.LENGTH_LONG).show();
		}
		else
		{
			if(DEBUG) Log.e(TAG, "vSave_LogInfo_to_USB_Storage============Copy Log file to USB Storage Fail!=");
			Toast.makeText(GlobalData.global_context, "Copy Log file to USB Storage Fail!", Toast.LENGTH_LONG).show();
		}
	}

}
