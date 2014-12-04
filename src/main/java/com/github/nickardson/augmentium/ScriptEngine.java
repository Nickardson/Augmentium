package com.github.nickardson.augmentium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.mozilla.javascript.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class ScriptEngine {

    public static Scriptable spawnScope(String name) {
        Context cx = Context.enter();

        cx.setLanguageVersion(Context.VERSION_1_8);

        ImporterTopLevel scope = new ImporterTopLevel(cx);
        //scope.initStandardObjects(null, false);

        Context.exit();
        scope.putConst("_logger", scope, LogManager.getLogger("Augmentium-" + name));
        scope.putConst("_modclass", scope, AugmentiumMod.class);
        scope.putConst("_mod", scope, AugmentiumMod.instance);

        ScriptEngine.runResource(scope, "/lib/require.js");
        ScriptEngine.runResource(scope, "/lib/main.js");

        return scope;
    }

    public static Object eval(Scriptable scope, String code, String source) {
        Context context = Context.enter();
        try {
            return context.evaluateString(scope, code, source, 1, null);
        } catch (RhinoException e) {
            AugmentiumMod.logger.error(e.getMessage());
            return e;
        } catch (Exception e) {
            return AugmentiumMod.logger.throwing(e);
        } finally {
            Context.exit();
        }
    }

    public static void runResource(Scriptable scope, String path) {
        runFile(scope, ScriptEngine.class.getResourceAsStream(path), "cp://" + path);
    }

    public static void runFile(Scriptable scope, InputStream file, String source) {
        eval(scope, new Scanner(file).useDelimiter("\\Z").next(), source);
    }

    public static void runFile(Scriptable scope, File file) {
        try {
            eval(scope, new Scanner(file).useDelimiter("\\Z").next(), file.getPath());
        } catch (FileNotFoundException ex) {
            System.out.println("No such file: " + file);
        }
    }

    public static void runFile(Scriptable scope, String file) {
        runFile(scope, new File(file));
    }
}
