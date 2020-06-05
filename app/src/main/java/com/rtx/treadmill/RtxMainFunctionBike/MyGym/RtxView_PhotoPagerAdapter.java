package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class RtxView_PhotoPagerAdapter extends PagerAdapter {

    private ArrayList<Bitmap> list_PhotoBitmap;

    private Context mContext;

    public RtxView_PhotoPagerAdapter(Context context) {
        mContext = context;
    }

    public void change(ArrayList<Bitmap> list_PhotoBitmap) {
        this.list_PhotoBitmap = list_PhotoBitmap;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        int iCount = 0;

        if(list_PhotoBitmap != null)
        {
            iCount = list_PhotoBitmap.size();
        }

        return iCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        boolean bResult = false;

        if(view == (View) object)
        {
            bResult = true;
        }

        return bResult;
    }

    @Override
    public Object instantiateItem (ViewGroup container, int position) {
        ImageView iv = new ImageView(mContext);
        try {
            Bitmap bm = list_PhotoBitmap.get(position);//載入bitmap
            iv.setImageBitmap(bm);
        } catch (OutOfMemoryError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ((ViewPager)container).addView(iv, 0);
        return iv;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
