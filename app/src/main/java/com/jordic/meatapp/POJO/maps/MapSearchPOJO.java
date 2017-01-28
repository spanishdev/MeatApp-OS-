
package com.jordic.meatapp.POJO.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapSearchPOJO {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public MapSearchPOJO withHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public MapSearchPOJO withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MapSearchPOJO withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
