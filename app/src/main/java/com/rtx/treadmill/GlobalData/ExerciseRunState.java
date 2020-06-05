package com.rtx.treadmill.GlobalData;

/**
 * Created by chasechang on 3/27/17.
 */

public enum ExerciseRunState {
    /* enum */
    PROC_EXERCISE_TR                     (0x00001000) ,
    PROC_EXERCISE_TR_MANUAL              (0x00001011) ,
    PROC_EXERCISE_TR_HILL                (0x00001012) ,
    PROC_EXERCISE_TR_FATBURN             (0x00001013) ,
    PROC_EXERCISE_TR_CARDIO              (0x00001014) ,
    PROC_EXERCISE_TR_STRENGHT            (0x00001015) ,
    PROC_EXERCISE_TR_INTERVAL            (0x00001016) ,
    PROC_EXERCISE_TR_CALORIES            (0x00001017) ,
    PROC_EXERCISE_TR_TIME                (0x00001018) ,
    PROC_EXERCISE_TR_DISTANCE            (0x00001019) ,
    PROC_EXERCISE_TR_INCINE              (0x0000101A) ,
    PROC_EXERCISE_TR_MOUNTANT            (0x0000101B) ,
    PROC_EXERCISE_TR_CONSWATT            (0x0000101C) ,

    PROC_EXERCISE_PHY                    (0x00002000) ,
    PROC_EXERCISE_PHY_GERKIN             (0x00002011) ,
    PROC_EXERCISE_PHY_COOPER             (0x00002012) ,
    PROC_EXERCISE_PHY_USMC               (0x00002013) ,
    PROC_EXERCISE_PHY_ARMY               (0x00002014) ,
    PROC_EXERCISE_PHY_NAVY               (0x00002015) ,
    PROC_EXERCISE_PHY_USAF               (0x00002016) ,
    PROC_EXERCISE_PHY_FEDERAL            (0x00002017) ,

    PROC_EXERCISE_HEARTRATE              (0x00003000) ,

    PROC_EXERCISE_HIIT                   (0x00004000) ,
    PROC_EXERCISE_WORKOUT                (0x00005000) ,
    PROC_EXERCISE_QUICKSTART             (0x00006000) ,

    PROC_EXERCISE_COOL_DOWN              (0x00007000) ,

    PROC_EXERCISE_GETSTATUS              (0x0000F001) ,
    PROC_EXERCISE_DELAY                  (0x0000F002) ,
    PROC_EXERCISE_CACULATION             (0x0000F003) ,
    PROC_EXERCISE_CHECK_STATUS           (0x0000F004) ,

    PROC_INIT                            (0x01000000) ,
    PROC_EXIT                            (0x10000000) ,
    PROC_IDLE                            (0x20000000) ,
    PROC_NULL                            (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    ExerciseRunState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static void set_MState_AllValue(ExerciseRunState mval) {
        ExerciseData.E_imode = mval.getValue();
    }

    public static ExerciseRunState fromId(int value) {
        for(ExerciseRunState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
