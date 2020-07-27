package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.*;
import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.function.Predicate;

/**
 * <p>A single type of slabfish that exists.</p>
 *
 * @author Ocelot
 */
public class SlabfishType implements Predicate<SlabfishConditionContext> {
    private final boolean translucent;
    private final ResourceLocation customBackpack;
    private final SlabfishRarity rarity;
    private final boolean tradable;
    private final boolean modLoaded;
    private final int priority;
    private final SlabfishCondition[] conditions;
    private ResourceLocation registryName;
    private ITextComponent displayName;

    public SlabfishType(SlabfishRarity rarity, @Nullable ITextComponent displayName, @Nullable ResourceLocation customBackpack, boolean translucent, boolean tradable, boolean modLoaded, int priority, SlabfishCondition[] conditions) {
        this.rarity = rarity;
        this.displayName = displayName;
        this.customBackpack = customBackpack;
        this.translucent = translucent;
        this.tradable = tradable;
        this.modLoaded = modLoaded;
        this.priority = priority;
        this.conditions = conditions;
    }

    /**
     * Creates a new {@link SlabfishType} from the specified {@link PacketBuffer} on the client side.
     *
     * @param buf The buffer to read from
     * @return A new slabfish type created from the data in the buffer
     */
    @OnlyIn(Dist.CLIENT)
    public static SlabfishType readFrom(PacketBuffer buf) {
        ResourceLocation registryName = buf.readResourceLocation();
        ITextComponent displayName = buf.readTextComponent();
        ResourceLocation customBackpack = buf.readBoolean() ? buf.readResourceLocation() : null;
        boolean translucent = buf.readBoolean();
        SlabfishRarity rarity = SlabfishRarity.byId(buf.readVarInt());
        boolean modLoaded = buf.readBoolean();
        int priority = buf.readVarInt();
        return new SlabfishType(rarity, displayName, customBackpack, translucent, false, modLoaded, priority, new SlabfishCondition[0]).setRegistryName(registryName);
    }

    @Override
    public boolean test(SlabfishConditionContext slabfishEntity) {
        for (SlabfishCondition condition : this.conditions)
            if (!condition.test(slabfishEntity))
                return false;
        return true;
    }

    /**
     * @return The registry name of this slabfish
     */
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    /**
     * Sets the registry name of this slabfish type.
     *
     * @param registryName The new registry name for this slabfish
     */
    SlabfishType setRegistryName(ResourceLocation registryName) {
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
     * @return Whether or not the slabfish texture is translucent
     */
    public boolean isTranslucent() {
        return translucent;
    }

    /**
     * @return The type of backpack this slabfish should use
     */
    @Nullable
    public ResourceLocation getCustomBackpack() {
        return customBackpack;
    }

    /**
     * @return The rarity of this slabfish
     */
    public SlabfishRarity getRarity() {
        return rarity;
    }

    /**
     * @return Whether or not this item can be traded for with a wandering trader
     */
    public boolean isTradable() {
        return tradable;
    }

    /**
     * @return Whether or not this type should be loaded only if a mod with a provided name is loaded
     */
    public boolean isModLoaded() {
        return modLoaded;
    }

    /**
     * @return The priority of this slabfish. Used to determine what slabfish should be chosen over others when different types have passed conditions
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Writes this {@link SlabfishType} into the specified {@link PacketBuffer} for syncing with clients.
     *
     * @param buf The buffer to write into
     */
    public void writeTo(PacketBuffer buf) {
        buf.writeResourceLocation(this.registryName);
        buf.writeTextComponent(this.displayName);
        buf.writeBoolean(this.customBackpack != null);
        if (this.customBackpack != null)
            buf.writeResourceLocation(this.customBackpack);
        buf.writeBoolean(this.translucent);
        buf.writeVarInt(this.rarity.ordinal());
        buf.writeBoolean(this.modLoaded);
        buf.writeVarInt(this.priority);
    }

    @Override
    public String toString() {
        return "SlabfishType{" +
                "registryName=" + registryName +
                ", displayName=" + displayName.getString() +
                ", rarity=" + rarity +
                ", modLoaded=" + modLoaded +
                ", priority=" + priority +
                '}';
    }

    /**
     * <p>Deserializes a slabfish type from JSON.</p>
     *
     * @author Ocelot
     */
    public static class Deserializer implements JsonDeserializer<SlabfishType> {
        private static SlabfishRarity deserializeRarity(JsonElement element) throws JsonParseException {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
                throw new JsonSyntaxException("'rarity' expected to be a string");
            String name = element.getAsString();
            for (SlabfishRarity rarity : SlabfishRarity.values())
                if (rarity.name().equalsIgnoreCase(name))
                    return rarity;
            throw new JsonSyntaxException("Invalid slabfish rarity: " + name);
        }

        @Override
        public SlabfishType deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            if (!jsonObject.has("rarity"))
                throw new JsonSyntaxException("'rarity' is required");

            SlabfishRarity rarity = deserializeRarity(jsonObject.get("rarity"));
            ITextComponent displayName = jsonObject.has("displayName") ? context.deserialize(jsonObject.get("displayName"), ITextComponent.class) : null;
            ResourceLocation customBackpack = jsonObject.has("customBackpack") ? context.deserialize(jsonObject.get("customBackpack"), ResourceLocation.class) : null;
            boolean translucent = jsonObject.has("translucent") && jsonObject.get("translucent").getAsBoolean();
            boolean tradable = !jsonObject.has("tradable") || jsonObject.get("tradable").getAsBoolean();
            boolean modLoaded = !jsonObject.has("mod") || ModList.get().isLoaded(jsonObject.get("mod").getAsString());
            int priority = jsonObject.has("priority") ? jsonObject.get("priority").getAsInt() : 0;
            SlabfishCondition[] conditions = jsonObject.has("conditions") ? context.deserialize(jsonObject.get("conditions"), SlabfishCondition[].class) : new SlabfishCondition[0];

            return new SlabfishType(rarity, displayName, customBackpack, translucent, tradable, modLoaded, priority, conditions);
        }
    }
}
