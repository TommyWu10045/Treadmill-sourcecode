package com.rtx.treadmill.RtxMainFunctionBike.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseActivity.Rtx_BaseActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxShare.Share_Facebook;
import com.rtx.treadmill.RtxShare.Share_Twitter;
import com.rtx.treadmill.RtxShare.Share_Weibo;
import com.rtx.treadmill.RtxTools.Rtx_Bitmap;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chasechang on 3/22/17.
 */

public class HomeLayout extends Rtx_BaseLayout {

    private Context mContext;

    //ImageView
    private RtxImageView imageView_Logo;
    private RtxImageView imageView_LanguageIcon;
    private RtxImageView imageView_LanguageArrow;
    private RtxImageView imageView_LoginFrame;
    //private RtxImageView imageView_LoginHint;
    protected RtxImageView imageView_Icon_Training;
    protected RtxImageView imageView_Icon_HeartRateControl;
    protected RtxImageView imageView_Icon_Hiit;
    protected RtxImageView imageView_Icon_PhysicalTest;
    protected RtxImageView imageView_Icon_TargetTrain;
    protected RtxImageView imageView_Icon_MyPerformance;
    protected RtxImageView imageView_Icon_BodyManager;
    protected RtxImageView imageView_Icon_MyGym;
    protected RtxImageView imageView_Icon_MyWorkout;
    protected RtxImageView imageView_Button_QuickStart;

    protected RtxImageView imageView_Settings;
    protected RtxImageView imageView_Logout;
    protected RtxImageView imageView_Mail;
    protected RtxImageView imageView_MailHalo;

    private RtxImageView imageView_WeatherIcon;

    //TextView
    protected RtxTextView textView_Language;
    protected RtxTextView textView_Login;
    //private RtxTextView textView_LoginMsg;
    private RtxTextView textView_Icon_Training;
    private RtxTextView textView_Icon_HeartRateControl;
    private RtxTextView textView_Icon_Hiit;
    private RtxTextView textView_Icon_PhysicalTest;
    private RtxTextView textView_Icon_TargetTrain;
    private RtxTextView textView_Icon_MyPerformance;
    private RtxTextView textView_Icon_BodyManager;
    private RtxTextView textView_Icon_MyGym;
    private RtxTextView textView_Icon_MyWorkout;
    private RtxTextView textView_QuickStart;

    private RtxTextView textView_UnLogin;
    private RtxTextView textView_Mail;
    private RtxTextView textView_Settings;
    private RtxTextView textView_Logout;

    private RtxTextView textView_City;
    private RtxTextView textView_Temp;

    private RtxTextView textView_Time;
    private RtxTextView textView_Date;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private SimpleDateFormat timeFormat = null;

    private boolean bEffectMail = false;
    private long leng_downtime;
    private long leng_time = 5000;

    UiDataStruct.HOME_USER_INFO userInfo;

    int iMailEffectCount = 0;

