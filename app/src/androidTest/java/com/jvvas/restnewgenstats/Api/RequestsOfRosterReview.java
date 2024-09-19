package com.jvvas.restnewgenstats.Api;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestsOfRosterReview {


//    @POST("dbapi")
//    Call<Void> postMatch( @Body MatchDTO matchDto );

    @POST("dbapi")
    Call<Void> postMatch(@Body MatchDTO matchDto );

//    // Update only the fields that was changed
//    @PATCH("dbapi/{league_id}")
//    Call<MatchDTO> patchMatch(@Path("league_id") String leagueId,@Body MatchDTO matchDto);

}
