package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;

/**
 * TODO: document your custom view class.
 */
public class RtxView_RecPercent_Vertical extends View {
    private static String TAG = "Jerry=BDView";
    private final static boolean DEBUG = false;

    protected static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    protected static final int DEFAULT_PAINT1_COLOR = Color.argb(255, 48, 197, 255);
    protected static final int DEFAULT_PAINT2_COLOR = Color.argb(255, 0, 255, 157);
    protected static final int DEFAULT_PAINT3_COLOR = Color.argb(255, 255, 218, 0);
    protected static final int DEFAULT_PAINT4_COLOR = Color.argb(255, 252, 119, 119);
    protected static final int DEFAULT_PAINT5_COLOR = Color.GRAY;
    protected static final int DEFAULT_PAINT6_COLOR = Color.YELLOW;
    protected static final int DEFAULT_POINTER_COLOR = Color.argb(0xFF, 0x4F, 0xEC, 0xBE);
    protected static final int DEFAULT_POINTERTEXT_COLOR = Color.argb(0xFF, 0x4F, 0xEC, 0xBE);

    protected Paint mPaintText;
    protected Paint mPaintRec;
    protected Paint mPaintPointer;

    //Index String
    private int i_sTextPercent_right = 35;
    String[] sTextPercent = {"Under", "Idea", "Over", "Obese", "Null", "Null"};
    private int mTextPercentColor = DEFAULT_TEXT_COLOR;
    private int iTextPercent_size = 13;
    private Typeface mTextPercent_tf;

    //Rec
    protected int[] mColor = {DEFAULT_PAINT1_COLOR, DEFAULT_PAINT2_COLOR, DEFAULT_PAINT3_COLOR
            , DEFAULT_PAINT4_COLOR, DEFAULT_PAINT5_COLOR, DEFAULT_PAINT6_COLOR};
    int[][] iTextPercent = {{0, 0}, {0, 0},{0, 0},{0, 0},{0, 0}, {0, 0}};
    private int iRec_w = 30;
    private int iRec_h = 30;
    private int iRec_shiftx = 30;
    private int iRec_shifty = 30;

    //Pointer
    private boolean bPointer_triangle_Color_fixed = false;
    private boolean bPointer_triangle = false;
    private int iPointer_posy = 0;
    private int itriangle_size = 15;
    private int mPointerColor = DEFAULT_POINTER_COLOR;

    private boolean bPointer_Text_Color_fixed = false;
    private int mPointerTextColor = DEFAULT_POINTERTEXT_COLOR;
    private int iPointerText_size = 52;
    private Typeface mPointerText_tf;
    String sVal = "-";
    float fVal = 0f;


    int iPercent_range = 100;
    int[] iPercent = {0, 0, 0, 0, 0, 0};
    private boolean bInvert = true;

    public RtxView_RecPercent_Vertical(Context context ) {
        super(context);

        init(context);

    }

    public RtxView_RecPercent_Vertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public RtxView_RecPercent_Vertical(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context)
    {
        iRec_w = getWidth();
        iRec_h = getHeight();

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintRec = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintPointer = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPointer.setStyle(Paint.Style.FILL);
        mTextPercent_tf = Typeface.createFromAsset(context.getAssets(), Common.Font.Relay_Black);
        mPointerText_tf = Typeface.createFromAsset(context.getAssets(), Common.Font.Relay_Black);

    }

    //Index String
    public void setTextPercent(String[] sarray) {
        sTextPercent = sarray;
    }
    public void setTextPercentColor(int TextPercentColor) {
        mTextPercentColor = TextPercentColor;
    }
    public void setTextPercent_size(int TextPercent_size) {
        iTextPercent_size = TextPercent_size;
    }
    public void setTextPercent_tf(Typeface TextPercent_tf) {
        mTextPercent_tf = TextPercent_tf;
    }

    //Rec
    public void setColor(int isel, int color) {
        mColor[isel] = color;
    }
    public void setColor(int[] color) {
        mColor = color;
    }
    public void setRec_WH(int iw, int ih)
    {
        iRec_w = iw;
        iRec_h = ih;
    };
    public void set_Rec_Shiftx(int ix) {
        iRec_shiftx = ix;
    }
    public void set_Rec_Shifty(int iy) {
        iRec_shifty = iy;
    }

