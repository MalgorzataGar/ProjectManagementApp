package com.example.projectmanagementapp.AwsAPI;


import com.example.projectmanagementapp.data.model.User;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class AwsApiTest {
    @Test
    public void getUserTest() throws IOException {
        User user = AwsApi.getUser("3");
        /*System.out.println(user.getName());*/
        assertEquals("test","kkdsk","Done");

    }


}
