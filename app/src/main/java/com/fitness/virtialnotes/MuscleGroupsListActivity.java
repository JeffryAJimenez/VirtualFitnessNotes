package com.fitness.virtialnotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fitness.virtialnotes.adapters.MusclesAdapter;
import com.fitness.virtialnotes.adapters.PlannerAdapter;
import com.fitness.virtialnotes.database.MuscleGroupDbHelper;
import com.fitness.virtialnotes.models.MuscleGroup;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;

public class MuscleGroupsListActivity extends AppCompatActivity  implements MusclesAdapter.OnNoteClickListener {

    String TAG= "MuscleGroupsListActivity";
    private EditText input;
    private RecyclerView recyclerView;
    private ImageView addButton;
    MuscleGroupDbHelper db;
    MusclesAdapter adapter;
    ArrayList<MuscleGroup> data;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_groups_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input = (EditText) findViewById(R.id.input);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        addButton = (ImageView) findViewById(R.id.add_button);

        db = new MuscleGroupDbHelper(this);
        data = db.getAllMuscleGroups();
        adapter = new MusclesAdapter(this, data, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void onAddMuscleGroup(View view){
        String name = input.getText().toString();
        db.addNMuscle(name);
        MuscleGroup item =  (MuscleGroup) db.getMuscleGroupByName(name);

        if(item.getName().equals("")) return;
        data.add(item);


        adapter.notifyItemInserted(data.indexOf(item));
        adapter.notifyItemRangeChanged(data.indexOf(item), data.size());
        input.setText("");
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onDeleteItem(int position) {
       MuscleGroup selectedItem =  (MuscleGroup) data.get(position);
       Log.v("Onlcik", "clicked");
       db.deleteMuscle(selectedItem);
       data.remove(position);
       adapter.notifyItemRemoved(position);
       adapter.notifyItemRangeChanged(position, data.size());
    }

}