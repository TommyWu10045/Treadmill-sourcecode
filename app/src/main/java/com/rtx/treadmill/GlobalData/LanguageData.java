package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;

import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxTextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by jerry on 2017/6/23.
 */

public class LanguageData
{
    /////////////////////////////////////////////////////////////////////////////////////
    private static String TAG = "Tom=";
    private static boolean DEBUG = false;
    private static boolean BTEST = false;

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public static int ilan = 3 ;
    public static void v_set_language(int ival) { ilan = ival; }
    public static int i_get_language( ) { return ilan; }
    public static String[][] llan_array = {
            {"العربية"         ,   "ks"     ,   "IN"   },   //阿拉柏
            {"简体中文"         ,   "za"    ,  "CN"    },
            {"繁體中文"         ,   "zh"    ,   "TW"   },
            //{"Dansk"           ,   "da"    ,   "DK"    },
            {"English - U.S."  ,   "en"    ,   "US"    },
            {"Français"        ,   "fr"    ,   "FR"    },   //法國
            {"Deutsch"         ,   "de"    ,   "DE"    },   //德國
            //{"Italiano"        ,   "it"    ,   "IT"    },
            {"日本語"            ,   "ja"    ,   "JP"    },    //日文
            {"Nederlands"      ,   "nl"    ,   "NL"    },   //荷蘭
            //{"Norsk"           ,   "nn"    ,   "NO"    },
            //{"فارسی"           ,   "fa"    ,   "AF"    },
            //{"Polski"          ,   "pl"    ,   "PL"    },
            {"Português"       ,   "pt"    ,  "PT"     },   //葡萄牙
            {"Pусский"         ,   "ru"    ,   "RU"    },   //俄羅斯
            {"Español"         ,   "es"    ,   "ES"    },   //西班牙
            {"Svenska"         ,   "sv"    ,   "SE"    },   //瑞典
            {"ไทย"             ,   "th"    ,   "TH"    },   //泰文
            {"Türk"            ,   "tr"    ,   "TR"    },   //土耳其
            {"tiếng việt"      ,   "vi"    ,   "VN"    }    //越南
    };

    public static ArrayList<CloudDataStruct.CLOUD_TATGET_GOAL> list_CloudTargetGoal = new ArrayList<>();

    private static String s_get_test_key()
    {
        String sret;

        sret = "-" + LanguageData.llan_array[LanguageData.ilan][1];

        return sret;
    }

    public static TreeMap<String, String> string_list = new TreeMap<String, String>();

    public static void clear()
    {
        string_list.clear();
    }

