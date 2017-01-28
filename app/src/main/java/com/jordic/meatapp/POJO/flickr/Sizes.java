
package com.jordic.meatapp.POJO.flickr;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes {

    @SerializedName("canblog")
    @Expose
    public long canblog;
    @SerializedName("canprint")
    @Expose
    public long canprint;
    @SerializedName("candownload")
    @Expose
    public long candownload;
    @SerializedName("size")
    @Expose
    public List<Size> size = null;

}