    //Pointer
    public void set_pointer_posy(float iy)
    {
        iPointer_posy = (int)iy;
        if(iPointer_posy < 0)
        {
            iPointer_posy = 0;
        }
        else if(iPointer_posy > 100)
        {
            iPointer_posy = 100;
        }
    };
    public void setPointerColor(int color) {
        mPointerColor = color;
    }
    public void setmPointerTextColor(int color) {
        mPointerTextColor = color;
    }
    public void setPointerTextSize(int isize) {
        iPointerText_size = isize;
    }
    public void setPointerText_tf(Typeface PointerText_tf) {
        mPointerText_tf = PointerText_tf;
    }


    public void setPercent_range(int iPercent_range) {
        this.iPercent_range = iPercent_range;
    }
    public void setPercent(int isel, int ival)
    {
        iPercent[isel] = ival;
        if(DEBUG)Log.e("Jerry", "===========iPercent[" + isel + "]=" + iPercent[isel]);
    }
    public void setInvert(boolean bInvert) {
        this.bInvert = bInvert;
    }

    public void v_Redraw()
    {
        invalidate();
    }


    public void set_pointer_textval(String sval)
    {
        sVal = sval;
    };

    public void set_pointer_textval(float fval, String sval)
    {
        fVal = fval;
        sVal = sval;
    };

    public void clear_Percent()
    {
        int iLoop;

        for(iLoop = 0; iLoop < iPercent.length; iLoop++) {
            iPercent[iLoop] = 0;
        }
        for(iLoop = 0; iLoop < iTextPercent.length; iLoop++) {
            iTextPercent[iLoop][0] = 0;
            iTextPercent[iLoop][1] = 0;
        }
    }


    private void v_Calculate_info( )
    {
        int iLoop ;

        for(iLoop = 0; iLoop < iPercent.length; iLoop++) {
            if(iPercent[iLoop] == 0)
            {
                continue;
            }
            iTextPercent[iLoop][1] = iPercent[iLoop];

            if(iLoop == 0) {
                iTextPercent[iLoop][0] = 0;
            }
            else {
                iTextPercent[iLoop][0] = iTextPercent[iLoop-1][1];
            }
        }

    }

    private void v_RecBack_draw( Canvas canvas)
    {
        mPaintRec.setColor(Color.DKGRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaintRec);
    }

    private void v_Rec_draw( Canvas canvas)
    {
        int ih_start;
        int ih_end;
        float fdata;
        int iLoop ;

        for(iLoop = 0; iLoop < iTextPercent.length; iLoop++) {
            if (iTextPercent[iLoop][1] != 0) {
                if (bInvert) {
                    fdata = ((float) 1 - ((float) iTextPercent[iLoop][1]) / (float) iPercent_range);
                    ih_start = (int) (iRec_h * fdata);
                    fdata = ((float) 1 - (float) (iTextPercent[iLoop][0]) / (float)iPercent_range);
                    ih_end = (int) (iRec_h * fdata);
                } else {
                    ih_start = iRec_h * iTextPercent[iLoop][0] / iPercent_range;
                    ih_end = iRec_h * iTextPercent[iLoop][1] / iPercent_range;
                }

                ih_start += iRec_shifty;
                ih_end += iRec_shifty;
                if(DEBUG)Log.e("Jerry", "===========ih_start=" + ih_start);
                if(DEBUG)Log.e("Jerry", "===========ih_end=" + ih_end);
                mPaintRec.setColor(mColor[iLoop]);
                canvas.drawRect(iRec_shiftx, ih_start, iRec_w+iRec_shiftx, ih_end, mPaintRec);
            }
        }

    }

    private int i_x_shift(String str, int ix_shift)
    {
        int ival = 0;
        int text_height = 0;
        int text_width = 0;

        Rect bounds = new Rect();
        mPaintText.getTextBounds(str, 0, str.length(), bounds);

        text_height =  bounds.height();
        text_width =  bounds.width();

        if(ix_shift >= 0) {
            ival = iRec_shiftx - ix_shift - text_width;
            if (ival < 0) {
                ival = 0;
            }
        }

        return ival;
    }

