package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.SoundSubRegistryHelper;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalSounds {

	public static final SoundSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getSoundSubHelper();

	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BURP = HELPER.createSoundEvent("entity.slabfish.burp");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_DEATH = HELPER.createSoundEvent("entity.slabfish.death");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_STEP = HELPER.createSoundEvent("entity.slabfish.step");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_HURT = HELPER.createSoundEvent("entity.slabfish.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_EAT = HELPER.createSoundEvent("entity.slabfish.eat");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_TRANSFORM = HELPER.createSoundEvent("entity.slabfish.transform");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BACKPACK = HELPER.createSoundEvent("entity.slabfish.backpack");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_SWEATER = HELPER.createSoundEvent("entity.slabfish.sweater");

	public static final RegistryObject<SoundEvent> SLABRAVE = HELPER.createSoundEvent("music.record.slabrave");
	public static final RegistryObject<SoundEvent> LEAVING_HOME = HELPER.createSoundEvent("music.record.leaving_home");

	public static final RegistryObject<SoundEvent> ENTITY_KOI_FLOP = HELPER.createSoundEvent("entity.koi.flop");

	public static final RegistryObject<SoundEvent> ENTITY_DEER_AMBIENT = HELPER.createSoundEvent("entity.deer.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_DEER_STEP = HELPER.createSoundEvent("entity.deer.step");
	public static final RegistryObject<SoundEvent> ENTITY_DEER_HURT = HELPER.createSoundEvent("entity.deer.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_DEER_DEATH = HELPER.createSoundEvent("entity.deer.death");
	
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = HELPER.createSoundEvent("entity.duck.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_STEP = HELPER.createSoundEvent("entity.duck.step");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT = HELPER.createSoundEvent("entity.duck.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_DEATH = HELPER.createSoundEvent("entity.duck.death");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_EGG = HELPER.createSoundEvent("entity.duck.egg");

	public static final RegistryObject<SoundEvent> ENTITY_YAK_AMBIENT = HELPER.createSoundEvent("entity.yak.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_YAK_MILK = HELPER.createSoundEvent("entity.yak.milk");
	public static final RegistryObject<SoundEvent> ENTITY_YAK_HURT = HELPER.createSoundEvent("entity.yak.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_YAK_DEATH = HELPER.createSoundEvent("entity.yak.death");

	public static final RegistryObject<SoundEvent> ENTITY_PIG_SNIFF = HELPER.createSoundEvent("entity.pig.sniff");
}
