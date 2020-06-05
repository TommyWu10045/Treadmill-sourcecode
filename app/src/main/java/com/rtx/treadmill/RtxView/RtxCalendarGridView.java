package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.GridView;

/**
 * Created by jerry on 2016/12/29.
 */

public class RtxCalendarGridView extends GridView
{
    final int DATE_CIRCLE_SIZE = 70 ;

    int iVerticalSpacing = 0;
    int iHorizontalSpacing = 0;
    int iNumColumns = 0;

    int iSetDrawStartRow = -1;
    int iSetDrawEndRow = -1;
    int iSetDrawStartColumn = -1;
    int iSetDrawEndColumn = -1;

    int iSetDrawStartRow_1 = -1;
    int iSetDrawEndRow_1 = -1;
    int iSetDrawStartColumn_1 = -1;
    int iSetDrawEndColumn_1 = -1;

    int iColor_0;
    int iColor_1;

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        //super.setBackgroundColor(color);

        iBackgrounfColor = color;
    }

    int iBackgrounfColor;

    boolean bClearFlag = false;

    private Paint mPaint = new Paint();

    public RtxCalendarGridView(Context context) {
        super(context);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
    }

    public void setHighLightColor_0(int iColor)
    {
        iColor_0 = iColor;
    }

    public void setHighLightColor_1(int iColor)
    {
        iColor_1 = iColor;
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        iNumColumns = numColumns;
    }

    @Override
    public void setVerticalSpacing(int verticalSpacing) {
        super.setVerticalSpacing(verticalSpacing);
        iVerticalSpacing = verticalSpacing;
    }

    @Override
    public void setHorizontalSpacing(int horizontalSpacing) {
        super.setHorizontalSpacing(horizontalSpacing);
        iHorizontalSpacing = horizontalSpacing;
    }


    public void setDrawRange(int iStartRow, int iStartColumn, int iEndRow, int iEndColumn)
    {
        iSetDrawStartRow = iStartRow;
        iSetDrawEndRow = iEndRow;
        iSetDrawStartColumn = iStartColumn;
        iSetDrawEndColumn = iEndColumn;

        bClearFlag = false;

        invalidate();
    }

    public void setDrawRange_1(int iStartRow, int iStartColumn, int iEndRow, int iEndColumn)
    {
        iSetDrawStartRow_1 = iStartRow;
        iSetDrawEndRow_1 = iEndRow;
        iSetDrawStartColumn_1 = iStartColumn;
        iSetDrawEndColumn_1 = iEndColumn;

        bClearFlag = false;

        invalidate();
    }

    private void drawRange(Canvas canvas, int iStartRow, int iStartColumn, int iEndRow, int iEndColumn)
    {
        int iRowIndex = iStartRow;
        int iLines = iEndColumn - iStartColumn;

        if(iStartRow == iEndRow)
        {
            drawRow(canvas,iStartRow,iStartColumn,iEndColumn);
        }
        else if(iStartRow < iEndRow)
        {
            for( ; iRowIndex <= iEndRow ; iRowIndex++)
            {
                if(iRowIndex == iStartRow)
                {
                    drawRow(canvas,iRowIndex,iStartColumn,6);
                }
                else if(iRowIndex == iEndRow)
                {
                    drawRow(canvas,iRowIndex,0,iEndColumn);
                }
                else
                {
                    drawRow(canvas,iRowIndex,0,6);
                }
            }
        }
    }

    private void drawRow(Canvas canvas, int iRow, int iStartColumn, int iEndColumn)
    {
        int iPosX,iPosY;
        int iW,iH;

        iPosY = ( DATE_CIRCLE_SIZE + iVerticalSpacing ) * iRow;
        iPosX = ( DATE_CIRCLE_SIZE + iHorizontalSpacing ) * iStartColumn;
        iW = ( DATE_CIRCLE_SIZE * (iEndColumn - iStartColumn + 1 )) + ( iHorizontalSpacing *  (iEndColumn - iStartColumn + 1));
        iH = DATE_CIRCLE_SIZE;

        RectF rect=new RectF();
        rect.left = iPosX;
        rect.top = iPosY;
        rect.right = iPosX + iW;
        rect.bottom = iPosY + iH;

        canvas.drawRoundRect(rect ,(DATE_CIRCLE_SIZE / 2), (DATE_CIRCLE_SIZE / 2), mPaint);
    }

    public void clear()
    {
        Log.e("Jerry","clear()");
        bClearFlag = true;

        iSetDrawStartRow = -1;
        iSetDrawStartColumn = -1;
        iSetDrawEndRow = -1;
        iSetDrawEndColumn = -1;

        iSetDrawStartRow_1 = -1;
        iSetDrawStartColumn_1 = -1;
        iSetDrawEndRow_1 = -1;
        iSetDrawEndColumn_1 = -1;

        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub

        //canvas.drawColor(0xFFFF0000);
        canvas.drawColor(iBackgrounfColor);

        if(!bClearFlag)
        {
            if(iSetDrawStartRow_1 != -1)
            {
                //mPaint.setColor(iColor_1);
                mPaint.setColor(iColor_0);
                drawRange(canvas, iSetDrawStartRow_1, iSetDrawStartColumn_1, iSetDrawEndRow_1, iSetDrawEndColumn_1);
            }

            if(iSetDrawStartRow != -1)
            {
                mPaint.setColor(iColor_0);
                drawRange(canvas, iSetDrawStartRow, iSetDrawStartColumn, iSetDrawEndRow, iSetDrawEndColumn);
            }
        }

        super.draw(canvas);
    }
}
