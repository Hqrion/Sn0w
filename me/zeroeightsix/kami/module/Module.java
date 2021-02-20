//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Converter
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.module;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.builder.SettingBuilder;
import me.zeroeightsix.kami.util.Bind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Module {
    private final String originalName = this.getAnnotation().name();
    private final Setting<String> name = this.register(Settings.s("Name", this.originalName));
    private final String description = this.getAnnotation().description();
    private final Category category = this.getAnnotation().category();
    private Setting<Bind> bind = this.register(Settings.custom("Bind", Bind.none(), new BindConverter()).build());
    private Setting<Boolean> enabled = this.register(Settings.booleanBuilder("Enabled").withVisibility(aBoolean -> false).withValue(false).build());
    private Setting<Boolean> drawn = this.register(Settings.booleanBuilder("Drawn").withVisibility(aBoolean -> false).withValue(true).build());
    public boolean alwaysListening;
    protected static final Minecraft mc = Minecraft.getMinecraft();
    public List<Setting> settingList = new ArrayList<Setting>();

    public Module() {
        this.alwaysListening = this.getAnnotation().alwaysListening();
        this.registerAll(this.bind, this.enabled);
    }

    private Info getAnnotation() {
        if (this.getClass().isAnnotationPresent(Info.class)) {
            return this.getClass().getAnnotation(Info.class);
        }
        throw new IllegalStateException("No Annotation on class " + this.getClass().getCanonicalName() + "!");
    }

    public void onUpdate() {
    }

    public static boolean nullCheck() {
        return Module.mc.player == null;
    }

    public static boolean fullNullCheck() {
        return Module.mc.player == null || Module.mc.world == null;
    }

    public void onRender() {
    }

    public void onWorldRender(RenderEvent event) {
    }

    public Bind getBind() {
        return this.bind.getValue();
    }

    public String getBindName() {
        return this.bind.getValue().toString();
    }

    public void setName(String name) {
        this.name.setValue(name);
        ModuleManager.updateLookup();
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.enabled.getValue();
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }

    public void enable() {
        this.enabled.setValue(true);
        this.onEnable();
        if (!this.alwaysListening) {
            KamiMod.EVENT_BUS.subscribe((Object)this);
        }
    }

    public void disable() {
        this.enabled.setValue(false);
        this.onDisable();
        if (!this.alwaysListening) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
        }
    }

    public void draw() {
        this.drawn.setValue(true);
    }

    public void dontDraw() {
        this.drawn.setValue(false);
    }

    public boolean isDisabled() {
        return !this.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        boolean prev = this.enabled.getValue();
        if (prev != enabled) {
            if (enabled) {
                this.enable();
            } else {
                this.disable();
            }
        }
    }

    public void setDrawn(boolean drawn) {
        boolean prev = this.drawn.getValue();
        if (prev != drawn) {
            if (drawn) {
                this.draw();
            } else {
                this.dontDraw();
            }
        }
    }

    public String getHudInfo() {
        return null;
    }

    protected final void setAlwaysListening(boolean alwaysListening) {
        this.alwaysListening = alwaysListening;
        if (alwaysListening) {
            KamiMod.EVENT_BUS.subscribe((Object)this);
        }
        if (!alwaysListening && this.isDisabled()) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
        }
    }

    public void destroy() {
    }

    protected void registerAll(Setting ... settings) {
        for (Setting setting : settings) {
            this.register(setting);
        }
    }

    protected <T> Setting<T> register(Setting<T> setting) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        this.settingList.add(setting);
        return SettingBuilder.register(setting, "modules." + this.originalName);
    }

    protected <T> Setting<T> register(SettingBuilder<T> builder) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        Setting<T> setting = builder.buildAndRegister("modules." + this.name);
        this.settingList.add(setting);
        return setting;
    }

    private class BindConverter
    extends Converter<Bind, JsonElement> {
        private BindConverter() {
        }

        protected JsonElement doForward(Bind bind) {
            return new JsonPrimitive(bind.toString());
        }

        protected Bind doBackward(JsonElement jsonElement) {
            String s = jsonElement.getAsString();
            if (s.equalsIgnoreCase("None")) {
                return Bind.none();
            }
            int key = -1;
            try {
                key = Keyboard.getKeyIndex((String)s.toUpperCase());
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (key == 0) {
                return Bind.none();
            }
            return new Bind(key);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Info {
        public String name();

        public String description() default "Descriptionless";

        public Category category();

        public boolean alwaysListening() default false;
    }

    public static enum Category {
        COMBAT("Combat", false),
        EXPLOITS("Exploits", false),
        RENDER("Render", false),
        MISC("Misc", false),
        PLAYER("Player", false),
        MOVEMENT("Movement", false),
        CHAT("Chat", false),
        DEV("Dev", false),
        GUI("Gui", false),
        HUD("HUD", false),
        HIDDEN("Hidden", true);

        boolean hidden;
        String name;

        private Category(String name, boolean hidden) {
            this.name = name;
            this.hidden = hidden;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public String getName() {
            return this.name;
        }
    }
}

