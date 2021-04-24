package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.fitness.virtialnotes.adapters.PlannerAdapter;
import com.fitness.virtialnotes.models.Note;


import java.util.ArrayList;

public class ShowPlanActivity extends AppCompatActivity implements PlannerAdapter.OnNoteClickListener{
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


        data = (ArrayList<Note>) getIntent().getSerializableExtra(DATA_TAG);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);

        adapter = new PlannerAdapter(this, data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

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
        startActivity(intent);
    }
}