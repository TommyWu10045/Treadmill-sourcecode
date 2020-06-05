package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Login.LoginState;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainChooseTargetLayout extends Rtx_BaseLayout {

    final int TARGET_DIS        = 0;
    final int TARGET_CAL        = 1;
    final int TARGET_FREQ       = 2;
    final int TARGET_WEI        = 3;
    final int TARGET_BDY_FAT    = 4;

    private Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private RtxImageView    imageView_Button_Dis;
    private RtxImageView    imageView_Button_Cal;
    private RtxImageView    imageView_Button_Freq;
    private RtxImageView    imageView_Button_Wei;
    private RtxImageView    imageView_Button_BdyFat;

    private RtxTextView     textView_Dis;
    private RtxTextView     textView_Cal;
    private RtxTextView     textView_Freq;
    private RtxTextView     textView_Wei;
    private RtxTextView     textView_BdyFat;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainChooseTargetLayout(Context context, MainActivity mMainActivity) {
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
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    public void setTargetTrainInfo(UiDataStruct.TargetTrainInfo data)
    {
        if(data.bIsDistance())  { enable_Button(TARGET_DIS, false); }
        else                    { enable_Button(TARGET_DIS, true); }

        if(data.bIsCalories())  { enable_Button(TARGET_CAL, false); }
        else                    { enable_Button(TARGET_CAL, true); }

        if(data.bIsFreq())      { enable_Button(TARGET_FREQ, false); }
        else                    { enable_Button(TARGET_FREQ, true); }

        if(data.bIsWeight())    { enable_Button(TARGET_WEI, false); }
        else                    { enable_Button(TARGET_WEI, true); }

        if(data.bIsBodyFat())   { enable_Button(TARGET_BDY_FAT, false); }
        else                    { enable_Button(TARGET_BDY_FAT, true); }
    }

    private void init_View()
    {
        {
            init_BackPrePage();
            init_Title();
            vSetTitleText(R.string.choose_your_fitness_target);
        }

        //ImageView
        {
            if (imageView_Button_Dis == null)       { imageView_Button_Dis = new RtxImageView(mContext); }
            if (imageView_Button_Cal == null)       { imageView_Button_Cal = new RtxImageView(mContext); }
            if (imageView_Button_Freq == null)      { imageView_Button_Freq = new RtxImageView(mContext); }
            if (imageView_Button_Wei == null)       { imageView_Button_Wei = new RtxImageView(mContext); }
            if (imageView_Button_BdyFat == null)    { imageView_Button_BdyFat = new RtxImageView(mContext); }
        }

        {
            if(textView_Dis == null)    { textView_Dis = new RtxTextView(mContext); }
            if(textView_Cal == null)    { textView_Cal = new RtxTextView(mContext); }
            if(textView_Freq == null)   { textView_Freq = new RtxTextView(mContext); }
            if(textView_Wei == null)    { textView_Wei = new RtxTextView(mContext); }
            if(textView_BdyFat == null) { textView_BdyFat = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Button_Dis.setOnClickListener(mButtonListener);
        imageView_Button_Cal.setOnClickListener(mButtonListener);
        imageView_Button_Freq.setOnClickListener(mButtonListener);
        imageView_Button_Wei.setOnClickListener(mButtonListener);
        imageView_Button_BdyFat.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Button_Dis, R.drawable.target_input_dis, 194, 163, 170, 170);
        addRtxImageViewToLayout(imageView_Button_Cal, R.drawable.target_input_cal, 555, 163, 170, 170);
        addRtxImageViewToLayout(imageView_Button_Freq, R.drawable.target_input_freq, 916, 163, 170, 170);
        addRtxImageViewToLayout(imageView_Button_Wei, R.drawable.target_input_wei, 374, 483, 170, 170);
        addRtxImageViewToLayout(imageView_Button_BdyFat, R.drawable.target_input_bdy_fat, 735, 483, 170, 170);

        addRtxTextViewToLayout(textView_Dis, R.string.distance, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 194-100, 379-50, 170+200, 16+100);
        addRtxTextViewToLayout(textView_Cal, R.string.calories, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 555-100, 379-50, 170+200, 16+100);
        addRtxTextViewToLayout(textView_Freq, R.string.exercise_frequency, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 916-100, 379-50, 170+200, 16+100);
        addRtxTextViewToLayout(textView_Wei, R.string.weight_upper, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 374-100, 699-50, 170+200, 16+100);
        addRtxTextViewToLayout(textView_BdyFat, R.string.bodyfat_percent, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 735-100, 699-50, 170+200, 16+100);
    }

    private void enable_Button(int iTarget, boolean bEnable)
    {
        if(bEnable)
        {
            if(iTarget == TARGET_DIS)
            {
                imageView_Button_Dis.setImageResource(R.drawable.target_input_dis);
                imageView_Button_Dis.setClickable(bEnable);
            }
            else if(iTarget == TARGET_CAL)
            {
                imageView_Button_Cal.setImageResource(R.drawable.target_input_cal);
                imageView_Button_Cal.setClickable(bEnable);
            }
            else if(iTarget == TARGET_FREQ)
            {
                imageView_Button_Freq.setImageResource(R.drawable.target_input_freq);
                imageView_Button_Freq.setClickable(bEnable);
            }
            else if(iTarget == TARGET_WEI)
            {
                imageView_Button_Wei.setImageResource(R.drawable.target_input_wei);
                imageView_Button_Wei.setClickable(bEnable);
            }
            else if(iTarget == TARGET_BDY_FAT)
            {
                imageView_Button_BdyFat.setImageResource(R.drawable.target_input_bdy_fat);
                imageView_Button_BdyFat.setClickable(bEnable);
            }
        }
        else
        {
            if(iTarget == TARGET_DIS)
            {
                imageView_Button_Dis.setImageResource(R.drawable.target_input_dis_disable);
                imageView_Button_Dis.setClickable(bEnable);
            }
            else if(iTarget == TARGET_CAL)
            {
                imageView_Button_Cal.setImageResource(R.drawable.target_input_cal_disable);
                imageView_Button_Cal.setClickable(bEnable);
            }
            else if(iTarget == TARGET_FREQ)
            {
                imageView_Button_Freq.setImageResource(R.drawable.target_input_freq_disable);
                imageView_Button_Freq.setClickable(bEnable);
            }
            else if(iTarget == TARGET_WEI)
            {
                imageView_Button_Wei.setImageResource(R.drawable.target_input_wei_disable);
                imageView_Button_Wei.setClickable(bEnable);
            }
            else if(iTarget == TARGET_BDY_FAT)
            {
                imageView_Button_BdyFat.setImageResource(R.drawable.target_input_bdy_fat_disable);
                imageView_Button_BdyFat.setClickable(bEnable);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
    }

    private void vEnterDisAddPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_SELECT_TYPE);
        cloud_TargetGoal.setType(-1);
    }

    private void vEnterCalAddPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_CAL_SET_CAL);
    }

    private void vEnterWeiAddPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_WEI_SET_CURRENT);
    }

    private void vEnterBodyFatAddPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_BDY_FAT_SET_CURRENT);
    }

    private void vEnterFreqAddPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_FREQ_SELECT_DAY);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                      { vBackPrePage(); }
            else if(v == imageView_Button_Dis)                  { vEnterDisAddPage(); }
            else if(v == imageView_Button_Cal)                  { vEnterCalAddPage(); }
            else if(v == imageView_Button_Freq)                 { vEnterFreqAddPage(); }
            else if(v == imageView_Button_Wei)                  { vEnterWeiAddPage(); }
            else if(v == imageView_Button_BdyFat)               { vEnterBodyFatAddPage(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;
    }
}
