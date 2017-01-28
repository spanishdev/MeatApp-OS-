
package com.jordic.meatapp.POJO.flickr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Size {

    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("width")
    @Expose
    public String width;
    @SerializedName("height")
    @Expose
    public String height;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("media")
    @Expose
    public String media;

}
