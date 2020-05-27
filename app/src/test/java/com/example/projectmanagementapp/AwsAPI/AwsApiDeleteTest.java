package com.example.projectmanagementapp.AwsAPI;

import com.example.projectmanagementapp.data.model.User;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AwsApiDeleteTest {

    @Test
    public void deleteUser() throws IOException {
        List<String> exIDs = new ArrayList();
        exIDs.add("1");
        exIDs.add("2");
        User user = new User(exIDs,"test@test.test",exIDs,"3","TEST","dasijioasdjijdsaijdsa");
        int result = AwsApi.deleteUser(user);
        assertEquals(200,result);
    }

    @Test
    public void deleteTask() throws IOException {
        int result = AwsApi.deleteTask("084104","1","dasijioasdjijdsaijdsa");
        assertEquals(200,result);
    }

    @Test
    public void deleteGroup() throws IOException {
        int result = AwsApi.deleteGroup("085006","2","dasijioasdjijdsaijdsa");
        assertEquals(200,result);
    }
}