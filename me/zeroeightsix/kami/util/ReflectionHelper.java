//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiHopper
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiScreenServerList
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.gui.inventory.GuiShulkerBox
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.renderer.block.model.ModelManager
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.ITextureObject
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.network.datasync.DataParameter
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketUpdateSign
 *  net.minecraft.network.play.client.CPacketVehicleMove
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.network.play.server.SPacketExplosion
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraft.util.FoodStats
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Session
 *  net.minecraft.util.Timer
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.common.FMLLog
 */
package me.zeroeightsix.kami.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;

public class ReflectionHelper {
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;
    public static Field playerViewX;
    public static Field playerViewY;
    public static Field timer;
    public static Field modelManager;
    public static Field pressed;
    public static Field cpacketPlayerYaw;
    public static Field cpacketPlayerPitch;
    public static Field spacketPlayerPosLookYaw;
    public static Field spacketPlayerPosLookPitch;
    public static Field mapTextureObjects;
    public static Field cpacketPlayerOnGround;
    public static Field rightClickDelayTimer;
    public static Field horseJumpPower;
    public static Method rightClickMouse;
    public static Field curBlockDamageMP;
    public static Field blockHitDelay;
    public static Field debugFps;
    public static Field lowerChestInventory;
    public static Field shulkerInventory;
    public static Field spacketExplosionMotionX;
    public static Field spacketExplosionMotionY;
    public static Field spacketExplosionMotionZ;
    public static Field cpacketPlayerY;
    public static Field cpacketVehicleMoveY;
    public static Field session;
    public static Field PLAYER_MODEL_FLAG;
    public static Field speedInAir;
    public static Field guiButtonHovered;
    public static Field ridingEntity;
    public static Field foodExhaustionLevel;
    public static Field cPacketUpdateSignLines;
    public static Field hopperInventory;
    public static Field cPacketChatMessage;
    public static Field guiSceenServerListServerData;
    public static Field guiDisconnectedParentScreen;
    public static Field sPacketChatChatComponent;
    public static Field boundingBox;
    public static Field y_vec3d;
    public static Field sleeping;
    public static Field sleepTimer;
    private static Field modifiersField;

