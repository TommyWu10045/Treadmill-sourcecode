package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;

import java.util.ArrayList;


/**
 * Created by jerry on 2016/12/29.
 */

public class HistoryAdapter extends BaseAdapter {

    private Context         mContext;
    private MainActivity    mMainActivity;

    protected boolean bDeleteVisible = false;

    ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose;

    public HistoryAdapter(Context context, MainActivity mMainActivity, ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose)
    {
        mContext = context;
        this.mMainActivity = mMainActivity;
        this.list_CloudTargetGoalClose = list_CloudTargetGoalClose;
    }

    @Override
    public int getCount() {

        int iSize = 0;

        if(list_CloudTargetGoalClose != null)
        {
            iSize = list_CloudTargetGoalClose.size();
        }

        return iSize;
    }

    @Override
    public Object getItem(int position)
    {
        if(list_CloudTargetGoalClose == null)
        {
            return null;
        }

        return list_CloudTargetGoalClose.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        TargetTrainHistory_Block mTargetTrainHistory_Block = new TargetTrainHistory_Block(mContext,mMainActivity);

        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(406, 302);
        mTargetTrainHistory_Block.setLayoutParams(mLayoutParams);

        if(list_CloudTargetGoalClose != null)
        {
            if(position < list_CloudTargetGoalClose.size())
            {
                mTargetTrainHistory_Block.vSetCloudTargetGoalClose(list_CloudTargetGoalClose.get(position),bDeleteVisible);
            }
        }

        return (View)mTargetTrainHistory_Block;
    }
}

