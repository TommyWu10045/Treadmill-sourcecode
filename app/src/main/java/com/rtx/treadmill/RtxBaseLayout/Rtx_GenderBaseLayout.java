package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public abstract class Rtx_GenderBaseLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private RtxImageView imageView_Female;
    private RtxImageView imageView_Male;

    private RtxTextView textView_Female;
    private RtxTextView textView_Male;

    private RtxFillerTextView textView_Mask_Female;
    private RtxFillerTextView textView_Mask_Male;

    public Rtx_GenderBaseLayout(Context context, MainActivity mMainActivity) {
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
    public void display()
    {
        init_View();
        init_CustomerView();    //Let user can override this.
        init_Event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.

    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {

        init_BackPrePage();
        init_Title();

        if(imageView_Female == null)        {   imageView_Female = new RtxImageView(mContext); }
        if(imageView_Male == null)          {   imageView_Male = new RtxImageView(mContext); }
        if(textView_Female == null)         {   textView_Female = new RtxTextView(mContext); }
        if(textView_Male == null)           {   textView_Male = new RtxTextView(mContext); }

    }

    private void init_Event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);

        imageView_Female.setOnClickListener(mButtonListener);
        imageView_Male.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        vSetTitleText(R.string.gender);

        addRtxImageViewToLayout(imageView_Female, R.drawable.reg_female, 218, 240, 310, 310);
        addRtxImageViewToLayout(imageView_Male, R.drawable.reg_male, 748, 240, 310, 310);

        addRtxTextViewToLayout(textView_Female, R.string.female, 28f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 218, 590, 310, 40);
        addRtxTextViewToLayout(textView_Male, R.string.male, 28f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 748, 590, 310, 40);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void vClickBack();

    abstract protected void vClickFemale();

    abstract protected void vClickMale();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == imageView_Female)  { vClickFemale(); }
            else if(v == imageView_Male)    { vClickMale(); }
        }
    }

}
