package com.obsidian.mixin;

import com.obsidian.ObsidianMod;
import com.obsidian.ObsidianMultiNoiseBiomeParameterList;
import com.obsidian.ObsidianMultiNoiseBiomeSourceParameterLists;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterLists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiNoiseBiomeSourceParameterLists.class)
public class MultiNoiseBiomeSourceParameterListsMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void bootstrap(Registerable<MultiNoiseBiomeSourceParameterList> registry, CallbackInfo ci) {
        ObsidianMod.LOGGER.info("BOOTSTRAP");

        RegistryEntryLookup<Biome> registryEntryLookup = registry.getRegistryLookup(RegistryKeys.BIOME);
        registry.register(ObsidianMultiNoiseBiomeSourceParameterLists.END, new MultiNoiseBiomeSourceParameterList(ObsidianMultiNoiseBiomeParameterList.END, registryEntryLookup));
    }
}
