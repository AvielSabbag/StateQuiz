package edu.uga.cs.statequiz;
/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * QuizRecyclerAdapater class created to show past quizzes in the past quizzes page, QuizHistoryHolder
 * created within to inflate past_quiz_entry layout
 */
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHistoryHolder>{

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";
    private List<Quiz> quizList;

    public QuizRecyclerAdapter(List<Quiz> quizList){
        this.quizList = quizList;
    }

    class QuizHistoryHolder extends RecyclerView.ViewHolder{
        TextView quizID;
        TextView date;
        TextView score;

        public QuizHistoryHolder(@NonNull View itemView) {
            super(itemView);

            quizID = itemView.findViewById(R.id.quizNum);
            date = itemView.findViewById(R.id.quizDate);
            score = itemView.findViewById(R.id.quizScore);

        }


    }
    @Override
    public QuizHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_quiz_entry, parent, false);
        return new QuizHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizHistoryHolder holder, int position) {
        Quiz quiz = quizList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + quiz.getId());

        int quizNumOffset = quiz.getId();

        holder.quizID.setText("Quiz #: " + String.valueOf(quizNumOffset));
        holder.date.setText("Date Started: " + String.valueOf(quiz.getDate()));
        holder.score.setText("Score: " + String.valueOf(quiz.getScore()));

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

}
