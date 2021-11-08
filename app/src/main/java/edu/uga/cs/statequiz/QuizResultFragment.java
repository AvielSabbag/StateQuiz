package edu.uga.cs.statequiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResultFragment extends Fragment {
    private static Quiz currentQuiz;
    TextView scoreText;
    Button returnHome;


    public QuizResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuizResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizResultFragment newInstance(Quiz quiz) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        currentQuiz = quiz;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fullView = inflater.inflate(R.layout.fragment_quiz_result, container, false);
        scoreText = fullView.findViewById(R.id.textView16);
        returnHome = fullView.findViewById(R.id.button2);
        scoreText.setText(Integer.toString(currentQuiz.getScore()));
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return home
            }
        });
        return fullView;
    }
}