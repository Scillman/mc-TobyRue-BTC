
package io.github.tobyrue.btc.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import io.github.tobyrue.btc.BTC;
import io.github.tobyrue.btc.RootWhere;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DungeonWireBlock extends Block
{
    public static final MapCodec<DungeonWireBlock> CODEC = createCodec(DungeonWireBlock::new);

    public static final EnumProperty<RootWhere> ROOT_WHERE = EnumProperty.of("root_where", RootWhere.class);
    public static final BooleanProperty FACING_DOWN = BooleanProperty.of("facing_down");
    public static final BooleanProperty FACING_UP = BooleanProperty.of("facing_up");
    public static final BooleanProperty FACING_LEFT = BooleanProperty.of("facing_left");
    public static final BooleanProperty FACING_RIGHT = BooleanProperty.of("facing_right");
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty ROOT = BooleanProperty.of("root");
    public static final BooleanProperty MAIN = BooleanProperty.of("main");
    public static final BooleanProperty CONNECTED_MAIN = BooleanProperty.of("connected_main");
    public static final DirectionProperty FACING = Properties.FACING;

    public DungeonWireBlock(Settings settings)
    {
        super(settings);

        this.setDefaultState(this.stateManager.getDefaultState()
            .with(FACING_DOWN, false)
            .with(FACING_UP, false)
            .with(FACING_RIGHT, false)
            .with(FACING_LEFT, false)
            .with(FACING, Direction.NORTH)
            .with(ROOT, false)
            .with(MAIN, false)
            .with(CONNECTED_MAIN, false)
            .with(ROOT_WHERE, RootWhere.NONE)
            .with(POWERED, false));
    }

    @Override
    public MapCodec<? extends Block> getCodec()
    {
        return CODEC;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        // TODO: facing_X based on FACING

        return this.getDefaultState()
            .with(FACING_DOWN, false)
            .with(FACING_UP, false)
            .with(FACING_LEFT, false)
            .with(FACING_RIGHT, false)
            .with(ROOT, false)
            .with(MAIN, false)
            .with(CONNECTED_MAIN, false)
            .with(ROOT_WHERE, RootWhere.NONE)
            .with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()))
            .with(FACING, ctx.getSide().getOpposite().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(
            CONNECTED_MAIN,
            FACING_DOWN,
            FACING_LEFT,
            FACING_RIGHT,
            FACING_UP,
            FACING,
            MAIN,
            POWERED,
            ROOT_WHERE,
            ROOT
        );
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
    {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        if (!world.isClient)
        {
            updateStateBasedOnNeighbors(state, world, pos);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
    {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (!world.isClient)
        {
            updateStateBasedOnNeighbors(state, world, pos);
        }
    }

    private void updateStateBasedOnNeighbors(BlockState state, World world, BlockPos pos)
    {
        boolean facingDown = false;
        boolean facingUp = false;
        boolean facingLeft = false;
        boolean facingRight = false;
        boolean powered = false;
        boolean connectedMain = false;
        RootWhere rootWhere = RootWhere.NONE; // Default or initial value
        boolean tempPowered = false;

        for (Direction direction : Direction.values())
        {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            // if main is powered set powered to true if not powered false maybe fix????


            // If any neighbor is powered, set the flag to true

            if (state.get(ROOT))
            {
                tempPowered = true; // ROOT is true, so POWERED must be true
                System.out.println("Root Powered," + state.get(POWERED));
                world.updateNeighbors(pos, this);
            }

/*
            if (!state.get(POWERED) && state.get(ROOT))
            {
                powered = false;
                System.out.println("Root UnPowered");
            }
*/

            if (neighborState.getBlock() instanceof DungeonWireBlock)
            {
                connectedMain = (neighborState.get(MAIN) || neighborState.get(CONNECTED_MAIN));
                BTC.LOGGER.info("{} {}",
                    (connectedMain ? "connected" : "disconnected"),
                    pos
                );

                if (!neighborState.get(CONNECTED_MAIN))
                {
                    tempPowered = false;
                }

                if (state.get(CONNECTED_MAIN))
                {
                    tempPowered = neighborState.get(POWERED);
                    BTC.LOGGER.info("{} {}",
                        (tempPowered ? "powering" : "unpowering"),
                        pos
                    );
                }

                if (state.get(MAIN))
                {
                    tempPowered = neighborState.get(ROOT);
                }

//                if (state.get(CONNECTED_MAIN) && !state.get(MAIN) && neighborState.get(POWERED))
//                {
//                    System.out.println("powering" + pos);
//                    tempPowered = true;
//                }
//                if (!neighborState.get(POWERED))
//                {
//                    tempPowered = false;
//                }

                if (state.get(MAIN))
                {
                    if (direction == Direction.UP)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.UP)
                        {
                            rootWhere = RootWhere.UP;
                        }
                    }
                    else if (direction == Direction.DOWN)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.DOWN)
                        {
                            rootWhere = RootWhere.DOWN;
                        }
                    }
                    else if (direction == Direction.NORTH)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.NORTH)
                        {
                            rootWhere = RootWhere.NORTH;
                        }
                    }
                    else if (direction == Direction.EAST)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.EAST)
                        {
                            rootWhere = RootWhere.EAST;
                        }
                    }
                    else if (direction == Direction.SOUTH)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.SOUTH)
                        {
                            rootWhere = RootWhere.SOUTH;
                        }
                    }
                    else if (direction == Direction.WEST)
                    {
                        if (neighborState.get(ROOT) || neighborState.get(ROOT_WHERE) == RootWhere.WEST)
                        {
                            rootWhere = RootWhere.WEST;
                        }
                    }
                }
            }

            if ((neighborState.getBlock() instanceof DungeonWireBlock))
            {
                if (direction == Direction.UP)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.UP)
                    {
                        tempPowered = true;
                    }
                }
                else if (direction == Direction.DOWN)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.DOWN)
                    {
                        tempPowered = true;
                    }
                }
                else if (direction == Direction.NORTH)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.NORTH)
                    {
                        tempPowered = true;
                    }
                }
                else if (direction == Direction.EAST)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.EAST)
                    {
                        tempPowered = true;
                    }
                }
                else if (direction == Direction.SOUTH)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.SOUTH)
                    {
                        tempPowered = true;
                    }
                }
                else if (direction == Direction.WEST)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.WEST)
                    {
                        tempPowered = true;
                    }
                }
            }
            else
            {
                if (direction == Direction.UP)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.UP)
                    {
                        tempPowered = false;
                    }
                }
                else if (direction == Direction.DOWN)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.DOWN)
                    {
                        tempPowered = false;
                    }
                }
                else if (direction == Direction.NORTH)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.NORTH)
                    {
                        tempPowered = false;
                    }
                }
                else if (direction == Direction.EAST)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.EAST)
                    {
                        tempPowered = false;
                    }
                }
                else if (direction == Direction.SOUTH)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.SOUTH)
                    {
                        tempPowered = false;
                    }
                }
                else if (direction == Direction.WEST)
                {
                    if (state.get(ROOT_WHERE) == RootWhere.WEST)
                    {
                        tempPowered = false;
                    }
                }
            }

            if (neighborState.getBlock() instanceof DungeonWireBlock)
            {
                if (state.get(FACING) == Direction.UP)
                {
                    switch (direction)
                    {
                        case UP:                        break;
                        case DOWN:                      break;
                        case NORTH: facingDown  = true; break;
                        case EAST:  facingLeft  = true; break;
                        case SOUTH: facingUp    = true; break;
                        case WEST:  facingRight = true; break;
                        default:
                            break;
                    }
                }
                else if (state.get(FACING) == Direction.DOWN)
                {
                    switch (direction)
                    {
                        case UP:                        break;
                        case DOWN:                      break;
                        case NORTH: facingUp    = true; break;
                        case EAST:  facingLeft  = true; break;
                        case SOUTH: facingDown  = true; break;
                        case WEST:  facingRight = true; break;
                        default:
                            break;
                    }
                }
                else if (state.get(FACING) == Direction.NORTH)
                {
                    switch (direction)
                    {
                        case UP:    facingUp    = true; break;
                        case DOWN:  facingDown  = true; break;
                        case NORTH:                     break;
                        case EAST:  facingLeft  = true; break;
                        case SOUTH:                     break;
                        case WEST:  facingRight = true; break;
                        default:
                            break;
                    }
                }
                else if (state.get(FACING) == Direction.EAST)
                {
                    switch (direction) {
                        case UP:    facingUp    = true; break;
                        case DOWN:  facingDown  = true; break;
                        case NORTH: facingRight = true; break;
                        case EAST:                      break;
                        case SOUTH: facingLeft  = true; break;
                        case WEST:                      break;
                        default:
                            break;
                    }
                }
                else if (state.get(FACING) == Direction.SOUTH)
                {
                    switch (direction)
                    {
                        case UP:    facingUp    = true; break;
                        case DOWN:  facingDown  = true; break;
                        case NORTH:                     break;
                        case EAST:  facingRight = true; break;
                        case SOUTH:                     break;
                        case WEST:  facingLeft  = true; break;
                        default:
                            break;
                    }
                }
                else if (state.get(FACING) == Direction.WEST)
                {
                    switch (direction)
                    {
                        case UP:    facingUp    = true; break;
                        case DOWN:  facingDown  = true; break;
                        case NORTH: facingLeft  = true; break;
                        case EAST:                      break;
                        case SOUTH: facingRight = true; break;
                        case WEST:                      break;
                        default:
                            break;
                    }
                }
            }
        }
        powered = tempPowered;

        BlockState newState = state
            .with(FACING_DOWN, facingDown)
            .with(FACING_UP, facingUp)
            .with(FACING_LEFT, facingLeft)
            .with(FACING_RIGHT, facingRight)
            .with(ROOT_WHERE, rootWhere)
            .with(CONNECTED_MAIN, connectedMain)
            .with(POWERED, powered);

        world.setBlockState(pos, newState, NOTIFY_ALL_AND_REDRAW);
    }
}
