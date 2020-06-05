package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainHistory_Main extends Rtx_BaseLayout {

    final   int     MODE_EDIT   =   0;
    final   int     MODE_DONE   =   1;

    protected         Context mContext;

    protected         ButtonListener      mButtonListener;
    protected         MainActivity        mMainActivity;

    protected         RtxFillerTextView   fillerTextView_Edit;
    protected         HistoryAdapter      mHistoryAdapter;
    protected          GridView            mGridView;

    private         int                 iMode = 1;


    ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose;

    public TargetTrainHistory_Main(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;

        setBackgroundColor(Common.Color.background);
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
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_BackPrePage();
            init_Title();
            vSetTitleText(R.string.history_upper);
        }

        {
            if(fillerTextView_Edit == null) { fillerTextView_Edit = new RtxFillerTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Edit.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        addRtxTextViewToLayout(fillerTextView_Edit, R.string.edit, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1124, 40, 106, 40, 50);
        fillerTextView_Edit.setMode(3);

        setEditButtonState(iMode);
    }

    protected void add_GridView()
    {
        addViewToLayout(mGridView,31,183,1280-31-31,800-183);
    }

    protected void init_GridView()
    {
        mHistoryAdapter = new HistoryAdapter(mContext,mMainActivity,list_CloudTargetGoalClose);

        if(mGridView == null)
        {
            mGridView = new GridView(mContext);
        }

        add_GridView();

        mGridView.setAdapter(mHistoryAdapter);
        mGridView.setNumColumns(3);
        mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mGridView.setVerticalSpacing(100);
        mGridView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

        if(iMode == MODE_DONE)
        {
            imageView_BackPrePage.setVisibility(VISIBLE);
            mHistoryAdapter.bDeleteVisible = false;
        }
        else
        {
            imageView_BackPrePage.setVisibility(INVISIBLE);
            mHistoryAdapter.bDeleteVisible = true;
        }

        mHistoryAdapter.notifyDataSetChanged();
    }

    private void setEditButtonState(int mode)
    {
        if(mode == MODE_DONE)
        {
            imageView_BackPrePage.setVisibility(VISIBLE);

            fillerTextView_Edit.setText(LanguageData.s_get_string(mContext,R.string.edit));
            fillerTextView_Edit.setTextColor(Common.Color.white);
            fillerTextView_Edit.setBackgroundColor(Common.Color.white);

            if(mHistoryAdapter != null)
            {
                mHistoryAdapter.bDeleteVisible = false;
                mHistoryAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            imageView_BackPrePage.setVisibility(INVISIBLE);

            fillerTextView_Edit.setText(LanguageData.s_get_string(mContext,R.string.done));
            fillerTextView_Edit.setTextColor(Common.Color.yellow);
            fillerTextView_Edit.setBackgroundColor(Common.Color.yellow);

            if(mHistoryAdapter != null)
            {
                mHistoryAdapter.bDeleteVisible = true;
                mHistoryAdapter.notifyDataSetChanged();
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_MAIN);
    }

    private void vChangeMode()
    {
        if(iMode == MODE_DONE)
        {
            iMode = MODE_EDIT;
        }
        else
        {
            iMode = MODE_DONE;
        }

        setEditButtonState(iMode);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vBackPrePage(); }
            else if(v == fillerTextView_Edit)   { vChangeMode(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoalCloseList(ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE> list_CloudTargetGoalClose)
    {
        this.list_CloudTargetGoalClose = list_CloudTargetGoalClose;

        init_GridView();
    }
}
