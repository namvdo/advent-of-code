package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Pair;
import com.learntocodetogether.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author namvdo
 */
public class Day3 {
	public static int solvePart1(String input) {
		String[] lines = input.split("\n");
		int sum = 0;
		for(final var line : lines) {
			List<String> compartments = getCompartment(line);
			String character = getCommonBetweenCompartment(compartments);
			sum += getPriority(character);
		}
		return sum;
	}

	static int getPriority(String character) {
		char ch = character.toLowerCase().charAt(0);
		if (character.toUpperCase().equals(character)) {
			return (ch - 'a') + 26 + 1;
		} else {
			return (ch - 'a') + 1;
		}
	}

	public static int solvePart2(String input) {
		String[] lines = input.split("\n");
		List<String> commons = getCommonForGroup(lines);
		int sum = 0;
		for(final var common : commons) {
			sum += getPriority(common);
		}
		return sum;
	}

	private static List<String> getCommonForGroup(String[] lines) {
		List<String> res = new ArrayList<>();
		for(int i = 0; i <= lines.length - 3; i+=3) {
			String common = getCommonBetweenCompartment(List.of(
					lines[i],
					lines[i + 1],
					lines[i + 2])
			);
			res.add(common);
		}
		return res;
	}


	public static void main(String[] args) {
		String input = Utils.getResourceAsString("2022/day3/part1");
		System.out.println(solvePart1(input));
		System.out.println(solvePart2(input));
	}

	private static String getCommonBetweenCompartment(List<String> compartments) {
		Set<String> set = Arrays.stream(compartments.get(0).split("")).collect(Collectors.toSet());
		for(int i = 1; i < compartments.size(); i++) {
			List<String> comp = Arrays.stream(compartments.get(i).split("")).collect(Collectors.toList());
			set.retainAll(comp);
		}
		return set.stream().findFirst().orElse("");
	}

	private static List<String> getCompartment(String line) {
		int length = line.length();
		return List.of(line.substring(0, length / 2), line.substring(length / 2));
	}
}
