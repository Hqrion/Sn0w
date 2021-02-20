/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockDoor
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockFenceGate
 *  net.minecraft.block.BlockRailBase
 *  net.minecraft.block.BlockWall
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.init.Blocks
 *  net.minecraft.pathfinding.NodeProcessor
 *  net.minecraft.pathfinding.Path
 *  net.minecraft.pathfinding.PathFinder
 *  net.minecraft.pathfinding.PathNodeType
 *  net.minecraft.pathfinding.PathPoint
 *  net.minecraft.pathfinding.WalkNodeProcessor
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@Module.Info(name="Pathfind", category=Module.Category.HIDDEN)
public class Pathfind
extends Module {
    public static ArrayList<PathPoint> points = new ArrayList();
    static PathPoint to = null;

    public static boolean createPath(PathPoint end) {
        to = end;
        AnchoredWalkNodeProcessor walkNodeProcessor = new AnchoredWalkNodeProcessor(new PathPoint((int)Pathfind.mc.field_71439_g.field_70165_t, (int)Pathfind.mc.field_71439_g.field_70163_u, (int)Pathfind.mc.field_71439_g.field_70161_v));
        EntityZombie zombie = new EntityZombie((World)Pathfind.mc.field_71441_e);
        zombie.func_184644_a(PathNodeType.WATER, 16.0f);
        zombie.field_70165_t = Pathfind.mc.field_71439_g.field_70165_t;
        zombie.field_70163_u = Pathfind.mc.field_71439_g.field_70163_u;
        zombie.field_70161_v = Pathfind.mc.field_71439_g.field_70161_v;
        PathFinder finder = new PathFinder((NodeProcessor)walkNodeProcessor);
        Path path = finder.func_186336_a((IBlockAccess)Pathfind.mc.field_71441_e, (EntityLiving)zombie, new BlockPos(end.field_75839_a, end.field_75837_b, end.field_75838_c), Float.MAX_VALUE);
        zombie.func_184644_a(PathNodeType.WATER, 0.0f);
        if (path == null) {
            Command.sendChatMessage("Failed to create path!");
            return false;
        }
        points = new ArrayList<PathPoint>(Arrays.asList(path.field_75884_a));
        return points.get(points.size() - 1).func_75829_a(end) <= 1.0f;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (points.isEmpty()) {
            return;
        }
        GL11.glDisable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.5f);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179097_i();
        GL11.glBegin((int)1);
        PathPoint first = points.get(0);
        GL11.glVertex3d((double)((double)first.field_75839_a - Pathfind.mc.func_175598_ae().field_78725_b + 0.5), (double)((double)first.field_75837_b - Pathfind.mc.func_175598_ae().field_78726_c), (double)((double)first.field_75838_c - Pathfind.mc.func_175598_ae().field_78723_d + 0.5));
        for (int i = 0; i < points.size() - 1; ++i) {
            PathPoint pathPoint = points.get(i);
            GL11.glVertex3d((double)((double)pathPoint.field_75839_a - Pathfind.mc.func_175598_ae().field_78725_b + 0.5), (double)((double)pathPoint.field_75837_b - Pathfind.mc.func_175598_ae().field_78726_c), (double)((double)pathPoint.field_75838_c - Pathfind.mc.func_175598_ae().field_78723_d + 0.5));
            if (i == points.size() - 1) continue;
            GL11.glVertex3d((double)((double)pathPoint.field_75839_a - Pathfind.mc.func_175598_ae().field_78725_b + 0.5), (double)((double)pathPoint.field_75837_b - Pathfind.mc.func_175598_ae().field_78726_c), (double)((double)pathPoint.field_75838_c - Pathfind.mc.func_175598_ae().field_78723_d + 0.5));
        }
        GL11.glEnd();
        GlStateManager.func_179126_j();
    }

    @Override
    public void onUpdate() {
        PathPoint closest = points.stream().min(Comparator.comparing(pathPoint -> Pathfind.mc.field_71439_g.func_70011_f((double)pathPoint.field_75839_a, (double)pathPoint.field_75837_b, (double)pathPoint.field_75838_c))).orElse(null);
        if (closest == null) {
            return;
        }
        if (Pathfind.mc.field_71439_g.func_70011_f((double)closest.field_75839_a, (double)closest.field_75837_b, (double)closest.field_75838_c) > 0.8) {
            return;
        }
        Iterator<PathPoint> iterator = points.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == closest) {
                iterator.remove();
                break;
            }
            iterator.remove();
        }
        if (points.size() <= 1 && to != null) {
            boolean flag;
            boolean b = Pathfind.createPath(to);
            boolean bl = flag = points.size() <= 4;
            if (b && flag || flag) {
                points.clear();
                to = null;
                if (b) {
                    Command.sendChatMessage("Arrived!");
                } else {
                    Command.sendChatMessage("Can't go on: pathfinder has hit dead end");
                }
            }
        }
    }

    private static class AnchoredWalkNodeProcessor
    extends WalkNodeProcessor {
        PathPoint from;

        public AnchoredWalkNodeProcessor(PathPoint from) {
            this.from = from;
        }

        public PathPoint func_186318_b() {
            return this.from;
        }

        public boolean func_186323_c() {
            return true;
        }

        public boolean func_186322_e() {
            return true;
        }

        public PathNodeType func_186330_a(IBlockAccess blockaccessIn, int x, int y, int z) {
            PathNodeType pathnodetype = this.func_189553_b(blockaccessIn, x, y, z);
            if (pathnodetype == PathNodeType.OPEN && y >= 1) {
                Block block = blockaccessIn.func_180495_p(new BlockPos(x, y - 1, z)).func_177230_c();
                PathNodeType pathnodetype1 = this.func_189553_b(blockaccessIn, x, y - 1, z);
                PathNodeType pathNodeType = pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
                if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.field_189877_df) {
                    pathnodetype = PathNodeType.DAMAGE_FIRE;
                }
                if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
                    pathnodetype = PathNodeType.DAMAGE_CACTUS;
                }
            }
            pathnodetype = this.func_193578_a(blockaccessIn, x, y, z, pathnodetype);
            return pathnodetype;
        }

        protected PathNodeType func_189553_b(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
            BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
            IBlockState iblockstate = p_189553_1_.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            Material material = iblockstate.func_185904_a();
            PathNodeType type2 = block.getAiPathNodeType(iblockstate, p_189553_1_, blockpos);
            if (type2 != null) {
                return type2;
            }
            if (material == Material.field_151579_a) {
                return PathNodeType.OPEN;
            }
            if (block != Blocks.field_150415_aT && block != Blocks.field_180400_cw && block != Blocks.field_150392_bi) {
                if (block == Blocks.field_150480_ab) {
                    return PathNodeType.DAMAGE_FIRE;
                }
                if (block == Blocks.field_150434_aF) {
                    return PathNodeType.DAMAGE_CACTUS;
                }
                if (block instanceof BlockDoor && material == Material.field_151575_d && !((Boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)).booleanValue()) {
                    return PathNodeType.DOOR_WOOD_CLOSED;
                }
                if (block instanceof BlockDoor && material == Material.field_151573_f && !((Boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)).booleanValue()) {
                    return PathNodeType.DOOR_IRON_CLOSED;
                }
                if (block instanceof BlockDoor && ((Boolean)iblockstate.func_177229_b((IProperty)BlockDoor.field_176519_b)).booleanValue()) {
                    return PathNodeType.DOOR_OPEN;
                }
                if (block instanceof BlockRailBase) {
                    return PathNodeType.RAIL;
                }
                if (!(block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate && !((Boolean)iblockstate.func_177229_b((IProperty)BlockFenceGate.field_176466_a)).booleanValue())) {
                    if (material == Material.field_151586_h) {
                        return PathNodeType.WALKABLE;
                    }
                    if (material == Material.field_151587_i) {
                        return PathNodeType.LAVA;
                    }
                    return block.func_176205_b(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
                }
                return PathNodeType.FENCE;
            }
            return PathNodeType.TRAPDOOR;
        }
    }
}

