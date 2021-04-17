package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.fitness.virtialnotes.adapters.NoteListAdapter;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;

public class ViewNotesActivity extends AppCompatActivity {

    private ListView notes_list_view;
    private VirtualNotesDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        notes_list_view  = (ListView) findViewById(R.id.notes_listView);
        db = new VirtualNotesDbHelper(getApplicationContext());


        NoteListAdapter adapter = new NoteListAdapter(this, R.layout.adapter_view_note_layout, db.getAllNotes());
        notes_list_view.setAdapter(adapter);
    }
}