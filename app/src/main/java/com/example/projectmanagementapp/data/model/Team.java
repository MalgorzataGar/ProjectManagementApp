package com.example.projectmanagementapp.data.model;

import java.util.List;
import java.util.Map;

public class Team {
    public String adminID;
    public String groupName;
    public String ID;
    public List<String> taskIDs;
    public Map<String,String> usersIDsROles;
    public Team(){}

    public String getAdminID() {
        return adminID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getID() {
        return ID;
    }

    public List<String> getTaskIDs() {
        return taskIDs;
    }

    public Map<String, String> getUsersIDsROles() {
        return usersIDsROles;
    }

    public Team(String adminID, String groupName, String ID, List<String> taskIDs, Map<String, String> usersIDsROles) {
        this.adminID = adminID;
        this.groupName = groupName;
        this.ID = ID;
        this.taskIDs = taskIDs;
        this.usersIDsROles = usersIDsROles;
    }
    public Team(String adminID, String groupName, String ID, List<String> taskIDs) {
        this.adminID = adminID;
        this.groupName = groupName;
        this.ID = ID;
        this.taskIDs = taskIDs;
    }
}
