/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringJoiner;

public final class StreamReader {
    private final InputStream stream;

    public StreamReader(InputStream stream) {
        this.stream = stream;
    }

    public final String read() {
        StringJoiner joiner = new StringJoiner("\n");
        try {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(this.stream));
            while ((line = br.readLine()) != null) {
                joiner.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return joiner.toString();
    }
}

