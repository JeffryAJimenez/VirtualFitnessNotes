package com.fitness.virtialnotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.fitness.virtialnotes.VirtualNotesDbHelper;
import com.fitness.virtialnotes.models.MuscleGroup;
import com.fitness.virtialnotes.models.Note;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MuscleGroupDbHelper extends SQLiteOpenHelper {

    String TAG = "MuscleGroupDbHelper";

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

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FitnessVirtualNotes.db";

    /* Inner class that defines the table contents */
    public static class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "MuscleGroups";
        public static final String COLUMN_NAME = "name";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY," +
                    TableEntry.COLUMN_NAME+ " TEXT)";

    private static final String SQL_CREATE_NOTES_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + VirtualNotesDbHelper.FeedEntry.TABLE_NAME + " (" +
                    VirtualNotesDbHelper.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    VirtualNotesDbHelper.FeedEntry.COLUMN_NAME_MUSCLE_GROUP+ " TEXT," +
                    VirtualNotesDbHelper.FeedEntry.COLUMN_NAME_EXERCISE_NAME+ " TEXT," +
                    VirtualNotesDbHelper.FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";


    public MuscleGroupDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.v(TAG, "database created");
        InitializeData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 1:
                db.execSQL(SQL_CREATE_NOTES_ENTRIES);
                // no break statement, so case 2 will execute after this.
            case 2:
                db.execSQL(SQL_CREATE_ENTRIES);
                InitializeData(db);
//                DROP_DOWN_VALUES.forEach(this::addNMuscle);
        }
    }

    public void InitializeData(SQLiteDatabase db){


        DROP_DOWN_VALUES.forEach(e -> {db.execSQL("INSERT INTO " + TableEntry.TABLE_NAME + " VALUES ('" + DROP_DOWN_VALUES.indexOf(e) + "', '" + e.toUpperCase() + "');") ; });

    }

    public long addNMuscle(String muscleGroup){

        muscleGroup = muscleGroup.toUpperCase();

        SQLiteDatabase readable = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                TableEntry.COLUMN_NAME,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = TableEntry.COLUMN_NAME + " = ?";
        String[] selectionArgs = { muscleGroup };


        Cursor cursor = readable.query(
                TableEntry.TABLE_NAME,   // The table to query
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
        values.put(TableEntry.COLUMN_NAME, muscleGroup);

        // Insert the new row, returning the primary key value of the new row
        return db.insert(TableEntry.TABLE_NAME, null, values);
    }

    public int deleteMuscle(MuscleGroup muscleGroup){
        SQLiteDatabase db = getWritableDatabase();

        String selection = TableEntry._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { muscleGroup.getID() };
        // Issue SQL statement.
        return db.delete(TableEntry.TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<MuscleGroup> getAllMuscleGroups(){

//        DROP_DOWN_VALUES.forEach(this::addNMuscle);

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MuscleGroup> muscleGroups = new ArrayList<MuscleGroup>();

        String[] projection = {
                BaseColumns._ID,
                TableEntry.COLUMN_NAME,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = TableEntry.COLUMN_NAME + " DESC";

        Cursor cursor = db.query(
                TableEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        int name_index = cursor.getColumnIndex(TableEntry.COLUMN_NAME);
        int id_index = cursor.getColumnIndex(TableEntry._ID);

        while(cursor.moveToNext()){

            String name = cursor.getString(name_index);
            String id = cursor.getString(id_index);

            muscleGroups.add(new MuscleGroup(name, id));
        }

        cursor.close();

        return muscleGroups;
    }

    public MuscleGroup getMuscleGroupByName(String name){
        name = name.toUpperCase();
        SQLiteDatabase db = getReadableDatabase();
        MuscleGroup muscleGroup = new MuscleGroup("", "");

        String[] projection = {
                BaseColumns._ID,
                TableEntry.COLUMN_NAME
        };

        // How you want the results sorted in the resulting Cursor

        String selection = TableEntry.COLUMN_NAME+ " LIKE ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(
                TableEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        int name_index = cursor.getColumnIndex(TableEntry.COLUMN_NAME);
        int id_index = cursor.getColumnIndex(TableEntry._ID);


        while(cursor.moveToNext()){

            muscleGroup.setID(cursor.getString(id_index));
            muscleGroup.setName(cursor.getString(name_index));

        }

        cursor.close();

        return muscleGroup;
    }

}
