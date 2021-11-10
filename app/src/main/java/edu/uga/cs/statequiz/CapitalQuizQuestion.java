package edu.uga.cs.statequiz;
/**
 * Aviel Sabbag & Kevan Kadkhodaian
 * POJO class for quiz quesiton
 */
public class CapitalQuizQuestion {

    private int id;
    private String state;
    private String city;
    private String city1;
    private String answer;

    public CapitalQuizQuestion(){
        this.id = -1;
        this.state = "/";
        this.answer = "/";
        this.city = "/";
        this.city1 = "/";
    }

    public CapitalQuizQuestion(String state, String answer, String city, String city1){
        this.id = -1;
        this.state = state;
        this.answer = answer;
        this.city = city;
        this.city1 = city1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity1() {
        return city;
    }

    public void setCity1(String city2) {
        this.city = city;
    }

    public String getCity2() {
        return city1;
    }

    public void setCity2(String city3) {
        this.city1 = city1;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
