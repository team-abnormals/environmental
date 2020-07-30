package com.minecraftabnormals.environmental.common.item;
//package com.pugz.environmental.common.item;
//
//import com.pugz.environmental.common.entity.ButterflyEntity;
//import com.pugz.environmental.core.registry.EnvironmentalEntities;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.SpawnReason;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.GlassBottleItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.util.Hand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import javax.annotation.Nullable;
//import java.util.List;
//
//@SuppressWarnings("rawtypes")
//public class ButterflyBottleItem extends GlassBottleItem {
//    public ButterflyBottleItem(Item.Properties properties) {
//        super(properties);
//    }
//
//	private void releaseButterfly(World worldIn, ItemStack stack, BlockPos pos) {
//        EntityType butterfly = EnvironmentalEntities.BUTTERFLY;
//        butterfly.spawn(worldIn, stack, (PlayerEntity)null, pos.up(), SpawnReason.SPAWN_EGG, true, false);
//    }
//
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
//        ItemStack stack = new ItemStack(this);
//        releaseButterfly(world, stack, player.getPosition().offset(player.getHorizontalFacing()));
//        player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
//        return new ActionResult<>(ActionResultType.SUCCESS, stack);
//    }
//
//    @SuppressWarnings("unused")
//	@OnlyIn(Dist.CLIENT)
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        CompoundNBT compoundnbt = stack.getTag();
//        if (compoundnbt != null && compoundnbt.contains("BottleVariantTag", 3)) {
//            int i = compoundnbt.getInt("BottleVariantTag");
//            TextFormatting[] formatting = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
//            tooltip.add((new TranslationTextComponent(ButterflyEntity.getBottleName())).applyTextStyles(formatting));
//        }
//    }
//}