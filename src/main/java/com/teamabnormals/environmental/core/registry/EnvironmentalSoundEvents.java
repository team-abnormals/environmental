package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalSoundEvents {
	public static final SoundSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getSoundSubHelper();

	public static final RegistryObject<SoundEvent> SLABFISH_BURP = HELPER.createSoundEvent("entity.slabfish.burp");
	public static final RegistryObject<SoundEvent> SLABFISH_DEATH = HELPER.createSoundEvent("entity.slabfish.death");
	public static final RegistryObject<SoundEvent> SLABFISH_STEP = HELPER.createSoundEvent("entity.slabfish.step");
	public static final RegistryObject<SoundEvent> SLABFISH_HURT = HELPER.createSoundEvent("entity.slabfish.hurt");
	public static final RegistryObject<SoundEvent> SLABFISH_EAT = HELPER.createSoundEvent("entity.slabfish.eat");
	public static final RegistryObject<SoundEvent> SLABFISH_TRANSFORM = HELPER.createSoundEvent("entity.slabfish.transform");
	public static final RegistryObject<SoundEvent> SLABFISH_BACKPACK = HELPER.createSoundEvent("entity.slabfish.backpack");
	public static final RegistryObject<SoundEvent> SLABFISH_SWEATER = HELPER.createSoundEvent("entity.slabfish.sweater");

	public static final RegistryObject<SoundEvent> KOI_FLOP = HELPER.createSoundEvent("entity.koi.flop");

	public static final RegistryObject<SoundEvent> DEER_AMBIENT = HELPER.createSoundEvent("entity.deer.ambient");
	public static final RegistryObject<SoundEvent> DEER_STEP = HELPER.createSoundEvent("entity.deer.step");
	public static final RegistryObject<SoundEvent> DEER_HURT = HELPER.createSoundEvent("entity.deer.hurt");
	public static final RegistryObject<SoundEvent> DEER_DEATH = HELPER.createSoundEvent("entity.deer.death");

	public static final RegistryObject<SoundEvent> DUCK_AMBIENT = HELPER.createSoundEvent("entity.duck.ambient");
	public static final RegistryObject<SoundEvent> DUCK_STEP = HELPER.createSoundEvent("entity.duck.step");
	public static final RegistryObject<SoundEvent> DUCK_HURT = HELPER.createSoundEvent("entity.duck.hurt");
	public static final RegistryObject<SoundEvent> DUCK_DEATH = HELPER.createSoundEvent("entity.duck.death");
	public static final RegistryObject<SoundEvent> DUCK_EGG = HELPER.createSoundEvent("entity.duck.egg");

	public static final RegistryObject<SoundEvent> YAK_AMBIENT = HELPER.createSoundEvent("entity.yak.ambient");
	public static final RegistryObject<SoundEvent> YAK_MILK = HELPER.createSoundEvent("entity.yak.milk");
	public static final RegistryObject<SoundEvent> YAK_HURT = HELPER.createSoundEvent("entity.yak.hurt");
	public static final RegistryObject<SoundEvent> YAK_DEATH = HELPER.createSoundEvent("entity.yak.death");

	public static final RegistryObject<SoundEvent> TAPIR_LEAF_STEP = HELPER.createSoundEvent("entity.tapir.leaf_step");

	public static final RegistryObject<SoundEvent> PIG_SNIFF = HELPER.createSoundEvent("entity.pig.sniff");

	public static final RegistryObject<SoundEvent> CATTAIL_PICK_SEEDS = HELPER.createSoundEvent("item.cattail_seeds.pick_from_plant");
	public static final RegistryObject<SoundEvent> SHOVEL_DIG = HELPER.createSoundEvent("item.shovel.dig");

	public static final RegistryObject<SoundEvent> LARGE_LILY_PAD_BOUNCE = HELPER.createSoundEvent("block.large_lily_pad.bounce");
	public static final RegistryObject<SoundEvent> GIANT_LILY_PAD_BOUNCE = HELPER.createSoundEvent("block.giant_lily_pad.bounce");

	public static final RegistryObject<SoundEvent> MUSIC_DISC_SLABRAVE = HELPER.createSoundEvent("music_disc.slabrave");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_LEAVING_HOME = HELPER.createSoundEvent("music_disc.leaving_home");
}
