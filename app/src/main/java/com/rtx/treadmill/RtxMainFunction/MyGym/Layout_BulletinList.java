package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by chasechang on 3/22/17.
 */

public class Layout_BulletinList extends ListView {

    private Context mContext;
    private MainActivity mMainActivity;

    ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo;
    BaseAdapter_BulletinList adapter_BulletinList;

    public Layout_BulletinList(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;

        init();
    }

    private void init()
    {
        {
            ColorDrawable sage = new ColorDrawable(Common.Color.blue_7);
            setDivider(sage);
        }

        addHeaderView(new ViewStub(mContext));
        addHeaderView(new ViewStub(mContext));
        addFooterView(new ViewStub(mContext));
        //setDivider(new ColorDrawable(Common.Color.yellow));
        setDividerHeight(22);
        setBackgroundColor(Common.Color.blue_7);
        setScrollbarFadingEnabled(false);

        try {
            Field mScrollCacheField = View.class.getDeclaredField("mScrollCache");
            mScrollCacheField.setAccessible(true);
            Object mScrollCache = mScrollCacheField.get(this); // 从listview中获取bar

            Field scrollBarField = mScrollCache.getClass().getDeclaredField("scrollBar");
            scrollBarField.setAccessible(true);
            Object scrollBar = scrollBarField.get(mScrollCache);

            Method method1 = scrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable", Drawable.class);//滚动条
            method1.setAccessible(true);
//                    Method method2 = scrollBar.getClass().getDeclaredMethod("setVerticalTrackDrawable", Drawable.class);//滚动条背景
//                    method2.setAccessible(true);
//                    method2.invoke(scrollBar, getResources().getDrawable(R.drawable.scrollbar_color));

            // Set your drawable here.
            method1.invoke(scrollBar, getResources().getDrawable(R.drawable.scrollbar_color));
        } catch (Exception e) {
            e.printStackTrace();
        }

        {
            adapter_BulletinList = new BaseAdapter_BulletinList(mContext,list_CloudBulletinInfo);
            this.setAdapter(adapter_BulletinList);
        }
    }

    public void display()
    {
        mMainActivity.mMainProcTreadmill.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_BULLETIN_LIST);
    }

    protected void setDataList(ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        this.list_CloudBulletinInfo = list_CloudBulletinInfo;

        if(adapter_BulletinList != null)
        {
            adapter_BulletinList.updateDataList(list_CloudBulletinInfo);
        }

//        adapter_BulletinList = new BaseAdapter_BulletinList(mContext,list_CloudBulletinInfo);
//        this.setAdapter(adapter_BulletinList);
    }

    protected void updateDataList(ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        if(adapter_BulletinList != null)
        {
            adapter_BulletinList.updateDataList(list_CloudBulletinInfo);
        }
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_event()
    {

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
