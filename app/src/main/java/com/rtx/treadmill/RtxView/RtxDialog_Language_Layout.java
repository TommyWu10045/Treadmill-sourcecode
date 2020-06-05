package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Language_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxTextView          textView_SelectLanguage;
    public RtxFillerTextView[]  fillerTextView_Lang;

    private int iLangSize = 0;

    private ButtonListener mButtonListener;
    private OnSelectListener mOnSelectListener;

    public RtxDialog_Language_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {
        if(mButtonListener == null) { mButtonListener = new ButtonListener(); }
        iLangSize = LanguageData.llan_array.length;
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
        if(textView_SelectLanguage == null)     { textView_SelectLanguage = new RtxTextView(mContext);   }
        if(fillerTextView_Lang == null)         { fillerTextView_Lang = new RtxFillerTextView[iLangSize]; }

        int iIndex = 0;
        for( ; iIndex < iLangSize ; iIndex++)
        {
            if(fillerTextView_Lang[iIndex] == null) { fillerTextView_Lang[iIndex] = new RtxFillerTextView(mContext); }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,-1,-1,1196,590);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxTextViewToLayout(textView_SelectLanguage, R.string.select_language, 28f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 55 - 50, 1196, 20 + 100);

        addLanguageView();

        vSetHighlightIndex(LanguageData.ilan);
    }

    private void addLanguageView()
    {
        int iX = 66;
        int iY = 159;
        int iW = 210;
        int iH = 40;

        int iOffsetX = 213;
        int iOffsetY = 100;

        int iColumn = 5;

        int iIndex = 0;

        for( ; iIndex < iLangSize ; iIndex++)
        {
            int iTempX = iX + ((iIndex % iColumn) * iOffsetX);
            int iTempY = iY + ((iIndex / iColumn) * iOffsetY);

            addRtxTextViewToLayout(fillerTextView_Lang[iIndex], LanguageData.llan_array[iIndex][0], 23.33f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, iTempX, iTempY, iW, iH, Common.Color.blue_1);
            fillerTextView_Lang[iIndex].setOnClickListener(mButtonListener);
            fillerTextView_Lang[iIndex].setMode(-1);
        }
    }

    private void vSetHighlight(RtxFillerTextView view, boolean bHighlight)
    {
        if(bHighlight)
        {
            view.setTextColor(Common.Color.blue_1);
            view.setMode(5);
        }
        else
        {
            view.setTextColor(Common.Color.white);
            view.setMode(-1);
        }
    }

    public void vSetHighlightIndex(int iSelectIndex)
    {
        int iIndex = 0;

        for( ; iIndex < iLangSize ; iIndex++)
        {
            if(iIndex == iSelectIndex)
            {
                vSetHighlight(fillerTextView_Lang[iIndex],true);
            }
            else
            {
                vSetHighlight(fillerTextView_Lang[iIndex],false);
            }
        }
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v) {
            int iIndex = 0;
            int iSelectIndex = 0;

            for( ; iIndex < iLangSize ; iIndex++)
            {
                if(fillerTextView_Lang[iIndex] == v)
                {
                    vSetHighlight(fillerTextView_Lang[iIndex],true);
                    iSelectIndex = iIndex;
                }
                else
                {
                    vSetHighlight(fillerTextView_Lang[iIndex],false);
                }
            }

            vSelectLang(iSelectIndex);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vSelectLang(int iIndex)
    {
        if(mOnSelectListener != null)
        {
            mOnSelectListener.onSelect(iIndex);
        }
    }

    public void setOnSelectListener(RtxDialog_Language_Layout.OnSelectListener l) {
        mOnSelectListener = l;
    }

    public interface OnSelectListener
    {
        public abstract void onSelect(int iIndex);
    }
}
