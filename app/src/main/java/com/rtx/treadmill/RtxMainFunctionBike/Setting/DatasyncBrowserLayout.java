package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.retonix.circlecloud.Cloud_20_CHK_LGI;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxWebView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by chasechang on 3/22/17.
 */

public class DatasyncBrowserLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry=" ;
    private boolean DEBUG = true;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    RtxWebView w_view;
    RtxImageView i_web_but;

    private int imode = 0;
    String skey_SUCCESS = "CIRCLE_AUTHORIZATION_SUCCESS";
    //String skey_SUCCESS = "data upload error";

    public DatasyncBrowserLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        if(w_view == null)  {
            hookWebView();
            w_view = new RtxWebView(mContext);
        }


        if(i_web_but == null)  {   i_web_but = new RtxImageView(mContext); }
    }

    private void init_event()
    {
        i_web_but.setOnClickListener(mButtonListener);
    }

    @SuppressLint("JavascriptInterface")
    private void add_View()
    {
        int ix, iy, iw, ih;

        ix = 100;
        iy = 100;
        iw = 1080;
        ih = 600;
        w_view.onResume();
        addViewToLayout(null, w_view, ix, iy, iw, ih);

//        w_view.getSettings().setJavaScriptEnabled(true);
//        w_view.getSettings().setPluginState(WebSettings.PluginState.ON);
//        w_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        w_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存，只从网络获取数据.

//        w_view.setWebViewClient(mWebViewClient);
//        w_view.setWebChromeClient(mWebChromeClient);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            w_view.addJavascriptInterface(new MyJavaScriptInterface(mContext), "HtmlViewer");
//        }


        ix = 1100;
        iy = 0;
        iw = 180;
        ih = 180;
        addRtxImage(null, i_web_but, R.drawable.quick_login_close, ix, iy, iw, ih, 0, null);
//        i_web_but.setBackgroundColor(Common.Color.gray);

        Open_Webview();
    }

    private void Open_Webview()
    {
        String shttp = "http://ws.cfamanage.com/";
        String sweb = "";
        String suser = CloudDataStruct.CloudData_20.Get_output(Cloud_20_CHK_LGI.Output.USR_SEQ);
        String sSTR_TIM = Rtx_Calendar.s_trans_DateTime_Str(0, "yyyy-MM-dd", null, null, -100, 0);
        String sEND_TIM = Rtx_Calendar.s_trans_DateTime_Str(0, "yyyy-MM-dd", null, null, 0, 0);
        String shttp_login = null;
        if(DEBUG) Log.e(TAG, "Open_Webview====imode=" + imode);

        switch (imode)
        {
            case 2:
                sweb = "googlefit/user.php?";
                shttp_login = shttp + sweb + "USR_SEQ=:" + suser + "&STR_TIM=:" + sSTR_TIM + "&END_TIM=:" + sEND_TIM;
                break;

            case 3:
                sweb = "mapmyfitness/index.php?";
                shttp_login = shttp +  sweb + "USR_SEQ=:" + suser + "&STR_TIM=:" + sEND_TIM;
                break;

            case 4:
                sweb = "runkeeper/index.php?";
                shttp_login = shttp +  sweb + "USR_SEQ=:" + suser + "&STR_TIM=:" + sEND_TIM;
                break;

            case 5:
                sweb = "fitbit/index.php?";
                shttp_login = shttp +  sweb + "USR_SEQ=:" + suser + "&STR_TIM=:" + sEND_TIM;
                break;

            case 6:
                sweb = "jawbone/index.php?";
                shttp_login = shttp +  sweb + "USR_SEQ=:" + suser + "&STR_TIM=:" + sEND_TIM;
                break;

            default:
                break;
        }

        if(DEBUG) Log.e(TAG, "shttp_login=" + shttp_login);
        if(shttp_login != null)
        {
            w_view.loadUrl(shttp_login);
        }

    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(DEBUG) Log.e(TAG, "shouldOverrideUrlLoading    request=" + request);

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(DEBUG) Log.e(TAG, "onPageFinished    url=" + url);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                if(DEBUG) Log.e(TAG, "showHTML=====" + html);
                                if(bCheck_success_keyword(skey_SUCCESS.toLowerCase(), html.toLowerCase()))
                                {
                                    vClickClose();
                                }

                            }
                        });
            }else{
                view.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        }


        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if(DEBUG) Log.e(TAG, "onLoadResource    url=" + url);

        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
            if(DEBUG) Log.e(TAG, "onReceivedLoginRequest    realm=" + realm);
            if(DEBUG) Log.e(TAG, "onReceivedLoginRequest    account=" + account);
            if(DEBUG) Log.e(TAG, "onReceivedLoginRequest    args=" + args);

        }

        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            super.onReceivedClientCertRequest(view, request);
            if(DEBUG) Log.e(TAG, "onReceivedClientCertRequest    request=" + request);

        }

    };

    WebChromeClient mWebChromeClient = new WebChromeClient() {
        //获取网站标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if(DEBUG) Log.e(TAG, "onReceivedTitle    title=" + title);

        }

        //获取加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(DEBUG) Log.e(TAG, "onProgressChanged    newProgress=" + newProgress);

        }

    };

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
            if(DEBUG) Log.e(TAG, "showHTML=====" + html);
            if(bCheck_success_keyword(skey_SUCCESS.toLowerCase(), html.toLowerCase()))
            {
                vClickClose();
            }
        }

    }



    private void hookWebView() {
        Class<?> factoryClass = null;
        try {
            factoryClass = Class.forName("android.webkit.WebViewFactory");
            Method getProviderClassMethod = null;
            Object sProviderInstance = null;

            if (Build.VERSION.SDK_INT == 23) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
                getProviderClassMethod.setAccessible(true);
                Class<?> providerClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
                Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
                Constructor<?> constructor = providerClass.getConstructor(delegateClass);
                if (constructor != null) {
                    constructor.setAccessible(true);
                    Constructor<?> constructor2 = delegateClass.getDeclaredConstructor();
                    constructor2.setAccessible(true);
                    sProviderInstance = constructor.newInstance(constructor2.newInstance());
                }
            } else if (Build.VERSION.SDK_INT == 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
                getProviderClassMethod.setAccessible(true);
                Class<?> providerClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
                Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
                Constructor<?> constructor = providerClass.getConstructor(delegateClass);
                if (constructor != null) {
                    constructor.setAccessible(true);
                    Constructor<?> constructor2 = delegateClass.getDeclaredConstructor();
                    constructor2.setAccessible(true);
                    sProviderInstance = constructor.newInstance(constructor2.newInstance());
                }
            } else if (Build.VERSION.SDK_INT == 21) {// Android 21无WebView安全限制
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
                getProviderClassMethod.setAccessible(true);
                Class<?> providerClass = (Class<?>) getProviderClassMethod.invoke(factoryClass);
                sProviderInstance = providerClass.newInstance();
            }
            if (sProviderInstance != null) {
                Field field = factoryClass.getDeclaredField("sProviderInstance");
                field.setAccessible(true);
                field.set("sProviderInstance", sProviderInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    public void vset_mode(int imode)
    {
        if(DEBUG) Log.e(TAG, "vset_mode=" + imode);
        this.imode = imode;
    }

    private boolean bCheck_success_keyword(String skey, String sresource)
    {
        boolean bret = false;

        if(sresource.contains(skey))
        {
            bret = true;
            mMainActivity.mMainProcBike.settingProc.v_set62_status();
        }

        return bret;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    private void vClickClose()
    {
        if(DEBUG) Log.e(TAG, "===vClickClose===");

        w_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);

        w_view.getSettings().setJavaScriptEnabled(false);
        w_view.getSettings().setPluginState(WebSettings.PluginState.OFF);
        w_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        w_view.onPause();

        w_view.clearCache(true);
        w_view.clearHistory();
        w_view.clearFormData();
        w_view.clearAnimation();

        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC);
    }


    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == i_web_but)  { vClickClose(); }
        }
    }
}
