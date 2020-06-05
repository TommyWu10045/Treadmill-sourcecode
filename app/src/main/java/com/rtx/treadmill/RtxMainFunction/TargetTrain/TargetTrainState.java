package com.rtx.treadmill.RtxMainFunction.TargetTrain;

/**
 * Created by chasechang on 3/27/17.
 */

public enum TargetTrainState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_MAIN(1) ,
    PROC_REFRESH_SIMPLE_INFO(2) ,
    PROC_SHOW_PAGE_DETAIL_DISTANCE(3) ,
    PROC_SHOW_PAGE_DETAIL_CALORIES(4) ,
    PROC_SHOW_PAGE_DETAIL_FREQUENCY(5) ,
    PROC_SHOW_PAGE_DETAIL_WEIGHT(6) ,
    PROC_SHOW_PAGE_DETAIL_BODYFAT(7) ,
    PROC_SHOW_PAGE_MODIFY_CURRENT_WEIGHT(8) ,
    PROC_SHOW_PAGE_MODIFY_CURRENT_BODYFAT(9) ,
    PROC_CLOUD_LOAD_GOAL(10) ,
    PROC_CLOUD_LOAD_GOAL_CHECK(11) ,
    PROC_SHOW_PAGE_CHOOSE_TARGET(12) ,
    PROC_CLOUD_UPLOAD_WEIGHT(13) ,
    PROC_CLOUD_UPLOAD_WEIGHT_CHECK(14) ,
    PROC_CLOUD_UPLOAD_BodyFat(15) ,
    PROC_CLOUD_UPLOAD_BodyFat_CHECK(16) ,
    PROC_CLOUD_UPLOAD_TARGET_GOAL(17)   ,
    PROC_CLOUD_UPLOAD_TARGET_GOAL_CHECK(18)   ,
    PROC_SHOW_PAGE_ADD_SELECT_TYPE(19) ,
    PROC_SHOW_PAGE_ADD_DIS_SET(20)  ,
    PROC_SHOW_PAGE_ADD_CAL_SET_CAL(21)  ,
    PROC_SHOW_PAGE_ADD_BDY_FAT_SET_CURRENT(22) ,
    PROC_SHOW_PAGE_ADD_BDY_FAT_SET_TARGET(23) ,
    PROC_SHOW_PAGE_ADD_WEI_SET_CURRENT(24) ,
    PROC_SHOW_PAGE_ADD_WEI_SET_TARGET(25) ,
    PROC_SHOW_PAGE_ADD_FREQ_SELECT_DAY(26)  ,
    PROC_SHOW_PAGE_SET_DAY(27) ,
    PROC_SHOW_PAGE_SET_WEEK(28) ,
    PROC_CLOUD_LOAD_GOAL_CLOSE(29) ,
    PROC_CLOUD_LOAD_GOAL_CLOSE_CHECK(30) ,
    PROC_SHOW_PAGE_HISTORY_MAIN(31) ,
    PROC_CLOUD_DELETE_GOAL_CLOSE(32) ,
    PROC_CLOUD_DELETE_GOAL_CLOSE_CHECK(33) ,
    PROC_CLOUD_ADD_GOAL_CLOSE(34) ,
    PROC_CLOUD_ADD_GOAL_CLOSE_CHECK(35) ,
    PROC_CHECK_TARGET_STATUS(36) ,
    PROC_SHOW_DISTANCE_REFRESH      (37) ,

    PROC_IDLE(998) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    TargetTrainState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static TargetTrainState fromId(int value) {
        for(TargetTrainState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
