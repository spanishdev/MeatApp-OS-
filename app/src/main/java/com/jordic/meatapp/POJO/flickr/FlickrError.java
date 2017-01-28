
package com.jordic.meatapp.POJO.flickr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrError {

    @SerializedName("stat")
    @Expose
    private String stat;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
