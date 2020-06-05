package com.rtx.treadmill.RtxMainFunction.Setting;

import android.content.Context;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retonix.circlecloud.Cloud_20_CHK_LGI;
import com.retonix.circlecloud.Cloud_62_GET_TRD_TKN;
import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.utils.MyLog;

import java.util.TreeMap;

/**
 * Created by chasechang on 3/27/17.
 */

public class SettingProc {
    private String TAG = "Jerry=" ;

    private Context mContext;

    private MainActivity mMainActivity ;

    private SettingState mState = SettingState.PROC_INIT ;
    private SettingState mNextState = SettingState.PROC_NULL ;
    private SettingState tempState = SettingState.PROC_NULL ;

    private SettingLayout               mSettingLayout              ;
    private ProfileLayout               mProfileLayout              ;
    private NameLayout                  mNameLayout                 ;
    private HeightLayout                mHeightLayout               ;
    private BirthdayLayout              mBirthdayLayout             ;
    private PasswordOldLayout           mPasswordOldLayout          ;
    private PasswordNewLayout           mPasswordNewLayout          ;
    private PasswordConfirmLayout       mPasswordConfirmLayout      ;
    private PasswordOkLayout            mPasswordOkLayout           ;
    private DatasyncLayout              mDatasyncLayout             ;

    private NameNikeLayout              mNameNikeLayout             ;
    private PasswordNikeLayout          mPasswordNikeLayout         ;
    private NameGarminLayout            mNameGarminLayout           ;
    private PasswordGarminLayout        mPasswordGarminLayout       ;
    private DatasyncWebLayout           mDatasyncWebLayout          ;
    private DatasyncBrowserLayout       mDatasyncBrowserLayout      ;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public String spassword = "";
    public boolean bupdate_info = false;

    public int itrd_pty_app = 0;
    public String strd_pty_app = "";
    public String strd_of = "";
    public String strd_id = "";
    public String strd_pw = "";


    public SettingProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mContext = mMainActivity.mContext;

        mState = SettingState.PROC_INIT ;

        if(mSettingLayout         == null) { mSettingLayout          =  new SettingLayout        (mMainActivity.mContext, mMainActivity);}
        if(mProfileLayout         == null) { mProfileLayout          =  new ProfileLayout        (mMainActivity.mContext, mMainActivity);}
        if(mNameLayout            == null) { mNameLayout             =  new NameLayout           (mMainActivity.mContext, mMainActivity);}
        if(mHeightLayout          == null) { mHeightLayout           =  new HeightLayout         (mMainActivity.mContext, mMainActivity);}
        if(mBirthdayLayout        == null) { mBirthdayLayout         =  new BirthdayLayout       (mMainActivity.mContext, mMainActivity);}
        if(mPasswordOldLayout     == null) { mPasswordOldLayout      =  new PasswordOldLayout    (mMainActivity.mContext, mMainActivity);}
        if(mPasswordNewLayout     == null) { mPasswordNewLayout      =  new PasswordNewLayout    (mMainActivity.mContext, mMainActivity);}
        if(mPasswordConfirmLayout == null) { mPasswordConfirmLayout  =  new PasswordConfirmLayout(mMainActivity.mContext, mMainActivity);}
        if(mPasswordOkLayout      == null) { mPasswordOkLayout       =  new PasswordOkLayout     (mMainActivity.mContext, mMainActivity);}
        if(mDatasyncLayout        == null) { mDatasyncLayout         =  new DatasyncLayout       (mMainActivity.mContext, mMainActivity);}

        if(mNameNikeLayout        == null) { mNameNikeLayout         =  new NameNikeLayout       (mMainActivity.mContext, mMainActivity);}
        if(mPasswordNikeLayout    == null) { mPasswordNikeLayout     =  new PasswordNikeLayout   (mMainActivity.mContext, mMainActivity);}
        if(mNameGarminLayout      == null) { mNameGarminLayout       =  new NameGarminLayout     (mMainActivity.mContext, mMainActivity);}
        if(mPasswordGarminLayout  == null) { mPasswordGarminLayout   =  new PasswordGarminLayout (mMainActivity.mContext, mMainActivity);}
        if(mDatasyncWebLayout     == null) { mDatasyncWebLayout      =  new DatasyncWebLayout    (mMainActivity.mContext, mMainActivity);}
        if(mDatasyncBrowserLayout == null) { mDatasyncBrowserLayout  =  new DatasyncBrowserLayout(mMainActivity.mContext, mMainActivity);}

    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(SettingState nextState)
    {
        mNextState = nextState;
    }

    public void vSet_trd_pty_app(int iapp)
    {
        itrd_pty_app = iapp;
        strd_pty_app = Rtx_TranslateValue.sInt2String(iapp);
    }

    public void vSet_trd_of(String sof)
    {
        strd_of = sof;
    }

