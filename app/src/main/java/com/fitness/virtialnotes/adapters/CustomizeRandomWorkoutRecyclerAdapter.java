package com.fitness.virtialnotes.adapters;

import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.virtialnotes.R;
import com.fitness.virtialnotes.models.PlannerConstraint;

import java.util.ArrayList;


public class CustomizeRandomWorkoutRecyclerAdapter extends RecyclerView.Adapter<CustomizeRandomWorkoutRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<PlannerConstraint> data;

    public  CustomizeRandomWorkoutRecyclerAdapter(Context context, ArrayList<PlannerConstraint> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.customize_random_workout_recycler_row, parent,false);
        return new ViewHolder(view);
    }

    //communicating with viewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.v("Muscle_Group", data.get(position).getMuscleGroup());
        Log.v("NUMBER", Integer.toString(data.get(position).getAmount()));
        holder.muscleGroup.setText(data.get(position).getMuscleGroup());
        holder.numberOfExercises.setText(Integer.toString(data.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView muscleGroup, numberOfExercises;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            muscleGroup = itemView.findViewById(R.id.muscle_group);
            numberOfExercises = itemView.findViewById(R.id.number);
        }
    }
}
