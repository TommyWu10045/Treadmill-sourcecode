package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxPercentBlockView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Distance_Layout extends TargetTrainDetailBaseLayout {

    protected Context mContext;

    private ButtonListener                              mButtonListener;
    private MainActivity                                mMainActivity;

    protected RtxImageView                                imageView_TypeIcon;
    protected RtxTextView                                 textView_PercentVal;
    protected RtxDoubleStringView                         doubleStringView_CurrentVal;
    protected RtxTextView                                 textView_CurrentText;
    protected RtxDoubleStringView                         doubleStringView_TargetVal;
    protected RtxPercentBlockView                         mRtxPercentBlockView;
    protected CloudDataStruct.CLOUD_TATGET_GOAL           cloud_TargetGoal;
    protected CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE     cloud_TatgetGoalClose;

    public TargetTrainDetail_Distance_Layout(Context context, MainActivity mMainActivity, int iMode) {
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
        data.targetTrainInfo_Dis.vExportDataToTargetClose(cloud_TatgetGoalClose);

        this.cloud_TatgetGoalClose = cloud_TatgetGoalClose;
        this.cloud_TargetGoal = cloud_TargetGoal;
        this.cloud_TargetGoal.copy(data.targetTrainInfo_Dis.cloud_TargetGol);

        setCurrentVal(data.targetTrainInfo_Dis.fCurrentVal);
        setTargetVal(data.targetTrainInfo_Dis.fTargetVal);
        setLeftVal(data.targetTrainInfo_Dis.iDays_Left);
        setExerciseTyoe(data.targetTrainInfo_Dis.sExerciseType);

        {
            int iPercent = 0;

            //20181223 修正變更轉換單位所產生的誤差
            iPercent = (int)((Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(data.targetTrainInfo_Dis.fCurrentVal), 1) * 100f)/Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(data.targetTrainInfo_Dis.fTargetVal), 1));

            setPercent(iPercent);
        }

        setTarrgetDays(data.targetTrainInfo_Dis.iDays_Target);
        setCurrentDays(data.targetTrainInfo_Dis.iDays_Current);
    }

    protected void init_View()
    {
        vSetTitleText(R.string.distance);

        if(imageView_TypeIcon == null)          { imageView_TypeIcon = new RtxImageView(mContext); }
        if(textView_PercentVal == null)         { textView_PercentVal = new RtxTextView(mContext); }
        if(doubleStringView_CurrentVal == null) { doubleStringView_CurrentVal = new RtxDoubleStringView(mContext); }
        if(textView_CurrentText == null)        { textView_CurrentText = new RtxTextView(mContext); }
        if(doubleStringView_TargetVal == null)  { doubleStringView_TargetVal = new RtxDoubleStringView(mContext); }
        if(mRtxPercentBlockView == null)        { mRtxPercentBlockView = new RtxPercentBlockView(mContext); }
    }

    protected void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
        imageView_Close.setOnClickListener(mButtonListener);
        imageView_Share.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        addViewToLayout(mRtxPercentBlockView, 371, 800-471, 537, 103);
        addRtxImageViewToLayout(imageView_TypeIcon, R.drawable.target_type_a, 620, 800-687, 41, 41);

        mRtxPercentBlockView.setPercentValue(0);

        addRtxTextViewToLayout(textView_PercentVal, 109.03f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 800-609-20, 1280, 78 + 40);
        addRtxTextViewToLayout(textView_CurrentText, R.string.current, 20.0f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 145 - 25, 800-383-20, 95 + 100, 15 + 40);

        textView_PercentVal.setText("0%");

        addViewToLayout(doubleStringView_CurrentVal,133-100,800-458-50,125+200,49+100);
        addViewToLayout(doubleStringView_TargetVal,766-100,800-337-50,141+200,49+100);

        doubleStringView_CurrentVal.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_TargetVal.setPaint(Common.Font.Relay_Black,Common.Color.white,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);

        doubleStringView_CurrentVal.setText("0", EngSetting.Distance.getUnitString(mContext));
        doubleStringView_TargetVal.setText("0",EngSetting.Distance.getUnitString(mContext));

        add_Botton_Item_1(57,183);
        add_Botton_Item_2(381,199);
        add_Botton_Item_3(700,137);
        add_Botton_Item_4(1007,154);

        set_Bottom_Item_1("0.0",EngSetting.Distance.getUnitString(mContext),R.string.target_distance);
        set_Bottom_Item_2("0.0",EngSetting.Distance.getUnitString(mContext),R.string.current_distance);
        set_Bottom_Item_3("-","days",R.string.target_days);
        set_Bottom_Item_4("-","days",R.string.current_days);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackToPrePage()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
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
            if(v == imageView_BackPrePage)  { vBackToPrePage(); }
            else if(v == imageView_Close)   { vShowCloseDialog(); }
            else if(v == imageView_Delete)  { vShowDeleteDialog(); }
            else if(v == imageView_Share)   { vShare(); }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPercent(int iPercent)
    {
        if(iPercent < 0)
        {
            iPercent = 0;
        }
        else
        if(iPercent > 100)
        {
            iPercent = 100;
        }

        textView_PercentVal.setText(Rtx_TranslateValue.sInt2String(iPercent) + "%");
        mRtxPercentBlockView.setPercentValue(iPercent);
    }

    public void setCurrentVal(float fVal)
    {
        doubleStringView_CurrentVal.setText(EngSetting.Distance.getValString(fVal),EngSetting.Distance.getUnitString(mContext));
        set_Bottom_ItemVal_2(EngSetting.Distance.getValString(fVal),EngSetting.Distance.getUnitString(mContext));
    }

    public void setTargetVal(float fVal)
    {
        //20190108 变更規格 距離顯示方式由整數變更為小數點一位
        doubleStringView_TargetVal.setText(EngSetting.Distance.getValString(fVal,1),EngSetting.Distance.getUnitString(mContext));
        set_Bottom_ItemVal_1(EngSetting.Distance.getValString(fVal,1),EngSetting.Distance.getUnitString(mContext));
    }

    public void setLeftVal(int iVal)
    {
        setLeftString(Rtx_TranslateValue.sInt2String(iVal));
    }

    public void setTarrgetDays(int iVal)
    {
        String sDay= LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
          //  sDay = "days";
        }
        else
        {
          //  sDay = "day";
        }

        set_Bottom_ItemVal_3(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    public void setCurrentDays(int iVal)
    {
        String sDay=LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
          //  sDay = "days";
        }
        else
        {
          //  sDay = "day";
        }

        set_Bottom_ItemVal_4(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    public void setExerciseTyoe(String sType)
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
}
