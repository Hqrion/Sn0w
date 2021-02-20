/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Module.Info(name="8b8t AutoDupe", description="Make Bartender Useless\u00e2\u201e\u00a2", category=Module.Category.HIDDEN)
public class AutoDupeEightB
extends Module {
    static final int timeoutMillis = 5000;
    public static WaitPhase currentWaitPhase = WaitPhase.NONE;
    static long startTimeStamp = 0L;
    static int countBefore = 0;
    static int idBefore = 0;
    static int slotBefore = 0;
    static final int clickIntervalMillis = 300;
    static long lastClickStamp = System.currentTimeMillis();
    public static final InventoryUtils INVENTORY_UTILS = new InventoryUtils();

    static boolean inProgress() {
        return currentWaitPhase != WaitPhase.NONE;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public void OnUpdate(TickEvent.ClientTickEvent event) {
        if (!AutoDupeEightB.inProgress() || Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        if (System.currentTimeMillis() - startTimeStamp > 5000L) {
            AutoDupeEightB.abort("Dupe failed. Timed out.");
        }
        if (currentWaitPhase == WaitPhase.DROP) {
            if (AutoDupeEightB.mc.field_71439_g.field_71071_by.func_70448_g().func_190926_b()) {
                return;
            }
            if (System.currentTimeMillis() - startTimeStamp < 120L) {
                if (!AutoDupeEightB.mc.field_71439_g.func_192035_E().func_192812_b()) {
                    AutoDupeEightB.mc.field_71439_g.func_192035_E().func_192813_a(true);
                }
                return;
            }
            idBefore = Item.func_150891_b((Item)AutoDupeEightB.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b());
            countBefore = INVENTORY_UTILS.countItem(0, 8, idBefore);
            slotBefore = AutoDupeEightB.mc.field_71439_g.field_71071_by.field_70461_c;
            INVENTORY_UTILS.throwAllInSlot(slotBefore + 36, 500L);
            mc.func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)AutoDupeEightB.mc.field_71439_g));
            if (!AutoDupeEightB.mc.field_71439_g.func_192035_E().func_192812_b()) {
                return;
            }
            currentWaitPhase = WaitPhase.WAIT_PICKUP;
        } else if (currentWaitPhase == WaitPhase.WAIT_PICKUP) {
            if (System.currentTimeMillis() - lastClickStamp < 300L) {
                return;
            }
            lastClickStamp = System.currentTimeMillis();
        }
    }

    private static void abort(String msg) {
        Minecraft mc = Minecraft.func_71410_x();
        Command.sendChatMessage(msg);
        currentWaitPhase = WaitPhase.NONE;
        mc.field_71439_g.func_192035_E().func_192813_a(false);
        mc.func_147108_a(null);
    }

    private static void dupeCurrent() {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        currentWaitPhase = WaitPhase.DROP;
        startTimeStamp = System.currentTimeMillis();
    }

    public static void clickAction(String title) {
        switch (title) {
            case "dupe current": {
                AutoDupeEightB.dupeCurrent();
                break;
            }
        }
    }

    public static enum WaitPhase {
        NONE,
        DROP,
        WAIT_PICKUP;

    }
}

