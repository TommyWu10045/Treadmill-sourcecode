package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;


import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;


/**
 * Created by jerry on 2016/12/29.
 */

public class Layout_ClassList extends Rtx_BaseLayout
{
    final int MODE_CLASS = 1;
    final int MODE_MY_CLASS = 2;

    private     MainActivity        mMainActivity;

    public class BlockViewInfo
    {
        String                  sCLS_ID;
        String                  sBKG_SEQ;
        Calendar                cal_StartTime;
        Calendar                cal_EndTime;
        String                  sInstructor;
        String                  sClassName;
        String                  sPhotoUrl;
        Bitmap                  bitmap;
        Layout_ClassBlock       view;
        int                     iViewId;
        boolean                 bFlag_BitmapSet = false;
    }

    Context mContext;
    //ArrayList<BlockViewInfo> list_BlockViewInfo;
    TreeMap<String,BlockViewInfo> treeMap_BlockViewInfo;

//    ArrayList<CloudDataStruct.CLOUD_GYM_CLASS_INFO>     list_CloudClassInfo;
//    ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO>  list_CloudMyClassInfo;

    public Layout_ClassList(Context context, MainActivity mMainActivity) {
        super(context);

        this.mMainActivity = mMainActivity;
        mContext = context;
        init();
    }

    @Override
    public void init()
    {
        //if(list_BlockViewInfo == null)  { list_BlockViewInfo = new ArrayList<BlockViewInfo>(); }
        if(treeMap_BlockViewInfo == null)  { treeMap_BlockViewInfo = new TreeMap<String,BlockViewInfo>(); }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }


    private void init_View()
    {

    }

    private void init_event()
    {

    }

    private void add_View()
    {

    }

    protected void vUpdateBlockBitmap()
    {

        Log.e("Jerry","vUpdateBlockBitmap");

        if(treeMap_BlockViewInfo == null)
        {
            return;
        }

        int iIndex = 0;
        int iSize = treeMap_BlockViewInfo.size();

        BlockViewInfo blockViewInfo;

        for( ; iIndex < iSize ; iIndex++ )
        {
            blockViewInfo = treeMap_BlockViewInfo.get(treeMap_BlockViewInfo.keySet().toArray()[iIndex]);

            if(blockViewInfo.bFlag_BitmapSet == false)
            {
                Bitmap bitmap = null;
                bitmap = GlobalData.searchBitmap(blockViewInfo.sPhotoUrl);

                if(bitmap != null)
                {
                    blockViewInfo.view.setBitmap(bitmap);
                    blockViewInfo.bFlag_BitmapSet = true;
                }
            }
        }
    }

    private void vCreateView(TreeMap<String,BlockViewInfo> treeMap)
    {
        if(treeMap == null)
        {
            return;
        }

        int iBlockStartPosY[] = {0,0,0,0,0,0,0};
        int iIndex = 0;
        int iSize = treeMap.size();

        BlockViewInfo blockViewInfo;

        for( ; iIndex < iSize ; iIndex++ )
        {
            blockViewInfo = treeMap.get(treeMap.keySet().toArray()[iIndex]);

            {
                int iDayOfWeek = -1;
                String sTime = Rtx_Calendar.sCalendar2Str(blockViewInfo.cal_StartTime,"HH:mm") + " - " + Rtx_Calendar.sCalendar2Str(blockViewInfo.cal_EndTime,"HH:mm");
                blockViewInfo.view = new Layout_ClassBlock(mContext,sTime,blockViewInfo.sInstructor,blockViewInfo.sClassName,blockViewInfo.sCLS_ID);

                {
                    final BlockViewInfo finalBlockViewInfo = blockViewInfo;
                    blockViewInfo.view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("Jerry","finalBlockViewInfo.sCLS_ID = " + finalBlockViewInfo.sCLS_ID);
                            Log.e("Jerry","finalBlockViewInfo.sBKG_SEQ = " + finalBlockViewInfo.sBKG_SEQ);

                            mMainActivity.mMainProcTreadmill.myGymProc.vSetDetailDate(finalBlockViewInfo.cal_StartTime);
                            mMainActivity.mMainProcTreadmill.myGymProc.vSetClassID(finalBlockViewInfo.sCLS_ID);
                            mMainActivity.mMainProcTreadmill.myGymProc.vSetBkgSeq(finalBlockViewInfo.sBKG_SEQ);
                            mMainActivity.mMainProcTreadmill.myGymProc.vSetNextState(MyGymState.PROC_SHOW_PAGE_CLASS_DETAIL);

                        }
                    });
                }

                iDayOfWeek = blockViewInfo.cal_StartTime.get(Calendar.DAY_OF_WEEK) - 1;

