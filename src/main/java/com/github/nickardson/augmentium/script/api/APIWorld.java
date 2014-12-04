package com.github.nickardson.augmentium.script.api;

import com.github.nickardson.augmentium.script.EventHook;

public class APIWorld {
    public static APIWorld instance = new APIWorld();

    private final EventHook onTick = new EventHook();
    public EventHook getOnTick() {return onTick;}

    private final EventHook onLoad = new EventHook();
    public EventHook getOnLoad() {return onLoad;}

    @Override
    public String toString() {
        return "[api World]";
    }
}
