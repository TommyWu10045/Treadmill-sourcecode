package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;

import java.util.ArrayList;


/**
 * Created by jerry on 2016/12/29.
 */

public class View_MyWorkout_DataList extends HorizontalScrollView
{
    public class BLOCK_INFO
    {
        View_MyWorkout_DataBlock    view;
        int                         iViewId;
        int                         iTime;
        float                       fIncline;
        float                       fSpeed;
    }

    private Context mContext;
    private MainActivity mMainActivity;

    private int iHorGap = 10;

    private LinearLayout mLinearLayout;
    private ArrayList<BLOCK_INFO> list_View;

    private View_MyWorkout_AddBlock mView_MyWorkout_AddBlock;

    private ButtonListener mButtonListener;
    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    public View_MyWorkout_DataList(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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
        if(mView_MyWorkout_AddBlock == null)    { mView_MyWorkout_AddBlock = new View_MyWorkout_AddBlock(mContext); }
    }

    private void add_View()
    {
        addViewToLayout(mLinearLayout,0,0,1280,545);

        addViewToLinearLayout(mView_MyWorkout_AddBlock,0,0,183,545,-1);
    }

    private void set_Event()
    {
        mView_MyWorkout_AddBlock.setOnClickListener(mButtonListener);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addDataBlock(int iTime , float fSpeed , float fIncline)
    {
        final BLOCK_INFO blockInfo = new BLOCK_INFO();
        View_MyWorkout_DataBlock view = new View_MyWorkout_DataBlock(mContext);
        int iIndex = -1;
        final int iViewId;
        int iSize = 1;

        if(list_View != null)
        {
            iSize += list_View.size();
            iIndex = list_View.size();
        }


        addViewToLinearLayout(view,0,0,183,545,iIndex);

        iViewId = View_MyWorkout_DataBlock.generateViewId();
        view.setId(iViewId);
        view.vSetNumber(iSize);
        view.vSetData(iTime,fSpeed,fIncline);

        blockInfo.view = view;
        blockInfo.iViewId = iViewId;
        blockInfo.iTime = iTime;
        blockInfo.fIncline = fIncline;
        blockInfo.fSpeed = fSpeed;

        list_View.add(blockInfo);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mMainActivity.mMainProcBike.myWorkoutProc.vSetSettingIndex(iSearchIndexFromList(iViewId));
                mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_SETTING);
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
    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem)
    {
        this.workoutItem = workoutItem;
        vSyncBlockList();
        scrollToEnd();
    }

    public void vSyncBlockList()
    {
        int iDataSize = workoutItem.list_Stage.size();
        int iBlockSize = list_View.size();

        int iIndex = 0;

//        if(iBlockSize > iDataSize)
//        {
//            int iDiff = iDataSize - iBlockSize;
//
//            for (iIndex = 0 ; iIndex < iDiff ; iIndex++)
//            {
//                iBlockSize = list_View.size();
//                list_View.remove(iBlockSize - 1);
//            }
//        }

        iBlockSize = list_View.size();

        mLinearLayout.removeAllViews();

        for( iIndex = 0 ; iIndex < iDataSize ; iIndex++)
        {
            if(iIndex >= iBlockSize)
            {
                addDataBlock(workoutItem.list_Stage.get(iIndex).iTime,workoutItem.list_Stage.get(iIndex).fGetSpeed(),workoutItem.list_Stage.get(iIndex).fGetIncline());
            }
            else
            {
                list_View.get(iIndex).view.vSetData(workoutItem.list_Stage.get(iIndex).iTime,workoutItem.list_Stage.get(iIndex).fGetSpeed(),workoutItem.list_Stage.get(iIndex).fGetIncline());
                addViewToLinearLayout(list_View.get(iIndex).view,0,0,183,545,-1);
            }
        }

        if(iDataSize < 50)
        {
            addViewToLinearLayout(mView_MyWorkout_AddBlock,0,0,183,545,-1);
        }
        else
        {

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickAddButton()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetSettingIndex(-1);
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_SETTING);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == mView_MyWorkout_AddBlock)                                      { vClickAddButton(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addViewToLinearLayout(View view , int iX , int iY , int iWidth , int iHeight , int iIndex)
    {
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(iWidth,iHeight);
        mLayoutParams.setMargins(iHorGap,0,0,0);

        view.setLayoutParams(mLayoutParams);

        if(iIndex == -1)
        {
            mLinearLayout.addView(view);
        }
        else
        {
            mLinearLayout.addView(view,iIndex);
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
