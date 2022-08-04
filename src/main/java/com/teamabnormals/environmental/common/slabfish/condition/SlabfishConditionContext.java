package com.teamabnormals.environmental.common.slabfish.condition;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>A context used for determining what kinds of slabfish can be spawned.</p>
 *
 * @author Ocelot
 */
public class SlabfishConditionContext {
	private final Event event;
	private final LazyLoadedValue<Random> random;
	private final LazyLoadedValue<String> name;
	private final LazyLoadedValue<BlockPos> pos;
	private final LazyLoadedValue<Biome> biome;
	private final LazyLoadedValue<Boolean> inRaid;
	private final LazyLoadedValue<BlockState> inBlock;
	private final LazyLoadedValue<FluidState> inFluid;
	private final LazyLoadedValue<Boolean> dayTime;
	private final LazyLoadedValue<Boolean> nightTime;
	private final LazyLoadedValue<Integer> light;
	private final Map<LightLayer, LazyLoadedValue<Integer>> lightTypes;
	private final LazyLoadedValue<ResourceLocation> dimension;
	private final LazyLoadedValue<ResourceLocation> slabfishType;
	private final LazyLoadedValue<Boolean> breederInsomnia;
	private final Pair<SlabfishType, SlabfishType> parents;

	private SlabfishConditionContext(Slabfish slabfish, Event event, @Nullable ServerPlayer breeder, @Nullable Slabfish parent1, @Nullable Slabfish parent2) {
		ServerLevel world = (ServerLevel) slabfish.getCommandSenderWorld();
		this.event = event;
		this.random = new LazyLoadedValue<>(world::getRandom);
		this.name = new LazyLoadedValue<>(() -> slabfish.getDisplayName().getString().trim());
		this.pos = new LazyLoadedValue<>(() -> new BlockPos(slabfish.position()));
		this.biome = new LazyLoadedValue<>(() -> world.getBiome(this.pos.get()).value());
		this.inRaid = new LazyLoadedValue<>(() -> world.getRaidAt(this.pos.get()) != null);
		this.inBlock = new LazyLoadedValue<>(() -> world.getBlockState(this.pos.get()));
		this.inFluid = new LazyLoadedValue<>(() -> world.getFluidState(this.pos.get()));
		this.dayTime = new LazyLoadedValue<>(world::isDay);
		this.nightTime = new LazyLoadedValue<>(world::isNight);
		this.light = new LazyLoadedValue<>(() -> world.getMaxLocalRawBrightness(this.pos.get()));
		this.lightTypes = new HashMap<>();
		for (LightLayer lightType : LightLayer.values())
			this.lightTypes.put(lightType, new LazyLoadedValue<>(() -> world.getBrightness(lightType, this.pos.get())));
		this.dimension = new LazyLoadedValue<>(() -> world.dimension().location());
		this.slabfishType = new LazyLoadedValue<>(slabfish::getSlabfishType);
		this.breederInsomnia = new LazyLoadedValue<>(() -> breeder != null && breeder.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) >= 72000 && world.isNight());
		SlabfishManager slabfishManager = SlabfishManager.get(world);
		this.parents = parent1 != null && parent2 != null ? new ImmutablePair<>(slabfishManager.getSlabfishType(parent1.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH), slabfishManager.getSlabfishType(parent2.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH)) : null;
	}

	/**
	 * Fetches a new context for the specified entity when spawned.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext spawned(Slabfish slabfish) {
		return new SlabfishConditionContext(slabfish, Event.SPAWN, null, null, null);
	}

	/**
	 * Fetches a new context for the specified entity when renamed.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext rename(Slabfish slabfish) {
		return new SlabfishConditionContext(slabfish, Event.RENAME, null, null, null);
	}

	/**
	 * Fetches a new context for the specified entity when struck by lightning.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext lightning(Slabfish slabfish) {
		return new SlabfishConditionContext(slabfish, Event.LIGHTNING, null, null, null);
	}

	/**
	 * Fetches a new context for the specified entity with the two parents.
	 *
	 * @param slabfish The entity to focus on
	 * @param breeder  The player that bred the two parents together
	 * @param parent1  The first parent of breeding with
	 * @param parent2  The second parent of breeding
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext breeding(Slabfish slabfish, @Nullable ServerPlayer breeder, Slabfish parent1, Slabfish parent2) {
		return new SlabfishConditionContext(slabfish, Event.BREED, breeder, parent1, parent2);
	}

	/**
	 * @return The type of event this context is fired under
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @return The slabfish world random number generator
	 */
	public Random getRandom() {
		return this.random.get();
	}

	/**
	 * @return The name of the slabfish
	 */
	public String getName() {
		return this.name.get();
	}

	/**
	 * @return The position of the slabfish
	 */
	public BlockPos getPos() {
		return this.pos.get();
	}

	/**
	 * @return The biome the slabfish is in
	 */
	public Biome getBiome() {
		return this.biome.get();
	}

	/**
	 * @return Whether or not it is currently day
	 */
	public boolean isDay() {
		return this.dayTime.get();
	}

	/**
	 * @return Whether or not it is currently night
	 */
	public boolean isNight() {
		return this.nightTime.get();
	}

	/**
	 * @return Whether or not a raid is currently ongoing
	 */
	public boolean isInRaid() {
		return this.inRaid.get();
	}

	/**
	 * @return Whether or not the slabfish is currently in water
	 */
	public boolean isInBlock(Block block) {
		return this.inBlock.get().is(block);
	}

	/**
	 * @return Whether or not the slabfish is currently in that tag
	 */
	public boolean isInBlock(TagKey<Block> tag) {
		return this.inBlock.get().is(tag);
	}

	/**
	 * @return Whether or not the slabfish is currently in that tag
	 */
	public boolean isInFluid(TagKey<Fluid> tag) {
		return this.inFluid.get().is(tag);
	}

	/**
	 * @return The light value at the slabfish position
	 */
	public int getLight() {
		return this.light.get();
	}

	/**
	 * Fetches light for the specified type of light
	 *
	 * @param lightType The type of light to get
	 * @return The sky light value at the slabfish position
	 */
	public int getLightFor(LightLayer lightType) {
		return this.lightTypes.get(lightType).get();
	}

	/**
	 * @return The dimension the slabfish is in
	 */
	public ResourceLocation getDimension() {
		return this.dimension.get();
	}

	/**
	 * @return The type of slabfish this slabfish was before trying to undergo a change
	 */
	public ResourceLocation getSlabfishType() {
		return this.slabfishType.get();
	}

	/**
	 * @return Whether or not the player that bred the two slabfish together has insomnia
	 */
	public boolean isBreederInsomnia() {
		return this.breederInsomnia.get();
	}

	/**
	 * @return The types of slabfish the parents were or null if there are no parents
	 */
	@Nullable
	public Pair<SlabfishType, SlabfishType> getParentTypes() {
		return this.parents;
	}

	/**
	 * The type a context can be fired under.
	 */
	public enum Event {
		SPAWN, RENAME, LIGHTNING, BREED
	}
}
