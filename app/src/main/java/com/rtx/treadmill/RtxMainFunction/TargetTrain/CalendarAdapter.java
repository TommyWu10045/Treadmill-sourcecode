package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxFillerTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by jerry on 2016/12/29.
 */

public class CalendarAdapter extends BaseAdapter {

    final int DATE_CIRCLE_SIZE = 70 ;

    private Context mContext;

    private Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar currentDate;


    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int mnthlength;
    String itemvalue, curentDateString, selectDateString;
    SimpleDateFormat df;
    int iCurrentPosition = -1;
    View vCurrentView = null;

    private ArrayList<String> items;
    public static List<String> dayString;
    private View previousView;

    Calendar cValidStartDate = GlobalData.getInstance();

    public CalendarAdapter(Context c, GregorianCalendar monthCalendar, Calendar cValidStartDate)
    {
        CalendarAdapter.dayString = new ArrayList<String>();
        month = monthCalendar;
        currentDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        GlobalData.vSetTimezone(df);
        curentDateString = df.format(currentDate.getTime());
        refreshDays();

        this.cValidStartDate = cValidStartDate;
    }



//    public void setItems(ArrayList<String> items)
//    {
//        for (int i = 0; i != items.size(); i++)
//        {
//            if (items.get(i).length() == 1)
//            {
//                items.set(i, "0" + items.get(i));
//            }
//        }
//        this.items = items;
//    }

    public int getCount()
    {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RtxFillerTextView dayView;

        {
            dayView = new RtxFillerTextView(mContext , false);

            FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(DATE_CIRCLE_SIZE, DATE_CIRCLE_SIZE);
            dayView.setLayoutParams(mLayoutParams);

            dayView.setTextSize(31f);
            dayView.setTextColor(Common.Color.white);
            dayView.setGravity(Gravity.CENTER);
            dayView.setTypeface(dayView.getTypeface(), Typeface.BOLD);
            dayView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Medium));
            dayView.setBackgroundColor(Common.Color.blue_1);
            dayView.setStrokeWidth(2);
            dayView.setMode(-1);
        }

        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        // checking whether the day is in current month or not.

        {
            int iCompare = 0;
            Calendar cDay = GlobalData.getInstance();
            cDay = Rtx_Calendar.cStr2Calendar(dayString.get(position),"yyyy-MM-dd");

            iCompare = cDay.compareTo(cValidStartDate);

            if(iCompare >= 0)
            {

            }
            else
            {
                dayView.setTextColor(Common.Color.gray_5);
                dayView.setClickable(false);
                dayView.setFocusable(false);
            }

        }

//        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay))
//        {
//            // setting offdays to white color.
//            dayView.setTextColor(Common.Color.gray_5);
//            dayView.setClickable(false);
//            dayView.setFocusable(false);
//        }
//        else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
//            dayView.setTextColor(Common.Color.gray_5);
//            dayView.setClickable(false);
//            dayView.setFocusable(false);
//        }
//        else
//        {
//            {
//                int iDiffDays = 0;
//                Calendar cal_Select = null;
//                Calendar cal_Today = GlobalData.getInstance();
//                cal_Select = Rtx_Calendar.cStr2Calendar(dayString.get(position),"yyyy-MM-dd");
//                iDiffDays = Rtx_Calendar.getDiffDays(cal_Today,cal_Select);
//                if(iDiffDays < 0)
//                {
//                    dayView.setTextColor(Common.Color.gray_5);
//                    dayView.setClickable(false);
//                    dayView.setFocusable(false);
//                }
//                else
//                {
//                    // setting curent month's days in blue color.
//                    dayView.setTextColor(Color.WHITE);
//                }
//            }
//        }

        if (dayString.get(position).equals(curentDateString))
        {
            iCurrentPosition = position;
            vCurrentView = dayView;
            setSelected(null,null);
        } else {

            dayView.setMode(-1);
        }
        dayView.setText(gridvalue);

        if(selectDateString != null)
        {
            if(dayString.get(position).equals(selectDateString))
            {
                setSelected(dayView,selectDateString);
            }
        }

        // create date string for comparison
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        return (View)dayView;
    }

    public View setSelected(View view,String sDate)
    {
        if(sDate != null)
        {
            selectDateString = sDate;
        }

        RtxFillerTextView textViewRoundRec_Current = (RtxFillerTextView)view;
        RtxFillerTextView textViewRoundRec_Previous = (RtxFillerTextView)previousView;

        if(vCurrentView != null)
        {
            setHighlightMode(2,(RtxFillerTextView)vCurrentView);
        }

        if (textViewRoundRec_Previous != null)
        {
            setHighlightMode(-1,textViewRoundRec_Previous);
        }

        if(textViewRoundRec_Current != null)
        {
            previousView = view;
            setHighlightMode(1, textViewRoundRec_Current);
            return view;
        }
        else
        {
            if(vCurrentView != null)
            {
                return vCurrentView;
            }
            else
            {
                return null;
            }
        }
    }

    // -1:Normal  1:Circle  2:Stroke Circle
    public void setHighlightMode(int iMode , RtxFillerTextView textViewRoundRec)
    {
        if(iMode == -1)
        {
            textViewRoundRec.setMode(-1);
        }
        else if(iMode == 1)
        {
            textViewRoundRec.setMode(1);
        }
        else if(iMode == 2)
        {
            textViewRoundRec.setMode(2);
        }
        else
        {
            textViewRoundRec.setMode(-1);
        }

        textViewRoundRec.setVisibility(View.INVISIBLE);
        textViewRoundRec.setVisibility(View.VISIBLE);
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);
        }
    }

    private int getMaxP() {
        int maxP;
        if ((month.get(GregorianCalendar.MONTH)) == (month.getActualMinimum(GregorianCalendar.MONTH)))
        {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }
}

