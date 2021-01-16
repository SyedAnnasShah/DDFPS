package com.example.papersdashboard;

public class Papers {
    int paperid, courseid, teacherid;
    String createddate, status, type , semester;

    public Papers(int paperid, int courseid, int teacherid, String createddate, String status, String type, String semester) {
        this.paperid = paperid;
        this.courseid = courseid;
        this.teacherid = teacherid;
        this.createddate = createddate;
        this.status = status;
        this.type = type;
        this.semester = semester;
    }

    public int getPaperid() {
        return paperid;
    }

    public void setPaperid(int paperid) {
        this.paperid = paperid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
