package com.github.nickardson.augmentium;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class ServerRunner {
    public static void run(Class serverClass) {
        try {
            executeInstance((NanoHTTPD) serverClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeInstance(NanoHTTPD server) {
        try {
            AugmentiumMod.logger.info("Augmentium Web Server starting.");
            server.start();
            AugmentiumMod.logger.info("\n\nAugmentium Web Server started at http://localhost:" + server.getListeningPort() + "/\n\n");
        } catch (IOException ioe) {
            AugmentiumMod.logger.error("Couldn't start server:\n" + ioe);
        }
    }

    public static void stopInstance(NanoHTTPD server) {
        server.stop();
        AugmentiumMod.logger.info("Augmentium Web Server stopped.");
    }
}