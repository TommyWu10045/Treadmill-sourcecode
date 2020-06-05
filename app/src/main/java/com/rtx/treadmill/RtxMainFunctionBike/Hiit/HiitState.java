package com.rtx.treadmill.RtxMainFunctionBike.Hiit;

/**
 * Created by chasechang on 3/27/17.
 */

public enum HiitState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_MAIN(1) ,

    PROC_IDLE(998) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    HiitState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static HiitState fromId(int value) {
        for(HiitState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
