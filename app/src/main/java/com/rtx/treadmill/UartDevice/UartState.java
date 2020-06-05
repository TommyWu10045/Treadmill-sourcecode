package com.rtx.treadmill.UartDevice;

/**
 * Created by chasechang on 3/27/17.
 */

public enum UartState {
    /* enum */
    //MState
    PROC_UART_INIT             (0x00000001) ,
    PROC_UART_SEND             (0x00000002) ,
    PROC_UART_DELAY            (0x00000003) ,
    PROC_UART_GET              (0x00000004) ,
    PROC_UART_CHECK            (0x00000005) ,

    PROC_UART_IDLE              (0x01000000) ,
    PROC_UART_EXIT              (0x02000000) ,
    PROC_UART_NULL              (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    UartState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static UartState fromId(int value) {
        for(UartState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_UART_NULL ) ;
    }
}
