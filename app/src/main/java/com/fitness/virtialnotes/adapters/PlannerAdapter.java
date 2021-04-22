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
    private OnNoteClickListener onNoteClickListener;

    public PlannerAdapter(Context context, ArrayList<Note> data, OnNoteClickListener onNoteClickListener){
        this.context = context;
        this.data = data;
        this.onNoteClickListener = onNoteClickListener;
    }

//    public PlannerAdapter(Context context, ArrayList<Note> data){
//        this.context = context;
//        this.data = data;
//        this.onNoteClickListener = null;
//    }

    @NonNull
    @Override
    public PlannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_plan_row, parent,false);
        return new ViewHolder(view, onNoteClickListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView muscleGroup, workOutName, description;
        OnNoteClickListener onNoteClickListener;

        public ViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);

            muscleGroup = itemView.findViewById(R.id.muscle_group);
            workOutName = itemView.findViewById(R.id.workout_name);
            description = itemView.findViewById(R.id.description);
            this.onNoteClickListener = onNoteClickListener;

            Log.v("PLANNERADAPTER", "about to set on click listener ");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onNoteClickListener == null){
                Log.v("PLANNERADAPTER", "onNoteClickListener is null ");
            }
            Log.v("PLANNERADAPTER", "onClick: ");
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener{
        void onNoteClick(int position);
    }
}
