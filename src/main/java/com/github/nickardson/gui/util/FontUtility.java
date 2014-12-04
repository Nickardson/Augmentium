package com.github.nickardson.gui.util;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FontUtility {
    static Font serif = new Font("serif", Font.PLAIN, 24);

    /**
     * Creates a font from a raw source.
     * @param source The source for the font.
     * @param size The size of the font, in pt.
     * @param mods The modifications for this font, ie: Font.BOLD | Font.Italic
     */
    public static Font fromStream(InputStream source, int size, int mods) {
        if (source == null) {
            return serif;
        }

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, source);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font.deriveFont(mods, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            return serif;
        }
    }

    public static Font fromClasspath(String name, int size, int mods) {
        return fromStream(FontUtility.class.getResourceAsStream(name), size, mods);
    }

    public static Font fromSystem(String name, int size, int mods) {
        return new Font(name, mods, size);
    }

    public static Font fromFile(File file, int size, int mods) throws FileNotFoundException {
        return fromStream(new FileInputStream(file), size, mods);
    }
}
