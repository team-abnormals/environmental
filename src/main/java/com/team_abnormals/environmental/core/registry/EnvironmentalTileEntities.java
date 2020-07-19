package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.common.tile.KilnTileEntity;
import com.team_abnormals.environmental.common.tile.SlabfishEffigyTileEntity;
import com.team_abnormals.environmental.core.Environmental;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalTileEntities {
    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;

    public static final RegistryObject<TileEntityType<SlabfishEffigyTileEntity>> SLABFISH_EFFIGY = HELPER.createTileEntity("slabfish_effigy", SlabfishEffigyTileEntity::new, () -> new Block[]{EnvironmentalBlocks.SLABFISH_EFFIGY.get()});
    public static final RegistryObject<TileEntityType<KilnTileEntity>> KILN = HELPER.createTileEntity("kiln", KilnTileEntity::new, () -> new Block[]{EnvironmentalBlocks.KILN.get()});
}
