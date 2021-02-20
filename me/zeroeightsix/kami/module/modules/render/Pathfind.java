//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        AnchoredWalkNodeProcessor walkNodeProcessor = new AnchoredWalkNodeProcessor(new PathPoint((int)Pathfind.mc.player.posX, (int)Pathfind.mc.player.posY, (int)Pathfind.mc.player.posZ));
        EntityZombie zombie = new EntityZombie((World)Pathfind.mc.world);
        zombie.setPathPriority(PathNodeType.WATER, 16.0f);
        zombie.posX = Pathfind.mc.player.posX;
        zombie.posY = Pathfind.mc.player.posY;
        zombie.posZ = Pathfind.mc.player.posZ;
        PathFinder finder = new PathFinder((NodeProcessor)walkNodeProcessor);
        Path path = finder.findPath((IBlockAccess)Pathfind.mc.world, (EntityLiving)zombie, new BlockPos(end.x, end.y, end.z), Float.MAX_VALUE);
        zombie.setPathPriority(PathNodeType.WATER, 0.0f);
        if (path == null) {
            Command.sendChatMessage("Failed to create path!");
            return false;
        }
        points = new ArrayList<PathPoint>(Arrays.asList(path.points));
        return points.get(points.size() - 1).distanceTo(end) <= 1.0f;
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
        GlStateManager.disableDepth();
        GL11.glBegin((int)1);
        PathPoint first = points.get(0);
        GL11.glVertex3d((double)((double)first.x - Pathfind.mc.getRenderManager().renderPosX + 0.5), (double)((double)first.y - Pathfind.mc.getRenderManager().renderPosY), (double)((double)first.z - Pathfind.mc.getRenderManager().renderPosZ + 0.5));
        for (int i = 0; i < points.size() - 1; ++i) {
            PathPoint pathPoint = points.get(i);
            GL11.glVertex3d((double)((double)pathPoint.x - Pathfind.mc.getRenderManager().renderPosX + 0.5), (double)((double)pathPoint.y - Pathfind.mc.getRenderManager().renderPosY), (double)((double)pathPoint.z - Pathfind.mc.getRenderManager().renderPosZ + 0.5));
            if (i == points.size() - 1) continue;
            GL11.glVertex3d((double)((double)pathPoint.x - Pathfind.mc.getRenderManager().renderPosX + 0.5), (double)((double)pathPoint.y - Pathfind.mc.getRenderManager().renderPosY), (double)((double)pathPoint.z - Pathfind.mc.getRenderManager().renderPosZ + 0.5));
        }
        GL11.glEnd();
        GlStateManager.enableDepth();
    }

    @Override
    public void onUpdate() {
        PathPoint closest = points.stream().min(Comparator.comparing(pathPoint -> Pathfind.mc.player.getDistance((double)pathPoint.x, (double)pathPoint.y, (double)pathPoint.z))).orElse(null);
        if (closest == null) {
            return;
        }
        if (Pathfind.mc.player.getDistance((double)closest.x, (double)closest.y, (double)closest.z) > 0.8) {
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

        public PathPoint getStart() {
            return this.from;
        }

        public boolean getCanEnterDoors() {
            return true;
        }

        public boolean getCanSwim() {
            return true;
        }

        public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
            PathNodeType pathnodetype = this.getPathNodeTypeRaw(blockaccessIn, x, y, z);
            if (pathnodetype == PathNodeType.OPEN && y >= 1) {
                Block block = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
                PathNodeType pathnodetype1 = this.getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
                PathNodeType pathNodeType = pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
                if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.MAGMA) {
                    pathnodetype = PathNodeType.DAMAGE_FIRE;
                }
                if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
                    pathnodetype = PathNodeType.DAMAGE_CACTUS;
                }
            }
            pathnodetype = this.checkNeighborBlocks(blockaccessIn, x, y, z, pathnodetype);
            return pathnodetype;
        }

        protected PathNodeType getPathNodeTypeRaw(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
            BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
            IBlockState iblockstate = p_189553_1_.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            Material material = iblockstate.getMaterial();
            PathNodeType type2 = block.getAiPathNodeType(iblockstate, p_189553_1_, blockpos);
            if (type2 != null) {
                return type2;
            }
            if (material == Material.AIR) {
                return PathNodeType.OPEN;
            }
            if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY) {
                if (block == Blocks.FIRE) {
                    return PathNodeType.DAMAGE_FIRE;
                }
                if (block == Blocks.CACTUS) {
                    return PathNodeType.DAMAGE_CACTUS;
                }
                if (block instanceof BlockDoor && material == Material.WOOD && !((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue()) {
                    return PathNodeType.DOOR_WOOD_CLOSED;
                }
                if (block instanceof BlockDoor && material == Material.IRON && !((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue()) {
                    return PathNodeType.DOOR_IRON_CLOSED;
                }
                if (block instanceof BlockDoor && ((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue()) {
                    return PathNodeType.DOOR_OPEN;
                }
                if (block instanceof BlockRailBase) {
                    return PathNodeType.RAIL;
                }
                if (!(block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate && !((Boolean)iblockstate.getValue((IProperty)BlockFenceGate.OPEN)).booleanValue())) {
                    if (material == Material.WATER) {
                        return PathNodeType.WALKABLE;
                    }
                    if (material == Material.LAVA) {
                        return PathNodeType.LAVA;
                    }
                    return block.isPassable(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
                }
                return PathNodeType.FENCE;
            }
            return PathNodeType.TRAPDOOR;
        }
    }
}

