package com.example.projectmanagementapp.AwsAPI;


import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.Team;
import com.example.projectmanagementapp.data.model.User;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AwsApiGetTest {
    @Test
    public void getUserTest() throws IOException {
        List<String> groupIDs = new ArrayList();
        groupIDs.add("3");
        List<String> taskIDs = new ArrayList();
        taskIDs.add("2");
        taskIDs.add("223737");
        taskIDs.add("224749");
        User user = new User(groupIDs,"test@test.test",taskIDs,"1","test@test.test","dasijioasdjijdsaijdsa");
        User result = AwsApi.getUser("1","dasijioasdjijdsaijdsa");
        System.out.println(result);
        System.out.println(user);
        assertEquals(true,user.equals(result));
    }

    @Test
    public void getGroupTest() throws IOException{
        List<String> ids = new ArrayList();
        ids.add("1");
        ids.add("2");
        Team team = new Team("2","Android test name","085006",ids,ids);
        Team result = AwsApi.getTeam("085006","2","dasijioasdjijdsaijdsa");
        assertEquals(true,result.equals(team));
    }

    @Test
    public void getTaskTest() throws IOException{
        List<String> ids = new ArrayList();
        ids.add("1");
        Task task = new Task("1","12:06:2020",ids,"1","1","major","active","This is first task","FirstTask");
        Task result = AwsApi.getTask("1","1","dasijioasdjijdsaijdsa");
        assertEquals(true,result.equals(task));
    }
}
