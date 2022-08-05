package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the slabfish names are met.</p>
 *
 * @author Ocelot
 */
public class SlabfishRenameCondition implements SlabfishCondition {

	private static final Codec<SlabfishRenameCondition> PATTERN_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.comapFlatMap((string) -> {
			try {
				return DataResult.success(Pattern.compile(string));
			} catch (PatternSyntaxException var2) {
				return DataResult.error("Invalid regex pattern '" + string + "': " + var2.getMessage());
			}
		}, Pattern::pattern).fieldOf("pattern").forGetter(SlabfishRenameCondition::getPattern)
	).apply(instance, SlabfishRenameCondition::new));

	private static final Codec<SlabfishRenameCondition> NAMES_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.listOf().xmap(list -> list.toArray(String[]::new), Arrays::asList).fieldOf("names").forGetter(SlabfishRenameCondition::getNames),
		Codec.BOOL.optionalFieldOf("case_sensitive", false).forGetter(SlabfishRenameCondition::isCaseSensitive)
	).apply(instance, SlabfishRenameCondition::new));

	public static final Codec<SlabfishRenameCondition> CODEC = ExtraCodecs.xor(SlabfishRenameCondition.NAMES_CODEC, SlabfishRenameCondition.PATTERN_CODEC).xmap(
		c -> c.left().isPresent() ? c.left().get() : c.right().get(),
		c -> c.getNames() == null ? Either.right(c) : Either.left(c)
	);

	@Nullable
	private final String[] names;
	private final boolean caseSensitive;
	private final Pattern pattern;

	public SlabfishRenameCondition(Pattern pattern) {
		this.names = null;
		this.caseSensitive = false;
		this.pattern = pattern;
	}

	public SlabfishRenameCondition(String[] names, boolean caseSensitive) {
		this.names = names;
		this.caseSensitive = caseSensitive;

		StringBuilder regexBuilder = new StringBuilder();
		for (int i = 0; i < names.length; i++) {
			regexBuilder.append("\\b").append(names[i]);
			if (i < names.length - 1)
				regexBuilder.append("|");
		}

		this.pattern = Pattern.compile(regexBuilder.toString(), (!caseSensitive ? Pattern.CASE_INSENSITIVE : 0) | Pattern.UNICODE_CASE);
	}

	@Nullable
	public String[] getNames() {
		return names;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getEvent() == SlabfishConditionContext.Event.RENAME && this.pattern.matcher(context.getName()).matches();
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.RENAME.get();
	}
}
