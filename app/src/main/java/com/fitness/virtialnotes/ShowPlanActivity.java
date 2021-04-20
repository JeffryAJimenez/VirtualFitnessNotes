package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.fitness.virtialnotes.adapters.PlannerAdapter;
import com.fitness.virtialnotes.models.Note;


import java.util.ArrayList;

public class ShowPlanActivity extends AppCompatActivity {
    private final String DATA_TAG = "DATA";

    RecyclerView recyclerView;
    private static ArrayList<Note> data;
    PlannerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);


        data = (ArrayList<Note>) getIntent().getSerializableExtra(DATA_TAG);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);

        adapter = new PlannerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}