package io.github.tobyrue.btc;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum RootWhere implements StringIdentifiable
{
    NONE("none"),
    UP("up"),
    DOWN("down"),
    NORTH("north"),
    SOUTH("south"),
    WEST("west"),
    EAST("east");

    private final String name;

    private RootWhere(String name)
    {
        this.name = name;
    }

    @Override
    public String asString()
    {
        return this.name;
    }

    public static RootWhere of(Direction direction)
    {
        if (direction == Direction.UP)    return RootWhere.UP;
        if (direction == Direction.DOWN)  return RootWhere.DOWN;
        if (direction == Direction.NORTH) return RootWhere.NORTH;
        if (direction == Direction.EAST)  return RootWhere.EAST;
        if (direction == Direction.SOUTH) return RootWhere.SOUTH;

        assert(direction == Direction.WEST);
        return RootWhere.WEST;
    }
}
