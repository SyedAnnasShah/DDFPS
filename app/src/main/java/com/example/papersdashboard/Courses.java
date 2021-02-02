package com.example.papersdashboard;

public class Courses {
    int courseid,crs;
    String coursename , coursecode;

    public Courses(int courseid, String coursename, String coursecode, int crs) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.coursecode = coursecode;
        this.crs=crs;
    }

    public int getCrs() {
        return crs;
    }

    public void setCrs(int crs) {
        this.crs = crs;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }
}
