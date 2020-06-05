package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.NumberScroll.NumberPicker;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxView.RtxCircularDuration;
import com.rtx.treadmill.RtxView.RtxCircularSeekBar;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumberWheel;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

import java.lang.reflect.Field;


/**
 * Created by jerry on 2016/12/29.
 */

public class Rtx_BaseLayout extends FrameLayout
{
    Context mContext;

    public  RtxImageView imageView_BackPrePage;
    private RtxTextView textView_Title;
    public  RtxTextView textView_SubTitle;
    public  RtxImageView imageView_BackHome;
    private RtxView view_TopBlockBackground;
    public  RtxView view_Background;
    public  RtxView view_Mask=null;

    public Rtx_BaseLayout(Context context){
        super(context);
        mContext=context;

        this.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void init(){

    }

    public void display(){

    }

    public void init_Background(){
        if(view_Background == null){ view_Background = new RtxView(mContext); }
        view_Background.setBackgroundColor(Common.Color.transparent);
        addViewToLayout(view_Background, 0, 0, 1280, 800);
    }

    public void init_ViewMask(){
//        if(view_Mask == null)   { view_Mask = new RtxView(mContext); }
//        addRtxViewToLayout(view_Mask,0,0,1280,800, Common.Color.background);
    }

    public void vVisibleMaskView(boolean bVisible){
        if(view_Mask != null){
            if(bVisible){
                if(view_Mask.getVisibility() == INVISIBLE){ view_Mask.setVisibility(VISIBLE); }
            }else{
                if(view_Mask.getVisibility() == VISIBLE){ view_Mask.setVisibility(INVISIBLE); }
            }
        }
    }

    public void init_TopBackground(){
        if(view_TopBlockBackground == null){ view_TopBlockBackground = new RtxView(mContext); }
        view_TopBlockBackground.setBackgroundColor(Common.Color.background_TopBar);
        addViewToLayout(view_TopBlockBackground, 0, 0, 1280, 104);
    }

    public void init_BackPrePage(){
        if(imageView_BackPrePage == null){ imageView_BackPrePage = new RtxImageView(mContext); }
        addRtxImagePaddingViewToLayout(imageView_BackPrePage, R.xml.back, 73, 41, 45, 35, 80);
    }

    public void init_BackHome(){
        if(imageView_BackHome == null){ imageView_BackHome = new RtxImageView(mContext); }
        addRtxImagePaddingViewToLayout(imageView_BackHome, R.xml.home, 1173, 38, 39, 39, 50);
    }

    public void init_Title(){
        if(textView_Title == null){ textView_Title = new RtxTextView(mContext); }
        addRtxTextViewToLayout(textView_Title, 28.00f, Common.Color.main_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 38, 800, 39);//By Alan
    }

    public void init_SubTitle(){
        if(textView_SubTitle == null){ textView_SubTitle = new RtxTextView(mContext); }
        addRtxTextViewToLayout(textView_SubTitle, 29.68f, Common.Color.login_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 133, 800, 36);
    }

    public void vSetTitleText(int iResId){
        if(textView_Title == null){ init_Title(); }

        textView_Title.setText(LanguageData.s_get_string(mContext, iResId));
    }

    public void vSetTitleText(String sStr)
    {
        if(textView_Title == null)  { init_Title(); }

        textView_Title.setText(sStr);
    }

    public void vSetSubTitleText(int iResId){
        if(textView_SubTitle == null){ init_SubTitle(); }
        textView_SubTitle.setText(LanguageData.s_get_string(mContext, iResId));
    }

    public void vSetSubTitleText(String str){
        if(textView_SubTitle == null){ init_SubTitle(); }
        if(str != null){ textView_SubTitle.setText(str); }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addViewToLayout(View view , int iX , int iY , int iWidth , int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addViewToLayout!!!");
            return;
        }
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        if(iX == -1){
            if(iY == -1){ mLayoutParams.gravity = Gravity.CENTER; }
            else{
                mLayoutParams.gravity=Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                mLayoutParams.topMargin=iY;
            }
        }else if(iY == -1){
            mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            mLayoutParams.leftMargin = iX;
        }else{
            mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            mLayoutParams.leftMargin = iX;
            mLayoutParams.topMargin = iY;
        }
        view.setLayoutParams(mLayoutParams);
        try {
            addView(view);
        }catch (Exception e){
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }

    public void addViewToLayout(FrameLayout mFrame, View view , int iX , int iY , int iWidth , int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addViewToLayout!!!");
            return;
        }
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        if(iX == -1){ mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP; }
        else{
            mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            mLayoutParams.leftMargin = iX;
        }
        mLayoutParams.topMargin = iY;
        view.setLayoutParams(mLayoutParams);
        try {
            if(mFrame != null){ mFrame.addView(view); }
            else{ addView(view); }
        }catch(Exception e){
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRtxViewToLayout(View view , int iX , int iY , int iWidth , int iHeight , int iColor){
        view.setBackgroundColor(iColor);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPaddingViewToLayout(View view , int iX , int iY , int iWidth , int iHeight , int iPadding){
        setViewPadding(view , iPadding);
        addViewToLayout(view , iX - iPadding , iY - iPadding , iWidth + (2*iPadding) , iHeight + (2*iPadding));
    }

    public void addPaddingViewToLayout(FrameLayout mFrame, View view , int iX , int iY , int iWidth , int iHeight , int iPadding){
        setViewPadding(view , iPadding);
        addViewToLayout(mFrame, view , iX - iPadding , iY - iPadding , iWidth + (2*iPadding) , iHeight + (2*iPadding));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addRtxImageViewToLayout(RtxImageView view , int resId , int iX , int iY , int iWidth , int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxImageViewToLayout!!!");
            return;
        }
        if(resId != -1){ view.setImageResource(resId); }
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        view.setBackgroundColor(0x00000000);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxImagePaddingViewToLayout(RtxImageView view , int iResID , int iX , int iY , int iWidth , int iHeight , int iPadding){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }

        if(iResID != -1){ view.setImageResource(iResID); }
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        view.setBackgroundColor(0x00000000);
        addPaddingViewToLayout(view,iX,iY,iWidth,iHeight,iPadding);
    }

    public void addRtxImage(FrameLayout mFrame, RtxImageView view , int iResID , int iX , int iY , int iWidth , int iHeight , int iPadding , ImageView.ScaleType scaletype){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxImage!!!");
            return;
        }
        if(iResID != -1){ view.setImageResource(iResID); }
        if(scaletype == null){ view.setScaleType(ImageView.ScaleType.CENTER_INSIDE); }
        else{ view.setScaleType(scaletype); }
        view.setBackgroundColor(0x00000000);
        if(mFrame == null){ addPaddingViewToLayout(view, iX, iY, iWidth, iHeight, iPadding); }
        else{ addPaddingViewToLayout(mFrame, view, iX, iY, iWidth, iHeight, iPadding); }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setTextArg(RtxTextView view, float fSize, int iColor, String sFont, int style, int gravity){
        view.setGravity(gravity);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);
        view.setTextColor(iColor);
        view.setTypeface(view.getTypeface(), style);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), sFont));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setViewPadding(View view , int iPadding){
        view.setPadding(iPadding,iPadding,iPadding,iPadding);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addRtxTextViewToLayout(RtxTextView view, float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextViewToLayout(RtxTextView view, int iResID,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        if(iResID != -1){ view.setText(LanguageData.s_get_string(mContext, iResID)); }
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextViewToLayout(RtxTextView view, int iResID,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight, int iBackgroundColor){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        if(iResID != -1){ view.setText(LanguageData.s_get_string(mContext, iResID)); }
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        view.setBackgroundColor(iBackgroundColor);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextViewToLayout(RtxTextView view, String sStr,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight, int iBackgroundColor){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        if(sStr != null){ view.setText(sStr); }
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        view.setBackgroundColor(iBackgroundColor);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextViewToLayout(RtxTextView view, String sStr, float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight) {
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        view.setText(sStr);
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextView(FrameLayout mFrame, RtxTextView view, String sStr,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight, int iBackgroundColor){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        view.setText(sStr);
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        view.setBackgroundColor(iBackgroundColor);
        addViewToLayout(mFrame, view,iX,iY,iWidth,iHeight);
    }

    public void addRtxTextView(FrameLayout mFrame, RtxTextView view, String sStr,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        view.setText(sStr);
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        addViewToLayout(mFrame, view,iX,iY,iWidth,iHeight);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addRtxDoubleStringView(RtxDoubleStringView view, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextViewToLayout!!!");
            return;
        }
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addRtxText(RtxTextView view, int iResID,float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight , int iPadding){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextPaddingViewToLayout!!!");
            return;
        }
        view.setText(LanguageData.s_get_string(mContext, iResID));
        setTextArg(view, fSize, iColor, sFont, style, gravity);
        addPaddingViewToLayout(view,iX,iY,iWidth,iHeight,iPadding);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addRtxEditViewToLayout(RtxEditText view, int iType, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxEditViewToLayout!!!");
            return;
        }
        view.setBackgroundColor(0xffffffff);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,53.82f);
        view.setGravity(Gravity.CENTER);
        try{
            Field f=TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(view, R.drawable.cursor);
        }catch(Exception e){
            e.printStackTrace();
        }
        view.setInputType(iType);
        //view.requestFocus();
        view.setSingleLine(true);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    @Override
    public void removeAllViews(){
        try{
            super.removeAllViews();
        }catch(Exception e){
            Log.e("Jerry","removeAllViews e = " + e);
        }
    }

    @Override
    public void removeView(View view){
        try{
            super.removeView(view);
        }catch(Exception e){
            Log.e("Jerry","removeView e = " + e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRtxTextScrollViewToLayout(RtxTextScrollView view, float fSize, int iColor, String sFont, int style, int gravity, int iX, int iY, int iWidth, int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addRtxTextScrollViewToLayout!!!");
            return;
        }
        view.setTextArg(fSize, iColor, sFont, style, gravity);
        addViewToLayout(view,iX,iY,iWidth,iHeight);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void setNumberPickerArg(NumberPicker view, int iMin, int iMax, String[] sDisplayVal, int iVal, RtxNumberWheel.OnValueChangeListener np){
        view.setMinValue(iMin);
        view.setMaxValue(iMax);
        view.setDisplayedValues(sDisplayVal);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setValue(iVal);
        view.setDescendantFocusability(RtxNumberWheel.FOCUS_BLOCK_DESCENDANTS);
        view.setOnValueChangedListener(np);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setTextViewSpan(EditText view, int fontSize, int start, int end, int color){
        Spannable span = new SpannableString(view.getText());
        span.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(span);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setViewTouchDisable(View view){
        view.setClickable(false);
        view.setEnabled(false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addViewToLayout(RtxCircularSeekBar view , int iX , int iY , int iWidth , int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addViewToLayout!!!");
            return;
        }
        view.vSet_WH(iWidth, iHeight);
        try{
            addViewToLayout((View)view, iX, iY, iWidth, iHeight);
        }catch(Exception e){
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }

    public void addViewToLayout(RtxCircularDuration view , int iX , int iY , int iWidth , int iHeight){
        if(view == null){
            Rtx_Log.Log_Error("view is null in addViewToLayout!!!");
            return;
        }
        view.vSet_WH(iWidth, iHeight);
        try{
            addViewToLayout((View)view, iX, iY, iWidth, iHeight);
        }catch(Exception e){
            Rtx_Log.Log_Error(e.getMessage());
            return;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void vRtxBaseLayoutE_SetShareView(RtxImageView mRtxImageView){
        if(Rtx_Debug.bRtxDebug_GetShareEnable()){ mRtxImageView.setVisibility(VISIBLE); }
        else{ mRtxImageView.setVisibility(INVISIBLE); }
    }

    public void vRtxBaseLayoutE_SetShareView(RtxTextView mRtxTextView){
        if(Rtx_Debug.bRtxDebug_GetShareEnable()){ mRtxTextView.setVisibility(VISIBLE); }
        else{ mRtxTextView.setVisibility(INVISIBLE); }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
