/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.rgui.component.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.poof.use.AdditionPoof;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import org.lwjgl.opengl.GL11;

public abstract class AbstractContainer
extends AbstractComponent
implements Container {
    protected ArrayList<Component> children = new ArrayList();
    int originoffsetX = 0;
    int originoffsetY = 0;

    public AbstractContainer(Theme theme) {
        this.setTheme(theme);
    }

    @Override
    public ArrayList<Component> getChildren() {
        return this.children;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Container addChild(Component ... components) {
        for (Component component : components) {
            if (this.children.contains(component)) continue;
            component.setTheme(this.getTheme());
            component.setParent(this);
            component.getUI().handleAddComponent(component, this);
            component.getUI().handleSizeComponent(component);
            ArrayList<Component> arrayList = this.children;
            synchronized (arrayList) {
                this.children.add(component);
                Collections.sort(this.children, new Comparator<Component>(){

                    @Override
                    public int compare(Component o1, Component o2) {
                        return o1.getPriority() - o2.getPriority();
                    }
                });
                component.callPoof(AdditionPoof.class, null);
            }
        }
        return this;
    }

    @Override
    public Container removeChild(Component component) {
        if (this.children.contains(component)) {
            this.children.remove(component);
        }
        return this;
    }

    @Override
    public boolean hasChild(Component component) {
        return this.children.contains(component);
    }

    @Override
    public void renderChildren() {
        for (Component c : this.getChildren()) {
            if (!c.isVisible()) continue;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)c.getX(), (float)c.getY(), (float)0.0f);
            c.getRenderListeners().forEach(RenderListener::onPreRender);
            c.getUI().renderComponent(c, this.getTheme().getFontRenderer());
            if (c instanceof Container) {
                GL11.glTranslatef((float)((Container)c).getOriginOffsetX(), (float)((Container)c).getOriginOffsetY(), (float)0.0f);
                ((Container)c).renderChildren();
                GL11.glTranslatef((float)(-((Container)c).getOriginOffsetX()), (float)(-((Container)c).getOriginOffsetY()), (float)0.0f);
            }
            c.getRenderListeners().forEach(RenderListener::onPostRender);
            GL11.glTranslatef((float)(-c.getX()), (float)(-c.getY()), (float)0.0f);
            GL11.glPopMatrix();
        }
    }

    @Override
    public Component getComponentAt(int x, int y) {
        for (int i = this.getChildren().size() - 1; i >= 0; --i) {
            Container container;
            Component c = this.getChildren().get(i);
            if (!c.isVisible()) continue;
            int componentX = c.getX() + this.getOriginOffsetX();
            int componentY = c.getY() + this.getOriginOffsetY();
            int componentWidth = c.getWidth();
            int componentHeight = c.getHeight();
            if (c instanceof Container) {
                container = (Container)c;
                boolean penetrate = container.penetrateTest(x - this.getOriginOffsetX(), y - this.getOriginOffsetY());
                if (!penetrate) continue;
                Component a = ((Container)c).getComponentAt(x - componentX, y - componentY);
                if (a != c) {
                    return a;
                }
            }
            if (x < componentX || y < componentY || x > componentX + componentWidth || y > componentY + componentHeight) continue;
            if (c instanceof Container) {
                container = (Container)c;
                Component hit = container.getComponentAt(x - componentX, y - componentY);
                return hit;
            }
            return c;
        }
        return this;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width + this.getOriginOffsetX());
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height + this.getOriginOffsetY());
    }

    @Override
    public void kill() {
        for (Component c : this.children) {
            c.kill();
        }
        super.kill();
    }

    @Override
    public int getOriginOffsetX() {
        return this.originoffsetX;
    }

    @Override
    public int getOriginOffsetY() {
        return this.originoffsetY;
    }

    public void setOriginOffsetX(int originoffsetX) {
        this.originoffsetX = originoffsetX;
    }

    public void setOriginOffsetY(int originoffsetY) {
        this.originoffsetY = originoffsetY;
    }

    @Override
    public boolean penetrateTest(int x, int y) {
        return true;
    }
}

