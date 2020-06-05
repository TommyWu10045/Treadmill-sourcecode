package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyGym_Class_DetailInfoLayout extends Rtx_BaseLayout {


    protected Context mContext;

    protected ButtonListener      mButtonListener;
    protected MainActivity        mMainActivity;

    protected RtxView_DateIcon_Big    dateIcon;
    protected RtxTextView             textView_Time;
    protected RtxTextView             textView_ClassName;
    protected RtxTextView             textView_Instructor;
    protected RtxTextScrollView       textScrollView_Contents;
    protected RtxFillerTextView       fillerTextView_Operator;

    protected RtxImageView            imageView_Default;

    protected ViewPager                   viewPager_Photo;
    protected RtxView_PhotoPagerAdapter   pagerAdapter_Photo;
    protected CirclePageIndicator         circlePageIndicator_Photo;

    protected UiDataStruct.CLASS_DETAIL_INFO mCLASS_DETAIL_INFO;
    protected ArrayList<Bitmap> list_PhotoBitmap;
    protected boolean             bPhotoUpdate = false;
    protected int                 iOperatorMode = 0;

    public MyGym_Class_DetailInfoLayout(Context context, MainActivity mMainActivity) {
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

        mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_GET_CLASS_DETAIL_INFO);
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
            if(dateIcon == null)                    { dateIcon = new RtxView_DateIcon_Big(mContext); }
            if(textView_Time == null)               { textView_Time = new RtxTextView(mContext); }
            if(textView_ClassName == null)          { textView_ClassName = new RtxTextView(mContext); }
            if(textView_Instructor == null)         { textView_Instructor = new RtxTextView(mContext); }
            if(textScrollView_Contents == null)     { textScrollView_Contents = new RtxTextScrollView(mContext); }
            if(viewPager_Photo == null)             { viewPager_Photo = new ViewPager(mContext); }
            if(pagerAdapter_Photo == null)          { pagerAdapter_Photo = new RtxView_PhotoPagerAdapter(mContext); }
            if(circlePageIndicator_Photo == null)   { circlePageIndicator_Photo = new CirclePageIndicator(mContext); }
            if(fillerTextView_Operator == null)     { fillerTextView_Operator = new RtxFillerTextView(mContext); }

            if(imageView_Default == null)           { imageView_Default = new RtxImageView(mContext); }

            if(mCLASS_DETAIL_INFO == null)          { mCLASS_DETAIL_INFO = new UiDataStruct.CLASS_DETAIL_INFO(); }
            else                                    { mCLASS_DETAIL_INFO.vClear(); }

            if(list_PhotoBitmap == null)            { list_PhotoBitmap = new ArrayList<Bitmap>(); }
            else                                    { list_PhotoBitmap.clear(); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Operator.setOnClickListener(mButtonListener);
    }

    protected void add_View()
    {
        init_BackPrePage();
        init_Title();

        addViewToLayout(dateIcon,101,171,74,74);
        addRtxTextViewToLayout(textView_Time,-1,26.67f,Common.Color.yellow_1,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 221, 197 - 25, 300, 20 + 50);
        addRtxTextViewToLayout(textView_ClassName,-1,28.48f,Common.Color.white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.LEFT | Gravity.TOP, 101, 274, 636 - 25, 60 + 50);
        addRtxTextViewToLayout(textView_Instructor,-1,21.09f,Common.Color.blue_5,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 101, 350 - 25, 636, 19 + 50);
        addRtxTextScrollViewToLayout(textScrollView_Contents,23.74f,Common.Color.white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.LEFT | Gravity.TOP, 101, 409, 636, 363);
        addRtxTextViewToLayout(fillerTextView_Operator, -1, 16.00f, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 810, 137, 328, 49, 0x00);//By Alan

        textView_ClassName.setLines(2);
        textView_ClassName.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        textView_Instructor.setSingleLine();
        textView_Instructor.setEllipsize(TextUtils.TruncateAt.valueOf("END"));

        addRtxImagePaddingViewToLayout(imageView_Default, R.drawable.gym_default_image, 778,319,401,401, 0);
        viewPager_Photo.setBackgroundColor(0x00000000);
        viewPager_Photo.setAdapter(pagerAdapter_Photo);
        circlePageIndicator_Photo.setViewPager(viewPager_Photo);
        addViewToLayout(viewPager_Photo,778,319,401,401);
        addViewToLayout(circlePageIndicator_Photo,778,319 + 401,401,10);
    }

    private final int OPERATOR_ADD = 0;
    private final int OPERATOR_ADDED = 1;
    private final int OPERATOR_REMOVE = 2;
    private final int OPERATOR_REMOVED = 3;

    private void vSetOperatorMode(int iMode)
    {
        int iTextColor = 0;
        String sText = null;
        int iButtonMode = 0;
        int iButtonColor = 0;
        int iStroke = 4;

        if(iMode == OPERATOR_ADD)
        {
            iTextColor = Common.Color.white;
            sText = LanguageData.s_get_string(mContext,R.string.add_to_my_class);
            iButtonColor = Common.Color.yellow_1;
            iButtonMode = 3;
        }
        else if(iMode == OPERATOR_ADDED)
        {
            iTextColor = Common.Color.black;
            sText = LanguageData.s_get_string(mContext,R.string.added);
            iButtonColor = Common.Color.yellow_1;
            iButtonMode = 0;
        }
        else if(iMode == OPERATOR_REMOVE)
        {
            iTextColor = Common.Color.white;
            sText = LanguageData.s_get_string(mContext,R.string.remove_from_my_class);
            iButtonColor = Common.Color.pink;
            iButtonMode = 3;
        }
        else if(iMode == OPERATOR_REMOVED)
        {
            iTextColor = Common.Color.black;
            sText = LanguageData.s_get_string(mContext,R.string.removed);
            iButtonColor = Common.Color.pink;
            iButtonMode = 0;
        }

        //20190108 新增 MyClass 顯示規則
        if(!check_Login() || Rtx_Calendar.getDiffSec(GlobalData.getInstance(), mCLASS_DETAIL_INFO.cStartDateTime) < 0)
        {
            iTextColor = Common.Color.gym_disable;
            iButtonColor = Common.Color.gym_disable;
            iButtonMode = 3;
            setViewTouchDisable(fillerTextView_Operator);
        }
        else
        {
            fillerTextView_Operator.setClickable(true);
            fillerTextView_Operator.setEnabled(true);
        }

        fillerTextView_Operator.setStrokeWidth(iStroke);
        fillerTextView_Operator.setText(sText);
        fillerTextView_Operator.setTextColor(iTextColor);
        fillerTextView_Operator.setBackgroundColor(iButtonColor);
        fillerTextView_Operator.setMode(iButtonMode);
    }

    protected void vImportCloudData()
    {
        if(mCLASS_DETAIL_INFO == null)
        {
            return;
        }

        mCLASS_DETAIL_INFO.vImportCloudData();
    }

    protected void vUpdateUI()
    {
        if(mCLASS_DETAIL_INFO == null)
        {
            return;
        }

        dateIcon.setDay(mCLASS_DETAIL_INFO.cStartDateTime);
        textView_Time.setText(mCLASS_DETAIL_INFO.sTime);
        textView_ClassName.setText(mCLASS_DETAIL_INFO.sClassName);
        textView_Instructor.setText(mCLASS_DETAIL_INFO.sInstructor);
        textScrollView_Contents.setText(mCLASS_DETAIL_INFO.sContents);

        bPhotoUpdate = true;
        updateBitmap();
    }

    protected void vSetExistInMyClass(boolean bIsExist, String sCLS_ID, String sBKG_SEQ)
    {
//        Log.e("Jerry","bIsExist = " + bIsExist);

        if(sBKG_SEQ == null)    //Class
        {
            vSetTitleText(R.string.class_str);
            if(bIsExist)
            {
                iOperatorMode = OPERATOR_ADDED;
            }
            else
            {
                iOperatorMode = OPERATOR_ADD;
            }
        }
        else    //MyClass
        {
            vSetTitleText(R.string.my_class);
            if(bIsExist)
            {
                iOperatorMode = OPERATOR_REMOVE;
            }
            else
            {
                iOperatorMode = OPERATOR_REMOVED;
            }
        }

        vSetOperatorMode(iOperatorMode);
    }

    protected void updateBitmap()
    {
        Log.e("Jerry","updateBitmap");

        if(bPhotoUpdate == false)
        {
            return;
        }

        if(mCLASS_DETAIL_INFO == null)
        {
            return;
        }

        if(mCLASS_DETAIL_INFO.list_Pho == null)
        {
            return;
        }

        Log.e("Jerry","updateBitmap - 1");

        int iIndex = 0;
        int iSize = mCLASS_DETAIL_INFO.list_Pho.size();

        for( ; iIndex < iSize ; iIndex++)
        {
            if(mCLASS_DETAIL_INFO.list_Pho.get(iIndex).bAlreadySet == false)
            {
                if(mCLASS_DETAIL_INFO.list_Pho.get(iIndex).sUri != null)
                {
                    Bitmap bitmap = null;
                    bitmap = GlobalData.searchBitmap(mCLASS_DETAIL_INFO.list_Pho.get(iIndex).sUri);

                    if(bitmap != null)
                    {
                        Log.e("Jerry","bitmap = " + bitmap);
                        viewPager_Photo.setBackgroundColor(Common.Color.background_gym);
                        mCLASS_DETAIL_INFO.list_Pho.get(iIndex).bAlreadySet = true;
                        list_PhotoBitmap.add(bitmap);
                        pagerAdapter_Photo.change(list_PhotoBitmap);
                    }
                    else
                    {
                        viewPager_Photo.setBackgroundColor(0x00000000);
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickOperator()
    {
        if(iOperatorMode == OPERATOR_ADD)
        {
            mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_ADD_CLASS);
        }
        else if(iOperatorMode == OPERATOR_REMOVE)
        {
            mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_CLOUD_DELETE_CLASS);
        }
    }

    private void vBackPrePage()
    {
        bPhotoUpdate = false;
        mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_SHOW_PAGE_MAIN);
        removeAllViews();
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vBackPrePage(); }
            else if(v == fillerTextView_Operator)   { vClickOperator(); }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vTest()
    {
        dateIcon.setDay("12","SUN");
        textView_Time.setText("12:00 - 14:00");
        textView_ClassName.setText("Yoga");
        textView_Instructor.setText("Jerry LIn");
        textScrollView_Contents.setText("Japan's UN Ambassador Koro Bessho strongly condemned the attacks on civilians and said his nation was deeply disturbed at reports of killings.\n" +
                "Myanmar's special envoy told the Council the country realizes the humanitarian situation needs to be addressed. He said thousands fled because of fear due to terrorism, and that Myanmar is cooperating with the Red Cross.\n" +
                "The UN Secretary-General warned that \"we should not be surprised if decades of discrimination and double standards in treatment of the Rohingya create an opening for radicalization.\"\n" +
                "China, a neighbor of Myanmar and Bangladesh where hundreds of thousands have fled, said \"there is no quick fix\" to the conflict.\n" +
                "Diplomats say they want a political dialogue to start. The Council plans to hear from former UN Secretary-General Kofi Annan, who chaired a commission on Myanmar packed with recommendations, next week.\n" +
                "Swedish UN Ambassador Olof Skoog said the Annan report \"provides the way forward,\" as he urged the Myanmar government to take responsibility to bring an end to the conflict once and for all.\"\n" +
                "CNN's Ben Westcott and Karen Smith contributed to this report.Japan's UN Ambassador Koro Bessho strongly condemned the attacks on civilians and said his nation was deeply disturbed at reports of killings.\n" +
                "Myanmar's special envoy told the Council the country realizes the humanitarian situation needs to be addressed. He said thousands fled because of fear due to terrorism, and that Myanmar is cooperating with the Red Cross.\n" +
                "The UN Secretary-General warned that \"we should not be surprised if decades of discrimination and double standards in treatment of the Rohingya create an opening for radicalization.\"\n" +
                "China, a neighbor of Myanmar and Bangladesh where hundreds of thousands have fled, said \"there is no quick fix\" to the conflict.\n" +
                "Diplomats say they want a political dialogue to start. The Council plans to hear from former UN Secretary-General Kofi Annan, who chaired a commission on Myanmar packed with recommendations, next week.\n" +
                "Swedish UN Ambassador Olof Skoog said the Annan report \"provides the way forward,\" as he urged the Myanmar government to take responsibility to bring an end to the conflict once and for all.\"\n" +
                "CNN's Ben Westcott and Karen Smith contributed to this report.");



        {
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.main_icon_body_manager);
            list_PhotoBitmap.add(bmp);
        }
        pagerAdapter_Photo.change(list_PhotoBitmap);
        {
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.main_icon_heart_rate_control);
            list_PhotoBitmap.add(bmp);
        }
        pagerAdapter_Photo.change(list_PhotoBitmap);
        {
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.main_icon_hiit);
            list_PhotoBitmap.add(bmp);
        }
        pagerAdapter_Photo.change(list_PhotoBitmap);
        {
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.main_icon_my_gym);
            list_PhotoBitmap.add(bmp);
        }
        pagerAdapter_Photo.change(list_PhotoBitmap);
    }

    private boolean check_Login()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();

        return bLogin;
    }
}