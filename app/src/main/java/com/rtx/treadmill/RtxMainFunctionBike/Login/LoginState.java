package com.rtx.treadmill.RtxMainFunctionBike.Login;

/**
 * Created by chasechang on 3/27/17.
 */

public enum LoginState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_LOGIN(1) ,
    PROC_SHOW_PAGE_LOGIN_EMAIL(2) ,
    PROC_SHOW_PAGE_LOGIN_PASSWORD(3) ,
    PROC_SHOW_PAGE_FORGOT_PASSWORD(4) ,
    PROC_SHOW_PAGE_EMAIL_SENT(5) ,
    PROC_IDLE(6) ,
    PROC_CLOUD_LOGIN(7) ,
    PROC_CLOUD_LOGIN_CHECK(8) ,
    PROC_CLOUD_SEND_EMAIL(9) ,
    PROC_CLOUD_SEND_EMAIL_CHECK(10) ,
    PROC_SHOW_PAGE_REG_EMAIL(11),
    PROC_SHOW_PAGE_REG_PASSWORD(12),
    PROC_SHOW_PAGE_REG_CONFIRM_PASSWORD(13),
    PROC_SHOW_PAGE_REG_NAME(14),
    PROC_SHOW_PAGE_REG_UNIT(15),
    PROC_SHOW_PAGE_REG_GENDER(16),
    PROC_SHOW_PAGE_REG_HEIGHT(17),
    PROC_SHOW_PAGE_REG_BIRTHDAY(18),
    PROC_SHOW_PAGE_REG_POLICY(19),
    PROC_SHOW_PAGE_REG_DONE(21),
    PROC_CLOUD_REG(22) ,
    PROC_CLOUD_REG_CHECK(23) ,
    PROC_SHOW_PAGE_TUTORIAL(24) ,
    PROC_RETURN_HOME(25) ,
    PROC_CLOUD_CHECK_EMAIL_EXIST(26) ,
    PROC_CLOUD_CHECK_EMAIL_EXIST_CHECK(27) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    LoginState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static LoginState fromId(int value) {
        for(LoginState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
