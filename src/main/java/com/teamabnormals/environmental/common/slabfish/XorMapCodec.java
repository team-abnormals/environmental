package com.teamabnormals.environmental.common.slabfish;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.*;

import java.util.Optional;
import java.util.stream.Stream;

public class XorMapCodec<F, S> extends MapCodec<Either<F, S>> {
	private final MapCodec<F> first;
	private final MapCodec<S> second;

	public XorMapCodec(MapCodec<F> first, MapCodec<S> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public <T> DataResult<Either<F, S>> decode(final DynamicOps<T> ops, final MapLike<T> input) {
		final DataResult<Either<F, S>> firstRead = first.decode(ops, input).map(Either::left);
		final DataResult<Either<F, S>> secondRead = second.decode(ops, input).map(Either::right);
		final Optional<Either<F, S>> firstResult = firstRead.result();
		final Optional<Either<F, S>> secondResult = secondRead.result();
		if (firstResult.isPresent() && secondResult.isPresent()) {
			return DataResult.error(() -> "Both alternatives read successfully, can not pick the correct one; first: " + firstResult.get() + " second: " + secondResult.get(), firstResult.get());
		}
		if (firstResult.isPresent()) {
			return firstRead;
		}
		if (secondResult.isPresent()) {
			return secondRead;
		}
		return firstRead.apply2((f, s) -> s, secondRead);
	}

	@Override
	public <T> RecordBuilder<T> encode(final Either<F, S> input, final DynamicOps<T> ops, final RecordBuilder<T> prefix) {
		return input.map(
				value1 -> first.encode(value1, ops, prefix),
				value2 -> second.encode(value2, ops, prefix)
		);
	}

	@Override
	public <T> Stream<T> keys(DynamicOps<T> ops) {
		return Stream.concat(this.first.keys(ops), this.second.keys(ops));
	}

	public static <F, S> MapCodec<Either<F, S>> xor(final MapCodec<F> first, final MapCodec<S> second) {
		return new XorMapCodec<>(first, second);
	}
}