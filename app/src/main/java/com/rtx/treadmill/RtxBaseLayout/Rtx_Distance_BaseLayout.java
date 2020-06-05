package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDisSliderView;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxRecPercent;


/**
 *
 ************************************************************
 * Created by chasechang on 3/22/17.
 ************************************************************
 * User Must to Override Function:
 *
 * public void vClickButton_Back()
 * public void vClickButton_Confirm()
 ************************************************************
 * User Could Use Function
 *
 * public void  setCurrentVal_Metric(float fVal)
 * public float getCurrentVal_Metric()
 ************************************************************
 * User Could Override Function:
 *
 * protected void init_CustomerView();
 * protected void init_CustomerEvent();
 * protected void add_CustomerView();
 * protected void onCustomerClick(View v)
 *************************************************************
 *
 */


public class Rtx_Distance_BaseLayout extends Rtx_BaseLayout {
    private     Context mContext;

    protected     ButtonListener      mButtonListener;

    public RtxImageView i_Confirm;

    RtxDoubleStringView t_ProgressVal;
    RtxRecPercent mView_RecPercent;
    RtxDisSliderView mView_Slider;


    private float fslow = 100;

    private float fdis_min = 1f;
    private float fdis_max = 99f;
    private float fdef_val = 3.0f;
    private float fdistance = fdef_val;

    private float fdis_step = 1f; // 上方數值單位
    private int ipercent_def = 50;

    private float flast_acc = 0;
    private float flast_speed = 0;

