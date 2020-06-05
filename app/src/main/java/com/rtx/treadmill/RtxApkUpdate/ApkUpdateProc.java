package com.rtx.treadmill.RtxApkUpdate;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_01_QRY_UPD;
import com.retonix.circlecloud.Cloud_02_CHK_LIV;
import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Kevin on 8/16/18.
 */

public class ApkUpdateProc {
    private boolean DEBUG = false ;
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity mMainActivity ;
    private DownloadManager manager;
    private DownloadManager.Request request;

    private Intent mIntent;

    private BufferedOutputStream bufferedOutputStream;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private ZipInputStream zipInputStream;
    private ZipEntry zipEntry;
    private File file;

//    private String sStoragePath = "/storage/emulated/0/";
    private String sStoragePath = "/sdcard/";
    private String sRtxUpdateDir = "/TreadmillApk/";
    private String sRtxUpdateFile = "";
    private String sRtxUpdateApkPath = "";

    private String sUrl = "";

    private long downloadId;
    private int iPreviousFileSize = -1;
    private int iBufferSize = 4096;
    private int iBufferCount;
    byte buffer[] = new  byte[iBufferSize];

    private ApkUpdateState mState = ApkUpdateState.PROC_INIT ;
    private ApkUpdateState mNextState = ApkUpdateState.PROC_NULL ;
    private ApkUpdateState tempState = ApkUpdateState.PROC_NULL ;

    private int icount;
    private int iretry_count;

    public ApkUpdateProc(Context mContext) {
        this.mContext = mContext;

        manager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);

        mState = ApkUpdateState.PROC_INIT;

        icount = 0 ;
        iretry_count = 0 ;
    }

    public ApkUpdateProc(MainActivity mMainActivity) {
        this.mContext = mMainActivity.mContext;
        this.mMainActivity = mMainActivity ;

        manager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);

        mState = ApkUpdateState.PROC_INIT;

        icount = 0 ;
        iretry_count = 0 ;
    }

    /* ------------------------------------------------------------------------ */
    private boolean bCheckLogin()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();

        return bLogin;
    }

    private boolean bCheckInExercise()
    {
        boolean bCheck = false;

        bCheck = ExerciseData.b_is_exercising();

        return bCheck;
    }

    private void vRtxUpdate_RecursionDelFile(File fFile)
    {
        if(fFile.isFile())
        {
            fFile.delete();
            return;
        }
        if(fFile.isDirectory())
        {
            File[] childFile = fFile.listFiles();
            if(childFile == null || childFile.length == 0)
            {
                fFile.delete();
                return;
            }
            for (File fChildFile : childFile)
            {
                vRtxUpdate_RecursionDelFile(fChildFile);
            }

            fFile.delete();
        }
    }

    private void vRtxUpdate_DelFile(String sPath)
    {
        File fFile = new File(sPath);
        if(fFile.isDirectory()) {
            vRtxUpdate_RecursionDelFile(fFile);
        }
        else if (fFile.isFile()) {
            if (fFile.exists()) {
                if (fFile.delete()) {
                } else {
                }
            } else {
            }
        }
    }
    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        icount = 0 ;
