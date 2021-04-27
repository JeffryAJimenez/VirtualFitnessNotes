package com.fitness.virtialnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.fitness.virtialnotes.models.Note;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewNoteActivity extends AppCompatActivity {
    String WORK_OUT_NAME = "workout_name";
    String MUSCLE_GROUP = "muscle-group";
    String DESCRIPTION = "description";
    private final String DATA_TAG = "DATA";

    TextView mWorkout_name;
    TextView mMuscleGroup;
    TextView mDescription;
    private ArrayList<Note> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String workoutName = intent.getStringExtra(WORK_OUT_NAME);
        String muscle_group = intent.getStringExtra(MUSCLE_GROUP);
        String description = intent.getStringExtra(DESCRIPTION);
        data = (ArrayList<Note>) getIntent().getSerializableExtra(DATA_TAG);

        mWorkout_name = (TextView) findViewById(R.id.workout_name);
        mMuscleGroup = (TextView) findViewById(R.id.muscle_group);
        mDescription = (TextView) findViewById(R.id.description);

        mWorkout_name.setText(workoutName);
        mMuscleGroup.setText(muscle_group);
        mDescription.setText(description);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent  = new Intent(this, ShowPlanActivity.class);
                intent.putExtra(DATA_TAG, data);
                startActivity(intent);
                finish();
                return true; //this does the trick
        }
        return super.onOptionsItemSelected(item);
    }
}