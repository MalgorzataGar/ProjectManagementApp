package com.example.projectmanagementapp.AwsAPI;

import android.util.JsonReader;
import android.util.MalformedJsonException;
import android.view.textclassifier.TextLinks;

import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AwsApi {
    private static OkHttpClient getHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static User getUser(String id) throws IOException, JsonSyntaxException {
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getUserData?userID=%s", id);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            /*if (body != null) {
                int i = 0;
                while (i < body.length()) {
                    if (body.charAt(i) == '"') {
                        body = body.substring(0, i) + "\\" + body.substring(i, body.length());
                        i++;
                    }
                    i++;
                }*/
                System.out.println(body);
                Gson g = new Gson();
                User user = g.fromJson(body, User.class);
                System.out.println(user.name);
                return user;
            } else {
                return null;
            }
        }
        catch(JsonSyntaxException e){
            System.out.println(e);
            return null;
        }
        catch(IOException e){
            System.out.println(e);
            return null;
        }

    }

    public static Task getTask(String id){
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getTask?taskID=%s", id);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            if (body != null) {
               return null;
            } else {
                return null;
            }
        }
        catch(IllegalStateException e){
            System.out.println(e);
            return null;
        }
        catch(IOException e){
            System.out.println(e);
            return null;
        }
    }


}
