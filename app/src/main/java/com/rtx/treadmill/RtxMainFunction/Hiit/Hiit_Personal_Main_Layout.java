package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Personal_Main_Layout extends Rtx_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxFillerTextView   fillerTextView_Create;

    private RtxFillerTextView   fillerTextView_Start_1st;
    private RtxFillerTextView   fillerTextView_Start_2st;
    private RtxFillerTextView   fillerTextView_Start_3st;

    private RtxTextView         textView_Name_1st;
    private RtxTextView         textView_Name_2st;
    private RtxTextView         textView_Name_3st;

    private View_HIIT_RunChart  simplePreview_1st;
    private View_HIIT_RunChart  simplePreview_2st;
    private View_HIIT_RunChart  simplePreview_3st;

    private View_HIIT_TimeBar   timeBar_1st;
    private View_HIIT_TimeBar   timeBar_2st;
    private View_HIIT_TimeBar   timeBar_3st;

    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private ArrayList<UiDataStruct.HIIT_ITEM_INFO> list_HiitItemInfo;
    private HiitProc    hiitProc;

    public static int   SprintTime=0;
    public static float SprintSpeed=0f;
    public static float SprintIncline=0f;

    public static int   RecoveryTime=0;
    public static float RecoverySpeed=0f;
    public static float RecoveryIncline=0f;

    public Hiit_Personal_Main_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
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

        if(check_Login())
        {
            setCreateButtonEnable(true);
        }
        else
        {
            setCreateButtonEnable(false);
        }
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
            init_BackPrePage();
            init_Title();

            {
                String sTitle = LanguageData.s_get_string(mContext,R.string.hiit) + " - " + LanguageData.s_get_string(mContext,R.string.personal);
                vSetTitleText(sTitle);
            }

        }

        {
            if(fillerTextView_Create == null)           { fillerTextView_Create = new RtxFillerTextView(mContext); }

            if(fillerTextView_Start_1st == null)        { fillerTextView_Start_1st = new RtxFillerTextView(mContext); }
            if(fillerTextView_Start_2st == null)        { fillerTextView_Start_2st = new RtxFillerTextView(mContext); }
            if(fillerTextView_Start_3st == null)        { fillerTextView_Start_3st = new RtxFillerTextView(mContext); }

            if(textView_Name_1st == null)               { textView_Name_1st = new RtxTextView(mContext); }
            if(textView_Name_2st == null)               { textView_Name_2st = new RtxTextView(mContext); }
            if(textView_Name_3st == null)               { textView_Name_3st = new RtxTextView(mContext); }

            if(simplePreview_1st == null)               { simplePreview_1st = new View_HIIT_RunChart(mContext,418,127); }
            if(simplePreview_2st == null)               { simplePreview_2st = new View_HIIT_RunChart(mContext,418,127); }
            if(simplePreview_3st == null)               { simplePreview_3st = new View_HIIT_RunChart(mContext,418,127); }

            if(timeBar_1st == null)                     { timeBar_1st = new View_HIIT_TimeBar(mContext,418,18); }
            if(timeBar_2st == null)                     { timeBar_2st = new View_HIIT_TimeBar(mContext,418,18); }
            if(timeBar_3st == null)                     { timeBar_3st = new View_HIIT_TimeBar(mContext,418,18); }

            timeBar_1st.vSetWarmUpColor(Common.Color.green_7);
            timeBar_1st.vSetCoolDownColor(Common.Color.blue_13);
            timeBar_1st.setBackgroundColor(Common.Color.blue_2);
            timeBar_2st.vSetWarmUpColor(Common.Color.green_7);
            timeBar_2st.vSetCoolDownColor(Common.Color.blue_13);
            timeBar_2st.setBackgroundColor(Common.Color.blue_2);
            timeBar_3st.vSetWarmUpColor(Common.Color.green_7);
            timeBar_3st.vSetCoolDownColor(Common.Color.blue_13);
            timeBar_3st.setBackgroundColor(Common.Color.blue_2);

            simplePreview_1st.vSetWarmUpColor(Common.Color.green_7);
            simplePreview_1st.vSetCoolDownColor(Common.Color.blue_13);
            simplePreview_1st.setBackgroundColor(Common.Color.blue_2);
            simplePreview_2st.vSetWarmUpColor(Common.Color.green_7);
            simplePreview_2st.vSetCoolDownColor(Common.Color.blue_13);
            simplePreview_2st.setBackgroundColor(Common.Color.blue_2);
            simplePreview_3st.vSetWarmUpColor(Common.Color.green_7);
            simplePreview_3st.vSetCoolDownColor(Common.Color.blue_13);
            simplePreview_3st.setBackgroundColor(Common.Color.blue_2);
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Create.setOnClickListener(mButtonListener);

        textView_Name_1st.setOnClickListener(mButtonListener);
        textView_Name_2st.setOnClickListener(mButtonListener);
        textView_Name_3st.setOnClickListener(mButtonListener);

        simplePreview_1st.setOnClickListener(mButtonListener);
        simplePreview_2st.setOnClickListener(mButtonListener);
        simplePreview_3st.setOnClickListener(mButtonListener);

        timeBar_1st.setOnClickListener(mButtonListener);
        timeBar_2st.setOnClickListener(mButtonListener);
        timeBar_3st.setOnClickListener(mButtonListener);

        fillerTextView_Start_1st.setOnClickListener(mButtonListener);
        fillerTextView_Start_2st.setOnClickListener(mButtonListener);
        fillerTextView_Start_3st.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(fillerTextView_Create, R.string.create_your_workout, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 295, 363, 690, 75, Common.Color.login_button_yellow);
    }

    private void add_Preview(int iIndex , ArrayList<UiDataStruct.HIIT_ITEM_INFO> list_HiitItemInfo)
    {
        if(iIndex == 0)      { add_Preview_1st(list_HiitItemInfo.get(iIndex)); }
        else if(iIndex == 1) { add_Preview_2st(list_HiitItemInfo.get(iIndex)); }
        else if(iIndex == 2) { add_Preview_3st(list_HiitItemInfo.get(iIndex)); }
    }

    private void add_Preview_1st(UiDataStruct.HIIT_ITEM_INFO hiit_Item)
    {
        addViewToLayout(simplePreview_1st, 81, 278, 418, 127);
        addViewToLayout(timeBar_1st, 81, 278 + 127 - simplePreview_1st.getVerticalOffset(), 418, 43);
        vSetPreviewInfo(simplePreview_1st,timeBar_1st,hiit_Item);
    }

    private void add_Preview_2st(UiDataStruct.HIIT_ITEM_INFO hiit_Item)
    {
        addViewToLayout(simplePreview_2st, 686, 278, 418, 127);
        addViewToLayout(timeBar_2st, 686, 278 + 127 - simplePreview_2st.getVerticalOffset(), 418, 43);
        vSetPreviewInfo(simplePreview_2st,timeBar_2st,hiit_Item);
    }

    private void add_Preview_3st(UiDataStruct.HIIT_ITEM_INFO hiit_Item)
    {
        addViewToLayout(simplePreview_3st, 81, 557, 418, 127);
        addViewToLayout(timeBar_3st, 81, 557 + 127 - simplePreview_3st.getVerticalOffset(), 418, 43);
        vSetPreviewInfo(simplePreview_3st,timeBar_3st,hiit_Item);
    }

    private void vSetPreviewInfo(View_HIIT_RunChart preview, View_HIIT_TimeBar timeBar , UiDataStruct.HIIT_ITEM_INFO hiit_Item)
    {
        preview.setItemInfo(hiit_Item);
        timeBar.setItemInfo(hiit_Item);
    }

    private void add_Item(int iIndex , String sName)
    {
        if(iIndex == 0)      { add_Item_1st(sName); }
        else if(iIndex == 1) { add_Item_2st(sName); }
        else if(iIndex == 2) { add_Item_3st(sName); }
    }

    private void add_Item_1st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_1st, R.string.start, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 514, 313, 96, 96, Common.Color.login_button_yellow);
        fillerTextView_Start_1st.setMode(1);
        addRtxTextViewToLayout(textView_Name_1st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 81 , 461 - 25, 417, 23 + 50);
    }

    private void add_Item_2st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_2st, R.string.start, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1121, 313, 96, 96, Common.Color.login_button_yellow);
        fillerTextView_Start_2st.setMode(1);
        addRtxTextViewToLayout(textView_Name_2st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 686, 461 - 25, 417, 23 + 50);
    }

    private void add_Item_3st(String sName)
    {
        addRtxTextViewToLayout(fillerTextView_Start_3st, R.string.start, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 514, 593, 96, 96, Common.Color.login_button_yellow);
        fillerTextView_Start_3st.setMode(1);
        addRtxTextViewToLayout(textView_Name_3st, sName, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 81 , 741 - 25, 417, 23 + 50);
    }

    private boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();
        //Login
        if(bLogin)
        {

        }
        //Un Login
        else
        {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGIN,-1,-1);
            mMainActivity.dialogLayout_Login.fillerTextView_Login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    hiitProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    hiitProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcTreadmill.vSetBeforeLoginState(MainState.PROC_TARGET_TRAIN);
                    //mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //hiitProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });
