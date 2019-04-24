package com.cityofnewyork.project.data.network.interfaces;

import com.cityofnewyork.project.data.network.services.Models.SATDetailsResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpInterface {

    @GET("/resource/f9bf-2cp4.json")
    Call<List<SATDetailsResponse>> getSATDetails(
            @Query("$limit") String limit,
            @Query("$offset") String offset
    );


    @GET("/resource/f9bf-2cp4.json")
    Call<List<SATDetailsResponse>> getSATDetailsBySchoolName(
            @Query("school_name") String schoolName
    );
}
