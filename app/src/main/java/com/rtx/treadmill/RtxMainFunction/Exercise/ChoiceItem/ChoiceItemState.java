package com.rtx.treadmill.RtxMainFunction.Exercise.ChoiceItem;

/**
 * Created by chasechang on 3/27/17.
 */

public enum ChoiceItemState {
    /* enum */
    PROC_CHOICE_INIT              (0x00000001) ,
    PROC_CHOICE_CHECK             (0x00000002) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    ChoiceItemState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static ChoiceItemState fromId(int value) {
        for(ChoiceItemState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
