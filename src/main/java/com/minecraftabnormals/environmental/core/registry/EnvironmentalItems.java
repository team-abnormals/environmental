package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.item.ArchitectBeltItem;
import com.minecraftabnormals.environmental.common.item.ExecutionerCleaverItem;
import com.minecraftabnormals.environmental.common.item.FoolWingsItem;
import com.minecraftabnormals.environmental.common.item.GiantLilyPadItem;
import com.minecraftabnormals.environmental.common.item.LargeLilyPadItem;
import com.minecraftabnormals.environmental.common.item.MudBallItem;
import com.minecraftabnormals.environmental.common.item.SlabfishBucketItem;
import com.minecraftabnormals.environmental.common.item.ThiefHoodItem;
import com.minecraftabnormals.environmental.common.item.WandererBootsItem;
import com.minecraftabnormals.environmental.common.item.YakPantsItem;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalFoods;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import com.teamabnormals.abnormals_core.common.items.AbnormalsMusicDiscItem;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.LilyPadItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.SoupItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalItems {
    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;

    public static final RegistryObject<Item> THIEF_HOOD			 = HELPER.createItem("thief_hood", () -> new ThiefHoodItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> FOOL_WINGS		 	 = HELPER.createItem("fool_wings", () -> new FoolWingsItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ARCHITECT_BELT 	 = HELPER.createItem("architect_belt", () -> new ArchitectBeltItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> WANDERER_BOOTS		 = HELPER.createItem("wanderer_boots", () -> new WandererBootsItem(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> EXECUTIONER_CLEAVER = HELPER.createItem("executioner_cleaver", () -> new ExecutionerCleaverItem(EnvironmentalTiers.Item.EXECUTIONER, 6.5f, -3.1F, new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT)));

    public static final RegistryObject<Item> CHERRIES           = HELPER.createItem("cherries", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.CHERRIES)));
    public static final RegistryObject<Item> CHERRY_PIE         = HELPER.createItem("cherry_pie", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.CHERRY_PIE)));
    public static final RegistryObject<Item> APPLE_PIE 	        = HELPER.createItem("apple_pie", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.APPLE_PIE)));
    public static final RegistryObject<Item> VENISON            = HELPER.createItem("venison", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.VENISON)));
    public static final RegistryObject<Item> COOKED_VENISON     = HELPER.createItem("cooked_venison", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.COOKED_VENISON)));
    public static final RegistryObject<Item> DUCK            	= HELPER.createItem("duck", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.DUCK)));
    public static final RegistryObject<Item> COOKED_DUCK     	= HELPER.createItem("cooked_duck", () -> new Item(new Item.Properties().group(ItemGroup.FOOD).food(EnvironmentalFoods.COOKED_DUCK)));

    public static final RegistryObject<Item> MUD_BALL 		= HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> MUD_BRICK 		= HELPER.createItem("mud_brick", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    
    public static final RegistryObject<Item> YAK_HAIR 		= HELPER.createItem("yak_hair", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> YAK_PANTS 		= HELPER.createItem("yak_pants", () -> new YakPantsItem(EnvironmentalTiers.Armor.YAK, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));

    public static final RegistryObject<Item> WILLOW_BOAT 	= HELPER.createBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
    public static final RegistryObject<Item> WISTERIA_BOAT 	= HELPER.createBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);
    public static final RegistryObject<Item> CHERRY_BOAT 	= HELPER.createBoatItem("cherry", EnvironmentalBlocks.CHERRY_PLANKS);

    public static final RegistryObject<Item> CATTAIL_SEEDS      = HELPER.createItem("cattail_seeds", () -> new BlockNamedItem(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> DUCKWEED           = HELPER.createItem("duckweed", () -> new LilyPadItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RICE               = HELPER.createItem("rice", () -> new BlockItem(EnvironmentalBlocks.RICE.get(), new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> RICE_BALL          = HELPER.createItem("rice_ball", () -> new Item(new Item.Properties().food(EnvironmentalFoods.RICE_BALL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> SQUID_INK_RISOTTO  = HELPER.createItem("squid_ink_risotto", () -> new SoupItem(new Item.Properties().maxStackSize(1).food(EnvironmentalFoods.SQUID_INK_RISOTTO).group(ItemGroup.FOOD)));

    public static final RegistryObject<Item> COD_KELP_ROLL              = HELPER.createItem("cod_kelp_roll", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COD_KELP_ROLL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> TROPICAL_FISH_KELP_ROLL    = HELPER.createItem("tropical_fish_kelp_roll", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TROPICAL_FISH_KELP_ROLL).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> CRAB_KELP_ROLL             = HELPER.createCompatItem("quark", "crab_kelp_roll", new Item.Properties().food(EnvironmentalFoods.CRAB_KELP_ROLL), ItemGroup.FOOD);
    public static final RegistryObject<Item> PIKE_KELP_ROLL             = HELPER.createCompatItem("upgrade_aquatic", "pike_kelp_roll", new Item.Properties().food(EnvironmentalFoods.PIKE_KELP_ROLL), ItemGroup.FOOD);

    public static final RegistryObject<Item> SALMON_RICE_CAKE       = HELPER.createItem("salmon_rice_cake", () -> new Item(new Item.Properties().food(EnvironmentalFoods.SALMON_RICE_CAKE).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> PUFFERFISH_RICE_CAKE   = HELPER.createItem("pufferfish_rice_cake", () -> new Item(new Item.Properties().food(EnvironmentalFoods.PUFFERFISH_RICE_CAKE).group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> LIONFISH_RICE_CAKE     = HELPER.createCompatItem("upgrade_aquatic", "lionfish_rice_cake", new Item.Properties().food(EnvironmentalFoods.LIONFISH_RICE_CAKE), ItemGroup.FOOD);

    public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE        = HELPER.createItem("music_disc_slabrave", () -> new AbnormalsMusicDiscItem(12, () -> EnvironmentalSounds.SLABRAVE.get(), new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MUSIC_DISC_LEAVING_HOME    = HELPER.createItem("music_disc_leaving_home", () -> new AbnormalsMusicDiscItem(12, () -> EnvironmentalSounds.LEAVING_HOME.get(), new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> LARGE_LILY_PAD	= HELPER.createItem("large_lily_pad", () -> new LargeLilyPadItem(new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> GIANT_LILY_PAD	= HELPER.createItem("giant_lily_pad", () -> new GiantLilyPadItem(new Item.Properties().group(ItemGroup.DECORATIONS)));
    
//    public static final RegistryObject<Item> AXOLOTL_BUCKET = HELPER.createItem("axolotl_bucket", () -> new AxolotlBucketItem(() -> EnvironmentalEntities.AXOLOTL.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
    public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(() -> EnvironmentalEntities.SLABFISH.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

    public static final RegistryObject<Item> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", () -> EnvironmentalEntities.SLABFISH.get(), 6263617, 13940616);
    public static final RegistryObject<Item> DUCK_SPAWN_EGG = HELPER.createSpawnEggItem("duck", () -> EnvironmentalEntities.DUCK.get(), 1138489, 16754947);
    public static final RegistryObject<Item> DEER_SPAWN_EGG     = HELPER.createSpawnEggItem("deer", () -> EnvironmentalEntities.DEER.get(), 10057035, 15190442);
    public static final RegistryObject<Item> YAK_SPAWN_EGG = HELPER.createSpawnEggItem("yak", () -> EnvironmentalEntities.YAK.get(), 5392966, 8607802);

//	  public static final RegistryObject<Item> AXOLOTL_SPAWN_EGG = HELPER.createSpawnEggItem("axolotl", () -> EnvironmentalEntities.AXOLOTL.get(), 6263617, 13940616);
}
