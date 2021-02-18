package net.cherryflavor.api.mojang.resources;

import com.google.gson.annotations.SerializedName;
import net.cherryflavor.api.tools.TextFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampName {

    @SerializedName("name")
    private String username;

    @SerializedName("changedToAt")
    private long changeToAt;

    public TimeStampName(String username, long changeToAt) {
        this.username = username;
        this.changeToAt = changeToAt;
    }

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
