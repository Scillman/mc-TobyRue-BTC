package io.github.tobyrue.btc.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class BTCDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        // FabricDataGenerator.Pack pack = generator.createPack();

        // pack.addProvider(ModLanguageProvider::new);
        // pack.addProvider(ModBlockTagProvider::new);
        // pack.addProvider(ModItemTagProvider::new);
        // pack.addProvider(ModLootTableProvider::new);
        // pack.addProvider(ModModelProvider::new);
        // pack.addProvider(ModRecipeProvider::new);
    }
}
