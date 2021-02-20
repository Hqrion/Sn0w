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
        GlStateManager.func_179098_w();
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        for (Object o : Nametags3.mc.field_71441_e.field_73010_i) {
            Entity entity = (Entity)o;
            if (!(entity instanceof EntityPlayer) || entity == Nametags3.mc.field_71439_g || !entity.func_70089_S() || !((double)entity.func_70032_d((Entity)Nametags3.mc.field_71439_g) <= this.range.getValue())) continue;
            Vec3d m = Nametags3.renderPosEntity(entity);
            this.renderNameTagsFor((EntityPlayer)entity, m.field_72450_a, m.field_72448_b, m.field_72449_c);
        }
        GlStateManager.func_179090_x();
        RenderHelper.func_74518_a();
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
    }

    public void renderNameTagsFor(EntityPlayer entityPlayer, double n, double n2, double n3) {
        this.renderNametags(entityPlayer, n, n2, n3);
    }

    public static double timerPos(double n, double n2) {
        return n2 + (n - n2) * (double)Nametags3.mc.field_71428_T.field_194147_b;
    }

    public static Vec3d renderPosEntity(Entity entity) {
        return new Vec3d(Nametags3.timerPos(entity.field_70165_t, entity.field_70142_S), Nametags3.timerPos(entity.field_70163_u, entity.field_70137_T), Nametags3.timerPos(entity.field_70161_v, entity.field_70136_U));
    }

    private void renderEnchants(ItemStack itemStack, int x, int y) {
        Iterator iterator2;
        GlStateManager.func_179098_w();
        Iterator iterator = iterator2 = EnchantmentHelper.func_82781_a((ItemStack)itemStack).keySet().iterator();
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
            Nametags3.mc.field_71466_p.func_175063_a(this.stringForEnchants(enchantment3, EnchantmentHelper.func_77506_a((Enchantment)enchantment3, (ItemStack)itemStack)), (float)(x * 2), (float)y, new Color(255, 255, 255).getRGB());
            y += 8;
            iterator = iterator2;
        }
        if (itemStack.func_77973_b().equals(Items.field_151153_ao) && itemStack.func_77962_s()) {
            Nametags3.mc.field_71466_p.func_175063_a("God", (float)(x * 2), (float)y, new Color(195, 77, 65).getRGB());
        }
        GlStateManager.func_179090_x();
    }

    private String stringForEnchants(Enchantment enchantment, int n) {
        int n2;
        ResourceLocation resourceLocation = (ResourceLocation)Enchantment.field_185264_b.func_177774_c((Object)enchantment);
        String substring = resourceLocation == null ? enchantment.func_77320_a() : resourceLocation.toString();
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
        GlStateManager.func_179098_w();
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        Nametags3.mc.field_71466_p.func_175063_a(itemStack.func_82833_r(), (float)(-Nametags3.mc.field_71466_p.func_78256_a(itemStack.func_82833_r()) / 2), (float)y, new Color(255, 255, 255).getRGB());
        GlStateManager.func_179121_F();
        GlStateManager.func_179090_x();
    }

    private void renderItemDurability(ItemStack itemStack, int x, int y) {
        int maxDamage = itemStack.func_77958_k();
        float n3 = (float)(maxDamage - itemStack.func_77952_i()) / (float)maxDamage;
        float green = ((float)itemStack.func_77958_k() - (float)itemStack.func_77952_i()) / (float)itemStack.func_77958_k();
        if (green > 1.0f) {
            green = 1.0f;
        } else if (green < 0.0f) {
            green = 0.0f;
        }
        float red = 1.0f - green;
        GlStateManager.func_179098_w();
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        Nametags3.mc.field_71466_p.func_175063_a(new StringBuilder().insert(0, (int)(n3 * 100.0f)).append('%').toString(), (float)(x * 2), (float)y, new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB());
        GlStateManager.func_179121_F();
        GlStateManager.func_179090_x();
    }

    private void renderItems(ItemStack itemStack, int n, int n2, int n3) {
        GlStateManager.func_179098_w();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179086_m((int)256);
        GlStateManager.func_179126_j();
        GlStateManager.func_179118_c();
        int n4 = n3 > 4 ? (n3 - 4) * 8 / 2 : 0;
        Nametags3.mc.func_175599_af().field_77023_b = -150.0f;
        RenderHelper.func_74519_b();
        mc.func_175599_af().func_180450_b(itemStack, n, n2 + n4);
        mc.func_175599_af().func_175030_a(Nametags3.mc.field_71466_p, itemStack, n, n2 + n4);
        RenderHelper.func_74518_a();
        Nametags3.mc.func_175599_af().field_77023_b = 0.0f;
        KamiTessellator.prepareTags();
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        this.renderEnchants(itemStack, n, n2 - 24);
        GlStateManager.func_179121_F();
    }

    private void renderNametags(EntityPlayer entityPlayer, double n, double distance, double n2) {
        double tempY = distance;
        EntityPlayerSP entity2 = mc.func_175606_aa() == null ? Nametags3.mc.field_71439_g : mc.func_175606_aa();
        EntityPlayerSP entity = entity2;
        double posX = entity2.field_70165_t;
        double posY = entity2.field_70163_u;
        double posZ = entity2.field_70161_v;
        Vec3d m = Nametags3.renderPosEntity((Entity)entity2);
        entity2.field_70165_t = m.field_72450_a;
        entity2.field_70163_u = m.field_72448_b;
        entity2.field_70161_v = m.field_72449_c;
        distance = entity.func_70011_f(n, distance, n2);
        String[] text = new String[]{this.renderEntityName(entityPlayer)};
        KamiTessellator.drawNametag(n, (tempY += entityPlayer.func_70093_af() ? 0.5 : 0.7) + 1.4, n2, text, this.renderPing(entityPlayer), 2);
        ItemStack heldItemMainhand = entityPlayer.func_184614_ca();
        ItemStack heldItemOffhand = entityPlayer.func_184592_cb();
        int n10 = 0;
        int n11 = 0;
        boolean b = false;
        int i = 3;
        int n12 = 3;
        while (i >= 0) {
            ItemStack itemStack = (ItemStack)entityPlayer.field_71071_by.field_70460_b.get(n12);
            if (!itemStack.func_190926_b()) {
                int size;
                Boolean j = this.durability.getValue();
                n10 -= 8;
                if (j.booleanValue()) {
                    b = true;
                }
                if (this.armor.getValue().booleanValue() && (size = EnchantmentHelper.func_82781_a((ItemStack)itemStack).size()) > n11) {
                    n11 = size;
                }
            }
            i = --n12;
        }
        if (!heldItemOffhand.func_190926_b() && (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemOffhand.func_77984_f())) {
            int size2;
            n10 -= 8;
            if (this.durability.getValue().booleanValue() && heldItemOffhand.func_77984_f()) {
                b = true;
            }
            if (this.armor.getValue().booleanValue() && (size2 = EnchantmentHelper.func_82781_a((ItemStack)heldItemOffhand).size()) > n11) {
                n11 = size2;
            }
        }
        if (!heldItemMainhand.func_190926_b()) {
            Nametags3 nametags;
            int size3;
            if (this.armor.getValue().booleanValue() && (size3 = EnchantmentHelper.func_82781_a((ItemStack)heldItemMainhand).size()) > n11) {
                n11 = size3;
            }
            int k = this.armorValue(n11);
            if (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemMainhand.func_77984_f()) {
                n10 -= 8;
            }
            if (this.armor.getValue().booleanValue()) {
                ItemStack itemStack2 = heldItemMainhand;
                int n13 = n10;
                int n14 = k;
                k -= 32;
                this.renderItems(itemStack2, n13, n14, n11);
            }
            if (this.durability.getValue().booleanValue() && heldItemMainhand.func_77984_f()) {
                int n15 = k;
                this.renderItemDurability(heldItemMainhand, n10, k);
                k = n15 - Nametags3.mc.field_71466_p.field_78288_b;
                nametags = this;
            } else {
                if (b) {
                    k -= Nametags3.mc.field_71466_p.field_78288_b;
                }
                nametags = this;
            }
            if (nametags.itemName.getValue().booleanValue()) {
                this.renderItemName(heldItemMainhand, n10, k);
            }
            if (this.armor.getValue().booleanValue() || this.durability.getValue().booleanValue() && heldItemMainhand.func_77984_f()) {
                n10 += 16;
            }
        }
        int l = 3;
        int n16 = 3;
        while (l >= 0) {
            ItemStack itemStack3 = (ItemStack)entityPlayer.field_71071_by.field_70460_b.get(n16);
            if (!itemStack3.func_190926_b()) {
                int m2 = this.armorValue(n11);
                if (this.armor.getValue().booleanValue()) {
                    ItemStack itemStack4 = itemStack3;
                    int n17 = n10;
                    int n18 = m2;
                    m2 -= 32;
                    this.renderItems(itemStack4, n17, n18, n11);
                }
                if (this.durability.getValue().booleanValue() && itemStack3.func_77984_f()) {
                    this.renderItemDurability(itemStack3, n10, m2);
                }
                n10 += 16;
            }
            l = --n16;
        }
        if (!heldItemOffhand.func_190926_b()) {
            int m3 = this.armorValue(n11);
            if (this.armor.getValue().booleanValue()) {
                ItemStack itemStack5 = heldItemOffhand;
                int n19 = n10;
                int n20 = m3;
                m3 -= 32;
                this.renderItems(itemStack5, n19, n20, n11);
            }
            if (this.durability.getValue().booleanValue() && heldItemOffhand.func_77984_f()) {
                this.renderItemDurability(heldItemOffhand, n10, m3);
            }
            n10 += 16;
        }
        GlStateManager.func_179121_F();
        double posZ2 = posZ;
        EntityPlayerSP entity3 = entity;
        double posY2 = posY;
        entity.field_70165_t = posX;
        entity3.field_70163_u = posY2;
        entity3.field_70161_v = posZ2;
    }

    private String renderEntityName(EntityPlayer entityPlayer) {
        double d;
        String s = entityPlayer.func_145748_c_().func_150254_d();
        if (this.entityId.getValue().booleanValue()) {
            s = new StringBuilder().insert(0, s).append(" ID: ").append(entityPlayer.func_145782_y()).toString();
        }
        if (this.gamemode.getValue().booleanValue()) {
            s = entityPlayer.func_184812_l_() ? new StringBuilder().insert(0, s).append(" [C]").toString() : (entityPlayer.func_175149_v() ? new StringBuilder().insert(0, s).append(" [I]").toString() : new StringBuilder().insert(0, s).append(" [S]").toString());
        }
        if (this.ping.getValue().booleanValue() && mc.func_147114_u() != null && mc.func_147114_u().func_175102_a(entityPlayer.func_110124_au()) != null) {
            s = new StringBuilder().insert(0, s).append(" ").append(mc.func_147114_u().func_175102_a(entityPlayer.func_110124_au()).func_178853_c()).append("ms").toString();
        }
        if (!this.health.getValue().booleanValue()) {
            return s;
        }
        String s2 = TextFormatting.GREEN.toString();
        double ceil = Math.ceil(entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj());
        if (d > 0.0) {
            if (entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() <= 5.0f) {
                s2 = TextFormatting.RED.toString();
            } else if (entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() > 5.0f && entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() <= 10.0f) {
                s2 = TextFormatting.GOLD.toString();
            } else if (entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() > 10.0f && entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() <= 15.0f) {
                s2 = TextFormatting.YELLOW.toString();
            } else if (entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() > 15.0f && entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() <= 20.0f) {
                s2 = TextFormatting.DARK_GREEN.toString();
            } else if (entityPlayer.func_110143_aJ() + entityPlayer.func_110139_bj() > 20.0f) {
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
        if (Friends.isFriend(entityPlayer.func_70005_c_())) {
            return Color.blue;
        }
        if (entityPlayer.func_82150_aj()) {
            return new Color(128, 128, 128);
        }
        if (mc.func_147114_u() != null && mc.func_147114_u().func_175102_a(entityPlayer.func_110124_au()) == null) {
            return new Color(239, 1, 71);
        }
        if (entityPlayer.func_70093_af()) {
            return new Color(255, 153, 0);
        }
        return new Color(255, 255, 255);
    }
}

