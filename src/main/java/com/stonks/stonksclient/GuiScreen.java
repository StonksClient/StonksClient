package com.stonks.stonksclient;

import com.stonks.stonksclient.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class GuiScreen extends Screen {

    private final Pattern ON_OFF = Pattern.compile(" [A-Z]+");
    protected GuiScreen() {
        super(new LiteralText(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.renderBackground(matrices);
        addButton(new ButtonWidget(0, 0, 100, 20, new LiteralText("Movement"), (self) -> {
            int y = 20;
            for (Module m : StonksClient.modules) {
                if (m.getCategory() == Module.Category.MOVEMENT) {
                    int Y = y;
                    AtomicReference<ButtonWidget> ar = new AtomicReference<>();
                    ar.set(addButton(new ButtonWidget(0, Y, 100, 20, new LiteralText(m.getName() + (m.isActivated() ? " ON" : " OFF")).setStyle(Style.EMPTY.withColor(m.isActivated() ? Formatting.GREEN : Formatting.RED)), (s) -> {
                        addButton(new ButtonWidget(100, Y, 100, 20, new LiteralText("Toggle"), (thing) -> {
                            m.toggleActivated();
                            LiteralText message = (LiteralText) ar.get().getMessage();
                            String nmsg = ON_OFF.matcher(message.getRawString()).replaceAll(m.activated ? " ON" : " OFF");
                            ar.get().setMessage(new LiteralText(nmsg).setStyle(Style.EMPTY.withFormatting(m.activated ? Formatting.GREEN : Formatting.RED)));
                        }));
                    })));
                    y += 20;

                }
            }
        }));
        addButton(new ButtonWidget(200, 0, 100, 20, new LiteralText("Combat"), (self) -> {
            int y = 20;
            for (Module m : StonksClient.modules) {
                if (m.getCategory() == Module.Category.COMBAT) {
                    int Y = y;
                    AtomicReference<ButtonWidget> ar = new AtomicReference<>();
                    ar.set(addButton(new ButtonWidget(200, Y, 100, 20, new LiteralText(m.getName() + (m.isActivated() ? " ON" : " OFF")).setStyle(Style.EMPTY.withColor(m.isActivated() ? Formatting.GREEN : Formatting.RED)), (s) -> {
                        addButton(new ButtonWidget(300, Y, 100, 20, new LiteralText("Toggle"), (thing) -> {
                            m.toggleActivated();
                            LiteralText message = (LiteralText) ar.get().getMessage();
                            String nmsg = ON_OFF.matcher(message.getRawString()).replaceAll(m.activated ? " ON" : " OFF");
                            ar.get().setMessage(new LiteralText(nmsg).setStyle(Style.EMPTY.withFormatting(m.activated ? Formatting.GREEN : Formatting.RED)));
                        }));
                    })));
                    y += 20;

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
