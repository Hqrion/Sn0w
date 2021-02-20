/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Converter
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package me.zeroeightsix.kami;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.CommandManager;
import me.zeroeightsix.kami.event.ForgeEventProcessor;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.SettingsRegister;
import me.zeroeightsix.kami.setting.config.Configuration;
import me.zeroeightsix.kami.util.CapeManager;
import me.zeroeightsix.kami.util.CapeUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.GuiManager;
import me.zeroeightsix.kami.util.LagCompensator;
import me.zeroeightsix.kami.util.ReflectionHelper;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid="hephaestus", name="Sn0w", version="V3")
public class KamiMod {
    public static final String MODID = "hephaestus";
    public static final String MODNAME = "Sn0w";
    public static final String MODVER = "V3";
    public static final String NAME_UNICODE = "Sn0w";
    public static final Logger LOGGER = LogManager.getLogger((String)"Hephaestus");
    public static final EventBus EVENT_BUS = new EventManager();
    private static final String CONFIG_NAME_DEFAULT = "Hephaestus_Config.json";
    @Mod.Instance
    private static KamiMod INSTANCE;
    public GuiManager guiManager;
    public KamiGUI kamiGUI;
    public CommandManager commandManager;
    public CapeManager capeManager;
    public CapeUtil capeUtils;
    private Setting<JsonObject> guiStateSetting = Settings.custom("gui", new JsonObject(), (Converter)new Converter<JsonObject, JsonObject>(){

        protected JsonObject doForward(JsonObject jsonObject) {
            return jsonObject;
        }

        protected JsonObject doBackward(JsonObject jsonObject) {
            return jsonObject;
        }
    }).buildAndRegister("");

    public static String getConfigName() {
        Path config = Paths.get("Hephaestus_LastConfig.txt", new String[0]);
        String kamiConfigName = CONFIG_NAME_DEFAULT;
        try (BufferedReader reader = Files.newBufferedReader(config);){
            kamiConfigName = reader.readLine();
            if (!KamiMod.isFilenameValid(kamiConfigName)) {
                kamiConfigName = CONFIG_NAME_DEFAULT;
            }
        }
        catch (NoSuchFileException e) {
            try (BufferedWriter writer = Files.newBufferedWriter(config, new OpenOption[0]);){
                writer.write(CONFIG_NAME_DEFAULT);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return kamiConfigName;
    }

    public static void loadConfiguration() {
        try {
            KamiMod.loadConfigurationUnsafe();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfigurationUnsafe() throws IOException {
        String kamiConfigName = KamiMod.getConfigName();
        Path kamiConfig = Paths.get(kamiConfigName, new String[0]);
        if (!Files.exists(kamiConfig, new LinkOption[0])) {
            return;
        }
        Configuration.loadConfiguration(kamiConfig);
        JsonObject gui = KamiMod.INSTANCE.guiStateSetting.getValue();
        for (Map.Entry entry : gui.entrySet()) {
            Optional<Component> optional = KamiMod.INSTANCE.kamiGUI.getChildren().stream().filter(component -> component instanceof Frame).filter(component -> ((Frame)component).getTitle().equals(entry.getKey())).findFirst();
            if (optional.isPresent()) {
                JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
                Frame frame = (Frame)optional.get();
                frame.setX(object.get("x").getAsInt());
                frame.setY(object.get("y").getAsInt());
                Docking docking = Docking.values()[object.get("docking").getAsInt()];
                if (docking.isLeft()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.LEFT);
                } else if (docking.isRight()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.RIGHT);
                } else if (docking.isCenterVertical()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.CENTER);
                }
                frame.setDocking(docking);
                frame.setMinimized(object.get("minimized").getAsBoolean());
                frame.setPinned(object.get("pinned").getAsBoolean());
                continue;
            }
            System.err.println("Found GUI config entry for " + (String)entry.getKey() + ", but found no frame with that name");
        }
        KamiMod.getInstance().getKamiGUI().getChildren().stream().filter(component -> component instanceof Frame && ((Frame)component).isPinneable() && component.isVisible()).forEach(component -> component.setOpacity(0.0f));
    }

    public static void saveConfiguration() {
        try {
            KamiMod.saveConfigurationUnsafe();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfigurationUnsafe() throws IOException {
        JsonObject object = new JsonObject();
        KamiMod.INSTANCE.kamiGUI.getChildren().stream().filter(component -> component instanceof Frame).map(component -> (Frame)component).forEach(frame -> {
            JsonObject frameObject = new JsonObject();
            frameObject.add("x", (JsonElement)new JsonPrimitive((Number)frame.getX()));
            frameObject.add("y", (JsonElement)new JsonPrimitive((Number)frame.getY()));
            frameObject.add("docking", (JsonElement)new JsonPrimitive((Number)Arrays.asList(Docking.values()).indexOf((Object)frame.getDocking())));
            frameObject.add("minimized", (JsonElement)new JsonPrimitive(Boolean.valueOf(frame.isMinimized())));
            frameObject.add("pinned", (JsonElement)new JsonPrimitive(Boolean.valueOf(frame.isPinned())));
            object.add(frame.getTitle(), (JsonElement)frameObject);
        });
        KamiMod.INSTANCE.guiStateSetting.setValue(object);
        Path outputFile = Paths.get(KamiMod.getConfigName(), new String[0]);
        if (!Files.exists(outputFile, new LinkOption[0])) {
            Files.createFile(outputFile, new FileAttribute[0]);
        }
        Configuration.saveConfiguration(outputFile);
        ModuleManager.getModules().forEach(Module::destroy);
    }

    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public static KamiMod getInstance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("\n\nInitializing Sn0w V3");
        ReflectionHelper.init();
        ModuleManager.initialize();
        ModuleManager.getModules().stream().filter(module -> module.alwaysListening).forEach(EVENT_BUS::subscribe);
        MinecraftForge.EVENT_BUS.register((Object)new ForgeEventProcessor());
        LagCompensator.INSTANCE = new LagCompensator();
        Wrapper.init();
        this.guiManager = new GuiManager();
        this.kamiGUI = new KamiGUI();
        this.kamiGUI.initializeGUI();
        this.commandManager = new CommandManager();
        this.capeManager = new CapeManager();
        this.capeManager.initializeCapes();
        this.capeUtils = new CapeUtil();
        Friends.initFriends();
        SettingsRegister.register("commandPrefix", Command.commandPrefix);
        KamiMod.loadConfiguration();
        LOGGER.info("Settings loaded");
        ModuleManager.updateLookup();
        if (ModuleManager.getModuleByName("FakePlayer").isEnabled()) {
            ModuleManager.getModuleByName("FakePlayer").disable();
        }
        if (ModuleManager.getModuleByName("Holefill2").isEnabled()) {
            ModuleManager.getModuleByName("Holefill2").disable();
        }
        if (ModuleManager.getModuleByName("Hacker Piston").isEnabled()) {
            ModuleManager.getModuleByName("Hacker Piston").disable();
        }
        if (ModuleManager.getModuleByName("Hopper Auto32k").isEnabled()) {
            ModuleManager.getModuleByName("Hopper Auto32k").disable();
        }
        ModuleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);
        if (ModuleManager.getModuleByName("GUI").isDisabled()) {
            ModuleManager.getModuleByName("GUI").enable();
        }
        LOGGER.info("Sn0w initialized!\n");
    }

    public KamiGUI getKamiGUI() {
        return this.kamiGUI;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}

