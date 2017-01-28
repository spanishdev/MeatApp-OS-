
package com.jordic.meatapp.POJO.flickr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrPOJO {

    @SerializedName("sizes")
    @Expose
    public Sizes sizes;
    @SerializedName("stat")
    @Expose
    public String stat;
    @SerializedName("message")
    @Expose
    public String message;

}
