package com.rtx.treadmill.RtxMainFunction.MyWorkout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_EditText_Input_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkout_Name_Layout extends Rtx_EditText_Input_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private int iMode = MODE_NONE;

    private Context mContext;
    private MainActivity   mMainActivity;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    private RtxFillerTextView   fillerTextView_Save;

    public MyWorkout_Name_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        if(fillerTextView_Save == null) { fillerTextView_Save = new RtxFillerTextView(mContext); }
    }

    @Override
    protected void init_CustomerEvent()
    {
        fillerTextView_Save.setOnClickListener(mButtonListener);
    }

    @Override
    protected void add_CustomerView()
    {
        vSetTitleText(R.string.workout_name);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem, int iSelectMode)
    {
        this.workoutItem = workoutItem;
        iMode = iSelectMode;
        add_ConfirmButton();

        vSetInput(workoutItem.sName);
    }

    private void add_ConfirmButton()
    {
        if(iMode != MODE_CREATE)
        {
            removeView(imageView_Confirm);
            addRtxTextViewToLayout(fillerTextView_Save, R.string.save, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
            fillerTextView_Save.setMode(1);
        }
    }

    @Override
    protected void setConfirmButtonEnable(boolean bFlag)
    {
        if(iMode != MODE_CREATE)
        {
            if(bFlag)
            {
                fillerTextView_Save.setBackgroundColor(Common.Color.login_button_yellow);
            }
            else
            {
                fillerTextView_Save.setBackgroundColor(Common.Color.gray_4);
            }

        }
        else
        {
            int resId = 0;

            if(bFlag)
            {
                resId = R.drawable.next_big_button_enable;
            }
            else
            {
                resId = R.drawable.next_big_button_disable;
            }
            imageView_Confirm.setImageResource(resId);
            imageView_Confirm.setEnabled(bFlag);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void vClickButton_Back()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);

        if(iMode != MODE_CREATE)
        {
            mMainActivity.mMainProcTreadmill.myWorkoutProc.vBackPreState();
        }
        else
        {
            vSetInput("");
            mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_MAIN);
        }
    }

    @Override
    protected void vClickButton_Confirm()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        {
            workoutItem.sName = sGetInput();
            mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
        }
    }

    @Override
    protected void onCustomerClick(View v)
    {
        if(v == fillerTextView_Save)    { vClickButton_Confirm(); }
    }
}
