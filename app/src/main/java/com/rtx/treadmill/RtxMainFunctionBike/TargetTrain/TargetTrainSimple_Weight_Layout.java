package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxGaugeView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Weight_Layout extends Rtx_TargetTrainSimple_BaseLayout {

    protected Context mContext;

    protected RtxDoubleStringView doubleStringView_Val;
    protected RtxTextView textView_LeftStr;
    protected RtxTextView textView_RightStr;
    protected RtxTextView textView_LeftVal;
    protected RtxTextView textView_RightVal;

    protected RtxGaugeView gaugeView;

    public TargetTrainSimple_Weight_Layout(Context context, int iMode) {
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
//        Log.e("Jerry","data.targetTrainInfo_Wei.fStartVal = " + data.targetTrainInfo_Wei.fStartVal);
//        Log.e("Jerry","data.targetTrainInfo_Wei.fCurrentVal = " + data.targetTrainInfo_Wei.fCurrentVal);
//        Log.e("Jerry","data.targetTrainInfo_Wei.fTargetVal = " + data.targetTrainInfo_Wei.fTargetVal);

        {
            int iColor = 0xFFFFFFFF;
            iColor = iGetValColor(data.targetTrainInfo_Wei.fStartVal, data.targetTrainInfo_Wei.fTargetVal, data.targetTrainInfo_Wei.fCurrentVal);
            doubleStringView_Val.setPaint(Common.Font.Relay_Black,iColor,66.67f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        }

        {
            setStartAndTargetVal(data.targetTrainInfo_Wei.fStartVal, data.targetTrainInfo_Wei.fTargetVal);
            setCurrentVal(data.targetTrainInfo_Wei.fCurrentVal);
        }

        {
            setLeft(data.targetTrainInfo_Wei.iDays_Left,true);
        }
    }

    public void onDestroy()
    {
//        removeAllViews();
//        System.gc();
    }

    protected void init_View()
    {
        setInfoTitle(R.string.weight_upper);

        if(doubleStringView_Val == null)    { doubleStringView_Val = new RtxDoubleStringView(mContext); }
        if(textView_LeftStr == null)        { textView_LeftStr = new RtxTextView(mContext); }
        if(textView_RightStr == null)       { textView_RightStr = new RtxTextView(mContext); }
        if(textView_LeftVal == null)        { textView_LeftVal = new RtxTextView(mContext); }
        if(textView_RightVal == null)       { textView_RightVal = new RtxTextView(mContext); }

        if(gaugeView == null)               { gaugeView = new RtxGaugeView(mContext); }

        gaugeView.setClickable(false);
    }

    protected void add_View()
    {
        addViewToLayout(doubleStringView_Val,-1,71,426,88);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,66.67f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(16);

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

            textView_LeftVal.setText(EngSetting.Weight.getValString(fTarget));
            textView_RightVal.setText(EngSetting.Weight.getValString(fStart));

            gaugeView.vSetLimit(fTarget,fStart);
        }
        else
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.start));
            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.target));

            textView_LeftVal.setText(EngSetting.Weight.getValString(fStart));
            textView_RightVal.setText(EngSetting.Weight.getValString(fTarget));

            gaugeView.vSetLimit(fStart,fTarget);
        }
    }

    protected void setCurrentVal(float fVal)
    {
        doubleStringView_Val.setText(EngSetting.Weight.getValString(fVal),EngSetting.Weight.getUnitString(mContext));
        gaugeView.vSetVal(fVal);
    }
}
