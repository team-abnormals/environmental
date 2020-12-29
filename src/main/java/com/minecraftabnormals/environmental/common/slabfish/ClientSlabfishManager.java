package com.minecraftabnormals.environmental.common.slabfish;

import com.minecraftabnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>A client wrapper for {@link SlabfishManager} that reflects what the server has.</p>
 *
 * @author Ocelot
 */
public final class ClientSlabfishManager implements SlabfishManager {
	static final ClientSlabfishManager INSTANCE = new ClientSlabfishManager();

	private final Map<ResourceLocation, SlabfishType> slabfishTypes;
	private final Map<ResourceLocation, SweaterType> sweaterTypes;
	private final Map<ResourceLocation, BackpackType> backpackTypes;

	private ClientSlabfishManager() {
		this.slabfishTypes = new HashMap<>();
		this.sweaterTypes = new HashMap<>();
		this.backpackTypes = new HashMap<>();
	}

	/**
	 * Receives the slabfish types from the server.
	 *
	 * @param msg The message containing the new types
	 */
	public static void receive(SSyncSlabfishTypeMessage msg) {
		INSTANCE.slabfishTypes.clear();
		INSTANCE.slabfishTypes.putAll(Arrays.stream(msg.getSlabfishTypes()).collect(Collectors.toMap(SlabfishType::getRegistryName, slabfishType -> slabfishType)));
	}

	/**
	 * Receives the sweater types from the server.
	 *
	 * @param msg The message containing the new types
	 */
	public static void receive(SSyncSweaterTypeMessage msg) {
		INSTANCE.sweaterTypes.clear();
		INSTANCE.sweaterTypes.putAll(Arrays.stream(msg.getSweaterTypes()).collect(Collectors.toMap(SweaterType::getRegistryName, sweaterType -> sweaterType)));
	}

	/**
	 * Receives the backpack types from the server.
	 *
	 * @param msg The message containing the new types
	 */
	public static void receive(SSyncBackpackTypeMessage msg) {
		INSTANCE.backpackTypes.clear();
		INSTANCE.backpackTypes.putAll(Arrays.stream(msg.getBackpackTypes()).collect(Collectors.toMap(BackpackType::getRegistryName, backpackType -> backpackType)));
	}

	@Override
	public Optional<SlabfishType> getSlabfishType(ResourceLocation registryName) {
		return Optional.ofNullable(this.slabfishTypes.get(registryName));
	}

	@Override
	public Optional<SweaterType> getSweaterType(ResourceLocation registryName) {
		return Optional.ofNullable(this.sweaterTypes.get(registryName));
	}

	@Override
	public Optional<BackpackType> getBackpackType(ResourceLocation registryName) {
		return Optional.ofNullable(this.backpackTypes.get(registryName));
	}

	@Override
	public Optional<SlabfishType> getSlabfishType(Predicate<SlabfishType> predicate, SlabfishConditionContext context) {
		throw new UnsupportedOperationException("Client does not have access to select random slabfish");
	}

	@Override
	public Optional<SweaterType> getSweaterType(ItemStack stack) {
		return this.sweaterTypes.values().stream().filter(sweaterType -> sweaterType.test(stack)).findFirst();
	}

	@Override
	public Optional<BackpackType> getBackpackType(ItemStack stack) {
		return this.backpackTypes.values().stream().filter(backpackType -> backpackType.test(stack)).findFirst();
	}

	@Override
	public Optional<SlabfishType> getRandomSlabfishType(Predicate<SlabfishType> predicate, Random random) {
		throw new UnsupportedOperationException("Client does not have access to select random slabfish");
	}

	@Override
	public SlabfishType[] getAllSlabfishTypes() {
		return this.slabfishTypes.values().toArray(new SlabfishType[0]);
	}

	@Override
	public SweaterType[] getAllSweaterTypes() {
		return this.sweaterTypes.values().toArray(new SweaterType[0]);
	}

	@Override
	public BackpackType[] getAllBackpackTypes() {
		return this.backpackTypes.values().toArray(new BackpackType[0]);
	}
}
