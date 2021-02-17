package net.cherryflavor.api.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cherryflavor.api.exceptions.MojangAPIException;
import net.cherryflavor.api.mojang.resources.TimeStampName;
import org.apache.commons.lang.ObjectUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MojangAPI {

    private static JsonParser jsonParser = new JsonParser();

    /**
     * @param uuid
     * @return the current name of user
     * @throws MojangAPIException
     */

    public static String getCurrentName(UUID uuid) throws MojangAPIException {
        String username = null;
        try {
            username = getUsernameHistory(uuid).get(uuid).get(0).getUsername();
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

    public static Map<UUID, List<TimeStampName>> getUsernameHistory(UUID uuid) throws MojangAPIException, IOException {
        String validID = uuid.toString().replace("-","");
        List<TimeStampName> nameHistory = new ArrayList<>();

        Map<UUID, List<TimeStampName>> usernameHistory = new HashMap<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format("https://api.mojang.com/user/profiles/%s/names", validID)).openConnection();
            connection.setRequestMethod("GET");

            JsonArray array = (JsonArray) jsonParser.parse(new InputStreamReader(connection.getInputStream()));

            for (int i = 0; i < array.size(); i++) {
                JsonObject response = (JsonObject) array.get(i);

                String username = response.getAsJsonObject("name").toString();
                long changeToAt = (Long.valueOf(response.getAsJsonObject("changedToAt").toString()) == null ? 0L : Long.valueOf(response.getAsJsonObject("changedToAt").toString()));

                if (username == null) {
                    continue;
                }

                String cause = response.getAsJsonObject("cause").toString();
                String errorMessage = response.getAsJsonObject("errorMessage").toString();

                if (cause != null && cause.length() > 0) {
                    throw new IllegalStateException(errorMessage);
                }

                nameHistory.add(new TimeStampName(username, changeToAt));
            }

            usernameHistory.put(uuid, nameHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (usernameHistory.isEmpty()) {
            throw new MojangAPIException("Couldn't retrieve any username history for user");
        }

        return usernameHistory;
    }

}
