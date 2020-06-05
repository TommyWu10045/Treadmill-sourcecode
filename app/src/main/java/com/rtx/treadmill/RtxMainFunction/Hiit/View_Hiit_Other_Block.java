package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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

public class View_Hiit_Other_Block extends Rtx_BaseLayout {

    private int MODE_WARM_UP = 0;
    private int MODE_COOL_DOWN = 1;

    private int iMode = -1;

    private Context mContext;

    //Sprint
    private RtxView         view_Background;
    private RtxTextView     textView_Name;

    private RtxImageView    imageView_Time;
    private RtxImageView    imageView_Speed;
    private RtxImageView    imageView_Incline;

    private RtxTextView     textView_Time;
    private RtxTextView     textView_Speed;
    private RtxTextView     textView_Incline;


    public View_Hiit_Other_Block(Context context, int iMode) {
        super(context);

        mContext = context;

        this.iMode = iMode;

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
        if(view_Background == null)          { view_Background = new RtxView(mContext); }
        if(textView_Name == null)            { textView_Name = new RtxTextView(mContext); }

        if(imageView_Time == null)           { imageView_Time = new RtxImageView(mContext); }
        if(imageView_Speed == null)          { imageView_Speed = new RtxImageView(mContext); }
        if(imageView_Incline == null)        { imageView_Incline = new RtxImageView(mContext); }

        if(textView_Time == null)            { textView_Time = new RtxTextView(mContext); }
        if(textView_Speed == null)           { textView_Speed = new RtxTextView(mContext); }
        if(textView_Incline == null)         { textView_Incline = new RtxTextView(mContext); }


        setViewTouchDisable(view_Background);
        setViewTouchDisable(textView_Name);
        setViewTouchDisable(imageView_Time);
        setViewTouchDisable(imageView_Speed);
        setViewTouchDisable(imageView_Incline);
        setViewTouchDisable(textView_Time);
        setViewTouchDisable(textView_Speed);
        setViewTouchDisable(textView_Incline);
    }

    private void add_View()
    {
        removeAllViews();

        addViewToLayout(view_Background,0,0,113,444);
        addRtxTextViewToLayout(textView_Name, -1, 15, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 34, 113, 40);
    }

    private void add_InfoView()
    {
        addRtxImagePaddingViewToLayout(imageView_Time, R.drawable.hiit_block_time, 24, 167, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.hiit_block_speed, 24, 214, 19, 19, 0);
        addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.hiit_block_incline, 24, 267, 16, 12, 0);

        addRtxTextViewToLayout(textView_Time, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 167, 55, 19);
        addRtxTextViewToLayout(textView_Speed, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 214, 55, 19);
        addRtxTextViewToLayout(textView_Incline, -1, 20f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 51, 267 - 10, 55, 12 + 20);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetData(UiDataStruct.INTERVAL_STAGE_INFO stageInfo)
    {
        if(stageInfo == null)
        {
            return;
        }

        {
            String sText = null;

            if(iMode == MODE_WARM_UP)
            {
                sText = LanguageData.s_get_string(mContext,R.string.warm_up);

                Log.e("Jerry","stageInfo = " + stageInfo);

                if(stageInfo.iTime <= 0)
                {
                    view_Background.setBackgroundColor(Common.Color.hiit_block_warm_disable);
                }
                else
                {
                    view_Background.setBackgroundColor(Common.Color.hiit_block_warm_enable);
                    vSetValData(stageInfo.iTime,stageInfo.fGetSpeed(),stageInfo.fGetIncline());
                }
            }
            else
            {
                sText = LanguageData.s_get_string(mContext,R.string.cool_donw);

                if(stageInfo.iTime <= 0)
                {
                    view_Background.setBackgroundColor(Common.Color.hiit_block_cool_disable);
                }
                else
                {
                    view_Background.setBackgroundColor(Common.Color.hiit_block_cool_enable);
                    vSetValData(stageInfo.iTime,stageInfo.fGetSpeed(),stageInfo.fGetIncline());
                }
            }

            textView_Name.setText(sText);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vSetValData(int iTime, float fSpeed, float fIncline)
    {
        if(iTime > 0)
        {
            add_InfoView();

            textView_Time.setText(Rtx_TranslateValue.sInt2String(iTime));
            //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
            if(fSpeed > EngSetting.f_Get_Max_Speed() && (fSpeed - 1) < EngSetting.f_Get_Max_Speed())
            {
                fSpeed = EngSetting.f_Get_Max_Speed();
            }
            if(fSpeed < EngSetting.f_Get_Min_Speed() && (fSpeed + 1) > EngSetting.f_Get_Min_Speed())
            {
                fSpeed = EngSetting.f_Get_Min_Speed();
            }
            textView_Speed.setText(Rtx_TranslateValue.sFloat2String(fSpeed,1));
            textView_Incline.setText(Rtx_TranslateValue.sFloat2String(fIncline,1));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
