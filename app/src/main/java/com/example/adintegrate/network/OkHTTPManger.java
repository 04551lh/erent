package com.example.adintegrate.network;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by dell on 2019/11/27 21:25
 * Description:
 * Emain: 1187278976@qq.com
 */
public class OkHTTPManger {
    private static OkHttpClient okHttpClient;
    private volatile static OkHTTPManger instance;//防止多个线程同时访问
    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private static String responseStrGETAsyn;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private OkHTTPManger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("TAG",message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient =  new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        cookieStore.put(url.host(), cookies);
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies = cookieStore.get(url.host());
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
////自动管理Cookie发送Request都不用管Cookie这个参数也不用去response获取新Cookie什么的了。还能通过cookieStore获取当前保存的Cookie。
//                    }
//                });
    }


    /**
     * 懒汉式加锁单例模式
     * @return
     */
    public static OkHTTPManger getInstance() {
        if (instance == null) {
            synchronized (OkHTTPManger.class) {
                if (instance == null) {
                    instance = new OkHTTPManger();
                }
            }
        }
        return instance;
    }


    /**
     * get同步请求不需要传参数
     * 通过response.body().string()获取返回的字符串
     *
     * @param url
     * @return
     */
    public String getSyncBackString(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            // 将response转化成String
            String responseStr = response.body().string();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get同步请求
     * 通过response.body().bytes()获取返回的二进制字节数组
     *
     * @param url
     * @return
     */
    public byte[] getSyncBackByteArray(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            // 将response转化成String
            byte[] responseStr = response.body().bytes();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为byte数组异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get同步请求
     * 通过response.body().byteStream()获取返回的二进制字节流
     *
     * @param url
     * @return
     */
    public InputStream getSyncBackByteStream(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            // 将response转化成String
            InputStream responseStr = response.body().byteStream();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get同步请求
     * 通过response.body().byteStream()获取返回的二进制字节流
     *
     * @param url
     * @return
     */
    public Reader getSyncBackCharReader(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            // 将response转化成String
            Reader responseStr = response.body().charStream();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为Reader异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get异步请求不传参数
     * 通过response.body().string()获取返回的字符串
     * 异步返回值不能更新UI，要开启新线程
     *
     * @param url
     * @return
     */
    public String getAsynBackStringWithoutParms(String url, final MyDataCallBack myDataCallBack) {

        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            // 请求加入调度
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
//                 Logger.e("GET异步请求为String失败"+e.toString());
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("GET异步请求为String解析异常失败" + e.toString());
                    }

                }
            });

            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("GET异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * get异步请求传参数(可以传null)
     * 通过response.body().string()获取返回的字符串
     * 异步返回值不能更新UI，要开启新线程
     *
     * @param url
     * @return
     */
    public String getAsynBackStringWithParms(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        // 请求url（baseUrl+参数）
        String doUrl = urlJoint(url, params);
        final Request request = new Request.Builder()
                .url(doUrl)
//                .header("Cookie", "自动管理更新需要携带的Cookie")
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            // 请求加入调度
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
//                 Logger.e("GET异步请求为String失败"+e.toString());
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("GET异步请求为String解析异常失败" + e.toString());
                    }

                }
            });

            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("GET异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * post异步请求map传参
     * 通过response.body().string()获取返回的字符串
     * 异步返回值不能更新UI，要开启新线程
     *
     * @param url
     * @return
     */
    public String postAsynBackString(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }

        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 在这对添加的参数进行遍历
         */
        addMapParmsToFromBody(params, builder);

        requestBody = builder.build();
        String realURL = urlJoint(url, null);
        //结果返回
        final Request request = new Request.Builder()
                .url(realURL)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            // 请求加入调度
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();//此处也可以解析为byte[],Reader,InputStream
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("POST异步请求为String解析异常失败" + e.toString());
                    }

                }
            });

            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    private void addMapParmsToFromBody(Map<String, String> params, FormBody.Builder builder) {
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            /**
             * 判断值是否是空的
             */
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            /**
             * 把key和value添加到formbody中
             */
            builder.add(key, value);
        }
    }

    /**
     * post异步请求json传参
     * 通过response.body().string()获取返回的字符串
     * 异步返回值不能更新UI，要开启新线程
     *
     * @param url
     * @return
     */
    public String postAsynRequireJson(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        // 将map转换成json,需要引入Gson包
        String mapToJson = new Gson().toJson(params);
        final String realURL = urlJoint(url, null);
        final Request request = buildJsonPostRequest(realURL, mapToJson);
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            // 请求加入调度
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str =  response.body().string().replace("\\","");
                    responseStrGETAsyn = str.substring(1,str.length()-1);//此处也可以解析为byte[],Reader,InputStream
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("POST异步请求为String解析异常失败" + e.toString());
                    }

                }
            });

            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * Json_POST请求参数
     *
     * @param url  url
     * @param json json
     * @return requestBody
     */
    private Request buildJsonPostRequest(String url, String json) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        RequestBody requestBody = RequestBody.create(JSON, json);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * String_POST请求参数
     *
     * @param url  url
     * @param json json
     * @return requestBody
     */
    private Request buildStringPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * @param url    实际URL的path
     * @param params
     * @return
     */
    private static String urlJoint(String url, Map<String, String> params) {
        StringBuilder realURL = new StringBuilder(url);
        boolean isFirst = true;
        if (params == null) {
            params = new HashMap<>();
        } else {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                if (isFirst && !url.contains("?")) {
                    isFirst = false;
                    realURL.append("?");
                } else {
                    realURL.append("&");
                }
                realURL.append(entry.getKey());
                realURL.append("=");
                if (entry.getValue() == null) {
                    realURL.append(" ");
                } else {
                    realURL.append(entry.getValue());
                }

            }
        }

        return realURL.toString();
    }
}

