//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        this.framebuffer = new Framebuffer(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight, false);
    }

    public void onGuiClosed() {
        KamiGUI gui = KamiMod.getInstance().getKamiGUI();
        gui.getChildren().stream().filter(component -> component instanceof Frame && ((Frame)component).isPinneable() && component.isVisible()).forEach(component -> component.setOpacity(0.0f));
    }

    public void initGui() {
        this.gui = KamiMod.getInstance().getKamiGUI();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.calculateMouse();
        this.gui.drawGUI();
        GL11.glEnable((int)3553);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.gui.handleMouseDown(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.gui.handleMouseRelease(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.gui.handleMouseDrag(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    public void updateScreen() {
        int a;
        if (Mouse.hasWheel() && (a = Mouse.getDWheel()) != 0) {
            this.gui.handleWheel(mouseX, mouseY, a);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.lastScreen);
        } else {
            this.gui.handleKeyDown(keyCode);
            this.gui.handleKeyUp(keyCode);
        }
    }

    public static int getScale() {
        int scaleFactor;
        int scale = Wrapper.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        for (scaleFactor = 0; scaleFactor < scale && Wrapper.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().displayHeight / (scaleFactor + 1) >= 240; ++scaleFactor) {
        }
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }

    private void calculateMouse() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scaleFactor = DisplayGuiScreen.getScale();
        mouseX = Mouse.getX() / scaleFactor;
        mouseY = minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1;
    }
}

