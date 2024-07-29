package net.br1ghtsteel.amberandcopper.block.properties;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum BarShape implements StringIdentifiable {
    STRAIGHT("straight", 0, Direction.AxisDirection.POSITIVE, Direction.AxisDirection.NEGATIVE),
    POSITIVE_POSITIVE_CORNER("positive_positive", 1, Direction.AxisDirection.POSITIVE, Direction.AxisDirection.POSITIVE),
    POSITIVE_NEGATIVE_CORNER("positive_negative", 2, Direction.AxisDirection.POSITIVE, Direction.AxisDirection.NEGATIVE),
    NEGATIVE_NEGATIVE_CORNER("negative_negative", 3, Direction.AxisDirection.NEGATIVE, Direction.AxisDirection.NEGATIVE),
    NEGATIVE_POSITIVE_CORNER("negative_positive", 4, Direction.AxisDirection.NEGATIVE, Direction.AxisDirection.POSITIVE);

    public static final BarShape[] VALUES = values();
    public static final StringIdentifiable.Codec<BarShape> CODEC = StringIdentifiable.createCodec(BarShape::values);
    private final String name;
    private final int id;
    private final Direction.AxisDirection firstAxisDirection;
    private final Direction.AxisDirection secondAxisDirection;
    private static final BarShape[] CORNERS = new BarShape[] {
        NEGATIVE_NEGATIVE_CORNER,
        NEGATIVE_POSITIVE_CORNER,
        POSITIVE_NEGATIVE_CORNER,
        POSITIVE_POSITIVE_CORNER };


    private BarShape(String name, int id, Direction.AxisDirection firstAxisDirection, Direction.AxisDirection secondAxisDirection) {
        this.name = name;
        this.id = id;
        this.firstAxisDirection = firstAxisDirection;
        this.secondAxisDirection = secondAxisDirection;
    }

    public String toString() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Direction.AxisDirection getFirstAxisDirection() {
        return this.firstAxisDirection;
    }

    public Direction.AxisDirection getSecondAxisDirection() {
        return this.secondAxisDirection;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static BarShape getCornerFromAxisDirections(Direction.AxisDirection firstAxisDirection, Direction.AxisDirection secondAxisDirection) {
        int index = (firstAxisDirection == Direction.AxisDirection.POSITIVE ? 2 : 0) | (secondAxisDirection == Direction.AxisDirection.POSITIVE ? 1 : 0);
        return CORNERS[index];
    }
}
