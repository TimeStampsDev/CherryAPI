package net.cherryflavor.api.mojang.resources;

import com.google.gson.annotations.SerializedName;
import net.cherryflavor.api.tools.TextFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class TimeStampName {

    @SerializedName("name")
    private String username;

    @SerializedName("changedToAt")
    private long changeToAt;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public TimeStampName(String username, long changeToAt) {
        this.username = username;
        this.changeToAt = changeToAt;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    public String getUsername() {
        return username;
    }

    public long getChangeToAt() {
        return changeToAt;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(TextFormat.addRightPadding("MMM/dd/yyyy '@' h:mm:ss", ' ',22));
        SimpleDateFormat timezone = new SimpleDateFormat("zz");
        return "Name: " + TextFormat.addRightPadding(username, ' ', 17) + " Time: " + (changeToAt == 0L ? "First Name" :  TextFormat.addRightPadding(sdf.format(changeToAt), ' ', 23) + timezone.format(changeToAt) ) ;
    }
}
