package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxUpDownButton extends Rtx_BaseLayout {


    private Context mContext;

    private final float     DEF_VAL_MAX     = 20f;
    private final float     DEF_VAL_MIN     = 0f;
    private final float     DEF_VAL_UNIT    = 0.5f;
    private final float     DEF_VAL_DEFAULT = 0f;

    private final long      DEF_TIME_LEVEL = 750l;//1500l;
    private final long      DEF_TIME_BLANKING_FIRST = 250l;//500l
    private final long      DEF_TIME_BLANKING_SECOND = 100l;//200;

    //private RtxTextView  textView_Val;
    private RtxDoubleStringView doubleStringView_Val;

    private RtxImageView imageView_Up;
    private RtxImageView imageView_Down;

    private ButtonTouchListener mButtonTouchListener;

    private String sUnitText = "";

    private float fVal_Max      = DEF_VAL_MAX;
    private float fVal_Min      = DEF_VAL_MIN;
    private float fVal_Unit     = DEF_VAL_UNIT;
    private float fVal_Default  = DEF_VAL_DEFAULT;
    private float fVal          = DEF_VAL_DEFAULT;

    long long_LevelTime = DEF_TIME_LEVEL;
    long long_FirstBlankingTime = DEF_TIME_BLANKING_FIRST;
    long long_SecondBlankingTime = DEF_TIME_BLANKING_SECOND;

    private Long long_CurrentTime;
    private Long long_PreTime;
    private Long long_DownTime;

    public RtxUpDownButton(Context context)
    {
        super(context);

        mContext = context;

        init();
        display();
    }

    public RtxUpDownButton(Context context, float fMax, float fMin, float fUnit, float fDefaultVal, String sUnit)
    {
        super(context);

        mContext = context;
        fVal_Max = fMax;
        fVal_Min = fMin;
        fVal_Unit = fUnit;
        fVal_Default = fDefaultVal;
        fVal = fDefaultVal;
        sUnitText = new String(sUnit);

        init();
        display();
    }

    public void setUnitText(String sUnit)
    {
        sUnitText = new String(sUnit);
    }

    @Override
    public void init()
    {
        if(mButtonTouchListener == null)
        {
            mButtonTouchListener = new ButtonTouchListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        add_View();
        vSetValText(fVal);
    }

    private void init_View()
    {
        if(imageView_Up == null)          { imageView_Up = new RtxImageView(mContext); }
        if(imageView_Down == null)        { imageView_Down = new RtxImageView(mContext); }

        if(doubleStringView_Val == null)  { doubleStringView_Val = new RtxDoubleStringView(mContext); }
    }

    private void add_View()
    {
        //addRtxTextViewToLayout(textView_Val, -1, 92.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 0, 392, 388);
        //textView_Val.setSingleLine();

        addViewToLayout(doubleStringView_Val,0,0,392,388);
        doubleStringView_Val.setPaint(Common.Font.Relay_Black,Common.Color.white,92.33f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);
        doubleStringView_Val.setGap(25);

        addRtxImagePaddingViewToLayout(imageView_Up  , R.drawable.workout_up_button  , 144, 0  , 113, 113, 30);
        addRtxImagePaddingViewToLayout(imageView_Down, R.drawable.workout_down_button, 144, 275, 113, 113, 30);

        imageView_Up.setClickable(false);
        imageView_Down.setClickable(false);

        imageView_Up.setOnTouchListener(mButtonTouchListener);
        imageView_Down.setOnTouchListener(mButtonTouchListener);
    }

    public void clear()
    {
        fVal = fVal_Default;

        vSetVal(fVal);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public float fGetVal()
    {
        return fVal;
    }

    public void vSetVal(float val)
    {
        fVal = Rtx_TranslateValue.fRoundingVal(val,1);
        vSetValText(fVal);
    }

    private void vSetValText(float val)
    {
        //針對設定level值所做出的區別
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            doubleStringView_Val.setText(Rtx_TranslateValue.sFloat2String(val, 1),sUnitText);
        }
        else
        {
            doubleStringView_Val.setText(Rtx_TranslateValue.sFloat2String(val, 0), sUnitText);
        }
    }

    private void vIncrease()
    {
        fVal = fVal + fVal_Unit;

        if(fVal >= fVal_Max)
        {
            fVal = fVal_Max;
        }

        vSetValText(fVal);
    }

    private void vReduce()
    {
        fVal = fVal - fVal_Unit;

        if(fVal <= fVal_Min)
        {
            fVal = fVal_Min;
        }

        vSetValText(fVal);
    }

    private boolean bCheckButtonEffect(MotionEvent event)
    {
        boolean bResult = false;

        long long_BlankingTime = 0l;

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            long_DownTime = 0l;
            long_CurrentTime = 0l;
            long_PreTime = 0l;

            bResult = true;
        }
        else
        if(event.getAction() == MotionEvent.ACTION_UP)
        {

        }
        else
        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(long_PreTime == 0)
            {
                long_DownTime = event.getDownTime();
                long_PreTime = long_DownTime;
            }

            long_CurrentTime = event.getEventTime();

            if((long_CurrentTime - long_DownTime) < long_LevelTime)
            {
                long_BlankingTime = long_FirstBlankingTime;
            }
            else
            {
                long_BlankingTime = long_SecondBlankingTime;
            }

            if((long_CurrentTime - long_PreTime) > long_BlankingTime)
            {
                long_PreTime = long_CurrentTime;
                bResult = true;
            }
        }

        return bResult;
    }

    private void vTouchUpButton(MotionEvent event)
    {
        if(bCheckButtonEffect(event))
        {
            vIncrease();
        }
    }

    private void vTouchDownButton(MotionEvent event)
    {
        if(bCheckButtonEffect(event))
        {
            vReduce();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ButtonTouchListener implements OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if(v == imageView_Up)           { vTouchUpButton(event); }
            else if(v == imageView_Down)    { vTouchDownButton(event); }

            return true;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
