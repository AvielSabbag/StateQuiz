package edu.uga.cs.statequiz;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {

    private static final String DEBUG = "edu.uga.cs.statequiz";
    private static CapitalsData capitalsData;
    private static List<CapitalQuizQuestion> questionList;
    private static Context context;
    private static Quiz quizToTake;

    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SplashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashFragment newInstance(int questionNumber, List<CapitalQuizQuestion> qList, Context cont) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNumber);
        fragment.setArguments(args);
        questionList = qList;
        capitalsData = new CapitalsData(cont);
        context = cont;
        quizToTake = null;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fullSplash = inflater.inflate(R.layout.fragment_splash, null);
        Button quizButton = fullSplash.findViewById(R.id.button);

        quizButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String[] answers = {"a", "a", "a", "a", "a", "a"};
                createNewQuiz(questionList);
            }
        });
        int questionNum = getQuestionNum();
        Log.d(DEBUG, "onCreateView(SplashFragment): question - " + questionNum);
        if(questionNum >= 1) {
            quizButton.setText("Continue Quiz");
        }
        return fullSplash;
    }

    public int getQuestionNum() {

        if(getArguments() != null) {
            return getArguments().getInt("questionNum", 0);
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNewQuiz(List<CapitalQuizQuestion> allQuestions) {
        CapitalDBUnfinishedQuizReader dbQuizReader = new CapitalDBUnfinishedQuizReader();
        dbQuizReader.execute();
    }
    public void returnToOldQuiz(Quiz quiz) {
        QuizFragment quizFragment = QuizFragment.newInstance(quiz, context);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, quizFragment).commit();
    }

    public class CapitalDBQuizWriter extends AsyncTask<Quiz,
            Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... cqc) {
            capitalsData.storeQuiz(cqc[0]);
            return cqc[0];
        }
        @Override
        protected void onPostExecute( Quiz cqc) {
            Log.d("SplashFragment", "onPostExecute: new quiz " + cqc.getId() + " stored in DB" );
            QuizFragment quizFragment = QuizFragment.newInstance(cqc, context);
            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, quizFragment).commit();
        }
    }
    public class CapitalDBUnfinishedQuizReader extends AsyncTask<Quiz,
            Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... cqc) {
            return capitalsData.returnUnfinishedQuiz();
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute( Quiz cqc) {
            if(cqc == null) {
                Quiz newQuiz;
                Collections.shuffle(questionList);
                CapitalQuizQuestion[] randomQuestions = new CapitalQuizQuestion[6];
                String[] answers = {"a", "a", "a", "a", "a", "a"};
                for (int i = 0; i < 6; i++) {
                    Log.d("Splash", "createNewQuiz.questionAnswers: " + questionList.get(i).getState() + ";" + questionList.get(i).getAnswer() +
                            ";" + questionList.get(i).getCity1() + ";" + questionList.get(i).getCity2());
                    randomQuestions[i] = questionList.get(i);
                }
                newQuiz = new Quiz(LocalDateTime.now().toString(), randomQuestions, answers);
                quizToTake = newQuiz;
                Quiz[] currentQuiz = {quizToTake};
                CapitalDBQuizWriter quizWriter = new CapitalDBQuizWriter();
                quizWriter.execute(currentQuiz);
            } else {
                Quiz unfinishedQuiz = cqc;
                quizToTake = unfinishedQuiz;
                returnToOldQuiz(unfinishedQuiz);
            }

        }
    }


}