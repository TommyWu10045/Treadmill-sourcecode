package com.rtx.treadmill.RtxMainFunction.TargetTrain;

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
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxGaugeView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Weight_Layout extends TargetTrainDetailBaseLayout {

    protected Context mContext;

    protected     ButtonListener                          mButtonListener;
    private     MainActivity                            mMainActivity;

    protected RtxDoubleStringView                         doubleStringView_Val;
    protected RtxTextView                                 textView_LeftStr;
    protected RtxTextView                                 textView_RightStr;
    protected RtxTextView                                 textView_LeftVal;
    protected RtxTextView                                 textView_RightVal;
    protected RtxImageView                                imageView_InputIcon;
    protected RtxTextView                                 textView_InputText;
    protected RtxGaugeView                                gaugeView;

    private CloudDataStruct.CLOUD_TATGET_GOAL           cloud_TargetGoal;
    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE     cloud_TatgetGoalClose;


    public TargetTrainDetail_Weight_Layout(Context context, MainActivity mMainActivity, int iMode) {
        super(context , mMainActivity , iMode);

        mContext = context;
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
        init_TargetBaseView();
        init_View();
        add_TargetBaseView();
        add_TargetDaysLeftView();
        add_View();
        init_event();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    protected void setTargetTrainInfo(UiDataStruct.TargetTrainInfo data , CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal,CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE cloud_TatgetGoalClose)
    {
        data.targetTrainInfo_Wei.vExportDataToTargetClose(cloud_TatgetGoalClose);

        this.cloud_TatgetGoalClose = cloud_TatgetGoalClose;
        this.cloud_TargetGoal = cloud_TargetGoal;
        this.cloud_TargetGoal.copy(data.targetTrainInfo_Wei.cloud_TargetGol);

        {
            int iColor = 0xFFFFFFFF;
            iColor = iGetValColor(data.targetTrainInfo_Wei.fStartVal, data.targetTrainInfo_Wei.fTargetVal, data.targetTrainInfo_Wei.fCurrentVal);
            doubleStringView_Val.setPaint(Common.Font.Relay_Black,iColor,109.03f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        }

        setStartAndTargetVal(data.targetTrainInfo_Wei.fStartVal,data.targetTrainInfo_Wei.fTargetVal);
        setCurrentVal(data.targetTrainInfo_Wei.fCurrentVal);
        setLeftVal(data.targetTrainInfo_Wei.iDays_Left);
        setTargetDays(data.targetTrainInfo_Wei.iDays_Target);
        setCurrentDays(data.targetTrainInfo_Wei.iDays_Current);
    }

    protected void init_View()
    {
        vSetTitleText(R.string.weight_upper);

        if(doubleStringView_Val == null)        { doubleStringView_Val = new RtxDoubleStringView(mContext); }
        if(textView_LeftStr == null)            { textView_LeftStr = new RtxTextView(mContext); }
        if(textView_RightStr == null)           { textView_RightStr = new RtxTextView(mContext); }
        if(textView_LeftVal == null)            { textView_LeftVal = new RtxTextView(mContext); }
        if(textView_RightVal == null)           { textView_RightVal = new RtxTextView(mContext); }
        if(imageView_InputIcon == null)         { imageView_InputIcon = new RtxImageView(mContext); }
        if(textView_InputText == null)          { textView_InputText = new RtxTextView(mContext); }
        if(gaugeView == null)                   { gaugeView = new RtxGaugeView(mContext); }
    }

    protected void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_InputIcon.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
        imageView_Close.setOnClickListener(mButtonListener);
        imageView_Share.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        addViewToLayout(doubleStringView_Val,-1,467-50,600,79+100);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,109.03f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(16);

        addRtxTextViewToLayout(textView_LeftStr, 32.71f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 352 - 50, 163 - 25, 126 + 100, 24 + 50);
        addRtxTextViewToLayout(textView_RightStr, 32.71f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 813 - 50, 163 - 25, 104 + 100, 24 + 50);

        addRtxTextViewToLayout(textView_LeftVal, 66.59f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 382 - 50, 212 - 25, 147 + 100, 48 + 50);
        addRtxTextViewToLayout(textView_RightVal, 66.59f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 797 - 50, 212 - 25, 137 + 100, 48 + 50);

        addViewToLayout(gaugeView,-1,245,350,350);

        addRtxImageViewToLayout(imageView_InputIcon, R.drawable.target_input_button, 125, 251, 131, 131);
        addRtxTextViewToLayout(textView_InputText, R.string.input_weight, 26.67f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 93 - 50, 414 - 25, 194 + 100, 20 + 50);

        add_Botton_Item_1(96,165);
        add_Botton_Item_2(402,182);
        add_Botton_Item_3(694,137);
        add_Botton_Item_4(992,153);

        set_Bottom_Item_1("100", EngSetting.Weight.getUnitString(mContext),R.string.target_weight);
        set_Bottom_Item_2("130",EngSetting.Weight.getUnitString(mContext),R.string.current_weight);
        set_Bottom_Item_3("330","days",R.string.target_days);
        set_Bottom_Item_4("300","days",R.string.current_days);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackToPrePage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
    }

    private void vEnterInputPage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MODIFY_CURRENT_WEIGHT);
    }

    @Override
    protected void vClose()
    {
        if(cloud_TargetGoal != null)
        {
            cloud_TargetGoal.setDataForDelete();
        }

        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_ADD_GOAL_CLOSE);
    }

    private void vShare()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_SHARE,mMainActivity,mMainActivity.frameLayout_Base);
    }

    @Override
    protected void vDelete()
    {
        if(cloud_TargetGoal != null)
        {
            cloud_TargetGoal.setDataForDelete();
        }

        mMainActivity.mMainProcTreadmill.targetTrainProc.setDeleteFlag(true);
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vBackToPrePage(); }
            else if(v == imageView_InputIcon)       { vEnterInputPage(); }
            else if(v == imageView_Close)           { vShowCloseDialog(); }
            else if(v == imageView_Delete)          { vShowDeleteDialog(); }
            else if(v == imageView_Share)           { vShare(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setStartAndTargetVal(float fStart , float fTarget)
    {
        if(fStart >= fTarget)
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.target));
            textView_LeftStr.setTextColor(Common.Color.yellow_4);

            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.start));
            textView_RightStr.setTextColor(Common.Color.blue_1);

            textView_LeftVal.setText(EngSetting.Weight.getValString(fTarget));
            textView_LeftVal.setTextColor(Common.Color.yellow_4);
            textView_RightVal.setText(EngSetting.Weight.getValString(fStart));
            textView_RightVal.setTextColor(Common.Color.white);

            gaugeView.vSetLimit(fTarget,fStart);
        }
        else
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.start));
            textView_LeftStr.setTextColor(Common.Color.blue_1);

            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.target));
            textView_RightStr.setTextColor(Common.Color.yellow_4);

            textView_LeftVal.setText(EngSetting.Weight.getValString(fStart));
            textView_LeftVal.setTextColor(Common.Color.white);
            textView_RightVal.setText(EngSetting.Weight.getValString(fTarget));
            textView_RightVal.setTextColor(Common.Color.yellow_4);

            gaugeView.vSetLimit(fStart,fTarget);
        }

        set_Bottom_ItemVal_1(EngSetting.Weight.getValString(fTarget),EngSetting.Weight.getUnitString(mContext));
    }

    private void setCurrentVal(float fVal)
    {
        gaugeView.vSetVal(fVal);
        doubleStringView_Val.setText(EngSetting.Weight.getValString(fVal),EngSetting.Weight.getUnitString(mContext));
        set_Bottom_ItemVal_2(EngSetting.Weight.getValString(fVal),EngSetting.Weight.getUnitString(mContext));
    }

    private void setLeftVal(int iVal)
    {
        setLeftString(Rtx_TranslateValue.sInt2String(iVal));
    }

    private void setTargetDays(int iVal)
    {
        //String sDay;

        String sDay= LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
            //sDay = "days";
        }
        else
        {
            //sDay = "day";
        }

        set_Bottom_ItemVal_3(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    private void setCurrentDays(int iVal)
    {
        String sDay= LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
            //sDay = "days";
        }
        else
        {
            //sDay = "day";
        }

        set_Bottom_ItemVal_4(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }
}