                if(iDayOfWeek != -1)
                {
                    addViewToLayout(blockViewInfo.view,59+(iDayOfWeek*169),iBlockStartPosY[iDayOfWeek],149,blockViewInfo.view.iGetBlockHeight());

                    {
                        Bitmap bitmap = null;
                        bitmap = GlobalData.searchBitmap(blockViewInfo.sPhotoUrl);

                        if(bitmap != null)
                        {
                            blockViewInfo.view.setBitmap(bitmap);
                            blockViewInfo.bFlag_BitmapSet = true;
                        }
                    }

                    iBlockStartPosY[iDayOfWeek] += blockViewInfo.view.iGetBlockHeight();
                }
            }

            blockViewInfo.bFlag_BitmapSet = false;
        }
    }

    protected void vImportMyClassBlockInfo(Calendar cal, ArrayList<CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO> list_CloudMyClassInfo)
    {
        if(list_CloudMyClassInfo == null)
        {
            return;
        }

        if(treeMap_BlockViewInfo == null)
        {
            return;
        }

        treeMap_BlockViewInfo.clear();

        BlockViewInfo Info_Data;
        CloudDataStruct.CLOUD_GYM_MY_CLASS_INFO cloud_Data;
        int iIndex = 0;
        int iSize = list_CloudMyClassInfo.size();

        for( ; iIndex < iSize ; iIndex++)
        {
            cloud_Data = list_CloudMyClassInfo.get(iIndex);

            if( cloud_Data.sCLS_DEL.equals("1")){
                continue;
            }

            if(!Rtx_Calendar.isSameWeek(cal,Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT , "yyyy-MM-dd")))
            {
                continue;
            }


            Info_Data = new BlockViewInfo();
            Info_Data.sCLS_ID = cloud_Data.sCLS_ID;
            Info_Data.sBKG_SEQ = cloud_Data.sBKG_SEQ;
            Info_Data.cal_StartTime = Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT + " " + cloud_Data.sUser_CLS_STR_TIM , "yyyy-MM-dd HH:mm:ss");
            Info_Data.cal_EndTime = Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT + " " + cloud_Data.sUser_CLS_END_TIM , "yyyy-MM-dd HH:mm:ss");
            Info_Data.sInstructor = cloud_Data.sCLS_TCH_NAM;
            Info_Data.sClassName = cloud_Data.sCLS_NAME;
            Info_Data.sPhotoUrl = cloud_Data.sCLS_PHO;

            String sKey = cloud_Data.sUser_CLS_SUC_DT + cloud_Data.sUser_CLS_STR_TIM + cloud_Data.sCLS_ID;

            treeMap_BlockViewInfo.put(sKey,Info_Data);
        }

        vCreateView(treeMap_BlockViewInfo);
    }

    protected void vImportClassBlockInfo(Calendar cal, ArrayList<CloudDataStruct.CLOUD_GYM_CLASS_INFO> list_CloudClassInfo)
    {
        if(list_CloudClassInfo == null)
        {
            return;
        }

        if(treeMap_BlockViewInfo == null)
        {
            return;
        }

        treeMap_BlockViewInfo.clear();

        BlockViewInfo Info_Data;
        CloudDataStruct.CLOUD_GYM_CLASS_INFO cloud_Data;
        int iIndex = 0;
        int iSize = list_CloudClassInfo.size();

        for( ; iIndex < iSize ; iIndex++)
        {
            cloud_Data = list_CloudClassInfo.get(iIndex);

            if(!Rtx_Calendar.isSameWeek(cal,Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT , "yyyy-MM-dd")))
            {
                continue;
            }

            Info_Data = new BlockViewInfo();
            Info_Data.sCLS_ID = cloud_Data.sCLS_ID;
            Info_Data.sBKG_SEQ = null;
            Info_Data.cal_StartTime = Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT + " " + cloud_Data.sUser_CLS_STR_TIM , "yyyy-MM-dd HH:mm:ss");
            Info_Data.cal_EndTime = Rtx_Calendar.cStr2Calendar(cloud_Data.sUser_CLS_SUC_DT + " " + cloud_Data.sUser_CLS_END_TIM , "yyyy-MM-dd HH:mm:ss");
            Info_Data.sInstructor = cloud_Data.sCLS_TCH_NAM;
            Info_Data.sClassName = cloud_Data.sCLS_NAME;
            Info_Data.sPhotoUrl = cloud_Data.sCLS_PHO;


            String sKey = cloud_Data.sUser_CLS_SUC_DT + cloud_Data.sUser_CLS_STR_TIM + cloud_Data.sCLS_ID;

            treeMap_BlockViewInfo.put(sKey,Info_Data);
        }

        vCreateView(treeMap_BlockViewInfo);
    }
}
