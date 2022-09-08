package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.common.item.BlueprintMobBucketItem;
import com.teamabnormals.blueprint.common.item.BlueprintRecordItem;
import com.teamabnormals.blueprint.common.item.FuelItem;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.environmental.common.item.*;
import com.teamabnormals.environmental.common.item.explorer.ArchitectBeltItem;
import com.teamabnormals.environmental.common.item.explorer.HealerPouchItem;
import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import com.teamabnormals.environmental.common.item.explorer.WandererBootsItem;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalItems {
	public static final ItemSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> THIEF_HOOD = HELPER.createItem("thief_hood", () -> new ThiefHoodItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<Item> HEALER_POUCH = HELPER.createItem("healer_pouch", () -> new HealerPouchItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<Item> ARCHITECT_BELT = HELPER.createItem("architect_belt", () -> new ArchitectBeltItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<Item> WANDERER_BOOTS = HELPER.createItem("wanderer_boots", () -> new WandererBootsItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT)));

	public static final RegistryObject<Item> CHERRIES = HELPER.createItem("cherries", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.CHERRIES)));
	public static final RegistryObject<Item> CHERRY_PIE = HELPER.createItem("cherry_pie", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.CHERRY_PIE)));
	public static final RegistryObject<Item> APPLE_PIE = HELPER.createItem("apple_pie", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.APPLE_PIE)));
	public static final RegistryObject<Item> VENISON = HELPER.createItem("venison", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.VENISON)));
	public static final RegistryObject<Item> COOKED_VENISON = HELPER.createItem("cooked_venison", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.COOKED_VENISON)));

	public static final RegistryObject<Item> KOI = HELPER.createItem("koi", () -> new Item(new Item.Properties().food(EnvironmentalFoods.KOI).tab(CreativeModeTab.TAB_FOOD)));

	public static final RegistryObject<Item> DUCK = HELPER.createItem("duck", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.DUCK)));
	public static final RegistryObject<Item> COOKED_DUCK = HELPER.createItem("cooked_duck", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(EnvironmentalFoods.COOKED_DUCK)));
	public static final RegistryObject<Item> DUCK_EGG = HELPER.createItem("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));

	public static final RegistryObject<Item> TRUFFLE = HELPER.createItem("truffle", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Item> TRUFFLE_MASH = HELPER.createItem("truffle_mash", () -> new BowlFoodItem((new Item.Properties()).stacksTo(1).food(EnvironmentalFoods.TRUFFLE_MASH).tab(CreativeModeTab.TAB_FOOD)));
	public static final RegistryObject<Item> TRUFFLE_PIE = HELPER.createItem("truffle_pie", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE_PIE).tab(CreativeModeTab.TAB_FOOD)));

	public static final RegistryObject<Item> MUD_BALL = HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> MUD_BRICK = HELPER.createItem("mud_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

	public static final RegistryObject<Item> YAK_HAIR = HELPER.createItem("yak_hair", () -> new FuelItem(25, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> YAK_PANTS = HELPER.createItem("yak_pants", () -> new YakPantsItem(EnvironmentalTiers.Armor.YAK, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

	public static final RegistryObject<Item> WILLOW_BOAT = HELPER.createBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
	public static final RegistryObject<Item> WISTERIA_BOAT = HELPER.createBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);
	public static final RegistryObject<Item> CHERRY_BOAT = HELPER.createBoatItem("cherry", EnvironmentalBlocks.CHERRY_PLANKS);

	public static final RegistryObject<Item> CATTAIL_SEEDS = HELPER.createItem("cattail_seeds", () -> new ItemNameBlockItem(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> DUCKWEED = HELPER.createItem("duckweed", () -> new WaterLilyBlockItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

	public static final RegistryObject<Item> MUSIC_DISC_LEAVING_HOME = HELPER.createItem("music_disc_leaving_home", () -> new BlueprintRecordItem(6, EnvironmentalSoundEvents.MUSIC_DISC_LEAVING_HOME, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE = HELPER.createItem("music_disc_slabrave", () -> new BlueprintRecordItem(15, EnvironmentalSoundEvents.MUSIC_DISC_SLABRAVE, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> LARGE_LILY_PAD = HELPER.createItem("large_lily_pad", () -> new LargeLilyPadItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
	public static final RegistryObject<Item> GIANT_LILY_PAD = HELPER.createItem("giant_lily_pad", () -> new GiantLilyPadItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

	public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(EnvironmentalEntityTypes.SLABFISH::get, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));
	public static final RegistryObject<Item> KOI_BUCKET = HELPER.createItem("koi_bucket", () -> new BlueprintMobBucketItem(EnvironmentalEntityTypes.KOI, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

	public static final RegistryObject<ForgeSpawnEggItem> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", EnvironmentalEntityTypes.SLABFISH::get, 6263617, 13940616);
	public static final RegistryObject<ForgeSpawnEggItem> DUCK_SPAWN_EGG = HELPER.createSpawnEggItem("duck", EnvironmentalEntityTypes.DUCK::get, 1138489, 16754947);
	public static final RegistryObject<ForgeSpawnEggItem> DEER_SPAWN_EGG = HELPER.createSpawnEggItem("deer", EnvironmentalEntityTypes.DEER::get, 10057035, 15190442);
	public static final RegistryObject<ForgeSpawnEggItem> YAK_SPAWN_EGG = HELPER.createSpawnEggItem("yak", EnvironmentalEntityTypes.YAK::get, 5392966, 8607802);
	public static final RegistryObject<ForgeSpawnEggItem> KOI_SPAWN_EGG = HELPER.createSpawnEggItem("koi", EnvironmentalEntityTypes.KOI::get, 5392966, 16754947);
	public static final RegistryObject<ForgeSpawnEggItem> TAPIR_SPAWN_EGG = HELPER.createSpawnEggItem("tapir", EnvironmentalEntityTypes.TAPIR::get, 0x38373D, 0xC6CACE);
	public static final RegistryObject<ForgeSpawnEggItem> FENNEC_FOX_SPAWN_EGG = HELPER.createSpawnEggItem("fennec_fox", EnvironmentalEntityTypes.FENNEC_FOX::get, 0xFBDB9E, 0xFFFFFF);

	public static final class EnvironmentalFoods {
		public static final FoodProperties CHERRIES = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).fast().build();
		public static final FoodProperties CHERRY_PIE = new FoodProperties.Builder().nutrition(6).saturationMod(0.3F).build();
		public static final FoodProperties APPLE_PIE = new FoodProperties.Builder().nutrition(10).saturationMod(0.2F).build();

		public static final FoodProperties VENISON = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build();
		public static final FoodProperties COOKED_VENISON = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).meat().build();

		public static final FoodProperties DUCK = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
		public static final FoodProperties COOKED_DUCK = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.3F).meat().build();

		public static final FoodProperties TRUFFLE = new FoodProperties.Builder().nutrition(4).saturationMod(0.1F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 2400), 1.0F).build();
		public static final FoodProperties TRUFFLE_MASH = new FoodProperties.Builder().nutrition(20).saturationMod(0.5F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000), 1.0F).build();
		public static final FoodProperties TRUFFLE_PIE = new FoodProperties.Builder().nutrition(15).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 3600), 1.0F).build();

		public static final FoodProperties KOI = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build();
	}
}