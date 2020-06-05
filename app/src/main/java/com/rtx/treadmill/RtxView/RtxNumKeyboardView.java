package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;

import java.lang.reflect.Method;
import java.util.List;


public class RtxNumKeyboardView extends KeyboardView {

    final String    FONT_PATH = Common.Font.Relay_BlackItalic;
    final int       FONT_TYPE = Typeface.NORMAL;
    final float     FONT_SIZE = 50.18f;


    Context mContext;

    float   fCircleRudius;

    int     iColor_KeyBackground_Press;
    int     iColor_KeyBackground_Normal;
    int     iColor_Text;

    String  sFontPath;
    int     iFontStyle;

    Paint mPaint_KeyBackground_Press = null;
    Paint mPaint_KeyBackground_Normal = null;
    Paint mPaint_Text;

    Keyboard mKeyboardNumber;

    public RtxNumKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initDefault();
        initPaint();
    }

    public RtxNumKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initDefault();
        initPaint();
    }

    private void initDefault()
    {
        fCircleRudius = 40;

        iColor_KeyBackground_Press = Color.YELLOW;
        iColor_KeyBackground_Normal = Color.WHITE;
        iColor_Text = Common.Color.background;

        sFontPath = FONT_PATH;
        iFontStyle = FONT_TYPE;

        mKeyboardNumber = new Keyboard(mContext, R.xml.view_keyboard_mode1);
        setKeyboard(mKeyboardNumber);

        setEnabled(true);
        setPreviewEnabled(false);
        setVisibility(View.VISIBLE);
        setBackgroundColor(Common.Color.transparent);
    }

    public void setCircleRudius(float fRudius)
    {
        fCircleRudius = fRudius;
    }

    public void setPressKeyBackgroundColor(int iColor)
    {
        iColor_KeyBackground_Press = iColor;
        initPaint();
    }

    public void setNormalKeyBackgroundColor(int iColor)
    {
        iColor_KeyBackground_Normal = iColor;
        initPaint();
    }

    public void setFontPath(String sPath)
    {
        sFontPath = sPath;
        initPaint();
    }

    public void setFontStyle(int iStyle)
    {
        iFontStyle = iStyle;
        initPaint();
    }

    public void setKeyMode(int xmlLayoutResId)
    {
        mKeyboardNumber = new Keyboard(mContext, xmlLayoutResId);
        setKeyboard(mKeyboardNumber);
    }

    private void initPaint()
    {
        {
            if (mPaint_KeyBackground_Press == null) {
                mPaint_KeyBackground_Press = new Paint();
            }

            mPaint_KeyBackground_Press.setColor(iColor_KeyBackground_Press);
            mPaint_KeyBackground_Press.setAntiAlias(true);
        }

        {
            if (mPaint_KeyBackground_Normal == null) {
                mPaint_KeyBackground_Normal = new Paint();
            }

            mPaint_KeyBackground_Normal.setColor(iColor_KeyBackground_Normal);
            mPaint_KeyBackground_Normal.setAntiAlias(true);
        }

        {
            if (mPaint_Text == null) {
                mPaint_Text = new Paint();
            }


            Typeface font = Typeface.createFromAsset(mContext.getAssets(), sFontPath);
            Typeface type = Typeface.create(font, iFontStyle);
            mPaint_Text.setTypeface(type);
            mPaint_Text.setAntiAlias(true);
            mPaint_Text.setTextSize(FONT_SIZE);
            //mPaint_Text.setTextSize(fCircleRudius);
            mPaint_Text.setColor(iColor_Text);
        }
    }

    private void drawCenter(Canvas canvas, Rect r, Paint paint, String str)
    {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        int x = r.left + (r.width() / 2) - (bounds.width() / 2);
        int y = r.top + (r.height() / 2) - (bounds.height() / 2) + bounds.height();
        canvas.drawText(str, x, y, paint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        List<Keyboard.Key> keys = getKeyboard().getKeys();

        for (Keyboard.Key key : keys)
        {
            if(key.pressed)
            {
                //Log.e("Jerry","key.pressed");
                canvas.drawCircle(((key.x + key.x + key.width) / 2),((key.y + key.y + key.height) / 2),fCircleRudius,mPaint_KeyBackground_Press);
            }
            else
            {
                //Log.e("Jerry","else");
                canvas.drawCircle(((key.x + key.x + key.width) / 2),((key.y + key.y + key.height) / 2),fCircleRudius,mPaint_KeyBackground_Normal);
            }

            if (key.label != null)
            {
                Rect rect = new Rect();
                rect.left = key.x;
                rect.top = key.y;
                rect.right = key.x + key.width;
                rect.bottom = key.y + key.height;

                drawCenter(canvas,rect,mPaint_Text,key.label.toString());
            }
            else
            {
                int iDrableW = key.icon.getBounds().width();
                int iDrableH = key.icon.getBounds().height();

                int iLeft = key.x + ((key.width - key.icon.getBounds().width()) / 2);
                int iTop = key.y + ((key.height - key.icon.getBounds().height()) / 2);


                key.icon.setBounds(iLeft,iTop,iLeft+iDrableW,iTop+iDrableH);
                key.icon.draw(canvas);
            }
        }
    }

    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void setOnKeyboardActionListener(final EditText editText , final int iLen) {
        OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable = editText.getText();
                editText.setSelection(editText.getText().toString().length());
                int start = editText.getSelectionStart();
                if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                } else {
                    if (start < iLen) {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                }
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };

        this.setOnKeyboardActionListener(mOnKeyboardActionListener);
    }
}
