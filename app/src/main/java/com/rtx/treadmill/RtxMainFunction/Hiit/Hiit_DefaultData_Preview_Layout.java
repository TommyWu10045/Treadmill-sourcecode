package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_DefaultData_Preview_Layout extends Rtx_BaseLayout {

    private int iLevelMode = -1;

    private Context mContext;

    private MainActivity        mMainActivity;
    private HiitProc            hiitProc;
    private ButtonListener      mButtonListener;

    private ArrayList<RtxFillerTextView>    list_CircleButton;
    private RtxFillerTextView               fillerTextView_Button_Start;

    private View_Hiit_CombineChart          mView_Hiit_CombineChart;


    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private int                         iSelectIndex = -1;

    public Hiit_DefaultData_Preview_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
        super(context);
        mContext = context;
        this.hiitProc = hiitProc;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init(){
        if(mButtonListener == null){ mButtonListener = new ButtonListener(); }
        if(mView_Hiit_CombineChart == null) { mView_Hiit_CombineChart = new View_Hiit_CombineChart(mContext); }
        else{
            mView_Hiit_CombineChart.onDestroy();
            mView_Hiit_CombineChart.init();
        }
    }

    @Override
    public void display(){
        init_View();
        init_event();
        add_View();
        init_BackPrePage();
    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    private void init_View(){
        {
            init_TopBackground();
            init_Title();
            init_BackPrePage();
        }
        {
            if(list_CircleButton == null){ list_CircleButton = new ArrayList<>(); }
            if(fillerTextView_Button_Start == null){ fillerTextView_Button_Start = new RtxFillerTextView(mContext); }
        }
    }

    private void init_event(){
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Button_Start.setOnClickListener(mButtonListener);
    }

    private void add_View(){
        addRtxTextViewToLayout(fillerTextView_Button_Start, R.string.start, 20f, Common.Color.black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1097, 628, 133, 133, Common.Color.login_button_yellow);
        addListButton();
        addViewToLayout(mView_Hiit_CombineChart,55 - mView_Hiit_CombineChart.iAxisX_Offset,136,1180 + mView_Hiit_CombineChart.iAxisX_Offset,479);
    }

    private void addListButton(){
        int iW = 83;
        int iH = 83;
        int iGap = 55;
        int iPosX = 252;
        int iPosY = 654;
        int iIndex = 0;
        int iCount = 6;
        int iResID[] = { R.string.a , R.string.b , R.string.c , R.string.d , R.string.e , R.string.f};

        for(; iIndex < iCount ; iIndex++){
            RtxFillerTextView fillerTextView_Mode = new RtxFillerTextView(mContext);
            fillerTextView_Mode.setBackgroundColor(Common.Color.white);
            fillerTextView_Mode.setStrokeWidth(2);
            fillerTextView_Mode.setMode(2);
            list_CircleButton.add(fillerTextView_Mode);
            if(iIndex != 0){ iPosX=iPosX+(iGap+iW); }
            list_CircleButton.get(iIndex).setOnClickListener(mButtonListener);
            addRtxTextViewToLayout(list_CircleButton.get(iIndex), iResID[iIndex], 29.8f, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, iPosX, iPosY, iW, iH);
        }
    }

    private void setSelectStrength(UiDataStruct.HIIT_ITEM_INFO itemInfo, int iIndex , boolean bSelected){
        if(bSelected){
            vGetHiitDefaultData(itemInfo,iLevelMode,iIndex);
            mView_Hiit_CombineChart.vSetWorkoutItemInfo(itemInfo);
        }
        setButtonSelected(iIndex, bSelected);
    }

    private void setButtonSelected(int iIndex , boolean bSelected){
        if(bSelected){
            iSelectIndex=iIndex;
            list_CircleButton.get(iIndex).setMode(1);
            list_CircleButton.get(iIndex).setBackgroundColor(Common.Color.yellow);
            list_CircleButton.get(iIndex).setTextColor(Common.Color.black);
        }else{
            list_CircleButton.get(iIndex).setMode(2);
            list_CircleButton.get(iIndex).setBackgroundColor(Common.Color.blue_1);
            list_CircleButton.get(iIndex).setTextColor(Common.Color.white);
        }
        list_CircleButton.get(iIndex).setVisibility(INVISIBLE);
        list_CircleButton.get(iIndex).setVisibility(VISIBLE);
    }

    private void vClearAllSelect(){
        iSelectIndex=0;
        setSelectStrength(hiitItemInfo,0,true);
        setSelectStrength(hiitItemInfo,1,false);
        setSelectStrength(hiitItemInfo,2,false);
        setSelectStrength(hiitItemInfo,3,false);
        setSelectStrength(hiitItemInfo,4,false);
        setSelectStrength(hiitItemInfo,5,false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vSetWorkoutItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo, int iMode){
        vSetTitleFromMode(iMode);
        this.iLevelMode=iMode;
        this.hiitItemInfo=hiitItemInfo;
        vClearAllSelect();
    }


    protected void vSetTitleFromMode(int iMode){
        String sHiit=LanguageData.s_get_string(mContext,R.string.hiit);
        String sMode=null;
        String sTitleText=null;

        if(iMode == hiitProc.MODE_NORMAL){ sMode=LanguageData.s_get_string(mContext,R.string.normal_upper); }
        else if(iMode == hiitProc.MODE_HIGH){ sMode=LanguageData.s_get_string(mContext,R.string.high); }
        else if(iMode == hiitProc.MODE_ADVANCED){ sMode=LanguageData.s_get_string(mContext,R.string.advanced); }
        sTitleText=sHiit+" - "+sMode;
        vSetTitleText(sTitleText);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBackButton(){
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_MAIN);
    }

    private void vClickStartButton(){
        hiitProc.vSetNextState(HiitState.PROC_EXERCISE_PREPARE);
    }

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v){
            if(v == imageView_BackPrePage){ vClickBackButton(); }
            else if(v == fillerTextView_Button_Start){ vClickStartButton(); }
            else{
                int iIndex=0;
                int iCount=6;
                for(; iIndex < iCount; iIndex++){
                    if(v == list_CircleButton.get(iIndex)){
                        setSelectStrength(hiitItemInfo,iIndex,true);
                        iSelectIndex=iIndex;
                    }else{ setSelectStrength(hiitItemInfo,iIndex,false); }
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vGetHiitDefaultData(UiDataStruct.HIIT_ITEM_INFO itemInfo, int iMode, int iStrength){
        int iIndex = 0;
        int iSize = 0;

        String sMode[]={LanguageData.s_get_string(mContext,R.string.normal_upper),LanguageData.s_get_string(mContext,R.string.high),LanguageData.s_get_string(mContext,R.string.advanced)};
        itemInfo.sName=new String(sMode[iMode]);
        iSize=hiitProc.Hiit_Data_Time[iMode].length;
        iIndex=0;
        itemInfo.stage_WarmUp.iTime=hiitProc.Hiit_Data_Time[iMode][iIndex];
        itemInfo.stage_WarmUp.fIncline=hiitProc.Hiit_Data_Incline[iMode][iIndex];
        itemInfo.stage_WarmUp.fSpeed=hiitProc.Hiit_Data_Speed[iMode][iStrength][iIndex];
        iIndex=iSize-1;
        itemInfo.stage_CoolDown.iTime=hiitProc.Hiit_Data_Time[iMode][iIndex];
        itemInfo.stage_CoolDown.fIncline=hiitProc.Hiit_Data_Incline[iMode][iIndex];
        itemInfo.stage_CoolDown.fSpeed=hiitProc.Hiit_Data_Speed[iMode][iStrength][iIndex];
        iSize=iSize-2;
        itemInfo.list_Stage.clear();

        for (iIndex=0; iIndex < iSize; iIndex++){
            UiDataStruct.INTERVAL_STAGE_INFO stageInfo = new UiDataStruct.INTERVAL_STAGE_INFO();
            stageInfo.iTime = hiitProc.Hiit_Data_Time[iMode][iIndex+1];
            stageInfo.fIncline = hiitProc.Hiit_Data_Incline[iMode][iIndex+1];
            stageInfo.fSpeed = hiitProc.Hiit_Data_Speed[iMode][iStrength][iIndex+1];
            itemInfo.list_Stage.add(stageInfo);
        }
    }
}
