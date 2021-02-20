/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package me.zeroeightsix.kami.setting.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.SettingsRegister;
import me.zeroeightsix.kami.setting.converter.Convertable;

public class Configuration {
    public static JsonObject produceConfig() {
        return Configuration.produceConfig(SettingsRegister.ROOT);
    }

    private static JsonObject produceConfig(SettingsRegister register) {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, SettingsRegister> entry : register.registerHashMap.entrySet()) {
            object.add(entry.getKey(), (JsonElement)Configuration.produceConfig(entry.getValue()));
        }
        for (Map.Entry<String, Object> entry : register.settingHashMap.entrySet()) {
            Setting setting = (Setting)entry.getValue();
            if (!(setting instanceof Convertable)) continue;
            object.add(entry.getKey(), (JsonElement)setting.converter().convert(setting.getValue()));
        }
        return object;
    }

    public static void saveConfiguration(Path path) throws IOException {
        Configuration.saveConfiguration(Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void saveConfiguration(OutputStream stream) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson((JsonElement)Configuration.produceConfig());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
        writer.write(json);
        writer.close();
    }

    public static void loadConfiguration(Path path) throws IOException {
        InputStream stream = Files.newInputStream(path, new OpenOption[0]);
        Configuration.loadConfiguration(stream);
        stream.close();
    }

    public static void loadConfiguration(InputStream stream) {
        try {
            Configuration.loadConfiguration(new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject());
        }
        catch (IllegalStateException e) {
            KamiMod.LOGGER.error("KAMI Config malformed: resetting.");
            Configuration.loadConfiguration(new JsonObject());
        }
    }

    public static void loadConfiguration(JsonObject input) {
        Configuration.loadConfiguration(SettingsRegister.ROOT, input);
    }

    private static void loadConfiguration(SettingsRegister register, JsonObject input) {
        for (Map.Entry entry : input.entrySet()) {
            String key = (String)entry.getKey();
            JsonElement element = (JsonElement)entry.getValue();
            if (register.registerHashMap.containsKey(key)) {
                Configuration.loadConfiguration(register.subregister(key), element.getAsJsonObject());
                continue;
            }
            Setting setting = register.getSetting(key);
            if (setting == null) continue;
            setting.setValue(setting.converter().reverse().convert((Object)element));
        }
    }
}

