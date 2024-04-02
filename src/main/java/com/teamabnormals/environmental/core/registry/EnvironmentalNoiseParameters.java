package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalNoiseParameters {
    public static final DeferredRegister<NormalNoise.NoiseParameters> NOISE_PARAMETERS = DeferredRegister.create(Registry.NOISE_REGISTRY, Environmental.MOD_ID);

    public static final RegistryObject<NormalNoise.NoiseParameters> PINE_BARRENS_STONE = NOISE_PARAMETERS.register("pine_barrens_stone", () -> new NormalNoise.NoiseParameters(-4, 1.0D));
    public static final RegistryObject<NormalNoise.NoiseParameters> NOISE_CUP_LICHEN = NOISE_PARAMETERS.register("noise_cup_lichen", () -> new NormalNoise.NoiseParameters(-7, 1.0D));
}