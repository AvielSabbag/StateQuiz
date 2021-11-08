package edu.uga.cs.statequiz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static CapitalsData capitalsData;
    private static Context context;
    private static Quiz currentQuiz;
    private static int questionIndex;
    CapitalQuizQuestion currentQuestion;
    TextView questionNum;
    TextView question;
    RadioButton ans1;
    RadioButton ans2;
    RadioButton ans3;
    Button submitQuiz;

    public static PlaceholderFragment newInstance(int sectionNumber, Quiz quiz, Context con) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt("questionIndex", sectionNumber);
        fragment.setArguments(args);
        currentQuiz = quiz;
        context = con;
        capitalsData = new CapitalsData(context);
        Log.d("PlaceHolderFragment", "newInstance: " + questionIndex);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        currentQuestion = currentQuiz.getQuestions()[getArguments().getInt("questionIndex") - 1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz_question, container, false);
            questionNum = rootView.findViewById(R.id.textView12);
            question = rootView.findViewById(R.id.textView13);
            ans1 = rootView.findViewById(R.id.radioButton);
            ans2 = rootView.findViewById(R.id.radioButton2);
            ans3 = rootView.findViewById(R.id.radioButton3);
            submitQuiz = rootView.findViewById(R.id.submitQuiz);
            //quizID, questionNum, answer
            String[] ans1Info = {Integer.toString(currentQuiz.getId()),Integer.toString(getArguments().getInt("questionIndex")), (String)ans1.getText()};
            ans1.setOnClickListener(new ReportAnswerListener(ans1Info));
            String[] ans2Info = {Integer.toString(currentQuiz.getId()),Integer.toString(getArguments().getInt("questionIndex")), (String)ans2.getText()};
            ans2.setOnClickListener(new ReportAnswerListener(ans2Info));
            String[] ans3Info = {Integer.toString(currentQuiz.getId()),Integer.toString(getArguments().getInt("questionIndex")), (String)ans3.getText()};
            ans3.setOnClickListener(new ReportAnswerListener(ans3Info));
            submitQuiz.setEnabled(false);
            submitQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CapitalDBQuizSubmitter newSubmitter = new CapitalDBQuizSubmitter();
                    newSubmitter.execute(currentQuiz);
                }
            });

        return rootView;
    }

    static boolean isComplete(Quiz currentQuiz) {
        String[] answers = currentQuiz.getAnswers();
        for(int i = 0; i< answers.length; i++) {
            if(answers[i] == "a") {
                return false;
            }
        }
        return true;
    }

    public class ReportAnswerListener implements View.OnClickListener {

        String[] answerInfo;

        public ReportAnswerListener(String[] ansInfo) {
            answerInfo = ansInfo;
        }
        @Override
        public void onClick(View v) {
            CapitalDBAnswerWriter answerWriter = new CapitalDBAnswerWriter();
            answerWriter.execute(answerInfo);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof Context) {
            Log.d("OnActivityCreated", "onActivityCreated: " + questionIndex);
            final String questionNumS = "Question " + (getArguments().getInt("questionIndex"));
            final String questionS = "What is the Capital of " + currentQuestion.getState() + "?";
            List<String> answerSet = new ArrayList<String>();
            answerSet.add((currentQuestion.getAnswer()));
            answerSet.add(currentQuestion.getCity1());
            answerSet.add(currentQuestion.getCity2());
            Collections.shuffle(answerSet);
            final String ans1S = answerSet.get(0);
            final String ans2S = answerSet.get(1);
            final String ans3S = answerSet.get(2);
            final TextView[] textViews = {questionNum, question};
            final RadioButton[] radioButtons = {ans1, ans2, ans3};
            final String[] strings = {questionNumS, questionS, ans1S, ans2S, ans3S, Integer.toString(getArguments().getInt("questionIndex"))};

            QuizFragment.loadView(textViews, radioButtons, strings, submitQuiz);
        } else {
            Log.d("PlaceholderFragment", "onActivityCreated: quizFragment not made");
        }
    }

    public class CapitalDBAnswerWriter extends AsyncTask<String,
            String> {
        @Override
        protected String doInBackground(String... cqc) {
            String[] answers = currentQuiz.getAnswers();
            answers[Integer.parseInt(cqc[1]) - 1] = cqc[2];
            capitalsData.reportAnswer(Integer.parseInt(cqc[0]),Integer.parseInt(cqc[1]), cqc[2] );
            Log.d("CapitalDBAnswerWriter", "doInBackground: question reported");
            return cqc[0];
        }
        @Override
        protected void onPostExecute(String cqc) {

        }
    }

    public class CapitalDBQuizSubmitter extends AsyncTask<Quiz,
            Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... cqc) {
            capitalsData.submitQuiz(cqc[0]);
            return cqc[0];
        }
        @Override
        protected void onPostExecute( Quiz cqc) {
            Log.d("SplashFragment", "onPostExecute: quiz "+ cqc.getId() + " stored in DB" );
            //inflate results screen
        }
    }

}