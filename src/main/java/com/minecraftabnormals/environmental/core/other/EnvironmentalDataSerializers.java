package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalDataSerializers {
	public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, Environmental.MODID);

	public static final IDataSerializer<ResourceLocation> RESOURCE_LOCATION = new IDataSerializer<ResourceLocation>() {
		@Override
		public void write(PacketBuffer buf, ResourceLocation value) {
			buf.writeResourceLocation(value);
		}

		@Override
		public ResourceLocation read(PacketBuffer buf) {
			return buf.readResourceLocation();
		}

		@Override
		public ResourceLocation copyValue(ResourceLocation value) {
			return new ResourceLocation(value.getNamespace(), value.getPath());
		}
	};

	static {
		SERIALIZERS.register("resource_location", () -> new DataSerializerEntry(RESOURCE_LOCATION));
	}
}
