package io.github.tobyrue.btc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.tobyrue.btc.registry.ModBlockEntities;
import io.github.tobyrue.btc.registry.ModBlocks;
import io.github.tobyrue.btc.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class BTC implements ModInitializer
{
    public static final String MOD_ID = "btc";
    public static final String MOD_NAME = "BTC";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    /**
     * Prints text to the system output stream.
     * @param args Arguments that need to be printed to the system output stream.
     */
    public static void println(Object... args)
    {
        System.out.println(String.join(" ", java.util.Arrays.stream(args).map(Object::toString).toArray(String[]::new)));
    }

    /**
     * Called by the Fabric Loader. (e.g. mod entry point)
     */
    @Override
    public void onInitialize()
    {
        ModBlocks.initialize();
        ModItems.initialize();
        ModBlockEntities.initialize();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTC::addToIngredientsGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(BTC::addToFunctionalGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(BTC::addToCombatGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(BTC::addToRedstoneGroup);
    }

    /**
     * Adds custom mod items to the Ingredients item group.
     * @param itemGroup The Ingredients item group.
     */
    private static void addToIngredientsGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModItems.RUBY_TRIAL_KEY);
        itemGroup.add(ModItems.STAFF);
    }

    /**
     * Adds custom mod items to the Functional item group.
     * @param itemGroup The Functional item group.
     */
    private static void addToFunctionalGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModBlocks.OMINOUS_BEACON);
        itemGroup.add(ModBlocks.PEDESTAL);
        itemGroup.add(ModBlocks.DUNGEON_WIRE);
    }

    /**
     * Adds custom mod items to Combat item group.
     * @param itemGroup The Combat item group.
     */
    private static void addToCombatGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModItems.STAFF);
        itemGroup.add(ModItems.WIND_STAFF);
    }

    /**
     * Add custom mod items to Redstone item group.
     * @param itemGroup The Redstone item group.
     */
    private static void addToRedstoneGroup(FabricItemGroupEntries itemGroup)
    {
        itemGroup.add(ModBlocks.DUNGEON_WIRE);
        itemGroup.add(ModBlocks.DUNGEON_PRESSURE_PLATE);
    }
}
