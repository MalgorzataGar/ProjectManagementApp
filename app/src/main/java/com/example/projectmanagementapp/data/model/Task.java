package com.example.projectmanagementapp.data.model;

public class Task {
    public String Name;
    public String Description;
    public State State;
    public Team Team;
    public User Executor;
}

enum State{
    ToDo,
    InProgress,
    Done,
}