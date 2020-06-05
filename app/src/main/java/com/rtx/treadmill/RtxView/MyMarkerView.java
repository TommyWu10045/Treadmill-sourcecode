package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.rtx.treadmill.R;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private int imode = 0;
    private Context mContext;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        mContext = context;

        tvContent = (TextView) findViewById(R.id.tvContent);

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if((imode & 0x1000) == 0x1000)
        {
            int idigit = imode & 0x000F;
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), idigit, false));
            } else {
                tvContent.setText("" + Utils.formatNumber(e.getY(), idigit, false));
            }
        }
        else {
            if (e instanceof CandleEntry) {

                CandleEntry ce = (CandleEntry) e;

                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            } else {

                tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
            }
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public void set_mark_type(int imode, float fsize, int icolor, String sfont)
    {
        this.imode = imode;
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,fsize);
        tvContent.setTextColor(icolor);
        tvContent.setTypeface(Typeface.createFromAsset(mContext.getAssets(), sfont));
        set_first_show(0, 0);
    }
}
