package com.lsh.packagelibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/4/26
 */

public abstract class TempActivity extends AppCompatActivity {
    private CordovaWebView cordWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        cordWebView = (CordovaWebView) findViewById(R.id.cordovaview);
        initCachePath();
        initPre();
        checkOpen();
        initCordWebView();
    }

    private void checkOpen() {
        OkHttpUtils
                .post()
                .url(getUrl())
                .addParams("order_id", getAppId() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        startJump();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResultBean result = new Gson().fromJson(response, ResultBean.class);
                            if (result.isJump()) {
                                loadUrl(result.getData());
                            } else {
                                startJump();
                            }

                        } catch (Exception e) {
                            startJump();
                        }
                    }
                });
    }

    private void startJump() {
        startActivity(new Intent(TempActivity.this, getTargetActivity()));
        finish();
    }

    private void initPre() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public abstract Class<Activity> getTargetActivity();

    public abstract int getAppId();

    public abstract String getUrl();


    //初始化cordovaWebView
    public void initCordWebView() {
        cordWebView.setWebViewClient(new CordovaWebViewClient(new CordovaContext(this), cordWebView) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("log", "WebViewActivity onPageFinished...");
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                super.onPageFinished(view, url);
            }


            //...WebView报错时
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                hideWaitingPage();
            }

            // 重写 WebViewClient  的  shouldInterceptRequest （）
            // API 21 以下用shouldInterceptRequest(WebView view, String url)
            // API 21 以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
            // 下面会详细说明
            // API 21 以下用shouldInterceptRequest(WebView view, String url)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.e("log", "WebViewActivity WebResourceResponse 21以下");
//                WebResourceResponse response = mFiles.getWebResourceResponse(url, cachePath, filePath);
//                if (response != null) return response;
                //Log.e("log", "未使用缓存>>>");
                return super.shouldInterceptRequest(view, url);
            }

            // API 21 以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.e("log", "WebViewActivity WebResourceResponse 21以上");
//                String url = request.getUrl().toString();
//                WebResourceResponse response = mFiles.getWebResourceResponse(url, cachePath, filePath);
//                if (response != null) return response;
                //Log.e("log", "未使用缓存>>>");
                return super.shouldInterceptRequest(view, request);
            }
        });

        //获取缓存文件夹
        //String cachepath = getCacheDirectory(this, "").getAbsolutePath();
        // 设置 缓存模式
        cordWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置缓存路径
        cordWebView.getSettings().setAppCachePath(cachePath);
        // 开启 DOM storage API 功能
        cordWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        cordWebView.getSettings().setDatabaseEnabled(true);
        //开启缓存
        cordWebView.getSettings().setAppCacheEnabled(true);

        //cordWebView.getSettings().setDatabasePath(cachepath);
        //设置允许访问文件
        cordWebView.getSettings().setAllowFileAccess(true);
        //支持自动加载图片
        cordWebView.getSettings().setLoadsImagesAutomatically(true);
        //重新设置WebView的UserAgent
        String ua = cordWebView.getSettings().getUserAgentString();
        cordWebView.getSettings().setUserAgentString(ua + ";TAOBAODB_ANDROID_APP");

        cordWebView.getSettings().setLoadWithOverviewMode(true);
        cordWebView.getSettings().setUseWideViewPort(true);

        //注册监听WebView页面的js方法
//        cordWebView.addJavascriptInterface(new JsToJavaAPI(), "androidAPI");
//        boolean b = initPageUrl(href, WebViewActivity.this);
//        String ck = (String) SharedPreferencesUtils.getParam(getActivity(), "cookie", "");
//        Log.w("ckkkkkk", ck);
//        if (!TextUtils.isEmpty(ck)) {
//            synchronousWebCookies(this, href, ck);
//        }
//        loadUrl(href);

    }

    public void loadUrl(String url) {
        Log.e("log", "WebViewActivity loadUrl:" + url);
        cordWebView.loadUrl(url);
    }

    private String cachePath;
    private String filePath;

    //初始化缓存文件夹
    public void initCachePath() {
        cachePath = FileUtils.getCacheDirectory(this, "").getAbsolutePath();
        filePath = FileUtils.getCacheDirectory(this, "images").getAbsolutePath();
    }

    private class CordovaContext extends ContextWrapper implements CordovaInterface {

        Activity activity;
        protected final ExecutorService threadPool = Executors.newCachedThreadPool();

        public CordovaContext(Activity activity) {
            super(activity.getBaseContext());
            this.activity = activity;
        }

        public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
            //activity.startActivityForResult(command, intent, requestCode);
        }

        public void setActivityResultCallback(CordovaPlugin plugin) {
            //activity.setActivityResultCallback(plugin);
        }

        public Activity getActivity() {
            return activity;
        }

        public Object onMessage(String id, Object data) {
            return null;
        }

        public ExecutorService getThreadPool() {
            return threadPool;
        }
    }
}
