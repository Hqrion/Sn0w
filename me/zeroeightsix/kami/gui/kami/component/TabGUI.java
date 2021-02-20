/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.gui.kami.component;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class TabGUI
extends AbstractComponent
implements EventListener {
    public final ArrayList<Tab> tabs = new ArrayList();
    public int width;
    public int height;
    public int selected;
    public float selectedLerpY;
    public boolean tabOpened;

    public TabGUI() {
        FMLCommonHandler.instance().bus().register((Object)this);
        LinkedHashMap<Module.Category, Tab> tabMap = new LinkedHashMap<Module.Category, Tab>();
        for (Module.Category category : Module.Category.values()) {
            tabMap.put(category, new Tab(category.getName()));
        }
        ArrayList<Module> features = new ArrayList<Module>();
        features.addAll(ModuleManager.getModules());
        for (Module feature : features) {
            if (feature.getCategory() == null || feature.getCategory().isHidden()) continue;
            ((Tab)tabMap.get((Object)feature.getCategory())).add(feature);
        }
        Iterator iterator = tabMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            if (!((Tab)entry.getValue()).features.isEmpty()) continue;
            iterator.remove();
        }
        this.tabs.addAll(tabMap.values());
        this.tabs.forEach(tab -> tab.updateSize());
        this.updateSize();
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        Container framep = this.getParent();
        while (!(framep instanceof Frame)) {
            framep = framep.getParent();
        }
        if (!((Frame)framep).isPinned()) {
            return;
        }
        if (this.tabOpened) {
            switch (Keyboard.getEventKey()) {
                case 203: {
                    this.tabOpened = false;
                    break;
                }
                default: {
                    this.tabs.get(this.selected).onKeyPress(Keyboard.getEventKey());
                    break;
                }
            }
        } else {
            switch (Keyboard.getEventKey()) {
                case 208: {
                    if (this.selected < this.tabs.size() - 1) {
                        ++this.selected;
                        break;
                    }
                    this.selected = 0;
                    break;
                }
                case 200: {
                    if (this.selected > 0) {
                        --this.selected;
                        break;
                    }
                    this.selected = this.tabs.size() - 1;
                    break;
                }
                case 205: {
                    this.tabOpened = true;
                }
            }
        }
    }

    private void updateSize() {
        this.width = 64;
        for (Tab tab : this.tabs) {
            int tabWidth = Wrapper.getFontRenderer().getStringWidth(tab.name) + 10;
            if (tabWidth <= this.width) continue;
            this.width = tabWidth;
        }
        this.height = this.tabs.size() * 10;
    }

    public static final class Tab {
        public final String name;
        public final ArrayList<Module> features = new ArrayList();
        public int width;
        public int height;
        public int selected;
        public float lerpSelectY = 0.0f;

        public Tab(String name) {
            this.name = name;
        }

        public void updateSize() {
            this.width = 64;
            for (Module feature : this.features) {
                int fWidth = Wrapper.getFontRenderer().getStringWidth(feature.getName()) + 10;
                if (fWidth <= this.width) continue;
                this.width = fWidth;
            }
            this.height = this.features.size() * 10;
        }

        public void onKeyPress(int keyCode) {
            switch (keyCode) {
                case 208: {
                    if (this.selected < this.features.size() - 1) {
                        ++this.selected;
                        break;
                    }
                    this.selected = 0;
                    break;
                }
                case 200: {
                    if (this.selected > 0) {
                        --this.selected;
                        break;
                    }
                    this.selected = this.features.size() - 1;
                    break;
                }
                case 205: {
                    this.features.get(this.selected).toggle();
                }
            }
        }

        public void add(Module feature) {
            this.features.add(feature);
        }
    }
}

