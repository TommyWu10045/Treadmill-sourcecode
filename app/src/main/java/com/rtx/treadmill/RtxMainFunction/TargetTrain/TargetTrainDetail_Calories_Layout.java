package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetail_Calories_Layout extends TargetTrainDetailBaseLayout {

    private Context mContext;

    private     ButtonListener                          mButtonListener;
    private     MainActivity                            mMainActivity;

    protected RtxImageView                                imageView_icon;
    protected RtxDoubleStringView                         doubleStringView_CurrentVal;
    protected RtxTextView                                 textView_CurrentText;
    protected RtxPercentCircleView                        mRtxPercentCircleView;
    protected RtxTextView                                 textView_PercentVal;
    private CloudDataStruct.CLOUD_TATGET_GOAL           cloud_TargetGoal;
    private CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE     cloud_TatgetGoalClose;

    public TargetTrainDetail_Calories_Layout(Context context, MainActivity mMainActivity, int iMode) {
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
        data.targetTrainInfo_Cal.vExportDataToTargetClose(cloud_TatgetGoalClose);

        this.cloud_TatgetGoalClose = cloud_TatgetGoalClose;
        this.cloud_TargetGoal = cloud_TargetGoal;
        this.cloud_TargetGoal.copy(data.targetTrainInfo_Cal.cloud_TargetGol);

        setCurrentVal(data.targetTrainInfo_Cal.fCurrentVal);
        setTargetVal(data.targetTrainInfo_Cal.fTargetVal);
        setLeftVal(data.targetTrainInfo_Cal.iDays_Left);

        {
            int iPercent = 0;

            iPercent = (int)(( data.targetTrainInfo_Cal.fCurrentVal * 100f ) / data.targetTrainInfo_Cal.fTargetVal);

            setPercent(iPercent);
        }

        setTargetDays(data.targetTrainInfo_Cal.iDays_Target);
        setCurrentDays(data.targetTrainInfo_Cal.iDays_Current);
    }

    protected void init_View()
    {
        vSetTitleText(R.string.distance);

        if(imageView_icon == null)                  { imageView_icon = new RtxImageView(mContext); }
        if(doubleStringView_CurrentVal == null)     { doubleStringView_CurrentVal = new RtxDoubleStringView(mContext); }
        if(textView_CurrentText == null)            { textView_CurrentText = new RtxTextView(mContext); }
        if(mRtxPercentCircleView == null)           { mRtxPercentCircleView = new RtxPercentCircleView(mContext); }
        if(textView_PercentVal == null)             { textView_PercentVal = new RtxTextView(mContext); }
    }

    protected void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Close.setOnClickListener(mButtonListener);
        imageView_Delete.setOnClickListener(mButtonListener);
        imageView_Share.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        addRtxImageViewToLayout(imageView_icon, R.drawable.target_cal_icon, 164, 253, 51, 64);

        addRtxTextViewToLayout(textView_CurrentText, R.string.calories , 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 141-100, 414-25, 98+200, 15+50);
        addRtxTextViewToLayout(textView_PercentVal , 109.3f, Common.Color.yellow_4, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 167, 371, 371);

        addViewToLayout(doubleStringView_CurrentVal,107-100,343-50,165+200,48+100);
        doubleStringView_CurrentVal.setPaint(Common.Font.Relay_Black,Common.Color.yellow_4,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);

        addViewToLayout(mRtxPercentCircleView, -1, 167, 371, 371);

        add_Botton_Item_1(23,262);
        add_Botton_Item_2(335,262);
        add_Botton_Item_3(647,262);
        add_Botton_Item_4(960,262);

        set_Bottom_Item_1("2000","kcal",R.string.target_calories);
        set_Bottom_Item_2("1999","kcal",R.string.current_calories);
        set_Bottom_Item_3("330","days",R.string.target_days);
        set_Bottom_Item_4("300","days",R.string.current_days);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackToPrePage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
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
            if(v == imageView_BackPrePage)  { vBackToPrePage(); }
            else if(v == imageView_Close)   { vShowCloseDialog(); }
            else if(v == imageView_Delete)  { vShowDeleteDialog(); }
            else if(v == imageView_Share)   { vShare(); }

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        mRtxPercentCircleView.setPercentValue(iPercent);
    }

    public void setCurrentVal(float fVal)
    {
        doubleStringView_CurrentVal.setText(Rtx_TranslateValue.sFloat2String(fVal,0),"kcal");
        set_Bottom_ItemVal_2(Rtx_TranslateValue.sFloat2String(fVal,0),"kcal");
    }

    public void setTargetVal(float fVal)
    {
        set_Bottom_ItemVal_1(Rtx_TranslateValue.sFloat2String(fVal,0),"kcal");
    }

    public void setLeftVal(int iVal)
    {
        setLeftString(Rtx_TranslateValue.sInt2String(iVal));
    }

    public void setTargetDays(int iVal)
    {
        //String sDay;
        String sDay= LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
         //   sDay = "days";
        }
        else
        {
         //   sDay = "day";
        }

        set_Bottom_ItemVal_3(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

    public void setCurrentDays(int iVal)
    {
        //String sDay;
        String sDay= LanguageData.s_get_string(mContext,R.string.day);

        if(iVal > 1)
        {
         //   sDay = "days";
        }
        else
        {
         //   sDay = "day";
        }

        set_Bottom_ItemVal_4(Rtx_TranslateValue.sInt2String(iVal),sDay);
    }

}
