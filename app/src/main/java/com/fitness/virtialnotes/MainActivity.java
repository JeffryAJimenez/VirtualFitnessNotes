package com.fitness.virtialnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fitness.virtialnotes.models.Note;

public class MainActivity extends AppCompatActivity {

    private static final String[]  DROP_DOWN_VALUES = {"calves",
            "hamstrings",
            "quadriceps",
            "glutes",
            "biceps",
            "triceps",
            "forearms",
            "traps",
            "lats",
            "chest"};

    private EditText input_name;
    private EditText input_description;
    private Spinner drop_down_menu;
    private String selectedMuscleGroup;
    private VirtualNotesDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new VirtualNotesDbHelper(getApplicationContext());
        selectedMuscleGroup = "";
        input_name = (EditText)findViewById(R.id.name);
        input_description = (EditText)findViewById(R.id.description);
        drop_down_menu = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, DROP_DOWN_VALUES);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        drop_down_menu.setAdapter(adapter);
        drop_down_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedMuscleGroup = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onCreateNewNote(View view){

        String name = input_name.getText().toString();
        String description = input_description.getText().toString();

        if (name.isEmpty() || description.isEmpty() || selectedMuscleGroup.isEmpty()){
            Log.v("EMPTY_VALUE", "Please fill every Field");
            return;
        }

        Note note = new Note(name, description, selectedMuscleGroup);
        db.addNote(note);
    }

    public void onSeeNotes(View view){
        Intent intent = new Intent(MainActivity.this, ViewNotesActivity.class);
        startActivity(intent);
    }
}