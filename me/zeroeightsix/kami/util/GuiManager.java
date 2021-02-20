/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.zeroeightsix.kami.util;

import com.mojang.realmsclient.gui.ChatFormatting;

public class GuiManager {
    private float guiRed = 0.55f;
    private float guiGreen = 0.7f;
    private float guiBlue = 0.25f;
    private String textColor = ChatFormatting.GRAY.toString();
    private String hudTextColor;
    private int moduleListRed = 255;
    private int moduleListGreen = 255;
    private int moduleListBlue = 255;
    private ModuleListMode moduleListMode = ModuleListMode.RAINBOW;
    private boolean textRadarPots = true;
    private int textRadarPlayers = 8;

    public boolean isTextRadarPots() {
        return this.textRadarPots;
    }

    public void setTextRadarPots(boolean textRadarPots) {
        this.textRadarPots = textRadarPots;
    }

    public int getTextRadarPlayers() {
        return this.textRadarPlayers;
    }

    public void setTextRadarPlayers(int textRadarPlayers) {
        this.textRadarPlayers = textRadarPlayers;
    }

    public ModuleListMode getModuleListMode() {
        return this.moduleListMode;
    }

    public void setModuleListMode(ModuleListMode moduleListMode) {
        this.moduleListMode = moduleListMode;
    }

    public void setModuleListColors(int r, int g, int b) {
        this.moduleListRed = r;
        this.moduleListGreen = g;
        this.moduleListBlue = b;
    }

    public void setGuiColors(float r, float g, float b) {
        this.guiRed = r;
        this.guiGreen = g;
        this.guiBlue = b;
    }

    public String getTextColor() {
        return this.textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getHUDTextColor() {
        return this.hudTextColor;
    }

    public void setHUDTextColor(String textColor) {
        this.hudTextColor = textColor;
    }

    public float getGuiRed() {
        return this.guiRed;
    }

    public float getGuiGreen() {
        return this.guiGreen;
    }

    public float getGuiBlue() {
        return this.guiBlue;
    }

    public int getModuleListRed() {
        return this.moduleListRed;
    }

    public int getModuleListGreen() {
        return this.moduleListGreen;
    }

    public int getModuleListBlue() {
        return this.moduleListBlue;
    }

    public static enum ModuleListMode {
        STATIC,
        RAINBOW;

    }
}

