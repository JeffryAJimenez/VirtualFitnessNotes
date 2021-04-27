package com.fitness.virtialnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;


import com.fitness.virtialnotes.adapters.PlannerAdapter;
import com.fitness.virtialnotes.models.Note;


import java.util.ArrayList;

public class ShowPlanActivity extends AppCompatActivity implements PlannerAdapter.OnNoteClickListener{
    String TAG = "ShowPlanActivity";
    private final String DATA_TAG = "DATA";
    String WORK_OUT_NAME = "workout_name";
    String MUSCLE_GROUP = "muscle-group";
    String DESCRIPTION = "description";

    RecyclerView recyclerView;
    private static ArrayList<Note> data;
    PlannerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = (ArrayList<Note>) getIntent().getSerializableExtra(DATA_TAG);

        if(savedInstanceState != null) {
            data = (ArrayList<Note>) savedInstanceState.getSerializable(DATA_TAG);
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);

        adapter = new PlannerAdapter(this, data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putSerializable(DATA_TAG, data);
//        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState: saved!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//    }
//
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        data = (ArrayList<Note>) savedInstanceState.getSerializable(DATA_TAG);
//        Log.d(TAG, "onSaveInstanceState: Restored :)");
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    public void onNoteClick(int position) {
        Note selectedItem =  (Note) data.get(position);
        String name = selectedItem.getExerciseName();
        String description = selectedItem.getDescription();
        String muscleGroup = selectedItem.getMuscleGroup();

        Intent intent = new Intent(ShowPlanActivity.this, ViewNoteActivity.class);
        intent.putExtra(WORK_OUT_NAME , name);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(MUSCLE_GROUP, muscleGroup);
        intent.putExtra(DATA_TAG, data);
        startActivity(intent);
    }
}