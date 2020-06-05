package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by jerry on 2016/12/29.
 */

public class Layout_ClassBlock extends Rtx_BaseLayout {

    Context mContext;
    int iLayoutWidth = 149;
    int iLayoutHeight = 262;

    RtxTextView textView_Time;
    RtxTextView textView_Instructor;
    RtxTextView textView_Name;
    RtxImageView imageView_Pic;

    String sTime = null;
    String sInstructor = null;
    String sClassName = null;

    Paint paintLine;

    int iBlockHeight = 0;
    String sCLS_ID = null;

    public Layout_ClassBlock(Context context, String sTime, String sInstructor, String sClassName, String sCLS_ID) {
        super(context);

        mContext = context;

        this.sTime = sTime;
        this.sInstructor = sInstructor;
        this.sClassName = sClassName;
        this.sCLS_ID = sCLS_ID;

        init();
        display();
    }

    @Override
    public void init()
    {

        setBackgroundColor(getResources().getColor(R.color.activity_background));
        init_View();
        init_Paint();
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    private void init_View()
    {

        {
            if(textView_Time == null)           { textView_Time = new RtxTextView(mContext); }
            if(textView_Instructor == null)     { textView_Instructor = new RtxTextView(mContext); }
            if(textView_Name == null)           { textView_Name = new RtxTextView(mContext); }
            if(imageView_Pic == null)           { imageView_Pic = new RtxImageView(mContext); }

            if(paintLine == null)               { paintLine = new Paint(); }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        textView_Time.setClickable(false);
        textView_Instructor.setClickable(false);
        textView_Name.setClickable(false);
        imageView_Pic.setClickable(false);

        textView_Time.setEnabled(false);
        textView_Instructor.setEnabled(false);
        textView_Name.setEnabled(false);
        imageView_Pic.setEnabled(false);

        if(sTime != null)
        {
            addRtxTextViewToLayout(textView_Time,-1,21.09f,Common.Color.yellow_1,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 23, iLayoutWidth, 20);
        }

        //if(sInstructor != null)
        {
            addRtxTextViewToLayout(textView_Instructor,-1,21.09f,Common.Color.blue_5,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 55, iLayoutWidth, 30);
            addRtxTextViewToLayout(textView_Name,-1,21.09f,Common.Color.white,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 88, iLayoutWidth, 60);
            addRtxImagePaddingViewToLayout(imageView_Pic, R.drawable.gym_default_image, 0, 158, 149, 83, 0);

            textView_Instructor.setSingleLine();
            textView_Instructor.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            textView_Name.setLines(2);
            textView_Name.setEllipsize(TextUtils.TruncateAt.valueOf("END"));

            iBlockHeight = iLayoutHeight;

            setInstructor(sInstructor);
        }
        //else
        //{
        //    addRtxTextViewToLayout(textView_Name,-1,21.09f,Common.Color.white,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 65 - 36, iLayoutWidth, 60);
        //    addRtxImagePaddingViewToLayout(imageView_Pic, -1, 0, 132 - 36, 149, 83, 0);

        //    iBlockHeight = 132 - 36 + 83;
        //}

        setTime(sTime);
        setName(sClassName);
    }

    protected int iGetBlockHeight()
    {
        return iBlockHeight;
    }

    protected String sGetClassID()
    {
        return sCLS_ID;
    }

    private void init_Paint()
    {
        paintLine.setColor(Common.Color.blue_9);
        paintLine.setStrokeWidth(2);
    }

    public void setTime(String sTime)
    {
        textView_Time.setText(sTime);
    }

    public void setName(String sName)
    {
        textView_Name.setText(sName);
    }

    public void setInstructor(String sName)
    {
        textView_Instructor.setText(sName);
    }

    public void setBitmap(Bitmap bitmap)
    {
        imageView_Pic.setImageBitmap(bitmap);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,iLayoutHeight - 2,iLayoutWidth,iLayoutHeight - 2,paintLine);
    }
}
