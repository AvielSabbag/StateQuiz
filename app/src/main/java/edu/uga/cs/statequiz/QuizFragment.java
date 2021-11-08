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
    private static Quiz currentQuiz;
    private static Context context;

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
    public static QuizFragment newInstance(Quiz quiz, Context con) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable("quiz", quiz);
        fragment.setArguments(args);
        currentQuiz = quiz;
        context = con;
        return fragment;
    }
    public static void loadView(TextView[] textViews, RadioButton[] buttons, String[] strings, Button subButton) {
        Log.d("QuizFragment", "loadView: question:" + strings[0]);
        if(Integer.parseInt(strings[5]) == 6) {
            subButton.setVisibility(View.VISIBLE);
        } else {
            subButton.setVisibility(View.GONE);
        }
        textViews[0].setText(strings[0]);
        textViews[1].setText(strings[1]);
        buttons[0].setText(strings[2]);
        buttons[1].setText(strings[3]);
        buttons[2].setText(strings[4]);
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

        quizAdapter = new QuizPagerAdapter(getParentFragmentManager(), currentQuiz, context);
        qPager = (ViewPager) fullQuiz.findViewById(R.id.qPager);
        qPager.setAdapter(quizAdapter);
        tabLayout.setTabsFromPagerAdapter(quizAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                qPager.setCurrentItem((tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        qPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return fullQuiz;
    }
}