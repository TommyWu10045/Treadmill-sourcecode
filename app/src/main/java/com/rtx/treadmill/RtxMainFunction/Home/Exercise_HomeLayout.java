package com.rtx.treadmill.RtxMainFunction.Home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class Exercise_HomeLayout extends Rtx_BaseLayoutE {

    private Context mContext;

    //ImageView
    private RtxImageView imageView_Logo;

    protected RtxImageView imageView_Icon_TargetTrain;
    protected RtxImageView imageView_Icon_MyPerformance;
    protected RtxImageView imageView_Icon_BodyManager;
    protected RtxImageView imageView_Icon_MyGym;

    //TextView
    private RtxTextView textView_Icon_TargetTrain;
    private RtxTextView textView_Icon_MyPerformance;
    private RtxTextView textView_Icon_BodyManager;
    private RtxTextView textView_Icon_MyGym;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private long leng_downtime;
    private long leng_time = 5000;

    public Exercise_HomeLayout(Context context, MainActivity mMainActivity) {
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
    }

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
        //Image
        {
            init_BackExercise();

            //Top Item
            if(imageView_Logo == null)                  {   imageView_Logo = new RtxImageView(mContext);                }
            //Icon
            if(imageView_Icon_TargetTrain == null)      {   imageView_Icon_TargetTrain = new RtxImageView(mContext);    }
            if(imageView_Icon_MyPerformance == null)    {   imageView_Icon_MyPerformance = new RtxImageView(mContext);  }
            if(imageView_Icon_BodyManager == null)      {   imageView_Icon_BodyManager = new RtxImageView(mContext);    }
            if(imageView_Icon_MyGym == null)            {   imageView_Icon_MyGym = new RtxImageView(mContext);          }
        }

        //Text
        {
            //Icon
            if (textView_Icon_TargetTrain == null)      {   textView_Icon_TargetTrain = new RtxTextView(mContext);      }
            if (textView_Icon_MyPerformance == null)    {   textView_Icon_MyPerformance = new RtxTextView(mContext);    }
            if (textView_Icon_BodyManager == null)      {   textView_Icon_BodyManager = new RtxTextView(mContext);      }
            if (textView_Icon_MyGym == null)            {   textView_Icon_MyGym = new RtxTextView(mContext);            }
        }
    }


    private void add_View()
    {
        //Image
        {
            //Top
            addRtxImageViewToLayout(imageView_Logo, R.drawable.circle_logo, 50, 48, 143, 30);

            addRtxImageViewToLayout(imageView_Icon_TargetTrain, R.drawable.main_icon_target_train_star, 84, 162, 153, 153);
            addRtxImageViewToLayout(imageView_Icon_MyPerformance, R.drawable.main_icon_my_performance_star, 324, 162, 153, 153);
            addRtxImageViewToLayout(imageView_Icon_BodyManager, R.drawable.main_icon_body_manager_star, 565, 162, 153, 153);
            addRtxImageViewToLayout(imageView_Icon_MyGym, R.drawable.main_icon_my_gym_star, 806, 162, 153, 153);
        }

        //Text
        {
            addRtxTextViewToLayout(textView_Icon_TargetTrain, R.string.target_train, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 84 - 50, 338 - 25, 153 + 100, 15 + 50);
            addRtxTextViewToLayout(textView_Icon_MyPerformance, R.string.my_performance, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 324 - 50, 338 - 25, 153 + 100, 15 + 50);
            addRtxTextViewToLayout(textView_Icon_BodyManager, R.string.body_manager, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 565 - 50, 338 - 25, 153 + 100, 15 + 50);
            addRtxTextViewToLayout(textView_Icon_MyGym, R.string.my_gym, 20f, Common.Color.main_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 806 - 50, 338 - 25, 153 + 100, 15 + 50);
        }


    }

    protected void vAddLoginView(boolean bLogin)
    {
        vSetLoginIcon(bLogin);
    }

    private void vSetLoginIcon(boolean bLogin)
    {
        int iResID_BodyManager = -1;
        int iResID_MyGym = -1;
        int iResID_MyPerformance = -1;
        int iResID_TargetTrain = -1;

        if(bLogin)
        {
            iResID_BodyManager = R.drawable.main_icon_body_manager;
            iResID_MyGym = R.drawable.main_icon_my_gym;
            iResID_MyPerformance = R.drawable.main_icon_my_performance;
            iResID_TargetTrain = R.drawable.main_icon_target_train;
        }
        else
        {
            iResID_BodyManager = R.drawable.main_icon_body_manager_star;
            iResID_MyGym = R.drawable.main_icon_my_gym_star;
            iResID_MyPerformance = R.drawable.main_icon_my_performance_star;
            iResID_TargetTrain = R.drawable.main_icon_target_train_star;
        }

        imageView_Icon_BodyManager.setImageResource(iResID_BodyManager);
        imageView_Icon_MyGym.setImageResource(iResID_MyGym);
        imageView_Icon_MyPerformance.setImageResource(iResID_MyPerformance);
        imageView_Icon_TargetTrain.setImageResource(iResID_TargetTrain);
    }

    private void init_event()
    {
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_TARGET_TRAIN))     { imageView_Icon_TargetTrain.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_MY_PERFORMANCE))   { imageView_Icon_MyPerformance.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_BODY_MANAGER))     { imageView_Icon_BodyManager.setOnClickListener(mButtonListener); }
        if(Rtx_Debug.bGetFunctionEnable(Rtx_Debug.FUNCTION_INDEX_MY_GYM))           { imageView_Icon_MyGym.setOnClickListener(mButtonListener); }

        //imageView_Logo.setOnClickListener(mButtonListener);
        //imageView_Logo.setOnTouchListener(logoTouch);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);

    }

    private OnTouchListener logoTouch = new OnTouchListener(){
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
                        vMainChangePage(MainState.PROC_ENGMODE);
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
    ////////////////////////////////////////////////////////////////////////
    private void vBackToExercisePage()
    {
        mMainActivity.mMainProcTreadmill.v_Goto_ExercisePage();
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v) {
            if      (v == imageView_ReturnExercisePage)     {   vBackToExercisePage();                              }
            else if (v == imageView_Icon_TargetTrain)       {   vMainChangePage(MainState.PROC_TARGET_TRAIN);       }
            else if (v == imageView_Icon_MyPerformance)     {   vMainChangePage(MainState.PROC_MY_PERFORMANCE);     }
            else if (v == imageView_Icon_BodyManager)       {   vMainChangePage(MainState.PROC_BODY_MANAGER);       }
            else if (v == imageView_Icon_MyGym)             {   vMainChangePage(MainState.PROC_MY_GYM);             }
        }
    }

    private void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcTreadmill.homeProc.vSetNextState(HomeState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
