package com.stonks.stonksclient.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    @Override
    public String getName() {
        return "nofall";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            if (mc.player != null && activated && (mc.player.abilities.flying || mc.player.getVelocity().y < -0.5)) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
            }
        });
    }

    @Override
    public Category getCategory() {
        return Category.MOVEMENT;
    }
}
