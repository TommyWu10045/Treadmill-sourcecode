package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.ExerciseData;

/**
 * TODO: document your custom view class.
 */
public class RtxLapCircle extends RtxImageView {

    Paint mPaint;
    Paint mRunPaint;
    Paint mStartLinePaint;

    int iPaintStroke = 30;
    int iStartLinePaintStroke = 10;

    int iPercent = 0;
    int iLapPercent = ExerciseData.iLapMeter;
    Bitmap b = null;
    Canvas c = null;

    public RtxLapCircle(Context context ) {
        super(context);

        init_paint();
    }

    public void setPercent(int iVal)
    {
        iPercent = iVal;
        b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        b.eraseColor(Common.Color.background);
        c = new Canvas(b);

        Create_Bitmap(c);

        return;
    }

    private void init_paint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(iPaintStroke);
        mPaint.setColor(0xFF292498);

        mRunPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRunPaint.setStyle(Paint.Style.STROKE);
        mRunPaint.setStrokeWidth(iPaintStroke);
        mRunPaint.setColor(Common.Color.exercise_word_yellow);
        mRunPaint.setStrokeJoin(Paint.Join.ROUND);
        mRunPaint.setStrokeCap(Paint.Cap.ROUND);


        mStartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStartLinePaint.setStyle(Paint.Style.STROKE);
        mStartLinePaint.setStrokeWidth(iStartLinePaintStroke);
        mStartLinePaint.setColor(Common.Color.exercise_word_white);
        mStartLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mStartLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void vAddPath_1st(Path path , int iPosX_Start , int iPosX_End , int iPosY , int iPercent)
    {
        int iLen = 0;

        iLen = ((iPosX_End - iPosX_Start) * iPercent ) / iLapPercent;

        path.moveTo(iPosX_Start,iPosY);
        path.lineTo(iPosX_Start+iLen,iPosY);
    }

    private void vAddPath_2st(Path path , int iLeft , int iTop , int iRight , int iBottom , int iPercent)
    {
        int iAngle = 0;

        iAngle = (180 * iPercent) / iLapPercent;

        path.addArc(iLeft,iTop,iRight,iBottom,-90,iAngle);
    }

    private void vAddPath_3st(Path path , int iPosX_Start , int iPosX_End , int iPosY , int iPercent)
    {
        int iPosX = 0;

        iPosX = iPosX_Start - (((iPosX_Start - iPosX_End) * iPercent ) / iLapPercent);

        path.lineTo(iPosX,iPosY);
    }

    private void vAddPath_4st(Path path , int iLeft , int iTop , int iRight , int iBottom , int iPercent)
    {
        int iAngle = 0;

        iAngle = (180 * iPercent) / iLapPercent;

        path.addArc(iLeft,iTop,iRight,iBottom,90,iAngle);
    }

    private void drawPlayground(Canvas canvas , int iW , int iH , int iPaintStroke , Paint paint, int iOffsetY)
    {
        int iLen;

        int iHalfPaintStroke = iPaintStroke / 2;
        int iRadius = (iH - iPaintStroke) / 2;

        int iPosX_FistLineStart = iH / 2;
        int iPosY_FistLineStart = iHalfPaintStroke;

        int iPosX_FistLineEnd = iW-iHalfPaintStroke-iRadius;
        int iPosY_FistLineEnd = iPosY_FistLineStart;

        int iPosX_SecLineEnd = iPosX_FistLineStart;
        int iPosY_SecLineEnd = iH - iHalfPaintStroke;

        Path path = new Path();
        path.moveTo(iPosX_FistLineStart,iPosY_FistLineStart+iOffsetY);
        path.lineTo(iPosX_FistLineEnd,iPosY_FistLineEnd+iOffsetY);

        path.addArc(iPosX_FistLineEnd - iRadius,
                    iPosY_FistLineEnd + iOffsetY,
                    iPosX_FistLineEnd + iRadius,
                    iH - iHalfPaintStroke + iOffsetY
                    ,-90,180);

        path.lineTo(iPosX_SecLineEnd,iPosY_SecLineEnd+iOffsetY);

        path.addArc(iPosX_FistLineStart - iRadius,
                    iPosY_FistLineStart + +iOffsetY,
                    iPosX_FistLineStart + iRadius,
                    iH - iHalfPaintStroke + +iOffsetY,
                    90,180);

        //iLen = (int)(2f*(float)iRadius*3.14f) + ((iPosX_FistLineEnd - iPosX_FistLineStart)*2);
        canvas.drawPath(path,paint);

    }