    public HomeLayout(Context context, MainActivity mMainActivity) {
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

        setBackgroundResource(R.drawable.main_backgroundb);
        this.mMainActivity = mMainActivity;
    }

    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }

        init_View();
        init_event();
    }

    public void display()
    {
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        //Image
        {
            //Top Item
            if(imageView_Logo == null) imageView_Logo = new RtxImageView(mContext);
            if(imageView_LanguageIcon == null) imageView_LanguageIcon = new RtxImageView(mContext);
            if(imageView_LanguageArrow == null) imageView_LanguageArrow = new RtxImageView(mContext);
            if(imageView_LoginFrame == null) imageView_LoginFrame = new RtxImageView(mContext);
            //if(imageView_LoginHint == null) imageView_LoginHint = new RtxImageView(mContext);

            //Icon
            if(imageView_Icon_Training == null) imageView_Icon_Training = new RtxImageView(mContext);
            if(imageView_Icon_HeartRateControl == null) imageView_Icon_HeartRateControl = new RtxImageView(mContext);
            if(imageView_Icon_Hiit == null) imageView_Icon_Hiit = new RtxImageView(mContext);
            if(imageView_Icon_PhysicalTest == null) imageView_Icon_PhysicalTest = new RtxImageView(mContext);
            if(imageView_Icon_TargetTrain == null) imageView_Icon_TargetTrain = new RtxImageView(mContext);
            if(imageView_Icon_MyPerformance == null) imageView_Icon_MyPerformance = new RtxImageView(mContext);
            if(imageView_Icon_BodyManager == null) imageView_Icon_BodyManager = new RtxImageView(mContext);
            if(imageView_Icon_MyGym == null) imageView_Icon_MyGym = new RtxImageView(mContext);
            if(imageView_Icon_MyWorkout == null) imageView_Icon_MyWorkout = new RtxImageView(mContext);

            //Bottom
            if(imageView_Button_QuickStart == null) imageView_Button_QuickStart = new RtxImageView(mContext);
            if(imageView_Settings == null) imageView_Settings = new RtxImageView(mContext);
            if(imageView_Logout == null) imageView_Logout = new RtxImageView(mContext);
            if(imageView_Mail == null) imageView_Mail = new RtxImageView(mContext);
            if(imageView_MailHalo == null) imageView_MailHalo = new RtxImageView(mContext);

            if(imageView_WeatherIcon == null) imageView_WeatherIcon = new RtxImageView(mContext);
        }

        //Text
        {
            //Top Item
            if(textView_Language == null) textView_Language = new RtxTextView(mContext);
            if(textView_Login == null) textView_Login = new RtxTextView(mContext);
            //if(textView_LoginMsg == null) textView_LoginMsg = new RtxTextView(mContext);

            //Icon
            if(textView_Icon_Training == null) textView_Icon_Training = new RtxTextView(mContext);
            if(textView_Icon_HeartRateControl == null) textView_Icon_HeartRateControl = new RtxTextView(mContext);
            if(textView_Icon_Hiit == null) textView_Icon_Hiit = new RtxTextView(mContext);
            if(textView_Icon_PhysicalTest == null) textView_Icon_PhysicalTest = new RtxTextView(mContext);
            if(textView_Icon_TargetTrain == null) textView_Icon_TargetTrain = new RtxTextView(mContext);
            if(textView_Icon_MyPerformance == null) textView_Icon_MyPerformance = new RtxTextView(mContext);
            if(textView_Icon_BodyManager == null) textView_Icon_BodyManager = new RtxTextView(mContext);
            if(textView_Icon_MyGym == null) textView_Icon_MyGym = new RtxTextView(mContext);
            if(textView_Icon_MyWorkout == null) textView_Icon_MyWorkout = new RtxTextView(mContext);

            //Bottom
            if(textView_QuickStart == null) textView_QuickStart = new RtxTextView(mContext);
            if(textView_Temp == null) textView_Temp = new RtxTextView(mContext);
            if(textView_City == null) textView_City = new RtxTextView(mContext);

            if(textView_UnLogin == null)   textView_UnLogin = new RtxTextView(mContext);

            if(textView_Mail == null)   textView_Mail = new RtxTextView(mContext);
            if(textView_Settings == null)   textView_Settings = new RtxTextView(mContext);
            if(textView_Logout == null)   textView_Logout = new RtxTextView(mContext);
        }

        {
            if(textView_Time == null) textView_Time = new RtxTextView(mContext);
            if(textView_Date == null) textView_Date = new RtxTextView(mContext);
        }
    }

    private void add_View()
    {
        //Image
        {
            //Top
            addRtxImageViewToLayout(imageView_Logo, R.drawable.circle_logo, 50, 48, 143, 30);
            addRtxImagePaddingViewToLayout(imageView_LanguageIcon, R.drawable.main_language_icon, 256, 51, 19, 19, 30);
            addRtxImagePaddingViewToLayout(imageView_LanguageArrow, R.drawable.main_language_arrow, 440, 57, 13, 8, 30);//By Alan


            //1st icon
            addRtxImageViewToLayout(imageView_Icon_Training, R.drawable.main_icon_training, 100, 163, 153, 153);//By Alan
            addRtxImageViewToLayout(imageView_Icon_HeartRateControl, R.drawable.main_icon_heart_rate_control, 400, 163, 153, 153);//By Alan
//            addRtxImageViewToLayout(imageView_Icon_Hiit, R.drawable.main_icon_hiit, 565, 163, 153, 153);
//            addRtxImageViewToLayout(imageView_Icon_PhysicalTest, R.drawable.main_icon_physical_test, 807, 163, 153, 153);
            addRtxImageViewToLayout(imageView_Icon_TargetTrain, R.drawable.main_icon_target_train_star, 640+77, 163, 153, 153);//By Alan

            //2st icon
            addRtxImageViewToLayout(imageView_Icon_MyPerformance, R.drawable.main_icon_my_performance_star, 1280-100-153, 163, 153, 153);//By Alan
            addRtxImageViewToLayout(imageView_Icon_BodyManager, R.drawable.main_icon_body_manager_star, 100, 398, 153, 153);//By Alan
            addRtxImageViewToLayout(imageView_Icon_MyGym, R.drawable.main_icon_my_gym_star, 400, 398, 153, 153);//By Alan
            addRtxImageViewToLayout(imageView_Icon_MyWorkout, R.drawable.main_icon_my_workout_star, 640+77, 398, 153, 153);//By Alan

            //Bottom
            addRtxImageViewToLayout(imageView_Button_QuickStart, R.drawable.main_button_quick_start, 434, 670, 415, 97);//By Alan
            addRtxImageViewToLayout(imageView_WeatherIcon, -1, 68, 657, 39, 37);
        }

        //Text
        {
            //Top
            addRtxTextViewToLayout(textView_Language, LanguageData.sGetUserLanguageTxt(), 23.33f, Common.Color.main_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 287, 20, 148, 81);//By Alan

            //1st icon
            addRtxTextViewToLayout(textView_Icon_Training, R.string.training, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 50, 328, 252, 50);//By Alan
            addRtxTextViewToLayout(textView_Icon_HeartRateControl, R.string.heart_rate_control, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 350, 328, 252, 50);//By Alan
//            addRtxTextViewToLayout(textView_Icon_Hiit, R.string.hiit, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 515, 328, 252, 39);
//            addRtxTextViewToLayout(textView_Icon_PhysicalTest, R.string.physical_test, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 757, 328, 252, 39);
            addRtxTextViewToLayout(textView_Icon_TargetTrain, R.string.target_train, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 670, 328, 252, 50);//By Alan

            //2st icon
            addRtxTextViewToLayout(textView_Icon_MyPerformance, R.string.my_performance, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 980, 328, 252, 50);//By Alan
            addRtxTextViewToLayout(textView_Icon_BodyManager, R.string.body_manager, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 50, 563, 252, 50);//By Alan
            addRtxTextViewToLayout(textView_Icon_MyGym, R.string.my_gym, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 350, 563, 252, 50);//By Alan
            addRtxTextViewToLayout(textView_Icon_MyWorkout, R.string.my_workout, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_HORIZONTAL, 670, 563, 252, 50);//By Alan

            //Bottom
            addRtxTextViewToLayout(textView_QuickStart, R.string.quick_start, 28.87f, Common.Color.main_word_purple, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 486, 620, 310, 197);//By Alan
            textView_QuickStart.setClickable(false);
            textView_QuickStart.setEnabled(false);

            addRtxTextViewToLayout(textView_Temp, EngSetting.s_Get_DEFAULT_TEMP(), 28.87f, Common.Color.main_word_white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT, 88, 688, 242, 37);//By Alan
            addRtxTextViewToLayout(textView_City, EngSetting.s_Get_DEFAULT_CITY(), 28.87f, Common.Color.main_word_white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT, 88, 718, 291, 37);//By Alan
        }

        {
//            addViewToLayout(mRtxDigitalClock,529,678,224,70);
//            addViewToLayout(mRtxDigitalDate,68,693,291,37);
            addRtxTextViewToLayout(textView_Time, 28.87f,Common.Color.main_word_white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.RIGHT,965,688,224,70);//By Alan
            addRtxTextViewToLayout(textView_Date, 28.87f,Common.Color.main_word_white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.RIGHT,900,720,291,37);//By Alan
        }
    }

    private void add_UnLoginView()
    {
        addRtxImageViewToLayout(imageView_LoginFrame, R.drawable.main_login_frame, 950, 38, 244, 43);//By Alan
        //addRtxImageViewToLayout(imageView_LoginHint, R.drawable.main_hint, 850, 86, 310, 53);
        addRtxText(textView_Login, R.string.log_in, 23.33f, Common.Color.main_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 995 , 39, 140, 42 , 25);//By Alan
        //addRtxTextViewToLayout(textView_LoginMsg, R.string.login_msg, 20f, Common.Color.main_word_dark_black, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 850, 101, 310, 39);
        addRtxTextViewToLayout(textView_UnLogin, R.string.login_msg, 23.33f, Common.Color.yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT | Gravity.CENTER_VERTICAL, 510, 95, 680, 47);//By Alan
    }

    private void add_LoginView()
    {
        //addRtxImageViewToLayout(imageView_Mail, R.drawable.main_mail_icon, 777, 47, 35, 26);
        //addRtxImageViewToLayout(imageView_MailHalo, R.drawable.main_mail_halo_icon, 761, 32, 66, 56);

        addRtxImagePaddingViewToLayout(imageView_Settings, R.drawable.main_settings_icon, 869, 43, 33, 33, 30);
        addRtxImagePaddingViewToLayout(imageView_Logout, R.drawable.main_logout_icon, 1064, 46, 33, 27,30);

        //addRtxTextViewToLayout(textView_Mail, R.string.you_have_message, 23.33f, Common.Color.yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 539, 47, 220, 26);
        addRtxTextViewToLayout(textView_Settings, R.string.settings, 20f, Common.Color.main_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 917, 20, 143, 79);
        addRtxTextViewToLayout(textView_Logout, R.string.log_out, 20f, Common.Color.main_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 1115, 20, 150, 79);
    }

    protected void vAddLoginView(boolean bLogin)
    {
        vSetLoginIcon(bLogin);

        if(bLogin)
        {
            remove_UnLoginView();
            add_LoginView();
        }
        else
        {
            remove_LoginView();
            add_UnLoginView();
        }
    }

    protected void vUpdateMailState(UiDataStruct.HOME_USER_INFO userInfo)
    {
        this.userInfo = userInfo;

        removeView(textView_Mail);
        removeView(imageView_Mail);
        removeView(imageView_MailHalo);

        addRtxTextViewToLayout(textView_Mail, R.string.you_have_a_message, 23.33f, Common.Color.yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, 869, 90, 400, 26);

        if(userInfo.bGetNewInfo())
        {
            if(userInfo.bGetAlreadyReaded())
            {
                textView_Mail.setText(LanguageData.s_get_string(mContext,R.string.hi) + " " + CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_NAM));
                setEffectMailFullAlpha();
                addRtxImagePaddingViewToLayout(imageView_Mail, R.drawable.main_mail_icon, 777, 47, 35, 26,30);
            }
            else
            {
                textView_Mail.setText(LanguageData.s_get_string(mContext,R.string.you_have_a_message));
                addRtxImagePaddingViewToLayout(imageView_Mail, R.drawable.main_mail_icon, 777, 47, 35, 26,30);
                addRtxImagePaddingViewToLayout(imageView_MailHalo, R.drawable.main_mail_halo_icon, 761, 32, 66, 56,30);
            }
        }
        else
        {
            textView_Mail.setText(LanguageData.s_get_string(mContext,R.string.hi) + " " + CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_NAM));
        }
    }

    private void vSetLoginIcon(boolean bLogin)
    {
        int iResID_BodyManager = -1;
        int iResID_MyGym = -1;
        int iResID_MyPerformance = -1;
        int iResID_MyWorkout = -1;
        int iResID_TargetTrain = -1;

        if(bLogin)
        {
            iResID_BodyManager = R.drawable.main_icon_body_manager;
            iResID_MyGym = R.drawable.main_icon_my_gym;
            iResID_MyPerformance = R.drawable.main_icon_my_performance;
            iResID_MyWorkout = R.drawable.main_icon_my_workout;
            iResID_TargetTrain = R.drawable.main_icon_target_train;
        }
        else
        {
            iResID_BodyManager = R.drawable.main_icon_body_manager_star;
            iResID_MyGym = R.drawable.main_icon_my_gym_star;
            iResID_MyPerformance = R.drawable.main_icon_my_performance_star;
            iResID_MyWorkout = R.drawable.main_icon_my_workout_star;
            iResID_TargetTrain = R.drawable.main_icon_target_train_star;
        }

        imageView_Icon_BodyManager.setImageResource(iResID_BodyManager);
        imageView_Icon_MyGym.setImageResource(iResID_MyGym);
        imageView_Icon_MyPerformance.setImageResource(iResID_MyPerformance);
        imageView_Icon_MyWorkout.setImageResource(iResID_MyWorkout);
        imageView_Icon_TargetTrain.setImageResource(iResID_TargetTrain);
    }

    private void remove_UnLoginView()
    {
        removeView(imageView_LoginFrame);
        //removeView(imageView_LoginHint);
        removeView(textView_Login);
        //removeView(textView_LoginMsg);
        removeView(textView_UnLogin);
    }

    private void remove_LoginView()
    {
        removeView(imageView_Mail);
        removeView(imageView_MailHalo);
        removeView(imageView_Settings);
        removeView(imageView_Logout);
        removeView(textView_Mail);
        removeView(textView_Settings);
        removeView(textView_Logout);
    }

    private void init_event()
    {
        textView_Login.setOnClickListener(mButtonListener);
        imageView_LanguageIcon.setOnClickListener(mButtonListener);
        textView_Language.setOnClickListener(mButtonListener);
        imageView_LanguageArrow.setOnClickListener(mButtonListener);
        imageView_Settings.setOnClickListener(mButtonListener);
        textView_Settings.setOnClickListener(mButtonListener);
        textView_Logout.setOnClickListener(mButtonListener);
        imageView_Logout.setOnClickListener(mButtonListener);


        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_TRAINING))         { imageView_Icon_Training.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_HEART_RATE))       { imageView_Icon_HeartRateControl.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_HIIT))             { imageView_Icon_Hiit.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_PHYSICAL_TEST))    { imageView_Icon_PhysicalTest.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_TARGET_TRAIN))     { imageView_Icon_TargetTrain.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_MY_PERFORMANCE))   { imageView_Icon_MyPerformance.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_BODY_MANAGER))     { imageView_Icon_BodyManager.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_MY_GYM))           { imageView_Icon_MyGym.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_MY_WORKOUT))       { imageView_Icon_MyWorkout.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_QUICK_START))      { imageView_Button_QuickStart.setOnClickListener(mButtonListener); }

        imageView_Logo.setOnClickListener(mButtonListener);
        imageView_Logo.setOnTouchListener(logoTouch);

        imageView_Mail.setOnClickListener(mButtonListener);
        imageView_MailHalo.setOnClickListener(mButtonListener);

    }

    private View.OnTouchListener logoTouch = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    leng_downtime = event.getDownTime();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if( (leng_downtime + leng_time) < event.getEventTime())
                    {
                        leng_downtime = event.getEventTime();
                        if(!CloudDataStruct.CloudData_20.is_log_in()) {
                            vMainChangePage(MainState.PROC_ENGMODE);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }

            return false;
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void startEffectMail()
    {
        bEffectMail = true;
    }

    protected void stopEffectMail()
    {
        bEffectMail = false;
    }

    protected void setEffectMailFullAlpha()
    {
        imageView_MailHalo.setAlpha(1.0f);
    }

    protected void effectMail()
    {
        int iTotalStep = 20;

        //if(bEffectMail)
        {
            iMailEffectCount += 2;

            if(imageView_MailHalo != null)
            {
                int iCount = 0;
                float fAlpha = 1.0f;
                float fMaxAlpha = 1.0f;

                iCount = iMailEffectCount % (iTotalStep * 2);
                if(iCount < iTotalStep)
                {
                    fAlpha = (fMaxAlpha / (float)iTotalStep) * (iCount);
                }
                else
                {
                    fAlpha = (fMaxAlpha / (float)iTotalStep) * ((iTotalStep * 2) - iCount);
                }

                imageView_MailHalo.setAlpha(fAlpha);
            }
        }
    }

    protected void updateDateAndTime()
    {
        Calendar cal = GlobalData.getInstance();
        String sDate = null;
        String sTime = null;
        //20190121文件 規格變更 日期＆時間不補零
        //20190129文件 分鐘需補零
        if(textView_Date != null)
        {
            //sDate =   Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1]
            //        + " "
            //        + Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)]
            //        + " "
            //       + String.format("%d",cal.get(Calendar.DAY_OF_MONTH));
            //  Log.e(TAG, "sDate="+Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)]);

            if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "MON")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_1);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "TUE")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_2);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "WED")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_3);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "THU")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_4);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "FRI")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_5);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "SAT")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_6);
            }
            else if(Common.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1] == "SUN")
            {
                sDate = LanguageData.s_get_string(mContext, R.string.WeekStr_7);
            }
