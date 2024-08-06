package io.github.tobyrue.btc;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum RootWhere implements StringIdentifiable
{
    NONE("none", Direction.UP),
    UP("up", Direction.UP),
    DOWN("down", Direction.DOWN),
    NORTH("north", Direction.NORTH),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST),
    EAST("east", Direction.EAST);

    private final String name;
    private final Direction direction;

    private RootWhere(String name, Direction direction)
    {
        this.name = name;
        this.direction = direction;
    }

    @Override
    public String asString()
    {
        return this.name;
    }

    public Direction toDirection()
    {
        assert(this != RootWhere.NONE);
        return this.direction;
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
