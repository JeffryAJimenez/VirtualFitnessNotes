package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewNoteActivity extends AppCompatActivity {
    String WORK_OUT_NAME = "workout_name";
    String MUSCLE_GROUP = "muscle-group";
    String DESCRIPTION = "description";

    TextView mWorkout_name;
    TextView mMuscleGroup;
    TextView mDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Intent intent = getIntent();
        String workoutName = intent.getStringExtra(WORK_OUT_NAME);
        String muscle_group = intent.getStringExtra(MUSCLE_GROUP);
        String description = intent.getStringExtra(DESCRIPTION);

        mWorkout_name = (TextView) findViewById(R.id.workout_name);
        mMuscleGroup = (TextView) findViewById(R.id.muscle_group);
        mDescription = (TextView) findViewById(R.id.description);

        mWorkout_name.setText(workoutName);
        mMuscleGroup.setText(muscle_group);
        mDescription.setText(description);

    }
}