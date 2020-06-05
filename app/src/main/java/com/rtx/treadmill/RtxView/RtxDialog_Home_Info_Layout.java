package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Home_Info_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    private RtxTextView         textView_InfoTitle;
    private RtxHomeInfoItemScrollView   textScrollView_Contents;
    private RtxImageView        imageView_Icon;
    public RtxFillerTextView   fillerTextView_OK;

    private int iCurrentPageIndex = -1;

    private UiDataStruct.HOME_USER_INFO userInfo;

    public RtxDialog_Home_Info_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void display()
    {
        init_View();
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
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
            if(imageView_Icon == null)              { imageView_Icon = new RtxImageView(mContext);   }
            if(textView_InfoTitle == null)          { textView_InfoTitle = new RtxTextView(mContext);   }
            if(textScrollView_Contents == null)     { textScrollView_Contents = new RtxHomeInfoItemScrollView(mContext);     }
            if(fillerTextView_OK == null)           { fillerTextView_OK = new RtxFillerTextView(mContext);     }
        }
    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,667);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxImagePaddingViewToLayout(imageView_Icon, -1, -1, 147-64, 35, 26, 0);
        addRtxTextViewToLayout(textView_InfoTitle, 27.99f, Common.Color.blue_6, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 168-25, 1002, 70);
        addRtxViewToLayout(textScrollView_Contents,  -1, 249, 740, 238, 0);
        //textScrollView_Contents.textView.setLineSpacing(1.0f,1.5f);
        addRtxTextViewToLayout(fillerTextView_OK, R.string.ok, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 520, 378, 75, Common.Color.login_button_yellow);
    }

    public void setContents(UiDataStruct.HOME_USER_INFO userInfo)
    {
        this.userInfo = userInfo;

        iCurrentPageIndex = -1;
        imageView_Icon.setImageResource(userInfo.iInfoIcon);

        if(userInfo.treeMap_UserInfoDialog != null)
        {
            int iSize = userInfo.treeMap_UserInfoDialog.size();

            if(iSize > 0)
            {
                iCurrentPageIndex = 0;
                Integer iKey = -1;
                UiDataStruct.HOME_USER_INFO_DIALOG userInfoDialog;
                iKey = (Integer)userInfo.treeMap_UserInfoDialog.keySet().toArray()[iCurrentPageIndex];
                userInfoDialog = userInfo.treeMap_UserInfoDialog.get(iKey);
                textView_InfoTitle.setText(userInfoDialog.sTitle);

                String sContents = "";
                textScrollView_Contents.clear();

                if(iKey == 0)
                {
                    UiDataStruct.HOME_USER_INFO_ITEM_DIALOG userInfoItemDialog;
                    userInfoItemDialog = userInfoDialog.list_InfoItem.get(0);

                    textScrollView_Contents.addItem(-1,userInfoItemDialog.sContents);
                }
                else
                {
                    int iItemSize = userInfoDialog.list_InfoItem.size();
                    int iItemIndex = 0;

                    for ( ; iItemIndex < iItemSize ; iItemIndex++ )
                    {
                        UiDataStruct.HOME_USER_INFO_ITEM_DIALOG userInfoItemDialog;
                        userInfoItemDialog = userInfoDialog.list_InfoItem.get(iItemIndex);

                        textScrollView_Contents.addItem(userInfoItemDialog.iImgResId_Tag, userInfoItemDialog.sContents);
                    }
                }
            }
        }
    }

    public boolean bNextPage()
    {
        boolean bResult = false;

        iCurrentPageIndex ++ ;
        imageView_Icon.setImageResource(userInfo.iInfoIcon);

        if(userInfo.treeMap_UserInfoDialog != null)
        {
            int iSize = userInfo.treeMap_UserInfoDialog.size();

            if(iSize > iCurrentPageIndex)
            {
                Integer iKey = -1;
                UiDataStruct.HOME_USER_INFO_DIALOG userInfoDialog;
                iKey = (Integer)userInfo.treeMap_UserInfoDialog.keySet().toArray()[iCurrentPageIndex];
                userInfoDialog = userInfo.treeMap_UserInfoDialog.get(iKey);
                textView_InfoTitle.setText(userInfoDialog.sTitle);

                String sContents = "";

                {
                    int iItemSize = userInfoDialog.list_InfoItem.size();
                    int iItemIndex = 0;

                    textScrollView_Contents.clear();

                    for ( ; iItemIndex < iItemSize ; iItemIndex++ )
                    {
                        UiDataStruct.HOME_USER_INFO_ITEM_DIALOG userInfoItemDialog;
                        userInfoItemDialog = userInfoDialog.list_InfoItem.get(iItemIndex);

                        textScrollView_Contents.addItem(userInfoItemDialog.iImgResId_Tag, userInfoItemDialog.sContents);
                    }
                }

                bResult = true;
            }
        }

        return bResult;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
