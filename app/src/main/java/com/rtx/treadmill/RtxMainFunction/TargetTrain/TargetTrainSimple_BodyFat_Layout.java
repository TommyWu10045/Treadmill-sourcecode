package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxGaugeView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_BodyFat_Layout extends Rtx_TargetTrainSimple_BaseLayout {

    protected Context mContext;

    protected RtxTextView textView_Val;
    protected RtxTextView textView_LeftStr;
    protected RtxTextView textView_RightStr;
    protected RtxTextView textView_LeftVal;
    protected RtxTextView textView_RightVal;

    protected RtxGaugeView gaugeView;

    public TargetTrainSimple_BodyFat_Layout(Context context, int iMode) {
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
            int iColor = 0xFFFFFFFF;
            iColor = iGetValColor(data.targetTrainInfo_BdyFat.fStartVal, data.targetTrainInfo_BdyFat.fTargetVal, data.targetTrainInfo_BdyFat.fCurrentVal);
            textView_Val.setTextColor(iColor);
        }

        {
            setStartAndTargetVal(data.targetTrainInfo_BdyFat.fStartVal, data.targetTrainInfo_BdyFat.fTargetVal);
            setCurrentVal(data.targetTrainInfo_BdyFat.fCurrentVal);
        }

        {
            setLeft(data.targetTrainInfo_BdyFat.iDays_Left,true);
        }

        {
            gaugeView.vSetVal(data.targetTrainInfo_BdyFat.fCurrentVal);
        }
    }

    public void onDestroy()
    {
//        removeAllViews();
//        System.gc();
    }

    protected void init_View()
    {
        setInfoTitle(R.string.bodyfat_percent);

        if(textView_Val == null)            { textView_Val = new RtxTextView(mContext); }
        if(textView_LeftStr == null)        { textView_LeftStr = new RtxTextView(mContext); }
        if(textView_RightStr == null)       { textView_RightStr = new RtxTextView(mContext); }
        if(textView_LeftVal == null)        { textView_LeftVal = new RtxTextView(mContext); }
        if(textView_RightVal == null)       { textView_RightVal = new RtxTextView(mContext); }

        if(gaugeView == null)               { gaugeView = new RtxGaugeView(mContext); }
    }

    protected void add_View()
    {
        addRtxTextViewToLayout(textView_Val, 66.67f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 71, 426, 88);

        addRtxTextViewToLayout(textView_LeftStr, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 161, 213, 55);
        addRtxTextViewToLayout(textView_RightStr, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 161, 213, 55);

        addRtxTextViewToLayout(textView_LeftVal, 43.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 211, 213, 55);
        addRtxTextViewToLayout(textView_RightVal, 43.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 213, 211, 213, 55);

        addViewToLayout(gaugeView,-1,246,214,214);
//        doubleStringView_Val.setText("85.5","kg");
//        textView_LeftStr.setText("TARGET");
//        textView_RightStr.setText("START");
//        textView_LeftVal.setText("83.0");
//        textView_RightVal.setText("87.5");
    }

    protected void setStartAndTargetVal(float fStart , float fTarget)
    {
        if(fStart >= fTarget)
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.target));
            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.start));

            textView_LeftVal.setText(Rtx_TranslateValue.sFloat2String(fTarget,1) + "%");
            textView_RightVal.setText(Rtx_TranslateValue.sFloat2String(fStart,1) + "%");

            gaugeView.vSetLimit(fTarget,fStart);
        }
        else
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.start));
            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.target));

            textView_LeftVal.setText(Rtx_TranslateValue.sFloat2String(fStart,1) + "%");
            textView_RightVal.setText(Rtx_TranslateValue.sFloat2String(fTarget,1) + "%");

            gaugeView.vSetLimit(fStart,fTarget);
        }
    }

    protected void setCurrentVal(float fVal)
    {
        textView_Val.setText(Rtx_TranslateValue.sFloat2String(fVal,1) + "%");
    }
}
