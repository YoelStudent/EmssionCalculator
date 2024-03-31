package com.example.emssioncalculator.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "History.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_NAME = "history";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_INDEX = "table_index";
    private static final String COLUMN_CO2 = "co2";
    private static final String COLUMN_TREES = "trees";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_NAME = "name";





    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INDEX + " TEXT, " +
                COLUMN_CO2 + " TEXT, " + COLUMN_TREES + " TEXT , " + COLUMN_DATE + " TEXT , " + COLUMN_NAME + " TEXT )";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(String Index, String CO2, String Trees, String Date, String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INDEX, Index);
        cv.put(COLUMN_CO2, CO2);
        cv.put(COLUMN_TREES, Trees);
        cv.put(COLUMN_DATE, Date);
        cv.put(COLUMN_NAME, Name);


        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }



    void updateData(String row_id, String Index, String CO2, String Trees, String Date, String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_INDEX, Index);
        cv.put(COLUMN_CO2, CO2);
        cv.put(COLUMN_TREES, Trees);
        cv.put(COLUMN_DATE, Date);
        cv.put(COLUMN_NAME, Name);



        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor findIdByDate(String date)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = '"+ date + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
//    Boolean CheckLogIn(String Email, String Pass)
//    {
//
//        String query = " SELECT EXISTS (SELECT * FROM " + TABLE_NAME + " WHERE Email = "+ Email+ " AND password = "+ Pass+ ")";
//        try (SQLiteDatabase db = this.getReadableDatabase())
//        {
//            Cursor cursor = db.rawQuery(query,null);
//            Boolean result = false;
//            if (cursor!=null){
//                if (cursor.moveToFirst()){
//                    int exists = cursor.getInt(0);
//                    result = exists==1;
//                }
//                cursor.close();
//            }
//            return result;
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public boolean checkExists(String Email)
//    {
//        String query = " SELECT EXISTS (SELECT * FROM " + TABLE_NAME + " WHERE Email = "+ Email+ ")";
//        try (SQLiteDatabase db = this.getReadableDatabase())
//        {
//            Cursor cursor = db.rawQuery(query,null);
//            Boolean result = false;
//            if (cursor!=null){
//                if (cursor.moveToFirst()){
//                    int exists = cursor.getInt(0);
//                    result = exists==1;
//                }
//                cursor.close();
//            }
//            return result;
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
