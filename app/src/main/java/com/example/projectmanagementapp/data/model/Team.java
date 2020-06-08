package com.example.projectmanagementapp.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Team {
    public String adminID;
    public String groupName;
    public String ID;
    public List<String> taskIDs;
    public List<String> usersIDs;
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

    public List<String> getUsersIDs() {
        return usersIDs;
    }

    public Team(String adminID, String groupName, String ID, List<String> taskIDs, List<String> usersIDs) {
        this.adminID = adminID;
        this.groupName = groupName;
        this.ID = ID;
        this.taskIDs = taskIDs;
        if(!usersIDs.contains(adminID))
            usersIDs.add(adminID);
        this.usersIDs = usersIDs;
    }
    public Team(String adminID, String groupName, String ID, List<String> taskIDs) {
        this.adminID = adminID;
        this.groupName = groupName;
        this.ID = ID;
        this.taskIDs = taskIDs;
        this.usersIDs = new ArrayList<String>();
        usersIDs.add(adminID);
    }

    public boolean equals(Team otherTeam){
        if(!this.ID.equals(otherTeam.ID)){
            return false;
        }
        if(!this.adminID.equals(otherTeam.adminID)){
            return false;
        }
        if(!this.groupName.equals(otherTeam.groupName)){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return ID+" "+groupName;
    }
}
