package edu.uga.cs.statequiz;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DBConstructor extends AppCompatActivity{

    private CapitalsData capitalsData = new CapitalsData(this);
    private static List<CapitalQuizQuestion> capitalQuizQuestions;

    private void constructDatabase(String fileName) {
        try {
            InputStream in_s = getAssets().open(fileName);
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                CapitalQuizQuestion[] cqc = {new CapitalQuizQuestion(nextLine[0], nextLine[1], nextLine[2], nextLine[3])};
                CapitalDBWriter newWriter = new CapitalDBWriter();
                newWriter.doInBackground(cqc);
            }
        } catch (Exception e) {
            Log.e("DBConstruction", e.toString());
        }
    }

    public class CapitalDBWriter extends AsyncTask<CapitalQuizQuestion,
            CapitalQuizQuestion> {
        @Override
        protected CapitalQuizQuestion doInBackground(CapitalQuizQuestion... cqc) {
            capitalsData.storeQuizQuestion(cqc[0]);
            return cqc[0];
        }
        @Override
        protected void onPostExecute( CapitalQuizQuestion cqc) {
            capitalQuizQuestions.add(cqc);
        }
    }

    @Override
    protected void onResume() {
        Log.d("StateQuiz:", "DBConstructor: onResume()");
        if(capitalsData != null) {
            capitalsData.open();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("StateQuiz:", "DBConstructor: onPause()");
        if(capitalsData != null) {
            capitalsData.open();
        }
        super.onPause();
    }

}
