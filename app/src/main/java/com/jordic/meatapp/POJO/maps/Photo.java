
package com.jordic.meatapp.POJO.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("height")
    @Expose
    private long height;
    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    private String photoReference;
    @SerializedName("width")
    @Expose
    private long width;

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public Photo withHeight(long height) {
        this.height = height;
        return this;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Photo withHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Photo withPhotoReference(String photoReference) {
        this.photoReference = photoReference;
        return this;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public Photo withWidth(long width) {
        this.width = width;
        return this;
    }

}
