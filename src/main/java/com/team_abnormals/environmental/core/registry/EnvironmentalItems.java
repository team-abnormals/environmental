package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.common.item.DuckweedItem;
import com.team_abnormals.environmental.common.item.MudBallItem;
import com.team_abnormals.environmental.common.item.SlabfishBucketItem;
import com.team_abnormals.environmental.common.item.ThiefHoodItem;
import com.team_abnormals.environmental.common.item.WandererBootsItem;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.other.EnvironmentalFoods;
import com.teamabnormals.abnormals_core.common.items.AbnormalsMusicDiscItem;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalItems {
    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;

    public static final RegistryObject<Item> THIEF_HOOD		= HELPER.createItem("thief_hood", () -> new ThiefHoodItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> WANDERER_BOOTS	= HELPER.createItem("wanderer_boots", () -> new WandererBootsItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));

    public static final RegistryObject<Item> MUD_BALL 		= HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> MUD_BRICK 		= HELPER.createItem("mud_brick", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> MUD_BUCKET 	= HELPER.createItem("mud_bucket", () -> new BucketItem(EnvironmentalFluids.MUD, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1)));
    public static final RegistryObject<Item> WILLOW_BOAT 	= HELPER.createBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
    public static final RegistryObject<Item> WISTERIA_BOAT 	= HELPER.createBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);

    public static final RegistryObject<Item> CATTAIL_SEEDS 		= HELPER.createItem("cattail_seeds", () -> new BlockNamedItem(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> DUCKWEED 			= HELPER.createItem("duckweed", () -> new DuckweedItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RICE 				= HELPER.createItem("rice", () -> new BlockItem(EnvironmentalBlocks.RICE.get(), new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> RICE_BALL 			= HELPER.createItem("rice_ball", () -> new Item(new Item.Properties().food(EnvironmentalFoods.RICE_BALL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> SQUID_INK_RISOTTO 	= HELPER.createItem("squid_ink_risotto", () -> new SoupItem(new Item.Properties().maxStackSize(1).food(EnvironmentalFoods.SQUID_INK_RISOTTO).group(ItemGroup.FOOD)));

    public static final RegistryObject<Item> COD_KELP_ROLL = HELPER.createItem("cod_kelp_roll", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COD_KELP_ROLL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> TROPICAL_FISH_KELP_ROLL = HELPER.createItem("tropical_fish_kelp_roll", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TROPICAL_FISH_KELP_ROLL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> CRAB_KELP_ROLL = HELPER.createCompatItem("quark", "crab_kelp_roll", new Item.Properties().food(EnvironmentalFoods.CRAB_KELP_ROLL), ItemGroup.FOOD);
    public static final RegistryObject<Item> PIKE_KELP_ROLL = HELPER.createCompatItem("upgrade_aquatic", "pike_kelp_roll", new Item.Properties().food(EnvironmentalFoods.PIKE_KELP_ROLL), ItemGroup.FOOD);
    public static final RegistryObject<Item> CAVEFISH_KELP_ROLL = HELPER.createCompatItem("none", "cavefish_kelp_roll", new Item.Properties().food(EnvironmentalFoods.CAVEFISH_KELP_ROLL), ItemGroup.FOOD);

    public static final RegistryObject<Item> SALMON_RICE_CAKE = HELPER.createItem("salmon_rice_cake", () -> new Item(new Item.Properties().food(EnvironmentalFoods.SALMON_RICE_CAKE).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> PUFFERFISH_RICE_CAKE = HELPER.createItem("pufferfish_rice_cake", () -> new Item(new Item.Properties().food(EnvironmentalFoods.PUFFERFISH_RICE_CAKE).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> LIONFISH_RICE_CAKE = HELPER.createCompatItem("upgrade_aquatic", "lionfish_rice_cake", new Item.Properties().food(EnvironmentalFoods.LIONFISH_RICE_CAKE), ItemGroup.FOOD);

    public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE = HELPER.createItem("music_disc_slabrave", () -> new AbnormalsMusicDiscItem(12, () -> EnvironmentalSounds.SLABRAVE.get(), new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_LEAVING_HOME = HELPER.createItem("music_disc_leaving_home", () -> new AbnormalsMusicDiscItem(12, () -> EnvironmentalSounds.LEAVING_HOME.get(), new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE)));

//	public static final RegistryObject<Item> AXOLOTL_BUCKET = HELPER.createItem("axolotl_bucket", () -> new AxolotlBucketItem(() -> EnvironmentalEntities.AXOLOTL.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
	public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(() -> EnvironmentalEntities.SLABFISH.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

    public static final RegistryObject<Item> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", () -> EnvironmentalEntities.SLABFISH.get(), 6263617, 13940616);
//	public static final RegistryObject<Item> AXOLOTL_SPAWN_EGG = HELPER.createSpawnEggItem("axolotl", () -> EnvironmentalEntities.AXOLOTL.get(), 6263617, 13940616);

    public static void setupProperties() {
        ItemModelsProperties.func_239418_a_(SLABFISH_BUCKET.get(), new ResourceLocation("variant"), (stack, world, entity) -> {
            CompoundNBT compoundnbt = stack.getTag();
            if (compoundnbt != null && compoundnbt.contains("SlabfishType", 3)) {
                return compoundnbt.getInt("SlabfishType");
            }
            return 0;
        });
    }
}
