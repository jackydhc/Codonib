package com.ian.codonib.network;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.ian.codonib.StepDataOuterClass;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.apache.http.conn.ssl.SSLSocketFactory;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.jackson.JacksonConverterFactory;
//import retrofit2.converter.protobuf.ProtoConverterFactory;

public class ServiceFactory {

    public static final String UPLOAD_URL = "";
    public static final String GETSTEPS_URL = "";

    public static StringBuffer agent = new StringBuffer();
    private static String deviceId;
    private static Interceptor interceptor = new Interceptor() {
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Builder builder = chain.request().newBuilder();
            String token = ServiceFactory.getToken();
            String devicesId = ServiceFactory.getXDeviceId();
            String XUniqueId = ServiceFactory.getXUniqueId();
            if (!TextUtils.isEmpty(devicesId)) {
                builder.addHeader("X-Device-Id", devicesId);
            }
            if (!TextUtils.isEmpty(token)) {
                builder.addHeader("Authorization", "Bearer " + token);
            }
            if (!TextUtils.isEmpty(XUniqueId)) {
                builder.addHeader("X-Unique-Id", XUniqueId);
            }
            String method = request.method();
            String path = request.url().encodedPath();
            long timestamp = System.currentTimeMillis();
            String nonce = "CGwMjLIac" + timestamp + "MBESwVRPSB";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(method).append(path).append(timestamp).append(nonce);
            if (!TextUtils.isEmpty(token)) {
                stringBuffer.append(token);
            }
            if (path.contains("user_data/upload_v2")) {
                builder.addHeader("Content-Type", "application/x-protobuf");
            } else if (!(request.body() == null || request.body().contentLength() == 0)) {
                builder.addHeader("Content-Type", "application/json; charset=UTF-8");
            }
            builder.addHeader("accept-encoding", "gzip, deflate");
            builder.addHeader("Connection", "keep-alive");
            builder.addHeader("X-Request-Id", System.currentTimeMillis() + "" + XUniqueId);
            builder.addHeader("X-Timestamp", timestamp + "");
            builder.addHeader("X-App-Version", "1.9.1");
            builder.addHeader("X-Nonce", nonce);
            builder.addHeader("X-Access-Key", "iheucXaZugq2x3mevgEqHXnd");
            builder.addHeader("User-Agent", ServiceFactory.agent.toString());
            builder.addHeader("accept", "application/json");
            String sign = ServiceFactory.getSignStr(stringBuffer.toString());
            if (sign != null) {
                builder.addHeader("X-Signature", sign);
            }
            return chain.proceed(builder.build());
        }
    };
    private static Retrofit retrofit = null;
    private static String user_token;
    private static String x_uniqueid;
    private static UserMode userMode;
    private static CodonApi codonApi;

    public static void init() {

        agent = new StringBuffer();
        agent.append("ClubCodoon");
        agent.append("(").append("1.9.1").append(" ").append(115);
        agent.append(";");
        agent.append("android ").append(VERSION.RELEASE).append(";").append(Build.MANUFACTURER).append(" ").append(Build.MODEL).append(")");
        retrofit = new Retrofit.Builder().baseUrl("https://club-api.codoon.com/v1/").
                client(genericClient())
//                .addConverterFactory(ProtoConverterFactory.create())
//                .addConverterFactory(JacksonConverterFactory.create(BaseModel.mMapper))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        codonApi = retrofit.create(CodonApi.class);


    }


    private static String getToken() {
        if (userMode != null) {
            user_token = userMode.token;
        }
        return user_token;
    }

    private static String getXDeviceId() {
        if (userMode != null) {
            deviceId = userMode.deviceID;
        }
        return deviceId;
    }

    private static String getXUniqueId() {
        if (userMode != null) {
            x_uniqueid = userMode.uuid;
        }
        return x_uniqueid;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.addInterceptor(interceptor);
        httpBuilder.connectTimeout(1, TimeUnit.MINUTES);
        httpBuilder.readTimeout(1, TimeUnit.MINUTES);
        httpBuilder.writeTimeout(1, TimeUnit.MINUTES);
        httpBuilder.retryOnConnectionFailure(true);
        try {
            setSSLSocketFactory(httpBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpBuilder.build();
    }

    public static String getSignStr(String string) {
        try {
            String stringToSign = string;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec("DCBGtjU9zs2eHvTMy5MyHIwl".getBytes(), "HmacSHA256"));
            return Base64.encodeToString(sha256_HMAC.doFinal(stringToSign.getBytes()), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSSLSocketFactory(OkHttpClient.Builder builder) throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                if (chain == null) {
                    throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
                } else if (chain.length <= 0) {
                    throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
                } else {
                    try {
                        chain[0].checkValidity();
                        if (authType == null || !authType.toUpperCase().contains("RSA")) {
                            throw new CertificateException("checkServerTrusted: AuthType is not RSA:" + authType);
                        }
                    } catch (Exception e) {
                        throw new CertificateException("Certificate not valid or trusted.");
                    }
                }
            }
        }};
        SSLContext sslcontext = SSLContext.getInstance(SSLSocketFactory.TLS);
        sslcontext.init(null, trustAllCerts, null);
        builder.sslSocketFactory(sslcontext.getSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public static void setUserMode(UserMode mode){
        userMode = mode;
        getXUniqueId();
        getToken();
        getXDeviceId();
    }

    public static void doPost(int steps,int time){

        codonApi.postSteps(UPLOAD_URL,generateData(steps,time)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()){
                    String body = response.body();
                    Log.d("codonservice","dopost-reslut:"+body);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private static StepDataOuterClass.StepData generateData(int steps,int time{

    }
}
