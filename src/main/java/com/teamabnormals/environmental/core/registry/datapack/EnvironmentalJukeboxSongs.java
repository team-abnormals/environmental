package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class EnvironmentalJukeboxSongs {
	public static final ResourceKey<JukeboxSong> LEAVING_HOME = create("leaving_home");
	public static final ResourceKey<JukeboxSong> SLABRAVE = create("slabrave");

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, LEAVING_HOME, EnvironmentalSoundEvents.MUSIC_DISC_LEAVING_HOME, 144, 6);
		register(context, SLABRAVE, EnvironmentalSoundEvents.MUSIC_DISC_SLABRAVE, 111, 13);
	}

	private static ResourceKey<JukeboxSong> create(String name) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, Environmental.location(name));
	}

	private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
		context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), lengthInSeconds, comparatorOutput));
	}
}
