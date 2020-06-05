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
import com.rtx.treadmill.RtxView.RtxExercisePreview;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyWorkout_Preview_Layout extends Rtx_BaseLayout {

    final int MODE_NONE = -1;
    final int MODE_CREATE = 0;
    final int MODE_MODIFY = 1;
    final int MODE_PREVIEW = 2;

    private int iMode = MODE_NONE;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxFillerTextView       fillerTextView_Button;
    private RtxTextView             textView_Details;
    private RtxImageView            imageView_Details;

    private RtxImageView            imageView_ModifyItem;
    private RtxImageView            imageView_DeleteItem;

    private RtxImageView            imageView_NamePen;

    private RtxExercisePreview      preview_Chart;

    private UiDataStruct.WORKOUT_ITEM_INFO workoutItem;

    public MyWorkout_Preview_Layout(Context context, MainActivity mMainActivity) {
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
            if(fillerTextView_Button == null)     { fillerTextView_Button = new RtxFillerTextView(mContext); }
            if(textView_Details == null)          { textView_Details = new RtxTextView(mContext); }
            if(imageView_Details == null)         { imageView_Details = new RtxImageView(mContext); }

            if(imageView_ModifyItem == null)      { imageView_ModifyItem = new RtxImageView(mContext); }
            if(imageView_DeleteItem == null)      { imageView_DeleteItem = new RtxImageView(mContext); }

            if(imageView_NamePen == null)         { imageView_NamePen = new RtxImageView(mContext); }

            if(preview_Chart == null)             { preview_Chart = new RtxExercisePreview(mContext,1); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Button.setOnClickListener(mButtonListener);
        textView_Details.setOnClickListener(mButtonListener);
        imageView_Details.setOnClickListener(mButtonListener);
        imageView_DeleteItem.setOnClickListener(mButtonListener);
        imageView_ModifyItem.setOnClickListener(mButtonListener);
        imageView_NamePen.setOnClickListener(mButtonListener);
    }



    private void add_View()
    {
        fillerTextView_Button.setMode(1);
        addRtxTextViewToLayout(textView_Details, R.string.details, 28f, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 561 - 50, 679 - 25, 135 + 100, 32 + 50);
        addRtxImagePaddingViewToLayout(imageView_Details, R.drawable.workout_details, 614, 738, 30, 17, 30);
    }

    private void add_Preview()
    {
        addViewToLayout(preview_Chart, 90, 140, 1100, 505);

        {
            int iDataCount = 0;

            ArrayList<Float> list_speed = new ArrayList<Float>();
            workoutItem.bExportStageInfoToMinList(list_speed, workoutItem.TRANSLATE_SPEDD);

            ArrayList<Float> list_incline = new ArrayList<Float>();
            workoutItem.bExportStageInfoToMinList(list_incline, workoutItem.TRANSLATE_INCLINE);

            iDataCount = list_incline.size();

            preview_Chart.init(1f,iDataCount);
            preview_Chart.i_profile.vSetInclineList(list_incline);
            preview_Chart.i_profile.vSetSpeedList(list_speed);
            preview_Chart.i_profile.vSetflash(iDataCount-1);

            preview_Chart.t_speed.setText(LanguageData.s_get_string(mContext,R.string.resistance_level));
            preview_Chart.i_speed.setImageResource(R.drawable.resistance_level_icon);

            preview_Chart.t_speed.setVisibility(VISIBLE);
            preview_Chart.i_speed.setVisibility(VISIBLE);
            preview_Chart.t_incline.setVisibility(INVISIBLE);
            preview_Chart.i_incline.setVisibility(INVISIBLE);
            preview_Chart.t_min.setVisibility(VISIBLE);

        }
    }

    private void add_ButtonView()
    {
        addRtxImagePaddingViewToLayout(imageView_DeleteItem, R.drawable.workout_delete_item, 1059, 44, 25, 29, 30);
        addRtxImagePaddingViewToLayout(imageView_ModifyItem, R.drawable.workout_edit_item_pen, 1170, 42, 25, 34, 30);
        addRtxTextViewToLayout(fillerTextView_Button, R.string.start, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);//By Alan
    }

    private void add_CreateView()
    {
        addRtxTextViewToLayout(fillerTextView_Button, R.string.save, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);//By Alan
    }

    private void add_ModifyView()
    {
        addRtxTextViewToLayout(fillerTextView_Button, R.string.save, 20f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 1088, 628, 133, 133, Common.Color.login_button_yellow);//By Alan
        addPen(workoutItem.sName);
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

    protected void vSetWorkoutItemInfo(UiDataStruct.WORKOUT_ITEM_INFO workoutItem, int iSelectMode)
    {
        this.workoutItem = workoutItem;
        vSetTitleText(this.workoutItem.sName);

        iMode = iSelectMode;

        add_Preview();

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
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
        }
        else if(iMode == MODE_MODIFY)
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
        }
    }

    private void vClickButton()
    {
        if(iMode == MODE_CREATE)
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_CLOUD_ADD_INTERVAL);
        }
        else if(iMode == MODE_MODIFY)
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_CLOUD_UPDATE_INTERVAL);
        }
        else
        {
            mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_EXERCISE_PREPARE);
        }
    }

    private void vClickDetailsButton()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DETAILS);
    }

    private void vClickDeleteItem()
    {
		vShowDeleteDialog();        
		//mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_CLOUD_DELETE_INTERVAL);
    }

    private void vClickModifyItem()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_DATALIST);
        mMainActivity.mMainProcBike.myWorkoutProc.vSetMode(MODE_MODIFY);
    }

    private void vClickEditNameButton()
    {
        mMainActivity.mMainProcBike.myWorkoutProc.vSetPreState(MyWorkoutState.PROC_SHOW_PAGE_PREVIEW);
        mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_PAGE_NAME);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vBackButton(); }
            else if(v == fillerTextView_Button)     { vClickButton(); }
            else if(v == textView_Details)          { vClickDetailsButton(); }
            else if(v == imageView_Details)         { vClickDetailsButton(); }
            else if(v == imageView_DeleteItem)      { vClickDeleteItem(); }
            else if(v == imageView_ModifyItem)      { vClickModifyItem(); }
            else if(v == imageView_NamePen)         { vClickEditNameButton(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void vShowDeleteDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE, LanguageData.s_get_string(mContext,R.string.delete_item_description),-1);

        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.mMainProcBike.myWorkoutProc.vSetDeleteMode(0);
                mMainActivity.mMainProcBike.myWorkoutProc.vSetNextState(MyWorkoutState.PROC_SHOW_DIALOG_DELETE);
            }
        });
    }
}
