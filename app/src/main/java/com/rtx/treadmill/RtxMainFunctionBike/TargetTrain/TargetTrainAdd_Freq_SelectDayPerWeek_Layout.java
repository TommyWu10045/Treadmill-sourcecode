package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_Freq_SelectDayPerWeek_Layout extends Rtx_BaseLayout {

    private     Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private     RtxTextView                     textView_Description;
    private     ArrayList<RtxFillerTextView>    list_CircleButton;

    private     int                             iSelectDay = -1;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_Freq_SelectDayPerWeek_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();

        clearSelect();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_BackPrePage();
            init_Title();
            vSetTitleText(R.string.exercise_frequency);
        }

        {
            if(textView_Description == null)    { textView_Description = new RtxTextView(mContext); }
            if(list_CircleButton == null)       { list_CircleButton = new ArrayList<>(); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Description,R.string.how_many_days_per_week,29.74f, Common.Color.blue_1, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 377 - 100, 247 - 50, 527 + 200, 23 + 100);
        addListButton();
    }

    private void addListButton()
    {
        int iW = 138;
        int iH = 138;
        int iGap = 31;
        int iPosX = (1280 - (iW * 7) - (iGap * 6)) / 2;
        int iPosY = 392;

        int iIndex = 0;
        int iDaysOfWeek = 7;

        for( ; iIndex < iDaysOfWeek ; iIndex++)
        {
            RtxFillerTextView fillerTextView_Day = new RtxFillerTextView(mContext);

            fillerTextView_Day.setBackgroundColor(Common.Color.blue_1);
            fillerTextView_Day.setStrokeWidth(2);
            fillerTextView_Day.setMode(2);

            list_CircleButton.add(fillerTextView_Day);

            if(iIndex != 0)
            {
                iPosX = iPosX + (iGap + iW);
            }

            list_CircleButton.get(iIndex).setOnClickListener(mButtonListener);

            addRtxTextViewToLayout(list_CircleButton.get(iIndex), Rtx_TranslateValue.sInt2String(iIndex + 1), 66.66f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, iPosY, iW, iH);
        }
    }

    private void setValue(int iIndex , boolean bSelected)
    {
        if(bSelected)
        {
            iSelectDay = iIndex;
            list_CircleButton.get(iIndex).setMode(1);
            list_CircleButton.get(iIndex).setBackgroundColor(Common.Color.yellow);
        }
        else
        {
            list_CircleButton.get(iIndex).setMode(2);
            list_CircleButton.get(iIndex).setBackgroundColor(Common.Color.blue_1);
        }
        list_CircleButton.get(iIndex).setVisibility(INVISIBLE);
        list_CircleButton.get(iIndex).setVisibility(VISIBLE);
    }

    public void clearSelect()
    {
        iSelectDay = -1;

        setValue(0,false);
        setValue(1,false);
        setValue(2,false);
        setValue(3,false);
        setValue(4,false);
        setValue(5,false);
        setValue(6,false);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    private void vConfirm()
    {
        {
            cloud_TargetGoal.sGol_Item = "Frequency";
            cloud_TargetGoal.sGol_Val = Rtx_TranslateValue.sInt2String(getDaysPerWeek());
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_SET_WEEK);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                      { vBackPrePage(); }
            else
            {
                int iIndex = 0;
                int iDaysOfWeek = 7;

                for( ; iIndex < iDaysOfWeek ; iIndex++)
                {
                    if(v == list_CircleButton.get(iIndex))
                    {
                        setValue(iIndex,true);
                        iSelectDay = iIndex + 1;
                    }
                    else
                    {
                        setValue(iIndex,false);
                    }
                }

                vConfirm();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected int getDaysPerWeek()
    {
        return iSelectDay;
    }

    protected Calendar cGetStartValidDate()
    {
        Calendar cStartValidDate = GlobalData.getInstance();

        if(Rtx_Calendar.iGetDaysLeftOfWeek(cStartValidDate) >= iSelectDay)
        {
            cStartValidDate.set(Calendar.DAY_OF_WEEK,1);
            cStartValidDate.add(Calendar.WEEK_OF_YEAR,2);
        }
        else
        {
            cStartValidDate.set(Calendar.DAY_OF_WEEK,1);
            cStartValidDate.add(Calendar.WEEK_OF_YEAR,3);
        }

        cStartValidDate.set(Calendar.AM_PM,Calendar.AM);
        cStartValidDate.set(Calendar.HOUR,0);
        cStartValidDate.set(Calendar.MINUTE,0);
        cStartValidDate.set(Calendar.SECOND,0);
        cStartValidDate.set(Calendar.MILLISECOND,0);

        return cStartValidDate;
    }

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;
    }
}

