
package com.jordic.meatapp.POJO.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    private boolean openNow;
    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekdayText = null;

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public OpeningHours withOpenNow(boolean openNow) {
        this.openNow = openNow;
        return this;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

    public OpeningHours withWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
        return this;
    }

}
