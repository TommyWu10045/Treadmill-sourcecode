package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;

/**
 * TODO: document your custom view class.
 */
public class RtxGaugeView extends View {

    //int iColor_Green = 0xFF4FECBE;
    int iColor_Red = 0xFFFF5050;
    int iColor_Yellow = 0xFFFBF000;
    int iColor_Green = iColor_Red;

    Paint mOutSidePaint;
    Paint mInSidePaint;
    Paint mLinePaint;

    private float fStartVal;
    private float fTargetVal;
    private float fCurrentVal;

    public RtxGaugeView(Context context) {
        super(context);

        fStartVal = 0;
        fTargetVal = 100;
        fCurrentVal = (fStartVal + fTargetVal) / 2;

        init();
    }

    public RtxGaugeView(Context context, float fStart , float fTarget) {
        super(context);

        fStartVal = fStart;
        fTargetVal = fTarget;
        fCurrentVal = (fStartVal + fTargetVal) / 2;
        init();
    }

    public RtxGaugeView(Context context, float fStart , float fTarget , float fVal) {
        super(context);

        fStartVal = fStart;
        fTargetVal = fTarget;
        fCurrentVal = fVal;
        init();
    }

    public void init()
    {
        mOutSidePaint = new Paint();
        mOutSidePaint.setAntiAlias(true); //消除锯齿
        mOutSidePaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mOutSidePaint.setStrokeWidth(PAINT_STROKE_WIDTH); //设置进度条宽度
        mOutSidePaint.setColor(Common.Color.gray_3); //设置进度条颜色
        mOutSidePaint.setStrokeJoin(Paint.Join.ROUND);
        mOutSidePaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        mInSidePaint = new Paint();
        mInSidePaint.setAntiAlias(true); //消除锯齿
        mInSidePaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mInSidePaint.setStrokeWidth(PAINT_STROKE_WIDTH); //设置进度条宽度
        mInSidePaint.setColor(Common.Color.yellow_4); //设置进度条颜色

        mLinePaint = new Paint();
        mLinePaint.setColor(Common.Color.white);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(2);


    }

    public void vSetLimit(float fStart , float fTarget)
    {
        fStartVal = fStart;
        fTargetVal = fTarget;
    }

    public void vSetVal(float fVal)
    {
        fCurrentVal = fVal;
    }

    private void vDrawDirect(Canvas canvas)
    {
        int iAngle = 0;
        float fBigVal = 0;
        float fSmallVal = 0;

        if(fStartVal < fTargetVal)
        {
            fBigVal = fTargetVal;
            fSmallVal = fStartVal;
        }
        else
        {
            fBigVal = fStartVal;
            fSmallVal = fTargetVal;
        }

        iAngle = -(MARK_RANGE_ANGLE / 2) + ((int) (((fCurrentVal - fSmallVal) * MARK_RANGE_ANGLE) / (fBigVal - fSmallVal)));

        if(iAngle > 90)
        {
            iAngle = 90;
        }
        else
        if(iAngle < -90)
        {
            iAngle = -90;
        }

        canvas.rotate(iAngle, getWidth() / 2, getHeight() / 2);

        int iShortSideOffset = (int)(getWidth() * 0.05);
        int iVerTexY = (int)(getHeight() * 0.29);
        int iShortVertexY = (int)(getHeight() * 0.54);

        {
            Path path = new Path();



            path.moveTo( (getWidth() / 2)       , iVerTexY );// 此点为多边形的起点
            path.lineTo( (getWidth() / 2) - iShortSideOffset  , iShortVertexY);
            path.lineTo( (getWidth() / 2) + iShortSideOffset  , iShortVertexY);

            path.close(); // 使这些点构成封闭的多边形
            vSetPaintColor(mLinePaint);
            canvas.drawPath(path, mLinePaint);
        }

        {
            RectF mBigOval = new RectF((getWidth() / 2) + 1 - iShortSideOffset, iShortVertexY - iShortSideOffset - 1, (getWidth() / 2) + iShortSideOffset - 1, iShortVertexY + iShortSideOffset - 1);
            canvas.drawArc(mBigOval, -180        , -180    , true, mLinePaint);
        }
    }

