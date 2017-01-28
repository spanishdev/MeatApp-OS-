package com.jordic.meatapp.fragments;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jordic.meatapp.POJO.flickr.FlickrError;
import com.jordic.meatapp.POJO.maps.MapSearchPOJO;
import com.jordic.meatapp.POJO.maps.MapsError;
import com.jordic.meatapp.POJO.maps.Result;
import com.jordic.meatapp.R;
import com.jordic.meatapp.Retrofit.MapsService;
import com.jordic.meatapp.Utils.ConnectionVariables;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Jordi on 09/01/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    LocationManager locationManager;
    GoogleMap googleMap;
    final int RADIUS = 500; //Radius in meters

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, rootView);

        //Initialize locationManager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Initialize Maps Presenter and Google Maps
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        getCoordinates();
    }

    /**
     * Finds the LastKnownLocation, searches along all providers (GPS, NETWORK...)
     *
     * @return The most accurated location
     */
    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                continue;
            }
            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = location;
            }
        }
        return bestLocation;
    }

    /**
     * Gets the last known coordinates of the device and move the Camera to such location
     */
    public void getCoordinates() {
        if (locationManager != null) {
            Location location = getLastKnownLocation();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            LatLng positionCoordinates = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(positionCoordinates));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(positionCoordinates, 16.0f));

            searchShops(latitude, longitude, RADIUS);
        }
    }

    /**
     * It searches using Google Maps API the text "MEAT SHOP" within a radius with latitude and longitude as center
     * Once done, it adds markers to the Google Map
     *
     * @param latitude  Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radius    Radius (in meters)
     */
    private void searchShops(double latitude, double longitude, int radius) {

        String apiKey = getContext().getString(R.string.google_maps_key);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectionVariables.MAPS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MapsService mapsServices = retrofit.create(MapsService.class);

        Log.d("LANG LOT", latitude + "," + longitude);
        Log.d("MAPS API KEY",apiKey);

        Call<MapSearchPOJO> mapCall = mapsServices.getNearbyPlaces(apiKey,
                latitude + "," + longitude,
                radius);

        mapCall.enqueue(new Callback<MapSearchPOJO>() {
            @Override
            public void onResponse(Call<MapSearchPOJO> call, Response<MapSearchPOJO> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus().equalsIgnoreCase("ok"))
                    {
                        for (Result result : response.body().getResults()) {
                            com.jordic.meatapp.POJO.maps.Location resultLocation = result.getGeometry().getLocation();
                            LatLng resultCoordinates = new LatLng(resultLocation.getLat(), resultLocation.getLng());

                            googleMap.addMarker(new MarkerOptions().position(resultCoordinates).title(result.getName()));
                        }
                    }
                    else
                    {
                        printToast(response.body().getErrorMessage());
                        Log.e("REQUEST STATUS", response.body().getStatus());
                        Log.e("REQUEST MESSAGE", response.body().getErrorMessage());
                    }

                } else {
                    if (call.isCanceled()) {
                        Log.e("CANCELLED REQUEST", "request was cancelled");
                    } else {
                        onMapsError(response);
                    }

                }
            }

            @Override
            public void onFailure(Call<MapSearchPOJO> call, Throwable t) {
                printToast("An error has occurred: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void onMapsError(Response response) {
        Gson gson = new GsonBuilder().create();
        try {
            MapsError error = gson.fromJson(response.errorBody().string(), MapsError.class);
            if (error != null) printToast(error.getErrorMessage());
            else printToast("An unknown error has occurred");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void printToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
