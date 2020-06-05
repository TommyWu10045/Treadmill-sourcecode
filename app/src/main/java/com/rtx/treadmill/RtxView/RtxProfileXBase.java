package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * TODO: document your custom view class.
 */
public class  RtxProfileXBase extends View {
    private Context mContext;

    private Paint mPaintScale;          //Paint Scale

    private int ishift_count = 0;
    private int iXScale = 30;
    private int iXScale_Show = 5;

    private int COLOR_SCALE = Common.Color.exercise_word_white;
    private int iViewWidth = 100;
    private int iViewHeight = 100;
    private float fscale = 1f;

    private int iIndex;

    public RtxProfileXBase(Context context){
        super(context);

        mContext = context;

    }

    public void init(int iscale, float fscale, int ix, int iy, int iw, int ih)
    {
        this.iXScale = iscale;
        this.fscale = fscale;
        iViewWidth = iw;
        iViewHeight = ih;
        init_paint();
        invalidate();
    }

    private void init_paint()
    {
        mPaintScale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintScale.setColor(COLOR_SCALE);
        mPaintScale.setTextSize(fGet_Scale(15.96f));
        mPaintScale.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Lato_Black));
    }

    public void vSetShift(int iflash)
    {
        ishift_count = iflash;
        invalidate();
    }

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
    public void iDrawXScale(Canvas canvas)
    {
        int iLoop ;
        int iPosX ;
        int iPosY ;
        int text_width;
        int text_height;
        int iSingleWidth = iViewWidth/(iXScale+1);
        iIndex = 0;
        String sdata;

        int iXScaleShow = iXScale_Show;

        if(iXScale > 100)
        {
            iXScaleShow = 50;
        }
        else if(iXScale < 5)
        {
            iXScaleShow = 1;
        }

        if(iSingleWidth == 0)
        {
            iXScaleShow = ((iXScale + iViewWidth) /  iViewWidth) * 50;
        }

        for (iLoop = ishift_count; iLoop < iXScale + ishift_count + 1; iLoop++) {
            if (iLoop % iXScaleShow == 0) {
                sdata = Rtx_TranslateValue.sInt2String(iLoop);
                Rect bounds = new Rect();
                mPaintScale.getTextBounds(sdata, 0, sdata.length(), bounds);
                text_width =  bounds.width();
                text_height = bounds.height();

                //大於0時,繪圖時會跟profile chart有誤差,採兩種不同計算方式
                if(iSingleWidth == 0)
                {
                    iPosX = iIndex * iViewWidth / (iXScale + 1) + (iGet_Scale(34) - text_width) / 2;
                }
                else
                {
                    iPosX = iIndex * iSingleWidth + (iGet_Scale(34) - text_width) / 2;
                }

                iPosY = text_height + iGet_Scale(10);

                //最後欄位超過邊界時,不顯示
                if(iPosX < (iViewWidth - text_width))
                {
                    canvas.drawText(sdata, iPosX, iPosY, mPaintScale);
                }
            }
            iIndex ++;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        iDrawXScale(canvas);

    }

}