/////////////////////////////////////////////////////////////////////////////////////////////////


            if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "JAN")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_1);

            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "FED")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_2);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "MAR")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_3);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "APR")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_4);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "MAY")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_5);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "JUN")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_6);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "JUL")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_7);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "AUG")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_8);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "SEP")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_9);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "OCT")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_10);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "NOV")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_11);
            }
            else if(Common.MONTH_OF_YEAR2[cal.get(Calendar.MONTH)] == "DEC")
            {
                sDate +=" "+LanguageData.s_get_string(mContext, R.string.month_12);
            }

            sDate+=" "+String.format("%d",cal.get(Calendar.DAY_OF_MONTH));

            textView_Date.setText(sDate);
        }

        if(textView_Time != null)
        {
            if(timeFormat == null) {
                timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
                GlobalData.vSetTimezone(timeFormat);
            }

            sTime = timeFormat.format(cal.getTime());
            textView_Time.setText(sTime);
        }
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if      (v == textView_Login)                   {   vMainChangePage(MainState.PROC_LOGIN);              }
            else if (v == imageView_LanguageIcon || v == textView_Language || v == imageView_LanguageArrow)                {   clickLanguage();           }
            else if (v == imageView_Icon_Training)          {   vMainChangePage(MainState.PROC_TRAINING);           }
            else if (v == imageView_Icon_HeartRateControl)  {   vMainChangePage(MainState.PROC_HEART_RATE_CONTROL); }
            else if (v == imageView_Icon_Hiit)              {   vMainChangePage(MainState.PROC_HIIT);               }
            else if (v == imageView_Icon_PhysicalTest)      {   vMainChangePage(MainState.PROC_PHYSICAL);           }
            else if (v == imageView_Icon_TargetTrain)       {   vMainChangePage(MainState.PROC_TARGET_TRAIN);       }
            else if (v == imageView_Icon_MyPerformance)     {   vMainChangePage(MainState.PROC_MY_PERFORMANCE);     }
            else if (v == imageView_Icon_BodyManager)       {   vMainChangePage(MainState.PROC_BODY_MANAGER);       }
            else if (v == imageView_Icon_MyGym)             {   vMainChangePage(MainState.PROC_MY_GYM);             }
            else if (v == imageView_Icon_MyWorkout)         {   vMainChangePage(MainState.PROC_MY_WORKOUT);         }
            else if (v == imageView_Settings         )      {   vMainChangePage(MainState.PROC_SETTING);            }
            else if (v == textView_Settings         )       {   vMainChangePage(MainState.PROC_SETTING);            }
            else if (v == imageView_Button_QuickStart)      {   vMainChangePage(MainState.PROC_QUICK_START);        }
