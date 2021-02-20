/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBShaderObjects
 */
package me.zeroeightsix.kami.gui.rgui.render.util;

import me.zeroeightsix.kami.gui.rgui.render.util.StreamReader;
import org.lwjgl.opengl.ARBShaderObjects;

public final class ShaderHelper {
    private ShaderHelper() {
    }

    public static void createProgram(int programID) {
        ARBShaderObjects.glLinkProgramARB((int)programID);
        ShaderHelper.checkObjecti(programID, 35714);
        ARBShaderObjects.glValidateProgramARB((int)programID);
        ShaderHelper.checkObjecti(programID, 35715);
    }

    public static int loadShader(String path, int type2) {
        int shaderID = ARBShaderObjects.glCreateShaderObjectARB((int)type2);
        if (shaderID == 0) {
            return 0;
        }
        String src = new StreamReader(ShaderHelper.class.getResourceAsStream(path)).read();
        ARBShaderObjects.glShaderSourceARB((int)shaderID, (CharSequence)src);
        ARBShaderObjects.glCompileShaderARB((int)shaderID);
        ShaderHelper.checkObjecti(shaderID, 35713);
        return shaderID;
    }

    private static String getLogInfo(int objID) {
        return ARBShaderObjects.glGetInfoLogARB((int)objID, (int)ARBShaderObjects.glGetObjectParameteriARB((int)objID, (int)35716));
    }

    private static void checkObjecti(int objID, int name) {
        if (ARBShaderObjects.glGetObjectParameteriARB((int)objID, (int)name) == 0) {
            try {
                throw new Exception(ShaderHelper.getLogInfo(objID));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

