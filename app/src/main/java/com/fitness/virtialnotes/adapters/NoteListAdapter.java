package com.fitness.virtialnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitness.virtialnotes.R;
import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteListAdapter extends ArrayAdapter<Note> {

    private static final String TAG = "NoteListAdapter";
    private Context mContext;
    private int resource_id;
    private int lastPosition;

    static class ViewHolder {
        TextView name;
        TextView description;
        TextView muscleGroup;
    }

    public NoteListAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        mContext = context;
        resource_id = resource;
        lastPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get object infor
        String name = getItem(position).getExerciseName();
        String description = getItem(position).getDescription();
        String muscleGroup = getItem(position).getMuscleGroup();

        //create a note object
        Note note = new Note(name, description, muscleGroup);
        ViewHolder holder;

        //create the view result for showing the animation
        final View result;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resource_id, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.listView_item_name);
            holder.description = (TextView) convertView.findViewById(R.id.listView_item_description);
            holder.muscleGroup = (TextView) convertView.findViewById(R.id.listView_item_muscle_group);

            result = convertView;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(note.getExerciseName());
        holder.description.setText(note.getDescription());
        holder.muscleGroup.setText(note.getMuscleGroup());
        return convertView;

    }
}
