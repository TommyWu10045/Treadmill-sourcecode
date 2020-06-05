package com.rtx.treadmill.RtxMainFunction.Hiit;

/**
 * Created by chasechang on 3/27/17.
 */

public enum HiitState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_MAIN(1) ,
    PROC_SHOW_PAGE_DEFAULT_DATA_PREVIEW(2) ,
    PROC_SHOW_PAGE_PERSONAL_MAIN(3) ,
    PROC_SHOW_PAGE_PERSONAL_NAME(4) ,
    PROC_SHOW_PAGE_PERSONAL_DATALIST(5) ,
    PROC_SHOW_PAGE_PERSONAL_SETTING_SPRINT(6) ,
    PROC_SHOW_PAGE_PERSONAL_SETTING_RECOVERY(7) ,
    PROC_SHOW_PAGE_PERSONAL_SETTING_WARM_UP(8) ,
    PROC_SHOW_PAGE_PERSONAL_SETTING_COOL_DOWN(9) ,
    PROC_SHOW_PAGE_PERSONAL_PREVIEW(10) ,
    PROC_SHOW_PAGE_DIALOG_DELETE(11) ,
    PROC_CLOUD_GET_NAME_LIST(30),
    PROC_CLOUD_GET_NAME_LIST_CHECK(31),
    PROC_CLOUD_GET_INTERVAL(32),
    PROC_CLOUD_GET_INTERVAL_CHECK(33),
    PROC_CLOUD_ADD_INTERVAL(34),
    PROC_CLOUD_ADD_INTERVAL_CHECK(35),
    PROC_CLOUD_DELETE_INTERVAL(36),
    PROC_CLOUD_DELETE_INTERVAL_CHECK(37),
    PROC_CLOUD_UPDATE_INTERVAL(38),
    PROC_CLOUD_UPDATE_INTERVAL_CHECK(39),
    PROC_EXERCISE_PREPARE(50),
    PROC_EXERCISE_SHOW(51),
    PROC_EXERCISE_INIT(52),
    PROC_EXERCISE_REFRESH(53),
    PROC_EXERCISE_FINISH(54),
    PROC_EXERCISE_SHOW_DONE(55),
    PROC_EXERCISE_SHOW_LOGOUT(56),
    PROC_EXERCISE_CHECK_COUNTDOWN   (57) ,
    PROC_IDLE(998) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    private final int iState ;

    HiitState(int code){
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static HiitState fromId(int value){
        for(HiitState mCmd : values()){
            if (mCmd.iState == value){ return mCmd; }
        }
        return ( PROC_NULL ) ;
    }
}
