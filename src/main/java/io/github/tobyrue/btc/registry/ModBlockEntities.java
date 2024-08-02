package io.github.tobyrue.btc.registry;

import io.github.tobyrue.btc.BTC;
import io.github.tobyrue.btc.block.entity.OminousBeaconBlockEntity;
import io.github.tobyrue.btc.block.entity.PedestalBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom block entities inside the mod.
 */
public final class ModBlockEntities
{
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_BLOCK_ENTITY =
        register("staff_pedestal", PedestalBlockEntity::new, ModBlocks.PEDESTAL);

    public static final BlockEntityType<OminousBeaconBlockEntity> OMINOUS_BEACON_BLOCK_ENTITY =
        register("ominous_beacon", OminousBeaconBlockEntity::new, ModBlocks.OMINOUS_BEACON);

    /**
     * Registers a custom block entity type.
     * @param <T> The type of the block entity type to register.
     * @param id The id used to reference the block entity type.
     * @param factory The factory to use to create am instance of the block entity.
     * @param block The block associated with the block entity.
     * @return The block entity type as it was registered.
     */
    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityFactory<T> factory, Block block)
    {
        return Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(BTC.MOD_ID, id),
            BlockEntityType.Builder.create(factory, block).build()
        );
    }

    /**
     * Initialize the mod block entities class.
     * @remarks Must be called even when empty.
     */
    public static void initialize()
    {

    }
}
