package com.obsidian;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.obsidian.ObsidianMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaterialRuleData {
    private static final BiMap<RegistryKey<DimensionType>, Set<MaterialRules.MaterialRule>> materialRulesMap = HashBiMap.create();

    public static Set<MaterialRules.MaterialRule> getDimensionMaterialRules(RegistryKey<DimensionType> dimensionType) {
        return materialRulesMap.get(dimensionType);
    }


    public static void addEndMaterialRule(MaterialRules.MaterialRule materialRule) {
        addMaterialRule(DimensionTypes.THE_END, materialRule);
    }
    public static void addNetherMaterialRule(MaterialRules.MaterialRule materialRule) {
        addMaterialRule(DimensionTypes.THE_NETHER, materialRule);
    }

    /**
     * @deprecated This may lead to unintended effects with mod dimensions due to the system's nature.
     */
    @Deprecated(forRemoval = true)
    public static void addOverworldMaterialRule(MaterialRules.MaterialRule materialRule) {
        addMaterialRule(DimensionTypes.OVERWORLD, materialRule);
    }
    public static void addMaterialRule(RegistryKey<DimensionType> dimensionType, MaterialRules.MaterialRule materialRule) {
        if(!materialRulesMap.containsKey(dimensionType)) {
            materialRulesMap.put(dimensionType, new HashSet<>());
        }
        Set<MaterialRules.MaterialRule> newRuleSet = materialRulesMap.get(dimensionType);
        newRuleSet.add(materialRule);
        materialRulesMap.forcePut(dimensionType, newRuleSet);
    }
}
