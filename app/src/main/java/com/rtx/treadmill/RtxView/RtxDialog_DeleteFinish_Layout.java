package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_DeleteFinish_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxImageView         imageView_DeleteIcon;
    public RtxTextView          textView_Delete;

    public RtxDialog_DeleteFinish_Layout(Context context) {
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
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
            if(imageView_DeleteIcon == null)        { imageView_DeleteIcon = new RtxImageView(mContext);   }
            if(textView_Delete == null)             { textView_Delete = new RtxTextView(mContext);   }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,487);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxImageViewToLayout(imageView_DeleteIcon, R.drawable.delete_finish_icon_dialog, -1, 164, 75, 88);
        addRtxTextViewToLayout(textView_Delete, R.string.deleted, 28f, Common.Color.red_1, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 296 - 50, 1280, 21 + 100);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
