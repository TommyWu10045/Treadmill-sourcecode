package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxTargetScoreForm extends Rtx_BaseLayout {

    final int STATUS_GOLD       =   1;
    final int STATUS_SILVER     =   2;
    final int STATUS_GOODTRY    =   3;

    private Context mContext;

    private RtxImageView        imageView_ScoreForm;
    private RtxTextView         textView_Score_1st;
    private RtxTextView         textView_Score_2st;
    private RtxTextView         textView_Score_3st;
    private RtxTextView         textView_Score_4st;
    private RtxImageView        imageView_ScoreDirect;
    private RtxTextView         textView_Achieved;

    public RtxTargetScoreForm(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

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
        {
            if(imageView_ScoreForm == null)     { imageView_ScoreForm = new RtxImageView(mContext);   }
            if(imageView_ScoreDirect == null)   { imageView_ScoreDirect = new RtxImageView(mContext);   }
            if(textView_Score_1st == null)      { textView_Score_1st = new RtxTextView(mContext);   }
            if(textView_Score_2st == null)      { textView_Score_2st = new RtxTextView(mContext);   }
            if(textView_Score_3st == null)      { textView_Score_3st = new RtxTextView(mContext);   }
            if(textView_Score_4st == null)      { textView_Score_4st = new RtxTextView(mContext);   }
            if(textView_Achieved == null)       { textView_Achieved = new RtxTextView(mContext);   }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_ScoreForm, R.drawable.target_score_form, 0, 12, 92, 355);

        {
            int iW = 100;
            int iH = 26 + 10;

            int iPosX = 87;
            int iPosY = 0;

            int iGap = 117;

            addRtxTextViewToLayout(textView_Score_1st, 34.34f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, 0 - 8        , iW, iH);
            addRtxTextViewToLayout(textView_Score_2st, 34.34f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, iGap * 1 - 8 , iW, iH);
            addRtxTextViewToLayout(textView_Score_3st, 34.34f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, iGap * 2 - 8 , iW, iH);
            addRtxTextViewToLayout(textView_Score_4st, 34.34f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX, iGap * 3 - 8, iW, iH);
        }
    }

    public void setScoreText(int iWeeks_Target, int iAchievedWeek)
    {
        textView_Score_1st.setText(Rtx_TranslateValue.sInt2String(iWeeks_Target));
        textView_Score_2st.setText(Rtx_TranslateValue.sInt2String((int)Math.ceil((double)iWeeks_Target * (double)0.601)));
        textView_Score_3st.setText(Rtx_TranslateValue.sInt2String((int)Math.ceil((double)iWeeks_Target * (double)0.201)));
        textView_Score_4st.setText(Rtx_TranslateValue.sInt2String(0));

        textView_Achieved.setText(Rtx_TranslateValue.sInt2String(iAchievedWeek) + " " + LanguageData.s_get_string(mContext,R.string.week_achieved));
    }

    public void setScore(int iScore)
    {
        int iW_Image = 22;
        int iH_Image = 17;
        int iPosX_Image = 130;
        int iPosY_Image = 0;

        int iW_Text = 140;
        int iH_Text = 109;
        int iPosX_Text = 150;
        int iPosY_Text = 0;

        iH_Text = (380 - 24) / 3;

        removeView(imageView_ScoreDirect);
        removeView(textView_Achieved);

        if(iScore == STATUS_GOLD)
        {
            iPosY_Image = 18+46;
            iPosY_Text = 18;
        }
        else if(iScore == STATUS_SILVER)
        {
            iPosY_Image = 134+46;
            iPosY_Text = 134;
        }
        else //if(iScore == STATUS_GOODTRY)
        {
            iPosY_Image = 251+46;
            iPosY_Text = 251;
        }

        addRtxImageViewToLayout(imageView_ScoreDirect, R.drawable.target_score_direct, iPosX_Image, iPosY_Image, iW_Image, iH_Image);
        addRtxTextViewToLayout(textView_Achieved, 23.32f, Common.Color.yellow_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, iPosX_Text, iPosY_Text, iW_Text, iH_Text);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
