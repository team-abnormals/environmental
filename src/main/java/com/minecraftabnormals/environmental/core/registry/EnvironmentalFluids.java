package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.block.fluid.MudFluid;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Environmental.MODID, bus = Bus.MOD)
public class EnvironmentalFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Environmental.MODID);

    public static final RegistryObject<Fluid> MUD = FLUIDS.register("mud", () -> new MudFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_MUD = FLUIDS.register("flowing_mud", () -> new MudFluid.Flowing());
}
