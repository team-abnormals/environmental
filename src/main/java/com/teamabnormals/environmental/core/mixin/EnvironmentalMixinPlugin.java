package com.teamabnormals.environmental.core.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class EnvironmentalMixinPlugin implements IMixinConfigPlugin {
	private boolean incubationLoaded;

	@Override
	public void onLoad(String mixinPackage) {
		try {
			Class.forName("com.teamabnormals.incubation.core.api.EggLayer");
			this.incubationLoaded = true;
		} catch (ClassNotFoundException ignored) {
		}
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return this.incubationLoaded || !mixinClassName.equals("com.teamabnormals.environmental.core.mixin.DuckMixin");
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}