package edu.uga.cs.statequiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    Quiz currentQuiz;
    CapitalQuizQuestion currentQuestion;
    TextView questionNum;
    TextView question;
    RadioButton ans1;
    RadioButton ans2;
    RadioButton ans3;

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            currentQuiz = (Quiz) getParentFragment().getArguments().getSerializable("quiz");
            currentQuestion = currentQuiz.getQuestions()[getArguments().getInt(ARG_SECTION_NUMBER)];
        } else {
            currentQuiz = null;
        }
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
            questionNum.setText(getArguments().getInt(ARG_SECTION_NUMBER));
            question.setText(currentQuestion.getState());
            List<String> answerSet = new ArrayList<String>();
            answerSet.add((currentQuestion.getAnswer()));
            answerSet.add(currentQuestion.getCity1());
            answerSet.add(currentQuestion.getCity2());
            Collections.shuffle(answerSet);
            ans1.setText(answerSet.get(0));
            ans2.setText(answerSet.get(1));
            ans3.setText(answerSet.get(2));

        return rootView;
    }

}