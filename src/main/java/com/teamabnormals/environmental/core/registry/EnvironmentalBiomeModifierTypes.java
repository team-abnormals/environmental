package com.teamabnormals.environmental.core.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomeModifierTypes {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Environmental.MOD_ID);

	public static final RegistryObject<Codec<InvertedRemoveSpawnsBiomeModifier>> REMOVE_SPAWNS_INVERTED = BIOME_MODIFIER_SERIALIZERS.register("remove_spawns_inverted", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("inverted_biomes").forGetter(InvertedRemoveSpawnsBiomeModifier::biomes),
					RegistryCodecs.homogeneousList(ForgeRegistries.Keys.ENTITY_TYPES).fieldOf("entity_types").forGetter(InvertedRemoveSpawnsBiomeModifier::entityTypes)
			).apply(builder, InvertedRemoveSpawnsBiomeModifier::new))
	);

	public record InvertedRemoveSpawnsBiomeModifier(HolderSet<Biome> biomes, HolderSet<EntityType<?>> entityTypes) implements BiomeModifier {
		@Override
		public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
			if (phase == Phase.REMOVE && !this.biomes.contains(biome)) {
				MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
				for (MobCategory category : MobCategory.values()) {
					List<SpawnerData> spawns = spawnBuilder.getSpawner(category);
					spawns.removeIf(spawnerData -> this.entityTypes.contains(ForgeRegistries.ENTITY_TYPES.getHolder(spawnerData.type).get()));
				}
			}
		}

		@Override
		public Codec<? extends BiomeModifier> codec() {
			return REMOVE_SPAWNS_INVERTED.get();
		}
	}
}