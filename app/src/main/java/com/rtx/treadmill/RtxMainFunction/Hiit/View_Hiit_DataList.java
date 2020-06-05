package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutState;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.View_MyWorkout_AddBlock;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.View_MyWorkout_DataBlock;

import java.util.ArrayList;


/**
 * Created by jerry on 2016/12/29.
 */

public class View_Hiit_DataList extends HorizontalScrollView
{
    final int BLOCK_WIDTH = 113;
    final int BLOCK_HEIGHT = 444;

    final int HIIT_MAX_STAGE = 16;

    public class BLOCK_INFO
    {
        View_Hiit_StageBlock                view;
        int                                 iViewId;
    }

    private Context mContext;
    private MainActivity mMainActivity;

    private int iHorGap = 8;

    private LinearLayout mLinearLayout;
    private ArrayList<BLOCK_INFO> list_View;

    private ButtonListener mButtonListener;
    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private HiitProc    hiitProc;

    public View_Hiit_DataList(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
        super(context);

        mContext = context;
        this.hiitProc = hiitProc;

        this.mMainActivity = mMainActivity;

        init();
    }

    private void init()
    {
        init_View();
        add_View();
        set_Event();
    }

    private void init_View()
    {
        if(list_View == null)                   { list_View = new ArrayList<BLOCK_INFO>(); }

        if(mButtonListener == null)             { mButtonListener = new ButtonListener(); }

        if(mLinearLayout == null)               { mLinearLayout = new LinearLayout(mContext); }
    }

    private void add_View()
    {
        addViewToLayout(mLinearLayout,0,0,1280,545);

//        {
//
//            addViewToLinearLayout(block_WarmUp,0,0,BLOCK_WIDTH,BLOCK_HEIGHT,-1);
//            addViewToLinearLayout(block_CoolDown,0,0,BLOCK_WIDTH,BLOCK_HEIGHT,-1);
//        }
    }

    private void set_Event()
    {

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addDataBlock(UiDataStruct.INTERVAL_STAGE_INFO stageInfo_Sprint, UiDataStruct.INTERVAL_STAGE_INFO stageInfo_Recovery)
    {
        final BLOCK_INFO blockInfo = new BLOCK_INFO();
        View_Hiit_StageBlock view = new View_Hiit_StageBlock(mContext);
        int iIndex = -1;
        final int iViewId;
        int iSize = 1;

        if(list_View != null)
        {
            iSize += list_View.size();
            iIndex = list_View.size();
        }


        addViewToLinearLayout(view,0,0,BLOCK_WIDTH,BLOCK_HEIGHT,-1);

        iViewId = View_Hiit_StageBlock.generateViewId();
        view.setId(iViewId);
        view.vSetData(iSize,stageInfo_Sprint,stageInfo_Recovery);

        blockInfo.view = view;
        blockInfo.iViewId = iViewId;
//        blockInfo.stageInfo_Sprint = stageInfo_Sprint;
//        blockInfo.stageInfo_Recovery = stageInfo_Recovery;

        list_View.add(blockInfo);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                hiitProc.vSetSettingIndex(iSearchIndexFromList(iViewId));
                hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_SPRINT);
            }
        });

        scrollToEnd();
    }

    private int iSearchIndexFromList(int iViewId)
    {
        int iListIndex = -1;

        if(list_View == null)
        {
            return iListIndex;
        }

        int iIndex = 0;
        int iSize = list_View.size();

        for( ; iIndex < iSize ; iIndex++)
        {
            if(list_View.get(iIndex).iViewId == iViewId)
            {
                iListIndex = iIndex;
                break;
            }
        }

        return iListIndex;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo)
    {
        this.hiitItemInfo = hiitItemInfo;
        vSyncBlockList(this.hiitItemInfo);
        scrollToEnd();
    }

    public void vSyncBlockList(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo)
    {
        mLinearLayout.removeAllViews();

        if(hiitItemInfo == null)
        {
            return;
        }

        if(hiitItemInfo.list_Stage == null)
        {
            return;
        }

        int iStageIndex = 0;
        int iStageSize = hiitItemInfo.list_Stage.size();
        int iDisplayBlockSize = iStageSize / 2;
        int iBlockSize = list_View.size();

        int iIndex = 0;


        for( iIndex = 0 ; iIndex < iDisplayBlockSize ; iIndex++ )
        {
            iStageIndex = iIndex * 2;

            if(iIndex >= iBlockSize)
            {
                addDataBlock(hiitItemInfo.list_Stage.get(iStageIndex),hiitItemInfo.list_Stage.get(iStageIndex + 1));
                iBlockSize = list_View.size();
            }
            else
            {
                addViewToLinearLayout(list_View.get(iIndex).view,0,0,BLOCK_WIDTH,BLOCK_HEIGHT,-1);
                list_View.get(iIndex).view.display();
                list_View.get(iIndex).view.vSetData(iIndex+1,hiitItemInfo.list_Stage.get(iStageIndex),hiitItemInfo.list_Stage.get(iStageIndex + 1));
            }
        }

        int iFreeBlockSize = 0;

        if(iDisplayBlockSize <= HIIT_MAX_STAGE)
        {
            iFreeBlockSize = HIIT_MAX_STAGE - iDisplayBlockSize;

            if(iDisplayBlockSize > 3)
            {
                if(iFreeBlockSize > 4)
                {
                    iFreeBlockSize = 4;
                }
            }
            else
            {
                iFreeBlockSize = 8 - iDisplayBlockSize;
            }
        }

        for( iIndex = 0 ; iIndex < iFreeBlockSize ; iIndex++ )
        {
            UiDataStruct.INTERVAL_STAGE_INFO info1 = new UiDataStruct.INTERVAL_STAGE_INFO(0,0,0);
            UiDataStruct.INTERVAL_STAGE_INFO info2 = new UiDataStruct.INTERVAL_STAGE_INFO(0,0,0);

            if((iDisplayBlockSize + iIndex) >= iBlockSize)
            {
                addDataBlock(info1,info2);
                iBlockSize = list_View.size();
            }
            else
            {
                addViewToLinearLayout(list_View.get(iDisplayBlockSize + iIndex).view,0,0,BLOCK_WIDTH,BLOCK_HEIGHT,-1);
                list_View.get(iDisplayBlockSize + iIndex).view.display();
                list_View.get(iDisplayBlockSize + iIndex).view.vSetData(iDisplayBlockSize + iIndex + 1,info1,info2);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickAddButton()
    {
        hiitProc.vSetSettingIndex(-1);
        //hiitProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_SETTING);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //if(v == mView_MyWorkout_AddBlock)                                      { vClickAddButton(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addViewToLinearLayout(View view , int iX , int iY , int iWidth , int iHeight , int iIndex)
    {
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(iWidth,iHeight);
        mLayoutParams.setMargins(iHorGap,0,0,0);

        view.setLayoutParams(mLayoutParams);


        try {
            if(iIndex == -1)
            {
                mLinearLayout.addView(view);
            }
            else
            {
                mLinearLayout.addView(view,iIndex);
            }
        }
        catch (IllegalStateException e)
        {

        }
    }

    public void addViewToLayout(View view , int iX , int iY , int iWidth , int iHeight)
    {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;

        view.setLayoutParams(mLayoutParams);
        addView(view);
    }

    public void scrollToEnd()
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