   public static void getDeviceLanguage(Context mContext) {
        String sfile = "values-" + LanguageData.llan_array[LanguageData.ilan][1] + "/strings.xml";
        if(DEBUG) Log.e(TAG, "=======sfile=" + sfile);
        clear();
        try {
            XmlPullParser parser ;
            InputStream istr = mContext.getAssets().open(sfile);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(istr, "UTF-8");

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName();

                    if (name.equals("string")) {
                        String currentID = null;
                        String currentVal = null;
                        int i = 0;
                        while (i < parser.getAttributeCount()) {
                            if (parser.getAttributeName(i).equals("name")) {
                                currentID = parser.getAttributeValue(i);
                                parser.next();
                                currentVal = parser.getText();
                                String str2 = currentVal.replace("\\","");
                                if(BTEST) {
                                    str2 += s_get_test_key();
                                }
                                string_list.put(currentID, str2);
                            }
                            i++;
                        }
                    }
                }
                eventType = parser.next();
            }
            istr.close();
        } catch (Exception e) {
            // do nothing
        }

        return ;
    }

    private static String get_string_from_list(String skey)
    {
        String sdata = null;

        if(skey != null)
        {
            sdata = string_list.get(skey);
        }

        return sdata;
    }

    public static String s_get_string(Context mContext, int ikey)
    {
        String sdata ;

        Resources res = mContext.getResources();
        String tag = res.getResourceEntryName(ikey);

        sdata = get_string_from_list(tag);
        if(DEBUG)Log.e(TAG, "========" + tag + "=" + sdata);
        if(sdata == null)
        {
            sdata = res.getString(ikey);
        }

        return sdata;
    }

    public static void s_set_string(Context mContext, int imode, Object mObj, int ikey)
    {
        String sdata ;

        Resources res = mContext.getResources();
        String tag = res.getResourceEntryName(ikey);

        sdata = get_string_from_list(tag);
        if(DEBUG)Log.e(TAG, "========" + tag + "=" + sdata);
        if(sdata == null)
        {
            sdata = res.getString(ikey);
        }

        if ( mObj.getClass() == RtxTextView.class ) {
            s_set_string(imode, (RtxTextView) mObj, sdata);
        }
        else if ( mObj.getClass() == RtxEditText.class ) {
            s_set_string(imode, (RtxEditText) mObj, sdata);
        }

    }

    public static void s_set_string(int imode, RtxTextView mView, String sdata)
    {
        if(imode == 0)
        {
            mView.setText(sdata);
        }
        else if(imode == 1) {
            mView.setHint(sdata);
        }
    }

    public static void s_set_string(int imode, RtxEditText mView, String sdata)
    {
        if(imode == 0)
        {
            mView.setText(sdata);
        }
        else if(imode == 1) {
            mView.setHint(sdata);
        }
    }

   private static SpannableString v_text_spannable(Context mContext, String str, String sub, int iid)
   {
       SpannableString msp = new SpannableString(str);
       String s;

       if(BTEST) {
           s = sub.replace(s_get_test_key(), "");
       }
       else
       {
           s = sub;
       }

       int istart = str.indexOf(s);
       int iend = istart + s.length();

       if(istart >= 0) {
           Drawable drawable = mContext.getResources().getDrawable(iid);
           drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
           ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE) {
               @Override
               public void draw(Canvas canvas, CharSequence text, int start,
                                int end, float x, int top, int y, int bottom,
                                Paint paint) {
                   Drawable b = getDrawable();
                   canvas.save();

                   int transY = bottom - b.getBounds().bottom;
                   // this is the key
//                   transY += paint.getFontMetricsInt().descent*2;
                   transY = (bottom - ((((paint.getFontMetricsInt().descent - paint.getFontMetricsInt().ascent)) + b.getBounds().bottom)/2));

                   canvas.translate(x, transY);
                   b.draw(canvas);
                   canvas.restore();
               }
           };
           msp.setSpan(imageSpan, istart, iend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       }

       return msp;
   }

   public static void s_set_string(Context mContext, RtxTextView mView, int ikey, int ikey_click, int iid)
   {
       String sdata ;
       String sclick ;

       Resources res = mContext.getResources();
       String tag = res.getResourceEntryName(ikey);
       String tag_click = res.getResourceEntryName(ikey_click);

       sdata = get_string_from_list(tag);
       sclick = get_string_from_list(tag_click);
       if(DEBUG)Log.e(TAG, "========" + tag + "=" + sdata);
       if(DEBUG)Log.e(TAG, "========" + tag_click + "=" + sclick);

       if(sdata != null)
       {
           if(sclick != null)
           {
               mView.setText(v_text_spannable(mContext, sdata, sclick, iid));
           }
           else
           {
               mView.setText(v_text_spannable(mContext, sdata, res.getString(ikey_click), iid));
           }
       }
       else
       {
           if(sclick != null)
           {
               mView.setText(v_text_spannable(mContext, res.getString(ikey), sclick, iid));
           }
           else
           {
               mView.setText(v_text_spannable(mContext, res.getString(ikey), res.getString(ikey_click), iid));
           }
       }
   }

    public static void s_set_string(Context mContext, RtxTextView mView, int ikey, int ikey_text, int ikey_click, int iid)
    {
        String sdata ;
        String sclick ;

        Resources res = mContext.getResources();
        String tag = res.getResourceEntryName(ikey);
        String tag_text = res.getResourceEntryName(ikey_text);
        String tag_click = res.getResourceEntryName(ikey_click);

        sdata = String.format(get_string_from_list(tag), get_string_from_list(tag_text));
        sclick = get_string_from_list(tag_click);
        if(DEBUG)Log.e(TAG, "========" + tag + "=" + sdata);
        if(DEBUG)Log.e(TAG, "========" + tag_click + "=" + sclick);

        if(sdata != null)
        {
            if(sclick != null)
            {
                mView.setText(v_text_spannable(mContext, sdata, sclick, iid));
            }
            else
            {
                mView.setText(v_text_spannable(mContext, sdata, res.getString(ikey_click), iid));
            }
        }
        else
        {
            if(sclick != null)
            {
                mView.setText(v_text_spannable(mContext, res.getString(ikey), sclick, iid));
            }
            else
            {
                mView.setText(v_text_spannable(mContext, res.getString(ikey), res.getString(ikey_click), iid));
            }
        }
    }

    public static String sGetUserLanguageTxt()
    {
        return llan_array[ilan][0];
    }
}
