package com.github.nickardson.augmentium.script;

import com.github.nickardson.augmentium.AugmentiumMod;
import com.github.nickardson.augmentium.ScriptEngine;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

import java.util.concurrent.CopyOnWriteArrayList;

public class EventHook {
    private static final Object[] EMPTY_OBJ_ARRAY = new Object[0];

    private CopyOnWriteArrayList<Function> bound;
    private CopyOnWriteArrayList<Function> onceList;

    public EventHook() {
        bound = new CopyOnWriteArrayList<Function>();
        onceList = new CopyOnWriteArrayList<Function>();
    }

    public EventHook on(Function function) {
        bound.add(function);
        return this;
    }

    public EventHook once(Function function) {
        onceList.add(function);
        return this;
    }

    public EventHook off(Function function) {
        bound.remove(function);
        return this;
    }

    public EventHook trigger(Object... args) {
        Context ctx = Context.enter();
        for (Function f : onceList) {
            Scriptable scope = f.getParentScope();

            try {
                f.call(ctx, scope, scope, args);
            } catch (RhinoException e) {
                AugmentiumMod.logger.error(e.getMessage());
            } catch (Exception e) {
                AugmentiumMod.logger.throwing(e);
            }
        }
        onceList.clear();
        for (Function f : bound) {
            Scriptable scope = f.getParentScope();

            try {
                f.call(ctx, scope, scope, args);
            } catch (RhinoException e) {
                AugmentiumMod.logger.error(e.getMessage());
            } catch (Exception e) {
                AugmentiumMod.logger.throwing(e);
            }
        }
        Context.exit();
        return this;
    }

    public EventHook trigger() {
        trigger(EMPTY_OBJ_ARRAY);
        return this;
    }

    public EventHook clear() {
        bound.clear();
        onceList.clear();
        return this;
    }
}
