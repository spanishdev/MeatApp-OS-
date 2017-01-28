package com.jordic.meatapp.MVP;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jordic.meatapp.MVP.home.FlickrManager;
import com.jordic.meatapp.POJO.flickr.FlickrError;
import com.jordic.meatapp.POJO.flickr.FlickrPOJO;
import com.jordic.meatapp.Retrofit.FlickrService;
import com.jordic.meatapp.Retrofit.MapsService;
import com.jordic.meatapp.Utils.ConnectionVariables;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by J on 28/01/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.View homeView;
    FlickrManager flickrManager; //It manages Flickr connections

    public HomePresenter(HomeContract.View view,String key, String secret)
    {
        homeView=view;
        flickrManager = new FlickrManager(key,secret);
    }

    /**
     * We get the image URL from the FlickR API, taking into account that we know the ID
     */
    @Override
    public void getPhotoDataAsync(String photoID, final WeakReference<ImageView> imageViewWeakReference) {

        flickrManager.getPhotoDataAsync(photoID,new Callback<FlickrPOJO>() {
            @Override
            public void onResponse(Call<FlickrPOJO> call, Response<FlickrPOJO> response) {
                if(response.isSuccessful())
                {
                    //If the Response is OK, we proceed to fill the ImageView
                    if(response.body().stat.equalsIgnoreCase("OK"))
                    {
                        //We get the Medium Size of the Image (index 5)
                        String imageSource = response.body().sizes.size.get(5).source;
                        homeView.loadImageFromURL(imageSource,imageViewWeakReference);
                    }
                    //Otherwise, there was an error (we print it on the Log)
                    else
                    {
                        Log.e("ERROR LOADING IMAGE",response.body().message);
                    }

                }
                else
                {
                    if (call.isCanceled()) {
                        Log.e("CANCELLED REQUEST", "request was cancelled");
                    }
                    else
                    {
                        try {
                            onFlickrError(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<FlickrPOJO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * It handles in case that there was an error on retriveing the Image Info on Flickr API. We just print it on the log.
     * @param responseString
     */
    private void onFlickrError(String responseString) {
        Gson gson = new GsonBuilder().create();
        try {
            FlickrError error = gson.fromJson(responseString, FlickrError.class);
            if(error!=null) Log.e("ERROR LOADING IMAGE",error.getMessage());
            else  Log.e("ERROR LOADING IMAGE","Unknown error");
        }
        catch(JsonSyntaxException e)
        {
            e.printStackTrace();
        }
    }

    public void cancelRequests(boolean cancel)
    {
        flickrManager.cancelRequests(cancel);
    }

}
