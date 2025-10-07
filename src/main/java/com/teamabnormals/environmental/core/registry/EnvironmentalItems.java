package com.teamabnormals.environmental.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.item.BlueprintBoatItem;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.environmental.common.item.*;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalArmorMaterials;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBannerPatternTags;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalJukeboxSongs;
import com.teamabnormals.environmental.integration.boatload.EnvironmentalBoatTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Predicate;

import static com.teamabnormals.blueprint.core.util.item.ItemStackUtil.is;
import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class EnvironmentalItems {
	public static final ItemSubRegistryHelper ITEMS = Environmental.REGISTRY_HELPER.getItemSubHelper();

	public static final DeferredItem<Item> CHERRIES = ITEMS.createItem("cherries", () -> new Item(new Item.Properties().food(EnvironmentalFoods.CHERRIES)));
	public static final DeferredItem<Item> PLUM = ITEMS.createItem("plum", () -> new Item(new Item.Properties().food(EnvironmentalFoods.PLUM)));

	public static final DeferredItem<Item> VENISON = ITEMS.createItem("venison", () -> new Item(new Item.Properties().food(EnvironmentalFoods.VENISON)));
	public static final DeferredItem<Item> COOKED_VENISON = ITEMS.createItem("cooked_venison", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COOKED_VENISON)));

	public static final DeferredItem<Item> KOI = ITEMS.createItem("koi", () -> new Item(new Item.Properties().food(EnvironmentalFoods.KOI)));

	public static final DeferredItem<Item> DUCK = ITEMS.createItem("duck", () -> new Item(new Item.Properties().food(EnvironmentalFoods.DUCK)));
	public static final DeferredItem<Item> COOKED_DUCK = ITEMS.createItem("cooked_duck", () -> new Item(new Item.Properties().food(EnvironmentalFoods.COOKED_DUCK)));
	public static final DeferredItem<Item> DUCK_EGG = ITEMS.createItem("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16)));

	public static final DeferredItem<Item> TRUFFLE = ITEMS.createItem("truffle", () -> new Item(new Item.Properties().food(EnvironmentalFoods.TRUFFLE)));

	public static final DeferredItem<Item> MUD_BALL = ITEMS.createItem("mud_ball", () -> new MudBallItem(new Item.Properties()));

	public static final DeferredItem<Item> YAK_HAIR = ITEMS.createItem("yak_hair", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> YAK_PANTS = ITEMS.createItem("yak_pants", () -> new YakPantsItem(EnvironmentalArmorMaterials.YAK, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(Type.LEGGINGS.getDurability(5))));

	public static final Pair<DeferredItem<BlueprintBoatItem>, DeferredItem<BlueprintBoatItem>> WILLOW_BOAT = ITEMS.createBoatAndChestBoatItem("willow", EnvironmentalBlocks.WILLOW_PLANKS);
	public static final DeferredItem<Item> WILLOW_FURNACE_BOAT = ITEMS.createItem("willow_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.WILLOW_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LARGE_WILLOW_BOAT = ITEMS.createItem("large_willow_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_WILLOW_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<DeferredItem<BlueprintBoatItem>, DeferredItem<BlueprintBoatItem>> PINE_BOAT = ITEMS.createBoatAndChestBoatItem("pine", EnvironmentalBlocks.PINE_PLANKS);
	public static final DeferredItem<Item> PINE_FURNACE_BOAT = ITEMS.createItem("pine_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.PINE_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LARGE_PINE_BOAT = ITEMS.createItem("large_pine_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_PINE_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<DeferredItem<BlueprintBoatItem>, DeferredItem<BlueprintBoatItem>> WISTERIA_BOAT = ITEMS.createBoatAndChestBoatItem("wisteria", EnvironmentalBlocks.WISTERIA_PLANKS);
	public static final DeferredItem<Item> WISTERIA_FURNACE_BOAT = ITEMS.createItem("wisteria_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.WISTERIA_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LARGE_WISTERIA_BOAT = ITEMS.createItem("large_wisteria_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_WISTERIA_BOAT : () -> new Item(new Item.Properties()));

	public static final Pair<DeferredItem<BlueprintBoatItem>, DeferredItem<BlueprintBoatItem>> PLUM_BOAT = ITEMS.createBoatAndChestBoatItem("plum", EnvironmentalBlocks.PLUM_PLANKS);
	public static final DeferredItem<Item> PLUM_FURNACE_BOAT = ITEMS.createItem("plum_furnace_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.PLUM_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LARGE_PLUM_BOAT = ITEMS.createItem("large_plum_boat", ModList.get().isLoaded("boatload") ? EnvironmentalBoatTypes.LARGE_PLUM_BOAT : () -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> CATTAIL_FLUFF = ITEMS.createItem("cattail_fluff", () -> new ItemNameBlockItem(EnvironmentalBlocks.CATTAIL_SPROUT.get(), new Item.Properties()));
	public static final DeferredItem<Item> DUCKWEED = ITEMS.createItem("duckweed", () -> new PlaceOnWaterBlockItem(EnvironmentalBlocks.DUCKWEED.get(), new Item.Properties()));

	public static final DeferredItem<Item> MUSIC_DISC_LEAVING_HOME = ITEMS.createItem("music_disc_leaving_home", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(EnvironmentalJukeboxSongs.LEAVING_HOME)));
	public static final DeferredItem<Item> MUSIC_DISC_SLABRAVE = ITEMS.createItem("music_disc_slabrave", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(EnvironmentalJukeboxSongs.SLABRAVE)));
	public static final DeferredItem<Item> LUMBERER_BANNER_PATTERN = ITEMS.createItem("lumberer_banner_pattern", () -> new BannerPatternItem(EnvironmentalBannerPatternTags.PATTERN_ITEM_LUMBERER, new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> HELPER_BANNER_PATTERN = ITEMS.createItem("helper_banner_pattern", () -> new BannerPatternItem(EnvironmentalBannerPatternTags.PATTERN_ITEM_HELPER, new Item.Properties().stacksTo(1)));

	public static final DeferredItem<Item> LARGE_LILY_PAD = ITEMS.createItem("large_lily_pad", () -> new LargeLilyPadItem(new Item.Properties()));
	public static final DeferredItem<Item> GIANT_LILY_PAD = ITEMS.createItem("giant_lily_pad", () -> new GiantLilyPadItem(new Item.Properties()));

	public static final DeferredItem<Item> SLABFISH_BUCKET = ITEMS.createItem("slabfish_bucket", () -> new SlabfishBucketItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> KOI_BUCKET = ITEMS.createItem("koi_bucket", () -> new KoiBucketItem(new Item.Properties().stacksTo(1)));

	public static final DeferredItem<Item> YELLOW_HIBISCUS = ITEMS.createItem("yellow_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), EnvironmentalBlocks.YELLOW_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final DeferredItem<Item> ORANGE_HIBISCUS = ITEMS.createItem("orange_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), EnvironmentalBlocks.ORANGE_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final DeferredItem<Item> RED_HIBISCUS = ITEMS.createItem("red_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.RED_HIBISCUS.get(), EnvironmentalBlocks.RED_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final DeferredItem<Item> PINK_HIBISCUS = ITEMS.createItem("pink_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.PINK_HIBISCUS.get(), EnvironmentalBlocks.PINK_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final DeferredItem<Item> MAGENTA_HIBISCUS = ITEMS.createItem("magenta_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), EnvironmentalBlocks.MAGENTA_WALL_HIBISCUS.get(), new Item.Properties()));
	public static final DeferredItem<Item> PURPLE_HIBISCUS = ITEMS.createItem("purple_hibiscus", () -> new HibiscusBlockItem(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), EnvironmentalBlocks.PURPLE_WALL_HIBISCUS.get(), new Item.Properties()));

	public static final DeferredItem<DeferredSpawnEggItem> SLABFISH_SPAWN_EGG = ITEMS.createSpawnEggItem("slabfish", EnvironmentalEntityTypes.SLABFISH::get, 0x5F9341, 0xD4B788);
	public static final DeferredItem<DeferredSpawnEggItem> DUCK_SPAWN_EGG = ITEMS.createSpawnEggItem("duck", EnvironmentalEntityTypes.DUCK::get, 0x2A5B36, 0xF2C937);
	public static final DeferredItem<DeferredSpawnEggItem> DEER_SPAWN_EGG = ITEMS.createSpawnEggItem("deer", EnvironmentalEntityTypes.DEER::get, 0xA17A4A, 0xE6CBA6);
	public static final DeferredItem<DeferredSpawnEggItem> REINDEER_SPAWN_EGG = ITEMS.createSpawnEggItem("reindeer", EnvironmentalEntityTypes.REINDEER::get, 0x704E45, 0xEAD0C9);
	public static final DeferredItem<DeferredSpawnEggItem> YAK_SPAWN_EGG = ITEMS.createSpawnEggItem("yak", EnvironmentalEntityTypes.YAK::get, 0x443934, 0xD9A565);
	public static final DeferredItem<DeferredSpawnEggItem> KOI_SPAWN_EGG = ITEMS.createSpawnEggItem("koi", EnvironmentalEntityTypes.KOI::get, 0x37363D, 0xFF926B);
	public static final DeferredItem<DeferredSpawnEggItem> TAPIR_SPAWN_EGG = ITEMS.createSpawnEggItem("tapir", EnvironmentalEntityTypes.TAPIR::get, 0x38373D, 0xC6CACE);
	public static final DeferredItem<DeferredSpawnEggItem> ZEBRA_SPAWN_EGG = ITEMS.createSpawnEggItem("zebra", EnvironmentalEntityTypes.ZEBRA::get, 0xD4CBC6, 0x342E2B);
	public static final DeferredItem<DeferredSpawnEggItem> ZORSE_SPAWN_EGG = ITEMS.createSpawnEggItem("zorse", EnvironmentalEntityTypes.ZORSE::get, 0xC09E7D, 0x342E2B);
	public static final DeferredItem<DeferredSpawnEggItem> ZONKEY_SPAWN_EGG = ITEMS.createSpawnEggItem("zonkey", EnvironmentalEntityTypes.ZONKEY::get, 0x534539, 0x342E2B);
	public static final DeferredItem<DeferredSpawnEggItem> PINECONE_GOLEM_SPAWN_EGG = ITEMS.createSpawnEggItem("pinecone_golem", EnvironmentalEntityTypes.PINECONE_GOLEM::get, 0x775D49, 0x8FA147);

	public static final class EnvironmentalFoods {
		public static final FoodProperties CHERRIES = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).fast().build();
		public static final FoodProperties PLUM = new FoodProperties.Builder().nutrition(5).saturationModifier(0.3F).build();

		public static final FoodProperties VENISON = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.3F).build();
		public static final FoodProperties COOKED_VENISON = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.8F).build();

		public static final FoodProperties DUCK = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
		public static final FoodProperties COOKED_DUCK = (new FoodProperties.Builder()).nutrition(8).saturationModifier(0.3F).build();

		public static final FoodProperties TRUFFLE = new FoodProperties.Builder().nutrition(16).saturationModifier(1.2F).build();

		public static final FoodProperties KOI = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
	}

	public static void setupTabEditors() {
		CreativeModeTabContentsPopulator.mod(Environmental.MOD_ID)
				.tab(FOOD_AND_DRINKS)
				.addItemsBefore(of(Items.SWEET_BERRIES), CHERRIES, PLUM)
				.addItemsBefore(of(Items.CHICKEN), VENISON, COOKED_VENISON)
				.addItemsBefore(of(Items.RABBIT), DUCK, COOKED_DUCK)
				.addItemsBefore(of(Items.ROTTEN_FLESH), TRUFFLE)
				.addItemsBefore(of(Items.TROPICAL_FISH), KOI)
				.tab(INGREDIENTS)
				.addItemsAfter(of(Items.GLOBE_BANNER_PATTERN), LUMBERER_BANNER_PATTERN, HELPER_BANNER_PATTERN)
				.addItemsAfter(of(Items.EGG), DUCK_EGG)
				.addItemsBefore(of(Items.LEATHER), YAK_HAIR)
				.addItemsAfter(of(Items.CLAY_BALL), MUD_BALL)
				.addItemsBefore(of(Items.STRING), CATTAIL_FLUFF)
				.tab(COMBAT)
				.addItemsAfter(of(Items.EGG), MUD_BALL)
				.addItemsBefore(of(Items.LEATHER_HORSE_ARMOR), YAK_PANTS)
				.tab(TOOLS_AND_UTILITIES)
				.addItemsBefore(of(Items.TROPICAL_FISH_BUCKET), KOI_BUCKET)
				.addItemsBefore(of(Items.TADPOLE_BUCKET), SLABFISH_BUCKET)
				.addItemsBefore(of(Items.BAMBOO_RAFT), WILLOW_BOAT.getFirst(), WILLOW_BOAT.getSecond())
				.addItemsBefore(modLoaded(Items.BAMBOO_RAFT, "boatload"), WILLOW_FURNACE_BOAT, LARGE_WILLOW_BOAT)
				.addItemsBefore(of(Items.BAMBOO_RAFT), PINE_BOAT.getFirst(), PINE_BOAT.getSecond())
				.addItemsBefore(modLoaded(Items.BAMBOO_RAFT, "boatload"), PINE_FURNACE_BOAT, LARGE_PINE_BOAT)
				.addItemsBefore(of(Items.BAMBOO_RAFT), PLUM_BOAT.getFirst(), PLUM_BOAT.getSecond())
				.addItemsBefore(modLoaded(Items.BAMBOO_RAFT, "boatload"), PLUM_FURNACE_BOAT, LARGE_PLUM_BOAT)
				.addItemsBefore(of(Items.BAMBOO_RAFT), WISTERIA_BOAT.getFirst(), WISTERIA_BOAT.getSecond())
				.addItemsBefore(modLoaded(Items.BAMBOO_RAFT, "boatload"), WISTERIA_FURNACE_BOAT, LARGE_WISTERIA_BOAT)
				.addItemsBefore(of(Items.MUSIC_DISC_5), MUSIC_DISC_LEAVING_HOME, MUSIC_DISC_SLABRAVE)
				.tab(NATURAL_BLOCKS)
				.addItemsBefore(of(Items.TORCHFLOWER_SEEDS), CATTAIL_FLUFF)
				.tab(SPAWN_EGGS)
				.addItemsAlphabetically(is(SpawnEggItem.class), "spawn_egg|_", SLABFISH_SPAWN_EGG, DUCK_SPAWN_EGG, DEER_SPAWN_EGG, REINDEER_SPAWN_EGG, YAK_SPAWN_EGG, KOI_SPAWN_EGG, TAPIR_SPAWN_EGG, ZEBRA_SPAWN_EGG, ZORSE_SPAWN_EGG, ZONKEY_SPAWN_EGG, PINECONE_GOLEM_SPAWN_EGG);
	}

	public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
		return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
	}

}