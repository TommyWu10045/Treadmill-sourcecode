package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/23/17.
 */

public class DrawObj {
    private static String TAG = "DrawObj";

    public static void addView(Context mContext, FrameLayout mLayout , ArrayList<DrawData> mDataArray) {
        for (DrawData mImageInfo : mDataArray) {

            if ( mImageInfo.mObj.getClass() == RtxImageView.class ) {
                parseImageView( mContext, (RtxImageView)mImageInfo.mObj , mImageInfo ) ;
            } else if ( mImageInfo.mObj.getClass() == RtxTextView.class ) {
                parseTextView( mContext, (RtxTextView)mImageInfo.mObj , mImageInfo ) ;
            } else if ( mImageInfo.mObj.getClass() == RtxDoubleStringView.class ) {
                parseDoubleTextView( mContext, (RtxDoubleStringView)mImageInfo.mObj , mImageInfo ) ;
            } else {
                parseDefault(mContext, (View)mImageInfo.mObj , mImageInfo) ;
            }

            mLayout.addView( (View)mImageInfo.mObj ) ;
        }
    }

    /*     ,          , 0 , 1 , 2 , 3 , 4     */
    /* obj , image_id , x , y , w , h , color */
    /* obj , color_id , x , y , w , h , color */
    public static void parseImageView(Context mContext, RtxImageView mView , DrawData mData ) {
        if ( mData.mArgvs.size() < 4 ) {
            return ;
        }

        if ( mData.iResID != 0 ) {
            try {
                TypedValue mTypedValue = new TypedValue();

                mContext.getResources().getValue(mData.iResID, mTypedValue, true);

                if (mTypedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && mTypedValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                    mView.setBackgroundColor(mContext.getResources().getColor(mData.iResID));
                } else {
                    String mResType = mContext.getResources().getResourceTypeName(mData.iResID);
                    if (mResType.matches("drawable")) {
                        mView.setImageResource(mData.iResID);
                    }
                }
            } catch(Resources.NotFoundException e) {
                mView.setBackgroundColor(mData.iResID);
            }
        }

        if ( mData.mArgvs.size() >= 5 ) {
            int iColor = ((Integer) mData.mArgvs.get(4)).intValue() ;

            try {
                TypedValue mTypedValue = new TypedValue();

                mContext.getResources().getValue(iColor, mTypedValue, true);

                if (mTypedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && mTypedValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                    mView.setBackgroundColor(mContext.getResources().getColor(iColor));
                }
            } catch(Resources.NotFoundException e) {
                mView.setBackgroundColor(iColor);
            }
        }

        /* adjust layout */
        {
            int iX = ((Integer) mData.mArgvs.get(0)).intValue();
            int iY = ((Integer) mData.mArgvs.get(1)).intValue();
            int iW = ((Integer) mData.mArgvs.get(2)).intValue();
            int iH = ((Integer) mData.mArgvs.get(3)).intValue();
            FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iW, iH);
            mLayoutParams.leftMargin = iX;
            mLayoutParams.topMargin = iY;
            mView.setLayoutParams(mLayoutParams);
        }
    }

    /*     ,        , 0 , 1 , 2 , 3 , 4 , 5 , 6
     * obj , res_id , x , y , w , h , font_size/font/color
     * obj , string , x , y , w , h , font_size/font/color
     * */

    public static void parseTextView(Context mContext, RtxTextView mView , DrawData mData ) {
        if ( mData.mArgvs.size() < 4 ) {
            return ;
        }

        if ( mData.mArgvs.size() > 4 ) {
            int iIndex ;

            for ( iIndex = 4 ; iIndex < mData.mArgvs.size() ; iIndex ++ ) {
                Object mObject = mData.mArgvs.get(iIndex) ;

                /* is float ? */
                if ( mObject.getClass() == java.lang.Double.class ) {
                    mView.setTextSize(((Double) mObject).floatValue());
                }
                else if ( mObject.getClass() == java.lang.String.class ) {
                    Typeface font = Typeface.createFromAsset(mContext.getAssets(), (String) mObject);
                    if(font != null) {
                        mView.setTypeface(font);
                    }
                } else {
                    int iResID = ((Integer) mObject).intValue() ;
                    try {
                        TypedValue mTypedValue = new TypedValue();
                        mContext.getResources().getValue(iResID, mTypedValue, true);
                        if (mTypedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && mTypedValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                            mView.setTextColor(mContext.getResources().getColor(iResID));
                        }
                    } catch (Resources.NotFoundException e) {
                        mView.setTextColor(iResID);
                    }
                }
            }
        }

        if ( mData.mText != null ) {
            mView.setText(mData.mText);
        } else if ( mData.iResID != 0 ) {
            String mResName ;
            try {
                mResName = mContext.getResources().getResourceName(mData.iResID) ;
            } catch(Resources.NotFoundException e) {
                mResName = null ;
            }
            if ( mResName != null ) {
                mView.setText(mData.iResID);
            } else {
                mView.setText(String.valueOf(mData.iResID));
            }
        }

        /* adjust layout */
        {
            int iX = ((Integer) mData.mArgvs.get(0)).intValue();
            int iY = ((Integer) mData.mArgvs.get(1)).intValue();
            int iW = ((Integer) mData.mArgvs.get(2)).intValue();
            int iH = ((Integer) mData.mArgvs.get(3)).intValue();
            FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iW, iH);
            mLayoutParams.leftMargin = iX;
            mLayoutParams.topMargin = iY;
            mView.setLayoutParams(mLayoutParams);
        }
    }

    /*     ,        , 0 , 1 , 2 , 3 , 4  , 5    , 6     , 7    , 8    , 9     , 10
     * obj , res_id , x , y , w , h , gap, font1, color1, size1, font2, color2, size2
     * */

    public static void parseDoubleTextView(Context mContext, RtxDoubleStringView mView , DrawData mData ) {
        if ( mData.mArgvs.size() < 4 ) {
            return ;
        }

        if ( mData.mArgvs.size() >= 10 ) {
            int iIndex ;
            int igap = ((Integer) mData.mArgvs.get(4)).intValue();
            mView.setGap(igap);

            String font1 = ((String) mData.mArgvs.get(5)).toString();
            int color1 = ((Integer) mData.mArgvs.get(6)).intValue();
            float size1 = ((Double) mData.mArgvs.get(7)).floatValue();

            String font2 = ((String) mData.mArgvs.get(5)).toString();
            int color2 = ((Integer) mData.mArgvs.get(6)).intValue();
            float size2 = ((Double) mData.mArgvs.get(7)).floatValue();

            mView.setPaint(font1, color1, size1, font2, color2, size2);

        }

        if ( mData.mText != null && mData.mText2 != null) {
            mView.setText(mData.mText, mData.mText2);
        }

        /* adjust layout */
        {
            int iX = ((Integer) mData.mArgvs.get(0)).intValue();
            int iY = ((Integer) mData.mArgvs.get(1)).intValue();
            int iW = ((Integer) mData.mArgvs.get(2)).intValue();
            int iH = ((Integer) mData.mArgvs.get(3)).intValue();
            FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iW, iH);
            mLayoutParams.leftMargin = iX;
            mLayoutParams.topMargin = iY;
            mView.setLayoutParams(mLayoutParams);
        }
    }

    /*     ,        , 0 , 1 , 2 , 3 */
    /* obj ,        , x , y , w , h */
    public static void parseDefault(Context mContext, View mView , DrawData mData ) {
        if ( mData.mArgvs.size() < 4 ) {
            return ;
        }

        /* adjust layout */
        {
            int iX = ((Integer) mData.mArgvs.get(0)).intValue();
            int iY = ((Integer) mData.mArgvs.get(1)).intValue();
            int iW = ((Integer) mData.mArgvs.get(2)).intValue();
            int iH = ((Integer) mData.mArgvs.get(3)).intValue();
            FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iW, iH);
            mLayoutParams.leftMargin = iX;
            mLayoutParams.topMargin = iY;
            mView.setLayoutParams(mLayoutParams);
        }
    }
}
