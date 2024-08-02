package io.github.tobyrue.btc.registry;

import io.github.tobyrue.btc.BTC;
import io.github.tobyrue.btc.block.DungeonWireBlock;
import io.github.tobyrue.btc.block.OminousBeaconBlock;
import io.github.tobyrue.btc.block.PedestalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom blocks inside the mod.
 * @see {@link net.minecraft.block.Blocks}
 */
public final class ModBlocks
{
    public static final OminousBeaconBlock OMINOUS_BEACON = register("ominous_beacon", new OminousBeaconBlock(
        AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.VAULT).luminance((state) -> {
            return 14;
        })
    ));

    public static final PressurePlateBlock DUNGEON_PRESSURE_PLATE = register("dungeon_pressure_plate", new PressurePlateBlock(
        BlockSetType.STONE, AbstractBlock.Settings.create().strength(-1.0F, 3600000.0F)
    ));

    public static final PedestalBlock PEDESTAL = register("pedestal", new PedestalBlock(
        AbstractBlock.Settings.create().nonOpaque().luminance((state) -> {
            return 12;
        }).strength(-1.0F, 3600000.0F)
    ));

    public static final DungeonWireBlock DUNGEON_WIRE = register("dungeon_wire", new DungeonWireBlock(
        AbstractBlock.Settings.create().strength(1000000.0F, 3600000.0F)
    ));

    /**
     * Registers a new block.
     * @param <T> The type of the block to register.
     * @param id The id used to reference the block.
     * @param block The block to register.
     * @return The block as it was registered.
     */
    private static <T extends Block> T register(String id, T block)
    {
        return register(id, block, true);
    }

    /**
     * Registers a new block.
     * @param <T> The type of the block to register.
     * @param id The id used to reference the block.
     * @param block The block to register.
     * @param shouldRegisterItem An indicator whether to register a {@link net.minecraft.item.Item Item} for the block as well.
     * @return The block as it was registered.
     */
    private static <T extends Block> T register(String id, T block, boolean shouldRegisterItem)
    {
        Identifier guid = Identifier.of(BTC.MOD_ID, id);

        T result = Registry.register(Registries.BLOCK, guid, block);

        if (shouldRegisterItem)
        {
            Registry.register(Registries.ITEM, guid, new BlockItem(result, new Item.Settings()));
        }

        return result;
    }

    /**
     * Initialize the mod blocks class.
     * @remarks Must be called even when empty.
     */
    public static void initialize()
    {
    }
}
