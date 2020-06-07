package com.example.projectmanagementapp.AwsAPI;

import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.Team;
import com.example.projectmanagementapp.data.model.User;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class AwsApiPostTest {

    @Test
    public void UpdateUser() throws IOException, JSONException {
        List<String> grpIDS = new ArrayList();
        //grpIDS.add("1");
        List<String> tasks = new ArrayList();
        tasks.add("3");
        tasks.add("2");
        User user = new User(grpIDS,"test@test.test",tasks,"3","androidTest","dasijioasdjijdsaijdsa");
        int result = AwsApi.updateUser(user,"1","dasijioasdjijdsaijdsa");
        assertEquals(200,result);
    }

    @Test
    public void postOrUpdateTask() throws IOException, JSONException{
        List<String> exIDs = new ArrayList();
        exIDs.add("1");
        exIDs.add("2");
        Task task = new Task("1","12:06:2020",exIDs,"2","084824","major","abandoned","Android test update task","New name");
        int result = AwsApi.postOrUpdateTask(task,true,"1","dasijioasdjijdsaijdsa");
        assertEquals(200,result);
    }

    @Test
    public void postOrUpdateGroup() throws IOException, JSONException{
        List<String> ids = new ArrayList();
        ids.add("1");
        ids.add("5");
        ids.add("3");
        Team team = new Team("2","Android test name","104134",ids,ids);
        int result = AwsApi.postOrUpdateGroup(team,true,"3","dasijioasdjijdsaijdsa");
        assertEquals(200,result);
    }
}