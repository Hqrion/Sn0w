/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBShaderObjects
 *  org.lwjgl.util.vector.Vector2f
 *  org.lwjgl.util.vector.Vector3f
 */
package me.zeroeightsix.kami.gui.rgui.render.util;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public final class Uniform {
    private final String name;
    private final int location;

    private Uniform(String name, int location) {
        this.name = name;
        this.location = location;
    }

    public final void setInt(int value) {
        ARBShaderObjects.glUniform1iARB((int)this.location, (int)value);
    }

    public final void setFloat(float value) {
        ARBShaderObjects.glUniform1fARB((int)this.location, (float)value);
    }

    public final void setBoolean(boolean value) {
        ARBShaderObjects.glUniform1fARB((int)this.location, (float)(value ? 1.0f : 0.0f));
    }

    public final void setVec(Vector2f value) {
        ARBShaderObjects.glUniform2fARB((int)this.location, (float)value.x, (float)value.y);
    }

    public final void setVec(Vector3f value) {
        ARBShaderObjects.glUniform3fARB((int)this.location, (float)value.x, (float)value.y, (float)value.z);
    }

    public final String getName() {
        return this.name;
    }

    public final int getLocation() {
        return this.location;
    }

    public static Uniform get(int shaderID, String uniformName) {
        return new Uniform(uniformName, ARBShaderObjects.glGetUniformLocationARB((int)shaderID, (CharSequence)uniformName));
    }
}

