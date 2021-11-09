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
            CapitalsDBHelper.quizzes_Q6,
            CapitalsDBHelper.quizzes_A1,
            CapitalsDBHelper.quizzes_A2,
            CapitalsDBHelper.quizzes_A3,
            CapitalsDBHelper.quizzes_A4,
            CapitalsDBHelper.quizzes_A5,
            CapitalsDBHelper.quizzes_A6
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
        Log.d("CapitalsData", "retrieveAllQuestions: " + quizQuestion.get(0).getState());
        return quizQuestion;
    }

    //Store a new capital quiz question
    public CapitalQuizQuestion storeQuizQuestion(CapitalQuizQuestion capQuestion) {

        ContentValues values = new ContentValues();
        values.put(CapitalsDBHelper.capitals_COLUMN_STATENAME, capQuestion.getState());
        Log.d("CapitalsData", "storeQuizQuestion: " + capQuestion.getState());
        values.put(CapitalsDBHelper.capitals_COLUMN_CAPITAL, capQuestion.getAnswer());
        values.put(CapitalsDBHelper.capitals_COLUMN_CITY, capQuestion.getCity1());
        values.put(CapitalsDBHelper.capitals_COLUMN_CITY1, capQuestion.getCity2());

        long id = db.insert(CapitalsDBHelper.TABLE_CAPITALS, null, values);

        capQuestion.setId((int)id);

        return capQuestion;
    }

    public static Quiz storeQuiz(Quiz quiz) {

        String[] answers = quiz.getAnswers();
        ContentValues values = new ContentValues();
        values.put(CapitalsDBHelper.quizzes_DATE, quiz.getDate());
        values.put(CapitalsDBHelper.quizzes_SCORE, quiz.getScore());
        values.put(CapitalsDBHelper.quizzes_Q1, quiz.getQuestions()[0].getId());
        values.put(CapitalsDBHelper.quizzes_Q2, quiz.getQuestions()[1].getId());
        values.put(CapitalsDBHelper.quizzes_Q3, quiz.getQuestions()[2].getId());
        values.put(CapitalsDBHelper.quizzes_Q4, quiz.getQuestions()[3].getId());
        values.put(CapitalsDBHelper.quizzes_Q5, quiz.getQuestions()[4].getId());
        values.put(CapitalsDBHelper.quizzes_Q6, quiz.getQuestions()[5].getId());
        values.put(CapitalsDBHelper.quizzes_A1, answers[0]);
        values.put(CapitalsDBHelper.quizzes_A2, answers[1]);
        values.put(CapitalsDBHelper.quizzes_A3, answers[2]);
        values.put(CapitalsDBHelper.quizzes_A4, answers[3]);
        values.put(CapitalsDBHelper.quizzes_A5, answers[4]);
        values.put(CapitalsDBHelper.quizzes_A6, answers[5]);

        long id = db.insert(CapitalsDBHelper.TABLE_QUIZZES, null, values);

        quiz.setId((int)id);

        return quiz;
    }

    public void reportAnswer(int quizID, int answerNum, String answer) {
        ContentValues cv = new ContentValues();
        cv.put("a" + answerNum, answer);
        String quizIDS = Integer.toString(quizID);
        String[] sArray = {quizIDS};
        db.update(CapitalsDBHelper.TABLE_QUIZZES, cv ,CapitalsDBHelper.quizzes_COLUMN_ID + "= ?",sArray );
    }

    public int submitQuiz(Quiz quiz) {
        Log.d("CapitalsData", "submitQuiz: quiz submission started...");
        int quizID = quiz.getId();
        CapitalQuizQuestion[] questions = quiz.getQuestions();
        String[] answers = quiz.getAnswers();
        int quizScore = 0;
        Log.d("CapitalsData", "submitQuiz: questionsNum = " + questions.length);
        for (int i = 0; i<questions.length;i++) {
            Log.d("CapitalData", "submitQuiz: realAnswer = " + questions[i].getAnswer());
            Log.d("CapitalData", "submitQuiz: userAnswer = " + answers[i]);
            if(questions[i].getAnswer().equals(answers[i])) {
                quizScore = quizScore + 1;
            }
            Log.d("CapitalsData", "submitQuiz: quizScore = " + quizScore);
        }
        quiz.setScore(quizScore);
        ContentValues cv = new ContentValues();
        cv.put(CapitalsDBHelper.quizzes_SCORE, quiz.getScore());
        String quizIDS = Integer.toString(quizID);
        String[] sArray = {quizIDS};
        db.update(CapitalsDBHelper.TABLE_QUIZZES, cv ,CapitalsDBHelper.quizzes_COLUMN_ID + "= ?",sArray );
        return quiz.getScore();
    }
}
