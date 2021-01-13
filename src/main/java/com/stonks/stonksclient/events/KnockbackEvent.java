package com.stonks.stonksclient.events;

public class KnockbackEvent implements Event {
    public double x;
    public double y;
    public double z;

    public KnockbackEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
