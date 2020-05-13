package com.example.projectmanagementapp.AwsAPI;

import com.example.projectmanagementapp.data.model.User;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class AwsApiPostTest {

    @Test
    public void postOrUpdateUser() throws IOException, JSONException {
        /*List<String> grpIDS = new ArrayList();
        User user = new User(grpIDS,"login",grpIDS,"6","androidTest","lksadjlkasdlnasd");
        AwsApi.postOrUpdateUser(user);*/
        AwsApi.deleteGroup("2");
    }
}