    public static void init() {
        try {
            renderPosX = ReflectionHelper.getField(RenderManager.class, "renderPosX", "renderPosX");
            renderPosY = ReflectionHelper.getField(RenderManager.class, "renderPosY", "renderPosY");
            renderPosZ = ReflectionHelper.getField(RenderManager.class, "renderPosZ", "renderPosZ");
            playerViewX = ReflectionHelper.getField(RenderManager.class, "playerViewX", "playerViewX");
            playerViewY = ReflectionHelper.getField(RenderManager.class, "playerViewY", "playerViewY");
            timer = ReflectionHelper.getField(Minecraft.class, "timer", "timer");
            modelManager = ReflectionHelper.getField(Minecraft.class, "modelManager", "modelManager");
            rightClickMouse = ReflectionHelper.getMethod(Minecraft.class, new String[]{"rightClickMouse", "rightClickMouse"}, new Class[0]);
            pressed = ReflectionHelper.getField(KeyBinding.class, "pressed", "pressed");
            cpacketPlayerYaw = ReflectionHelper.getField(CPacketPlayer.class, "yaw", "yaw");
            cpacketPlayerPitch = ReflectionHelper.getField(CPacketPlayer.class, "pitch", "pitch");
            spacketPlayerPosLookYaw = ReflectionHelper.getField(SPacketPlayerPosLook.class, "yaw", "yaw");
            spacketPlayerPosLookPitch = ReflectionHelper.getField(SPacketPlayerPosLook.class, "pitch", "pitch");
            mapTextureObjects = ReflectionHelper.getField(TextureManager.class, "mapTextureObjects", "mapTextureObjects");
            cpacketPlayerOnGround = ReflectionHelper.getField(CPacketPlayer.class, "onGround", "onGround");
            rightClickDelayTimer = ReflectionHelper.getField(Minecraft.class, "rightClickDelayTimer", "rightClickDelayTimer");
            horseJumpPower = ReflectionHelper.getField(EntityPlayerSP.class, "horseJumpPower", "horseJumpPower");
            curBlockDamageMP = ReflectionHelper.getField(PlayerControllerMP.class, "curBlockDamageMP", "curBlockDamageMP");
            blockHitDelay = ReflectionHelper.getField(PlayerControllerMP.class, "blockHitDelay", "blockHitDelay");
            debugFps = ReflectionHelper.getField(Minecraft.class, "debugFPS", "debugFPS");
            lowerChestInventory = ReflectionHelper.getField(GuiChest.class, "lowerChestInventory", "lowerChestInventory");
            shulkerInventory = ReflectionHelper.getField(GuiShulkerBox.class, "inventory", "inventory");
            spacketExplosionMotionX = ReflectionHelper.getField(SPacketExplosion.class, "motionX", "motionX");
            spacketExplosionMotionY = ReflectionHelper.getField(SPacketExplosion.class, "motionY", "motionY");
            spacketExplosionMotionZ = ReflectionHelper.getField(SPacketExplosion.class, "motionZ", "motionZ");
            cpacketPlayerY = ReflectionHelper.getField(CPacketPlayer.class, "y", "y");
            cpacketVehicleMoveY = ReflectionHelper.getField(CPacketVehicleMove.class, "y", "y");
            session = ReflectionHelper.getField(Minecraft.class, "session", "session");
            PLAYER_MODEL_FLAG = ReflectionHelper.getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "PLAYER_MODEL_FLAG");
            speedInAir = ReflectionHelper.getField(EntityPlayer.class, "speedInAir", "speedInAir");
            guiButtonHovered = ReflectionHelper.getField(GuiButton.class, "hovered", "hovered");
            ridingEntity = ReflectionHelper.getField(Entity.class, "ridingEntity", "ridingEntity");
            foodExhaustionLevel = ReflectionHelper.getField(FoodStats.class, "foodExhaustionLevel", "foodExhaustionLevel");
            cPacketUpdateSignLines = ReflectionHelper.getField(CPacketUpdateSign.class, "lines", "lines");
            hopperInventory = ReflectionHelper.getField(GuiHopper.class, "hopperInventory", "hopperInventory");
            cPacketChatMessage = ReflectionHelper.getField(CPacketChatMessage.class, "message", "message");
            guiSceenServerListServerData = ReflectionHelper.getField(GuiScreenServerList.class, "serverData", "serverData");
            guiDisconnectedParentScreen = ReflectionHelper.getField(GuiDisconnected.class, "parentScreen", "parentScreen");
            sPacketChatChatComponent = ReflectionHelper.getField(SPacketChat.class, "chatComponent", "chatComponent");
            boundingBox = ReflectionHelper.getField(Entity.class, "boundingBox", "chatComponent");
            y_vec3d = ReflectionHelper.getField(Vec3d.class, "y", "y", "c");
            sleeping = ReflectionHelper.getField(EntityPlayer.class, "sleeping", "sleeping", "bK");
            sleepTimer = ReflectionHelper.getField(EntityPlayer.class, "sleepTimer", "sleepTimer");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Field getField(Class c, String ... names) {
        for (String s : names) {
            try {
                Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            }
            catch (IllegalAccessException e) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }
        throw new IllegalStateException("Field with names: " + names + " not found!");
    }

    public static Method getMethod(Class c, String[] names, Class<?> ... args) {
        for (String s : names) {
            try {
                Method m = c.getDeclaredMethod(s, args);
                m.setAccessible(true);
                return m;
            }
            catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
            }
        }
        throw new IllegalStateException("Method with names: " + names + " not found!");
    }

