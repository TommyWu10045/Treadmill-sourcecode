package com.rtx.treadmill.RtxMainFunction.MyWorkout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkout_Details_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private MainActivity        mMainActivity;
    private ButtonListener      mButtonListener;

    private RtxImageView        imageView_Icon_1st;
    private RtxImageView        imageView_Icon_2st;
    private RtxImageView        imageView_Icon_3st;
    private RtxImageView        imageView_Icon_4st;
    private RtxImageView        imageView_Icon_5st;

    private RtxDoubleStringView doubleStringView_Val_1st;
    private RtxDoubleStringView doubleStringView_Val_2st;
    private RtxDoubleStringView doubleStringView_Val_3st;
    private RtxDoubleStringView doubleStringView_Val_4st;
    private RtxDoubleStringView doubleStringView_Val_5st;

    private RtxTextView         textView_Item_1st;
    private RtxTextView         textView_Item_2st;
    private RtxTextView         textView_Item_3st;
    private RtxTextView         textView_Item_4st;
    private RtxTextView         textView_Item_5st;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    public MyWorkout_Details_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

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

        init_BackPrePage();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_TopBackground();
            init_Title();
            init_BackPrePage();
        }

        {
            if(imageView_Icon_1st == null)           { imageView_Icon_1st = new RtxImageView(mContext); }
            if(imageView_Icon_2st == null)           { imageView_Icon_2st = new RtxImageView(mContext); }
            if(imageView_Icon_3st == null)           { imageView_Icon_3st = new RtxImageView(mContext); }
            if(imageView_Icon_4st == null)           { imageView_Icon_4st = new RtxImageView(mContext); }
            if(imageView_Icon_5st == null)           { imageView_Icon_5st = new RtxImageView(mContext); }

            if(doubleStringView_Val_1st == null)     { doubleStringView_Val_1st = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_2st == null)     { doubleStringView_Val_2st = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_3st == null)     { doubleStringView_Val_3st = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_4st == null)     { doubleStringView_Val_4st = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_5st == null)     { doubleStringView_Val_5st = new RtxDoubleStringView(mContext); }

            if(textView_Item_1st == null)            { textView_Item_1st = new RtxTextView(mContext); }
            if(textView_Item_2st == null)            { textView_Item_2st = new RtxTextView(mContext); }
            if(textView_Item_3st == null)            { textView_Item_3st = new RtxTextView(mContext); }
            if(textView_Item_4st == null)            { textView_Item_4st = new RtxTextView(mContext); }
            if(textView_Item_5st == null)            { textView_Item_5st = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxImagePaddingViewToLayout(imageView_Icon_1st, R.drawable.workout_details_duration, 240, 210, 54, 54, 0);
        addRtxImagePaddingViewToLayout(imageView_Icon_2st, R.drawable.workout_details_distance, 616, 205, 45, 64, 0);
        addRtxImagePaddingViewToLayout(imageView_Icon_3st, R.drawable.workout_details_calories, 1014, 203, 51, 64, 0);
        addRtxImagePaddingViewToLayout(imageView_Icon_4st, R.drawable.workout_details_avg_pace, 428, 498, 56, 67, 0);
        addRtxImagePaddingViewToLayout(imageView_Icon_5st, R.drawable.workout_details_avg_speed, 814, 511, 65, 34, 0);

        doubleStringView_Val_1st.setPaint(Common.Font.Relay_Black, Common.Color.white, 66.67f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 33.05f);
        doubleStringView_Val_2st.setPaint(Common.Font.Relay_Black, Common.Color.white, 66.67f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 33.05f);
        doubleStringView_Val_3st.setPaint(Common.Font.Relay_Black, Common.Color.white, 66.67f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 33.05f);
        doubleStringView_Val_4st.setPaint(Common.Font.Relay_Black, Common.Color.white, 66.67f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 33.05f);
        doubleStringView_Val_5st.setPaint(Common.Font.Relay_Black, Common.Color.white, 66.67f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 33.05f);

        addViewToLayout(doubleStringView_Val_1st, 84, 291, 362, 70);
        addViewToLayout(doubleStringView_Val_2st, 457, 291, 362, 70);
        addViewToLayout(doubleStringView_Val_3st, 858, 291, 362, 70);
        addViewToLayout(doubleStringView_Val_4st, 276, 591, 362, 70);
        addViewToLayout(doubleStringView_Val_5st, 666, 591, 362, 70);

        addRtxTextViewToLayout(textView_Item_1st, R.string.duration , 20.07f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 84, 375 - 25, 362, 21 + 50);
        addRtxTextViewToLayout(textView_Item_2st, R.string.distance , 20.07f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 457, 375 - 25, 362, 21 + 50);
        addRtxTextViewToLayout(textView_Item_3st, R.string.calories , 20.07f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 858, 375 - 25, 362, 21 + 50);
        addRtxTextViewToLayout(textView_Item_4st, R.string.avg_pace , 20.07f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 276, 679 - 25, 362, 21 + 50);
        addRtxTextViewToLayout(textView_Item_5st, R.string.avg_speed, 20.07f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 666, 679 - 25, 362, 21 + 50);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem)
    {
        this.workoutItem = workoutItem;
        vSetTitleText(this.workoutItem.sName);

        {
            String sDuration = null;
            String sDistance = null;
            String sCalories = null;
            String sAvgPace = null;
            String sAvgSpeed = null;

            sDuration = new String(Rtx_Calendar.sMin2Str(workoutItem.iGetTotalMin(),null));
            sDistance = new String(Rtx_TranslateValue.sFloat2String(workoutItem.fGetTotalDistance(),1));
            sCalories = new String(Rtx_TranslateValue.sInt2String(workoutItem.iGetCalories(CloudDataStruct.CloudData_17.f_get_user_weight())));
            sAvgPace = new String(Rtx_Calendar.sSec2Str(workoutItem.iGetAvgPaceSec(),null));
            //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
            if(workoutItem.fGetAvgSpeed() > EngSetting.f_Get_Max_Speed() && (workoutItem.fGetAvgSpeed() - 1) < EngSetting.f_Get_Max_Speed())
            {
                sAvgSpeed = new String(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_Speed(),1));
            }
            else if(workoutItem.fGetAvgSpeed() < EngSetting.f_Get_Min_Speed() && (workoutItem.fGetAvgSpeed() + 1) > EngSetting.f_Get_Min_Speed())
            {
                sAvgSpeed = new String(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_Speed(),1));
            }
            else
            {
                sAvgSpeed = new String(Rtx_TranslateValue.sFloat2String(workoutItem.fGetAvgSpeed(),1));
            }

            vSetInfo(sDuration,sDistance,sCalories,sAvgPace,sAvgSpeed);
        }


    }

    protected void vSetInfo(String sDuration, String sDistance, String sCalories, String sAvgPace, String sAvgSpeed)
    {
        doubleStringView_Val_1st.setText(sDuration,"");
        doubleStringView_Val_2st.setText(sDistance, EngSetting.Distance.getUnitString(mContext));
        doubleStringView_Val_3st.setText(sCalories, LanguageData.s_get_string(mContext,R.string.kcal));
        doubleStringView_Val_4st.setText(sAvgPace,"");
        doubleStringView_Val_5st.setText(sAvgSpeed,EngSetting.Distance.getSpeedUnitLowerString(mContext));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackButton()
    {
        mMainActivity.mMainProcTreadmill.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_PREVIEW);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                                      { vBackButton(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
