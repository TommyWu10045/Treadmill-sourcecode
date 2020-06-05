package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;

/**
 * Created by Retronix on 09/22/17.
 */

public class Rtx_HRExerciseBaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private RtxImageView      i_logo;
    private RtxFillerTextView f_page[];

    private int[] i_strlist = { R.string.profile, R.string.simple};
    int imax = i_strlist.length;

    public Rtx_HRExerciseBaseLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display() {
        init_View();
        init_CustomerView();    //Let user can override this.
        init_Event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.

        if(imageView_BackHome != null)
        {
            setViewTouchDisable(imageView_BackHome);
        }
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View() {
        int iLoop;

        init_BackHome();

        if (i_logo == null) {
            i_logo = new RtxImageView(mContext);
        }

        if (f_page == null)
        {
            f_page = new RtxFillerTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                f_page[iLoop] = new RtxFillerTextView(mContext);
            }
        }
    }

    private void init_Event()
    {
        int iLoop;

        imageView_BackHome.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax; iLoop++) {
            f_page[iLoop].setOnClickListener(mButtonListener);
        }
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        String sdata;
        int ix_shift = 200;
        float fsize;
        int iLoop;

        ix = 50;
        iy = 48;
        iw = 143;
        ih = 30;
        addRtxImageViewToLayout(i_logo, R.drawable.circle_logo, ix, iy, iw, ih);

        ix = 444;
        iy = 30;
        iw = 182;
        ih = 52;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            addRtxTextViewToLayout(f_page[iLoop], -1, fsize, Common.Color.exercise_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.exercise_word_blue);
            sdata = LanguageData.s_get_string(mContext, i_strlist[iLoop]);
            f_page[iLoop].setText(sdata.toUpperCase());
            f_page[iLoop].setMode(5);
            ix += ix_shift;
        }

        v_Choicepage(Bottom_UI_Info.iGet_PageState());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_Choicepage(int isel)
    {
        int iLoop;

        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            if(isel == iLoop)
            {
                f_page[iLoop].setMode(4);
                f_page[iLoop].setTextColor(Common.Color.exercise_word_white);
            }
            else
            {
                f_page[iLoop].setMode(5);
                f_page[iLoop].setTextColor(Common.Color.exercise_word_blue);
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //overide area
    protected void init_CustomerView()
    {
        //Let user can override this.
    }

    protected void init_CustomerEvent()
    {
        //Let user can override this.
    }

    protected void add_CustomerView()
    {
        //Let user can override this.
    }

    public void vClickHome()
    {

    }

    public void v_clickpage(int isel)
    {

    }

    //////////////////////////////////////////////////////////////////////////////

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)  { vClickHome(); }
            else {
                int iLoop ;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == f_page[iLoop])
                    {
                        v_Choicepage(iLoop);
                        v_clickpage(iLoop);
                        break;
                    }
                }
            }
        }
    }



}
