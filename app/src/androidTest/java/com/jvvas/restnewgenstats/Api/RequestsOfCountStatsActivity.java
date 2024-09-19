package com.jvvas.restnewgenstats.Api;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RequestsOfCountStatsActivity {

    @POST("dbapi")
    Call<Void> postMatch(@Body MatchDTO matchDto);

    @POST("dbapi")
    Call<Void> postSomeFields(@Body Map<String, Object> fields);
}
