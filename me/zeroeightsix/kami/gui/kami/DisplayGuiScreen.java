/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami;

import java.io.IOException;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class DisplayGuiScreen
extends GuiScreen {
    KamiGUI gui;
    public final GuiScreen lastScreen;
    public static int mouseX;
    public static int mouseY;
    Framebuffer framebuffer;

    public DisplayGuiScreen(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        KamiGUI gui = KamiMod.getInstance().getKamiGUI();
        for (Component c : gui.getChildren()) {
            Frame child;
            if (!(c instanceof Frame) || !(child = (Frame)c).isPinneable() || !child.isVisible()) continue;
            child.setOpacity(0.5f);
        }
        this.framebuffer = new Framebuffer(Wrapper.getMinecraft().field_71443_c, Wrapper.getMinecraft().field_71440_d, false);
    }

    public void func_146281_b() {
        KamiGUI gui = KamiMod.getInstance().getKamiGUI();
        gui.getChildren().stream().filter(component -> component instanceof Frame && ((Frame)component).isPinneable() && component.isVisible()).forEach(component -> component.setOpacity(0.0f));
    }

    public void func_73866_w_() {
        this.gui = KamiMod.getInstance().getKamiGUI();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.calculateMouse();
        this.gui.drawGUI();
        GL11.glEnable((int)3553);
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.gui.handleMouseDown(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void func_146286_b(int mouseX, int mouseY, int state) {
        this.gui.handleMouseRelease(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void func_146273_a(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.gui.handleMouseDrag(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    public void func_73876_c() {
        int a;
        if (Mouse.hasWheel() && (a = Mouse.getDWheel()) != 0) {
            this.gui.handleWheel(mouseX, mouseY, a);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a(this.lastScreen);
        } else {
            this.gui.handleKeyDown(keyCode);
            this.gui.handleKeyUp(keyCode);
        }
    }

    public static int getScale() {
        int scaleFactor;
        int scale = Wrapper.getMinecraft().field_71474_y.field_74335_Z;
        if (scale == 0) {
            scale = 1000;
        }
        for (scaleFactor = 0; scaleFactor < scale && Wrapper.getMinecraft().field_71443_c / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().field_71440_d / (scaleFactor + 1) >= 240; ++scaleFactor) {
        }
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }

    private void calculateMouse() {
        Minecraft minecraft = Minecraft.func_71410_x();
        int scaleFactor = DisplayGuiScreen.getScale();
        mouseX = Mouse.getX() / scaleFactor;
        mouseY = minecraft.field_71440_d / scaleFactor - Mouse.getY() / scaleFactor - 1;
    }
}

