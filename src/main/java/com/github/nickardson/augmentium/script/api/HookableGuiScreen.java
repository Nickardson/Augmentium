package com.github.nickardson.augmentium.script.api;

import com.github.nickardson.augmentium.script.EventHook;
import com.github.nickardson.gui.util.RenderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class HookableGuiScreen extends GuiScreen {
    public EventHook onKey = new EventHook();
    public EventHook onMouseMove = new EventHook();
    public EventHook onMouseUp = new EventHook();
    public EventHook onMouseDown = new EventHook();
    public EventHook onRender = new EventHook();
    public EventHook onOpen = new EventHook();
    public EventHook onClose = new EventHook();

    @Override
    protected void mouseMovedOrUp(int x, int y, int which) {
        if (which == -1) {
            onMouseMove.trigger(x, y, which);
        } else {
            onMouseUp.trigger(x, y, which);
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int which) {
        onMouseDown.trigger(x, y, which);
    }

    @Override
    protected void keyTyped(char charater, int keyID) {
        onKey.trigger(keyID, charater);

        super.keyTyped(charater, keyID);
    }

    @Override
    public void drawScreen(int x, int y, float param) {
        RenderUtility.scale1to1();
        onRender.trigger(param, x, y);
        RenderUtility.unscale();
    }

    private boolean pauses = false;
    public boolean getPauses() {
        return pauses;
    }

    public void setPauses(boolean pauses) {
        this.pauses = pauses;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return pauses;
    }

    private boolean showBackground = false;
    public boolean getShowBackground() {
        return showBackground;
    }
    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }

    @Override
    public void drawBackground(int i) {
        if (showBackground) {
            super.drawBackground(i);
        }
    }

    public void drawText(String text, int x, int y, int color) {
        drawString(Minecraft.getMinecraft().fontRenderer, text, x, y, color);
    }

    public void show() {
        Minecraft.getMinecraft().displayGuiScreen(this);
        onOpen.trigger();
    }

    public void close() {
        this.mc.displayGuiScreen(null);
        this.mc.setIngameFocus();
    }

    @Override
    public void onGuiClosed() {
        onClose.trigger();
    }
}
