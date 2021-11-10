package edu.uga.cs.statequiz;
/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * Review quizzes fragment to show history of quizzes taken
 */
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewQuizzesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewQuizzesFragment extends Fragment {

    public static final String DEBUG_TAG = "ReviewQuizzes";

    private RecyclerView recyclerView;
    private QuizRecyclerAdapter recyclerAdapter;

    private CapitalsData capitalsData = null;
    private static List<Quiz> quizList;
    private static Context context;


    public ReviewQuizzesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReviewQuizzesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewQuizzesFragment newInstance(List<Quiz> list, Context con) {
        ReviewQuizzesFragment fragment = new ReviewQuizzesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        quizList = list;
        context = con;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d( DEBUG_TAG, "ReviewQuizzesFragment.onCreate()" );

        super.onCreate( savedInstanceState );

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fullView = inflater.inflate(R.layout.fragment_review_quizzes, container, false);
        recyclerView = fullView.findViewById(R.id.recyclerView1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        capitalsData = new CapitalsData(context);
        Log.d("ReviewQuizzesFragment", "onCreateView: QuizList: " + quizList.get(0).getId());
        recyclerAdapter = new QuizRecyclerAdapter(quizList);
        recyclerView.setAdapter(recyclerAdapter);
        Log.d("ReviewQuizzesFragment", "onCreateView: completed");
        return fullView;

    }

}