    private void v_Percent_Text_draw( Canvas canvas)
    {
        int ih_start;
        int ih_end;
        float fdata;
        int iLoop ;

        mPaintText.setColor(mTextPercentColor);
        mPaintText.setTextSize(iTextPercent_size);
        mPaintText.setTypeface(mTextPercent_tf);

        for(iLoop = 0; iLoop < iTextPercent.length; iLoop++) {
            if (iTextPercent[iLoop][1] != 0) {
                if (bInvert) {
                    fdata = ((float) 1 - (float) (iTextPercent[iLoop][0]) / (float)iPercent_range);
                    ih_start = (int) (iRec_h * fdata);

                    fdata = ((float) 1 - ((float) iTextPercent[iLoop][1]) / (float) iPercent_range);
                    ih_end = (int) (iRec_h * fdata);

                } else {
                    ih_start = iRec_h * iTextPercent[iLoop][0] / iPercent_range;
                    ih_end = iRec_h * iTextPercent[iLoop][1] / iPercent_range;
                }
                fdata = iRec_shifty + (ih_end + ih_start ) / 2;
                int ival = i_x_shift(sTextPercent[iLoop], i_sTextPercent_right);
                canvas.drawText(sTextPercent[iLoop], ival, fdata, mPaintText);
            }
        }
    }

    private void v_draw_Pointer_triangle(Canvas canvas, Path path)
    {
        if(bPointer_triangle) {
            canvas.drawPath(path, mPaintPointer);
        }
    }


    private int i_check_Pointer_Color()
    {
        int icolor = mPointerColor;
        int iLoop;

        for(iLoop = 0; iLoop < iPercent.length; iLoop++)
        {
            if(fVal <= iPercent[iLoop])
            {
                icolor = mColor[iLoop];
                break;
            }
        }

        if(iLoop == iPercent.length)
        {
            for(iLoop = 1; iLoop < iPercent.length; iLoop++) {
                if (iPercent[iLoop] == 0) {
                    icolor = mColor[iLoop - 1];
                    break;
                }
            }
        }

        return icolor;
    }

    private int i_check_Pointer_triangle_Color()
    {
        int icolor = mPointerColor;
        int iLoop;

        if(bPointer_triangle_Color_fixed)return icolor;

        icolor = i_check_Pointer_Color();

        return icolor;
    }

    private void v_draw_Pointer_Text(Canvas canvas, int ih_start)
    {
        canvas.drawText(sVal, iRec_w+iRec_shiftx+itriangle_size+5, ih_start, mPaintPointer);
    }

    private int i_check_Pointer_Text_Color()
    {
        int icolor = mPointerTextColor;
        int iLoop;

        if(bPointer_Text_Color_fixed)return icolor;

        icolor = i_check_Pointer_Color();

        return icolor;
    }

    private void v_Pointer_Val_draw( Canvas canvas)
    {
        int ih_start;

        mPaintPointer.setTextSize(iPointerText_size);
        mPaintPointer.setTypeface(mPointerText_tf);

        mPointerColor = i_check_Pointer_triangle_Color();
        mPointerTextColor = i_check_Pointer_Text_Color();

        Path path = new Path();
        if (bInvert) {
            ih_start = iRec_shifty + (iRec_h * (100 - iPointer_posy))/100;
            if(DEBUG)Log.e(TAG, "=========ih_start=" + ih_start);
            path.moveTo(iRec_w + iRec_shiftx, ih_start);
            path.lineTo(iRec_w + iRec_shiftx + itriangle_size, ih_start + (itriangle_size/2));
            path.lineTo(iRec_w + iRec_shiftx + itriangle_size, ih_start - (itriangle_size/2));
            path.close();
            mPaintPointer.setColor(mPointerColor);
            v_draw_Pointer_triangle(canvas, path);

            ih_start += iPointerText_size/3;
            mPaintPointer.setColor(mPointerTextColor);
            v_draw_Pointer_Text(canvas, ih_start);
        }
        else
        {
            ih_start = iRec_shifty + (iRec_h * iPointer_posy)/100;;
            if(DEBUG)Log.e(TAG, "=========ih_start=" + ih_start);

            path.moveTo(iRec_w + iRec_shiftx, ih_start);
            path.lineTo(iRec_w + iRec_shiftx + itriangle_size, ih_start + (itriangle_size/2));
            path.lineTo(iRec_w + iRec_shiftx + itriangle_size, ih_start - (itriangle_size/2));
            path.close();

            mPaintPointer.setColor(mPointerColor);
            v_draw_Pointer_triangle(canvas, path);

            ih_start += iPointerText_size/3;
            mPaintPointer.setColor(mPointerTextColor);
            v_draw_Pointer_Text(canvas, ih_start);

        }


    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        v_Calculate_info();

        if(DEBUG)
        {
            v_RecBack_draw(canvas);
        }

        v_Rec_draw(canvas);
        v_Percent_Text_draw(canvas);
        v_Pointer_Val_draw(canvas);

    }


}
