package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
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

public class TargetTrainDetail_BodyFat_Layout extends TargetTrainDetailBaseLayout {

    private Context mContext;

    protected     ButtonListener                          mButtonListener;
    private     MainActivity                            mMainActivity;

    protected RtxDoubleStringView                         doubleStringView_Val;
    protected RtxTextView                                 textView_LeftStr;
    protected RtxTextView                                 textView_RightStr;
    protected RtxTextView                                 textView_LeftVal;
    protected RtxTextView                                 textView_RightVal;
    protected RtxImageView                                imageView_InputIcon;
    protected RtxTextView                                 textView_InputText;

    private CloudDataStruct.CLOUD_TATGET_GOAL           cloud_TargetGoal;
    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE     cloud_TatgetGoalClose;

    protected RtxGaugeView        gaugeView;

    public TargetTrainDetail_BodyFat_Layout(Context context, MainActivity mMainActivity, int iMode) {
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
        data.targetTrainInfo_BdyFat.vExportDataToTargetClose(cloud_TatgetGoalClose);

        this.cloud_TatgetGoalClose = cloud_TatgetGoalClose;
        this.cloud_TargetGoal = cloud_TargetGoal;
        this.cloud_TargetGoal.copy(data.targetTrainInfo_BdyFat.cloud_TargetGol);

        {
            int iColor = 0xFFFFFFFF;
            iColor = iGetValColor(data.targetTrainInfo_BdyFat.fStartVal, data.targetTrainInfo_BdyFat.fTargetVal, data.targetTrainInfo_BdyFat.fCurrentVal);
            doubleStringView_Val.setPaint(Common.Font.Relay_Black,iColor,109.03f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        }

        setStartAndTargetVal(data.targetTrainInfo_BdyFat.fStartVal,data.targetTrainInfo_BdyFat.fTargetVal);
        setCurrentVal(data.targetTrainInfo_BdyFat.fCurrentVal);
        setLeftVal(data.targetTrainInfo_BdyFat.iDays_Left);
        setTargetDays(data.targetTrainInfo_BdyFat.iDays_Target);
        setCurrentDays(data.targetTrainInfo_BdyFat.iDays_Current);
    }

    protected void init_View()
    {
        vSetTitleText(R.string.bodyfat_percent);

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
        addViewToLayout(doubleStringView_Val,-1,451-50,600,115+100);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,109.03f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(16);

        addRtxTextViewToLayout(textView_LeftStr, 32.71f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 352 - 50, 163 - 25, 126 + 100, 24 + 50);
        addRtxTextViewToLayout(textView_RightStr, 32.71f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 813 - 50, 163 - 25, 104 + 100, 24 + 50);

        addRtxTextViewToLayout(textView_LeftVal, 66.59f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 313 - 50, 212 - 25, 204 + 100, 48 + 50);
        addRtxTextViewToLayout(textView_RightVal, 66.59f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 761 - 50, 212 - 25, 207 + 100, 48 + 50);

        addViewToLayout(gaugeView,-1,245,350,350);

        addRtxImageViewToLayout(imageView_InputIcon, R.drawable.target_input_button, 125, 251, 131, 131);
        addRtxTextViewToLayout(textView_InputText, R.string.input_body_fat, 26.67f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 93 - 50, 414 - 25, 194 + 100, 20 + 50);

        add_Botton_Item_1(100,120);//By Alan
        add_Botton_Item_2(450,120);//By Alan
        add_Botton_Item_3(880,137);//By Alan
        add_Botton_Item_4(110,128);//By Alan

        set_Bottom_Item_1("12.8%","",R.string.target_body_fat);
        set_Bottom_Item_2("14.7%","",R.string.current_body_fat);
        set_Bottom_Item_3("330","",R.string.target_days);
        set_Bottom_Item_4("300","",R.string.current_days);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackToPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
    }

    private void vEnterInputPage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MODIFY_CURRENT_BODYFAT);
    }

    @Override
    protected void vClose()
    {
        if(cloud_TargetGoal != null)
        {
            cloud_TargetGoal.setDataForDelete();
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_ADD_GOAL_CLOSE);
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

        mMainActivity.mMainProcBike.targetTrainProc.setDeleteFlag(true);
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_TARGET_GOAL);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vBackToPrePage();  }
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

            textView_LeftVal.setText(Rtx_TranslateValue.sFloat2String(fTarget,1) + "%");
            textView_LeftVal.setTextColor(Common.Color.yellow_4);
            textView_RightVal.setText(Rtx_TranslateValue.sFloat2String(fStart,1) + "%");
            textView_RightVal.setTextColor(Common.Color.white);

            gaugeView.vSetLimit(fTarget,fStart);
        }
        else
        {
            textView_LeftStr.setText(LanguageData.s_get_string(mContext,R.string.start));
            textView_LeftStr.setTextColor(Common.Color.blue_1);

            textView_RightStr.setText(LanguageData.s_get_string(mContext,R.string.target));
            textView_RightStr.setTextColor(Common.Color.yellow_4);

            textView_LeftVal.setText(Rtx_TranslateValue.sFloat2String(fStart,1) + "%");
            textView_LeftVal.setTextColor(Common.Color.white);
            textView_RightVal.setText(Rtx_TranslateValue.sFloat2String(fTarget,1) + "%");
            textView_RightVal.setTextColor(Common.Color.yellow_4);

            gaugeView.vSetLimit(fStart,fTarget);
        }

        set_Bottom_ItemVal_1(Rtx_TranslateValue.sFloat2String(fTarget,1) + "%","");
    }

    private void setCurrentVal(float fVal)
    {
        gaugeView.vSetVal(fVal);
        doubleStringView_Val.setText(Rtx_TranslateValue.sFloat2String(fVal,1) + "%","");
        set_Bottom_ItemVal_2(Rtx_TranslateValue.sFloat2String(fVal,1) + "%","");
    }

    private void setLeftVal(int iVal)
    {
        setLeftString(Rtx_TranslateValue.sInt2String(iVal));
    }

    private void setTargetDays(int iVal)
    {
        String sDay;

        if(iVal > 1)
        {
            sDay = "days";
        }
        else
        {
            sDay = "day";
        }

        set_Bottom_ItemVal_3(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    private void setCurrentDays(int iVal)
    {
        String sDay;

        if(iVal > 1)
        {
            sDay = "days";
        }
        else
        {
            sDay = "day";
        }

        set_Bottom_ItemVal_4(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }
}
