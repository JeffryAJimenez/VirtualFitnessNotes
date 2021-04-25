package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

    public void onCreateNote(View view) {
        Intent intent= new Intent(DashBoardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onViewNotes(View view){
        Intent intent= new Intent(DashBoardActivity.this, ViewNotesActivity.class);
        startActivity(intent);
    }

    public void onStartRandomize(View view){
        Intent intent = new Intent(DashBoardActivity.this, CustomizeRandomWorkoutView.class);
        startActivity(intent);
    }

    public void onAddMuscleGroup(View view){
        Intent intent = new Intent(DashBoardActivity.this, MuscleGroupsListActivity.class);
        startActivity(intent);
    }
}