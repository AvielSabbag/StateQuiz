package edu.uga.cs.statequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class CapitalsData {
    public static final String DEBUG_TAG = "CapitalsData";
    private static SQLiteDatabase db;
    private static SQLiteOpenHelper capitalsDbHelper;
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

    public static void open() {
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

    public List<CapitalQuizQuestion> retrieveAllQuestions() {
        ArrayList<CapitalQuizQuestion> quizQuestion = new ArrayList<CapitalQuizQuestion>();
        Cursor cursor = null;

        try {
            cursor = db.query(CapitalsDBHelper.TABLE_CAPITALS, capitalColumns,
                    null, null, null, null, null);
            //collect all quiz questions
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(CapitalsDBHelper.capitals_COLUMN_ID));
                    String stateName = cursor.getString(cursor.getColumnIndex(CapitalsDBHelper.capitals_COLUMN_STATENAME));
                    String capitalName = cursor.getString(cursor.getColumnIndex(CapitalsDBHelper.capitals_COLUMN_CAPITAL));
                    String cityName = cursor.getString(cursor.getColumnIndex(CapitalsDBHelper.capitals_COLUMN_CITY));
                    String city2Name = cursor.getString(cursor.getColumnIndex(CapitalsDBHelper.capitals_COLUMN_CITY1));

                    CapitalQuizQuestion capitalQuizQuestion =
                            new CapitalQuizQuestion(stateName, capitalName, cityName, city2Name);
                    capitalQuizQuestion.setId((int) id);
                    quizQuestion.add(capitalQuizQuestion);

                }
            }
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return quizQuestion;
    }

    //Store a new capital quiz question
    public CapitalQuizQuestion storeQuizQuestion(CapitalQuizQuestion capQuestion) {

        ContentValues values = new ContentValues();
        values.put(CapitalsDBHelper.capitals_COLUMN_STATENAME, capQuestion.getState());
        values.put(CapitalsDBHelper.capitals_COLUMN_CAPITAL, capQuestion.getCapital());
        values.put(CapitalsDBHelper.capitals_COLUMN_CITY, capQuestion.getCity());
        values.put(CapitalsDBHelper.capitals_COLUMN_CITY1, capQuestion.getCity1());

        long id = db.insert(CapitalsDBHelper.TABLE_CAPITALS, null, values);

        capQuestion.setId((int)id);

        return capQuestion;
    }
}
