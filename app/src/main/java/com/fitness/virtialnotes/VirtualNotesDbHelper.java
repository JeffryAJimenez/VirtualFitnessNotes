package com.fitness.virtialnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.fitness.virtialnotes.models.Note;

import java.util.ArrayList;


public class VirtualNotesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FitnessVirtualNotes.db";

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_NAME_EXERCISE_NAME = "exerciseName";
        public static final String COLUMN_NAME_MUSCLE_GROUP = "muscleGroup";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    public VirtualNotesDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_MUSCLE_GROUP+ " TEXT," +
                    FeedEntry.COLUMN_NAME_EXERCISE_NAME+ " TEXT," +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addNote(Note note){

        note.setExerciseName(note.getExerciseName().toUpperCase());

        SQLiteDatabase readable = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedEntry.COLUMN_NAME_MUSCLE_GROUP
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_EXERCISE_NAME + " = ?";
        String[] selectionArgs = { note.getExerciseName() };


        Cursor cursor = readable.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null             // The sort order
        );

        if(cursor.getCount() > 0){
            cursor.close();
            return -1;
        }

        cursor.close();


        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_EXERCISE_NAME, note.getExerciseName());
        values.put(FeedEntry.COLUMN_NAME_DESCRIPTION, note.getDescription());
        values.put(FeedEntry.COLUMN_NAME_MUSCLE_GROUP, note.getMuscleGroup());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public int deleteNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        String selection = FeedEntry.COLUMN_NAME_EXERCISE_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { note.getExerciseName() };
        // Issue SQL statement.
        return db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public int UpdateNote(Note note, String original_name){

        SQLiteDatabase db = getWritableDatabase();
        note.setExerciseName(note.getExerciseName().toUpperCase());

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_EXERCISE_NAME, note.getExerciseName());
        values.put(FeedEntry.COLUMN_NAME_DESCRIPTION, note.getDescription());
        values.put(FeedEntry.COLUMN_NAME_MUSCLE_GROUP, note.getMuscleGroup());

        // Which row to update, based on the title
        String selection = FeedEntry.COLUMN_NAME_EXERCISE_NAME + " LIKE ?";
        String[] selectionArgs = {original_name };

        return db.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public ArrayList<Note> getNotesByMuscleGroup(String muscleGroup){

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<Note>();

        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                FeedEntry.COLUMN_NAME_MUSCLE_GROUP,
                FeedEntry.COLUMN_NAME_DESCRIPTION
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedEntry.COLUMN_NAME_MUSCLE_GROUP + " DESC";
        String selection = FeedEntry.COLUMN_NAME_MUSCLE_GROUP+ " LIKE ?";
        String[] selectionArgs = {muscleGroup};

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        int name_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_EXERCISE_NAME);
        int description_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DESCRIPTION);
        int muscleGroup_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_MUSCLE_GROUP);

        while(cursor.moveToNext()){

            String name = cursor.getString(name_index);
            String description = cursor.getString(description_index);
            String muscleGroup_val = cursor.getString(muscleGroup_index);

            notes.add(new Note(name, description, muscleGroup_val));
        }

        cursor.close();

        return notes;
    }

    public ArrayList<Note> getAllNotes(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<Note>();

        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                FeedEntry.COLUMN_NAME_MUSCLE_GROUP,
                FeedEntry.COLUMN_NAME_DESCRIPTION
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedEntry.COLUMN_NAME_MUSCLE_GROUP + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        int name_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_EXERCISE_NAME);
        int description_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DESCRIPTION);
        int muscleGroup_index = cursor.getColumnIndex(FeedEntry.COLUMN_NAME_MUSCLE_GROUP);

        while(cursor.moveToNext()){

            String name = cursor.getString(name_index);
            String description = cursor.getString(description_index);
            String muscleGroup = cursor.getString(muscleGroup_index);

            notes.add(new Note(name, description, muscleGroup));
        }

        cursor.close();

        return notes;
    }
}
