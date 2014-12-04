package com.github.nickardson.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

public class RenderUtility {
    private static UnicodeFontRenderer DEFAULT_FONT;

    public static int getScale() {
        return getScaledResolution().getScaleFactor();
    }

    public static ScaledResolution getScaledResolution() {
        Minecraft mc = Minecraft.getMinecraft();
        return new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    }

    public static void scale1to1() {
        GL11.glPushMatrix();
        double s = 1.0 / getScale();
        GL11.glScaled(s, s, 1);
    }

    public static void unscale() {
        GL11.glPopMatrix();
    }

    public static void disableDepth() {
        glDepthMask(false);
        glDisable(GL_DEPTH_TEST);
    }

    public static void enableDepth() {
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
    }

    public static void startOverlay() {
        disableDepth();

        glShadeModel(GL_SMOOTH);
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_CULL_FACE);
    }

    public static void endOverlay() {
        enableDepth();

        glLineWidth(1);
        glDisable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LINE_SMOOTH);
        glColor3f(1, 1, 1);
    }

    public static void translate(Point off) {
        translate(off.getX(), off.getY());
    }

    public static void translate(int x, int y) {
        GL11.glTranslatef(x, y, 0);
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void push() {
        GL11.glPushMatrix();
    }
    public static void pop() {
        GL11.glPopMatrix();
    }

    public static void text(UnicodeFontRenderer font, String text, int x, int y) {
        font.drawString(text, x, y, CharacterCode.White);
    }

    public static void text(String text, int x, int y) {
        if (DEFAULT_FONT == null) {
            DEFAULT_FONT = new UnicodeFontRenderer(FontUtility.fromSystem("Arial", 12, Font.PLAIN));
        }
        DEFAULT_FONT.drawString(text, x, y, CharacterCode.White);
    }
}