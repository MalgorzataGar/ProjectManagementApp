package com.example.projectmanagementapp.data.model;

import java.util.List;

public class Task {
    public String creatorID;
    public String deadline;
    public List<String> executorsIDs;
    public String groupID;
    public String ID;
    public String priority;
    public String state;
    public String taskDescription;
    public String taskName;
    public Task(){}

    public String getCreatorID() {
        return creatorID;
    }

    public String getDeadline() {
        return deadline;
    }

    public List<String> getExecutorsIDs() {
        return executorsIDs;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getID() {
        return ID;
    }

    public String getPriority() {
        return priority;
    }

    public String getState() {
        return state;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task(String creatorID, String deadline, List<String> executorsIDs, String groupID, String ID, String priority, String state, String taskDescription, String taskName) {
        this.creatorID = creatorID;
        this.deadline = deadline;
        this.executorsIDs = executorsIDs;
        this.groupID = groupID;
        this.ID = ID;
        this.priority = priority;
        this.state = state;
        this.taskDescription = taskDescription;
        this.taskName = taskName;
    }
}
