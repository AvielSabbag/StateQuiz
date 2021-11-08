package edu.uga.cs.statequiz;

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

            quizID = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            score = itemView.findViewById(R.id.score);

        }


    }
    @Override
    public QuizHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_review_quizzes, parent, false);
        return new QuizHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizHistoryHolder holder, int position) {
        Quiz quiz = quizList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + quiz);

        int quizNumOffset = quiz.getId();
        quizNumOffset++;

        holder.quizID.setText("Quiz #: " + String.valueOf(quizNumOffset));
        holder.date.setText("Date Taken: " + String.valueOf(quiz.getDate()));
        holder.score.setText("Score: " + String.valueOf(quiz.getScore()));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
