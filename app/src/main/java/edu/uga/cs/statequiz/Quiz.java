package edu.uga.cs.statequiz;


/** POJO Class for quiz*/
public class Quiz {
    private int id;
    private String date;
    private int score;
    private CapitalQuizQuestion[] questions;
    private boolean isFinished;

    public Quiz() {
        id = 0;
        date = "";
        score = 0;
        isFinished = false;
    }

    public Quiz(String date, int score, CapitalQuizQuestion[] q){
        this.date = date;
        this.score = score;
        questions = q;
        isFinished = false;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public CapitalQuizQuestion[] getQuestionIds() {return questions;}

    public void setQuestionIds( CapitalQuizQuestion[] qids) { questions = qids;}

}
