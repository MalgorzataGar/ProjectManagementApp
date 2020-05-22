package com.example.projectmanagementapp.AwsAPI;

import android.util.Log;

import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.Team;
import com.example.projectmanagementapp.data.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kotlin.text.UStringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;



public class AwsApi {
    private static <T> T returnObject(String stringObj) {//TODO zastąpić każde wywołanie z json2obj
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        Type type = new TypeToken<T>() {
        }.getType();
        T target = gson.fromJson(stringObj, type);
        return target;
    }
    public static <T> T json2obj(String s, Class<T> clazz) {
        return new Gson().fromJson(s, clazz);
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static String userID = "1"; //TODO replace with singleton
    private static String passwordHash ="dasijioasdjijdsaijdsa"; //TODO replace with singleton


    private static OkHttpClient getHttpClient() {//TODO
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

    public static User getUser(String id) throws IOException, IllegalStateException {//TODO - check
    //public static User getUser(String id, String passwordHash) throws IOException, IllegalStateException {
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getUserData?userID=%s&passwordHash=%s",userID,passwordHash);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            Log.v("REST_USER",body);
            if (body != null) {
                User user = json2obj(body, User.class);
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
    //public static Task getTask(String id,String userID,String passwordHash) throws IOException, IllegalStateException{
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/getTask?taskID=%s&userID=%s&passwordHash=%s",id, userID, passwordHash);
            Log.v("REST", address);
            Request request = new Request.Builder().url(address).get().build();
            Response response = getHttpClient().newCall(request).execute();
            String body = response.body().string();
            if (body != null) {
               Log.v("REST_TASK","get task"+body);
               Task task = json2obj(body,Task.class);
               Log.v("REST_TASK","Created task "+task.toString());
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

    public static Team getTeam(String id) throws IOException, IllegalStateException{ //TODO
    //public static Team getTeam(String id,String userID,String passwordHash) throws IOException, IllegalStateException{
        try {
            String address = String.format("https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/GroupData?groupID=%s&userID=%s&passwordHash=%s", id,userID,passwordHash);
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

    public static void updateUser(User user) throws JSONException, IOException {
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

    //public static void postOrUpdateTask(Task task) throws JSONException, IOException { //TODO
    public static void postOrUpdateTask(Task task, boolean updateTask, String userID, String passwordHash) throws JSONException, IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/createTask";
        JsonObject json = new JsonObject();
        json.addProperty("userID",userID);
        json.addProperty("passwordHash",passwordHash);
        json.addProperty("creatorID",task.getCreatorID());
        json.addProperty("deadline",task.getDeadline());
        json.addProperty("executorsIDs",task.getExecutorsIDs().toString());
        json.addProperty("groupID",task.getGroupID());
        if(updateTask) {
            json.addProperty("taskID", task.getID());
        }
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

    //public static void postOrUpdateGroup(Team group) throws JSONException, IOException { //TODO
    public static void postOrUpdateGroup(Team group, boolean updateGroup, String userID, String passwordHash) throws JSONException, IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/createGroup";
        JsonObject json = new JsonObject();
        json.addProperty("userID",userID);
        json.addProperty("passwordHash",passwordHash);
        json.addProperty("adminID",group.getAdminID());
        json.addProperty("groupName",group.getGroupName());
        if(updateGroup) {
            json.addProperty("groupID", group.getID());
        }
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

    public static void deleteUser(User user) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteUser";
        JsonObject json = new JsonObject();
        json.addProperty("userID",user.getID());
        json.addProperty("passwordHash",user.getPasswordHash());
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

    public static void deleteTask(String taskID) throws IOException { //TODO
    //public static void deleteTask(String taskID, String userID, String passwordHash) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteTask";
        JsonObject json = new JsonObject();
        json.addProperty("taskID",taskID);
        json.addProperty("userID",userID);
        json.addProperty("passwordHash",passwordHash);
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

    public static void deleteGroup(String groupID) throws IOException { //TODO
    //public static void deleteGroup(String groupID, String userID, String passwordHash) throws IOException {
        String url = "https://qd9c42cc50.execute-api.eu-west-2.amazonaws.com/deleteGroup";
        JsonObject json = new JsonObject();
        json.addProperty("groupID",groupID);
        json.addProperty("userID",userID);
        json.addProperty("passwordHash",passwordHash);
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
