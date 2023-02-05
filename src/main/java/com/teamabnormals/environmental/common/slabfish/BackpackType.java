package com.teamabnormals.environmental.common.slabfish;

import com.google.gson.*;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * <p>A single type of backpack that exists.</p>
 *
 * @author Ocelot
 */
public class BackpackType implements Predicate<ItemStack> {
	private final Ingredient ingredient;
	private final LazyLoadedValue<ResourceLocation> textureLocation = new LazyLoadedValue<>(() -> new ResourceLocation(this.getRegistryName().getNamespace(), "backpack/" + this.getRegistryName().getPath()));
	private ResourceLocation registryName;
	private Component displayName;

	public BackpackType(@Nullable Component displayName, @Nullable Ingredient ingredient) {
		this.displayName = displayName;
		this.ingredient = ingredient;
	}

	/**
	 * Creates a new {@link BackpackType} from the specified {@link FriendlyByteBuf} on the client side.
	 *
	 * @param buf The buffer to read from
	 * @return A new backpack type created from the data in the buffer
	 */
	@OnlyIn(Dist.CLIENT)
	public static BackpackType readFrom(FriendlyByteBuf buf) {
		ResourceLocation registryName = buf.readResourceLocation();
		Component displayName = buf.readComponent();
		Ingredient ingredient = buf.readBoolean() ? Ingredient.fromNetwork(buf) : null;
		return new BackpackType(displayName, ingredient).setRegistryName(registryName);
	}

	@Override
	public boolean test(ItemStack stack) {
		return this.ingredient != null && this.ingredient.test(stack);
	}

	/**
	 * @return The registry name of this backpack
	 */
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	/**
	 * Sets the registry name of this backpack type.
	 *
	 * @param registryName The new registry name for this backpack
	 */
	BackpackType setRegistryName(ResourceLocation registryName) {
		this.registryName = registryName;
		if (this.displayName == null)
			this.displayName = Component.literal(registryName.toString());
		return this;
	}

	/**
	 * @return The display name to use when showing this type
	 */
	public Component getDisplayName() {
		return displayName;
	}

	/**
	 * @return The location of the texture for this backpack
	 */
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTextureLocation() {
		return this.textureLocation.get();
	}

	/**
	 * Writes this {@link BackpackType} into the specified {@link FriendlyByteBuf} for syncing with clients.
	 *
	 * @param buf The buffer to write into
	 */
	public void writeTo(FriendlyByteBuf buf) {
		buf.writeResourceLocation(this.registryName);
		buf.writeComponent(this.displayName);
		buf.writeBoolean(this.ingredient != null);
		if (this.ingredient != null)
			this.ingredient.toNetwork(buf);
	}

	@Override
	public String toString() {
		return "BackpackType{" +
				"registryName=" + registryName +
				", displayName=" + displayName.getString() +
				", ingredient=" + (ingredient == null ? null : Arrays.toString(ingredient.getItems())) +
				'}';
	}

	/**
	 * <p>Deserializes a backpack type from JSON.</p>
	 *
	 * @author Ocelot
	 */
	public static class Deserializer implements JsonDeserializer<BackpackType> {
		@Override
		public BackpackType deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			if (jsonObject.has("item") && jsonObject.has("tag"))
				throw new JsonSyntaxException("Either 'item' or 'tag' can be present");

			Component displayName = jsonObject.has("displayName") ? context.deserialize(jsonObject.get("displayName"), Component.class) : null;
			Item item = jsonObject.has("item") && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(jsonObject.get("item").getAsString())) ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(jsonObject.get("item").getAsString())) : null;
			TagKey<Item> tag = jsonObject.has("tag") ? TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(jsonObject.get("tag").getAsString())) : null;
			Ingredient ingredient = item != null ? Ingredient.of(item) : tag != null ? Ingredient.of(tag) : null;

			return new BackpackType(displayName, ingredient);
		}
	}
}
