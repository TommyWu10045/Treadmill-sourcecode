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
import android.util.Log;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

public class Share_Weibo {

    private MainActivity mMainActivity;

    private WbShareHandler wbShareHandler;

    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public Share_Weibo(MainActivity mMainActivity)
    {
        boolean bReg = false;

        this.mMainActivity = mMainActivity;

        WbSdk.install(mMainActivity, new AuthInfo(mMainActivity.mContext, "1885400172", REDIRECT_URL, SCOPE));
        wbShareHandler = new WbShareHandler(mMainActivity);
        bReg = wbShareHandler.registerApp();
        Log.e("Jerry","bReg = " + bReg);
    }

    public void share()
    {
        sendMultiMessage(true,false);
    }

    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        weiboMessage.mediaObject = getWebpageObj();
        wbShareHandler.shareMessage(weiboMessage, true);
    }
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "Jerry分享的文字";
        textObject.title = "Jerry分享的title";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/Image.png", options);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "#医生工作站#最新前沿相关文章推荐阅读";
        mediaObject.description = "最新前沿相关文章推荐阅读";
        Bitmap bitmap = BitmapFactory.decodeResource(mMainActivity.getResources(), R.drawable.circle_logo);
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = "shareUrl";
        mediaObject.defaultText = "Webpage";
        return mediaObject;
    }
}
