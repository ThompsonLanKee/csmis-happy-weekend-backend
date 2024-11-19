package com.spring.csmis.font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FontLoader {
    public static void main(String[] args) {
        Path fontPath = Paths.get("src/main/resources/fonts/NotoSansMyanmar-Regular.ttf");
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontPath.toFile()).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            System.out.println("Font successfully loaded and registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
