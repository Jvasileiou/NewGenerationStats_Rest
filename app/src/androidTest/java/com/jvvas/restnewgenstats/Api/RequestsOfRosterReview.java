package com.jvvas.restnewgenstats.Api;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestsOfRosterReview {


//    @POST("dbapi")
//    Call<Void> postMatch( @Body MatchDTO matchDto );

    @POST("dbapi")
    Call<Void> postMatch(@Body MatchDTO matchDto );

//    // Update only the fields that was changed
//    @PATCH("dbapi/{league_id}")
//    Call<MatchDTO> patchMatch(@Path("league_id") String leagueId,@Body MatchDTO matchDto);

}
