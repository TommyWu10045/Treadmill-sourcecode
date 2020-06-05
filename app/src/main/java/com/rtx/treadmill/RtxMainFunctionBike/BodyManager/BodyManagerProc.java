package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.retonix.circlecloud.Cloud_14_GET_BDY_HIS;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.CloudDataStruct.HistoryData;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chasechang on 3/27/17.
 */

public class BodyManagerProc {
    private String TAG = "Jerry" ;

    private MainActivity mMainActivity ;

    private BodyManagerState mState = BodyManagerState.PROC_INIT ;
    private BodyManagerState mNextState = BodyManagerState.PROC_NULL ;
    private BodyManagerState tempState = BodyManagerState.PROC_NULL ;

    private MainDefaultLayout            mMainDefaultLayout;
    private Main01Layout                 mMain01Layout;
    private Main02Layout                 mMain02Layout;
    private MuscleLayout                 mMuscleLayout;
    private BodyFatLayout                 mBodyFatLayout;
    private AllViewLayout                 mAllViewLayout;
    private InputLayout                 mInputLayout;
    private HistoryLayout                 mHistoryLayout;

    private MainDefaultLayoutE            mMainDefaultLayoutE;
    private Main01LayoutE                 mMain01LayoutE;
    private Main02LayoutE                 mMain02LayoutE;
    private MuscleLayoutE                 mMuscleLayoutE;
    private BodyFatLayoutE                 mBodyFatLayoutE;
    private AllViewLayoutE                 mAllViewLayoutE;
    private InputLayoutE                 mInputLayoutE;
    private HistoryLayoutE                 mHistoryLayoutE;

    private int imode ;
    private int iedit_mode = 0; //0 view; 1 edit; 2 cxercise
    private boolean blogin = false;
    private boolean bupload = false;
    private String scnt = "20";

    TreeMap<String, String[]> mHistTree = CloudDataStruct.BodyIndexData_14.tree_clound_cmd14_result;
    TreeMap<String, HistoryData> tree_clound_cmd14_his ;
    private ArrayList<HistoryData> historypoints = CloudDataStruct.BodyIndexData_14.historypoints_list;


    public int ipage_list[][] = {//item name
            {
                Cloud_05_DB_BDY_IDX_REC.Input.fWeight,
                Cloud_05_DB_BDY_IDX_REC.Input.fBDY_FAT,
                Cloud_05_DB_BDY_IDX_REC.Input.fBMI,
                Cloud_05_DB_BDY_IDX_REC.Input.fBMR,
                Cloud_05_DB_BDY_IDX_REC.Input.fTAL_BDY_WAT,
                Cloud_05_DB_BDY_IDX_REC.Input.fVCL_FAT_RTG,
                Cloud_05_DB_BDY_IDX_REC.Input.fBON_MAS
            },
            {
                Cloud_05_DB_BDY_IDX_REC.Input.fITR_WAT,
                Cloud_05_DB_BDY_IDX_REC.Input.fECR_WAT,
                Cloud_05_DB_BDY_IDX_REC.Input.fProtein,
                Cloud_05_DB_BDY_IDX_REC.Input.fMinerals,
                Cloud_05_DB_BDY_IDX_REC.Input.fWIT_HI_RAO,
                Cloud_05_DB_BDY_IDX_REC.Input.fOBY_DGE
            }
    };

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public BodyManagerProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = BodyManagerState.PROC_INIT ;

        if(mMainDefaultLayout == null)           { mMainDefaultLayout = new MainDefaultLayout(mMainActivity.mContext , mMainActivity); }
        if(mMain01Layout == null)                { mMain01Layout = new Main01Layout(mMainActivity.mContext , mMainActivity); }
        if(mMain02Layout == null)                { mMain02Layout = new Main02Layout(mMainActivity.mContext , mMainActivity); }
        if(mMuscleLayout == null)                { mMuscleLayout = new MuscleLayout(mMainActivity.mContext , mMainActivity); }
        if(mBodyFatLayout == null)               { mBodyFatLayout = new BodyFatLayout(mMainActivity.mContext , mMainActivity); }
        if(mAllViewLayout == null)               { mAllViewLayout = new AllViewLayout(mMainActivity.mContext , mMainActivity); }
        if(mInputLayout == null)                 { mInputLayout = new InputLayout(mMainActivity.mContext , mMainActivity); }
        if(mHistoryLayout == null)               { mHistoryLayout = new HistoryLayout(mMainActivity.mContext , mMainActivity); }

