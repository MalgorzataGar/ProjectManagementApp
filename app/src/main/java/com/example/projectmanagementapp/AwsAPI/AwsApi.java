package com.example.projectmanagementapp.AwsAPI;

import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.Team;
import com.example.projectmanagementapp.data.model.User;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;



public class AwsApi {
    private static <T> T returnObject(String stringObj){
        return null;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

    public static User getUser(String id) throws IOException, IllegalStateException {
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getUserData?userID=%s", id);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            System.out.println(body);
            if (body != null) {
                User user = (User) returnObject(body);
                return user;
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

    public static Task getTask(String id) throws IOException, IllegalStateException{
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getTask?taskID=%s", id);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            if (body != null) {
               Task task = (Task) returnObject(body);
               return task;
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

    public static Team getTeam(String id) throws IOException, IllegalStateException{
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/GroupData?groupID=%s", id);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            if (body != null) {
                Team group = (Team) returnObject(body);
                return group;
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

    public static void postOrUpdateUser(User user) throws JSONException, IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/createUser";
        JsonObject json = new JsonObject();
        json.addProperty("userID",user.getID());
        json.addProperty("login",user.getLogin());
        json.addProperty("name",user.getName());
        json.addProperty("passwordHash",user.getPasswordHash());
        json.addProperty("taskIDs",user.getTaskIDs().toString());
        json.addProperty("groupIDs",user.getGroupIDs().toString());
        System.out.println(json.toString());
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }
    }

    public static void postOrUpdateTask(Task task) throws JSONException, IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/createTask";
        JsonObject json = new JsonObject();
        json.addProperty("creatorID",task.getCreatorID());
        json.addProperty("deadline",task.getDeadline());
        json.addProperty("executorsIDs",task.getExecutorsIDs().toString());
        json.addProperty("groupID",task.getGroupID());
        json.addProperty("taskID",task.getID());
        json.addProperty("priority",task.getPriority());
        json.addProperty("state",task.getState());
        json.addProperty("taskDescription",task.getTaskDescription());
        json.addProperty("taskName",task.getTaskName());
        System.out.println(json.toString());
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }
    }

    public static void postOrUpdateGroup(Team group) throws JSONException, IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/createGroup";
        JsonObject json = new JsonObject();
        json.addProperty("adminID",group.getAdminID());
        json.addProperty("groupName",group.getGroupName());
        json.addProperty("groupID",group.getID());
        json.addProperty("taskIDs",group.getTaskIDs().toString());
        json.addProperty("usersIDsROles",group.getUsersIDsROles().toString());

        System.out.println(json.toString());
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }
    }

    public static void deleteUser(String id) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteUser";
        JsonObject json = new JsonObject();
        json.addProperty("userID",id);
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .delete(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }

    }

    public static void deleteTask(String id) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteTask";
        JsonObject json = new JsonObject();
        json.addProperty("taskID",id);
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .delete(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }

    }

    public static void deleteGroup(String id) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteGroup";
        JsonObject json = new JsonObject();
        json.addProperty("groupID",id);
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .delete(body)
                .build();
        Response res = getHttpClient().newCall(request).execute();
        if (res!=null){
            System.out.println(res.body().string());
        }

    }
}
