package com.rtx.treadmill.RtxMainFunction.Performance;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;

/**
 * Created by chasechang on 3/22/17.
 */

public class MainDefaultLayoutE extends SessionLayoutE {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;

    public MainDefaultLayoutE(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);
        this.mMainActivity = mMainActivity;
    }


    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();

        if(check_Login())
        {
            mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_GET_SESSION);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();
        //Login
        if(!bLogin)
        {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGIN,-1,-1);
            mMainActivity.dialogLayout_Login.fillerTextView_Login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    mMainActivity.mMainProcTreadmill.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcTreadmill.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    //mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcTreadmill.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });
        }

        return bLogin;
    }
}
