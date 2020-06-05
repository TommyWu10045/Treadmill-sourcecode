package com.rtx.treadmill.RtxMainFunction.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumKeyboardView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class InputLayout extends Rtx_BaseLayout {
    private static String TAG = "Jerry";
    private final static boolean DEBUG = false;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxNumKeyboardView mNumKeyboard;
    private RtxEditText editText_Val;
    private RtxDoubleStringView         t_data;
    private RtxTextView textView_ErrorMsg;

    private RtxImageView i_input;
//    private RtxImageView i_input_cancel;

    private int imode;

    private float fdata = 0f;
    private int i_gap_data = 20;
    private int i_color_normal = Common.Color.bd_word_green;
    private int i_color_warring = Common.Color.bd_word_pink;
    private String sinput = "";

    public InputLayout(Context context, MainActivity mMainActivity) {
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
        init_Title();
        init_BackPrePage();

        if(mNumKeyboard == null)           {mNumKeyboard = new RtxNumKeyboardView(mContext, null);}
        if(editText_Val == null)            {   editText_Val = new RtxEditText(mContext); }
        if(t_data == null)            {   t_data = new RtxDoubleStringView(mContext); }
        if(i_input == null)           {i_input = new RtxImageView(mContext);}
//        if(i_input_cancel == null)           {i_input_cancel = new RtxImageView(mContext);}
        if(textView_ErrorMsg == null)       {   textView_ErrorMsg = new RtxTextView(mContext); }

    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_input.setOnClickListener(mButtonListener);
//        i_input_cancel.setOnClickListener(mButtonListener);
    }

    private void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata;
        float fsize_unit;

        //        menu
        sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, imode);
        vSetTitleText(sdata.toUpperCase());

        //        add keyboard
        v_input_show();

        ix = 1050;
        iy = 620;
        iw = 150;
        ih = 150;
        addRtxImageViewToLayout(i_input, R.xml.comfirm_tick, ix, iy, iw, ih);

        //add cancel button--Tommy 20200603
