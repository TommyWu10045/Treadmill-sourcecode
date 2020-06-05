package com.rtx.treadmill.RtxBaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainProcBike;
import com.rtx.treadmill.MainProcTreadmill;
import com.rtx.treadmill.RtxShare.Service_VirtualButton;
import com.rtx.treadmill.RtxShare.Share_Facebook;
import com.rtx.treadmill.RtxShare.Share_Ig;
import com.rtx.treadmill.RtxShare.Share_Twitter;
import com.rtx.treadmill.RtxShare.Share_Weibo;
import com.rtx.treadmill.RtxTools.Rtx_Bitmap;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxView.RtxDialog_ApkUpdate_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_DeleteFinish_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Delete_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Fan_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Home_Info_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Icon_Title_Info_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Info_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Language_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Login_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Logout_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_QuickLoginFail_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_QuickLogin_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_RegExit_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Share_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_TargetCloseInfo_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_TargetClose_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_TargetEnd_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Timeout_Reset;
import com.rtx.treadmill.RtxView.RtxDialog_TitleAndInfo_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_Tutorial_Layout;
import com.rtx.treadmill.RtxView.RtxDialog_YesNo_Layout;
import com.rtx.treadmill.RtxView.RtxFrameLayout;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;
import static android.view.View.combineMeasuredStates;

/**
 * Created by jerry on 2017/2/10.
 */

public class Rtx_BaseActivity extends Activity{
    private MainActivity mMainActivity;
    private int iTimeoutResetPreviousSec = -1;
    DisplayMetrics metrics;
    public RtxFrameLayout frameLayout_ContentView;
    public RtxFrameLayout frameLayout_Base;
    public RtxFrameLayout frameLayout_Dialog;
    public RtxFrameLayout[] frameLayout_Exercise;
    public MainProcTreadmill mMainProcTreadmill = null;
    public MainProcBike mMainProcBike = null;
    public RtxDialog_TitleAndInfo_Layout            dialogLayout_TitleAndInfo;
    public RtxDialog_Login_Layout                   dialogLayout_Login;
    public RtxDialog_Delete_Layout                  dialogLayout_Delete;
    public RtxDialog_TargetClose_Layout             dialogLayout_TargetClose;
    public RtxDialog_RegExit_Layout                 dialogLayout_RegExit;
    public RtxDialog_TargetCloseInfo_Layout         dialogLayout_TargetCloseInfo;
    public RtxDialog_TargetEnd_Layout               dialogLayout_TargetEnd;
    public RtxDialog_DeleteFinish_Layout            dialogLayout_DeleteFinish;
    public RtxDialog_Info_Layout                    dialogLayout_Info;
    public RtxDialog_YesNo_Layout                   dialogLayout_YesNo;
    public RtxDialog_Icon_Title_Info_Layout         dialogLayout_IconTitleInfo;
    public RtxDialog_Home_Info_Layout               dialogLayout_HomeInfo;
    public RtxDialog_QuickLogin_Layout              dialogLayout_QuickLogin;
    public RtxDialog_QuickLoginFail_Layout          dialogLayout_QuickLoginFail;
    public RtxDialog_Share_Layout                   dialogLayout_Share;
    public RtxDialog_Timeout_Reset                  dialogLayout_TimeoutReset;
    public RtxDialog_Tutorial_Layout                dialogLayout_Tutorial_Layout;
    public RtxDialog_Language_Layout                dialogLayout_Language;
    public RtxDialog_ApkUpdate_Layout               dialogLayout_ApkUpdate;
    public RtxDialog_Fan_Layout                     dialogLayout_Fan;
    public RtxDialog_Logout_Layout                  dialogLayout_Logout;
    private Context mContext = this;
    private ProgressBar progressBar;
    private Timer timeLogout;

    public Rtx_BaseActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void base_init(){
        init_window();
        init_FrameLayout();
        init_Layout();
    }

