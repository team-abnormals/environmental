package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.common.items.AbnormalsMusicDiscItem;
import com.minecraftabnormals.abnormals_core.common.items.AbnormalsSpawnEggItem;
import com.minecraftabnormals.abnormals_core.common.items.FuelItem;
import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.minecraftabnormals.environmental.common.item.*;
import com.minecraftabnormals.environmental.common.item.explorer.ArchitectBeltItem;
import com.minecraftabnormals.environmental.common.item.explorer.HealerPouchItem;
import com.minecraftabnormals.environmental.common.item.explorer.ThiefHoodItem;
import com.minecraftabnormals.environmental.common.item.explorer.WandererBootsItem;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalFoods;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalItems {
	public static final ItemSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> THIEF_HOOD = HELPER.createItem("thief_hood", () -> new ThiefHoodItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_COMBAT)));
	public static final RegistryObject<Item> HEALER_POUCH = HELPER.createItem("healer_pouch", () -> new HealerPouchItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_COMBAT)));
	public static final RegistryObject<Item> ARCHITECT_BELT = HELPER.createItem("architect_belt", () -> new ArchitectBeltItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_COMBAT)));
	public static final RegistryObject<Item> WANDERER_BOOTS = HELPER.createItem("wanderer_boots", () -> new WandererBootsItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_COMBAT)));

	public static final RegistryObject<Item> CHERRIES = HELPER.createItem("cherries", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.CHERRIES)));
	public static final RegistryObject<Item> CHERRY_PIE = HELPER.createItem("cherry_pie", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.CHERRY_PIE)));
	public static final RegistryObject<Item> APPLE_PIE = HELPER.createItem("apple_pie", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.APPLE_PIE)));
	public static final RegistryObject<Item> VENISON = HELPER.createItem("venison", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.VENISON)));
	public static final RegistryObject<Item> COOKED_VENISON = HELPER.createItem("cooked_venison", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.COOKED_VENISON)));

	public static final RegistryObject<Item> KOI = HELPER.createItem("koi", () -> new Item(new Item.Properties().food(EnvironmentalFoods.KOI).tab(ItemGroup.TAB_FOOD)));

	public static final RegistryObject<Item> DUCK = HELPER.createItem("duck", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.DUCK)));
	public static final RegistryObject<Item> COOKED_DUCK = HELPER.createItem("cooked_duck", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.COOKED_DUCK)));
	public static final RegistryObject<Item> DUCK_EGG = HELPER.createItem("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_MISC)));

	public static final RegistryObject<Item> FRIED_EGG = HELPER.createItem("fried_egg", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.FRIED_EGG)));
	public static final RegistryObject<Item> SCRAMBLED_EGGS = HELPER.createItem("scrambled_eggs", () -> new ScrambledEggsItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_FOOD).food(EnvironmentalFoods.SCRAMBLED_EGGS)));

	public static final RegistryObject<Item> TRUFFLE = HELPER.createItem("truffle", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE).tab(ItemGroup.TAB_FOOD)));
	public static final RegistryObject<Item> TRUFFLE_MASH = HELPER.createItem("truffle_mash", () -> new SoupItem((new Item.Properties()).stacksTo(1).food(EnvironmentalFoods.TRUFFLE_MASH).tab(ItemGroup.TAB_FOOD)));
	public static final RegistryObject<Item> TRUFFLE_PIE = HELPER.createItem("truffle_pie", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE_PIE).tab(ItemGroup.TAB_FOOD)));
	
	public static final RegistryObject<Item> MUD_BALL = HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> MUD_BRICK = HELPER.createItem("mud_brick", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

	public static final RegistryObject<Item> YAK_HAIR = HELPER.createItem("yak_hair", () -> new FuelItem(25, new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> YAK_PANTS = HELPER.createItem("yak_pants", () -> new YakPantsItem(EnvironmentalTiers.Armor.YAK, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

	public static final RegistryObject<Item> WILLOW_BOAT = HELPER.createBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
	public static final RegistryObject<Item> WISTERIA_BOAT = HELPER.createBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);
	public static final RegistryObject<Item> CHERRY_BOAT = HELPER.createBoatItem("cherry", EnvironmentalBlocks.CHERRY_PLANKS);

	public static final RegistryObject<Item> CATTAIL_SEEDS = HELPER.createItem("cattail_seeds", () -> new BlockNamedItem(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> DUCKWEED = HELPER.createItem("duckweed", () -> new LilyPadItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

	public static final RegistryObject<Item> MUSIC_DISC_LEAVING_HOME = HELPER.createItem("music_disc_leaving_home", () -> new AbnormalsMusicDiscItem(6, EnvironmentalSounds.MUSIC_DISC_LEAVING_HOME, new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE = HELPER.createItem("music_disc_slabrave", () -> new AbnormalsMusicDiscItem(15, EnvironmentalSounds.MUSIC_DISC_SLABRAVE, new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> LARGE_LILY_PAD = HELPER.createItem("large_lily_pad", () -> new LargeLilyPadItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
	public static final RegistryObject<Item> GIANT_LILY_PAD = HELPER.createItem("giant_lily_pad", () -> new GiantLilyPadItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

	public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(EnvironmentalEntities.SLABFISH::get, () -> Fluids.WATER, new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));
	public static final RegistryObject<Item> KOI_BUCKET = HELPER.createItem("koi_bucket", () -> new KoiBucketItem(EnvironmentalEntities.KOI::get, () -> Fluids.WATER, new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));

	public static final RegistryObject<AbnormalsSpawnEggItem> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", EnvironmentalEntities.SLABFISH::get, 6263617, 13940616);
	public static final RegistryObject<AbnormalsSpawnEggItem> DUCK_SPAWN_EGG = HELPER.createSpawnEggItem("duck", EnvironmentalEntities.DUCK::get, 1138489, 16754947);
	public static final RegistryObject<AbnormalsSpawnEggItem> DEER_SPAWN_EGG = HELPER.createSpawnEggItem("deer", EnvironmentalEntities.DEER::get, 10057035, 15190442);
	public static final RegistryObject<AbnormalsSpawnEggItem> YAK_SPAWN_EGG = HELPER.createSpawnEggItem("yak", EnvironmentalEntities.YAK::get, 5392966, 8607802);
	public static final RegistryObject<AbnormalsSpawnEggItem> KOI_SPAWN_EGG = HELPER.createSpawnEggItem("koi", EnvironmentalEntities.KOI::get, 5392966, 16754947);
	//public static final RegistryObject<AbnormalsSpawnEggItem> FENNEC_FOX_SPAWN_EGG = HELPER.createSpawnEggItem("fennec_fox", EnvironmentalEntities.FENNEC_FOX::get, 0xFBDB9E, 0xFFFFFF);
}