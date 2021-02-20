/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.container.use;

import java.util.HashMap;
import java.util.Map;
import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.OrganisedContainer;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.UpdateListener;
import me.zeroeightsix.kami.gui.rgui.layout.Layout;
import me.zeroeightsix.kami.gui.rgui.layout.UselessLayout;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.FramePoof;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.gui.rgui.util.Docking;

public class Frame
extends OrganisedContainer {
    String title;
    int trueheight = 0;
    int truemaxheight = 0;
    int dx = 0;
    int dy = 0;
    boolean doDrag = false;
    boolean startDrag = false;
    boolean isMinimized = false;
    boolean isMinimizeable = true;
    boolean isCloseable = true;
    boolean isPinned = false;
    boolean isPinneable = false;
    boolean isLayoutWorking = false;
    Docking docking = Docking.NONE;
    HashMap<Component, Boolean> visibilityMap = new HashMap();

    public Frame(Theme theme, String title) {
        this(theme, new UselessLayout(), title);
    }

    public Frame(Theme theme, final Layout layout, String title) {
        super(theme, layout);
        this.title = title;
        this.addPoof(new FramePoof<Frame, FramePoof.FramePoofInfo>(){

            @Override
            public void execute(Frame component, FramePoof.FramePoofInfo info) {
                switch (info.getAction()) {
                    case MINIMIZE: {
                        if (!Frame.this.isMinimizeable) break;
                        Frame.this.setMinimized(true);
                        break;
                    }
                    case MAXIMIZE: {
                        if (!Frame.this.isMinimizeable) break;
                        Frame.this.setMinimized(false);
                        break;
                    }
                    case CLOSE: {
                        if (!Frame.this.isCloseable) break;
                        Frame.this.getParent().removeChild(Frame.this);
                    }
                }
            }
        });
        this.addUpdateListener(new UpdateListener(){

            public void updateSize(Component component, int oldWidth, int oldHeight) {
                if (Frame.this.isLayoutWorking) {
                    return;
                }
                if (!component.equals(Frame.this)) {
                    Frame.this.isLayoutWorking = true;
                    layout.organiseContainer(Frame.this);
                    Frame.this.isLayoutWorking = false;
                }
            }

            public void updateLocation(Component component, int oldX, int oldY) {
                if (Frame.this.isLayoutWorking) {
                    return;
                }
                if (!component.equals(Frame.this)) {
                    Frame.this.isLayoutWorking = true;
                    layout.organiseContainer(Frame.this);
                    Frame.this.isLayoutWorking = false;
                }
            }
        });
        this.addRenderListener(new RenderListener(){

            @Override
            public void onPreRender() {
                if (Frame.this.startDrag) {
                    FrameDragPoof.DragInfo info = new FrameDragPoof.DragInfo(DisplayGuiScreen.mouseX - Frame.this.dx, DisplayGuiScreen.mouseY - Frame.this.dy);
                    Frame.this.callPoof(FrameDragPoof.class, info);
                    Frame.this.setX(info.getX());
                    Frame.this.setY(info.getY());
                }
            }

            @Override
            public void onPostRender() {
            }
        });
        this.addMouseListener(new GayMouseListener());
    }

    public void setCloseable(boolean closeable) {
        this.isCloseable = closeable;
    }

    public void setMinimizeable(boolean minimizeable) {
        this.isMinimizeable = minimizeable;
    }

    public boolean isMinimizeable() {
        return this.isMinimizeable;
    }

    public boolean isMinimized() {
        return this.isMinimized;
    }

    public void setMinimized(boolean minimized) {
        if (minimized && !this.isMinimized) {
            this.trueheight = this.getHeight();
            this.truemaxheight = this.getMaximumHeight();
            this.setHeight(0);
            this.setMaximumHeight(this.getOriginOffsetY());
            for (Component c : this.getChildren()) {
                this.visibilityMap.put(c, c.isVisible());
                c.setVisible(false);
            }
        } else if (!minimized && this.isMinimized) {
            this.setMaximumHeight(this.truemaxheight);
            this.setHeight(this.trueheight - this.getOriginOffsetY());
            for (Map.Entry<Component, Boolean> entry : this.visibilityMap.entrySet()) {
                entry.getKey().setVisible(entry.getValue());
            }
        }
        this.isMinimized = minimized;
    }

    public boolean isCloseable() {
        return this.isCloseable;
    }

    public boolean isPinneable() {
        return this.isPinneable;
    }

    public boolean isPinned() {
        return this.isPinned;
    }

    public void setPinneable(boolean pinneable) {
        this.isPinneable = pinneable;
    }

    public void setPinned(boolean pinned) {
        this.isPinned = pinned && this.isPinneable;
    }

    public String getTitle() {
        return this.title;
    }

    public Docking getDocking() {
        return this.docking;
    }

    public void setDocking(Docking docking) {
        this.docking = docking;
    }

    public static abstract class FrameDragPoof<T extends Frame, S extends DragInfo>
    extends Poof<T, S> {

        public static class DragInfo
        extends PoofInfo {
            int x;
            int y;

            public DragInfo(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return this.x;
            }

            public int getY() {
                return this.y;
            }

            public void setX(int x) {
                this.x = x;
            }

            public void setY(int y) {
                this.y = y;
            }
        }
    }

    public class GayMouseListener
    implements MouseListener {
        @Override
        public void onMouseDown(MouseListener.MouseButtonEvent event) {
            Frame.this.dx = event.getX() + Frame.this.getOriginOffsetX();
            Frame.this.dy = event.getY() + Frame.this.getOriginOffsetY();
            Frame.this.doDrag = Frame.this.dy <= Frame.this.getOriginOffsetY() && event.getButton() == 0 && Frame.this.dy > 0;
            if (Frame.this.isMinimized && event.getY() > Frame.this.getOriginOffsetY()) {
                event.cancel();
            }
        }

        @Override
        public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            Frame.this.doDrag = false;
            Frame.this.startDrag = false;
        }

        @Override
        public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            if (!Frame.this.doDrag) {
                return;
            }
            Frame.this.startDrag = true;
        }

        @Override
        public void onMouseMove(MouseListener.MouseMoveEvent event) {
        }

        @Override
        public void onScroll(MouseListener.MouseScrollEvent event) {
        }
    }
}

