package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.util.Log;

import com.retonix.circlecloud.Cloud_30_GET_BLT_BOD;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chasechang on 3/27/17.
 */

public class MyGymProc {
    private String TAG = "MyGymProc" ;

    final int MODE_BULLETIN = 0;
    final int MODE_CLASS = 1;
    final int MODE_MY_CLASS = 2;

    private int iModeSelect = MODE_BULLETIN;

    private MainActivity mMainActivity ;

    private MyGymState mState = MyGymState.PROC_INIT ;
    private MyGymState mNextState = MyGymState.PROC_NULL ;
    private MyGymState tempState = MyGymState.PROC_NULL ;
    private MyGymState mPreState = MyGymState.PROC_NULL ;

    private MyGymMainLayout                             mMyGymMainLayout;
    private MyGym_Bulletin_DetailInfoLayout             mMyGym_Bulletin_DetailInfoLayout;
    private MyGym_Class_DetailInfoLayout                mMyGym_Class_DetailInfoLayout;

    private MyGymMainLayout                             mMyGymMainLayout_Normal;
    private MyGym_Bulletin_DetailInfoLayout             mMyGym_Bulletin_DetailInfoLayout_Normal;
    private MyGym_Class_DetailInfoLayout                mMyGym_Class_DetailInfoLayout_Normal;

    private MyGymMainLayout_Exercise                    mMyGymMainLayout_Exercise;
    private MyGym_Bulletin_DetailInfoLayout_Exercise    mMyGym_Bulletin_DetailInfoLayout_Exercise;
    private MyGym_Class_DetailInfoLayout_Exercise       mMyGym_Class_DetailInfoLayout_Exercise;

    private int         iCount = 0;

    private boolean bServerResponseFlag_GetBulletinList = false;
    private boolean bServerResponseFlag_GetBulletinDetailInfo = false;
    private boolean bServerResponseFlag_GetClassInfo = false;
    private boolean bServerResponseFlag_GetMyClassInfo = false;
    private boolean bServerResponseFlag_CheckMyClassExist = false;
    private boolean bServerResponseFlag_GetClassDetailInfo = false;
    private boolean bServerResponseFlag_AddClass = false;
    private boolean bServerResponseFlag_DeleteClass = false;

    private int     iServerResponseResult_GetBulletinList = -1;
    private int     iServerResponseResult_GetBulletinDetailInfo = -1;
    private int     iServerResponseResult_GetClassInfo = -1;
    private int     iServerResponseResult_GetMyClassInfo = -1;
    private int     iServerResponseResult_CheckMyClassExist = -1;
    private int     iServerResponseResult_GetClassDetailInfo = -1;
    private int     iServerResponseResult_AddClass = -1;
    private int     iServerResponseResult_DeleteClass = -1;

    private boolean bAlreadyLoadBulletinList = false;

    private Calendar    cal_StartDate;
    private Calendar    cal_EndDate;
    private Calendar    cal_DetailDate;

    private String      sBulletinID = "";
    private String      sClassID = "";
    private String      sBkgSeq = "";

    private Thread      thread_LoadingBitmap = null;
    private boolean     bThreadRun_LoadingBitmap = true;
    private boolean     bBitmapReady = false;

    ArrayList<Calendar>   list_SearchWeek_Class;
    ArrayList<Calendar>   list_SearchWeek_MyClass;

    public MyGymProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = MyGymState.PROC_INIT ;

        //Layout
        if(mMyGymMainLayout_Normal == null)                   { mMyGymMainLayout_Normal = new MyGymMainLayout(mMainActivity.mContext , mMainActivity); }
        if(mMyGymMainLayout_Exercise == null)                 { mMyGymMainLayout_Exercise = new MyGymMainLayout_Exercise(mMainActivity.mContext , mMainActivity); }

        if(mMyGym_Bulletin_DetailInfoLayout_Normal == null)   { mMyGym_Bulletin_DetailInfoLayout_Normal = new MyGym_Bulletin_DetailInfoLayout(mMainActivity.mContext , mMainActivity); }
        if(mMyGym_Bulletin_DetailInfoLayout_Exercise == null) { mMyGym_Bulletin_DetailInfoLayout_Exercise = new MyGym_Bulletin_DetailInfoLayout_Exercise(mMainActivity.mContext , mMainActivity); }

