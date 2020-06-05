package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jerry on 2017/1/10.
 */

public class BaseAdapter_BulletinList extends BaseAdapter
{
    Context mContext;
    ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo;

    public BaseAdapter_BulletinList(Context context)
    {
        mContext = context;
    }

    public BaseAdapter_BulletinList(Context context, ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        mContext = context;
        setDataList(list_CloudBulletinInfo);
    }

    protected void setDataList(ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        this.list_CloudBulletinInfo = list_CloudBulletinInfo;
    }

    protected void updateDataList(ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        this.list_CloudBulletinInfo = list_CloudBulletinInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        int iCount = 0;
        int iDefaultCount = 6;

        if(list_CloudBulletinInfo != null)
        {
            iCount = list_CloudBulletinInfo.size();
        }

        if(ExerciseData.b_is_exercising())
        {
            iDefaultCount = 5;
        }

        if(iCount < iDefaultCount)
        {
            iCount = iDefaultCount;
        }

        return iCount;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        String sState = "";
        String sTitle = "";
        String sDate = "";

        if(list_CloudBulletinInfo != null)
        {
            if( i < list_CloudBulletinInfo.size() )
            {
                sState = "";
                sTitle = list_CloudBulletinInfo.get(i).sBLT_TIL;
                //sDate = list_CloudBulletinInfo.get(i).sBLT_AUC_DT;
                //Log.e("Jerry","list_CloudBulletinInfo.get(i).sBLT_AUC_DT = " + list_CloudBulletinInfo.get(i).sBLT_AUC_DT);
                sDate = Rtx_Calendar.sParseDate2FormatStr(list_CloudBulletinInfo.get(i).sBLT_AUC_DT,"yyyy-MM-dd","M/dd/yyyy");

                if(list_CloudBulletinInfo.get(i).sH_FLG != null)
                {
                    if(list_CloudBulletinInfo.get(i).sH_FLG.equals("1"))
                    {
                        sState = LanguageData.s_get_string(mContext, R.string.hot);
                    }
                }

            }
        }

        Layout_BulletinItem mLayout_BulletinItem = new Layout_BulletinItem(mContext);
        mLayout_BulletinItem.setContents(sState,sTitle,sDate);
        mLayout_BulletinItem.setEnabled(false);
        mLayout_BulletinItem.setClickable(false);

        return (mLayout_BulletinItem);
    }
}
