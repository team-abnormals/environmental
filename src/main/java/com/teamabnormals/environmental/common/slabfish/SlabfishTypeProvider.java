package com.teamabnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SlabfishTypeProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final ExistingFileHelper helper;
    private final Map<ResourceLocation, SlabfishType> slabfishTypes = new LinkedHashMap<>();

    public SlabfishTypeProvider(DataGenerator generator, ExistingFileHelper helper) {
        this.generator = generator;
        this.helper = helper;
    }

    public void registerSlabfish() {
        this.add(new ResourceLocation(Environmental.MOD_ID, "badlands"),
            SlabfishType.builder()
                .addCondition(
                    new SlabfishEventCondition(new SlabfishConditionContext.Event[]{
                        SlabfishConditionContext.Event.SPAWN,
                        SlabfishConditionContext.Event.BREED
                    }))
                .addCondition(new SlabfishInBiomeCondition(BiomeTags.IS_BADLANDS)).build());

        this.add(new ResourceLocation(Environmental.MOD_ID, "bagel"),
            SlabfishType.builder()
                .setWeight(10)
                .addCondition(
                    new SlabfishRenameCondition(new String[]{
                        "bagel",
                        "shyguy",
                        "shy guy",
                        "bagel.jpg",
                    }, false)).build());
    }

    protected void add(ResourceLocation id, SlabfishType type) {
        this.addSlabfish(id, type);
    }

    private void addSlabfish(ResourceLocation path, SlabfishType definition) {
        if (this.slabfishTypes.put(path, definition) != null) {
            throw new IllegalStateException("Slabfish type '" + path + "' already exists");
        }
    }

    @Override
    public void run(HashCache cache) throws IOException {
        this.slabfishTypes.clear();
        this.registerSlabfish();

        for (Map.Entry<ResourceLocation, SlabfishType> entry : this.slabfishTypes.entrySet()) {
            ResourceLocation location = entry.getKey();
            DataProvider.save(GSON, cache, SlabfishType.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow(false, LOGGER::error), this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/" + Environmental.MOD_ID + "/slabfish_type/" + location.getPath() + ".json"));
        }
    }

    @Override
    public String getName() {
        return "Slabfish Types";
    }
}