package com.example.papersdashboard;

public class Questions {
    int paperquestionid, paperid, marks;
    String questiondata, difficulty, image, questionno;

    public Questions(int paperquestionid, int paperid, int marks, String questiondata, String difficulty, String image, String questionno) {
        this.paperquestionid = paperquestionid;
        this.paperid = paperid;
        this.marks = marks;
        this.questiondata = questiondata;
        this.difficulty = difficulty;
        this.image = image;
        this.questionno = questionno;
    }

    public String getQuestionno() {
        return questionno;
    }

    public void setQuestionno(String questionno) {
        this.questionno = questionno;
    }

    public int getPaperquestionid() {
        return paperquestionid;
    }

    public void setPaperquestionid(int paperquestionid) {
        this.paperquestionid = paperquestionid;
    }

    public int getPaperid() {
        return paperid;
    }

    public void setPaperid(int paperid) {
        this.paperid = paperid;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getQuestiondata() {
        return questiondata;
    }

    public void setQuestiondata(String questiondata) {
        this.questiondata = questiondata;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