        if(mMainDefaultLayoutE == null)           { mMainDefaultLayoutE = new MainDefaultLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mMain01LayoutE == null)                { mMain01LayoutE = new Main01LayoutE(mMainActivity.mContext , mMainActivity); }
        if(mMain02LayoutE == null)                { mMain02LayoutE = new Main02LayoutE(mMainActivity.mContext , mMainActivity); }
        if(mMuscleLayoutE == null)                { mMuscleLayoutE = new MuscleLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mBodyFatLayoutE == null)               { mBodyFatLayoutE = new BodyFatLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mAllViewLayoutE == null)               { mAllViewLayoutE = new AllViewLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mInputLayoutE == null)                 { mInputLayoutE = new InputLayoutE(mMainActivity.mContext , mMainActivity); }
        if(mHistoryLayoutE == null)               { mHistoryLayoutE = new HistoryLayoutE(mMainActivity.mContext , mMainActivity); }

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(BodyManagerState nextState)
    {
        mNextState = nextState;
    }

    public void vSet_Select(int imode)
    {
        this.imode = imode;
    }

    public void vSet_Edit_mode(int imode)
    {
        this.iedit_mode = imode;
    }

    public void vSet_Upload_flag(boolean flag)
    {
        this.bupload = flag;
    }

    public boolean bGet_ServerResponseFlag() { return bServerResponseFlag; }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        CloudDataStruct.BodyIndexData_05.clear();
        bupload = false;
        if(!ExerciseData.b_is_exercising())
        {
            iedit_mode = 0;
        }
        else
        {
            iedit_mode = 2;
        }

        blogin = CloudDataStruct.CloudData_20.is_log_in();

