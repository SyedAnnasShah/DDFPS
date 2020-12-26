package com.example.papersdashboard;

public class course {
    String course_id, course_datecreated, course_status, course_version, course_semester;

    public course(String course_id, String course_datecreated, String course_status, String course_version, String course_semester) {
        this.course_id = course_id;
        this.course_datecreated = course_datecreated;
        this.course_status = course_status;
        this.course_version = course_version;
        this.course_semester = course_semester;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_datecreated() {
        return course_datecreated;
    }

    public void setCourse_datecreated(String course_datecreated) {
        this.course_datecreated = course_datecreated;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getCourse_version() {
        return course_version;
    }

    public void setCourse_version(String course_version) {
        this.course_version = course_version;
    }

    public String getCourse_semester() {
        return course_semester;
    }

    public void setCourse_semester(String course_semester) {
        this.course_semester = course_semester;
    }
}
