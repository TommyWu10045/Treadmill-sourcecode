package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_SelectType extends Rtx_BaseLayout {

    private Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private RtxImageView    imageView_Running;
    private RtxImageView    imageView_Biking;
    private RtxImageView    imageView_Elliptical;
    private RtxImageView    imageView_All;

    private RtxTextView     textView_Description;

    private RtxTextView     textView_Running;
    private RtxTextView     textView_Biking;
    private RtxTextView     textView_Elliptical;
    private RtxTextView     textView_All;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_SelectType(Context context, MainActivity mMainActivity) {
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

    private void init_View()
    {
        {
            init_BackPrePage();
            init_Title();
            vSetTitleText(R.string.distance);
        }

        //ImageView
        {
            if (imageView_Running == null)      { imageView_Running = new RtxImageView(mContext); }
            if (imageView_Biking == null)       { imageView_Biking = new RtxImageView(mContext); }
            if (imageView_Elliptical == null)   { imageView_Elliptical = new RtxImageView(mContext); }
            if (imageView_All == null)          { imageView_All = new RtxImageView(mContext); }
        }

        //TextView
        {
            if(textView_Description == null)    { textView_Description = new RtxTextView(mContext); }
            if(textView_Running == null)        { textView_Running = new RtxTextView(mContext); }
            if(textView_Biking == null)         { textView_Biking = new RtxTextView(mContext); }
            if(textView_Elliptical == null)     { textView_Elliptical = new RtxTextView(mContext); }
            if(textView_All == null)            { textView_All = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Running.setOnClickListener(mButtonListener);
        imageView_Biking.setOnClickListener(mButtonListener);
        imageView_Elliptical.setOnClickListener(mButtonListener);
        imageView_All.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Running,      R.drawable.exercise_type_r,   88, 289, 170, 170);
        addRtxImageViewToLayout(imageView_Biking,       R.drawable.exercise_type_b,  399, 289, 170, 170);
        addRtxImageViewToLayout(imageView_Elliptical,   R.drawable.exercise_type_e,  710, 289, 170, 170);
        addRtxImageViewToLayout(imageView_All,          R.drawable.exercise_type_a, 1021, 289, 170, 170);

        addRtxTextViewToLayout(textView_Description, R.string.select_exercise_type   , 27.99f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER,   -1, 192 - 50, 1280, 21 + 100);

        addRtxTextViewToLayout(textView_Running   , R.string.running   , 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER,   88 - 50, 504 - 50, 170 + 100, 25 + 100);
        addRtxTextViewToLayout(textView_Biking    , R.string.biking    , 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER,  399 - 50, 504 - 50, 170 + 100, 25 + 100);
        addRtxTextViewToLayout(textView_Elliptical, R.string.elliptical, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER,  710 - 50, 504 - 50, 170 + 100, 25 + 100);
        addRtxTextViewToLayout(textView_All       , R.string.all       , 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1021 - 50, 504 - 50, 170 + 100, 25 + 100);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    private void vEnterNextState()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_DIS_SET);
    }

    private void vClearHighlightIcon()
    {
        imageView_Running.setImageResource(R.drawable.exercise_type_r);
        imageView_Biking.setImageResource(R.drawable.exercise_type_b);
        imageView_Elliptical.setImageResource(R.drawable.exercise_type_e);
        imageView_All.setImageResource(R.drawable.exercise_type_a);
    }

    private void vHighlightIcon(int iType)
    {
        vClearHighlightIcon();

        if(iType == EngSetting.RUNNING || iType == EngSetting.RUNNING6 || iType == EngSetting.RUNNING7)
        {
            imageView_Running.setImageResource(R.drawable.exercise_type_r_down);
        }
        else if(iType == EngSetting.RBIKING || iType == EngSetting.RBIKING6)
        {
            imageView_Biking.setImageResource(R.drawable.exercise_type_b_down);
        }
        else if(iType == EngSetting.ELLIPTICAL || iType == EngSetting.ELLIPTICAL6)
        {
            imageView_Elliptical.setImageResource(R.drawable.exercise_type_e_down);
        }
        else if(iType == EngSetting.ALL)
        {
            imageView_All.setImageResource(R.drawable.exercise_type_a_down);
        }
        else
        {

        }
    }

    private void vClickRunning()
    {
        vHighlightIcon(EngSetting.RUNNING);
        cloud_TargetGoal.setType(EngSetting.RUNNING);
        vEnterNextState();
    }

    private void vClickBiking()
    {
        vHighlightIcon(EngSetting.RBIKING);
        cloud_TargetGoal.setType(EngSetting.RBIKING);
        vEnterNextState();
    }

    private void vClickElliptical()
    {
        vHighlightIcon(EngSetting.ELLIPTICAL);
        cloud_TargetGoal.setType(EngSetting.ELLIPTICAL);
        vEnterNextState();
    }

    private void vClickAll()
    {
        vHighlightIcon(EngSetting.ALL);
        cloud_TargetGoal.setType(EngSetting.ALL);
        vEnterNextState();
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)     { vBackPrePage(); }
            else if(v == imageView_Running)    { vClickRunning(); }
            else if(v == imageView_Biking)     { vClickBiking(); }
            else if(v == imageView_Elliptical) { vClickElliptical(); }
            else if(v == imageView_All)        { vClickAll(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vResumeSetting(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        if(cloud_TargetGoal == null)
        {
            return;
        }

        int iType = -1;

        iType = cloud_TargetGoal.getType();

        //if(iType == -1)
        {
            vClearHighlightIcon();
        }
        //else
        //{
        //    vHighlightIcon(iType);
        //}
    }

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;
        vResumeSetting(this.cloud_TargetGoal);
    }
}
