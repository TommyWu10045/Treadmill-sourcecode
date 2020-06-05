package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.Exercisefunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;
import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Exercise_Layout extends Rtx_BaseLayout {
    private final int TIME_BAR_WIDTH = 980;
    private final int TIME_BAR_HEIGHT = 49;
    private final int TIME_BAR_POS_X = 150;
    private final int TIME_BAR_POS_Y = 717;
    private final int EXERCISE_STATUS_WARM_UP = 0;
    private final int EXERCISE_STATUS_SPRINT = 1;
    private final int EXERCISE_STATUS_RECOVERY = 2;
    private final int EXERCISE_STATUS_COOL_DOWN = 3;
    private Context mContext;
    private ButtonListener mButtonListener;
    private MainActivity mMainActivity;
    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private RtxTextView textView_InclineText;
    private RtxTextView textView_SpeedText;
    private RtxTextView textView_TimeText;
    private RtxTextView textView_InclineVal;
    private RtxTextView textView_SpeedVal;
    private RtxTextView textView_TimeVal;
    private RtxTextView textView_SecText;
    private RtxTextView textView_DistanceUnitText;
    private RtxTextView textView_SkipText;
    private RtxImageView imageView_Skip;
    private RtxTextView textView_Status;
    private RtxPercentCircleView mRtxPercentCircleView;
    private View_HIIT_TimeBar mView_HIIT_TimeBar;
    private RtxImageView imageView_TimeBarPad;
    private RtxTextView textView_TimeBarText;
    private HiitProc hiitProc;
    private int iCurrentProgressSec=0;

    private ExerciseRunState mState;
    private ExerciseRunState mNextState=ExerciseRunState.PROC_NULL ;

    public Hiit_Exercise_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc){
        super(context);
        mContext=context;
        this.hiitProc=hiitProc;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
        this.mMainActivity=mMainActivity;
    }

    @Override
    public void init(){
        if(mButtonListener == null){ mButtonListener=new ButtonListener(); }
    }

    @Override
    public void display(){
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    private void init_View(){
        { init_Title(); }
        {
            if(textView_InclineText == null){ textView_InclineText = new RtxTextView(mContext); }
            if(textView_SpeedText == null){ textView_SpeedText = new RtxTextView(mContext); }
            if(textView_TimeText == null){ textView_TimeText = new RtxTextView(mContext); }
            if(textView_InclineVal == null){ textView_InclineVal = new RtxTextView(mContext); }
            if(textView_SpeedVal == null){ textView_SpeedVal = new RtxTextView(mContext); }
            if(textView_TimeVal == null){ textView_TimeVal = new RtxTextView(mContext); }
            if(textView_SecText == null){ textView_SecText = new RtxTextView(mContext); }
            if(textView_DistanceUnitText == null){ textView_DistanceUnitText = new RtxTextView(mContext); }
            if(textView_SkipText == null){ textView_SkipText = new RtxTextView(mContext); }
            if(textView_Status == null){ textView_Status = new RtxTextView(mContext); }
            if(imageView_Skip == null){ imageView_Skip = new RtxImageView(mContext); }
            if(mRtxPercentCircleView == null){ mRtxPercentCircleView = new RtxPercentCircleView(mContext); }
            if(mView_HIIT_TimeBar == null){ mView_HIIT_TimeBar = new View_HIIT_TimeBar(mContext,TIME_BAR_WIDTH,TIME_BAR_HEIGHT); }
            if(imageView_TimeBarPad == null){ imageView_TimeBarPad = new RtxImageView(mContext); }
            if(textView_TimeBarText == null){ textView_TimeBarText = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_Skip.setOnClickListener(mButtonListener);
    }

    private void add_View(){
        addViewToLayout(mRtxPercentCircleView, 467, 210, 347, 347); //By Alan
        addRtxTextViewToLayout(textView_InclineText, R.string.incline, 50f, Common.Color.blue_1, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, 115 - 100, 85 - 25, 196 + 200, 37 + 50);
        addRtxTextViewToLayout(textView_TimeText  , R.string.time   , 50f, Common.Color.blue_1, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, 580 - 100, 85 - 25, 119 + 200, 37 + 50);
        addRtxTextViewToLayout(textView_SpeedText   , R.string.speed  , 50f, Common.Color.blue_1, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, 977 - 100, 85 - 25, 170 + 200, 37 + 50);//By Alan
        addRtxTextViewToLayout(textView_InclineVal, -1, 133.33f, Common.Color.green_8, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 109 - 100, 327 - 25, 209 + 200, 95 + 50);//By Alan
        addRtxTextViewToLayout(textView_TimeVal  , -1, 133.33f, Common.Color.green_8, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 540 - 100, 329 - 25, 199 + 200, 94 + 50);//By Alan
        addRtxTextViewToLayout(textView_SpeedVal , -1, 133.33f, Common.Color.green_8, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 977 - 100, 327 - 25, 180 + 200, 95 + 50);//By Alan
        addRtxTextViewToLayout(textView_SecText   , R.string.sec, 36.68f, Common.Color.blue_1, Common.Font.ProximaNova_Bold, Typeface.NORMAL, Gravity.CENTER, 611 - 100, 454 - 25, 58 + 200, 20 + 50);//By Alan
        addRtxTextViewToLayout(textView_DistanceUnitText, R.string.kph_lower, 31.05f, Common.Color.blue_1, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, 1041 - 100, 449 - 25, 52 + 200, 30 + 50);//By Alan
        addRtxTextViewToLayout(textView_SkipText, R.string.skip, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 927 - 120, 605 - 25, 46 + 200, 14 + 50);//By Alan
        addRtxTextViewToLayout(textView_Status, -1, 50.00f, Common.Color.purple_2, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 420, 580, 430, 101);//By Alan
        addRtxImagePaddingViewToLayout(imageView_Skip, R.drawable.hiit_skip_icon, 997, 543, 139, 139, 50);
        addViewToLayout(mView_HIIT_TimeBar,TIME_BAR_POS_X,TIME_BAR_POS_Y,TIME_BAR_WIDTH,TIME_BAR_HEIGHT);
        addRtxImagePaddingViewToLayout(imageView_TimeBarPad, R.drawable.hiit_exercise_run_bar_pad, TIME_BAR_POS_X, TIME_BAR_POS_Y-1, 14, 50, 0);
        addRtxTextViewToLayout(textView_TimeBarText, -1, 21.98f, Common.Color.purple_2, Common.Font.Lato_Black, Typeface.NORMAL, Gravity.CENTER,TIME_BAR_POS_X,TIME_BAR_POS_Y,TIME_BAR_WIDTH,TIME_BAR_HEIGHT);
        textView_DistanceUnitText.setText(EngSetting.Distance.getSpeedUnitLowerString(mContext));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetHiitItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo){
        this.hiitItemInfo=hiitItemInfo;
        mView_HIIT_TimeBar.setItemInfo(hiitItemInfo);
        {
            String sTotalTime;
            int iTotalSec=hiitItemInfo.iGetTotalSec();
            if(iTotalSec >= 3600){ sTotalTime=Rtx_Calendar.sSec2Str(iTotalSec,"HH:mm:ss"); }
            else if(iTotalSec >= 60){ sTotalTime=Rtx_Calendar.sSec2Str(iTotalSec,"mm:ss"); }
            else{ sTotalTime=Rtx_Calendar.sSec2Str(iTotalSec,"ss"); }
            textView_TimeBarText.setText(sTotalTime);
        }
    }

    protected void Refresh(){
        boolean bEnd;
        bEnd=bSetBarPadPos(ExerciseData.i_get_list_count(), ExerciseData.mCaculate_Data.icurrent_time);
        if(!bEnd){
            textView_TimeVal.setText(Rtx_TranslateValue.sInt2String(ExerciseData.mCaculate_Data.icurrent_time));
            //20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
            if(Exercisefunc.fGetCurrentSpeed() > EngSetting.f_Get_Max_Speed() && (Exercisefunc.fGetCurrentSpeed() - 1) < EngSetting.f_Get_Max_Speed()){
                textView_SpeedVal.setText(Rtx_TranslateValue.sFloat2String(EngSetting.Distance.getVal(EngSetting.f_Get_Max_Speed()), 1));
            }
            else if(Exercisefunc.fGetCurrentSpeed() < EngSetting.f_Get_Min_Speed() && (Exercisefunc.fGetCurrentSpeed() + 1) > EngSetting.f_Get_Min_Speed()){
                textView_SpeedVal.setText(Rtx_TranslateValue.sFloat2String(EngSetting.Distance.getVal(EngSetting.f_Get_Min_Speed()), 1));
            }
            else{
                textView_SpeedVal.setText(Rtx_TranslateValue.sFloat2String(EngSetting.Distance.getVal(Exercisefunc.fGetCurrentSpeed()), 1));
            }
            textView_InclineVal.setText(Rtx_TranslateValue.sFloat2String(Exercisefunc.fGetCurrentIncline(),1));
            vSetSecPercent();
            vSetStatus(ExerciseData.i_get_list_count());
            getCurrentProgressTime(ExerciseData.i_get_list_count());
            if(ExerciseData.i_get_finish() == 0x05){ //Click Cool Down
                vProc_Exit(ExerciseState.PROC_EXERCISE_COOLDOWN); //modify by Steven 2020/04/10
                /*
                if(hiitItemInfo.stage_CoolDown.iTime > 0){
                    if(!bCheckInCoolDown()){
                        vJumpToCoolDownStage();
                        ExerciseData.v_set_finish(-1);
                    }
                }
                */
            }
        }
    }
//// Add by Steven 2020/04/10
    private void vProc_Exit(ExerciseState mstate) {
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(mstate);

        mState = ExerciseRunState.PROC_INIT ;
        mNextState = ExerciseRunState.PROC_NULL;
    }

    private int getCurrentProgressTime(int iListCount){
        int iSec;
        iSec=hiitItemInfo.iGetCurrentProgressSec(iListCount);
        return iSec;
    }

    protected boolean bSetBarPadPos(int iListCount, int iProgessSec){
        boolean bEnd=false;
        float fRate;
        int iPosX;
        int iCurrentTime=hiitItemInfo.iGetCurrentProgressSec(iListCount)+iProgessSec;
        //int iCurrentTime=mCaculate_Data.itotal_time;

        if(mCaculate_Data.itotal_time > 0 && hiitItemInfo.iGetTotalSec() > 0){
            fRate=(float)iCurrentTime/(float)hiitItemInfo.iGetTotalSec();
            if(fRate > 1){
                fRate=1;
                bEnd=true;
            }
            iPosX=TIME_BAR_POS_X+((int)(fRate*(float)TIME_BAR_WIDTH));
            imageView_TimeBarPad.layout(iPosX,TIME_BAR_POS_Y-1, iPosX+14, TIME_BAR_POS_Y+50);
        }
        return bEnd;
    }

    protected void vSetSecPercent(){
        int iCurrentSec=ExerciseData.mCaculate_Data.icurrent_time;
        int iCurrentTargetSec=Exercisefunc.iGetCurrentTargetSec();
        mRtxPercentCircleView.setPercentValue(iCurrentSec,iCurrentTargetSec);
    }

    protected void vSetStatus(int iListCount){
        String sStatus;
        int iColor;
        int iExerciseStatus;
//        Log.e("Jerry","iListCount = " + iListCount);
//        Log.e("Jerry","hiitItemInfo.iGetTotalSize() = " + hiitItemInfo.iGetTotalSize());

        if(iListCount == 0){
           if(hiitItemInfo.stage_WarmUp.iTime > 0){ iExerciseStatus=EXERCISE_STATUS_WARM_UP; }
           else{ iExerciseStatus=EXERCISE_STATUS_SPRINT; }
        }else if((iListCount + 1) >= hiitItemInfo.iGetTotalSize() && hiitItemInfo.stage_CoolDown.iTime > 0){
           iExerciseStatus=EXERCISE_STATUS_COOL_DOWN;
        }else{
           if((iListCount % 2) == 0){
               if(hiitItemInfo.stage_WarmUp.iTime > 0){ iExerciseStatus = EXERCISE_STATUS_RECOVERY; }
               else{ iExerciseStatus=EXERCISE_STATUS_SPRINT; }
           }else{
               if(hiitItemInfo.stage_WarmUp.iTime > 0){ iExerciseStatus = EXERCISE_STATUS_SPRINT; }
               else{ iExerciseStatus=EXERCISE_STATUS_RECOVERY; }
           }
        }

        switch (iExerciseStatus){
            case EXERCISE_STATUS_WARM_UP:
               sStatus=LanguageData.s_get_string(mContext,R.string.warm_up);
               iColor=Common.Color.hiit_exercise_warm_up;
                break;
            case EXERCISE_STATUS_RECOVERY:
               sStatus=LanguageData.s_get_string(mContext,R.string.recovery);
               iColor=Common.Color.hiit_exercise_recovery;
               break;
            case EXERCISE_STATUS_COOL_DOWN:
               sStatus=LanguageData.s_get_string(mContext,R.string.cool_donw);
               iColor=Common.Color.hiit_exercise_cool_down;
               break;
            case EXERCISE_STATUS_SPRINT:
            default:
               sStatus=LanguageData.s_get_string(mContext,R.string.sprint);
               iColor=Common.Color.hiit_exercise_sprint;
               break;
        }

        if(iExerciseStatus == EXERCISE_STATUS_COOL_DOWN){
           imageView_Skip.setVisibility(INVISIBLE);
           textView_SkipText.setVisibility(INVISIBLE);
        }else{
           imageView_Skip.setVisibility(VISIBLE);
           textView_SkipText.setVisibility(VISIBLE);
        }
        mRtxPercentCircleView.setValueBarColor(iColor);
        textView_InclineVal.setTextColor(iColor);
        textView_TimeVal.setTextColor(iColor);
        textView_SpeedVal.setTextColor(iColor);
        textView_Status.setBackgroundColor(iColor);
        textView_Status.setText(sStatus);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vButtonClick_Skip(){
        if(!hiitItemInfo.bCurrentIsCoolDown(ExerciseData.i_get_list_count())){
            //skip button delay time
            if ( mCaculate_Data.icurrent_time >= 1 ){ Exercisefunc.bForceToNextListCount(); }
        }
    }

    private boolean bCheckInCoolDown(){
        boolean bResult = false;

        int iCoolDownIndex=hiitItemInfo.iGetCoolDownIndex();
        if(iCoolDownIndex > 0){
            if(ExerciseData.list_real_setting.size() > iCoolDownIndex){
                if(ExerciseData.ilist_count == iCoolDownIndex){ bResult=true; }
            }
        }
        return bResult;
    }

    private void vJumpToCoolDownStage(){
        int iCoolDownIndex=hiitItemInfo.iGetCoolDownIndex();

        if(iCoolDownIndex > 0){
            if(ExerciseData.list_real_setting.size() > iCoolDownIndex){
                //set cooldown speed
                ExerciseData.ilist_count = iCoolDownIndex-1;
                Exercisefunc.v_check_motor_befor_cheange(0);
                ExerciseData.ilist_count = iCoolDownIndex;
                Exercisefunc.v_Force_Reset_Current_time();
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v)
        {
            if(v == imageView_Skip){ vButtonClick_Skip(); }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
