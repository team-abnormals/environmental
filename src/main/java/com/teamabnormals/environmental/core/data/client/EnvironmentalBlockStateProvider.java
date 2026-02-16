package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CattailStalkBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockStateProvider extends BlueprintBlockStateProvider {
	public static final String[] WISTERIA_BOOKSHELF_POSITIONS = new String[]{"outer_left", "mid_left", "inner_left", "inner_right", "mid_right", "outer_right"};

	public EnvironmentalBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Environmental.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.blockFamily(EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		this.blockFamily(EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);

		this.blockFamily(EnvironmentalBlockFamilies.SMOOTH_MUDDY_SANDSTONE_FAMILY);
		this.block(MUDGLASS);
		this.paneBlock((IronBarsBlock) MUDGLASS_PANE.get(), this.modLoc("block/mudglass"), this.modLoc("block/mudglass_pane_side"));
		this.generatedItem(MUDGLASS_PANE.get(), this.modLoc("block/mudglass"));

		this.blockFamily(EnvironmentalBlockFamilies.WILLOW_PLANKS_FAMILY);
		this.logBlocks(WILLOW_LOG, WILLOW_WOOD);
		this.logBlocks(STRIPPED_WILLOW_LOG, STRIPPED_WILLOW_WOOD);
		this.hangingSignBlocks(STRIPPED_WILLOW_LOG, WILLOW_HANGING_SIGNS);
		this.leavesBlocks(WILLOW_LEAVES, WILLOW_LEAF_PILE);
		this.crossBlockWithPot(WILLOW_SAPLING, POTTED_WILLOW_SAPLING);
		this.woodworksBlocks(WILLOW_PLANKS, WILLOW_BOARDS, WILLOW_LADDER, WILLOW_BOOKSHELF, WILLOW_BEEHIVE, WILLOW_CHEST, TRAPPED_WILLOW_CHEST);
		this.chiseledBookshelfBlock(CHISELED_WILLOW_BOOKSHELF, DEFAULT_BOOKSHELF_POSITIONS);

		this.blockFamily(EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		this.logBlocks(PINE_LOG, PINE_WOOD);
		this.logBlocks(STRIPPED_PINE_LOG, STRIPPED_PINE_WOOD);
		this.hangingSignBlocks(STRIPPED_PINE_LOG, PINE_HANGING_SIGNS);
		this.leavesBlocks(PINE_LEAVES, PINE_LEAF_PILE);
		this.crossBlockWithPot(PINE_SAPLING, POTTED_PINE_SAPLING);
		this.woodworksBlocks(PINE_PLANKS, PINE_BOARDS, PINE_LADDER, PINE_BOOKSHELF, PINE_BEEHIVE, PINE_CHEST, TRAPPED_PINE_CHEST);
		this.chiseledBookshelfBlock(CHISELED_PINE_BOOKSHELF, ALTERNATE_BOOKSHELF_POSITIONS);

		this.cubeColumnBlock(PINECONE);
		this.cubeColumnBlock(WAXED_PINECONE, PINECONE);

		this.blockFamily(EnvironmentalBlockFamilies.CEDAR_PLANKS_FAMILY);
		this.logBlocks(CEDAR_LOG, CEDAR_WOOD);
		this.logBlocks(STRIPPED_CEDAR_LOG, STRIPPED_CEDAR_WOOD);
		this.hangingSignBlocks(STRIPPED_CEDAR_LOG, CEDAR_HANGING_SIGNS);
		this.leavesBlocks(CEDAR_LEAVES, CEDAR_LEAF_PILE);
		this.crossBlockWithPot(CEDAR_SAPLING, POTTED_CEDAR_SAPLING);
		this.woodworksBlocks(CEDAR_PLANKS, CEDAR_BOARDS, CEDAR_LADDER, CEDAR_BOOKSHELF, CEDAR_BEEHIVE, CEDAR_CHEST, TRAPPED_CEDAR_CHEST);
		this.chiseledBookshelfBlock(CHISELED_CEDAR_BOOKSHELF, ALTERNATE_BOOKSHELF_POSITIONS);

		this.blockFamily(EnvironmentalBlockFamilies.PLUM_PLANKS_FAMILY);
		this.logBlocks(PLUM_LOG, PLUM_WOOD);
		this.logBlocks(STRIPPED_PLUM_LOG, STRIPPED_PLUM_WOOD);
		this.hangingSignBlocks(STRIPPED_PLUM_LOG, PLUM_HANGING_SIGNS);
		this.leavesBlocks(PLUM_LEAVES, PLUM_LEAF_PILE);
		this.leavesBlocks(CHEERFUL_PLUM_LEAVES, CHEERFUL_PLUM_LEAF_PILE);
		this.leavesBlocks(MOODY_PLUM_LEAVES, MOODY_PLUM_LEAF_PILE);
		this.crossBlockWithPot(PLUM_SAPLING, POTTED_PLUM_SAPLING);
		this.crossBlockWithPot(CHEERFUL_PLUM_SAPLING, POTTED_CHEERFUL_PLUM_SAPLING);
		this.crossBlockWithPot(MOODY_PLUM_SAPLING, POTTED_MOODY_PLUM_SAPLING);
		this.woodworksBlocks(PLUM_PLANKS, PLUM_BOARDS, PLUM_LADDER, PLUM_BOOKSHELF, PLUM_BEEHIVE, PLUM_CHEST, TRAPPED_PLUM_CHEST);
		this.chiseledBookshelfBlock(CHISELED_PLUM_BOOKSHELF, DEFAULT_BOOKSHELF_POSITIONS);

		this.blockFamily(EnvironmentalBlockFamilies.WISTERIA_PLANKS_FAMILY);
		this.logBlocks(WISTERIA_LOG, WISTERIA_WOOD);
		this.logBlocks(STRIPPED_WISTERIA_LOG, STRIPPED_WISTERIA_WOOD);
		this.hangingSignBlocks(STRIPPED_WISTERIA_LOG, WISTERIA_HANGING_SIGNS);
		this.leavesBlocks(WISTERIA_LEAVES, WISTERIA_LEAF_PILE);
		this.crossBlockWithPot(PINK_WISTERIA_SAPLING, POTTED_PINK_WISTERIA_SAPLING);
		this.crossBlockWithPot(BLUE_WISTERIA_SAPLING, POTTED_BLUE_WISTERIA_SAPLING);
		this.crossBlockWithPot(PURPLE_WISTERIA_SAPLING, POTTED_PURPLE_WISTERIA_SAPLING);
		this.crossBlockWithPot(WHITE_WISTERIA_SAPLING, POTTED_WHITE_WISTERIA_SAPLING);
		this.woodworksBlocks(WISTERIA_PLANKS, WISTERIA_BOARDS, WISTERIA_LADDER, WISTERIA_BOOKSHELF, WISTERIA_BEEHIVE, WISTERIA_CHEST, TRAPPED_WISTERIA_CHEST);
		this.leafPileBlock(PINK_WISTERIA_LEAVES, PINK_WISTERIA_LEAF_PILE);
		this.leafPileBlock(BLUE_WISTERIA_LEAVES, BLUE_WISTERIA_LEAF_PILE);
		this.leafPileBlock(PURPLE_WISTERIA_LEAVES, PURPLE_WISTERIA_LEAF_PILE);
		this.leafPileBlock(WHITE_WISTERIA_LEAVES, WHITE_WISTERIA_LEAF_PILE);
		this.chiseledBookshelfBlock(CHISELED_WISTERIA_BOOKSHELF, WISTERIA_BOOKSHELF_POSITIONS);

		this.leavesBlocks(HIBISCUS_LEAVES, HIBISCUS_LEAF_PILE);

		this.block(CATTAIL_FLUFF_BLOCK);
		this.cattail(CATTAIL_SPROUT, CATTAIL, CATTAIL_STALK);

		this.block(BURIED_TRUFFLE);
		this.block(CHISELED_MUD_BRICKS);
		this.directionalBlock(CHERRY_CRATE);
		this.directionalBlock(PLUM_CRATE);
		this.cubeBottomTopBlock(DUCK_EGG_CRATE);

		this.crossBlock(MYCELIUM_SPROUTS);
		this.cactusBobble(CACTUS_BOBBLE);

		this.crossBlockWithPot(BLUEBELL, POTTED_BLUEBELL);
		this.crossBlockWithPot(DIANTHUS, POTTED_DIANTHUS);
		this.crossBlockWithPot(VIOLET, POTTED_VIOLET);
		this.crossBlockWithPot(TASSELFLOWER, POTTED_TASSELFLOWER);
		this.crossBlockWithPot(WHITE_LOTUS_FLOWER, POTTED_WHITE_LOTUS_FLOWER);
		this.crossBlockWithPot(RED_LOTUS_FLOWER, POTTED_RED_LOTUS_FLOWER);

		this.sunflower(BLAZING_SUNFLOWER);
		this.sunflower(BEAMING_SUNFLOWER);
		this.sunflower(ECLIPSED_SUNFLOWER);
		this.sunflower(RADIANT_SUNFLOWER);
	}

	public void cubeColumnBlock(DeferredBlock<Block> block) {
		this.cubeColumnBlock(block, block);
	}

	public void cubeColumnBlock(DeferredBlock<Block> block, DeferredBlock<Block> parent) {
		this.simpleBlock(block.get(), this.models().cubeColumn(name(block.get()), suffix(blockTexture(parent.get()), "_side"), suffix(blockTexture(parent.get()), "_end")));
		this.blockItem(block);
	}

	public void sunflower(DeferredBlock<Block> block) {
		ModelFile sunflowerTop = new UncheckedModelFile("block/sunflower_top");

		ResourceLocation frontTexture = blockTexture(block.get()).withSuffix("_front");
		ModelFile top = this.models().getBuilder(name(block.get()) + "_top").parent(sunflowerTop).renderType("cutout")
				.texture("particle", frontTexture)
				.texture("cross", blockTexture(block.get()).withSuffix("_top"))
				.texture("back", blockTexture(block.get()).withSuffix("_back"))
				.texture("front", frontTexture);
		ModelFile bottom = this.models().cross(name(block.get()) + "_bottom", blockTexture(block.get()).withSuffix("_bottom")).renderType("cutout");

		this.getVariantBuilder(block.get()).forAllStates(state -> {
			return ConfiguredModel.builder().modelFile(state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? bottom : top).build();
		});

		this.generatedItem(block, frontTexture);
	}

	public void cattail(DeferredBlock<Block> cattailSproutObject, DeferredBlock<Block> cattailObject, DeferredBlock<Block> cattailStalkObject) {
		Block cattailSprout = cattailSproutObject.get();
		Block cattail = cattailObject.get();
		Block cattailStalk = cattailStalkObject.get();

		this.getVariantBuilder(cattailSprout).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailSprout), blockTexture(cattailSprout), state.getValue(CattailBlock.CATTAILS), false)).build(), BlockStateProperties.WATERLOGGED);

		MultiPartBlockStateBuilder cattailBuilder = this.getMultipartBuilder(cattail);

		for (int i = 1; i <= 3; i++) {
			cattailBuilder
					.part().modelFile(cattailStalkModel(name(cattail), blockTexture(cattail), i, false)).addModel().condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailStalkModel(name(cattail) + "_top", suffix(blockTexture(cattail), "_stalk_top"), i, true)).addModel().condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), false, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), true, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), false, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), true, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end();
		}

		this.getVariantBuilder(cattailStalk).forAllStatesExcept(state -> {
			return ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailStalk) + (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle"), suffix(blockTexture(cattailStalk), (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle")), state.getValue(CattailBlock.CATTAILS), false)).build();
		}, BlockStateProperties.WATERLOGGED);
	}

	public ModelFile cattailStalkModel(String name, ResourceLocation texture, int stalks, boolean top) {
		String stalkSuffix = getStalkSuffix(stalks);
		ModelFile stalkParent = new UncheckedModelFile(Environmental.location("block/template_cattail_stalk" + stalkSuffix));
		BlockModelBuilder builder = this.models().getBuilder(name + stalkSuffix).parent(stalkParent);
		for (int i = 1; i <= stalks; i++) {
			String suffix = getStalkSuffix(i);
			builder.texture("stalk" + suffix, top ? suffix(texture, suffix) : texture);
		}
		return builder;
	}

	public static String getStalkSuffix(int stalks) {
		return stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
	}

	public ModelFile cattailHeadModel(String name, ResourceLocation texture, boolean top, int stalks) {
		String stalkSuffix = stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
		ModelFile cattailParent = new UncheckedModelFile(Environmental.location("block/template_" + (top ? "cattail_top" : "cattail") + stalkSuffix));
		return this.models().getBuilder(name + (top ? "_top" : "") + stalkSuffix).parent(cattailParent).texture("cattail", texture);
	}

	public void cactusBobble(DeferredBlock<Block> cactusBobble) {
		this.simpleBlock(cactusBobble.get(), models().getBuilder(name(cactusBobble.get())).parent(new UncheckedModelFile(Environmental.location("block/template_cactus_bobble"))).texture("all", blockTexture(cactusBobble.get())));
	}
}
