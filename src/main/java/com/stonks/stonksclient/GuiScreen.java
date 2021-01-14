package com.stonks.stonksclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class GuiScreen extends Screen {

    protected GuiScreen() {
        super(new LiteralText(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        textRenderer.drawWithShadow(matrices, "Useless Gui, press ESC to quit", 0, 0, Color.RED.getRGB() | 1 << 24);
    }

    @Override
    public void onClose() {
        StonksClient.k1.setPressed(true);
        super.onClose();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
