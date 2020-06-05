package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

/**
 * Created by chasechang on 3/27/17.
 */

public enum MyGymState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_MAIN(1) ,
    PROC_SHOW_PAGE_BULLETIN_DETAIL(2) ,
    PROC_SHOW_PAGE_CLASS_DETAIL(3) ,

    PROC_CLOUD_GET_BULLETIN_LIST(30),
    PROC_CLOUD_GET_BULLETIN_LIST_CHECK(31),
    PROC_CLOUD_GET_BULLETIN_DETAIL_INFO(32),
    PROC_CLOUD_GET_BULLETIN_DETAIL_INFO_CHECK(33),
    PROC_CLOUD_GET_CLASS_INFO_LIST(34),
    PROC_CLOUD_GET_CLASS_INFO_LIST_CHECK(35),
    PROC_CLOUD_GET_MY_CLASS_INFO_LIST(36),
    PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK(37),
    PROC_CLOUD_GET_CLASS_DETAIL_INFO(38),
    PROC_CLOUD_GET_CLASS_DETAIL_INFO_CHECK(39),
    PROC_CLOUD_ADD_CLASS(40),
    PROC_CLOUD_ADD_CLASS_CHECK(41),
    PROC_CLOUD_DELETE_CLASS(42),
    PROC_CLOUD_DELETE_CLASS_CHECK(43),
    PROC_CLOUD_CHECK_MY_CLASS_EXIST(44),
    PROC_CLOUD_CHECK_MY_CLASS_EXIST_CHECK(45),

    PROC_IDLE(998) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    MyGymState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static MyGymState fromId(int value) {
        for(MyGymState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
