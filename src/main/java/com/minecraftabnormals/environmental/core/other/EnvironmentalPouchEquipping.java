package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentalPouchEquipping {

    private static final List<Structure<?>> STRUCTURES_FOR_POUCHES = new ArrayList<>();

    public static void setupPouchStructures(){
        STRUCTURES_FOR_POUCHES.add(Structure.MINESHAFT);
        STRUCTURES_FOR_POUCHES.add(Structure.STRONGHOLD);

        if (ModList.get().isLoaded("betterstrongholds")) {
            STRUCTURES_FOR_POUCHES.add(getStructure("betterstrongholds", "stronghold"));
        }

        if (ModList.get().isLoaded("bettermineshafts")) {
            STRUCTURES_FOR_POUCHES.add(getStructure("bettermineshafts", "mineshaft"));
        }

        if (ModList.get().isLoaded("endrem")) {
            STRUCTURES_FOR_POUCHES.add(getStructure("endrem", "end_castle"));
        }

        if (ModList.get().isLoaded("repurposed_structures")) {
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_birch"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_dark_forest"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_desert"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_end"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_nether"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_crimson"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_warped"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_icy"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_jungle"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_ocean"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_savanna"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_stone"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_swamp"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_taiga"));
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "stronghold_nether"));

            // Mistake on RS's end. Will be fixed next RS update
            STRUCTURES_FOR_POUCHES.add(getStructure("repurposed_structures", "mineshaft_swamp_forest"));
        }
    }

    private static Structure<?> getStructure(String modid, String structureid){
        return ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(modid, structureid));
    }

    public static void equipPouch(ServerWorld world, MobEntity mobEntity, int difficultyChance){
        boolean validPouchStructure = STRUCTURES_FOR_POUCHES.stream().anyMatch(structure -> world.structureFeatureManager().getStructureAt(mobEntity.blockPosition(), true, structure).isValid());
        if (validPouchStructure && Math.random() < difficultyChance * 0.01F) {
            mobEntity.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(EnvironmentalItems.HEALER_POUCH.get()));
            mobEntity.setDropChance(EquipmentSlotType.CHEST, 1.0F);
        }
    }
}
