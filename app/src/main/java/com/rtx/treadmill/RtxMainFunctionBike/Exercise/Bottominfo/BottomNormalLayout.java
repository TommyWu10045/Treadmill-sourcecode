package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Bottominfo;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.rtx.treadmill.GlobalData.Choice_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.ExerciseData.SimpleObj;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;

/**
 * TODO: document your custom view class.
 */
public class BottomNormalLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;
    private ButtonListener mButtonListener;

    private int COLOR_BACKGROUND = Common.Color.exercise_bottom_backgroud;

    private float fscale = 1f;

    private RtxDoubleStringView[] t_data ;
    private RtxDoubleStringView[] t_title ;

    SimpleObj[] ArrayObj;
    private int imax_item = 5;

    private int icount;

    private int i_item_data[][] = {//item , choice , icon, arrow
            {0, 0, R.drawable.distance_01       , 0 }, //Pace
            {1, 0, R.drawable.distance_01       , 0 }, //Distance
            {2, 0, R.drawable.tr_time_icon      , 0 }, //Time
            {3, 0, R.drawable.tr_calories_icon  , 0 },  //Calories
            {4, 0, R.drawable.hrc_heart         , 0 }  //HeartRate
    };

    private int i_choice_list[][] = {//item choice name for normal
            {R.string.speed, R.string.avg_speed, R.string.rpm, R.string.watt},
            {R.string.distance},
            {R.string.ellipse_time,R.string.remaining_time},
            {R.string.calories, R.string.calories_hour, R.string.mets},
            {R.string.heart_rate, R.string.avg_heart_rate}
    };

    // 0x10 distance ; 0x11 pace ; 0x12 best pace ; 0x13 avg pace ; 0x14 speed ; 0x15 avg speed ; 0x16 watt; 0x17 RPM; 0x18 Ave RPM; 0x19 Max RPM
    // 0x20 calories ; 0x21 kcal/h ; 0x22 mets
    // 0x30 heartrate ; 0x31 max hr ; 0x32 avg hr
    // 0x40 elapsed time ; 0x41 remanining time
    private int i_choice_val[][] = {//item choice value for normal
            {0x14, 0x15, 0x17, 0x16 },
            {0x10 },
            {0x40, 0x41},
            {0x20, 0x21, 0x22 },
            {0x30, 0x32 }
    };

    private int[] i_item = new int[imax_item];
    private int[] i_item_icon = new int[imax_item];
    private int[] i_choice = new int[imax_item];
    private int[] i_arrow = new int[imax_item];

    public BottomNormalLayout(Context context, MainActivity mMainActivity){
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
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        int iLoop;
        int ipadding = 20;

        ix = iGet_Scale(20);
        iy = iGet_Scale(30);
        ih = iGet_Scale(60);
        fsize = fGet_Scale(50f);
        fsize_unit = fGet_Scale(22f);
        for(iLoop = 0; iLoop < imax_item; iLoop++) {
            if(iLoop == 2) {
                iw = iGet_Scale(230);//By Alan
            }
            else {
                iw = iGet_Scale(240);
            }
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(iGet_Scale(20));
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.exercise_word_blue, fsize_unit);
            t_data[iLoop].setText("", "");
            ix += iw;
        }

        ix = iGet_Scale(20);
        iy = iGet_Scale(90);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(20f);
        fsize_unit = fGet_Scale(12f);
        for(iLoop = 0; iLoop < imax_item; iLoop++) {
            if(iLoop == 2) {
                iw = iGet_Scale(230);//By Alan
            }
            else {
                iw = iGet_Scale(240);
            }
            addPaddingViewToLayout(t_title[iLoop], ix, iy, iw, ih, ipadding);
            t_title[iLoop].setGap(iGet_Scale(20));
            t_title[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
            t_title[iLoop].setText("", "");

            ArrayObj[iLoop].ixcenter = ix + iw/2;
            ix += iw;
        }

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

    }

    public void Refresh()
    {
        vUpdate_Data();

        icount++;
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

    class ButtonListener implements ImageButton.OnClickListener
    {
        @Override
        public void onClick(View v) {
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
