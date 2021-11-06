package edu.uga.cs.statequiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
            @Override
            public void onClick(View v) {
                QuizFragment quizFragment = QuizFragment.newInstance();
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
}