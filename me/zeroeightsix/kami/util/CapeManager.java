/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CapeManager {
    private static final String usersPastebin = "https://pastebin.com/MnSMqV4T";
    private static final String ogUsersPastebin = "https://pastebin.com/raw/9H0WwSkw";
    private static HashMap<String, Boolean> capeUsers;

    public CapeManager() {
        capeUsers = new HashMap();
    }

    public static boolean hasCape(UUID uuid) {
        return capeUsers.containsKey(CapeManager.sanitizeUuid(uuid));
    }

    public static boolean isOg(UUID uuid) {
        if (CapeManager.hasCape(uuid)) {
            return capeUsers.get(CapeManager.sanitizeUuid(uuid));
        }
        return false;
    }

    private static String sanitizeUuid(UUID uuid) {
        return CapeManager.sanitizeUuidString(uuid.toString());
    }

    private static String sanitizeUuidString(String uuidString) {
        return uuidString.replaceAll("-", "").toLowerCase();
    }

    public void initializeCapes() {
        this.getFromPastebin(usersPastebin).forEach(uuid -> capeUsers.put((String)uuid, false));
        this.getFromPastebin(ogUsersPastebin).forEach(uuid -> capeUsers.put((String)uuid, true));
    }

    private List<String> getFromPastebin(String urlString) {
        BufferedReader bufferedReader;
        URL url;
        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        ArrayList<String> uuidList = new ArrayList<String>();
        while (true) {
            String line;
            try {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }
            uuidList.add(CapeManager.sanitizeUuidString(line));
        }
        try {
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        return uuidList;
    }
}

