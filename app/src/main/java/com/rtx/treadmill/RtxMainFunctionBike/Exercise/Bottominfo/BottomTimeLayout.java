package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Bottominfo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Choice_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData.SimpleObj;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * TODO: document your custom view class.
 */
public class BottomTimeLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;
    private ButtonListener mButtonListener;

    private int COLOR_BACKGROUND = Common.Color.exercise_bottom_backgroud;

    private float fscale = 1f;

    private RtxDoubleStringView[] t_data ;
    private RtxDoubleStringView[] t_title ;

    SimpleObj[] ArrayObj;
    private int imax_item = 4;

    private int icount = 0;
    private boolean bup = false;
    private boolean bdwon = false;
    private int itimes = 1;

    private int itarget_mode = 1; //1 time; 2 distance; 3 calories; 4 heartrate
    private RtxImageView i_up;
    private RtxImageView i_down;
    private RtxDoubleStringView t_target;
    private RtxTextView t_target_title;

    private int i_item_data[][] = {//item , choice , icon, arrow
            {0, 0, R.drawable.hrc_heart         , 0 }, //HeartRate
            {1, 0, R.drawable.distance_01       , 0 }, //Pace and Distance
            {2, 0, R.drawable.tr_calories_icon  , 0 }, //Calories
            {3, 0, R.drawable.tr_time_icon      , 0 }, //Time

    };

    private int i_choice_list[][] = {//item choice name for normal
            {R.string.heart_rate, R.string.avg_heart_rate},
            {R.string.distance, R.string.speed, R.string.avg_speed, R.string.rpm, R.string.watt},
            {R.string.calories, R.string.calories_hour, R.string.mets},
            {R.string.ellipse_time},
    };

    // 0x10 distance ; 0x11 pace ; 0x12 best pace ; 0x13 avg pace ; 0x14 speed ; 0x15 avg speed ; 0x16 watt; 0x17 RPM; 0x18 Ave RPM; 0x19 Max RPM
    // 0x20 calories ; 0x21 kcal/h ; 0x22 mets
    // 0x30 heartrate ; 0x31 max hr ; 0x32 avg hr
    // 0x40 elapsed time ; 0x41 remanining time
    private int i_choice_val[][] = {//item choice value for normal
            {0x30, 0x31, 0x32 },
            {0x10, 0x14, 0x15, 0x17, 0x16 },
            {0x20, 0x21, 0x22 },
            {0x40},
    };

    private int[] i_item = new int[imax_item];
    private int[] i_item_icon = new int[imax_item];
    private int[] i_choice = new int[imax_item];
    private int[] i_arrow = new int[imax_item];

    public BottomTimeLayout(Context context, MainActivity mMainActivity){
        super(context);
        this.mMainActivity = mMainActivity;

        mContext = context;

        setBackgroundColor(COLOR_BACKGROUND);

        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }

        init_View();
        init_Event();

    }

    public void init(float fscale)
    {

        itarget_mode = ExerciseGenfunc.iGet_target_mode();

        this.fscale = fscale;

        add_View();

    }

    ///////////////////////////////////////
    private void init_View() {
        int iLoop;

        if(t_data == null) {
            t_data = new RtxDoubleStringView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++)
            {
                t_data[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(t_title == null) {
            t_title = new RtxDoubleStringView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++)
            {
                t_title[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(ArrayObj == null) {
            ArrayObj = new SimpleObj[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                ArrayObj[iLoop] = new SimpleObj();
            }
        }

        if(i_up == null) { i_up = new RtxImageView(mContext);}
        if(i_down == null) { i_down = new RtxImageView(mContext);}
        if(t_target == null) { t_target = new RtxDoubleStringView(mContext);}
        if(t_target_title == null) { t_target_title = new RtxTextView(mContext);}

        vCheck_item_size();

        vUpdate_ArrayObjs();

    }

    private void init_Event()
    {
        int iLoop;

        for (iLoop = 0; iLoop < imax_item; iLoop++)
        {
            t_title[iLoop].setOnClickListener(mButtonListener);
        }

        i_up.setOnClickListener(mButtonListener);
        i_down.setOnClickListener(mButtonListener);

        i_up.setOnTouchListener(upTouch);
        i_down.setOnTouchListener(downTouch);

    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        int iLoop;
        String sdata;
        int ipadding = 20;

        ix = iGet_Scale(0);
        iy = iGet_Scale(30);
        ih = iGet_Scale(60);
        fsize = fGet_Scale(50f);
        fsize_unit = fGet_Scale(22f);
        for(iLoop = 0; iLoop < imax_item; iLoop++) {
            if(iLoop == 0) {//By Alan
                iw = iGet_Scale(270);//By Alan
            }
            else {
                iw = iGet_Scale(230);//By Alan
            }
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(iGet_Scale(20));
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.exercise_word_blue, fsize_unit);
            t_data[iLoop].setText("", "");
            ix += iw;
        }

        ix = iGet_Scale(70);//By Alan
        iy = iGet_Scale(90);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(20f);//By Alan
        fsize_unit = fGet_Scale(12f);
        for(iLoop = 0; iLoop < imax_item; iLoop++) {
            if(iLoop == 3) {
                iw = iGet_Scale(270);
            }
            else {
                iw = iGet_Scale(220);
            }
            addPaddingViewToLayout(t_title[iLoop], ix, iy, iw, ih, ipadding);
            t_title[iLoop].setGap(iGet_Scale(20));
            t_title[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
            t_title[iLoop].setText("", "");

            ArrayObj[iLoop].ixcenter = ix + iw/2;
            ix += iw;
        }

        ix = iGet_Scale(1200);
        iy = iGet_Scale(30);
        iw = iGet_Scale(60);
        ih = iGet_Scale(60);
        addRtxImage(null, i_up, R.drawable.triangle_up, ix, iy, iw, ih, 20, ImageView.ScaleType.FIT_CENTER);

        ix = iGet_Scale(950);
        iy = iGet_Scale(30);
        iw = iGet_Scale(60);
        ih = iGet_Scale(60);
        addRtxImage(null, i_down, R.drawable.triangle_down, ix, iy, iw, ih, 20, ImageView.ScaleType.FIT_CENTER);

        ix = iGet_Scale(1020);
        iy = iGet_Scale(34);
        iw = iGet_Scale(170);
        ih = iGet_Scale(50);
        fsize = fGet_Scale(50f);
        fsize_unit = fGet_Scale(22f);
        addRtxDoubleStringView(t_target, ix, iy, iw, ih);
        t_target.setGap(iGet_Scale(20));
        t_target.setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_yellow, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
        t_target.setText("", "");

        ix = iGet_Scale(1000);
        iy = iGet_Scale(84);
        iw = iGet_Scale(210);
        ih = iGet_Scale(50);
        fsize = fGet_Scale(20f);
        sdata = LanguageData.s_get_string(mContext, R.string.target_time);
        addRtxTextView(null, t_target_title, sdata.toUpperCase(), fsize, Common.Color.exercise_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        vUpdate_Data();

    }

    ////////////////////////////////////////////////////////////
    public void vUpdate_Data()
    {
        int iLoop;
        SimpleObj Obj;
        String[] sdata = new String[2];

        for(iLoop = 0; iLoop < imax_item; iLoop++)
        {
            Obj = ArrayObj[iLoop];
            if(Obj != null) {
                ExerciseGenfunc.v_simple_refresh(mContext, Obj);
                t_data[iLoop].setText(Obj.sVal, Obj.sUnit);

                sdata[0] = Obj.sTitle;
                if (iLoop >= 0) {
                    if(Obj.i_item_type == 1)
                    {
                        sdata[1] = "▼";
                    }
                    else
                    {
                        sdata[1] = "";
                    }
                    t_title[iLoop].setText(sdata[0].toUpperCase(), sdata[1]);
                }

            }
        }

        sdata = ExerciseGenfunc.s_Get_target(mContext, itarget_mode);
        t_target.setText(sdata[0], sdata[1]);

    }

    public void Refresh()
    {
        vUpdate_Data();

        v_target_long_click();
    }

    ////////////////////////////////////////////////////////////
    private void vCheck_item_size( )
    {
        int iLoop ;

        for (iLoop = 0; iLoop < imax_item; iLoop++) {
            i_item[iLoop] = i_item_data[iLoop][0];
            i_choice[iLoop] = i_item_data[iLoop][1];
            i_item_icon[iLoop] = i_item_data[iLoop][2];
            i_arrow[iLoop] = i_item_data[iLoop][3];
        }

        return ;
    }

    private void vUpdate_ArrayObjs()
    {
        int iLoop;

        for(iLoop = 0; iLoop < imax_item; iLoop++)
        {
            ArrayObj[iLoop].i_Item = i_item[iLoop];
            ArrayObj[iLoop].icon_id = i_item_icon[iLoop];
            ArrayObj[iLoop].sVal = "";
            ArrayObj[iLoop].sUnit = "";
            ArrayObj[iLoop].sTitle = LanguageData.s_get_string(mContext, i_choice_list[i_item[iLoop]][i_choice[iLoop]]);
            ArrayObj[iLoop].iChoice = i_choice[iLoop];
            ArrayObj[iLoop].iArrow = i_arrow[iLoop];
            ArrayObj[iLoop].i_item_list = i_choice_list[iLoop];
            ArrayObj[iLoop].i_val_list = i_choice_val[iLoop];
            if(i_choice_val[iLoop].length > 1) {
                ArrayObj[iLoop].i_item_type = 1;
            }
            else
            {
                ArrayObj[iLoop].i_item_type = 0;
            }
        }
    }

    private int iGet_Scale(int input)
    {
        int ival = (int)(input * fscale);

        if(ival <= 0)
        {
            ival = 1;
        }

        return ival;
    }

    private float fGet_Scale(float finput)
    {
        float fval = finput * fscale;

        if(fval <= 0)
        {
            fval = 1f;
        }

        return fval;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////
    private void item_choice_show(int iLoop)
    {
        int ixshift ;
        int iy_bot = iGet_Scale(30);
        float fsize = fGet_Scale(22f);
        SimpleObj obj = ArrayObj[iLoop];

        if(obj != null && obj.i_item_type != 0) {
            ixshift = obj.ixcenter;
            Choice_UI_Info.vSet_Choice(ixshift, iy_bot, fsize, obj);
        }
    }

    private View.OnTouchListener upTouch = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View arg0, MotionEvent motionEvent) {
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    ExerciseGenfunc.vTarget_up(itarget_mode, 1);
                    icount = 0;
                    bup = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                default:
                    bup = false;
                    break;

            }

            return false;
        }
    };

    private View.OnTouchListener downTouch = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View arg0, MotionEvent motionEvent) {
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    ExerciseGenfunc.vTarget_down(itarget_mode, 1);
                    icount = 0;
                    bdwon = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                default:
                    bdwon = false;
                    break;
            }

            return false;
        }
    };

    private void v_target_long_click()
    {
        int idelay = 1000 / (EngSetting.DEF_UNIT_TIME * EngSetting.DEF_EXERCISE_BOTTOM_REFRESH);
        int itimes_max = 10;

        if (bup || bdwon) {
            if(icount >= idelay) {
                itimes = icount / idelay;
                if(itimes > itimes_max)
                {
                    itimes = itimes_max;
                }
                if (bup) {
                    ExerciseGenfunc.vTarget_up(itarget_mode, itimes);
                } else if (bdwon) {
                    ExerciseGenfunc.vTarget_down(itarget_mode, itimes);
                }
            }
            icount++;
        }

    }

    class ButtonListener implements ImageButton.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == i_up)
            {
//                Exercisefunc.vTarget_up(itarget_mode, 1);
            }
            else if (v == i_down)
            {
//                Exercisefunc.vTarget_down(itarget_mode, 1);
            }
            else
            {
                for (int iLoop = 0; iLoop < imax_item; iLoop++)
                {
                    if(v == t_title[iLoop])
                    {
                        item_choice_show(iLoop);
                    }
                }
            }
        }
    }

}