    private void drawPlaygroundPart(Canvas canvas , int iW , int iH , int iPaintStroke , Paint paint, int iOffsetY,int iPercent)
    {
        int iLen;

        int iHalfPaintStroke = iPaintStroke / 2;
        int iRadius = (iH - iPaintStroke) / 2;

        int iPosX_FistLineStart = iH / 2;
        int iPosY_FistLineStart = iHalfPaintStroke;

        int iPosX_FistLineEnd = iW-iHalfPaintStroke-iRadius;
        int iPosY_FistLineEnd = iPosY_FistLineStart;

        int iPosX_SecLineEnd = iPosX_FistLineStart;
        int iPosY_SecLineEnd = iH - iHalfPaintStroke;

        int iLineLen = iPosX_FistLineEnd - iPosX_FistLineStart;
        int iPerimeter = (int)((float)iRadius * 3.14f * 2f);
        int iHalfPerimeter = iPerimeter / 2;

        int iTotalLen = (int)(2f*(float)iRadius*3.14f) + ((iPosX_FistLineEnd - iPosX_FistLineStart)*2);

        int iMaxVal_1_Part = iPosX_FistLineEnd - iPosX_FistLineStart;
        int iMaxVal_2_Part = iMaxVal_1_Part + iHalfPerimeter;
        int iMaxVal_3_Part = iMaxVal_2_Part + iMaxVal_1_Part;
        int iMaxVal_4_Part = iTotalLen;

        int iMaxVal_1_Part_percent = (iMaxVal_1_Part * iLapPercent ) / iTotalLen;
        int iMaxVal_2_Part_percent = (iMaxVal_2_Part * iLapPercent ) / iTotalLen;
        int iMaxVal_3_Part_percent = (iMaxVal_3_Part * iLapPercent ) / iTotalLen;
        int iMaxVal_4_Part_percent = iLapPercent;

//        Log.e("Jerry","iMaxVal_1_Part_percent = "+iMaxVal_1_Part_percent);
//        Log.e("Jerry","iMaxVal_2_Part_percent = "+iMaxVal_2_Part_percent);
//        Log.e("Jerry","iMaxVal_3_Part_percent = "+iMaxVal_3_Part_percent);

        Path path = new Path();

        if(iPercent >= iLapPercent)
        {
            vAddPath_1st(path , iPosX_FistLineStart , iPosX_FistLineEnd , iPosY_FistLineStart+iOffsetY , iLapPercent);
            vAddPath_2st(path , iPosX_FistLineEnd - iRadius,iPosY_FistLineEnd + iOffsetY,iPosX_FistLineEnd + iRadius,iH - iHalfPaintStroke + iOffsetY , iLapPercent);
            vAddPath_3st(path , iPosX_FistLineEnd , iPosX_SecLineEnd , iPosY_SecLineEnd+iOffsetY , iLapPercent);
            vAddPath_4st(path , iPosX_FistLineStart - iRadius , iPosY_FistLineStart + +iOffsetY , iPosX_FistLineStart + iRadius , iH - iHalfPaintStroke + +iOffsetY , iLapPercent);
        }
        else if(iPercent >= iMaxVal_3_Part_percent)
        {
            int iPartPercent = ((iPercent - iMaxVal_3_Part_percent) * iLapPercent / (iMaxVal_4_Part_percent - iMaxVal_3_Part_percent));

            vAddPath_1st(path , iPosX_FistLineStart , iPosX_FistLineEnd , iPosY_FistLineStart+iOffsetY , iLapPercent);
            vAddPath_2st(path , iPosX_FistLineEnd - iRadius,iPosY_FistLineEnd + iOffsetY,iPosX_FistLineEnd + iRadius,iH - iHalfPaintStroke + iOffsetY , iLapPercent);
            vAddPath_3st(path , iPosX_FistLineEnd , iPosX_SecLineEnd , iPosY_SecLineEnd+iOffsetY , iLapPercent);
            vAddPath_4st(path , iPosX_FistLineStart - iRadius , iPosY_FistLineStart + +iOffsetY , iPosX_FistLineStart + iRadius , iH - iHalfPaintStroke + +iOffsetY , iPartPercent);
        }
        else if(iPercent >= iMaxVal_2_Part_percent)
        {
            int iPartPercent = ((iPercent - iMaxVal_2_Part_percent) * iLapPercent / (iMaxVal_3_Part_percent - iMaxVal_2_Part_percent));

            vAddPath_1st(path , iPosX_FistLineStart , iPosX_FistLineEnd , iPosY_FistLineStart+iOffsetY , iLapPercent);
            vAddPath_2st(path , iPosX_FistLineEnd - iRadius,iPosY_FistLineEnd + iOffsetY,iPosX_FistLineEnd + iRadius,iH - iHalfPaintStroke + iOffsetY , iLapPercent);
            vAddPath_3st(path , iPosX_FistLineEnd , iPosX_SecLineEnd , iPosY_SecLineEnd+iOffsetY , iPartPercent);
        }
        else if(iPercent >= iMaxVal_1_Part_percent)
        {
            int iPartPercent = ((iPercent - iMaxVal_1_Part_percent) * iLapPercent / (iMaxVal_2_Part_percent - iMaxVal_1_Part_percent));

            vAddPath_1st(path , iPosX_FistLineStart , iPosX_FistLineEnd , iPosY_FistLineStart+iOffsetY , iLapPercent);
            vAddPath_2st(path , iPosX_FistLineEnd - iRadius,iPosY_FistLineEnd + iOffsetY,iPosX_FistLineEnd + iRadius,iH - iHalfPaintStroke + iOffsetY , iPartPercent);
        }
        else
        {
            int iPartPercent = iPercent * iLapPercent / iMaxVal_1_Part_percent;

            vAddPath_1st(path , iPosX_FistLineStart , iPosX_FistLineEnd , iPosY_FistLineStart+iOffsetY , iPartPercent);
        }

        canvas.drawPath(path,paint);
    }

    private void drawStartLine(Canvas canvas , int iH , int iOffsetY , int iPaintStroke , Paint paint)
    {
        canvas.drawLine((iH / 2) - iPaintStroke , iPaintStroke / 2 , (iH / 2) - iPaintStroke , iOffsetY + iPaintStroke + (iPaintStroke) + iOffsetY , paint);
    }

    private void Create_Bitmap(Canvas canvas)
    {

        drawPlayground(canvas,getWidth(),getHeight()-20,iPaintStroke,mPaint,20);
        drawPlaygroundPart(canvas,getWidth(),getHeight()-20,iPaintStroke,mRunPaint,20,iPercent);

        drawStartLine(canvas,getHeight()-20,20,iStartLinePaintStroke,mStartLinePaint);

        setImageBitmap(b);
    }

}