//            setCreateButtonState(0);
        }

        return bLogin;
    }

    protected void setCreateButtonEnable(boolean bEnable)
    {
        if(bEnable)
        {
            fillerTextView_Create.setBackgroundColor(Common.Color.login_button_yellow);
        }
        else
        {
            fillerTextView_Create.setBackgroundColor(Common.Color.gray_4);
        }

        fillerTextView_Create.setClickable(bEnable);
        fillerTextView_Create.setEnabled(bEnable);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo)
    {
        this.hiitItemInfo = hiitItemInfo;
    }

    protected void vUpdateNameInfo(ArrayList<UiDataStruct.HIIT_ITEM_INFO> list_HiitItemInfo)
    {
        if(list_HiitItemInfo == null)
        {
            return;
        }

        int iSize = list_HiitItemInfo.size();
        int iIndex = 0;

        if(iSize > 0)
        {
            removeView(fillerTextView_Create);
            addRtxTextViewToLayout(fillerTextView_Create, R.string.create_your_workout, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 295, 147, 690, 75, Common.Color.login_button_yellow);
            if(iSize >= 3)
            {
                setCreateButtonEnable(false);
            }
            else
            {
                setCreateButtonEnable(true);
            }

            for( ; iIndex < iSize ; iIndex++)
            {
                add_Item(iIndex,list_HiitItemInfo.get(iIndex).sGetDisplayName());
            }
        }
    }

    protected void vUpdatePreviewInfo(ArrayList<UiDataStruct.HIIT_ITEM_INFO> list_HiitItemInfo)
    {
        if(list_HiitItemInfo == null)
        {
            return;
        }

        this.list_HiitItemInfo = list_HiitItemInfo;

        int iSize = list_HiitItemInfo.size();
        int iIndex = 0;

        if(iSize > 0)
        {
            for( ; iIndex < iSize ; iIndex++)
            {
                add_Preview(iIndex,list_HiitItemInfo);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vBackPrePage()
    {
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_MAIN);
    }


    private void vClickCreateButton()
    {
        hiitItemInfo.clear();
        hiitProc.vSetPersonalMode(hiitProc.MODE_CREATE);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_NAME);
        hiitProc.vSetSelectItemIndex(-1);
    }

    private void vClickItem(int iIndex)
    {
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_PREVIEW);
        hiitProc.vSetPersonalMode(MODE_PREVIEW);
        hiitProc.vSetSelectItemIndex(iIndex);
    }

    private void vClickStart(int iIndex)
    {
        if(iIndex < list_HiitItemInfo.size())
        {
            list_HiitItemInfo.get(iIndex).copyTo(hiitItemInfo);
            hiitProc.vSetNextState(HiitState.PROC_EXERCISE_PREPARE);
        }
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                                      { vBackPrePage(); }
            else if(v == fillerTextView_Create)                                 { vClickCreateButton(); }
            else if(v == textView_Name_1st)                                     { vClickItem(0); }
            else if(v == textView_Name_2st)                                     { vClickItem(1); }
            else if(v == textView_Name_3st)                                     { vClickItem(2); }
            else if(v == simplePreview_1st)                                     { vClickItem(0); }
            else if(v == simplePreview_2st)                                     { vClickItem(1); }
            else if(v == simplePreview_3st)                                     { vClickItem(2); }
            else if(v == timeBar_1st)                                           { vClickItem(0); }
            else if(v == timeBar_2st)                                           { vClickItem(1); }
            else if(v == timeBar_3st)                                           { vClickItem(2); }
            else if(v == fillerTextView_Start_1st)                              { vClickStart(0); }
            else if(v == fillerTextView_Start_2st)                              { vClickStart(1); }
            else if(v == fillerTextView_Start_3st)                              { vClickStart(2); }

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
