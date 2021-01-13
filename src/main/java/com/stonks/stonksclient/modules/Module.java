package com.stonks.stonksclient.modules;

public abstract class Module {
    public boolean activated = true;

    public abstract String getName();

    public void toggleActivated() {
        activated = !activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public abstract int getKeyCode();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void registerEvents();

    public enum Category {
        COMBAT,
        MOVMENT,
        WORLD,
        PLAYER
    }
}

