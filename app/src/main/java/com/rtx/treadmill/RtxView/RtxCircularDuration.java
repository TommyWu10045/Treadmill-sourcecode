package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;

import com.rtx.treadmill.GlobalData.Common;

/**
 * TODO: document your custom view class.
 */
public class RtxCircularDuration extends RtxImageView {

    /**
     * Used to scale the dp units to pixels
     */
    protected final float DPTOPX_SCALE = 1f;

    /**
     * Minimum touch target size in DP. 48dp is the Android design recommendation
     */
    protected final float MIN_TOUCH_TARGET_DP = 48;

    // Default values
    protected static final float DEFAULT_CIRCLE_X_RADIUS = 30f;
    protected static final float DEFAULT_CIRCLE_Y_RADIUS = 30f;
    protected static final float DEFAULT_POINTER_RADIUS = 10;
    protected static final float DEFAULT_POINTER_HALO_WIDTH = 6f;
    protected static final float DEFAULT_POINTER_HALO_BORDER_WIDTH = 2f;
    protected static final float DEFAULT_CIRCLE_STROKE_WIDTH = 105f;

//    protected static final float DEFAULT_START_ANGLE = 180f; // Geometric (clockwise, relative to 3 o'clock)
//    protected static final float DEFAULT_END_ANGLE = 360f; // Geometric (clockwise, relative to 3 o'clock)

