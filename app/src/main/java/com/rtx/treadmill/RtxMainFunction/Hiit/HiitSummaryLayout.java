package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;

import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.BaseLayout.SummaryBaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.Exercisefunc;

/**
 * Created by chasechang on 3/22/17.
 */

abstract public class HiitSummaryLayout extends SummaryBaseLayout{
    private String TAG="Jerry";
    private Context mContext;
    private MainActivity mMainActivity;
    //public RtxProfile fprofile;
    public View_Hiit_CombineChart mView_Hiit_CombineChart;
    private int iMachine_mode=0; //0 : treadmill; etc : bike
    private int iYMin=20;
    private int iYMax=0;
    private UiDataStruct.HIIT_ITEM_INFO itemRealInfo;

    public HiitSummaryLayout(Context context, MainActivity mMainActivity){
        super(context, mMainActivity);
        mContext=context;
        this.mMainActivity=mMainActivity;
    }


    @Override
    protected void init_CustomerView(){
        if(itemRealInfo == null){ itemRealInfo = new UiDataStruct.HIIT_ITEM_INFO(); }
        if(mView_Hiit_CombineChart == null){ mView_Hiit_CombineChart=new View_Hiit_CombineChart(mContext,0.7f,true); }
        else{
            mView_Hiit_CombineChart.onDestroy();
            mView_Hiit_CombineChart.init();
        }
    }

    @Override
    protected void init_CustomerEvent(){

    }

    @Override
    protected void add_CustomerView(){
        v_Refresh_Time();
        add_CustomerUpdateView();
        removeView(mView_Hiit_CombineChart);
    }

    public void vSetItemInfo(UiDataStruct.HIIT_ITEM_INFO itemInfo){
        int ix, iy, iw, ih;

        //Profile Frame
        ix=-1;
        iy=140;
        iw=1180+mView_Hiit_CombineChart.iAxisX_Offset;
        ih=479;
        itemInfo.copyTo(itemRealInfo);
        mView_Hiit_CombineChart.vSetWorkoutItemInfo(itemRealInfo);
        addViewToLayout(mView_Hiit_CombineChart,ix, iy, (int)(iw * 0.7), (int)(ih * 0.8));
        v_set_type_name(LanguageData.s_get_string(mContext,R.string.hiit),itemRealInfo.sGetDisplayName());
        vUpdateRealInfo(itemRealInfo);
    }

    public void vUpdateRealInfo(UiDataStruct.HIIT_ITEM_INFO itemInfo){
        int iIndex=0;
        int iSize=0;
        int iListIndex=0;

        iSize=ExerciseData.ilist_count;
        if(itemInfo.stage_WarmUp.iTime > 0){
            itemInfo.stage_WarmUp.vSetSpeed(Exercisefunc.fGetRealSpeed(0));
            itemInfo.stage_WarmUp.vSetIncline(Exercisefunc.fGetRealIncline(0));
            iIndex++;
        }

        if(itemInfo.stage_CoolDown.iTime > 0){
            if(iSize == itemInfo.iGetTotalItemSize()){
                itemInfo.stage_CoolDown.vSetSpeed(Exercisefunc.fGetRealSpeed(iSize-1));
                itemInfo.stage_CoolDown.vSetIncline(Exercisefunc.fGetRealIncline(iSize-1));
                iSize-=1;
            }
        }
        for(; iIndex < iSize; iIndex++){
            if(iListIndex < itemInfo.list_Stage.size()){
                itemInfo.list_Stage.get(iListIndex).vSetSpeed(Exercisefunc.fGetRealSpeed(iIndex));
                itemInfo.list_Stage.get(iListIndex).vSetIncline(Exercisefunc.fGetRealIncline(iIndex));
                iListIndex++;
            }
        }
    }

    private void v_Refresh_Time() {
//        int iLoop;
//        int icount_total;
//        int icount_list;
//        int icount_end;
//        int icount_flash;
//        int iprofile_total = ExerciseData.ilist_virtual_num;
//        int iprofile_center = iprofile_total;
//        ExerciseData.DEVICE_COMMAND Uart_command = null;
//        int istep_sec = ExerciseData.iStep_time;
//        int imode = 0; //0: 0 start; 1: finish end
//
//        if (ExerciseData.list_real_setting != null) {
//
//            icount_total = ExerciseData.list_real_setting.size();
//            icount_list = ExerciseData.ilist_count;
//
//            if(icount_total <= 0 || iprofile_total <= 3)
//            {
//                return;
//            }
//
//            if (icount_total <= iprofile_total) {
//                iLoop = 0;
//                icount_end = icount_total;
//                icount_flash = icount_list;
//            }
//            else if (icount_list <= iprofile_center) {
//                iLoop = 0;
//                icount_end = iprofile_total;
//                icount_flash = icount_list;
//            }
//            else {
//                if(imode == 0)
//                {
//                    iLoop = 0;
//                    icount_end = iprofile_total;
//                    icount_flash = iprofile_center;
//                }
//                else
//                {
//                    iLoop = icount_list - iprofile_center;
//                    icount_end = iLoop + iprofile_total;
//                    icount_flash = iprofile_center;
//                }
//
//                if (icount_end > icount_total) {
//                    iLoop = icount_total - iprofile_total;
//                    icount_end = icount_total;
//                    icount_flash = icount_list - (icount_total - iprofile_total) ;
//                }
//            }
//
//            //XScale Redraw
//            fprofile.i_xprofile.vSetShift(iLoop);
//
//            ExerciseData.list_incline.clear();
//            ExerciseData.list_speed.clear();
//            for (; iLoop < icount_end; iLoop++) {
//            //避開ExerciseRunProc.java v_CheckTime_list同時存取產生的錯誤
//            try {
//                Uart_command = ExerciseData.list_real_setting.get(iLoop);
//            }
//            catch (IndexOutOfBoundsException e)
//            {
//                Uart_command = null;
//            }
//
//                if (Uart_command != null) {
//                    ExerciseData.list_incline.add(Uart_command.fincline);
//                    ExerciseData.list_speed.add(Uart_command.fspeed);
//                }
//            }
//
//            fprofile.i_profile.vSetInclineList(ExerciseData.list_incline);
//            fprofile.i_profile.vSetSpeedList(ExerciseData.list_speed);
//            fprofile.i_profile.vSetflash(icount_flash);
//
//        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    protected void add_CustomerUpdateView(){
    }
}
