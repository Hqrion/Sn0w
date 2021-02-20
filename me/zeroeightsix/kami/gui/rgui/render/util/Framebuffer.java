/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL30
 *  org.lwjgl.opengl.GL32
 */
package me.zeroeightsix.kami.gui.rgui.render.util;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class Framebuffer {
    private int WIDTH = Display.getWidth();
    private int HEIGHT = Display.getHeight();
    private int framebufferID;
    private int framebufferTexture;
    private int framebufferDepthbuffer;

    public Framebuffer() {
        this(Display.getWidth(), Display.getHeight());
    }

    public Framebuffer(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.initialiseFramebuffer();
    }

    public void cleanUp() {
        GL30.glDeleteFramebuffers((int)this.framebufferID);
        GL11.glDeleteTextures((int)this.framebufferTexture);
        GL30.glDeleteRenderbuffers((int)this.framebufferDepthbuffer);
    }

    public void bindFrameBuffer() {
        this.bindFrameBuffer(this.framebufferID, this.WIDTH, this.HEIGHT);
    }

    public void unbindFramebuffer() {
        GL30.glBindFramebuffer((int)36160, (int)0);
        GL11.glViewport((int)0, (int)0, (int)Display.getWidth(), (int)Display.getHeight());
    }

    public int getFramebufferTexture() {
        return this.framebufferTexture;
    }

    private void initialiseFramebuffer() {
        this.framebufferID = this.createFrameBuffer();
        this.framebufferTexture = this.createTextureAttachment(this.WIDTH, this.HEIGHT);
        this.framebufferDepthbuffer = this.createDepthBufferAttachment(this.WIDTH, this.HEIGHT);
        this.unbindFramebuffer();
    }

    private void bindFrameBuffer(int frameBuffer, int width, int height) {
        GL11.glBindTexture((int)3553, (int)0);
        GL30.glBindFramebuffer((int)36160, (int)frameBuffer);
        GL11.glViewport((int)0, (int)0, (int)width, (int)height);
    }

    private int createFrameBuffer() {
        int frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer((int)36160, (int)frameBuffer);
        GL11.glDrawBuffer((int)36064);
        return frameBuffer;
    }

    private int createTextureAttachment(int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)texture);
        GL11.glTexImage2D((int)3553, (int)0, (int)6407, (int)width, (int)height, (int)0, (int)6407, (int)5121, (ByteBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL32.glFramebufferTexture((int)36160, (int)36064, (int)texture, (int)0);
        return texture;
    }

    private int createDepthTextureAttachment(int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)texture);
        GL11.glTexImage2D((int)3553, (int)0, (int)33191, (int)width, (int)height, (int)0, (int)6402, (int)5126, (ByteBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL32.glFramebufferTexture((int)36160, (int)36096, (int)texture, (int)0);
        return texture;
    }

    private int createDepthBufferAttachment(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer((int)36161, (int)depthBuffer);
        GL30.glRenderbufferStorage((int)36161, (int)6402, (int)width, (int)height);
        GL30.glFramebufferRenderbuffer((int)36160, (int)36096, (int)36161, (int)depthBuffer);
        return depthBuffer;
    }

    public void framebufferClear() {
        this.bindFrameBuffer();
        GL11.glClear((int)16384);
        this.unbindFramebuffer();
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }
}

