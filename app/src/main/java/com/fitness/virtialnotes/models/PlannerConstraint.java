package com.fitness.virtialnotes.models;

public class PlannerConstraint {

    private String muscleGroup;
    private int amount;

    public PlannerConstraint(String muscleGroup, int amount){
        this.muscleGroup = muscleGroup;
        this.amount = amount;

    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }




}
