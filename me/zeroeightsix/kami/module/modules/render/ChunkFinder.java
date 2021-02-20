//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.event.world.ChunkEvent$Unload
 *  org.apache.commons.lang3.SystemUtils
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.ChunkEvent;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.opengl.GL11;

@Module.Info(name="ChunkFinder", description="Highlights newly generated chunks", category=Module.Category.HIDDEN)
public class ChunkFinder
extends Module {
    private Setting<Integer> yOffset = this.register(Settings.i("Y Offset", 0));
    private Setting<Boolean> relative = this.register(Settings.b("Relative", true));
    private Setting<Boolean> saveNewChunks = this.register(Settings.b("Save New Chunks", false));
    private Setting<SaveOption> saveOption = this.register(Settings.enumBuilder(SaveOption.class).withValue(SaveOption.extraFolder).withName("Save Option").withVisibility(aBoolean -> this.saveNewChunks.getValue()).build());
    private Setting<Boolean> saveInRegionFolder = this.register(Settings.booleanBuilder("In Region").withValue(false).withVisibility(aBoolean -> this.saveNewChunks.getValue()).build());
    private Setting<Boolean> alsoSaveNormalCoords = this.register(Settings.booleanBuilder("Save Normal Coords").withValue(false).withVisibility(aBoolean -> this.saveNewChunks.getValue()).build());
    private LastSetting lastSetting = new LastSetting();
    private PrintWriter logWriter;
    static ArrayList<Chunk> chunks = new ArrayList();
    private static boolean dirty = true;
    private int list = GL11.glGenLists((int)1);
    @EventHandler
    public Listener<ChunkEvent> listener = new Listener<ChunkEvent>(event -> {
        if (!event.getPacket().isFullChunk()) {
            chunks.add(event.getChunk());
            dirty = true;
            if (this.saveNewChunks.getValue().booleanValue()) {
                this.saveNewChunk(event.getChunk());
            }
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<ChunkEvent.Unload> unloadListener = new Listener<ChunkEvent.Unload>(event -> {
        dirty = chunks.remove(event.getChunk());
    }, new Predicate[0]);

    @Override
    public void onWorldRender(RenderEvent event) {
        if (dirty) {
            GL11.glNewList((int)this.list, (int)4864);
            GL11.glPushMatrix();
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.0f);
            for (Chunk chunk : chunks) {
                double posX = chunk.x * 16;
                double posY = 0.0;
                double posZ = chunk.z * 16;
                GL11.glColor3f((float)0.6f, (float)0.1f, (float)0.2f);
                GL11.glBegin((int)2);
                GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
                GL11.glVertex3d((double)(posX + 16.0), (double)posY, (double)posZ);
                GL11.glVertex3d((double)(posX + 16.0), (double)posY, (double)(posZ + 16.0));
                GL11.glVertex3d((double)posX, (double)posY, (double)(posZ + 16.0));
                GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
                GL11.glEnd();
            }
            GL11.glDisable((int)3042);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEndList();
            dirty = false;
        }
        double x = ChunkFinder.mc.getRenderManager().renderPosX;
        double y = this.relative.getValue() != false ? 0.0 : -ChunkFinder.mc.getRenderManager().renderPosY;
        double z = ChunkFinder.mc.getRenderManager().renderPosZ;
        GL11.glTranslated((double)(-x), (double)(y + (double)this.yOffset.getValue().intValue()), (double)(-z));
        GL11.glCallList((int)this.list);
        GL11.glTranslated((double)x, (double)(-(y + (double)this.yOffset.getValue().intValue())), (double)z);
    }

    @Override
    protected void onDisable() {
        this.logWriterClose();
        chunks.clear();
    }

    public void saveNewChunk(Chunk chunk) {
        this.saveNewChunk(this.testAndGetLogWriter(), this.getNewChunkInfo(chunk));
    }

    private String getNewChunkInfo(Chunk chunk) {
        String rV = String.format("%d,%d,%d", System.currentTimeMillis(), chunk.x, chunk.z);
        if (this.alsoSaveNormalCoords.getValue().booleanValue()) {
            rV = rV + String.format(",%d,%d", chunk.x * 16 + 8, chunk.z * 16 + 8);
        }
        return rV;
    }

    private PrintWriter testAndGetLogWriter() {
        if (this.lastSetting.testChangeAndUpdate()) {
            this.logWriterClose();
            this.logWriterOpen();
        }
        return this.logWriter;
    }

    private void logWriterOpen() {
        String filepath = this.getPath().toString();
        try {
            this.logWriter = new PrintWriter(new BufferedWriter(new FileWriter(filepath, true)), true);
            String head = "timestamp,ChunkX,ChunkZ";
            if (this.alsoSaveNormalCoords.getValue().booleanValue()) {
                head = head + ",x coordinate,z coordinate";
            }
            this.logWriter.println(head);
        }
        catch (Exception e) {
            e.printStackTrace();
            KamiMod.LOGGER.error("some exception happened when trying to start the logging -> " + e.getMessage());
            Command.sendChatMessage("onLogStart: " + e.getMessage());
        }
    }

    private Path getPath() {
        File file = null;
        int dimension = ChunkFinder.mc.player.dimension;
        if (mc.isSingleplayer()) {
            try {
                file = mc.getIntegratedServer().getWorld(dimension).getChunkSaveLocation();
            }
            catch (Exception e) {
                e.printStackTrace();
                KamiMod.LOGGER.error("some exception happened when getting canonicalFile -> " + e.getMessage());
                Command.sendChatMessage("onGetPath: " + e.getMessage());
            }
            if (file.toPath().relativize(ChunkFinder.mc.gameDir.toPath()).getNameCount() != 2) {
                file = file.getParentFile();
            }
        } else {
            file = this.makeMultiplayerDirectory().toFile();
        }
        if (dimension != 0) {
            file = new File(file, "DIM" + dimension);
        }
        if (this.saveInRegionFolder.getValue().booleanValue()) {
            file = new File(file, "region");
        }
        file = new File(file, "newChunkLogs");
        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        file = new File(file, mc.getSession().getUsername() + "_" + date + ".csv");
        Path rV = file.toPath();
        try {
            if (!Files.exists(rV, new LinkOption[0])) {
                Files.createDirectories(rV.getParent(), new FileAttribute[0]);
                Files.createFile(rV, new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            KamiMod.LOGGER.error("some exception happened when trying to make the file -> " + e.getMessage());
            Command.sendChatMessage("onCreateFile: " + e.getMessage());
        }
        return rV;
    }

    private Path makeMultiplayerDirectory() {
        File rV = Minecraft.getMinecraft().gameDir;
        switch (this.saveOption.getValue()) {
            case liteLoaderWdl: {
                String folderName = ChunkFinder.mc.getCurrentServerData().serverName;
                rV = new File(rV, "saves");
                rV = new File(rV, folderName);
                break;
            }
            case nhackWdl: {
                String folderName = this.getNHackInetName();
                rV = new File(rV, "config");
                rV = new File(rV, "wdl-saves");
                rV = new File(rV, folderName);
                if (rV.exists()) break;
                Command.sendChatMessage("nhack wdl directory doesnt exist: " + folderName);
                Command.sendChatMessage("creating the directory now. It is recommended to update the ip");
                break;
            }
            default: {
                String folderName = ChunkFinder.mc.getCurrentServerData().serverName + "-" + ChunkFinder.mc.getCurrentServerData().serverIP;
                if (SystemUtils.IS_OS_WINDOWS) {
                    folderName = folderName.replace(":", "_");
                }
                rV = new File(rV, "KAMI_NewChunks");
                rV = new File(rV, folderName);
            }
        }
        return rV.toPath();
    }

    private String getNHackInetName() {
        String folderName = ChunkFinder.mc.getCurrentServerData().serverIP;
        if (SystemUtils.IS_OS_WINDOWS) {
            folderName = folderName.replace(":", "_");
        }
        if (this.hasNoPort(folderName)) {
            folderName = folderName + "_25565";
        }
        return folderName;
    }

    private boolean hasNoPort(String ip) {
        if (!ip.contains("_")) {
            return true;
        }
        String[] sp = ip.split("_");
        String ending = sp[sp.length - 1];
        return !this.isInteger(ending);
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void logWriterClose() {
        if (this.logWriter != null) {
            this.logWriter.close();
            this.logWriter = null;
        }
    }

    private void saveNewChunk(PrintWriter log, String data) {
        log.println(data);
    }

    @Override
    public void destroy() {
        GL11.glDeleteLists((int)1, (int)1);
    }

    private class LastSetting {
        SaveOption lastSaveOption;
        boolean lastInRegion;
        boolean lastSaveNormal;
        int dimension;
        String ip;

        private LastSetting() {
        }

        public boolean testChangeAndUpdate() {
            if (this.testChange()) {
                this.update();
                return true;
            }
            return false;
        }

        public boolean testChange() {
            if (ChunkFinder.this.saveOption.getValue() != this.lastSaveOption) {
                return true;
            }
            if ((Boolean)ChunkFinder.this.saveInRegionFolder.getValue() != this.lastInRegion) {
                return true;
            }
            if ((Boolean)ChunkFinder.this.alsoSaveNormalCoords.getValue() != this.lastSaveNormal) {
                return true;
            }
            if (this.dimension != mc.player.dimension) {
                return true;
            }
            return !mc.getCurrentServerData().serverIP.equals(this.ip);
        }

        private void update() {
            this.lastSaveOption = (SaveOption)((Object)ChunkFinder.this.saveOption.getValue());
            this.lastInRegion = (Boolean)ChunkFinder.this.saveInRegionFolder.getValue();
            this.lastSaveNormal = (Boolean)ChunkFinder.this.alsoSaveNormalCoords.getValue();
            this.dimension = mc.player.dimension;
            this.ip = mc.getCurrentServerData().serverIP;
        }
    }

    private static enum SaveOption {
        extraFolder,
        liteLoaderWdl,
        nhackWdl;

    }
}