    public static double getRenderPosX() {
        try {
            return (Double)renderPosX.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosY() {
        try {
            return (Double)renderPosY.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosZ() {
        try {
            return (Double)renderPosZ.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getPlayerViewY() {
        try {
            return ((Float)playerViewY.get(Wrapper.getMinecraft().getRenderManager())).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getPlayerViewX() {
        try {
            return ((Float)playerViewX.get(Wrapper.getMinecraft().getRenderManager())).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Timer getTimer() {
        try {
            return (Timer)timer.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static ModelManager getModelManager() {
        try {
            return (ModelManager)modelManager.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void rightClickMouse() {
        try {
            rightClickMouse.invoke(Wrapper.getMinecraft(), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getPressed(KeyBinding binding) {
        try {
            return (Boolean)pressed.get(binding);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setPressed(KeyBinding keyBinding, boolean state) {
        try {
            pressed.set(keyBinding, state);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerYaw(CPacketPlayer packet, float value) {
        try {
            cpacketPlayerYaw.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerPitch(CPacketPlayer packet, float value) {
        try {
            cpacketPlayerPitch.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketPlayerPosLookYaw(float value, SPacketPlayerPosLook packet) {
        try {
            spacketPlayerPosLookYaw.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketPlayerPosLookPitch(float value, SPacketPlayerPosLook packet) {
        try {
            spacketPlayerPosLookPitch.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Map<ResourceLocation, ITextureObject> getMapTextureObjects() {
        try {
            return (Map)mapTextureObjects.get(Wrapper.getMinecraft().getTextureManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerOnGround(CPacketPlayer packet, boolean onGround) {
        try {
            cpacketPlayerOnGround.set(packet, onGround);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setRightClickDelayTimer(int value) {
        try {
            rightClickDelayTimer.set(Wrapper.getMinecraft(), value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setHorseJumpPower(float value) {
        try {
            horseJumpPower.set(Wrapper.getMinecraft().player, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getCurBlockDamageMP() {
        try {
            return ((Float)curBlockDamageMP.get(Wrapper.getMinecraft().playerController)).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCurBlockDamageMP(float value) {
        try {
            curBlockDamageMP.set(Wrapper.getMinecraft().playerController, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getBlockHitDelay() {
        try {
            return (Integer)blockHitDelay.get(Wrapper.getMinecraft().playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setBlockHitDelay(float value) {
        try {
            blockHitDelay.set(Wrapper.getMinecraft().playerController, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getDebugFps() {
        try {
            return (Integer)debugFps.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getLowerChestInventory(GuiChest chest) {
        try {
            return (IInventory)lowerChestInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getShulkerInventory(GuiShulkerBox chest) {
        try {
            return (IInventory)shulkerInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionX(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionX.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionY(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionY.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionZ(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionZ.set(packet, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getCPacketPlayerY(CPacketPlayer packet) {
        try {
            return (Double)cpacketPlayerY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerY(CPacketPlayer packet, double value) {
        try {
            cpacketPlayerY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getCPacketVehicleMoveY(CPacketVehicleMove packet) {
        try {
            return (Double)cpacketVehicleMoveY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketVehicleMoveY(CPacketVehicleMove packet, double value) {
        try {
            cpacketVehicleMoveY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSession(Session newSession) {
        try {
            session.set(Wrapper.getMinecraft(), newSession);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter)PLAYER_MODEL_FLAG.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSpeedInAir(EntityPlayer entityPlayer, float newValue) {
        try {
            speedInAir.set(entityPlayer, Float.valueOf(newValue));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getSpeedInAir(EntityPlayer entityPlayer) {
        try {
            return ((Float)speedInAir.get(entityPlayer)).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getGuiButtonHovered(GuiButton button) {
        try {
            return (Boolean)guiButtonHovered.get(button);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setGuiButtonHovered(GuiButton button, boolean value) {
        try {
            guiButtonHovered.set(button, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Entity getRidingEntity(Entity toGetFrom) {
        try {
            return (Entity)ridingEntity.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getFoodExhaustionLevel() {
        try {
            return ((Float)foodExhaustionLevel.get(Wrapper.getMinecraft().player.getFoodStats())).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketUpdateSignLines(CPacketUpdateSign packet, String[] value) {
        try {
            cPacketUpdateSignLines.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getHopperInventory(GuiHopper chest) {
        try {
            return (IInventory)hopperInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketChatMessage(CPacketChatMessage packet, String value) {
        try {
            cPacketChatMessage.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static ServerData getServerData(GuiScreenServerList data) {
        try {
            return (ServerData)guiSceenServerListServerData.get(data);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static GuiScreen getGuiDisconnectedParentScreen(GuiDisconnected toGetFrom) {
        try {
            return (GuiScreen)guiDisconnectedParentScreen.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketChatChatComponent(SPacketChat packet, TextComponentString value) {
        try {
            sPacketChatChatComponent.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setY_vec3d(Vec3d vec, double val) {
        try {
            y_vec3d.set(vec, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getSleeping(EntityPlayer mgr) {
        try {
            return (Boolean)sleeping.get(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSleeping(EntityPlayer entityPlayer, boolean value) {
        try {
            sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void sleepTimer(EntityPlayer entityPlayer, int value) {
        try {
            sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    static {
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

