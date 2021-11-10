package edu.uga.cs.statequiz;

/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * QuizPagerAdapter to get each page of the quiz with the question and number
 */
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class QuizPagerAdapter extends FragmentPagerAdapter {
    private Quiz currentQuiz;
    private List<PlaceholderFragment> fragmentList;
    private Context context;
    public QuizPagerAdapter(FragmentManager fm, Quiz quiz, Context con) {
        super(fm);
        currentQuiz = quiz;
        Log.d("QuizPagerAdapter", "QuizPagerAdapter: quizID = " + currentQuiz.getId());
        context = con;
        fragmentList = new ArrayList<PlaceholderFragment>();
    }

    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("PagerAdapter", "getItem: " + position + " ; " + currentQuiz.getQuestions()[position].getState());
        for(int i = 0; i<currentQuiz.getQuestions().length; i++) {
            Log.d("PagerAdapter", "getItem: " + i + " ; " + currentQuiz.getQuestions()[i].getState());
        }
        Log.d("PagerAdapter", "getItem: quizID = " + currentQuiz.getId());
        //fragmentList.get(position).radioGroup.clearCheck();
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int questionNum = position + 1;
        return String.valueOf("Q " + questionNum);
    }

    public void createQuestionList() {
        List<PlaceholderFragment> questionList = new ArrayList<PlaceholderFragment>();
        for(int i = 0; i<6; i++) {
            Log.d("QuizPagerAdapter", "createQuestionList: created question " + i + " for quiz " + currentQuiz.getId());
            PlaceholderFragment placeholderFragment = PlaceholderFragment.newInstance(i + 1, currentQuiz, context);
            questionList.add(placeholderFragment);
        }
        fragmentList = questionList;
    }

}

