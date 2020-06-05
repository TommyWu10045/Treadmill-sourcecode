package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.TutorialView.RtxTutorialBaseLayout;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_BodyManagerPage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_FistPage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_GymPage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_LastPage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_PerformancePage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_TargetTrainPage;
import com.rtx.treadmill.TutorialView.RtxTutorialLayout_WorkoutPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Tutorial_Layout extends Rtx_BaseLayout {

    private Context mContext;

    protected OnDismissListener mOnDismissListener;

    private List<RtxTutorialBaseLayout> pageList;

    private RtxViewPager          viewPager_Tutotial;

    private RtxTutorialLayout_FistPage          mRtxTutorialLayout_FistPage;
    private RtxTutorialLayout_TargetTrainPage   mRtxTutorialLayout_TargetTrainPage;
    private RtxTutorialLayout_PerformancePage   mRtxTutorialLayout_PerformancePage;
    private RtxTutorialLayout_BodyManagerPage   mRtxTutorialLayout_BodyManagerPage;
    private RtxTutorialLayout_GymPage           mRtxTutorialLayout_GymPage;
    private RtxTutorialLayout_WorkoutPage       mRtxTutorialLayout_WorkoutPage;
    private RtxTutorialLayout_LastPage          mRtxTutorialLayout_LastPage;

    public RtxDialog_Tutorial_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

    }

    private void initPage() {

        if(mRtxTutorialLayout_FistPage == null)         { mRtxTutorialLayout_FistPage = new RtxTutorialLayout_FistPage(mContext); }
        if(mRtxTutorialLayout_TargetTrainPage == null)  { mRtxTutorialLayout_TargetTrainPage = new RtxTutorialLayout_TargetTrainPage(mContext); }
        if(mRtxTutorialLayout_PerformancePage == null)  { mRtxTutorialLayout_PerformancePage = new RtxTutorialLayout_PerformancePage(mContext); }
        if(mRtxTutorialLayout_BodyManagerPage == null)  { mRtxTutorialLayout_BodyManagerPage = new RtxTutorialLayout_BodyManagerPage(mContext); }
        if(mRtxTutorialLayout_GymPage == null)          { mRtxTutorialLayout_GymPage = new RtxTutorialLayout_GymPage(mContext); }
        if(mRtxTutorialLayout_WorkoutPage == null)      { mRtxTutorialLayout_WorkoutPage = new RtxTutorialLayout_WorkoutPage(mContext); }
        if(mRtxTutorialLayout_LastPage == null)
        {
            mRtxTutorialLayout_LastPage = new RtxTutorialLayout_LastPage(mContext);
            mRtxTutorialLayout_LastPage.textView_Start.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    vDismiss();
                }
            });
        }

        mRtxTutorialLayout_FistPage.display();
        mRtxTutorialLayout_TargetTrainPage.display();
        mRtxTutorialLayout_PerformancePage.display();
        mRtxTutorialLayout_BodyManagerPage.display();
        mRtxTutorialLayout_GymPage.display();
        mRtxTutorialLayout_WorkoutPage.display();
        mRtxTutorialLayout_LastPage.display();

        pageList = new ArrayList<>();
        pageList.add(mRtxTutorialLayout_FistPage);
        pageList.add(mRtxTutorialLayout_TargetTrainPage);
        pageList.add(mRtxTutorialLayout_PerformancePage);
        pageList.add(mRtxTutorialLayout_BodyManagerPage);
        pageList.add(mRtxTutorialLayout_GymPage);
        pageList.add(mRtxTutorialLayout_WorkoutPage);
        pageList.add(mRtxTutorialLayout_LastPage);
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();

        initPage();
        viewPager_Tutotial.setAdapter(new pagerAdapter_Tutorial());
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            if(viewPager_Tutotial == null)       { viewPager_Tutotial = new RtxViewPager(mContext);   }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(viewPager_Tutotial,0,0,1280,800);
    }

    private class pagerAdapter_Tutorial extends PagerAdapter
    {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void vDismiss()
    {
        if(mOnDismissListener != null)
        {
            mOnDismissListener.onDismiss();
        }
    }

    public void setOnDismissListener(RtxDialog_Tutorial_Layout.OnDismissListener l) {
        mOnDismissListener = l;
    }

    public interface OnDismissListener
    {
        public abstract void onDismiss();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