        if(mMyGym_Class_DetailInfoLayout_Normal == null)      { mMyGym_Class_DetailInfoLayout_Normal = new MyGym_Class_DetailInfoLayout(mMainActivity.mContext , mMainActivity); }
        if(mMyGym_Class_DetailInfoLayout_Exercise == null)    { mMyGym_Class_DetailInfoLayout_Exercise = new MyGym_Class_DetailInfoLayout_Exercise(mMainActivity.mContext , mMainActivity); }



        if(list_SearchWeek_Class == null)                     { list_SearchWeek_Class = new ArrayList<Calendar>(); }
        if(list_SearchWeek_MyClass == null)                   { list_SearchWeek_MyClass = new ArrayList<Calendar>(); }

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(MyGymState nextState)
    {
        mNextState = nextState;
    }

    public void vSetPreState(MyGymState preState)
    {
        mPreState = preState;
    }

    public MyGymState vGerPreState()
    {
        return mPreState;
    }

    public void vInit()
    {
        iModeSelect = MODE_BULLETIN;

        MyGymState mState = MyGymState.PROC_INIT ;
        MyGymState mNextState = MyGymState.PROC_NULL ;
        MyGymState tempState = MyGymState.PROC_NULL ;
        MyGymState mPreState = MyGymState.PROC_NULL ;

        iCount = 0;

        bServerResponseFlag_GetBulletinList = false;
        bServerResponseFlag_GetBulletinDetailInfo = false;
        bServerResponseFlag_GetClassInfo = false;
        bServerResponseFlag_GetMyClassInfo = false;
        bServerResponseFlag_CheckMyClassExist = false;
        bServerResponseFlag_GetClassDetailInfo = false;
        bServerResponseFlag_AddClass = false;
        bServerResponseFlag_DeleteClass = false;

        iServerResponseResult_GetBulletinList = -1;
        iServerResponseResult_GetBulletinDetailInfo = -1;
        iServerResponseResult_GetClassInfo = -1;
        iServerResponseResult_GetMyClassInfo = -1;
        iServerResponseResult_CheckMyClassExist = -1;
        iServerResponseResult_GetClassDetailInfo = -1;
        iServerResponseResult_AddClass = -1;
        iServerResponseResult_DeleteClass = -1;

        bAlreadyLoadBulletinList = false;

        cal_StartDate = GlobalData.getInstance();
        cal_EndDate = GlobalData.getInstance();
        cal_DetailDate = GlobalData.getInstance();

        sBulletinID = "";
        sClassID = "";
        sBkgSeq = "";

        bBitmapReady = false;

        if(list_SearchWeek_Class != null)
        {
            list_SearchWeek_Class.clear();
        }

        if(list_SearchWeek_MyClass != null)
        {
            list_SearchWeek_MyClass.clear();
        }
    }
    /* ------------------------------------------------------------------------ */

    private void vProc_Init()
    {
        vInit();

        vClearClassList();
        vClearMyClassList();

        if(ExerciseData.b_is_exercising())
        {
            mMyGymMainLayout = mMyGymMainLayout_Exercise;
            mMyGym_Bulletin_DetailInfoLayout = mMyGym_Bulletin_DetailInfoLayout_Exercise;
            mMyGym_Class_DetailInfoLayout = mMyGym_Class_DetailInfoLayout_Exercise;
        }
        else
        {
            mMyGymMainLayout = mMyGymMainLayout_Normal;
            mMyGym_Bulletin_DetailInfoLayout = mMyGym_Bulletin_DetailInfoLayout_Normal;
            mMyGym_Class_DetailInfoLayout = mMyGym_Class_DetailInfoLayout_Normal;
        }

        if(ExerciseData.b_is_exercising())
        {
            if(mMyGymMainLayout_Exercise != null)
            {
                mMyGymMainLayout_Exercise.vSetWeekSeletIndex(0);
                mMyGymMainLayout_Exercise.vSetModeSelect(0);
            }
        }
        else
        {
            if(mMyGymMainLayout != null)
            {
                mMyGymMainLayout.vSetWeekSeletIndex(0);
                mMyGymMainLayout.vSetModeSelect(0);
            }
        }

        thread_LoadingBitmap = new Thread(Runnable_LoadingBitmap);
        thread_LoadingBitmap.start();
        bAlreadyLoadBulletinList = false;

        mState = MyGymState.PROC_SHOW_PAGE_MAIN;
    }