    protected static final float DEFAULT_START_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)
    protected static final float DEFAULT_END_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)

    protected static final int DEFAULT_MAX = 100;
    protected static final int DEFAULT_PROGRESS = 0;
    protected static final int DEFAULT_CIRCLE_PROGRESS_COLOR = Color.argb(235, 74, 138, 255);
    protected static final int DEFAULT_POINTER_COLOR = Color.argb(235, 74, 138, 255);
    protected static final int DEFAULT_POINTER_HALO_COLOR = Color.argb(135, 74, 138, 255);
    protected static final int DEFAULT_POINTER_HALO_COLOR_ONTOUCH = Color.argb(135, 74, 138, 255);
    protected static final int DEFAULT_CIRCLE_FILL_COLOR = Color.TRANSPARENT;
    protected static final int DEFAULT_POINTER_ALPHA = 135;
    protected static final int DEFAULT_POINTER_ALPHA_ONTOUCH = 100;
    protected static final boolean DEFAULT_USE_CUSTOM_RADII = false;
    protected static final boolean DEFAULT_MAINTAIN_EQUAL_CIRCLE = true;
    protected static final boolean DEFAULT_MOVE_OUTSIDE_CIRCLE = false;
    protected static final boolean DEFAULT_LOCK_ENABLED = true;

    protected static final int DEFAULT_MARKLINE_POSITION_MAX = 60;

    /**
     * {@code Paint} instance used to draw the glow from the active circle.
     */
    protected Paint mCircleProgressGlowPaint;

    /**
     * {@code Paint} instance used to draw the active circle (represents progress).
     */
    protected Paint mCircleProgressPaint;

    /**
     * {@code Paint} instance used to draw the center of the pointer.
     * Note: This is broken on 4.0+, as BlurMasks do not work with hardware acceleration.
     */
    protected Paint mPointerPaint;
    protected Paint mCenterCirclePaint;

    protected Paint mMarkLinePaint;

    /**
     * The width of the circle (in pixels).
     */
    protected float mCircleStrokeWidth;

    /**
     * The X radius of the circle (in pixels).
     */
    protected float mCircleXRadius;

    /**
     * The Y radius of the circle (in pixels).
     */
    protected float mCircleYRadius;

    /**
     * The radius of the pointer (in pixels).
     */
    protected float mPointerRadius;

    /**
     * The width of the pointer halo (in pixels).
     */
    protected float mPointerHaloWidth;

    /**
     * The width of the pointer halo border (in pixels).
     */
    protected float mPointerHaloBorderWidth;

    /**
     * Start angle of the CircularSeekBar.
     * Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted
     * from the mEndAngle to make the circle function properly.
     */
    protected float mStartAngle;

    /**
     * End angle of the CircularSeekBar.
     * Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted
     * from the mEndAngle to make the circle function properly.
     */
    protected float mEndAngle;

    /**
     * {@code RectF} that represents the circle (or ellipse) of the seekbar.
     */
    protected RectF mCircleRectF = new RectF();

    /**
     * Holds the color value for {@code mPointerPaint} before the {@code Paint} instance is created.
     */
    protected int mPointerColor = DEFAULT_POINTER_COLOR;
    protected int mCenterCircleColor = DEFAULT_POINTER_COLOR;

    /**
     * Holds the color value for {@code mPointerHaloPaint} before the {@code Paint} instance is created.
     */
    protected int mPointerHaloColor = DEFAULT_POINTER_HALO_COLOR;

    /**
     * Holds the color value for {@code mPointerHaloPaint} before the {@code Paint} instance is created.
     */
    protected int mPointerHaloColorOnTouch = DEFAULT_POINTER_HALO_COLOR_ONTOUCH;

    /**
     * Holds the color value for {@code mCircleFillPaint} before the {@code Paint} instance is created.
     */
    protected int mCircleFillColor = DEFAULT_CIRCLE_FILL_COLOR;

    /**
     * Holds the color value for {@code mCircleProgressPaint} before the {@code Paint} instance is created.
     */
    protected int mCircleProgressColor = DEFAULT_CIRCLE_PROGRESS_COLOR;
    protected int mCircleProgressGlowColor = DEFAULT_CIRCLE_PROGRESS_COLOR;

    /**
     * Holds the alpha value for {@code mPointerHaloPaint}.
     */
    protected int mPointerAlpha = DEFAULT_POINTER_ALPHA;

    /**
     * Holds the OnTouch alpha value for {@code mPointerHaloPaint}.
     */
    protected int mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;

    /**
     * Distance (in degrees) that the the circle/semi-circle makes up.
     * This amount represents the max of the circle in degrees.
     */
    protected float mTotalCircleDegrees;

    /**
     * Distance (in degrees) that the current progress makes up in the circle.
     */
    protected float mProgressDegrees;

    /**
     * {@code Path} used to draw the circle/semi-circle.
     */
    protected Path mCirclePath;

    /**
     * {@code Path} used to draw the progress on the circle.
     */
    protected Path mCircleProgressPath;
    protected Path mCircleProgressGlowPath;

    /**
     * Max value that this CircularSeekBar is representing.
     */
    protected int mMax;

    /**
     * Progress value that this CircularSeekBar is representing.
     */
    protected int mProgress;

    /**
     * If true, then the user can specify the X and Y radii.
     * If false, then the View itself determines the size of the CircularSeekBar.
     */
    protected boolean mCustomRadii;

    /**
     * Maintain a perfect circle (equal x and y radius), regardless of view or custom attributes.
     * The smaller of the two radii will always be used in this case.
     * The default is to be a circle and not an ellipse, due to the behavior of the ellipse.
     */
    protected boolean mMaintainEqualCircle;

    /**
     * Once a user has touched the circle, this determines if moving outside the circle is able
     * to change the position of the pointer (and in turn, the progress).
     */
    protected boolean mMoveOutsideCircle;

    /**
     * Used for enabling/disabling the lock option for easier hitting of the 0 progress mark.
     */
    protected boolean lockEnabled = true;

    /**
     * Used for when the user moves beyond the start of the circle when moving counter clockwise.
     * Makes it easier to hit the 0 progress mark.
     */
    protected boolean lockAtStart = true;

    /**
     * Used for when the user moves beyond the end of the circle when moving clockwise.
     * Makes it easier to hit the 100% (max) progress mark.
     */
    protected boolean lockAtEnd = false;

    /**
     * When the user is touching the circle on ACTION_DOWN, this is set to true.
     * Used when touching the CircularSeekBar.
     */
    protected boolean mUserIsMovingPointer = false;

    /**
     * Represents the clockwise distance from {@code mStartAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    protected float cwDistanceFromStart;

    /**
     * Represents the counter-clockwise distance from {@code mStartAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    protected float ccwDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mEndAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    protected float cwDistanceFromEnd;

    /**
     * Represents the counter-clockwise distance from {@code mEndAngle} to the touch angle.
     * Used when touching the CircularSeekBar.
     * Currently unused, but kept just in case.
     */
    @SuppressWarnings("unused")
    protected float ccwDistanceFromEnd;

    /**
     * The previous touch action value for {@code cwDistanceFromStart}.
     * Used when touching the CircularSeekBar.
     */
    protected float lastCWDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mPointerPosition} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    protected float cwDistanceFromPointer;

    /**
     * Represents the counter-clockwise distance from {@code mPointerPosition} to the touch angle.
     * Used when touching the CircularSeekBar.
     */
    protected float ccwDistanceFromPointer;

    /**
     * True if the user is moving clockwise around the circle, false if moving counter-clockwise.
     * Used when touching the CircularSeekBar.
     */
    protected boolean mIsMovingCW;

    /**
     * The width of the circle used in the {@code RectF} that is used to draw it.
     * Based on either the View width or the custom X radius.
     */
    protected float mCircleWidth;

    /**
     * The height of the circle used in the {@code RectF} that is used to draw it.
     * Based on either the View width or the custom Y radius.
     */
    protected float mCircleHeight;

    /**
     * Represents the progress mark on the circle, in geometric degrees.
     * This is not provided by the user; it is calculated;
     */
    protected float mPointerPosition;

    /**
     * Pointer position in terms of X and Y coordinates.
     */
    protected float[] mPointerPositionXY = new float[2];

    protected float[][] mMarkLinePositionXY = new float[DEFAULT_MARKLINE_POSITION_MAX][2];
    protected float[][] mMarkLineStartPositionXY = new float[DEFAULT_MARKLINE_POSITION_MAX][2];
    protected float[][] mMarkLineEndPositionXY = new float[DEFAULT_MARKLINE_POSITION_MAX][2];

    protected int iRtxCircularDurationPartLen = 12; //default
    protected boolean bRtxCircularDurationPartEnd = false; //default

    /**
     * Listener.
     */
    protected OnCircularSeekBarChangeListener mOnCircularSeekBarChangeListener;

    /**
     * True if user touch input is enabled, false if user touch input is ignored.
     * This does not affect setting values programmatically.
     */
    protected boolean isTouchEnabled = true;
    protected float fOutRadiusRatio = 0f;
    protected float fOutMarkLineRadiusRatio = 0f;
    protected float fInMarkLineRadiusRatio = 0f;
    protected float fProgressLen = 0;
    protected int   iMarkHighLightIndex = 0;


    Bitmap b = null;
    Canvas c = null;
    int iW, iH;

    protected void initAttributes() {
        mCircleXRadius = DEFAULT_CIRCLE_X_RADIUS;
        mCircleYRadius = DEFAULT_CIRCLE_Y_RADIUS;
        mPointerRadius = DEFAULT_POINTER_RADIUS;
        mPointerHaloWidth = DEFAULT_POINTER_HALO_WIDTH;
        mPointerHaloBorderWidth = DEFAULT_POINTER_HALO_BORDER_WIDTH;
        mCircleStrokeWidth = DEFAULT_CIRCLE_STROKE_WIDTH;

        mPointerColor = DEFAULT_POINTER_COLOR;
        mCenterCircleColor = DEFAULT_POINTER_COLOR;
        mPointerHaloColor = DEFAULT_POINTER_HALO_COLOR;
        mPointerHaloColorOnTouch = DEFAULT_POINTER_HALO_COLOR_ONTOUCH;
        mCircleProgressColor = DEFAULT_CIRCLE_PROGRESS_COLOR;
        mCircleFillColor = DEFAULT_CIRCLE_FILL_COLOR;

        mPointerAlpha = Color.alpha(mPointerHaloColor);

        mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;
        if (mPointerAlphaOnTouch > 255 || mPointerAlphaOnTouch < 0) {
            mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;
        }

        mMax = DEFAULT_MAX;
        mProgress = DEFAULT_PROGRESS;
        mCustomRadii = DEFAULT_USE_CUSTOM_RADII;
        mMaintainEqualCircle = DEFAULT_MAINTAIN_EQUAL_CIRCLE;
        mMoveOutsideCircle = DEFAULT_MOVE_OUTSIDE_CIRCLE;
        lockEnabled = DEFAULT_LOCK_ENABLED;

        // Modulo 360 right now to avoid constant conversion
        mStartAngle =  DEFAULT_START_ANGLE % 360f;
        mEndAngle = DEFAULT_END_ANGLE % 360f;
        if (mStartAngle == mEndAngle) {
            mEndAngle = mEndAngle - .1f;
        }
    }

    /**
     * Initializes the {@code Paint} objects with the appropriate styles.
     */
    protected void initPaints() {
        mCircleProgressPaint = new Paint();
        mCircleProgressPaint.setAntiAlias(true);
        mCircleProgressPaint.setDither(true);
        mCircleProgressPaint.setColor(mCircleProgressColor);
        mCircleProgressPaint.setStrokeWidth(mCircleStrokeWidth);
        mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        //mCircleProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        //mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mCircleProgressGlowPaint = new Paint();
        mCircleProgressGlowPaint.set(mCircleProgressPaint);
        mCircleProgressGlowPaint.setMaskFilter(new BlurMaskFilter((5f * DPTOPX_SCALE), BlurMaskFilter.Blur.NORMAL));

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setDither(true);
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(mPointerColor);
        mPointerPaint.setStrokeWidth(mPointerRadius);
        mPointerPaint.setStrokeJoin(Paint.Join.ROUND);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);

        mCenterCirclePaint = new Paint();
        mCenterCirclePaint.set(mPointerPaint);
        mCenterCirclePaint.setColor(mCenterCircleColor);

        mMarkLinePaint = new Paint();
        mMarkLinePaint.setAntiAlias(true);
        mMarkLinePaint.setDither(true);
        mMarkLinePaint.setStyle(Paint.Style.FILL);
        mMarkLinePaint.setColor(0xFFFFFFFF);
        mMarkLinePaint.setStrokeWidth(3);

    }

    /**
     * Calculates the total degrees between mStartAngle and mEndAngle, and sets mTotalCircleDegrees
     * to this value.
     */
    protected void calculateTotalDegrees() {
        mTotalCircleDegrees = (360f - (mStartAngle - mEndAngle)) % 360f; // Length of the entire circle/arc
        if (mTotalCircleDegrees <= 0f) {
            mTotalCircleDegrees = 360f;
        }
    }

    /**
     * Calculate the degrees that the progress represents. Also called the sweep angle.
     * Sets mProgressDegrees to that value.
     */
    protected void calculateProgressDegrees() {
        mProgressDegrees = mPointerPosition - mStartAngle; // Verified
        mProgressDegrees = (mProgressDegrees < 0 ? 360f + mProgressDegrees : mProgressDegrees); // Verified
    }

    /**
     * Calculate the pointer position (and the end of the progress arc) in degrees.
     * Sets mPointerPosition to that value.
     */
    protected void calculatePointerAngle() {
        float progressPercent = ((float)mProgress / (float)mMax);
        mPointerPosition = (progressPercent * mTotalCircleDegrees) + mStartAngle;
        mPointerPosition = mPointerPosition % 360f;
    }

    protected void calculatePointerXYPosition() {
        PathMeasure pm = new PathMeasure(mCircleProgressPath, false);
        boolean returnValue = pm.getPosTan(pm.getLength() , mPointerPositionXY, null);

        fProgressLen = pm.getLength();
        if (!returnValue)
        {
            pm = new PathMeasure(mCirclePath, false);
            returnValue = pm.getPosTan(0, mPointerPositionXY, null);
        }
    }

    protected void calculateMarkLinePointerXYPosition()
    {
        PathMeasure pm = new PathMeasure(mCirclePath, false);
        boolean returnValue = false;
        int iIndex = 0;
        float fPartLen = pm.getLength() / iRtxCircularDurationPartLen;
        float fLen = 0;

        for (iIndex = 0 ; iIndex < iRtxCircularDurationPartLen ; iIndex++)
        {
            fLen = fPartLen*iIndex;
            returnValue = pm.getPosTan(fLen, mMarkLinePositionXY[iIndex], null);

            mMarkLineStartPositionXY[iIndex][0] = mMarkLinePositionXY[iIndex][0] * fInMarkLineRadiusRatio;
            mMarkLineStartPositionXY[iIndex][1] = mMarkLinePositionXY[iIndex][1] * fInMarkLineRadiusRatio;

            mMarkLineEndPositionXY[iIndex][0] = mMarkLinePositionXY[iIndex][0] * fOutMarkLineRadiusRatio;
            mMarkLineEndPositionXY[iIndex][1] = mMarkLinePositionXY[iIndex][1] * fOutMarkLineRadiusRatio;

            if(fLen <= fProgressLen)
            {
                iMarkHighLightIndex = iIndex;
            }
        }

    }

    /**
     * Initialize the {@code Path} objects with the appropriate values.
     */
    protected void initPaths() {
        mCirclePath = new Path();
        mCirclePath.addArc(mCircleRectF, mStartAngle, mTotalCircleDegrees);

        mCircleProgressPath = new Path();
        mCircleProgressPath.addArc(mCircleRectF, mStartAngle, mProgressDegrees);

        mCircleProgressGlowPath = new Path();
        mCircleProgressGlowPath.addArc(mCircleRectF, 0, 360);
    }

    /**
     * Initialize the {@code RectF} objects with the appropriate values.
     */
    protected void initRects() {
        mCircleRectF.set(-mCircleWidth, -mCircleHeight, mCircleWidth, mCircleHeight);
    }

    protected void Create_Bitmap() {
        b = Bitmap.createBitmap(iW, iH, Bitmap.Config.RGB_565);
        b.eraseColor(Common.Color.background);
        c = new Canvas(b);

        Create_Bitmap(c);
    }

    protected void Create_Bitmap(Canvas canvas) {
        canvas.translate(iW / 2, iH / 2);

        canvas.drawPath(mCircleProgressGlowPath, mCircleProgressGlowPaint);
        canvas.drawPath(mCircleProgressPath, mCircleProgressPaint);

        canvas.drawCircle(0, 0, 10, mCenterCirclePaint);
        canvas.drawLine(0, 0, mPointerPositionXY[0] * fOutRadiusRatio, mPointerPositionXY[1] * fOutRadiusRatio, mPointerPaint);

        int iIndex = 0;

        for(iIndex = 0 ; iIndex < iRtxCircularDurationPartLen ; iIndex++)
        {
            if(iIndex <= iMarkHighLightIndex || bRtxCircularDurationPartEnd == true)
            {
                //mMarkLinePaint.setAlpha(100);
                mMarkLinePaint.setColor(0xFFFFFFFF);
            }
            else
            {
                //mMarkLinePaint.setAlpha(50);
                mMarkLinePaint.setColor(0xFF373347);
            }

            canvas.drawLine(mMarkLineStartPositionXY[iIndex][0],mMarkLineStartPositionXY[iIndex][1],mMarkLineEndPositionXY[iIndex][0],mMarkLineEndPositionXY[iIndex][1],mMarkLinePaint);
        }

        setImageBitmap(b);
    }

    /**
     * Get the progress of the CircularSeekBar.
     * @return The progress of the CircularSeekBar.
     */
    public int getProgress() {
        int progress = Math.round((float)mMax * mProgressDegrees / mTotalCircleDegrees);
        return progress;
    }

    /**
     * Set the progress of the CircularSeekBar.
     * If the progress is the same, then any listener will not receive a onProgressChanged event.
     * @param progress The progress to set the CircularSeekBar to.
     */
    public void setProgress(int progress) {
        //if (mProgress != progress)
        {
            mProgress = progress;
            if (mOnCircularSeekBarChangeListener != null) {
                mOnCircularSeekBarChangeListener.onProgressChanged(this, progress, false);
            }

            recalculateAll();
            Create_Bitmap();
        }
    }

    public void setProgress(int progress, int imode) {
        if (imode == 0) {
            mProgress = progress;
            recalculateAll();
            Create_Bitmap();
        }
    }

    protected void setProgressBasedOnAngle(float angle) {
        mPointerPosition = angle;
        calculateProgressDegrees();
        mProgress = Math.round((float)mMax * mProgressDegrees / mTotalCircleDegrees);
    }

    protected void recalculateAll() {
        calculateTotalDegrees();
        calculatePointerAngle();
        calculateProgressDegrees();

        initRects();

        initPaths();

        calculatePointerXYPosition();
        calculateMarkLinePointerXYPosition();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = iH;
        int width = iW;

        if (mMaintainEqualCircle) {
            int min = Math.min(width, height);
            setMeasuredDimension(min, min);
        } else {
            setMeasuredDimension(width, height);
        }

        // Set the circle width and height based on the view for the moment
        mCircleHeight = ((float)height - mCircleStrokeWidth) / 2f ;
        mCircleWidth = ((float)width - mCircleStrokeWidth) / 2f ;

        // If it is not set to use custom
        if (mCustomRadii) {
            // Check to make sure the custom radii are not out of the view. If they are, just use the view values
            if ((mCircleYRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleHeight) {
                mCircleHeight = mCircleYRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }

            if ((mCircleXRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleWidth) {
                mCircleWidth = mCircleXRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }
        }

        if (mMaintainEqualCircle) { // Applies regardless of how the values were determined
            float min = Math.min(mCircleHeight, mCircleWidth);
            mCircleHeight = min;
            mCircleWidth = min;
        }

        float fHandLen = (Math.max(mCircleHeight, mCircleWidth) + (mCircleStrokeWidth / 2));
        fOutRadiusRatio =  fHandLen / (Math.max(mCircleHeight, mCircleWidth));

        float fMarkLineLen = (mCircleStrokeWidth / 4);
        float fOutMarkLineLen = (Math.max(mCircleHeight, mCircleWidth) + (fMarkLineLen / 2));
        fOutMarkLineRadiusRatio = fOutMarkLineLen / (Math.max(mCircleHeight, mCircleWidth));

        float fInMarkLineLen = (Math.max(mCircleHeight, mCircleWidth) - (fMarkLineLen / 2));
        fInMarkLineRadiusRatio = fInMarkLineLen / (Math.max(mCircleHeight, mCircleWidth));

        recalculateAll();
        Create_Bitmap();
    }

    /**
     * Get whether the pointer locks at zero and max.
     * @return Boolean value of true if the pointer locks at zero and max, false if it does not.
     */
    public boolean isLockEnabled() {
        return lockEnabled;
    }

    public void setLockEnabled(boolean lockEnabled) {
        this.lockEnabled = lockEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isTouchEnabled){
            return false;
        }

        // Convert coordinates to our internal coordinate system
        float x = event.getX() - getWidth() / 2;
        float y = event.getY() - getHeight() / 2;

        // Get the distance from the center of the circle in terms of x and y
        float distanceX = mCircleRectF.centerX() - x;       //mCircleRectF.centerX() = 0
        float distanceY = mCircleRectF.centerY() - y;       //mCircleRectF.centerY() = 0

        // Get the distance from the center of the circle in terms of a radius
        float touchEventRadius = (float) Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));

        float minimumTouchTarget = MIN_TOUCH_TARGET_DP * DPTOPX_SCALE; // Convert minimum touch target into px
        float additionalRadius; // Either uses the minimumTouchTarget size or larger if the ring/pointer is larger

        if (mCircleStrokeWidth < minimumTouchTarget)
        { // If the width is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        }
        else {
            additionalRadius = mCircleStrokeWidth / 2; // Otherwise use the width
        }
        float outerRadius = Math.max(mCircleHeight, mCircleWidth) + additionalRadius; // Max outer radius of the circle, including the minimumTouchTarget or wheel width
        float innerRadius = Math.min(mCircleHeight, mCircleWidth) - additionalRadius; // Min inner radius of the circle, including the minimumTouchTarget or wheel width

        if (mPointerRadius < (minimumTouchTarget / 2)) { // If the pointer radius is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        }
        else {
            additionalRadius = mPointerRadius; // Otherwise use the radius
        }

        float touchAngle;
        touchAngle = (float) ((Math.atan2(y, x) / Math.PI * 180) % 360); // Verified
        touchAngle = (touchAngle < 0 ? 360 + touchAngle : touchAngle); // Verified

        cwDistanceFromStart = touchAngle - mStartAngle; // Verified
        cwDistanceFromStart = (cwDistanceFromStart < 0 ? 360f + cwDistanceFromStart : cwDistanceFromStart); // Verified
        ccwDistanceFromStart = 360f - cwDistanceFromStart; // Verified

        cwDistanceFromEnd = touchAngle - mEndAngle; // Verified
        cwDistanceFromEnd = (cwDistanceFromEnd < 0 ? 360f + cwDistanceFromEnd : cwDistanceFromEnd); // Verified
        ccwDistanceFromEnd = 360f - cwDistanceFromEnd; // Verified

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // These are only used for ACTION_DOWN for handling if the pointer was the part that was touched
                float pointerRadiusDegrees = (float) ((mPointerRadius * 180) / (Math.PI * Math.max(mCircleHeight, mCircleWidth)));
                cwDistanceFromPointer = touchAngle - mPointerPosition;
                cwDistanceFromPointer = (cwDistanceFromPointer < 0 ? 360f + cwDistanceFromPointer : cwDistanceFromPointer);
                ccwDistanceFromPointer = 360f - cwDistanceFromPointer;

                // This is for if the first touch is on the actual pointer.
                if (((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) && ( (cwDistanceFromPointer <= pointerRadiusDegrees) || (ccwDistanceFromPointer <= pointerRadiusDegrees)) ) {
                    setProgressBasedOnAngle(mPointerPosition);
                    lastCWDistanceFromStart = cwDistanceFromStart;
                    mIsMovingCW = true;

                    recalculateAll();
                    Create_Bitmap();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                    }
                    mUserIsMovingPointer = true;
                    lockAtEnd = false;
                    lockAtStart = false;
                } else if (cwDistanceFromStart > mTotalCircleDegrees) { // If the user is touching outside of the start AND end
                    mUserIsMovingPointer = false;
                    return false;
                } else if ((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) { // If the user is touching near the circle
                    setProgressBasedOnAngle(touchAngle);
                    lastCWDistanceFromStart = cwDistanceFromStart;
                    mIsMovingCW = true;

                    recalculateAll();
                    Create_Bitmap();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                        mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                    }
                    mUserIsMovingPointer = true;
                    lockAtEnd = false;
                    lockAtStart = false;
                } else { // If the user is not touching near the circle
                    mUserIsMovingPointer = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mUserIsMovingPointer) {
                    if (lastCWDistanceFromStart < cwDistanceFromStart) {
                        if ((cwDistanceFromStart - lastCWDistanceFromStart) > 180f && !mIsMovingCW) {
                            lockAtStart = true;
                            lockAtEnd = false;
                        } else {
                            mIsMovingCW = true;
                        }
                    } else {
                        if ((lastCWDistanceFromStart - cwDistanceFromStart) > 180f && mIsMovingCW) {
                            lockAtEnd = true;
                            lockAtStart = false;
                        } else {
                            mIsMovingCW = false;
                        }
                    }

                    if (lockAtStart && mIsMovingCW) {
                        lockAtStart = false;
                    }
                    if (lockAtEnd && !mIsMovingCW) {
                        lockAtEnd = false;
                    }
                    if (lockAtStart && !mIsMovingCW && (ccwDistanceFromStart > 180)) {
                        lockAtStart = false;
                    }
                    if (lockAtEnd && mIsMovingCW && (cwDistanceFromEnd > 180)) {
                        lockAtEnd = false;
                    }
                    // Fix for passing the end of a semi-circle quickly
                    if (!lockAtEnd && cwDistanceFromStart > mTotalCircleDegrees && mIsMovingCW && lastCWDistanceFromStart < mTotalCircleDegrees) {
                        lockAtEnd = true;
                    }

                    if (lockAtStart && lockEnabled) {
                        // TODO: Add a check if mProgress is already 0, in which case don't call the listener
                        mProgress = 0;
                        recalculateAll();
                        Create_Bitmap();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }

                    } else if (lockAtEnd && lockEnabled) {
                        mProgress = mMax;
                        recalculateAll();
                        Create_Bitmap();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }
                    } else if ((mMoveOutsideCircle) || (touchEventRadius <= outerRadius)) {
                        if (!(cwDistanceFromStart > mTotalCircleDegrees)) {
                            setProgressBasedOnAngle(touchAngle);
                        }
                        recalculateAll();
                        Create_Bitmap();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }
                        lastCWDistanceFromStart = cwDistanceFromStart;
                    } else {
                        lastCWDistanceFromStart = cwDistanceFromStart;
                        break;
                    }

