package com.obsidian.mixin;

import com.obsidian.ObsidianMod;
import com.obsidian.ObsidianMultiNoiseBiomeParameterList;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;
import java.util.stream.Stream;

@Mixin(MultiNoiseBiomeSourceParameterList.Preset.class)
public class PresetMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;of([Ljava/lang/Object;)Ljava/util/stream/Stream;"))
    private static Stream<MultiNoiseBiomeSourceParameterList.Preset> addEndToStream(Object[] values) {
        return Stream.of(
                MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD,
                MultiNoiseBiomeSourceParameterList.Preset.NETHER,
                ObsidianMultiNoiseBiomeParameterList.END);
    }
}
