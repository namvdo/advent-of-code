package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.*;

public class Day1 {
    public static int solvePart1(String input) {
        String[] calories = input.split("\n");
        System.out.println(Arrays.toString(calories));
        int max = -1;
        int currentCalories = 0;
        for(final var cal : calories) {
            if (Utils.getAsInt(cal) == Integer.MIN_VALUE)  {
                if (currentCalories > max) {
                    max = currentCalories;
                }
                currentCalories = 0;
            } else {
                currentCalories += Utils.getAsInt(cal);
            }
        }
        return currentCalories;
    }

    public static int solvePart2(String input) {
		String[] calories = input.split("\n");
		PriorityQueue<Integer> pq = new PriorityQueue<>(((Comparator<Integer>) Comparator.naturalOrder()).reversed());
		int cur = 0;
		for(final var cal : calories) {
			if ("".equals(cal.strip())) {
				pq.add(cur);
				cur = 0;
			} else {
				cur += Utils.getAsInt(cal);
			}
		}
		int count = 3;
		int sum = 0;
		while (--count >= 0) {
			if (pq.peek() != null) {
				sum += pq.poll();
			}
		}
		return sum;
    }

    public static void main(String[] args) {
        String part1 = Utils.getResourceAsString("twentytwo/day1/part1");
	    String part2 = Utils.getResourceAsString("twentytwo/day1/part2");
	    System.out.println(solvePart1(part1));
	    System.out.println(solvePart2(part2));
    }
}
