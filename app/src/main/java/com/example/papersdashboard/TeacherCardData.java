package com.example.papersdashboard;

public class TeacherCardData {
    String coursecode, coursename, mids, finals, midsdate, finalsdate, firstname, lastname,pgroup;
    int psid, tid, finalpaper, midspaper;

    public TeacherCardData(String coursecode, String coursename, String mids, String finals, String midsdate, String finalsdate, String firstname, String lastname, String pgroup, int psid, int tid, int finalpaper, int midspaper) {
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.mids = mids;
        this.finals = finals;
        this.midsdate = midsdate;
        this.finalsdate = finalsdate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pgroup = pgroup;
        this.psid = psid;
        this.tid = tid;
        this.finalpaper = finalpaper;
        this.midspaper = midspaper;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getMids() {
        return mids;
    }

    public void setMids(String mids) {
        this.mids = mids;
    }

    public String getFinals() {
        return finals;
    }

    public void setFinals(String finals) {
        this.finals = finals;
    }

    public String getMidsdate() {
        return midsdate;
    }

    public void setMidsdate(String midsdate) {
        this.midsdate = midsdate;
    }

    public String getFinalsdate() {
        return finalsdate;
    }

    public void setFinalsdate(String finalsdate) {
        this.finalsdate = finalsdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPgroup() {
        return pgroup;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public int getPsid() {
        return psid;
    }

    public void setPsid(int psid) {
        this.psid = psid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getFinalpaper() {
        return finalpaper;
    }

    public void setFinalpaper(int finalpaper) {
        this.finalpaper = finalpaper;
    }

    public int getMidspaper() {
        return midspaper;
    }

    public void setMidspaper(int midspaper) {
        this.midspaper = midspaper;
    }
}
