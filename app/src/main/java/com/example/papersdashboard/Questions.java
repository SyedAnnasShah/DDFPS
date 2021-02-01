package com.example.papersdashboard;

public class Questions {
    int paperquestionid, paperid;
    String questiondata, difficulty, image;

    public Questions(int paperquestionid, int paperid, String questiondata, String difficulty, String image) {
        this.paperquestionid = paperquestionid;
        this.paperid = paperid;
        this.questiondata = questiondata;
        this.difficulty = difficulty;
        this.image = image;
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
