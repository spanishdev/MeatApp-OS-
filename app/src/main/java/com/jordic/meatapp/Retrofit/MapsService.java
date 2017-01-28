package com.jordic.meatapp.Retrofit;

import com.jordic.meatapp.POJO.maps.MapSearchPOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by J on 27/01/2017.
 */

public interface MapsService {

    @GET("maps/api/place/nearbysearch/json?sensor=true&name=meat+shop")
    Call<MapSearchPOJO> getNearbyPlaces(@Query("key") String key, @Query("location") String location, @Query("radius") int radius);

}
