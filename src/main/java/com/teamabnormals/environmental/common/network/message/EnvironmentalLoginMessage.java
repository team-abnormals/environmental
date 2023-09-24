package com.teamabnormals.environmental.common.network.message;

import java.util.function.IntSupplier;

/**
 * <p>Used for interations with login channels.</p>
 *
 * @author Ocelot
 */
public class EnvironmentalLoginMessage implements IntSupplier {
	private int loginIndex;

	/**
	 * @return The login index of this message
	 */
	public int getLoginIndex() {
		return this.loginIndex;
	}

	/**
	 * Sets the login index for this message
	 *
	 * @param loginIndex The new login index
	 */
	public void setLoginIndex(final int loginIndex) {
		this.loginIndex = loginIndex;
	}

	@Override
	public int getAsInt() {
		return this.getLoginIndex();
	}
}
