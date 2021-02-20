//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.text.TextFormatting
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

@Module.Info(name="Nametags", description="Draws descriptive nametags above entities", category=Module.Category.RENDER)
public class Nametags3
extends Module {
    private Setting<Double> range = this.register(Settings.d("Range", 200.0));
    private Setting<Boolean> health = this.register(Settings.b("Health", true));
    private Setting<Boolean> armor;
    private Setting<Boolean> durability = this.register(Settings.b("Durability", true));
    private Setting<Boolean> enchantnames = this.register(Settings.b("Enchant Names", true));
    private Setting<Boolean> itemName;
    private Setting<Boolean> entityId = this.register(Settings.b("Entity Ids", true));
    private Setting<Boolean> ping;
    public static Setting<Boolean> CustomColor = Settings.b("Custom Color", false);
    public static Setting<Integer> red = Settings.integerBuilder("Red").withMinimum(1).withValue(255).withMaximum(255).build();
    public static Setting<Integer> green = Settings.integerBuilder("Green").withMinimum(1).withValue(255).withMaximum(255).build();
    public static Setting<Integer> blue = Settings.integerBuilder("Blue").withMinimum(1).withValue(1).withMaximum(255).build();
    public static Setting<Integer> alpha = Settings.integerBuilder("Alpha").withMinimum(1).withValue(1).withMaximum(255).build();
    private Setting<Boolean> gamemode = this.register(Settings.b("gamemode", true));
    public static Nametags3 INSTANCE;

    public Nametags3() {
        this.ping = this.register(Settings.b("ping", true));
        this.armor = this.register(Settings.b("Armor", true));
        this.itemName = this.register(Settings.b("Itemnames", true));
        this.register(CustomColor);
        this.register(red);
        this.register(green);
        this.register(blue);
        this.register(alpha);
        INSTANCE = this;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        for (Object o : Nametags3.mc.world.playerEntities) {
            Entity entity = (Entity)o;
            if (!(entity instanceof EntityPlayer) || entity == Nametags3.mc.player || !entity.isEntityAlive() || !((double)entity.getDistance((Entity)Nametags3.mc.player) <= this.range.getValue())) continue;
            Vec3d m = Nametags3.renderPosEntity(entity);
            this.renderNameTagsFor((EntityPlayer)entity, m.x, m.y, m.z);
        }
        GlStateManager.disableTexture2D();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public void renderNameTagsFor(EntityPlayer entityPlayer, double n, double n2, double n3) {
        this.renderNametags(entityPlayer, n, n2, n3);
    }

    public static double timerPos(double n, double n2) {
        return n2 + (n - n2) * (double)Nametags3.mc.timer.renderPartialTicks;
    }

    public static Vec3d renderPosEntity(Entity entity) {
        return new Vec3d(Nametags3.timerPos(entity.posX, entity.lastTickPosX), Nametags3.timerPos(entity.posY, entity.lastTickPosY), Nametags3.timerPos(entity.posZ, entity.lastTickPosZ));
    }

    private void renderEnchants(ItemStack itemStack, int x, int y) {
        Iterator iterator2;
        GlStateManager.enableTexture2D();
        Iterator iterator = iterator2 = EnchantmentHelper.getEnchantments((ItemStack)itemStack).keySet().iterator();
        while (iterator.hasNext()) {
            Enchantment enchantment = (Enchantment)iterator2.next();
            if (enchantment == null) {
                iterator = iterator2;
                continue;
            }
            Enchantment enchantment3 = enchantment;
            if (!this.enchantnames.getValue().booleanValue()) {
                return;
            }
            Nametags3.mc.fontRenderer.drawStringWithShadow(this.stringForEnchants(enchantment3, EnchantmentHelper.getEnchantmentLevel((Enchantment)enchantment3, (ItemStack)itemStack)), (float)(x * 2), (float)y, new Color(255, 255, 255).getRGB());
            y += 8;
            iterator = iterator2;
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            Nametags3.mc.fontRenderer.drawStringWithShadow("God", (float)(x * 2), (float)y, new Color(195, 77, 65).getRGB());
        }
        GlStateManager.disableTexture2D();
    }

    private String stringForEnchants(Enchantment enchantment, int n) {
        int n2;
        ResourceLocation resourceLocation = (ResourceLocation)Enchantment.REGISTRY.getNameForObject((Object)enchantment);
        String substring = resourceLocation == null ? enchantment.getName() : resourceLocation.toString();
        int n3 = n2 = n > 1 ? 12 : 13;
        if (substring.length() > n2) {
            substring = substring.substring(10, n2);
        }
        StringBuilder sb = new StringBuilder();
        String s = substring;
        boolean n32 = false;
        String s2 = sb.insert(0, s.substring(0, 1).toUpperCase()).append(substring.substring(1)).toString();
        if (n > 1) {
            s2 = new StringBuilder().insert(0, s2).append(n).toString();
        }
        return s2;
    }

    private void renderItemName(ItemStack itemStack, int x, int y) {
        GlStateManager.enableTexture2D();
        GlStateManager.pushMatrix();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        Nametags3.mc.fontRenderer.drawStringWithShadow(itemStack.getDisplayName(), (float)(-Nametags3.mc.fontRenderer.getStringWidth(itemStack.getDisplayName()) / 2), (float)y, new Color(255, 255, 255).getRGB());
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
    }

    private void renderItemDurability(ItemStack itemStack, int x, int y) {
        int maxDamage = itemStack.getMaxDamage();
        float n3 = (float)(maxDamage - itemStack.getItemDamage()) / (float)maxDamage;
        float green = ((float)itemStack.getMaxDamage() - (float)itemStack.getItemDamage()) / (float)itemStack.getMaxDamage();
        if (green > 1.0f) {
            green = 1.0f;
        } else if (green < 0.0f) {
            green = 0.0f;
        }
        float red = 1.0f - green;
        GlStateManager.enableTexture2D();
        GlStateManager.pushMatrix();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        Nametags3.mc.fontRenderer.drawStringWithShadow(new StringBuilder().insert(0, (int)(n3 * 100.0f)).append('%').toString(), (float)(x * 2), (float)y, new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB());
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
    }

    private void renderItems(ItemStack itemStack, int n, int n2, int n3) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        int n4 = n3 > 4 ? (n3 - 4) * 8 / 2 : 0;
        Nametags3.mc.getRenderItem().zLevel = -150.0f;
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        mc.getRenderItem().renderItemOverlays(Nametags3.mc.fontRenderer, itemStack, n, n2 + n4);
        RenderHelper.disableStandardItemLighting();
        Nametags3.mc.getRenderItem().zLevel = 0.0f;
        KamiTessellator.prepareTags();
        GlStateManager.pushMatrix();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        this.renderEnchants(itemStack, n, n2 - 24);
        GlStateManager.popMatrix();
    }

    private void renderNametags(EntityPlayer entityPlayer, double n, double distance, double n2) {
        double tempY = distance;
        EntityPlayerSP entity2 = mc.getRenderViewEntity() == null ? Nametags3.mc.player : mc.getRenderViewEntity();
        EntityPlayerSP entity = entity2;
        double posX = entity2.posX;
        double posY = entity2.posY;
        double posZ = entity2.posZ;
        Vec3d m = Nametags3.renderPosEntity((Entity)entity2);
        entity2.posX = m.x;
        entity2.posY = m.y;
        entity2.posZ = m.z;
        distance = entity.getDistance(n, distance, n2);
        String[] text = new String[]{this.renderEntityName(entityPlayer)};
        KamiTessellator.drawNametag(n, (tempY += entityPlayer.isSneaking() ? 0.5 : 0.7) + 1.4, n2, text, this.renderPing(entityPlayer), 2);
        ItemStack heldItemMainhand = entityPlayer.getHeldItemMainhand();
        ItemStack heldItemOffhand = entityPlayer.getHeldItemOffhand();
        int n10 = 0;
        int n11 = 0;
        boolean b = false;
        int i = 3;
        int n12 = 3;
        while (i >= 0) {
            ItemStack itemStack = (ItemStack)entityPlayer.inventory.armorInventory.get(n12);
            if (!itemStack.isEmpty()) {
                int size;
                Boolean j = this.durability.getValue();
                n10 -= 8;
                if (j.booleanValue()) {
                    b = true;
                }
                if (this.armor.getValue().booleanValue() && (size = EnchantmentHelper.getEnchantments((ItemStack)itemStack).size()) > n11) {
                    n11 = size;
                }
            }
            i = --n12;
        }
        if (!heldItemOffhand.isEmpty() && (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemOffhand.isItemStackDamageable())) {
            int size2;
            n10 -= 8;
            if (this.durability.getValue().booleanValue() && heldItemOffhand.isItemStackDamageable()) {
                b = true;
            }
            if (this.armor.getValue().booleanValue() && (size2 = EnchantmentHelper.getEnchantments((ItemStack)heldItemOffhand).size()) > n11) {
                n11 = size2;
            }
        }
        if (!heldItemMainhand.isEmpty()) {
            Nametags3 nametags;
            int size3;
            if (this.armor.getValue().booleanValue() && (size3 = EnchantmentHelper.getEnchantments((ItemStack)heldItemMainhand).size()) > n11) {
                n11 = size3;
            }
            int k = this.armorValue(n11);
            if (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemMainhand.isItemStackDamageable()) {
                n10 -= 8;
            }
            if (this.armor.getValue().booleanValue()) {
                ItemStack itemStack2 = heldItemMainhand;
                int n13 = n10;
                int n14 = k;
                k -= 32;
                this.renderItems(itemStack2, n13, n14, n11);
            }
            if (this.durability.getValue().booleanValue() && heldItemMainhand.isItemStackDamageable()) {
                int n15 = k;
                this.renderItemDurability(heldItemMainhand, n10, k);
                k = n15 - Nametags3.mc.fontRenderer.FONT_HEIGHT;
                nametags = this;
            } else {
                if (b) {
                    k -= Nametags3.mc.fontRenderer.FONT_HEIGHT;
                }
                nametags = this;
            }
            if (nametags.itemName.getValue().booleanValue()) {
                this.renderItemName(heldItemMainhand, n10, k);
            }
            if (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemMainhand.isItemStackDamageable()) {
                n10 += 16;
            }
        }
        int l = 3;
        int n16 = 3;
        while (l >= 0) {
            ItemStack itemStack3 = (ItemStack)entityPlayer.inventory.armorInventory.get(n16);
            if (!itemStack3.isEmpty()) {
                int m2 = this.armorValue(n11);
                if (this.armor.getValue().booleanValue()) {
                    ItemStack itemStack4 = itemStack3;
                    int n17 = n10;
                    int n18 = m2;
                    m2 -= 32;
                    this.renderItems(itemStack4, n17, n18, n11);
                }
                if (this.durability.getValue().booleanValue() && itemStack3.isItemStackDamageable()) {
                    this.renderItemDurability(itemStack3, n10, m2);
                }
                n10 += 16;
            }
            l = --n16;
        }
        if (!heldItemOffhand.isEmpty()) {
            int m3 = this.armorValue(n11);
            if (this.armor.getValue().booleanValue()) {
                ItemStack itemStack5 = heldItemOffhand;
                int n19 = n10;
                int n20 = m3;
                m3 -= 32;
                this.renderItems(itemStack5, n19, n20, n11);
            }
            if (this.durability.getValue().booleanValue() && heldItemOffhand.isItemStackDamageable()) {
                this.renderItemDurability(heldItemOffhand, n10, m3);
            }
            n10 += 16;
        }
        GlStateManager.popMatrix();
        double posZ2 = posZ;
        EntityPlayerSP entity3 = entity;
        double posY2 = posY;
        entity.posX = posX;
        entity3.posY = posY2;
        entity3.posZ = posZ2;
    }

    private String renderEntityName(EntityPlayer entityPlayer) {
        double d;
        String s = entityPlayer.getDisplayName().getFormattedText();
        if (this.entityId.getValue().booleanValue()) {
            s = new StringBuilder().insert(0, s).append(" ID: ").append(entityPlayer.getEntityId()).toString();
        }
        if (this.gamemode.getValue().booleanValue()) {
            s = entityPlayer.isCreative() ? new StringBuilder().insert(0, s).append(" [C]").toString() : (entityPlayer.isSpectator() ? new StringBuilder().insert(0, s).append(" [I]").toString() : new StringBuilder().insert(0, s).append(" [S]").toString());
        }
        if (this.ping.getValue().booleanValue() && mc.getConnection() != null && mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) != null) {
            s = new StringBuilder().insert(0, s).append(" ").append(mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime()).append("ms").toString();
        }
        if (!this.health.getValue().booleanValue()) {
            return s;
        }
        String s2 = TextFormatting.GREEN.toString();
        double ceil = Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount());
        if (d > 0.0) {
            if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 5.0f) {
                s2 = TextFormatting.RED.toString();
            } else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 5.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 10.0f) {
                s2 = TextFormatting.GOLD.toString();
            } else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 10.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 15.0f) {
                s2 = TextFormatting.YELLOW.toString();
            } else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 15.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 20.0f) {
                s2 = TextFormatting.DARK_GREEN.toString();
            } else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 20.0f) {
                s2 = TextFormatting.GREEN.toString();
            }
        } else {
            s2 = TextFormatting.DARK_RED.toString();
        }
        return new StringBuilder().insert(0, s).append(s2).append(" ").append(ceil > 0.0 ? Integer.valueOf((int)ceil) : "0").toString();
    }

    private int armorValue(int n) {
        int n2;
        int n3 = n2 = this.armor.getValue() != false ? -26 : -27;
        if (n > 4) {
            n2 -= (n - 4) * 8;
        }
        return n2;
    }

    private Color renderPing(EntityPlayer entityPlayer) {
        if (Friends.isFriend(entityPlayer.getName())) {
            return Color.blue;
        }
        if (entityPlayer.isInvisible()) {
            return new Color(128, 128, 128);
        }
        if (mc.getConnection() != null && mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) == null) {
            return new Color(239, 1, 71);
        }
        if (entityPlayer.isSneaking()) {
            return new Color(255, 153, 0);
        }
        return new Color(255, 255, 255);
    }
}

