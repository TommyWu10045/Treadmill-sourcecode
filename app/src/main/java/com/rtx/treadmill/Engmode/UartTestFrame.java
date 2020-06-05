package com.rtx.treadmill.Engmode;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;
import com.rtx.treadmill.UartDevice.Uartcommand;

import java.lang.reflect.Field;

public class UartTestFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = true;

    Context mContext;
    EngModeActivity mEngModeActivity;
    Uartcommand mUartcmd;

    ButtonListener mButtonListener;
    private RtxView v_background;

    private ScrollView scroller;
    private FrameLayout fscroll;
    private FrameLayout ftail;

    private RtxTextView tclose;
    private RtxTextView ttype;
    private int itype = 0;
    private RtxTextView tstop;
    private boolean bstop = false;

    String[] slist = {
            "unitM-F05",
            "unitI-F05",
            "Reset-F10",
            "Pause-F61",
            "Stop-F61",
            "Start-F61",
            "KeyEna-F64",
            "KeyDis-F64",
    };
    RtxFillerTextView[] f_but;
    RtxTextView[] t_text;
    int imax = slist.length;

    String[][] slist_e_treadmill = {
            {"ReadData-F15", "250"},
            {"Speed-F13",   "100"},
            {"Incline-F14", "30"},
            {"Machine-F18", "3,0,530,600,35,130"},
            {"ParAdd-F62",     "5/50"},
            {"ParSub-F62",     "6/50"},
            {"Limit-F65",   "3,005,250,000,150"},
    };

    String[][] slist_e_bike = {
            {"ReadData-F16", "250"},
            {"Level-F17",   "10,630"},
            {"Machine-F18", "5,100"},
            {"Limit-F65",   "5,25"},
    };


    RtxFillerTextView[] f_but_e;
    RtxEditText[] e_text;
    RtxTextView[] t_text_e;
    int imax_e_treadmill = slist_e_treadmill.length;
    int imax_e_bike = slist_e_bike.length;

    int islist_max ;
    int imax_e ;
    String[][] slist_e;

    private int icmd = 0;
    private int icmd_mode = 0;

    private int icount;
    private int isel_mode = -1;
    private int isel_list = -1;
    private int readpass = 0;
    private int readng = 0;
    private int irepeat = 0; //0 one times;

    public UartTestFrame(Context context, EngModeActivity mEngModeActivity) {
        super(context);

        this.mContext = context;
        this.mEngModeActivity = mEngModeActivity;
        this.mUartcmd = mEngModeActivity.mUartcmd;

        setBackgroundColor(Common.Color.dialog_background);

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
        int iLoop;

        init_Title();
        if(imax_e_treadmill > imax_e_bike)
        {
            islist_max = imax_e_treadmill;
        }
        else
        {
            islist_max = imax_e_bike;
        }

        if(v_background == null) { v_background = new RtxView(mContext); }

        if(scroller == null)           {scroller = new ScrollView(mContext);}
        if(fscroll == null)           {
            fscroll = new FrameLayout(mContext);
            scroller.addView(fscroll);
        }
        if(ftail == null)           {
            ftail = new FrameLayout(mContext);
            fscroll.addView(ftail);
        }

        if(tclose == null){ tclose = new RtxTextView(mContext); }
        if(ttype == null){ ttype = new RtxTextView(mContext); }
        if(tstop == null){ tstop = new RtxTextView(mContext); }

        if(f_but == null)
        {
            f_but = new RtxFillerTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                f_but[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if(t_text == null)
        {
            t_text = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                t_text[iLoop] = new RtxTextView(mContext);
            }
        }

        if(f_but_e == null)
        {
            f_but_e = new RtxFillerTextView[islist_max];
            for (iLoop = 0; iLoop < islist_max; iLoop++)
            {
                f_but_e[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if(e_text == null)
        {
            e_text = new RtxEditText[islist_max];
            for (iLoop = 0; iLoop < islist_max; iLoop++)
            {
                e_text[iLoop] = new RtxEditText(mContext);
            }
        }

        if(t_text_e == null)
        {
            t_text_e = new RtxTextView[islist_max];
            for (iLoop = 0; iLoop < islist_max; iLoop++)
            {
                t_text_e[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event()
    {
        int iLoop;

        for (iLoop = 0; iLoop < imax; iLoop++)
        {
            f_but[iLoop].setOnClickListener(mButtonListener);
        }

        for (iLoop = 0; iLoop < islist_max; iLoop++)
        {
            f_but_e[iLoop].setOnClickListener(mButtonListener);
        }

        tclose.setOnClickListener(mButtonListener);
        v_background.setOnClickListener(mButtonListener);
        ttype.setOnClickListener(mButtonListener);
        tstop.setOnClickListener(mButtonListener);
    }

    private void add_scrollview(ScrollView mScrollView, int iX , int iY , int iWidth , int iHeight) {
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iWidth,iHeight);
        if(iX == -1)
        {
            mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        }
        else
        {
            mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            mLayoutParams.leftMargin = iX;
        }
        mLayoutParams.topMargin = iY;
        mScrollView.setLayoutParams(mLayoutParams);

        addView(mScrollView);
    }

    private void add_view(FrameLayout mFram, int iX , int iY , int iWidth , int iHeight) {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mFram.setLayoutParams(mLayoutParams);
    }

    private void addRtxEditView(FrameLayout mFram, RtxEditText view, int iType, int iX, int iY, int iWidth, int iHeight)
    {
        if(view == null)
        {
            Rtx_Log.Log_Error("view is null in addRtxEditViewToLayout!!!");
            return;
        }

        view.setBackgroundColor(Common.Color.engmode_word_backgroud);
        view.setTextColor(Common.Color.engmode_word_gray);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,20f);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(view.getTypeface(), Typeface.NORMAL);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), Common.Font.Relay_Black));

        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(view, R.drawable.cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setInputType(iType);
        view.setSingleLine(true);

        addViewToLayout(mFram, view,iX,iY,iWidth,iHeight);
    }

    private void add_View()
    {
        if (DEBUG) Log.e(TAG, "init_view");
        int ix;
        int iy;
        int iw;
        int ih;
        float fsize;
        String sdata;
        int iLoop;
        int ix_gap = 20;
        int iy_gap = 20;
        int iy_base = 100;

        ix = 0;
        iy = 0;
        iw = 1280;
        ih = 800;
        addViewToLayout(v_background, ix, iy, iw, ih);
        add_scrollview(scroller, ix, iy, iw, ih);

        //Frame
        ix = 0;
        iy = 800;
        iw = 1280;
        ih = 500;
        add_view(ftail, ix, iy, iw, ih);

        ix = 50;
        iy = 40;
        iw = 150;
        ih = 50;
        fsize = 40f;
        addRtxTextView(fscroll, tclose, "Close", fsize, Common.Color.yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += iw + 100;
        iw = 300;
        if(itype == 0)
        {
            sdata = "Treadmill";
        }
        else
        {
            sdata = "Bike";
        }
        addRtxTextView(fscroll, ttype, sdata, fsize, Common.Color.yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += iw + 100;
        sdata = "Stop";
        addRtxTextView(fscroll, tstop, sdata, fsize, Common.Color.yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 50;
        iy = iy_base;
        iw = 150;
        ih = 50;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxTextView(fscroll, f_but[iLoop], slist[iLoop], fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            f_but[iLoop].setFillet(ih / 2);
            iy += (ih + iy_gap);
        }

        ix += (iw + ix_gap);
        iy = iy_base;
        iw = 300;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxTextView(fscroll, t_text[iLoop], "", fsize, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            t_text[iLoop].setBackgroundColor(Common.Color.gray);
            iy += (ih + iy_gap);
        }


        ix += (iw + ix_gap);
        iy = iy_base;
        iw = 150;
        for(iLoop = 0; iLoop < islist_max; iLoop++) {
            addRtxTextView(fscroll, f_but_e[iLoop], "", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            f_but_e[iLoop].setFillet(ih / 2);
            iy += (ih + iy_gap);
        }

        ix += (iw + ix_gap);
        iy = iy_base;
        iw = 200;
        for(iLoop = 0; iLoop < islist_max; iLoop++) {
            addRtxEditView(fscroll, e_text[iLoop], InputType.TYPE_CLASS_NUMBER, ix, iy, iw, ih);
            iy += (ih + iy_gap);
        }

        ix += (iw + ix_gap);
        iy = iy_base;
        iw = 300;
        for(iLoop = 0; iLoop < islist_max; iLoop++) {
            addRtxTextView(fscroll, t_text_e[iLoop], "", fsize, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            t_text_e[iLoop].setBackgroundColor(Common.Color.gray);
            iy += (ih + iy_gap);
        }

        v_refresh_edit();

    }

    private void v_refresh_edit()
    {
        int iLoop ;

        for(iLoop = 0; iLoop < islist_max; iLoop++)
        {
            f_but_e[iLoop].setVisibility(INVISIBLE);
            e_text[iLoop].setVisibility(INVISIBLE);
            t_text_e[iLoop].setVisibility(INVISIBLE);
        }

        if(itype == 0)
        {
            slist_e = slist_e_treadmill;
        }
        else
        {
            slist_e = slist_e_bike;
        }
        imax_e = slist_e.length;

        for(iLoop = 0; iLoop < imax_e; iLoop++)
        {
            f_but_e[iLoop].setText(slist_e[iLoop][0]);
            e_text[iLoop].setText(slist_e[iLoop][1]);
            t_text_e[iLoop].setText("");

            f_but_e[iLoop].setVisibility(VISIBLE);
            e_text[iLoop].setVisibility(VISIBLE);
            t_text_e[iLoop].setVisibility(VISIBLE);
        }


    }

//    String[] slist = {
//            "unitM-F05",
//            "unitE-F05",
//            "Reset-F10",
//            "Pause-F61",
//            "Stop-F61",
//            "Start-F61",
//            "KeyEna-F64",
//            "KeyDis-F64",
//    };

    private void vClickButton01(int isel)
    {
        switch (isel)
        {
            case 0:
                icmd = guart.SET_UNIT;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "0");
                break;
            case 1:
                icmd = guart.SET_UNIT;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "1");
                break;
            case 2:
                icmd = guart.SET_RESET;
                icmd_mode = 0x01;
                mUartcmd.uart_command_write(icmd);
                break;
            case 3:
                icmd = guart.SET_STOP;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "1");
                break;
            case 4:
                icmd = guart.SET_STOP;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "2");
                break;
            case 5:
                icmd = guart.SET_STOP;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "3");
                break;
            case 6:
                icmd = guart.SET_KEY_ENABLE;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "1");
                break;
            case 7:
                icmd = guart.SET_KEY_ENABLE;
                icmd_mode = 0x01;
                mUartcmd.uart_command_string(1, icmd, "0");
                break;
            default:
                break;
        }

        if(isel < imax) {
            if(DEBUG)Log.e(TAG, "===slist[" + isel + "]=" + slist[isel]);
            icount = 0;
            mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST_CHECK);
        }

        vClickBackgroud();

    }

//    String[] slist_e_treadmill = {
//            "ReadData-F15",
//            "Speed-F13",
//            "Incline-F14",
//            "Machine-F18",
//            "ParAdd-F62",
//            "ParSub-F62",
//            "Limit-F65",
//    };
    private void vClickButton02(int isel)
    {
        String scmd = "";

        switch (isel)
        {
            case 0:
                icmd = guart.GET_READ_DATA1;
                try {
                    irepeat = Rtx_TranslateValue.iString2Int(e_text[isel].getText().toString())/EngSetting.DEF_UNIT_TIME;
                }
                catch (Exception e)
                {
                    irepeat = 0;
                }
                icmd_mode = 0x03;
                break;
            case 1:
                icmd = guart.SET_SPEED;
                icmd_mode = 0x01;
                break;
            case 2:
                icmd = guart.SET_INCLINE;
                icmd_mode = 0x01;
                break;
            case 3:
                icmd = guart.SET_MACHINE_PAR;
                icmd_mode = 0x01;
                break;
            case 4:
                icmd = guart.SET_SPEED_ADD;
                icmd_mode = 0x01;
                break;
            case 5:
                icmd = guart.SET_SPEED_ADD;
                icmd_mode = 0x01;
                break;
            case 6:
                icmd = guart.SET_LIMIT;
                icmd_mode = 0x01;
                break;
            default:
                break;
        }

        if(isel < imax_e) {
            scmd = e_text[isel].getText().toString();
            if(DEBUG)Log.e(TAG, "===slist_e[" + isel + "]=" + slist_e[isel] + "   scmd=" + scmd);

            if(icmd_mode == 0x01)
            {
                mUartcmd.uart_command_string(1, icmd, scmd);
            }
            else
            {
                mUartcmd.uart_command_write(icmd);
            }

            icount = 0;
            mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST_CHECK);
        }

        vClickBackgroud();

    }

//    String[][] slist_e_bike = {
//            {"ReadData-F16", "250"},
//            {"Level-F17",   "10,630"},
//            {"Machine-F18", "5,100"},
//            {"Limit-F65",   "5,25"},
//    };
    private void vClickButton03(int isel)
    {
        String scmd = "";

        switch (isel)
        {
            case 0:
                icmd = guart.GET_READ_DATA2;
                try {
                    irepeat = Rtx_TranslateValue.iString2Int(e_text[isel].getText().toString())/EngSetting.DEF_UNIT_TIME;
                }
                catch (Exception e)
                {
                    irepeat = 0;
                }
                icmd_mode = 0x03;
                break;
            case 1:
                icmd = guart.SET_LEVEL;
                icmd_mode = 0x01;
                break;
            case 2:
                icmd = guart.SET_MACHINE_PAR;
                icmd_mode = 0x01;
                break;
            case 3:
                icmd = guart.SET_LIMIT;
                icmd_mode = 0x01;
                break;
            default:
                break;
        }

        if(isel < imax_e) {
            scmd = e_text[isel].getText().toString();
            if(DEBUG)Log.e(TAG, "===slist_e[" + isel + "]=" + slist_e[isel] + "   scmd=" + scmd);

            if(icmd_mode == 0x01)
            {
                mUartcmd.uart_command_string(1, icmd, scmd);
            }
            else
            {
                mUartcmd.uart_command_write(icmd);
            }

            icount = 0;
            mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST_CHECK);
        }

        vClickBackgroud();

    }

    private void vClickBackgroud()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
    }

    public void vUartCmd_check()
    {
        int iret = -1;

        icount++;

        if(icmd_mode > 0) {
            iret = mUartcmd.uart_command_check_result(icmd_mode - 1, icmd);
        }

        if(irepeat == 0) {
            if (iret == 0) {
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_IDLE);
                v_uarttest_finish(false);
            } else {
                if (icount > 50) {
                    mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_IDLE);
                    v_uarttest_finish(true);
                }
            }
        }
        else
        {
            if (iret == 0) {
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST_REPEAT);
                v_uarttest_finish(false);
            } else {
                if (icount >= irepeat) {
                    mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST_REPEAT);
                    v_uarttest_finish(true);
                }
            }
        }
    }

    public void vUartCmd_Repeat_check()
    {
        icount++;

        if(bstop) {
            bstop = false;
            mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_IDLE);
        }
        else if (icount > irepeat)
        {
            icount = 0;
            if(isel_mode == 0)
            {
                vClickButton01(isel_list);
            }
            else
            {
                if(itype == 0) {
                    vClickButton02(isel_list);
                }
                else
                {
                    vClickButton03(isel_list);
                }
            }
        }

    }


    private void v_uarttest_finish(boolean btimeout) {
        String str = "";
        final RtxTextView t_show;
        String stime = "time=" + Rtx_TranslateValue.sInt2String(icount * EngSetting.DEF_UNIT_TIME);
        String sresult = "";

        if (isel_mode == 0) {
            t_show = t_text[isel_list];
        } else {
            t_show = t_text_e[isel_list];
        }

        if (btimeout) {
            str = "timeout; ";
            readng++;
        } else {
            try {
                if (guart.irlen > 3) {
                    str = new String(guart.brbuf, 0, guart.irlen - 2);
                    readpass++;
                }
            } catch (Exception e) {

            }
        }

        if (icmd == guart.GET_READ_DATA1 || icmd == guart.GET_READ_DATA2) {
            sresult = str + " ;" + stime + " ;pass=" + readpass + " ;fail=" + readng;
        }
        else
        {
            sresult = str + " ;" + stime;
        }

        final String sshow = sresult;
        mEngModeActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                t_show.setText(sshow);
            }
        });

    }

    private void vClickClose()
    {
        mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_WIFI);
        vClickBackgroud();
    }

    private void vClickType()
    {
        int iLoop;
        String sdata;

        if(itype == 0)
        {
            itype++;
            sdata = "Bike";
        }
        else
        {
            itype = 0;
            sdata = "Treadmill";
        }
        ttype.setText(sdata);

        v_refresh_edit();

        vClickBackgroud();
    }

    private void vClickStop()
    {
        bstop = true;
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            int iLoop;

            if(mUartcmd != null) {
                icount = 0;
                icmd_mode = 0x00;
                readpass = 0;
                readng = 0;
                irepeat = 0;
                if(v == tclose)
                {
                    vClickClose();
                }
                else if(v == ttype)
                {
                    vClickType();
                }
                else if(v == tstop)
                {
                    vClickStop();
                }
                else if(v == v_background)
                {
                    vClickBackgroud();
                }
                else
                {
                    for (iLoop = 0; iLoop < imax; iLoop++) {
                        if (v == f_but[iLoop]) {
                            isel_mode = 0;
                            isel_list = iLoop;
                            vClickButton01(iLoop);
                        }
                    }

                    for (iLoop = 0; iLoop < imax_e; iLoop++) {
                        if (v == f_but_e[iLoop]) {
                            isel_mode = 1;
                            isel_list = iLoop;
                            if(itype == 0) {
                                vClickButton02(iLoop);
                            }
                            else
                            {
                                vClickButton03(iLoop);
                            }
                        }
                    }
                }


            }

        }

    }
}
