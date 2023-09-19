package com.teamabnormals.environmental.core.data.client;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock.VerticalSlabType;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CattailStalkBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockStateProvider extends BlockStateProvider {

	public EnvironmentalBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.blockFamily(EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY, PINE_VERTICAL_SLAB.get());
		this.logBlocks(PINE_LOG.get(), PINE_WOOD.get());
		this.logBlocks(STRIPPED_PINE_LOG.get(), STRIPPED_PINE_WOOD.get());
		this.leavesBlock(PINE_LEAVES.get());
		this.crossBlockWithPot(PINE_SAPLING.get(), POTTED_PINE_SAPLING.get());
		this.planksCompat(PINE_PLANKS.get(), PINE_BOARDS.get(), PINE_LADDER.get(), PINE_BOOKSHELF.get(), PINE_BEEHIVE.get(), PINE_CHESTS, VERTICAL_PINE_PLANKS.get());
		this.logCompat(PINE_LOG.get(), STRIPPED_PINE_LOG.get(), PINE_POST.get(), STRIPPED_PINE_POST.get());
		this.leavesCompat(PINE_LEAVES.get(), PINE_LOG.get(), PINE_LEAF_PILE.get(), PINE_HEDGE.get(), PINE_LEAF_CARPET.get());

		this.block(CATTAIL_FLUFF_BLOCK.get());
		this.cattail(CATTAIL_SPROUT.get(), CATTAIL.get(), CATTAIL_STALK.get());
	}

	public void cattail(Block cattailSprout, Block cattail, Block cattailStalk) {
		this.getVariantBuilder(cattailSprout).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailSprout), blockTexture(cattailSprout), state.getValue(CattailBlock.CATTAILS))).build(), BlockStateProperties.WATERLOGGED);

		MultiPartBlockStateBuilder cattailBuilder = this.getMultipartBuilder(cattail);

		for (int i = 1; i <= 3; i++) {
			cattailBuilder
					.part().modelFile(cattailStalkModel(
							name(cattail), blockTexture(cattail), i)).addModel().condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailStalkModel(
							name(cattail) + "_top", suffix(blockTexture(cattail), "_stalk_top"), i)).addModel().condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(
							name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), false, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(
							name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), true, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(
							name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), false, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(
							name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), true, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end();
		}


		this.getVariantBuilder(cattailStalk).forAllStatesExcept(state -> {
			return ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailStalk) + (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle"), suffix(blockTexture(cattailStalk), (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle")), state.getValue(CattailBlock.CATTAILS))).build();
		}, BlockStateProperties.WATERLOGGED);
	}

	public ModelFile cattailStalkModel(String name, ResourceLocation texture, int stalks) {
		String stalkSuffix = stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
		ModelFile stalkParent = new UncheckedModelFile(new ResourceLocation(Environmental.MOD_ID, "block/template_cattail_stalk" + stalkSuffix));
		return this.models().getBuilder(name + stalkSuffix).parent(stalkParent).texture("stalk", texture);
	}

	public ModelFile cattailHeadModel(String name, ResourceLocation texture, boolean top, int stalks) {
		String stalkSuffix = stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
		ModelFile cattailParent = new UncheckedModelFile(new ResourceLocation(Environmental.MOD_ID, "block/template_" + (top ? "cattail_top" : "cattail") + stalkSuffix));
		return this.models().getBuilder(name + (top ? "_top" : "") + stalkSuffix).parent(cattailParent).texture("cattail", texture);
	}

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	public void block(Block block) {
		simpleBlock(block, cubeAll(block));
		blockItem(block);
	}

	public void blockFamily(BlockFamily family, Block verticalSlab) {
		Block block = family.getBaseBlock();
		this.block(block);

		if (family.getVariants().containsKey(Variant.CHISELED)) {
			this.block(family.get(Variant.CHISELED));
		}

		if (family.getVariants().containsKey(Variant.SLAB)) {
			SlabBlock slab = (SlabBlock) family.get(Variant.SLAB);
			this.slabBlock(slab, blockTexture(block), blockTexture(block));
			this.blockItem(slab);

			this.verticalSlabBlock(block, (VerticalSlabBlock) verticalSlab);
			this.blockItem(verticalSlab);
		}

		if (family.getVariants().containsKey(Variant.STAIRS)) {
			StairBlock stairs = (StairBlock) family.get(Variant.STAIRS);
			this.stairsBlock(stairs, blockTexture(block));
			this.blockItem(stairs);
		}

		if (family.getVariants().containsKey(Variant.WALL)) {
			WallBlock wall = (WallBlock) family.get(Variant.WALL);
			this.wallBlock(wall, blockTexture(block));
			this.itemModels().getBuilder(name(wall)).parent(this.models().wallInventory(name(wall) + "_inventory", blockTexture(block)));
		}

		if (family.getVariants().containsKey(Variant.FENCE)) {
			FenceBlock fence = (FenceBlock) family.get(Variant.FENCE);
			this.fenceBlock(fence, blockTexture(block));
			this.itemModels().getBuilder(name(fence)).parent(this.models().fenceInventory(name(fence) + "_inventory", blockTexture(block)));
		}

		if (family.getVariants().containsKey(Variant.FENCE_GATE)) {
			FenceGateBlock fenceGate = (FenceGateBlock) family.get(Variant.FENCE_GATE);
			this.fenceGateBlock(fenceGate, blockTexture(block));
			this.blockItem(fenceGate);
		}

		if (family.getVariants().containsKey(Variant.BUTTON)) {
			ButtonBlock button = (ButtonBlock) family.get(Variant.BUTTON);
			ModelFile buttonModel = models().withExistingParent(name(button), "block/button").texture("texture", blockTexture(block));
			ModelFile buttonPressedModel = models().withExistingParent(name(button) + "_pressed", "block/button_pressed").texture("texture", blockTexture(block));
			ModelFile buttonInventoryModel = models().withExistingParent(name(button) + "_inventory", "block/button_inventory").texture("texture", blockTexture(block));
			this.buttonBlock(button, (state -> state.getValue(BlockStateProperties.POWERED) ? buttonPressedModel : buttonModel));
			this.itemModels().getBuilder(name(button)).parent(buttonInventoryModel);
		}

		if (family.getVariants().containsKey(Variant.PRESSURE_PLATE)) {
			BasePressurePlateBlock pressurePlate = (BasePressurePlateBlock) family.get(Variant.PRESSURE_PLATE);
			ModelFile pressurePlateModel = models().withExistingParent(name(pressurePlate), "block/pressure_plate_up").texture("texture", blockTexture(block));
			ModelFile pressurePlateDownModel = models().withExistingParent(name(pressurePlate) + "_down", "block/pressure_plate_down").texture("texture", blockTexture(block));
			this.pressurePlateBlock(pressurePlate, (state -> state.getValue(BlockStateProperties.POWERED) ? pressurePlateDownModel : pressurePlateModel));
			this.blockItem(pressurePlate);
		}

		if (family.getVariants().containsKey(Variant.DOOR)) {
			DoorBlock door = (DoorBlock) family.get(Variant.DOOR);
			this.doorBlock(door, suffix(blockTexture(door), "_bottom"), suffix(blockTexture(door), "_top"));
			this.generatedItem(door, "item");
		}

		if (family.getVariants().containsKey(Variant.TRAPDOOR)) {
			TrapDoorBlock trapdoor = (TrapDoorBlock) family.get(Variant.TRAPDOOR);
			this.trapdoorBlock(trapdoor, blockTexture(trapdoor), true);
			this.itemModels().getBuilder(name(trapdoor)).parent(this.models().trapdoorOrientableBottom(name(trapdoor) + "_bottom", blockTexture(trapdoor)));
		}

		if (family.getVariants().containsKey(Variant.SIGN)) {
			SignBlock sign = (SignBlock) family.get(Variant.SIGN);
			this.simpleBlock(sign, particle(sign, blockTexture(block)));
			this.generatedItem(sign, "item");
		}

		if (family.getVariants().containsKey(Variant.WALL_SIGN)) {
			WallSignBlock wallSign = (WallSignBlock) family.get(Variant.WALL_SIGN);
			this.simpleBlock(wallSign, particle(wallSign, blockTexture(block)));
		}
	}

	public void blockItem(Block block) {
		this.simpleBlockItem(block, new ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
	}

	private void generatedItem(ItemLike item, String type) {
		generatedItem(item, item, type);
	}

	private void generatedItem(ItemLike item, ItemLike texture, String type) {
		itemModels().withExistingParent(ForgeRegistries.ITEMS.getKey(item.asItem()).getPath(), "item/generated").texture("layer0", new ResourceLocation(ForgeRegistries.ITEMS.getKey(texture.asItem()).getNamespace(), type + "/" + ForgeRegistries.ITEMS.getKey(texture.asItem()).getPath()));
	}

	public void crossBlockWithPot(Block cross, Block flowerPot) {
		this.crossBlock(cross);
		this.simpleBlock(flowerPot, models().singleTexture(name(flowerPot), new ResourceLocation("block/flower_pot_cross"), "plant", blockTexture(cross)));
	}

	public void crossBlockWithCustomPot(Block cross, Block flowerPot) {
		this.crossBlock(cross);
		this.simpleBlock(flowerPot, models().singleTexture(name(flowerPot), new ResourceLocation("block/flower_pot_cross"), "plant", blockTexture(flowerPot)));
	}

	public void crossBlock(Block cross) {
		this.simpleBlock(cross, models().cross(name(cross), blockTexture(cross)));
		this.generatedItem(cross, "block");
	}

	public void cubeBottomTopBlock(Block block) {
		this.simpleBlock(block, models().cubeBottomTop(name(block), suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_bottom"), suffix(blockTexture(block), "_top")));
		this.blockItem(block);
	}

	public void directionalBlock(Block block) {
		this.directionalBlock(block, models().cubeBottomTop(name(block), suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_bottom"), suffix(blockTexture(block), "_top")));
		this.blockItem(block);
	}

	public void cubeBottomTopBlock(Block block, Block parent) {
		this.simpleBlock(block, models().cubeBottomTop(name(block), suffix(blockTexture(parent), "_side"), suffix(blockTexture(parent), "_bottom"), suffix(blockTexture(block), "_top")));
		this.blockItem(block);
	}

	public void bookshelfBlock(Block planks, Block bookshelf) {
		this.simpleBlock(bookshelf, this.models().cubeColumn(name(bookshelf), blockTexture(bookshelf), blockTexture(planks)));
		this.blockItem(bookshelf);
	}

	public void ladderBlock(Block ladder) {
		this.horizontalBlock(ladder, models().withExistingParent(name(ladder), "block/ladder").texture("particle", blockTexture(ladder)).texture("texture", blockTexture(ladder)));
		this.generatedItem(ladder, "block");
	}

	public void boardsBlock(Block boards) {
		ModelFile boardsModel = models().getBuilder(name(boards)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards"))).texture("all", blockTexture(boards));
		ModelFile boardsHorizontalModel = models().getBuilder(name(boards) + "_horizontal").parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards_horizontal"))).texture("all", blockTexture(boards));
		this.getVariantBuilder(boards).partialState().with(RotatedPillarBlock.AXIS, Axis.Y).modelForState().modelFile(boardsModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.Z).modelForState().modelFile(boardsHorizontalModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.X).modelForState().modelFile(boardsHorizontalModel).rotationY(270).addModel();
		this.blockItem(boards);
	}

	public void verticalPlanksBlock(Block planks, Block verticalPlanks) {
		this.simpleBlock(verticalPlanks, models().getBuilder(name(verticalPlanks)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/vertical_planks"))).texture("all", blockTexture(planks)));
		this.blockItem(verticalPlanks);
	}

	public void beehiveBlock(Block block) {
		ModelFile beehive = models().orientableWithBottom(name(block), suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		ModelFile beehiveHoney = models().orientableWithBottom(name(block) + "_honey", suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front_honey"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		this.horizontalBlock(block, (state -> state.getValue(BlockStateProperties.LEVEL_HONEY) == 5 ? beehiveHoney : beehive));
		this.blockItem(block);
	}

	public void buttonBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
		this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).uvLock(state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.WALL).rotationX(state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.WALL ? 90 : state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0).rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + (state.getValue(BlockStateProperties.ATTACH_FACE) != AttachFace.CEILING ? 180 : 0)) % 360).build());
	}

	public void pressurePlateBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
		this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).build());
	}

	public void planksCompat(Block planks, Block boards, Block ladder, Block bookshelf, Block beehive, Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> chests, Block verticalPlanks) {
		this.boardsBlock(boards);
		this.ladderBlock(ladder);
		this.bookshelfBlock(planks, bookshelf);
		this.beehiveBlock(beehive);
		this.chestBlocks(planks, chests);
		this.verticalPlanksBlock(planks, verticalPlanks);
	}

	public void logCompat(Block log, Block strippedLog, Block post, Block strippedPost) {
		this.postBlock(log, post);
		this.postBlock(strippedLog, strippedPost);
	}

	public void leavesCompat(Block leaves, Block log, Block leafPile, Block hedge, Block leafCarpet) {
		this.leafPileBlock(leaves, leafPile);
		this.leafCarpetBlock(leaves, leafCarpet);
		this.hedgeBlock(leaves, log, hedge);
	}

	public void leavesBlock(Block leaves) {
		this.simpleBlock(leaves, models().getBuilder(name(leaves)).parent(new UncheckedModelFile(new ResourceLocation("block/leaves"))).texture("all", blockTexture(leaves)));
		this.blockItem(leaves);
	}

	public void leafPileBlock(Block leaves, Block leafPile) {
		this.leafPileBlock(leaves, leafPile, true);
	}

	public void leafPileBlock(Block leaves, Block leafPile, boolean tint) {
		ModelFile leafPileModel = models().getBuilder(name(leafPile)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/" + (tint ? "tinted_" : "") + "leaf_pile"))).texture("all", blockTexture(leaves));
		MultiPartBlockStateBuilder builder = getMultipartBuilder(leafPile);
		builder.part().modelFile(leafPileModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true);
		builder.part().modelFile(leafPileModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).addModel().condition(BlockStateProperties.NORTH, true);
		builder.part().modelFile(leafPileModel).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true);
		builder.part().modelFile(leafPileModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true);
		builder.part().modelFile(leafPileModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true);
		builder.part().modelFile(leafPileModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true);
		builder.part().modelFile(leafPileModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		this.generatedItem(leafPile, leaves, "block");
	}

	public void leafCarpetBlock(Block leaves, Block leafCarpet) {
		this.simpleBlock(leafCarpet, models().getBuilder(name(leafCarpet)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/leaf_carpet"))).texture("all", blockTexture(leaves)));
		this.blockItem(leafCarpet);
	}

	public void hedgeBlock(Block leaves, Block log, Block hedge) {
		ModelFile hedgePost = this.hedgePost(name(hedge) + "_post", blockTexture(log), blockTexture(leaves));
		ModelFile hedgeSide = this.hedgeSide(name(hedge) + "_side", blockTexture(leaves));
		ModelFile hedgeExtend = this.hedgeExtend(name(hedge) + "_extend", blockTexture(leaves));
		this.hedgeBlock(hedge, hedgePost, hedgeSide, hedgeExtend);
		this.itemModels().getBuilder(name(hedge)).parent(hedgePost);
	}

	public static final BooleanProperty[] CHAINED = new BooleanProperty[]{BooleanProperty.create("chain_down"), BooleanProperty.create("chain_up"), BooleanProperty.create("chain_north"), BooleanProperty.create("chain_south"), BooleanProperty.create("chain_west"), BooleanProperty.create("chain_east")};

	public void postBlock(Block log, Block post) {
		ModelFile model = models().getBuilder(name(post)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/post"))).texture("texture", blockTexture(log));
		ModelFile chainSmall = new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/chain_small"));
		ModelFile chainSmallTop = new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/chain_small_top"));
		this.getMultipartBuilder(post).part().modelFile(model).addModel().condition(RotatedPillarBlock.AXIS, Axis.Y).end().part().modelFile(model).rotationX(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.Z).end().part().modelFile(model).rotationX(90).rotationY(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.X).end().part().modelFile(chainSmall).addModel().condition(CHAINED[0], true).end().part().modelFile(chainSmallTop).addModel().condition(CHAINED[1], true).end().part().modelFile(chainSmallTop).rotationX(90).addModel().condition(CHAINED[2], true).end().part().modelFile(chainSmall).rotationX(90).addModel().condition(CHAINED[3], true).end().part().modelFile(chainSmall).rotationX(90).rotationY(90).addModel().condition(CHAINED[4], true).end().part().modelFile(chainSmallTop).rotationX(90).rotationY(90).addModel().condition(CHAINED[5], true).end();
		this.blockItem(post);
	}

	public void hedgeBlock(Block block, ModelFile post, ModelFile side, ModelFile extend) {
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
		builder.part().modelFile(post).addModel().condition(BooleanProperty.create("extend"), false).end().part().modelFile(extend).addModel().condition(BooleanProperty.create("extend"), true);
		PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
			Direction dir = e.getKey();
			if (dir.getAxis().isHorizontal()) {
				builder.part().modelFile(side).rotationY((((int) dir.toYRot()) + 180) % 360).uvLock(true).addModel().condition(e.getValue(), true);
			}
		});
	}

	public ModelFile hedgePost(String name, ResourceLocation log, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_post"))).texture("log", log).texture("leaf", leaf);
	}

	public ModelFile hedgeSide(String name, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_side"))).texture("leaf", leaf);
	}

	public ModelFile hedgeExtend(String name, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_extend"))).texture("leaf", leaf);
	}

	public void signBlocks(Block planks, Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> pair) {
		this.simpleBlock(pair.getFirst().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.simpleBlock(pair.getSecond().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.generatedItem(pair.getFirst().get(), "item");
	}

	public void chestBlocks(Block planks, Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> chests) {
		this.simpleBlock(chests.getFirst().get(), particle(chests.getFirst().get(), blockTexture(planks)));
		this.simpleBlock(chests.getSecond().get(), particle(chests.getFirst().get(), blockTexture(planks)));
		this.simpleBlockItem(chests.getFirst().get(), new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
		this.simpleBlockItem(chests.getSecond().get(), new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
	}

	public ModelFile particle(Block block, ResourceLocation texture) {
		return this.models().getBuilder(name(block)).texture("particle", texture);
	}

	public void logBlocks(Block log, Block wood) {
		this.logBlock(log);

		this.axisBlock((RotatedPillarBlock) wood, blockTexture(log), blockTexture(log));
		this.blockItem(wood);
	}

	public void logBlock(Block block) {
		this.axisBlock((RotatedPillarBlock) block, blockTexture(block), suffix(blockTexture(block), "_top"));
		this.blockItem(block);
	}

	public ModelFile verticalSlab(String name, ResourceLocation texture) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/vertical_slab"))).texture("side", texture).texture("bottom", texture).texture("top", texture);
	}

	public void verticalSlabBlock(Block planks, VerticalSlabBlock slab) {
		ModelFile verticalSlab = this.verticalSlab(name(slab), blockTexture(planks));
		this.getVariantBuilder(slab).partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.NORTH).addModels(new ConfiguredModel(verticalSlab, 0, 0, true)).partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.SOUTH).addModels(new ConfiguredModel(verticalSlab, 0, 180, true)).partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.EAST).addModels(new ConfiguredModel(verticalSlab, 0, 90, true)).partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.WEST).addModels(new ConfiguredModel(verticalSlab, 0, 270, true)).partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE).addModels(new ConfiguredModel(models().cubeAll(name(planks), blockTexture(planks))));
	}

	private String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	private ResourceLocation prefix(String prefix, ResourceLocation rl) {
		return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
	}

	private ResourceLocation suffix(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}

	private ResourceLocation remove(ResourceLocation rl, String remove) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath().replace(remove, ""));
	}
}
