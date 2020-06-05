package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

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
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainDetailBaseLayout extends Rtx_BaseLayout {

    int iColor_Red = 0xFFFF5050;
    int iColor_Yellow = 0xFFFBF000;
    //int iColor_Green = 0xFF4FECBE;
    int iColor_Green = iColor_Red;

    protected int MODE_NORMAL = 0;
    protected int MODE_EXERCISE = 1;

    private Context mContext;

    public RtxImageView    imageView_Close;
    public RtxImageView    imageView_Delete;
    public RtxImageView    imageView_Share;

    private RtxDoubleStringView doubleStringView_Val_1;
    private RtxDoubleStringView doubleStringView_Val_2;
    private RtxDoubleStringView doubleStringView_Val_3;
    private RtxDoubleStringView doubleStringView_Val_4;

    private RtxTextView         textView_Item_1;
    private RtxTextView         textView_Item_2;
    private RtxTextView         textView_Item_3;
    private RtxTextView         textView_Item_4;

    private RtxFillerTextView   textView_LeftVal;
    private RtxTextView         textView_LeftText;

    private int                 iMode = -1;
//    private RtxImageView        imageView_InputIcon;
//    private RtxImageView        imageView_InputText;

    private     MainActivity        mMainActivity;

    public TargetTrainDetailBaseLayout(Context context, MainActivity mMainActivity, int iMode) {
        super(context);

        mContext = context;

        this.iMode = iMode;

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


    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    protected void init_TargetBaseView()
    {
        {
            init_TopBackground();
            init_Title();
            init_BackPrePage();
        }

        {
            if(imageView_Close == null)             { imageView_Close = new RtxImageView(mContext); }
            if(imageView_Delete == null)            { imageView_Delete = new RtxImageView(mContext); }
            if(imageView_Share == null)             { imageView_Share = new RtxImageView(mContext); }

            if(textView_LeftVal == null)            { textView_LeftVal = new RtxFillerTextView(mContext); }
            if(textView_LeftText == null)           { textView_LeftText = new RtxTextView(mContext); }

            if(doubleStringView_Val_1 == null)      { doubleStringView_Val_1 = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_2 == null)      { doubleStringView_Val_2 = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_3 == null)      { doubleStringView_Val_3 = new RtxDoubleStringView(mContext); }
            if(doubleStringView_Val_4 == null)      { doubleStringView_Val_4 = new RtxDoubleStringView(mContext); }

            if(textView_Item_1 == null)             { textView_Item_1 = new RtxTextView(mContext); }
            if(textView_Item_2 == null)             { textView_Item_2 = new RtxTextView(mContext); }
            if(textView_Item_3 == null)             { textView_Item_3 = new RtxTextView(mContext); }
            if(textView_Item_4 == null)             { textView_Item_4 = new RtxTextView(mContext); }
        }
    }

    protected void add_TargetBaseView()
    {
        {
            addRtxImagePaddingViewToLayout(imageView_Close, R.drawable.close, 958, 39, 36, 36, 30);
            addRtxImagePaddingViewToLayout(imageView_Delete, R.drawable.delete, 1057, 43, 25, 29, 30);
            addRtxImagePaddingViewToLayout(imageView_Share, R.drawable.share, 1157, 43, 39, 31, 30);
        }

        vRtxBaseLayoutE_SetShareView(imageView_Share);
    }

    protected void add_TargetDaysLeftView()
    {
        if(iMode == MODE_NORMAL)
        {
            addRtxTextViewToLayout(textView_LeftVal, -1, 59.97f, Common.Color.black_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1025, 800 - 549, 131, 131, Common.Color.blue_1);
            addRtxTextViewToLayout(textView_LeftText, R.string.days_left, 26.67f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 1021 - 50, 800 - 386 - 20, 139 + 100, 20 + 40);
        }
        else
        {
            addRtxTextViewToLayout(textView_LeftVal, -1, 59.97f, Common.Color.black_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 1030, 219, 118, 118, Common.Color.blue_1);
            addRtxTextViewToLayout(textView_LeftText, R.string.days_left, 26.67f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 1005, 345, 168, 58);
        }

        {
            textView_LeftVal.setText("-");
            textView_LeftVal.setMode(1);
        }
    }

    protected void add_Botton_Item_1(int iPosX, int iW)
    {
        add_Bottom_Item(doubleStringView_Val_1,textView_Item_1,iPosX,iW);
    }

    protected void add_Botton_Item_2(int iPosX, int iW)
    {
        add_Bottom_Item(doubleStringView_Val_2,textView_Item_2,iPosX,iW);
    }

    protected void add_Botton_Item_3(int iPosX, int iW)
    {
        add_Bottom_Item(doubleStringView_Val_3,textView_Item_3,iPosX,iW);
    }

    protected void add_Botton_Item_4(int iPosX, int iW)
    {
        add_Bottom_Item(doubleStringView_Val_4,textView_Item_4,iPosX,iW);
    }

    protected void set_Bottom_Item_1(String sVal, String sUnit, int iResID)
    {
        doubleStringView_Val_1.setText(sVal,sUnit);
        textView_Item_1.setText(LanguageData.s_get_string(mContext, iResID));
    }

    protected void set_Bottom_Item_2(String sVal, String sUnit, int iResID)
    {
        doubleStringView_Val_2.setText(sVal,sUnit);
        textView_Item_2.setText(LanguageData.s_get_string(mContext, iResID));
    }

    protected void set_Bottom_Item_3(String sVal, String sUnit, int iResID)
    {
        doubleStringView_Val_3.setText(sVal,sUnit);
        textView_Item_3.setText(LanguageData.s_get_string(mContext, iResID));
    }

    protected void set_Bottom_Item_4(String sVal, String sUnit, int iResID)
    {
        doubleStringView_Val_4.setText(sVal,sUnit);
        textView_Item_4.setText(LanguageData.s_get_string(mContext, iResID));
    }

    protected void set_Bottom_ItemVal_1(String sVal, String sUnit)
    {
        doubleStringView_Val_1.setText(sVal,sUnit);
    }

    protected void set_Bottom_ItemVal_2(String sVal, String sUnit)
    {
        doubleStringView_Val_2.setText(sVal,sUnit);
    }

    protected void set_Bottom_ItemVal_3(String sVal, String sUnit)
    {
        doubleStringView_Val_3.setText(sVal,sUnit);
    }

    protected void set_Bottom_ItemVal_4(String sVal, String sUnit)
    {
        doubleStringView_Val_4.setText(sVal,sUnit);
    }

    private void add_Bottom_Item(RtxDoubleStringView doubleStringView , RtxTextView textView , int iPosX , int iW)
    {
        iW = iW + 200;
        iPosX = iPosX - 100;

        doubleStringView.setPaint(Common.Font.Relay_Black,Common.Color.white,66.59f,Common.Font.Relay_BoldItalic,Common.Color.blue_1,33.05f);

        if(iMode == MODE_NORMAL)
        {
            addViewToLayout(doubleStringView,iPosX,614,iW,89);
            addRtxTextViewToLayout(textView, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, 699, iW, 55);
        }
        else
        {
            addViewToLayout(doubleStringView,iPosX,514,iW,82);
            addRtxTextViewToLayout(textView, 20f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, 593, iW, 53);
        }
    }

    protected void setLeftString(String sVal)
    {
        textView_LeftVal.setText(sVal);
    }

    protected void vDelete()
    {

    }

    protected void vClose()
    {

    }

    protected void vShowDeleteDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE,LanguageData.s_get_string(mContext,R.string.delete_description),-1);

        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vDelete();
            }
        });
    }

    protected void vShowCloseDialog()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TARGET_CLOSE,-1,-1);

        mMainActivity.dialogLayout_TargetClose.fillerTextView_Close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vClose();
            }
        });
    }

    protected void vShowCloseInfoDialog(CloudDataStruct.CLOUD_TATGET_GOAL_CLOSE goal_Close)
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TARGET_CLOSE_INFO,goal_Close,false);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected int iGetValColor(float fStartVal, float fTargetVal, float fCurrentVal)
    {
        int iColor = 0x00000000;

        if(fStartVal < fTargetVal)
        {
            if(fCurrentVal < fStartVal)
            {
                iColor = iColor_Green;
            }
            else
            if(fCurrentVal > fTargetVal)
            {
                iColor = iColor_Red;
            }
            else
            {
                iColor = iColor_Yellow;
            }
        }
        else
        {
            if(fCurrentVal < fTargetVal)
            {
                iColor = iColor_Green;//green
            }
            else
            if(fCurrentVal > fStartVal)
            {
                iColor = iColor_Red;//red
            }
            else
            {
                iColor = iColor_Yellow;//yellow
            }
        }

        return iColor;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected String sGetOrderStr(int iNum)
    {
        String sVal;
        String sUnit;

        int iDigitInOnes = 0;

        iDigitInOnes = iNum % 10;

        if(iDigitInOnes == 1)
        {
            if(iNum == 11)
            {
                sUnit = "th";
            }
            else
            {
                sUnit = "st";
            }
        }
        else if(iDigitInOnes == 2)
        {
            if(iNum == 12)
            {
                sUnit = "th";
            }
            else
            {
                sUnit = "nd";
            }
        }
        else if(iDigitInOnes == 3)
        {
            if(iNum == 13)
            {
                sUnit = "th";
            }
            else
            {
                sUnit = "rd";
            }
        }
        else
        {
            sUnit = "th";
        }

        sVal = Rtx_TranslateValue.sInt2String(iNum) + sUnit;

        return sVal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
