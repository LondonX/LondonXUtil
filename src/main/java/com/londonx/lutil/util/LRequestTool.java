package com.londonx.lutil.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.londonx.lutil.entity.LResponse;
import com.londonx.lutil.impl.DefaultGlobalResponseListener;
import com.londonx.lutil.impl.GlobalResponseListener;
import com.londonx.lutil.impl.OnResponseListener;
import com.londonx.lutil.struct.DefaultParams;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by london on 15/6/2.
 * LRequestTool
 * Update in 2015-08-04 21:50:33 DELETE not working
 * Update in 2015-08-06 11:17:52 onPostExecute not calling in pre-JELLYBEAN
 * Update in 2015-08-13 12:43:01 twice onResponse called in pre-JELLYBEAN
 * Update in 2015-08-19 19:58:15 add URLEncoder in GET
 * Update in 2015-08-21 12:40:53 'com.loopj.android:android-async-http:1.4.8' for large file upload
 * Update in 2015-08-21 19:13:19 avoid downloading the exist file
 * Update in 2015-11-03 19:12:51 add onUploadListener
 * Update in 2016-03-11 18:14:42 add doPostRaw for json posting
 * Update in 2016-04-07 10:55:18 fully refactor by using OkHttp
 */
public class LRequestTool implements Handler.Callback {
    public static final MediaType FORM_URLENCODED
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final MediaType IMAGE_PNG
            = MediaType.parse("image/png; charset=utf-8");
    public static final MediaType IMAGE_JPG
            = MediaType.parse("image/jpg; charset=utf-8");
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final int whatNoResponse = 0;
    private static final int whatStringResponse = 1;
    private static final int whatFileResponse = 2;
    private static final String keyFilePath = "filePath";
    private static final OkHttpClient CLIENT = new OkHttpClient();
    @NonNull
    private static GlobalResponseListener globalResponseListener =
            new DefaultGlobalResponseListener();

    @NonNull
    private static MediaType selectedMediaType = FORM_URLENCODED;
    @Nullable
    private OnResponseListener responseListener;
    private Handler handler = new Handler(this);

    public static void setMediaType(@NonNull MediaType mediaType) {
        LRequestTool.selectedMediaType = mediaType;
    }

    public static void setGlobalResponseListener(@NonNull
                                                 GlobalResponseListener globalResponseListener) {
        LRequestTool.globalResponseListener = globalResponseListener;
    }

    public LRequestTool(@Nullable OnResponseListener responseListener) {
        this.responseListener = responseListener;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalThreadStateException(
                    "LRequestTool can only new in the main thread (UI thread)");
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        LResponse lResponse = new LResponse();
        lResponse.requestCode = msg.arg1;

        switch (msg.what) {
            case whatNoResponse:
                lResponse.responseCode = 0;
                lResponse.body = msg.obj.toString();
                break;
            case whatStringResponse:
                lResponse.responseCode = msg.arg2;
                lResponse.body = msg.obj.toString();
                break;
            case whatFileResponse:
                break;
        }
        globalResponseListener.onGlobalResponse(lResponse);
        if (responseListener == null) {
            return false;
        }
        responseListener.onResponse(lResponse);
        return false;
    }

    public void doGet(@NonNull final String url,
                      @Nullable final DefaultParams params,
                      final int requestCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url + hashMapToGetParams(params))
                        .build();
                try {
                    Response response = CLIENT.newCall(request).execute();
                    if (responseListener == null) {
                        return;
                    }
                    Message message = handler.obtainMessage();
                    message.what = whatStringResponse;
                    message.arg1 = requestCode;
                    message.arg2 = response.code();
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (responseListener == null) {
                        return;
                    }
                    Message message = handler.obtainMessage();
                    message.what = whatNoResponse;
                    message.arg1 = requestCode;
                    message.arg2 = 0;
                    message.obj = e.getMessage();
                }
            }
        }).start();
    }

    public void doPost(@NonNull final String url,
                       @Nullable final DefaultParams params,
                       final int requestCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .post(hashMapToRequestBody(params))
                        .build();
                doRequestSync(request, requestCode);
            }
        }).start();
    }

    private void doRequestSync(Request request, int requestCode) {
        try {
            Response response = CLIENT.newCall(request).execute();
            if (responseListener == null) {
                return;
            }
            Message message = handler.obtainMessage();
            message.what = whatStringResponse;
            message.arg1 = requestCode;
            message.arg2 = response.code();
            message.obj = response.body().string();
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            if (responseListener == null) {
                return;
            }
            Message message = handler.obtainMessage();
            message.what = whatNoResponse;
            message.arg1 = requestCode;
            message.arg2 = 0;
            message.obj = e.getMessage();
        }
    }

    private RequestBody hashMapToRequestBody(DefaultParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String k : params.keySet()) {
            Object v = params.get(k);
            if (v instanceof File) {
                File f = ((File) v);
                String fileName = f.getName();
                builder.addFormDataPart(k, fileName,
                        RequestBody.create(FileUtil.getMediaType(f.getName()), f));
            } else {
                builder.addFormDataPart(k, v.toString());
            }
        }
        return builder.build();
    }

    private String hashMapToGetParams(DefaultParams params) {
        if (params == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String k : params.keySet()) {
            Object v = params.get(k);
            if (v instanceof File) {
                throw new IllegalStateException("All params must NOT be type of "
                        + File.class.getName());
            }
            if (sb.length() == 0) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(k);
            sb.append("=");
            sb.append(v.toString());
        }
        return sb.toString();
    }
}
