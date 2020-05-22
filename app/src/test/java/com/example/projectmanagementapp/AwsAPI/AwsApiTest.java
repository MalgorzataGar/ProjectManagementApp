package com.example.projectmanagementapp.AwsAPI;


import com.example.projectmanagementapp.data.model.User;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AwsApiTest {
    @Test
    public void getUserTest() throws IOException {
        User user = AwsApi.getUser("3");
        //assertEquals("test",user.equals(new User(new List<String>("3"),"test@test.test","{\"2\"}","3","TEST","dasijioasdjijdsaijdsa")),"Done");
        //List<String> groupIDs, String login, List<String> taskIDs, String ID, String name, String passwordHash
    }
}
