package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_COMPLETED};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<ListItem> getItems(){
        ArrayList<ListItem> items = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                boolean completed = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_COMPLETED)) == 1;
                items.add(new ListItem(id, name, completed));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long save(ListItem item) {
        if(item.isNew()) {
            return insert(item);
        } else {
            return update(item);
        }
    }

    private long insert(ListItem item){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, item.getName());
        cv.put(DatabaseHelper.COLUMN_COMPLETED, item.getCompleted() ? 1 : 0);

        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    private long update(ListItem item){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, item.getName());
        cv.put(DatabaseHelper.COLUMN_COMPLETED, item.getCompleted() ? 1 : 0);
        return database.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + item.getId(), null);
    }

    public long delete(ListItem item){
        return database.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID + "=" + item.getId(), null);
    }
}