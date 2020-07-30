package com.minecraftabnormals.environmental.common.network.message;

import java.util.function.IntSupplier;

/**
 * <p>Used for interations with login channels.</p>
 *
 * @author Ocelot
 */
public interface EnvironmentalLoginMessage extends IntSupplier
{
    /**
     * @return The login index of this message
     */
    int getLoginIndex();

    /**
     * Sets the login index for this message
     *
     * @param loginIndex The new login index
     */
    void setLoginIndex(int loginIndex);

    @Override
    default int getAsInt()
    {
        return this.getLoginIndex();
    }
}
