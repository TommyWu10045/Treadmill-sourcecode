package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Login.LoginState;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class Layout_ClassCalendar extends Rtx_BaseLayout {

    final int MODE_CLASS = 1;
    final int MODE_MY_CLASS = 2;

    private int iModeSelect = MODE_CLASS;

    private Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private     RtxImageView        imageView_Left;
    private     RtxImageView        imageView_Right;
    private     RtxTextView         textView_Date;

    private     RtxView_DateIcon_Big[]  view_DateIcon = null;

    private     ScrollView          scrollView_ClassList;
    protected   Layout_ClassList    layout_ClassList;

    private     int                 iSelectWeek = 0;

    Paint       paint_Line;


    ArrayList<CloudDataStruct.CLOUD_GYM_CLASS_INFO> list_CloudClassInfo;

    public Layout_ClassCalendar(Context context, MainActivity mMainActivity) {
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

        init();
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    public void display(int iMode)
    {
        iModeSelect = iMode;

        init_View();
        init_event();
        add_View();
        vSetDate(iSelectWeek);
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            if(imageView_Left == null)          { imageView_Left = new RtxImageView(mContext); }
            if(imageView_Right == null)         { imageView_Right = new RtxImageView(mContext); }
            if(textView_Date == null)           { textView_Date = new RtxTextView(mContext); }

            if(scrollView_ClassList == null)    { scrollView_ClassList = new ScrollView(mContext); }
            if(layout_ClassList == null)        { layout_ClassList = new Layout_ClassList(mContext,mMainActivity); }

            if(paint_Line == null)              { paint_Line = new Paint(); }

            init_DateIcon();
        }
    }

    protected void vEnableButton(boolean bEnable)
    {
        imageView_Left.setClickable(bEnable);
        imageView_Right.setClickable(bEnable);
    }

    private void init_event()
    {
        imageView_Left.setOnClickListener(mButtonListener);
        imageView_Right.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        init_Paint();

        {
            addRtxImagePaddingViewToLayout(imageView_Left, R.drawable.mygym_date_select_left, 441, 14, 33, 33, 30);
            addRtxImagePaddingViewToLayout(imageView_Right, R.drawable.mygym_date_select_right, 803, 14, 33, 33, 30);

            addRtxTextViewToLayout(textView_Date,-1,26.67f,Common.Color.yellow_1,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.CENTER, 474, 14, 329, 33);

            //addViewToLayout(mLayout_BullerinList,0,104,1280,593);
        }

        {
            //addViewToLayout(scrollView_ClassList,0,192,1280,400);
            addViewToLayout(scrollView_ClassList,0,165,1280,427);

            try
            {
                scrollView_ClassList.addView(layout_ClassList);
            }
            catch (IllegalStateException e)
            {
                //view is already exist.
            }

        }
    }

    private void init_Paint()
    {
        paint_Line.setColor(Common.Color.blue_1);
        paint_Line.setStrokeWidth(1);
    }

    private void init_DateIcon()
    {
        if(view_DateIcon == null)
        {
            view_DateIcon = new RtxView_DateIcon_Big[7];
        }

        {
            int iSize = 7;
            int iIndex = 0;

            int iW = 74;
            int iH = 74;

            int iPosX = 97;
            int iPosY = 90;
            int iGap = 169;

            for(iIndex = 0 ; iIndex < iSize ; iIndex++)
            {
                if(iIndex != 0)
                {
                    iPosX += iGap;
                }

                if(view_DateIcon[iIndex] == null)
                {
                    view_DateIcon[iIndex] = new RtxView_DateIcon_Big(mContext);
                }

                addViewToLayout(view_DateIcon[iIndex],iPosX,iPosY,iW,iH);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vEnableArrowButton(boolean bEnable)
    {
        if(imageView_Left != null && imageView_Right != null)
        {
            if(bEnable)
            {
                imageView_Left.setAlpha(1.0f);
                imageView_Right.setAlpha(1.0f);
            }
            else
            {
                imageView_Left.setAlpha(0.5f);
                imageView_Right.setAlpha(0.5f);
            }
            imageView_Left.setClickable(bEnable);
            imageView_Left.setEnabled(bEnable);
            imageView_Right.setClickable(bEnable);
            imageView_Right.setEnabled(bEnable);
        }
    }

    protected void vSetDate(int iWeek)
    {
        Calendar cal_Start = null;
        Calendar cal_End = null;

        Calendar cal = GlobalData.getInstance();

        if(iWeek != 0)
        {
            cal.add(Calendar.WEEK_OF_YEAR,iWeek);
        }

        String sThisWeek = null;

        int iIndex = 0;

        for( ; iIndex < 7 ; iIndex++)
        {
            cal.set(Calendar.DAY_OF_WEEK,(iIndex + 1));
            view_DateIcon[iIndex].setDay(cal);

            if(iIndex == 0) { cal_Start = (Calendar)cal.clone(); }
            if(iIndex == 6) { cal_End = (Calendar)cal.clone(); }
        }

        mMainActivity.mMainProcBike.myGymProc.vSetSearchDate(cal_Start,cal_End);

        if(iModeSelect == MODE_CLASS)
        {
            mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_CLASS_INFO_LIST);
			vEnableArrowButton(true);
        }
        else
        //if(iModeSelect ==MODE_MY_CLASS)
        {
            mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_MY_CLASS_INFO_LIST);

            if(check_Login())
            {
				vEnableArrowButton(true);
                //mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_MY_CLASS_INFO_LIST);
            }
            else
            {
                vEnableArrowButton(false);
            }
        }

        sThisWeek = LanguageData.s_get_string(mContext,Common.LONG_MONTH_OF_YEAR[cal.get(Calendar.MONTH)]) + " " + cal.get(Calendar.YEAR);
        textView_Date.setText(sThisWeek);
    }

    protected void vSetWeekSeletIndex(int iIndex)
    {
        iSelectWeek = iIndex;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickLeftButton()
    {
        iSelectWeek --;
        vSetDate(iSelectWeek);
    }

    private void vClickRightButton()
    {
        iSelectWeek ++;
        vSetDate(iSelectWeek);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_Left)         { vClickLeftButton(); }
            else if(v == imageView_Right)   { vClickRightButton(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(canvas == null)
        {
            return;
        }

        if(paint_Line == null)
        {
            return;
        }

        int iW = 1;

        int iPosX = 49;
        int iPosY_Start = 90;
        int iPosY_End = 606;
        int iGap = 169;

        int iIndex = 0;

        for(iIndex = 0 ; iIndex < 8 ; iIndex++)
        {
            if(iIndex != 0)
            {
                iPosX = iPosX + iGap;

            }

            canvas.drawLine(iPosX,iPosY_Start,iPosX,iPosY_End,paint_Line);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();
        //Login
        if(bLogin)
        {

        }
        //Un Login
        else
        {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_LOGIN,-1,-1);
            mMainActivity.dialogLayout_Login.fillerTextView_Login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMainActivity.mMainProcBike.myGymProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_SignUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    mMainActivity.mMainProcBike.myGymProc.vMainChangePage(MainState.PROC_LOGIN);
                    //mMainActivity.dismissInfoDialog();
                }
            });

            mMainActivity.dialogLayout_Login.fillerTextView_Ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
                    //mMainActivity.mMainProcBike.myGymProc.vMainChangePage(MainState.PROC_LOGIN);
                    mMainActivity.dismissInfoDialog();
                }
            });
        }

        return bLogin;
    }
}