    private void init_window(){
        int ival;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ival = EngSetting.i_Get_Screen_Rotation();
        if(ival == EngSetting.iROTATION_00){ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); }
        else if(ival == EngSetting.iROTATION_90){ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); }
        else if(ival == EngSetting.iROTATION_180){ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT); }
        else if(ival == EngSetting.iROTATION_270){ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE); }
        else{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); } //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(progressBar == null){
            progressBar = new ProgressBar(this);
            addProgressBarToLayout(progressBar,300,300);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void init_FrameLayout(){
        int iLoop;
        {
            frameLayout_ContentView = new RtxFrameLayout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
            frameLayout_ContentView.setLayoutParams(lparam);
            this.setContentView(frameLayout_ContentView);
        }
        {
            frameLayout_Base = new RtxFrameLayout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
            frameLayout_Base.setLayoutParams(lparam);
            frameLayout_Base.setBackgroundColor(Common.Color.background);
        }
        {
            frameLayout_Dialog = new RtxFrameLayout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
            frameLayout_Dialog.setLayoutParams(lparam);
            frameLayout_Dialog.setBackgroundColor(Common.Color.transparent);
            frameLayout_Dialog.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                }
            });
            frameLayout_Dialog.setClickable(false);
        }


        if(frameLayout_Exercise == null){
            frameLayout_Exercise = new RtxFrameLayout[ExerciseData.layer_max];
            for(iLoop=0; iLoop < ExerciseData.layer_max; iLoop++){
                frameLayout_Exercise[iLoop] = new RtxFrameLayout(this);
                FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
                frameLayout_Exercise[iLoop].setLayoutParams(lparam);
                frameLayout_Exercise[iLoop].setBackgroundColor(Common.Color.transparent);
            }
        }
        frameLayout_ContentView.addView(frameLayout_Base);
        frameLayout_ContentView.addView(frameLayout_Dialog);
        for(iLoop=0; iLoop < ExerciseData.layer_max; iLoop++){ frameLayout_ContentView.addView(frameLayout_Exercise[iLoop]); }
    }

    private void init_Layout(){

    }

    protected void addProgressBarToLayout(ProgressBar progressBar , int iWidth , int iHeight){
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(mLayoutParams);
        addView(progressBar);
    }

    public void setBackgroundColor(int iColor)
    {
        frameLayout_Base.setBackgroundColor(iColor);
    }

    public void removeAllViews(){
        try{ frameLayout_Base.removeAllViews(); }
        catch(Exception e){ Rtx_Log.Loge(e.getMessage()); }
    }

    public void addView(View view){
        try{ frameLayout_Base.addView(view); }
        catch(Exception e){ Rtx_Log.Loge(e.getMessage()); }
    }

    public void removeExerciseViews(int ilayer){
        try{
            if(ilayer < ExerciseData.layer_max){ frameLayout_Exercise[ilayer].removeAllViews(); }
            else{
                int iLoop;
                for(iLoop=0; iLoop < ExerciseData.layer_max; iLoop++){ frameLayout_Exercise[ilayer].removeAllViews(); }
            }
        }catch(Exception e){ Rtx_Log.Loge(e.getMessage()); }
    }

    public void addExerciseView(int ilayer, View view){
        try{ if(ilayer < ExerciseData.layer_max){ frameLayout_Exercise[ilayer].addView(view); }}
        catch (Exception e){ Rtx_Log.Loge(e.getMessage()); }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////Dialog Info Layout//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final public static int DIALOG_TYPE_LOGIN               =   0;
    final public static int DIALOG_TYPE_TITLE_INFO          =   1;
    final public static int DIALOG_TYPE_ONLY_INFO           =   2;
    final public static int DIALOG_TYPE_DELETE              =   3;
    final public static int DIALOG_TYPE_DELETE_FINISH       =   4;
    final public static int DIALOG_TYPE_TARGET_CLOSE        =   5;
    final public static int DIALOG_TYPE_TARGET_CLOSE_INFO   =   6;
    final public static int DIALOG_TYPE_TARGET_END          =   7;
    final public static int DIALOG_TYPE_YES_NO              =   8;
    final public static int DIALOG_TYPE_ICON_TITLE_INFO     =   9;
    final public static int DIALOG_TYPE_HOME_INFO           =   10;
    final public static int DIALOG_TYPE_QUICK_LOGIN         =   11;
    final public static int DIALOG_TYPE_QUICK_LOGIN_FAIL    =   12;
    final public static int DIALOG_TYPE_REG_EXIT            =   13;
    final public static int DIALOG_TYPE_SHARE               =   14;
    final public static int DIALOG_TYPE_TIMEOUT_RESET       =   15;
    final public static int DIALOG_TYPE_TUTORIAL            =   16;
    final public static int DIALOG_TYPE_LANGUAGE            =   17;
    final public static int DIALOG_TYPE_APKUPDATE           =   18;
    final public static int DIALOG_TYPE_FAN                 =   19;
    final public static int DIALOG_TYPE_LOGOUT              =   20;

    public void showInfoDialog(int iType , Object obj1 , Object obj2){
        showInfoDialog(iType,obj1,obj2,null);
    }

    public void showInfoDialog(int iType , Object obj1 , Object obj2 , Object obj3){
        if(iType == DIALOG_TYPE_LOGIN){ vShowDialog_Login(); }
        else if(iType == DIALOG_TYPE_TITLE_INFO){ vShowDialog_TitleAndInfo((int)obj1 , (int)obj2); }
        else if(iType == DIALOG_TYPE_ONLY_INFO){ vShowDialog_Info((int)obj2); }
        else if(iType == DIALOG_TYPE_DELETE){ vShowDialog_Delete((String)obj1); }
        else if(iType == DIALOG_TYPE_DELETE_FINISH){ vShowDialog_DeleteFinish(); }
        else if(iType == DIALOG_TYPE_TARGET_CLOSE){ vShowDialog_TargetClose(); }
        else if(iType == DIALOG_TYPE_TARGET_CLOSE_INFO){ vShowDialog_TargetCloseInfo((CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE)obj1,(boolean)obj2); }
        else if(iType == DIALOG_TYPE_TARGET_END){ vShowDialog_TargetEnd((CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE)obj1); }
        else if(iType == DIALOG_TYPE_YES_NO){ vShowDialog_YesNo((String) obj1); }
        else if(iType == DIALOG_TYPE_ICON_TITLE_INFO){ vShowDialog_Icon_Title_Info((int)obj1 , (int)obj2 , (int)obj3); }
        else if(iType == DIALOG_TYPE_HOME_INFO){ vShowDialog_HomeInfo((UiDataStruct.HOME_USER_INFO)obj1); }
        else if(iType == DIALOG_TYPE_QUICK_LOGIN){ vShowDialog_QuickLogin(); }
        else if(iType == DIALOG_TYPE_QUICK_LOGIN_FAIL){ vShowDialog_QuickLoginFail(); }
        else if(iType == DIALOG_TYPE_REG_EXIT){ vShowDialog_RegExit(); }
        else if(iType == DIALOG_TYPE_SHARE){ vShowDialog_Share((MainActivity) obj1,(FrameLayout) obj2); }
        else if(iType == DIALOG_TYPE_TIMEOUT_RESET){ vShowDialog_TimeoutReset((int)obj1); }
        else if(iType == DIALOG_TYPE_TUTORIAL){ vShowDialog_Tutotrial(); }
        else if(iType == DIALOG_TYPE_LANGUAGE){ vShowDialog_Language(); }
        else if(iType == DIALOG_TYPE_APKUPDATE){ vShowDialog_ApkUpdate((int)obj1 , (int)obj2 , (int)obj3); }
        else if(iType == DIALOG_TYPE_FAN){ vShowDialog_Fan((int)obj1 , (int)obj2 , (int)obj3); }
        else if(iType == DIALOG_TYPE_LOGOUT){ vShowDialog_Logout(); }
        v_frameLayout_set();
    }

    public void showInfoDialog(int iType , int iResId_Title , int iResId_Contents, String info_tag){
        if(info_tag == null){ showInfoDialog(iType, iResId_Title, iResId_Contents); }
        else{
            if(iType == DIALOG_TYPE_LOGIN){ vShowDialog_Login(); }
            else if(iType == DIALOG_TYPE_TITLE_INFO){ vShowDialog_TitleAndInfo(iResId_Title, iResId_Contents, info_tag); }
            else if(iType == DIALOG_TYPE_ONLY_INFO){ vShowDialog_Info(iResId_Contents, info_tag); }
            v_frameLayout_set();
        }
    }

    ////////////////////////////////////////////////////
    //Login Dialog
    private void vShowDialog_Login(){
        int icolor_Login = Common.Color.login_button_yellow;
        int icolor_SignUp = Common.Color.login_button_green;
        int icolor_disable = Common.Color.login_button_gray;

        if(dialogLayout_Login == null){
            dialogLayout_Login = new RtxDialog_Login_Layout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_Login.setLayoutParams(lparam);

            dialogLayout_Login.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }else{
            dialogLayout_Login.onDestroy();
            dialogLayout_Login.display();
        }

        if(!ExerciseData.b_is_exercising()){
            addViewToLayout(dialogLayout_Login, frameLayout_Dialog, 1002, 583);
            dialogLayout_Login.fillerTextView_Login.setEnabled(true);
            dialogLayout_Login.fillerTextView_SignUp.setEnabled(true);
            dialogLayout_Login.fillerTextView_Ok.setEnabled(false);
            dialogLayout_Login.fillerTextView_Login.setBackgroundColor(icolor_Login);
            dialogLayout_Login.fillerTextView_SignUp.setBackgroundColor(icolor_SignUp);
            dialogLayout_Login.fillerTextView_Ok.setBackgroundColor(Common.Color.red_1);
            dialogLayout_Login.fillerTextView_Ok.setVisibility(GONE);
            dialogLayout_Login.fillerTextView_Login.setVisibility(View.VISIBLE);
            dialogLayout_Login.fillerTextView_SignUp.setVisibility(View.VISIBLE);
            dialogLayout_Login.fillerTextView_SignUp.bringToFront();
            dialogLayout_Login.fillerTextView_Login.bringToFront();
        }else{
            addViewToLayout(dialogLayout_Login, frameLayout_Dialog, -1, 30, 1002, 583);
            dialogLayout_Login.fillerTextView_Login.setEnabled(false);
            dialogLayout_Login.fillerTextView_SignUp.setEnabled(false);
            dialogLayout_Login.fillerTextView_Ok.setEnabled(false);
            dialogLayout_Login.fillerTextView_Login.setBackgroundColor(icolor_disable);
            dialogLayout_Login.fillerTextView_Login.setTextColor(Common.Color.white);
            dialogLayout_Login.fillerTextView_SignUp.setBackgroundColor(icolor_disable);
            dialogLayout_Login.fillerTextView_SignUp.setTextColor(Common.Color.white);
            dialogLayout_Login.fillerTextView_Ok.setBackgroundColor(Common.Color.red_1);
            //dialogLayout_Login.fillerTextView_Login.setVisibility(GONE);
            //dialogLayout_Login.fillerTextView_SignUp.setVisibility(GONE);
            dialogLayout_Login.fillerTextView_Ok.setVisibility(GONE);
            //dialogLayout_Login.fillerTextView_Ok.bringToFront();
            //dialogLayout_Login.fillerTextView_Ok.setAlpha((float) 0.5);
            dialogLayout_Login.fillerTextView_SignUp.bringToFront();
            dialogLayout_Login.fillerTextView_SignUp.setAlpha((float) 0.1);
            dialogLayout_Login.fillerTextView_Login.bringToFront();
            dialogLayout_Login.fillerTextView_Login.setAlpha((float) 0.1);
        }
    }

    ////////////////////////////////////////////////////
    //Logout Dialog
    private void vShowDialog_Logout(){
        int icolor_Login = Common.Color.red_1;
        int icolor_SignUp = Common.Color.white;
        int icolor_disable = Common.Color.login_button_gray;

        if(dialogLayout_Logout == null){
            dialogLayout_Logout = new RtxDialog_Logout_Layout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_Logout.setLayoutParams(lparam);
            dialogLayout_Logout.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeLogout.cancel();
                    dismissInfoDialog();
                }
            });
        }else{
            dialogLayout_Logout.onDestroy();
            dialogLayout_Logout.display();
        }

        if(!ExerciseData.b_is_exercising()){
            addViewToLayout(dialogLayout_Logout, frameLayout_Dialog, 1002, 583);
            dialogLayout_Logout.fillerTextView_Login.setEnabled(true);
            dialogLayout_Logout.fillerTextView_SignUp.setEnabled(true);
            dialogLayout_Logout.fillerTextView_Login.setBackgroundColor(icolor_Login);
            dialogLayout_Logout.fillerTextView_SignUp.setBackgroundColor(icolor_SignUp);
        }else{
            addViewToLayout(dialogLayout_Logout, frameLayout_Dialog, -1, 30, 1002, 583);
            dialogLayout_Logout.fillerTextView_Login.setEnabled(false);
            dialogLayout_Logout.fillerTextView_SignUp.setEnabled(false);
            dialogLayout_Logout.fillerTextView_Login.setBackgroundColor(icolor_disable);
            dialogLayout_Logout.fillerTextView_SignUp.setBackgroundColor(icolor_disable);
        }
        timeLogout=new Timer();
        timeLogout.schedule(new TimerTask(){
            public void run(){
                //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TIMEOUT_RESET, 60, null);
                dialogLayout_Logout.imageView_Close.performClick();
                dismissInfoDialog();
            }
        }, 30000);
    }

    public void vShowDialog_YesNo(String sText)
    {
        if(dialogLayout_YesNo == null)
        {
            dialogLayout_YesNo = new RtxDialog_YesNo_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_YesNo.setLayoutParams(lparam);

            dialogLayout_YesNo.fillerTextView_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_YesNo.onDestroy();
            dialogLayout_YesNo.display();
        }

        dialogLayout_YesNo.vSetDesciption(sText);
        addViewToLayout(dialogLayout_YesNo,frameLayout_Dialog,1002,583);
    }

    //Delete Dialog
    public void vShowDialog_Delete(String sText)
    {
        if(dialogLayout_Delete == null)
        {
            dialogLayout_Delete = new RtxDialog_Delete_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_Delete.setLayoutParams(lparam);

            dialogLayout_Delete.fillerTextView_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_Delete.onDestroy();
            dialogLayout_Delete.display();
        }

        addViewToLayout(dialogLayout_Delete,frameLayout_Dialog,1002,583);
        dialogLayout_Delete.vSetDesciption(sText);
    }

    //Delete Finish Dialog
    public void vShowDialog_DeleteFinish()
    {
        if(dialogLayout_DeleteFinish == null)
        {
            dialogLayout_DeleteFinish = new RtxDialog_DeleteFinish_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_DeleteFinish.setLayoutParams(lparam);
        }
        else
        {
            dialogLayout_DeleteFinish.onDestroy();
            dialogLayout_DeleteFinish.display();
        }

        addViewToLayout(dialogLayout_DeleteFinish,frameLayout_Dialog,1002,583);
    }

    //Target Close Dialog
    public void vShowDialog_TargetClose()
    {
        if(dialogLayout_TargetClose == null)
        {
            dialogLayout_TargetClose = new RtxDialog_TargetClose_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_TargetClose.setLayoutParams(lparam);

            dialogLayout_TargetClose.fillerTextView_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_TargetClose.onDestroy();
            dialogLayout_TargetClose.display();
        }

        addViewToLayout(dialogLayout_TargetClose,frameLayout_Dialog,1002,583);
    }

    public void vShowDialog_RegExit()
    {
        if(dialogLayout_RegExit == null)
        {
            dialogLayout_RegExit = new RtxDialog_RegExit_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_RegExit.setLayoutParams(lparam);

            dialogLayout_RegExit.fillerTextView_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_RegExit.onDestroy();
            dialogLayout_RegExit.display();
        }

        addViewToLayout(dialogLayout_RegExit,frameLayout_Dialog,1002,583);
    }

    //Target Close Info Dialog
    public void vShowDialog_TargetCloseInfo(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close, boolean bHalfWay)
    {
        if(dialogLayout_TargetCloseInfo == null)
        {
            dialogLayout_TargetCloseInfo = new RtxDialog_TargetCloseInfo_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 667);
            dialogLayout_TargetCloseInfo.setLayoutParams(lparam);

            dialogLayout_TargetCloseInfo.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_TargetCloseInfo.onDestroy();
            dialogLayout_TargetCloseInfo.display();
        }

        addViewToLayout(dialogLayout_TargetCloseInfo,frameLayout_Dialog,1002,667);
        dialogLayout_TargetCloseInfo.setGoalCloseInfo(goal_Close,bHalfWay);

