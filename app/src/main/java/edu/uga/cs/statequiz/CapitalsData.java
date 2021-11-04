package edu.uga.cs.statequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.InputStream;
import java.io.InputStreamReader;

public class CapitalsData {
    public static final String DEBUG_TAG = "CapitalsData";
    private SQLiteDatabase db;
    private SQLiteOpenHelper capitalsDbHelper;
    private static final String[] capitalColumns = {
            CapitalsDBHelper.capitals_COLUMN_ID,
            CapitalsDBHelper.capitals_COLUMN_STATENAME,
            CapitalsDBHelper.capitals_COLUMN_CAPITAL,
            CapitalsDBHelper.capitals_COLUMN_CITY,
            CapitalsDBHelper.capitals_COLUMN_CITY1
    };

    private static final String[] quizColumns = {
            CapitalsDBHelper.quizzes_COLUMN_ID,
            CapitalsDBHelper.quizzes_DATE,
            CapitalsDBHelper.quizzes_SCORE,
            CapitalsDBHelper.quizzes_Q1,
            CapitalsDBHelper.quizzes_Q2,
            CapitalsDBHelper.quizzes_Q3,
            CapitalsDBHelper.quizzes_Q4,
            CapitalsDBHelper.quizzes_Q5,
            CapitalsDBHelper.quizzes_Q6
    };

    public CapitalsData( Context context ) {
        this.capitalsDbHelper = CapitalsDBHelper.getInstance( context );
    }

    public void open() {
        db = capitalsDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "CapitalsData: db open" );
    }

    // Close the database
    public void close() {
        if( capitalsDbHelper != null ) {
            capitalsDbHelper.close();
            Log.d(DEBUG_TAG, "CapitalsData: db closed");
        }
    }




}
