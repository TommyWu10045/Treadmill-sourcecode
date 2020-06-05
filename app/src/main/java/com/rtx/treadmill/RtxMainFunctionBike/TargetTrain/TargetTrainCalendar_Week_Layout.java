package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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
import com.rtx.treadmill.RtxView.RtxCalendarView;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainCalendar_Week_Layout extends Rtx_BaseLayout {


    private Context mContext;

    private ButtonListener          mButtonListener;
    private MainActivity            mMainActivity;

    private RtxImageView            imageView_Confirm;

    private RtxDoubleStringView     doubleStringView_TotalDays;
    private RtxCalendarView         calendarView;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    Calendar cal_Select;
    Calendar cal_Start;
    int iDiffDays = 0;
    int iTotalWeek = 0;
    int iSelectDay = 0;

    public TargetTrainCalendar_Week_Layout(Context context, MainActivity mMainActivity) {
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

        if(cal_Select == null)
        {
            setConfirmEnable(false);
        }
        else
        {
            setConfirmEnable(true);
        }
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
            vSetTitleText(R.string.dates);
        }

        //ImageView
        {
            if (imageView_Confirm == null)          { imageView_Confirm = new RtxImageView(mContext); }
        }

        {
            if(doubleStringView_TotalDays == null)  { doubleStringView_TotalDays = new RtxDoubleStringView(mContext); }
        }

        {
            if(calendarView == null)                { calendarView = new RtxCalendarView(mContext,600,650,mMainActivity.mMainProcBike.targetTrainProc.cGetValidStartDate()); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Confirm.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Confirm,R.drawable.next_big_button_enable,1097,628,133,133);

        addViewToLayout(doubleStringView_TotalDays,860-300,359-50,68+600,96+100);
        doubleStringView_TotalDays.setPaint(Common.Font.Relay_Black,Common.Color.white,100.0f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,55.00f);
        doubleStringView_TotalDays.setGap(32);

        init_RtxCalendarView();

    }

    private void init_RtxCalendarView()
    {
        int iW,iH,iX,iY;

        iW = 600;
        iH = 650;
        iX = 100;
        iY = ((800 - 39 - 75 - iH) / 2) + 39 + 75;

        if(calendarView != null)
        {
            removeView(calendarView);
        }

        addViewToLayout(calendarView,iX,iY,iW,iH);

        calendarView.setOnChangeListener(new RtxCalendarView.OnChangeListener() {
            @Override
            public void onChange(Calendar cal, boolean bValidDate)
            {
                if(bValidDate)
                {
                    setDate(cal);
                    setConfirmEnable(true);
                }
                else
                {
                    setDate(null);
                    setConfirmEnable(false);
                }
            }
        });
    }

    public void setConfirmEnable(boolean bEnable)
    {
        if(imageView_Confirm != null)
        {
            if(bEnable)
            {
                imageView_Confirm.setImageResource(R.drawable.next_big_button_enable);
                imageView_Confirm.setClickable(true);
            }
            else
            {
                imageView_Confirm.setImageResource(R.drawable.next_big_button_disable);
                imageView_Confirm.setClickable(false);
            }
        }
    }

    public void setDate(Calendar cal)
    {
        if(cal == null)
        {
            if(doubleStringView_TotalDays != null)
            {
                doubleStringView_TotalDays.setText("0",R.string.week);
            }
        }
        else
        {
            cal_Select = cal;

            cal_Start = mMainActivity.mMainProcBike.targetTrainProc.cGetValidStartDate();
            iDiffDays = Rtx_Calendar.getDiffDays(cal_Start,cal_Select) + 1;

//            Log.e("Jerry","cal_Start = " + Rtx_Calendar.sCalendar2Str(cal_Start,"yyyy-MM-dd"));
//            Log.e("Jerry","cal_Select = " + Rtx_Calendar.sCalendar2Str(cal_Select,"yyyy-MM-dd"));
//            Log.e("Jerry","iDiffDays = " + iDiffDays);

            if(iDiffDays >= 0)
            {
                iTotalWeek = (iDiffDays / 7) + 2;
                doubleStringView_TotalDays.setText(Rtx_TranslateValue.sInt2String(iTotalWeek),R.string.weeks);
            }
            else
            {
                doubleStringView_TotalDays.setText("0",R.string.week);
            }
        }
    }

    protected Calendar cGetStartDate()
    {
        Calendar cStartValidDate = GlobalData.getInstance();

        if(Rtx_Calendar.iGetDaysLeftOfWeek(cStartValidDate) >= iSelectDay)
        {

        }
        else
        {
            cStartValidDate.set(Calendar.DAY_OF_WEEK,1);
            cStartValidDate.add(Calendar.WEEK_OF_YEAR,1);
        }

        cStartValidDate.set(Calendar.AM_PM,Calendar.AM);
        cStartValidDate.set(Calendar.HOUR,0);
        cStartValidDate.set(Calendar.MINUTE,0);
        cStartValidDate.set(Calendar.SECOND,0);
        cStartValidDate.set(Calendar.MILLISECOND,0);

        return cStartValidDate;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickButton_Back()
    {
        vClearCalendarView();

        TargetTrainState mTargetTrainState = mMainActivity.mMainProcBike.targetTrainProc.vGerPreState();

        if(mTargetTrainState != TargetTrainState.PROC_NULL)
        {
            mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(mTargetTrainState);
        }
        else
        {
            mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
        }

    }

    private void vClickButton_Confirm()
    {
//        vClearCalendarView();
//        calendarView = new RtxCalendarView(mContext,600,650,mMainActivity.mMainProcBike.targetTrainProc.cGetValidStartDate());
//        init_RtxCalendarView();

        {
            Calendar cStartDate = GlobalData.getInstance();

            if(cal_Start != null)
            {
                cStartDate = (Calendar) cal_Start.clone();
                cStartDate.add(Calendar.WEEK_OF_YEAR,-3);
            }

            cloud_TargetGoal.sStartDate = Rtx_Calendar.sCalendar2Str(cGetStartDate(),"yyyy-MM-dd");
            cloud_TargetGoal.sGol_Duration = Rtx_TranslateValue.sInt2String(iTotalWeek);

//            cloud_TargetGoal.printAllArgs();
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                  { vClickButton_Back(); }
            else if(v == imageView_Confirm)                 { vClickButton_Confirm(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vClearCalendarView()
    {
        if(cal_Select != null)
        {
            cal_Select.clear();
            cal_Select = null;
        }

        setDate(null);

        if(doubleStringView_TotalDays != null)
        {
            doubleStringView_TotalDays.setText("0",R.string.week);
        }

        setConfirmEnable(false);
        calendarView = null;
    }

    public void setWorkoutFreqMode(boolean bFlag)
    {
        calendarView.setDateRangeHighlight(bFlag);
    }

    public void setFreqDaysPerWeek(int iDays)
    {
        calendarView.setSelectDays(iDays);
    }

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;

        iSelectDay = Rtx_TranslateValue.iString2Int(this.cloud_TargetGoal.sGol_Val);

        setFreqDaysPerWeek(iSelectDay);
        setWorkoutFreqMode(true);
    }
}
