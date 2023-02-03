package com.teamabnormals.environmental.common.slabfish;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>A single type of slabfish that exists.</p>
 *
 * @author Ocelot
 */
public class SlabfishType extends ForgeRegistryEntry<SlabfishType> implements Predicate<SlabfishConditionContext> {

	public static final Map<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> RARITIES = Util.make(new HashMap<>(), map -> {
		map.put(EnvironmentalSlabfishTypeTags.COMMON, Pair.of(1.0F, ChatFormatting.GRAY));
		map.put(EnvironmentalSlabfishTypeTags.UNCOMMON, Pair.of(0.55F, ChatFormatting.GREEN));
		map.put(EnvironmentalSlabfishTypeTags.RARE, Pair.of(0.15F, ChatFormatting.AQUA));
		map.put(EnvironmentalSlabfishTypeTags.EPIC, Pair.of(0.8F, ChatFormatting.LIGHT_PURPLE));
		map.put(EnvironmentalSlabfishTypeTags.LEGENDARY, Pair.of(0.2F, ChatFormatting.GOLD));
	});

	public static final Codec<SlabfishType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.optionalFieldOf("backpack").forGetter(type -> Optional.ofNullable(type.getBackpack())),
			Codec.STRING.optionalFieldOf("mod").forGetter(type -> Optional.ofNullable(type.getRequiredMod())),
			Codec.INT.optionalFieldOf("weight", 0).forGetter(SlabfishType::getWeight),
			SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).optionalFieldOf("conditions", new SlabfishCondition[0]).forGetter(SlabfishType::getConditions)
	).apply(instance, (backpack, requiredMod, weight, conditions) -> new SlabfishType(backpack.orElse(null), requiredMod.orElse(null), weight, conditions)));

	public static final Codec<SlabfishType> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.optionalFieldOf("backpack").forGetter(type -> Optional.ofNullable(type.getBackpack()))
	).apply(instance, (backpack) -> new SlabfishType(backpack.orElse(null), null, 0, new SlabfishCondition[0])));

	private final Supplier<Component> displayName = Suppliers.memoize(() -> new TranslatableComponent(Util.makeDescriptionId("slabfish", this.registryName())));
	private final Supplier<ResourceLocation> textureLocation = Suppliers.memoize(() -> new ResourceLocation(this.registryName().getNamespace(), "type/" + this.registryName().getPath()));
	private final Supplier<Boolean> modLoaded = Suppliers.memoize(() -> ModList.get().isLoaded(this.getRequiredMod()));

	private final ResourceLocation backpack;
	private final String requiredMod;
	private final int weight;
	private final SlabfishCondition[] conditions;

	private ResourceLocation registryName;
	private ResourceKey<SlabfishType> resourceKey;
	private Holder<SlabfishType> holder;

	private SlabfishType(@Nullable ResourceLocation backpack, @Nullable String requiredMod, int weight, SlabfishCondition[] conditions) {
		this.backpack = backpack;
		this.requiredMod = requiredMod;
		this.weight = weight;
		this.conditions = conditions;
	}

	public static TagKey<SlabfishType> getRandomRarity(float chance) {
		for (Map.Entry<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> entry : SlabfishType.RARITIES.entrySet()) {
			if (chance <= entry.getValue().getFirst())
				return entry.getKey();
		}

		return EnvironmentalSlabfishTypeTags.COMMON;
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public boolean test(SlabfishConditionContext slabfishEntity) {
		for (SlabfishCondition condition : this.conditions)
			if (!condition.test(slabfishEntity))
				return false;
		return true;
	}

	public ResourceLocation registryName() {
		if (this.getRegistryName() != null) // TODO: remove in 1.19
			this.registryName = this.getRegistryName();

		if (this.registryName == null)
			this.registryName = EnvironmentalSlabfishTypes.registryAccess().getKey(this);
		return registryName;
	}

	public ResourceKey<SlabfishType> resourceKey() {
		if (this.resourceKey == null)
			this.resourceKey = EnvironmentalSlabfishTypes.registryAccess().getResourceKey(this).orElse(null);
		return resourceKey;
	}

	public Holder<SlabfishType> holder() {
		if (this.holder == null)
			this.holder = EnvironmentalSlabfishTypes.registryAccess().getHolderOrThrow(this.resourceKey());
		return holder;
	}

	public boolean is(TagKey<SlabfishType> tag) {
		Registry<SlabfishType> registry = EnvironmentalSlabfishTypes.registryAccess();
		Optional<HolderSet.Named<SlabfishType>> set = registry.getTag(tag);
		if (set.isEmpty())
			return false;

		return set.get().contains(this.holder());
	}

	public boolean is(SlabfishType type) {
		return this == type;
	}

	/**
	 * @return The display name to use when showing this type
	 */
	public Component getDisplayName() {
		return this.displayName.get();
	}

	/**
	 * @return The location of the texture for this backpack
	 */
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTextureLocation() {
		return this.textureLocation.get();
	}

	/**
	 * @return The type of backpack this slabfish should use
	 */
	@Nullable
	public ResourceLocation getBackpack() {
		return backpack;
	}

	/**
	 * @return Whether or not this type should be loaded only if a mod with a provided name is loaded
	 */
	@Nullable
	public String getRequiredMod() {
		return requiredMod;
	}

	public boolean isModLoaded() {
		return modLoaded.get();
	}

	/**
	 * @return The priority of this slabfish. Used to determine what slabfish should be chosen over others when different types have passed conditions
	 */
	public int getWeight() {
		return weight;
	}

	public SlabfishCondition[] getConditions() {
		return conditions;
	}

	public TagKey<SlabfishType> getRarity() {
		for (Map.Entry<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> entry : SlabfishType.RARITIES.entrySet()) {
			if (this.is(entry.getKey()))
				return entry.getKey();
		}

		return EnvironmentalSlabfishTypeTags.COMMON;
	}

	@Override
	public String toString() {
		return "SlabfishType{" +
				"registryName=" + this.registryName() +
				", displayName=" + this.getDisplayName().getString() +
				", modLoaded=" + requiredMod +
				", priority=" + weight +
				'}';
	}

	public static final class Builder {
		private final List<SlabfishCondition> conditions = new LinkedList<>();

		private ResourceLocation backpack;
		private String requiredMod;
		private int weight;

		private Builder() {
		}

		public Builder setBackpack(ResourceLocation type) {
			this.backpack = type;
			return this;
		}

		public Builder setRequiredMod(String modId) {
			this.requiredMod = modId;
			return this;
		}

		public Builder setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		public Builder addCondition(SlabfishCondition condition) {
			this.conditions.add(condition);
			return this;
		}

		public SlabfishType build() {
			return new SlabfishType(this.backpack, this.requiredMod, this.weight, this.conditions.toArray(SlabfishCondition[]::new));
		}
	}
}
