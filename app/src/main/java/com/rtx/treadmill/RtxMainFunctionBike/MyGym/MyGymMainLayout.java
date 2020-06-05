package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyGymMainLayout extends Rtx_BaseLayout {

    final int MODE_BULLETIN = 0;
    final int MODE_CLASS = 1;
    final int MODE_MY_CLASS = 2;

    protected int iModeSelect = MODE_BULLETIN;


    protected Context mContext;

    protected     RtxImageView        imageView_InfoButton;
    protected     ButtonListener      mButtonListener;
    protected     MainActivity        mMainActivity;

    protected   Layout_BulletinList     mLayout_BulletinList;
    protected   Layout_ClassCalendar    mLayout_ClassCalendar;

    protected RtxFillerTextView   fillerTextView_Bulletin;
    protected RtxFillerTextView   fillerTextView_Class;
    protected RtxFillerTextView   fillerTextView_MyClass;
    protected RtxImageView        imageView_ButtonIcon_Bulletin;
    protected RtxImageView        imageView_ButtonIcon_Class;
    protected RtxImageView        imageView_ButtonIcon_MyClass;

    protected RtxImageView        imageView_ReturnExercisePage;

    ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo;

    public MyGymMainLayout(Context context, MainActivity mMainActivity) {
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
        {
            init_TopBackground();
            init_BackHome();
            init_Title();
            vSetTitleText(R.string.my_gym);
        }

        //ImageView
        {
            if(imageView_InfoButton == null)            { imageView_InfoButton = new RtxImageView(mContext); }
            if(mLayout_BulletinList == null)            { mLayout_BulletinList = new Layout_BulletinList(mContext,mMainActivity); }
            if(mLayout_ClassCalendar == null)           { mLayout_ClassCalendar = new Layout_ClassCalendar(mContext,mMainActivity); }

            if(fillerTextView_Bulletin == null)         { fillerTextView_Bulletin = new RtxFillerTextView(mContext); }
            if(fillerTextView_Class == null)            { fillerTextView_Class = new RtxFillerTextView(mContext); }
            if(fillerTextView_MyClass == null)          { fillerTextView_MyClass = new RtxFillerTextView(mContext); }

            if(imageView_ButtonIcon_Bulletin == null)   { imageView_ButtonIcon_Bulletin = new RtxImageView(mContext); }
            if(imageView_ButtonIcon_Class == null)      { imageView_ButtonIcon_Class = new RtxImageView(mContext); }
            if(imageView_ButtonIcon_MyClass == null)    { imageView_ButtonIcon_MyClass = new RtxImageView(mContext); }

            if(imageView_ReturnExercisePage == null)    {   imageView_ReturnExercisePage = new RtxImageView(mContext);  }
        }
    }

    private void init_event()
    {
        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_InfoButton.setOnClickListener(mButtonListener);

        fillerTextView_Bulletin.setOnClickListener(mButtonListener);
        fillerTextView_Class.setOnClickListener(mButtonListener);
        fillerTextView_MyClass.setOnClickListener(mButtonListener);

        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);
    }

    private void vSetFillButtonActivity(RtxFillerTextView fillerTextView ,boolean bActivity)
    {
        if(bActivity)
        {
            fillerTextView.setTextColor(Common.Color.black);
            fillerTextView.setMode(4);

            if(fillerTextView == fillerTextView_Bulletin)
            {
                imageView_ButtonIcon_Bulletin.setImageResource(R.drawable.bulletin_icon_black);
            }
            else
            if(fillerTextView == fillerTextView_Class)
            {
                imageView_ButtonIcon_Class.setImageResource(R.drawable.class_icon_black);
            }
            else
            if(fillerTextView == fillerTextView_MyClass)
            {
                imageView_ButtonIcon_MyClass.setImageResource(R.drawable.myclass_icon_black);
            }
        }
        else
        {
            fillerTextView.setTextColor(Common.Color.yellow_1);
            fillerTextView.setMode(5);

            if(fillerTextView == fillerTextView_Bulletin)
            {
                imageView_ButtonIcon_Bulletin.setImageResource(R.drawable.bulletin_icon_yellow);
            }
            else
            if(fillerTextView == fillerTextView_Class)
            {
                imageView_ButtonIcon_Class.setImageResource(R.drawable.class_icon_yellow);
            }
            else
            if(fillerTextView == fillerTextView_MyClass)
            {
                imageView_ButtonIcon_MyClass.setImageResource(R.drawable.myclass_icon_yellow);
            }
        }
    }

    protected void vSetModeButton(int iMode)
    {
        if(iMode == MODE_BULLETIN)
        {
            vSetFillButtonActivity(fillerTextView_Bulletin,true);
            vSetFillButtonActivity(fillerTextView_Class,false);
            vSetFillButtonActivity(fillerTextView_MyClass,false);
        }
        else
        if(iMode == MODE_CLASS)
        {
            vSetFillButtonActivity(fillerTextView_Bulletin,false);
            vSetFillButtonActivity(fillerTextView_Class,true);
            vSetFillButtonActivity(fillerTextView_MyClass,false);
        }
        else
        //if(iMode == MODE_MY_CLASS)
        {
            vSetFillButtonActivity(fillerTextView_Bulletin,false);
            vSetFillButtonActivity(fillerTextView_Class,false);
            vSetFillButtonActivity(fillerTextView_MyClass,true);
        }

    }

    protected void add_View()
    {
        {
            addRtxTextViewToLayout(fillerTextView_Bulletin, R.string.bulletin, 20.0f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 200, 729, 280, 50, Common.Color.yellow_1);
            fillerTextView_Bulletin.setMode(5);
            addRtxTextViewToLayout(fillerTextView_Class, R.string.class_str, 20.0f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 528, 729, 280, 50, Common.Color.yellow_1);
            fillerTextView_Class.setMode(5);
            addRtxTextViewToLayout(fillerTextView_MyClass, R.string.my_class, 20.0f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 855, 729, 280, 50, Common.Color.yellow_1);
            fillerTextView_MyClass.setMode(5);

            addRtxImageViewToLayout(imageView_ButtonIcon_Bulletin,R.drawable.bulletin_icon_yellow,450,742,19,24);
            addRtxImageViewToLayout(imageView_ButtonIcon_Class,R.drawable.class_icon_yellow,770,743,28,23);
            addRtxImageViewToLayout(imageView_ButtonIcon_MyClass,R.drawable.myclass_icon_yellow,1092,742,29,24);

            imageView_ButtonIcon_Bulletin.setClickable(false);
            imageView_ButtonIcon_Class.setClickable(false);
            imageView_ButtonIcon_MyClass.setClickable(false);

            imageView_ButtonIcon_Bulletin.setEnabled(false);
            imageView_ButtonIcon_Class.setEnabled(false);
            imageView_ButtonIcon_MyClass.setEnabled(false);
        }

        addRtxImagePaddingViewToLayout(imageView_InfoButton, R.drawable.info_icon, 800, 40, 34, 34, 30);//By Alan

        if(iModeSelect == MODE_BULLETIN)
        {
            removeView(mLayout_ClassCalendar);

            if(mLayout_BulletinList == null)
            {
            }
            else
            {
                addViewToLayout(mLayout_BulletinList, 0, 104, 1280, 593);
                mLayout_BulletinList.display();
            }
        }
        else
        if(iModeSelect == MODE_CLASS)
        {
            removeView(mLayout_BulletinList);

            addViewToLayout(mLayout_ClassCalendar,0,104,1280,593);
            mLayout_ClassCalendar.display(iModeSelect);
        }
        else
        if(iModeSelect == MODE_MY_CLASS)
        {
            removeView(mLayout_BulletinList);

            addViewToLayout(mLayout_ClassCalendar,0,104,1280,593);
            mLayout_ClassCalendar.display(iModeSelect);
        }

        vSetModeButton(iModeSelect);
    }

    protected void vSetModeSelect(int iMode)
    {
        iModeSelect = iMode;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetWeekSeletIndex(int iIndex)
    {
        if(mLayout_ClassCalendar != null)
        {
            mLayout_ClassCalendar.vSetWeekSeletIndex(iIndex);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void updateBitmap()
    {
        if(iModeSelect == MODE_BULLETIN)
        {

        }
        else
        if(iModeSelect == MODE_CLASS)
        {
            if(mLayout_ClassCalendar != null)
            {
                if(mLayout_ClassCalendar.layout_ClassList != null)
                {
                    mLayout_ClassCalendar.layout_ClassList.vUpdateBlockBitmap();
                }
            }
        }
        else
        if(iModeSelect == MODE_MY_CLASS)
        {

        }
    }

    //Bulletin
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void updateBulletinList(ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        mLayout_BulletinList.updateDataList(list_CloudBulletinInfo);
    }

    protected void setBulletinDataList(final ArrayList<CloudDataStruct.CLOUD_BULLETIN_INFO> list_CloudBulletinInfo)
    {
        this.list_CloudBulletinInfo = list_CloudBulletinInfo;


        if(mLayout_BulletinList != null)
        {
            mLayout_BulletinList.setDataList(this.list_CloudBulletinInfo);

            mLayout_BulletinList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int iIndex = (int)id;

                    if(iIndex < list_CloudBulletinInfo.size())
                    {

                        mMainActivity.mMainProcBike.myGymProc.vSetBulletinID(iIndex);
                        mMainActivity.mMainProcBike.myGymProc.vSetNextState(MyGymState.PROC_SHOW_PAGE_BULLETIN_DETAIL);
                    }
                }
            });
        }
//        else
//        {
//            updateBulletinList(this.list_CloudBulletinInfo);
//        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vBackHome()
    {
        mMainActivity.mMainProcBike.myGymProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vShowInfoDialog()
    {
        //mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_ICON_TITLE_INFO,R.drawable.main_icon_my_gym,R.string.my_gym,R.string.mygym_dialog_info);

        String stitle01 = LanguageData.s_get_string(mContext, R.string.my_gym);
        String sinfo01 = LanguageData.s_get_string(mContext, R.string.mygym_dialog_info).toUpperCase();

        Dialog_UI_Info.v_tist_Dialog(R.drawable.main_icon_my_gym, -1, stitle01, null, sinfo01, null, "mg_information", ImageView.ScaleType.FIT_XY);
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_INFO);
    }

    private void vClickBulletinButton()
    {
        mMainActivity.mMainProcBike.myGymProc.vClearSearchList();

        if(iModeSelect != MODE_BULLETIN)
        {
            vSetModeButton(MODE_BULLETIN);
            iModeSelect = MODE_BULLETIN;
            vSetWeekSeletIndex(0);
            removeAllViews();
            display();
        }
    }

    private void vClickClassButton()
    {
        mMainActivity.mMainProcBike.myGymProc.vClearSearchList();

        if(iModeSelect != MODE_CLASS)
        {
            vSetModeButton(MODE_CLASS);
            iModeSelect = MODE_CLASS;
            vSetWeekSeletIndex(0);
            removeAllViews();
            display();
        }
    }

    private void vClickMyClassButton()
    {
        mMainActivity.mMainProcBike.myGymProc.vClearSearchList();

        if(iModeSelect != MODE_MY_CLASS)
        {
            vSetModeButton(MODE_MY_CLASS);
            iModeSelect = MODE_MY_CLASS;
            vSetWeekSeletIndex(0);
            removeAllViews();
            display();
        }
    }

    protected void vClickReturnExercisePage()
    {

    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)                                         { vBackHome(); }
            else if(v == imageView_InfoButton)                                  { vShowInfoDialog(); }
            else if(v == fillerTextView_Bulletin)                               { vClickBulletinButton(); }
            else if(v == fillerTextView_Class)                                  { vClickClassButton(); }
            else if(v == fillerTextView_MyClass)                                { vClickMyClassButton(); }
            else if(v == imageView_ReturnExercisePage)                          { vClickReturnExercisePage(); }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
