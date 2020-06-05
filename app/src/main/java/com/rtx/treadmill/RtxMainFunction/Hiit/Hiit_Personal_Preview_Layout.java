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
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Draw;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class Hiit_Personal_Preview_Layout extends Rtx_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private int iMode = MODE_NONE;

    private Context mContext;

    private MainActivity        mMainActivity;
    private HiitProc            hiitProc;
    private ButtonListener      mButtonListener;

    private RtxFillerTextView               fillerTextView_Button;

    private RtxImageView                    imageView_DeleteItem;
    private RtxImageView                    imageView_ModifyItem;
    private RtxImageView                    imageView_NamePen;

    private RtxTextView                     textView_Intervals_Val;
    private RtxTextView                     textView_Intervals_Text;
    private RtxTextView                     textView_Duration_Val;
    private RtxTextView                     textView_Duration_Text;

    private View_Hiit_CombineChart          mView_Hiit_CombineChart;


    private UiDataStruct.HIIT_ITEM_INFO hiitItemInfo;

    public Hiit_Personal_Preview_Layout(Context context, MainActivity mMainActivity, HiitProc hiitProc) {
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
            if(fillerTextView_Button == null)             { fillerTextView_Button = new RtxFillerTextView(mContext); }

            if(imageView_NamePen == null)                 { imageView_NamePen = new RtxImageView(mContext); }
            if(imageView_ModifyItem == null)              { imageView_ModifyItem = new RtxImageView(mContext); }
            if(imageView_DeleteItem == null)              { imageView_DeleteItem = new RtxImageView(mContext); }

            if(textView_Intervals_Val == null)            { textView_Intervals_Val = new RtxTextView(mContext); }
            if(textView_Intervals_Text == null)           { textView_Intervals_Text = new RtxTextView(mContext); }
            if(textView_Duration_Val == null)             { textView_Duration_Val = new RtxTextView(mContext); }
            if(textView_Duration_Text == null)            { textView_Duration_Text = new RtxTextView(mContext); }

            if(mView_Hiit_CombineChart == null)           {
                mView_Hiit_CombineChart = new View_Hiit_CombineChart(mContext);
            }
            else {
                mView_Hiit_CombineChart.onDestroy();
                mView_Hiit_CombineChart.init();
            }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Button.setOnClickListener(mButtonListener);
        imageView_DeleteItem.setOnClickListener(mButtonListener);
        imageView_ModifyItem.setOnClickListener(mButtonListener);
        imageView_NamePen.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(textView_Intervals_Val, -1, 50f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 432 - 50, 673 - 25, 112 + 100, 57 + 50);
        addRtxTextViewToLayout(textView_Intervals_Text, R.string.intervals, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 432 - 50, 737 - 25, 112 + 100, 18 + 50);
        addRtxTextViewToLayout(textView_Duration_Val, -1, 50f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 667 - 50, 682 - 25, 241 + 100, 36 + 50);
        addRtxTextViewToLayout(textView_Duration_Text, R.string.duration_upper, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 667 - 50, 737 - 25, 241 + 100, 18 + 50);

        addViewToLayout(mView_Hiit_CombineChart,55 - mView_Hiit_CombineChart.iAxisX_Offset,136,1200 + mView_Hiit_CombineChart.iAxisX_Offset,479);
    }

    private void add_ModifyView()
    {
        addRtxTextViewToLayout(fillerTextView_Button, R.string.save, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
        addPen(hiitItemInfo.sName);
    }

    private void addPen(String sText)
    {
        if(iMode == MODE_MODIFY)
        {
            int iWidth = -1;
            iWidth = Rtx_Draw.iGetTextWidth(mContext,sText,Common.Font.KatahdinRound,29.75f);

            int iX = 0;
            iX = ((1280 - iWidth) / 2) + iWidth + 30;

            addRtxImagePaddingViewToLayout(imageView_NamePen, R.drawable.workout_pen, iX, 43, 43, 27, 30);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO hiitItemInfo, int iSelectMode)
    {
        this.iMode = iSelectMode;
        this.hiitItemInfo = hiitItemInfo;


        vSetTitleText(hiitItemInfo.sGetDisplayName());
        mView_Hiit_CombineChart.vSetWorkoutItemInfo(hiitItemInfo);

        if(iMode == MODE_PREVIEW)
        {
            add_ButtonView();
        }
        else if(iMode == MODE_CREATE)
        {
            add_CreateView();
        }
        else if(iMode == MODE_MODIFY)
        {
            add_ModifyView();
        }

        textView_Duration_Val.setText(Rtx_Calendar.sSec2Str(hiitItemInfo.iGetTotalSec()));
        textView_Intervals_Val.setText(Rtx_TranslateValue.sInt2String(hiitItemInfo.list_Stage.size() / 2));
    }

    private void add_ButtonView()
    {
        addRtxImagePaddingViewToLayout(imageView_DeleteItem, R.drawable.hiit_delete_icon, 77, 662, 87, 87, 30);
        addRtxImagePaddingViewToLayout(imageView_ModifyItem, R.drawable.hiit_edit_icon, 194, 662, 87, 87, 30);
        addRtxTextViewToLayout(fillerTextView_Button, R.string.start, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
    }

    private void add_CreateView()
    {
        addRtxTextViewToLayout(fillerTextView_Button, R.string.save, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBackButton()
    {
        if(iMode == MODE_PREVIEW)
        {
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_MAIN);
        }
        else if(iMode == MODE_CREATE)
        {
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        }
        else if(iMode == MODE_MODIFY)
        {
            hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        }
    }

    private void vClickButton()
    {
        if(iMode == MODE_CREATE)
        {
            hiitProc.vSetNextState(HiitState.PROC_CLOUD_ADD_INTERVAL);
        }
        else if(iMode == MODE_MODIFY)
        {
            hiitProc.vSetNextState(HiitState.PROC_CLOUD_UPDATE_INTERVAL);
        }
        else
        {
            hiitProc.vSetNextState(HiitState.PROC_EXERCISE_PREPARE);
        }
    }

    private void vClickDeleteItem()
    {
        vShowDeleteDialog();
        //hiitProc.vSetNextState(MyWorkoutState.PROC_CLOUD_DELETE_INTERVAL);
    }

    private void vClickModifyItem()
    {
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_DATALIST);
        hiitProc.vSetPersonalMode(MODE_MODIFY);
    }

    private void vClickEditNameButton()
    {
        hiitProc.vSetPreState(HiitState.PROC_SHOW_PAGE_PERSONAL_PREVIEW);
        hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_PERSONAL_NAME);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)              { vClickBackButton(); }
            else if(v == fillerTextView_Button)         { vClickButton(); }
            else if(v == imageView_DeleteItem)          { vClickDeleteItem(); }
            else if(v == imageView_ModifyItem)          { vClickModifyItem(); }
            else if(v == imageView_NamePen)             { vClickEditNameButton(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vShowDeleteDialog()
    {
        String str = LanguageData.s_get_string(mContext,R.string.hiit_delete_item_description) + "\n" + hiitItemInfo.sGetDisplayName();
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE, str,-1);

        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hiitProc.vSetDeleteMode(hiitProc.MODE_DELETE_ITEM);
                hiitProc.vSetNextState(HiitState.PROC_SHOW_PAGE_DIALOG_DELETE);
            }
        });
    }
}
