package io.github.tobyrue.btc.block;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import io.github.tobyrue.btc.block.entity.PedestalBlockEntity;
import io.github.tobyrue.btc.registry.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PedestalBlock extends Block implements BlockEntityProvider
{
    public PedestalBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        Optional<PedestalBlockEntity> blockEntity = world.getBlockEntity(pos, ModBlockEntities.PEDESTAL_BLOCK_ENTITY);
        assert(blockEntity.isPresent());
        return blockEntity.get().onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new PedestalBlockEntity(pos, state);
    }
}
