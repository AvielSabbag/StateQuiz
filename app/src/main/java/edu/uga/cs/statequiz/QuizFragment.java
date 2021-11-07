package edu.uga.cs.statequiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {
    QuizPagerAdapter quizAdapter;
    TabLayout tabLayout;
    ViewPager qPager;

    public final static String questionNum = "Question ";

    public final static String question = "Which City is the Capital of ";



    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(Quiz quiz) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable("quiz", quiz);
        fragment.setArguments(args);
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
        View fullQuiz = inflater.inflate(R.layout.fragment_quiz, container, false);
        tabLayout = (TabLayout) fullQuiz.findViewById(R.id.tabLayout);

        quizAdapter = new QuizPagerAdapter(getParentFragmentManager());
        qPager = (ViewPager) fullQuiz.findViewById(R.id.qPager);
        qPager.setAdapter(quizAdapter);
        tabLayout.setTabsFromPagerAdapter(quizAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(qPager));
        qPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return fullQuiz;
    }
}