//            else if (v == imageView_Button_QuickStart)      {   vTest();        }

            else if (v == imageView_Mail)                   {   clickMail();        }
            else if (v == imageView_MailHalo)               {   clickMail();        }



            else if (v == textView_Logout)                  {    clickLogout();                                     }
            else if (v == imageView_Logout)                 {    clickLogout();                                     }
        }
    }

    private void clickMail()
    {
        userInfo.vSetAlreadyReaded(true);

        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_HOME_INFO,userInfo,-1,-1);

        mMainActivity.dialogLayout_HomeInfo.fillerTextView_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!mMainActivity.dialogLayout_HomeInfo.bNextPage())
                {
                    mMainActivity.dismissInfoDialog();
                    mMainActivity.mMainProcBike.homeProc.vSetNextState(HomeState.PROC_UPDATE_USER_INFO);
                }
            }
        });
    }

    private void clickLanguage()
    {
        mMainActivity.showInfoDialog(Rtx_BaseActivity.DIALOG_TYPE_LANGUAGE,null,null);
    }

    private void clickLogout()
    {
        mMainActivity.mMainProcBike.homeProc.vSetNextState(HomeState.PROC_LOGOUT);
    }

    private void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.homeProc.vSetNextState(HomeState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    public void physical_Start_Button()//By Alan*
    {
        imageView_Button_QuickStart.performClick();
    }

    private void vTest()
    {
        Rtx_Bitmap.saveBitmap(Rtx_Bitmap.loadBitmapFromView(mMainActivity.frameLayout_Base));
        mMainActivity.createFloatView();

        //Twitter
        //Share_Twitter mShare_Twitter = new Share_Twitter(mMainActivity);
        //mShare_Twitter.share();

        //Ig
        //Share_Ig mShare_Ig = new Share_Ig(mMainActivity);
        //mShare_Ig.share();

        //FB
        //Share_Facebook mShare_Facebook = new Share_Facebook(mMainActivity);
        //mShare_Facebook.share();

        //Weibo
        //Share_Weibo mShare_Weibo = new Share_Weibo(mMainActivity);
        //mShare_Weibo.share();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void Weather_Refresh()
    {
        if(GlobalData.Weather.checkUpdate())
        {
//            if (imageView_WeatherIcon != null && GlobalData.Weather.Bmap != null) {
//                imageView_WeatherIcon.setImageBitmap(GlobalData.Weather.Bmap);
//            }
            if (imageView_WeatherIcon != null)
            {
                int iCode = GlobalData.Weather.iCurrentCode;

                if(iCode >= 0 && iCode <= 47)
                {
                    imageView_WeatherIcon.setImageResource(GlobalData.WeatherIcon.iResId[iCode]);
                }

            }
            if (textView_Temp != null && GlobalData.Weather.fCTemp != 9999 && GlobalData.Weather.iFTemp != 9999) {
                textView_Temp.setText(GlobalData.Weather.get_Temperature(EngSetting.getUnit()));
                EngSetting.v_Set_DEFAULT_TEMP(GlobalData.Weather.get_Temperature(EngSetting.getUnit()));
            }
            if (textView_City != null && GlobalData.Weather.sCity.compareTo("") != 0) {
                textView_City.setText(GlobalData.Weather.sCity);
            }
        }
    }
}
