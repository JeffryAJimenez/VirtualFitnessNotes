package com.fitness.virtialnotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.virtialnotes.R;
import com.fitness.virtialnotes.models.Note;
import com.fitness.virtialnotes.models.PlannerConstraint;

import java.util.ArrayList;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> data;

    public PlannerAdapter(Context context, ArrayList<Note> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PlannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_plan_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.muscleGroup.setText(data.get(position).getMuscleGroup());
        holder.workOutName.setText((data.get(position).getExerciseName()));
        holder.description.setText(data.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView muscleGroup, workOutName, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            muscleGroup = itemView.findViewById(R.id.muscle_group);
            workOutName = itemView.findViewById(R.id.workout_name);
            description = itemView.findViewById(R.id.description);
        }
    }
}
