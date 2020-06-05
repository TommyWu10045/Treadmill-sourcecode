package com.rtx.treadmill.RtxView;

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
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseData.SimpleObj;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;

/**
 * TODO: document your custom view class.
 */
public class RtxSimple extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;
    private ButtonListener mButtonListener;

    private int COLOR_BACKGROUND = Common.Color.exercise_backgroud;

    private float fscale = 1f;

    private RtxImageView[] i_title ;
    private RtxDoubleStringView[] t_data ;
    private RtxDoubleStringView[] t_title ;
    private RtxImageView[] img_arrow ;

    private int itarget_mode = 0; //1 time; 2 distance; 3 calories; 4 heartrate; 6 watt
    private RtxImageView i_up;
    private RtxImageView i_down;
    private RtxDoubleStringView t_target;
    private RtxTextView t_target_title;
    private String s_target_title = "";

    SimpleObj[] ArrayObj;
    private int imax_item = 4;

    private int icount = 0;
    private boolean bup = false;
    private boolean bdwon = false;
    private int itimes = 1;

    //              item0
    //
    //  item1      item2      item3
    ///////////Quick Start/////////
    private int i_item_quickstart[][] = {//item , item_list choice(default) , icon, arrow for normal
            {0, 0, R.drawable.distance_01        , 1 }, //Distance
            {1, 0, R.drawable.tr_calories_icon   , 1 }, //Calories
            {2, 0, R.drawable.hrc_heart          , 1 }, //HeartRate
            {3, 0, R.drawable.tr_time_icon       , 1 }  //Time
    };

    private int i_item_list_quickstart[][] = {//item choice name for normal
            {R.string.distance, R.string.pace, R.string.avg_pace},
            {R.string.calories, R.string.calories_hour},
            {R.string.heart_rate, R.string.max_heart_rate, R.string.avg_heart_rate},
            {R.string.ellipse_time}
    };

    // 0x10 distance ; 0x11 pace ; 0x12 best pace ; 0x13 avg pace
    // 0x14 speed ; 0x15 avg speed ; 0x16 watt; 0x17 RPM; 0x18 Ave RPM; 0x19 Max RPM
    // 0x20 calories ; 0x21 kcal/h ; 0x22 mets
    // 0x30 heartrate ; 0x31 max hr ; 0x32 avg hr
    // 0x40 elapsed time ; 0x41 remanining time
    private int i_val_list_quickstart[][] = {//item choice value for normal
            {0x10, 0x11, 0x12, 0x13 },
            {0x20, 0x21 },
            {0x30, 0x31, 0x32 },
            {0x40}
    };

    ///////////normal/////////
    private int i_item_normal[][] = {//item , choice , icon, arrow for normal
            {0, 0, R.drawable.distance_01        , 1 }, //Distance
            {1, 0, R.drawable.tr_calories_icon   , 1 }, //Calories
            {2, 0, R.drawable.hrc_heart          , 1 }, //HeartRate
            {3, 0, R.drawable.tr_time_icon       , 1 }  //Time
    };

    private int i_item_list_normal[][] = {//item choice name for normal
            {R.string.ellipse_time,R.string.remaining_time},
            {R.string.distance, R.string.pace, R.string.avg_pace},
            {R.string.calories, R.string.calories_hour},
            {R.string.heart_rate, R.string.avg_heart_rate},

    };

    // 0x10 distance ; 0x11 pace ; 0x12 best pace ; 0x13 avg pace
    // 0x14 speed ; 0x15 avg speed ; 0x16 watt; 0x17 RPM; 0x18 Ave RPM; 0x19 Max RPM
    // 0x20 calories ; 0x21 kcal/h ; 0x22 mets
    // 0x30 heartrate ; 0x31 max hr ; 0x32 avg hr
    // 0x40 elapsed time ; 0x41 remanining time
    private int i_val_list_normal[][] = {//item choice value for normal
            {0x40, 0x41},
            {0x10, 0x11, 0x13 },
            {0x20, 0x21 },
            {0x30, 0x32 },

    };

    ///////////target is time/////////
    private int i_item_time[][] = {//item , choice , icon, arrow for time
            {0, 0, R.drawable.tr_time_icon       , 0 },  //Time
            {1, 0, R.drawable.distance_01        , 0 }, //Distance
            {2, 0, R.drawable.tr_calories_icon   , 0 }, //Calories
            {3, 0, R.drawable.hrc_heart          , 0 }, //HeartRate

    };

    private int i_item_list_time[][] = {//item choice name
            {R.string.ellipse_time,R.string.remaining_time},
            {R.string.distance, R.string.pace, R.string.avg_pace},
            {R.string.calories, R.string.calories_hour},
            {R.string.heart_rate, R.string.avg_heart_rate}

    };

    private int i_val_list_time[][] = {//item choice value for time
            {0x40, 0x41},
            {0x10, 0x11, 0x13 },
            {0x20, 0x21 },
            {0x30, 0x32 }

    };

    ///////////target is calories/////////
    private int i_item_calories[][] = {//item , choice , icon, arrow for calories
            {0, 0, R.drawable.tr_calories_icon   , 0 }, //Calories
            {1, 0, R.drawable.distance_01        , 0 }, //Distance
            {2, 0, R.drawable.hrc_heart          , 0 }, //HeartRate
            {3, 0, R.drawable.tr_time_icon       , 0 }  //Time
    };

    private int i_item_list_calories[][] = {//item choice name for calories
            {R.string.calories},
            {R.string.ellipse_time},
            {R.string.distance, R.string.pace, R.string.avg_pace},
            {R.string.heart_rate, R.string.avg_heart_rate}
    };

    private int i_val_list_calories[][] = {//item choice value for calories
            {0x20 },
            {0x40 },
            {0x10, 0x11, 0x13 },
            {0x30, 0x32 },

    };

    ///////////target is distance/////////
    private int i_item_distance[][] = {//item , choice , icon, arrow for distance
            {0, 0, R.drawable.distance_01        , 0 }, //Distance
            {1, 0, R.drawable.tr_calories_icon   , 0 }, //Calories
            {2, 0, R.drawable.hrc_heart          , 0 }, //HeartRate
            {3, 0, R.drawable.tr_time_icon       , 0 }  //Time
    };

    private int i_item_list_distance[][] = {//item choice name for distance
            {R.string.distance},
            {R.string.ellipse_time, R.string.pace, R.string.avg_pace},
            {R.string.calories, R.string.calories_hour},
            {R.string.heart_rate, R.string.avg_heart_rate},

    };

    private int i_val_list_distance[][] = {//item choice value for distance
            {0x10},
            {0x40, 0x11, 0x13 },
            {0x20, 0x21 },
            {0x30, 0x32 },

    };

    ///////////target is heartrate/////////
    private int i_item_heartrate[][] = {//item , choice , icon, arrow for heartrate
            {0, 0, R.drawable.hrc_heart          , 0 }, //HeartRate
            {1, 0, R.drawable.distance_01        , 0 }, //Distance
            {2, 0, R.drawable.tr_calories_icon   , 0 }, //Calories
            {3, 0, R.drawable.tr_time_icon       , 0 }  //Time
    };

    private int i_item_list_heartrate[][] = {//item choice name for heartrate
            {R.string.heart_rate},
            {R.string.distance, R.string.pace, R.string.avg_pace},
            {R.string.calories, R.string.calories_hour, R.string.mets},
            {R.string.ellipse_time,R.string.remaining_time}
    };

    private int i_val_list_heartrate[][] = {//item choice value for heartrate
            {0x30 },
            {0x10, 0x11, 0x13 },
            {0x20, 0x21, 0x22 },
            {0x40, 0x41}
    };

    private int[][] i_item_data;
    private int[][] i_choice_list;
    private int[][] i_choice_val;

    private int[] i_item = new int[imax_item];
    private int[] i_item_icon = new int[imax_item];
    private int[] i_choice = new int[imax_item];
    private int[] i_arrow = new int[imax_item];

    public RtxSimple(Context context, MainActivity mMainActivity){
        super(context);

        this.mMainActivity = mMainActivity;
        mContext = context;
        mButtonListener = new ButtonListener();

        setBackgroundColor(COLOR_BACKGROUND);

        int iLoop;

        if(i_title == null) {
            i_title = new RtxImageView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                i_title[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_data == null) {
            t_data = new RtxDoubleStringView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                t_data[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(t_title == null) {
            t_title = new RtxDoubleStringView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                t_title[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(ArrayObj == null) {
            ArrayObj = new SimpleObj[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                ArrayObj[iLoop] = new SimpleObj();
            }
        }

        if(img_arrow == null) {
            img_arrow = new RtxImageView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                img_arrow[iLoop] = new RtxImageView(mContext);
            }
        }

        if(i_up == null) { i_up = new RtxImageView(mContext);}
        if(i_down == null) { i_down = new RtxImageView(mContext);}
        if(t_target == null) { t_target = new RtxDoubleStringView(mContext);}
        if(t_target_title == null) { t_target_title = new RtxTextView(mContext);}

    }

    private void v_taget_show(boolean ben, String str, int imode)
    {

        itarget_mode = imode;

        if(ben)
        {
            i_up.setVisibility(VISIBLE);
            i_down.setVisibility(VISIBLE);
            t_target.setVisibility(VISIBLE);
            t_target_title.setVisibility(VISIBLE);
            s_target_title = str;
        }
        else
        {
            i_up.setVisibility(INVISIBLE);
            i_down.setVisibility(INVISIBLE);
            t_target.setVisibility(INVISIBLE);
            t_target_title.setVisibility(INVISIBLE);
            s_target_title = "";
        }



    }

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


    private boolean bSet_mode_style_parameter( )
    {
        boolean bret = false;
        String sdata;

        itarget_mode = ExerciseGenfunc.iGet_target_mode();
        switch (itarget_mode)
        {
            case 0: //normal
            case 7: //tr_manual
                i_item_data = i_item_normal ;
                i_choice_list = i_item_list_normal;
                i_choice_val = i_val_list_normal;
                sdata = "";
                v_taget_show(false, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 1: //time
                i_item_data = i_item_time ;
                i_choice_list = i_item_list_time;
                i_choice_val = i_val_list_time;
                sdata = LanguageData.s_get_string(mContext, R.string.target_time);
                v_taget_show(true, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 2: //distance
                i_item_data = i_item_distance ;
                i_choice_list = i_item_list_distance;
                i_choice_val = i_val_list_distance;
                sdata = LanguageData.s_get_string(mContext, R.string.target_distance);
                v_taget_show(true, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 3: //calories
                i_item_data = i_item_calories ;
                i_choice_list = i_item_list_calories;
                i_choice_val = i_val_list_calories;
                sdata = LanguageData.s_get_string(mContext, R.string.target_calories);
                v_taget_show(true, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 4: //heartrate
                i_item_data = i_item_heartrate ;
                i_choice_list = i_item_list_heartrate;
                i_choice_val = i_val_list_heartrate;
                sdata = LanguageData.s_get_string(mContext, R.string.target_hr);
                v_taget_show(true, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 5: //normal no remainint time
                i_item_data = i_item_quickstart ;
                i_choice_list = i_item_list_quickstart;
                i_choice_val = i_val_list_quickstart;
                sdata = "";
                v_taget_show(false, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            case 6: //watt
                i_item_data = i_item_calories ;
                i_choice_list = i_item_list_calories;
                i_choice_val = i_val_list_calories;
                sdata = LanguageData.s_get_string(mContext, R.string.target_watt);
                v_taget_show(true, sdata.toUpperCase(), itarget_mode);
                bret = true;
                break;
            default:
                break;
        }

        if(bret) {
            vCheck_item_size();
        }

        return bret;
    }

    private boolean bSet_mode_style( )
    {
        boolean bret = false;
        String sdata;

        itarget_mode = ExerciseGenfunc.iGet_target_mode();
        switch (itarget_mode)
        {
            case 0: //normal
            case 7: //tr_manual
                bret = true;
                break;
            case 1: //time
                bret = true;
                break;
            case 2: //distance
                bret = true;
                break;
            case 3: //calories
                bret = true;
                break;
            case 4: //heartrate
                i_up.setVisibility(INVISIBLE);
                i_down.setVisibility(INVISIBLE);
                t_target.setColor(Common.Color.exercise_word_yellow, Common.Color.exercise_word_blue);
                t_data[0].setColor(Common.Color.exercise_word_white, Common.Color.exercise_word_blue);
                bret = true;
                break;
            case 5: //normal no remainint time
                bret = true;
                break;
            case 6: //watt
                bret = true;
                break;
            default:
                break;
        }

        return bret;
    }

    public void init(float fscale)
    {
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        int ix_shift;
        int iLoop;
        int ipadding = 20;

        this.fscale = fscale;

        if(!bSet_mode_style_parameter())
        {
            return;
        }

        vUpdate_ArrayObjs();

        //iLoop 0 Obj
        iLoop = 0;
        ix = iGet_Scale(600);
        iy = iGet_Scale(30);
        iw = iGet_Scale(80);
        ih = iGet_Scale(100);
        addRtxImage(null, i_title[iLoop], -1, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_CENTER);

        ix = iGet_Scale(200);
        iy = iGet_Scale(130);
        iw = iGet_Scale(880);
        ih = iGet_Scale(140);
        fsize = fGet_Scale(130f);
        fsize_unit = fGet_Scale(52.55f);
        addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
        t_data[iLoop].setGap(iGet_Scale(46));
        t_data[iLoop].vsetArrow(false, iGet_Scale(40));
        t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_yellow, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
        t_data[iLoop].setText("", "");

        ix = iGet_Scale(200);
        iy = iGet_Scale(270);
        iw = iGet_Scale(880);
        ih = iGet_Scale(90);
        fsize = fGet_Scale(52.55f);
        fsize_unit = fGet_Scale(30f);
        addPaddingViewToLayout(t_title[iLoop], ix, iy, iw, ih, ipadding);
        t_title[iLoop].setGap(iGet_Scale(50));
        t_title[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
        t_title[iLoop].setText("", "");

        //iLoop 1~3 Obj
        ix = iGet_Scale(257);
        iy = iGet_Scale(437);
        iw = iGet_Scale(54);
        ih = iGet_Scale(66);
        ix_shift = iGet_Scale(360);
        for(iLoop = 1; iLoop < imax_item; iLoop++) {
            addRtxImage(null, i_title[iLoop], -1, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_CENTER);
            ix += ix_shift;
        }

        ix = iGet_Scale(0);
        iy = iGet_Scale(515);
        iw = iGet_Scale(560);
        ih = iGet_Scale(85);
        fsize = fGet_Scale(66.67f);
        fsize_unit = fGet_Scale(22f);
        for(iLoop = 1; iLoop < imax_item; iLoop++) {
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(iGet_Scale(20));
            t_data[iLoop].vsetArrow(i_arrow[iLoop] == 1 ? true : false, iGet_Scale(40));
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_white, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
            t_data[iLoop].setText("", "");
            ix += ix_shift;
        }

        ix = iGet_Scale(150);
        iy = iGet_Scale(600);
        iw = iGet_Scale(260);
        ih = iGet_Scale(50);
        fsize = fGet_Scale(20f);
        fsize_unit = fGet_Scale(12f);
        for(iLoop = 1; iLoop < imax_item; iLoop++) {
            addPaddingViewToLayout(t_title[iLoop], ix, iy, iw, ih, ipadding);
            t_title[iLoop].setGap(iGet_Scale(20));
            t_title[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
            t_title[iLoop].setText("", "");
            ix += ix_shift;
        }

        //Target Obj
        ix = iGet_Scale(1080);
        iy = iGet_Scale(90);
        iw = iGet_Scale(60);
        ih = iGet_Scale(60);
        addRtxImage(null, i_up, R.drawable.triangle_up_blue, ix, iy, iw, ih, 20, ImageView.ScaleType.FIT_CENTER);

        ix = iGet_Scale(1080);
        iy = iGet_Scale(300);
        iw = iGet_Scale(60);
        ih = iGet_Scale(60);
        addRtxImage(null, i_down, R.drawable.triangle_down_blue, ix, iy, iw, ih, 20, ImageView.ScaleType.FIT_CENTER);

        ix = iGet_Scale(940);
        iy = iGet_Scale(180);
        iw = iGet_Scale(340);
        ih = iGet_Scale(60);
        fsize = fGet_Scale(50f);
        fsize_unit = fGet_Scale(20f);
        addRtxDoubleStringView(t_target, ix, iy, iw, ih);
        t_target.setGap(iGet_Scale(20));
        t_target.setPaint(Common.Font.Relay_Black, Common.Color.exercise_word_white, fsize, Common.Font.Relay_Black, Common.Color.exercise_word_blue, fsize_unit);
        t_target.setText("", "");

        ix = iGet_Scale(940);
        iy = iGet_Scale(240);
        iw = iGet_Scale(340);
        ih = iGet_Scale(40);
        fsize = fGet_Scale(20f);
        addRtxTextView(null, t_target_title, s_target_title.toUpperCase(), fsize, Common.Color.exercise_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        vUpdate_Data();

        for (iLoop = 0; iLoop < imax_item; iLoop++) {
            img_arrow[iLoop].setOnClickListener(mButtonListener);
            t_title[iLoop].setOnClickListener(mButtonListener);
        }

        i_up.setOnClickListener(mButtonListener);
        i_down.setOnClickListener(mButtonListener);

        i_up.setOnTouchListener(upTouch);
        i_down.setOnTouchListener(downTouch);

        bSet_mode_style( );
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

    private void v_arrow_shift(int iLoop)
    {
        int ix, iy, iw, ih;
        int ix_shift;
        int ix_offset;

        ix_offset = t_data[iLoop].i_Get_Xshift();

        ix = iGet_Scale(0);
        iy = iGet_Scale(540);
        iw = iGet_Scale(30);
        ih = iGet_Scale(30);
        ix_shift = iGet_Scale(360);

        ix += ix_shift * (iLoop - 1) + ix_offset;

        addRtxImage(null, img_arrow[iLoop], R.drawable.arrow_up_white, ix, iy , iw, ih, iGet_Scale(20), ImageView.ScaleType.FIT_XY);
        img_arrow[iLoop].setVisibility(VISIBLE);
    }

    public void vUpdate_Data()
    {
        int iLoop;
        SimpleObj Obj;
        String sdata, sunit;

        for(iLoop = 0; iLoop < imax_item; iLoop++)
        {
            Obj = getSimpleObj(iLoop);
            if(Obj != null) {
                ExerciseGenfunc.v_simple_refresh(mContext, Obj);
                if(iLoop == 0)
                {
                    if((Obj.i_val_list[Obj.iChoice] & 0x30) == 0x30)
                    {
                        Obj.sUnit = LanguageData.s_get_string(mContext, R.string.bpm).toLowerCase();
                    }
                }
                t_data[iLoop].setText(Obj.sVal, Obj.sUnit);

                sdata = Obj.sTitle;
                sunit = "";
                if (iLoop >= 0) {
                    if(iLoop > 0) {
                        if(Obj.icon_id != -1) {
                            i_title[iLoop].setImageResource(Obj.icon_id);
                        }
                    }
                    if(Obj.i_item_type == 1)
                    {
                        sunit = "▼";
                    }

                    if(Obj.iArrow == 1) {
                        v_arrow_shift(iLoop);
                    }
                    else
                    {
                        img_arrow[iLoop].setVisibility(INVISIBLE);
                    }
                }
                t_title[iLoop].setText(sdata.toUpperCase(), sunit);
            }
        }

        v_simple_target_refresh();
    }

    public SimpleObj getSimpleObj(int iIndex)
    {
        SimpleObj obj = null;
        int iLoop;

        for(iLoop = 0; iLoop < imax_item; iLoop++) {
            if(ArrayObj[iLoop].i_Item == iIndex)
            {
                obj = ArrayObj[iLoop];
                break;
            }
        }

        return obj;
    }

    private void v_simple_target_refresh()
    {
        String[] sdata ;

        v_target_long_click();

        //1 time; 2 distance; 3 calories; 4 heartrate
        sdata = ExerciseGenfunc.s_Get_target(mContext, itarget_mode);
        t_target.setText(sdata[0], sdata[1]);

    }

    /////////////////////////////
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
        int idelay = 1000 / (EngSetting.DEF_UNIT_TIME * EngSetting.DEF_EXERCISE_REFRESH);
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

    /////////////////////////////
    private void swapOrderArray(int iIndex_1 , int iIndex_2)
    {
        int iTemp;
        ExerciseData.SimpleObj Obj1, Obj2;


        Obj1 = getSimpleObj(iIndex_1);
        Obj2 = getSimpleObj(iIndex_2);

        if(Obj1 != null && Obj2 != null) {
            if(Obj1.iArrow == 1 && Obj2.iArrow == 1) {
                iTemp = Obj1.i_Item;
                Obj1.i_Item = Obj2.i_Item;
                Obj2.i_Item = iTemp;
            }
        }
    }

    protected void v_Set_Item_Choice_Info(int ixshift, int iy_bot, float fsize, SimpleObj obj)
    {
        Choice_UI_Info.vSet_Choice(ixshift, iy_bot, fsize, obj);
    }

    private void item_choice_show(int iLoop)
    {
        int ixshift ;
        int iy_bot = iGet_Scale(30);
        float fsize = fGet_Scale(30f);
        SimpleObj obj = getSimpleObj(iLoop);

        if(obj != null && obj.i_item_type != 0) {
            if(iLoop == 0)
            {
                iy_bot = iGet_Scale(230);
                ixshift = iGet_Scale(280) + iGet_Scale(360) * (2 - 1);
            }
            else {
                ixshift = iGet_Scale(280) + iGet_Scale(360) * (iLoop - 1);
            }
            v_Set_Item_Choice_Info(ixshift, iy_bot, fsize, obj);
        }
    }

    class ButtonListener implements ImageButton.OnClickListener
    {
        @Override
        public void onClick(View v) {
            int iLoop;

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
                for(iLoop = 0 ; iLoop < imax_item ; iLoop++)
                {
                    if (v == img_arrow[iLoop])
                    {
                        swapOrderArray(0, iLoop);
                        break;
                    }
                    if (v == t_title[iLoop])
                    {
                        item_choice_show(iLoop);
                        break;
                    }
                }
            }

        }
    }


}
