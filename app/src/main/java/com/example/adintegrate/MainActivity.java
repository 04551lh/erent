package com.example.adintegrate;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.adintegrate.bean.Bean;
import com.example.adintegrate.bean.TestBean;
import com.example.adintegrate.network.Constant;
import com.example.adintegrate.network.MyDataCallBack;
import com.example.adintegrate.network.OkHTTPManger;
import com.example.adintegrate.utils.CommonUtil;
import com.example.adintegrate.utils.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";
    private Bean.DataBean mData;
    private WebView mWebView;


    private String mUA;
    private String mReferer;
    private Map<String, String> mHeaderParams;

    private Map<String, String> mUrlParams;

    private int mStayTime;
    private int mExecTimes;

    private OkHTTPManger mOkHTTPManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            if(getIntent().getSerializableExtra(Constant.FLAG) != null)
                mData = (Bean.DataBean)getIntent().getSerializableExtra(Constant.FLAG);
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException");
        }
        Log.i("TAG","Uid："+ CommonUtil.getUid(this));
        Log.i("TAG","Rx："+TrafficStats.getUidRxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","Tx："+TrafficStats.getUidTxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","total："+ CommonUtil.FormetFileSize(TrafficStats.getUidRxBytes(CommonUtil.getUid(this))+
                TrafficStats.getUidTxBytes(CommonUtil.getUid(this))));
        TrafficStats.getUidRxBytes(CommonUtil.getUid(this));
        TrafficStats.getUidTxBytes(CommonUtil.getUid(this));
        Log.i("TAG","Rx："+TrafficStats.getUidRxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","Tx："+TrafficStats.getUidTxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","total："+ CommonUtil.FormetFileSize(TrafficStats.getUidRxBytes(CommonUtil.getUid(this))+
                TrafficStats.getUidTxBytes(CommonUtil.getUid(this))));
        Log.i("TAG","APPName："+ CommonUtil.getAppName(this));
        Log.i("TAG","PackageName："+ CommonUtil.getPackageName(this));
        Log.i("TAG","IMEI："+ CommonUtil.getIMEI(this));
        Log.i("TAG","Name："+ CommonUtil.getLocalVersionName(this));
        Log.i("TAG","Version："+ CommonUtil.getLocalVersion(this));
        Log.i("TAG","IP："+ NetWorkUtils.getIPAddress(this));


        mWebView = new WebView(this);
        if (mWebView == null) {
            return;
        }
        initWebView();
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout);
        mOkHTTPManger = OkHTTPManger.getInstance();
        mUrlParams = new HashMap<>();
        mUrlParams.put("cid",CommonUtil.getIMEI(this));
        mUrlParams.put("ip",NetWorkUtils.getIPAddress(this));
//
        mOkHTTPManger.postAsynRequireJson("http://172.16.0.188:9220/task", mUrlParams, new MyDataCallBack() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void requestSuccess(Object result) {

            }

            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void onAfter() {

            }
        });
//        initRequestHeaderParams();
//        initCommonData();
//        CommonUtil.getTimer(this,5);

    }

    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);


        try {
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mWebView.getLayoutParams();
            Display display = getWindowManager().getDefaultDisplay();
            params.height= mData.getResolution().getH() == 0?0:display.getHeight();
            params.weight= mData.getResolution().getW()== 0?0:display.getWidth();
            mWebView.setLayoutParams(params);
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException");
        }

        mWebView.setWebViewClient(new WebViewClient(){

//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                StringBuilder stringBuilder = new StringBuilder();
//                BufferedReader bufferedReader = null;
//                if (!TextUtils.isEmpty(url)) {
//                    if (url.contains("_OS_")&&!url.contains("0101")&&!url.contains("0111")) {
//                        try {
//                            String tempId = url.substring(0, url.indexOf("&") + 1);
//                            tempId = tempId.substring(url.indexOf("=") + 1, url.indexOf("&"));
////                            mUrlParams.put(ID, tempId);
////                            url = url.substring(0,url.indexOf("?")+1);
////                            url = replaceParameters(url, mUrlParams).toString();
//                            Log.i(TAG,url);
//                            URL url1 = new URL(url);
//                            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
//                            httpURLConnection.setConnectTimeout(10 * 1000);
//                            httpURLConnection.setReadTimeout(40 * 1000);
//                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                            String line = "";
//                            while ((line = bufferedReader.readLine()) != null)
//                                stringBuilder.append(line);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            if (bufferedReader != null)
//                                try {
//                                    bufferedReader.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                        }
//                        WebResourceResponse webResourceResponse = null;
//                        webResourceResponse = new WebResourceResponse("", "", new ByteArrayInputStream(stringBuilder.toString().getBytes()));
//                        return webResourceResponse;
//                    }
//                }
//                return  super.shouldInterceptRequest(view,url);
//
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("http") ||url.startsWith("https")){
                    mWebView.loadUrl(url);
                    return true;
                }
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.i(TAG,url);
                super.onLoadResource(view, url);
            }
        });

//        mWebView.loadUrl(Constant.URL);

        setContentView(mWebView);
    }

    private void initRequestHeaderParams(){
        mHeaderParams = new HashMap<>();
        try {
            mUA = TextUtils.isEmpty(mData.getUa())?Constant.DEFAULT_UA:mData.getUa();
            mReferer = TextUtils.isEmpty(mData.getUa())?null:mData.getUa();
            mHeaderParams.put(Constant.USER_AGNRT,mUA);
            if(mReferer != null) mHeaderParams.put(Constant.REFERER,mReferer);
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException");
        }

    }


    private void initCommonData(){
        try {
            mStayTime =  mData.getStay_time() == 0?0:mData.getStay_time();
            mExecTimes = mData.getExec_times() == 0?0:mData.getExec_times();
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException");
        }

    }

    private void initReplaceRequestParams(){
        if(mData.getReplace() != null){


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG","Uid："+ CommonUtil.getUid(this));
        Log.i("TAG","Rx："+TrafficStats.getUidRxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","Tx："+TrafficStats.getUidTxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","total："+ CommonUtil.FormetFileSize(TrafficStats.getUidRxBytes(CommonUtil.getUid(this))+
                TrafficStats.getUidTxBytes(CommonUtil.getUid(this))));
        TrafficStats.getUidRxBytes(CommonUtil.getUid(this));
        TrafficStats.getUidTxBytes(CommonUtil.getUid(this));
        Log.i("TAG","Rx："+TrafficStats.getUidRxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","Tx："+TrafficStats.getUidTxBytes(CommonUtil.getUid(this)));
        Log.i("TAG","total："+ CommonUtil.FormetFileSize(TrafficStats.getUidRxBytes(CommonUtil.getUid(this))+
                TrafficStats.getUidTxBytes(CommonUtil.getUid(this))));

    }
}
