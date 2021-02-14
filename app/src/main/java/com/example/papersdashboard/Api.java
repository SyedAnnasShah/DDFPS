package com.example.papersdashboard;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST("AddQuestion")
    Call<ResponseBody> AddQuestion(
           // @Field("questionid")int questionid,
            @Field("questionno")String questionno,
            @Field("questiondata")String questiondata,
            @Field("difficulty")String difficulty,
            @Field("image") String image,
            @Field("paperid")int paperid,
            //@Field("status")String status,
            @Field("marks")int marks
    );
}
