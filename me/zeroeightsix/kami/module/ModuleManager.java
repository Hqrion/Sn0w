//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.module;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.ClickGUI;
import me.zeroeightsix.kami.util.ClassFinder;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.KamiTessellator;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList();
    static HashMap<String, Module> lookup = new HashMap();

    public static void updateLookup() {
        lookup.clear();
        for (Module m : modules) {
            lookup.put(m.getName().toLowerCase(), m);
        }
    }

    public static void initialize() {
        Set<Class> classList = ClassFinder.findClasses(ClickGUI.class.getPackage().getName(), Module.class);
        classList.forEach(aClass -> {
            try {
                Module module = (Module)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                modules.add(module);
            }
            catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
        });
        KamiMod.LOGGER.info("Modules initialised");
        ModuleManager.getModules().sort(Comparator.comparing(Module::getName));
    }

    public static void onUpdate() {
        modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> module.onUpdate());
    }

    public static void onRender() {
        modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> module.onRender());
    }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("kami");
        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth((float)1.0f);
        Vec3d renderPos = EntityUtil.getInterpolatedPos((Entity)Wrapper.getPlayer(), event.getPartialTicks());
        RenderEvent e = new RenderEvent(KamiTessellator.INSTANCE, renderPos);
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();
        modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });
        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        KamiTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }

    public static void onBind(int eventKey) {
        if (eventKey == 0) {
            return;
        }
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static Module getModuleByName(String name) {
        return lookup.get(name.toLowerCase());
    }

    public static boolean isModuleEnabled(String moduleName) {
        Module m = ModuleManager.getModuleByName(moduleName);
        if (m == null) {
            return false;
        }
        return m.isEnabled();
    }
}

