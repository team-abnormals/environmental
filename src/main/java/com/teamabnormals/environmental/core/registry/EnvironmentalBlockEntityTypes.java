package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import com.teamabnormals.environmental.common.block.entity.KilnBlockEntity;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBlockEntityTypes {
	public static final BlockEntitySubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBlockEntitySubHelper();

	public static final RegistryObject<BlockEntityType<KilnBlockEntity>> KILN = HELPER.createBlockEntity("kiln", KilnBlockEntity::new, () -> new Block[]{EnvironmentalBlocks.KILN.get()});
}
