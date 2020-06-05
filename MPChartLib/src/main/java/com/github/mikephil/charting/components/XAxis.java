
package com.github.mikephil.charting.components;

import com.github.mikephil.charting.utils.Utils;

/**
 * Class representing the x-axis labels settings. Only use the setter methods to
 * modify it. Do not access public variables directly. Be aware that not all
 * features the XLabels class provides are suitable for the RadarChart.
 *
 * @author Philipp Jahoda
 */
public class XAxis extends AxisBase {

    /**
     * width of the x-axis labels in pixels - this is automatically
     * calculated by the computeSize() methods in the renderers
     */
    public int mLabelWidth = 1;

    /**
     * height of the x-axis labels in pixels - this is automatically
     * calculated by the computeSize() methods in the renderers
     */
    public int mLabelHeight = 1;

    /**
     * width of the (rotated) x-axis labels in pixels - this is automatically
     * calculated by the computeSize() methods in the renderers
     */
    public int mLabelRotatedWidth = 1;

    /**
     * height of the (rotated) x-axis labels in pixels - this is automatically
     * calculated by the computeSize() methods in the renderers
     */
    public int mLabelRotatedHeight = 1;

    /**
     * This is the angle for drawing the X axis labels (in degrees)
     */
    protected float mLabelRotationAngle = 0f;

    /**
     * if set to true, the chart will avoid that the first and last label entry
     * in the chart "clip" off the edge of the chart
     */
    private boolean mAvoidFirstLastClipping = false;

    /**
     * the position of the x-labels relative to the chart
     */
    private XAxisPosition mPosition = XAxisPosition.TOP;

    /**
     * enum for the position of the x-labels relative to the chart
     */
    public enum XAxisPosition {
        TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE, BOTTOM_INSIDE
    }

    public XAxis() {
        super();

        mYOffset = Utils.convertDpToPixel(4.f); // -3
    }

    public int mxlabel_xShift = 0;
    public int mxlabel_yShift = 0;
    public float mxlabel_size = 10f;
    public String mxlabel_string = "";
    public int mxline_offset = 0;

    public int get_xlabel_xShift()
    {
        return  mxlabel_xShift;
    }

    public void set_xlabel_xShift(int shift)
    {
         mxlabel_xShift = shift;
    }

    public int get_xlabel_yShift()
    {
        return  mxlabel_yShift;
    }

    public void set_xlabel_yShift(int shift)
    {
         mxlabel_yShift = shift;
    }

    public float get_xlabel_size()
    {
        return  mxlabel_size;
    }

    public void set_xlabel_size(float fsize)
    {
        mxlabel_size = fsize;
    }

    public String get_xlabel_string()
    {
        return  mxlabel_string;
    }

    public void set_xlabel_string(String s)
    {
        mxlabel_string = s;
    }

    public int get_xline_offset()
    {
        return  mxline_offset;
    }

    public void set_xline_offset(int shift)
    {
        mxline_offset = shift;
    }


    /**
     * returns the position of the x-labels
     */
    public XAxisPosition getPosition() {
        return mPosition;
    }

    /**
     * sets the position of the x-labels
     *
     * @param pos
     */
    public void setPosition(XAxisPosition pos) {
        mPosition = pos;
    }

    /**
     * returns the angle for drawing the X axis labels (in degrees)
     */
    public float getLabelRotationAngle() {
        return mLabelRotationAngle;
    }

    /**
     * sets the angle for drawing the X axis labels (in degrees)
     *
     * @param angle the angle in degrees
     */
    public void setLabelRotationAngle(float angle) {
        mLabelRotationAngle = angle;
    }

    /**
     * if set to true, the chart will avoid that the first and last label entry
     * in the chart "clip" off the edge of the chart or the screen
     *
     * @param enabled
     */
    public void setAvoidFirstLastClipping(boolean enabled) {
        mAvoidFirstLastClipping = enabled;
    }

    /**
     * returns true if avoid-first-lastclipping is enabled, false if not
     *
     * @return
     */
    public boolean isAvoidFirstLastClippingEnabled() {
        return mAvoidFirstLastClipping;
    }
}
