package com.example.papersdashboard;

public class MembersClass {
    int memberid;
    String firstname;
    String lastname;
    String membertype;

    public MembersClass(int memberid, String firstname, String lastname, String membertype) {
        this.memberid = memberid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.membertype = membertype;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
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

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }
}
