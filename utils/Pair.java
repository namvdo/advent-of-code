package com.learntocodetogether.utils;

/**
 * @author namvdo
 */
public record Pair<T, E>(T first, E second) {

	public static <T, E> Pair<T, E> create(T first, E second) {
		return new Pair<>(first, second);
	}


}
