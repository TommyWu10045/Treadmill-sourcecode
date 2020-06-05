package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retonix.circlecloud.Cloud_69_GET_ACN_DAT;
import com.rtx.treadmill.BT.BT_login_Peripheral;
import com.rtx.treadmill.BT.gbt;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;

/**
 * Created by chasechang on 3/27/17.
 */

public class LoginProc {
    private String TAG = "Jerry=LoginProc" ;

    private MainActivity mMainActivity ;
    private Context mContext;

    private LoginState mState = LoginState.PROC_INIT ;
    private LoginState mNextState = LoginState.PROC_NULL ;
    private LoginState tempState = LoginState.PROC_NULL ;

    private LoginLayout                 mLoginLayout;
    private LoginEmailLayout            mLoginEmailLayout;
    private LoginPasswordLayout         mLoginPasswordLayout;
    private ForgotPasswordLayout        mForgotPasswordLayout;
    private EmailSentLayout             mEmailSentLayout;
    private RegEmailLayout              mRegEmailLayout;
    private RegPasswordLayout           mRegPasswordLayout;
    private RegConfirmPasswordLayout    mRegConfirmPasswordLayout;
    private RegNameLayout               mRegNameLayout;
    private RegUnitLayout               mRegUnitLayout;
    private RegGenderLayout             mRegGenderLayout;
    private RegHeightLayout             mRegHeightLayout;
    private RegBirthdayLayout           mRegBirthdayLayout;
    private RegPolicyLayout             mRegPolicyLayout;
    private RegFinishLayout             mRegFinishLayout;

    private boolean isBtOpen = false;
    private boolean bBtOnStart = false;

    protected String sInput_Name;
    protected String sInput_Password;
    protected String sInput_ForgotPasswordEmail;

    private boolean bServerResponseFlag_Login = false;
    private boolean bServerResponseFlag_ForgotPassword = false;
    private boolean bServerResponseFlag_CheckIdExist = false;
    private boolean bServerResponseFlag_Registration = false;

    private int     iServerResponseResult_Login = -1;
    private int     iServerResponseResult_ForgotPassword = -1;
    private int     iServerResponseResult_CheckIdExist = -1;
    private int     iServerResponseResult_Registration = -1;

    private boolean bBT_Login = false;

    private int iFinishCount = 0;

    public LoginProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = LoginState.PROC_INIT ;

