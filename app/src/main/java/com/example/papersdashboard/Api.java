package com.example.papersdashboard;

import android.database.Observable;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("login")
    Call<Login> loginrecord(
            @Query("username") String email,
            @Query("pass") String pass
    );
    
    @GET("papers")
    Call<List<Papers>> getPapers(
            @Query("status") String status,
            @Query("id") int id
    );

    @GET("papersForTeacher")
    Call<List<Papers>> getPapersForTeacher(
            @Query("id") int id
    );

    @GET("paperViaPaperID")
    Call<Papers> getPaperViaPaperID(
            @Query("pid") int id
    );

    @GET("QuestionsForComments")
    Call<List<Questions>> getQuestionsForComments(
            @Query("pid") int id
    );

    @GET("Acourses")
    Call<List<Papers>> getACourses(
    );

    @GET("Dcourses")
    Call<List<Papers>> getDCourses(
    );

    @GET("MemberType")
    Call<MembersClass>getMemberType(
            @Query("memberid") int id
    );

    @GET("getCodeAndName")
    Call<Courses> getCodeAndName(
            @Query("cid")int id
    );

    @GET("getCodesAndNames")
    Call<List<Courses>> getCodesAndNames(
            @Query("tid")int id
    );

    @GET("Session")
    Call<List<Papers>> getSession(
            @Query("tid")int id
    );

    @GET("UserName")
    Call<List<MembersClass>> getUserName(
            @Query("id")int id
    );

    @GET("UpdateQuestion")
    Call<ResponseBody> getUpdateQuestionsAfterReview(
            @Query("questionid")int questionid,
            @Query("questiondata")String questiondata,
            @Query("comments")String comments,
            @Query("paperid")int paperid,
            @Query("status")String status,
            @Query("pstatus")String pstatus
    );
    //test only questions


    //// aby chutia post main form or field hoti hai baraway teri banda kuss b maray
    @FormUrlEncoded
    @POST("AddQuestion")
    Call<ResponseBody> AddQuestions(
            //@Field("questionid")int questionid,
            @Field("questionno") String questionno,
            @Field("questiondata")String questiondata,
            @Field("difficulty")String difficulty,
            @Field("image") String image,
            @Field("paperid")int paperid,
            @Field("status")String status,
            @Field("marks")int marks,
            @Field("comments")String comments
    );


    @GET("updatethestatusREVIEW")
    Call<ResponseBody> updatethestatusREVIEW(
            @Query("paperid")int paperid
    );


    @GET("getTeacherMidFinal")
    Call<List<TeacherMidFinalStatus>> getTeacherMidFinal(
            @Query("tid")int tid,
            @Query("ccode")String ccode
    );




    @PUT("UpdateQuestionD/{questionid}/")
    @FormUrlEncoded
    Call<ResponseBody> getUpdateQuestionD(@Path("questionid") int questionid,
                              @Field("qdata") String qdata,
                              @Field("pid") int pid,
                              @Field("qstatus") String qstatus,
                              @Field("comments") String comments,
                              @Field("pstatus") String pstatus
    );


//@GET("UpdateQuestionD")
//    Call<ResponseBody> getUpdateQuestionD(
//            @Query("qid")int qid,
//            @Query("qdata")String qdata,
//            @Query("pid")int pid,
//            @Query("qstatus")String qstatus,
//            @Query("comments")String comments,
//            @Query("pstatus")String pstatus
//    );
//


//    @FormUrlEncoded
//    @POST("UpdateQuestionD")
//    Call<ResponseBody> getUpdateQuestionD(
//            //@Field("questionid")int questionid,
//            @Field("questionno")String questionno,
//            @Field("questiondata")String questiondata,
//            @Field("difficulty")String difficulty,
//            @Field("image") String image,
//            @Field("paperid")int paperid,
//            @Field("status")String status,
//            @Field("marks")int marks,
//            @Field("comments")String comments,
//            @Field("st")String st
//    );

    @FormUrlEncoded
    @POST("UpdateQuestionP")
    Call<ResponseBody> getUpdateQuestionP(
            //@Field("questionid")int questionid,
            @Field("questionno")String questionno,
            @Field("questiondata")String questiondata,
            @Field("difficulty")String difficulty,
            @Field("image") String image,
            @Field("paperid")int paperid,
            @Field("status")String status,
            @Field("marks")int marks,
            @Field("comments")String comments,
            @Field("st")String st,
            @Field("tid")int tid
    );



    @FormUrlEncoded
    @POST("AddQuestion")
    Call<ResponseBody> CreatePaperObject(
            // @Field("questionid")int questionid,
            @Field("qd")QData qd,
            //@Field("status")String status,
            @Field("pid")int pid
    );
}
