package edu.uga.cs.statequiz;


import java.io.Serializable;

/** POJO Class for quiz*/
public class Quiz implements Serializable {
    private int id;
    private String date;
    private int score;
    private CapitalQuizQuestion[] questions;
    private String[] answers;

    public Quiz() {
        id = 0;
        date = "";
        score = 0;

    }

    public Quiz(String date, CapitalQuizQuestion[] q, String[] ans){
        this.date = date;
        this.score = -1;
        questions = q;
        answers = ans;
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

    public String[] getAnswers() {return answers;}

    public void setAnswers(String[] ans) {answers = ans;}



}
