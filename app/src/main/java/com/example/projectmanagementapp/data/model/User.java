package com.example.projectmanagementapp.data.model;

import java.util.List;

public class User {

    public List<String> groupIDs;
    public String login;
    public List<String> taskIDs;
    public String ID;
    public String name;
    public User(){}

/*
    public User(String id, String Name, String login, List<String> TeamsIDs,
                List<String> TasksIDs){
        this.ID = id;
        this.name = Name;
        this.login = login;
        this.groupIDs = TeamsIDs;
        this.taskIDs = TasksIDs;
    }
*/
}