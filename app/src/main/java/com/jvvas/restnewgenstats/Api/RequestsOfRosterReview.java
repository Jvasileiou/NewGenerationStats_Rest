package com.jvvas.restnewgenstats.Api;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestsOfRosterReview {

    @POST("dbapi")
    Call<Void> postMatch(@Body MatchDTO matchDto );
}
