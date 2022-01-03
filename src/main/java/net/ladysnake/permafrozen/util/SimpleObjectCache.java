package net.ladysnake.permafrozen.util;

import net.minecraft.util.math.ChunkPos;

import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * Fast cache that shortens the number of possible hash values via using an `original & mask` algorithm, and uses an array lookup.
 * Original concept by Gegy, this implementation written from scratch by Valoeghese
 */
public class SimpleObjectCache<T> {
	/**
	 * @param size the size of the cache. Must be a power of 2!
	 * @param operator the operation to cache.
	 */
	public SimpleObjectCache(int size, IntFunction<T[]> generator, Operator<T> operator) {
		this.operator = operator;
		this.mask = size - 1;
		this.positions = new long[size];
		this.values = generator.apply(size);

		// It's pretty much never gonna be max value so this is a good "no value" template
		Arrays.fill(this.positions, Long.MAX_VALUE);
	}

	private final Operator<T> operator;

	private int mask;
	private long[] positions;
	private T[] values;

	public T sample(int x, int z) {
		long pos = ChunkPos.toLong(x, z);
		int loc = mix5(x, z) & this.mask;

		if (this.positions[loc] != pos) {
			this.positions[loc] = pos;
			return this.values[loc] = this.operator.sample(x, z);
		} else {
			return this.values[loc];
		}
	}

	private static int mix5(int a, int b) {
		return (((a >> 4) & 1) << 9) |
				(((b >> 4) & 1) << 8) |
				(((a >> 3) & 1) << 7) |
				(((b >> 3) & 1) << 6) |
				(((a >> 2) & 1) << 5) |
				(((b >> 2) & 1) << 4) |
				(((a >> 1) & 1) << 3) |
				(((b >> 1) & 1) << 2) |
				((a & 1) << 1) |
				(b & 1);
	}

	@FunctionalInterface
	public interface Operator<T> {
		T sample(int x, int z);
	}
}