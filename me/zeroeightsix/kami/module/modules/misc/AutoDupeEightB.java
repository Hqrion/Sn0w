//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (!AutoDupeEightB.inProgress() || Minecraft.getMinecraft().player == null) {
            return;
        }
        if (System.currentTimeMillis() - startTimeStamp > 5000L) {
            AutoDupeEightB.abort("Dupe failed. Timed out.");
        }
        if (currentWaitPhase == WaitPhase.DROP) {
            if (AutoDupeEightB.mc.player.inventory.getCurrentItem().isEmpty()) {
                return;
            }
            if (System.currentTimeMillis() - startTimeStamp < 120L) {
                if (!AutoDupeEightB.mc.player.getRecipeBook().isGuiOpen()) {
                    AutoDupeEightB.mc.player.getRecipeBook().setGuiOpen(true);
                }
                return;
            }
            idBefore = Item.getIdFromItem((Item)AutoDupeEightB.mc.player.inventory.getCurrentItem().getItem());
            countBefore = INVENTORY_UTILS.countItem(0, 8, idBefore);
            slotBefore = AutoDupeEightB.mc.player.inventory.currentItem;
            INVENTORY_UTILS.throwAllInSlot(slotBefore + 36, 500L);
            mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)AutoDupeEightB.mc.player));
            if (!AutoDupeEightB.mc.player.getRecipeBook().isGuiOpen()) {
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
        Minecraft mc = Minecraft.getMinecraft();
        Command.sendChatMessage(msg);
        currentWaitPhase = WaitPhase.NONE;
        mc.player.getRecipeBook().setGuiOpen(false);
        mc.displayGuiScreen(null);
    }

    private static void dupeCurrent() {
        if (Minecraft.getMinecraft().player == null) {
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

