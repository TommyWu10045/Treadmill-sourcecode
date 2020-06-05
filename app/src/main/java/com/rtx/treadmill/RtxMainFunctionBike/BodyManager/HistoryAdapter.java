package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.GlobalData.CloudDataStruct.HistoryData;

import java.util.ArrayList;

import static com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC.Input.fBON_MAS;

/**
 * Created by Jay on 2015/9/21 0021.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HistoryData> mData = null ;
    private int imode = 0;

    public HistoryAdapter() {
    }

    public HistoryAdapter(ArrayList<HistoryData> mData, Context mContext, int itype) {
        this.mData = mData;
        this.mContext = mContext;
        this.imode = itype;
    }

    //往特定位置，添加一个元素
    public void v_set_mode(int itype){
        this.imode = itype;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        float fSize = 50f;
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bodymanager_history_list, parent, false);
            holder = new ViewHolder();
            holder.txt1 = (RtxTextView) convertView.findViewById(R.id.textDate);
            holder.txt1.setTypeface(typeface);
            holder.txt1.setTextColor(Common.Color.bd_word_white);
            holder.txt1.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);

            holder.txt3 = (RtxTextView) convertView.findViewById(R.id.textVal);
            holder.txt3.setTypeface(typeface);
            holder.txt3.setTextColor(Common.Color.bd_word_yellow);
            holder.txt3.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt1.setText(mData.get(position).sDate);

        String sdata = BodyManagerFunc.s_get_bodymanage_unit(mContext, imode);

        if(imode == fBON_MAS)
        {
            float fVal = 0;
            fVal = Rtx_TranslateValue.fRoundingVal(mData.get(position).sVal,2);
            mData.get(position).sVal = Rtx_TranslateValue.sFloat2String(fVal,2);
        }

        sdata = mData.get(position).sVal + " " + sdata;
        holder.txt3.setText(sdata);

        return convertView;
    }

    //添加一个元素
    public void add(HistoryData data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,HistoryData data){
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if(mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if(mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        RtxTextView txt1;
        RtxTextView txt2;
        RtxTextView txt3;
    }

}
