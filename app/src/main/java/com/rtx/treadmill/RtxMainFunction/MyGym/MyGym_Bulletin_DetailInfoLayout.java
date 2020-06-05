package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyGym_Bulletin_DetailInfoLayout extends Rtx_BaseLayout {


    protected Context mContext;

    protected ButtonListener      mButtonListener;
    protected MainActivity        mMainActivity;

    protected RtxDoubleStringView doubleStringView_Title;
    protected RtxFillerTextView   fillerTextView_Date;
    protected ScrollView          mScrollView;
    protected LinearLayout        mLinearLayout;
    protected RtxTextView         textView_Contents;
    protected RtxImageView        imageView_Pho;

    protected boolean             bBitmapSet = false;
    protected String              sPhotoUrl = null;

    public MyGym_Bulletin_DetailInfoLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;

        setBackgroundColor(Common.Color.background_gym);
    }

    @Override
    public void init()
    {
        init_TopBackground();

        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        add_View();
        init_event();

        mMainActivity.mMainProcTreadmill.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_BULLETIN_DETAIL_INFO);
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_TopBackground();
        }

        {
            if(doubleStringView_Title == null)  { doubleStringView_Title = new RtxDoubleStringView(mContext); }
            if(fillerTextView_Date == null)     { fillerTextView_Date = new RtxFillerTextView(mContext); }
            if(mScrollView == null)             { mScrollView = new ScrollView(mContext); }
            if(mLinearLayout == null)           { mLinearLayout = new LinearLayout(mContext); }
            if(textView_Contents == null)       { textView_Contents = new RtxTextView(mContext); }
            if(imageView_Pho == null)           { imageView_Pho = new RtxImageView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        init_BackPrePage();
        init_Title();
        vSetTitleText(R.string.bulletin);

        {
            addViewToLayout(doubleStringView_Title, 102, 160 - 25, 860, 26 + 50);
            doubleStringView_Title.setPaint(Common.Font.Relay_Medium, Common.Color.white, 28.48f, Common.Font.Agenda_Black, Common.Color.pink, 22.16f);
            doubleStringView_Title.setAlignLeft(Gravity.LEFT);
            doubleStringView_Title.setEllipsis(true);
        }

        {
            addRtxTextViewToLayout(fillerTextView_Date, -1, 26.67f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 982, 150, 186, 43, Common.Color.blue_1);
            fillerTextView_Date.setMode(5);
        }

        {
            addViewToLayout(mScrollView,-1,235,1085,563);

            {
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(1085,563);
                mLinearLayout.setOrientation(LinearLayout.VERTICAL);
                mLinearLayout.setLayoutParams(mLayoutParams);
                if(mScrollView.getChildCount() != 0)
                {
                    mScrollView.removeAllViews();
                }
                mScrollView.addView(mLinearLayout);
            }
        }
    }

    protected void updateBitmap()
    {
        if(bBitmapSet == true)
        {
            return;
        }

        if(imageView_Pho == null)
        {
            return;
        }

        if(sPhotoUrl == null)
        {
            return;
        }

        Bitmap bitmap = null;
        bitmap = GlobalData.searchBitmap(sPhotoUrl);

        if(bitmap != null)
        {
            imageView_Pho.setImageBitmap(bitmap);
            bBitmapSet = true;
        }
    }

    protected void vSetData(boolean bHot, String sTitle, Calendar cDate, String sContents, String sUrl)
    {
        vSetTitle(bHot,sTitle);
        vSetDate(cDate);
        vSetContents(sContents);

        bBitmapSet = false;
        sPhotoUrl = null;

        if(sUrl != null)
        {
            if(sUrl.length() > 0)
            {
                vSetPhoto(sUrl);
            }
        }
    }

    private void vSetTitle(boolean bHot, String sTitle)
    {
        if(bHot)
        {
            doubleStringView_Title.setText(sTitle,LanguageData.s_get_string(mContext,R.string.hot));
        }
        else
        {
            doubleStringView_Title.setText(sTitle,"");
        }
    }

    private void vSetDate(Calendar cal)
    {
        String sDate = Rtx_Calendar.sCalendar2Str(cal,"M/dd/yyyy");
        fillerTextView_Date.setText(sDate);
    }

    private void vSetContents(String sContents)
    {
        addContentsView();
        textView_Contents.setText(sContents);
    }

    private void vSetPhoto(String sUrl)
    {
        sPhotoUrl = new String(sUrl);

        addPhotoView();
        updateBitmap();
    }

    protected void addContentsView()
    {
        setTextArg(textView_Contents, 23.88f, Common.Color.white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT | Gravity.TOP);

        try
        {
            mLinearLayout.addView(textView_Contents);
        }
        catch (IllegalStateException e)
        {

        }
    }

    private void removeContentsView()
    {
        mLinearLayout.removeView(textView_Contents);
    }

    protected void addPhotoView()
    {
        //imageView_Pho.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView_Pho.setScaleType(ImageView.ScaleType.FIT_START);
        imageView_Pho.setBackgroundColor(0x00000000);
        imageView_Pho.setImageResource(R.drawable.gym_default_image);

        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(912,684);
        mLayoutParams.setMargins(0,30,0,-60);
        mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

        try
        {
            mLinearLayout.addView(imageView_Pho,mLayoutParams);
        }
        catch (IllegalStateException e)
        {

        }
    }

    private void removePhotoView()
    {
        mLinearLayout.removeView(imageView_Pho);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.myGymProc.vSetNextState(MyGymState.PROC_SHOW_PAGE_MAIN);

        bBitmapSet = true;
        sPhotoUrl = null;

        mLinearLayout.removeAllViews();
        mScrollView.removeAllViews();
        removeAllViews();
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vBackPrePage(); }
//            else if(v == fillerTextView_Edit)   { vChangeMode(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}