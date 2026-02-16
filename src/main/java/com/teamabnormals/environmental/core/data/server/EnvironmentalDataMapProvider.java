package com.teamabnormals.environmental.core.data.server;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.builtin.BiomeVillagerType;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalDataMapProvider extends DataMapProvider {

	public EnvironmentalDataMapProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider);
	}

	@Override
	protected void gather(Provider provider) {
		this.builder(NeoForgeDataMaps.VILLAGER_TYPES)
				.add(EnvironmentalBiomes.MARSH, new BiomeVillagerType(VillagerType.SWAMP), false)
				.add(EnvironmentalBiomes.CEDAR_SWAMP, new BiomeVillagerType(VillagerType.SWAMP), false)
				.add(EnvironmentalBiomes.CEDAR_RIVER, new BiomeVillagerType(VillagerType.SWAMP), false)
				.add(EnvironmentalBiomes.CEDAR_BANK, new BiomeVillagerType(VillagerType.SWAMP), false);

		this.builder(NeoForgeDataMaps.FURNACE_FUELS)
				.add(DWARF_SPRUCE.getId(), new FurnaceFuel(100), false)
				.add(YAK_HAIR_BLOCK.getId(), new FurnaceFuel(200), false)
				.add(YAK_HAIR_RUG.getId(), new FurnaceFuel(67), false);

		this.builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(WILLOW_LEAVES.getId(), new Compostable(0.30F), false)
				.add(WILLOW_SAPLING.getId(), new Compostable(0.30F), false)
				.add(HANGING_WILLOW_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PINE_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PINE_SAPLING.getId(), new Compostable(0.30F), false)
				.add(PINECONE.getId(), new Compostable(0.85F), false)
				.add(CEDAR_LEAVES.getId(), new Compostable(0.30F), false)
				.add(CEDAR_SAPLING.getId(), new Compostable(0.30F), false)
				.add(PLUM_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PLUM_SAPLING.getId(), new Compostable(0.30F), false)
				.add(CHEERFUL_PLUM_LEAVES.getId(), new Compostable(0.30F), false)
				.add(CHEERFUL_PLUM_SAPLING.getId(), new Compostable(0.30F), false)
				.add(MOODY_PLUM_LEAVES.getId(), new Compostable(0.30F), false)
				.add(MOODY_PLUM_SAPLING.getId(), new Compostable(0.30F), false)
				.add(EnvironmentalItems.CHERRIES, new Compostable(0.30F), false)
				.add(CHERRY_CRATE.getId(), new Compostable(1.0F), false)
				.add(EnvironmentalItems.PLUM, new Compostable(0.65F), false)
				.add(PLUM_CRATE.getId(), new Compostable(1.0F), false)
				.add(EnvironmentalItems.CATTAIL_FLUFF, new Compostable(0.30F), false)
				.add(CATTAIL_FLUFF_BLOCK.getId(), new Compostable(1.0F), false)
				.add(DUCKWEED.getId(), new Compostable(0.65F), false)
				.add(CATTAIL.getId(), new Compostable(0.65F), false)
				.add(GIANT_TALL_GRASS.getId(), new Compostable(0.65F), false)
				.add(MYCELIUM_SPROUTS.getId(), new Compostable(0.50F), false)
				.add(CUP_LICHEN.getId(), new Compostable(0.30F), false)
				.add(TREE_LICHEN.getId(), new Compostable(0.30F), false)
				.add(SHRUB.getId(), new Compostable(0.50F), false)
				.add(FLOWERING_SHRUB.getId(), new Compostable(0.50F), false)
				.add(DWARF_SPRUCE.getId(), new Compostable(0.50F), false)
				.add(EnvironmentalItems.TRUFFLE, new Compostable(0.65F), false)
				.add(CATTAIL_THATCH.getId(), new Compostable(0.65F), false)
				.add(CATTAIL_THATCH_SLAB.getId(), new Compostable(0.65F), false)
				.add(CATTAIL_THATCH_STAIRS.getId(), new Compostable(0.65F), false)
				.add(DUCKWEED_THATCH.getId(), new Compostable(0.65F), false)
				.add(DUCKWEED_THATCH_SLAB.getId(), new Compostable(0.65F), false)
				.add(DUCKWEED_THATCH_STAIRS.getId(), new Compostable(0.65F), false)
				.add(GRASS_THATCH.getId(), new Compostable(0.65F), false)
				.add(GRASS_THATCH_SLAB.getId(), new Compostable(0.65F), false)
				.add(GRASS_THATCH_STAIRS.getId(), new Compostable(0.65F), false)
				.add(WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(BLUE_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(WHITE_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PINK_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PURPLE_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(BLUE_HANGING_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(WHITE_HANGING_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PINK_HANGING_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(PURPLE_HANGING_WISTERIA_LEAVES.getId(), new Compostable(0.30F), false)
				.add(BLUE_WISTERIA_SAPLING.getId(), new Compostable(0.30F), false)
				.add(WHITE_WISTERIA_SAPLING.getId(), new Compostable(0.30F), false)
				.add(PINK_WISTERIA_SAPLING.getId(), new Compostable(0.30F), false)
				.add(PURPLE_WISTERIA_SAPLING.getId(), new Compostable(0.30F), false)
				.add(CARTWHEEL.getId(), new Compostable(0.65F), false)
				.add(VIOLET.getId(), new Compostable(0.65F), false)
				.add(DIANTHUS.getId(), new Compostable(0.65F), false)
				.add(RED_LOTUS_FLOWER.getId(), new Compostable(0.65F), false)
				.add(WHITE_LOTUS_FLOWER.getId(), new Compostable(0.65F), false)
				.add(BLUEBELL.getId(), new Compostable(0.65F), false)
				.add(TASSELFLOWER.getId(), new Compostable(0.65F), false)
				.add(YELLOW_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(ORANGE_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(RED_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(PINK_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(MAGENTA_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(PURPLE_HIBISCUS.getId(), new Compostable(0.65F), false)
				.add(HIBISCUS_LEAVES.getId(), new Compostable(0.50F), false)
				.add(BLUE_DELPHINIUM.getId(), new Compostable(0.65F), false)
				.add(WHITE_DELPHINIUM.getId(), new Compostable(0.65F), false)
				.add(PINK_DELPHINIUM.getId(), new Compostable(0.65F), false)
				.add(PURPLE_DELPHINIUM.getId(), new Compostable(0.65F), false)
				.add(BIRD_OF_PARADISE.getId(), new Compostable(0.65F), false);

		var bubbleColumnRenewables = DataMapType.builder(ResourceLocation.fromNamespaceAndPath("upgrade_aquatic", "bubble_column_renewables"), Registries.BLOCK, BubbleColumnRenewable.CODEC).build();
		this.builder(bubbleColumnRenewables)
				.add(MUDDY_SANDSTONE.getId(), new BubbleColumnRenewable(MUDDY_SAND), false);
	}

	private record BubbleColumnRenewable(Holder<Block> fallingBlock) {
		private static final Codec<BubbleColumnRenewable> CODEC = RecordCodecBuilder.create((in) -> in.group(RegistryFixedCodec.create(Registries.BLOCK).fieldOf("falling_block").forGetter(BubbleColumnRenewable::fallingBlock)).apply(in, BubbleColumnRenewable::new));
	}
}