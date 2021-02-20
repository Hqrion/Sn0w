/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.rgui.component.use;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class InputField
extends AbstractComponent {
    char echoChar = '\u0000';
    InputState currentState = new InputState("", 0, false, 0, 0);
    long startRail = 0L;
    float railT = 0.0f;
    boolean rail = false;
    int railChar = 0;
    KeyListener inputListener;
    int railDelay = 500;
    int railRepeat = 32;
    long lastTypeMS = 0L;
    int undoT = 0;
    ArrayList<InputState> undoMap = new ArrayList();
    ArrayList<InputState> redoMap = new ArrayList();
    int scrollX = 0;
    boolean shift = false;
    FontRenderer fontRenderer = null;

    public FontRenderer getFontRenderer() {
        return this.fontRenderer == null ? this.getTheme().getFontRenderer() : this.fontRenderer;
    }

    public void setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    public InputField(String text) {
        this.currentState.text = text;
        this.addRenderListener(new RenderListener(){

            @Override
            public void onPreRender() {
            }

            @Override
            public void onPostRender() {
                if (!InputField.this.isFocussed()) {
                    InputField.this.currentState.selection = false;
                }
                int[] real = GUI.calculateRealPosition(InputField.this);
                int scale = DisplayGuiScreen.getScale();
                GL11.glScissor((int)(real[0] * scale - InputField.this.getParent().getOriginOffsetX() - 1), (int)(Display.getHeight() - InputField.this.getHeight() * scale - real[1] * scale - 1), (int)(InputField.this.getWidth() * scale + InputField.this.getParent().getOriginOffsetX() + 1), (int)(InputField.this.getHeight() * scale + 1));
                GL11.glEnable((int)3089);
                GL11.glTranslatef((float)(-InputField.this.scrollX), (float)0.0f, (float)0.0f);
                FontRenderer fontRenderer = InputField.this.getFontRenderer();
                GL11.glLineWidth((float)1.0f);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                boolean cursor = (int)((System.currentTimeMillis() - InputField.this.lastTypeMS) / 500L) % 2 == 0 && InputField.this.isFocussed();
                int x = 0;
                int i = 0;
                boolean selection = false;
                if (InputField.this.getCursorRow() == 0 && cursor) {
                    GL11.glBegin((int)1);
                    GL11.glVertex2d((double)4.0, (double)2.0);
                    GL11.glVertex2d((double)4.0, (double)(fontRenderer.getFontHeight() - 1));
                    GL11.glEnd();
                }
                for (char c : InputField.this.getDisplayText().toCharArray()) {
                    int w = fontRenderer.getStringWidth(c + "");
                    if (InputField.this.getCurrentState().isSelection() && i == InputField.this.getCurrentState().getSelectionStart()) {
                        selection = true;
                    }
                    if (selection) {
                        GL11.glColor4f((float)0.2f, (float)0.6f, (float)1.0f, (float)0.3f);
                        GL11.glBegin((int)7);
                        GL11.glVertex2d((double)(x + 2), (double)2.0);
                        GL11.glVertex2d((double)(x + 2), (double)(fontRenderer.getFontHeight() - 2));
                        GL11.glVertex2d((double)(x + w + 2), (double)(fontRenderer.getFontHeight() - 2));
                        GL11.glVertex2d((double)(x + w + 2), (double)2.0);
                        GL11.glEnd();
                    }
                    x += w;
                    if (++i == InputField.this.getCursorRow() && cursor && !InputField.this.getCurrentState().isSelection()) {
                        GL11.glBegin((int)1);
                        GL11.glVertex2d((double)(x + 2), (double)2.0);
                        GL11.glVertex2d((double)(x + 2), (double)fontRenderer.getFontHeight());
                        GL11.glEnd();
                    }
                    if (!InputField.this.getCurrentState().isSelection() || i != InputField.this.getCurrentState().getSelectionEnd()) continue;
                    selection = false;
                }
                String s = InputField.this.getDisplayText();
                if (s.isEmpty()) {
                    s = " ";
                }
                GL11.glEnable((int)3042);
                fontRenderer.drawString(0, -1, s);
                GL11.glDisable((int)3553);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glTranslatef((float)InputField.this.scrollX, (float)0.0f, (float)0.0f);
                GL11.glDisable((int)3089);
            }
        });
        this.inputListener = new KeyListener(){

            @Override
            public void onKeyDown(KeyListener.KeyEvent event) {
                InputField.this.lastTypeMS = System.currentTimeMillis();
                if (event.getKey() == 14) {
                    if (InputField.this.getText().length() > 0) {
                        InputField.this.pushUndo();
                        if (InputField.this.currentState.selection) {
                            InputField.this.currentState.cursorRow = InputField.this.currentState.selectionEnd;
                            InputField.this.scroll();
                            InputField.this.remove(InputField.this.currentState.selectionEnd - InputField.this.currentState.selectionStart);
                            InputField.this.currentState.selection = false;
                        } else {
                            InputField.this.remove(1);
                        }
                    }
                } else if (Keyboard.getEventCharacter() == '\u001a') {
                    if (!InputField.this.undoMap.isEmpty()) {
                        InputField.this.redoMap.add(0, InputField.this.currentState.clone());
                        InputField.this.currentState = InputField.this.undoMap.get(0);
                        InputField.this.undoMap.remove(0);
                    }
                } else if (Keyboard.getEventCharacter() == '\u0019') {
                    if (!InputField.this.redoMap.isEmpty()) {
                        InputField.this.undoMap.add(0, InputField.this.currentState.clone());
                        InputField.this.currentState = InputField.this.redoMap.get(0);
                        InputField.this.redoMap.remove(0);
                    }
                } else if (Keyboard.getEventCharacter() == '\u0001') {
                    InputField.this.currentState.selection = true;
                    InputField.this.currentState.selectionStart = 0;
                    InputField.this.currentState.selectionEnd = InputField.this.currentState.getText().length();
                } else if (event.getKey() == 54) {
                    InputField.this.shift = true;
                } else if (event.getKey() == 1) {
                    InputField.this.currentState.selection = false;
                } else if (Keyboard.getEventCharacter() == '\u0016') {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    try {
                        InputField.this.type((String)clipboard.getData(DataFlavor.stringFlavor));
                    }
                    catch (UnsupportedFlavorException unsupportedFlavorException) {
                    }
                    catch (IOException iOException) {}
                } else if (Keyboard.getEventCharacter() == '\u0003') {
                    if (InputField.this.currentState.selection) {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        StringSelection selection = new StringSelection(InputField.this.currentState.getText().substring(InputField.this.currentState.selectionStart, InputField.this.currentState.selectionEnd));
                        clipboard.setContents(selection, selection);
                    }
                } else if (event.getKey() == 205) {
                    if (InputField.this.currentState.cursorRow < InputField.this.getText().length()) {
                        if (InputField.this.shift) {
                            if (!InputField.this.currentState.selection) {
                                InputField.this.currentState.selectionStart = InputField.this.currentState.cursorRow;
                                InputField.this.currentState.selectionEnd = InputField.this.currentState.cursorRow;
                            }
                            InputField.this.currentState.selection = true;
                            InputField.this.currentState.selectionEnd = Math.min(InputField.this.getText().length(), InputField.this.currentState.selectionEnd + 1);
                        } else if (InputField.this.currentState.selection) {
                            InputField.this.currentState.selection = false;
                            InputField.this.currentState.cursorRow = InputField.this.currentState.selectionEnd;
                            InputField.this.scroll();
                        } else {
                            InputField.this.currentState.cursorRow = Math.min(InputField.this.getText().length(), InputField.this.currentState.cursorRow + 1);
                            InputField.this.scroll();
                        }
                    }
                } else if (event.getKey() == 203) {
                    if (InputField.this.currentState.cursorRow > 0) {
                        if (InputField.this.shift) {
                            if (!InputField.this.currentState.selection) {
                                InputField.this.currentState.selectionStart = InputField.this.currentState.cursorRow;
                                InputField.this.currentState.selectionEnd = InputField.this.currentState.cursorRow;
                            }
                            InputField.this.currentState.selection = true;
                            InputField.this.currentState.selectionStart = Math.max(0, InputField.this.currentState.selectionStart - 1);
                        } else if (InputField.this.currentState.selection) {
                            InputField.this.currentState.selection = false;
                            InputField.this.currentState.cursorRow = InputField.this.currentState.selectionStart;
                            InputField.this.scroll();
                        } else {
                            InputField.this.currentState.cursorRow = Math.max(0, InputField.this.currentState.cursorRow - 1);
                            InputField.this.scroll();
                        }
                    }
                } else if (Keyboard.getEventCharacter() != '\u0000') {
                    InputField.this.pushUndo();
                    if (InputField.this.currentState.selection) {
                        InputField.this.currentState.cursorRow = InputField.this.currentState.selectionEnd;
                        InputField.this.remove(InputField.this.currentState.selectionEnd - InputField.this.currentState.selectionStart);
                        InputField.this.currentState.selection = false;
                    }
                    InputField.this.type(Keyboard.getEventCharacter() + "");
                }
                if (event.getKey() == 42) {
                    return;
                }
                InputField.this.startRail = System.currentTimeMillis();
                InputField.this.railChar = event.getKey();
            }

            @Override
            public void onKeyUp(KeyListener.KeyEvent event) {
                InputField.this.rail = false;
                InputField.this.startRail = 0L;
                if (event.getKey() == 54) {
                    InputField.this.shift = false;
                }
            }
        };
        this.addKeyListener(this.inputListener);
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                InputField.this.currentState.selection = false;
                int x = -InputField.this.scrollX;
                int i = 0;
                for (char c : InputField.this.getText().toCharArray()) {
                    if (event.getX() < (x += InputField.this.getFontRenderer().getStringWidth(c + ""))) {
                        InputField.this.currentState.cursorRow = i;
                        InputField.this.scroll();
                        return;
                    }
                    ++i;
                }
                InputField.this.currentState.cursorRow = i;
                InputField.this.scroll();
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                InputField.this.currentState.selection = true;
                InputField.this.currentState.selectionStart = InputField.this.currentState.cursorRow;
                int x = -InputField.this.scrollX;
                int i = 0;
                for (char c : InputField.this.getText().toCharArray()) {
                    if (event.getX() < (x += InputField.this.getFontRenderer().getStringWidth(c + ""))) {
                        InputField.this.currentState.selectionEnd = i;
                        InputField.this.scroll();
                        break;
                    }
                    ++i;
                }
                InputField.this.currentState.selectionEnd = i;
                int buf = InputField.this.currentState.cursorRow;
                InputField.this.currentState.cursorRow = i;
                InputField.this.scroll();
                InputField.this.currentState.cursorRow = buf;
                if (InputField.this.currentState.selectionStart > InputField.this.currentState.selectionEnd) {
                    int a = InputField.this.currentState.selectionStart;
                    InputField.this.currentState.selectionStart = InputField.this.currentState.selectionEnd;
                    InputField.this.currentState.selectionEnd = a;
                }
                if (InputField.this.currentState.selectionStart == InputField.this.currentState.selectionEnd) {
                    InputField.this.currentState.selection = false;
                }
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
        this.addRenderListener(new RenderListener(){

            @Override
            public void onPreRender() {
                if (InputField.this.startRail == 0L) {
                    return;
                }
                if (!InputField.this.rail) {
                    InputField.this.railT = System.currentTimeMillis() - InputField.this.startRail;
                    if (InputField.this.railT > (float)InputField.this.railDelay) {
                        InputField.this.rail = true;
                        InputField.this.startRail = System.currentTimeMillis();
                    }
                } else {
                    InputField.this.railT = System.currentTimeMillis() - InputField.this.startRail;
                    if (InputField.this.railT > (float)InputField.this.railRepeat) {
                        InputField.this.inputListener.onKeyDown(new KeyListener.KeyEvent(InputField.this.railChar));
                        InputField.this.startRail = System.currentTimeMillis();
                    }
                }
            }

            @Override
            public void onPostRender() {
            }
        });
    }

    public InputField() {
        this("");
    }

    public InputField(int width) {
        this("");
    }

    public InputState getCurrentState() {
        return this.currentState;
    }

    public void type(String text) {
        try {
            this.setText(this.getText().substring(0, this.currentState.getCursorRow()) + text + this.getText().substring(this.currentState.getCursorRow()));
            this.currentState.cursorRow += text.length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.scroll();
    }

    public void remove(int back) {
        back = Math.min(back, this.currentState.getCursorRow());
        boolean a = this.setText(this.getText().substring(0, Math.max(this.currentState.getCursorRow() - back, 0)) + this.getText().substring(this.currentState.getCursorRow()));
        if (!a) {
            this.currentState.cursorRow -= back;
        }
        this.scroll();
    }

    private void scroll() {
        int diff;
        int aX = 0;
        int i = 0;
        String a = "";
        for (char c : this.getText().toCharArray()) {
            aX += this.getFontRenderer().getStringWidth(c + "");
            a = a + c;
            if (++i >= this.currentState.cursorRow) break;
        }
        if ((diff = aX - this.scrollX) > this.getWidth()) {
            this.scrollX = aX - this.getWidth() + 8;
        } else if (diff < 0) {
            this.scrollX = aX + 8;
        }
        if (this.currentState.cursorRow == 0) {
            this.scrollX = 0;
        }
    }

    public int getCursorRow() {
        return this.currentState.getCursorRow();
    }

    private void pushUndo() {
        ++this.undoT;
        if (this.undoT > 3) {
            this.undoT = 0;
            this.undoMap.add(0, this.currentState.clone());
        }
    }

    public String getText() {
        return this.currentState.getText();
    }

    public String getDisplayText() {
        return this.isEchoCharSet() ? this.getText().replaceAll(".", this.getEchoChar() + "") : this.getText();
    }

    public boolean setText(String text) {
        this.currentState.text = text;
        this.callPoof(InputFieldTextPoof.class, null);
        if (this.currentState.cursorRow > this.currentState.text.length()) {
            this.currentState.cursorRow = this.currentState.text.length();
            this.scroll();
            return true;
        }
        return false;
    }

    public char getEchoChar() {
        return this.echoChar;
    }

    public InputField setEchoChar(char echoChar) {
        this.echoChar = echoChar;
        return this;
    }

    public boolean isEchoCharSet() {
        return this.echoChar != '\u0000';
    }

    public static abstract class InputFieldTextPoof<T extends InputField, S extends PoofInfo>
    extends Poof<T, S> {
    }

    public class InputState {
        String text;
        int cursorRow;
        boolean selection;
        int selectionStart;
        int selectionEnd;

        public InputState(String text, int cursorRow, boolean selection, int selectionStart, int selectionEnd) {
            this.text = text;
            this.cursorRow = cursorRow;
            this.selection = selection;
            this.selectionStart = selectionStart;
            this.selectionEnd = selectionEnd;
        }

        protected InputState clone() {
            return new InputState(this.getText(), this.getCursorRow(), this.isSelection(), this.getSelectionStart(), this.getSelectionEnd());
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCursorRow() {
            return this.cursorRow;
        }

        public void setCursorRow(int cursorRow) {
            this.cursorRow = cursorRow;
            InputField.this.scroll();
        }

        public boolean isSelection() {
            return this.selection;
        }

        public void setSelection(boolean selection) {
            this.selection = selection;
        }

        public int getSelectionStart() {
            return this.selectionStart;
        }

        public void setSelectionStart(int selectionStart) {
            this.selectionStart = selectionStart;
        }

        public int getSelectionEnd() {
            return this.selectionEnd;
        }

        public void setSelectionEnd(int selectionEnd) {
            this.selectionEnd = selectionEnd;
        }
    }
}

