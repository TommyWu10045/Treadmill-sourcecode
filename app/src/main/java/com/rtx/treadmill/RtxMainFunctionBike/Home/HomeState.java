package com.rtx.treadmill.RtxMainFunctionBike.Home;

/**
 * Created by chasechang on 3/27/17.
 */

public enum HomeState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_HOME(1) ,
    PROC_SHOW_PAGE_EXERCISE_HOME(2) ,
    PROC_LOGOUT(3) ,
    PROC_UPDATE_USER_INFO(4) ,

    PROC_CLOUD_GET_MY_CLASS_INFO_LIST(50) ,
    PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK(51) ,
    PROC_CLOUD_LOAD_GOAL(52) ,
    PROC_CLOUD_LOAD_GOAL_CHECK(53) ,
    PROC_CLOUD_UPLOAD_FIRST_LOGIN(54) ,
    PROC_CLOUD_UPLOAD_FIRST_LOGIN_CHECK(55) ,


    PROC_IDLE(99) ,
    PROC_EXIT(100) ,

    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    HomeState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static HomeState fromId(int value) {
        for(HomeState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