        if(mLoginLayout == null)                { mLoginLayout = new LoginLayout(mMainActivity.mContext , mMainActivity); }
        if(mLoginEmailLayout == null)           { mLoginEmailLayout = new LoginEmailLayout(mMainActivity.mContext , mMainActivity); }
        if(mLoginPasswordLayout == null)        { mLoginPasswordLayout = new LoginPasswordLayout(mMainActivity.mContext , mMainActivity); }
        if(mForgotPasswordLayout == null)       { mForgotPasswordLayout = new ForgotPasswordLayout(mMainActivity.mContext , mMainActivity); }
        if(mEmailSentLayout == null)            { mEmailSentLayout = new EmailSentLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegEmailLayout == null)             { mRegEmailLayout = new RegEmailLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegPasswordLayout == null)          { mRegPasswordLayout = new RegPasswordLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegConfirmPasswordLayout == null)   { mRegConfirmPasswordLayout = new RegConfirmPasswordLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegNameLayout == null)              { mRegNameLayout = new RegNameLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegUnitLayout == null)              { mRegUnitLayout = new RegUnitLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegGenderLayout == null)            { mRegGenderLayout = new RegGenderLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegHeightLayout == null)            { mRegHeightLayout = new RegHeightLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegBirthdayLayout == null)          { mRegBirthdayLayout = new RegBirthdayLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegPolicyLayout == null)            { mRegPolicyLayout = new RegPolicyLayout(mMainActivity.mContext , mMainActivity); }
        if(mRegFinishLayout == null)            { mRegFinishLayout = new RegFinishLayout(mMainActivity.mContext , mMainActivity); }
    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(LoginState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init()
    {
        mContext = mMainActivity.mContext;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == LoginState.PROC_NULL)
        {
            mState = LoginState.PROC_SHOW_PAGE_LOGIN;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Login() {

        v_open_bt(true);
        vSetBtOnStart(true);
        vChangeDisplayPage(mLoginLayout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
            }
        });

        sInput_Name = null;
        sInput_Password = null;

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Email()
    {
        vChangeDisplayPage(mLoginEmailLayout);
        sInput_Password = null;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

                mLoginEmailLayout.setEmail(sInput_Name);
            }
        });

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Password()
    {
        vChangeDisplayPage(mLoginPasswordLayout);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

                mLoginPasswordLayout.setPassword(sInput_Password);

            }
        });

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_ForgotPassword()
    {
        vChangeDisplayPage(mForgotPasswordLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_EmailSent()
    {
        vChangeDisplayPage(mEmailSentLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegEmail()
    {
        vChangeDisplayPage(mRegEmailLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegPassword()
    {
        vChangeDisplayPage(mRegPasswordLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegConfirmPassword()
    {
        vChangeDisplayPage(mRegConfirmPasswordLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegName()
    {
        vChangeDisplayPage(mRegNameLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegUnits()
    {
        vChangeDisplayPage(mRegUnitLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegGender()
    {
        vChangeDisplayPage(mRegGenderLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegHeight()
    {
        vChangeDisplayPage(mRegHeightLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegBirthday()
    {
        vChangeDisplayPage(mRegBirthdayLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegPolicy()
    {
        vChangeDisplayPage(mRegPolicyLayout);

        mState = LoginState.PROC_IDLE ;
    }

    private void vProc_ShowPage_RegFinish()
    {
        if(iFinishCount == 0)
        {
            vChangeDisplayPage(mRegFinishLayout);

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mRegFinishLayout.setName(GlobalData.global_RegData.sName);
                    GlobalData.global_RegData.clear();
                }
            });
        }

        iFinishCount++;

        if(iFinishCount > (EngSetting.DEF_SEC_COUNT))
        {
            iFinishCount = 0;
            mState = LoginState.PROC_IDLE ;

            vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN);
        }
    }

    private void vProc_Idle()
    {
        if(mNextState != LoginState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = LoginState.PROC_NULL;
        }
        else if(isBtOpen)
        {
            v_count_bt();
        }
    }

    private void vProc_Exit() {

        EngSetting.vClearTempUnit();
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = LoginState.PROC_INIT ;
        mNextState = LoginState.PROC_NULL;
    }

    private void vProc_Cloud_Login()
    {
        bServerResponseFlag_Login = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(bBT_Login == false)
                {
                    mMainActivity.showProgressBar();
                }

                new Thread(CloudRunnable_Login).start();
            }
        });

        mState = LoginState.PROC_CLOUD_LOGIN_CHECK;
    }

    private void vProc_Cloud_Login_Check()
    {
        if(bServerResponseFlag_Login)
        {
            mState = LoginState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_Login == 0)
                    {
                        //mLoginPasswordLayout.setErrorMsg("Success!!!");

                        sInput_Name = null;
                        sInput_Password = null;

                        vDismissInfoDialog();

                        if(bCheckFirstLogin())
                        {
                            vSetNextState(LoginState.PROC_SHOW_PAGE_TUTORIAL);
                        }
                        else
                        {
                            vSetNextState(LoginState.PROC_RETURN_HOME);
                        }

                        GlobalData.global_RegData.sName = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_NAM);
                    }
                    else if(iServerResponseResult_Login == -1)
                    {
                        if(mLoginPasswordLayout != null)
                        {
                            mLoginPasswordLayout.setErrorMsg(R.string.connect_problem);
                        }

                        if(isBtOpen && (bBT_Login == false))
                        {
                            vShowQuickLoginFail();
                        }

                        vSetNextState(LoginState.PROC_IDLE);
                    }
                    else if(iServerResponseResult_Login == 1)
                    {
                        if(mLoginPasswordLayout != null)
                        {
                            mLoginPasswordLayout.setErrorMsg(R.string.password_erroe);
                        }

                        if(isBtOpen && (bBT_Login == false))
                        {
                            vShowQuickLoginFail();
                        }

                        vSetNextState(LoginState.PROC_IDLE);
                    }
                    else
                    {
                        vDismissInfoDialog();
                        vSetNextState(LoginState.PROC_IDLE);
                    }

                    mMainActivity.closeProgressBar();

                }
            });
        }
    }

    private void vProc_ShowDialog_Tutorial()
    {
        mState = LoginState.PROC_IDLE;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_TUTORIAL,null,null);
                vSetNextState(LoginState.PROC_RETURN_HOME);
            }
        });
    }

    private void vProc_ReturnHomePage()
    {
        mState = LoginState.PROC_IDLE;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                vReturnMainProc();
            }
        });
    }

    private void vProc_Cloud_ForgotPassword()
    {
        bServerResponseFlag_ForgotPassword = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_ForgotPassword).start();
            }
        });

        mState = LoginState.PROC_CLOUD_SEND_EMAIL_CHECK;
    }

    private void vProc_Cloud_ForgotPassword_Check()
    {
        if(bServerResponseFlag_ForgotPassword)
        {
            mState = LoginState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
//                    if(!CloudDataStruct.CloudData_63.check_is_null())
                    //變更只要回傳成功就判定顯示 email sent
                    if(iServerResponseResult_ForgotPassword == 0)
                    {
                        vSetNextState(LoginState.PROC_SHOW_PAGE_EMAIL_SENT);
                        Rtx_Keyboard.closeSoftKeybord(mForgotPasswordLayout,mMainActivity.mContext);
                    }
                    else
                    {
                        if(iServerResponseResult_ForgotPassword == 1)
                        {
                            mForgotPasswordLayout.setErrorMsg(LanguageData.s_get_string(mContext,R.string.no_account_matches) + " " + sInput_ForgotPasswordEmail + " " + LanguageData.s_get_string(mContext,R.string.please_try_again));
                        }
                    }

                    mMainActivity.closeProgressBar();

                }
            });
        }
    }

    private void vProc_Cloud_CheckIdExist()
    {
        bServerResponseFlag_CheckIdExist = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_CheckIdExist).start();
            }
        });

        mState = LoginState.PROC_CLOUD_CHECK_EMAIL_EXIST_CHECK;
    }

    private void vProc_Cloud_CheckIdExist_Check()
    {
        if(bServerResponseFlag_CheckIdExist)
        {
            bServerResponseFlag_CheckIdExist = false;

            mState = LoginState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_CheckIdExist == 0)
                    {
                        //Success
                        Log.e(TAG,"Check Id Success!");

                        if(CloudDataStruct.CloudData_69.bCheck_IdIsExist())
                        {
                            mRegEmailLayout.setErrorMsg(LanguageData.s_get_string(mContext,
                            R.string.user_id_exist_1).toUpperCase() +
                            "\n" +
                            LanguageData.s_get_string(mContext,R.string.user_id_exist_2).toUpperCase());
                        }
                        else
                        {
                            Rtx_Keyboard.closeSoftKeybord(mRegEmailLayout,mContext);
                            vSetNextState(LoginState.PROC_SHOW_PAGE_REG_PASSWORD);
                        }
                    }
                    else
                    {
                        //Fail
                        Log.e(TAG,"Check Id Fail!");
                    }

                    mMainActivity.closeProgressBar();

                }
            });
        }
    }

    private void vProc_Cloud_Registration()
    {
        bServerResponseFlag_Registration = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Registration).start();
            }
        });

        mState = LoginState.PROC_CLOUD_REG_CHECK;
    }

    private void vProc_Cloud_Registration_Check()
    {
        if(bServerResponseFlag_Registration)
        {
            mState = LoginState.PROC_IDLE;
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_Registration == 0)
                    {
                        //Success
                        Log.e(TAG,"Reg Success!");
                        vSetNextState(LoginState.PROC_SHOW_PAGE_REG_DONE);
                        mState = LoginState.PROC_IDLE;
                        //vReturnMainProc();
                    }
                    else
                    if(iServerResponseResult_Registration == 1)
                    {
                        //exist
                        Log.e(TAG,"Reg Exist!");
                        vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                        mRegEmailLayout.setErrorMsg(R.string.account_exist);
                        mState = LoginState.PROC_IDLE;
                    }
                    else
                    {
                        //Fail
                        Log.e(TAG,"Reg Fail!");
                    }

                    mMainActivity.closeProgressBar();

                }
            });
        }
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.dismissInfoDialog();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    public void vReturnMainProc()
    {
        if(mMainActivity.mMainProcTreadmill.vGetBeforeLoginState() == MainState.PROC_NULL)
        {
            mMainActivity.mMainProcTreadmill.loginProc.vMainChangePage(MainState.PROC_HOME);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.loginProc.vMainChangePage(mMainActivity.mMainProcTreadmill.vGetBeforeLoginState());
            mMainActivity.mMainProcTreadmill.vClearBeforeLoginState();
        }
    }

    public void vSetLoginData(String sName, String sPassword)
    {
        this.sInput_Name = sName;
        this.sInput_Password = sPassword;
    }

    public void vShowQuickLogin()
    {
        bBT_Login = true;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_QUICK_LOGIN,-1,-1);
            }
        });
    }

    public void vShowQuickLoginFail()
    {
        bBT_Login = true;

        v_open_bt(false);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_QUICK_LOGIN_FAIL,-1,-1);
                vSetBtOnStart(false);
            }
        });
    }

    public void vDismissInfoDialog()
    {
        bBT_Login = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
            }
        });
    }
    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[LoginProc] mState = " + mState);
            tempState = mState;
        }

        if((mState != LoginState.PROC_SHOW_PAGE_LOGIN) && (mState != LoginState.PROC_IDLE) && (mState != LoginState.PROC_CLOUD_LOGIN) && (mState != LoginState.PROC_CLOUD_LOGIN_CHECK))
        {
            if(isBtOpen)
            {
                v_open_bt(false);
                vSetBtOnStart(false);
            }
        }
        else
        {
            if(mMainActivity.dialogLayout_QuickLoginFail != null) {
                if (mMainActivity.dialogLayout_QuickLoginFail.isShown()) {
//                    if (bGetBtOnStart()) {
//                        v_open_bt(false);
//                        vSetBtOnStart(false);
//                    }
                } else {
                    if (!bGetBtOnStart()) {
                        v_open_bt(true);
                        vSetBtOnStart(true);
                    }
                }
            }
        }

        switch( mState )
        {
            case PROC_INIT                                  :{   vProc_Init();                          break;  }
            case PROC_SHOW_PAGE_LOGIN                       :{   vProc_ShowPage_Login();                break;  }
            case PROC_SHOW_PAGE_LOGIN_EMAIL                 :{   vProc_ShowPage_Email();                break;  }
            case PROC_SHOW_PAGE_LOGIN_PASSWORD              :{   vProc_ShowPage_Password();             break;  }
            case PROC_CLOUD_LOGIN                           :{   vProc_Cloud_Login();                   break;  }
            case PROC_CLOUD_LOGIN_CHECK                     :{   vProc_Cloud_Login_Check();             break;  }
            case PROC_SHOW_PAGE_TUTORIAL                    :{   vProc_ShowDialog_Tutorial();           break;  }
            case PROC_RETURN_HOME                           :{   vProc_ReturnHomePage();                break;  }
            case PROC_SHOW_PAGE_FORGOT_PASSWORD             :{   vProc_ShowPage_ForgotPassword();       break;  }
            case PROC_CLOUD_SEND_EMAIL                      :{   vProc_Cloud_ForgotPassword();          break;  }
            case PROC_CLOUD_SEND_EMAIL_CHECK                :{   vProc_Cloud_ForgotPassword_Check();    break;  }
            case PROC_SHOW_PAGE_EMAIL_SENT                  :{   vProc_ShowPage_EmailSent();            break;  }
            case PROC_SHOW_PAGE_REG_EMAIL                   :{   vProc_ShowPage_RegEmail();             break;  }
            case PROC_SHOW_PAGE_REG_PASSWORD                :{   vProc_ShowPage_RegPassword();          break;  }
            case PROC_SHOW_PAGE_REG_CONFIRM_PASSWORD        :{   vProc_ShowPage_RegConfirmPassword();   break;  }
            case PROC_SHOW_PAGE_REG_NAME                    :{   vProc_ShowPage_RegName();              break;  }
            case PROC_SHOW_PAGE_REG_UNIT                    :{   vProc_ShowPage_RegUnits();             break;  }
            case PROC_SHOW_PAGE_REG_GENDER                  :{   vProc_ShowPage_RegGender();            break;  }
            case PROC_SHOW_PAGE_REG_HEIGHT                  :{   vProc_ShowPage_RegHeight();            break;  }
            case PROC_SHOW_PAGE_REG_BIRTHDAY                :{   vProc_ShowPage_RegBirthday();          break;  }
            case PROC_SHOW_PAGE_REG_POLICY                  :{   vProc_ShowPage_RegPolicy();            break;  }
            case PROC_CLOUD_CHECK_EMAIL_EXIST               :{   vProc_Cloud_CheckIdExist();            break;  }
            case PROC_CLOUD_CHECK_EMAIL_EXIST_CHECK         :{   vProc_Cloud_CheckIdExist_Check();      break;  }
            case PROC_CLOUD_REG                             :{   vProc_Cloud_Registration();            break;  }
            case PROC_CLOUD_REG_CHECK                       :{   vProc_Cloud_Registration_Check();      break;  }
            case PROC_SHOW_PAGE_REG_DONE                    :{   vProc_ShowPage_RegFinish();            break;  }
            case PROC_IDLE                                  :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                          break;  }
            default                                         :{   vProc_Idle();                          break;  }
        }
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_Login = new Runnable() {
        @Override
        public void run()
        {
            if(CloudDataStruct.CloudData_20.Set_input(sInput_Name,sInput_Password))
            {
                iServerResponseResult_Login = mMainActivity.mCloudCmd.iCloudCmd_Login();
                if(iServerResponseResult_Login == 0) {
                    //登入清除
                    if(Rtx_Debug.bRtxDebug_GetShareEnable())
                    {
                        GlobalData.vGlobalData_ShareAllClear();
                    }
                    GlobalData.Weather.bUpdate = true;
                    iServerResponseResult_Login = mMainActivity.mCloudCmd.iCloudCmd_GetUserInfo();
                }
                else
                {
                    //iServerResponseResult_Login = -1;
                    CloudDataStruct.CloudData_20.clear();
                }
            }
            bServerResponseFlag_Login = true;
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_ForgotPassword = new Runnable() {
        @Override
        public void run()
        {
            CloudDataStruct.CloudData_63.clear();
            iServerResponseResult_ForgotPassword = mMainActivity.mCloudCmd.iCloudCmd_ForgotPassword(sInput_ForgotPasswordEmail);
            bServerResponseFlag_ForgotPassword = true;
        }
    };

    public Runnable CloudRunnable_CheckIdExist = new Runnable() {
        @Override
        public void run()
        {
            iServerResponseResult_CheckIdExist = mMainActivity.mCloudCmd.iCloudCmd_Reg_CheckUserExist(GlobalData.global_RegData.sEmail);
            bServerResponseFlag_CheckIdExist = true;
        }
    };

    public Runnable CloudRunnable_Registration = new Runnable() {
        @Override
        public void run()
        {
            iServerResponseResult_Registration = mMainActivity.mCloudCmd.iCloudCmd_Registration(GlobalData.global_RegData);
            bServerResponseFlag_Registration = true;
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void vSetBtOnStart(boolean bState)
    {
        bBtOnStart = bState;
    }

    public boolean bGetBtOnStart()
    {
        return bBtOnStart;
    }

    public void v_open_bt(boolean enable)
    {
        isBtOpen = enable;
        if(gbt.bt_state != enable && Rtx_Debug.bGetBT_Enable())
        {
            if(gbt.bt_class == null)
            {
                gbt.bt_class = new BT_login_Peripheral(mContext, this);
            }
            if(enable) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gbt.bt_class.onStart(mContext);
                }
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gbt.bt_class.onStop();
                }
            }
        }
        gbt.bt_state = enable;
    }

    public void v_count_bt()
    {
        if(gbt.bt_state)
        {
            if(gbt.bt_class == null)
            {
                gbt.bt_class = new BT_login_Peripheral(mContext,this);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                gbt.bt_class.v_timeout_count();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean bCheckFirstLogin()
    {
        boolean bResult = false;

        if(CloudDataStruct.CloudData_20.is_log_in())
        {
            if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
            {
                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T] != null)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T].equals("0"))
                    {
                        //CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_T] = "1";
                        bResult = true;
                    }
                }
            }
            else
            if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
            {
                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E] != null)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E].equals("0"))
                    {
                        //CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_E] = "1";
                        bResult = true;
                    }
                }
            }
            else
            if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
            {
                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B] != null)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B].equals("0"))
                    {
                        //CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_B] = "1";
                        bResult = true;
                    }
                }
            }
            else
            if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
            {
                if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R] != null)
                {
                    if(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R].equals("0"))
                    {
                        //CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USE_R] = "1";
                        bResult = true;
                    }
                }
            }
        }

        return bResult;
    }
}
