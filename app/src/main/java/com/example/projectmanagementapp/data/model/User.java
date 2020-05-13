package com.example.projectmanagementapp.data.model;

import java.util.List;

public class User {


    public List<String> groupIDs;
    public String login;
    public List<String> taskIDs;
    public String ID;
    public String name;
    public String passwordHash;
    public User(){}

    public User(List<String> groupIDs, String login, List<String> taskIDs, String ID, String name, String passwordHash) {
        this.groupIDs = groupIDs;
        this.login = login;
        this.taskIDs = taskIDs;
        this.ID = ID;
        this.name = name;
        this.passwordHash = passwordHash;
    }
    public List<String> getGroupIDs() {
        return groupIDs;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getTaskIDs() {
        return taskIDs;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}