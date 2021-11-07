package edu.uga.cs.statequiz;


/** POJO Class for quiz*/
public class Quiz {
    private int id;
    private String date;
    private int score;
    private CapitalQuizQuestion[] questions;

    public Quiz() {
        id = 0;
        date = "";
        score = 0;
    }

    public Quiz(String date, CapitalQuizQuestion[] q){
        this.date = date;
        this.score = -1;
        questions = q;
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

    public CapitalQuizQuestion[] getQuestions() {return questions;}

    public void setQuestions( CapitalQuizQuestion[] qids) { questions = qids;}

}
