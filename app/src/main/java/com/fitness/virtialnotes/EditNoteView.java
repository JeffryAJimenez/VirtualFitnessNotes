package com.fitness.virtialnotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)

public class EditNoteView extends AppCompatActivity {
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

    final private String NAME_TAG = "NAME";
    final private String DESCRIPTION_TAG = "description";
    final private String MUSCLE_GROUP_TAG = "MUSCLE_GROUP";

    private String original;
    private EditText input_name;
    private EditText input_description;
    private AutoCompleteTextView drop_down_menu;

    private VirtualNotesDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_view);

        Bundle extras = getIntent().getExtras();

        db = new VirtualNotesDbHelper(getApplicationContext());

        input_name = (EditText)findViewById(R.id.name_box);
        input_description = (EditText)findViewById(R.id.description_box);
        drop_down_menu = (AutoCompleteTextView) findViewById(R.id.spinner_autoComplete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditNoteView.this, R.layout.option_item, DROP_DOWN_VALUES);

//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        drop_down_menu.setAdapter(adapter);
        int spinnerPosition = DROP_DOWN_VALUES.indexOf(extras.getString(MUSCLE_GROUP_TAG));
        drop_down_menu.setText(adapter.getItem(spinnerPosition), false);

        original = extras.getString(NAME_TAG);
        input_name.setText(extras.getString(NAME_TAG));
        input_description.setText(extras.getString(DESCRIPTION_TAG));

//        int spinnerPosition = DROP_DOWN_VALUES.indexOf(extras.getString(MUSCLE_GROUP_TAG));
//        drop_down_menu.setText(adapter.getItem(spinnerPosition), false);

    }

    public void onUpdateNote(View view){

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
        db.UpdateNote(note, original);

        finish();
    }
}