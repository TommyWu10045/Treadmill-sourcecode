package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * TODO: document your custom view class.
 */

public class RtxFrameLayout extends FrameLayout {


    public RtxFrameLayout(Context context) {
        super(context);
    }

    public RtxFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RtxFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        super.removeOnAttachStateChangeListener(listener);

        //Log.e("Jerry","removeOnAttachStateChangeListener");
    }

    @Override
    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        super.addOnAttachStateChangeListener(listener);

        //Log.e("Jerry","addOnAttachStateChangeListener");
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        //Log.e("Jerry","onAttachedToWindow");
    }

    @Override
    public void onViewAdded(View child) {
        //Log.e("Jerry","onViewAdded !!!");

        super.onViewAdded(child);

        //Log.e("Jerry","onViewAdded chid = " + child.toString());
    }

    @Override
    public void onViewRemoved(View child) {

        //Log.e("Jerry","onViewRemoved chid!!!");

        super.onViewRemoved(child);

        //Log.e("Jerry","onViewRemoved chid = " + child.toString());
    }
}
