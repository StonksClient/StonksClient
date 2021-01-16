package com.stonks.stonksclient.gui;

import com.stonks.stonksclient.StonksClient;
import com.stonks.stonksclient.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

import java.awt.*;

public class ModsOverlay {

    private static FontMetrics fm = null;

    public static void render(MatrixStack matrices, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;
        int width = mc.getWindow().getScaledWidth();
        if (fm == null) {
            Canvas c = new Canvas();
            fm = c.getFontMetrics(new Font("Impact", Font.PLAIN, 12));
        }

        int y = 0;
        for (Module m : StonksClient.modules) {
            if (m.isActivated()) {
                float x = System.currentTimeMillis() % 2000 / 1000f;
                textRenderer.draw(matrices, new LiteralText(m.getName()).setStyle(Style.EMPTY.withFont(new Identifier("stonks", "impact"))), width - fm.stringWidth(m.getName()), y, Color.HSBtoRGB((float) Math.abs(Math.sin(x)), 1.0f, 1.0f));
                y += 20;
            }
        }
    }
}