//        iretry_count = 0 ;

        if(mNextState == ApkUpdateState.PROC_NULL)
        {
            mNextState = ApkUpdateState.PROC_APKUPDATE_UPDATE;
        }
        else
        {

        }

        mState = ApkUpdateState.PROC_IDLE;
    }

    private void vProc_ApkUpdate_Update()
    {
        icount++;

//        if(icount > 3000)   //for test
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : Start UPDATE function");
            mState = ApkUpdateState.PROC_APKUPDATE_CHECKUPDATE;
            icount = 0;
        }
    }

    private void vProc_ApkUpdate_CheckUpdate()
    {
//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : Start check update");
        if (CloudDataStruct.CloudData_02.sdata_out[Cloud_02_CHK_LIV.Output.UPD_MOD] != null)
        {
            //M:手動 A:自動
            if (CloudDataStruct.CloudData_02.sdata_out[Cloud_02_CHK_LIV.Output.UPD_MOD].equals("A"))
            {
                if (CloudDataStruct.CloudData_02.sdata_out[Cloud_02_CHK_LIV.Output.UPD_COD] != null)
                {
                    //0:不更新 1:SW更新 2:FW更新
                    if (CloudDataStruct.CloudData_02.sdata_out[Cloud_02_CHK_LIV.Output.UPD_COD].equals("1"))
                    {
                        if (CloudDataStruct.CloudData_02.sdata_out[Cloud_02_CHK_LIV.Output.SET_TME] != null)
                        {
                            mMainActivity.mCloudCmd.iCloudCmd_GetUpdateInfo();
                            if (CloudDataStruct.CloudData_01.sdata_out[Cloud_01_QRY_UPD.Output.SW_URL] != null)
                            {
                                sUrl = CloudDataStruct.CloudData_01.sdata_out[Cloud_01_QRY_UPD.Output.SW_URL];
                                mState = ApkUpdateState.PROC_APKUPDATE_CHECKURL;
                            }
                        }
                    }
                }
            }
        }
//        mState = ApkUpdateState.PROC_APKUPDATE_CHECKURL;     //for test
    }

    private void vProc_ApkUpdate_CheckUrl()
    {
//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func=" + "vProc_ApkUpdate_CheckUrl, iretry_count = " + iretry_count);

        try {
            URL mUrl = new URL(sUrl);
            HttpURLConnection mHttpurlconnection = (HttpURLConnection) mUrl.openConnection();
            mHttpurlconnection.setRequestProperty("User-agent", "IE/6.0");
            mHttpurlconnection.setReadTimeout(1000);
            mHttpurlconnection.setConnectTimeout(1000);
            mHttpurlconnection.connect();

            switch (mHttpurlconnection.getResponseCode()){
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:    //504   網址逾時
                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 504" + sUrl);
                    mState = ApkUpdateState.PROC_INIT;
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:          //503   網址禁止
                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 503" + sUrl);
                    mState = ApkUpdateState.PROC_INIT;
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:     //500   網址錯誤或不存在
                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 500" + sUrl);
                    mState = ApkUpdateState.PROC_INIT;
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:          //404   網址錯誤
                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 404" + sUrl);
                    mState = ApkUpdateState.PROC_INIT;
                    break;
                case HttpURLConnection.HTTP_OK:                 //Success
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : Success " + Inet4Address.getByName(mUrl.getHost()) + ":" + mHttpurlconnection.getResponseCode());
                    if(iretry_count > 0)
                    {
                        iretry_count = 0;
                        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
                    }
                    mState = ApkUpdateState.PROC_APKUPDATE_CHECKIDLE;
                    break;
            }
        }catch(MalformedURLException e)
        {
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 網址格式錯誤" + sUrl);
            mState = ApkUpdateState.PROC_INIT;
        }catch (Exception e)
        {
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 連線異常" + ++iretry_count);
            if(iretry_count > 10)
            {
                mState = ApkUpdateState.PROC_APKUPDATE_DOWNLOADFAIL;
            }
        }
    }

    private void vProc_ApkUpdate_DownloadFail()
    {
        String stitle01 = LanguageData.s_get_string(mContext, R.string.connection_lost);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.data_upload_unsuccessful);

        Dialog_UI_Info.v_tist_Dialog(R.drawable.wifi_disconect_icon, -1, stitle01, null, sinfo01, null, null, ImageView.ScaleType.CENTER_INSIDE);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_CLOUD_UPDATE_FAIL02);

        mState = ApkUpdateState.PROC_INIT;
    }

    private void vProc_ApkUpdate_CheckIdle()
    {
        icount++;

        if(!bCheckInExercise())
        {
            if (!bCheckLogin())
            {
//                if(icount % 1000 == 0) {
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : CHECK_INTERACTION_300_SEC " + GlobalData.lGetInteractionDiffSec());
//                }
                if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_300_SEC))
                {
                    mState = ApkUpdateState.PROC_APKUPDATE_SHOWDIALOG;
                    icount = 0;
                }
            }
        }
    }

    private void vProc_ApkUpdate_ShowDialog()
    {
//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func=" + "vProc_ApkUpdate_ShowUpdate");

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_APKUPDATE,R.drawable.update, R.string.apkupdate_title,R.string.apkupdate_info);
            }
        });
        mState = ApkUpdateState.PROC_APKUPDATE_DOWNLOADFILE;
    }

    private void vProc_ApkUpdate_DownloadFile()
    {
//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func=" + "vProc_ApkUpdate_DownloadFile");

        sRtxUpdateFile = sUrl.split("/")[sUrl.split("/").length-1];
        sRtxUpdateApkPath = sStoragePath + sRtxUpdateDir + sRtxUpdateFile;

        vRtxUpdate_DelFile(sStoragePath + sRtxUpdateDir);

        request = new DownloadManager.Request(Uri.parse(sUrl));

        request.setDestinationInExternalPublicDir(sRtxUpdateDir, sRtxUpdateFile);

        downloadId = manager.enqueue(request);

        mState = ApkUpdateState.PROC_APKUPDATE_WAITDOWNLOAD;

//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : downloadId=" + downloadId);
    }

    private void vProc_ApkUpdate_WaitDownload()
    {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cur = manager.query(query);
        int index = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);

        icount++;

        if (cur != null)
        {
            if (cur.moveToFirst()) {
                if(iPreviousFileSize != cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)))
                {
                    iPreviousFileSize = cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 目標:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)) + " 大小:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) + " 進度:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 標題:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_TITLE)) + " 網址:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_URI)) + " 類型:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE))+" 描述:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION)));
                }
                if (cur.getInt(index) == DownloadManager.STATUS_SUCCESSFUL) {
                    sRtxUpdateFile = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    iPreviousFileSize = -1;
                    icount = 0;
                    mState = ApkUpdateState.PROC_APKUPDATE_CHECKFILE;
                }

                if(cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) > 0)
                {
                    if(cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)) == null)
                    {
//                        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : [下載失敗！]目標:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)) + " 大小:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) + " 進度:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                        icount = 0;
                        mState = ApkUpdateState.PROC_EXIT;
                    }
                }

                //Timeout 3分鐘
                if(icount % 30000 == 0)
                {
                    if(cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) == 0)
                    {
//                        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : [下載失敗！]目標:" + cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)) + " 大小:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) + " 進度:" + cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                        icount = 0;
                        mState = ApkUpdateState.PROC_EXIT;
                    }
                }
            }
            else
            {
            }
        }
        cur.close();

        //wifi disconnect
        if(Dialog_UI_Info.iDialogUiInfo_GetWifiState() == 1)
        {
            icount = 0;
            mState = ApkUpdateState.PROC_EXIT;
        }
    }

    private void vProc_ApkUpdate_CheckFile()
    {
        //{sExtension},{sMimetype}
        //{apk},{application/vnd.android.package-archive}
        //{zip},{application/zip}

        icount++;

        String sExtension = MimeTypeMap.getFileExtensionFromUrl(sRtxUpdateApkPath);
        String sMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(sExtension);

        switch (sExtension)
        {
            case "apk":
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : file type=[" + sExtension+ "][" + sMimeType + "]");
                mState = ApkUpdateState.PROC_APKUPDATE_CHECKSERVICE;
                icount = 0;
                break;
            case "zip":
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : file type=[" + sExtension+ "][" + sMimeType + "]");
//                mState = ApkUpdateState.PROC_APKUPDATE_UNZIP;
                mState = ApkUpdateState.PROC_APKUPDATE_WAITUNZIP;
                icount = 0;
                break;
            default:
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : file type=[" + sExtension+ "][" + sMimeType + "]");
                mState = ApkUpdateState.PROC_EXIT;
                break;
        }

        if(icount > 10)
        {
            mState = ApkUpdateState.PROC_EXIT;
            icount = 0;
        }
    }

    //目前只支援解開一個壓縮檔
    private void vProc_ApkUpdate_WaitUnzip()
    {
        if(icount == 0)
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func = vProc_ApkUpdate_WaitUnzip");
            new Thread(rProc_ApkUpdate_UnzipThread).start();
        }

        icount++;

        if(icount > 3000)
        {
            mState = ApkUpdateState.PROC_EXIT;
            icount = 0;
        }
    }

    //目前只支援解開一個壓縮檔
    private void vProc_ApkUpdate_Unzip(){
        if(icount == 0){
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func = vProc_ApkUpdate_Unzip");
        }
        bufferedOutputStream = null;
        icount++;
        try{
            fileInputStream=new FileInputStream(sRtxUpdateApkPath);
            mState=ApkUpdateState.PROC_APKUPDATE_ZIPENTRY;
            icount=0;
        }catch(FileNotFoundException e){
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 檔案不存在");
        }
        if(icount > 10){
            mState = ApkUpdateState.PROC_EXIT;
            icount = 0;
        }
    }

    private void vProc_ApkUpdate_ZipEntry()
    {
        if(icount == 0)
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : new ZipInputStream");
            zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));
        }

        icount++;

        try
        {
            if((zipEntry = zipInputStream.getNextEntry()) != null)
            {
                sRtxUpdateFile = zipEntry.getName();
                sRtxUpdateApkPath = sStoragePath + sRtxUpdateDir + sRtxUpdateFile;

                file = new File(sRtxUpdateApkPath);

                if (file.exists())
                {
                    file.delete();
                }

                if (zipEntry.isDirectory())
                {
                    file.mkdirs();
                }
                else
                {
                    icount = 0;
                    mState = ApkUpdateState.PROC_APKUPDATE_CHECKUNZIP;
                }
            }
            else
            {
                zipInputStream.close();

                mState = ApkUpdateState.PROC_APKUPDATE_CHECKFILE;
                icount = 0;
            }
        }catch (IOException e)
        {}
    }

    private void vProc_ApkUpdate_CheckUnzip()
    {

        if(icount == 0)
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func = vProc_ApkUpdate_CheckUnzip");

            try {
                fileOutputStream = new FileOutputStream(file);
            }catch (FileNotFoundException e)
            {}

            bufferedOutputStream = new BufferedOutputStream(fileOutputStream, iBufferSize);
        }

        icount++;

        try {

            if((iBufferCount = zipInputStream.read(buffer, 0, iBufferSize)) != -1)
            {
                bufferedOutputStream.write(buffer, 0, iBufferCount);
            }
            else
            {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                if(zipInputStream != null)
                {
                    zipInputStream.close();
                }
                mState = ApkUpdateState.PROC_APKUPDATE_CHECKFILE;
                icount = 0;
            }
        }catch (IOException e)
        {

        }
    }

    private void vProc_ApkUpdate_CheckService()
    {
//        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func= vProc_ApkUpdate_CheckService");
        if(bApkUpdateProc_CheckService())
        {
            mState = ApkUpdateState.PROC_APKUPDATE_STARTSERVICE;
        }
        else
        {
            mState = ApkUpdateState.PROC_APKUPDATE_INSTALLAPK;
        }
    }

    private void vProc_ApkUpdate_StartService()
    {
        vApkUpdateProc_StartService();

        mState = ApkUpdateState.PROC_APKUPDATE_INSTALLAPK;
    }

    //方法体可以在任何地方可套用，不用修改任何东东哦！
    private void vProc_ApkUpdate_InstallApk( ){
        String[] args = { "pm", "install", "-rdt", sRtxUpdateApkPath};
        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : cmd=" + args[0]+" "+args[1]+" "+args[2]+" "+args[3]);
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write("/n".getBytes());
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        mState = ApkUpdateState.PROC_EXIT;

        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : REPLACED END");
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.dismissInfoDialog();
            }
        });

        mState = ApkUpdateState.PROC_INIT;
        mNextState = ApkUpdateState.PROC_NULL;
    }

    private void vProc_Idle()
    {
        if(mNextState != ApkUpdateState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = ApkUpdateState.PROC_NULL;
        }
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

//        if(tempState != mState)
//        {
//            Log.e("Jerry", "[ApkUpdate] mState = " + mState);
//            tempState = mState;
//        }

        switch( mState )
        {
            case PROC_APKUPDATE_INIT                    :{   vProc_Init();                          break;  }
            case PROC_APKUPDATE_UPDATE                  :{   vProc_ApkUpdate_Update();              break;  }
            case PROC_APKUPDATE_CHECKUPDATE             :{   vProc_ApkUpdate_CheckUpdate();         break;  }
            case PROC_APKUPDATE_CHECKURL                :{   vProc_ApkUpdate_CheckUrl();            break;  }
            case PROC_APKUPDATE_DOWNLOADFAIL            :{   vProc_ApkUpdate_DownloadFail();        break;  }
            case PROC_APKUPDATE_CHECKIDLE               :{   vProc_ApkUpdate_CheckIdle();           break;  }
            case PROC_APKUPDATE_SHOWDIALOG              :{   vProc_ApkUpdate_ShowDialog();          break;  }
            case PROC_APKUPDATE_DOWNLOADFILE            :{   vProc_ApkUpdate_DownloadFile();        break;  }
            case PROC_APKUPDATE_WAITDOWNLOAD            :{   vProc_ApkUpdate_WaitDownload();        break;  }
            case PROC_APKUPDATE_CHECKFILE               :{   vProc_ApkUpdate_CheckFile();           break;  }
            case PROC_APKUPDATE_WAITUNZIP               :{   vProc_ApkUpdate_WaitUnzip();           break;  }
            case PROC_APKUPDATE_UNZIP                   :{   vProc_ApkUpdate_Unzip();               break;  }
            case PROC_APKUPDATE_ZIPENTRY                :{   vProc_ApkUpdate_ZipEntry();            break;  }
            case PROC_APKUPDATE_CHECKUNZIP              :{   vProc_ApkUpdate_CheckUnzip();          break;  }
            case PROC_APKUPDATE_CHECKSERVICE            :{   vProc_ApkUpdate_CheckService();        break;  }
            case PROC_APKUPDATE_STARTSERVICE            :{   vProc_ApkUpdate_StartService();        break;  }
            case PROC_APKUPDATE_INSTALLAPK              :{   vProc_ApkUpdate_InstallApk();          break;  }


            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Init();                          break;  }
        }
    }

    /* ------------------------------------------------------------------------ */

    public Runnable rProc_ApkUpdate_UnzipThread = new Runnable() {
        public void run()
        {
//            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func = vProc_ApkUpdate_Unzip");

            bufferedOutputStream = null;

            try
            {
                fileInputStream = new FileInputStream(sRtxUpdateApkPath);

                zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));

                while ((zipEntry = zipInputStream.getNextEntry()) != null)
                {
                    sRtxUpdateFile = zipEntry.getName();
                    sRtxUpdateApkPath = sStoragePath + sRtxUpdateDir + sRtxUpdateFile;

                    file = new File(sRtxUpdateApkPath);

                    if(file.exists())
                    {
                        file.delete();
                    }

                    if(zipEntry.isDirectory())
                    {
                        file.mkdirs();
                    }
                    else
                    {
                        fileOutputStream = new FileOutputStream(file);
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream, iBufferSize);

                        while ((iBufferCount = zipInputStream.read(buffer, 0, iBufferSize)) != -1)
                        {
                            bufferedOutputStream.write(buffer, 0, iBufferCount);
                        }

                        if(bufferedOutputStream != null)
                        {
                            bufferedOutputStream.flush();
                            bufferedOutputStream.close();
                            bufferedOutputStream = null;
                        }

                        if(mState == ApkUpdateState.PROC_APKUPDATE_WAITUNZIP)
                        {
                            mState = ApkUpdateState.PROC_APKUPDATE_CHECKFILE;
                            icount = 0;
                        }
                    }
                }

                if(zipInputStream != null)
                {
                    zipInputStream.close();
                    zipInputStream = null;
                }
            }catch (FileNotFoundException e)
            {
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 檔案不存在");
            }catch (IOException e)
            {
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : 錯誤");
            }
        }
    };


    public boolean bApkUpdateProc_CheckService()
    {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> processInfos = manager.getRunningServices(100);
        if (processInfos.size() > 0) {
            for (int i = 0; i < processInfos.size(); i++) {
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : "+i+"====> ["+processInfos.get(i).service.getClassName()+"]");
                if (processInfos.get(i).service.getClassName().equals("com.rtx.service.MainService")) {
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : = com.rtx.service.MainService");
                    return false;
                } else {
                }
            }
        }
        return true;
    }

    public void vApkUpdateProc_StartService()
    {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        try {
            Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : start = com.rtx.service.MainActivity");
            mIntent.setComponent(new ComponentName("com.rtx.service", "com.rtx.service.MainActivity"));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(mIntent);
        } catch (ActivityNotFoundException a) {
            a.getMessage();
        }
    }

    public void vApkUpdateProc_InstallApk(Context context, String sPathname)
    {
        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : func = vProc_ApkUpdate_InstallApk");

//        Intent install = new Intent(Intent.ACTION_VIEW);
        Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadFileUri = Uri.fromFile(new File(sPathname));

        if(downloadFileUri != null)
        {
            install.setDataAndType(downloadFileUri,"application/vnd.android.package-archive");
//            install.setData(downloadFileUri);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE,true);
            install.putExtra(Intent.EXTRA_RETURN_RESULT,true);
            install.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME,context.getApplicationInfo().packageName);

            context.startActivity(install);
        }
        else
        {
        }
    }
}