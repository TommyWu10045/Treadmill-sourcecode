package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxPercentBlockView;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Distance_Layout extends Rtx_TargetTrainSimple_BaseLayout {

    protected Context mContext;
    protected RtxPercentBlockView mRtxPercentBlockView;
    protected RtxTextView         textView_PercentVal;
    protected RtxImageView        imageView_TypeIcon;

    public TargetTrainSimple_Distance_Layout(Context context, int iMode) {
        super(context,iMode);

        mContext = context;
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void display()
    {
        init_View();
        add_View();

        add_ClickView();
    }

    public void setArg(UiDataStruct.TargetTrainInfo data)
    {
        {
            setType(data.targetTrainInfo_Dis.sExerciseType);
        }

        {
            int iPercent = 0;

            //20181223 修正變更轉換單位所產生的誤差
            iPercent = (int)((Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(data.targetTrainInfo_Dis.fCurrentVal), 1) * 100f)/Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(data.targetTrainInfo_Dis.fTargetVal), 1));
            setPercentVal(iPercent);
        }

        {
            setLeft(data.targetTrainInfo_Dis.iDays_Left,true);
        }
    }

    public void onDestroy()
    {
//        removeAllViews();
//        System.gc();
    }

    protected void init_View()
    {
        setInfoTitle(R.string.distance);

        if(mRtxPercentBlockView == null)    { mRtxPercentBlockView = new RtxPercentBlockView(mContext); }
        if(textView_PercentVal == null)     { textView_PercentVal = new RtxTextView(mContext); }
        if(imageView_TypeIcon == null)      { imageView_TypeIcon = new RtxImageView(mContext); }

    }


    protected void add_View()
    {
        {
            addViewToLayout(mRtxPercentBlockView, 48, 187, 328, 63);
        }

        {
            addRtxTextViewToLayout(textView_PercentVal, 66.67f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 82, 426, 88);
            addRtxImageViewToLayout(imageView_TypeIcon, R.drawable.target_type_a, 287, 19, 45, 45);

        }
    }

    final int TYPE_R = 0;
    final int TYPE_E = 1;
    final int TYPE_B = 2;
    final int TYPE_A = 3;

    public void setType(String sType)
    {
        int iResId = -1;

        if(sType.equals("R")) { iResId = R.drawable.target_type_r; }
        else
        if(sType.equals("E")) { iResId = R.drawable.target_type_e; }
        else
        if(sType.equals("B")) { iResId = R.drawable.target_type_b; }
        else
        if(sType.equals("A")) { iResId = R.drawable.target_type_a; }

        if(iResId != -1)
        {
            if(imageView_TypeIcon != null)
            {
                imageView_TypeIcon.setImageResource(iResId);
            }
        }
    }

    public void setPercentVal(int iVal)
    {
        if(iVal < 0)
        {
            iVal = 0;
        }
        else
        if(iVal > 100)
        {
            iVal = 100;
        }

        mRtxPercentBlockView.setPercentValue(iVal);
        textView_PercentVal.setText(Rtx_TranslateValue.sInt2String(iVal) + "%");
    }
}
