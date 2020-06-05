package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_EditText_Input_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutState;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxFillerTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Personal_Name_Layout extends Rtx_EditText_Input_BaseLayout {

    private int iMode = -1;

    private Context mContext;
    private MainActivity   mMainActivity;

    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;

    private RtxFillerTextView   fillerTextView_Save;

    private HiitProc    hiitProc;

    public Hiit_Personal_Name_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
        super(context);

        mContext = context;
        this.hiitProc = hiitProc;

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
        vSetTitleText(R.string.name);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO itemInfo, int iPersonalMode)
    {
        this.hiitItemInfo = itemInfo;
        iMode = iPersonalMode;
        add_ConfirmButton();

        vSetInput(hiitItemInfo.sGetDisplayName());
    }

    private void add_ConfirmButton()
    {
        if(iMode != hiitProc.MODE_CREATE)
        {
            removeView(imageView_Confirm);
            addRtxTextViewToLayout(fillerTextView_Save, R.string.next, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
            fillerTextView_Save.setMode(1);
            vSetTitleText(R.string.edit_name);
        }
    }

    @Override
    protected void setConfirmButtonEnable(boolean bFlag)
    {
        if(iMode != hiitProc.MODE_CREATE)
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

        if(iMode != hiitProc.MODE_CREATE)
        {
            hiitProc.vBackPreState();
        }
        else
        {
            vSetInput("");
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
        }
    }

    @Override
    protected void vClickButton_Confirm()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        {
            hiitItemInfo.sName = "#H#" + sGetInput();
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        }
    }

    @Override
    protected void onCustomerClick(View v)
    {
        if(v == fillerTextView_Save)    { vClickButton_Confirm(); }
    }
}
