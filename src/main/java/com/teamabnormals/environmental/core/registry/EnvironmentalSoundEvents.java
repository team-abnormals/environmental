package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EnvironmentalSoundEvents {
	public static final SoundSubRegistryHelper SOUND_EVENTS = Environmental.REGISTRY_HELPER.getSoundSubHelper();

	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_BURP = SOUND_EVENTS.createSoundEvent("entity.slabfish.burp");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_DEATH = SOUND_EVENTS.createSoundEvent("entity.slabfish.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_STEP = SOUND_EVENTS.createSoundEvent("entity.slabfish.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_HURT = SOUND_EVENTS.createSoundEvent("entity.slabfish.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_EAT = SOUND_EVENTS.createSoundEvent("entity.slabfish.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_TRANSFORM = SOUND_EVENTS.createSoundEvent("entity.slabfish.transform");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_BACKPACK = SOUND_EVENTS.createSoundEvent("entity.slabfish.backpack");
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABFISH_SWEATER = SOUND_EVENTS.createSoundEvent("entity.slabfish.sweater");

	public static final DeferredHolder<SoundEvent, SoundEvent> KOI_HURT = SOUND_EVENTS.createSoundEvent("entity.koi.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> KOI_DEATH = SOUND_EVENTS.createSoundEvent("entity.koi.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> KOI_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.koi.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> KOI_FLOP = SOUND_EVENTS.createSoundEvent("entity.koi.flop");

	public static final DeferredHolder<SoundEvent, SoundEvent> DEER_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.deer.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEER_STEP = SOUND_EVENTS.createSoundEvent("entity.deer.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEER_HURT = SOUND_EVENTS.createSoundEvent("entity.deer.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEER_DEATH = SOUND_EVENTS.createSoundEvent("entity.deer.death");

	public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.duck.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_STEP = SOUND_EVENTS.createSoundEvent("entity.duck.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_HURT = SOUND_EVENTS.createSoundEvent("entity.duck.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_DEATH = SOUND_EVENTS.createSoundEvent("entity.duck.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_EGG = SOUND_EVENTS.createSoundEvent("entity.duck.egg");

	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.yak.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_MILK = SOUND_EVENTS.createSoundEvent("entity.yak.milk");
	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_HURT = SOUND_EVENTS.createSoundEvent("entity.yak.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_DEATH = SOUND_EVENTS.createSoundEvent("entity.yak.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_CHARGE = SOUND_EVENTS.createSoundEvent("entity.yak.charge");
	public static final DeferredHolder<SoundEvent, SoundEvent> YAK_RAM = SOUND_EVENTS.createSoundEvent("entity.yak.ram");

	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.tapir.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_HURT = SOUND_EVENTS.createSoundEvent("entity.tapir.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_DEATH = SOUND_EVENTS.createSoundEvent("entity.tapir.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_SNIFF = SOUND_EVENTS.createSoundEvent("entity.tapir.sniff");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_REJECT = SOUND_EVENTS.createSoundEvent("entity.tapir.reject");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_STEP = SOUND_EVENTS.createSoundEvent("entity.tapir.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TAPIR_LEAF_STEP = SOUND_EVENTS.createSoundEvent("entity.tapir.leaf_step");

	public static final DeferredHolder<SoundEvent, SoundEvent> PINECONE_GOLEM_HURT = SOUND_EVENTS.createSoundEvent("entity.pinecone_golem.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> PINECONE_GOLEM_DEATH = SOUND_EVENTS.createSoundEvent("entity.pinecone_golem.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> PINECONE_GOLEM_STEP = SOUND_EVENTS.createSoundEvent("entity.pinecone_golem.step");

	public static final DeferredHolder<SoundEvent, SoundEvent> ZEBRA_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.zebra.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZEBRA_ANGRY = SOUND_EVENTS.createSoundEvent("entity.zebra.angry");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZEBRA_DEATH = SOUND_EVENTS.createSoundEvent("entity.zebra.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZEBRA_EAT = SOUND_EVENTS.createSoundEvent("entity.zebra.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZEBRA_HURT = SOUND_EVENTS.createSoundEvent("entity.zebra.hurt");

	public static final DeferredHolder<SoundEvent, SoundEvent> ZORSE_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.zorse.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZORSE_ANGRY = SOUND_EVENTS.createSoundEvent("entity.zorse.angry");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZORSE_DEATH = SOUND_EVENTS.createSoundEvent("entity.zorse.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZORSE_EAT = SOUND_EVENTS.createSoundEvent("entity.zorse.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZORSE_HURT = SOUND_EVENTS.createSoundEvent("entity.zorse.hurt");

	public static final DeferredHolder<SoundEvent, SoundEvent> ZONKEY_AMBIENT = SOUND_EVENTS.createSoundEvent("entity.zonkey.ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZONKEY_ANGRY = SOUND_EVENTS.createSoundEvent("entity.zonkey.angry");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZONKEY_DEATH = SOUND_EVENTS.createSoundEvent("entity.zonkey.death");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZONKEY_EAT = SOUND_EVENTS.createSoundEvent("entity.zonkey.eat");
	public static final DeferredHolder<SoundEvent, SoundEvent> ZONKEY_HURT = SOUND_EVENTS.createSoundEvent("entity.zonkey.hurt");

	public static final DeferredHolder<SoundEvent, SoundEvent> PIG_SNIFF = SOUND_EVENTS.createSoundEvent("entity.pig.sniff");

	public static final DeferredHolder<SoundEvent, SoundEvent> CATTAIL_HARVEST = SOUND_EVENTS.createSoundEvent("block.cattail.harvest");
	public static final DeferredHolder<SoundEvent, SoundEvent> CATTAIL_MEOW = SOUND_EVENTS.createSoundEvent("block.cattail.meow");

	public static final DeferredHolder<SoundEvent, SoundEvent> SHOVEL_DIG = SOUND_EVENTS.createSoundEvent("item.shovel.dig");

	public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_SLABRAVE = SOUND_EVENTS.createSoundEvent("music_disc.slabrave");
	public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_LEAVING_HOME = SOUND_EVENTS.createSoundEvent("music_disc.leaving_home");
}
