package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.TimeFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RtxDrawView extends FrameLayout implements OnChartValueSelectedListener{
    private static String TAG = "Jerry=RTXDrawView";
    private static boolean DEBUG = false;

    final private static boolean bEnablePerformanceDisplayAllTag = false ;
    final private static int iPerformanceDisplayAllTagMode = 1;  // 0 : Just word  1 : MyMarker
    final private static boolean bEnablePerformanceDrawCirclePoint = false;
    final private static boolean bEnablePerformanceCubicBezier = true;
    final private static boolean bSkipZeroVal = false;

    final private static boolean bEnableBodyManagementDisplayAllTag = false ;
    final private static int iBodyManagementDisplayAllTagMode = 1;  // 0 : Just word  1 : MyMarker


    private TreeMap<String, SDraw_Data> TDraw_Data;

    private int ix_view = 0; //Draw x at View
    private int iy_view = 0; //Draw y at View
    private int iw_view = 800; //Draw width at View
    private int ih_view = 600; //Draw heigt at View
    private int Xcolor = Color.WHITE ;
    private int Ycolor = Color.WHITE ;
    private int Linecolor = Color.YELLOW ;

    private Context mContext ;
    private LineChart mChart;
    private MyMarkerView mv;
    ArrayList Xarray = new ArrayList<String>();

    private class SDraw_Data
    {
        public String stitle ;

        public int ixcount ;
        public String[] sx ;
        public int iycount ;
        public float fymin ;
        public float fymax ;
        public float[] dy ;

    }

    public RtxDrawView(Context context, int iX, int iY, int iWidth, int iHeight) {
        super(context);

        ix_view = iX;
        iy_view = iY;
        iw_view = iWidth;
        ih_view = iHeight;

        mContext = context;
        mChart = new LineChart(mContext);
        add_view(mChart, ix_view, iy_view, iw_view, ih_view);

        mChart.setOnChartValueSelectedListener(this);
        mv = new MyMarkerView(mContext, R.xml.custom_marker_view);
        mChart.setMarker(mv);
        //default chart configure
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setExtraLeftOffset(15);
        mChart.setExtraRightOffset(50);
        mChart.setExtraBottomOffset(10);

    }

    private ArrayList<Highlight> getHilights() {
        ArrayList<Highlight> highlights = new ArrayList<>();
        for (int i = 0; i < mChart.getData().getDataSetCount(); i++) {
            int xMin = (int) mChart.getData().getDataSetByIndex(i).getXMin();
            int xMax = (int) mChart.getData().getDataSetByIndex(i).getXMax();
            //float yMin = mChart.getData().getDataSetByIndex(i).getYMin();
            //float yMax = mChart.getData().getDataSetByIndex(i).getYMax();
            for (int j = xMin; j < xMax + 1; j++)
            {
                float y = mChart.getData().getDataSetByIndex(i).getEntryForXValue(j, Float.NaN).getY();
                //if (y == yMin || y == yMax)
                {
                    highlights.add(new Highlight(j, y, i));
                }
            }
        }
        return highlights;
    }

    public void RtxDrawView_BD(String sunit, int iDecimalNumber) {
        mChart.clear();
            //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //X軸設定
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        //set xlable string
        xAxis.set_xlabel_string("Date");
        xAxis.set_xlabel_size(11f);
        xAxis.set_xlabel_xShift(22);//distance from x line
        xAxis.set_xlabel_yShift(12);//distance from x string
        //set x string
        xAxis.setTextSize(14.66f);
        xAxis.setYOffset(5);
        xAxis.setTextColor(Xcolor);
        //set x line
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Xcolor);
        xAxis.set_xline_offset(30);//line往左延伸

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Xarray == null || value < 0)
                {
                    return "";
                }
                if(Xarray.size() <= 0 )
                {
                    return "";
                }
                if(Xarray.size() <= value )
                {
                    return "";
                }
                if(Xarray.get((int)value) == null)
                {
                    return "";
                }

                return (String) Xarray.get((int) value);
            }
        });

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setTextColor(Ycolor);
        leftAxis.setTextSize(14.66f);
        leftAxis.setXOffset(22); //和第一筆資料距離
        leftAxis.setMinWidth(110f);
        // min = 10 , max = 50 => diff = max - min = 40 ; ylabel_count = 7
        // bottom val = min - (diff * 下方空間比值/100)
        // top val = min + diff * ( 1 + 上方空間比值/100)
        // range = top val - bottom val
        // interval = range / ylabel_count
        // 每一格 : bottom val + interval * count[0~6]
        leftAxis.setSpaceTop(40f);//上方空間比值
        leftAxis.setSpaceBottom(10f);//下方空間比值
        leftAxis.set_rtx_unit(sunit);//最後一格字串
        leftAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        leftAxis.setValueFormatter(new LargeValueFormatter("一", iDecimalNumber)); // 小數幾位
        leftAxis.setDrawBottomYLabelEntryEnabled(false);
        leftAxis.setLabelCount(7, true);

        //右Y軸設定
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        //Lable設定
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);
//        l.setForm(Legend.LegendForm.EMPTY);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setXOffset(-52);
//        l.setYOffset(0);
//        l.setTextSize(14.66f);
//        l.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));

    }

    public void RtxDrawView_PF_Month(String sunit, String sTimeFormat) {
        mChart.clear();
        //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //X軸設定
        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        //set xlable string
        xAxis.set_xlabel_string("DATE");
        xAxis.set_xlabel_size(15f);
        xAxis.set_xlabel_xShift(15);//distance from x line
        xAxis.set_xlabel_yShift(-5);//distance from x string
        //set x string
        xAxis.setTextSize(18f);
        xAxis.setYOffset(5);
        xAxis.setTextColor(Xcolor);
        //set x line
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Xcolor);
        xAxis.set_xline_offset(40);//line往左延伸

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Xarray == null || value < 0)
                {
                    return "";
                }
                if(Xarray.size() <= 0 )
                {
                    return "";
                }
                if(Xarray.size() <= value )
                {
                    return "";
                }
                if(Xarray.get((int)value) == null)
                {
                    return "";
                }

                return (String) Xarray.get((int) value);
            }
        });

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setTextColor(Ycolor);
        leftAxis.setTextSize(14.66f);
        leftAxis.setXOffset(22); //和第一筆資料距離
        leftAxis.setMinWidth(120f);
        // min = 10 , max = 50 => diff = max - min = 40 ; ylabel_count = 7
        // bottom val = min - (diff * 下方空間比值/100)
        // top val = min + diff * ( 1 + 上方空間比值/100)
        // range = top val - bottom val
        // interval = range / ylabel_count
        // 每一格 : bottom val + interval * count[0~5]
        leftAxis.setSpaceTop(40f);//上方空間比值
        leftAxis.setSpaceBottom(10f);//下方空間比值
        leftAxis.set_rtx_unit(sunit);//最後一格字串
        leftAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        leftAxis.setValueFormatter(new TimeFormatter("一", sTimeFormat)); // Time Format
        leftAxis.setDrawBottomYLabelEntryEnabled(false);
        leftAxis.setLabelCount(6, true);

        //右Y軸設定
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        //Lable設定
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);
//        l.setForm(Legend.LegendForm.EMPTY);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setXOffset(-52);
//        l.setYOffset(0);
//        l.setTextSize(14.66f);
//        l.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));

    }

    public void RtxDrawView_PF_Month(String sunit, int iDecimalNumber) {
        mChart.clear();
        //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //X軸設定
        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        //set xlable string
        xAxis.set_xlabel_string("DATE");
        xAxis.set_xlabel_size(15f);
        xAxis.set_xlabel_xShift(15);//distance from x line
        xAxis.set_xlabel_yShift(-5);//distance from x string
        //set x string
        xAxis.setTextSize(18f);
        xAxis.setYOffset(5);
        xAxis.setTextColor(Xcolor);
        //set x line
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Xcolor);
        xAxis.set_xline_offset(40);//line往左延伸

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Xarray == null || value < 0)
                {
                    return "";
                }
                if(Xarray.size() <= 0 )
                {
                    return "";
                }
                if(Xarray.size() <= value )
                {
                    return "";
                }
                if(Xarray.get((int)value) == null)
                {
                    return "";
                }

                return (String) Xarray.get((int) value);
            }
        });

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setTextColor(Ycolor);
        leftAxis.setTextSize(14.66f);
        leftAxis.setXOffset(22); //和第一筆資料距離
        leftAxis.setMinWidth(120f);
        // min = 10 , max = 50 => diff = max - min = 40 ; ylabel_count = 7
        // bottom val = min - (diff * 下方空間比值/100)
        // top val = min + diff * ( 1 + 上方空間比值/100)
        // range = top val - bottom val
        // interval = range / ylabel_count
        // 每一格 : bottom val + interval * count[0~5]
        leftAxis.setSpaceTop(40f);//上方空間比值
        leftAxis.setSpaceBottom(10f);//下方空間比值
        leftAxis.set_rtx_unit(sunit);//最後一格字串
        leftAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        leftAxis.setValueFormatter(new LargeValueFormatter("一", iDecimalNumber)); // 小數幾位
        leftAxis.setDrawBottomYLabelEntryEnabled(false);
        leftAxis.setLabelCount(6, true);

        //右Y軸設定
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        //Lable設定
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);
//        l.setForm(Legend.LegendForm.EMPTY);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setXOffset(-52);
//        l.setYOffset(0);
//        l.setTextSize(14.66f);
//        l.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));

    }


    public void RtxDrawView_PF_Year(String sunit, String sTimeFormat)
    {
        mChart.clear();
        //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //X軸設定
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        //set xlable string
        xAxis.set_xlabel_string("MONTH");
        xAxis.set_xlabel_size(15f);
        xAxis.set_xlabel_xShift(15);//distance from x line
        xAxis.set_xlabel_yShift(-5);//distance from x string
        //set x string
        xAxis.setTextSize(18f);
        xAxis.setYOffset(5);
        xAxis.setTextColor(Xcolor);
        //set x line
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Xcolor);
        xAxis.set_xline_offset(40);//line往左延伸

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Xarray == null || value < 0)
                {
                    return "";
                }
                if(Xarray.size() <= 0 )
                {
                    return "";
                }
                if(Xarray.size() <= value )
                {
                    return "";
                }
                if(Xarray.get((int)value) == null)
                {
                    return "";
                }

                return (String) Xarray.get((int) value);
            }
        });

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setTextColor(Ycolor);
        leftAxis.setTextSize(14.66f);
        leftAxis.setXOffset(22); //和第一筆資料距離
        leftAxis.setMinWidth(120f);
        // min = 10 , max = 50 => diff = max - min = 40 ; ylabel_count = 7
        // bottom val = min - (diff * 下方空間比值/100)
        // top val = min + diff * ( 1 + 上方空間比值/100)
        // range = top val - bottom val
        // interval = range / ylabel_count
        // 每一格 : bottom val + interval * count[0~5]
        leftAxis.setSpaceTop(40f);//上方空間比值
        leftAxis.setSpaceBottom(10f);//下方空間比值
        leftAxis.set_rtx_unit(sunit);//最後一格字串
        leftAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        leftAxis.setValueFormatter(new TimeFormatter("一", sTimeFormat)); // Time Format
        leftAxis.setDrawBottomYLabelEntryEnabled(false);
        leftAxis.setLabelCount(6, true);

        //右Y軸設定
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        //Lable設定
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);
//        l.setForm(Legend.LegendForm.EMPTY);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setXOffset(-52);
//        l.setYOffset(0);
//        l.setTextSize(14.66f);
//        l.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
    }

    public void RtxDrawView_PF_Year(String sunit, int iDecimalNumber) {
        mChart.clear();
        //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //X軸設定
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        //set xlable string
        xAxis.set_xlabel_string("MONTH");
        xAxis.set_xlabel_size(15f);
        xAxis.set_xlabel_xShift(15);//distance from x line
        xAxis.set_xlabel_yShift(-5);//distance from x string
        //set x string
        xAxis.setTextSize(18f);
        xAxis.setYOffset(5);
        xAxis.setTextColor(Xcolor);
        //set x line
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Xcolor);
        xAxis.set_xline_offset(40);//line往左延伸

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if(Xarray == null || value < 0)
                {
                    return "";
                }
                if(Xarray.size() <= 0 )
                {
                    return "";
                }
                if(Xarray.size() <= value )
                {
                    return "";
                }
                if(Xarray.get((int)value) == null)
                {
                    return "";
                }

                return (String) Xarray.get((int) value);
            }
        });

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        leftAxis.setTextColor(Ycolor);
        leftAxis.setTextSize(14.66f);
        leftAxis.setXOffset(22); //和第一筆資料距離
        leftAxis.setMinWidth(120f);
        // min = 10 , max = 50 => diff = max - min = 40 ; ylabel_count = 7
        // bottom val = min - (diff * 下方空間比值/100)
        // top val = min + diff * ( 1 + 上方空間比值/100)
        // range = top val - bottom val
        // interval = range / ylabel_count
        // 每一格 : bottom val + interval * count[0~5]
        leftAxis.setSpaceTop(40f);//上方空間比值
        leftAxis.setSpaceBottom(10f);//下方空間比值
        leftAxis.set_rtx_unit(sunit);//最後一格字串
        leftAxis.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        leftAxis.setValueFormatter(new LargeValueFormatter("一", iDecimalNumber)); // 小數幾位
        leftAxis.setDrawBottomYLabelEntryEnabled(false);
        leftAxis.setLabelCount(6, true);

        //右Y軸設定
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        //Lable設定
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);
//        l.setForm(Legend.LegendForm.EMPTY);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setXOffset(-52);
//        l.setYOffset(0);
//        l.setTextSize(14.66f);
//        l.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));

    }

    //重新設定左Y軸最大值&最小值
    public void vRtxDrawView_setAxisMaxMin(float fMaxValue , float fMinValue) {
        mChart.clear();
        //圖表右下角字串設為""
        Description mDescript =  new Description();
        mDescript.setText("");
        mChart.setDescription(mDescript);

        //左Y軸設定
        YAxis leftAxis = mChart.getAxisLeft();
        if ( fMaxValue == 0) {
            leftAxis.resetAxisMaximum();
        }
        else {
            leftAxis.setAxisMaximum( fMaxValue );
        }

        if ( fMinValue == 0) {
            leftAxis.resetAxisMinimum();
        }
        else {
            leftAxis.setAxisMinimum( fMinValue );
        }
    }


    public void set_mark_mode(int imode)
    {
        if(mv != null) {
            mv.set_mark_type(imode, 20f, Common.Color.bd_word_marker, Common.Font.Relay_Black);
        }
    }

    public void add_view(LineChart mchart, int ix, int iy, int iw, int ih)
    {
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iw,ih);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = ix;
        mLayoutParams.topMargin = iy;
        mchart.setLayoutParams(mLayoutParams);
        addView(mchart);
    }

    private void setData(String sname) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        LineDataSet set1;
        int ixcount = 0;
        float fymin = 0;
        float fymax = 0;

        XAxis xAxis = mChart.getXAxis();
        if(TDraw_Data == null)
        {
            return;
        }

        for(Map.Entry<String, SDraw_Data> entry : TDraw_Data.entrySet()) {
            String key = entry.getKey();
            SDraw_Data value = entry.getValue();
            if(value != null)
            {
                fymin = value.fymin;
                fymax = value.fymax;
                for (int i = 0; i < value.iycount; i++)
                {
                    float val = value.dy[i];

                    if(bSkipZeroVal)
                    {
                        if(val != 0)
                        {
                            values.add(new Entry(i, val, null));
                        }
                        else
                        {
                            //values.add(new Entry(i, val, null));

                        }
                    }
                    else
                    {
                        values.add(new Entry(i, val, null));
                    }
                }

                Xarray.clear();
                for (int i = 0; i < value.ixcount; i++) {
                    Xarray.add(value.sx[i]);
                    ixcount++;
                }

                if(xAxis != null) {
                    xAxis.setAxisMinimum(0);
                    xAxis.setAxisMaximum(value.ixcount);
                    xAxis.setLabelCount(value.ixcount);
                }
                break;
            }
        }

        //mChart.setAutoScaleMinMaxEnabled(true);

        if(values.size() > 0)
        {
            if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0)
            {
                set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                set1.setLabel(sname);
                set1.setDrawHighlightIndicators(false);

                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            }
            else
            {
                // create a dataset and give it a type
                set1 = new LineDataSet(values, sname);
                set1.setDrawIcons(false);
                set1.setDrawHighlightIndicators(false);
                set1.setColor(Linecolor);
                set1.setLineWidth(3f);
                set1.setDrawValues(false);
                set1.setDrawCircles(false);

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(set1); // add the datasets

                // create a data object with the datasets
                LineData data = new LineData(dataSets);

                // set data
                mChart.setData(data);
            }


        }
    }


    public void Set_Draw_XLabel(String sname, int ixlen, String[] sx)
    {
        if(sx == null || sx.length <= 0)
        {
            return;
        }
        if(sx.length < ixlen)
        {
            return;
        }

        int i ;
        SDraw_Data sdata ;

        if(TDraw_Data == null)
        {
            TDraw_Data = new TreeMap<>();
            sdata = new SDraw_Data();
        }
        else
        {
            sdata = TDraw_Data.get(sname);
            if(sdata == null)
            {
                sdata = new SDraw_Data();
            }
        }


        sdata.ixcount = ixlen;

        if(sdata.sx != null)
        {
            if(sdata.sx.length < ixlen)
            {
                sdata.sx = null;
                sdata.sx = new String[ixlen];
            }
        }
        else
        {
            sdata.sx = new String[ixlen];
        }

        for(i = 0; i < ixlen; i++) {
            sdata.sx[i] = sx[i];
        }

        if(TDraw_Data.get(sname) == null) {
            TDraw_Data.put(sname, sdata);
        }

        return;
    }

    public void Set_Draw_YData(String sname, int iylen, float[] dy)
    {
        if(dy == null || dy.length <= 0)
        {
            return;
        }
        if(dy.length < iylen)
        {
            return;
        }

        int i ;
        float fymin = Float.MAX_VALUE;
        float fymax = 0;
        SDraw_Data sdata ;

        if(TDraw_Data == null)
        {
            TDraw_Data = new TreeMap<>();
            sdata = new SDraw_Data();
        }
        else
        {
            sdata = TDraw_Data.get(sname);
            if(sdata == null)
            {
                sdata = new SDraw_Data();
            }
        }

        sdata.iycount = iylen;
        if(sdata.dy != null)
        {
            if(sdata.dy.length < iylen)
            {
                sdata.dy = null;
                sdata.dy = new float[iylen];
            }
        }
        else
        {
            sdata.dy = new float[iylen];
        }

        for(i = 0; i < iylen; i++) {
            sdata.dy[i] = dy[i];
        }

        for(i = 0; i < iylen; i++) {
            if(fymin > dy[i])
            {
                fymin = dy[i];
            }
            if(fymax < dy[i])
            {
                fymax = dy[i];
            }
        }

        sdata.fymin = fymin;
        sdata.fymax = fymax;

        if(TDraw_Data.get(sname) == null) {
            TDraw_Data.put(sname, sdata);
        }

        return;
    }

    public void Create_Draw_Data(String sname, int ixlen, String[] sx, int iylen, float[] dy)
    {
        if(TDraw_Data != null) {
            TDraw_Data.clear();
        }

        if(sx != null && dy != null)
        {
            if(sx.length >= ixlen)
            {
                Set_Draw_XLabel(sname, ixlen, sx);

            }
            if(dy.length >= iylen)
            {
                Set_Draw_YData(sname, iylen, dy);

            }
            setData(sname);
        }
        mChart.invalidate();
    }

    public void set_Draw_mode(int imode)
    {
        LineDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            if(imode == 0)
            {
                set1.setDrawFilled(false);
                mChart.setTouchEnabled(false);
                set1.setColor(Linecolor);

                if(bEnablePerformanceCubicBezier)
                {
                    set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                }

                if(bEnablePerformanceDisplayAllTag)
                {
                    if(iPerformanceDisplayAllTagMode == 0)
                    {
                        set1.setValueTextSize(12f);
                        set1.setValueTextColor(Linecolor);
                        set1.setDrawValues(true);
                    }
                    else
                    {
                        Highlight[] highlight = getHilights().toArray(new Highlight[0]);
                        mChart.highlightValues(highlight);
                        set1.setDrawValues(false);
                    }

                    mChart.setTouchEnabled(false);
                }

                if(bEnablePerformanceDrawCirclePoint)
                {
                    //Draw point
                    set1.setDrawCircles(true);
                    //set1.setCircleRadius(20f);
                    set1.setCircleColor(Linecolor);
                    set1.setCircleColorHole(Linecolor);
                }
                else
                {
                    set1.setDrawCircles(false);
                }
            }
            else if(imode == 1) {
                set1.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_yellow);
                set1.setFillDrawable(drawable);
                mChart.setTouchEnabled(true);
                set1.setColor(Color.TRANSPARENT);

                if(bEnableBodyManagementDisplayAllTag)
                {
                    if(iBodyManagementDisplayAllTagMode == 0)
                    {
                        set1.setValueTextSize(12f);
                        set1.setValueTextColor(Linecolor);
                        set1.setDrawValues(true);
                    }
                    else
                    {
                        Highlight[] highlight = getHilights().toArray(new Highlight[0]);
                        mChart.highlightValues(highlight);
                        set1.setDrawValues(false);
                    }


                    mChart.setTouchEnabled(false);
                }
            }
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }
}
