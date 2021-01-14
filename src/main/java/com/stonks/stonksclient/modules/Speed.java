package com.stonks.stonksclient.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
    @Setting
    public float speed = 2.5f;
    private boolean toggle = true;

    @Override
    public String getName() {
        return "speed";
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
            if (mc.player != null && activated) {
                if (mc.options.keyForward.isPressed() && toggle) {
                    //go fast
                    Vec3d v = mc.player.getVelocity();
                    mc.player.setVelocity(v.x * speed, v.y, v.z * speed);
                    toggle = false;
                } else if (!toggle) {
                    toggle = true;
                }
            }
        });
    }

    @Override
    public Category getCategory() {
        return Category.MOVEMENT;
    }
}
