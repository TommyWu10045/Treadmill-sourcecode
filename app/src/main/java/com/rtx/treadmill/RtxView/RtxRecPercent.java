package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.rtx.treadmill.GlobalData.Common;


/**
 * TODO: document your custom view class.
 */
public class RtxRecPercent extends View {
    private String TAG = "Jerry=";

    protected static final int DEFAULT_PAINT1_COLOR = Color.argb(0xFF, 0x69, 0x69, 0x69);
    protected static final int DEFAULT_PAINT2_COLOR = Color.YELLOW;
    protected static final int DEFAULT_POINTER_COLOR = Color.BLUE;

    private int imode = 0x02; //0x01 center move ; 0x02 center fix ;

    protected Paint mPaint;
    protected Paint mPointerPaint;

    protected int mPaintBackColor = DEFAULT_PAINT1_COLOR ;
    protected int mPaintFrontColor = DEFAULT_PAINT2_COLOR ;
    protected int mPointerColor = DEFAULT_POINTER_COLOR;

    protected float fPointerRadius = 30;

    protected boolean isPointerEnabled = false;
    protected boolean isTouchEnabled = false;

    protected boolean bPointSel_Check = false;
    protected boolean bPointSel = false;
    private int iPointSel_range = 5;

    private int iPercentValue;
    private int iPercent_def = 50;


    private VelocityTracker mVelocityTracker;
    private float flastX = 0;
    private int mPointerId;
    private int mTouchSlop;
    private int mMinVelocity;
    private int mMaxVelocity;
    private static final int MAX_FLING_VELOCITY = 1;

    private float velocityX ;
    private float velocityY ;


    protected OnChangeListener mOnChangeListener;

    public RtxRecPercent(Context context ) {
        super(context);

        init();

        // initialize constants
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity() / MAX_FLING_VELOCITY;
        Log.e(TAG, "======mTouchSlop=" + mTouchSlop + "======mMinVelocity=" + mMinVelocity + "======mMaxVelocity=" + mMaxVelocity);
    }

    public RtxRecPercent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public RtxRecPercent(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init()
    {
        iPercentValue = 30;
        init_paint();
    }

    private void init_paint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeWidth(67);
        mPaint.setColor(Common.Color.yellow);

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setDither(true);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(mPointerColor);
        mPointerPaint.setStrokeWidth(fPointerRadius);

    }

    public void setBackColor(int color) {
        mPaintBackColor = color;
        invalidate();
    }

    public void setFrontColor(int color) {
        mPaintFrontColor = color;
        invalidate();
    }

    public void setPointerColor(int color) {
        mPointerColor = color;
        mPointerPaint.setColor(mPointerColor);
        invalidate();
    }

    public void setTouchEnabled(boolean bTouchEnabled) {
        this.isTouchEnabled = bTouchEnabled;
    }

    public void setPointerEnabled(boolean bPointerEnabled) {
        this.isPointerEnabled = bPointerEnabled;
    }

    public void setPointSel_Check(boolean bPointSel_Check) {
        this.bPointSel_Check = bPointSel_Check;
    }

    public void setPointerRadiusd(int iPointerRadiusd) {
        this.fPointerRadius = iPointerRadiusd;
    }

    public void setPercent_def(int iPercent_def) {
        this.iPercent_def = iPercent_def;
        setPercent(iPercent_def);
    }

    public void setPercent(int iPercent)
    {
        iPercentValue = iPercent;
    }

    public int getPercent()
    {
        return iPercentValue;
    }

    private void setPercent(boolean bSel, int iPercent)
    {
        if(bSel) {
            iPercentValue = iPercent;
        }
        else
        {
            iPercentValue = iPercent_def;
        }
    }

    private boolean b_PointerSel_CheckX(float fx, int idiffx)
    {
        boolean bret = false;
        float fleft = iPercent_def - idiffx;
        float fright= iPercent_def + idiffx;

        if(!bPointSel_Check || idiffx == 0)
        {
            bret = true;
        }
        else if(fx > fleft && fx < fright)
        {
            bret = true;
        }

        return bret;
    }

