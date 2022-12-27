package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Pair;
import com.learntocodetogether.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author namvdo
 */
public class Day4 {
	public static int solvePart1(String input) {
		int count = 0;
		List<Pair<String, String>> pairs = getPairs(input);
		for(final var pair : pairs) {
			if (within(pair)) {
				count += 1;
			}
		}
		return count;
	}


	public static int solvePart2(String input) {
		int count = 0;
		List<Pair<String, String>> pairs = getPairs(input);
		for(final var pair : pairs) {
			if (within2(pair)) {
				count += 1;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		String input = Utils.getResourceAsString("2022/day4/part1");
//		System.out.println(solvePart1(input));
		System.out.println(solvePart2(input));
	}

	static boolean within(Pair<String, String> pair) {
		Pair<Integer, Integer> range1 = getPairInt(pair).get(0);
		Pair<Integer, Integer> range2 = getPairInt(pair).get(1);
		return range1.getFirst() <= range2.getFirst() && range1.getSecond() >= range2.getSecond()
				|| range2.getFirst() <= range1.getFirst() && range2.getSecond() >= range1.getSecond()
				|| range1.getFirst().equals(range2.getFirst()) && range1.getSecond().equals(range2.getSecond());
	}

	static List<Pair<Integer, Integer>> getPairInt(Pair<String, String> pair) {
		String[] split1 = pair.getFirst().split("-");
		Pair<Integer, Integer> range1 = Pair.create(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
		String[] split2 = pair.getSecond().split("-");
		Pair<Integer, Integer> range2 = Pair.create(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
		return List.of(range1, range2);
	}

	private static boolean within2(Pair<String, String> pair) {
		Pair<Integer, Integer> range1 = getPairInt(pair).get(0);
		Pair<Integer, Integer> range2 = getPairInt(pair).get(1);

	}



	private static List<Pair<String, String>> getPairs(String input) {
		List<Pair<String, String>>	pairs = new ArrayList<>();
		String[] lines = input.split("\n");
		for(final var line : lines) {
			String[] split = line.split(",");
			pairs.add(Pair.create(split[0], split[1]));
		}
		return pairs;
	}
}
