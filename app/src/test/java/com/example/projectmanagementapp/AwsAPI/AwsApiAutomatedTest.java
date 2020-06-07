package com.example.projectmanagementapp.AwsAPI;

import com.example.projectmanagementapp.data.model.Task;
import com.example.projectmanagementapp.data.model.Team;
import com.example.projectmanagementapp.data.model.User;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AwsApiAutomatedTest {
    private String IuserLogin = "AutTest";
    private String usersPasswordHash = "asdfgqwerty";
    private String IuserName = "AutTestName";
    private String IIuserName = "AutTestNameII";
    private String IIuserLogin = "IIAutTest";

    private String IgroupNamePrefix = "IautTestGroup";
    private String IIgroupNamePrefix ="IIautTestGroup";

    private String ItaskNamePrefix = "IautTestTask";
    private String IItaskNamePrefix = "IIautTestTask";
    private String deadlinePrefix = "20:06";

    private List<String> emptyList = new ArrayList();

/*
    @Test
    public void AwsApiAutomatedTestHandler() throws IOException, JSONException {
        //Log in users
        String IusrID = AwsApi.login(IuserLogin,usersPasswordHash);
        assertEquals("14",IusrID);
        String IIusrID = AwsApi.login(IIuserLogin,usersPasswordHash);
        assertEquals("15",IIusrID);

        //Create empty groups
        Team teamI = new Team(IusrID,IgroupNamePrefix,"999",emptyList,emptyList);
        String result = AwsApi.postOrUpdateGroup(teamI,false,IusrID,usersPasswordHash);
        assertEquals(200,result);
        Team teamII = new Team(IIusrID,IIgroupNamePrefix,"998",emptyList,emptyList);
        result = AwsApi.postOrUpdateGroup(teamII,false,IIusrID,usersPasswordHash);
        assertEquals(200,result);

        //Create empty tasks
        Task task1 = new Task(IusrID,deadlinePrefix,emptyList,"","999","normal","active","descrpiton",ItaskNamePrefix);
        result = AwsApi.postOrUpdateTask(task1,false,IusrID,usersPasswordHash);
        assertEquals(200,result);
        Task task2 = new Task(IIusrID,deadlinePrefix,emptyList,"","998","normal","active","description",IItaskNamePrefix);
        result = AwsApi.postOrUpdateTask(task2,false,IIusrID,usersPasswordHash);
        assertEquals(200,result);

        //Get all groups to find previously created groups ids
        JsonObject allGroups = AwsApi.getAllGroups(IusrID,usersPasswordHash);
        teamI.ID = allGroups.get(IgroupNamePrefix).getAsString();
        teamII.ID = allGroups.get(IIgroupNamePrefix).getAsString();

        //Get all tasks to find previously created tasks ids
        JsonObject allTasks = AwsApi.getAllTasks(IusrID,usersPasswordHash);
        System.out.println(allTasks.get(IItaskNamePrefix));
        task1.ID = allTasks.get(ItaskNamePrefix).getAsString();
        //task1.ID = allTasks.getAsString(ItaskNamePrefix);
        task2.ID = allTasks.get(IItaskNamePrefix).getAsString();
        System.out.println(task1.ID);

        //Get users data
        User user1 = AwsApi.getUser(IusrID,usersPasswordHash);
        User user2 = AwsApi.getUser(IIusrID,usersPasswordHash);

        //Update users
        List<String> TaskList = new ArrayList();
        TaskList.add(task1.ID);
        List<String> GroupsList = new ArrayList();
        GroupsList.add(teamI.ID);
        user1.taskIDs = TaskList;
        user1.groupIDs = GroupsList;
        user1.passwordHash = usersPasswordHash;
        result = AwsApi.updateUser(user1,user1.ID,user1.passwordHash);
        assertEquals(200,result);

        List<String> TaskList2 = new ArrayList();
        TaskList2.add(task2.ID);
        List<String> GroupsList2 = new ArrayList();
        GroupsList2.add(teamII.ID);
        user2.taskIDs = TaskList2;
        user2.groupIDs = GroupsList2;
        user2.passwordHash = usersPasswordHash;
        result = AwsApi.updateUser(user2,user2.ID,user2.passwordHash);
        assertEquals(200,result);

        //Update tasks
        task1.groupID = teamI.ID;
        List<String> IUsersList = new ArrayList();
        IUsersList.add(user1.ID);
        task1.executorsIDs = IUsersList;
        result = AwsApi.postOrUpdateTask(task1,true,user1.ID,usersPasswordHash);
        assertEquals(200,result);

        task2.groupID = teamII.ID;
        List<String> IIUsersList = new ArrayList();
        IIUsersList.add(user2.ID);
        task2.executorsIDs = IIUsersList;
        result = AwsApi.postOrUpdateTask(task2,true,user2.ID,usersPasswordHash);
        assertEquals(200,result);

        //Update groups
        teamI.usersIDs = IUsersList;
        teamI.taskIDs = TaskList;
        result = AwsApi.postOrUpdateGroup(teamI,true,user1.ID,usersPasswordHash);
        assertEquals(200,result);

        teamII.usersIDs = IIUsersList;
        teamII.taskIDs = TaskList2;
        result = AwsApi.postOrUpdateGroup(teamII,true,user2.ID,usersPasswordHash);
        assertEquals(200,result);

        //Delete tasks
        result = AwsApi.deleteTask(task1.ID,user1.ID,usersPasswordHash);
        assertEquals(200,result);

        result = AwsApi.deleteTask(task2.ID,user2.ID,usersPasswordHash);
        assertEquals(200,result);

        //Delete groups
        result = AwsApi.deleteGroup(teamI.ID,user1.ID,usersPasswordHash);
        assertEquals(200, result);

        result = AwsApi.deleteGroup(teamII.ID,user2.ID,usersPasswordHash);
        assertEquals(200,result);

        //Clean users
        user1.groupIDs = emptyList;
        user1.taskIDs = emptyList;
        result = AwsApi.updateUser(user1,user1.ID, user1.passwordHash);
        assertEquals(200,result);

        user2.groupIDs = emptyList;
        user2.taskIDs = emptyList;
        result = AwsApi.updateUser(user2,user2.ID,user2.passwordHash);
        assertEquals(200,result);


    }*/
}