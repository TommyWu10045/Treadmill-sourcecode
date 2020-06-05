package com.rtx.treadmill.RtxMainFunction.Exercise.Bottominfo;

import android.content.Context;

import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;

/**
 * TODO: document your custom view class.
 */
public class BottomLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private int COLOR_BACKGROUND = Common.Color.exercise_transparent;

    private MainActivity        mMainActivity;
    private Context mContext;

    private int itarget_mode;
    private int icount;

    private BottomNormalLayout mBottomNormalLayout;
    private BottomTimeLayout mBottomTimeLayout;
    private BottomDistanceLayout mBottomDistanceLayout;
    private BottomCaloriesLayout mBottomCaloriesLayout;
    private BottomHeartRateLayout mBottomHeartRateLayout;
    private BottomQuickStartLayout mBottomQuickStartLayout;

    private float fscale = 1f;

    public BottomLayout(Context context, MainActivity mMainActivity){
        super(context);

        this.mMainActivity = mMainActivity;

        mContext = context;

        setBackgroundColor(COLOR_BACKGROUND);
        this.setClickable(false);

        init_View();
    }

    @Override
    public void init()
    {
        itarget_mode = ExerciseGenfunc.iGet_target_mode();
    }

    @Override
    public void display() {
        init_View();
        init_CustomerView();    //Let user can override this.
        init_Event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View() {
        if(mBottomNormalLayout == null)              { mBottomNormalLayout = new BottomNormalLayout(mMainActivity.mContext , mMainActivity); }
        if(mBottomTimeLayout == null)                { mBottomTimeLayout = new BottomTimeLayout(mMainActivity.mContext , mMainActivity); }
        if(mBottomDistanceLayout == null)            { mBottomDistanceLayout = new BottomDistanceLayout(mMainActivity.mContext , mMainActivity); }
        if(mBottomCaloriesLayout == null)            { mBottomCaloriesLayout = new BottomCaloriesLayout(mMainActivity.mContext , mMainActivity); }
        if(mBottomHeartRateLayout == null)           { mBottomHeartRateLayout = new BottomHeartRateLayout(mMainActivity.mContext , mMainActivity); }
        if(mBottomQuickStartLayout == null)           { mBottomQuickStartLayout = new BottomQuickStartLayout(mMainActivity.mContext , mMainActivity); }

    }

    private void init_Event()
    {

    }

    private void add_View()
    {
        int ix, iy, iw, ih;

        ix = 0;
        iy = 640;
        iw = 1280;
        ih = 160;

        switch (itarget_mode)
        {
            case 0: //normal
            case 7: //tr_manual
                addViewToLayout(mBottomNormalLayout, ix, iy, iw, ih);
                mBottomNormalLayout.init(fscale);
                break;
            case 1: //time
                addViewToLayout(mBottomTimeLayout, ix, iy, iw, ih);
                mBottomTimeLayout.init(fscale);
                break;
            case 2: //distance
                addViewToLayout(mBottomDistanceLayout, ix, iy, iw, ih);
                mBottomDistanceLayout.init(fscale);
                break;
            case 3: //calories
                addViewToLayout(mBottomCaloriesLayout, ix, iy, iw, ih);
                mBottomCaloriesLayout.init(fscale);
                break;
            case 4: //heartrate
                addViewToLayout(mBottomHeartRateLayout, ix, iy, iw, ih);
                mBottomHeartRateLayout.init(fscale);
                break;
            case 5: //normal no remaining time
                addViewToLayout(mBottomQuickStartLayout, ix, iy, iw, ih);
                mBottomQuickStartLayout.init(fscale);
                break;
            default:
                break;
        }
    }

    //////////////////////////////////////////////////////////////////////
    //overide area
    protected void init_CustomerView()
    {
        //Let user can override this.
    }

    protected void init_CustomerEvent()
    {
        //Let user can override this.
    }

    protected void add_CustomerView()
    {
        //Let user can override this.
    }

    /////////////////////////////////////////////////////////////////////
    public void vSet_Scale(float fscale)
    {
        this.fscale = fscale;
    }

    public void vUpdate_Data()
    {
        switch (itarget_mode)
        {
            case 0: //normal
            case 7: //tr_manual
                if(mBottomNormalLayout != null) {
                    mBottomNormalLayout.Refresh();
                }
                break;
            case 1: //time
                if(mBottomTimeLayout != null) {
                    mBottomTimeLayout.Refresh();
                }
                break;
            case 2: //distance
                if(mBottomDistanceLayout != null) {
                    mBottomDistanceLayout.Refresh();
                }
                break;
            case 3: //calories
                if(mBottomCaloriesLayout != null) {
                    mBottomCaloriesLayout.Refresh();
                }
                break;
            case 4: //heartrate
                if(mBottomHeartRateLayout != null) {
                    mBottomHeartRateLayout.Refresh();
                }
                break;
            case 5: //normal no remaining time
                if(mBottomQuickStartLayout != null) {
                    mBottomQuickStartLayout.Refresh();
                }
                break;
            default:
                Bottom_UI_Info.clear();
                break;
        }
    }

    public void Refresh()
    {
        if(icount % EngSetting.DEF_EXERCISE_BOTTOM_REFRESH == 0)
        {
            vUpdate_Data();
        }

        icount++;

    }
}
