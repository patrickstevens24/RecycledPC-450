package pws24.uw.tacoma.edu.recycledpc.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pws24.uw.tacoma.edu.recycledpc.R;

/**
 * Created by Arthur on 3/2/2017.
 */

public class NameDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Name.db";

    private CourseDBHelper mCourseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public NameDB(Context context) {
        mCourseDBHelper = new CourseDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mCourseDBHelper.getWritableDatabase();
    }

    public boolean insertName(int id, String email, String firstName, String lastName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);


        long rowId = mSQLiteDatabase.insert("Name", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public int getID(){
        Cursor c = mSQLiteDatabase.query("name", null, null, null, null, null, null);
        c.moveToFirst();

        return c.getInt(0);
    }

    public String getFName() {
        Cursor c = mSQLiteDatabase.query("name", null, null, null, null, null, null);
        c.moveToFirst();

        return c.getString(2);
    }

    public String getLName() {
        Cursor c = mSQLiteDatabase.query("name", null, null, null, null, null, null);
        c.moveToFirst();

        return c.getString(3);
    }

    public String getEmail() {
        Cursor c = mSQLiteDatabase.query("name", null, null, null, null, null, null);
        c.moveToFirst();

        return c.getString(1);
    }

    class CourseDBHelper extends SQLiteOpenHelper {
        private final String CREATE_NAME_SQL;

        private final String DROP_NAME_SQL;

        public CourseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_NAME_SQL = context.getString(R.string.CREATE_NAME_SQL);
            DROP_NAME_SQL = context.getString(R.string.DROP_NAME_SQL);

        }



        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_NAME_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_NAME_SQL);
            onCreate(sqLiteDatabase);
        }




    }

    /**
     * Delete all the data from the COURSE_TABLE
     */
    public void deleteCourses() {
        mSQLiteDatabase.delete("Name", null, null);
    }


}
