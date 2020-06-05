package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TargetTrainSimple_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainSimple_Calories_Layout extends Rtx_TargetTrainSimple_BaseLayout {

    protected Context mContext;

    protected RtxPercentCircleView    mRtxPercentCircleView;
    protected RtxTextView             textView_PercentVal;

    public TargetTrainSimple_Calories_Layout(Context context, int iMode) {
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
            int iPercent = 0;

            iPercent = (int)((data.targetTrainInfo_Cal.fCurrentVal * 100f) / data.targetTrainInfo_Cal.fTargetVal);
            setPercentVal(iPercent);
        }

        {
            setLeft(data.targetTrainInfo_Cal.iDays_Left,true);
        }
    }

    public void onDestroy()
    {
//        removeAllViews();
//        System.gc();
    }

    protected void init_View()
    {
        setInfoTitle(R.string.calories);

        if(mRtxPercentCircleView == null)       { mRtxPercentCircleView = new RtxPercentCircleView(mContext); }
        if(textView_PercentVal == null)         { textView_PercentVal = new RtxTextView(mContext); }
    }

    protected void add_View()
    {
        {
            addViewToLayout(mRtxPercentCircleView, -1, 97, 227, 227);
        }

        {
            addRtxTextViewToLayout(textView_PercentVal, 66.67f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 97, 227, 227);
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

        mRtxPercentCircleView.setPercentValue(iVal);
        textView_PercentVal.setText(Rtx_TranslateValue.sInt2String(iVal) + "%");
    }
}
