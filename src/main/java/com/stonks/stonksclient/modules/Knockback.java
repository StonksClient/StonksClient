package com.stonks.stonksclient.modules;

import com.stonks.stonksclient.StonksClient;
import com.stonks.stonksclient.events.KnockbackEvent;

public class Knockback extends Module {


    @Override
    public String getName() {
        return "Knockback";
    }

    @Override
    public int getKeyCode() {
        return 0;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void registerEvents() {
        StonksClient.registerListener(KnockbackEvent.class, (e) -> {
            if (!activated) {
                return;
            }
            //should always be true
            if (e instanceof KnockbackEvent) {
                KnockbackEvent kb = ((KnockbackEvent) e);
                kb.x = 0;
                kb.y = 0;
                kb.z = 0;
            }
        });
    }
}
