package com.rtx.treadmill.RtxTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Bitmap
{
    public static Uri Bitmap2Uri(Context mContext, Bitmap bitmap)
    {
        if(bitmap == null)
        {
            Log.e("Jerry","[Bitmap2Uri] bitmap = null !!!");
            return null;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap loadBitmapFromView(View v)
    {
        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width,
                v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public static void saveBitmap(Bitmap bmp)
    {
        try {
            // 取得外部儲存裝置路徑
            String path = Environment.getExternalStorageDirectory().toString ();
            // 開啟檔案
            File file = new File( path, "Image.png");
            // 開啟檔案串流
            FileOutputStream out = new FileOutputStream(file);
            // 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
            bmp.compress ( Bitmap. CompressFormat.PNG , 90 , out);
            // 刷新並關閉檔案串流
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }
    }
}
