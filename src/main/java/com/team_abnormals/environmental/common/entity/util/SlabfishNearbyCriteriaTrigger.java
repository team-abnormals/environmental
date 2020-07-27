package com.team_abnormals.environmental.common.entity.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/**
 * <p>A trigger used to detect nearby slabfish.</p>
 *
 * @author Ocelot
 */
public class SlabfishNearbyCriteriaTrigger extends AbstractCriterionTrigger<SlabfishNearbyCriteriaTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(Environmental.MODID, "slabfish");

    public ResourceLocation getId() {
        return ID;
    }

    public SlabfishNearbyCriteriaTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate predicate, ConditionArrayParser parser) {
        if (!json.has("slabfish") || !json.get("slabfish").isJsonPrimitive() || !json.get("slabfish").getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("'slabfish' required as string");
        return new SlabfishNearbyCriteriaTrigger.Instance(predicate, new ResourceLocation(json.get("slabfish").getAsString()));
    }

    public void trigger(ServerPlayerEntity player, SlabfishEntity slabfish) {
        this.triggerListeners(player, instance -> instance.slabfishType.equals(slabfish.getSlabfishType()));
    }

    public static class Instance extends CriterionInstance {
        private final ResourceLocation slabfishType;

        public Instance(EntityPredicate.AndPredicate p_i231560_1_, ResourceLocation slabfishType) {
            super(SlabfishNearbyCriteriaTrigger.ID, p_i231560_1_);
            this.slabfishType = slabfishType;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer serializer) {
            JsonObject jsonobject = super.serialize(serializer);
            jsonobject.addProperty("block", this.slabfishType.toString());
            return jsonobject;
        }
    }
}