    public void vSet_trd_id(String sid)
    {
        strd_id = sid;
    }

    public void vSet_trd_pw(String spw)
    {
        strd_pw = spw;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        CloudDataStruct.CloudData_17.OutData_Keep();

        if(mNextState == SettingState.PROC_NULL)
        {
            mState = SettingState.PROC_CLOUD_62_SET;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Setting() {

        vChangeDisplayPage(mSettingLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Profile()
    {
        vChangeDisplayPage(mProfileLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Name()
    {
        vChangeDisplayPage(mNameLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Height()
    {
        vChangeDisplayPage(mHeightLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_BirthDay()
    {
        vChangeDisplayPage(mBirthdayLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_OldPW()
    {
        vChangeDisplayPage(mPasswordOldLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_NewPW()
    {
        vChangeDisplayPage(mPasswordNewLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_ConfirmPW()
    {
        vChangeDisplayPage(mPasswordConfirmLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_OkPW()
    {
        vChangeDisplayPage(mPasswordOkLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync()
    {

        MyLog.d("vProc_ShowPage_DataSync");
        vChangeDisplayPage(mDatasyncLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_Nike()
    {
        vChangeDisplayPage(mNameNikeLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_NikeP()
    {
        vChangeDisplayPage(mPasswordNikeLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_Garmin()
    {
        vChangeDisplayPage(mNameGarminLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_GarminP()
    {
        vChangeDisplayPage(mPasswordGarminLayout);

        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_WebView()
    {
        if(mDatasyncWebLayout != null) {
            mDatasyncWebLayout.vset_mode(itrd_pty_app);
            vChangeDisplayPage(mDatasyncWebLayout);
        }
        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_DataSync_Browser()
    {
        if(mDatasyncBrowserLayout != null) {
            mDatasyncBrowserLayout.vset_mode(itrd_pty_app);
            vChangeDisplayPage(mDatasyncBrowserLayout);
        }
        mState = SettingState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != SettingState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = SettingState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = SettingState.PROC_INIT ;
        mNextState = SettingState.PROC_NULL;
    }

    private void vProc_Cloud_Set_Userinfo()
    {
        if(bupdate_info) {
            bServerResponseFlag = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.showProgressBar();
                    new Thread(CloudRunnable_update_userinfo).start();
                }
            });

            mState = SettingState.PROC_CLOUD_37_CHECK;

        }
        else
        {
            mState = SettingState.PROC_SHOW_SETTING;
        }
        bupdate_info = false;

    }

    private void vProc_Cloud_Set_Userinfo_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            if(iServerResponse != 0)
            {
                CloudDataStruct.CloudData_17.OutData_Undo();
            }

            mState = SettingState.PROC_SHOW_SETTING;
        }
    }

    private void vProc_Cloud_Set_password()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_update_password).start();
            }
        });
        mState = SettingState.PROC_CLOUD_45_CHECK;
    }

    private void vProc_Cloud_Set_password_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            if(iServerResponse == 0)
            {
                CloudDataStruct.CloudData_17.Set_output(Cloud_17_GET_USR_BSC.Output.USR_PW, spassword);
                CloudDataStruct.CloudData_17.Set_output_Old(Cloud_17_GET_USR_BSC.Output.USR_PW, spassword);
                CloudDataStruct.CloudData_20.Set_input(Cloud_20_CHK_LGI.Input.sUSR_PW, spassword);
            }
            else {
                String stitle01 = LanguageData.s_get_string(mContext, R.string.connection_lost).toUpperCase();
                String sinfo01 = LanguageData.s_get_string(mContext, R.string.data_not_uploaded_to_circlecloudgo).toUpperCase();

                Dialog_UI_Info.v_tist_Dialog(R.drawable.wifi_disconect_icon, -1, stitle01, null, sinfo01, null, null, ImageView.ScaleType.CENTER_INSIDE);
                Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_CLOUD_UPDATE_FAIL02);
                Dialog_UI_Info.v_Set_Dialog_Cloud_retryCmd(cloudglobal.iINS_END_WKO_REC04);
            }

            mState = SettingState.PROC_SHOW_SETTING;
        }
    }

    private void vProc_Cloud_Set_thirdapp()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_update_thirdAPP).start();
            }
        });

        mState = SettingState.PROC_CLOUD_43_CHECK;
    }

    private void vProc_Cloud_Set_thirdapp_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });

            if(iServerResponse == 0)
            {
                v_set62_status();
            }

            mState = SettingState.PROC_SHOW_DATASYNC;
        }
    }

    private void vProc_Cloud_Get_thirdapp()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Get_thirdAPP).start();
            }
        });

        mState = SettingState.PROC_CLOUD_62_CHECK;
    }

    private void vProc_Cloud_Get_thirdapp_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            mState = SettingState.PROC_SHOW_SETTING;
        }
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[settingProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                             :{   vProc_Init();                          break;  }
            case PROC_SHOW_SETTING                     :{   vProc_ShowPage_Setting();                break;  }
            case PROC_SHOW_PROFILE                     :{   vProc_ShowPage_Profile();                break;  }
            case PROC_SHOW_PROFILE_NAME                :{   vProc_ShowPage_Name();                break;  }
            case PROC_SHOW_PROFILE_HEIGHT              :{   vProc_ShowPage_Height();             break;  }
            case PROC_SHOW_PROFILE_BIRTH               :{   vProc_ShowPage_BirthDay();                   break;  }
            case PROC_SHOW_CHANGEPASSWORD00            :{   vProc_ShowPage_OldPW();             break;  }
            case PROC_SHOW_CHANGEPASSWORD01            :{   vProc_ShowPage_NewPW();       break;  }
            case PROC_SHOW_CHANGEPASSWORD02            :{   vProc_ShowPage_ConfirmPW();             break;  }
            case PROC_SHOW_CHANGEPASSWORD03            :{   vProc_ShowPage_OkPW();                   break;  }
            case PROC_SHOW_DATASYNC                    :{   vProc_ShowPage_DataSync();       break;  }

            case PROC_SHOW_DATASYNC_NIKE               :{   vProc_ShowPage_DataSync_Nike();       break;  }
            case PROC_SHOW_DATASYNC_NIKEP              :{   vProc_ShowPage_DataSync_NikeP();       break;  }
            case PROC_SHOW_DATASYNC_GARMIN             :{   vProc_ShowPage_DataSync_Garmin();       break;  }
            case PROC_SHOW_DATASYNC_GARMINP            :{   vProc_ShowPage_DataSync_GarminP();       break;  }

            case PROC_SHOW_DATASYNC_RUNKEEPER          :{   vProc_ShowPage_DataSync_WebView();       break;  }
            case PROC_SHOW_DATASYNC_GOOGLE             :{   vProc_ShowPage_DataSync_WebView();       break;  }
            //case PROC_SHOW_DATASYNC_GOOGLE             :{   vProc_ShowPage_DataSync_Browser();       break;  }
            case PROC_SHOW_DATASYNC_MYMAP              :{   vProc_ShowPage_DataSync_WebView();       break;  }
            case PROC_SHOW_DATASYNC_FITBIT             :{   vProc_ShowPage_DataSync_WebView();       break;  }
            case PROC_SHOW_DATASYNC_JAWBONE            :{   vProc_ShowPage_DataSync_WebView();       break;  }

            case PROC_CLOUD_37_SET                     :{   vProc_Cloud_Set_Userinfo();                   break;  }
            case PROC_CLOUD_37_CHECK                   :{   vProc_Cloud_Set_Userinfo_Check();       break;  }
            case PROC_CLOUD_45_SET                     :{   vProc_Cloud_Set_password();                   break;  }
            case PROC_CLOUD_45_CHECK                   :{   vProc_Cloud_Set_password_Check();       break;  }
            case PROC_CLOUD_43_SET                     :{   vProc_Cloud_Set_thirdapp();                   break;  }
            case PROC_CLOUD_43_CHECK                   :{   vProc_Cloud_Set_thirdapp_Check();       break;  }
            case PROC_CLOUD_62_SET                     :{   vProc_Cloud_Get_thirdapp();                   break;  }
            case PROC_CLOUD_62_CHECK                   :{   vProc_Cloud_Get_thirdapp_Check();       break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                          break;  }
            default                                         :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_update_userinfo = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_UpdateUserInfo();

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_update_password = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_UploadPW(spassword);

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_update_thirdAPP = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            if(CloudDataStruct.CloudData_43.Set_input(strd_pty_app, strd_id, strd_pw, strd_of)) {
                iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_ThirdAPP_Set();
            }

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_Get_thirdAPP = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetThirdPartyApps_Enable();
            if(iServerResponse == 0) {
                iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetThirdPartyApps();
            }

            bServerResponseFlag = true;
        }
    };

    //////////////////////////////////////////
    public void v_set62_status()
    {
        v_set62_status(itrd_pty_app, strd_of, strd_id, strd_pw);
    }

    private void v_set62_status(int id, String sof, String sid, String spw)
    {
        TreeMap<Integer, String[]> mTree = CloudDataStruct.CloudData_62.tree_clound_cmd62_result;
        String[] value;

        if(mTree != null)
        {
            value = mTree.get(id);
            if (value != null) {
                value[Cloud_62_GET_TRD_TKN.Output.TRD_OF] = sof;
                value[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_ID] = sid;
                value[Cloud_62_GET_TRD_TKN.Output.TRD_PTY_PW] = spw;
            }
            else
            {
                value = CloudDataStruct.CloudData_62.Create_Init(id, sof);
            }
            mTree.put(id, value);
        }

        return ;
    }

}
