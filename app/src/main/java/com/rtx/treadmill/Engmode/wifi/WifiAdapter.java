package com.rtx.treadmill.Engmode.wifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtx.treadmill.R;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/21 0021.
 */
public class WifiAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AccessPoint> accessPoints = null ;

    public WifiAdapter() {
    }

    public WifiAdapter(ArrayList<AccessPoint> mData, Context mContext) {
        //this.mData = mData;
        this.accessPoints = mData;

        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return accessPoints.size();
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
        //Log.e("Tom", "===position===" + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wifi_list, parent, false);
            holder = new ViewHolder();
            holder.txt_ssid = (TextView) convertView.findViewById(R.id.textView);
            holder.txt_status = (TextView) convertView.findViewById(R.id.textsummary);
            holder.img_wifi = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_ssid.setText(accessPoints.get(position).getssid());
        holder.txt_status.setText(accessPoints.get(position).getstatus());
        if(accessPoints.get(position).getImgId() != 0) {
            holder.img_wifi.setImageResource(accessPoints.get(position).getImgId());
        }
        return convertView;
    }

    //添加一个元素
    public void add(AccessPoint data) {
        accessPoints.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,AccessPoint data){
        accessPoints.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if(accessPoints != null) {
            accessPoints.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if(accessPoints != null) {
            accessPoints.clear();
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView txt_ssid;
        TextView txt_status;
        ImageView img_wifi;
    }

}
