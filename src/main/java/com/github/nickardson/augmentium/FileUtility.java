package com.github.nickardson.augmentium;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtility {

    public static void tryExtractFolder(File jar, String classpathFolder, File destination) {
        if (!destination.exists() || !destination.isDirectory()) {
            destination.mkdir();

            for (String filename : FileUtility.dir(jar, classpathFolder)) {
                File fileDest = new File(destination, filename.substring(classpathFolder.length() + 1));
                fileDest.getParentFile().mkdirs();
                try {
                    FileUtility.exportResource("/" + filename, fileDest);
                } catch (Exception e) {
                    AugmentiumMod.logger.fatal("Unable to extract: " + filename);
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * If we're in a dev environment, copies source files.
     * if we're in a jar, copies resources from the jar.
     * @param jar The folder or jar file to copy from
     * @param subdir The subdirectory or classpath to copy from
     * @param dest The destination folder to copy to
     * @throws IOException
     */
    public static void extractResources(File jar, String subdir, File dest) throws IOException {
        if (jar.isDirectory()) {
            if (subdir.startsWith("/")) {
                subdir = subdir.substring(1);
            }
            FileUtility.copyDirectory(new File(jar, subdir), dest);
        } else {
            if (!subdir.startsWith("/")) {
                subdir = "/" + subdir;
            }
            tryExtractFolder(jar, subdir, dest);
        }
    }

    /**
     * Gets a list of resource files inside of a JAR file.
     * @param jarFile The jar file to search inside
     * @param path Subpath to match.
     * @return
     */
    public static List<String> dir(File jarFile, final String path) {
        final List<String> ls = new ArrayList<String>();

        try {
            JarFile jar = new JarFile(jarFile);

            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    String name = entry.getName();
                    if (name.startsWith(path + "/")) {
                        ls.add(name);
                    }
                }
            }

            jar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ls;
    }

    static public File exportResource(String resourceName, File destination) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;

        try {
            stream = AugmentiumMod.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            //String jarFolder = new File(AugmentiumMod.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(destination);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return destination;
    }
}
