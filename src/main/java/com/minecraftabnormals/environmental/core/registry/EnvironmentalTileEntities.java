package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.TileEntitySubRegistryHelper;
import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.tile.BirdNestTileEntity;
import com.minecraftabnormals.environmental.common.tile.KilnTileEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalTileEntities {
	public static final TileEntitySubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getTileEntitySubHelper();

	public static final RegistryObject<TileEntityType<KilnTileEntity>> KILN = HELPER.createTileEntity("kiln", KilnTileEntity::new, () -> new Block[]{EnvironmentalBlocks.KILN.get()});
	public static final RegistryObject<TileEntityType<BirdNestTileEntity>> BIRD_NEST = HELPER.createTileEntity("bird_nest", BirdNestTileEntity::new, () -> TileEntitySubRegistryHelper.collectBlocks(BirdNestBlock.class));

}
