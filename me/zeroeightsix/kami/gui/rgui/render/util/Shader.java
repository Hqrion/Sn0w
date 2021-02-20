/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBShaderObjects
 */
package me.zeroeightsix.kami.gui.rgui.render.util;

import java.util.HashMap;
import java.util.Map;
import me.zeroeightsix.kami.gui.rgui.render.util.ShaderHelper;
import me.zeroeightsix.kami.gui.rgui.render.util.Uniform;
import org.lwjgl.opengl.ARBShaderObjects;

public abstract class Shader {
    private final Map<String, Uniform> uniforms = new HashMap<String, Uniform>();
    private final int programID = ARBShaderObjects.glCreateProgramObjectARB();
    private final int fragmentID;
    private final int vertexID;

    public Shader(String vertex, String fragment) {
        this.vertexID = ShaderHelper.loadShader(vertex, 35633);
        this.fragmentID = ShaderHelper.loadShader(fragment, 35632);
        ARBShaderObjects.glAttachObjectARB((int)this.programID, (int)this.vertexID);
        ARBShaderObjects.glAttachObjectARB((int)this.programID, (int)this.fragmentID);
        ShaderHelper.createProgram(this.programID);
    }

    public final void attach() {
        ARBShaderObjects.glUseProgramObjectARB((int)this.programID);
        this.update();
    }

    public final void detach() {
        ARBShaderObjects.glUseProgramObjectARB((int)0);
    }

    public abstract void update();

    public final void delete() {
        ARBShaderObjects.glUseProgramObjectARB((int)0);
        ARBShaderObjects.glDetachObjectARB((int)this.programID, (int)this.vertexID);
        ARBShaderObjects.glDetachObjectARB((int)this.programID, (int)this.fragmentID);
        ARBShaderObjects.glDeleteObjectARB((int)this.vertexID);
        ARBShaderObjects.glDeleteObjectARB((int)this.fragmentID);
        ARBShaderObjects.glDeleteObjectARB((int)this.programID);
    }

    protected final Uniform getUniform(String name) {
        return this.uniforms.computeIfAbsent(name, n -> Uniform.get(this.programID, n));
    }
}

