package com.team_abnormals.environmental.core.other;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;

public class EnvironmentalData {
    public static final IDataSerializer<ResourceLocation> RESOURCE_LOCATION = new IDataSerializer<ResourceLocation>()
    {
        @Override
        public void write(PacketBuffer buf, ResourceLocation value)
        {
            buf.writeResourceLocation(value);
        }

        @Override
        public ResourceLocation read(PacketBuffer buf)
        {
            return buf.readResourceLocation();
        }

        @Override
        public ResourceLocation copyValue(ResourceLocation value)
        {
            return new ResourceLocation(value.getNamespace(), value.getPath());
        }
    };

    public static void registerDataSerializers(){
        DataSerializers.registerSerializer(RESOURCE_LOCATION);
    }
}
