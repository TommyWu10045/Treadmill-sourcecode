package com.rtx.treadmill.RtxMainFunctionBike.Training;

import android.content.Context;

import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxMainFunctionBike.BaseLayout.ExerciseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class TrainingExerciseLayout extends ExerciseLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;


    public TrainingExerciseLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }

    @Override
    protected void vSet_CustomerView()
    {

    }

    @Override
    protected void vClickHomeCallBack()
    {
        Bottom_UI_Info.v_set_stage(0x01);
        mMainActivity.mMainProcBike.trainingProc.vMainChangePage(MainState.PROC_HOME);
    }

    @Override
    protected void vChoicePage_Set_Bottom(int isel)
    {
        switch (isel)
        {
            case 0: //profile
                Bottom_UI_Info.v_set_stage(0x01);
                break;
            case 1: //simple
                Bottom_UI_Info.v_clear_stage(0x01);
                break;
            case 2: //lap
                Bottom_UI_Info.v_set_stage(0x01);
                break;
            default:
                break;
        }
    }


}
