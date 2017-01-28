package com.jordic.meatapp.MVP;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by J on 28/01/2017.
 */

public interface HomeContract
{
    interface View
    {
        void printToast(String message);

        void loadImageFromURL(String imageSource, WeakReference<ImageView> imageViewWeakReference);
    }

    interface Presenter
    {
        void getPhotoDataAsync(String photoID, final WeakReference<ImageView> imageViewWeakReference);
    }
}
