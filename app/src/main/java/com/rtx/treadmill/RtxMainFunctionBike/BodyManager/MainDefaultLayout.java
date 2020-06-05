package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxMainFunctionBike.Login.LoginState;

/**
 * Created by chasechang on 3/22/17.
 */

public class MainDefaultLayout extends Main01Layout {
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
            mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_BD_GET);
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
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_BODY_MANAGER);
                    mMainActivity.mMainProcBike.bodymanagerProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_BODY_MANAGER);
                    mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcBike.bodymanagerProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcBike.vSetBeforeLoginState(MainState.PROC_BODY_MANAGER);
                    //mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcBike.bodymanagerProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });
        }

        return bLogin;
    }
}
