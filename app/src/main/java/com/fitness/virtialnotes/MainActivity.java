package com.fitness.virtialnotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fitness.virtialnotes.database.MuscleGroupDbHelper;
import com.fitness.virtialnotes.models.MuscleGroup;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainActivity extends AppCompatActivity {

    private static final ArrayList<String> DROP_DOWN_VALUES = new ArrayList<>(List.of("calves",
            "hamstrings",
            "quadriceps",
            "glutes",
            "biceps",
            "triceps",
            "forearms",
            "traps",
            "lats",
            "chest"));


    private EditText input_name;
    private EditText input_description;
    private AutoCompleteTextView drop_down_menu;
    private VirtualNotesDbHelper db;
    private  ArrayList<MuscleGroup> data_muscle_group;
    private MuscleGroupDbHelper muscle_group_db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new VirtualNotesDbHelper(getApplicationContext());
        muscle_group_db = new MuscleGroupDbHelper(getApplicationContext());

        input_name = (EditText)findViewById(R.id.name_box);
        input_description = (EditText)findViewById(R.id.description_box);
        drop_down_menu = (AutoCompleteTextView) findViewById(R.id.spinner_autoComplete);

        data_muscle_group = muscle_group_db.getAllMuscleGroups();

        ArrayList<String> temp = new ArrayList<>();

        data_muscle_group.forEach((muscleGroup -> temp.add(muscleGroup.getName())));

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.option_item, temp);

        drop_down_menu.setAdapter(adapter);
        drop_down_menu.setText(adapter.getItem(0), false);
    }

    public void onCreateNewNote(View view){

        String name = input_name.getText().toString().trim();
        String description = input_description.getText().toString().trim();
        String muscleGroup = drop_down_menu.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || muscleGroup.isEmpty()){
            Log.v("EMPTY_VALUE", "Please fill every Field");
            Log.v("NAME", input_name.getText().toString());
            Log.v("DESCRIPTION", input_description.getText().toString());
            Log.v("MUSCLE_GROUP", drop_down_menu.getText().toString());

            return;
        }

        Note note = new Note(name, description, muscleGroup);
        db.addNote(note);

        input_name.setText("");
        input_description.setText("");

    }




}