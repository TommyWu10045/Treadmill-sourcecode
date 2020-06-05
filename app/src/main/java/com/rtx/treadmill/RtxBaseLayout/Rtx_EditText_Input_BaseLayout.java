package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;

/**
 *
 ************************************************************
 * Created by JerryLin on 10/5/17.
 ************************************************************
 * User Must to Override Function:
 *
 * public void vClickButton_Back()
 * public void vClickButton_Confirm()
 ************************************************************
 * User Could Use Function
 *
 *
 * protected void vSetInput(String sInput)
 * protected String sGetInput()
 ************************************************************
 * User Could Override Function:
 *
 * protected void init_CustomerView();
 * protected void init_CustomerEvent();
 * protected void add_CustomerView();
 * protected void SetInputRule(CharSequence charSequence, int i, int i1, int i2)
 * protected void onCustomerClick(View v)
 *************************************************************
 *
 */

public class Rtx_EditText_Input_BaseLayout extends Rtx_BaseLayout {
    private Context mContext;
    protected ButtonListener mButtonListener;
    protected RtxEditText editText_Input;
    protected RtxImageView imageView_Confirm;

    public Rtx_EditText_Input_BaseLayout(Context context) {
        super(context);
        mContext=context;
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setBackgroundColor(Common.Color.background);
    }

    @Override
    public void init(){
        if(mButtonListener == null){ mButtonListener=new ButtonListener(); }
    }

    @Override
    public void display(){
        init_View();
        init_CustomerView(); //Let user can override this.
        init_Event();
        init_CustomerEvent(); //Let user can override this.
        add_View();
        add_CustomerView(); //Let user can override this.
        setEditViewRule();
        editText_Input.requestFocus();
        Rtx_Keyboard.openSoftKeybord(this,mContext);
        if(editText_Input.getText().length() > 0){ setConfirmButtonEnable(true); }
        else{ setConfirmButtonEnable(false); }
    }

    public void onDestroy(){
        removeAllViews();
        System.gc();
    }

    private void init_View(){
        {
            init_Title();
            init_Background();
            init_BackPrePage();
        }
        {
            if(editText_Input == null){ editText_Input = new RtxEditText(mContext); }
            if(imageView_Confirm == null){ imageView_Confirm = new RtxImageView(mContext); }
        }
    }

    private void init_Event(){
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Confirm.setOnClickListener(mButtonListener);
        view_Background.setOnClickListener(mButtonListener);
    }

    private void add_View(){
        addRtxEditViewToLayout(editText_Input, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,214,218,852,107);
        editText_Input.setSingleLine(true);
        addRtxImageViewToLayout(imageView_Confirm, R.drawable.next_big_button_enable, 1088, 629, 134, 134);
    }

    private void setEditViewRule(){
        editText_Input.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SetInputRule(charSequence,i,i1,i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editText_Input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){ vCloseKeyboard(); }
                return false;
            }
        });

    }

    protected void SetInputRule(CharSequence charSequence, int i, int i1, int i2){
        String str = editText_Input.getText().toString();
        if(str.length() > 0){ setConfirmButtonEnable(true); }
        else{ setConfirmButtonEnable(false); }
    }

    protected void setConfirmButtonEnable(boolean bFlag){
        int resId=0;

        if(bFlag){ resId=R.drawable.next_big_button_enable; }
        else{ resId=R.drawable.next_big_button_disable; }
        imageView_Confirm.setImageResource(resId);
        imageView_Confirm.setEnabled(bFlag);
    }

    protected void vSetInput(String sInput){
        if(editText_Input != null){
            if(sInput == null){
                editText_Input.setText("");
            }else{
                editText_Input.setText(sInput);
                vSetSelectionToEnd();
            }
        }
    }

    protected String sGetInput()
    {
        return editText_Input.getText().toString();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void init_CustomerView(){
        //Let user can override this.
    }

    protected void init_CustomerEvent(){
        //Let user can override this.
    }

    protected void add_CustomerView(){
        //Let user can override this.
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vClickButton_Back(){

    }

    protected void vClickButton_Confirm(){

    }

    protected void onCustomerClick(View v){

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vCloseKeyboard(){
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        editText_Input.layout(214,346,214+852,346+107);
    }

    protected void vSetSelectionToEnd(){
        editText_Input.setSelection(editText_Input.getText().length());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v){
            if(v == imageView_BackPrePage){ vClickButton_Back(); }
            else if(v == imageView_Confirm){ vClickButton_Confirm(); }
            else if(v == view_Background){ vCloseKeyboard(); }
            else{ onCustomerClick(v); }
        }
    }
}
