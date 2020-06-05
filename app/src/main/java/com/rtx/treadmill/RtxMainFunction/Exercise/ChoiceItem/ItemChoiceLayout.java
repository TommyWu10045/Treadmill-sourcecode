package com.rtx.treadmill.RtxMainFunction.Exercise.ChoiceItem;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import com.rtx.treadmill.GlobalData.Choice_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.ExerciseData.SimpleObj;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * TODO: document your custom view class.
 */
public class ItemChoiceLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;
    private ButtonListener mButtonListener;

    private int COLOR_BACKGROUND = Common.Color.exercise_choice_back;
    private int COLOR_BOX = Common.Color.exercise_choice_box;

    private RtxView v_back;
    private RtxView v_box;
    private RtxTextView[] t_list;

    private int imax = 8;

    private SimpleObj Obj ;
    private String[] slist ;
    private int ilist_count = 0;

    private Paint mPaint;
    private float fTextsize;
    private int iTextColor;
    private Typeface m_tf;

    public ItemChoiceLayout(Context context, MainActivity mMainActivity){
        super(context);

        this.mMainActivity = mMainActivity;

        mContext = context;

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
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View() {
        int iLoop;

        if(v_back == null) { v_back = new RtxView(mContext);}
        if(v_box == null) { v_box = new RtxView(mContext);}
        if(t_list == null) {
            t_list = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                t_list[iLoop] = new RtxTextView(mContext);
            }
        }

        if(slist == null) { slist = new String[imax]; }

        if(mPaint == null) {
            mPaint = new Paint();
            setTextSize(20f);
            setText_tf(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Black));
        }

    }

    private void init_Event()
    {
        int iLoop;

        v_back.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax; iLoop++)
        {
            t_list[iLoop].setOnClickListener(mButtonListener);
        }

    }

    private void add_View()
    {
        float fsize;
        int ixcenter;
        int iy_bot;

        Obj = Choice_UI_Info.S_get_Choice_Obj();
        if(Obj.i_item_list.length <= 0 || Obj.i_item_list.length > imax)
        {
            return ;
        }

        glist_String(Obj.i_item_list);

        if(slist == null)
        {
            return ;
        }

        fsize = Choice_UI_Info.f_get_Choice_size();
        ixcenter = Choice_UI_Info.f_get_Choice_xceter();
        iy_bot = Choice_UI_Info.f_get_Choice_ybot();

        setTextSize(fsize);

        v_setText_list(slist);

        v_setText_position(slist, ixcenter, iy_bot);

    }

    ////////////////////////////////////////////////////////////
    public void setmTextColor(int icolor) {
        iTextColor = icolor;
        mPaint.setColor(iTextColor);
    }

    public void setTextSize(float fsize) {
        fTextsize = fsize;
        mPaint.setTextSize(fTextsize);
    }

    public void setText_tf(Typeface Text_tf) {
        m_tf = Text_tf;
        mPaint.setTypeface(m_tf);
    }

    public void setText_tf(String Text_tf) {
        m_tf = Typeface.createFromAsset(mContext.getAssets(), Text_tf);
        mPaint.setTypeface(m_tf);
    }

    ////////////////////////////////////////////////////////////
    private String[] glist_String(int[] ilist)
    {
        int iLoop;
        String sdata;

        ilist_count = ilist.length + 1;
        for(iLoop = 0; iLoop < ilist_count; iLoop++)
        {
            if(iLoop == 0)
            {
                sdata ="X";
            }
            else
            {
                sdata = LanguageData.s_get_string(mContext, ilist[iLoop - 1]).toUpperCase();
            }
            slist[iLoop] = sdata;
        }

        return slist;
    }

    private void v_setText_list(String[] strlist)
    {
        int iLoop;

        for(iLoop = 0; iLoop < ilist_count; iLoop++)
        {
            t_list[iLoop].setText(strlist[iLoop]);
        }

        return ;
    }

    private void v_setText_position(String[] strlist, int ixcenter, int iyshift)
    {
        int iLoop;
        int[] i_max_wh;
        int ix, iy, iw, ih;

        i_max_wh = i_get_max_wh(strlist);
        iw = (int)(i_max_wh[0] * 1.5f);
        ih = (int)(i_max_wh[1] * 4f);

        ix = ixcenter - iw/2;
        iy = 800 - iyshift - ih * (ilist_count);


        addRtxViewToLayout(v_back, 0, 0, 1280, 800, COLOR_BACKGROUND);
        addRtxViewToLayout(v_box, ix, iy, iw, ih*ilist_count, COLOR_BOX);

        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            t_list[iLoop].setVisibility(INVISIBLE);
        }

        for(iLoop = 0; iLoop < ilist_count; iLoop++)
        {
            iy = 800 - iyshift - ih * (ilist_count - iLoop);
            addRtxTextView(null, t_list[iLoop], strlist[iLoop], fTextsize, Common.Color.exercise_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            t_list[iLoop].setVisibility(VISIBLE);
        }


        return ;
    }

    private Rect get_Rec(String str)
    {
        Rect bounds = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), bounds);

        return bounds;
    }

    private int[] i_get_max_wh(String[] str)
    {
        int[] text = {0, 0};
        int iLoop;

        for(iLoop = 0; iLoop < ilist_count; iLoop++) {
            Rect bounds = get_Rec(str[iLoop]);
            if(bounds.width() > text[0])
            {
                text[0] =  bounds.width();
            }

            if(bounds.height() > text[1])
            {
                text[1] =  bounds.height();
            }
        }

        return text;
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

    ////////////////////////////////////////////////////////
    private void vCloseView()
    {
        Choice_UI_Info.clear();
    }

    private void vChoceitem(int isel)
    {
        if(isel != 0) {
            Obj.iChoice = isel - 1;
        }

        vCloseView();
    }

    class ButtonListener implements ImageButton.OnClickListener
    {
        @Override
        public void onClick(View v) {
//            if(v == v_back)
//            {
//                vCloseView();
//            }
//            else
            {
                for (int iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == t_list[iLoop])
                    {
                        vChoceitem(iLoop);
                    }
                }
            }

        }
    }


}
