package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.Arrays;

public class Day1 {
    public static void solve(String input) {
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
        System.out.println("Most calories: " + max);
    }

    public static void main(String[] args) {
        String resource = Utils.getResourceAsString("twentytwo/day1/part1");
        solve(resource);
    }
}
