package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.fitness.virtialnotes.adapters.NoteListAdapter;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;

public class ViewNotesActivity extends AppCompatActivity  {

    private ListView notes_list_view;
    private VirtualNotesDbHelper db;
    final private String NAME_TAG = "NAME";
    final private String DESCRIPTION_TAG = "description";
    final private String MUSCLE_GROUP_TAG = "MUSCLE_GROUP";

    @Override
    protected void onResume() {
        super.onResume();

        NoteListAdapter adapter = new NoteListAdapter(this, R.layout.adapter_view_note_layout, db.getAllNotes());
        notes_list_view.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        notes_list_view  = (ListView) findViewById(R.id.notes_listView);
        db = new VirtualNotesDbHelper(getApplicationContext());


        NoteListAdapter adapter = new NoteListAdapter(this, R.layout.adapter_view_note_layout, db.getAllNotes());
        notes_list_view.setAdapter(adapter);
        notes_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                //                TextView selectedItem =  (TextView) adapterView.getItemAtPosition(position);
//                String name = ((TextView) selectedItem.findViewById(R.id.listView_item_name)).getText().toString();
//                String description = ((TextView) selectedItem.findViewById(R.id.listView_item_description)).getText().toString();
//                String muscleGroup = ((TextView) selectedItem.findViewById(R.id.listView_item_muscle_group)).getText().toString();

                Note selectedItem =  (Note) adapterView.getItemAtPosition(position);
                String name = selectedItem.getExerciseName();
                String description = selectedItem.getDescription();
                String muscleGroup = selectedItem.getMuscleGroup();

                Intent intent = new Intent(ViewNotesActivity.this, EditNoteView.class);
                intent.putExtra(NAME_TAG , name);
                intent.putExtra(DESCRIPTION_TAG, description);
                intent.putExtra(MUSCLE_GROUP_TAG, muscleGroup);
                startActivity(intent);
            }
        });


    }


}