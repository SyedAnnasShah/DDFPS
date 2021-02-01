package com.example.papersdashboard;

public class CourseAllocation {
    int courseallocationid , teacherid;
    String Semester , Session , section, coursecode;

    public CourseAllocation(int courseallocationid, int teacherid, String semester, String session, String section, String coursecode) {
        this.courseallocationid = courseallocationid;
        this.teacherid = teacherid;
        Semester = semester;
        Session = session;
        this.section = section;
        this.coursecode = coursecode;
    }

    public int getCourseallocationid() {
        return courseallocationid;
    }

    public void setCourseallocationid(int courseallocationid) {
        this.courseallocationid = courseallocationid;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }
}
