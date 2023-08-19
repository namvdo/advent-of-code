package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author namvdo
 */
@Slf4j
public class Day6 {
    public static int solvePart1(String input) {
        char[] chars = input.toCharArray();
        for(int i = 0; i < chars.length - 4; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; j < i + 4; j++) {
               set.add(chars[j]);
               if (set.size() == 4) {
                   return j + 1;
               }
            }
            set.clear();
        }
        return -1;
    }

    public static int solvePart2(String input) {
        char[] chars = input.toCharArray();
        for(int i = 0; i < chars.length - 14; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; j < i + 14; j++) {
               set.add(chars[j]);
               if (set.size() == 14) {
                   return j + 1;
               }
            }
            set.clear();
        }
        return -1;
    }

    public static void main(String[] args) {
        log.info("part 1: {}", solvePart1(Utils.getResourceAsString("2022/day6")));
        log.info("part 2: {}", solvePart2(Utils.getResourceAsString("2022/day6")));
    }
}
