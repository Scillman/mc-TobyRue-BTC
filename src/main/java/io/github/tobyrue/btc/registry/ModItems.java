package io.github.tobyrue.btc.registry;

import io.github.tobyrue.btc.BTC;
import io.github.tobyrue.btc.item.WindStaffItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Contains all the custom items inside the mod.
 */
public final class ModItems
{
    public static final Item RUBY_TRIAL_KEY = register("ruby_trial_key",
        new Item(new Item.Settings())
    );

    public static final Item STAFF = register("staff",
        new Item(new Item.Settings().maxCount(1))
    );

    public static final Item WIND_STAFF = register("wind_staff",
        new WindStaffItem(new Item.Settings().maxCount(1))
    );

    /**
     * Register an item.
     * @param <T> The type of the item to register.
     * @param id The id used to reference the item.
     * @param item The item to register.
     * @return The item as it was registerd.
     */
    private static <T extends Item> T register(String id, T item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(BTC.MOD_ID, id), item);
    }

    /**
     * Initialize the mod items class.
     * @remarks Must be called even when empty.
     */
    public static void initialize()
    {
    }
}