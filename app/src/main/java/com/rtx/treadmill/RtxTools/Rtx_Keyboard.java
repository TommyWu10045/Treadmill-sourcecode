package com.rtx.treadmill.RtxTools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxView.RtxEditText;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Keyboard
{
    public static void openSoftKeybord(View view, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);

    }

    public static void closeSoftKeybord(View view, Context mContext)
    {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity)mContext).getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void closeViewSoftKeybord(View view, Context mContext)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && view != null)
        {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void closeKeyboard(MainActivity mMainActivity)
    {
        View view = mMainActivity.getWindow().peekDecorView();
        if(view != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) mMainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

            if(inputMethodManager.isActive())
            {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            else
            {
            }
        }
        else
        {
        }
    }
}
