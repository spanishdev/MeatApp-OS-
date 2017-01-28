package com.jordic.meatapp.Retrofit;

import com.jordic.meatapp.POJO.flickr.FlickrPOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by J on 27/01/2017.
 */

public interface FlickrService {

    @GET("services/rest/?method=flickr.photos.getSizes&format=json&nojsoncallback=1")
    Call<FlickrPOJO> getPhotoUrls(@Query("photo_id") String photo_id, @Query("api_key") String api_key, @Query("api_sig") String api_sig);


}
