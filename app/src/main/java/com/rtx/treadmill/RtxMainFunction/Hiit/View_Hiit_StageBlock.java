package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by jerry on 2016/12/29.
 */

public class View_Hiit_StageBlock extends Rtx_BaseLayout {

    private Context mContext;



    //Sprint
    private RtxView         view_Sprint_Background;
    private RtxTextView     textView_Sprint_Name;

    private RtxImageView    imageView_Sprint_Time;
    private RtxImageView    imageView_Sprint_Speed;
    private RtxImageView    imageView_Sprint_Incline;

    private RtxTextView     textView_Sprint_Time;
    private RtxTextView     textView_Sprint_Speed;
    private RtxTextView     textView_Sprint_Incline;

    //Recovery
    private RtxView         view_Recovery_Background;
    private RtxTextView     textView_Recovery_Name;

    private RtxImageView    imageView_Recovery_Time;
    private RtxImageView    imageView_Recovery_Speed;
    private RtxImageView    imageView_Recovery_Incline;

    private RtxTextView     textView_Recovery_Time;
    private RtxTextView     textView_Recovery_Speed;
    private RtxTextView     textView_Recovery_Incline;

    public View_Hiit_StageBlock(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void display()
    {
        init_View();
        add_View();
    }

    private void init_View()
    {
        //Sprint
        if(view_Sprint_Background == null)          { view_Sprint_Background = new RtxView(mContext); }
        if(textView_Sprint_Name == null)            { textView_Sprint_Name = new RtxTextView(mContext); }

        if(imageView_Sprint_Time == null)           { imageView_Sprint_Time = new RtxImageView(mContext); }
        if(imageView_Sprint_Speed == null)          { imageView_Sprint_Speed = new RtxImageView(mContext); }
        if(imageView_Sprint_Incline == null)        { imageView_Sprint_Incline = new RtxImageView(mContext); }

        if(textView_Sprint_Time == null)            { textView_Sprint_Time = new RtxTextView(mContext); }
        if(textView_Sprint_Speed == null)           { textView_Sprint_Speed = new RtxTextView(mContext); }
        if(textView_Sprint_Incline == null)         { textView_Sprint_Incline = new RtxTextView(mContext); }

        //Recovery
        if(view_Recovery_Background == null)        { view_Recovery_Background = new RtxView(mContext); }
        if(textView_Recovery_Name == null)          { textView_Recovery_Name = new RtxTextView(mContext); }

        if(imageView_Recovery_Time == null)         { imageView_Recovery_Time = new RtxImageView(mContext); }
        if(imageView_Recovery_Speed == null)        { imageView_Recovery_Speed = new RtxImageView(mContext); }
        if(imageView_Recovery_Incline == null)      { imageView_Recovery_Incline = new RtxImageView(mContext); }

        if(textView_Recovery_Time == null)          { textView_Recovery_Time = new RtxTextView(mContext); }
        if(textView_Recovery_Speed == null)         { textView_Recovery_Speed = new RtxTextView(mContext); }
        if(textView_Recovery_Incline == null)       { textView_Recovery_Incline = new RtxTextView(mContext); }

        setViewTouchDisable(view_Sprint_Background);
        setViewTouchDisable(textView_Sprint_Name);
        setViewTouchDisable(imageView_Sprint_Time);
        setViewTouchDisable(imageView_Sprint_Speed);
        setViewTouchDisable(imageView_Sprint_Incline);
        setViewTouchDisable(textView_Sprint_Time);
        setViewTouchDisable(textView_Sprint_Speed);
        setViewTouchDisable(textView_Sprint_Incline);

        setViewTouchDisable(view_Recovery_Background);
        setViewTouchDisable(textView_Recovery_Name);
        setViewTouchDisable(imageView_Recovery_Time);
        setViewTouchDisable(imageView_Recovery_Speed);
        setViewTouchDisable(imageView_Recovery_Incline);
        setViewTouchDisable(textView_Recovery_Time);
        setViewTouchDisable(textView_Recovery_Speed);
        setViewTouchDisable(textView_Recovery_Incline);
    }

    private void add_View()
    {
        removeAllViews();

        addViewToLayout(view_Sprint_Background,0,0,120,222);
        view_Sprint_Background.setBackgroundColor(Common.Color.yellow_1);
        addRtxTextViewToLayout(textView_Sprint_Name, -1, 13, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 34 - 10, 120, 12 + 20);

        addViewToLayout(view_Recovery_Background,0,222,120,222);
        view_Recovery_Background.setBackgroundColor(Common.Color.blue_1);
        addRtxTextViewToLayout(textView_Recovery_Name, -1, 12, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 34 - 10 + 222, 120, 12 + 20);
    }

    private void add_SprintView()
    {
        addRtxImagePaddingViewToLayout(imageView_Sprint_Time, R.drawable.hiit_block_time, 24, 77, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Sprint_Speed, R.drawable.hiit_block_speed, 24, 125, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Sprint_Incline, R.drawable.hiit_block_incline, 24, 178, 16, 12, 0);

        addRtxTextViewToLayout(textView_Sprint_Time, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 77 - 10, 55, 19 + 20);
        addRtxTextViewToLayout(textView_Sprint_Speed, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 125 - 10, 55, 19 + 20);
        addRtxTextViewToLayout(textView_Sprint_Incline, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 178 - 10, 55, 12 + 20);
    }

    private void add_Recovery()
    {
        addRtxImagePaddingViewToLayout(imageView_Recovery_Time, R.drawable.hiit_recovery_block_time, 24, 77 + 222, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Recovery_Speed, R.drawable.hiit_recovery_block_speed, 24, 125 + 222, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Recovery_Incline, R.drawable.hiit_recovery_block_incline, 24, 178 + 222, 16, 12, 0);

        addRtxTextViewToLayout(textView_Recovery_Time, -1, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 77 + 222 - 10, 55, 19 + 20);
        addRtxTextViewToLayout(textView_Recovery_Speed, -1, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 125 + 222 - 10, 55, 19 + 20);
        addRtxTextViewToLayout(textView_Recovery_Incline, -1, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 178 + 222 - 10, 55, 12 + 20);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetData(int iIndex, UiDataStruct.INTERVAL_STAGE_INFO stageSprintInfo, UiDataStruct.INTERVAL_STAGE_INFO stageRecoveryInfo)
    {
        {
            String sSprint = LanguageData.s_get_string(mContext, R.string.sprint);
            textView_Sprint_Name.setText(sSprint + " " + Rtx_TranslateValue.sInt2String(iIndex));

            if(stageSprintInfo == null || stageSprintInfo.iTime <= 0)
            {
                view_Sprint_Background.setBackgroundColor(Common.Color.yellow_8);
                textView_Sprint_Name.setTextColor(Common.Color.purple_2);
                vSetSprintData(0, 0, 0);
            }
            else
            {
                view_Sprint_Background.setBackgroundColor(Common.Color.yellow_1);
                textView_Sprint_Name.setTextColor(Common.Color.purple_2);
                vSetSprintData(stageSprintInfo.iTime, stageSprintInfo.fGetSpeed(), stageSprintInfo.fGetIncline());
            }
        }

        {
            String sRecovery = LanguageData.s_get_string(mContext, R.string.recovery);
            textView_Recovery_Name.setText(sRecovery + " " + Rtx_TranslateValue.sInt2String(iIndex));

            if(stageRecoveryInfo == null || stageRecoveryInfo.iTime <= 0)
            {
                view_Recovery_Background.setBackgroundColor(Common.Color.blue_11);
                textView_Recovery_Name.setTextColor(Common.Color.gray_9);
                vSetRecoveryData(0, 0, 0);
            }
            else
            {
                view_Recovery_Background.setBackgroundColor(Common.Color.blue_1);
                textView_Recovery_Name.setTextColor(Common.Color.white);
                vSetRecoveryData(stageRecoveryInfo.iTime, stageRecoveryInfo.fGetSpeed(), stageRecoveryInfo.fGetIncline());
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vSetSprintData(int iTime, float fSpeed, float fIncline)
    {
        if(iTime != 0)
        {
            add_SprintView();
        }
        else
        {
            //remove_SprintView();
        }

        textView_Sprint_Time.setText(Rtx_TranslateValue.sInt2String(iTime));
        //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
        if(fSpeed > EngSetting.f_Get_Max_Speed() && (fSpeed - 1) < EngSetting.f_Get_Max_Speed())
        {
            fSpeed = EngSetting.f_Get_Max_Speed();
        }
        if(fSpeed < EngSetting.f_Get_Min_Speed() && (fSpeed + 1) > EngSetting.f_Get_Min_Speed())
        {
            fSpeed = EngSetting.f_Get_Min_Speed();
        }
        textView_Sprint_Speed.setText(Rtx_TranslateValue.sFloat2String(fSpeed,1));
        textView_Sprint_Incline.setText(Rtx_TranslateValue.sFloat2String(fIncline,1));
    }

    private void vSetRecoveryData(int iTime, float fSpeed, float fIncline)
    {
        if(iTime != 0)
        {
            add_Recovery();
        }
        else
        {
            //remove_RecoveryView();
        }

        textView_Recovery_Time.setText(Rtx_TranslateValue.sInt2String(iTime));
        //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
        if(fSpeed > EngSetting.f_Get_Max_Speed() && (fSpeed - 1) < EngSetting.f_Get_Max_Speed())
        {
            fSpeed = EngSetting.f_Get_Max_Speed();
        }
        if(fSpeed < EngSetting.f_Get_Min_Speed() && (fSpeed + 1) > EngSetting.f_Get_Min_Speed())
        {
            fSpeed = EngSetting.f_Get_Min_Speed();
        }
        textView_Recovery_Speed.setText(Rtx_TranslateValue.sFloat2String(fSpeed,1));
        textView_Recovery_Incline.setText(Rtx_TranslateValue.sFloat2String(fIncline,1));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
