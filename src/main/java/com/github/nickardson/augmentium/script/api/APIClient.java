package com.github.nickardson.augmentium.script.api;

import com.github.nickardson.augmentium.script.EventHook;
import org.lwjgl.opengl.Display;
import org.mozilla.javascript.annotations.JSGetter;

public class APIClient {
    private final EventHook onRender = new EventHook();
    @JSGetter
    public EventHook getOnRender() {
        return onRender;
    }

    public void setWindowTitle(String text) {
        Display.setTitle(text);
    }

    public String getWindowTitle() {
        return Display.getTitle();
    }

    public HookableGuiScreen createGUI() {
        return new HookableGuiScreen();
    }
}
