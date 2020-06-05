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
import com.rtx.treadmill.RtxMainFunction.MyWorkout.MyWorkoutState;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Draw;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Personal_DataList_Layout extends Rtx_BaseLayout {


    private int iMode = -1;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxFillerTextView       fillerTextView_Preview;

    private RtxImageView            imageView_NamePen;
    private RtxImageView            imageView_Duration;

    private RtxTextView             textView_Duration;

    private View_Hiit_Other_Block   block_WarmUp;
    private View_Hiit_Other_Block   block_CoolDown;
    private View_Hiit_DataList      mView_Hiit_DataList;

    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private HiitProc    hiitProc;

    public Hiit_Personal_DataList_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
        super(context);

        mContext = context;
        this.hiitProc = hiitProc;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
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
        init_View();
        init_event();
        add_View();

        init_BackPrePage();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_TopBackground();
            init_Title();
            init_BackPrePage();
        }

        {
            if(fillerTextView_Preview == null)      { fillerTextView_Preview = new RtxFillerTextView(mContext); }
            if(imageView_NamePen == null)           { imageView_NamePen = new RtxImageView(mContext); }

            if(imageView_Duration == null)          { imageView_Duration = new RtxImageView(mContext); }
            if(textView_Duration == null)           { textView_Duration = new RtxTextView(mContext); }

            if(block_WarmUp == null)                { block_WarmUp = new View_Hiit_Other_Block(mContext,0); }
            if(block_CoolDown == null)              { block_CoolDown = new View_Hiit_Other_Block(mContext,1); }
            if(mView_Hiit_DataList == null)         { mView_Hiit_DataList = new View_Hiit_DataList(mContext,mMainActivity,hiitProc); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Preview.setOnClickListener(mButtonListener);
        imageView_NamePen.setOnClickListener(mButtonListener);

        block_WarmUp.setOnClickListener(mButtonListener);
        block_CoolDown.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(fillerTextView_Preview, R.string.view, 18f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
        fillerTextView_Preview.setMode(1);

        addViewToLayout(mView_Hiit_DataList,152,147,968,444);

        addViewToLayout(block_WarmUp,39,147,113,444);
        addViewToLayout(block_CoolDown,1128,147,113,444);

        addRtxImagePaddingViewToLayout(imageView_Duration, R.drawable.hiit_duration_icon, 91, 669, 52, 52, 0);
        addRtxTextViewToLayout(textView_Duration, -1, 39.41f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 181, 674, 500, 41);
    }

    private void addPen(String sText)
    {
        if(iMode == hiitProc.MODE_MODIFY)
        {
            int iWidth = -1;
            iWidth = Rtx_Draw.iGetTextWidth(mContext,sText,Common.Font.KatahdinRound,29.75f);

            int iX = 0;
            iX = ((1280 - iWidth) / 2) + iWidth + 30;

            addRtxImagePaddingViewToLayout(imageView_NamePen, R.drawable.workout_pen, iX, 43, 43, 27, 30);
        }
    }


    protected void setPreviewButtonEnable(boolean bEnable)
    {
        if(bEnable)
        {
            fillerTextView_Preview.setBackgroundColor(Common.Color.login_button_yellow);
        }
        else
        {
            fillerTextView_Preview.setBackgroundColor(Common.Color.gray_4);
        }

        fillerTextView_Preview.setClickable(bEnable);
        fillerTextView_Preview.setEnabled(bEnable);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo, int iSelectMode)
    {
        iMode = iSelectMode;

        this.hiitItemInfo = hiitItemInfo;
        vSetTitleText(this.hiitItemInfo.sGetDisplayName());
        addPen(this.hiitItemInfo.sGetDisplayName());

        mView_Hiit_DataList.vSetItemInfo(hiitItemInfo);

        //if(hiitItemInfo.stage_WarmUp.iTime > 0)
        {
            block_WarmUp.display();
            block_WarmUp.vSetData(hiitItemInfo.stage_WarmUp);
        }

        //if(hiitItemInfo.stage_CoolDown.iTime > 0)
        {
            block_CoolDown.display();
            block_CoolDown.vSetData(hiitItemInfo.stage_CoolDown);
        }

        if(this.hiitItemInfo.list_Stage.size() >= 1)
        {
            setPreviewButtonEnable(true);
        }
        else
        {
            setPreviewButtonEnable(false);
        }

        {
            String sDuration = LanguageData.s_get_string(mContext,R.string.duration_upper) + " " + Rtx_Calendar.sSec2Str(hiitItemInfo.iGetTotalSec());
            textView_Duration.setText(sDuration);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackButton()
    {
        if(iMode == hiitProc.MODE_PREVIEW)
        {
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
        }
        else if(iMode == hiitProc.MODE_CREATE)
        {
            vShowBackConfirmDialog();
            //hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_NAME);
        }
        else if(iMode == hiitProc.MODE_MODIFY)
        {
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_PREVIEW);
            hiitProc.vSetPersonalMode(hiitProc.MODE_PREVIEW);
        }
    }

    private void vClickPreviewButton()
    {
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_PREVIEW);
    }

    private void vClickEditNameButton()
    {
        hiitProc.vSetPreState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_NAME);
    }

    private void vClickWarmUpBlock()
    {
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_WARM_UP);
    }

    private void vClickCoolDownBlock(){
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_SETTING_COOL_DOWN);
    }

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v){
            if(v == imageView_BackPrePage)                                      { vBackButton(); }
            else if(v == fillerTextView_Preview)                                { vClickPreviewButton(); }
            else if(v == imageView_NamePen)                                     { vClickEditNameButton(); }
            else if(v == block_WarmUp)                                          { vClickWarmUpBlock(); }
            else if(v == block_CoolDown)                                        { vClickCoolDownBlock(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vShowBackConfirmDialog()
    {
        String sText = new String(LanguageData.s_get_string(mContext,R.string.workout_has_not_been_saved) + "\n" + LanguageData.s_get_string(mContext,R.string.are_you_sure_you_want_to_leave));

        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_YES_NO, sText,-1);

        mMainActivity.dialogLayout_YesNo.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_NAME);
                hiitItemInfo.stage_WarmUp.clear();
                hiitItemInfo.list_Stage.clear();
                hiitItemInfo.stage_CoolDown.clear();
                mMainActivity.dismissInfoDialog();
            }
        });
    }
}
