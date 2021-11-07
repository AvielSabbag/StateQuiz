package edu.uga.cs.statequiz;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
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
    public static SplashFragment newInstance(int questionNumber) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNumber);
        fragment.setArguments(args);
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

                QuizFragment quizFragment = QuizFragment.newInstance(createNewQuiz());
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, quizFragment).commit();
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
    public Quiz createNewQuiz() {
        Quiz newQuiz;
        CapitalsData capitalsData = new CapitalsData(getContext());
        List<CapitalQuizQuestion> allQuestions = capitalsData.retrieveAllQuestions();
        Collections.shuffle(allQuestions);
        CapitalQuizQuestion[] randomQuestions = new CapitalQuizQuestion[6];
        for(int i = 0; i<6; i++) {
            randomQuestions[i] = allQuestions.get(i);
        }
        newQuiz = new Quiz(LocalDateTime.now().toString(), randomQuestions);
        return newQuiz;

    }
}