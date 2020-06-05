package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.CloudDataStruct.HistoryData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/22/17.
 */

public class HistoryLayout extends Rtx_BaseLayout {
    private static String TAG = "Jerry=BDHis";
    private final static boolean DEBUG = false;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    RtxTextView titem_date;
    RtxTextView titem;

    private HistoryAdapter adapter = null;
    private ArrayList<HistoryData> historypoints = CloudDataStruct.BodyIndexData_14.historypoints_list;
    private ListView lvhistory;

    private int imode;

    private int i_gap_null = 40;
    private int i_gap_data = 20;

    public HistoryLayout(Context context, MainActivity mMainActivity) {
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
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        init_Title();
        init_BackPrePage();

        if(titem_date == null)           {titem_date = new RtxTextView(mContext);}
        if(titem == null)           {titem = new RtxTextView(mContext);}

    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
    }

    private void add_listview(ListView mListView , int iX , int iY , int iWidth , int iHeight) {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mListView.setLayoutParams(mLayoutParams);

        addView(mListView);
    }

    private void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata;
        float fsize_unit;

        //        menu
        sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, imode) ;
        sdata += "   -   " + LanguageData.s_get_string(mContext, R.string.history);
        vSetTitleText(sdata.toUpperCase());

//        date
        ix = 200;
        iy = 130;
        iw = 300;
        ih = 60;//By Alan
        fsize = 20;
        addRtxTextViewToLayout(titem_date, R.string.date, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//        title
        ix += 600;
        sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, imode) ;
        addRtxTextViewToLayout(titem, sdata, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 200;
        iy = 200;
        iw = 1080;
        ih = 600;
        lvhistory = new ListView(mContext);
        adapter = new HistoryAdapter((ArrayList<HistoryData>)historypoints,mContext, imode);
        lvhistory.setAdapter(adapter);
        add_listview(lvhistory, ix, iy, iw, ih);

        lvhistory.setOnScrollListener(list_scroll);
    }

    public void v_setSelect(int isel)
    {
        imode = isel;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private AbsListView.OnScrollListener list_scroll = new AbsListView.OnScrollListener() {
        private int firstVisibleItem, visibleItemCount, totalItemCount;

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            this.firstVisibleItem = firstVisibleItem;
            this.visibleItemCount = visibleItemCount;
            this.totalItemCount = totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (totalItemCount == (firstVisibleItem + visibleItemCount)) {
                    mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_CLOUD_HIS_REFRESH);
                }
            }
        }
    };

    public void v_list_refresh()
    {
        adapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_ALLVIEW);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vClickBack(); }
        }
    }
}
