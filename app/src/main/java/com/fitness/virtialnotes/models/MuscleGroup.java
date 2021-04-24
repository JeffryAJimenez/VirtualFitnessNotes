package com.fitness.virtialnotes.models;

public class MuscleGroup {
    private String mName;
    private String mID;

    public MuscleGroup(String name, String ID){
        mName = name;
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }
}
