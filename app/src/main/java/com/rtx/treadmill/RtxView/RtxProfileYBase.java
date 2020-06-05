package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * TODO: document your custom view class.
 */
public class  RtxProfileYBase extends View {
    private Context mContext;

    private int imode = 0x01; //0x01 : show all scale bar; 0x02 only show scale bar

    private Paint mPaintScale;          //Paint Scale

    private int iYScale_Show = 5;
    private int[] iYScale = {0, 25};

    private int COLOR_SCALE = Common.Color.exercise_word_white;

    private int iShift = 20;
    private int iViewWidth = 100;
    private int iViewHeight = 100;
    private float fscale = 1f;


    public RtxProfileYBase(Context context){
        super(context);

        mContext = context;

    }

    public void vSet_mode(int imode)
    {
        this.imode = imode;
    }

    public void vSet_limit(int imin, int imax)
    {
        iYScale[0] = imin;
        iYScale[1] = imax;
    }

    public void init(float fscale, int imin, int imax, int ix, int iy, int iw, int ih, int iShift)
    {
        this.fscale = fscale;
        iYScale[0] = imin;
        iYScale[1] = imax;
        iViewWidth = iw;
        iViewHeight = ih;
        this.iShift = iShift;
        init_paint();
        //vUpdate_DrawData();
        invalidate();
    }

    private void init_paint()
    {
        mPaintScale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintScale.setColor(COLOR_SCALE);
        mPaintScale.setTextSize(fGet_Scale(15.96f));
        mPaintScale.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Lato_Black));
    }

    /////////////////////////////
    private int iGet_Scale(int input)
    {
        int ival = (int)(input * fscale);

        if(ival <= 0)
        {
            ival = 1;
        }

        return ival;
    }

    private float fGet_Scale(float finput)
    {
        float fval = finput * fscale;

        if(fval <= 0)
        {
            fval = 1f;
        }

        return fval;
    }
    /////////////////////////////
    public void iDrawYScale(Canvas canvas)
    {

        int iLoop , iIndex = 0;
        int iPosX ;
        int iPosY ;
        int iEndPosX ;
        int iEndPosY ;
        int idiff = iYScale[1] - iYScale[0];
        int iSingleHeight = 10;
        String sdata;
        RectF rect;
        int text_width;
        int text_height;

        if(idiff > 1)
        {
            iSingleHeight = (iViewHeight-iShift*2)/idiff;
        }

        for (iLoop = iYScale[0]; iLoop < iYScale[1] + 1; iLoop++) {
            iPosX = iGet_Scale(45);
            iEndPosX = iPosX + iGet_Scale(20);;

            iPosY = iViewHeight - iSingleHeight * iIndex - iShift;
            if (iLoop % iYScale_Show == 0) {
                iEndPosY = iPosY + iGet_Scale(2);
            } else {
                iEndPosY = iPosY + iGet_Scale(1);
            }
            rect = new RectF(iPosX, iPosY, iEndPosX, iEndPosY);

            if((imode & 0x01) == 0x01) {
                canvas.drawRoundRect(rect, 0, 0, mPaintScale);
            }
            else if((imode & 0x02) == 0x02 && iLoop % iYScale_Show == 0)
            {
                canvas.drawRoundRect(rect, 0, 0, mPaintScale);
            }

            if (iLoop % iYScale_Show == 0 && iLoop != 0) {
                sdata = Rtx_TranslateValue.sInt2String(iLoop);
                Rect bounds = new Rect();
                mPaintScale.getTextBounds(sdata, 0, sdata.length(), bounds);
                text_width =  bounds.width();
                text_height = bounds.height();
                iPosX = iGet_Scale(0)+(iGet_Scale(25) - text_width)/2;
                iPosY += text_height/2;
                canvas.drawText(sdata, iPosX, iPosY, mPaintScale);
            }

            iIndex++;
        }
    }



    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        iDrawYScale(canvas);

    }

}
