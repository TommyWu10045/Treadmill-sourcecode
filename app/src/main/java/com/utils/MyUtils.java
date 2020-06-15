package com.utils;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyUtils {


    public static int str2int(String num) {
        try {
            return Integer.parseInt(num);


        } catch (Exception e) {
            return 0;
        }
    }

    public static long str2Long(String num) {
        try {
            return Long.parseLong(num);


        } catch (Exception e) {
            return 0;
        }
    }

    public static String str2int2(String num) {
        try {
            float vv = Float.parseFloat(num);
            return ((int) vv) + "";


        } catch (Exception e) {
            return "0";
        }
    }


    public static double str2double(String num) {

        try {
            return Double.parseDouble(num);


        } catch (Exception e) {
            return 0;
        }
    }

}
