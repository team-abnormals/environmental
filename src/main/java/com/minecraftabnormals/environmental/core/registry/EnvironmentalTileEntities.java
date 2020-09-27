package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.block.BirdNestBlock;
import com.minecraftabnormals.environmental.common.tile.BirdNestTileEntity;
import com.minecraftabnormals.environmental.common.tile.KilnTileEntity;
import com.minecraftabnormals.environmental.common.tile.SlabfishEffigyTileEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalTileEntities {
    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;

    public static final RegistryObject<TileEntityType<SlabfishEffigyTileEntity>> SLABFISH_EFFIGY = HELPER.createTileEntity("slabfish_effigy", SlabfishEffigyTileEntity::new, () -> new Block[]{EnvironmentalBlocks.SLABFISH_EFFIGY.get()});
    public static final RegistryObject<TileEntityType<KilnTileEntity>> KILN = HELPER.createTileEntity("kiln", KilnTileEntity::new, () -> new Block[]{EnvironmentalBlocks.KILN.get()});
    public static final RegistryObject<TileEntityType<BirdNestTileEntity>> BIRD_NEST = HELPER.createTileEntity("bird_nest", BirdNestTileEntity::new, () -> collectBlocks(BirdNestBlock.class));
    
    private static Block[] collectBlocks(Class<? extends Block> blockClass) {
		return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> blockClass.isInstance(block)).toArray(Block[]::new);
	}
}
