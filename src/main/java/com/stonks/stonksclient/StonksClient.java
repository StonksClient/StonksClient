package com.stonks.stonksclient;

import com.stonks.stonksclient.events.Event;
import com.stonks.stonksclient.modules.Knockback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class StonksClient implements ModInitializer {

    static KeyBinding k1;
    private static final HashMap<Class<? extends Event>, List<Consumer<Event>>> listeners = new HashMap<>();
    private Knockback kb = new Knockback();

    public static <T extends Event> void dispatchEvent(T e) {
        for (Consumer<Event> con : listeners.get(e.getClass())) {
            con.accept(e);
        }
    }

    public static <T extends Event> void registerListener(Class<T> event, Consumer<Event> lis) {
        List<Consumer<Event>> ar = listeners.get(event);
        if (ar == null) {
            listeners.put(event, Collections.singletonList(lis));
        } else {
            ar.add(lis);
        }
    }

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
        //register every listener
        kb.registerEvents();
    }
}
