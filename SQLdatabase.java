package com.hasbrain.howfastareyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hasbrain.howfastareyou.Time.time;
import android.util.Log;
/**
 * Created by sinhhx on 10/6/16.
 */
public class SQLdatabase extends SQLiteOpenHelper{
    public static final int database_version =1;
    public String CREATE_QUERY ="CREATE TABLE "+time.TABLE_NAME+" ("+time.TIMESCORE+" TEXT"+");";
    public SQLdatabase(Context context) {
        super(context,time.DATABASE_NAME,null,database_version);
        Log.d("SQLdatabase","good");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void storescore(SQLdatabase sql,String score )
    {
    SQLiteDatabase sqldatabase = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(time.TIMESCORE,score);
        sqldatabase.insert(time.TABLE_NAME,null,cv);
    }
}
