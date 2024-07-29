package net.br1ghtsteel.amberandcopper.block.properties;

import net.minecraft.util.StringIdentifiable;

public enum BarShape implements StringIdentifiable {
    STRAIGHT("straight"),
    CURVE_UP("curve_up"),
    CURVE_DOWN("curve_down");

    private final String name;

    private BarShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
