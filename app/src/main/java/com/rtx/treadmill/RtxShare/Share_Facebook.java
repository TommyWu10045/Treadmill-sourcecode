/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.rtx.treadmill.RtxShare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxTools.Rtx_Bitmap;

import java.io.File;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Share_Facebook {

    private MainActivity mMainActivity;

    public Share_Facebook(MainActivity mMainActivity)
    {
        this.mMainActivity = mMainActivity;

        FacebookSdk.sdkInitialize(mMainActivity.mContext);
    }

    public void share()
    {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/Image.png", options);

            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .setCaption(new String("Retronix Test!!!"))
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();

            ShareDialog shareDialog = new ShareDialog(mMainActivity);
            shareDialog.show(content);
        }
        catch (IllegalStateException e)
        {
            Log.e("Jerry",e.getMessage());
        }


    }
}
