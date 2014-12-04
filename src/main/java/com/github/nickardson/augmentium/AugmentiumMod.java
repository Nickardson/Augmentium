package com.github.nickardson.augmentium;

import com.github.nickardson.augmentium.script.EventHook;
import com.github.nickardson.augmentium.script.api.APIClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;

@Mod(modid = AugmentiumMod.MODID, version = AugmentiumMod.VERSION)
public class AugmentiumMod
{
    public static final String MODID = "augmentium";
    public static final String VERSION = "1.7.10-1.0";

    public static AugmentiumMod instance;

    public static Logger logger;

    private static File jarFile;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        logger = LogManager.getLogger("Augmentium");

        // Source location
        jarFile = event.getSourceFile();

        logger.debug("Source Location: " + jarFile);
        if (jarFile.exists()) {
            if (jarFile.isFile()) {
                logger.debug("judging source to be compiled JAR");
            } else {
                logger.debug("judging source to be debug");
            }
        } else {
            logger.fatal("Source file/folder does not exist!");
        }
    }

    public static class APIWorld {
        private final EventHook onTick = new EventHook();
        public EventHook getOnTick() {return onTick;}

        private final EventHook onLoad = new EventHook();
        public EventHook getOnLoad() {return onLoad;}

        @Override
        public String toString() {
            return "[object World]";
        }
    }
    public static final APIWorld world = new APIWorld();
    public static final APIClient client = new APIClient();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new AugmentiumListener());

        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.debug("Creating temp directory");
                    File temp = Files.createTempDirectory("augmentium").toFile();
                    logger.debug("Temp folder: " + temp);

                    logger.debug("Extracting resources");
                    FileUtility.extractResources(jarFile, "/augmentium/www/", temp);
                    temp.deleteOnExit();

                    logger.debug("Starting web server");
                    new AugmentiumServer(temp, false).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        server.setName("Augmentium Web Server");
        server.start();

        logger.info("Augmentium Loaded");
    }
}
