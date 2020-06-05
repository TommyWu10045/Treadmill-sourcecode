package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class HiitLayout extends Rtx_BaseLayout {
    private Context mContext;
    private MainActivity mMainActivity;
    private ButtonListener mButtonListener;
    private RtxImageView imageView_InfoButton;
    private RtxImageView imageView_Button_Normal;
    private RtxImageView imageView_Button_High;
    private RtxImageView imageView_Button_Advanced;
    private RtxImageView imageView_Button_Personal;
    private RtxTextView textView_Normal;
    private RtxTextView textView_High;
    private RtxTextView textView_Advanced;
    private RtxTextView textView_Personal;

    private RtxImageView imageView_Info_Normal;
    private RtxImageView imageView_Info_High;
    private RtxImageView imageView_Info_Advanced;
    private RtxImageView imageView_Info_Personal;
    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;
    private HiitProc hiitProc;

    public HiitLayout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
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
        {
            init_TopBackground();
            init_BackHome();
            init_Title();
            vSetTitleText(R.string.hiit);
        }

        {
            if(imageView_InfoButton == null)                { imageView_InfoButton = new RtxImageView(mContext); }
            if(imageView_Button_Normal == null)             { imageView_Button_Normal = new RtxImageView(mContext); }
            if(imageView_Button_High == null)               { imageView_Button_High = new RtxImageView(mContext); }
            if(imageView_Button_Advanced == null)           { imageView_Button_Advanced = new RtxImageView(mContext); }
            if(imageView_Button_Personal == null)           { imageView_Button_Personal = new RtxImageView(mContext); }
            if(imageView_Info_Normal == null)               { imageView_Info_Normal = new RtxImageView(mContext); }
            if(imageView_Info_High == null)                 { imageView_Info_High = new RtxImageView(mContext); }
            if(imageView_Info_Advanced == null)             { imageView_Info_Advanced = new RtxImageView(mContext); }
            if(imageView_Info_Personal == null)             { imageView_Info_Personal = new RtxImageView(mContext); }
            if(textView_Normal == null)                     { textView_Normal = new RtxTextView(mContext); }
            if(textView_High == null)                       { textView_High = new RtxTextView(mContext); }
            if(textView_Advanced == null)                   { textView_Advanced = new RtxTextView(mContext); }
            if(textView_Personal == null)                   { textView_Personal = new RtxTextView(mContext); }
        }
    }

    private void init_event(){
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_InfoButton.setOnClickListener(mButtonListener);
        imageView_Button_Normal.setOnClickListener(mButtonListener);
        imageView_Button_High.setOnClickListener(mButtonListener);
        imageView_Button_Advanced.setOnClickListener(mButtonListener);
        imageView_Button_Personal.setOnClickListener(mButtonListener);
        textView_Normal.setOnClickListener(mButtonListener);
        textView_High.setOnClickListener(mButtonListener);
        textView_Advanced.setOnClickListener(mButtonListener);
        textView_Personal.setOnClickListener(mButtonListener);
        imageView_Info_Normal.setOnClickListener(mButtonListener);
        imageView_Info_High.setOnClickListener(mButtonListener);
        imageView_Info_Advanced.setOnClickListener(mButtonListener);
        imageView_Info_Personal.setOnClickListener(mButtonListener);
    }

    private void add_View(){
        addRtxImagePaddingViewToLayout(imageView_InfoButton, R.drawable.info_icon, 820, 40, 34, 34, 30);
        addRtxImagePaddingViewToLayout(imageView_Button_Normal, R.drawable.hiit_icon_normal, 55, 290, 209, 210, 30);
        addRtxImagePaddingViewToLayout(imageView_Button_High, R.drawable.hiit_icon_high, 375, 290, 209, 210, 30);
        addRtxImagePaddingViewToLayout(imageView_Button_Advanced, R.drawable.hiit_icon_advanced, 695, 290, 209, 210, 30);
        addRtxImagePaddingViewToLayout(imageView_Button_Personal, R.drawable.hiit_icon_personal, 1015, 290, 209, 210, 30);
        addRtxTextViewToLayout(textView_Normal, R.string.normal_upper       , 33.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 55 - 25, 546 - 10, 210 + 50, 24 + 20);
        addRtxTextViewToLayout(textView_High, R.string.high           , 33.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 375 - 25, 546 - 10, 210 + 50, 24 + 20);
        addRtxTextViewToLayout(textView_Advanced, R.string.advanced   , 33.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 695 - 25, 546 - 10, 210 + 50, 24 + 20);
        addRtxTextViewToLayout(textView_Personal, R.string.personal   , 33.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1015 - 25, 546 - 10, 210 + 50, 24 + 20);
        addRtxImagePaddingViewToLayout(imageView_Info_Normal, R.drawable.hiit_mode_info, 150, 598, 21, 21, 30);
        addRtxImagePaddingViewToLayout(imageView_Info_High, R.drawable.hiit_mode_info, 470, 598, 21, 21, 30);
        addRtxImagePaddingViewToLayout(imageView_Info_Advanced, R.drawable.hiit_mode_info, 790, 598, 21, 21, 30);
        addRtxImagePaddingViewToLayout(imageView_Info_Personal, R.drawable.hiit_mode_info, 1110, 598, 21, 21, 30);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetHiitItemInfo(UiDataStruct.HIIT_ITEM_INFO itemInfo){
        this.hiitItemInfo=itemInfo;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackHome()
    {
        hiitProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vShowInfoDialog(){
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.main_icon_hiit,R.string.hiit,R.string.hiit_info);

        String stitle01=LanguageData.s_get_string(mContext, R.string.hiit);
        String sinfo01=LanguageData.s_get_string(mContext, R.string.hiit_info).toUpperCase();
        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_hiit, -1, stitle01, null, sinfo01, null, "ht_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickNormal(){
        hiitProc.vSetLevelMode(hiitProc.MODE_NORMAL);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_DEFAULT_DATA_PREVIEW);
    }

    private void vClickHigh(){
        hiitProc.vSetLevelMode(hiitProc.MODE_HIGH);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_DEFAULT_DATA_PREVIEW);
    }

    private void vClickAdvanced(){
        hiitProc.vSetLevelMode(hiitProc.MODE_ADVANCED);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_DEFAULT_DATA_PREVIEW);
    }

    private void vClickPersonal(){
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
    }

    private void vClickInfo_Normal(){
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.hiit_icon_normal,R.string.normal_upper,R.string.hiit_normal_info);
        String stitle01=LanguageData.s_get_string(mContext, R.string.normal_upper);
        String sinfo01=LanguageData.s_get_string(mContext, R.string.hiit_normal_info).toUpperCase();
        Dialog_UI_Info.v_tist_Dialog(R.drawable.hiit_icon_normal, -1, stitle01, null, sinfo01, null, "ht_fatburn", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickInfo_High(){
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.hiit_icon_high,R.string.normal_upper,R.string.hiit_high_info);
        String stitle01=LanguageData.s_get_string(mContext, R.string.high);
        String sinfo01=LanguageData.s_get_string(mContext, R.string.hiit_high_info).toUpperCase();
        Dialog_UI_Info.v_tist_Dialog(R.drawable.hiit_icon_high, -1, stitle01, null, sinfo01, null, "ht_aerobic", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickInfo_Advanced(){
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.hiit_icon_advanced,R.string.advanced,R.string.hiit_advanced_info);
        String stitle01=LanguageData.s_get_string(mContext, R.string.advanced);
        String sinfo01=LanguageData.s_get_string(mContext, R.string.hiit_advanced_info).toUpperCase();
        Dialog_UI_Info.v_tist_Dialog(R.drawable.hiit_icon_advanced, -1, stitle01, null, sinfo01, null, "ht_performance", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickInfo_Personal(){
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.hiit_icon_personal,R.string.personal,R.string.hiit_personal_info);
        String stitle01=LanguageData.s_get_string(mContext, R.string.personal);
        String sinfo01=LanguageData.s_get_string(mContext, R.string.hiit_personal_info).toUpperCase();
        Dialog_UI_Info.v_tist_Dialog(R.drawable.hiit_icon_personal, -1, stitle01, null, sinfo01, null, "ht_customize", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v){
            if(v == imageView_BackHome){ vBackHome(); }
            else if(v == imageView_InfoButton){ vShowInfoDialog(); }
            else if(v == imageView_Button_Normal){ vClickNormal(); }
            else if(v == imageView_Button_High){ vClickHigh(); }
            else if(v == imageView_Button_Advanced){ vClickAdvanced(); }
            else if(v == imageView_Button_Personal){ vClickPersonal(); }
            else if(v == textView_Normal){ vClickNormal(); }
            else if(v == textView_High){ vClickHigh(); }
            else if(v == textView_Advanced){ vClickAdvanced(); }
            else if(v == textView_Personal){ vClickPersonal(); }
            else if(v == imageView_Info_Normal){ vClickInfo_Normal(); }
            else if(v == imageView_Info_High){ vClickInfo_High(); }
            else if(v == imageView_Info_Advanced){ vClickInfo_Advanced(); }
            else if(v == imageView_Info_Personal){ vClickInfo_Personal(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
