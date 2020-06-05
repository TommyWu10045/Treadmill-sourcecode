package com.rtx.treadmill.RtxMainFunctionBike.Physical;

/**
 * Created by chasechang on 3/27/17.
 */

public enum PhysicalState {
    /* enum */
    PROC_SHOW_PHYSICAL              (0x00000010) ,
    PROC_SHOW_WEIGHT                (0x00000020) ,
    PROC_SHOW_AGE                   (0x00000021) ,
    PROC_SHOW_GENDER                (0x00000022) ,
    PROC_SHOW_GO                    (0x00000023) ,
    PROC_SHOW_GERKIN                (0x00000051) ,
    PROC_SHOW_COOPER                (0x00000052) ,
    PROC_SHOW_USMC                  (0x00000053) ,
    PROC_SHOW_ARMY                  (0x00000054) ,
    PROC_SHOW_NAVY                  (0x00000055) ,
    PROC_SHOW_USAF                  (0x00000056) ,
    PROC_SHOW_FEDERAL               (0x00000057) ,
    PROC_EXERCISE_CHECK_COUNTDOWN   (0x00000058) ,

    PROC_SHOW_DONE                  (0x00000058) ,
    PROC_SHOW_LOGOUT                (0x00000059) ,

    PROC_SHOW_GERKIN_REFRESH        (0x00002051) ,
    PROC_SHOW_COOPER_REFRESH        (0x00002052) ,
    PROC_SHOW_USMC_REFRESH          (0x00002053) ,
    PROC_SHOW_ARMY_REFRESH          (0x00002054) ,
    PROC_SHOW_NAVY_REFRESH          (0x00002055) ,
    PROC_SHOW_USAF_REFRESH          (0x00002056) ,
    PROC_SHOW_FEDERAL_REFRESH       (0x00002057) ,

    PROC_SHOW_GERKIN_FINISH         (0x00012051) ,
    PROC_SHOW_COOPER_FINISH         (0x00012052) ,
    PROC_SHOW_USMC_FINISH           (0x00012053) ,
    PROC_SHOW_ARMY_FINISH           (0x00012054) ,
    PROC_SHOW_NAVY_FINISH           (0x00012055) ,
    PROC_SHOW_USAF_FINISH           (0x00012056) ,
    PROC_SHOW_FEDERAL_FINISH        (0x00012057) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    PhysicalState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static PhysicalState fromId(int value) {
        for(PhysicalState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
