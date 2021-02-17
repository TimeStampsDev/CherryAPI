package net.cherryflavor.api.mojang.resources;

public class TimeStampName {

    private String username;
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
}
