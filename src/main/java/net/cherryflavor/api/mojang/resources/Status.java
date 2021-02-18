package net.cherryflavor.api.mojang.resources;

import com.google.gson.annotations.SerializedName;
import net.cherryflavor.api.tools.TextFormat;

public class Status {

    @SerializedName("keys")
    private String service;

    @SerializedName(value = "minecraft.net", alternate = {"session.minecraft.net","account.mojang.com","skins.mojang.com","sessionserver.mojang.com","authserver.mojang.com","api.mojang.com","textures.minecraft.net","mojang.com"})
    private String serviceStatus;

    public Status(String service, String status) {
        this.service = service;
        this.serviceStatus = status;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public String toString() {
        return "Service: " + TextFormat.addRightPadding(service,' ', 24) + " Status: " + TextFormat.addRightPadding(serviceStatus + " (" + (serviceStatus.equalsIgnoreCase("green") ? "no issues" : serviceStatus.equalsIgnoreCase("yellow") ? "some issues" : serviceStatus.equalsIgnoreCase("red") ? "service unavailable" : "unknown") + ")",' ', 26);
    }
}
