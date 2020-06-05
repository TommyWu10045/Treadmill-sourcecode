package com.rtx.treadmill;

/**
 * Created by chasechang on 3/27/17.
 */

public enum MainState {
    /* enum */
    PROC_INIT(0) ,
    PROC_HOME(1) ,
    PROC_LOGIN(2) ,
    PROC_LANGUAGE(3) ,
    PROC_TRAINING(4) ,
    PROC_HEART_RATE_CONTROL(5) ,
    PROC_HIIT(6) ,
    PROC_PHYSICAL(7) ,
    PROC_TARGET_TRAIN(8) ,
    PROC_MY_PERFORMANCE(9) ,
    PROC_BODY_MANAGER(10) ,
    PROC_MY_GYM(11) ,
    PROC_MY_WORKOUT(12) ,
    PROC_QUICK_START(13) ,
    PROC_SETTING(14) ,
    PROC_ENGMODE(15) ,

    PROC_IDLE(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    MainState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static MainState fromId(int value) {
        for(MainState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
