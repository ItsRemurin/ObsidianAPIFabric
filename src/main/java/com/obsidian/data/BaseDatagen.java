package com.obsidian.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

public abstract class BaseDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        FabricTagProvider.BlockTagProvider blockTagProvider;
        if(getBlockTagProvider() != null) {
            blockTagProvider = pack.addProvider(getBlockTagProvider());
        } else {
            blockTagProvider = null;
        }
        if(getItemTagProvider() != null) {
            try {
                Constructor<FabricTagProvider.ItemTagProvider> constructor = getItemTagProvider().getConstructor(FabricDataOutput.class, CompletableFuture.class, FabricTagProvider.BlockTagProvider.class);
                pack.addProvider(((output, registriesFuture) ->
                {
                    try {
                        return constructor.newInstance(output, registriesFuture, blockTagProvider);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
            } catch (Exception ignored) {

            }
        }
        if(getDynamicRegistryProvider() != null) {
            pack.addProvider(
                    getDynamicRegistryProvider()
            );
        }
    }

    public FabricDataGenerator.Pack.RegistryDependentFactory<?> getDynamicRegistryProvider() {
        return null;
    }

    public Class<FabricTagProvider.ItemTagProvider> getItemTagProvider() {
        return null;
    }

    public FabricDataGenerator.Pack.RegistryDependentFactory<FabricTagProvider.BlockTagProvider> getBlockTagProvider() {
        return null;
    }
}
