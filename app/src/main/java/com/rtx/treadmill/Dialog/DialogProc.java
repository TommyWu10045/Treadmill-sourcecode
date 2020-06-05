package com.rtx.treadmill.Dialog;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by chasechang on 3/27/17.
 */

public class DialogProc {
    private boolean DEBUG = false ;
    private String TAG = "Jerry=" ;

    private Context mContext;
    private MainActivity mMainActivity ;

    private DialogState mNextState = DialogState.PROC_NULL ;
    private DialogState tempState = DialogState.PROC_NULL ;

    private BodyInfoLayout mBodyInfoLayout;
    private BodyInfoLayoutE mBodyInfoLayoutE;
    private TitleImageInfoLayout mTitleImageInfoLayout;
    private TitleImageInfoLayoutE mTitleImageInfoLayoutE;
    private CloudUploadFail mCloudUploadFail;
    private CloudUploadFail02 mCloudUploadFail02;

    private CloudUploadTest1 mCloudUploadTest1;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount = 0 ;
    private int iretry_count = 0 ;

    public DialogProc(MainActivity mMainActivity) {
        this.mContext = mMainActivity.mContext;
        this.mMainActivity = mMainActivity ;
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_INIT ;

        if(mBodyInfoLayout == null)                { mBodyInfoLayout = new BodyInfoLayout(mMainActivity.mContext , mMainActivity); }
        if(mBodyInfoLayoutE == null)                { mBodyInfoLayoutE = new BodyInfoLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mTitleImageInfoLayout == null)                { mTitleImageInfoLayout = new TitleImageInfoLayout(mMainActivity.mContext , mMainActivity); }
        if(mTitleImageInfoLayoutE == null)                { mTitleImageInfoLayoutE = new TitleImageInfoLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mCloudUploadFail == null)                { mCloudUploadFail = new CloudUploadFail(mMainActivity.mContext , mMainActivity); }
        if(mCloudUploadFail02 == null)                { mCloudUploadFail02 = new CloudUploadFail02(mMainActivity.mContext , mMainActivity); }
        if(mCloudUploadTest1 == null)                { mCloudUploadTest1 = new CloudUploadTest1(mMainActivity.mContext , mMainActivity); }

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(DialogState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        icount = 0 ;
        iretry_count = 0 ;
        if(mNextState == DialogState.PROC_NULL)
        {
            Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE;
        }
        else
        {
            Dialog_UI_Info.Dialog_mode = mNextState;
        }
    }

    private void vProc_Show_BM_Info()
    {
       vChangeDisplayPage(mBodyInfoLayout);
       Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
       icount = 0 ;
    }

    private void vProc_Show_BME_Info()
    {
        vChangeDisplayPage(mBodyInfoLayoutE);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_Info()
    {
        vChangeDisplayPage(mTitleImageInfoLayout);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_InfoE()
    {
        vChangeDisplayPage(mTitleImageInfoLayoutE);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_CloudUpdatFail()
    {
        vChangeDisplayPage(mCloudUploadFail);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_CloudUpdatFail02()
    {
        vChangeDisplayPage(mCloudUploadFail02);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_CloudWifiFail01()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mCloudUploadFail02.onDestroy();
                mCloudUploadFail02.display();
            }
        });
        Dialog_UI_Info.vDialogUiInfo_SetWifiState(1);

        String stitle01 = LanguageData.s_get_string(mContext, R.string.connection_lost).toUpperCase();
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.data_not_uploaded_to_circlecloudgo).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.wifi_disconect_icon, -1, stitle01, null, sinfo01, null, null, ImageView.ScaleType.CENTER_INSIDE);

        vChangeDisplayPage(mCloudUploadFail02);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Show_Test1()
    {
        vChangeDisplayPage(mCloudUploadTest1);
        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE ;
        icount = 0 ;
    }

    private void vProc_Cloud_ReSend()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.Dialog_layer);
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_Resend).start();
            }
        });

        Dialog_UI_Info.Dialog_mode = DialogState.PROC_DIALOG_CLOUD_RESEND_CHECK;
    }

    private void vProc_Cloud_ReSend_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });

            iretry_count++;
            if(iServerResponse != 0)
            {
                switch (Dialog_UI_Info.v_Get_Dialog_Cloud_retryCmd())
                {
                    case cloudglobal.iINS_END_WKO_REC04 :
                        String stitle01 = LanguageData.s_get_string(mContext, R.string.connection_lost)
                                + " : " + Rtx_TranslateValue.sInt2String(iretry_count) + " times.";
                        String sinfo01 = LanguageData.s_get_string(mContext, R.string.data_upload_unsuccessful);

                        Dialog_UI_Info.v_tist_Dialog(R.drawable.wifi_disconect_icon, -1, stitle01, null, sinfo01, null, null, ImageView.ScaleType.CENTER_INSIDE);
                        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_TEST1);
                        break;
                    default:
                        Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE;
                        break;
                }
            }
            else
            {
                Dialog_UI_Info.Dialog_mode = DialogState.PROC_IDLE;
            }

        }
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.Dialog_layer);
            }
        });

        Dialog_UI_Info.Dialog_mode = DialogState.PROC_INIT ;
        mNextState = DialogState.PROC_NULL;
    }

    private void vProc_Idle()
    {
        if(mNextState != DialogState.PROC_NULL)
        {
            Dialog_UI_Info.Dialog_mode = mNextState;
            mNextState = DialogState.PROC_NULL;
        }
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.Dialog_layer);
                mMainActivity.addExerciseView(ExerciseData.Dialog_layer, layout);
                layout.setClickable(true);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != Dialog_UI_Info.Dialog_mode)
        {
            //Log.e("Jerry", "[DialogProc] Dialog_UI_Info.Dialog_mode = " + Dialog_UI_Info.Dialog_mode);
            tempState = Dialog_UI_Info.Dialog_mode;
        }

        switch( Dialog_UI_Info.Dialog_mode )
        {
            case PROC_INIT                             :{   vProc_Init();                          break;  }
            case PROC_DIALOG_BM_INFO                   :{   vProc_Show_BM_Info();                          break;  }
            case PROC_DIALOG_BME_INFO                  :{   vProc_Show_BME_Info();                          break;  }
            case PROC_DIALOG_INFO                      :{   vProc_Show_Info();                          break;  }
            case PROC_DIALOG_INFOE                     :{   vProc_Show_InfoE();                          break;  }
            case PROC_DIALOG_CLOUD_UPDATE_FAIL         :{   vProc_Show_CloudUpdatFail();                          break;  }
            case PROC_DIALOG_CLOUD_UPDATE_FAIL02       :{   vProc_Show_CloudUpdatFail02();                          break;  }
            case PROC_DIALOG_CLOUD_WIFI_FAIL01          :{  vProc_Show_CloudWifiFail01();           break;  }

            case PROC_DIALOG_TEST1                     :{   vProc_Show_Test1();                          break;  }
            case PROC_DIALOG_CLOUD_RESEND              :{   vProc_Cloud_ReSend();                          break;  }
            case PROC_DIALOG_CLOUD_RESEND_CHECK        :{   vProc_Cloud_ReSend_Check();                          break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////
    public Runnable CloudRunnable_Resend = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            if(DEBUG) Log.e(TAG, "=====CloudRunnable_Resend=====" + Dialog_UI_Info.v_Get_Dialog_Cloud_retryCmd());
            switch (Dialog_UI_Info.v_Get_Dialog_Cloud_retryCmd())
            {
                case cloudglobal.iINS_END_WKO_REC04 :
                    iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_SendExercise_Data();
                    break;
                default:
                    iServerResponse = 0;
                    break;
            }

            bServerResponseFlag = true;
        }
    };

}
