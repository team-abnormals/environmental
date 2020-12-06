package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ITag;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
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
	private final LazyValue<Random> random;
	private final LazyValue<String> name;
	private final LazyValue<BlockPos> pos;
	private final LazyValue<Biome> biome;
	private final LazyValue<Boolean> inRaid;
	private final LazyValue<BlockState> inBlock;
	private final LazyValue<FluidState> inFluid;
	private final LazyValue<Boolean> dayTime;
	private final LazyValue<Boolean> nightTime;
	private final LazyValue<Integer> light;
	private final Map<LightType, LazyValue<Integer>> lightTypes;
	private final LazyValue<ResourceLocation> dimension;
	private final LazyValue<ResourceLocation> slabfishType;
	private final LazyValue<Boolean> breederInsomnia;
	private final Pair<SlabfishType, SlabfishType> parents;

	private SlabfishConditionContext(SlabfishEntity slabfish, Event event, @Nullable ServerPlayerEntity breeder, @Nullable SlabfishEntity parent1, @Nullable SlabfishEntity parent2) {
		ServerWorld world = (ServerWorld) slabfish.getEntityWorld();
		this.event = event;
		this.random = new LazyValue<>(world::getRandom);
		this.name = new LazyValue<>(() -> slabfish.getDisplayName().getString().trim());
		this.pos = new LazyValue<>(() -> new BlockPos(slabfish.getPositionVec()));
		this.biome = new LazyValue<>(() -> world.getBiome(this.pos.getValue()));
		this.inRaid = new LazyValue<>(() -> world.findRaid(this.pos.getValue()) != null);
		this.inBlock = new LazyValue<>(() -> world.getBlockState(this.pos.getValue()));
		this.inFluid = new LazyValue<>(() -> world.getFluidState(this.pos.getValue()));
		this.dayTime = new LazyValue<>(world::isDaytime);
		this.nightTime = new LazyValue<>(world::isNightTime);
		this.light = new LazyValue<>(() -> world.getLight(this.pos.getValue()));
		this.lightTypes = new HashMap<>();
		for (LightType lightType : LightType.values())
			this.lightTypes.put(lightType, new LazyValue<>(() -> world.getLightFor(lightType, this.pos.getValue())));
		this.dimension = new LazyValue<>(() -> world.func_234923_W_().func_240901_a_());
		this.slabfishType = new LazyValue<>(slabfish::getSlabfishType);
		this.breederInsomnia = new LazyValue<>(() -> breeder != null && breeder.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) >= 72000 && world.isNightTime());
		SlabfishManager slabfishManager = SlabfishManager.get(world);
		this.parents = parent1 != null && parent2 != null ? new ImmutablePair<>(slabfishManager.getSlabfishType(parent1.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH), slabfishManager.getSlabfishType(parent2.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH)) : null;
	}

	/**
	 * Fetches a new context for the specified entity when spawned.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext spawned(SlabfishEntity slabfish) {
		return new SlabfishConditionContext(slabfish, Event.SPAWN, null, null, null);
	}

	/**
	 * Fetches a new context for the specified entity when renamed.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext rename(SlabfishEntity slabfish) {
		return new SlabfishConditionContext(slabfish, Event.RENAME, null, null, null);
	}

	/**
	 * Fetches a new context for the specified entity when struck by lightning.
	 *
	 * @param slabfish The entity to focus on
	 * @return A new context with that slabfish as the focus
	 */
	public static SlabfishConditionContext lightning(SlabfishEntity slabfish) {
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
	public static SlabfishConditionContext breeding(SlabfishEntity slabfish, @Nullable ServerPlayerEntity breeder, SlabfishEntity parent1, SlabfishEntity parent2) {
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
		return this.random.getValue();
	}

	/**
	 * @return The name of the slabfish
	 */
	public String getName() {
		return this.name.getValue();
	}

	/**
	 * @return The position of the slabfish
	 */
	public BlockPos getPos() {
		return this.pos.getValue();
	}

	/**
	 * @return The biome the slabfish is in
	 */
	public Biome getBiome() {
		return this.biome.getValue();
	}

	/**
	 * @return Whether or not it is currently day
	 */
	public boolean isDay() {
		return this.dayTime.getValue();
	}

	/**
	 * @return Whether or not it is currently night
	 */
	public boolean isNight() {
		return this.nightTime.getValue();
	}

	/**
	 * @return Whether or not a raid is currently ongoing
	 */
	public boolean isInRaid() {
		return this.inRaid.getValue();
	}

	/**
	 * @return Whether or not the slabfish is currently in water
	 */
	public boolean isInBlock(Block block) {
		return this.inBlock.getValue().isIn(block);
	}

	/**
	 * @return Whether or not the slabfish is currently in that tag
	 */
	public boolean isInBlock(ITag<Block> tag) {
		return this.inBlock.getValue().isIn(tag);
	}

	/**
	 * @return Whether or not the slabfish is currently in that tag
	 */
	public boolean isInFluid(ITag<Fluid> tag) {
		return this.inFluid.getValue().isTagged(tag);
	}

	/**
	 * @return The light value at the slabfish position
	 */
	public int getLight() {
		return this.light.getValue();
	}

	/**
	 * Fetches light for the specified type of light
	 *
	 * @param lightType The type of light to get
	 * @return The sky light value at the slabfish position
	 */
	public int getLightFor(LightType lightType) {
		return this.lightTypes.get(lightType).getValue();
	}

	/**
	 * @return The dimension the slabfish is in
	 */
	public ResourceLocation getDimension() {
		return this.dimension.getValue();
	}

	/**
	 * @return The type of slabfish this slabfish was before trying to undergo a change
	 */
	public ResourceLocation getSlabfishType() {
		return this.slabfishType.getValue();
	}

	/**
	 * @return Whether or not the player that bred the two slabfish together has insomnia
	 */
	public boolean isBreederInsomnia() {
		return this.breederInsomnia.getValue();
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
