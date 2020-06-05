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

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.rtx.treadmill.MainActivity;

import java.io.File;

public class Share_Ig {

    String type = "image/*";
    String mediaPath = "/mnt/sdcard/Image.png";
    private MainActivity mMainActivity;

    public Share_Ig(MainActivity mMainActivity)
    {
        this.mMainActivity = mMainActivity;
    }

    public void share()
    {
        createInstagramIntent(type, mediaPath);
    }

    private void createInstagramIntent(String type, String mediaPath){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        share.setPackage("com.instagram.android");
        mMainActivity.startActivity(Intent.createChooser(share, "Share to"));
    }
}
