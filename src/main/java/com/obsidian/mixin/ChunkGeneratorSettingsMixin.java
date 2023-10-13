package com.obsidian.mixin;

import com.mojang.serialization.Codec;
import com.obsidian.MaterialRuleData;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {

    @Shadow @Final public static Codec<ChunkGeneratorSettings> CODEC;


    @Shadow @Final private GenerationShapeConfig generationShapeConfig;

    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    public void surfaceRule(CallbackInfoReturnable<MaterialRules.MaterialRule> cir) {
        Set<MaterialRules.MaterialRule> newRuleSet = null;

        if (generationShapeConfig == GenerationShapeConfig.END) {
            newRuleSet = MaterialRuleData.getDimensionMaterialRules(DimensionTypes.THE_END);
        } else if (generationShapeConfig == GenerationShapeConfig.NETHER) {
            newRuleSet = MaterialRuleData.getDimensionMaterialRules(DimensionTypes.THE_NETHER);
        } else if (generationShapeConfig == GenerationShapeConfig.SURFACE) {
            newRuleSet = MaterialRuleData.getDimensionMaterialRules(DimensionTypes.OVERWORLD);
        }

        if (newRuleSet != null) {
            MaterialRules.MaterialRule oldReturnValue = cir.getReturnValue();
            cir.setReturnValue(MaterialRules.sequence(MaterialRules.sequence(newRuleSet.toArray(MaterialRules.MaterialRule[]::new)), oldReturnValue));
        }
    }
}
