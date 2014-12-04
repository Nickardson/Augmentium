package com.github.nickardson.augmentium.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private ConcurrentHashMap<String, List<Function>> registered = new ConcurrentHashMap<String, List<Function>>();

    public void on(String name, Function function) {
        if (registered.containsKey(name)) {
            registered.get(name).add(function);
        } else {
            CopyOnWriteArrayList<Function> ls = new CopyOnWriteArrayList<Function>();
            ls.add(function);
            registered.put(name, ls);
        }
    }

    public void trigger(String name, Scriptable scope, Object[] args) {
        if (registered.containsKey(name)) {
            Context ctx = Context.enter();
            for (Function f : registered.get(name)) {
                f.call(ctx, scope, scope, args);
            }
            Context.exit();
        }
    }

    public void clear(String name) {
        if (registered.containsKey(name)) {
            registered.get(name).clear();
        }
    }

    public void clearAll() {
        registered = new ConcurrentHashMap<String, List<Function>>();
    }
}