    private void vDrawValCicle(Canvas canvas)
    {
        int iStartAngle;
        int iTotalAngle = 0;
        int iStrokeGap = PAINT_STROKE_WIDTH / 2;
        int iGapAngle = 0;

        RectF mBigOval = new RectF(iStrokeGap + FREE_GAP, iStrokeGap + FREE_GAP, getWidth() - iStrokeGap - FREE_GAP, getHeight() - iStrokeGap - FREE_GAP);

        if(fCurrentVal == fStartVal)
        {
            return;
        }

        if(fStartVal < fTargetVal)
        {
            iStartAngle = -90 - (MARK_RANGE_ANGLE / 2);

            if(fCurrentVal < fStartVal)
            {
                mInSidePaint.setColor(iColor_Green);
            }
            else
            if(fCurrentVal > fTargetVal)
            {
                mInSidePaint.setColor(iColor_Red);
            }
            else
            {
                mInSidePaint.setColor(iColor_Yellow);
            }

            iGapAngle = (BASE_CIRCLE_ANGLE - MARK_RANGE_ANGLE) / 2;
            iTotalAngle = (int) (((fCurrentVal - fStartVal) * MARK_RANGE_ANGLE) / (fTargetVal - fStartVal));
            mInSidePaint.setStrokeCap(Paint.Cap.BUTT);

            if(iTotalAngle > MARK_RANGE_ANGLE + iGapAngle)
            {
                iTotalAngle = MARK_RANGE_ANGLE + iGapAngle;
            }
            else
            if(iTotalAngle < (-iGapAngle))
            {
                iTotalAngle = (-iGapAngle);
            }

            canvas.drawArc(mBigOval, iStartAngle, iTotalAngle, false, mInSidePaint);
            mInSidePaint.setStrokeCap(Paint.Cap.ROUND);
            if(fCurrentVal < fStartVal)
            {
                canvas.drawArc(mBigOval, iStartAngle + iTotalAngle +1, 1, false, mInSidePaint);
            }
            else
            {
                canvas.drawArc(mBigOval, iStartAngle + iTotalAngle -1, 1, false, mInSidePaint);
            }
        }
        else
        {
            iStartAngle = -90 + (MARK_RANGE_ANGLE / 2);

            if(fCurrentVal < fTargetVal)
            {
                mInSidePaint.setColor(iColor_Green);//green
            }
            else
            if(fCurrentVal > fStartVal)
            {
                mInSidePaint.setColor(iColor_Red);//red
            }
            else
            {
                mInSidePaint.setColor(iColor_Yellow);//yellow
            }

            iGapAngle = (BASE_CIRCLE_ANGLE - MARK_RANGE_ANGLE) / 2;

            iTotalAngle = (int) (((fCurrentVal - fStartVal) * MARK_RANGE_ANGLE) / (fStartVal - fTargetVal));

            //Log.e("Jerry","iTotalAngle = "+iTotalAngle);

            mInSidePaint.setStrokeCap(Paint.Cap.BUTT);

            if(iTotalAngle > iGapAngle)
            {
                iTotalAngle = iGapAngle;
            }
            else
            if(iTotalAngle < (-iGapAngle-MARK_RANGE_ANGLE))
            {
                iTotalAngle = (-iGapAngle-MARK_RANGE_ANGLE);
            }

            canvas.drawArc(mBigOval, iStartAngle, iTotalAngle, false, mInSidePaint);
            mInSidePaint.setStrokeCap(Paint.Cap.ROUND);
            if(fCurrentVal > fStartVal)
            {
                canvas.drawArc(mBigOval, iStartAngle + iTotalAngle +1, 1, false, mInSidePaint);
            }
            else
            {
                canvas.drawArc(mBigOval, iStartAngle + iTotalAngle -1, 1, false, mInSidePaint);
            }
        }
    }

    private void vSetPaintColor(Paint paint)
    {
        if(fStartVal < fTargetVal)
        {
            if(fCurrentVal < fStartVal)
            {
                paint.setColor(iColor_Green);
            }
            else
            if(fCurrentVal > fTargetVal)
            {
                paint.setColor(iColor_Red);
            }
            else
            {
                paint.setColor(iColor_Yellow);
            }
        }
        else
        {
            if(fCurrentVal < fTargetVal)
            {
                paint.setColor(iColor_Green);//green
            }
            else
            if(fCurrentVal > fStartVal)
            {
                paint.setColor(iColor_Red);//red
            }
            else
            {
                paint.setColor(iColor_Yellow);//yellow
            }
        }
    }

    private static final int PAINT_STROKE_WIDTH = 20;
    private static final int BASE_CIRCLE_ANGLE = 160;

    private static final int MARK_RANGE_ANGLE = 110;

    private final int FREE_GAP = 10;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas)
    {
        {
            int iStrokeGap = PAINT_STROKE_WIDTH / 2;
            RectF mBigOval = new RectF(iStrokeGap + FREE_GAP, iStrokeGap + FREE_GAP, getWidth() - iStrokeGap - FREE_GAP, getHeight() - iStrokeGap - FREE_GAP);

            {
                int iStartAngle = 0;
                int iTotalAngle = 0;

                iStartAngle = -90 - (BASE_CIRCLE_ANGLE / 2);
                iTotalAngle = 160;

                canvas.drawArc(mBigOval, iStartAngle, iTotalAngle, false, mOutSidePaint);
            }

            {
                canvas.save();
                vDrawValCicle(canvas);
                canvas.restore();
                canvas.save();
                vDrawDirect(canvas);
                canvas.restore();
                canvas.save();
            }

            {
                {   //Draw Line
                    int iAngle = MARK_RANGE_ANGLE / 2;

                    //mLinePaint.setColor(0xffffffff);

                    {
                        int iStartX , iStartY;
                        int iEndX , iEndY;

                        canvas.save();
                        canvas.rotate(iAngle, getWidth() / 2, getHeight() / 2);

                        iStartX = getWidth() / 2;
                        iStartY = FREE_GAP + PAINT_STROKE_WIDTH;
                        iEndX = getWidth() / 2;
                        iEndY = 0;

                        canvas.drawLine(iStartX, iStartY, iEndX,iEndY, mLinePaint);

                        canvas.restore();
                        canvas.save();

                        canvas.rotate(-iAngle, getWidth() / 2, getHeight() / 2);
                        canvas.drawLine(iStartX, iStartY, iEndX,iEndY, mLinePaint);

                        canvas.restore();
                        canvas.save();
                    }
                }
            }
        }

        super.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int sideLength = Math.min(widthSpecSize, heightSpecSize);
        setMeasuredDimension(sideLength, sideLength);
//        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }
}
