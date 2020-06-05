package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxMainFunctionBike.Login.LoginState;

/**
 * Created by chasechang on 3/22/17.
 */

public class MainDefaultLayout extends PerformanceLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;

    public MainDefaultLayout(Context context, MainActivity mMainActivity) {
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
            mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_PERFORMANCE);
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
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    mMainActivity.mMainProcBike.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcBike.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_MY_PERFORMANCE);
                    //mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcBike.performanceProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });
        }

        return bLogin;
    }
}
