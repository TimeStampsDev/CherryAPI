package net.cherryflavor.api.mojang;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import net.cherryflavor.api.exceptions.MojangAPIException;
import net.cherryflavor.api.mojang.resources.Status;
import net.cherryflavor.api.mojang.resources.TimeStampName;
import net.cherryflavor.api.tools.TextFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;;
import java.util.*;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class MojangAPI {

    private static JsonParser jsonParser = new JsonParser();
    private static Gson gsonParser = new Gson();

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * @param uuid
     * @return the current name of user
     * @throws MojangAPIException
     */
    public static String getCurrentName(UUID uuid) throws MojangAPIException {
        String username = null;
        try {
            TimeStampName[] nameHist = getUsernameHistory(uuid);
            username = nameHist[nameHist.length-1].getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (username == null) {
            throw new MojangAPIException("Could not retrieve username by uuid");
        }

        return username;
    }

    /**
     * @param uuid
     * @return name history of user
     * @throws IOException
     * @throws MojangAPIException
     */
    public static TimeStampName[] getUsernameHistory(UUID uuid) throws MojangAPIException, IOException {
        String validID = uuid.toString().replace("-","");

        String response = getRawJsonResponse(new URL("https://api.mojang.com/user/profiles/" + validID + "/names"));

        TimeStampName[] names = gsonParser.fromJson(response, TimeStampName[].class);
        return names;
    }

    /**
     * @return mojang service status list
     * @throws IOException
     */
    public static Status[] getServiceStatus() throws IOException {
        String url = "https://status.mojang.com/check";
        String response = getRawJsonResponse(new URL(url));

        Status[] statuses = gsonParser.fromJson(response, Status[].class);

        statuses[0].setService("minecraft.net");
        statuses[1].setService("session.minecraft.net");
        statuses[2].setService("account.mojang.com");
        statuses[3].setService("authserver.mojang.com");
        statuses[4].setService("sessionserver.mojang.com");
        statuses[5].setService("api.mojang.com");
        statuses[6].setService("textures.minecraft.net");
        statuses[7].setService("mojang.com");

        return statuses;
    }

    private static String getRawJsonResponse(URL u) throws IOException {
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setDoInput(true);
        con.setConnectTimeout(2000);
        con.setReadTimeout(2000);
        con.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = in.readLine();
        in.close();
        return response;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Prints out mojang service
     */
    public static void printServiceStatus() {
        System.out.println("  " + TextFormat.addRightPadding("",'=',55));
        System.out.println("  | " + TextFormat.addRightPadding("SERVICE:", ' ', 26) + TextFormat.addRightPadding("STATUS:", ' ',26) + "|");

        try {
            for (Status status : MojangAPI.getServiceStatus()) {
                System.out.println("  |" + status.toString().replace("Status:", "").replace("Service:","") + "|  ");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println("  " + TextFormat.addRightPadding("",'=',55));
    }

}
