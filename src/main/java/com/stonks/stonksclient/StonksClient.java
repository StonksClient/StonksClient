package com.stonks.stonksclient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import org.lwjgl.glfw.GLFW;

public class StonksClient implements ModInitializer {

    static KeyBinding k1;

    @Override
    public void onInitialize() {
        //register a keys
        k1 = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.stonks.gui", GLFW.GLFW_KEY_RIGHT_SHIFT, "key.category.stonks", () -> true));
        //check each tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //if we are in game (we should be)
            if (client.world != null) {
                boolean guiOpen = k1.isPressed();
                if (guiOpen) {
                    if (client.currentScreen == null) {
                        client.openScreen(new GuiScreen());
                    }
                }
            }
        });
    }
}
