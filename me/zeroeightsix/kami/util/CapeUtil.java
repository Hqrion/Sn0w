/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapeUtil {
    List<UUID> uuids = new ArrayList<UUID>();

    public CapeUtil() {
        try {
            String inputLine;
            URL pastebin = new URL("https://pastebin.com/raw/WRw8Waf7");
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            while ((inputLine = in.readLine()) != null) {
                this.uuids.add(UUID.fromString(inputLine));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean hasCape(UUID id) {
        return this.uuids.contains(id);
    }
}

