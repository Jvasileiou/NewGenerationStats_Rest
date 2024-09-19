package com.jvvas.restnewgenstats.Api;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestsOfLoginActivity {


    // Request : http://www.ngstats.gr/dbapi?match_id=
    @GET("dbapi")
    Call<MatchDTO> getMatchDTO(@Query("match_id") String key);


}
