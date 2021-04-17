package com.fitness.virtialnotes.models;

public class Note {

    private String exerciseName;
    final private String description;
    final private String muscleGroup;

    public String getExerciseName() {
        return exerciseName;
    }

    public String getDescription() {
        return description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Note(String exerciseName, String description, String muscleGroup){
        this.exerciseName = exerciseName;
        this.description = description;
        this.muscleGroup = muscleGroup;
    }
}
