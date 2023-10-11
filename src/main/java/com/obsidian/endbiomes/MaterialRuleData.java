package com.obsidian.endbiomes;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class MaterialRuleData {
    public static BiMap<RegistryKey<Biome>, MaterialRules.MaterialRule> endBiomeMaterialRules = HashBiMap.create();

    public static void addEndBiomeMaterialRule(RegistryKey<Biome> biomeRegistryKey, MaterialRules.MaterialRule materialRule) {
        endBiomeMaterialRules.put(biomeRegistryKey, materialRule);
    }
}
