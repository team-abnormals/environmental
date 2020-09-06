package com.minecraftabnormals.environmental.common.slabfish;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
    private ResourceLocation registryName;
    private final LazyValue<ResourceLocation> textureLocation = new LazyValue<>(() -> new ResourceLocation(this.getRegistryName().getNamespace(), "textures/entity/slabfish/backpack/" + this.getRegistryName().getPath() + ".png"));
    private ITextComponent displayName;

    public BackpackType(@Nullable ITextComponent displayName, @Nullable Ingredient ingredient) {
        this.displayName = displayName;
        this.ingredient = ingredient;
    }

    /**
     * Creates a new {@link BackpackType} from the specified {@link PacketBuffer} on the client side.
     *
     * @param buf The buffer to read from
     * @return A new backpack type created from the data in the buffer
     */
    @OnlyIn(Dist.CLIENT)
    public static BackpackType readFrom(PacketBuffer buf) {
        ResourceLocation registryName = buf.readResourceLocation();
        ITextComponent displayName = buf.readTextComponent();
        Ingredient ingredient = buf.readBoolean() ? Ingredient.read(buf) : null;
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
            this.displayName = new StringTextComponent(registryName.toString());
        return this;
    }

    /**
     * @return The display name to use when showing this type
     */
    public ITextComponent getDisplayName() {
        return displayName;
    }

    /**
     * @return The location of the texture for this backpack
     */
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTextureLocation() {
        return this.textureLocation.getValue();
    }

    /**
     * Writes this {@link BackpackType} into the specified {@link PacketBuffer} for syncing with clients.
     *
     * @param buf The buffer to write into
     */
    public void writeTo(PacketBuffer buf) {
        buf.writeResourceLocation(this.registryName);
        buf.writeTextComponent(this.displayName);
        buf.writeBoolean(this.ingredient != null);
        if (this.ingredient != null)
            this.ingredient.write(buf);
    }

    @Override
    public String toString() {
        return "BackpackType{" +
                "registryName=" + registryName +
                ", displayName=" + displayName.getString() +
                ", ingredient=" + (ingredient == null ? null : Arrays.toString(ingredient.getMatchingStacks())) +
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

            ITextComponent displayName = jsonObject.has("displayName") ? context.deserialize(jsonObject.get("displayName"), ITextComponent.class) : null;
            Item item = jsonObject.has("item") && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(jsonObject.get("item").getAsString())) ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(jsonObject.get("item").getAsString())) : null;
            ITag<Item> tag = jsonObject.has("tag") ? TagCollectionManager.func_232928_e_().func_232925_b_().get(new ResourceLocation(jsonObject.get("tag").getAsString())) : null;
            Ingredient ingredient = item != null ? Ingredient.fromItems(item) : tag != null ? Ingredient.fromTag(tag) : null;

            return new BackpackType(displayName, ingredient);
        }
    }
}
