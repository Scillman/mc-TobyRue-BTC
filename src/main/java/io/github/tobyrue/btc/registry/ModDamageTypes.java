package io.github.tobyrue.btc.registry;

import io.github.tobyrue.btc.BTC;
import net.minecraft.entity.damage.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Contains all the custom damage types inside the mod.
 */
public final class ModDamageTypes
{
    public static final RegistryKey<DamageType> BEACON_BURN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(BTC.MOD_ID, "beacon_burn"));

    /**
     * Get the damage source associated with the registered damage type.
     * @param world The world whoms registry to use.
     * @param key The damage type registry key.
     * @return An instance of the damage source class.
     */
    public static DamageSource of(World world, RegistryKey<DamageType> key)
    {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
