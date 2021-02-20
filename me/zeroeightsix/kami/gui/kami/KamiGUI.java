//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.item.EntityMinecart
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityEgg
 *  net.minecraft.entity.projectile.EntitySnowball
 *  net.minecraft.entity.projectile.EntityWitherSkull
 *  net.minecraft.init.MobEffects
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.text.TextFormatting
 */
package me.zeroeightsix.kami.gui.kami;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.gui.kami.RootFontRenderer;
import me.zeroeightsix.kami.gui.kami.Stretcherlayout;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.kami.component.Radar;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiTheme;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Scrollpane;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.TickListener;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.component.use.Label;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.ColourHolder;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.LagCompensator;
import me.zeroeightsix.kami.util.Pair;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class KamiGUI
extends GUI {
    public static final RootFontRenderer fontRenderer = new RootFontRenderer(1.0f);
    private static final int DOCK_OFFSET = 0;
    private static final String witherSkullText = TextFormatting.DARK_GRAY + "Wither Skull";
    private static final String enderCrystalText = TextFormatting.LIGHT_PURPLE + "End Crystal";
    private static final String thrownEnderPearlText = TextFormatting.LIGHT_PURPLE + "Thrown Ender Pearl";
    private static final String minecartText = "Minecart";
    private static final String itemFrameText = "Item Frame";
    private static final String thrownEggText = "Thrown Egg";
    private static final String thrownSnowballText = "Thrown Snowball";
    public static ColourHolder primaryColour = new ColourHolder(29, 29, 29);
    public Theme theme = this.getTheme();

    public KamiGUI() {
        super(new KamiTheme());
    }

    private static String getEntityName(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return TextFormatting.DARK_AQUA + ((EntityItem)entity).getItem().getItem().getItemStackDisplayName(((EntityItem)entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return witherSkullText;
        }
        if (entity instanceof EntityEnderCrystal) {
            return enderCrystalText;
        }
        if (entity instanceof EntityEnderPearl) {
            return thrownEnderPearlText;
        }
        if (entity instanceof EntityMinecart) {
            return minecartText;
        }
        if (entity instanceof EntityItemFrame) {
            return itemFrameText;
        }
        if (entity instanceof EntityEgg) {
            return thrownEggText;
        }
        if (entity instanceof EntitySnowball) {
            return thrownSnowballText;
        }
        return entity.getName();
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void dock(Frame component) {
        Docking docking = component.getDocking();
        if (docking.isTop()) {
            component.setY(0);
        }
        if (docking.isBottom()) {
            component.setY(Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale() - component.getHeight() - 0);
        }
        if (docking.isLeft()) {
            component.setX(0);
        }
        if (docking.isRight()) {
            component.setX(Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale() - component.getWidth() - 0);
        }
        if (docking.isCenterHorizontal()) {
            component.setX(Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2);
        }
        if (docking.isCenterVertical()) {
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);
        }
    }

    @Override
    public void drawGUI() {
        super.drawGUI();
    }

    @Override
    public void initializeGUI() {
        int y;
        HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>>();
        for (final Module module : ModuleManager.getModules()) {
            if (module.getCategory().isHidden()) continue;
            Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey((Object)moduleCategory)) {
                Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                Scrollpane scrollpane1 = new Scrollpane(this.getTheme(), stretcherlayout, 300, 260);
                scrollpane1.setMaximumHeight(180);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<Scrollpane, SettingsPanel>(scrollpane1, new SettingsPanel(this.getTheme(), null)));
            }
            final Pair pair = (Pair)categoryScrollpaneHashMap.get((Object)moduleCategory);
            Scrollpane scrollpane = (Scrollpane)pair.getKey();
            final CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());
            checkButton.addTickListener(() -> {
                checkButton.setToggled(module.isEnabled());
                checkButton.setName(module.getName());
            });
            checkButton.addMouseListener(new MouseListener(){

                @Override
                public void onMouseDown(MouseListener.MouseButtonEvent event) {
                    if (event.getButton() == 1) {
                        ((SettingsPanel)pair.getValue()).setModule(module);
                        ((SettingsPanel)pair.getValue()).setX(event.getX() + checkButton.getX());
                        ((SettingsPanel)pair.getValue()).setY(event.getY() + checkButton.getY());
                    }
                }

                @Override
                public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                }

                @Override
                public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                }

                @Override
                public void onMouseMove(MouseListener.MouseMoveEvent event) {
                }

                @Override
                public void onScroll(MouseListener.MouseScrollEvent event) {
                }
            });
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>(){

                @Override
                public void execute(CheckButton component, CheckButton.CheckButtonPoof.CheckButtonPoofInfo info) {
                    if (info.getAction().equals((Object)CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }
        int x = 10;
        int nexty = y = 10;
        for (Map.Entry entry : categoryScrollpaneHashMap.entrySet()) {
            Stretcherlayout stretcherlayout = new Stretcherlayout(1);
            stretcherlayout.COMPONENT_OFFSET_Y = 1;
            Frame frame1 = new Frame(this.getTheme(), stretcherlayout, ((Module.Category)((Object)entry.getKey())).getName());
            Scrollpane scrollpane = (Scrollpane)((Pair)entry.getValue()).getKey();
            frame1.addChild(scrollpane);
            frame1.addChild((Component)((Pair)entry.getValue()).getValue());
            scrollpane.setOriginOffsetY(0);
            scrollpane.setOriginOffsetX(0);
            frame1.setCloseable(false);
            frame1.setX(x);
            frame1.setY(y);
            this.addChild(frame1);
            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            if (!((float)(x += frame1.getWidth() + 10) > (float)Wrapper.getMinecraft().displayWidth / 1.2f)) continue;
            nexty = y = nexty;
        }
        this.addMouseListener(new MouseListener(){

            private boolean isNotBetweenNullAnd(int val, int max) {
                return val > max || val < 0;
            }

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                List<SettingsPanel> panels = ContainerHelper.getAllChildren(SettingsPanel.class, KamiGUI.this);
                for (SettingsPanel settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) continue;
                    int[] real = GUI.calculateRealPosition(settingsPanel);
                    int pX = event.getX() - real[0];
                    int pY = event.getY() - real[1];
                    if (!this.isNotBetweenNullAnd(pX, settingsPanel.getWidth()) && !this.isNotBetweenNullAnd(pY, settingsPanel.getHeight())) continue;
                    settingsPanel.setVisible(false);
                }
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
        ArrayList<Frame> frames = new ArrayList<Frame>();
        Frame frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Active modules");
        frame.setCloseable(false);
        frame.addChild(new ActiveModules());
        frame.setPinneable(true);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Info");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label information = new Label("");
        information.setShadow(true);
        information.addTickListener(() -> {
            information.setText("");
            Wrapper.getMinecraft();
            information.addLine(KamiMod.getInstance().guiManager.getTextColor() + Math.round(LagCompensator.INSTANCE.getTickRate()) + ChatFormatting.DARK_GRAY.toString() + " tps  |  " + KamiMod.getInstance().guiManager.getTextColor() + Minecraft.debugFPS + ChatFormatting.DARK_GRAY.toString() + " fps");
        });
        frame.addChild(information);
        information.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Text Radar");
        Label list = new Label("");
        DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.CEILING);
        StringBuilder healthSB = new StringBuilder();
        StringBuilder potsSB = new StringBuilder();
        list.addTickListener(() -> {
            if (!list.isVisible()) {
                return;
            }
            list.setText("");
            Minecraft mc = Wrapper.getMinecraft();
            if (mc.player == null) {
                return;
            }
            List entityList = mc.world.playerEntities;
            Map<String, Integer> players = new HashMap();
            int playerStep = 0;
            for (Entity entity : entityList) {
                if (playerStep >= KamiMod.getInstance().guiManager.getTextRadarPlayers()) break;
                if (entity.getName().equals(mc.player.getName())) continue;
                EntityPlayer entityPlayer = (EntityPlayer)entity;
                String posString = entityPlayer.posY > mc.player.posY ? ChatFormatting.DARK_GREEN.toString() + "+" : (entityPlayer.posY == mc.player.posY ? " " : ChatFormatting.DARK_RED.toString() + "-");
                float hpRaw = entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
                String hp = dfHealth.format(hpRaw);
                if (hpRaw >= 20.0f) {
                    healthSB.append(ChatFormatting.GREEN.toString());
                } else if (hpRaw >= 10.0f) {
                    healthSB.append(ChatFormatting.YELLOW.toString());
                } else if (hpRaw >= 5.0f) {
                    healthSB.append(ChatFormatting.GOLD.toString());
                } else {
                    healthSB.append(ChatFormatting.RED.toString());
                }
                healthSB.append(hp);
                healthSB.append(" ");
                if (KamiMod.getInstance().guiManager.isTextRadarPots()) {
                    int duration;
                    PotionEffect effectweakness;
                    int duration2;
                    PotionEffect effectStrength = entityPlayer.getActivePotionEffect(MobEffects.STRENGTH);
                    if (effectStrength != null && entityPlayer.isPotionActive(MobEffects.STRENGTH) && (duration2 = effectStrength.getDuration()) > 0) {
                        potsSB.append(ChatFormatting.RED);
                        potsSB.append(" S ");
                        potsSB.append(ChatFormatting.GRAY);
                        potsSB.append(Potion.getPotionDurationString((PotionEffect)effectStrength, (float)1.0f));
                    }
                    if ((effectweakness = entityPlayer.getActivePotionEffect(MobEffects.WEAKNESS)) != null && entityPlayer.isPotionActive(MobEffects.WEAKNESS) && (duration = effectweakness.getDuration()) > 0) {
                        potsSB.append(ChatFormatting.GOLD);
                        potsSB.append(" W ");
                        potsSB.append(ChatFormatting.GRAY);
                        potsSB.append(Potion.getPotionDurationString((PotionEffect)effectweakness, (float)1.0f));
                    }
                }
                String nameColor = Friends.isFriend(entity.getName()) ? ChatFormatting.GREEN.toString() : KamiMod.getInstance().guiManager.getTextColor();
                players.put(ChatFormatting.GRAY.toString() + posString + " " + healthSB.toString() + nameColor + entityPlayer.getName() + potsSB.toString(), (int)mc.player.getDistance((Entity)entityPlayer));
                healthSB.setLength(0);
                potsSB.setLength(0);
                ++playerStep;
            }
            if (players.isEmpty()) {
                list.setText("");
                return;
            }
            players = KamiGUI.sortByValue(players);
            for (Map.Entry entry : players.entrySet()) {
                list.addLine((String)entry.getKey() + " " + ChatFormatting.DARK_GRAY.toString() + entry.getValue());
            }
        });
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.setMinimumWidth(75);
        list.setShadow(true);
        frame.addChild(list);
        list.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Entities");
        final Label entityLabel = new Label("");
        frame.setCloseable(false);
        entityLabel.addTickListener(new TickListener(){
            Minecraft mc = Wrapper.getMinecraft();

            @Override
            public void onTick() {
                if (this.mc.player == null || !entityLabel.isVisible()) {
                    return;
                }
                ArrayList entityList = new ArrayList(this.mc.world.loadedEntityList);
                if (entityList.size() <= 1) {
                    entityLabel.setText("");
                    return;
                }
                Map<String, Integer> entityCounts = entityList.stream().filter(Objects::nonNull).filter(e -> !(e instanceof EntityPlayer)).collect(Collectors.groupingBy(x$0 -> KamiGUI.getEntityName(x$0), Collectors.reducing(0, ent -> ent instanceof EntityItem ? Integer.valueOf(((EntityItem)ent).getItem().getCount()) : Integer.valueOf(1), Integer::sum)));
                entityLabel.setText("");
                Objects.requireNonNull(entityLabel);
                entityCounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(entry -> TextFormatting.GRAY + (String)entry.getKey() + " " + TextFormatting.DARK_GRAY + "x" + entry.getValue()).forEach(entityLabel::addLine);
            }
        });
        frame.addChild(entityLabel);
        frame.setPinneable(true);
        entityLabel.setShadow(true);
        entityLabel.setFontRenderer(fontRenderer);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Coordinates");
        frame.setCloseable(false);
        frame.setPinneable(true);
        final Label coordsLabel = new Label("");
        coordsLabel.addTickListener(new TickListener(){
            Minecraft mc = Minecraft.getMinecraft();

            @Override
            public void onTick() {
                boolean inHell = this.mc.world.getBiome(this.mc.player.getPosition()).getBiomeName().equals("Hell");
                int posX = (int)this.mc.player.posX;
                int posY = (int)this.mc.player.posY;
                int posZ = (int)this.mc.player.posZ;
                float f = !inHell ? 0.125f : 8.0f;
                int hposX = (int)(this.mc.player.posX * (double)f);
                int hposZ = (int)(this.mc.player.posZ * (double)f);
                coordsLabel.setText(String.format(" %sf%,d%s7, %sf%,d%s7, %sf%,d %s7(%sf%,d%s7, %sf%,d%s7, %sf%,d%s7)", Character.valueOf(Command.SECTIONSIGN()), posX, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posY, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posZ, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), hposX, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), posY, Character.valueOf(Command.SECTIONSIGN()), Character.valueOf(Command.SECTIONSIGN()), hposZ, Character.valueOf(Command.SECTIONSIGN())));
            }
        });
        frame.addChild(coordsLabel);
        coordsLabel.setFontRenderer(fontRenderer);
        coordsLabel.setShadow(true);
        frame.setHeight(20);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Radar");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        frame.addChild(new Radar());
        frame.setWidth(100);
        frame.setHeight(100);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Greeter");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        Label greeter = new Label("");
        greeter.addTickListener(() -> {
            if (Wrapper.getMinecraft().player == null) {
                return;
            }
            greeter.setText("");
            greeter.addLine(ChatFormatting.DARK_GRAY.toString() + "Welcome " + KamiMod.getInstance().guiManager.getTextColor() + Wrapper.getMinecraft().player.getDisplayNameString() + ChatFormatting.DARK_GRAY.toString() + " <3");
        });
        frame.addChild(greeter);
        greeter.setFontRenderer(fontRenderer);
        greeter.setShadow(true);
        frames.add(frame);
        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Watermark");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        Label watermark = new Label("");
        watermark.addTickListener(() -> {
            if (Wrapper.getMinecraft().player == null) {
                return;
            }
            watermark.setText("");
            watermark.addLine(KamiMod.getInstance().guiManager.getTextColor() + "Sn0w");
        });
        frame.addChild(watermark);
        watermark.setFontRenderer(fontRenderer);
        watermark.setShadow(true);
        frames.add(frame);
        for (Frame frame1 : frames) {
            frame1.setX(x);
            frame1.setY(y);
            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            x += frame1.getWidth() + 10;
            if ((float)(x * DisplayGuiScreen.getScale()) > (float)Wrapper.getMinecraft().displayWidth / 1.2f) {
                nexty = y = nexty;
                x = 10;
            }
            this.addChild(frame1);
        }
    }

    @Override
    public void destroyGUI() {
        this.kill();
    }
}

