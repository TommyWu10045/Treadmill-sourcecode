/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.NumberScroll.NumberPicker;

public class RtxNumberWheel extends NumberPicker {
    public static float ftextsize = 87.17f ;
    private static int itextcolor = Common.Color.gray_1;

    public RtxNumberWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if(view instanceof EditText)
        {
            if(GlobalData.fNumberWheelFontSize > 0)
            {
                ftextsize = GlobalData.fNumberWheelFontSize;
            }

            ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_PX,ftextsize);
            ((EditText) view).setTextColor(itextcolor);

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Common.Font.Relay_Black);
            ((EditText) view).setTypeface(tf);
        }
    }
}
