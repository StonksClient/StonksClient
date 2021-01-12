package com.stonks.stonksclient.modules;

public interface Module {


    String getName();

    void toggleActivated();

    boolean isActivated();

    int getKeyCode();

    void onEnable();

    void onDisable();

    enum Category {
        COMBAT,
        MOVMENT,
        WORLD,
        PLAYER
    }
}

