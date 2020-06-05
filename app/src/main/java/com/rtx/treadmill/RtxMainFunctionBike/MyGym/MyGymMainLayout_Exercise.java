package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

/**
 * Created by chasechang on 3/22/17.
 */

public class MyGymMainLayout_Exercise extends MyGymMainLayout {

    public MyGymMainLayout_Exercise(Context context, MainActivity mMainActivity) {
        super(context,mMainActivity);

    }

    @Override
    protected void add_View()
    {
        vSetTitleText("");

        {
            removeView(imageView_BackHome);
            addRtxImagePaddingViewToLayout(imageView_BackHome, R.xml.home, 1049, 38, 39, 39, 30);
        }

        {
            addRtxImageViewToLayout(imageView_ReturnExercisePage, R.drawable.home_return_exercise, 1151, 35, 51, 46);
        }

        {
            addRtxTextViewToLayout(fillerTextView_Bulletin, R.string.bulletin, 17.88f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 200, 40, 250, 45, Common.Color.yellow_1);
            fillerTextView_Bulletin.setMode(5);
            addRtxTextViewToLayout(fillerTextView_Class, R.string.class_str, 17.88f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 475, 40, 250, 45, Common.Color.yellow_1);
            fillerTextView_Class.setMode(5);
            addRtxTextViewToLayout(fillerTextView_MyClass, R.string.my_class, 17.88f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 749, 40, 250, 45, Common.Color.yellow_1);
            fillerTextView_MyClass.setMode(5);

            addRtxImageViewToLayout(imageView_ButtonIcon_Bulletin,R.drawable.bulletin_icon_yellow,405,52,17,21);
            addRtxImageViewToLayout(imageView_ButtonIcon_Class,R.drawable.class_icon_yellow,690,53,25,20);
            addRtxImageViewToLayout(imageView_ButtonIcon_MyClass,R.drawable.myclass_icon_yellow,953,52,26,21);

            imageView_ButtonIcon_Bulletin.setClickable(false);
            imageView_ButtonIcon_Class.setClickable(false);
            imageView_ButtonIcon_MyClass.setClickable(false);

            imageView_ButtonIcon_Bulletin.setEnabled(false);
            imageView_ButtonIcon_Class.setEnabled(false);
            imageView_ButtonIcon_MyClass.setEnabled(false);
        }

        if(iModeSelect == MODE_BULLETIN)
        {
            removeView(mLayout_ClassCalendar);

            if(mLayout_BulletinList == null)
            {
            }
            else
            {
                addViewToLayout(mLayout_BulletinList, 0, 104, 1280, 539);
                mLayout_BulletinList.display();
            }
        }
        else
        if(iModeSelect == MODE_CLASS)
        {
            removeView(mLayout_BulletinList);

            addViewToLayout(mLayout_ClassCalendar,0,104,1280,539);
            mLayout_ClassCalendar.display(iModeSelect);
        }
        else
        if(iModeSelect == MODE_MY_CLASS)
        {
            removeView(mLayout_BulletinList);

            addViewToLayout(mLayout_ClassCalendar,0,104,1280,539);
            mLayout_ClassCalendar.display(iModeSelect);
        }

        vSetModeButton(iModeSelect);
    }

    @Override
    protected void vClickReturnExercisePage()
    {
        mMainActivity.mMainProcBike.v_Goto_ExercisePage();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
