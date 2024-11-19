package com.spring.csmis.font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontExample {
    public static void main(String[] args) {
        // Get all available fonts in the JVM
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();

        // List all font names
        for (Font font : fonts) {
            System.out.println(font.getFontName());
        }

        // Check if a specific font is available
        String fontName = "Dialog"; // replace with your desired font
        boolean fontAvailable = false;
        for (Font font : fonts) {
            if (font.getFontName().equalsIgnoreCase(fontName)) {
                fontAvailable = true;
                break;
            }
        }

        if (fontAvailable) {
            System.out.println("Font " + fontName + " is available.");
        } else {
            System.out.println("Font " + fontName + " is not available.");
        }
    }
}
