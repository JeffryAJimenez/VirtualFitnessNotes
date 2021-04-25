package com.fitness.virtialnotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.virtialnotes.R;
import com.fitness.virtialnotes.models.MuscleGroup;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;

public class MusclesAdapter extends RecyclerView.Adapter<MusclesAdapter.ViewHolder>{

    Context context;
    ArrayList<MuscleGroup> data;
    private MusclesAdapter.OnNoteClickListener onNoteClickListener;

    public MusclesAdapter(Context context, ArrayList<MuscleGroup> data, MusclesAdapter.OnNoteClickListener onNoteClickListener){
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
    public MusclesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.muscles_row, parent,false);
        return new MusclesAdapter.ViewHolder(view, onNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MusclesAdapter.ViewHolder holder, int position) {

        holder.textLabel.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textLabel;
        ImageView imageView;
        MusclesAdapter.OnNoteClickListener onNoteClickListener;

        public ViewHolder(@NonNull View itemView, MusclesAdapter.OnNoteClickListener onNoteClickListener) {
            super(itemView);

            textLabel = itemView.findViewById(R.id.name_label);
            imageView = itemView.findViewById(R.id.image_view);
            this.onNoteClickListener = onNoteClickListener;

            Log.v("PLANNERADAPTER", "about to set on click listener ");
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onNoteClickListener == null){
                Log.v("PLANNERADAPTER", "onNoteClickListener is null ");
            }
            Log.v("PLANNERADAPTER", "onClick: ");
            onNoteClickListener.onDeleteItem(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener{
        void onDeleteItem( int position);
    }

}
