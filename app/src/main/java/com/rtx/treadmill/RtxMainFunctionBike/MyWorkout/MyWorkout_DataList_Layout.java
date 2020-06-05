package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

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
import com.rtx.treadmill.RtxTools.Rtx_Draw;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkout_DataList_Layout extends Rtx_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private int iMode = MODE_NONE;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private View_MyWorkout_DataList view_DataList;
    private RtxFillerTextView       fillerTextView_Preview;

    private RtxImageView            imageView_NamePen;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;


    public MyWorkout_DataList_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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
            if(view_DataList == null)               { view_DataList = new View_MyWorkout_DataList(mContext,mMainActivity); }
            if(imageView_NamePen == null)           { imageView_NamePen = new RtxImageView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Preview.setOnClickListener(mButtonListener);
        imageView_NamePen.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        addRtxTextViewToLayout(fillerTextView_Preview, R.string.preview, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 515, 700, 250, 75, Common.Color.login_button_yellow);//By Alan
        addViewToLayout(view_DataList,0,98,1280,545);
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


    protected void setPreviewButtonEnable(boolean bEnable)
    {
        if(bEnable)
        {
            fillerTextView_Preview.setBackgroundColor(Common.Color.login_button_yellow);
            fillerTextView_Preview.setEnabled(true);
        }
        else
        {
            fillerTextView_Preview.setBackgroundColor(Common.Color.gray_4);
            fillerTextView_Preview.setEnabled(false);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem, int iSelectMode)
    {
        iMode = iSelectMode;

        this.workoutItem = workoutItem;
        vSetTitleText(this.workoutItem.sName);
        addPen(this.workoutItem.sName);
        view_DataList.vSetWorkoutItemInfo(workoutItem);

        if(this.workoutItem.list_Stage.size() >= 1)
        {
            setPreviewButtonEnable(true);
        }
        else
        {
            setPreviewButtonEnable(false);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackButton()
    {
        if(iMode == MODE_PREVIEW)
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_MAIN);
        }
        else if(iMode == MODE_CREATE)
        {
            vShowBackConfirmDialog();
            //mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_NAME);
        }
        else if(iMode == MODE_MODIFY)
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_PREVIEW);
            mMainActivity.mMainProcBike.myWorkoutProc.vSetMode(MODE_PREVIEW);
        }
    }

    private void vClickPreviewButton()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_PREVIEW);
    }

    private void vClickEditNameButton()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetPreState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_NAME);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                                      { vBackButton(); }
            else if(v == fillerTextView_Preview)                                { vClickPreviewButton(); }
            else if(v == imageView_NamePen)                                     { vClickEditNameButton(); }
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
                mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_NAME);
                workoutItem.list_Stage.clear();
                mMainActivity.dismissInfoDialog();
            }
        });
    }
}
