package com.hasbrain.howfastareyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hasbrain.howfastareyou.Time.time;
import android.widget.Toast;
import android.util.Log;
/**
 * Created by sinhhx on 10/6/16.
 */
public class SQLdatabase extends SQLiteOpenHelper{
    public static final int database_version =1;
    public String CREATE_QUERY ="CREATE TABLE "+time.TABLE_NAME+" ("+time.TIMESCORE+" TEXT,"+time.TAPSCORE+" TEXT"+");";
    public SQLdatabase(Context context) {
        super(context,time.DATABASE_NAME,null,database_version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void storescore(SQLdatabase sql,String score,String tap )
    {
    SQLiteDatabase sqldatabase = sql.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(time.TIMESCORE,score);
        cv.put(time.TAPSCORE,tap);
        sqldatabase.insert(time.TABLE_NAME,null,cv);
    }

    public Cursor getScore(SQLdatabase sql){
        SQLiteDatabase sqlite= sql.getReadableDatabase();
        String[] colums = {time.TIMESCORE,time.TAPSCORE};
        Cursor CR = sqlite.query(time.TABLE_NAME,colums,null,null,null,null,null);
        return CR;

    }
//transaction
    public void clearscore(SQLdatabase sql){
        SQLiteDatabase sqldata = sql.getWritableDatabase();
        sqldata.delete(time.TABLE_NAME,null,null);

    }

}