//        if(ExerciseData.b_is_exercising()) {
//            dialogLayout_TargetCloseInfo.setScaleX(0.91f);
//            dialogLayout_TargetCloseInfo.setScaleY(0.91f);
//        }
//        else
//        {
//            dialogLayout_TargetCloseInfo.setScaleX(1f);
//            dialogLayout_TargetCloseInfo.setScaleY(1f);
//        }
    }

    //Target End Dialog
    public void vShowDialog_TargetEnd(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close)
    {
        if(dialogLayout_TargetEnd == null)
        {
            dialogLayout_TargetEnd = new RtxDialog_TargetEnd_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 667);
            dialogLayout_TargetEnd.setLayoutParams(lparam);

//            dialogLayout_TargetEnd.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismissInfoDialog();
//                }
//            });
        }
        else
        {
            dialogLayout_TargetEnd.onDestroy();
            dialogLayout_TargetEnd.display();
        }

        addViewToLayout(dialogLayout_TargetEnd,frameLayout_Dialog,1002,667);
        dialogLayout_TargetEnd.setGoalCloseInfo(goal_Close);

//        if(ExerciseData.b_is_exercising()) {
//            dialogLayout_TargetEnd.setScaleX(0.91f);
//            dialogLayout_TargetEnd.setScaleY(0.91f);
//        }
//        else
//        {
//            dialogLayout_TargetEnd.setScaleX(1f);
//            dialogLayout_TargetEnd.setScaleY(1f);
//        }
    }

    //Title Info Dialog
    private void vAddDialog_TitleAndInfo()
    {
        if(dialogLayout_TitleAndInfo == null)
        {
            dialogLayout_TitleAndInfo = new RtxDialog_TitleAndInfo_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1115, 683);
            dialogLayout_TitleAndInfo.setLayoutParams(lparam);

            dialogLayout_TitleAndInfo.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
//        else
//        {
//            dialogLayout_TitleAndInfo.onDestroy();
//            dialogLayout_TitleAndInfo.display();
//        }

        addViewToLayout(dialogLayout_TitleAndInfo,frameLayout_Dialog,1115,683);
    }

    private void vShowDialog_TitleAndInfo(int iResId_Title , int iResId_Contents)
    {
        vAddDialog_TitleAndInfo();

        if(iResId_Title != -1)      { dialogLayout_TitleAndInfo.setInfoTitle(iResId_Title); }
        if(iResId_Contents != -1)   { dialogLayout_TitleAndInfo.setInfoContents(iResId_Contents); }

    }

    private void vShowDialog_TitleAndInfo(int iResId_Title , int iResId_Contents, String info_tag)
    {
        vShowDialog_TitleAndInfo(iResId_Title, iResId_Contents);

        if(info_tag != null)
        {
            dialogLayout_TitleAndInfo.setInfoContents(info_tag);
        }

    }

    //Info Dialog
    private void vAddDialog_InfoLayout()
    {
        if(dialogLayout_Info == null)
        {
            dialogLayout_Info = new RtxDialog_Info_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 583);
            dialogLayout_Info.setLayoutParams(lparam);

            dialogLayout_Info.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
//        else
//        {
//            dialogLayout_Info.onDestroy();
//            dialogLayout_Info.display();
//        }

        addViewToLayout(dialogLayout_Info,frameLayout_Dialog,1002,583);
    }

    private void vShowDialog_Icon_Title_Info(int iResId_Icon , int iResId_Title , int iResId_Contents)
    {
        if(dialogLayout_IconTitleInfo == null)
        {
            dialogLayout_IconTitleInfo = new RtxDialog_Icon_Title_Info_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1115, 709);
            dialogLayout_IconTitleInfo.setLayoutParams(lparam);

            dialogLayout_IconTitleInfo.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
//        else
//        {
//            dialogLayout_IconTitleInfo.onDestroy();
//            dialogLayout_IconTitleInfo.display();
//        }

        dialogLayout_IconTitleInfo.setInfoTitle(iResId_Title);
        dialogLayout_IconTitleInfo.setInfoContents(iResId_Contents);
        dialogLayout_IconTitleInfo.setIconResourceId(iResId_Icon);
        addViewToLayout(dialogLayout_IconTitleInfo,frameLayout_Dialog,1115,709);
    }

    private void vShowDialog_Info(int iResId_Contents)
    {
        vAddDialog_InfoLayout();

        if(iResId_Contents != -1)   { dialogLayout_Info.setInfoContents(iResId_Contents); }

    }

    private void vShowDialog_Info(int iResId_Contents, String info_tag) {
        vShowDialog_Info(iResId_Contents);
        if (info_tag != null) {
            dialogLayout_Info.setInfoContents(info_tag);
        }
    }

    private void vShowDialog_HomeInfo(UiDataStruct.HOME_USER_INFO info)
    {
        if(dialogLayout_HomeInfo == null)
        {
            dialogLayout_HomeInfo = new RtxDialog_Home_Info_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1002, 667);
            dialogLayout_HomeInfo.setLayoutParams(lparam);

            dialogLayout_HomeInfo.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(!dialogLayout_HomeInfo.bNextPage())
                    {
                        dismissInfoDialog();
                    }
                }
            });
        }
        else
        {
            dialogLayout_HomeInfo.onDestroy();
            dialogLayout_HomeInfo.display();
        }

        dialogLayout_HomeInfo.setContents(info);
        addViewToLayout(dialogLayout_HomeInfo,frameLayout_Dialog,1002,667);

    }

    private void vShowDialog_QuickLogin()
    {
        if(dialogLayout_QuickLogin == null)
        {
            dialogLayout_QuickLogin = new RtxDialog_QuickLogin_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1280, 800);
            dialogLayout_QuickLogin.setLayoutParams(lparam);
        }
        else
        {
            dialogLayout_QuickLogin.onDestroy();
            dialogLayout_QuickLogin.display();
        }

        addViewToLayout(dialogLayout_QuickLogin,frameLayout_Dialog,1280,800);
    }

    private void vShowDialog_QuickLoginFail()
    {
        if(dialogLayout_QuickLoginFail == null)
        {
            dialogLayout_QuickLoginFail = new RtxDialog_QuickLoginFail_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1280, 800);
            dialogLayout_QuickLoginFail.setLayoutParams(lparam);

            dialogLayout_QuickLoginFail.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_QuickLoginFail.onDestroy();
            dialogLayout_QuickLoginFail.display();
        }

        addViewToLayout(dialogLayout_QuickLoginFail,frameLayout_Dialog,1280,800);
    }

    private void vShowDialog_Share(final MainActivity mMainActivity, final FrameLayout layout)
    {
        if(dialogLayout_Share == null)
        {
            dialogLayout_Share = new RtxDialog_Share_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1118, 432);
            dialogLayout_Share.setLayoutParams(lparam);

            dialogLayout_Share.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });

            dialogLayout_Share.imageView_FB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar();
                    //vPrepareShare(layout);
                    GlobalData.vGlobalData_SetFBFlag(true);
                    Share_Facebook mShare_Facebook = new Share_Facebook(mMainActivity);

                    mShare_Facebook.share();
                    closeProgressBar();
                }
            });

            dialogLayout_Share.imageView_Twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar();
                    //vPrepareShare(layout);
                    GlobalData.vGlobalData_SetTWFlag(true);
                    Share_Twitter mShare_Twitter = new Share_Twitter(mMainActivity);

                    mShare_Twitter.share();
                    closeProgressBar();
                }
            });

            dialogLayout_Share.imageView_IG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar();
                    //vPrepareShare(layout);
                    GlobalData.vGlobalData_SetIGFlag(true);
                    Share_Ig mShare_Ig = new Share_Ig(mMainActivity);

                    mShare_Ig.share();
                    closeProgressBar();
                }
            });

            dialogLayout_Share.imageView_Weibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar();
                    //vPrepareShare(layout);
                    GlobalData.vGlobalData_SetWBFlag(true);
                    Share_Weibo mShare_Weibo = new Share_Weibo(mMainActivity);

                    mShare_Weibo.share();
                    closeProgressBar();
                }
            });
        }
        else
        {
            dialogLayout_Share.onDestroy();
            dialogLayout_Share.display();
        }

        vPrepareShare(layout);
        addViewToLayout(dialogLayout_Share,frameLayout_Dialog,1118,432);
    }

    private void vShowDialog_TimeoutReset(int iSec)
    {
        if(iTimeoutResetPreviousSec != iSec)
        {
            if(dialogLayout_TimeoutReset == null)
            {
                dialogLayout_TimeoutReset = new RtxDialog_Timeout_Reset(this);
                FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(932, 550);
                dialogLayout_TimeoutReset.setLayoutParams(lparam);
                dialogLayout_TimeoutReset.fillerTextView_Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iTimeoutResetPreviousSec = -1;
                        dismissInfoDialog();
                    }
                });
            }
            else
            {
                dialogLayout_TimeoutReset.onDestroy();
                dialogLayout_TimeoutReset.display();
            }

            iTimeoutResetPreviousSec = iSec;
            dialogLayout_TimeoutReset.vSetTimeVal(iSec);
            addViewToLayout(dialogLayout_TimeoutReset, frameLayout_Dialog, 932, 550);
        }
    }

    private void vShowDialog_Tutotrial()
    {
        if(dialogLayout_Tutorial_Layout == null)
        {
            dialogLayout_Tutorial_Layout = new RtxDialog_Tutorial_Layout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1280, 800);
            dialogLayout_Tutorial_Layout.setLayoutParams(lparam);

            dialogLayout_Tutorial_Layout.setOnDismissListener(new RtxDialog_Tutorial_Layout.OnDismissListener() {
                @Override
                public void onDismiss() {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_Tutorial_Layout.onDestroy();
            dialogLayout_Tutorial_Layout.display();
        }

        addViewToLayout(dialogLayout_Tutorial_Layout,frameLayout_Dialog,1280,800);
    }

    private void vShowDialog_Language()
    {
        if(dialogLayout_Language == null)
        {
            dialogLayout_Language = new RtxDialog_Language_Layout(this);
            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1196, 590);
            dialogLayout_Language.setLayoutParams(lparam);

            dialogLayout_Language.setOnSelectListener(new RtxDialog_Language_Layout.OnSelectListener() {
                @Override
                public void onSelect(int iIndex) {

                    LanguageData.v_set_language(iIndex);
                    Perf.v_Update_Perf_Language(mContext);
                    LanguageData.getDeviceLanguage(mContext);


                    if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
                    {
                        mMainProcTreadmill.homeProc.vSetNextState(com.rtx.treadmill.RtxMainFunction.Home.HomeState.PROC_INIT);
                    }
                    else
                    {
                        mMainProcBike.homeProc.vSetNextState(com.rtx.treadmill.RtxMainFunctionBike.Home.HomeState.PROC_INIT);
                    }

                    //Log.e("Jerry","Select Language = " + LanguageData.llan_array[iIndex][0]);
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_Language.onDestroy();
            dialogLayout_Language.display();
        }

        dialogLayout_Language.vSetHighlightIndex(LanguageData.ilan);
        addViewToLayout(dialogLayout_Language,frameLayout_Dialog,1196,590);
    }

    private void vShowDialog_ApkUpdate(int iResId_Icon , int iResId_Title , int iResId_Contents)
    {
        if(dialogLayout_ApkUpdate == null)
        {
            dialogLayout_ApkUpdate = new RtxDialog_ApkUpdate_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1115, 709);
            dialogLayout_ApkUpdate.setLayoutParams(lparam);

            dialogLayout_ApkUpdate.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_ApkUpdate.onDestroy();
            dialogLayout_ApkUpdate.display();
        }

        dialogLayout_ApkUpdate.setInfoTitle(iResId_Title);
        dialogLayout_ApkUpdate.setInfoContents(iResId_Contents);
        dialogLayout_ApkUpdate.setIconResourceId(iResId_Icon);
        dialogLayout_ApkUpdate.imageView_Close.setVisibility(GONE);
        addViewToLayout(dialogLayout_ApkUpdate,frameLayout_Dialog,1115,709);
    }

    private void vShowDialog_Fan(int iResId_Icon , int iResId_Title , int iResId_Contents)
    {
        if(dialogLayout_Fan == null)
        {
            dialogLayout_Fan = new RtxDialog_Fan_Layout(this);

            FrameLayout.LayoutParams lparam = new FrameLayout.LayoutParams(1280, 800);
            dialogLayout_Fan.setLayoutParams(lparam);

            dialogLayout_Fan.imageView_Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissInfoDialog();
                }
            });
        }
        else
        {
            dialogLayout_Fan.onDestroy();
            dialogLayout_Fan.display();
        }

        dialogLayout_Fan.setInfoTitle(iResId_Title);
        dialogLayout_Fan.setInfoContents(iResId_Contents);
        dialogLayout_Fan.setIconResourceId(iResId_Icon);
        dialogLayout_Fan.imageView_Close.setVisibility(GONE);
        addViewToLayout(dialogLayout_Fan,frameLayout_Dialog,1280,800);
    }

    private void vPrepareShare(FrameLayout layout)
    {
        Rtx_Bitmap.saveBitmap(Rtx_Bitmap.loadBitmapFromView(layout));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void dismissInfoDialog()
    {
        frameLayout_Dialog.setBackgroundColor(Common.Color.transparent);
        frameLayout_Dialog.removeAllViews();
        frameLayout_Dialog.setClickable(false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_frameLayout_set()
    {
        frameLayout_Dialog.setBackgroundColor(Common.Color.transparent_dialog);
        frameLayout_Dialog.setClickable(true);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addViewToLayout(View view , FrameLayout frame, int iX , int iY , int iWidth , int iHeight)
    {
        if(view == null)
        {
            Rtx_Log.Log_Error("view is null in addViewToLayoutToLayout!!!");
            return;
        }

        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iWidth,iHeight);

        if(iX == -1)
        {
            mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

        }
        else
        {
            mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            mLayoutParams.leftMargin = iX;
        }
        mLayoutParams.topMargin = iY;

        view.setLayoutParams(mLayoutParams);

        try {
            frame.addView(view);
        }catch (Exception e)
        {
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }

    public void addViewToLayout(View view , FrameLayout frame, int iWidth , int iHeight)
    {
        if(view == null)
        {
            Rtx_Log.Log_Error("view is null in addViewToLayoutToLayout!!!");
            return;
        }

        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.CENTER;
        view.setLayoutParams(mLayoutParams);

        try {
            frame.addView(view);
        }catch (Exception e)
        {
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //progressBar/////////////////////////////////////////////////////////////////////////////

    public void showProgressBar()
    {
        Rtx_Keyboard.closeSoftKeybord(null, mContext);

        addProgressBarToLayout(progressBar,300,300);
        progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void closeProgressBar()
    {
        progressBar.setVisibility(View.INVISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void createFloatView()
    {
        startVirtualButtonSetvice();
    }

    public void startVirtualButtonSetvice(){
        Intent it = new Intent(this,Service_VirtualButton.class);
        startService(it);
    }
    public void stopVirtualButtonSetvice(){
        Intent it = new Intent(this,Service_VirtualButton.class);
        stopService(it);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("Jerry","Activity receive KEYCODE_BACK!!");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
