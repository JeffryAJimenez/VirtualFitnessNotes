package com.fitness.virtialnotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.fitness.virtialnotes.adapters.CustomizeRandomWorkoutRecyclerAdapter;
import com.fitness.virtialnotes.database.MuscleGroupDbHelper;
import com.fitness.virtialnotes.models.MuscleGroup;
import com.fitness.virtialnotes.models.Note;
import com.fitness.virtialnotes.models.PlannerConstraint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomizeRandomWorkoutView extends AppCompatActivity {
    private final String DATA_TAG = "DATA";
    private static final String[] DROP_DOWN_VALUES = {"calves",
            "hamstrings",
            "quadriceps",
            "glutes",
            "biceps",
            "triceps",
            "forearms",
            "traps",
            "lats",
            "chest"};

    RecyclerView recyclerView;
    private EditText number;
    private AutoCompleteTextView drop_down_menu;
    private VirtualNotesDbHelper db;
    private  ArrayList<MuscleGroup> data_muscle_group;
    private MuscleGroupDbHelper muscle_group_db;

    private static ArrayList<PlannerConstraint> data;
    CustomizeRandomWorkoutRecyclerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_random_workout_view);

        db = new VirtualNotesDbHelper(this);
        muscle_group_db = new MuscleGroupDbHelper(getApplicationContext());
        data = new ArrayList<>();

        recyclerView =  (RecyclerView) findViewById(R.id.recycle_view);
        number = (EditText) findViewById(R.id.number_box);
        drop_down_menu = (AutoCompleteTextView) findViewById(R.id.spinner_autoComplete);

        data_muscle_group = muscle_group_db.getAllMuscleGroups();
        ArrayList<String> temp = new ArrayList<>();
        data_muscle_group.forEach((muscleGroup -> temp.add(muscleGroup.getName())));

        ArrayAdapter<String> adapter_dropdown = new ArrayAdapter<String>(CustomizeRandomWorkoutView.this, R.layout.option_item, temp);

        drop_down_menu.setAdapter(adapter_dropdown);
        drop_down_menu.setText(adapter_dropdown.getItem(0), false);

        adapter = new CustomizeRandomWorkoutRecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addConstraint(View view){

        if( this.number.getText().toString().isEmpty()) return;
        int number = Integer.parseInt(this.number.getText().toString());
        String muscleGroup = drop_down_menu.getText().toString().trim();
        AtomicBoolean exist = new AtomicBoolean(false);

        if(number < 1 || number > 10) return;

        PlannerConstraint constraint = new PlannerConstraint(muscleGroup, number);

        data.forEach((o) -> {if(o.getMuscleGroup().equals(constraint.getMuscleGroup())){
            exist.set(true);
        }});

        if(exist.get()) return;

        data.add(constraint);
        adapter.notifyItemInserted(data.indexOf(constraint));


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void generateWorkoutPlan(View view){
        ArrayList<Note> plan = new ArrayList<Note>();

        data.forEach((o) -> {

            ArrayList<Note> notes = db.getNotesByMuscleGroup(o.getMuscleGroup());
            ArrayList<Integer> values = new ArrayList<>();
            Random random = new Random();

            int i = 0;

           while (i < o.getAmount()){
               Log.v("DATA SIZE", Integer.toString(notes.size()));
               Log.v("REQUESTED EXERCISES", Integer.toString(o.getAmount()));
                if(notes.size() <= o.getAmount()){
                    Log.v("RANDOMIZING", "FALSE");


                    values = IntStream.range(0, notes.size()).boxed().collect(Collectors.toCollection(ArrayList::new));
                    Log.v("VALUES", values.toString());
                    break;
                }

               int rand = random.nextInt(notes.size());
               Log.v("RANDOMIZING", "TRUE");
               if (!values.contains(rand)){
                   values.add(rand);
                   i++;
               }
           }

           values.forEach((e) -> {
               plan.add(notes.get(e));
           });

            Log.v("ARRAYLIST", plan.toString());

        });


        Intent intent = new Intent(CustomizeRandomWorkoutView.this, ShowPlanActivity.class);
        intent.putExtra(DATA_TAG, (Serializable) plan);

        startActivity(intent);

    }

}