    private void vProc_Idle()
    {
        vUpdateBitmap();

        if(mNextState != MyGymState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = MyGymState.PROC_NULL;
        }
    }

    private void vProc_Exit()
    {

        bThreadRun_LoadingBitmap = false;
        mMyGymMainLayout.mLayout_BulletinList = null;

        mMainActivity.mMainProcBike.vSetIdleState();
        mState = MyGymState.PROC_INIT ;
        mNextState = MyGymState.PROC_NULL;
    }

    private void vProc_ShowPage_Main()
    {
        vChangeDisplayPage(mMyGymMainLayout);

        if(bAlreadyLoadBulletinList == false)
        {
            bAlreadyLoadBulletinList = true;
        }

        mState = MyGymState.PROC_IDLE ;
    }

    private void vProc_ShowPage_BulletinDetailPage()
    {
        vChangeDisplayPage(mMyGym_Bulletin_DetailInfoLayout);

        mState = MyGymState.PROC_IDLE ;
    }

    private void vProc_ShowPage_ClassInfoDetailPage()
    {
        vChangeDisplayPage(mMyGym_Class_DetailInfoLayout);

        mState = MyGymState.PROC_IDLE ;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vUpdateBitmap()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(bGetBitmapLoadReady())
                {
                    if(mMyGymMainLayout != null)
                    {
                        mMyGymMainLayout.updateBitmap();
                    }

                    if(mMyGym_Bulletin_DetailInfoLayout != null)
                    {
                        mMyGym_Bulletin_DetailInfoLayout.updateBitmap();
                    }

                    if(mMyGym_Class_DetailInfoLayout != null)
                    {
                        mMyGym_Class_DetailInfoLayout.updateBitmap();
                    }

                    vSetBitmapLoadReady(false);
                }
            }
        });
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

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetSearchDate(Calendar cal_Start, Calendar cal_End)
    {
        cal_StartDate = (Calendar)cal_Start.clone();
        cal_EndDate = (Calendar)cal_End.clone();

//        Log.e("Jerry","cal_StartDate = " + Rtx_Calendar.sCalendar2Str(cal_StartDate,"yyyy-MM-dd"));
//        Log.e("Jerry","cal_EndDate = " + Rtx_Calendar.sCalendar2Str(cal_EndDate,"yyyy-MM-dd"));
    }

    protected void vSetDetailDate(Calendar cal)
    {
        cal_DetailDate = (Calendar)cal.clone();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void run() {

        if(tempState != mState)
        {
            Log.e("Jerry", "[MyGym] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                  :{   vProc_Init();                              break;  }

            case PROC_SHOW_PAGE_MAIN                        :{   vProc_ShowPage_Main();                     break;  }
            case PROC_SHOW_PAGE_BULLETIN_DETAIL             :{   vProc_ShowPage_BulletinDetailPage();       break;  }
            case PROC_SHOW_PAGE_CLASS_DETAIL                :{   vProc_ShowPage_ClassInfoDetailPage();      break;  }

            case PROC_CLOUD_GET_BULLETIN_LIST               :{   vProc_GetBulletinList();                   break;  }
            case PROC_CLOUD_GET_BULLETIN_LIST_CHECK         :{   vProc_GetBulletinList_Check();             break;  }
            case PROC_CLOUD_GET_BULLETIN_DETAIL_INFO        :{   vProc_GetBulletinDetailInfo();             break;  }
            case PROC_CLOUD_GET_BULLETIN_DETAIL_INFO_CHECK  :{   vProc_GetBulletinDetailInfo_Check();       break;  }
            case PROC_CLOUD_GET_CLASS_INFO_LIST             :{   vProc_GetClassInfoList();                  break;  }
            case PROC_CLOUD_GET_CLASS_INFO_LIST_CHECK       :{   vProc_GetClassInfoList_Check();            break;  }
            case PROC_CLOUD_CHECK_MY_CLASS_EXIST            :{   vProc_CheckMyClassExist();                 break;  }
            case PROC_CLOUD_CHECK_MY_CLASS_EXIST_CHECK      :{   vProc_CheckMyClassExist_Check();           break;  }
            case PROC_CLOUD_GET_MY_CLASS_INFO_LIST          :{   vProc_GetMyClassInfoList();                break;  }
            case PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK    :{   vProc_GetMyClassInfoList_Check();          break;  }
            case PROC_CLOUD_GET_CLASS_DETAIL_INFO           :{   vProc_GetClassDetailInfo();                break;  }
            case PROC_CLOUD_GET_CLASS_DETAIL_INFO_CHECK     :{   vProc_GetClassDetailInfo_Check();          break;  }
            case PROC_CLOUD_ADD_CLASS                       :{   vProc_AddClass();                          break;  }
            case PROC_CLOUD_ADD_CLASS_CHECK                 :{   vProc_AddClass_Check();                    break;  }
            case PROC_CLOUD_DELETE_CLASS                    :{   vProc_DeleteClass();                       break;  }
            case PROC_CLOUD_DELETE_CLASS_CHECK              :{   vProc_DeleteClass_Check();                 break;  }
            case PROC_IDLE                                  :{   vProc_Idle();                              break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                              break;  }
            default                                         :{   vProc_Idle();                              break;  }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vProc_GetBulletinList()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetBulletinList).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_GET_BULLETIN_LIST_CHECK;
    }

    private void vProc_GetBulletinList_Check()
    {
        if(bServerResponseFlag_GetBulletinList)
        {
            bServerResponseFlag_GetBulletinList = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetBulletinList == 0)
                    {
                        //Success
                        mMyGymMainLayout.setBulletinDataList(CloudDataStruct.CloudData_29.list_CloudBulletinInfo);
                        Log.e("Jerry","GetBulletinList Success!");
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","GetBulletinList Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });

            mState = MyGymState.PROC_IDLE;
        }
    }

    private void vProc_GetBulletinDetailInfo()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetBulletinDetailInfo).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_GET_BULLETIN_DETAIL_INFO_CHECK;
    }

    private void vProc_GetBulletinDetailInfo_Check()
    {
        if(bServerResponseFlag_GetBulletinDetailInfo)
        {
            bServerResponseFlag_GetBulletinDetailInfo = false;
            mState = MyGymState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetBulletinDetailInfo == 0)
                    {
                        //Success

                        if(mMyGym_Bulletin_DetailInfoLayout != null)
                        {
                            Calendar cDate = Rtx_Calendar.cStr2Calendar(CloudDataStruct.CloudData_30.sdata_out[Cloud_30_GET_BLT_BOD.Output.BLT_AUC_DT],"yyyy-MM-dd");

                            mMyGym_Bulletin_DetailInfoLayout.vSetData(
                                    false,
                                    CloudDataStruct.CloudData_30.sdata_out[Cloud_30_GET_BLT_BOD.Output.BLT_TIL],
                                    cDate,
                                    CloudDataStruct.CloudData_30.sdata_out[Cloud_30_GET_BLT_BOD.Output.BLT_COT],
                                    CloudDataStruct.CloudData_30.sdata_out[Cloud_30_GET_BLT_BOD.Output.BLT_PHO]);
                        }

                        Log.e("Jerry","GetBulletinDetailInfo Success!");
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","GetBulletinDetailInfo Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_GetClassInfoList()
    {
        if(bIsAlreadySearched(list_SearchWeek_Class,cal_StartDate))
        {
            bServerResponseFlag_GetClassInfo = true;
            iServerResponseResult_GetClassInfo = 0;
        }
        else
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.showProgressBar();
                    mMyGymMainLayout.mLayout_ClassCalendar.vEnableButton(false);

                    new Thread(CloudRunnable_GetClassInfoList).start();
                }
            });
        }


        mState = MyGymState.PROC_CLOUD_GET_CLASS_INFO_LIST_CHECK;
    }

    private void vProc_GetClassInfoList_Check()
    {
        if(bServerResponseFlag_GetClassInfo)
        {
            bServerResponseFlag_GetClassInfo = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetClassInfo == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_GetClassInfoList_Check Success!");

                        mMyGymMainLayout.mLayout_ClassCalendar.layout_ClassList.removeAllViews();
                        mMyGymMainLayout.mLayout_ClassCalendar.layout_ClassList.vImportClassBlockInfo(cal_StartDate,CloudDataStruct.CloudData_31.list_CloudClassInfo);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_GetClassInfoList_Check Fail!");
                    }

                    mMyGymMainLayout.mLayout_ClassCalendar.vEnableButton(true);
                    mMainActivity.closeProgressBar();
                    mState = MyGymState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_CheckMyClassExist()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_CheckMyClassExist).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_CHECK_MY_CLASS_EXIST_CHECK;
    }

    private void vProc_CheckMyClassExist_Check()
    {
        if(bServerResponseFlag_CheckMyClassExist)
        {
            bServerResponseFlag_CheckMyClassExist = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_CheckMyClassExist == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_CheckMyClassExist_Check Success!");

                        if(mMyGym_Class_DetailInfoLayout != null)
                        {
                            boolean bIsExist = false;
                            bIsExist = vCheckMyClassExist(sClassID,CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
                            mMyGym_Class_DetailInfoLayout.vSetExistInMyClass(bIsExist,sClassID,sBkgSeq);
                        }
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_CheckMyClassExist_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                    mState = MyGymState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_GetMyClassInfoList()
    {
        if(bIsAlreadySearched(list_SearchWeek_MyClass,cal_StartDate) || (!CloudDataStruct.CloudData_20.is_log_in()))
        {
            bServerResponseFlag_GetMyClassInfo = true;
            iServerResponseResult_GetMyClassInfo = 0;
        }
        else
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.showProgressBar();
                    mMyGymMainLayout.mLayout_ClassCalendar.vEnableButton(false);
                    new Thread(CloudRunnable_GetMyClassInfoList).start();
                }
            });
        }

        mState = MyGymState.PROC_CLOUD_GET_MY_CLASS_INFO_LIST_CHECK;
    }

    private void vProc_GetMyClassInfoList_Check()
    {
        if(bServerResponseFlag_GetMyClassInfo)
        {
            bServerResponseFlag_GetMyClassInfo = false;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetMyClassInfo == 0)
                    {
                        //Success
                        Log.e("Jerry","vProc_GetMyClassInfoList_Check Success!");

                        mMyGymMainLayout.mLayout_ClassCalendar.layout_ClassList.removeAllViews();
                        mMyGymMainLayout.mLayout_ClassCalendar.layout_ClassList.vImportMyClassBlockInfo(cal_StartDate,CloudDataStruct.CloudData_39.list_CloudMyClassInfo);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_GetMyClassInfoList_Check Fail!");
                    }

                    mMyGymMainLayout.mLayout_ClassCalendar.vEnableButton(true);
                    mMainActivity.closeProgressBar();
                    mState = MyGymState.PROC_IDLE;
                }
            });
        }
    }

    private void vProc_GetClassDetailInfo()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_GetClassDetailInfo).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_GET_CLASS_DETAIL_INFO_CHECK;
    }

    private void vProc_GetClassDetailInfo_Check()
    {
        if(bServerResponseFlag_GetClassDetailInfo)
        {
            bServerResponseFlag_GetClassDetailInfo = false;
            mState = MyGymState.PROC_IDLE;

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_GetClassDetailInfo == 0)
                    {
                        //Success

                        if(mMyGym_Class_DetailInfoLayout != null)
                        {
                            mMyGym_Class_DetailInfoLayout.vImportCloudData();
                            mMyGym_Class_DetailInfoLayout.vUpdateUI();
                        }

                        Log.e("Jerry","GetClassDetailInfo Success!");
//                        Log.e("Jerry","CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.size() = " + CloudDataStruct.CloudData_32.list_CloudClassDetailInfo.size());
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","GetClassDetailInfo Fail!");
                    }

                    mMainActivity.closeProgressBar();

                    vSetNextState(MyGymState.PROC_CLOUD_CHECK_MY_CLASS_EXIST);
                }
            });
        }
    }

    private void vProc_AddClass()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_AddClass).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_ADD_CLASS_CHECK;
    }

    private void vProc_AddClass_Check()
    {
        if(bServerResponseFlag_AddClass)
        {
            bServerResponseFlag_AddClass = false;
            mState = MyGymState.PROC_IDLE;
            vClearMyClassSearchList();

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_AddClass == 0)
                    {
                        //Success

                        Log.e("Jerry","vProc_Addlass_Check Success!");
                        vSetNextState(MyGymState.PROC_CLOUD_CHECK_MY_CLASS_EXIST);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_Addlass_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }

    private void vProc_DeleteClass()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_DeleteClass).start();
            }
        });

        mState = MyGymState.PROC_CLOUD_DELETE_CLASS_CHECK;
    }

    private void vProc_DeleteClass_Check()
    {
        if(bServerResponseFlag_DeleteClass)
        {
            bServerResponseFlag_DeleteClass = false;
            mState = MyGymState.PROC_IDLE;
            vClearMyClassSearchList();

            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    if(iServerResponseResult_DeleteClass == 0)
                    {
                        //Success

                        Log.e("Jerry","vProc_Deletelass_Check Success!");
                        vSetNextState(MyGymState.PROC_CLOUD_CHECK_MY_CLASS_EXIST);
                    }
                    else
                    {
                        //Fail
                        Log.e("Jerry","vProc_Deletelass_Check Fail!");
                    }

                    mMainActivity.closeProgressBar();
                }
            });
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_GetBulletinList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetBulletinList = false;
            iServerResponseResult_GetBulletinList = mMainActivity.mCloudCmd.iCloudCmd_GetBulletinList();
            bServerResponseFlag_GetBulletinList = true;
        }
    };

    public Runnable CloudRunnable_GetBulletinDetailInfo = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetBulletinDetailInfo = false;
            iServerResponseResult_GetBulletinDetailInfo = mMainActivity.mCloudCmd.iCloudCmd_GetBulletinDetailInfo(sBulletinID);
            bServerResponseFlag_GetBulletinDetailInfo = true;
        }
    };

    public Runnable CloudRunnable_GetClassInfoList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetClassInfo = false;
            iServerResponseResult_GetClassInfo = mMainActivity.mCloudCmd.iCloudCmd_GetGymClassList(cal_StartDate,cal_EndDate);

            if(iServerResponseResult_GetClassInfo == 0 || iServerResponseResult_GetClassInfo == 3)
            {
                list_SearchWeek_Class.add(cal_StartDate);
            }

            bServerResponseFlag_GetClassInfo = true;
        }
    };

    public Runnable CloudRunnable_GetMyClassInfoList = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetMyClassInfo = false;
            iServerResponseResult_GetMyClassInfo = mMainActivity.mCloudCmd.iCloudCmd_GetGymMyClassList(cal_StartDate,cal_EndDate);

            if(iServerResponseResult_GetMyClassInfo == 0 || iServerResponseResult_GetMyClassInfo == 3)
            {
                list_SearchWeek_MyClass.add(cal_StartDate);
            }

            bServerResponseFlag_GetMyClassInfo = true;
        }
    };

    public Runnable CloudRunnable_CheckMyClassExist = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_CheckMyClassExist = false;
            {
                Calendar cal_DetailDate_Start = (Calendar) cal_DetailDate.clone();
                cal_DetailDate_Start.set(Calendar.DATE,-5);

                iServerResponseResult_CheckMyClassExist = mMainActivity.mCloudCmd.iCloudCmd_GetGymMyClassList(cal_DetailDate_Start,cal_DetailDate);
            }