//                    lastCWDistanceFromStart = cwDistanceFromStart;
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mUserIsMovingPointer) {
                    mUserIsMovingPointer = false;
                    Create_Bitmap();
                    if (mOnCircularSeekBarChangeListener != null) {
                        mOnCircularSeekBarChangeListener.onStopTrackingTouch(this);
                    }
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL: // Used when the parent view intercepts touches for things like scrolling
                mUserIsMovingPointer = false;
                Create_Bitmap();
                break;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return true;
    }

    protected void init() {
        initAttributes();

        initPaints();
    }

    public RtxCircularDuration(Context context) {
        super(context, 0);
        init();
    }

    public void vSet_WH(int iw, int ih) {
        iW = iw;
        iH = ih;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable("PARENT", superState);
        state.putInt("MAX", mMax);
        state.putInt("PROGRESS", mProgress);
        state.putInt("mCircleProgressColor", mCircleProgressColor);
        state.putInt("mPointerColor", mPointerColor);
        state.putInt("mCenterCircleColor", mCenterCircleColor);
        state.putInt("mPointerHaloColor", mPointerHaloColor);
        state.putInt("mPointerHaloColorOnTouch", mPointerHaloColorOnTouch);
        state.putInt("mPointerAlpha", mPointerAlpha);
        state.putInt("mPointerAlphaOnTouch", mPointerAlphaOnTouch);
        state.putBoolean("lockEnabled", lockEnabled);
        state.putBoolean("isTouchEnabled", isTouchEnabled);

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;

        Parcelable superState = savedState.getParcelable("PARENT");
        super.onRestoreInstanceState(superState);

        mMax = savedState.getInt("MAX");
        mProgress = savedState.getInt("PROGRESS");
        mCircleProgressColor = savedState.getInt("mCircleProgressColor");
        mPointerColor = savedState.getInt("mPointerColor");
        mCenterCircleColor = savedState.getInt("mCenterCircleColor");
        mPointerHaloColor = savedState.getInt("mPointerHaloColor");
        mPointerHaloColorOnTouch = savedState.getInt("mPointerHaloColorOnTouch");
        mPointerAlpha = savedState.getInt("mPointerAlpha");
        mPointerAlphaOnTouch = savedState.getInt("mPointerAlphaOnTouch");
        lockEnabled = savedState.getBoolean("lockEnabled");
        isTouchEnabled = savedState.getBoolean("isTouchEnabled");

        initPaints();

        recalculateAll();
    }

    public void setOnSeekBarChangeListener(OnCircularSeekBarChangeListener l) {
        mOnCircularSeekBarChangeListener = l;
    }

    /**
     * Listener for the CircularSeekBar. Implements the same methods as the normal OnSeekBarChangeListener.
     */
    public interface OnCircularSeekBarChangeListener {

        public abstract void onProgressChanged(RtxCircularDuration circularSeekBar, int progress, boolean fromUser);

        public abstract void onStopTrackingTouch(RtxCircularDuration seekBar);

        public abstract void onStartTrackingTouch(RtxCircularDuration seekBar);
    }

    /**
     * Sets the circle progress color.
     * @param color the color of the circle progress
     */
    public void setCircleProgressGlowColor(int color) {
        mCircleProgressGlowColor = color;
        mCircleProgressGlowPaint.setColor(mCircleProgressGlowColor);
    }

    public void setCircleProgressColor(int color) {
        mCircleProgressColor = color;
        mCircleProgressPaint.setColor(mCircleProgressColor);
    }

    public void setCircleProgressPartEnd(boolean bFlag) {
        bRtxCircularDurationPartEnd = bFlag;
        if (bFlag == true)
        {
            mCircleProgressGlowPaint.setMaskFilter(new BlurMaskFilter((1f * DPTOPX_SCALE), BlurMaskFilter.Blur.SOLID));
        }
        else
        {
            mCircleProgressGlowPaint.setMaskFilter(new BlurMaskFilter((5f * DPTOPX_SCALE), BlurMaskFilter.Blur.NORMAL));
        }
    }

    /**
     * Sets the pointer color.
     * @param color the color of the pointer
     */
    public void setPointerColor(int color) {
        mPointerColor = color;
        mPointerPaint.setColor(mPointerColor);
    }

    public void setCenterCircleColor(int color) {
        mCenterCircleColor = color;
        mCenterCirclePaint.setColor(mCenterCircleColor);
    }

    /**
     * Set the max of the CircularSeekBar.
     * If the new max is less than the current progress, then the progress will be set to zero.
     * If the progress is changed as a result, then any listener will receive a onProgressChanged event.
     * @param max The new max for the CircularSeekBar.
     */
    public void setMax(int max) {
        if (!(max <= 0)) { // Check to make sure it's greater than zero
            if (max <= mProgress) {
                mProgress = 0; // If the new max is less than current progress, set progress to zero
                if (mOnCircularSeekBarChangeListener != null) {
                    mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, false);
                }
            }
            mMax = max;

            recalculateAll();
        }
    }

    /**
     * Get the current max of the CircularSeekBar.
     * @return Synchronized integer value of the max.
     */
    public synchronized int getMax() {
        return mMax;
    }


    public void setIsTouchEnabled(boolean isTouchEnabled) {
        this.isTouchEnabled = isTouchEnabled;
    }


    public boolean getIsTouchEnabled() {
        return isTouchEnabled;
    }

    public void vRtxCircularDuration_SetPartLen(int iNumber) {
        if (iNumber > DEFAULT_MARKLINE_POSITION_MAX)
        {
            iRtxCircularDurationPartLen = DEFAULT_MARKLINE_POSITION_MAX;
        }
        else if (iNumber < 0)
        {
            iRtxCircularDurationPartLen = 0;
        }
        else
        {
            iRtxCircularDurationPartLen = iNumber;
        }
    }
}
