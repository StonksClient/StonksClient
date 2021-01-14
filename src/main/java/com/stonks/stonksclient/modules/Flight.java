package com.stonks.stonksclient.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class Flight extends Module {
    @Override
    public String getName() {
        return "flight";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            mc.player.abilities.setFlySpeed(0.05f);
            mc.player.abilities.flying = false;
            if (mc.player.abilities.creativeMode) return;
            mc.player.abilities.allowFlying = false;
        }
    }

    @Override
    public void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            if (activated && mc.world != null && mc.player != null) {
                mc.player.abilities.setFlySpeed(1 / 20f);
                mc.player.abilities.flying = true;
                if (mc.player.abilities.creativeMode) return;
                mc.player.abilities.allowFlying = true;
            }
        });
    }

    @Override
    public Category getCategory() {
        return Category.MOVEMENT;
    }
}