//            Log.e("Jerry","cal_DetailDate = " + Rtx_Calendar.sCalendar2Str(cal_DetailDate,"yyyy-MM-dd hh:mm:ss"));
            bServerResponseFlag_CheckMyClassExist = true;
        }
    };

    public Runnable CloudRunnable_GetClassDetailInfo = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_GetClassDetailInfo = false;
            iServerResponseResult_GetClassDetailInfo = mMainActivity.mCloudCmd.iCloudCmd_GetGymClassDetailInfo(sClassID);
            bServerResponseFlag_GetClassDetailInfo = true;
        }
    };

    public Runnable CloudRunnable_AddClass = new Runnable() {
        @Override
        public void run()
        {
            bServerResponseFlag_AddClass = false;
            iServerResponseResult_AddClass = mMainActivity.mCloudCmd.iCloudCmd_GymClass_Add(cal_StartDate,sClassID);
            bServerResponseFlag_AddClass = true;
        }
    };

    public Runnable CloudRunnable_DeleteClass = new Runnable() {
        @Override
        public void run()
        {
            Log.e("Jerry","[CloudRunnable_DeleteClass] sBkgSeq = " + sBkgSeq);
            bServerResponseFlag_DeleteClass = false;
            iServerResponseResult_DeleteClass = mMainActivity.mCloudCmd.iCloudCmd_GymClass_Delete(sBkgSeq);
            bServerResponseFlag_DeleteClass = true;
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vSetBulletinID(int iBulletinListIndex)
    {
        if(CloudDataStruct.CloudData_29.list_CloudBulletinInfo != null)
        {
            if(iBulletinListIndex < CloudDataStruct.CloudData_29.list_CloudBulletinInfo.size())
            {
                sBulletinID = new String(CloudDataStruct.CloudData_29.list_CloudBulletinInfo.get(iBulletinListIndex).sBLT_ID);
            }
        }
    }

    public void vSetClassID(String sID)
    {
        sClassID = sID;
    }

    public void vSetBkgSeq(String sSeq)
    {
        sBkgSeq = sSeq;
    }

    public void vClearSearchList()
    {
        vClearClassSearchList();
        vClearMyClassSearchList();
    }

    public void vClearClassSearchList()
    {
        list_SearchWeek_Class.clear();
        vClearClassList();
    }

    public void vClearMyClassSearchList()
    {
        list_SearchWeek_MyClass.clear();
        vClearMyClassList();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean bIsAlreadySearched(ArrayList<Calendar> list_cal, Calendar cal)
    {
        boolean bResult = false;

        if((list_cal != null) && (cal != null))
        {
            int iIndex = 0;
            int iSize = list_cal.size();

            for( ; iIndex < iSize ; iIndex++)
            {
                if(Rtx_Calendar.isSameDate(list_cal.get(iIndex),cal))
                {
                    bResult = true;
                    break;
                }
            }
        }

        return bResult;
    }

    private void vClearClassList()
    {
        if(CloudDataStruct.CloudData_31.list_CloudClassInfo != null)
        {
            CloudDataStruct.CloudData_31.list_CloudClassInfo.clear();
        }
    }

    private void vClearMyClassList()
    {
        if(CloudDataStruct.CloudData_39.list_CloudMyClassInfo != null)
        {
            CloudDataStruct.CloudData_39.list_CloudMyClassInfo.clear();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void vSetBitmapLoadReady(boolean bFlag)
    {
        bBitmapReady = bFlag;
    }

    public boolean bGetBitmapLoadReady()
    {
        return bBitmapReady;
    }

    public Runnable Runnable_LoadingBitmap = new Runnable() {
        @Override
        public void run()
        {
            while(bThreadRun_LoadingBitmap)
            {
                if(GlobalData.loadBitmap())
                {
                    vSetBitmapLoadReady(true);
                    sleep(50);
                }
                else
                {
                    sleep(500);
                }
            }
        }
    };

    public void sleep(int millionSec)
    {
        try {
            Thread.sleep(millionSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean vCheckMyClassExist(String sID, ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudMyClassInfo)
    {
        boolean bResult = false;

        if(list_CloudMyClassInfo == null)
        {
            return bResult;
        }

        CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO cloud_Data;
        int iIndex = 0;
        int iSize = list_CloudMyClassInfo.size();

        for( ; iIndex < iSize ; iIndex++)
        {
            cloud_Data = list_CloudMyClassInfo.get(iIndex);

            Log.e("Jerry","sID = " + sID);
            Log.e("Jerry","cloud_Data.sCLS_ID = " + cloud_Data.sCLS_ID);

            if(cloud_Data.sCLS_ID.equals(sID))
            {
                bResult = true;
                break;
            }
        }

        return bResult;
    }
}
