package com.example.papersdashboard;

public class TeacherMidFinalStatus {

    String mids,finals,coursecode,pgroup,session;
    int tpsid,tid,midspaper,finalpaper;

    public TeacherMidFinalStatus(String mids, String finals, String coursecode, String pgroup, String session, int tpsid, int tid, int midspaper, int finalpaper) {
        this.mids = mids;
        this.finals = finals;
        this.coursecode = coursecode;
        this.pgroup = pgroup;
        this.session = session;
        this.tpsid = tpsid;
        this.tid = tid;
        this.midspaper = midspaper;
        this.finalpaper = finalpaper;
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

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getPgroup() {
        return pgroup;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getTpsid() {
        return tpsid;
    }

    public void setTpsid(int tpsid) {
        this.tpsid = tpsid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getMidspaper() {
        return midspaper;
    }

    public void setMidspaper(int midspaper) {
        this.midspaper = midspaper;
    }

    public int getFinalpaper() {
        return finalpaper;
    }

    public void setFinalpaper(int finalpaper) {
        this.finalpaper = finalpaper;
    }
}