//        ix = 800;
//        iy = 620;
//        iw = 150;
//        ih = 150;
//        addRtxImageViewToLayout(i_input_cancel, R.xml.cancel_tick, ix, iy, iw, ih);

    }

    public void v_setSelect(int isel)
    {
        imode = isel;
    }

    private void v_input_show()
    {
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata, sval;
        float fsize_unit;
        int inumber = 3;
        int igap = i_gap_data;

        addViewToLayout(mNumKeyboard, 195 - 40, 200, 300 + 40 + 40, 400);
        if(BodyManagerFunc.i_get_bodymanage_point(mContext, imode, 0) != 0) {
            mNumKeyboard.setKeyMode(R.xml.view_keyboard_mode0);
        }
        else
        {
            mNumKeyboard.setKeyMode(R.xml.view_keyboard_mode1);
        }
        mNumKeyboard.hideSystemSofeKeyboard(mContext, editText_Val);
        editText_Val.setEnabled(false);

        inumber = BodyManagerFunc.i_get_modify_numbers(mContext, imode, 0);
        mNumKeyboard.setOnKeyboardActionListener(editText_Val, inumber);

        setEditText(editText_Val);
        addViewToLayout(editText_Val, 676,279,451,208);

        ix = 480;
        iy = 300;
        iw = 760;
        ih = 185;
        fsize = 195.68f;
        fsize_unit = 62.47f;
        addRtxDoubleStringView(t_data, ix, iy, iw, ih);
        t_data.setGap(igap);
        t_data.setPaint(Common.Font.Relay_Black, i_color_normal, fsize, Common.Font.Relay_Black, i_color_normal, fsize_unit);

        sval = BodyManagerFunc.s_get_drawdata(mContext, imode);
        if (sval.compareTo("") == 0) {
            sval = "";
        }
        t_data.setText(sval, BodyManagerFunc.s_get_bodymanage_unit(mContext, imode));
        editText_Val.setText(sval);

        sdata = BodyManagerFunc.s_get_bodymanage_warring(mContext, imode);
        addRtxTextViewToLayout(textView_ErrorMsg, sdata, 33.33f, i_color_warring, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, 570, 194, 678, 50);
        textView_ErrorMsg.setVisibility(INVISIBLE);
        setNextButtonEnable(false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setEditText(final RtxEditText editText)
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged=" + charSequence.toString() + "   i=" + i + "   i1=" + i1 + "   i2=" + i2);

                setWarning(false,t_data,textView_ErrorMsg);

                Editable editable = editText.getText();
                int ipoint = BodyManagerFunc.i_get_bodymanage_point(mContext, imode, 0);
                byte[] b = editable.toString().getBytes();
                int ipoint_num = 0;
                String sinput_show = "";
                int iLoop;
                boolean bpoint = false;

                for(iLoop = 0; iLoop < b.length; iLoop++)
                {
                    if(bpoint)
                    {
                        ipoint_num++;
                    }

                    if(b[iLoop] == 46)
                    {
                        bpoint = true;
                    }
                }

                Log.e(TAG, "ipoint_num=" + ipoint_num + "   ipoint=" + ipoint);

                if(ipoint == 0)
                {
                    if(bpoint)
                    {
                        editable.delete(b.length - 1, b.length);
                    }
                }
                else {
                    if (ipoint_num > ipoint) {
                        editable.delete(b.length - 1, b.length);
                    }
                }

                sinput_show = editText.getText().toString();

                int siip ;
                if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    siip = 0;
                }
                else
                {
                    siip = 2;
                }

                fdata = BodyManagerFunc.f_get_drawdata_string(mContext, imode, sinput_show, siip);
                sinput = Rtx_TranslateValue.sFloat2String(fdata,5);
                if(fdata >= 0)
                {
                    setNextButtonEnable(true);
                }
                else
                {
                    setNextButtonEnable(false);
                }
                t_data.setText(sinput_show, BodyManagerFunc.s_get_bodymanage_unit(mContext, imode));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, "afterTextChanged=" + editable.toString());
                byte[] b = editable.toString().getBytes();
                int iLoop;

                if(b.length > 0) {
                    for (iLoop = 1; iLoop < b.length; iLoop++) {
                        if (b[iLoop - 1] == '0') {
                            if (b[iLoop] == '0' || b[iLoop] == '1' || b[iLoop] == '2'
                             || b[iLoop] == '3' || b[iLoop] == '4' || b[iLoop] == '5'
                             || b[iLoop] == '6' || b[iLoop] == '7' || b[iLoop] == '8'
                             || b[iLoop] == '9' )
                            {
                                editable.delete(0, 1);
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                Log.e(TAG, "editable=" + editable.toString() );


            }
        });
    }

    private void setNextButtonEnable(boolean bFlag)
    {
        i_input.setEnabled(bFlag);
    }

    private void setWarning(boolean bFlag , RtxDoubleStringView editText , TextView textView)
    {
        if(bFlag)
        {
            textView.setVisibility(VISIBLE);
            editText.setColor(i_color_warring, i_color_warring);
        }
        else
        {
            textView.setVisibility(INVISIBLE);
            editText.setColor(i_color_normal, i_color_normal);
        }
    }

    //fweigh is kg; fheigh is cm
    private void v_update_weight_relationship_data(float fweight)
    {
        String sdata;
        float fheigh = CloudDataStruct.CloudData_17.f_get_user_height();

        float fbmi = fweight/(fheigh*fheigh/10000);
        if(BodyManagerFunc.i_check_modify_range(mContext, Cloud_05_DB_BDY_IDX_REC.Input.fBMI, fbmi) == 0)
        {
            sdata = Rtx_TranslateValue.sFloat2String(fbmi, 5);
            CloudDataStruct.BodyIndexData_05.v_SetBodyIndex(Cloud_05_DB_BDY_IDX_REC.Input.fBMI, sdata);
        }

//        String smode = CloudDataStruct.BodyIndexData_05.sdata_in[Cloud_05_DB_BDY_IDX_REC.Input.sMCH_TYP];
//        if (smode != null && smode.toLowerCase().compareTo("inbody") == 0)
        {
            float fod;
            if(CloudDataStruct.CloudData_17.is_Male())
            {
                fod = fweight/((fheigh-80f)*0.7f);
            }
            else
            {
                fod = fweight/((fheigh-80f)*0.6f);
            }
            sdata = Rtx_TranslateValue.sFloat2String(fod, 5);
            CloudDataStruct.BodyIndexData_05.v_SetBodyIndex(Cloud_05_DB_BDY_IDX_REC.Input.fOBY_DGE, sdata);
        }

        return ;
    }

    private boolean b_update_data()
    {
        boolean bret = false;

        if(BodyManagerFunc.i_check_modify_range(mContext, imode, fdata) == 0)
        {
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetHaveChangeData(true);

            CloudDataStruct.BodyIndexData_05.v_SetBodyIndex(imode, sinput);
            if(imode == Cloud_05_DB_BDY_IDX_REC.Input.fWeight) {
                v_update_weight_relationship_data(fdata);
            }
            bret = true;
        }
        else
        {
            setWarning(true,t_data,textView_ErrorMsg);
        }

        return bret;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        int iLoop ;
        int istr_list[];

        istr_list = mMainActivity.mMainProcTreadmill.bodymanagerProc.ipage_list[0];


        for(iLoop = 0; iLoop < istr_list.length; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);
                return;
            }
        }

        istr_list = mMainActivity.mMainProcTreadmill.bodymanagerProc.ipage_list[1];
        for(iLoop = 0; iLoop < istr_list.length; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE02);
                return;
            }
        }

        mMainActivity.mMainProcTreadmill.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);

    }

    private void vClickModify()
    {
        if(b_update_data())
        {
            //upload to cloud
            mMainActivity.mMainProcTreadmill.bodymanagerProc.vSet_Upload_flag(true);
            vClickBack();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage )      { vClickBack(); }
            else if(v == i_input)      { vClickModify(); }
        }
    }
}
