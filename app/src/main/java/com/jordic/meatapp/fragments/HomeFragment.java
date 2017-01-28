package com.jordic.meatapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.jordic.meatapp.MVP.HomeContract;
import com.jordic.meatapp.MVP.HomePresenter;
import com.jordic.meatapp.R;
import com.jordic.meatapp.Utils.ConnectionVariables;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by J on 14/01/2017.
 */

public class HomeFragment extends Fragment implements HomeContract.View {

    HomePresenter presenter;

    @BindView(R.id.qualityImageView)
    ImageView qualityImageView;

    @BindView(R.id.serviceImageView)
    ImageView serviceImageView;

    @BindView(R.id.cateringImageView)
    ImageView cateringImageView;

    @BindView(R.id.delicatessenImageView)
    ImageView delicatessenImageView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        presenter=new HomePresenter(this,getString(R.string.flickr_api_key),getString(R.string.flickr_api_secret));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getPhotoDataAsync(ConnectionVariables.FLICKR_HAMBURGUER_PHOTO_ID,new WeakReference<>(qualityImageView));
        presenter.getPhotoDataAsync(ConnectionVariables.FLICKR_SERVICE_PHOTO_ID,new WeakReference<>(serviceImageView));
        presenter.getPhotoDataAsync(ConnectionVariables.FLICKR_RESTURANT_PHOTO_ID,new WeakReference<>(cateringImageView));
        presenter.getPhotoDataAsync(ConnectionVariables.FLICKR_DELICATESSEN_PHOTO_ID,new WeakReference<>(delicatessenImageView));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.cancelRequests(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.cancelRequests(true);
    }

    @Override
    public void printToast(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    /**
     * Load the image from URL
     * @param imageSource
     */
    @Override
    public void loadImageFromURL(String imageSource, final WeakReference<ImageView> imageViewWeakReference) {

        //We use Picasso to Load the Image asynchronously
        if(getContext()!=null) Picasso.with(getContext()).load(imageSource).into(imageViewWeakReference.get());
    }
}