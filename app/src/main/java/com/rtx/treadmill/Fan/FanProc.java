package com.rtx.treadmill.Fan;

import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

/**
 * Created by Kevin on 8/30/18.
 */

public class FanProc {
    private boolean DEBUG = false ;
    private String TAG = "Jerry" ;

    private MainActivity mMainActivity ;

    private FanState mState = FanState.PROC_INIT ;
    private FanState mNextState = FanState.PROC_NULL ;
    private FanState tempState = FanState.PROC_NULL ;

    private int iFanState = -1;

    private int icount = 0 ;
    private int iretry_count = 0 ;

    public FanProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;

        mState = FanState.PROC_INIT;

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */

    public void setiFanState(int iFanState) {
        this.iFanState = iFanState;
    }

    public boolean getiFanState() {
        if(iFanState != ExerciseData.uart_data1.ifan_duty)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        icount = 0 ;
        iretry_count = 0 ;
        if(mNextState == FanState.PROC_NULL)
        {
            mNextState = FanState.PROC_FAN_INIT;
        }
        else
        {

        }

        mState = FanState.PROC_IDLE;
    }

    private void vProc_Fan_Init() {
        setiFanState(ExerciseData.uart_data1.ifan_duty);

        mState = FanState.PROC_FAN_CHECK;
    }

    private void vProc_Fan_Check() {
        if (getiFanState()) {
            mState = FanState.PROC_FAN_CHECKVALUE;
        }
    }

    private void vProc_Fan_CheckValue() {
        setiFanState(ExerciseData.uart_data1.ifan_duty);

        mState = FanState.PROC_FAN_UNKNOW;

        if (ExerciseData.uart_data1.ifan_duty == 0) {
            mState = FanState.PROC_FAN_OFF;
        }

        if (ExerciseData.uart_data1.ifan_duty == 1) {
            mState = FanState.PROC_FAN_LOW;
        }

        if (ExerciseData.uart_data1.ifan_duty == 2) {
            mState = FanState.PROC_FAN_HIGH;
        }

        icount = 0;
    }

    private void vProc_Fan_Off() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_FAN, R.drawable.fan_off, R.string.fan_title,R.string.fan_info_off);
            }
        });

        mState = FanState.PROC_FAN_WAIT;
    }

    private void vProc_Fan_Low() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_FAN, R.drawable.fan_50, R.string.fan_title, R.string.fan_info_50);
            }
        });

        mState = FanState.PROC_FAN_WAIT;
    }

    private void vProc_Fan_High() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_FAN, R.drawable.fan_100, R.string.fan_title,R.string.fan_info_100);
            }
        });

        mState = FanState.PROC_FAN_WAIT;
    }

    private void vProc_Fan_Unknow() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_FAN, R.drawable.fan_off, R.string.fan_title, R.string.fan_info_unknow);
            }
        });

        mState = FanState.PROC_FAN_WAIT;
    }

    private void vProc_Fan_Wait() {
        icount++;

        //大約3秒
        if (icount % 300 == 0) {
            mState = FanState.PROC_FAN_DISMISSDIALOG;
            icount = 0;
        }

        if (getiFanState()) {
            mState = FanState.PROC_FAN_CHECKVALUE;
        }
    }

    private void vProc_Fan_DismissDialog() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
            }
        });

        mState = FanState.PROC_FAN_CHECK;
    }


    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.dismissInfoDialog();
            }
        });

        mState = FanState.PROC_INIT;
        mNextState = FanState.PROC_NULL;
    }

    private void vProc_Idle()
    {
        if(mNextState != FanState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = FanState.PROC_NULL;
        }
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[FanState] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_FAN_INIT                          :{   vProc_Fan_Init();              break;  }
            case PROC_FAN_CHECK                         :{   vProc_Fan_Check();             break;  }
            case PROC_FAN_CHECKVALUE                    :{   vProc_Fan_CheckValue();        break;  }
            case PROC_FAN_OFF                           :{   vProc_Fan_Off();               break;  }
            case PROC_FAN_LOW                           :{   vProc_Fan_Low();               break;  }
            case PROC_FAN_HIGH                          :{   vProc_Fan_High();              break;  }
            case PROC_FAN_UNKNOW                        :{   vProc_Fan_Unknow();            break;  }
            case PROC_FAN_WAIT                          :{   vProc_Fan_Wait();              break;  }
            case PROC_FAN_DISMISSDIALOG                 :{   vProc_Fan_DismissDialog();     break;  }


            case PROC_IDLE                              :{   vProc_Idle();                  break;  }
            case PROC_EXIT                              :{   vProc_Exit();                  break;  }
            default                                     :{   vProc_Init();                  break;  }
        }
    }
}