    public Rtx_Distance_BaseLayout(Context context) {
        super(context);

        mContext = context;
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
        init_CustomerView();    //Let user can override this.
        init_Event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.

        vRefreshdata();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        flast_speed = 0;

        init_BackPrePage();
        init_Title();

        if(i_Confirm == null)               { i_Confirm = new RtxImageView(mContext); }
        if(t_ProgressVal == null)    { t_ProgressVal = new RtxDoubleStringView(mContext); }
        if(mView_RecPercent == null)                { mView_RecPercent = new RtxRecPercent(mContext); }
        if(mView_Slider == null)                { mView_Slider = new RtxDisSliderView(mContext); }

    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_Confirm.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int ix;
        int iy;
        int iw;
        int ih;
        int iLoop;
        float fsize = 44f;
        float fsize_unit = 30f;
        int iGap = 20;

        addViewToLayout(t_ProgressVal, -1, 195 - 100, 1280, 141 + 200);
        t_ProgressVal.setPaint(Common.Font.Relay_Black, Common.Color.white, 195.6f, Common.Font.Relay_BoldItalic, Common.Color.blue_1, 62.46f);
        t_ProgressVal.setGap(35);

        ix = 0;
        iy = 420;
        iw = 1280;
        ih = 120;
        mView_RecPercent.setBackColor(Common.Color.gray);
        mView_RecPercent.setFrontColor(Common.Color.blue_1);
        mView_RecPercent.setPointerColor(Common.Color.yellow_1);
        mView_RecPercent.setPointerRadiusd(ih / 2);
        mView_RecPercent.setPointerEnabled(true);
        mView_RecPercent.setTouchEnabled(true);
        mView_RecPercent.setPointSel_Check(false);
        mView_RecPercent.setPercent_def(ipercent_def);
        addViewToLayout(mView_RecPercent, ix, iy, iw, ih);

        mView_RecPercent.setOnChangeListener(touchlister);

        ix = 0;
        iy = 550;
        iw = 1280 ;
        ih = 70;
        addViewToLayout(mView_Slider, ix, iy, iw, ih);
        mView_Slider.setPaint(Common.Font.Relay_Black, Common.Color.white, fsize, Common.Font.Relay_Black, Common.Color.white, fsize_unit);
        mView_Slider.setDef(EngSetting.Distance.getUnitString(mContext), 0, 5, fdis_min, fdis_max);
        addRtxImageViewToLayout(i_Confirm, R.drawable.icon_confirm,1097,628,133,133);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void vClickButton_Back()
    {

    }

    public void vClickButton_Confirm()
    {

    }

    protected void onCustomerClick(View v)
    {

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vClickButton_Back(); }
            else if(v == i_Confirm)             { vClickButton_Confirm(); }
            else                                { onCustomerClick(v); }
        }
    }

    private RtxRecPercent.OnChangeListener touchlister  = new RtxRecPercent.OnChangeListener() {
        @Override
        public void onChange() {
            int iPercent = i_Get_TouchDistance();
            final float fxPixel = 1 * ((float) iPercent - 50);
            //if(fxPixel != 0 )
            {
                mView_RecPercent.post(new Runnable() {
                    @Override
                    public void run() {
                        v_Refresh_data(fxPixel);
                    }
                });
            }
        }

        @Override
        public void onChange_finish(float fspeed) {
            v_Shift_Slide_bar_up(fspeed);
        }
    };

    public void v_Slide_bar_refresh()
    {
        float fsd;
        float ftimes = 1f;
        float fpixel_sub = 100f;
        float fpixel_div = fpixel_sub * 3;
        float fpixel;

        if(flast_speed == 0)
        {
            return;
        }

        fsd = Math.abs(flast_speed * ftimes);

        if(fsd <= fpixel_sub) {
            v_Shift_Slide_bar_stop();
            flast_speed = 0;
        }
        else
        {
            fpixel = -flast_speed / fpixel_div;

            v_Refresh_data(fpixel);

            if(flast_speed > 0) {
                flast_speed -= fpixel_sub;
            }
            else
            {
                flast_speed += fpixel_sub;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_Shift_Slide_bar_up(float fspeed)
    {
        float fsd;
        float ftimes = 1f;

        //Log.e("Jerry", "fspeed=" + fspeed );
        fsd = Math.abs(fspeed * ftimes);

        if(fsd < 1000) {
            v_Shift_Slide_bar_stop();
        }
        else
        {
            flast_speed = fspeed;
//            Log.e("Jerry", "flast_speed=" + flast_speed );
        }

    }

    private void v_Shift_Slide_bar_stop()
    {
        float fval;
        float fhalf;

        if(fdis_step < 1)
        {
            return;
        }

        fhalf = fdis_step / 2;

        if(fdistance%fdis_step > fhalf)
        {
            fval = (int)(fdistance + 1);
        }
        else
        {
            fval = (int)(fdistance);
        }

        fdistance = fval;

        if(fdistance < fdis_min) {
            fdistance = fdis_min;
        }

        if(fdistance > fdis_max) {
            fdistance = fdis_max;
        }

        mView_Slider.setVal(fdistance);

        v_set_ProgressVal(fdistance);

    }

    private void vRefreshdata() {

        if(t_ProgressVal != null)
        {
            v_set_ProgressVal(fdef_val);
        }

        fdistance = fdef_val;
        mView_Slider.setVal(fdistance);

    }

    private void v_set_ProgressVal(float fdata)
    {
        String sVal = null;
        float fval;

        if(fdis_step >= 1)
        {
            sVal = Rtx_TranslateValue.sFloat2String(fdata,0);
        }
        else
        {
            fval = fdata;
            if(fval < fdis_min)
            {
                fval = fdis_min;
            }
            if(fval > fdis_max)
            {
                fval = fdis_max;
            }

            sVal = Rtx_TranslateValue.sFloat2String(fval,1);
        }

        t_ProgressVal.setText(sVal,EngSetting.Distance.getUnitString(mContext));
    }

    private void v_Refresh_data(float fdis_pixel) {

        if (fdis_pixel == 0) {
            return;
        }

        fdistance += (fdis_pixel/fslow);
        if(fdistance < fdis_min)
        {
            fdistance = fdis_min;
        }

        if(fdistance > fdis_max)
        {
            fdistance = fdis_max;
        }

        mView_Slider.setVal(fdistance);

        v_set_ProgressVal(fdistance);
    }

    public int i_Get_TouchDistance() {
        int iPercent = 50;
        if(mView_RecPercent != null)
        {
            iPercent = mView_RecPercent.getPercent();
        }
        return iPercent;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void vDefault_init(float fmin, float fmax, float fdef, float finterval) {

        fdis_min = fmin;
        fdis_max = fmax;
        fdef_val = fdef;
        fdis_step = finterval;

        fdistance = fdef_val;
    }


    public void setCurrentVal_Metric(float fVal)
    {
        float fval;

        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            fval = fVal;
        }
        else
        {
            fval = fVal * EngSetting.km2mile;
        }

        fdistance = fVal;
        mView_Slider.setVal(fdistance);
        v_set_ProgressVal(fdistance);
    }

    public float getCurrentVal_Metric()
    {
        float fval;

        fdistance = Rtx_TranslateValue.fString2Float(t_ProgressVal.getMainText());

        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            fval = fdistance;
        }
        else
        {
            fval = fdistance * EngSetting.mile2km;
        }

        return fval;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

