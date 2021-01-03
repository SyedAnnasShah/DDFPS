package com.example.papersdashboard;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @GET("login")
    Call<Login> loginrecord(
            @Query("username") String email,
            @Query("pass") String pass
    );
    
    @GET("courses")
    Call<List<course>> getCourses(
            @Query("course_status") String status
    );

    @GET("Acourses")
    Call<List<course>> getACourses(
    );

    @GET("Dcourses")
    Call<List<course>> getDCourses(
    );

    @GET("MemberType")
    Call<MembersClass>getMemberType(
            @Query("memberid") int id
    );


}
