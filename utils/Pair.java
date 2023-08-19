package com.learntocodetogether.utils;

/**
 * @author namvdo
 */
public class Pair<T, E>{
	private final T first;
	private final E second;

	public Pair(T first, E second) {
		this.first = first;
		this.second = second;
	}

	public static<T, E> Pair<T, E> create(T first, E second) {
		return new Pair<>(first, second);
	}

	public T getFirst() {
		return first;
	}

	public E getSecond() {
		return second;
	}
}
