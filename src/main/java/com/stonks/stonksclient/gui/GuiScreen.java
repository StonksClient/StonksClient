package com.stonks.stonksclient.gui;

import com.stonks.stonksclient.StonksClient;
import com.stonks.stonksclient.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class GuiScreen extends Screen {

    private final Pattern ON_OFF = Pattern.compile(" [A-Z]+");

    public GuiScreen() {
        super(new LiteralText(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.renderBackground(matrices);
        addButton(new StonkButton(0, 0, 100, 20, new LiteralText("Movement").setStyle(Style.EMPTY.withFont(new Identifier("stonks", "impact"))), (self) -> {
            int y = 30;
            for (Module m : StonksClient.modules) {
                if (m.getCategory() == Module.Category.MOVEMENT) {
                    int Y = y;
                    AtomicReference<StonkButton> ar = new AtomicReference<>();
                    ar.set(addButton(new StonkButton(0, Y, 100, 20, new LiteralText(m.getName() + (m.isActivated() ? " ON" : " OFF")).setStyle(Style.EMPTY.withColor(m.isActivated() ? Formatting.GREEN : Formatting.RED).withFont(new Identifier("stonks", "impact"))), (s) -> {
                        addButton(new StonkButton(100, Y, 100, 20, new LiteralText("Toggle").setStyle(Style.EMPTY.withFont(new Identifier("stonks", "impact"))), (thing) -> {
                            m.toggleActivated();
                            LiteralText message = (LiteralText) ar.get().getMessage();
                            String nmsg = ON_OFF.matcher(message.getRawString()).replaceAll(m.activated ? " ON" : " OFF");
                            ar.get().setMessage(new LiteralText(nmsg).setStyle(Style.EMPTY.withFormatting(m.activated ? Formatting.GREEN : Formatting.RED).withFont(new Identifier("stonks", "impact"))));
                        }));
                    })));
                    y += 30;

                }
            }
        }));
        addButton(new StonkButton(200, 0, 100, 20, new LiteralText("Combat").setStyle(Style.EMPTY.withFont(new Identifier("stonks", "impact"))), (self) -> {
            int y = 20;
            for (Module m : StonksClient.modules) {
                if (m.getCategory() == Module.Category.COMBAT) {
                    int Y = y;
                    AtomicReference<StonkButton> ar = new AtomicReference<>();
                    ar.set(addButton(new StonkButton(200, Y, 100, 20, new LiteralText(m.getName() + (m.isActivated() ? " ON" : " OFF")).setStyle(Style.EMPTY.withColor(m.isActivated() ? Formatting.GREEN : Formatting.RED).withFont(new Identifier("stonks", "impact"))), (s) -> {
                        addButton(new StonkButton(300, Y, 100, 20, new LiteralText("Toggle").setStyle(Style.EMPTY.withFont(new Identifier("stonks", "impact"))), (thing) -> {
                            m.toggleActivated();
                            LiteralText message = (LiteralText) ar.get().getMessage();
                            String nmsg = ON_OFF.matcher(message.getRawString()).replaceAll(m.activated ? " ON" : " OFF");
                            ar.get().setMessage(new LiteralText(nmsg).setStyle(Style.EMPTY.withFormatting(m.activated ? Formatting.GREEN : Formatting.RED).withFont(new Identifier("stonks", "impact"))));
                        }));
                    })));
                    y += 30;

                }
            }
        }));
        super.render(matrices, mouseX, mouseY, delta);
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
