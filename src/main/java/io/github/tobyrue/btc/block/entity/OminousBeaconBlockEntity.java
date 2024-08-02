package io.github.tobyrue.btc.block.entity;

import io.github.tobyrue.btc.block.OminousBeaconBlock;
import io.github.tobyrue.btc.registry.ModBlockEntities;
import io.github.tobyrue.btc.registry.ModBlocks;
import io.github.tobyrue.btc.registry.ModDamageTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class OminousBeaconBlockEntity extends BlockEntity implements BlockEntityTicker<OminousBeaconBlockEntity>
{
    private static final int MAX_LENGTH = 16;
    private int beamLength = 0;

    public OminousBeaconBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.OMINOUS_BEACON_BLOCK_ENTITY, pos, state);
    }

    public void onWalkedIn(World world, BlockPos pos, BlockState state, Entity entity)
    {
        entity.damage(ModDamageTypes.of(world, ModDamageTypes.BEACON_BURN), 2.0f);
    }

    public int getBeamLength()
    {
        return this.beamLength;
    }

    private void updateBeam(World world, BlockPos pos, BlockState state)
    {
        Direction direction = state.get(OminousBeaconBlock.FACING);
        if (direction != Direction.UP && direction != Direction.NORTH && direction != Direction.EAST)
        {
            this.beamLength = 0;
            return;
        }

        for (int l = 1; l < MAX_LENGTH + 2; l++)
        {
            BlockPos offsetPos = pos.offset(direction, l);
            BlockState offsetState = world.getBlockState(offsetPos);

            if (offsetState.getBlock() == ModBlocks.OMINOUS_BEACON && offsetState.get(OminousBeaconBlock.FACING) == direction.getOpposite())
            {
                this.beamLength = l - 1;
                break;
            }

            if (!offsetState.isOf(Blocks.BEDROCK) && offsetState.getOpacity(world, offsetPos) >= 15)
            {
                world.breakBlock(offsetPos, true);
            }
        }

        world.getNonSpectatingEntities(LivingEntity.class, new Box(pos.toCenterPos(), pos.offset(direction, this.beamLength).toCenterPos()).expand(0.5)).forEach(entity -> entity.damage(ModDamageTypes.of(world, ModDamageTypes.BEACON_BURN), 2.0f));
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, OminousBeaconBlockEntity blockEntity)
    {
        updateBeam(world, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup)
    {
        return this.createNbt(registryLookup);
    }

    // @Override
    // public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    // {
    //     super.readNbt(nbt, registryLookup);
    // }

    // @Override
    // public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    // {
    //     super.writeNbt(nbt, registryLookup);
    // }

    // @Override
    // protected void readComponents(BlockEntity.ComponentsAccess components)
    // {
    //     super.readComponents(components);
    // }

    // @Override
    // protected void addComponents(ComponentMap.Builder componentMapBuilder)
    // {
    //     super.addComponents(componentMapBuilder);
    // }
}