    private boolean b_PointerSel_CheckY(float fy, int idiffy)
    {
        boolean bret = false;
        float ftop = iPercent_def - idiffy;
        float fbottom= iPercent_def + idiffy;

        if(!bPointSel_Check || idiffy == 0)
        {
            bret = true;
        }
        else if(fy > ftop && fy < fbottom)
        {
            bret = true;
        }

        return bret;
    }

    public void setPercent(int iVal , int iTotal)
    {
        iPercentValue = ( iVal * 100 ) / iTotal;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iWidth;

        iWidth = ( getWidth() * iPercentValue ) / 100;

        RectF rect1 = new RectF(0, 0, getWidth(), getHeight());
        mPaint.setColor(mPaintBackColor);
        canvas.drawRect(rect1, mPaint);

        RectF rect2 = new RectF(0, 0, iWidth, getHeight());
        mPaint.setColor(mPaintFrontColor);
        canvas.drawRect(rect2, mPaint);

        if(isPointerEnabled) {
            canvas.drawCircle(iWidth, getHeight() / 2, fPointerRadius, mPointerPaint);
        }

    }

    private void v_mode_draw_check()
    {
        if((imode & 0x01) == 0x01)
        {
            invalidate();
        }
    }

    private boolean v_action_mode_one(MotionEvent event) {
        boolean bret = true;

        float fxpercent = 100 * event.getX() / getWidth();
        float fypercent = 100 * event.getY() / getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bPointSel = b_PointerSel_CheckX(fxpercent, iPointSel_range);
                setPercent(bPointSel, (int) fxpercent);
                v_mode_draw_check();
                change();
                break;
            case MotionEvent.ACTION_MOVE:
                bPointSel = b_PointerSel_CheckY(fypercent, 120);
                setPercent(bPointSel, (int) fxpercent);
                v_mode_draw_check();
                change();
                break;
            case MotionEvent.ACTION_UP:
                bPointSel = false;
                setPercent(iPercent_def);
                v_mode_draw_check();
                change();
                break;
            case MotionEvent.ACTION_CANCEL: // Used when the parent view intercepts touches for things like scrolling
                v_mode_draw_check();
                change();
                break;
        }

        return bret;
    }

    ////////////////mode two///////////////////////
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private boolean v_action_mode_two(MotionEvent event) {
        boolean bret = true;

        float fxpercent = 100 * event.getX() / getWidth();
        float fypercent = 100 * event.getY() / getHeight();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bPointSel = b_PointerSel_CheckX(fxpercent, iPointSel_range);
                if (bPointSel) {
                    mPointerId = event.getPointerId(0);
                    flastX = fxpercent;
                } else {
                    bret = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (bPointSel)
                {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    velocityX = mVelocityTracker.getXVelocity(mPointerId);
                    velocityY = mVelocityTracker.getYVelocity(mPointerId);
                    float fdiff = Math.abs(flastX - fxpercent);
                    if(fdiff > 1)
                    {
                        setPercent(iPercent_def + (int)(flastX - fxpercent) * 7);
                        flastX = fxpercent;
                        change();
                    }
                }
                else
                {
                    bret = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseVelocityTracker();
                bPointSel = false;
                flastX = fxpercent;
                setPercent(iPercent_def);
                change_finish(velocityX);
                break;
            case MotionEvent.ACTION_CANCEL: // Used when the parent view intercepts touches for things like scrolling
                releaseVelocityTracker();
                bPointSel = false;
                flastX = fxpercent;
                setPercent(iPercent_def);
                break;
        }


        return bret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean bret = false;

        if(!isTouchEnabled){
            return bret;
        }

        if((imode & 0x01) == 0x01)
        {
            bret = v_action_mode_one(event);
        }
        else if((imode & 0x02) == 0x02)
        {
            bret = v_action_mode_two(event);
        }

        return bret;
    }

    public void change()
    {
        if(mOnChangeListener != null)
        {
            mOnChangeListener.onChange();
        }
    }

    public void change_finish(float fspeed)
    {
        if(mOnChangeListener != null)
        {
            mOnChangeListener.onChange_finish(fspeed);
        }
    }

    public void setOnChangeListener(RtxRecPercent.OnChangeListener l) {
        mOnChangeListener = l;
    }

    public interface OnChangeListener
    {
        public abstract void onChange();
        public abstract void onChange_finish(float fspeed);
    }

}
