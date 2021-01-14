package com.stonks.stonksclient.modules;
/*
   NOTE : this class NEEDS to be extended ONLY ONCE per class AND needs to have a DEFAULT CONSTRUCTOR
 */
public abstract class Module {
    public boolean activated = false;

    public abstract String getName();

    public void toggleActivated() {
        activated = !activated;
        if (activated) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public int getKeyCode() {
        return 0;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void registerEvents();

    public abstract Category getCategory();

    public enum Category {
        COMBAT,
        MOVEMENT,
        WORLD,
        PLAYER
    }
}

