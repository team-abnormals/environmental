package com.minecraftabnormals.environmental.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class ExecutionerCleaverItem extends SwordItem {
	private final float attackDamage;
	private final float attackSpeed;

	public ExecutionerCleaverItem(IItemTier tier, float attackDamage, float attackSpeed, Properties properties) {
		super(tier, 6, attackSpeed, properties);
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

		if (slot == EquipmentSlotType.MAINHAND) {
			multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
			multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}

	@SubscribeEvent
	public static void onExecutionerCleaverKill(LivingDeathEvent event) {
		if (event.getSource().getTrueSource() instanceof PlayerEntity && event.getEntity() instanceof PlayerEntity) {
			PlayerEntity wielder = (PlayerEntity) event.getSource().getTrueSource();
			PlayerEntity targetPlayer = (PlayerEntity) event.getEntity();
			World world = wielder.world;

			if (wielder.getHeldItemMainhand().getItem() != EnvironmentalItems.EXECUTIONER_CLEAVER.get() || targetPlayer == null)
				return;

			CompoundNBT skullNbt = new CompoundNBT();
			skullNbt.putString("SkullOwner", targetPlayer.getName().getString());

			ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
			stack.setTag(skullNbt);

			if (!world.isRemote())
				world.addEntity(new ItemEntity(world, targetPlayer.getPosX(), targetPlayer.getPosY(), targetPlayer.getPosZ(), stack));
		}
	}
}
