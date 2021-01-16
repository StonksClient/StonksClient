package com.stonks.stonksclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

public class StonkButton extends ButtonWidget {
    public StonkButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public static void drawTriangle(MatrixStack matrices, int x1, int y1, int x2, int y2, int x3, int y3, int color) {
        Matrix4f matrix = matrices.peek().getModel();

        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(4, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, (float) x3, (float) y3, 0.0F).color(g, h, k, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        DrawableHelper.fill(matrices, x, y, (int) (x + width * 0.6), y + 10, new Color(54, 255, 255).getRGB());
        drawTriangle(matrices, (int) (x + width * 0.6), y, (int) (x + width * 0.6), y + 10, (int) (x + 10 + width * 0.66), y + 10, new Color(54, 255, 255).getRGB());
        drawTriangle(matrices, (int) (x + width * 0.6), y, (int) (x + width * 0.80), y + 15, (int) (x + width * 0.80), y, new Color(219, 160, 32).getRGB());
        DrawableHelper.fill(matrices, (int) (x + width * 0.80), y, x + width, y + 15, new Color(219, 160, 32).getRGB());
        int j = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        if (this.isHovered()) {
            this.renderToolTip(matrices, mouseX, mouseY);
        }
    }
}
