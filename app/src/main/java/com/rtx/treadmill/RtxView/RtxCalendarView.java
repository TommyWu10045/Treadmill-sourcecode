package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxCalendarView extends Rtx_BaseLayout
{
    Context mContext;
    int iLayoutWidth;
    int iLayoutHeight;

    Handler mUI_Handler;

    RtxImageView imageButton_Left;
    RtxImageView imageButton_Right;
    RtxTextView textView_Item;
    RtxCalendarGridView gridview;

    Calendar    cal_Select;

    Calendar    cal_ActualStart = GlobalData.getInstance();
    Calendar    cal_ActualEnd = GlobalData.getInstance();


    View        view_Select;

    ////////////////////////
    boolean bDateRangeHighlight = false;
    int iSelectDays = 0;
    ////////////////////////

    public Calendar month, itemmonth;// calendar instances.
    public CalendarAdapter adapter;// adapter instance
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event treadmill.com.treadmill

    OnChangeListener mOnChangeListener;
    Calendar cValidStartDate;

    public RtxCalendarView(Context context, int iW, int iH, Calendar cValidStartDate) {
        super(context);

        mContext = context;
        iLayoutWidth = iW;
        iLayoutHeight = iH;

        mUI_Handler = new Handler();

        this.cValidStartDate = cValidStartDate;

        init();
    }

    public void init()
    {
        init_layout();
        init_Calendar();

        setBackgroundColor(Common.Color.background);
    }

    private void init_layout()
    {
        int iPosX = 0;
        int iPosY = 0;
        int iW = 0;
        int iH = 0;

        int iGap = 10;

        {
            iPosX = iGap + iW + iGap;
            iW = iLayoutWidth - (2*iW) - (4*iGap);
            iH = 30;

            textView_Item = new RtxTextView(mContext);

            addRtxTextViewToLayout(textView_Item, "Current Date", 28f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, iPosX, iPosY, iW, iH);
        }

        {
            int iIndex = 0;
            int iSize = 7;

            iPosX = 0;
            iPosY = iPosY + iH + iGap;
            iW = iLayoutWidth / 7;
            iH = 70;

            String sWeeks[] = {"S" , "M" , "T" , "W" , "T" , "F" , "S"};

            for(iIndex = 0 ; iIndex < iSize ; iIndex++)
            {
                iPosX = iIndex * iW;
                RtxTextView textView_Week = new RtxTextView(mContext);
                setTextWeekArg(textView_Week,sWeeks[iIndex],iPosX,iPosY,iW,iH);
            }
        }

        {
            gridview = new RtxCalendarGridView(mContext);

            iPosX = 0;
            iPosY = iPosY + iH + iGap;
            iW = iLayoutWidth;
            iH = iLayoutHeight - iPosY;

            gridview.setNumColumns(7);
            gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridview.setVerticalSpacing(15);
            gridview.setHorizontalSpacing(15);
            gridview.setBackgroundColor(Common.Color.background);
            gridview.setHighLightColor_0(Common.Color.blue_1);
            gridview.setHighLightColor_1(Common.Color.gray_7);

            addViewToLayout(gridview,iPosX,iPosY,iW,iH);

//            {
//                Calendar cal = GlobalData.getInstance();
//                Log.e("Jerry","cal.get(Calendar.WEEK_OF_MONTH) = " + cal.get(Calendar.WEEK_OF_MONTH));
//                Log.e("Jerry","cal.get(Calendar.DAY_OF_WEEK) = " + cal.get(Calendar.DAY_OF_WEEK));
//            }
        }

        {
            iW = 33;
            iH = 33;
            iPosX = iGap;
            iPosY = 10;

            imageButton_Left = new RtxImageView(mContext);
            addRtxImagePaddingViewToLayout(imageButton_Left, R.drawable.target_cal_left,iPosX,iPosY,iW,iH,30);
            imageButton_Left.setVisibility(INVISIBLE);
        }

        {
            iPosX = iLayoutWidth - iW - iGap;

            imageButton_Right = new RtxImageView(mContext);
            addRtxImagePaddingViewToLayout(imageButton_Right,R.drawable.target_cal_right,iPosX,iPosY,iW,iH,30);
        }
    }

    private void setTextWeekArg(RtxTextView textView , String str , int iX , int iY , int iW , int iH)
    {
        addRtxTextViewToLayout(textView, str, 26f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, iX,iY,iW,iH);
    }

    private void init_Calendar()
    {
        month = GlobalData.getInstance();
        itemmonth = (Calendar) month.clone();

        items = new ArrayList<String>();

        adapter = new CalendarAdapter(mContext, (GregorianCalendar) month, cValidStartDate);

        gridview.setAdapter(adapter);

        textView_Item.setText( Common.MONTH_OF_YEAR[month.get(Calendar.MONTH)] + " " + month.get(Calendar.YEAR));

        imageButton_Left.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        imageButton_Right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String selectedGridDate = CalendarAdapter.dayString.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                boolean bSameDay = false;

                // navigate to next or previous month on clicking offdays.

                {
                    cal_Select = Rtx_Calendar.cStr2Calendar(selectedGridDate,"yyyy-MM-dd");

                    if(Rtx_Calendar.getDiffDays(GlobalData.getInstance(),cal_Select) <= 0)
                    {
                        return;
                    }

                    Calendar cDayMax = GlobalData.getInstance();
                    cDayMax.add(Calendar.DAY_OF_MONTH,998); //最大天数 999天

                    if(cal_Select.compareTo(cDayMax) > 0)
                    {
                        return;
                    }

                    if(iSelectDays > 0)
                    {
                        if(!checkDateValidInFreqMode(iSelectDays,cal_Select))
                        {
                            return;
                        }
                    }

                    cal_ActualEnd = (Calendar) cal_Select.clone();
                }

                if ((gridvalue > 10) && (position < 8))
                {
                    setPreviousMonth();
                    refreshCalendar();
                    //return;
                }
                else if ((gridvalue < 7) && (position > 28))
                {
                    setNextMonth();
                    refreshCalendar();
                    //return;
                }
                else
                {
                    bSameDay = Rtx_Calendar.isSameDate(cal_Select,null);
                    if(bSameDay)
                    {
                        return;
                    }
                }

                view_Select = v;
                ((CalendarAdapter) parent.getAdapter()).setSelected(view_Select,selectedGridDate);

                if(bTranslateToActualCal(iSelectDays))
                {
                    if(bDateRangeHighlight)
                    {
                        refreshHighlightRange(cal_ActualStart, cal_ActualEnd, month, 1);
                    }
                    else
                    {
                        refreshHighlightRange(cal_ActualStart, cal_Select, month, 0);
                    }

                    if(mOnChangeListener != null)
                    {
                        mOnChangeListener.onChange(cal_ActualEnd,true);
                    }
                }
                else
                {
                    if(bDateRangeHighlight)
                    {
//                        refreshHighlightRange(cal_ActualStart, cal_ActualEnd, month, 1);
                        if(mOnChangeListener != null)
                        {
                            mOnChangeListener.onChange(cal_ActualEnd,false);
                        }
                    }
                    else
                    {
                        refreshHighlightRange(cal_ActualStart, cal_Select, month, 0);

                        if(mOnChangeListener != null)
                        {
                            mOnChangeListener.onChange(cal_ActualEnd,true);
                        }
                    }
                }


                //showToast(selectedGridDate);
            }
        });
    }

    private boolean bTranslateToActualCal(int iSelectDays)
    {
        boolean bRet = false;

        cal_ActualStart = GlobalData.getInstance();

        if(Rtx_Calendar.getDiffDays(cal_ActualStart,cal_ActualEnd) <= 0)
        {
            return false;
        }

        if(!bDateRangeHighlight)
        {
            //20190121文件 規格變更 增加Highlight功能
//            return true;
        }

        if(iSelectDays <= 0)
        {
            return false;
        }

        if((cal_ActualStart.get(Calendar.YEAR) == cal_ActualEnd.get(Calendar.YEAR)) &&
           (cal_ActualStart.get(Calendar.WEEK_OF_YEAR) == cal_ActualEnd.get(Calendar.WEEK_OF_YEAR)))
        {
            int iDiffDays = 0;

            iDiffDays = Rtx_Calendar.getDiffDays(cal_ActualStart,cal_ActualEnd) + 1;

            if(iDiffDays < iSelectDays)
            {
                clearMonthHighlightRange();
                return false;
            }
        }

        {
            int iDaysLeft = Rtx_Calendar.iGetDaysLeftOfWeek(cal_ActualStart);

            if(iDaysLeft < iSelectDays)
            {
                cal_ActualStart.add(Calendar.DAY_OF_MONTH,iDaysLeft);
            }
        }

        {
            //int iDayOfWeek = cal_ActualEnd.get(Calendar.DAY_OF_WEEK);
            int iDayOfWeek = Rtx_Calendar.iGetDaysLeftOfWeek(cal_ActualEnd) - 1;


            //if (iDayOfWeek < iSelectDays)
            {
                cal_ActualEnd.add(Calendar.DAY_OF_MONTH, iDayOfWeek);
            }
        }

        bRet = true;
        return bRet;
    }

    protected void showToast(String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();

    }

    protected void setNextMonth() {
        if ((month.get(Calendar.MONTH)) == (month.getActualMaximum(Calendar.MONTH))) {
            month.set((month.get(Calendar.YEAR) + 1),
                    month.getActualMinimum(Calendar.MONTH), 1);
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if ((month.get(Calendar.MONTH)) == (month.getActualMinimum(Calendar.MONTH))) {
            month.set((month.get(Calendar.YEAR) - 1),
                    month.getActualMaximum(Calendar.MONTH), 1);
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
        }
    }

    public void refreshCalendar()
    {
        Calendar cToday = GlobalData.getInstance();

        if((cToday.get(Calendar.MONTH) == month.get(Calendar.MONTH)) && (cToday.get(Calendar.YEAR) == month.get(Calendar.YEAR)))
        {
            imageButton_Left.setVisibility(INVISIBLE);
        }
        else
        {
            imageButton_Left.setVisibility(VISIBLE);
        }


        adapter.refreshDays();
        adapter.notifyDataSetChanged();

        textView_Item.setText( Common.MONTH_OF_YEAR[month.get(Calendar.MONTH)] + " " + month.get(Calendar.YEAR));
        if(bDateRangeHighlight)
        {
            refreshHighlightRange(cal_ActualStart, cal_ActualEnd, month, 1);
        }
        else
        {
            refreshHighlightRange(cal_ActualStart, cal_Select, month, 0);
        }

    }

    public void setDateRangeHighlight(boolean bEnable)
    {
        bDateRangeHighlight = bEnable;
    }

    public boolean refreshHighlightRange(Calendar cStart, Calendar cEnd, Calendar cMonth, int iMode)
    {
        boolean bRet = false;
        boolean bSameDay = false;

        if(!bDateRangeHighlight)
        {
            //20190121文件 規格變更 增加Highlight功能
//            return true;
        }

        bSameDay = Rtx_Calendar.isSameDate(cEnd,null);
        if(bSameDay)
        {
            return bRet;
        }

        Calendar cStartOfThisMonth = GlobalData.getInstance();
        Calendar cEndOfThisMonth = GlobalData.getInstance();

        if(cStart == null)
        {
            clearMonthHighlightRange();
            return bRet;
        }

        if(cEnd == null)
        {
            clearMonthHighlightRange();
            return bRet;
        }

        if(cMonth == null)
        {
            clearMonthHighlightRange();
            return bRet;
        }

        if(iMode == 0)
        {
            refreshMonthHighlightRange_0(cStart,cEnd,cMonth);
        }
        else
        {
            refreshMonthHighlightRange_1(cStart,cEnd,cMonth);
        }

        bRet = true;
        return bRet;
    }

    public void clearMonthHighlightRange()
    {
        gridview.clear();
    }

    public void refreshMonthHighlightRange_0(Calendar cStart, Calendar cEnd, Calendar cMonth)
    {
        Calendar cMonthStart = (Calendar)cMonth.clone();
        Calendar cMonthEnd = (Calendar)cMonth.clone();
        int iCompare = 0;

        int iStartRow = 0;
        int iEndRow = 0;
        int iStartColumn = 0;
        int iEndColumn = 0;

        {
            cMonthStart.set(Calendar.DATE, 1);
            cMonthEnd.set(Calendar.DATE, 1);
            cMonthEnd.add(Calendar.MONTH, 1);
            cMonthEnd.add(Calendar.DATE, -1);
        }

        iCompare = compareMonth(cMonthStart, cStart);
        if(iCompare == 0)
        {
            iStartRow = cStart.get(Calendar.WEEK_OF_MONTH) - 1;
            iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
        }
        else if(iCompare == 1)
        {
            if(cMonthStart.get(Calendar.WEEK_OF_YEAR) == cStart.get(Calendar.WEEK_OF_YEAR))
            {
                iStartRow = 0;
                iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
            }
            else
            {
                iStartRow = 0;
                iStartColumn = 0;
            }
        }
        else
        {
            if(cMonthEnd.get(Calendar.WEEK_OF_YEAR) == cStart.get(Calendar.WEEK_OF_YEAR))
            {
                iStartRow = cMonthEnd.get(Calendar.WEEK_OF_MONTH) - 1;
                iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
            }
            else
            {
                clearMonthHighlightRange();
                return;
            }
        }

        iCompare = compareMonth(cMonthStart,cEnd);
        if(iCompare == 0)
        {
            iEndRow = cEnd.get(Calendar.WEEK_OF_MONTH) - 1;
            iEndColumn = cEnd.get(Calendar.DAY_OF_WEEK) - 1;
        }
        else if(iCompare == -1)
        {
            iEndRow = cMonthEnd.get(Calendar.WEEK_OF_MONTH) - 1;
            iEndColumn = cEnd.get(Calendar.DAY_OF_WEEK) - 1;
        }
        else
        {
            if(cMonthStart.get(Calendar.WEEK_OF_YEAR) == cEnd.get(Calendar.WEEK_OF_YEAR))
            {
                iEndRow = 0;
                iEndColumn = cEnd.get(Calendar.DAY_OF_WEEK) - 1;
            }
            else
            {
                clearMonthHighlightRange();
                return;
            }
        }

        gridview.setDrawRange(iStartRow,iStartColumn,iEndRow,iEndColumn);
    }

    public void refreshMonthHighlightRange_1(Calendar cStart, Calendar cEnd, Calendar cMonth)
    {
        Calendar cMonthStart = (Calendar)cMonth.clone();
        Calendar cMonthEnd = (Calendar)cMonth.clone();
        int iCompare = 0;

        final boolean bStartDayIsAlwaysFirstDay = true; // false : start day is today.

        int iStartRow = 0;
        int iEndRow = 0;
        int iStartColumn = 0;
        int iEndColumn = 0;

        {
            cMonthStart.set(Calendar.DATE, 1);
            cMonthEnd.set(Calendar.DATE, 1);
            cMonthEnd.add(Calendar.MONTH, 1);
            cMonthEnd.add(Calendar.DATE, -1);
        }

        iCompare = compareMonth(cMonthStart, cStart);
        if(iCompare == 0)
        {
            iStartRow = cStart.get(Calendar.WEEK_OF_MONTH) - 1;
            iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
        }
        else if(iCompare == 1)
        {
            if(cMonthStart.get(Calendar.WEEK_OF_YEAR) == cStart.get(Calendar.WEEK_OF_YEAR))
            {
                iStartRow = 0;
                iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
            }
            else
            {
                iStartRow = 0;
                iStartColumn = 0;
            }
        }
        else
        {
            if(cMonthEnd.get(Calendar.WEEK_OF_YEAR) == cStart.get(Calendar.WEEK_OF_YEAR))
            {
                iStartRow = cMonthEnd.get(Calendar.WEEK_OF_MONTH) - 1;
                iStartColumn = cStart.get(Calendar.DAY_OF_WEEK) - 1;
            }
            else
            {
                clearMonthHighlightRange();
                return;
            }
        }

        iCompare = compareMonth(cMonthStart,cEnd);
        if(iCompare == 0)
        {
            iEndRow = cEnd.get(Calendar.WEEK_OF_MONTH) - 1;
            iEndColumn = cEnd.get(Calendar.DAY_OF_WEEK) - 1;
        }
        else if(iCompare == -1)
        {
            iEndRow = cMonthEnd.get(Calendar.WEEK_OF_MONTH) - 1;
            iEndColumn = 6;
        }
        else
        {
            if(cMonthStart.get(Calendar.WEEK_OF_YEAR) == cEnd.get(Calendar.WEEK_OF_YEAR))
            {
                iEndRow = 0;
                iEndColumn = 6;
            }
            else
            {
                clearMonthHighlightRange();
                return;
            }
        }

        if(bStartDayIsAlwaysFirstDay)
        {
            iStartColumn = 0;
        }

        gridview.setDrawRange_1(iStartRow,iStartColumn,iEndRow,iEndColumn);
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {

            adapter.notifyDataSetChanged();
        }
    };

    public void setOnChangeListener(RtxCalendarView.OnChangeListener l) {
        mOnChangeListener = l;
    }

    public interface OnChangeListener
    {
        public abstract void onChange(Calendar cal, boolean bValidDate);
    }

    public Calendar getSelecrCal()
    {
        return cal_Select;
    }

    public Calendar getActualStartCal()
    {
        return cal_ActualStart;
    }

    public Calendar getCal_ActualEnd()
    {
        return  cal_ActualEnd;
    }

    public void setSelectDays(int days)
    {
        iSelectDays = days;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean checkDateValidInFreqMode(int iDaysPerWeek , Calendar cSelect)
    {
        boolean bResult = false;
        int iCompare = 0;
        Calendar cStartValidDate = GlobalData.getInstance();

        if(Rtx_Calendar.iGetDaysLeftOfWeek(cStartValidDate) >= iDaysPerWeek)
        {
            cStartValidDate.set(Calendar.DAY_OF_WEEK,1);
            cStartValidDate.add(Calendar.WEEK_OF_YEAR,2);
        }
        else
        {
            cStartValidDate.set(Calendar.DAY_OF_WEEK,1);
            cStartValidDate.add(Calendar.WEEK_OF_YEAR,3);
        }

        cStartValidDate.set(Calendar.AM_PM,Calendar.AM);
        cStartValidDate.set(Calendar.HOUR,0);
        cStartValidDate.set(Calendar.MINUTE,0);
        cStartValidDate.set(Calendar.SECOND,0);
        cStartValidDate.set(Calendar.MILLISECOND,0);
//        Log.e("Jerry","cSelect = " + Rtx_Calendar.sCalendar2Str(cSelect,"yyyy-MM-dd HH:mm:ss"));
//        Log.e("Jerry","cStartValidDate = " + Rtx_Calendar.sCalendar2Str(cStartValidDate,"yyyy-MM-dd HH:mm:ss"));

        iCompare = cSelect.compareTo(cStartValidDate);

        if(iCompare >= 0)
        {
            bResult = true;
        }
        else
        {
            bResult = false;
        }

        return bResult;
    }

    public int compareMonth(Calendar cal_1,Calendar cal_2)
    {
        int iSame = 99999;

        iSame = cal_1.compareTo(cal_2);

        if(cal_1.get(Calendar.YEAR) == cal_2.get(Calendar.YEAR))
        {
            if(cal_1.get(Calendar.MONTH) == cal_2.get(Calendar.MONTH))
            {
                iSame = 0;
            }
        }

        return iSame;
    }
}
