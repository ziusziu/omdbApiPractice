package siu.example.com.omdbapi.omdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samsiu on 4/12/16.
 */
public interface omdbService {

    @GET("?")
    Call<Omdb> queryMovies(@Query("method") String method,
                           @Query("t") String title,
                           @Query("y") String year,
                           @Query("plot") String plot,
                           @Query("r") String format
                );
}
