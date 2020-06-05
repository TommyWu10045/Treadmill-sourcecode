package com.rtx.treadmill.RtxView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chasechang on 3/23/17.
 */

public class DrawData {
    private static String TAG = "DrawData";

    /* base data */
    public Object mObj = null ;

    /* resource id*/
    public int iResID = 0 ;

    /* text string */
    public String mText = null ;
    public String mText2 = null ;

    /* argvs */
    public List<Object> mArgvs = null ;

    public DrawData( Object object , ArrayList<Object> mArgvArray ) {
        this.mObj    = object ;
        this.iResID  = 0      ;
        this.mText   = null   ;

        if ( mArgvs == null ) {
            mArgvs = new ArrayList<Object>();
        }
        mArgvs.addAll(mArgvArray) ;
    }

    public DrawData( Object object , int iResID , ArrayList<Object> mArgvArray ) {
        this.mObj    = object ;
        this.iResID  = iResID ;
        this.mText   = null   ;

        if ( mArgvs == null ) {
            mArgvs = new ArrayList<Object>();
        }

        mArgvs.addAll(mArgvArray) ;
    }

    public DrawData( Object object , String mText , String mText2 ,ArrayList<Object> mArgvArray ) {
        this.mObj    = object ;
        this.iResID  = 0      ;
        this.mText   = mText  ;
        this.mText2   = mText2  ;

        if ( mArgvs == null ) {
            mArgvs = new ArrayList<Object>();
        }

        mArgvs.addAll(mArgvArray) ;
    }
}