        if(mNextState == BodyManagerState.PROC_NULL)
        {
            mState = BodyManagerState.PROC_INIT_PAGE;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Default() {
        if(iedit_mode <= 1)
        {
            vChangeDisplayPage(mMainDefaultLayout);
        }
        else
        {
            vChangeDisplayPage(mMainDefaultLayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Main01() {
        if(iedit_mode <= 1)
        {
            mMain01Layout.v_setSelect(blogin, iedit_mode);
            vChangeDisplayPage(mMain01Layout);
        }
        else
        {
            mMain01LayoutE.v_setSelect(blogin, iedit_mode);
            vChangeDisplayPage(mMain01LayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Main02() {
        if(iedit_mode <= 1)
        {
            mMain02Layout.v_setSelect(blogin, iedit_mode);
            vChangeDisplayPage(mMain02Layout);
        }
        else
        {
            mMain02LayoutE.v_setSelect(blogin, iedit_mode);
            vChangeDisplayPage(mMain02LayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_AllView() {
        if(iedit_mode <= 1)
        {
            mAllViewLayout.v_setSelect(blogin, imode);
            vChangeDisplayPage(mAllViewLayout);
        }
        else
        {
            mAllViewLayoutE.v_setSelect(blogin, imode);
            vChangeDisplayPage(mAllViewLayoutE);
        }
         mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Input() {
        if(iedit_mode <= 1)
        {
            mInputLayout.v_setSelect(imode);
            vChangeDisplayPage(mInputLayout);
        }
        else
        {
            mInputLayoutE.v_setSelect(imode);
            vChangeDisplayPage(mInputLayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_His() {
        if(iedit_mode <= 1)
        {
            mHistoryLayout.v_setSelect(imode);
            vChangeDisplayPage(mHistoryLayout);
        }
        else
        {
            mHistoryLayoutE.v_setSelect(imode);
            vChangeDisplayPage(mHistoryLayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Muscle() {
        if(iedit_mode <= 1)
        {
            mMuscleLayout.v_setSelect(imode);
            vChangeDisplayPage(mMuscleLayout);
        }
        else
        {
            mMuscleLayoutE.v_setSelect(imode);
            vChangeDisplayPage(mMuscleLayoutE);
        }
        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Bodyfat() {
        if(iedit_mode <= 1)
        {
            mBodyFatLayout.v_setSelect(imode);
            vChangeDisplayPage(mBodyFatLayout);
        }
        else
        {
            mBodyFatLayout.v_setSelect(imode);
            vChangeDisplayPage(mBodyFatLayoutE);
        }


        mState = BodyManagerState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != BodyManagerState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = BodyManagerState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = BodyManagerState.PROC_INIT ;
        mNextState = BodyManagerState.PROC_NULL;
    }


    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vChangeDisplayPage(final Rtx_BaseLayoutE layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    private void vProc_Cloud_Current()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_All).start();
            }
        });

        mState = BodyManagerState.PROC_CLOUD_GET_CHECK;
    }

    private void vProc_Cloud_Current_Check()
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

            v_Machine_IBA_ClearData();

            mState = BodyManagerState.PROC_SHOW_PAGE01;
        }
    }

    private void vProc_Cloud_History()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_His).start();
            }
        });

        mState = BodyManagerState.PROC_CLOUD_HIS_CHECK;
    }

    private void vProc_Cloud_History_Check()
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
            mState = BodyManagerState.PROC_SHOW_ALLVIEW;
        }
    }

    private void vProc_Cloud_History_Refresh()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                new Thread(CloudRunnable_His_refresh).start();
            }
        });

        mState = BodyManagerState.PROC_CLOUD_HIS_REFRESH_CHECK;
    }

    private void vProc_Cloud_History_Refresh_Check()
    {
        if(bServerResponseFlag)
        {
            if(iedit_mode <= 1)
            {
                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mHistoryLayout.v_list_refresh();
                    }
                });
            }
            else
            {
                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        mHistoryLayoutE.v_list_refresh();
                    }
                });
            }

            mState = BodyManagerState.PROC_IDLE ;
        }
    }

    private void vProc_Cloud_Update()
    {
        if(bupload) {
            bServerResponseFlag = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    new Thread(CloudRunnable_Update).start();
                }
            });
            mState = BodyManagerState.PROC_CLOUD_SET_CHECK;
        }
        else
        {
            vMainPage_choice();
        }
        this.bupload = false;
    }

    private void vProc_Cloud_Update_Check()
    {
        if(bServerResponseFlag)
        {
            vMainPage_choice();

            if (iServerResponse == 0) {
                v_Machine_IBA_Upload_Success();
                v_Machine_IBA_ClearData();
            }
            else
            {
                CloudDataStruct.BodyIndexData_05.v_BodyIndex_Undo();
            }
        }
    }

    //////////////////////////////////////////////////
    private void v_Machine_IBA_ClearData()
    {
        int iLoop;
        int istr_list[];
        int imch_type = BodyManagerFunc.iget_body_machine_type();

        istr_list = ipage_list[1];

        if(imch_type == 0 )//Circle
        {
            for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
                CloudDataStruct.BodyIndexData_05.sdata_in[istr_list[iLoop]] = "0";
            }
        }
        else //etc.
        {
        }
    }

    private void v_Machine_IBA_Upload_Success()
    {
        int iLoop;
        int isel_mode;
        int istr_list[];
        String skey = CloudDataStruct.BodyIndexData_05.sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.dMSR_DTE];
        String sdate = Rtx_Calendar.s_trans_DateTime_Str(1, "M/dd/yyyy", "yyyy-MM-dd", skey, 0, 0);
        String sval;

        istr_list = ipage_list[0];
        for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
            isel_mode = istr_list[iLoop];
            tree_clound_cmd14_his = CloudDataStruct.BodyIndexData_14.s_get_history_tree(isel_mode);
            sval = CloudDataStruct.BodyIndexData_05.sdata_in[isel_mode];

            HistoryData sHdata = new HistoryData(sdate, sval);
            tree_clound_cmd14_his.put(skey, sHdata);
        }

        istr_list = ipage_list[1];
        for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
            isel_mode = istr_list[iLoop];
            tree_clound_cmd14_his = CloudDataStruct.BodyIndexData_14.s_get_history_tree(isel_mode);
            sval = CloudDataStruct.BodyIndexData_05.sdata_in[isel_mode];

            HistoryData sHdata = new HistoryData(sdate, sval);
            tree_clound_cmd14_his.put(skey, sHdata);
        }

        CloudDataStruct.BodyIndexData_05.v_BodyIndex_Keep();

    }
    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[bodymanagerProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                    :{   vProc_Init();                          break;  }
            case PROC_INIT_PAGE                               :{   vProc_ShowPage_Default();              break;  }
            case PROC_SHOW_PAGE01                             :{   vProc_ShowPage_Main01();               break;  }
            case PROC_SHOW_PAGE02                             :{   vProc_ShowPage_Main02();               break;  }
            case PROC_SHOW_ALLVIEW                            :{   vProc_ShowPage_AllView();              break;  }
            case PROC_SHOW_INPUT                              :{   vProc_ShowPage_Input();                break;  }
            case PROC_SHOW_HISTORY                            :{   vProc_ShowPage_His();                  break;  }
            case PROC_SHOW_MUSCLE                             :{   vProc_ShowPage_Muscle();               break;  }
            case PROC_SHOW_BODYFAT                            :{   vProc_ShowPage_Bodyfat();              break;  }

            case PROC_CLOUD_BD_GET                            :{   vProc_Cloud_Current();                       break;  }
            case PROC_CLOUD_GET_CHECK                         :{   vProc_Cloud_Current_Check();                 break;  }
            case PROC_CLOUD_HIS_GET                           :{   vProc_Cloud_History();                       break;  }
            case PROC_CLOUD_HIS_CHECK                         :{   vProc_Cloud_History_Check();                 break;  }
            case PROC_CLOUD_HIS_REFRESH                       :{   vProc_Cloud_History_Refresh();               break;  }
            case PROC_CLOUD_HIS_REFRESH_CHECK                 :{   vProc_Cloud_History_Refresh_Check();         break;  }
            case PROC_CLOUD_BD_SET                            :{   vProc_Cloud_Update();                        break;  }
            case PROC_CLOUD_SET_CHECK                         :{   vProc_Cloud_Update_Check();                  break;  }

            case PROC_IDLE                                    :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                    :{   vProc_Exit();                          break;  }
            default                                           :{   vProc_Idle();                          break;  }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void padding_history_list(Context mContext, int imode)
    {
        for(Map.Entry<String,String[]> entry : mHistTree.entrySet()) {
            String skey = Rtx_Calendar.s_trans_DateTime_Str(0, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", entry.getKey(), 0, 0);
            String[] value = entry.getValue();
            if(value != null)
            {
                String sdate = Rtx_Calendar.s_trans_DateTime_Str(1, "M/dd/yyyy", "yyyy-MM-dd HH:mm:ss", value[Cloud_14_GET_BDY_HIS.Output.MSR_DT], 0, 0);
                String sval = BodyManagerFunc.s_get_drawdata_string(mContext, imode, value[Cloud_14_GET_BDY_HIS.Output.OUTTAG]);

                HistoryData sHdata = new HistoryData(sdate, sval);
                tree_clound_cmd14_his.put(skey, sHdata);
            }
        }

    }

    private void historypoints_refresh(Context mContext, int imode)
    {
        historypoints.clear();

        if(tree_clound_cmd14_his != null)
        {
            for(Map.Entry<String,HistoryData> entry : tree_clound_cmd14_his.entrySet()) {
                HistoryData value = entry.getValue();
                if(value != null)
                {
                    historypoints.add(value);
                }
            }
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_All = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            String sMSR_DTE = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd", null, null, 1, 0);

            if (sMSR_DTE != null && CloudDataStruct.BodyIndexData_15.Set_input(sMSR_DTE)) {
                iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetBodyIndex();
            }

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_His = new Runnable() {
        @Override
        public void run()
        {

            iServerResponse = -1;

            tree_clound_cmd14_his = CloudDataStruct.BodyIndexData_14.s_get_history_tree(imode);
            String type = BodyManagerFunc.s_get_bodymanage_history_key(mMainActivity.mContext, imode);
            String date;

            if(tree_clound_cmd14_his != null && tree_clound_cmd14_his.size() <= 0)
            {
                date = Rtx_Calendar.s_trans_DateTime_Str(2, "yyyy-MM-dd", null, null, 1, 0);
                //Log.e(TAG, "CloudRunnable_His=" + date);
                if(CloudDataStruct.BodyIndexData_14.Set_input(type, date, scnt)) {
                    iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetBodyIndexHistory();
                    if (iServerResponse == 0) {
                        padding_history_list(mMainActivity.mContext, imode);
                    }
                }
            }

            historypoints_refresh(mMainActivity.mContext, imode);

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_His_refresh = new Runnable() {
        @Override
        public void run()
        {

            iServerResponse = -1;

            String type = BodyManagerFunc.s_get_bodymanage_history_key(mMainActivity.mContext, imode);
            String date;

            tree_clound_cmd14_his = CloudDataStruct.BodyIndexData_14.s_get_history_tree(imode);

            if(tree_clound_cmd14_his != null && tree_clound_cmd14_his.size() > 0)
            {
                date = tree_clound_cmd14_his.lastKey();
                //Log.e(TAG, "CloudRunnable_His_refresh=" + date);
                if(CloudDataStruct.BodyIndexData_14.Set_input(type, date, scnt)) {
                    iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_GetBodyIndexHistory();
                    if (iServerResponse == 0) {
                        padding_history_list(mMainActivity.mContext, imode);
                    }
                }
            }

            historypoints_refresh(mMainActivity.mContext, imode);

            bServerResponseFlag = true;
        }
    };

    public Runnable CloudRunnable_Update = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_SetBodyIndex();

            bServerResponseFlag = true;
        }
    };

    /////////////////////////////////////////////
    private void vMainPage_choice()
    {
        int iLoop ;
        int istr_list[];

        mState = BodyManagerState.PROC_SHOW_PAGE01;

        istr_list = ipage_list[0];
        for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
            if (imode == istr_list[iLoop]) {
                mState = BodyManagerState.PROC_SHOW_PAGE01;
                break;
            }
        }

        istr_list = ipage_list[1];
        for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
            if (imode == istr_list[iLoop]) {
                mState = BodyManagerState.PROC_SHOW_PAGE02;
                break;
            }
        }
    }

}
