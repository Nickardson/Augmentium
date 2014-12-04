package com.github.nickardson.gui.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;

public class UnicodeFontRenderer extends FontRenderer {
    private static final String HEIGHT_TEST_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    private final UnicodeFont font;

    @SuppressWarnings("unchecked")
    public UnicodeFontRenderer(Font awtFont, Effect[] effects) {
        super(
                Minecraft.getMinecraft().gameSettings,
                new ResourceLocation("textures/font/ascii.png"),
                Minecraft.getMinecraft().getTextureManager(),
                false);

        font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(Color.white));
        font.getEffects().addAll(Arrays.asList(effects));

        font.addAsciiGlyphs();

        try {
            font.loadGlyphs();
        } catch (SlickException exception) {
            throw new RuntimeException(exception);
        }

        FONT_HEIGHT = font.getHeight(HEIGHT_TEST_STRING);
    }

    public UnicodeFontRenderer(Font awtFont) {
        this(awtFont, new Effect[0]);
    }

    @Override
    public int drawString(String text, int x, int y, int defaultColor, boolean shadow) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        boolean blend = glIsEnabled(GL_BLEND);
        boolean lighting = glIsEnabled(GL_LIGHTING);
        boolean texture = glIsEnabled(GL_TEXTURE_2D);
        if(!blend)
            glEnable(GL_BLEND);
        if(lighting)
            glDisable(GL_LIGHTING);
        if(texture)
            glDisable(GL_TEXTURE_2D);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glPushMatrix();

        double xOffset = 0;
        if (text.contains(CharacterCode.SECTION)) {
            // Split the text into subsections, seperated by colorcodes.  The first of which may or may not have a color code, but all sections after will.
            String[] sections = text.split(CharacterCode.SECTION);

            boolean firstIsPlain = !text.startsWith(CharacterCode.SECTION);

            for (int i = 0; i < sections.length; i++) {
                String str = sections[i];

                if (str.isEmpty()) {
                    continue;
                }

                // Get the color this section should be renderered in.
                Color color;
                if (str.length() > 6 && str.charAt(0) == '#') {
                    // This is a hex color $#FFFFFF (my own design, not minecraft standard)
                    try {
                        color = new Color(
                                Integer.valueOf( str.substring( 1, 3 ), 16 ),
                                Integer.valueOf( str.substring( 3, 5 ), 16 ),
                                Integer.valueOf( str.substring( 5, 7 ), 16 ) );
                    } catch (java.lang.NumberFormatException ex) {
                        color = new Color(defaultColor);
                    }

                    // Remove the hex color.
                    sections[i] = sections[i].substring(7);
                } else {
                    CharacterCode code = CharacterCode.fromString(CharacterCode.SECTION + str.charAt(0));

                    // Being the first with an unstyled text, or being a reset character puts us back to the default color.
                    if ((firstIsPlain && i == 0) || code == CharacterCode.Reset) {
                        color = new Color(defaultColor);
                    } else {
                        color = code.getColor();
                    }

                    if (i != 0 || !firstIsPlain) {
                        // Remove the color identifier from the string.
                        sections[i] = sections[i].substring(1);
                    }
                }

                if (shadow) {
                    color = Color.BLACK;
                }

                font.drawString((float)(x + xOffset), y, sections[i], new org.newdawn.slick.Color(color.getRed(), color.getGreen(), color.getBlue()));

                xOffset += getWidth(sections[i]);
            }
        } else {
            // If there's no special color code magical trickery, just do a simple render.
            font.drawString(x, y, text, new org.newdawn.slick.Color(shadow ? 0x000000 : defaultColor));
        }
        glPopMatrix();

        if(texture)
            glEnable(GL_TEXTURE_2D);
        if(lighting)
            glEnable(GL_LIGHTING);
        if(!blend)
            glDisable(GL_BLEND);

        if (text.startsWith(CharacterCode.SECTION)) {
            return (int) xOffset;
        } else {
            return getStringWidth(text);
        }
    }

    @Override
    public int drawString(String string, int x, int y, int color) {
        return drawString(string, x, y, color, false);
    }

    @Override
    public int drawStringWithShadow(String string, int x, int y, int color) {
        drawString(string, x, y, color, true);
        return drawString(string, x, y, color, false);
    }

    public int drawString(String string, int x, int y, Color color) {
        return drawString(string, x, y, color.getRGB());
    }

    public int drawString(String string, int x, int y, CharacterCode color) {
        return drawString(string, x, y, color.getColor());
    }

    @Override
    public int getCharWidth(char c) {
        return (int) getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String string) {
        return (int) getWidth(string);
    }

    public double getWidth(String string) {
        return font.getWidth(CharacterCode.strip(string));
    }

    public int getStringHeight(String string) {
        return (int) getHeight(string);
    }

    public double getHeight(String string) {
        return font.getHeight(string);
    }
}
