
package com.github.mikephil.charting.formatter;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Predefined value-formatter that formats large numbers in a pretty way.
 * Outputs: 856 = 856; 1000 = 1k; 5821 = 5.8k; 10500 = 10k; 101800 = 102k;
 * 2000000 = 2m; 7800000 = 7.8m; 92150000 = 92m; 123200000 = 123m; 9999999 =
 * 10m; 1000000000 = 1b; Special thanks to Roman Gromov
 * (https://github.com/romangromov) for this piece of code.
 *
 * @author Philipp Jahoda
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
public class TimeFormatter implements IValueFormatter, IAxisValueFormatter
{
    private String mText = "";
    private String sTimeFormat = "";

    public TimeFormatter()
    {
        sTimeFormat = "";
    }

    /**
     * Creates a formatter that appends a specified text to the result string
     *
     * @param appendix a text that will be appended
     */
    public TimeFormatter(String appendix)
    {
        this();
        mText = appendix;
    }

    public TimeFormatter(String appendix, String sTimeFormat) {
        this();
        mText = appendix;
        this.sTimeFormat = new String(sTimeFormat);
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return sTranslate(value);
    }

    // IAxisValueFormatter
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return sTranslate(value);
    }

    private String sTranslate(float value)
    {
        int iSec = 0;
        String sResult;
        String sFormat = "";

        try
        {
            iSec = (int)value;
        }
        catch (Exception e)
        {

        }
        
        if(sTimeFormat.equals(""))
        {
            if(iSec >= 3600)
            {
                sFormat = new String("H:mm:ss");
            }
            else
            {
                sFormat = new String("m:ss");
            }
        }
        else
        {
            sFormat = new String(sTimeFormat);
        }

        sResult = new String(sSec2Str(iSec, sFormat) + " " + mText);

        return sResult;
    }

    /**
     * Set an appendix text to be added at the end of the formatted value.
     *
     * @param appendix
     */
    public void setAppendix(String appendix) {
        this.mText = appendix;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String sSec2Str(int iSec, String sFormat)
    {
        String str;
        Calendar cal = cSec2Calendar(iSec);

        str = sCalendar2Str(cal,sFormat);

        return str;
    }

    public static Calendar cSec2Calendar(int iSec)
    {
        Calendar    cal = null;

        cal = cStr2Calendar("00:00:00","HH:mm:ss");
        cal.add(Calendar.SECOND,(int)iSec);

        return cal;
    }

    public static String sCalendar2Str(Calendar cal, String sFormat)
    {
        String formatted = null;
        SimpleDateFormat ref;

        if(cal != null)
        {
            ref = new SimpleDateFormat(sFormat, Locale.ENGLISH);
            DateFormatSymbols symbols = new DateFormatSymbols();
            symbols.setAmPmStrings(new String[] { "am", "pm" });
            ref.setDateFormatSymbols(symbols);
            //SimpleDateFormat format = new SimpleDateFormat(sFormat);
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            formatted = ref.format(cal.getTime());
        }

        return formatted;
    }

    public static Calendar cStr2Calendar(String str, String sFormat)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            cal = null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            cal = null;
        }

        return cal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
