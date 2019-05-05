package com.example.rishabhvishwakarma.medifriend.medical;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


class DatabaseHolder {
    private static final String database_name = "Symptomator";
    private static final int database_version = 1;

    private final String symptomList_tableName = "SymptomList";
    private final String selectedSymptoms_tableName = "SelectedSymptoms";
    private final String emergencyNumbers_tableName = "emergencyNumbers";

    private static final String create_table_symptom_list = "create table if not exists SymptomList (Symptom text not null, BodyPart text not null, Sex text not null);";

    private static final String create_table_selected_symptoms = "create table if not exists SelectedSymptoms (Symptom text not null);";

    private static final String create_table_emergency_numbers = "create table if not exists emergencyNumbers(Country text not null, Code text not null, Number text not null)";
    private static DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase db;


    DatabaseHolder(Context context) {
        this.context = context;
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
    }

    void open() {
        db  = dbHelper.getWritableDatabase();
    }

    void close() {
        dbHelper.close();
    }

    void insertInSymptomListTable(String symptom, String bodyPart, String sex){
        ContentValues content = new ContentValues();
        content.put("Symptom", symptom);
        content.put("BodyPart", bodyPart);
        content.put("Sex", sex);
        db.insertOrThrow(symptomList_tableName, null, content);
    }

    void insertInEmergencyNumbersTable(String country, String code, String number){
        ContentValues content = new ContentValues();
        content.put("Country", country);
        content.put("Code", code);
        content.put("Number", number);
        db.insertOrThrow(emergencyNumbers_tableName, null, content);
    }

    void insertInSelectedSymptomsTable(String symptom) {
        removeFromSelectedSymptomsTable(symptom);

        ContentValues content = new ContentValues();
        content.put("Symptom", symptom);
        db.insertOrThrow(selectedSymptoms_tableName, null, content);
    }

    void removeFromSelectedSymptomsTable(String symptom){
        db.delete(selectedSymptoms_tableName, "Symptom='" + symptom + "'", null);
    }

    void resetSelectedSymptomsTable(){
        db.execSQL("DROP TABLE IF EXISTS " + selectedSymptoms_tableName);
        db.execSQL(create_table_selected_symptoms);
    }

    Cursor returnSymptoms(String sex, String bodyPart){
        Cursor cursor = null;
        if (sex.equalsIgnoreCase("male")){
            try{
                cursor = db.query(true, symptomList_tableName,
                        new String[]{"Symptom"},
                        "BodyPart = '" + bodyPart + "' AND (Sex = '"+ sex +"' OR sex = 'All')",
                        null, null, null, "Symptom ASC", null);
            }
            catch (SQLiteException e){
                if (e.getMessage().contains("no such table")){
                    Toast.makeText(context, "ERROR: Table doesn't exist!", Toast.LENGTH_SHORT).show();
                } else e.printStackTrace();
            }
        }
        else if (sex.equalsIgnoreCase("female")){
            try{
                cursor = db.query(true, symptomList_tableName,
                        new String[]{"Symptom"},
                        "BodyPart = '" + bodyPart + "' AND (Sex = '"+ sex +"' OR sex = 'All')",
                        null, null, null, "Symptom ASC", null);
            }
            catch (SQLiteException e){
                if (e.getMessage().contains("no such table")){
                    Toast.makeText(context, "ERROR: Table doesn't exist!", Toast.LENGTH_SHORT).show();
                } else e.printStackTrace();
            }
        } else cursor = null;

        return cursor;
    }


    Cursor returnEmergencyNumber(String countryCode){
        Cursor cursor = null;
        try{
            cursor = db.query(emergencyNumbers_tableName,
                    new String[]{"Country", "Number"},
                    "Code = '" + countryCode.toUpperCase() + "'",
                    null, null, null, null, null);
        }
        catch (SQLiteException e){
            if (e.getMessage().contains("no such table")){
                Toast.makeText(context, "ERROR: Table doesn't exist!", Toast.LENGTH_SHORT).show();
                // create table
                // re-run query, etc.
            } else e.printStackTrace();
        }
        return cursor;
    }

    Cursor returnSelectedSymptoms(){
        Cursor cursor = null;
        try {
            cursor  = db.query(true, selectedSymptoms_tableName, new String[]{"Symptom"}, null, null, null, null, "Symptom ASC", null);
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                Toast.makeText(context, "ERROR: Table doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }
        return cursor;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, database_name, null, database_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(create_table_symptom_list);
                db.execSQL(create_table_selected_symptoms);
                db.execSQL(create_table_emergency_numbers);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS SymptomList");
            db.execSQL("DROP TABLE IF EXISTS SelectedSymptoms");
            db.execSQL("DROP TABLE IF EXISTS emergencyNumbers");
            onCreate(db);
        }
    }
}