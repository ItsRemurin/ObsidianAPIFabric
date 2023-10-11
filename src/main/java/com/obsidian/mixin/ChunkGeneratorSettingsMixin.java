package com.obsidian.mixin;

import com.obsidian.endbiomes.MaterialRuleData;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {

    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    public void surfaceRule(CallbackInfoReturnable<MaterialRules.MaterialRule> cir) {
        if(cir.getReturnValue().equals(VanillaSurfaceRules.getEndStoneRule())) {
            cir.setReturnValue(
                    MaterialRules.sequence(MaterialRules.sequence(MaterialRuleData.endBiomeMaterialRules.values().toArray(MaterialRules.MaterialRule[]::new)),
                            VanillaSurfaceRules.getEndStoneRule())
            );
        }
    }
}
