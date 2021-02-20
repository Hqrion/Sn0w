/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.ArrayList;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.GeometryMasks;
import me.zeroeightsix.kami.util.HueCycler;
import me.zeroeightsix.kami.util.KamiTessellator;
import me.zeroeightsix.kami.util.TrajectoryCalculator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(name="Trajectories", category=Module.Category.RENDER)
public class Trajectories
extends Module {
    ArrayList<Vec3d> positions = new ArrayList();
    HueCycler cycler = new HueCycler(100);

    @Override
    public void onWorldRender(RenderEvent event) {
        try {
            Trajectories.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityLivingBase).map(entity -> (EntityLivingBase)entity).forEach(entity -> {
                this.positions.clear();
                TrajectoryCalculator.ThrowingType tt = TrajectoryCalculator.getThrowType(entity);
                if (tt == TrajectoryCalculator.ThrowingType.NONE) {
                    return;
                }
                TrajectoryCalculator.FlightPath flightPath = new TrajectoryCalculator.FlightPath((EntityLivingBase)entity, tt);
                while (!flightPath.isCollided()) {
                    flightPath.onUpdate();
                    this.positions.add(flightPath.position);
                }
                BlockPos hit = null;
                if (flightPath.getCollidingTarget() != null) {
                    hit = flightPath.getCollidingTarget().func_178782_a();
                }
                GL11.glEnable((int)3042);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                if (hit != null) {
                    KamiTessellator.prepare(7);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.3f);
                    KamiTessellator.drawBox(hit, 0x33FFFFFF, (int)GeometryMasks.FACEMAP.get(flightPath.getCollidingTarget().field_178784_b));
                    KamiTessellator.release();
                }
                if (this.positions.isEmpty()) {
                    return;
                }
                GL11.glDisable((int)3042);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glLineWidth((float)2.0f);
                if (hit != null) {
                    GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                } else {
                    this.cycler.setNext();
                }
                GL11.glBegin((int)1);
                Vec3d a = this.positions.get(0);
                GL11.glVertex3d((double)(a.field_72450_a - Trajectories.mc.func_175598_ae().field_78725_b), (double)(a.field_72448_b - Trajectories.mc.func_175598_ae().field_78726_c), (double)(a.field_72449_c - Trajectories.mc.func_175598_ae().field_78723_d));
                for (Vec3d v : this.positions) {
                    GL11.glVertex3d((double)(v.field_72450_a - Trajectories.mc.func_175598_ae().field_78725_b), (double)(v.field_72448_b - Trajectories.mc.func_175598_ae().field_78726_c), (double)(v.field_72449_c - Trajectories.mc.func_175598_ae().field_78723_d));
                    GL11.glVertex3d((double)(v.field_72450_a - Trajectories.mc.func_175598_ae().field_78725_b), (double)(v.field_72448_b - Trajectories.mc.func_175598_ae().field_78726_c), (double)(v.field_72449_c - Trajectories.mc.func_175598_ae().field_78723_d));
                    if (hit != null) continue;
                    this.cycler.setNext();
                }
                GL11.glEnd();
                GL11.glEnable((int)3042);
                GL11.glEnable((int)3553);
                this.cycler.reset();
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

