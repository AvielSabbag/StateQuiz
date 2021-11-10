package edu.uga.cs.statequiz;
/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * CapitalsDBHelper class to create the database with the specifc tables to handle the quizzes created
 * and quizzes completed by the user
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CapitalsDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "CapitalsDBHelper";

    private static final String DB_NAME = "capitals.db";
    private static final int DB_VERSION = 2;

    public static final String TABLE_CAPITALS = "capitals";
    public static final String capitals_COLUMN_ID = "_id";
    public static final String capitals_COLUMN_STATENAME = "name";
    public static final String capitals_COLUMN_CAPITAL = "capital";
    public static final String capitals_COLUMN_CITY = "city1";
    public static final String capitals_COLUMN_CITY1 = "city2";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String quizzes_COLUMN_ID = "_id";
    public static final String quizzes_DATE = "date";
    public static final String quizzes_SCORE = "score";
    public static final String quizzes_Q1 = "q1";
    public static final String quizzes_Q2 = "q2";
    public static final String quizzes_Q3 = "q3";
    public static final String quizzes_Q4 = "q4";
    public static final String quizzes_Q5 = "q5";
    public static final String quizzes_Q6 = "q6";
    public static final String quizzes_A1 = "a1";
    public static final String quizzes_A2 = "a2";
    public static final String quizzes_A3 = "a3";
    public static final String quizzes_A4 = "a4";
    public static final String quizzes_A5 = "a5";
    public static final String quizzes_A6 = "a6";

    private static CapitalsDBHelper helperInstance;

    private static final String CREATE_CAPITALS =
            "create table " + TABLE_CAPITALS + " ("
                    + capitals_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + capitals_COLUMN_STATENAME + " TEXT, "
                    + capitals_COLUMN_CAPITAL + " TEXT, "
                    + capitals_COLUMN_CITY + " TEXT, "
                    + capitals_COLUMN_CITY1 + " TEXT"
                    + ")";

    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + quizzes_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + quizzes_DATE + " TEXT, "
                    + quizzes_SCORE + " TEXT, "
                    + quizzes_Q1 + " TEXT, "
                    + quizzes_Q2 + " TEXT, "
                    + quizzes_Q3 + " TEXT, "
                    + quizzes_Q4 + " TEXT, "
                    + quizzes_Q5 + " TEXT, "
                    + quizzes_Q6 + " TEXT,"
                    + quizzes_A1 + " TEXT, "
                    + quizzes_A2 + " TEXT, "
                    + quizzes_A3 + " TEXT, "
                    + quizzes_A4 + " TEXT, "
                    + quizzes_A5 + " TEXT, "
                    + quizzes_A6 + " TEXT"+ ")";

    private CapitalsDBHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    public static synchronized CapitalsDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new CapitalsDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( CREATE_CAPITALS );
        sqLiteDatabase.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_CAPITALS + " created" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "drop table if exists " + TABLE_CAPITALS );
        sqLiteDatabase.execSQL( "drop table if exists " + TABLE_QUIZZES);
        onCreate( sqLiteDatabase );
        Log.d( DEBUG_TAG, "Table " + TABLE_CAPITALS + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }


}
