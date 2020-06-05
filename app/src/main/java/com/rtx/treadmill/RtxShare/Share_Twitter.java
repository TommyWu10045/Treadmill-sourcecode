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

import android.net.Uri;
import android.util.Log;

import com.rtx.treadmill.MainActivity;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;

public class Share_Twitter {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "qZTYLC37Zgb545Rgbc5sNxhDf";
    private static final String TWITTER_SECRET = "akyMpg2oS1etO3NsfRo2iYe9j7nJzVrCF1V2SyZgOqMZkFTgOS";

    private MainActivity mMainActivity;

    public Share_Twitter(MainActivity mMainActivity)
    {
        this.mMainActivity = mMainActivity;

        TwitterConfig config = new TwitterConfig.Builder(mMainActivity)
            .logger(new DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
            .debug(true)
            .build();
            Twitter.initialize(config);
    }

    public void share()
    {
        File f = new File("/mnt/sdcard/Image.png");

        if(f == null)
        {
            return;
        }

        Uri imageUri = Uri.fromFile(f);

        TweetComposer.Builder builder = new TweetComposer.Builder(mMainActivity.mContext);
        builder.text("Jerry Share From Rtx App");
        builder.image(imageUri);
        builder.show();
    }
}
