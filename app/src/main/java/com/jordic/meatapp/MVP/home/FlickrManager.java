package com.jordic.meatapp.MVP.home;

import android.content.pm.LauncherApps;
import android.widget.ImageView;

import com.jordic.meatapp.POJO.flickr.FlickrPOJO;
import com.jordic.meatapp.Retrofit.FlickrService;
import com.jordic.meatapp.Utils.ConnectionVariables;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.format;
import static android.R.id.input;

/**
 * Created by J on 28/01/2017.
 */

public class FlickrManager
{
    private String key;
    private String secret;
    private boolean cancelRequests;

    public FlickrManager(String key, String secret)
    {
        this.key=key;
        this.secret=secret;
        cancelRequests=false;
    }

    public void cancelRequests(boolean cancel)
    {
        cancelRequests=cancel;
    }

    public void getPhotoDataAsync(String photoID,Callback<FlickrPOJO> callback)
    {
        //I use retrofit to retrive the Data from the API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectionVariables.FLICKR_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrService flickrService = retrofit.create(FlickrService.class);

        try {
            //It calls the service in order to get the URLs of the Photo ID which I pass by parameter
            Call<FlickrPOJO> flickrCall = flickrService.getPhotoUrls(photoID,key,generateAPISignature(photoID));
            flickrCall.enqueue(callback);

            if(cancelRequests) flickrCall.cancel();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * It generates the MD5 String according to the parameters of the Flickr API
     * @param photoID PhotoID to get the info
     * @return The generated API Signature as MD5
     * @throws NoSuchAlgorithmException In case that the MessageDigest does not recognize the algorithm
     */
    public String generateAPISignature(String photoID) throws NoSuchAlgorithmException
    {
        StringBuilder stringToEncodeBuilder = new StringBuilder(secret);
        stringToEncodeBuilder.append("api_key").append(key);
        stringToEncodeBuilder.append("formatjson");
        stringToEncodeBuilder.append("methodflickr.photos.getSizes");
        stringToEncodeBuilder.append("nojsoncallback1");
        stringToEncodeBuilder.append("photo_id").append(photoID);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5sum = md5.digest(stringToEncodeBuilder.toString().getBytes());
        String md5String = String.format("%032X", new BigInteger(1, md5sum));

        return md5String;
    }
}
